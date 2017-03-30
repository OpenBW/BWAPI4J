package org.openbw.bwapi4j.type;

import java.util.List;
import java.util.Map;

import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.util.Pair;

public enum UnitType {

	Terran_Marine(0), Terran_Ghost(1), Terran_Vulture(2), Terran_Goliath(3),
	// 4 Goliath Turret
	Terran_Siege_Tank_Tank_Mode(5),
	// 6 Siege Tank Turret (Tank Mode)
	Terran_SCV(7), Terran_Wraith(8), Terran_Science_Vessel(9), Hero_Gui_Montag(10), Terran_Dropship(
			11), Terran_Battlecruiser(12), Terran_Vulture_Spider_Mine(13), Terran_Nuclear_Missile(
					14), Terran_Civilian(15), Hero_Sarah_Kerrigan(16), Hero_Alan_Schezar(17),
	// 18 Alan Schezar Turret
	Hero_Jim_Raynor_Vulture(19), Hero_Jim_Raynor_Marine(20), Hero_Tom_Kazansky(21), Hero_Magellan(
			22), Hero_Edmund_Duke_Tank_Mode(23),
	// 24 Edmund Duke Turret (Tank Mode)
	Hero_Edmund_Duke_Siege_Mode(25),
	// 26 Edmund Duke Turret (Siege Mode)
	Hero_Arcturus_Mengsk(27), Hero_Hyperion(28), Hero_Norad_II(29), Terran_Siege_Tank_Siege_Mode(30),
	// 31 Siege Tank Turret (Siege Mode)
	Terran_Firebat(32), Spell_Scanner_Sweep(33), Terran_Medic(34), Zerg_Larva(35), Zerg_Egg(36), Zerg_Zergling(
			37), Zerg_Hydralisk(38), Zerg_Ultralisk(39), Zerg_Broodling(40), Zerg_Drone(41), Zerg_Overlord(
					42), Zerg_Mutalisk(43), Zerg_Guardian(44), Zerg_Queen(45), Zerg_Defiler(
							46), Zerg_Scourge(47), Hero_Torrasque(48), Hero_Matriarch(
									49), Zerg_Infested_Terran(50), Hero_Infested_Kerrigan(51), Hero_Unclean_One(
											52), Hero_Hunter_Killer(53), Hero_Devouring_One(54), Hero_Kukulza_Mutalisk(
													55), Hero_Kukulza_Guardian(56), Hero_Yggdrasill(
															57), Terran_Valkyrie(58), Zerg_Cocoon(59), Protoss_Corsair(
																	60), Protoss_Dark_Templar(61), Zerg_Devourer(
																			62), Protoss_Dark_Archon(63), Protoss_Probe(
																					64), Protoss_Zealot(
																							65), Protoss_Dragoon(
																									66), Protoss_High_Templar(
																											67), Protoss_Archon(
																													68), Protoss_Shuttle(
																															69), Protoss_Scout(
																																	70), Protoss_Arbiter(
																																			71), Protoss_Carrier(
																																					72), Protoss_Interceptor(
																																							73), Hero_Dark_Templar(
																																									74), Hero_Zeratul(
																																											75), Hero_Tassadar_Zeratul_Archon(
																																													76), Hero_Fenix_Zealot(
																																															77), Hero_Fenix_Dragoon(
																																																	78), Hero_Tassadar(
																																																			79), Hero_Mojo(
																																																					80), Hero_Warbringer(
																																																							81), Hero_Gantrithor(
																																																									82), Protoss_Reaver(
																																																											83), Protoss_Observer(
																																																													84), Protoss_Scarab(
																																																															85), Hero_Danimoth(
																																																																	86), Hero_Aldaris(
																																																																			87), Hero_Artanis(
																																																																					88), Critter_Rhynadon(
																																																																							89), Critter_Bengalaas(
																																																																									90), Special_Cargo_Ship(
																																																																											91), Special_Mercenary_Gunship(
																																																																													92), Critter_Scantid(
																																																																															93), Critter_Kakaru(
																																																																																	94), Critter_Ragnasaur(
																																																																																			95), Critter_Ursadon(
																																																																																					96), Zerg_Lurker_Egg(
																																																																																							97), Hero_Raszagal(
																																																																																									98), Hero_Samir_Duran(
																																																																																											99), Hero_Alexei_Stukov(
																																																																																													100), Special_Map_Revealer(
																																																																																															101), Hero_Gerard_DuGalle(
																																																																																																	102), Zerg_Lurker(
																																																																																																			103), Hero_Infested_Duran(
																																																																																																					104), Spell_Disruption_Web(
																																																																																																							105), Terran_Command_Center(
																																																																																																									106), Terran_Comsat_Station(
																																																																																																											107), Terran_Nuclear_Silo(
																																																																																																													108), Terran_Supply_Depot(
																																																																																																															109), Terran_Refinery(
																																																																																																																	110), Terran_Barracks(
																																																																																																																			111), Terran_Academy(
																																																																																																																					112), Terran_Factory(
																																																																																																																							113), Terran_Starport(
																																																																																																																									114), Terran_Control_Tower(
																																																																																																																											115), Terran_Science_Facility(
																																																																																																																													116), Terran_Covert_Ops(
																																																																																																																															117), Terran_Physics_Lab(
																																																																																																																																	118),
	// 119 Starbase (Unused)
	Terran_Machine_Shop(120),
	// 121 Repair Bay (Unused)
	Terran_Engineering_Bay(122), Terran_Armory(123), Terran_Missile_Turret(124), Terran_Bunker(
			125), Special_Crashed_Norad_II(126), Special_Ion_Cannon(127), Powerup_Uraj_Crystal(
					128), Powerup_Khalis_Crystal(129), Zerg_Infested_Command_Center(130), Zerg_Hatchery(131), Zerg_Lair(
							132), Zerg_Hive(133), Zerg_Nydus_Canal(134), Zerg_Hydralisk_Den(135), Zerg_Defiler_Mound(
									136), Zerg_Greater_Spire(137), Zerg_Queens_Nest(138), Zerg_Evolution_Chamber(
											139), Zerg_Ultralisk_Cavern(140), Zerg_Spire(141), Zerg_Spawning_Pool(
													142), Zerg_Creep_Colony(143), Zerg_Spore_Colony(144),
	// 144 Unused Zerg Building 1
	Zerg_Sunken_Colony(146), Special_Overmind_With_Shell(147), Special_Overmind(148), Zerg_Extractor(
			149), Special_Mature_Chrysalis(150), Special_Cerebrate(151), Special_Cerebrate_Daggoth(152),
	// 153 Unused Zerg Building 2
	Protoss_Nexus(154), Protoss_Robotics_Facility(155), Protoss_Pylon(156), Protoss_Assimilator(157),
	// 158 Unused Protoss Building 1
	Protoss_Observatory(159), Protoss_Gateway(160),
	// 161 Unused Protoss Building 2
	Protoss_Photon_Cannon(162), Protoss_Citadel_of_Adun(163), Protoss_Cybernetics_Core(164), Protoss_Templar_Archives(
			165), Protoss_Forge(166), Protoss_Stargate(167), Special_Stasis_Cell_Prison(168), Protoss_Fleet_Beacon(
					169), Protoss_Arbiter_Tribunal(170), Protoss_Robotics_Support_Bay(171), Protoss_Shield_Battery(
							172), Special_Khaydarin_Crystal_Form(173), Special_Protoss_Temple(
									174), Special_XelNaga_Temple(175), Resource_Mineral_Field(
											176), Resource_Mineral_Field_Type_2(
													177), Resource_Mineral_Field_Type_3(178),
	// 179 Cave (Unused)
	// 180 Cave-in (Unused)
	// 181 Cantina (Unused)
	// 182 Mining Platform (Unused)
	// 183 Independent Command Center (Unused)
	Special_Independant_Starport(184),
	// 185 Independent Jump Gate (Unused)
	// 186 Ruins (Unused)
	// 187 Khaydarin Crystal Formation (Unused)
	Resource_Vespene_Geyser(188), Special_Warp_Gate(189), Special_Psi_Disrupter(190),
	// 191 Zerg Marker (Unused)
	// 192 Terran Marker (Unused)
	// 193 Protoss Marker (Unused)
	Special_Zerg_Beacon(194), Special_Terran_Beacon(195), Special_Protoss_Beacon(196), Special_Zerg_Flag_Beacon(
			197), Special_Terran_Flag_Beacon(198), Special_Protoss_Flag_Beacon(199), Special_Power_Generator(
					200), Special_Overmind_Cocoon(201), Spell_Dark_Swarm(202), Special_Floor_Missile_Trap(
							203), Special_Floor_Hatch(204), Special_Upper_Level_Door(
									205), Special_Right_Upper_Level_Door(206), Special_Pit_Door(
											207), Special_Right_Pit_Door(208), Special_Floor_Gun_Trap(
													209), Special_Wall_Missile_Trap(210), Special_Wall_Flame_Trap(
															211), Special_Right_Wall_Missile_Trap(
																	212), Special_Right_Wall_Flame_Trap(
																			213), Special_Start_Location(
																					214), Powerup_Flag(
																							215), Powerup_Young_Chrysalis(
																									216), Powerup_Psi_Emitter(
																											217), Powerup_Data_Disk(
																													218), Powerup_Khaydarin_Crystal(
																															219), Powerup_Mineral_Cluster_Type_1(
																																	220), Powerup_Mineral_Cluster_Type_2(
																																			221), Powerup_Protoss_Gas_Orb_Type_1(
																																					222), Powerup_Protoss_Gas_Orb_Type_2(
																																							223), Powerup_Zerg_Gas_Sac_Type_1(
																																									224), Powerup_Zerg_Gas_Sac_Type_2(
																																											225), Powerup_Terran_Gas_Tank_Type_1(
																																													226), Powerup_Terran_Gas_Tank_Type_2(
																																															227), None(
																																																	228),
	// 229 All Units (BWAPI4)
	// 230 Men (BWAPI4)
	// 231 Buildings (BWAPI4)
	// 232 Factories (BWAPI4)
	Unknown(233);

