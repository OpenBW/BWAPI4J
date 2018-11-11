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

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.LookedUp;
import org.openbw.bwapi4j.ap.Named;
import org.openbw.bwapi4j.ap.NativeClass;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.type.PlayerType;
import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;
import org.openbw.bwapi4j.unit.Unit;

@LookedUp(method = "getPlayer")
@NativeClass(name = "BWAPI::Player")
public class Player {
  private final BW bw;

  // constant
  @BridgeValue(initializeOnly = true)
  @Named(name = "ID")
  int iD;

  private String name;

  @BridgeValue Race race;

  @BridgeValue(initializeOnly = true)
  @Named(name = "START_LOCATION")
  TilePosition startLocation;

  @BridgeValue(initializeOnly = true)
  @Named(name = "COLOR")
  Color color;

  @BridgeValue(initializeOnly = true)
  @Named(name = "TEXT_COLOR")
  char textColor;

  @BridgeValue PlayerType type;

  // dynamic: update per frame
  //  @BridgeValue
  //  int forceId;
  @BridgeValue boolean neutral;
  @BridgeValue boolean victorious;
  @BridgeValue boolean defeated;

  @BridgeValue(accessor = "leftGame()")
  boolean leftGame;

  @BridgeValue(accessor = "minerals()")
  int minerals;

  @BridgeValue(accessor = "gas()")
  int gas;

  @BridgeValue(accessor = "gatheredMinerals()")
  int gatheredMinerals;

  @BridgeValue(accessor = "gatheredGas()")
  int gatheredGas;

  @BridgeValue(accessor = "repairedMinerals()")
  int repairedMinerals;

  @BridgeValue(accessor = "repairedGas()")
  int repairedGas;

  @BridgeValue(accessor = "refundedMinerals()")
  int refundedMinerals;

  @BridgeValue(accessor = "refundedGas()")
  int refundedGas;

  @BridgeValue(accessor = "spentMinerals()")
  int spentMinerals;

  @BridgeValue(accessor = "spentGas()")
  int spentGas;

  @BridgeValue(accessor = "supplyTotal()")
  int supplyTotal;

  @BridgeValue int unitScore;
  @BridgeValue int killScore;
  @BridgeValue int buildingScore;
  @BridgeValue int razingScore;
  @BridgeValue int customScore;
  @BridgeValue boolean observer;

  @BridgeValue(accessor = "supplyUsed()")
  int supplyUsed;

  private int[] supplyTotalRace;
  private int[] supplyUsedRace;

  @BridgeValue(accessor = "allUnitCount()")
  int allUnitCount;

  @BridgeValue(accessor = "visibleUnitCount()")
  int visibleUnitCount;

  @BridgeValue(accessor = "completedUnitCount()")
  int completedUnitCount;

  @BridgeValue(accessor = "incompleteUnitCount()")
  int incompleteUnitCount;

  @BridgeValue(accessor = "deadUnitCount()")
  int deadUnitCount;

  @BridgeValue(accessor = "killedUnitCount()")
  int killedUnitCount;
  //  @BridgeValue
  //  boolean ally;
  //  @BridgeValue
  //  boolean enemy;

  Map<TechType, boolean[]> researchStatus;
  Map<UpgradeType, int[]> upgradeStatus;

  private final UnitStatCalculator unitStatCalculator;
  private boolean ally;
  private boolean enemy;

  Player(final BW bw, final int id, final String name) {
    this.bw = bw;
    this.iD = id;
    this.name = name;
    this.supplyTotalRace = new int[3];
    this.supplyUsedRace = new int[3];
    this.unitStatCalculator = new UnitStatCalculator(this);
  }

  /** Initializes a player with static information (constant through the course of a game). */
  public void initialize() {
    researchStatus = new EnumMap<>(TechType.class);
    for (TechType tech : TechType.values()) {
      researchStatus.put(tech, new boolean[2]);
    }
    upgradeStatus = new EnumMap<>(UpgradeType.class);
    for (UpgradeType upgrade : UpgradeType.values()) {
      upgradeStatus.put(upgrade, new int[2]);
    }
  }

  /** Updates dynamic player information. To be called once per frame. */
  public void update(int[] extra) {
    int index = 0;
    int upgradeStatusAmount = extra[index++];
    for (int i = 0; i < upgradeStatusAmount; i++) {
      int[] status = this.upgradeStatus.get(UpgradeType.withId(extra[index++]));
      status[0] = extra[index++];
      status[1] = extra[index++];
    }
    int researchStatusAmount = extra[index++];
    for (int i = 0; i < researchStatusAmount; i++) {
      boolean[] status = this.researchStatus.get(TechType.withId(extra[index++]));
      status[0] = extra[index++] == 1;
      status[1] = extra[index++] == 1;
    }

    ally = extra[index++] == 1;
    enemy = extra[index] == 1;
  }

