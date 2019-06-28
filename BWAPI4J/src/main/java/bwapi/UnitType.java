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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.Named;
import org.openbw.bwapi4j.ap.NativeClass;

@NativeClass(name = "BWAPI::UnitType", accessOperator = ".")
public enum UnitType implements WithId {
  Terran_Marine(0),
  Terran_Ghost(1),
  Terran_Vulture(2),
  Terran_Goliath(3),
  Terran_Goliath_Turret(4),
  Terran_Siege_Tank_Tank_Mode(5),
  Terran_Siege_Tank_Tank_Mode_Turret(6),
  Terran_SCV(7),
  Terran_Wraith(8),
  Terran_Science_Vessel(9),
  Hero_Gui_Montag(10),
  Terran_Dropship(11),
  Terran_Battlecruiser(12),
  Terran_Vulture_Spider_Mine(13),
  Terran_Nuclear_Missile(14),
  Terran_Civilian(15),
  Hero_Sarah_Kerrigan(16),
  Hero_Alan_Schezar(17),
  Hero_Alan_Schezar_Turret(18),
  Hero_Jim_Raynor_Vulture(19),
  Hero_Jim_Raynor_Marine(20),
  Hero_Tom_Kazansky(21),
  Hero_Magellan(22),
  Hero_Edmund_Duke_Tank_Mode(23),
  Hero_Edmund_Duke_Tank_Mode_Turret(24),
  Hero_Edmund_Duke_Siege_Mode(25),
  Hero_Edmund_Duke_Siege_Mode_Turret(26),
  Hero_Arcturus_Mengsk(27),
  Hero_Hyperion(28),
  Hero_Norad_II(29),
  Terran_Siege_Tank_Siege_Mode(30),
  Terran_Siege_Tank_Siege_Mode_Turret(31),
  Terran_Firebat(32),
  Spell_Scanner_Sweep(33),
  Terran_Medic(34),
  Zerg_Larva(35),
  Zerg_Egg(36),
  Zerg_Zergling(37),
  Zerg_Hydralisk(38),
  Zerg_Ultralisk(39),
  Zerg_Broodling(40),
  Zerg_Drone(41),
  Zerg_Overlord(42),
  Zerg_Mutalisk(43),
  Zerg_Guardian(44),
  Zerg_Queen(45),
  Zerg_Defiler(46),
  Zerg_Scourge(47),
  Hero_Torrasque(48),
  Hero_Matriarch(49),
  Zerg_Infested_Terran(50),
  Hero_Infested_Kerrigan(51),
  Hero_Unclean_One(52),
  Hero_Hunter_Killer(53),
  Hero_Devouring_One(54),
  Hero_Kukulza_Mutalisk(55),
  Hero_Kukulza_Guardian(56),
  Hero_Yggdrasill(57),
  Terran_Valkyrie(58),
  Zerg_Cocoon(59),
  Protoss_Corsair(60),
  Protoss_Dark_Templar(61),
  Zerg_Devourer(62),
  Protoss_Dark_Archon(63),
  Protoss_Probe(64),
  Protoss_Zealot(65),
  Protoss_Dragoon(66),
  Protoss_High_Templar(67),
  Protoss_Archon(68),
  Protoss_Shuttle(69),
  Protoss_Scout(70),
  Protoss_Arbiter(71),
  Protoss_Carrier(72),
  Protoss_Interceptor(73),
  Hero_Dark_Templar(74),
  Hero_Zeratul(75),
  Hero_Tassadar_Zeratul_Archon(76),
  Hero_Fenix_Zealot(77),
  Hero_Fenix_Dragoon(78),
  Hero_Tassadar(79),
  Hero_Mojo(80),
  Hero_Warbringer(81),
  Hero_Gantrithor(82),
  Protoss_Reaver(83),
  Protoss_Observer(84),
  Protoss_Scarab(85),
  Hero_Danimoth(86),
  Hero_Aldaris(87),
  Hero_Artanis(88),
  Critter_Rhynadon(89),
  Critter_Bengalaas(90),
  Special_Cargo_Ship(91),
  Special_Mercenary_Gunship(92),
  Critter_Scantid(93),
  Critter_Kakaru(94),
  Critter_Ragnasaur(95),
  Critter_Ursadon(96),
  Zerg_Lurker_Egg(97),
  Hero_Raszagal(98),
  Hero_Samir_Duran(99),
  Hero_Alexei_Stukov(100),
  Special_Map_Revealer(101),
  Hero_Gerard_DuGalle(102),
  Zerg_Lurker(103),
  Hero_Infested_Duran(104),
  Spell_Disruption_Web(105),
  Terran_Command_Center(106),
  Terran_Comsat_Station(107),
  Terran_Nuclear_Silo(108),
  Terran_Supply_Depot(109),
  Terran_Refinery(110),
  Terran_Barracks(111),
  Terran_Academy(112),
  Terran_Factory(113),
  Terran_Starport(114),
  Terran_Control_Tower(115),
  Terran_Science_Facility(116),
  Terran_Covert_Ops(117),
  Terran_Physics_Lab(118),
  Unused_Terran1(119),
  Terran_Machine_Shop(120),
  Unused_Terran2(121),
  Terran_Engineering_Bay(122),
  Terran_Armory(123),
  Terran_Missile_Turret(124),
  Terran_Bunker(125),
  Special_Crashed_Norad_II(126),
  Special_Ion_Cannon(127),
  Powerup_Uraj_Crystal(128),
  Powerup_Khalis_Crystal(129),
  Zerg_Infested_Command_Center(130),
  Zerg_Hatchery(131),
  Zerg_Lair(132),
  Zerg_Hive(133),
  Zerg_Nydus_Canal(134),
  Zerg_Hydralisk_Den(135),
  Zerg_Defiler_Mound(136),
  Zerg_Greater_Spire(137),
  Zerg_Queens_Nest(138),
  Zerg_Evolution_Chamber(139),
  Zerg_Ultralisk_Cavern(140),
  Zerg_Spire(141),
  Zerg_Spawning_Pool(142),
  Zerg_Creep_Colony(143),
  Zerg_Spore_Colony(144),
  Unused_Zerg1(145),
  Zerg_Sunken_Colony(146),
  Special_Overmind_With_Shell(147),
  Special_Overmind(148),
  Zerg_Extractor(149),
  Special_Mature_Chrysalis(150),
  Special_Cerebrate(151),
  Special_Cerebrate_Daggoth(152),
  Unused_Zerg2(153),
  Protoss_Nexus(154),
  Protoss_Robotics_Facility(155),
  Protoss_Pylon(156),
  Protoss_Assimilator(157),
  Unused_Protoss1(158),
  Protoss_Observatory(159),
  Protoss_Gateway(160),
  Unused_Protoss2(161),
  Protoss_Photon_Cannon(162),
  Protoss_Citadel_of_Adun(163),
  Protoss_Cybernetics_Core(164),
  Protoss_Templar_Archives(165),
  Protoss_Forge(166),
  Protoss_Stargate(167),
  Special_Stasis_Cell_Prison(168),
  Protoss_Fleet_Beacon(169),
  Protoss_Arbiter_Tribunal(170),
  Protoss_Robotics_Support_Bay(171),
  Protoss_Shield_Battery(172),
  Special_Khaydarin_Crystal_Form(173),
  Special_Protoss_Temple(174),
  Special_XelNaga_Temple(175),
  Resource_Mineral_Field(176),
  Resource_Mineral_Field_Type_2(177),
  Resource_Mineral_Field_Type_3(178),
  Unused_Cave(179),
  Unused_Cave_In(180),
  Unused_Cantina(181),
  Unused_Mining_Platform(182),
  Unused_Independant_Command_Center(183),
  Special_Independant_Starport(184),
  Unused_Independant_Jump_Gate(185),
  Unused_Ruins(186),
  Unused_Khaydarin_Crystal_Formation(187),
  Resource_Vespene_Geyser(188),
  Special_Warp_Gate(189),
  Special_Psi_Disrupter(190),
  Unused_Zerg_Marker(191),
  Unused_Terran_Marker(192),
  Unused_Protoss_Marker(193),
  Special_Zerg_Beacon(194),
  Special_Terran_Beacon(195),
  Special_Protoss_Beacon(196),
  Special_Zerg_Flag_Beacon(197),
  Special_Terran_Flag_Beacon(198),
  Special_Protoss_Flag_Beacon(199),
  Special_Power_Generator(200),
  Special_Overmind_Cocoon(201),
  Spell_Dark_Swarm(202),
  Special_Floor_Missile_Trap(203),
  Special_Floor_Hatch(204),
  Special_Upper_Level_Door(205),
  Special_Right_Upper_Level_Door(206),
  Special_Pit_Door(207),
  Special_Right_Pit_Door(208),
  Special_Floor_Gun_Trap(209),
  Special_Wall_Missile_Trap(210),
  Special_Wall_Flame_Trap(211),
  Special_Right_Wall_Missile_Trap(212),
  Special_Right_Wall_Flame_Trap(213),
  Special_Start_Location(214),
  Powerup_Flag(215),
  Powerup_Young_Chrysalis(216),
  Powerup_Psi_Emitter(217),
  Powerup_Data_Disk(218),
  Powerup_Khaydarin_Crystal(219),
  Powerup_Mineral_Cluster_Type_1(220),
  Powerup_Mineral_Cluster_Type_2(221),
  Powerup_Protoss_Gas_Orb_Type_1(222),
  Powerup_Protoss_Gas_Orb_Type_2(223),
  Powerup_Zerg_Gas_Sac_Type_1(224),
  Powerup_Zerg_Gas_Sac_Type_2(225),
  Powerup_Terran_Gas_Tank_Type_1(226),
  Powerup_Terran_Gas_Tank_Type_2(227),
  None(228),
  AllUnits(229),
  Men(230),
  Buildings(231),
  Factories(232),
  Unknown(233),
  MAX(234);