	private int id;
	private Race race;
	private int whatBuildID;
	private int requiredTechID;
	private int armorUpgradeID;
	private int maxHitPoints;
	private int maxShields;
	private int maxEnergy;
	private int armor;
	private int mineralPrice;
	private int gasPrice;
	private int buildTime;
	private int supplyRequired;
	private int supplyProvided;
	private int spaceRequired;
	private int spaceProvided;
	private int buildScore;
	private int destroyScore;
	private int sizeID;
	private int tileWidth;
	private int tileHeight;
	private int dimensionLeft;
	private int dimensionUp;
	private int dimensionRight;
	private int dimensionDown;
	private int seekRange;
	private int sightRange;
	private int groundWeaponID;
	private int maxGroundHits;
	private int airWeaponID;
	private int maxAirHits;
	private double topSpeed;
	private int acceleration;
	private int haltDistance;
	private int turnRadius;
	private boolean produceCapable;
	private boolean attackCapable;
	private boolean canMove;
	private boolean flyer;
	private boolean regenerates;
	private boolean spellcaster;
	private boolean invincible;
	private boolean organic;
	private boolean mechanical;
	private boolean robotic;
	private boolean detector;
	private boolean resourceContainer;
	private boolean refinery;
	private boolean worker;
	private boolean requiresPsi;
	private boolean requiresCreep;
	private boolean burrowable;
	private boolean cloakable;
	private boolean building;
	private boolean addon;
	private boolean flyingBuilding;
	private boolean spell;