  public boolean isAlly() {
    return ally;
  }

  public boolean isEnemy() {
    return enemy;
  }

  public UnitStatCalculator getUnitStatCalculator() {
    return this.unitStatCalculator;
  }

  /** Returns true if the this player can train/build the given type immediately. */
  public boolean canMake(UnitType type) {
    int supplyRequired = type.supplyRequired();
    if (type.isTwoUnitsInOneEgg()) {
      supplyRequired *= 2;
    }
    return minerals >= type.mineralPrice()
        && gas >= type.gasPrice()
        && (type.supplyRequired() == 0 || supplyUsed + supplyRequired <= supplyTotal)
        && hasResearched(type.requiredTech())
        && getMissingUnits(bw.getUnits(this), type.requiredUnits().keySet()).isEmpty();
  }

  public boolean canMake(Unit builder, UnitType type) {
    if (!canMake(type) || builder.getType() != type.whatBuilds().getUnitType()) {
      return false;
    }
    return type.requiredUnits()
        .keySet()
        .stream()
        .filter(UnitType::isAddon)
        .findAny()
        .map(
            requiredAddon -> {
              if (builder.getAddon() == null || builder.getAddon().getType() != requiredAddon) {
                return false;
              }
              return true;
            })
        .orElse(true);
  }

  /** Returns true, if this player can research the given tech immediately. */
  public boolean canResearch(TechType type) {
    if (hasResearched(type) || isResearching(type)) {
      return false;
    }
    List<UnitType> requiredUnits = new ArrayList<>();
    if (type.whatResearches() != UnitType.None) {
      requiredUnits.add(type.whatResearches());
    }
    if (type.requiredUnit() != UnitType.None) {
      requiredUnits.add(type.requiredUnit());
    }
    return minerals >= type.mineralPrice()
        && gas >= type.gasPrice()
        && getMissingUnits(bw.getUnits(this), requiredUnits).isEmpty();
  }

  public boolean canUpgrade(UpgradeType type) {
    int upgradeLevel = getUpgradeLevel(type);
    if (upgradeLevel >= type.maxRepeats() || isUpgrading(type)) {
      return false;
    }
    List<UnitType> requiredUnits = new ArrayList<>();
    UnitType whatsRequired = type.whatsRequired(upgradeLevel);
    if (whatsRequired != UnitType.None) {
      requiredUnits.add(whatsRequired);
    }
    requiredUnits.add(type.whatUpgrades());
    return minerals >= type.mineralPrice(upgradeLevel)
        && gas >= type.gasPrice(upgradeLevel)
        && getMissingUnits(bw.getUnits(this), requiredUnits).isEmpty();
  }

  public static Collection<UnitType> getMissingUnits(
      Collection<? extends Unit> group, Collection<UnitType> types) {
    HashSet<UnitType> result = new HashSet<>(types);
    group.stream().filter(Unit::isCompleted).map(Unit::getType).forEach(result::remove);
    return result;
  }

  /**
   * Retrieves a unique ID that represents the player. Returns An integer representing the ID of the
   * player.
   */
  public int getID() {
    return iD;
  }

  /**
   * Retrieves the name of the player. Returns A std::string object containing the player's name.
   * Note Don't forget to use std::string::c_str() when passing this parameter to Game::sendText and
   * other variadic functions. Example usage: BWAPI::Player myEnemy = BWAPI::Broodwar->enemy(); if (
   * myEnemy != nullptr ) // Make sure there is an enemy! BWAPI::Broodwar->sendText("Prepare to be
   * crushed, %s!", myEnemy->getName().c_str());
   */
  public String getName() {
    return name;
  }

  public List<Unit> getUnits() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the race of the player. This allows you to change strategies against different races,
   * or generalize some commands for yourself. Return values Races::Unknown If the player chose
   * Races::Random when the game started and they have not been seen. Returns The Race that the
   * player is using. Example usage: if ( BWAPI::Broodwar->enemy() ) { BWAPI::Race enemyRace =
   * BWAPI::Broodwar->enemy()->getRace(); if ( enemyRace == Races::Zerg )
   * BWAPI::Broodwar->sendText("Do you really think you can beat me with a zergling rush?"); }
   */
  public Race getRace() {
    return race;
  }