  @Named(name = "ID")
  @BridgeValue(initializeOnly = true)
  int iD;

  @BridgeValue Race race;

  @BridgeValue(accessor = "whatBuilds()")
  WhatBuilds whatBuilds;

  @BridgeValue(accessor = "buildsWhat()")
  List<UnitType> buildsWhat;

  @BridgeValue(accessor = "requiredUnits()")
  Map<UnitType, Integer> requiredUnits;

  @BridgeValue(accessor = "requiredTech()")
  TechType requiredTech;

  @BridgeValue(accessor = "cloakingTech()")
  TechType cloakingTech;

  @BridgeValue(accessor = "abilities()")
  List<TechType> abilities;

  @BridgeValue(accessor = "upgrades()")
  List<UpgradeType> upgrades;

  @BridgeValue(accessor = "armorUpgrade()")
  UpgradeType armorUpgrade;

  @BridgeValue(accessor = "maxHitPoints()")
  int maxHitPoints;

  @BridgeValue(accessor = "maxShields()")
  int maxShields;

  @BridgeValue(accessor = "maxEnergy()")
  int maxEnergy;

  @BridgeValue(accessor = "armor()")
  int armor;

  @BridgeValue(accessor = "mineralPrice()")
  int mineralPrice;

  @BridgeValue(accessor = "gasPrice()")
  int gasPrice;