	UnitType(int id) {
		this.id = id;
	}

	/**
	 * Retrieves the Race that the unit type belongs to. Returns Race indicating
	 * the race that owns this unit type. Return values Race::None indicating
	 * that the unit type does not belong to any particular race (a critter for
	 * example).
	 */
	public Race getRace() {
		return this.race;
	}

	/**
	 * Obtains the source unit type that is used to build or train this unit
	 * type, as well as the amount of them that are required. Returns std::pair
	 * in which the first value is the UnitType that builds this unit type, and
	 * the second value is the number of those types that are required (this
	 * value is 2 for Archons, and 1 for all other types). Return values
	 * pair(UnitTypes::None,0) If this unit type cannot be made by the player.
	 */
	public Pair<UnitType, Integer> whatBuilds() {
		return whatBuilds_native(pointer);
	}

	/**
	 * Retrieves the immediate technology tree requirements to make this unit
	 * type. Returns std::map containing a UnitType to number mapping of
	 * UnitTypes required.
	 */
	public Map<UnitType, Integer> requiredUnits() {
		return requiredUnits_native(pointer);
	}

	/**
	 * Identifies the required TechType in order to create certain units. Note
	 * The only unit that requires a technology is the Lurker, which needs
	 * Lurker Aspect. Returns TechType indicating the technology that must be
	 * researched in order to create this unit type. Return values
	 * TechTypes::None If creating this unit type does not require a technology
	 * to be researched.
	 */
	public TechType requiredTech() {
		return requiredTech_native(pointer);
	}

	/**
	 * Retrieves the cloaking technology associated with certain units. Returns
	 * TechType referring to the cloaking technology that this unit type uses as
	 * an ability. Return values TechTypes::None If this unit type does not have
	 * an active cloak ability.
	 */
	public TechType cloakingTech() {
		return cloakingTech_native(pointer);
	}

	/**
	 * Retrieves the set of abilities that this unit can use, provided it is
	 * available to you in the game. Returns Set of TechTypes containing ability
	 * information.
	 */
	public List<TechType> abilities() {
		return abilities_native(pointer);
	}

	/**
	 * Retrieves the set of upgrades that this unit can use to enhance its
	 * fighting ability. Returns Set of UpgradeTypes containing upgrade types
	 * that will impact this unit type.
	 */
	public List<UpgradeType> upgrades() {
		return upgrades_native(pointer);
	}

	/**
	 * Retrieves the upgrade type used to increase the armor of this unit type.
	 * For each upgrade, this unit type gains +1 additional armor. Returns
	 * UpgradeType indicating the upgrade that increases this unit type's armor
	 * amount.
	 */
	public UpgradeType armorUpgrade() {
		return armorUpgrade_native(pointer);
	}

	/**
	 * Retrieves the default maximum amount of hit points that this unit type
	 * can have. Note This value may not necessarily match the value seen in the
	 * Use Map Settings game type. Returns Integer indicating the maximum amount
	 * of hit points for this unit type.
	 */
	public int maxHitPoints() {
		return maxHitPoints_native(pointer);
	}

	/**
	 * Retrieves the default maximum amount of shield points that this unit type
	 * can have. Note This value may not necessarily match the value seen in the
	 * Use Map Settings game type. Returns Integer indicating the maximum amount
	 * of shield points for this unit type. Return values 0 If this unit type
	 * does not have shields.
	 */
	public int maxShields() {
		return maxShields_native(pointer);
	}

	/**
	 * Retrieves the maximum amount of energy this unit type can have by
	 * default. Returns Integer indicating the maximum amount of energy for this
	 * unit type. Return values 0 If this unit does not gain energy for
	 * abilities.
	 */
	public int maxEnergy() {
		return maxEnergy_native(pointer);
	}

	/**
	 * Retrieves the default amount of armor that the unit type starts with,
	 * excluding upgrades. Note This value may not necessarily match the value
	 * seen in the Use Map Settings game type. Returns The amount of armor the
	 * unit type has.
	 */
	public int armor() {
		return armor_native(pointer);
	}

	/**
	 * Retrieves the default mineral price of purchasing the unit. Note This
	 * value may not necessarily match the value seen in the Use Map Settings
	 * game type. Returns Mineral cost of the unit.
	 */
	public int mineralPrice() {
		return mineralPrice_native(pointer);
	}

	/**
	 * Retrieves the default vespene gas price of purchasing the unit. Note This
	 * value may not necessarily match the value seen in the Use Map Settings
	 * game type. Returns Vespene gas cost of the unit.
	 */
	public int gasPrice() {
		return gasPrice_native(pointer);
	}

	/**
	 * Retrieves the default time, in frames, needed to train, morph, or build
	 * the unit. Note This value may not necessarily match the value seen in the
	 * Use Map Settings game type. Returns Number of frames needed in order to
	 * build the unit. See also UnitInterface::getRemainingBuildTime
	 */
	public int buildTime() {
		return buildTime_native(pointer);
	}

