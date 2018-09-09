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

package org.openbw.bwapi4j.type;

import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.NativeClass;

@NativeClass(name = "BWAPI::UpgradeType", accessOperator = ".")
public enum UpgradeType implements WithId {
  Terran_Infantry_Armor,
  Terran_Vehicle_Plating,
  Terran_Ship_Plating,
  Zerg_Carapace,
  Zerg_Flyer_Carapace,
  Protoss_Ground_Armor,
  Protoss_Air_Armor,
  Terran_Infantry_Weapons,
  Terran_Vehicle_Weapons,
  Terran_Ship_Weapons,
  Zerg_Melee_Attacks,
  Zerg_Missile_Attacks,
  Zerg_Flyer_Attacks,
  Protoss_Ground_Weapons,
  Protoss_Air_Weapons,
  Protoss_Plasma_Shields,
  U_238_Shells, // marine range +1
  Ion_Thrusters, // vulture speed +50%
  Titan_Reactor, // science vessel start energy +12.5, max energy +50
  Ocular_Implants, // ghost sight/detection range +2
  Moebius_Reactor, // ghost start energy +12.5, max energy +50
  Apollo_Reactor, // wraith start energy +12.5, max energy +50
  Colossus_Reactor, // battlecruiser start energy +12.5, max energy +50
  Ventral_Sacs, // overlord transport ability
  Antennae, // overlord sight/detection range +2
  Pneumatized_Carapace, // overlord speed +400%
  Metabolic_Boost, // zergling speed +50%
  Adrenal_Glands, // zergling attack rate increase (TODO HOW MUCH??)
  Muscular_Augments, // hydralisk speed +50%
  Grooved_Spines, // hydralisk range +1
  Gamete_Meiosis, // queen start energy +12.5, max energy +50
  Metasynaptic_Node, // defiler start energy +12.5, max energy +50
  Singularity_Charge, // dragoon range +2
  Leg_Enhancements, // zealot speed +50%
  Scarab_Damage, // reaver scarab damage +25
  Reaver_Capacity, // reaver capacity +5
  Gravitic_Drive, // shuttle speed +50%
  Sensor_Array, // observer sight/detection range +2
  Gravitic_Boosters, // observer speed +50%
  Khaydarin_Amulet, // high templar start energy +12.5, max energy +50
  Apial_Sensors, // scout sight/detection range +2
  Gravitic_Thrusters, // scout speed +50%
  Carrier_Capacity, // carrier capacity +4
  Khaydarin_Core, // arbiter start energy +12.5, max energy +50
  Argus_Jewel, // corsair start energy +12.5, max energy +50
  Argus_Talisman, // dark archon max energy +50
  Caduceus_Reactor, // medic start energy +12.5, max energy +50
  Chitinous_Plating, // ultralisk armor +2
  Anabolic_Synthesis, // ultralisk speed +50%
  Charon_Boosters, // goliath air range +3
  Upgrade_60,
  None,
  Unknown;

  int id = 60;
  @BridgeValue
  Race race;
  int[] mineralPrices;
  @BridgeValue(accessor = "mineralPriceFactor()")
  int mineralPriceFactor;
  int[] gasPrices;
  @BridgeValue(accessor = "gasPriceFactor()")
  int gasPriceFactor;
  int[] upgradeTimes;
  @BridgeValue(accessor = "upgradeTimeFactor()")
  int upgradeTimeFactor;
  @BridgeValue(accessor = "maxRepeats()")
  int maxRepeats;
  @BridgeValue(accessor = "whatUpgrades()")
  UnitType whatUpgrades;
  UnitType[] whatsRequired;
  @BridgeValue(accessor = "whatUses()")
  List<UnitType> whatUses = new ArrayList<>();

  public static UpgradeType withId(int id) {
    return IdMapper.upgradeTypeForId[id];
  }

  @Override
  public int getId() {
    return this.id;
  }

  /**
   * Retrieves the race the upgrade is for. For example,
   * UpgradeTypes::Terran_Infantry_Armor.getRace() will return Races::Terran. Returns Race that this
   * upgrade belongs to.
   */
  public Race getRace() {
    return this.race;
  }

  /**
   * Returns the mineral price for the upgrade. Parameters level (optional) The next upgrade level.
   * Note Upgrades start at level 0. Returns The mineral cost of the upgrade for the given level.
   */
  public int mineralPrice(int level) {
    return this.mineralPrices[level];
  }

  /**
   * The amount that the mineral price increases for each additional upgrade. Returns The mineral
   * cost added to the upgrade after each level.
   */
  public int mineralPriceFactor() {
    return this.mineralPriceFactor;
  }

  /**
   * Returns the vespene gas price for the first upgrade. Parameters level (optional) The next
   * upgrade level. Note Upgrades start at level 0. Returns The gas cost of the upgrade for the
   * given level.
   */
  public int gasPrice(int level) {
    return this.gasPrices[level];
  }

  /**
   * Returns the amount that the vespene gas price increases for each additional upgrade. Returns
   * The gas cost added to the upgrade after each level.
   */
  public int gasPriceFactor() {
    return this.gasPriceFactor;
  }

  /**
   * Returns the number of frames needed to research the first upgrade. Parameters level (optional)
   * The next upgrade level. Note Upgrades start at level 0. Returns The time cost of the upgrade
   * for the given level.
   */
  public int upgradeTime(int level) {
    return this.upgradeTimes[level];
  }

  /**
   * Returns the number of frames that the upgrade time increases for each additional upgrade.
   * Returns The time cost added to the upgrade after each level.
   */
  public int upgradeTimeFactor() {
    return this.upgradeTimeFactor;
  }

  /**
   * Returns the maximum number of times the upgrade can be researched. Returns Maximum number of
   * times this upgrade can be upgraded.
   */
  public int maxRepeats() {
    return this.maxRepeats;
  }

  /**
   * Returns the type of unit that researches the upgrade. Returns The UnitType that is used to
   * upgrade this type.
   */
  public UnitType whatUpgrades() {
    return this.whatUpgrades;
  }

  /**
   * Returns the type of unit that is required for the upgrade. The player must have at least one of
   * these units completed in order to start upgrading this upgrade. Parameters level (optional) The
   * next upgrade level. Note Upgrades start at level 0. Returns UnitType required to obtain this
   * upgrade.
   */
  public UnitType whatsRequired(int level) {
    return this.whatsRequired[level];
  }

  public List<UnitType> whatUses() {
    return whatUses;
  }

  /** Called by native API */
  private void addUsingUnit(int typeId) {
    whatUses.add(UnitType.values()[typeId]);
  }

  private static class IdMapper {

    static final UpgradeType[] upgradeTypeForId = IdMapperHelper.toIdTypeArray(UpgradeType.class);
  }
}