  @BridgeValue(accessor = "buildTime()")
  int buildTime;

  @BridgeValue(accessor = "supplyRequired()")
  int supplyRequired;

  @BridgeValue(accessor = "supplyProvided()")
  int supplyProvided;

  @BridgeValue(accessor = "spaceRequired()")
  int spaceRequired;

  @BridgeValue(accessor = "spaceProvided()")
  int spaceProvided;

  @BridgeValue(accessor = "buildScore()")
  int buildScore;

  @BridgeValue(accessor = "destroyScore()")
  int destroyScore;

  @BridgeValue(accessor = "size()")
  UnitSizeType size;

  @BridgeValue(accessor = "tileWidth()")
  int tileWidth;

  @BridgeValue(accessor = "tileHeight()")
  int tileHeight;

  @BridgeValue(accessor = "dimensionLeft()")
  int dimensionLeft;

  @BridgeValue(accessor = "dimensionUp()")
  int dimensionUp;

  @BridgeValue(accessor = "dimensionRight()")
  int dimensionRight;

  @BridgeValue(accessor = "dimensionDown()")
  int dimensionDown;

  @BridgeValue(accessor = "width()")
  int width;

  @BridgeValue(accessor = "height()")
  int height;

  @BridgeValue(accessor = "seekRange()")
  int seekRange;

  @BridgeValue(accessor = "sightRange()")
  int sightRange;

  @BridgeValue(accessor = "groundWeapon()")
  WeaponType groundWeapon;

  @BridgeValue(accessor = "maxGroundHits()")
  int maxGroundHits;

  @BridgeValue(accessor = "airWeapon()")
  WeaponType airWeapon;

  @BridgeValue(accessor = "maxAirHits()")
  int maxAirHits;

  @BridgeValue(accessor = "topSpeed()")
  double topSpeed;

  @BridgeValue(accessor = "acceleration()")
  int acceleration;

  @BridgeValue(accessor = "haltDistance()")
  int haltDistance;

  @BridgeValue(accessor = "turnRadius()")
  int turnRadius;

  @BridgeValue(accessor = "canProduce()")
  boolean canProduce;

  @BridgeValue(accessor = "canAttack()")
  boolean canAttack;

  @BridgeValue(accessor = "canMove()")
  boolean canMove;

  @BridgeValue boolean flyer;

  @BridgeValue(accessor = "regeneratesHP()")
  boolean regeneratesHP;

  @BridgeValue boolean spellcaster;

  @BridgeValue(accessor = "hasPermanentCloak()")
  boolean hasPermanentCloak;

  @BridgeValue boolean invincible;
  @BridgeValue boolean organic;
  @BridgeValue boolean mechanical;
  @BridgeValue boolean robotic;
  @BridgeValue boolean detector;
  @BridgeValue boolean resourceContainer;
  @BridgeValue boolean resourceDepot;
  @BridgeValue boolean refinery;
  @BridgeValue boolean worker;

  @BridgeValue(accessor = "requiresPsi()")
  boolean requiresPsi;

  @BridgeValue(accessor = "requiresCreep()")
  boolean requiresCreep;

  @BridgeValue boolean twoUnitsInOneEgg;
  @BridgeValue boolean burrowable;
  @BridgeValue boolean cloakable;
  @BridgeValue boolean building;
  @BridgeValue boolean addon;
  @BridgeValue boolean flyingBuilding;
  @BridgeValue boolean neutral;
  @BridgeValue boolean hero;
  @BridgeValue boolean powerup;
  @BridgeValue boolean beacon;
  @BridgeValue boolean flagBeacon;
  @BridgeValue boolean specialBuilding;
  @BridgeValue boolean spell;

  @BridgeValue(accessor = "producesCreep()")
  boolean producesCreep;

  @BridgeValue(accessor = "producesLarva()")
  boolean producesLarva;

  @BridgeValue boolean mineralField;
  @BridgeValue boolean critter;

  @BridgeValue(accessor = "canBuildAddon()")
  boolean canBuildAddon;

  @BridgeValue(accessor = "researchesWhat()")
  List<TechType> researchesWhat;

  @BridgeValue(accessor = "upgradesWhat()")
  List<UpgradeType> upgradesWhat;

  UnitType(final int id) {
    this.iD = id;
    // this.whatBuilds will be created via JNI
    this.requiredUnits = new HashMap<>();
    this.abilities = new ArrayList<>();
    this.upgrades = new ArrayList<>();
    this.researchesWhat = new ArrayList<>();
    this.upgradesWhat = new ArrayList<>();
    buildsWhat = new ArrayList<>();
  }

