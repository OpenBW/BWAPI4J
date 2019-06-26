////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j.util;

import bwapi.BW;
import bwapi.BWAPI4J;
import java.awt.image.ColorModel;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.util.system.OSType;
import org.openbw.bwapi4j.util.system.SystemUtils;

public class DependencyManager {
  private static final Logger logger = LogManager.getLogger();

  private static final String SYSTEM_PROPERTY_JAVA_LIBRARY_PATH_ID = "java.library.path";

  public void extractDependencies(final BWAPI4J.BridgeType bridgeType) {
    try {
      final URL jarURL = BW.class.getProtectionDomain().getCodeSource().getLocation();
      final Path jarFile = Paths.get(jarURL.toURI());
      final Path cwd = getCurrentWorkingDirectory();

      if (Files.isRegularFile(jarFile)
          && !Files.isDirectory(jarFile)
          && jarFile.toString().endsWith(".jar")) {
        logger.debug("Extracting dependencies from: " + jarFile.toString());

        final ZipFile jar = new ZipFile(jarFile.toFile());

        extractFileIfNotExists(
            jar,
            SystemUtils.resolvePlatformLibraryFilename(bridgeType.getLibraryName()),
            cwd.toString());

        for (final String externalLibrary : getExternalLibraryNames()) {
          extractFileIfNotExists(
              jar, SystemUtils.resolvePlatformLibraryFilename(externalLibrary), cwd.toString());
        }
      }
    } catch (final Exception e) {
      logger.fatal("Failed to extract dependencies from JAR.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void extractFileIfNotExists(
      final ZipFile zipFile, final String sourceFilename, final String targetDirectory)
      throws ZipException {
    final Path targetFile = Paths.get(targetDirectory, sourceFilename);
    if (!Files.isRegularFile(targetFile)
        && !Files.isDirectory(targetFile)
        && !Files.exists(targetFile)) {
      zipFile.extractFile(sourceFilename, targetDirectory);
    }
  }

  public void loadSharedLibraries(
      final BWAPI4J.BridgeType bridgeType, final boolean extractBridgeDependencies) {
    logger.info(
        "jvm: {} ({}-bit)",
        System.getProperty("java.version"),
        System.getProperty("sun.arch.data.model"));
    logger.info("os: {}", System.getProperty("os.name"));

    logger.debug("cwd: {}", getCurrentWorkingDirectory().toString());
    logger.debug("user directory: {}", System.getProperty("user.dir"));
    logger.debug(
        "bot directory: {}",
        BW.class.getProtectionDomain().getCodeSource().getLocation().getPath());

    logger.debug("library path: " + getLibraryPath());

    logger.debug("bridge type: " + bridgeType.toString());

    final OSType osType = OSType.computeType();
    if (osType.equals(OSType.MAC)) {
      // static code in ColorModel loads AWT native library
      // if this isn't loaded before SDL2, the ui doesn't show up on MacOS
      ColorModel.getRGBdefault();
    }

    /* this is pretty hacky but required for now to run BWAPI4J on both Windows and Linux without modifying the source.
     *
     * Possible future solutions:
     *  - name BWAPI4JBridge and OpenBWAPI4JBridge the same. This way linux loads <name>.so and windows loads <name>.dll
     *  - build a single bwta.dll rather than libgmp-10 and libmpfr-4 and load bwta.so accordingly on linux.
     */
    final List<String> sharedLibraries = getSharedLibraryNames(bridgeType);
    try {
      loadLibraries(sharedLibraries);
    } catch (final UnsatisfiedLinkError e1) {
      addLibraryPath(getCurrentWorkingDirectory().toAbsolutePath().toString());

      if (extractBridgeDependencies) {
        extractDependencies(bridgeType);
      }

      try {
        loadLibraries(sharedLibraries);
      } catch (final UnsatisfiedLinkError e2) {
        logger.fatal("Could not load shared libraries.", e2);
        e2.printStackTrace();
        System.exit(1);
      }
    }

    logger.info("Successfully loaded shared libraries.");
  }

  private void loadLibraries(final List<String> libraries) {
    for (final String library : libraries) {
      System.loadLibrary(library);
    }
  }

  private List<String> getSharedLibraryNames(final BWAPI4J.BridgeType bridgeType) {
    final List<String> libraries = new ArrayList<>();

    libraries.add(bridgeType.getLibraryName());
    libraries.addAll(getExternalLibraryNames());

    return libraries;
  }

  private List<String> getExternalLibraryNames() {
    final List<String> libNames = new ArrayList<>();

    if (SystemUtils.isWindowsPlatform()) {
      //            libNames.add("libgmp-10");
      //            libNames.add("libmpfr-4");
    } else {
      //            libNames.add("BWTA2");
    }

    return libNames;
  }

  private Path getCurrentWorkingDirectory() {
    return Paths.get("").toAbsolutePath();
  }

  private void forceLibraryPathReload() throws NoSuchFieldException, IllegalAccessException {
    final java.lang.reflect.Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
    sysPathsField.setAccessible(true);
    sysPathsField.set(null, null);
  }

  private String getLibraryPath() {
    return System.getProperty(SYSTEM_PROPERTY_JAVA_LIBRARY_PATH_ID);
  }

  private void setLibraryPath(final String path) {
    try {
      forceLibraryPathReload();

      System.setProperty(SYSTEM_PROPERTY_JAVA_LIBRARY_PATH_ID, path);

      logger.info("Changed library path to: {}", getLibraryPath());
    } catch (Exception e) {
      logger.error("Could not modify library path to: " + path, e);
      e.printStackTrace();
    }
  }

  private void addLibraryPath(final String path) {
    try {
      //            final java.lang.reflect.Field usrPathsField =
      // ClassLoader.class.getDeclaredField("usr_paths");
      //            usrPathsField.setAccessible(true);
      //
      //            final String[] libraryPaths = (String[]) usrPathsField.get(null);
      //            for (final String libraryPath : libraryPaths) {
      //                if (libraryPath.equals(path)) {
      //                    return;
      //                }
      //            }
      //
      //            final String[] newLibraryPaths = Arrays.copyOf(libraryPaths, libraryPaths.length
      // + 1);
      //            newLibraryPaths[newLibraryPaths.length - 1] = path;
      //            usrPathsField.set(null, newLibraryPaths);

      final String currentLibraryPath = getLibraryPath();

      if (SystemUtils.isPathFoundInPathVariable(path, currentLibraryPath)) {
        logger.warn("The specified path already exists in library path: " + path);
        return;
      }

      logger.info("Adding library path: {}", path);

      final String libraryPathDelimiter = File.pathSeparator;
      final String newLibraryPath =
          currentLibraryPath
              + (!currentLibraryPath.endsWith(libraryPathDelimiter) ? libraryPathDelimiter : "")
              + path;
      setLibraryPath(newLibraryPath);
    } catch (final Exception e) {
      logger.error("Could not add library path: " + path, e);
      e.printStackTrace();
    }
  }
}