	/**
	 * Retrieves the amount of supply that this unit type will use when created.
	 * It will use the supply pool that is appropriate for its Race. Note In
	 * Starcraft programming, the managed supply values are double than what
	 * they appear in the game. The reason for this is because Zerglings use 0.5
	 * visible supply. Returns Integer containing the supply required to build
	 * this unit. See also supplyProvided, PlayerInterface::supplyTotal,
	 * PlayerInterface::supplyUsed
	 */
	public int supplyRequired() {
		return supplyRequired_native(pointer);
	}

	/**
	 * Retrieves the amount of supply that this unit type produces for its
	 * appropriate Race's supply pool. Note In Starcraft programming, the
	 * managed supply values are double than what they appear in the game. The
	 * reason for this is because Zerglings use 0.5 visible supply. See also
	 * supplyRequired, PlayerInterface::supplyTotal, PlayerInterface::supplyUsed
	 */
	public int supplyProvided() {
		return supplyProvided_native(pointer);
	}

	/**
	 * Retrieves the amount of space required by this unit type to fit inside a
	 * Bunker or Transport(Dropship, Shuttle, Overlord ). Returns Amount of
	 * space required by this unit type for transport. Return values 255 If this
	 * unit type can not be transported. See also spaceProvided
	 */
	public int spaceRequired() {
		return spaceRequired_native(pointer);
	}

	/**
	 * Retrieves the amount of space provided by this Bunker or
	 * Transport(Dropship, Shuttle, Overlord ) for unit transportation. Returns
	 * The number of slots provided by this unit type. See also spaceRequired
	 */
	public int spaceProvided() {
		return spaceProvided_native(pointer);
	}

	/**
	 * Retrieves the amount of score points awarded for constructing this unit
	 * type. This value is used for calculating scores in the post-game score
	 * screen. Returns Number of points awarded for constructing this unit type.
	 * See also destroyScore
	 */
	public int buildScore() {
		return buildScore_native(pointer);
	}

	/**
	 * Retrieves the amount of score points awarded for killing this unit type.
	 * This value is used for calculating scores in the post-game score screen.
	 * Returns Number of points awarded for killing this unit type. See also
	 * buildScore
	 */
	public int destroyScore() {
		return destroyScore_native(pointer);
	}

	/**
	 * Retrieves the UnitSizeType of this unit, which is used in calculations
	 * along with weapon damage types to determine the amount of damage that
	 * will be dealt to this type. Returns UnitSizeType indicating the
	 * conceptual size of the unit type. See also WeaponType::damageType
	 */
	public UnitSizeType size() {
		return size_native(pointer);
	}

	/**
	 * Retrieves the width of this unit type, in tiles. Used for determining the
	 * tile size of structures. Returns Width of this unit type, in tiles.
	 */
	public int tileWidth() {
		return tileWidth_native(pointer);
	}

	/**
	 * Retrieves the height of this unit type, in tiles. Used for determining
	 * the tile size of structures. Returns Height of this unit type, in tiles.
	 */
	public int tileHeight() {
		return tileHeight_native(pointer);
	}

	/**
	 * Retrieves the tile size of this unit type. Used for determining the tile
	 * size of structures. Returns TilePosition containing the width (x) and
	 * height (y) of the unit type, in tiles.
	 */
	public TilePosition tileSize() {
		return tileSize_native(pointer);
	}

	/**
	 * Retrieves the distance from the center of the unit type to its left edge.
	 * Returns Distance to this unit type's left edge from its center, in
	 * pixels.
	 */
	public int dimensionLeft() {
		return dimensionLeft_native(pointer);
	}

	/**
	 * Retrieves the distance from the center of the unit type to its top edge.
	 * Returns Distance to this unit type's top edge from its center, in pixels.
	 */
	public int dimensionUp() {
		return dimensionUp_native(pointer);
	}

	/**
	 * Retrieves the distance from the center of the unit type to its right
	 * edge. Returns Distance to this unit type's right edge from its center, in
	 * pixels.
	 */
	public int dimensionRight() {
		return dimensionRight_native(pointer);
	}

	/**
	 * Retrieves the distance from the center of the unit type to its bottom
	 * edge. Returns Distance to this unit type's bottom edge from its center,
	 * in pixels.
	 */
	public int dimensionDown() {
		return dimensionDown_native(pointer);
	}

	/**
	 * A macro for retrieving the width of the unit type, which is calculated
	 * using dimensionLeft + dimensionRight + 1. Returns Width of the unit, in
	 * pixels.
	 */
	public int width() {
		return width_native(pointer);
	}

	/**
	 * A macro for retrieving the height of the unit type, which is calculated
	 * using dimensionUp + dimensionDown + 1. Returns Height of the unit, in
	 * pixels.
	 */
	public int height() {
		return height_native(pointer);
	}

	/**
	 * Retrieves the range at which this unit type will start targeting enemy
	 * units. Returns Distance at which this unit type begins to seek out enemy
	 * units, in pixels.
	 */
	public int seekRange() {
		return seekRange_native(pointer);
	}

	/**
	 * Retrieves the sight range of this unit type. Returns Sight range of this
	 * unit type, measured in pixels.
	 */
	public int sightRange() {
		return sightRange_native(pointer);
	}

	/**
	 * Retrieves this unit type's weapon type used when attacking targets on the
	 * ground. Returns WeaponType used as this unit type's ground weapon. See
	 * also maxGroundHits, airWeapon
	 */
	public WeaponType groundWeapon() {
		return groundWeapon_native(pointer);
	}