  public static UnitType withId(int id) {
    if (id < 0) {
      return null;
    }
    UnitType result = IdMapper.unitTypeForId[id];
    if (result == null) {
      throw new IllegalStateException("UnitType with id " + id + " not found!");
    }
    return result;
  }

  @Override
  public int getID() {
    return this.iD;
  }

  /**
   * Retrieves the Race that the unit type belongs to. Returns Race indicating the race that owns
   * this unit type. Return values Race::None indicating that the unit type does not belong to any
   * particular race (a critter for example).
   */
  public Race getRace() {
    return this.race;
  }

  /**
   * Obtains the source unit type that is used to build or train this unit type, as well as the
   * amount of them that are required. Returns std::pair in which the first value is the UnitType
   * that builds this unit type, and the second value is the number of those types that are required
   * (this value is 2 for Archons, and 1 for all other types). Return values pair(UnitTypes::None,0)
   * If this unit type cannot be made by the player.
   */
  public WhatBuilds whatBuilds() {
    // Workaround for unit types where BWAPI doesn't include it in its allUnitTypes
    if (whatBuilds == null) {
      whatBuilds = new WhatBuilds(None, 0);
    }
    return whatBuilds;
  }

  /**
   * Retrieves the immediate technology tree requirements to make this unit type. Returns std::map
   * containing a UnitType to number mapping of UnitTypes required.
   */
  public Map<UnitType, Integer> requiredUnits() {
    return this.requiredUnits;
  }

  /**
   * Identifies the required TechType in order to create certain units. Note The only unit that
   * requires a technology is the Lurker, which needs Lurker Aspect. Returns TechType indicating the
   * technology that must be researched in order to create this unit type. Return values
   * TechTypes::None If creating this unit type does not require a technology to be researched.
   */
  public TechType requiredTech() {
    return this.requiredTech;
  }

  /**
   * Retrieves the cloaking technology associated with certain units. Returns TechType referring to
   * the cloaking technology that this unit type uses as an ability. Return values TechTypes::None
   * If this unit type does not have an active cloak ability.
   */
  public TechType cloakingTech() {
    return this.cloakingTech;
  }

  /**
   * Retrieves the set of abilities that this unit can use, provided it is available to you in the
   * game. Returns Set of TechTypes containing ability information.
   */
  public List<TechType> abilities() {
    return this.abilities;
  }

  /**
   * Retrieves the set of upgrades that this unit can use to enhance its fighting ability. Returns
   * Set of UpgradeTypes containing upgrade types that will impact this unit type.
   */
  public List<UpgradeType> upgrades() {
    return this.upgrades;
  }

  /**
   * Retrieves the upgrade type used to increase the armor of this unit type. For each upgrade, this
   * unit type gains +1 additional armor. Returns UpgradeType indicating the upgrade that increases
   * this unit type's armor amount.
   */
  public UpgradeType armorUpgrade() {
    return this.armorUpgrade;
  }

  /**
   * Retrieves the default maximum amount of hit points that this unit type can have. Note This
   * value may not necessarily match the value seen in the Use Map Settings game type. Returns
   * Integer indicating the maximum amount of hit points for this unit type.
   */
  public int maxHitPoints() {
    return this.maxHitPoints;
  }

  /**
   * Retrieves the default maximum amount of shield points that this unit type can have. Note This
   * value may not necessarily match the value seen in the Use Map Settings game type. Returns
   * Integer indicating the maximum amount of shield points for this unit type. Return values 0 If
   * this unit type does not have shields.
   */
  public int maxShields() {
    return this.maxShields;
  }

  /**
   * Retrieves the maximum amount of energy this unit type can have by default. Returns Integer
   * indicating the maximum amount of energy for this unit type. Return values 0 If this unit does
   * not gain energy for abilities.
   */
  public int maxEnergy() {
    return this.maxEnergy;
  }

  /**
   * Retrieves the default amount of armor that the unit type starts with, excluding upgrades. Note
   * This value may not necessarily match the value seen in the Use Map Settings game type. Returns
   * The amount of armor the unit type has.
   */
  public int armor() {
    return this.armor;
  }

  /**
   * Retrieves the default mineral price of purchasing the unit. Note This value may not necessarily
   * match the value seen in the Use Map Settings game type. Returns Mineral cost of the unit.
   */
  public int mineralPrice() {
    return this.mineralPrice;
  }

  /**
   * Retrieves the default vespene gas price of purchasing the unit. Note This value may not
   * necessarily match the value seen in the Use Map Settings game type. Returns Vespene gas cost of
   * the unit.
   */
  public int gasPrice() {
    return this.gasPrice;
  }

  /**
   * Retrieves the default time, in frames, needed to train, morph, or build the unit. Note This
   * value may not necessarily match the value seen in the Use Map Settings game type. Returns
   * Number of frames needed in order to build the unit. See also
   * UnitInterface::getRemainingBuildTime
   */
  public int buildTime() {
    return this.buildTime;
  }

  /**
   * Retrieves the amount of supply that this unit type will use when created. It will use the
   * supply pool that is appropriate for its Race. Note In Starcraft programming, the managed supply
   * values are double than what they appear in the game. The reason for this is because Zerglings
   * use 0.5 visible supply. Returns Integer containing the supply required to build this unit. See
   * also supplyProvided, PlayerInterface::supplyTotal, PlayerInterface::supplyUsed
   */
  public int supplyRequired() {
    return this.supplyRequired;
  }

