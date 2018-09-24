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

import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.Named;
import org.openbw.bwapi4j.ap.NativeClass;

@NativeClass(name = "BWAPI::WeaponType", accessOperator = ".")
public enum WeaponType implements WithId {
  Gauss_Rifle(0),
  Gauss_Rifle_Jim_Raynor(1),
  C_10_Canister_Rifle(2),
  C_10_Canister_Rifle_Sarah_Kerrigan(3),
  Fragmentation_Grenade(4),
  Fragmentation_Grenade_Jim_Raynor(5),
  Spider_Mines(6),
  Twin_Autocannons(7),
  Hellfire_Missile_Pack(8),
  Twin_Autocannons_Alan_Schezar(9),
  Hellfire_Missile_Pack_Alan_Schezar(10),
  Arclite_Cannon(11),
  Arclite_Cannon_Edmund_Duke(12),
  Fusion_Cutter(13),
  unk_14(14),
  Gemini_Missiles(15),
  Burst_Lasers(16),
  Gemini_Missiles_Tom_Kazansky(17),
  Burst_Lasers_Tom_Kazansky(18),
  ATS_Laser_Battery(19),
  ATA_Laser_Battery(20),
  ATS_Laser_Battery_Hero(21),
  ATA_Laser_Battery_Hero(22),
  ATS_Laser_Battery_Hyperion(23),
  ATA_Laser_Battery_Hyperion(24),
  Flame_Thrower(25),
  Flame_Thrower_Gui_Montag(26),
  Arclite_Shock_Cannon(27),
  Arclite_Shock_Cannon_Edmund_Duke(28),
  Longbolt_Missile(29),
  Yamato_Gun(30),
  Nuclear_Strike(31),
  Lockdown(32),
  EMP_Shockwave(33),
  Irradiate(34),
  Claws(35),
  Claws_Devouring_One(36),
  Claws_Infested_Kerrigan(37),
  Needle_Spines(38),
  Needle_Spines_Hunter_Killer(39),
  Kaiser_Blades(40),
  Kaiser_Blades_Torrasque(41),
  Toxic_Spores(42),
  Spines(43),
  unk_44(44),
  unk_45(45),
  Acid_Spore(46),
  Acid_Spore_Kukulza(47),
  Glave_Wurm(48),
  Glave_Wurm_Kukulza(49),
  unk_50(50),
  unk_51(51),
  Seeker_Spores(52),
  Subterranean_Tentacle(53),
  Suicide_Infested_Terran(54),
  Suicide_Scourge(55),
  Parasite(56),
  Spawn_Broodlings(57),
  Ensnare(58),
  Dark_Swarm(59),
  Plague(60),
  Consume(61),
  Particle_Beam(62),
  unk_63(63),
  Psi_Blades(64),
  Psi_Blades_Fenix(65),
  Phase_Disruptor(66),
  Phase_Disruptor_Fenix(67),
  unk_68(68),
  Psi_Assault(69),
  Psionic_Shockwave(70),
  Psionic_Shockwave_TZ_Archon(71),
  unk_72(72),
  Dual_Photon_Blasters(73),
  Anti_Matter_Missiles(74),
  Dual_Photon_Blasters_Mojo(75),
  Anti_Matter_Missiles_Mojo(76),
  Phase_Disruptor_Cannon(77),
  Phase_Disruptor_Cannon_Danimoth(78),
  Pulse_Cannon(79),
  STS_Photon_Cannon(80),
  STA_Photon_Cannon(81),
  Scarab(82),
  Stasis_Field(83),
  Psionic_Storm(84),
  Warp_Blades_Zeratul(85),
  Warp_Blades_Hero(86),
  unk_87(87),
  unk_88(88),
  unk_89(89),
  unk_90(90),
  unk_91(91),
  Platform_Laser_Battery(92),
  Independant_Laser_Battery(93),
  unk_94(94),
  unk_95(95),
  Twin_Autocannons_Floor_Trap(96),
  Hellfire_Missile_Pack_Wall_Trap(97),
  Flame_Thrower_Wall_Trap(98),
  Hellfire_Missile_Pack_Floor_Trap(99),
  Neutron_Flare(100),
  Disruption_Web(101),
  Restoration(102),
  Halo_Rockets(103),
  Corrosive_Acid(104),
  Mind_Control(105),
  Feedback(106),
  Optical_Flare(107),
  Maelstrom(108),
  Subterranean_Spines(109),
  Warp_Blades(111),
  C_10_Canister_Rifle_Samir_Duran(112),
  C_10_Canister_Rifle_Infested_Duran(113),
  Dual_Photon_Blasters_Artanis(114),
  Anti_Matter_Missiles_Artanis(115),
  C_10_Canister_Rifle_Alexei_Stukov(116),
  None(130),
  Unknown(131),
  MAX(132);

