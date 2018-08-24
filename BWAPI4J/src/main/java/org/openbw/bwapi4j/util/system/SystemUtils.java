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

package org.openbw.bwapi4j.util.system;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemUtils {
  public static boolean isWindowsPlatform() {
    return System.getProperty("os.name").contains("Windows");
  }

  public static boolean systemPropertyEquals(final String systemProperty, final boolean status) {
    final String systemPropertyValue = System.getProperty(systemProperty);

    if (systemPropertyValue == null) {
      return false;
    }

    final String[] trueValues = {status ? "1" : "0", Boolean.valueOf(status).toString()};

    for (final String trueValue : trueValues) {
      if (systemPropertyValue.equalsIgnoreCase(trueValue)) {
        return true;
      }
    }

    return false;
  }

  public static boolean systemPropertyEquals(
      final String systemProperty, final String targetPropertyValue) {
    final String systemPropertyValue = System.getProperty(systemProperty);
    return (systemPropertyValue != null
        && systemPropertyValue.equalsIgnoreCase(targetPropertyValue));
  }

  public static String resolvePlatformLibraryFilename(final String libraryName) {
    switch (OSType.computeType()) {
      case WINDOWS:
        return libraryName + ".dll";
      case MAC:
        return "lib" + libraryName + ".dylib";
      default:
        return "lib" + libraryName + ".so";
    }
  }

  public static boolean isPathFoundInPathVariable(final String path, final String pathVariable) {
    final String[] paths = pathVariable.split(File.pathSeparator);

    for (final String directory : paths) {
      final Path targetDirectory;
      try {
        targetDirectory = Paths.get(directory);
      } catch (final Exception e) {
        continue;
      }

      final Path targetPath;
      try {
        targetPath = Paths.get(targetDirectory.toString(), path);
      } catch (final Exception e) {
        continue;
      }

      if (Files.isRegularFile(targetPath) || Files.isDirectory(targetPath)) {
        return true;
      }
    }

    return false;
  }
}