  /**
   * Retrieves the amount of supply that this unit type produces for its appropriate Race's supply
   * pool. Note In Starcraft programming, the managed supply values are double than what they appear
   * in the game. The reason for this is because Zerglings use 0.5 visible supply. See also
   * supplyRequired, PlayerInterface::supplyTotal, PlayerInterface::supplyUsed
   */
  public int supplyProvided() {
    return this.supplyProvided;
  }

  /**
   * Retrieves the amount of space required by this unit type to fit inside a Bunker or
   * Transport(Dropship, Shuttle, Overlord ). Returns Amount of space required by this unit type for
   * transport. return values 255 If this unit type can not be transported. See also spaceProvided
   */
  public int spaceRequired() {
    return this.spaceRequired;
  }

  /**
   * Retrieves the amount of space provided by this Bunker or Transport(Dropship, Shuttle, Overlord
   * ) for unit transportation. Returns The number of slots provided by this unit type. See also
   * spaceRequired
   */
  public int spaceProvided() {
    return this.spaceProvided;
  }

  /**
   * Retrieves the amount of score points awarded for constructing this unit type. This value is
   * used for calculating scores in the post-game score screen. Returns Number of points awarded for
   * constructing this unit type. See also destroyScore
   */
  public int buildScore() {
    return this.buildScore;
  }

  /**
   * Retrieves the amount of score points awarded for killing this unit type. This value is used for
   * calculating scores in the post-game score screen. Returns Number of points awarded for killing
   * this unit type. See also buildScore
   */
  public int destroyScore() {
    return this.destroyScore;
  }

  /**
   * Retrieves the UnitSizeType of this unit, which is used in calculations along with weapon damage
   * types to determine the amount of damage that will be dealt to this type. Returns UnitSizeType
   * indicating the conceptual size of the unit type. See also WeaponType::damageType
   */
  public UnitSizeType size() {
    return this.size;
  }

  /**
   * Retrieves the width of this unit type, in tiles. Used for determining the tile size of
   * structures. Returns Width of this unit type, in tiles.
   */
  public int tileWidth() {
    return this.tileWidth;
  }

  /**
   * Retrieves the height of this unit type, in tiles. Used for determining the tile size of
   * structures. Returns Height of this unit type, in tiles.
   */
  public int tileHeight() {
    return this.tileHeight;
  }

  /**
   * Retrieves the tile size of this unit type. Used for determining the tile size of structures.
   * Returns TilePosition containing the width (x) and height (y) of the unit type, in tiles.
   */
  public TilePosition tileSize() {
    return new TilePosition(this.tileWidth, this.tileHeight);
  }

  /**
   * Retrieves the distance from the center of the unit type to its left edge. Returns Distance to
   * this unit type's left edge from its center, in pixels.
   */
  public int dimensionLeft() {
    return this.dimensionLeft;
  }

  /**
   * Retrieves the distance from the center of the unit type to its top edge. Returns Distance to
   * this unit type's top edge from its center, in pixels.
   */
  public int dimensionUp() {
    return this.dimensionUp;
  }

  /**
   * Retrieves the distance from the center of the unit type to its right edge. Returns Distance to
   * this unit type's right edge from its center, in pixels.
   */
  public int dimensionRight() {
    return this.dimensionRight;
  }

  /**
   * Retrieves the distance from the center of the unit type to its bottom edge. Returns Distance to
   * this unit type's bottom edge from its center, in pixels.
   */
  public int dimensionDown() {
    return this.dimensionDown;
  }

  /**
   * A macro for retrieving the width of the unit type, which is calculated using dimensionLeft +
   * dimensionRight + 1. Returns Width of the unit, in pixels.
   */
  public int width() {
    return this.width;
  }

  /**
   * A macro for retrieving the height of the unit type, which is calculated using dimensionUp +
   * dimensionDown + 1. Returns Height of the unit, in pixels.
   */
  public int height() {
    return this.height;
  }

  /**
   * Retrieves the range at which this unit type will start targeting enemy units. Returns Distance
   * at which this unit type begins to seek out enemy units, in pixels.
   */
  public int seekRange() {
    return this.seekRange;
  }

  /**
   * Retrieves the sight range of this unit type. Returns Sight range of this unit type, measured in
   * pixels.
   */
  public int sightRange() {
    return this.sightRange;
  }

  /**
   * Retrieves this unit type's weapon type used when attacking targets on the ground. Returns
   * WeaponType used as this unit type's ground weapon. See also maxGroundHits, airWeapon
   */
  public WeaponType groundWeapon() {
    return this.groundWeapon;
  }

  /**
   * Retrieves the maximum number of hits this unit can deal to a ground target using its ground
   * weapon. This value is multiplied by the ground weapon's damage to calculate the unit type's
   * damage potential. Returns Maximum number of hits given to ground targets. See also
   * groundWeapon, maxAirHits
   */
  public int maxGroundHits() {
    return this.maxGroundHits;
  }