	/**
	 * Retrieves the maximum number of hits this unit can deal to a ground
	 * target using its ground weapon. This value is multiplied by the ground
	 * weapon's damage to calculate the unit type's damage potential. Returns
	 * Maximum number of hits given to ground targets. See also groundWeapon,
	 * maxAirHits
	 */
	public int maxGroundHits() {
		return maxGroundHits_native(pointer);
	}

	/**
	 * Retrieves this unit type's weapon type used when attacking targets in the
	 * air. Returns WeaponType used as this unit type's air weapon. See also
	 * maxAirHits, groundWeapon
	 */
	public WeaponType airWeapon() {
		return airWeapon_native(pointer);
	}

	/**
	 * Retrieves the maximum number of hits this unit can deal to a flying
	 * target using its air weapon. This value is multiplied by the air weapon's
	 * damage to calculate the unit type's damage potential. Returns Maximum
	 * number of hits given to air targets. See also airWeapon, maxGroundHits
	 */
	public int maxAirHits() {
		return maxAirHits_native(pointer);
	}

	/**
	 * Retrieves this unit type's top movement speed with no upgrades. Note That
	 * some units have inconsistent movement and this value is sometimes an
	 * approximation. Returns The approximate top speed, in pixels per frame, as
	 * a double. For liftable Terran structures, this function returns their
	 * movement speed while lifted.
	 */
	public double topSpeed() {
		return topSpeed_native(pointer);
	}

	/**
	 * Retrieves the unit's acceleration amount. Returns How fast the unit can
	 * accelerate to its top speed.
	 */
	public int acceleration() {
		return acceleration_native(pointer);
	}

	/**
	 * Retrieves the unit's halting distance. This determines how fast a unit
	 * can stop moving. Returns A halting distance value.
	 */
	public int haltDistance() {
		return haltDistance_native(pointer);
	}

	/**
	 * Retrieves a unit's turning radius. This determines how fast a unit can
	 * turn. Returns A turn radius value.
	 */
	public int turnRadius() {
		return turnRadius_native(pointer);
	}

	/**
	 * Determines if a unit can train other units. For example,
	 * UnitTypes::Terran_Barracks.canProduce() will return true, while
	 * UnitTypes::Terran_Marine.canProduce() will return false. This is also
	 * true for two non-structures: Carrier (can produce interceptors) and
	 * Reaver (can produce scarabs). Returns true if this unit type can have a
	 * production queue, and false otherwise.
	 */
	public boolean canProduce() {
		return canProduce_native(pointer);
	}

	/**
	 * Checks if this unit is capable of attacking. Note This function returns
	 * false for units that can only inflict damage via special abilities, such
	 * as the High Templar. Returns true if this unit type is capable of
	 * damaging other units with a standard attack, and false otherwise.
	 */
	public boolean canAttack() {
		return canAttack_native(pointer);
	}

	/**
	 * Checks if this unit type is capable of movement. Note Buildings will
	 * return false, including Terran liftable buildings which are capable of
	 * moving when lifted. Returns true if this unit can use a movement command,
	 * and false if they cannot move.
	 */
	public boolean canMove() {
		return canMove_native(pointer);
	}

	/**
	 * Checks if this unit type is a flying unit. Flying units ignore ground
	 * pathing and collisions. Returns true if this unit type is in the air by
	 * default, and false otherwise.
	 */
	public boolean isFlyer() {
		return isFlyer_native(pointer);
	}

	/**
	 * Checks if this unit type can regenerate hit points. This generally
	 * applies to Zerg units. Returns true if this unit type regenerates its hit
	 * points, and false otherwise.
	 */
	public boolean regeneratesHP() {
		return regeneratesHP_native(pointer);
	}

	/**
	 * Checks if this unit type has the capacity to store energy and use it for
	 * special abilities. Returns true if this unit type generates energy, and
	 * false if it does not have an energy pool.
	 */
	public boolean isSpellcaster() {
		return isSpellcaster_native(pointer);
	}

	/**
	 * Checks if this unit type is permanently cloaked. This means the unit type
	 * is always cloaked and requires a detector in order to see it. Returns
	 * true if this unit type is permanently cloaked, and false otherwise.
	 */
	public boolean hasPermanentCloak() {
		return hasPermanentCloak_native(pointer);
	}

	/**
	 * Checks if this unit type is invincible by default. Invincible units
	 * cannot take damage. Returns true if this unit type is invincible, and
	 * false if it is vulnerable to attacks.
	 */
	public boolean isInvincible() {
		return isInvincible_native(pointer);
	}

	/**
	 * Checks if this unit is an organic unit. The organic property is required
	 * for some abilities such as Heal. Returns true if this unit type has the
	 * organic property, and false otherwise.
	 */
	public boolean isOrganic() {
		return isOrganic_native(pointer);
	}

	/**
	 * Checks if this unit is mechanical. The mechanical property is required
	 * for some actions such as Repair. Returns true if this unit type has the
	 * mechanical property, and false otherwise.
	 */
	public boolean isMechanical() {
		return isMechanical_native(pointer);
	}

	/**
	 * Checks if this unit is robotic. The robotic property is applied to
	 * robotic units such as the Probe which prevents them from taking damage
	 * from Irradiate. Returns true if this unit type has the robotic property,
	 * and false otherwise.
	 */
	public boolean isRobotic() {
		return isRobotic_native(pointer);
	}

	/**
	 * Checks if this unit type is capable of detecting units that are cloaked
	 * or burrowed. Returns true if this unit type is a detector by default,
	 * false if it does not have this property
	 */
	public boolean isDetector() {
		return isDetector_native(pointer);
	}

