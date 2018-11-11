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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.GameType;
import org.openbw.bwapi4j.type.Key;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.TechTypeBridge;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UnitTypeBridge;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.UpgradeTypeBridge;
import org.openbw.bwapi4j.type.WeaponType;
import org.openbw.bwapi4j.type.WeaponTypeBridge;
import org.openbw.bwapi4j.unit.DefaultUnitFactory;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.UnitBridge;
import org.openbw.bwapi4j.unit.UnitFactory;
import org.openbw.bwapi4j.unit.WeaponBridge;
import org.openbw.bwapi4j.util.Cache;
import org.openbw.bwapi4j.util.DependencyManager;
import org.openbw.bwapi4j.util.system.SystemUtils;

public class BW {
  private static final Logger logger = LogManager.getLogger();

  private final DependencyManager dependencyManager;

  private BWEventListener listener;
  private InteractionHandler interactionHandler;
  private MapDrawer mapDrawer;
  private DamageEvaluator damageEvaluator;
  private BWMapImpl bwMap;
  private UnitBridge unitDataBridge;
  private PlayerBridge playerBridge;
  private BulletBridge bulletBridge;

  private UpgradeTypeBridge upgradeTypeBridge;
  private WeaponTypeBridge weaponTypeBridge;
  private TechTypeBridge techTypeBridge;
  private UnitTypeBridge unitTypeBridge;

  private Map<Integer, Player> players;
  private Map<Integer, Unit> units;
  private Map<Integer, Bullet> bullets;
  private final UnitFactory unitFactory;
  private Charset charset;

  private Cache<Map<Player, List<Unit>>> getUnitsFromPlayerCache;
  private Cache<List<Unit>> getMineralPatchesCache;
  private Cache<List<Unit>> getVespeneGeysersCache;

  /**
   * The default value for {@code BridgeType} is {@link BWAPI4J.BridgeType#VANILLA} on Windows and
   * {@link BWAPI4J.BridgeType#OPENBW} on Linux. The default value for {@code extractDependencies}
   * is {@code true}.
   *
   * @see #BW(BWEventListener, BWAPI4J.BridgeType, boolean)
   */
  public BW(final BWEventListener listener) {
    this(
        listener,
        SystemUtils.isWindowsPlatform() ? BWAPI4J.BridgeType.VANILLA : BWAPI4J.BridgeType.OPENBW,
        true);
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
      final BWEventListener listener,
      BWAPI4J.BridgeType bridgeType,
      boolean extractBridgeDependencies) {
    this.dependencyManager = new DependencyManager();

    try {
      bridgeType =
          BWAPI4J.BridgeType.parseBridgeType(
              System.getProperty(BWAPI4J.Property.BRIDGE_TYPE.toString()));
    } catch (final Exception e) {
      /* Do nothing. */
    }

    /* The system property takes precedence. */
    final String extractBridgeDependenciesSystemProperty =
        System.getProperty(BWAPI4J.Property.EXTRACT_DEPENDENCIES.toString());
    if (extractBridgeDependenciesSystemProperty != null
        && !extractBridgeDependenciesSystemProperty.trim().isEmpty()) {
      extractBridgeDependencies =
          SystemUtils.systemPropertyEquals(BWAPI4J.Property.EXTRACT_DEPENDENCIES.toString(), true)
              || !SystemUtils.systemPropertyEquals(
                  BWAPI4J.Property.EXTRACT_DEPENDENCIES.toString(), false);
    }

    this.dependencyManager.loadSharedLibraries(bridgeType, extractBridgeDependencies);

    this.players = new HashMap<>();
    this.units = new HashMap<>();
    this.bullets = new HashMap<>();
    this.listener = listener;
    this.interactionHandler = new InteractionHandler(this);
    this.mapDrawer = new MapDrawer();
    this.damageEvaluator = new DamageEvaluator();
    this.bwMap = new BWMapImpl(this.interactionHandler);
    this.unitDataBridge = new UnitBridge(this, new WeaponBridge(this));
    this.playerBridge = new PlayerBridge(this);
    this.bulletBridge = new BulletBridge(this);
    this.upgradeTypeBridge = new UpgradeTypeBridge(this);
    this.weaponTypeBridge = new WeaponTypeBridge(this);
    this.techTypeBridge = new TechTypeBridge(this);
    this.unitTypeBridge = new UnitTypeBridge(this);

    this.unitFactory = new DefaultUnitFactory(this);

    try {
      this.charset = Charset.forName("Cp949"); /* Korean char set */
    } catch (UnsupportedCharsetException e) {
      logger.warn("Korean character set not available. Some characters may not be read properly.");
      this.charset = StandardCharsets.ISO_8859_1;
    }
  }