  /**
   * Retrieves this unit type's weapon type used when attacking targets in the air. Returns
   * WeaponType used as this unit type's air weapon. See also maxAirHits, groundWeapon
   */
  public WeaponType airWeapon() {
    return this.airWeapon;
  }

  /**
   * Retrieves the maximum number of hits this unit can deal to a flying target using its air
   * weapon. This value is multiplied by the air weapon's damage to calculate the unit type's damage
   * potential. Returns Maximum number of hits given to air targets. See also airWeapon,
   * maxGroundHits
   */
  public int maxAirHits() {
    return this.maxAirHits;
  }

  /**
   * Retrieves this unit type's top movement speed with no upgrades. Note That some units have
   * inconsistent movement and this value is sometimes an approximation. Returns The approximate top
   * speed, in pixels per frame, as a double. For liftable Terran structures, this function returns
   * their movement speed while lifted.
   */
  public double topSpeed() {
    return this.topSpeed;
  }

  /**
   * Retrieves the unit's acceleration amount. Returns How fast the unit can accelerate to its top
   * speed.
   */
  public int acceleration() {
    return this.acceleration;
  }

  /**
   * Retrieves the unit's halting distance. This determines how fast a unit can stop moving. Returns
   * A halting distance value.
   */
  public int haltDistance() {
    return this.haltDistance;
  }

  /**
   * Retrieves a unit's turning radius. This determines how fast a unit can turn. Returns A turn
   * radius value.
   */
  public int turnRadius() {
    return this.turnRadius;
  }

  /**
   * Determines if a unit can train other units. For example,
   * UnitTypes::Terran_Barracks.canProduce() will return true, while
   * UnitTypes::Terran_Marine.canProduce() will return false. This is also true for two
   * non-structures: Carrier (can produce interceptors) and Reaver (can produce scarabs). Returns
   * true if this unit type can have a production queue, and false otherwise.
   */
  public boolean canProduce() {
    return this.canProduce;
  }

  /**
   * Checks if this unit is capable of attacking. Note This function returns false for units that
   * can only inflict damage via special abilities, such as the High Templar. Returns true if this
   * unit type is capable of damaging other units with a standard attack, and false otherwise.
   */
  public boolean canAttack() {
    return this.canAttack;
  }

  /**
   * Checks if this unit type is capable of movement. Note Buildings will return false, including
   * Terran liftable buildings which are capable of moving when lifted. Returns true if this unit
   * can use a movement command, and false if they cannot move.
   */
  public boolean canMove() {
    return this.canMove;
  }

  /**
   * Checks if this unit type is a flying unit. Flying units ignore ground pathing and collisions.
   * Returns true if this unit type is in the air by default, and false otherwise.
   */
  public boolean isFlyer() {
    return this.flyer;
  }

  /**
   * Checks if this unit type can regenerate hit points. This generally applies to Zerg units.
   * Returns true if this unit type regenerates its hit points, and false otherwise.
   */
  public boolean regeneratesHP() {
    return this.regeneratesHP;
  }

  /**
   * Checks if this unit type has the capacity to store energy and use it for special abilities.
   * Returns true if this unit type generates energy, and false if it does not have an energy pool.
   */
  public boolean isSpellcaster() {
    return this.spellcaster;
  }

  /**
   * Checks if this unit type is permanently cloaked. This means the unit type is always cloaked and
   * requires a detector in order to see it. Returns true if this unit type is permanently cloaked,
   * and false otherwise.
   */
  public boolean hasPermanentCloak() {
    return this.hasPermanentCloak;
  }

  /**
   * Checks if this unit type is invincible by default. Invincible units cannot take damage. Returns
   * true if this unit type is invincible, and false if it is vulnerable to attacks.
   */
  public boolean isInvincible() {
    return this.invincible;
  }

  /**
   * Checks if this unit is an organic unit. The organic property is required for some abilities
   * such as Heal. Returns true if this unit type has the organic property, and false otherwise.
   */
  public boolean isOrganic() {
    return this.organic;
  }

  /**
   * Checks if this unit is mechanical. The mechanical property is required for some actions such as
   * Repair. Returns true if this unit type has the mechanical property, and false otherwise.
   */
  public boolean isMechanical() {
    return this.mechanical;
  }

  /**
   * Checks if this unit is robotic. The robotic property is applied to robotic units such as the
   * Probe which prevents them from taking damage from Irradiate. Returns true if this unit type has
   * the robotic property, and false otherwise.
   */
  public boolean isRobotic() {
    return this.robotic;
  }

  /**
   * Checks if this unit type is capable of detecting units that are cloaked or burrowed. Returns
   * true if this unit type is a detector by default, false if it does not have this property
   */
  public boolean isDetector() {
    return this.detector;
  }

  /**
   * Checks if this unit type is capable of storing resources such as Mineral Fields. Resources are
   * harvested from resource containers. Returns true if this unit type may contain resources that
   * can be harvested, false otherwise.
   */
  public boolean isResourceContainer() {
    return this.resourceContainer;
  }

  /**
   * Checks if this unit type is a resource depot. Resource depots must be placed a certain distance
   * from resources. Resource depots are typically the main building for any particular race.
   * Workers will return resources to the nearest resource depot. Example: if (
   * BWAPI::Broodwar->self() ) { BWAPI::Unitset myUnits = BWAPI::Broodwar->self()->getUnits(); for (
   * auto u : myUnits ) { if ( u->isIdle() && u->getType().isResourceDepot() ) u->train(
   * u->getType().getRace().getWorker() ); } } Returns true if the unit type is a resource depot,
   * false if it is not.
   */
  public boolean isResourceDepot() {
    return this.resourceDepot;
  }