	/**
	 * Checks if this unit type is capable of storing resources such as Mineral
	 * Fields. Resources are harvested from resource containers. Returns true if
	 * this unit type may contain resources that can be harvested, false
	 * otherwise.
	 */
	public boolean isResourceContainer() {
		return isResourceContainer_native(pointer);
	}

	/**
	 * Checks if this unit type is a resource depot. Resource depots must be
	 * placed a certain distance from resources. Resource depots are typically
	 * the main building for any particular race. Workers will return resources
	 * to the nearest resource depot. Example: if ( BWAPI::Broodwar->self() ) {
	 * BWAPI::Unitset myUnits = BWAPI::Broodwar->self()->getUnits(); for ( auto
	 * u : myUnits ) { if ( u->isIdle() && u->getType().isResourceDepot() )
	 * u->train( u->getType().getRace().getWorker() ); } } Returns true if the
	 * unit type is a resource depot, false if it is not.
	 */
	public boolean isResourceDepot() {
		return isResourceDepot_native(pointer);
	}

	/**
	 * Checks if this unit type is a refinery. A refinery is a structure that is
	 * placed on top of a Vespene Geyser . Refinery types are Refinery ,
	 * Extractor , and Assimilator. Example: if ( BWAPI::Broodwar->self() ) {
	 * BWAPI::Unitset myUnits = BWAPI::Broodwar->self()->getUnits(); for ( auto
	 * u : myUnits ) { if ( u->getType().isRefinery() ) { int nWorkersAssigned =
	 * u->getClientInfo<int>('work'); if ( nWorkersAssigned < 3 ) { Unit
	 * pClosestIdleWorker = u->getClosestUnit(BWAPI::Filter::IsWorker &&
	 * BWAPI::Filter::IsIdle); if ( pClosestIdleWorker ) { // gather from the
	 * refinery (and check if successful) if ( pClosestIdleWorker->gather(u) ) {
	 * // set a back reference for when the unit is killed or re-assigned (code
	 * not provided) pClosestIdleWorker->setClientInfo(u, 'ref'); // Increment
	 * the number of workers assigned and associate it with the refinery
	 * ++nWorkersAssigned; u->setClientInfo(nWorkersAssigned, 'work'); } } } //
	 * workers < 3 } // isRefinery } // for } Returns true if this unit type is
	 * a refinery, and false if it is not.
	 */
	public boolean isRefinery() {
		return isRefinery_native(pointer);
	}

	/**
	 * Checks if this unit type is a worker unit. Worker units can harvest
	 * resources and build structures. Worker unit types include the SCV ,
	 * Probe, and Drone. Returns true if this unit type is a worker, and false
	 * if it is not.
	 */
	public boolean isWorker() {
		return isWorker_native(pointer);
	}

	/**
	 * Checks if this structure is powered by a psi field. Structures powered by
	 * psi can only be placed near a Pylon. If the Pylon is destroyed, then this
	 * unit will lose power. Returns true if this unit type can only be placed
	 * in a psi field, false otherwise. Note If this function returns a
	 * successful state, then the following function calls will also return a
	 * successful state: isBuilding(), getRace() == Races::Protoss
	 */
	public boolean requiresPsi() {
		return requiresPsi_native(pointer);
	}

	/**
	 * Checks if this structure must be placed on Zerg creep. Returns true if
	 * this unit type requires creep, false otherwise. Note If this function
	 * returns a successful state, then the following function calls will also
	 * return a successful state: isBuilding(), getRace() == Races::Zerg
	 */
	public boolean requiresCreep() {
		return requiresCreep_native(pointer);
	}

	/**
	 * Checks if this unit type spawns two units when being hatched from an Egg.
	 * This is only applicable to Zerglings and Scourges. Returns true if
	 * morphing this unit type will spawn two of them, and false if only one is
	 * spawned.
	 */
	public boolean isTwoUnitsInOneEgg() {
		return isTwoUnitsInOneEgg_native(pointer);
	}

	/**
	 * Checks if this unit type has the capability to use the Burrow technology
	 * when it is researched. Note The Lurker can burrow even without
	 * researching the ability. See also TechTypes::Burrow Returns true if this
	 * unit can use the Burrow ability, and false otherwise. Note If this
	 * function returns a successful state, then the following function calls
	 * will also return a successful state: getRace() == Races::Zerg,
	 * !isBuilding(), canMove()
	 */
	public boolean isBurrowable() {
		return isBurrowable_native(pointer);
	}

	/**
	 * Checks if this unit type has the capability to use a cloaking ability
	 * when it is researched. This applies only to Wraiths and Ghosts, and does
	 * not include units which are permanently cloaked. Returns true if this
	 * unit has a cloaking ability, false otherwise. See also hasPermanentCloak,
	 * TechTypes::Cloaking_Field, TechTypes::Personnel_Cloaking
	 */
	public boolean isCloakable() {
		return isCloakable_native(pointer);
	}

	/**
	 * Checks if this unit is a structure. This includes Mineral Fields and
	 * Vespene Geysers. Returns true if this unit is a building, and false
	 * otherwise.
	 */
	public boolean isBuilding() {
		return isBuilding_native(pointer);
	}

	/**
	 * Checks if this unit is an add-on. Add-ons are attachments used by some
	 * Terran structures such as the Comsat Station. Returns true if this unit
	 * is an add-on, and false otherwise. Note If this function returns a
	 * successful state, then the following function calls will also return a
	 * successful state: getRace() == Races::Terran, isBuilding()
	 */
	public boolean isAddon() {
		return isAddon_native(pointer);
	}

