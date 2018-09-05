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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.UnitFactory;
import org.openbw.bwapi4j.unit.UnitImpl;
import org.openbw.bwapi4j.unit.UnitImplBridge;
import org.openbw.bwapi4j.unit.VespeneGeyser;
import org.openbw.bwapi4j.unit.WeaponBridge;
import org.openbw.bwapi4j.unit.Worker;
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
  private UnitImplBridge unitDataBridge;
  private PlayerBridge playerBridge;
  private BulletBridge bulletBridge;

  private Map<Integer, Player> players;
  private Map<Integer, UnitImpl> units;
  private Map<Integer, Bullet> bullets;
  private Map<Integer, WeaponType> weaponTypes;
  private Map<Integer, UpgradeType> upgradeTypes;
  private Map<Integer, TechType> techTypes;
  private UnitFactory unitFactory;
  private Charset charset;

  private Cache<Map<Player, List<PlayerUnit>>> getUnitsFromPlayerCache;
  private Cache<List<MineralPatch>> getMineralPatchesCache;
  private Cache<List<VespeneGeyser>> getVespeneGeysersCache;

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
    this.unitDataBridge = new UnitImplBridge(this, new WeaponBridge(this));
    this.playerBridge = new PlayerBridge(this);
    this.bulletBridge = new BulletBridge(this);
    setUnitFactory(new UnitFactory());

    try {
      this.charset = Charset.forName("Cp949"); /* Korean char set */
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

  public void startGame() {
    startGame(this);
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

  private native void startGame(BW bw);

  private native int[] getAllUnitsData();

  private native int[] getAllBulletsData();

  private native int[] getAllPlayersData();

  private native int[] getGameData();

  private native int getClientVersion();

  private native String getPlayerName(int playerId);

  private native int[] getPlayerExtra(int playerId);

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

    int index = 0;
    while (index < bulletData.length) {
      int bulletId = bulletData[index + BulletBridge.ID];
      Bullet bullet = this.bullets.get(bulletId);
      if (bullet == null) {
        bullet = new Bullet(this);
        this.bullets.put(bulletId, bullet);
        bulletBridge.initialize(bullet, bulletData, index);
      }
      index = bulletBridge.update(bullet, bulletData, index);
    }
  }

  private boolean typeChanged(UnitType oldType, UnitType newType) {
    return !oldType.equals(newType)
        && !oldType.equals(UnitType.Terran_Siege_Tank_Siege_Mode)
        && !newType.equals(UnitType.Terran_Siege_Tank_Siege_Mode);
  }

  private void updateAllUnits(int frame) {
    for (UnitImpl unit : this.units.values()) {
      unitDataBridge.reset(unit);
    }
    int[] unitData = this.getAllUnitsData();

    int index = 0;
    while (index < unitData.length) {
      int unitId = unitData[index + UnitImplBridge.ID];
      int typeId = unitData[index + UnitImplBridge.TYPE];
      UnitImpl unit = this.units.get(unitId);
      if (unit == null || typeChanged(unit.getType(), UnitType.values()[typeId])) {
        if (unit != null) {
          logger.debug(
              "unit {} changed type from {} to {}.",
              unit.getId(),
              unit.getType(),
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
          unitDataBridge.initialize(unit, unitData, index);
          index = unitDataBridge.update(unit, unitData, index);
          logger.trace("initial pos: {}", unit.getInitialTilePosition());
          logger.trace("current pos: {}", unit.getTilePosition());

          logger.trace(" done.");
        }
      } else {
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
        player = new Player(playerId, this.getPlayerName(playerId), this);
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

  public Player getPlayer(int playerId) {
    return this.players.get(playerId);
  }

  public WeaponType getWeapon(int typeId) {
    return weaponTypes.get(typeId);
  }

  public UpgradeType getUpgradeType(int typeId) {
    return upgradeTypes.get(typeId);
  }

  public TechType getTechType(int typeId) {
    return techTypes.get(typeId);
  }

  public Color getColor(int rgb) {
    for (Color color : Color.values()) {
      if (color.matches(rgb)) {
        return color;
      }
    }
    return null;
  }


  public Collection<Player> getAllPlayers() {
    return this.players.values();
  }

  public Unit getUnit(int unitId) {
    return unitId > 0 ? this.units.get(unitId) : null;
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

  public Collection<UnitImpl> getAllUnits() {
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

  private void onStart() {
    try {
      logger.trace(" --- onStart called.");
      this.players.clear();
      this.units.clear();
      this.bullets.clear();

      logger.trace(" -- precaching types");
      // TODO use Arrays/Lists instead!
      weaponTypes = Arrays.stream(WeaponType.values())
          .collect(Collectors.toMap(WeaponType::getId, Function.identity()));
      upgradeTypes = Arrays.stream(UpgradeType.values())
          .collect(Collectors.toMap(UpgradeType::getId, Function.identity()));
      techTypes = Arrays.stream(TechType.values())
          .collect(Collectors.toMap(TechType::getId, Function.identity()));

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
}