  public boolean isRefinery() {
    return this.refinery;
  }

  /**
   * Checks if this unit type is a worker unit. Worker units can harvest resources and build
   * structures. Worker unit types include the SCV , Probe, and Drone. Returns true if this unit
   * type is a worker, and false if it is not.
   */
  public boolean isWorker() {
    return this.worker;
  }

  /**
   * Checks if this structure is powered by a psi field. Structures powered by psi can only be
   * placed near a Pylon. If the Pylon is destroyed, then this unit will lose power. Returns true if
   * this unit type can only be placed in a psi field, false otherwise. Note If this function
   * returns a successful state, then the following function calls will also return a successful
   * state: isBuilding(), getRace() == Races::Protoss
   */
  public boolean requiresPsi() {
    return this.requiresPsi;
  }

  /**
   * Checks if this structure must be placed on Zerg creep. Returns true if this unit type requires
   * creep, false otherwise. Note If this function returns a successful state, then the following
   * function calls will also return a successful state: isBuilding(), getRace() == Races::Zerg
   */
  public boolean requiresCreep() {
    return this.requiresCreep;
  }

  /**
   * Checks if this unit type spawns two units when being hatched from an Egg. This is only
   * applicable to Zerglings and Scourges. Returns true if morphing this unit type will spawn two of
   * them, and false if only one is spawned.
   */
  public boolean isTwoUnitsInOneEgg() {
    return this.twoUnitsInOneEgg;
  }

  /**
   * Checks if this unit type has the capability to use the Burrow technology when it is researched.
   * Note The Lurker can burrow even without researching the ability. See also TechTypes::Burrow
   * Returns true if this unit can use the Burrow ability, and false otherwise. Note If this
   * function returns a successful state, then the following function calls will also return a
   * successful state: getRace() == Races::Zerg, !isBuilding(), canMove()
   */
  public boolean isBurrowable() {
    return this.burrowable;
  }

  /**
   * Checks if this unit type has the capability to use a cloaking ability when it is researched.
   * This applies only to Wraiths and Ghosts, and does not include units which are permanently
   * cloaked. Returns true if this unit has a cloaking ability, false otherwise. See also
   * hasPermanentCloak, TechTypes::Cloaking_Field, TechTypes::Personnel_Cloaking
   */
  public boolean isCloakable() {
    return this.cloakable;
  }

  /**
   * Checks if this unit is a structure. This includes Mineral Fields and Vespene Geysers. Returns
   * true if this unit is a building, and false otherwise.
   */
  public boolean isBuilding() {
    return this.building;
  }

  /**
   * Checks if this unit is an add-on. Add-ons are attachments used by some Terran structures such
   * as the Comsat Station. Returns true if this unit is an add-on, and false otherwise. Note If
   * this function returns a successful state, then the following function calls will also return a
   * successful state: getRace() == Races::Terran, isBuilding()
   */
  public boolean isAddon() {
    return this.addon;
  }

  /**
   * Checks if this structure has the capability to use the lift-off command. Returns true if this
   * unit type is a flyable building, false otherwise. Note If this function returns a successful
   * state, then the following function calls will also return a successful state: isBuilding()
   */
  public boolean isFlyingBuilding() {
    return this.flyingBuilding;
  }

  /**
   * Checks if this unit type is a neutral type, such as critters and resources. Returns true if
   * this unit is intended to be neutral, and false otherwise.
   */
  public boolean isNeutral() {
    return this.neutral;
  }

  /**
   * Checks if this unit type is a hero. Heroes are types that the player cannot obtain normally,
   * and are identified by the white border around their icon when selected with a group. Note There
   * are two non-hero units included in this set, the Civilian and Dark Templar Hero. Returns true
   * if this unit type is a hero type, and false otherwise.
   */
  public boolean isHero() {
    return this.hero;
  }

  /**
   * Checks if this unit type is a powerup. Powerups can be picked up and carried by workers. They
   * are usually only seen in campaign maps and Capture the Flag. Returns true if this unit type is
   * a powerup type, and false otherwise.
   */
  public boolean isPowerup() {
    return this.powerup;
  }

  /**
   * Checks if this unit type is a beacon. Each race has exactly one beacon each. They are
   * UnitTypes::Special_Zerg_Beacon, UnitTypes::Special_Terran_Beacon, and
   * UnitTypes::Special_Protoss_Beacon. See also isFlagBeacon Returns true if this unit type is one
   * of the three race beacons, and false otherwise.
   */
  public boolean isBeacon() {
    return this.beacon;
  }

  /**
   * Checks if this unit type is a flag beacon. Each race has exactly one flag beacon each. They are
   * UnitTypes::Special_Zerg_Flag_Beacon, UnitTypes::Special_Terran_Flag_Beacon, and
   * UnitTypes::Special_Protoss_Flag_Beacon. Flag beacons spawn a Flag after some ARBITRARY I FORGOT
   * AMOUNT OF FRAMES. See also isBeacon Returns true if this unit type is one of the three race
   * flag beacons, and false otherwise.
   */
  public boolean isFlagBeacon() {
    return this.flagBeacon;
  }

