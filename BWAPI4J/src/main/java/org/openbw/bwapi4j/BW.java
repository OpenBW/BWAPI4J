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

package org.openbw.bwapi4j;

import java.awt.image.ColorModel;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.UnitFactory;
import org.openbw.bwapi4j.unit.UnitImpl;
import org.openbw.bwapi4j.unit.VespeneGeyser;
import org.openbw.bwapi4j.unit.Worker;
import org.openbw.bwapi4j.util.Cache;
import org.openbw.bwapi4j.util.OSType;

public class BW {
  private static final Logger logger = LogManager.getLogger();

  private static final String SYSTEM_PROPERTY_JAVA_LIBRARY_PATH_ID = "java.library.path";

  public enum BridgeType {
    VANILLA("BWAPI4JBridge"),
    OPENBW("OpenBWAPI4JBridge");

    private final String name;

    BridgeType(final String name) {
      this.name = name;
    }

    public String getLibraryName() {
      return this.name;
    }

    public static BridgeType parseBridgeType(final String str) {
      for (final BridgeType bridgeType : BridgeType.values()) {
        if (bridgeType.toString().equalsIgnoreCase(str)) {
          return bridgeType;
        }
      }

      throw new IllegalArgumentException("Unrecognized bridge type: " + str);
    }
  }

  private enum Property {
    EXTRACT_DEPENDENCIES("bwapi4j.extractDependencies"),
    BRIDGE_TYPE("bwapi4j.bridgeType");

    private final String property;

    Property(final String property) {
      this.property = property;
    }

    public String toString() {
      return this.property;
    }
  }

  private BWEventListener listener;
  private InteractionHandler interactionHandler;
  private MapDrawer mapDrawer;
  private DamageEvaluator damageEvaluator;
  private BWMapImpl bwMap;

  private Map<Integer, Player> players;
  private Map<Integer, Unit> units;
  private Map<Integer, Bullet> bullets;
  private UnitFactory unitFactory;
  private int frame;
  private Charset charset;

  private Cache<Map<Player, List<PlayerUnit>>> getUnitsFromPlayerCache;
  private Cache<List<MineralPatch>> getMineralPatchesCache;
  private Cache<List<VespeneGeyser>> getVespeneGeysersCache;

  /**
   * The default value for {@code BridgeType} is {@link BridgeType#VANILLA} on Windows and {@link
   * BridgeType#OPENBW} on Linux. The default value for {@code extractBridgeDependencies} is {@code
   * true}.
   *
   * @see #BW(BWEventListener, BridgeType, boolean)
   */
  public BW(final BWEventListener listener) {
    this(listener, isWindowsPlatform() ? BridgeType.VANILLA : BridgeType.OPENBW, true);
  }

  /**
   * Creates a BW instance required to start a game.
   *
   * @param listener listener to inform of various game events
   * @param bridgeType bridge for Vanilla BW or OpenBW
   * @param extractBridgeDependencies whether to auto-extract the bridge dependencies from the
   *     running JAR file
   */
  public BW(
      final BWEventListener listener, BridgeType bridgeType, boolean extractBridgeDependencies) {
    try {
      bridgeType = BridgeType.parseBridgeType(System.getProperty(Property.BRIDGE_TYPE.toString()));
    } catch (final Exception e) {
      /* Do nothing. */
    }

    extractBridgeDependencies =
        ((extractBridgeDependencies
                && !systemPropertyEquals(Property.EXTRACT_DEPENDENCIES.toString(), false))
            || systemPropertyEquals(Property.EXTRACT_DEPENDENCIES.toString(), true));

    loadSharedLibraries(bridgeType, extractBridgeDependencies);

    this.players = new HashMap<>();
    this.units = new HashMap<>();
    this.bullets = new HashMap<>();
    this.listener = listener;
    this.interactionHandler = new InteractionHandler(this);
    this.mapDrawer = new MapDrawer();
    this.damageEvaluator = new DamageEvaluator();
    this.bwMap = new BWMapImpl(this.interactionHandler);
    setUnitFactory(new UnitFactory());

    try {
      this.charset = Charset.forName("Cp949"); // Korean char set
    } catch (UnsupportedCharsetException e) {
      logger.warn("Korean character set not available. Some characters may not be read properly.");
      this.charset = StandardCharsets.ISO_8859_1;
    }

    this.getUnitsFromPlayerCache =
        new Cache<>(
            () -> {
              final Map<Player, List<PlayerUnit>> playerListMap = new HashMap<>();

              for (final Unit unit : this.units.values()) {
                if (unit instanceof PlayerUnit) {
                  final PlayerUnit playerUnit = (PlayerUnit) unit;

                  final Player player = playerUnit.getPlayer();

                  if (player != null) {
                    final List<PlayerUnit> units =
                        playerListMap.computeIfAbsent(player, list -> new ArrayList<>());
                    units.add(playerUnit);
                  }
                }
              }

              return playerListMap;
            },
            this.interactionHandler);
    this.getMineralPatchesCache =
        new Cache<>(
            () ->
                this.units
                    .values()
                    .stream()
                    .filter(u -> u instanceof MineralPatch)
                    .map(u -> (MineralPatch) u)
                    .collect(Collectors.toList()),
            this.interactionHandler);
    this.getVespeneGeysersCache =
        new Cache<>(
            () ->
                this.units
                    .values()
                    .stream()
                    .filter(u -> u instanceof VespeneGeyser)
                    .map(u -> (VespeneGeyser) u)
                    .collect(Collectors.toList()),
            this.interactionHandler);
  }