  @Named(name = "ID")
  @BridgeValue(initializeOnly = true)
  int iD;

  @BridgeValue TechType tech;

  @BridgeValue(accessor = "whatUses()")
  UnitType whatUses;

  @BridgeValue(accessor = "damageAmount()")
  int damageAmount;

  @BridgeValue(accessor = "damageBonus()")
  int damageBonus;

  @BridgeValue(accessor = "damageCooldown()")
  int damageCooldown;

  @BridgeValue(accessor = "damageFactor()")
  int damageFactor;

  @BridgeValue(accessor = "upgradeType()")
  UpgradeType upgradeType;

  @BridgeValue(accessor = "damageType()")
  DamageType damageType;

  @BridgeValue(accessor = "explosionType()")
  ExplosionType explosionType;

  @BridgeValue(accessor = "minRange()")
  int minRange;

  @BridgeValue(accessor = "maxRange()")
  int maxRange;

  @BridgeValue(accessor = "innerSplashRadius()")
  int innerSplashRadius;

  @BridgeValue(accessor = "medianSplashRadius()")
  int medianSplashRadius;

  @BridgeValue(accessor = "outerSplashRadius()")
  int outerSplashRadius;

  @BridgeValue(accessor = "targetsAir()")
  boolean targetsAir;

  @BridgeValue(accessor = "targetsGround()")
  boolean targetsGround;

  @BridgeValue(accessor = "targetsMechanical()")
  boolean targetsMechanical;

  @BridgeValue(accessor = "targetsOrganic()")
  boolean targetsOrganic;

  @BridgeValue(accessor = "targetsNonBuilding()")
  boolean targetsNonBuilding;

  @BridgeValue(accessor = "targetsNonRobotic()")
  boolean targetsNonRobotic;

  @BridgeValue(accessor = "targetsTerrain()")
  boolean targetsTerrain;

  @BridgeValue(accessor = "targetsOrgOrMech()")
  boolean targetsOrgOrMech;

  @BridgeValue(accessor = "targetsOwn()")
  boolean targetsOwn;

  WeaponType(int id) {
    this.iD = id;
  }

  public static WeaponType withId(int id) {
    WeaponType result = IdMapper.weaponTypeForId[id];
    if (result == null) {
      throw new IllegalStateException("WeaponType with id " + id + " not found!");
    }
    return result;
  }

  @Override
  public int getId() {
    return this.iD;
  }

  /**
   * Retrieves the technology type that must be researched before this weapon can be used. Returns
   * TechType required by this weapon. Return values TechTypes::None if no tech type is required to
   * use this weapon. See also TechType::getWeapon
   */
  public TechType getTech() {
    return this.tech;
  }

  /**
   * Retrieves the unit type that is intended to use this weapon type. Note There is a rare case
   * where some hero unit types use the same weapon. Returns The UnitType that uses this weapon. See
   * also UnitType::groundWeapon, UnitType::airWeapon
   */
  public UnitType whatUses() {
    return this.whatUses;
  }

  /**
   * Retrieves the base amount of damage that this weapon can deal per attack. Note That this damage
   * amount must go through a DamageType and UnitSizeType filter before it is applied to a unit.
   * Returns Amount of base damage that this weapon deals.
   */
  public int damageAmount() {
    return this.damageAmount;
  }

  /**
   * Determines the bonus amount of damage that this weapon type increases by for every upgrade to
   * this type. See also upgradeType Returns Amount of damage added for every weapon upgrade.
   */
  public int damageBonus() {
    return this.damageBonus;
  }

  /**
   * Retrieves the base amount of cooldown time between each attack, in frames. Returns The amount
   * of base cooldown applied to the unit after an attack. See also
   * UnitInterface::getGroundWeaponCooldown, UnitInterface::getAirWeaponCooldown
   */
  public int damageCooldown() {
    return this.damageCooldown;
  }

  /**
   * Obtains the intended number of missiles/attacks that are used. This is used to multiply with
   * the damage amount to obtain the full amount of damage for an attack. Returns The damage factor
   * multiplied by the amount to obtain the total damage. See also damageAmount
   */
  public int damageFactor() {
    return this.damageFactor;
  }

