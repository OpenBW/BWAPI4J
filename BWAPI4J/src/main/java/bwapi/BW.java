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

package bwapi;

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
import org.openbw.bwapi4j.util.Cache;
import org.openbw.bwapi4j.util.DependencyManager;
import org.openbw.bwapi4j.util.buffer.BwapiDataBuffer;
import org.openbw.bwapi4j.util.buffer.DataBuffer;
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
  private final UnitCommandSimulation unitCommandSimulation = new UnitCommandSimulation();
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

  public BWMap getBWMap() {
    return bwMap;
  }

  public MapDrawer getMapDrawer() {
    return mapDrawer;
  }

  public DamageEvaluator getDamageEvaluator() {
    return damageEvaluator;
  }

  public InteractionHandler getInteractionHandler() {
    return interactionHandler;
  }

  public UnitCommandSimulation getUnitCommandSimulation() {
    return unitCommandSimulation;
  }

  private native void startGame_native(BW bw);

  public void startGame() {
    startGame_native(this);
  }

  public native void exit();

  private native void createUnit_native(int ownerId, int unitTypeId, int posX, int posY);

  /**
   * Creates a unit of the specified type for the speceified player at the specified position. This
   * method only works with OpenBW and will do nothing if used with original BW.
   */
  public void createUnit(final Player owner, final UnitType type, final Position position) {
    createUnit_native(owner.getID(), type.getID(), position.getX(), position.getY());
  }

  private native void killUnit_native(int unitID);

  /**
   * Kills the specified unit. This method only works with OpenBW and will do nothing if used with
   * original BW.
   */
  public void killUnit(final Unit unit) {
    killUnit_native(unit.getID());
  }

  public native int getClientVersion();

  private native int[] getGameData_native();

  private void updateGame() {
    final int[] data = getGameData_native();

    getInteractionHandler().update(data);
  }

  private native int[] getAllBulletsData_native();

  private void updateAllBullets() {
    final int[] bulletData = getAllBulletsData_native();

    int index = 0;
    while (index < bulletData.length) {
      final int bulletId = bulletData[index + BulletBridge.ID];

      Bullet bullet = bullets.get(bulletId);
      if (bullet == null) {
        bullet = new Bullet();
        bullets.put(bulletId, bullet);
        bulletBridge.initialize(bullet, bulletData, index);
      }

      index = bulletBridge.update(bullet, bulletData, index);
    }
  }

  private static boolean typeChanged(final UnitType oldType, final UnitType newType) {
    return oldType != newType
        && oldType != UnitType.Terran_Siege_Tank_Siege_Mode
        && newType != UnitType.Terran_Siege_Tank_Siege_Mode;
  }

  private native int[] getAllUnitsData_native();

  private void updateAllUnits(final int frame) {
    for (final Unit unit : units.values()) {
      unitDataBridge.reset(unit);
    }

    final int[] unitData = getAllUnitsData_native();

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

        unit =
            unitFactory.createUnit(
                unitId,
                UnitType.values()[unitTypeId],
                frame); // TODO: Use "UnitType.withId(unitTypeId)" instead?
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
        // TODO: Use "UnitType.withId(unitTypeId)" instead?
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

  private native int[] getAllPlayersData_native();

  private native String getPlayerName_native(int playerId);

  private native int[] getPlayerAdditionalData_native(int playerId);

  // TODO: Determine why this function seems to be called twice when using OpenBW. E.g.
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 1 ...
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 0 ...
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 1 ...
  // [DEBUG] [Thread-1] openbw.bwapi4j.BW:updateAllPlayers:617 - creating player for id 0 ...
  private void updateAllPlayers() {
    final int[] playerData = getAllPlayersData_native();

    int index = 0;
    while (index < playerData.length) {
      final int playerId = playerData[index + PlayerBridge.ID];

      Player player = players.get(playerId);
      if (player == null) {
        logger.debug("creating player for id {} ...", playerId);
        player = new Player(this, playerId, getPlayerName_native(playerId));

        logger.trace("player name: {}", player.getName());
        players.put(playerId, player);

        logger.trace("initializing...");
        playerBridge.initialize(player, playerData, index);
        player.initialize();

        logger.trace(" done.");
      }

      index = playerBridge.update(player, playerData, index);
      player.update(getPlayerAdditionalData_native(playerId));
    }
  }

  public Bullet getBullet(final int bulletId) {
    return bullets.get(bulletId);
  }

  public List<Unit> getUnits(final Player player) {
    return getUnitsFromPlayerCache.get().getOrDefault(player, Collections.emptyList());
  }

  public List<Unit> getVespeneGeysers() {
    return getVespeneGeysersCache.get();
  }

  private void resetCache() {
    getUnitsFromPlayerCache =
        new Cache<>(
            () -> {
              final Map<Player, List<Unit>> playerListMap = new HashMap<>();

              for (final Unit unit : units.values()) {
                final Player player = unit.getPlayer();

                if (player != null) {
                  final List<Unit> units =
                      playerListMap.computeIfAbsent(player, list -> new ArrayList<>());
                  units.add(unit);
                }
              }

              return playerListMap;
            },
            interactionHandler);

    getMineralPatchesCache =
        new Cache<>(
            () ->
                units
                    .values()
                    .stream()
                    .filter(u -> u.getType().isMineralField())
                    .collect(Collectors.toList()),
            interactionHandler);

    getVespeneGeysersCache =
        new Cache<>(
            () ->
                units
                    .values()
                    .stream()
                    .filter(u -> u.getType() == UnitType.Resource_Vespene_Geyser)
                    .collect(Collectors.toList()),
            interactionHandler);

    getInteractionHandler().resetCache();

    bwMap.resetCache();
  }

  private void initializeTypes() {
    initializeUpgradeTypes();
    initializeWeaponTypes();
    initializeTechTypes();
    initializeUnitTypes();
  }

  private native int[] getUpgradeTypesData_native();

  private void initializeUpgradeTypes() {
    final int data[] = getUpgradeTypesData_native();

    int index = 0;
    while (index < data.length) {
      final int id = data[index + UpgradeTypeBridge.ID];

      index = upgradeTypeBridge.update(UpgradeType.withId(id), data, index);
    }
  }

  private native int[] getWeaponTypesData_native();

  private void initializeWeaponTypes() {
    int data[] = getWeaponTypesData_native();
    int index = 0;
    while (index < data.length) {
      int id = data[index + WeaponTypeBridge.ID];
      index = weaponTypeBridge.update(WeaponType.withId(id), data, index);
    }
  }

  private native int[] getTechTypesData_native();

  private void initializeTechTypes() {
    int data[] = getTechTypesData_native();
    int index = 0;
    while (index < data.length) {
      int id = data[index + TechTypeBridge.ID];
      index = techTypeBridge.update(TechType.withId(id), data, index);
    }
  }

  private native int[] getUnitTypesData_native();

  private void initializeUnitTypes() {
    int data[] = getUnitTypesData_native();
    int index = 0;
    while (index < data.length) {
      int id = data[index + UnitTypeBridge.ID];
      index = unitTypeBridge.update(UnitType.withId(id), data, index);
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // These functions are called from C++
  ////////////////////////////////////////////////////////////////////////////////////

  private void onStart() {
    try {
      logger.trace(" --- onStart called.");
      players.clear();
      units.clear();
      bullets.clear();

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

  private void onEnd(boolean isWinner) {
    catchAllCalling(listener::onEnd, isWinner);
  }

  private void preFrame() {
    logger.trace("updating game state for frame {}...", getInteractionHandler().getFrameCount());
    updateGame();
    logger.trace("updated game.");

    updateAllPlayers();
    logger.trace("updated players.");

    updateAllUnits(getInteractionHandler().getFrameCount());
    logger.trace("updated all units.");

    updateAllBullets();
    logger.trace("updated all bullets.");
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

  private void onSendText(final String text) {
    catchAllCalling(listener::onSendText, text);
  }

  private void onReceiveText(final int playerId, final String text) {
    final Player player = players.get(playerId);
    catchAllCalling(listener::onReceiveText, player, text);
  }

  private void onPlayerLeft(final int playerId) {
    final Player player = players.get(playerId);
    catchAllCalling(listener::onPlayerLeft, player);
  }

  private void onNukeDetect(final int x, final int y) {
    catchAllCalling(listener::onNukeDetect, new Position(x, y));
  }

  private void onUnitDiscover(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitDiscover: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitDiscover, unit);
  }

  private void onUnitEvade(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitEvade: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitEvade, unit);
  }

  private void onUnitShow(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitShow: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitShow, unit);
  }

  private void onUnitHide(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitHide: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitHide, unit);
  }

  private void onUnitCreate(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitCreate: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitCreate, unit);
  }

  private void onUnitDestroy(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitDestroy: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitDestroy, unit);
    units.remove(unitId);
  }

  private void onUnitMorph(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitMorph: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitMorph, unit);
  }

  private void onUnitRenegade(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitRenegade: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitRenegade, unit);
  }

  private void onSaveGame(final String gameName) {
    catchAllCalling(listener::onSaveGame, gameName);
  }

  private void onUnitComplete(final int unitId) {
    final Unit unit = units.get(unitId);
    if (unit == null) {
      logger.error("onUnitComplete: no unit found for ID {}.", unitId);
    }
    catchAllCalling(listener::onUnitComplete, unit);
  }

  ////////////////////////////////////////////////////////////////////////////////////

  /**
   * Calls from native code would just ignore any kind of exception, therefore we catch and log them
   * before returning.
   */
  private static <T> void catchAllCalling(final Consumer<T> consumer, final T param) {
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
  private static <T, U> void catchAllCalling(
      final BiConsumer<T, U> consumer, final T param1, final U param2) {
    try {
      consumer.accept(param1, param2);
    } catch (Throwable t) {
      logger.error(t);
    }
  }

  private native int[] getForceIds_native();

  public List<Force> getForces() {
    final DataBuffer data = new DataBuffer(getForceIds_native());

    final List<Force> forces = new ArrayList<>(data.size());

    while (data.hasNext()) {
      final int id = data.readInt();

      final Force force = new Force(id, this);
      forces.add(force);
    }

    return forces;
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
    return getUnits(neutral());
  }

  private native int[] getStaticMinerals_native();

  public List<Unit> getStaticMinerals() {
    final DataBuffer data = new DataBuffer(getStaticMinerals_native());

    return BwapiDataBuffer.getUnitsByIds(data, this);
  }

  private native int[] getStaticGeysers_native();

  public List<Unit> getStaticGeysers() {
    final DataBuffer data = new DataBuffer(getStaticGeysers_native());

    return BwapiDataBuffer.getUnitsByIds(data, this);
  }

  private native int[] getStaticNeutralUnits_native();

  public List<Unit> getStaticNeutralUnits() {
    final DataBuffer data = new DataBuffer(getStaticNeutralUnits_native());

    return BwapiDataBuffer.getUnitsByIds(data, this);
  }

  public Collection<Bullet> getBullets() {
    return bullets.values();
  }

  public List<Position> getNukeDots() {
    return getInteractionHandler().getNukeDots();
  }

  public Force getForce(int forceID) {
    return new Force(forceID, this);
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
    return getInteractionHandler().getGameType();
  }

  public int getLatency() {
    return getInteractionHandler().getLatency();
  }

  public int getFrameCount() {
    return getInteractionHandler().getFrameCount();
  }

  public int getReplayFrameCount() {
    return getInteractionHandler().getReplayFrameCount();
  }

  public int getFPS() {
    return getInteractionHandler().getFPS();
  }

  public double getAverageFPS() {
    return getInteractionHandler().getAverageFPS();
  }

  public Position getMousePosition() {
    return getInteractionHandler().getMousePosition();
  }

  public boolean getMouseState(final MouseButton button) {
    return getInteractionHandler().getMouseState(button);
  }

  public boolean getKeyState(final Key key) {
    return getInteractionHandler().getKeyState(key);
  }

  public Position getScreenPosition() {
    return getInteractionHandler().getScreenPosition();
  }

  public void setScreenPosition(int x, int y) {
    getInteractionHandler().setScreenPosition(new Position(x, y));
  }

  public void setScreenPosition(Position p) {
    getInteractionHandler().setScreenPosition(p);
  }

  public void pingMinimap(int x, int y) {
    getInteractionHandler().pingMiniMap(x, y);
  }

  public void pingMinimap(Position p) {
    getInteractionHandler().pingMiniMap(p.getX(), p.getY());
  }

  public boolean isFlagEnabled(final int flag) {
    return getInteractionHandler().isFlagEnabled(flag);
  }

  public void enableFlag(final int flag) {
    getInteractionHandler().enableFlag(flag);
  }

  private native int[] getUnitsOnTile_native(int tileX, int tileY);

  public List<Unit> getUnitsOnTile(final int tileX, final int tileY) {
    final DataBuffer data = new DataBuffer(getUnitsOnTile_native(tileX, tileY));

    return BwapiDataBuffer.getUnitsByIds(data, this);
  }

  public List<Unit> getUnitsOnTile(final TilePosition tile) {
    return getUnitsOnTile(tile.getX(), tile.getY());
  }

  private native int[] getUnitsInRectangle_native(int left, int top, int right, int bottom);

  public List<Unit> getUnitsInRectangle(
      final int left, final int top, final int right, final int bottom) {
    final DataBuffer data = new DataBuffer(getUnitsInRectangle_native(left, top, right, bottom));

    return BwapiDataBuffer.getUnitsByIds(data, this);
  }

  public List<Unit> getUnitsInRectangle(final Position topLeft, final Position bottomRight) {
    return getUnitsInRectangle(
        topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());
  }

  private native int[] getUnitsInRadius_native(int x, int y, int radius);

  public List<Unit> getUnitsInRadius(final int x, final int y, final int radius) {
    final DataBuffer data = new DataBuffer(getUnitsInRadius_native(x, y, radius));

    return BwapiDataBuffer.getUnitsByIds(data, this);
  }

  public List<Unit> getUnitsInRadius(final Position center, final int radius) {
    return getUnitsInRadius(center.getX(), center.getY(), radius);
  }

  public Error getLastError() {
    return getInteractionHandler().getLastError();
  }

  private native boolean setLastError_native(int errorId);

  public boolean setLastError(final Error error) {
    return setLastError_native(error.ordinal());
  }

  public boolean setLastError() {
    return setLastError(Error.None);
  }

  public int mapWidth() {
    return getBWMap().mapWidth();
  }

  public int mapHeight() {
    return getBWMap().mapHeight();
  }

  public String mapFileName() {
    return getBWMap().mapFileName();
  }

  public String mapPathName() {
    return getBWMap().mapPathName();
  }

  public String mapName() {
    return getBWMap().mapName();
  }

  public String mapHash() {
    return getBWMap().mapHash();
  }

  public boolean isWalkable(final int walkX, final int walkY) {
    return getBWMap().isWalkable(walkX, walkY);
  }

  public boolean isWalkable(final WalkPosition walkPosition) {
    return getBWMap().isWalkable(walkPosition);
  }

  public int getGroundHeight(final int tileX, final int tileY) {
    return getBWMap().getGroundHeight(tileX, tileY);
  }

  public int getGroundHeight(final TilePosition tilePosition) {
    return getBWMap().getGroundHeight(tilePosition);
  }

  public boolean isBuildable(final int tileX, final int tileY) {
    return getBWMap().isBuildable(tileX, tileY);
  }

  public boolean isBuildable(final int tileX, final int tileY, final boolean includeBuildings) {
    return getBWMap().isBuildable(tileX, tileY, includeBuildings);
  }

  public boolean isBuildable(final TilePosition position) {
    return getBWMap().isBuildable(position);
  }

  public boolean isBuildable(final TilePosition tilePosition, final boolean includeBuildings) {
    return getBWMap().isBuildable(tilePosition, includeBuildings);
  }

  public boolean isVisible(int tileX, int tileY) {
    return getBWMap().isVisible(tileX, tileY);
  }

  public boolean isVisible(final TilePosition tilePosition) {
    return getBWMap().isVisible(tilePosition);
  }

  public boolean isExplored(final int tileX, final int tileY) {
    return getBWMap().isExplored(tileX, tileY);
  }

  public boolean isExplored(final TilePosition tilePosition) {
    return getBWMap().isExplored(tilePosition);
  }

  public boolean hasCreep(final int tileX, final int tileY) {
    return getBWMap().hasCreep(tileX, tileY);
  }

  public boolean hasCreep(final TilePosition tilePosition) {
    return getBWMap().hasCreep(tilePosition);
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
    return getBWMap().hasPower(tileX, tileY);
  }

  public boolean hasPower(int tileX, int tileY, UnitType unitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPower(final TilePosition tilePosition) {
    return getBWMap().hasPower(tilePosition);
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

  public boolean canBuildHere(final TilePosition tilePosition, final UnitType type) {
    return getBWMap().canBuildHere(tilePosition, type);
  }

  public boolean canBuildHere(
      final TilePosition tilePosition, final UnitType type, final Unit builder) {
    return getBWMap().canBuildHere(tilePosition, type, builder, false);
  }

  public boolean canBuildHere(
      final TilePosition tilePosition,
      final UnitType type,
      final Unit builder,
      final boolean checkExplored) {
    return getBWMap().canBuildHere(tilePosition, type, builder, checkExplored);
  }

  private native boolean canMake_native(int unitTypeId, int builderId);

  public boolean canMake(final UnitType unitType, final Unit builder) {
    return canMake_native(unitType.getID(), builder.getID());
  }

  public boolean canMake(final UnitType unitType) {
    return canMake_native(unitType.getID(), -1);
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
    return getBWMap().getStartLocations();
  }

  public void printf(final String text) {
    getInteractionHandler().printf(text);
  }

  public void sendText(final String text) {
    getInteractionHandler().sendText(text);
  }

  public void sendTextEx(final boolean toAllies, final String text) {
    getInteractionHandler().sendTextEx(toAllies, text);
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
    return getInteractionHandler().isPaused();
  }

  public boolean isReplay() {
    return getInteractionHandler().isReplay();
  }

  public void pauseGame() {
    getInteractionHandler().pauseGame();
  }

  public void resumeGame() {
    getInteractionHandler().resumeGame();
  }

  public void leaveGame() {
    getInteractionHandler().leaveGame();
  }

  public void restartGame() {
    getInteractionHandler().restartGame();
  }

  public void setLocalSpeed(int speed) {
    getInteractionHandler().setLocalSpeed(speed);
  }

  public boolean issueCommand(List<Unit> units, UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getSelectedUnits() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Player self() {
    return getInteractionHandler().self();
  }

  public Player enemy() {
    return getInteractionHandler().enemy();
  }

  public Player neutral() {
    return getInteractionHandler().neutral();
  }

  public List<Player> allies() {
    return getInteractionHandler().allies();
  }

  public List<Player> enemies() {
    return getInteractionHandler().enemies();
  }

  public List<Player> observers() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getLatencyFrames() {
    return getInteractionHandler().getLatencyFrames();
  }

  public int getLatencyTime() {
    return getInteractionHandler().getLatencyTime();
  }

  public int getRemainingLatencyFrames() {
    return getInteractionHandler().getRemainingLatencyFrames();
  }

  public int getRemainingLatencyTime() {
    return getInteractionHandler().getRemainingLatencyTime();
  }

  public int getRevision() {
    return getInteractionHandler().getRevision();
  }

  public boolean isDebug() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isLatComEnabled() {
    return getInteractionHandler().isLatComEnabled();
  }

  public void setLatCom(final boolean isEnabled) {
    getInteractionHandler().enableLatCom(isEnabled);
  }

  public boolean isGUIEnabled() {
    return getInteractionHandler().isGUIEnabled();
  }

  public void setGUI(final boolean enabled) {
    getInteractionHandler().setGUI(enabled);
  }

  public int getInstanceNumber() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getAPM() {
    return getInteractionHandler().getAPM();
  }

  public int getAPM(boolean includeSelects) {
    return getInteractionHandler().getAPM(includeSelects);
  }

  public boolean setMap(String cstr_mapFileName) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public void setFrameSkip(int frameSkip) {
    getInteractionHandler().setFrameSkip(frameSkip);
  }

  public boolean hasPath(Position source, Position destination) {
    return getBWMap().hasPath(source, destination);
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

  public native void setCommandOptimizationLevel(int level);

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

  public int getDamageFrom(
      final UnitType fromType, final UnitType toType, final Player fromPlayer) {
    return getDamageEvaluator().getDamageFrom(fromType, toType, fromPlayer);
  }

  public int getDamageFrom(final UnitType fromType, final UnitType toType) {
    return getDamageEvaluator().getDamageFrom(fromType, toType);
  }

  public int getDamageFrom(
      final UnitType fromType,
      final UnitType toType,
      final Player fromPlayer,
      final Player toPlayer) {
    return getDamageEvaluator().getDamageFrom(fromType, toType, fromPlayer, toPlayer);
  }

  public int getDamageTo(final UnitType toType, final UnitType fromType, final Player toPlayer) {
    return getDamageEvaluator().getDamageTo(toType, fromType, toPlayer);
  }

  public int getDamageTo(final UnitType toType, final UnitType fromType) {
    return getDamageEvaluator().getDamageTo(toType, fromType);
  }

  public int getDamageTo(
      final UnitType toType,
      final UnitType fromType,
      final Player toPlayer,
      final Player fromPlayer) {
    return getDamageEvaluator().getDamageTo(toType, fromType, toPlayer, fromPlayer);
  }
}