  private void extractBridgeDependencies(final BridgeType bridgeType) {
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
            jar, resolvePlatformLibraryFilename(bridgeType.getLibraryName()), cwd.toString());

        for (final String externalLibrary : getExternalLibraryNames()) {
          extractFileIfNotExists(
              jar, resolvePlatformLibraryFilename(externalLibrary), cwd.toString());
        }
      }
    } catch (final Exception e) {
      logger.fatal("Failed to extract dependencies from JAR.");
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static void extractFileIfNotExists(
      final ZipFile zipFile, final String sourceFilename, final String targetDirectory)
      throws ZipException {
    final Path targetFile = Paths.get(targetDirectory, sourceFilename);
    if (!Files.isRegularFile(targetFile)
        && !Files.isDirectory(targetFile)
        && !Files.exists(targetFile)) {
      zipFile.extractFile(sourceFilename, targetDirectory);
    }
  }

  private void loadSharedLibraries(
      final BridgeType bridgeType, final boolean extractBridgeDependencies) {
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

    OSType osType = OSType.computeType();
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
        extractBridgeDependencies(bridgeType);
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

  private List<String> getSharedLibraryNames(final BridgeType bridgeType) {
    final List<String> libraries = new ArrayList<>();

    libraries.add(bridgeType.getLibraryName());
    libraries.addAll(getExternalLibraryNames());

    return libraries;
  }

  private List<String> getExternalLibraryNames() {
    final List<String> libNames = new ArrayList<>();

    if (isWindowsPlatform()) {
      //            libNames.add("libgmp-10");
      //            libNames.add("libmpfr-4");
    } else {
      //            libNames.add("BWTA2");
    }

    return libNames;
  }
  private static boolean isWindowsPlatform() {
    return System.getProperty("os.name").contains("Windows");
  }

  private Path getCurrentWorkingDirectory() {
    return Paths.get("").toAbsolutePath();
  }

  private static void forceLibraryPathReload() throws NoSuchFieldException, IllegalAccessException {
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

      if (isPathFoundInPathVariable(currentLibraryPath, path)) {
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

  private static boolean systemPropertyEquals(final String systemProperty, final boolean status) {
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

  private static boolean systemPropertyEquals(
      final String systemProperty, final String targetPropertyValue) {
    final String systemPropertyValue = System.getProperty(systemProperty);

    return (systemPropertyValue != null)
        && systemPropertyValue.equalsIgnoreCase(targetPropertyValue);
  }

  private static String resolvePlatformLibraryFilename(String libraryName) {
    switch (OSType.computeType()) {
      case WINDOWS:
        return libraryName + ".dll";
      case MAC:
        return "lib" + libraryName + ".dylib";
        default:
          return "lib" + libraryName + ".so";
    }
  }

  private static boolean isPathFoundInPathVariable(final String pathVariable, final String path) {
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

  public void startGame() {
    setOnStartInitializationIsDone(false);
    final BW myBw = this;
    final Thread thread = new Thread(() -> startGame(myBw));

    thread.start();
    logger.trace("calling native mainThread...");
    mainThread();
    try {
      thread.join();
    } catch (final InterruptedException e) {
      logger.error("error joining thread.", e);
      e.printStackTrace();
    }
    logger.trace("finished thread.");
  }

  public void createUnit(Player owner, UnitType type, int posX, int posY) {
    this.createUnit(owner.getId(), type.getId(), posX, posY);
  }

  private native void createUnit(int ownerId, int unitTypeId, int posX, int posY);

  public void killUnit(Unit unit) {
    this.killUnit(unit.getId());
  }

  private native void killUnit(int unitID);

  public native void exit();

  private native void mainThread();

  private native void startGame(BW bw);

  private native int[] getAllUnitsData();

  private native int[] getAllBulletsData();

  private native int[] getAllPlayersData();

  private native int[] getGameData();

  private native int getClientVersion();

  private native String getPlayerName(int playerId);

  private native int[] getResearchStatus(int playerId);

  private native int[] getUpgradeStatus(int playerId);

  public void setUnitFactory(UnitFactory unitFactory) {
    this.unitFactory = unitFactory;
    this.unitFactory.setBW(this);
  }

  public BWMap getBWMap() {
    return this.bwMap;
  }

  public MapDrawer getMapDrawer() {
    return this.mapDrawer;
  }

  public DamageEvaluator getDamageEvaluator() {
    return this.damageEvaluator;
  }

  public InteractionHandler getInteractionHandler() {
    return this.interactionHandler;
  }

  private void updateGame() {
    int[] data = this.getGameData();
    this.interactionHandler.update(data);
  }

  private void updateAllBullets() {
    int[] bulletData = this.getAllBulletsData();

    for (int index = 0; index < bulletData.length; index += Bullet.CacheIndex.values().length) {
      int bulletId = bulletData[index + Bullet.CacheIndex.ID.ordinal()];
      Bullet bullet = this.bullets.get(bulletId);
      if (bullet == null) {
        bullet = new Bullet(this);
        this.bullets.put(bulletId, bullet);
        bullet.initialize(bulletData, index);
      }
      bullet.update(bulletData, index);
    }
  }

  private boolean typeChanged(UnitType oldType, UnitType newType) {
    return !oldType.equals(newType)
        && !oldType.equals(UnitType.Terran_Siege_Tank_Siege_Mode)
        && !newType.equals(UnitType.Terran_Siege_Tank_Siege_Mode);
  }

  private void updateAllUnits(int frame) {
    for (Unit unit : this.units.values()) {
      unit.preUpdate();
    }
    int[] unitData = this.getAllUnitsData();

    for (int index = 0; index < unitData.length; index += UnitImpl.TOTAL_PROPERTIES) {
      int unitId = unitData[index + 0]; // TODO: Use the enum from the Unit class.
      int typeId = unitData[index + 3]; // TODO: Use the enum from the Unit class.
      Unit unit = this.units.get(unitId);
      if (unit == null || typeChanged(unit.getInitialType(), UnitType.values()[typeId])) {
        if (unit != null) {
          logger.debug(
              "unit {} changed type from {} to {}.",
              unit.getId(),
              unit.getInitialType(),
              UnitType.values()[typeId]);
        }
        logger.trace(
            "creating unit for id {} and type {} ({}) ...",
            unitId,
            typeId,
            UnitType.values()[typeId]);

        unit = unitFactory.createUnit(unitId, UnitType.values()[typeId], frame);

        if (unit == null) {
          logger.error(
              "could not create unit for id {} and type {}.", unitId, UnitType.values()[typeId]);
        } else {
          logger.trace("state: {}", unit.exists() ? "completed" : "created");

          this.units.put(unitId, unit);
          unit.initialize(unitData, index, frame);
          unit.update(unitData, index, frame);
          logger.trace("initial pos: {}", unit.getInitialTilePosition());
          logger.trace("current pos: {}", unit.getTilePosition());

          logger.trace(" done.");
        }
      } else {
        unit.update(unitData, index, frame);
      }
    }
  }

  // TODO: Determine why this function seems to be called twice when using OpenBW. E.g.
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 1 ...
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 0 ...
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 1 ...
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 0 ...
  private void updateAllPlayers() {
    int[] playerData = this.getAllPlayersData();

    for (int index = 0; index < playerData.length; index += Player.CacheIndex.values().length) {
      int playerId = playerData[index + 0]; // TODO: Use the enum from the Player class.
      Player player = this.players.get(playerId);
      if (player == null) {
        logger.debug("creating player for id {} ...", playerId);
        player = new Player(playerId, this.getPlayerName(playerId), this);
        logger.trace("player name: {}", player.getName());
        this.players.put(playerId, player);
        logger.trace("initializing...");
        player.initialize(playerData, index);
        logger.trace(" done.");
      }
      player.update(
          playerData, index, this.getResearchStatus(playerId), this.getUpgradeStatus(playerId));
    }
  }

  public Player getPlayer(int playerId) {
    return this.players.get(playerId);
  }

  public Collection<Player> getAllPlayers() {
    return this.players.values();
  }

  public Unit getUnit(int unitId) {
    return this.units.get(unitId);
  }

  public Collection<Bullet> getBullets() {
    return this.bullets.values();
  }

  public Bullet getBullet(int bulletId) {
    return this.bullets.get(bulletId);
  }

  /**
   * Gets all units for given player.
   *
   * @param player player whose units to return
   * @return list of <code>PlayerUnit</code>
   */
  public List<PlayerUnit> getUnits(Player player) {
    return this.getUnitsFromPlayerCache.get().getOrDefault(player, Collections.emptyList());
  }

  /**
   * Gets a list of all mineral patches.
   *
   * @return list of mineral patches
   */
  public List<MineralPatch> getMineralPatches() {
    return this.getMineralPatchesCache.get();
  }

  /**
   * Gets a list of all vespene geysers.
   *
   * @return list of vespene geysers
   */
  public List<VespeneGeyser> getVespeneGeysers() {
    return this.getVespeneGeysersCache.get();
  }

  public Collection<Unit> getAllUnits() {
    return this.units.values();
  }

  // TODO: Remove "canBuildHere" functions from this class. It should only be in bwMap
  public boolean canBuildHere(TilePosition position, UnitType type) {
    return bwMap.canBuildHere(position, type);
  }

  public boolean canBuildHere(TilePosition position, UnitType type, Worker builder) {
    return bwMap.canBuildHere(position, type, builder);
  }

  private void preFrame() {
    logger.trace("updating game state for frame {}...", this.frame);
    updateGame();
    logger.trace("updated game.");
    updateAllPlayers();
    logger.trace("updated players.");
    updateAllUnits(this.frame);
    logger.trace("updated all units.");
    updateAllBullets();
    logger.trace("updated all bullets.");
  }

  private void onStart() {
    try {
      setOnStartInitializationIsDone(false);

      logger.trace(" --- onStart called.");
      this.frame = 0;
      this.players.clear();
      this.units.clear();
      this.bullets.clear();

      logger.trace(" --- calling initial preFrame...");
      preFrame();
      logger.trace("done.");
      listener.onStart();
    } catch (Throwable e) {
      logger.error("exception during onStart.", e);
      throw e;
    } finally {
      setOnStartInitializationIsDone(true);
    }
  }

  private native void setOnStartInitializationIsDone_native(boolean isDone);

  private void setOnStartInitializationIsDone(final boolean isDone) {
    setOnStartInitializationIsDone_native(isDone);
  }

  private void onEnd(boolean isWinner) {
    catchAllCalling(listener::onEnd, isWinner);
  }

  private void onFrame() {
    //    	logger.debug("onFrame {}", this.frame);
    try {
      preFrame();
      this.frame++;
      listener.onFrame();
    } catch (Throwable e) {
      logger.error("exception during onFrame", e);
      throw e;
    }
  }

  private void onSendText(String text) {
    catchAllCalling(listener::onSendText, text);
  }

  private void onReceiveText(int playerId, String text) {
    Player player = this.players.get(playerId);
    catchAllCalling(listener::onReceiveText, player, text);
  }

  private void onPlayerLeft(int playerId) {
    Player player = this.players.get(playerId);
    catchAllCalling(listener::onPlayerLeft, player);
  }

  private void onNukeDetect(int x, int y) {
    catchAllCalling(listener::onNukeDetect, new Position(x, y));
  }

  private void onUnitDiscover(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitDiscover: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitDiscover, unit);
  }

  private void onUnitEvade(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitEvade: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitEvade, unit);
  }

  private void onUnitShow(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitShow: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitShow, unit);
  }

  private void onUnitHide(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitHide: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitHide, unit);
  }

  private void onUnitCreate(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitCreate: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitCreate, unit);
  }

  private void onUnitDestroy(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitDestroy: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitDestroy, unit);
    this.units.remove(unitId);
  }

  private void onUnitMorph(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitMorph: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitMorph, unit);
  }

  private void onUnitRenegade(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitRenegade: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitRenegade, unit);
  }

  private void onSaveGame(String gameName) {
    catchAllCalling(listener::onSaveGame, gameName);
  }

  private void onUnitComplete(int unitId) {
    Unit unit = this.units.get(unitId);
    if (unit == null) {
      logger.error("onUnitComplete: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitComplete, unit);
  }
  /**
   * Calls from native code would just ignore any kind of exception, therefore we catch and log them
   * before returning.
   */
  private static <T> void catchAllCalling(Consumer<T> consumer, T param) {
    try {
      consumer.accept(param);
    } catch (Throwable t) {
      logger.error(t);
    }
  }

  /**
   * Calls from native code would just ignore any kind of exception, therefore we catch and log them
   * before returning.
   */
  private static <T, U> void catchAllCalling(BiConsumer<T, U> consumer, T param1, U param2) {
    try {
      consumer.accept(param1, param2);
    } catch (Throwable t) {
      logger.error(t);
    }
  }
}