	/**
	 * Checks if this structure has the capability to use the lift-off command.
	 * Returns true if this unit type is a flyable building, false otherwise.
	 * Note If this function returns a successful state, then the following
	 * function calls will also return a successful state: isBuilding()
	 */
	public boolean isFlyingBuilding() {
		return isFlyingBuilding_native(pointer);
	}

	/**
	 * Checks if this unit type is a neutral type, such as critters and
	 * resources. Returns true if this unit is intended to be neutral, and false
	 * otherwise.
	 */
	public boolean isNeutral() {
		return isNeutral_native(pointer);
	}

	/**
	 * Checks if this unit type is a hero. Heroes are types that the player
	 * cannot obtain normally, and are identified by the white border around
	 * their icon when selected with a group. Note There are two non-hero units
	 * included in this set, the Civilian and Dark Templar Hero. Returns true if
	 * this unit type is a hero type, and false otherwise.
	 */
	public boolean isHero() {
		return isHero_native(pointer);
	}

	/**
	 * Checks if this unit type is a powerup. Powerups can be picked up and
	 * carried by workers. They are usually only seen in campaign maps and
	 * Capture the Flag. Returns true if this unit type is a powerup type, and
	 * false otherwise.
	 */
	public boolean isPowerup() {
		return isPowerup_native(pointer);
	}

	/**
	 * Checks if this unit type is a beacon. Each race has exactly one beacon
	 * each. They are UnitTypes::Special_Zerg_Beacon,
	 * UnitTypes::Special_Terran_Beacon, and UnitTypes::Special_Protoss_Beacon.
	 * See also isFlagBeacon Returns true if this unit type is one of the three
	 * race beacons, and false otherwise.
	 */
	public boolean isBeacon() {
		return isBeacon_native(pointer);
	}

	/**
	 * Checks if this unit type is a flag beacon. Each race has exactly one flag
	 * beacon each. They are UnitTypes::Special_Zerg_Flag_Beacon,
	 * UnitTypes::Special_Terran_Flag_Beacon, and
	 * UnitTypes::Special_Protoss_Flag_Beacon. Flag beacons spawn a Flag after
	 * some ARBITRARY I FORGOT AMOUNT OF FRAMES. See also isBeacon Returns true
	 * if this unit type is one of the three race flag beacons, and false
	 * otherwise.
	 */
	public boolean isFlagBeacon() {
		return isFlagBeacon_native(pointer);
	}

	/**
	 * Checks if this structure is special and cannot be obtained normally
	 * within the game. Returns true if this structure is a special building,
	 * and false otherwise. Note If this function returns a successful state,
	 * then the following function calls will also return a successful state:
	 * isBuilding()
	 */
	public boolean isSpecialBuilding() {
		return isSpecialBuilding_native(pointer);
	}

	/**
	 * Identifies if this unit type is used to complement some abilities. These
	 * include UnitTypes::Spell_Dark_Swarm, UnitTypes::Spell_Disruption_Web, and
	 * UnitTypes::Spell_Scanner_Sweep, which correspond to
	 * TechTypes::Dark_Swarm, TechTypes::Disruption_Web, and
	 * TechTypes::Scanner_Sweep respectively. Returns true if this unit type is
	 * used for an ability, and false otherwise.
	 */
	public boolean isSpell() {
		return isSpell_native(pointer);
	}

	/**
	 * Checks if this structure type produces creep. That is, the unit type
	 * spreads creep over a wide area so that Zerg structures can be placed on
	 * it. Returns true if this unit type spreads creep. Note If this function
	 * returns a successful state, then the following function calls will also
	 * return a successful state: getRace() == Races::Zerg, isBuilding() Since
	 * 4.1.2
	 */
	public boolean producesCreep() {
		return producesCreep_native(pointer);
	}

	/**
	 * Checks if this unit type produces larva. This is essentially used to
	 * check if the unit type is a Hatchery, Lair, or Hive. Returns true if this
	 * unit type produces larva. Note If this function returns a successful
	 * state, then the following function calls will also return a successful
	 * state: getRace() == Races::Zerg, isBuilding()
	 */
	public boolean producesLarva() {
		return producesLarva_native(pointer);
	}

	/**
	 * Checks if this unit type is a mineral field and contains a resource
	 * amount. This indicates that the unit type is either
	 * UnitTypes::Resource_Mineral_Field,
	 * UnitTypes::Resource_Mineral_Field_Type_2, or
	 * UnitTypes::Resource_Mineral_Field_Type_3. Returns true if this unit type
	 * is a mineral field resource.
	 */
	public boolean isMineralField() {
		return isMineralField_native(pointer);
	}

	/**
	 * Checks if this unit type is a neutral critter. Returns true if this unit
	 * type is a critter, and false otherwise. Example usage: BWAPI::Position
	 * myBasePosition( BWAPI::Broodwar->self()->getStartLocation() );
	 * BWAPI::UnitSet unitsAroundTheBase =
	 * BWAPI::Broodwar->getUnitsInRadius(myBasePosition, 1024,
	 * !BWAPI::Filter::IsOwned && !BWAPI::Filter::IsParasited); for ( auto u :
	 * unitsAroundTheBase ) { if ( u->getType().isCritter() &&
	 * !u->isInvincible() ) { BWAPI::Unit myQueen =
	 * u->getClosestUnit(BWAPI::Filter::GetType == BWAPI::UnitTypes::Zerg_Queen
	 * && BWAPI::Filter::IsOwned); if ( myQueen )
	 * myQueen->useTech(BWAPI::TechTypes::Parasite, u); } }
	 */
	public boolean isCritter() {
		return isCritter_native(pointer);
	}

