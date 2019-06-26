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

import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.Indexed;
import org.openbw.bwapi4j.ap.Named;
import org.openbw.bwapi4j.ap.NativeClass;

@NativeClass(name = "BWAPI::UpgradeType", accessOperator = ".")
public enum UpgradeType implements WithId {
  Terran_Infantry_Armor(0),
  Terran_Vehicle_Plating(1),
  Terran_Ship_Plating(2),
  Zerg_Carapace(3),
  Zerg_Flyer_Carapace(4),
  Protoss_Ground_Armor(5),
  Protoss_Air_Armor(6),
  Terran_Infantry_Weapons(7),
  Terran_Vehicle_Weapons(8),
  Terran_Ship_Weapons(9),
  Zerg_Melee_Attacks(10),
  Zerg_Missile_Attacks(11),
  Zerg_Flyer_Attacks(12),
  Protoss_Ground_Weapons(13),
  Protoss_Air_Weapons(14),
  Protoss_Plasma_Shields(15),
  U_238_Shells(16),
  Ion_Thrusters(17),

  Titan_Reactor(19),
  Ocular_Implants(20),
  Moebius_Reactor(21),
  Apollo_Reactor(22),
  Colossus_Reactor(23),
  Ventral_Sacs(24),
  Antennae(25),
  Pneumatized_Carapace(26),
  Metabolic_Boost(27),
  Adrenal_Glands(28),
  Muscular_Augments(29),
  Grooved_Spines(30),
  Gamete_Meiosis(31),
  Metasynaptic_Node(32),
  Singularity_Charge(33),
  Leg_Enhancements(34),
  Scarab_Damage(35),
  Reaver_Capacity(36),
  Gravitic_Drive(37),
  Sensor_Array(38),
  Gravitic_Boosters(39),
  Khaydarin_Amulet(40),
  Apial_Sensors(41),
  Gravitic_Thrusters(42),
  Carrier_Capacity(43),
  Khaydarin_Core(44),

  Argus_Jewel(47),

  Argus_Talisman(49),

  Caduceus_Reactor(51),
  Chitinous_Plating(52),
  Anabolic_Synthesis(53),
  Charon_Boosters(54),

  Upgrade_60(60),
  None(61),
  Unknown(62),
  MAX(63);

  @Named(name = "ID")
  @BridgeValue(initializeOnly = true)
  int iD;

  @BridgeValue Race race;

  @BridgeValue(accessor = "mineralPrice(i)")
  @Indexed(getAmountBy = "maxRepeats()")
  int[] mineralPrices;

  @BridgeValue(accessor = "mineralPriceFactor()")
  int mineralPriceFactor;

  @BridgeValue(accessor = "gasPrice(i)")
  @Indexed(getAmountBy = "maxRepeats()")
  int[] gasPrices;

  @BridgeValue(accessor = "gasPriceFactor()")
  int gasPriceFactor;

  @BridgeValue(accessor = "upgradeTime(i)")
  @Indexed(getAmountBy = "maxRepeats()")
  int[] upgradeTimes;

  @BridgeValue(accessor = "upgradeTimeFactor()")
  int upgradeTimeFactor;

  @BridgeValue(accessor = "maxRepeats()")
  int maxRepeats;

  @BridgeValue(accessor = "whatUpgrades()")
  UnitType whatUpgrades;

  @BridgeValue(accessor = "whatsRequired(i)")
  @Indexed(getAmountBy = "maxRepeats()")
  UnitType[] whatsRequired;

  @BridgeValue(accessor = "whatUses()")
  List<UnitType> whatUses = new ArrayList<>();

  UpgradeType(int id) {
    this.iD = id;
  }

  public static UpgradeType withId(int id) {
    return IdMapper.upgradeTypeForId[id];
  }

  @Override
  public int getID() {
    return this.iD;
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