  /**
   * Retrieves the player's controller type. This allows you to distinguish betweeen computer and
   * human players. Returns The PlayerType that identifies who is controlling a player. Note Other
   * players using BWAPI will be treated as a human player and return PlayerTypes::Player. if (
   * BWAPI::Broodwar->enemy() ) { if ( BWAPI::Broodwar->enemy()->getType() == PlayerTypes::Computer
   * ) BWAPI::Broodwar << "Looks like something I can abuse!" << std::endl; }
   */
  public PlayerType getType() {
    return type;
  }

  public Force getForce() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isAlly(Player player) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isEnemy(Player player) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Checks if this player is the neutral player. Return values true if this player is the neutral
   * player. false if this player is any other player.
   */
  public boolean isNeutral() {
    return neutral;
  }

  /**
   * Retrieve's the player's starting location. Returns A TilePosition containing the position of
   * the start location. Return values TilePositions::None if the player does not have a start
   * location. TilePositions::Unknown if an error occured while trying to retrieve the start
   * location. See also Game::getStartLocations, Game::getLastError
   */
  public TilePosition getStartLocation() {
    return startLocation;
  }

  /**
   * Checks if the player has achieved victory. Returns true if this player has achieved victory,
   * otherwise false
   */
  public boolean isVictorious() {
    return victorious;
  }

  /**
   * Checks if the player has been defeated. Returns true if the player is defeated, otherwise false
   */
  public boolean isDefeated() {
    return defeated;
  }

  /**
   * Checks if the player has left the game. Returns true if the player has left the game, otherwise
   * false
   */
  public boolean leftGame() {
    return leftGame;
  }

  /**
   * Retrieves the current amount of minerals/ore that this player has. Note This function will
   * return 0 if the player is inaccessible. Returns Amount of minerals that the player currently
   * has for spending.
   */
  public int minerals() {
    return minerals;
  }

  /**
   * Retrieves the current amount of vespene gas that this player has. Note This function will
   * return 0 if the player is inaccessible. Returns Amount of gas that the player currently has for
   * spending.
   */
  public int gas() {
    return gas;
  }

  /**
   * Retrieves the cumulative amount of minerals/ore that this player has gathered since the
   * beginning of the game, including the amount that the player starts the game with (if any). Note
   * This function will return 0 if the player is inaccessible. Returns Cumulative amount of
   * minerals that the player has gathered.
   */
  public int gatheredMinerals() {
    return gatheredMinerals;
  }

  /**
   * Retrieves the cumulative amount of vespene gas that this player has gathered since the
   * beginning of the game, including the amount that the player starts the game with (if any). Note
   * This function will return 0 if the player is inaccessible. Returns Cumulative amount of gas
   * that the player has gathered.
   */
  public int gatheredGas() {
    return gatheredGas;
  }

  /**
   * Retrieves the cumulative amount of minerals/ore that this player has spent on repairing units
   * since the beginning of the game. This function only applies to Terran players. Note This
   * function will return 0 if the player is inaccessible. Returns Cumulative amount of minerals
   * that the player has spent repairing.
   */
  public int repairedMinerals() {
    return repairedMinerals;
  }

  /**
   * Retrieves the cumulative amount of vespene gas that this player has spent on repairing units
   * since the beginning of the game. This function only applies to Terran players. Note This
   * function will return 0 if the player is inaccessible. Returns Cumulative amount of gas that the
   * player has spent repairing.
   */
  public int repairedGas() {
    return repairedGas;
  }

  /**
   * Retrieves the cumulative amount of minerals/ore that this player has gained from refunding
   * (cancelling) units and structures. Note This function will return 0 if the player is
   * inaccessible. Returns Cumulative amount of minerals that the player has received from refunds.
   */
  public int refundedMinerals() {
    return refundedMinerals;
  }

  /**
   * Retrieves the cumulative amount of vespene gas that this player has gained from refunding
   * (cancelling) units and structures. Note This function will return 0 if the player is
   * inaccessible. Returns Cumulative amount of gas that the player has received from refunds.
   */
  public int refundedGas() {
    return refundedGas;
  }

  /**
   * Retrieves the cumulative amount of minerals/ore that this player has spent, excluding repairs.
   * Note This function will return 0 if the player is inaccessible. Returns Cumulative amount of
   * minerals that the player has spent.
   */
  public int spentMinerals() {
    return spentMinerals;
  }

  /**
   * Retrieves the cumulative amount of vespene gas that this player has spent, excluding repairs.
   * Note This function will return 0 if the player is inaccessible. Returns Cumulative amount of
   * gas that the player has spent.
   */
  public int spentGas() {
    return spentGas;
  }