  /**
   * Retrieves the upgrade type that increases this weapon's damage output. Returns The UpgradeType
   * used to upgrade this weapon's damage. See also damageBonus
   */
  public UpgradeType upgradeType() {
    return this.upgradeType;
  }

  /**
   * Retrieves the damage type that this weapon applies to a unit type. Returns DamageType used for
   * damage calculation. See also DamageType, UnitSizeType
   */
  public DamageType damageType() {
    return this.damageType;
  }

  /**
   * Retrieves the explosion type that indicates how the weapon deals damage. Returns ExplosionType
   * identifying how damage is applied to a target location.
   */
  public ExplosionType explosionType() {
    return this.explosionType;
  }

  /**
   * Retrieves the minimum attack range of the weapon, measured in pixels. This value is 0 for
   * almost all weapon types, except for WeaponTypes::Arclite_Shock_Cannon and
   * WeaponTypes::Arclite_Shock_Cannon_Edmund_Duke. Returns Minimum attack range, in pixels.
   */
  public int minRange() {
    return this.minRange;
  }

  /**
   * Retrieves the maximum attack range of the weapon, measured in pixels. Returns Maximum attack
   * range, in pixels.
   */
  public int maxRange() {
    return this.maxRange;
  }

  /**
   * Retrieves the inner radius used for splash damage calculations, in pixels. Returns Radius of
   * the inner splash area, in pixels.
   */
  public int innerSplashRadius() {
    return this.innerSplashRadius;
  }

  /**
   * Retrieves the middle radius used for splash damage calculations, in pixels. Returns Radius of
   * the middle splash area, in pixels.
   */
  public int medianSplashRadius() {
    return this.medianSplashRadius;
  }

  /**
   * Retrieves the outer radius used for splash damage calculations, in pixels. Returns Radius of
   * the outer splash area, in pixels.
   */
  public int outerSplashRadius() {
    return this.outerSplashRadius;
  }

  /**
   * Checks if this weapon type can target air units. Returns true if this weapon type can target
   * air units, and false otherwise. See also UnitInterface::isFlying, UnitType::isFlyer
   */
  public boolean targetsAir() {
    return this.targetsAir;
  }

  /**
   * Checks if this weapon type can target ground units. Returns true if this weapon type can target
   * ground units, and false otherwise. See also UnitInterface::isFlying, UnitType::isFlyer
   */
  public boolean targetsGround() {
    return this.targetsGround;
  }

  /**
   * Checks if this weapon type can only target mechanical units. Returns true if this weapon type
   * can only target mechanical units, and false otherwise. See also targetsOrgOrMech,
   * UnitType::isMechanical
   */
  public boolean targetsMechanical() {
    return this.targetsMechanical;
  }

  /**
   * Checks if this weapon type can only target organic units. Returns true if this weapon type can
   * only target organic units, and false otherwise. See also targetsOrgOrMech, UnitType::isOrganic
   */
  public boolean targetsOrganic() {
    return this.targetsOrganic;
  }

  /**
   * Checks if this weapon type cannot target structures. Returns true if this weapon type cannot
   * target buildings, and false if it can. See also UnitType::isBuilding
   */
  public boolean targetsNonBuilding() {
    return this.targetsNonBuilding;
  }

  /**
   * Checks if this weapon type cannot target robotic units. Returns true if this weapon type cannot
   * target robotic units, and false if it can. See also UnitType::isRobotic
   */
  public boolean targetsNonRobotic() {
    return this.targetsNonRobotic;
  }

  /**
   * Checks if this weapon type can target the ground. Note This is more for attacks like Psionic
   * Storm which can target a location, not to be confused with attack move. Returns true if this
   * weapon type can target a location, and false otherwise.
   */
  public boolean targetsTerrain() {
    return this.targetsTerrain;
  }

  /**
   * Checks if this weapon type can only target organic or mechanical units. Returns true if this
   * weapon type can only target organic or mechanical units, and false otherwise. See also
   * targetsOrganic, targetsMechanical, UnitType::isOrganic, UnitType::isMechanical
   */
  public boolean targetsOrgOrMech() {
    return this.targetsOrgOrMech;
  }

  /**
   * Checks if this weapon type can only target units owned by the same player. This is used for
   * WeaponTypes::Consume. Returns true if this weapon type can only target your own units, and
   * false otherwise. See also UnitInterface::getPlayer
   */
  public boolean targetsOwn() {
    return this.targetsOwn;
  }

  private static class IdMapper {

    static final WeaponType[] weaponTypeForId = IdMapperHelper.toIdTypeArray(WeaponType.class);
  }
}