  public void startGame() {
    startGame(this);
  }

  public void createUnit(Player owner, UnitType type, int posX, int posY) {
    this.createUnit(owner.getID(), type.getID(), posX, posY);
  }

  private native void createUnit(int ownerId, int unitTypeId, int posX, int posY);

  public void killUnit(Unit unit) {
    this.killUnit(unit.getID());
  }

  private native void killUnit(int unitID);

  public native void exit();

  private native void startGame(BW bw);

  private native int[] getUpgradeTypesData();

  private native int[] getWeaponTypesData();

  private native int[] getTechTypesData();

  private native int[] getUnitTypesData();

  private native int[] getAllUnitsData();

  private native int[] getAllBulletsData();

  private native int[] getAllPlayersData();

  private native int[] getGameData();

  private native int getClientVersion();

  private native String getPlayerName(int playerId);

  private native int[] getPlayerExtra(int playerId);

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

    int index = 0;
    while (index < bulletData.length) {
      int bulletId = bulletData[index + BulletBridge.ID];
      Bullet bullet = this.bullets.get(bulletId);
      if (bullet == null) {
        bullet = new Bullet();
        this.bullets.put(bulletId, bullet);
        bulletBridge.initialize(bullet, bulletData, index);
      }
      index = bulletBridge.update(bullet, bulletData, index);
    }
  }

  private boolean typeChanged(final UnitType oldType, final UnitType newType) {
    return !oldType.equals(newType)
        && !oldType.equals(UnitType.Terran_Siege_Tank_Siege_Mode)
        && !newType.equals(UnitType.Terran_Siege_Tank_Siege_Mode);
  }

  private void updateAllUnits(final int frame) {
    for (final Unit unit : this.units.values()) {
      unitDataBridge.reset(unit);
    }

    final int[] unitData = this.getAllUnitsData();

    int index = 0;
    while (index < unitData.length) {
      final int unitId = unitData[index + UnitBridge.ID];
      final int unitTypeId = unitData[index + UnitBridge.TYPE];

      Unit unit = units.get(unitId);

      if (unit == null) {
        logger.trace(
            "creating unit for id {} and type {} ({}) ...",
            unitId,
            unitTypeId,
            UnitType.values()[unitTypeId]);

        unit = unitFactory.createUnit(unitId, UnitType.values()[unitTypeId], frame);

        if (unit == null) {
          logger.error(
              "could not create unit for id {} and type {}.",
              unitId,
              UnitType.values()[unitTypeId]);
        } else {
          logger.trace("state: {}", unit.exists() ? "completed" : "created");

          units.put(unitId, unit);

          unitDataBridge.initialize(unit, unitData, index);

          index = unitDataBridge.update(unit, unitData, index);

          logger.trace("initial pos: {}", unit.getInitialTilePosition());
          logger.trace("current pos: {}", unit.getTilePosition());

          logger.trace(" done.");
        }
      } else {
        if (typeChanged(unit.getType(), UnitType.values()[unitTypeId])) {
          logger.debug(
              "unit {} changed type from {} to {}.",
              unit.getID(),
              unit.getType(),
              UnitType.values()[unitTypeId]);
        }

        index = unitDataBridge.update(unit, unitData, index);
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

    int index = 0;
    while (index < playerData.length) {
      int playerId = playerData[index + PlayerBridge.ID];
      Player player = this.players.get(playerId);
      if (player == null) {
        logger.debug("creating player for id {} ...", playerId);
        player = new Player(this, playerId, this.getPlayerName(playerId));
        logger.trace("player name: {}", player.getName());
        this.players.put(playerId, player);
        logger.trace("initializing...");
        playerBridge.initialize(player, playerData, index);
        player.initialize();
        logger.trace(" done.");
      }
      index = playerBridge.update(player, playerData, index);
      player.update(this.getPlayerExtra(playerId));
    }
  }

  public Collection<Player> getAllPlayers() {
    return this.players.values();
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
  public List<Unit> getUnits(Player player) {
    return this.getUnitsFromPlayerCache.get().getOrDefault(player, Collections.emptyList());
  }

  /**
   * Gets a list of all vespene geysers.
   *
   * @return list of vespene geysers
   */
  public List<Unit> getVespeneGeysers() {
    return this.getVespeneGeysersCache.get();
  }

  private void preFrame() {
    //    logger.trace("updating game state for frame {}...", this.frame);
    updateGame();
    logger.trace("updated game.");
    updateAllPlayers();
    logger.trace("updated players.");
    updateAllUnits(getInteractionHandler().getFrameCount());
    logger.trace("updated all units.");
    updateAllBullets();
    logger.trace("updated all bullets.");
  }

  private void resetCache() {
    this.getUnitsFromPlayerCache =
        new Cache<>(
            () -> {
              final Map<Player, List<Unit>> playerListMap = new HashMap<>();

              for (final Unit unit : this.units.values()) {
                final Player player = unit.getPlayer();

                if (player != null) {
                  final List<Unit> units =
                      playerListMap.computeIfAbsent(player, list -> new ArrayList<>());
                  units.add(unit);
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
                    .filter(u -> u.getType().isMineralField())
                    .collect(Collectors.toList()),
            this.interactionHandler);

    this.getVespeneGeysersCache =
        new Cache<>(
            () ->
                this.units
                    .values()
                    .stream()
                    .filter(u -> u.getType() == UnitType.Resource_Vespene_Geyser)
                    .collect(Collectors.toList()),
            this.interactionHandler);

    this.interactionHandler.resetCache();

    this.bwMap.resetCache();
  }

  private void onStart() {
    try {
      logger.trace(" --- onStart called.");
      this.players.clear();
      this.units.clear();
      this.bullets.clear();

      resetCache();

      initializeTypes();

      logger.trace(" --- calling initial preFrame...");
      preFrame();
      logger.trace("done.");
      listener.onStart();
    } catch (Throwable e) {
      logger.error("exception during onStart.", e);
      throw e;
    }
  }

  private void initializeTypes() {
    initializeUpgradeTypes();
    initializeWeaponTypes();
    initializeTechTypes();
    initializeUnitTypes();
  }

  private void initializeUpgradeTypes() {
    int data[] = getUpgradeTypesData();
    int index = 0;
    while (index < data.length) {
      int id = data[index + UpgradeTypeBridge.ID];
      index = upgradeTypeBridge.update(UpgradeType.withId(id), data, index);
    }
  }

  private void initializeWeaponTypes() {
    int data[] = getWeaponTypesData();
    int index = 0;
    while (index < data.length) {
      int id = data[index + WeaponTypeBridge.ID];
      index = weaponTypeBridge.update(WeaponType.withId(id), data, index);
    }
  }

  private void initializeTechTypes() {
    int data[] = getTechTypesData();
    int index = 0;
    while (index < data.length) {
      int id = data[index + TechTypeBridge.ID];
      index = techTypeBridge.update(TechType.withId(id), data, index);
    }
  }

  private void initializeUnitTypes() {
    int data[] = getUnitTypesData();
    int index = 0;
    while (index < data.length) {
      int id = data[index + UnitTypeBridge.ID];
      index = unitTypeBridge.update(UnitType.withId(id), data, index);
    }
  }

  private void onEnd(boolean isWinner) {
    catchAllCalling(listener::onEnd, isWinner);
  }

  private void onFrame() {
    try {
      preFrame();
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

  public List<Force> getForces() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Collection<Player> getPlayers() {
    return players.values();
  }

  public Collection<Unit> getAllUnits() {
    return units.values();
  }

  public List<Unit> getMinerals() {
    return getMineralPatchesCache.get();
  }

  public List<Unit> getGeysers() {
    return getVespeneGeysersCache.get();
  }

  public List<Unit> getNeutralUnits() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getStaticMinerals() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getStaticGeysers() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getStaticNeutralUnits() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Collection<Bullet> getBullets() {
    return bullets.values();
  }

  public List<Position> getNukeDots() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Force getForce(int forceID) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Player getPlayer(final int playerId) {
    return players.get(playerId);
  }

  public Unit getUnit(final int unitId) {
    return units.get(unitId);
  }

  public Unit indexToUnit(int unitIndex) {
    return getUnit(unitIndex);
  }

  public Region getRegion(int regionID) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public GameType getGameType() {
    return interactionHandler.getGameType();
  }

  public int getLatency() {
    return interactionHandler.getLatency();
  }

  public int getFrameCount() {
    return interactionHandler.getFrameCount();
  }

  public int getReplayFrameCount() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getFPS() {
    return interactionHandler.getFPS();
  }

  public double getAverageFPS() {
    return interactionHandler.getAverageFPS();
  }

  public Position getMousePosition() {
    return interactionHandler.getMousePosition();
  }

  public boolean getMouseState(MouseButton button) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean getKeyState(Key key) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Position getScreenPosition() {
    return interactionHandler.getScreenPosition();
  }

  public void setScreenPosition(int x, int y) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void setScreenPosition(Position p) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void pingMinimap(int x, int y) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void pingMinimap(Position p) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isFlagEnabled(int flag) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void enableFlag(int flag) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getUnitsOnTile(int tileX, int tileY) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getUnitsOnTile(TilePosition tile) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getUnitsInRectangle(int left, int top, int right, int bottom) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getUnitsInRectangle(Position topLeft, Position bottomRight) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getUnitsInRadius(int x, int y, int radius) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getUnitsInRadius(Position center, int radius) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Error getLastError() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setLastError() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setLastError(Error e) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int mapWidth() {
    return bwMap.mapWidth();
  }

  public int mapHeight() {
    return bwMap.mapHeight();
  }

  public String mapFileName() {
    return bwMap.mapFileName();
  }

  public String mapPathName() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public String mapName() {
    return bwMap.mapName();
  }

  public String mapHash() {
    return bwMap.mapHash();
  }

  public boolean isWalkable(final int walkX, final int walkY) {
    return bwMap.isWalkable(walkX, walkY);
  }

  public boolean isWalkable(final WalkPosition walkPosition) {
    return bwMap.isWalkable(walkPosition);
  }

  public int getGroundHeight(final int tileX, final int tileY) {
    return bwMap.getGroundHeight(tileX, tileY);
  }

  public int getGroundHeight(final TilePosition tilePosition) {
    return bwMap.getGroundHeight(tilePosition);
  }

  public boolean isBuildable(final int tileX, final int tileY) {
    return bwMap.isBuildable(tileX, tileY);
  }

  public boolean isBuildable(final int tileX, final int tileY, final boolean includeBuildings) {
    return bwMap.isBuildable(tileX, tileY, includeBuildings);
  }

  public boolean isBuildable(final TilePosition position) {
    return bwMap.isBuildable(position);
  }

  public boolean isBuildable(final TilePosition tilePosition, final boolean includeBuildings) {
    return bwMap.isBuildable(tilePosition, includeBuildings);
  }

  public boolean isVisible(int tileX, int tileY) {
    return bwMap.isVisible(tileX, tileY);
  }

  public boolean isVisible(final TilePosition tilePosition) {
    return bwMap.isVisible(tilePosition);
  }

  public boolean isExplored(final int tileX, final int tileY) {
    return bwMap.isExplored(tileX, tileY);
  }

  public boolean isExplored(final TilePosition tilePosition) {
    return bwMap.isExplored(tilePosition);
  }

  public boolean hasCreep(final int tileX, final int tileY) {
    return bwMap.hasCreep(tileX, tileY);
  }

  public boolean hasCreep(final TilePosition tilePosition) {
    return bwMap.hasCreep(tilePosition);
  }

  public boolean hasPowerPrecise(int x, int y) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPowerPrecise(int x, int y, UnitType unitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPowerPrecise(Position position) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPowerPrecise(Position position, UnitType unitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(int tileX, int tileY) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(int tileX, int tileY, UnitType unitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(TilePosition position) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(TilePosition position, UnitType unitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(int tileX, int tileY, int tileWidth, int tileHeight) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(int tileX, int tileY, int tileWidth, int tileHeight, UnitType unitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(TilePosition position, int tileWidth, int tileHeight) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(TilePosition position, int tileWidth, int tileHeight, UnitType unitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildHere(TilePosition position, UnitType type, Unit builder) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildHere(TilePosition position, UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildHere(
      TilePosition position, UnitType type, Unit builder, boolean checkExplored) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMake(UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMake(UnitType type, Unit builder) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canResearch(TechType type, Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canResearch(TechType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canResearch(TechType type, Unit unit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUpgrade(UpgradeType type, Unit unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUpgrade(UpgradeType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUpgrade(UpgradeType type, Unit unit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<TilePosition> getStartLocations() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void printf(String cstr_format) {
    interactionHandler.printf(cstr_format);
  }

  public void sendText(String cstr_format) {
    interactionHandler.sendText(cstr_format);
  }

  public void sendTextEx(boolean toAllies, String cstr_format) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isInGame() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isMultiplayer() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isBattleNet() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isPaused() {
    return interactionHandler.isPaused();
  }

  public boolean isReplay() {
    return interactionHandler.isReplay();
  }

  public void pauseGame() {
    interactionHandler.pauseGame();
  }

  public void resumeGame() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void leaveGame() {
    interactionHandler.leaveGame();
  }

  public void restartGame() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void setLocalSpeed(int speed) {
    interactionHandler.setLocalSpeed(speed);
  }

  public boolean issueCommand(List<Unit> units, UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getSelectedUnits() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Player self() {
    return interactionHandler.self();
  }

  public Player enemy() {
    return interactionHandler.enemy();
  }

  public Player neutral() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Player> allies() {
    return interactionHandler.allies();
  }

  public List<Player> enemies() {
    return interactionHandler.enemies();
  }

  public List<Player> observers() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getLatencyFrames() {
    return interactionHandler.getLatencyFrames();
  }

  public int getLatencyTime() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getRemainingLatencyFrames() {
    return interactionHandler.getRemainingLatencyFrames();
  }

  public int getRemainingLatencyTime() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getRevision() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isDebug() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isLatComEnabled() {
    return interactionHandler.isLatComEnabled();
  }

  public void setLatCom(boolean isEnabled) {
    interactionHandler.enableLatCom(isEnabled);
  }

  public boolean isGUIEnabled() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void setGUI(boolean enabled) {
    interactionHandler.setGUI(enabled);
  }

  public int getInstanceNumber() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getAPM() {
    return interactionHandler.getAPM();
  }

  public int getAPM(boolean includeSelects) {
    return interactionHandler.getAPM(includeSelects);
  }

  public boolean setMap(String cstr_mapFileName) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void setFrameSkip(int frameSkip) {
    interactionHandler.setFrameSkip(frameSkip);
  }

  public boolean hasPath(Position source, Position destination) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setAlliance(Player player, boolean allied) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setAlliance(Player player) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setAlliance(Player player, boolean allied, boolean alliedVictory) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setVision(Player player) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setVision(Player player, boolean enabled) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int elapsedTime() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void setCommandOptimizationLevel(int level) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int countdownTimer() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Region> getAllRegions() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Region getRegionAt(int x, int y) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Region getRegionAt(Position position) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getLastEventTime() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setRevealAll() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean setRevealAll(boolean reveal) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public TilePosition getBuildLocation(UnitType type, TilePosition desiredPosition, int maxRange) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public TilePosition getBuildLocation(UnitType type, TilePosition desiredPosition) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public TilePosition getBuildLocation(
      UnitType type, TilePosition desiredPosition, int maxRange, boolean creep) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getDamageFrom(UnitType fromType, UnitType toType, Player fromPlayer) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getDamageFrom(UnitType fromType, UnitType toType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getDamageFrom(UnitType fromType, UnitType toType, Player fromPlayer, Player toPlayer) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getDamageTo(UnitType toType, UnitType fromType, Player toPlayer) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getDamageTo(UnitType toType, UnitType fromType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getDamageTo(UnitType toType, UnitType fromType, Player toPlayer, Player fromPlayer) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }
}