	/**
	 * Checks if this unit type is capable of constructing an add-on. An add-on
	 * is an extension or attachment for Terran structures, specifically the
	 * Command Center, Factory, Starport, and Science Facility. Returns true if
	 * this unit type can construct an add-on, and false if it can not. See also
	 * isAddon
	 */
	public boolean canBuildAddon() {
		return canBuildAddon_native(pointer);
	}

	/**
	 * Retrieves the set of technologies that this unit type is capable of
	 * researching. Note Some maps have special parameters that disable certain
	 * technologies. Use PlayerInterface::isResearchAvailable to determine if a
	 * technology is actually available in the current game for a specific
	 * player. Returns TechType::set containing the technology types that can be
	 * researched. See also PlayerInterface::isResearchAvailable Since 4.1.2
	 */
	public List<TechType> researchesWhat() {
		return researchesWhat_native(pointer);
	}

	/**
	 * Retrieves the set of upgrades that this unit type is capable of
	 * upgrading. Note Some maps have special upgrade limitations. Use
	 * PlayerInterface::getMaxUpgradeLevel to check if an upgrade is available.
	 * Returns UpgradeType::set containing the upgrade types that can be
	 * upgraded. See also PlayerInterface::getMaxUpgradeLevel Since 4.1.2
	 */
	public List<UpgradeType> upgradesWhat() {
		return upgradesWhat_native(pointer);
	}

	private long pointer;

	private native String toString_native(long pointer);

	private native Race getRace_native(long pointer);

	private native Pair<UnitType, Integer> whatBuilds_native(long pointer);

	private native Map<UnitType, Integer> requiredUnits_native(long pointer);

	private native TechType requiredTech_native(long pointer);

	private native TechType cloakingTech_native(long pointer);

	private native List<TechType> abilities_native(long pointer);

	private native List<UpgradeType> upgrades_native(long pointer);

	private native UpgradeType armorUpgrade_native(long pointer);

	private native int maxHitPoints_native(long pointer);

	private native int maxShields_native(long pointer);

	private native int maxEnergy_native(long pointer);

	private native int armor_native(long pointer);

	private native int mineralPrice_native(long pointer);

	private native int gasPrice_native(long pointer);

	private native int buildTime_native(long pointer);

	private native int supplyRequired_native(long pointer);

	private native int supplyProvided_native(long pointer);

	private native int spaceRequired_native(long pointer);

	private native int spaceProvided_native(long pointer);

	private native int buildScore_native(long pointer);

	private native int destroyScore_native(long pointer);

	private native UnitSizeType size_native(long pointer);

	private native int tileWidth_native(long pointer);

	private native int tileHeight_native(long pointer);

	private native TilePosition tileSize_native(long pointer);

	private native int dimensionLeft_native(long pointer);

	private native int dimensionUp_native(long pointer);

	private native int dimensionRight_native(long pointer);

	private native int dimensionDown_native(long pointer);

	private native int width_native(long pointer);

	private native int height_native(long pointer);

	private native int seekRange_native(long pointer);

	private native int sightRange_native(long pointer);

	private native WeaponType groundWeapon_native(long pointer);

	private native int maxGroundHits_native(long pointer);

	private native WeaponType airWeapon_native(long pointer);

	private native int maxAirHits_native(long pointer);

	private native double topSpeed_native(long pointer);

	private native int acceleration_native(long pointer);

	private native int haltDistance_native(long pointer);

	private native int turnRadius_native(long pointer);

	private native boolean canProduce_native(long pointer);

	private native boolean canAttack_native(long pointer);

	private native boolean canMove_native(long pointer);

	private native boolean isFlyer_native(long pointer);

	private native boolean regeneratesHP_native(long pointer);

	private native boolean isSpellcaster_native(long pointer);

	private native boolean hasPermanentCloak_native(long pointer);

	private native boolean isInvincible_native(long pointer);

	private native boolean isOrganic_native(long pointer);

	private native boolean isMechanical_native(long pointer);

	private native boolean isRobotic_native(long pointer);

	private native boolean isDetector_native(long pointer);

	private native boolean isResourceContainer_native(long pointer);

	private native boolean isResourceDepot_native(long pointer);

	private native boolean isRefinery_native(long pointer);

	private native boolean isWorker_native(long pointer);

	private native boolean requiresPsi_native(long pointer);

	private native boolean requiresCreep_native(long pointer);

	private native boolean isTwoUnitsInOneEgg_native(long pointer);

	private native boolean isBurrowable_native(long pointer);

	private native boolean isCloakable_native(long pointer);

	private native boolean isBuilding_native(long pointer);

	private native boolean isAddon_native(long pointer);

	private native boolean isFlyingBuilding_native(long pointer);

	private native boolean isNeutral_native(long pointer);

	private native boolean isHero_native(long pointer);

	private native boolean isPowerup_native(long pointer);

	private native boolean isBeacon_native(long pointer);

	private native boolean isFlagBeacon_native(long pointer);

	private native boolean isSpecialBuilding_native(long pointer);

	private native boolean isSpell_native(long pointer);

	private native boolean producesCreep_native(long pointer);

	private native boolean producesLarva_native(long pointer);

	private native boolean isMineralField_native(long pointer);

	private native boolean isCritter_native(long pointer);

	private native boolean canBuildAddon_native(long pointer);

	private native List<TechType> researchesWhat_native(long pointer);

	private native List<UpgradeType> upgradesWhat_native(long pointer);

}