  /**
   * Checks if this structure is special and cannot be obtained normally within the game. Returns
   * true if this structure is a special building, and false otherwise. Note If this function
   * returns a successful state, then the following function calls will also return a successful
   * state: isBuilding()
   */
  public boolean isSpecialBuilding() {
    return this.specialBuilding;
  }

  /**
   * Identifies if this unit type is used to complement some abilities. These include
   * UnitTypes::Spell_Dark_Swarm, UnitTypes::Spell_Disruption_Web, and
   * UnitTypes::Spell_Scanner_Sweep, which correspond to TechTypes::Dark_Swarm,
   * TechTypes::Disruption_Web, and TechTypes::Scanner_Sweep respectively. Returns true if this unit
   * type is used for an ability, and false otherwise.
   */
  public boolean isSpell() {
    return this.spell;
  }

  /**
   * Checks if this structure type produces creep. That is, the unit type spreads creep over a wide
   * area so that Zerg structures can be placed on it. Returns true if this unit type spreads creep.
   * Note If this function returns a successful state, then the following function calls will also
   * return a successful state: getRace() == Races::Zerg, isBuilding() Since 4.1.2
   */
  public boolean producesCreep() {
    return this.producesCreep;
  }

  /**
   * Checks if this unit type produces larva. This is essentially used to check if the unit type is
   * a Hatchery, Lair, or Hive. Returns true if this unit type produces larva. Note If this function
   * returns a successful state, then the following function calls will also return a successful
   * state: getRace() == Races::Zerg, isBuilding()
   */
  public boolean producesLarva() {
    return this.producesLarva;
  }

  /**
   * Checks if this unit type is a mineral field and contains a resource amount. This indicates that
   * the unit type is either UnitTypes::Resource_Mineral_Field,
   * UnitTypes::Resource_Mineral_Field_Type_2, or UnitTypes::Resource_Mineral_Field_Type_3. Returns
   * true if this unit type is a mineral field resource.
   */
  public boolean isMineralField() {
    return this.mineralField;
  }

  /**
   * Checks if this unit type is a neutral critter. Returns true if this unit type is a critter, and
   * false otherwise. Example usage: BWAPI::Position myBasePosition(
   * BWAPI::Broodwar->self()->getStartLocation() ); BWAPI::UnitSet unitsAroundTheBase =
   * BWAPI::Broodwar->getUnitsInRadius(myBasePosition, 1024, !BWAPI::Filter::IsOwned &&
   * !BWAPI::Filter::IsParasited); for ( auto u : unitsAroundTheBase ) { if (
   * u->getType().isCritter() && !u->isInvincible() ) { BWAPI::Unit myQueen =
   * u->getClosestUnit(BWAPI::Filter::GetType == BWAPI::UnitTypes::Zerg_Queen &&
   * BWAPI::Filter::IsOwned); if ( myQueen ) myQueen->useTech(BWAPI::TechTypes::Parasite, u); } }
   */
  public boolean isCritter() {
    return this.critter;
  }

  /**
   * Checks if this unit type is capable of constructing an add-on. An add-on is an extension or
   * attachment for Terran structures, specifically the Command Center, Factory, Starport, and
   * Science Facility. Returns true if this unit type can construct an add-on, and false if it can
   * not. See also isAddon
   */
  public boolean canBuildAddon() {
    return this.canBuildAddon;
  }

  /**
   * Retrieves the set of technologies that this unit type is capable of researching. Note Some maps
   * have special parameters that disable certain technologies. Use
   * PlayerInterface::isResearchAvailable to determine if a technology is actually available in the
   * current game for a specific player. Returns TechType::set containing the technology types that
   * can be researched. See also PlayerInterface::isResearchAvailable Since 4.1.2
   */
  public List<TechType> researchesWhat() {
    return this.researchesWhat;
  }

  /**
   * Retrieves the set of upgrades that this unit type is capable of upgrading. Note Some maps have
   * special upgrade limitations. Use PlayerInterface::getMaxUpgradeLevel to check if an upgrade is
   * available. Returns UpgradeType::set containing the upgrade types that can be upgraded. See also
   * PlayerInterface::getMaxUpgradeLevel Since 4.1.2
   */
  public List<UpgradeType> upgradesWhat() {
    return this.upgradesWhat;
  }

  /**
   * Retrieves the set of units that this unit type is capable of creating. This includes training,
   * constructing, warping, and morphing. <i>Some maps have special parameters that disable
   * construction of units that are otherwise normally available. Use
   * PlayerInterface::isUnitAvailable to determine if a unit type is actually available in the
   * current game for a specific player.</i>
   *
   * @return A list containing the units it can build.
   */
  public List<UnitType> buildsWhat() {
    return buildsWhat;
  }

  private static class IdMapper {

    static final UnitType[] unitTypeForId = IdMapperHelper.toIdTypeArray(UnitType.class);
  }

  public static class WhatBuilds {

    private final UnitType unitType;
    private final int amount;

    @BridgeValue
    public WhatBuilds(UnitType unitType, int amount) {
      this.unitType = unitType;
      this.amount = amount;
    }

    public UnitType getUnitType() {
      return unitType;
    }

    public int getAmount() {
      return amount;
    }
  }
}