  /**
   * Retrieves the total amount of supply the player has available for unit control. Note In
   * Starcraft programming, the managed supply values are double than what they appear in the game.
   * The reason for this is because Zerglings use 0.5 visible supply. In Starcraft, the supply for
   * each race is separate. Having a Pylon and an Overlord will not give you 32 supply. It will
   * instead give you 16 Protoss supply and 16 Zerg supply. Parameters race (optional) The race to
   * query the total supply for. If this is omitted, then the player's current race will be used.
   * Returns The total supply available for this player and the given race. Example usage: if (
   * BWAPI::Broodwar->self()->supplyUsed() + 8 >= BWAPI::Broodwar->self()->supplyTotal() ) { //
   * Construct pylons, supply depots, or overlords } See also supplyUsed
   */
  public int supplyTotal() {
    return supplyTotal;
  }

  public int supplyTotal(final Race race) {
    return supplyTotalRace[race.ordinal()];
  }

  /**
   * Retrieves the current amount of supply that the player is using for unit control. Parameters
   * race (optional) The race to query the used supply for. If this is omitted, then the player's
   * current race will be used. Returns The supply that is in use for this player and the given
   * race. See also supplyTotal
   */
  public int supplyUsed() {
    return supplyUsed;
  }

  public int supplyUsed(final Race race) {
    return supplyUsedRace[race.ordinal()];
  }

  /**
   * Retrieves the total number of units that the player has. If the information about the player is
   * limited, then this function will only return the number of visible units. Parameters unit
   * (optional) The unit type to query. UnitType macros are accepted. If this parameter is omitted,
   * then it will use UnitTypes::AllUnits by default. Returns The total number of units of the given
   * type that the player owns. See also visibleUnitCount, completedUnitCount, incompleteUnitCount
   */
  public int allUnitCount() {
    return allUnitCount;
  }

  public int allUnitCount(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the total number of strictly visible units that the player has, even if information
   * on the player is unrestricted. Parameters unit (optional) The unit type to query. UnitType
   * macros are accepted. If this parameter is omitted, then it will use UnitTypes::AllUnits by
   * default. Returns The total number of units of the given type that the player owns, and is
   * visible to the BWAPI player. See also allUnitCount, completedUnitCount, incompleteUnitCount
   */
  public int visibleUnitCount() {
    return visibleUnitCount;
  }

  public int visibleUnitCount(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the number of completed units that the player has. If the information about the
   * player is limited, then this function will only return the number of visible completed units.
   * Parameters unit (optional) The unit type to query. UnitType macros are accepted. If this
   * parameter is omitted, then it will use UnitTypes::AllUnits by default. Returns The number of
   * completed units of the given type that the player owns. Example usage: bool
   * obtainNextUpgrade(BWAPI::UpgradeType upgType) { BWAPI::Player self = BWAPI::Broodwar->self();
   * int maxLvl = self->getMaxUpgradeLevel(upgType); int currentLvl =
   * self->getUpgradeLevel(upgType); if ( !self->isUpgrading(upgType) && currentLvl < maxLvl &&
   * self->completedUnitCount(upgType.whatsRequired(currentLvl+1)) > 0 &&
   * self->completedUnitCount(upgType.whatUpgrades()) > 0 ) return
   * self->getUnits().upgrade(upgType); return false; } See also allUnitCount, visibleUnitCount,
   * incompleteUnitCount
   */
  public int completedUnitCount() {
    return completedUnitCount;
  }

  public int completedUnitCount(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the number of incomplete units that the player has. If the information about the
   * player is limited, then this function will only return the number of visible incomplete units.
   * Note This function is a macro for allUnitCount() - completedUnitCount(). Parameters unit
   * (optional) The unit type to query. UnitType macros are accepted. If this parameter is omitted,
   * then it will use UnitTypes::AllUnits by default. Returns The number of incomplete units of the
   * given type that the player owns. See also allUnitCount, visibleUnitCount, completedUnitCount
   */
  public int incompleteUnitCount() {
    return incompleteUnitCount;
  }

  public int incompleteUnitCount(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the number units that have died for this player. Parameters unit (optional) The unit
   * type to query. UnitType macros are accepted. If this parameter is omitted, then it will use
   * UnitTypes::AllUnits by default. Returns The total number of units that have died throughout the
   * game.
   */
  public int deadUnitCount() {
    return deadUnitCount;
  }

  public int deadUnitCount(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the number units that the player has killed. Parameters unit (optional) The unit type
   * to query. UnitType macros are accepted. If this parameter is omitted, then it will use
   * UnitTypes::AllUnits by default. Returns The total number of units that the player has killed
   * throughout the game.
   */
  public int killedUnitCount() {
    return killedUnitCount;
  }

  public int killedUnitCount(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the current upgrade level that the player has attained for a given upgrade type.
   * Parameters upgrade The UpgradeType to query. Returns The number of levels that the upgrade has
   * been upgraded for this player. Example usage: bool obtainNextUpgrade(BWAPI::UpgradeType
   * upgType) { BWAPI::Player self = BWAPI::Broodwar->self(); int maxLvl =
   * self->getMaxUpgradeLevel(upgType); int currentLvl = self->getUpgradeLevel(upgType); if (
   * !self->isUpgrading(upgType) && currentLvl < maxLvl &&
   * self->completedUnitCount(upgType.whatsRequired(currentLvl+1)) > 0 &&
   * self->completedUnitCount(upgType.whatUpgrades()) > 0 ) return
   * self->getUnits().upgrade(upgType); return false; } See also UnitInterface::upgrade,
   * getMaxUpgradeLevel
   */
  public int getUpgradeLevel(final UpgradeType upgrade) {
    return upgradeStatus.get(upgrade)[0];
  }

  /**
   * Checks if the player has already researched a given technology. Parameters tech The TechType to
   * query. Returns true if the player has obtained the given tech, or false if they have not See
   * also isResearching, UnitInterface::research, isResearchAvailable
   */
  public boolean hasResearched(final TechType tech) {
    return TechType.None.equals(tech) || researchStatus.get(tech)[0];
  }

  /**
   * Checks if the player is researching a given technology type. Parameters tech The TechType to
   * query. Returns true if the player is currently researching the tech, or false otherwise See
   * also UnitInterface::research, hasResearched
   */
  public boolean isResearching(final TechType tech) {
    return researchStatus.get(tech)[1];
  }

  /**
   * Checks if the player is upgrading a given upgrade type. Parameters upgrade The upgrade type to
   * query. Returns true if the player is currently upgrading the given upgrade, false otherwise
   * Example usage: bool obtainNextUpgrade(BWAPI::UpgradeType upgType) { BWAPI::Player self =
   * BWAPI::Broodwar->self(); int maxLvl = self->getMaxUpgradeLevel(upgType); int currentLvl =
   * self->getUpgradeLevel(upgType); if ( !self->isUpgrading(upgType) && currentLvl < maxLvl &&
   * self->completedUnitCount(upgType.whatsRequired(currentLvl+1)) > 0 &&
   * self->completedUnitCount(upgType.whatUpgrades()) > 0 ) return
   * self->getUnits().upgrade(upgType); return false; } See also UnitInterface::upgrade
   */
  public boolean isUpgrading(final UpgradeType upgrade) {
    return upgradeStatus.get(upgrade)[1] == 1;
  }

  /**
   * Retrieves the color value of the current player. Returns Color object that represents the color
   * of the current player.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Retrieves the control code character that changes the color of text messages to represent this
   * player. Returns character code to use for text in Broodwar.
   */
  public char getTextColor() {
    return textColor;
  }

  public int maxEnergy(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public double topSpeed(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int weaponMaxRange(WeaponType weapon) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int sightRange(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int weaponDamageCooldown(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int armor(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int damage(WeaponType wpn) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  /**
   * Retrieves the total unit score, as seen in the end-game score screen. Returns The player's unit
   * score.
   */
  public int getUnitScore() {
    return unitScore;
  }

  /**
   * Retrieves the total kill score, as seen in the end-game score screen. Returns The player's kill
   * score.
   */
  public int getKillScore() {
    return killScore;
  }

  /**
   * Retrieves the total building score, as seen in the end-game score screen. Returns The player's
   * building score.
   */
  public int getBuildingScore() {
    return buildingScore;
  }

  /**
   * Retrieves the total razing score, as seen in the end-game score screen. Returns The player's
   * razing score.
   */
  public int getRazingScore() {
    return razingScore;
  }

  /**
   * Retrieves the player's custom score. This score is used in Use Map Settings game types. Returns
   * The player's custom score.
   */
  public int getCustomScore() {
    return customScore;
  }

  /**
   * Checks if the player is an observer player, typically in a Use Map Settings observer game. An
   * observer player does not participate in the game. Returns true if the player is observing, or
   * false if the player is capable of playing in the game.
   */
  public boolean isObserver() {
    return observer;
  }

  public int getMaxUpgradeLevel(UpgradeType upgrade) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isResearchAvailable(TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isUnitAvailable(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasUnitTypeRequirement(UnitType unit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasUnitTypeRequirement(UnitType unit, int amount) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    } else if (object instanceof Player) {
      final Player that = (Player) object;
      return this.getID() == that.getID();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return getID();
  }
}
