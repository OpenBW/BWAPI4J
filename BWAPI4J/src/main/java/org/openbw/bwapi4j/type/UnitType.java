package org.openbw.bwapi4j.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.util.Pair;

public enum UnitType {

    Terran_Marine,
    Terran_Ghost,
    Terran_Vulture,
    Terran_Goliath,
    Terran_Goliath_Turret,
    Terran_Siege_Tank_Tank_Mode,
    Terran_Siege_Tank_Tank_Mode_Turret,
    Terran_SCV,
    Terran_Wraith,
    Terran_Science_Vessel,
    Hero_Gui_Montag,
    Terran_Dropship,
    Terran_Battlecruiser,
    Terran_Vulture_Spider_Mine,
    Terran_Nuclear_Missile,
    Terran_Civilian,
    Hero_Sarah_Kerrigan,
    Hero_Alan_Schezar,
    Hero_Alan_Schezar_Turret,
    Hero_Jim_Raynor_Vulture,
    Hero_Jim_Raynor_Marine,
    Hero_Tom_Kazansky,
    Hero_Magellan,
    Hero_Edmund_Duke_Tank_Mode,
    Hero_Edmund_Duke_Tank_Mode_Turret,
    Hero_Edmund_Duke_Siege_Mode,
    Hero_Edmund_Duke_Siege_Mode_Turret,
    Hero_Arcturus_Mengsk,
    Hero_Hyperion,
    Hero_Norad_II,
    Terran_Siege_Tank_Siege_Mode,
    Terran_Siege_Tank_Siege_Mode_Turret,
    Terran_Firebat,
    Spell_Scanner_Sweep,
    Terran_Medic,
    Zerg_Larva,
    Zerg_Egg,
    Zerg_Zergling,
    Zerg_Hydralisk,
    Zerg_Ultralisk,
    Zerg_Broodling,
    Zerg_Drone,
    Zerg_Overlord,
    Zerg_Mutalisk,
    Zerg_Guardian,
    Zerg_Queen,
    Zerg_Defiler,
    Zerg_Scourge,
    Hero_Torrasque,
    Hero_Matriarch,
    Zerg_Infested_Terran,
    Hero_Infested_Kerrigan,
    Hero_Unclean_One,
    Hero_Hunter_Killer,
    Hero_Devouring_One,
    Hero_Kukulza_Mutalisk,
    Hero_Kukulza_Guardian,
    Hero_Yggdrasill,
    Terran_Valkyrie,
    Zerg_Cocoon,
    Protoss_Corsair,
    Protoss_Dark_Templar,
    Zerg_Devourer,
    Protoss_Dark_Archon,
    Protoss_Probe,
    Protoss_Zealot,
    Protoss_Dragoon,
    Protoss_High_Templar,
    Protoss_Archon,
    Protoss_Shuttle,
    Protoss_Scout,
    Protoss_Arbiter,
    Protoss_Carrier,
    Protoss_Interceptor,
    Hero_Dark_Templar,
    Hero_Zeratul,
    Hero_Tassadar_Zeratul_Archon,
    Hero_Fenix_Zealot,
    Hero_Fenix_Dragoon,
    Hero_Tassadar,
    Hero_Mojo,
    Hero_Warbringer,
    Hero_Gantrithor,
    Protoss_Reaver,
    Protoss_Observer,
    Protoss_Scarab,
    Hero_Danimoth,
    Hero_Aldaris,
    Hero_Artanis,
    Critter_Rhynadon,
    Critter_Bengalaas,
    Special_Cargo_Ship,
    Special_Mercenary_Gunship,
    Critter_Scantid,
    Critter_Kakaru,
    Critter_Ragnasaur,
    Critter_Ursadon,
    Zerg_Lurker_Egg,
    Hero_Raszagal,
    Hero_Samir_Duran,
    Hero_Alexei_Stukov,
    Special_Map_Revealer,
    Hero_Gerard_DuGalle,
    Zerg_Lurker,
    Hero_Infested_Duran,
    Spell_Disruption_Web,
    Terran_Command_Center,
    Terran_Comsat_Station,
    Terran_Nuclear_Silo,
    Terran_Supply_Depot,
    Terran_Refinery,
    Terran_Barracks,
    Terran_Academy,
    Terran_Factory,
    Terran_Starport,
    Terran_Control_Tower,
    Terran_Science_Facility,
    Terran_Covert_Ops,
    Terran_Physics_Lab,
    Unused_Terran1,
    Terran_Machine_Shop,
    Unused_Terran2,
    Terran_Engineering_Bay,
    Terran_Armory,
    Terran_Missile_Turret,
    Terran_Bunker,
    Special_Crashed_Norad_II,
    Special_Ion_Cannon,
    Powerup_Uraj_Crystal,
    Powerup_Khalis_Crystal,
    Zerg_Infested_Command_Center,
    Zerg_Hatchery,
    Zerg_Lair,
    Zerg_Hive,
    Zerg_Nydus_Canal,
    Zerg_Hydralisk_Den,
    Zerg_Defiler_Mound,
    Zerg_Greater_Spire,
    Zerg_Queens_Nest,
    Zerg_Evolution_Chamber,
    Zerg_Ultralisk_Cavern,
    Zerg_Spire,
    Zerg_Spawning_Pool,
    Zerg_Creep_Colony,
    Zerg_Spore_Colony,
    Unused_Zerg1,
    Zerg_Sunken_Colony,
    Special_Overmind_With_Shell,
    Special_Overmind,
    Zerg_Extractor,
    Special_Mature_Chrysalis,
    Special_Cerebrate,
    Special_Cerebrate_Daggoth,
    Unused_Zerg2,
    Protoss_Nexus,
    Protoss_Robotics_Facility,
    Protoss_Pylon,
    Protoss_Assimilator,
    Unused_Protoss1,
    Protoss_Observatory,
    Protoss_Gateway,
    Unused_Protoss2,
    Protoss_Photon_Cannon,
    Protoss_Citadel_of_Adun,
    Protoss_Cybernetics_Core,
    Protoss_Templar_Archives,
    Protoss_Forge,
    Protoss_Stargate,
    Special_Stasis_Cell_Prison,
    Protoss_Fleet_Beacon,
    Protoss_Arbiter_Tribunal,
    Protoss_Robotics_Support_Bay,
    Protoss_Shield_Battery,
    Special_Khaydarin_Crystal_Form,
    Special_Protoss_Temple,
    Special_XelNaga_Temple,
    Resource_Mineral_Field,
    Resource_Mineral_Field_Type_2,
    Resource_Mineral_Field_Type_3,
    Unused_Cave,
    Unused_Cave_In,
    Unused_Cantina,
    Unused_Mining_Platform,
    Unused_Independant_Command_Center,
    Special_Independant_Starport,
    Unused_Independant_Jump_Gate,
    Unused_Ruins,
    Unused_Khaydarin_Crystal_Formation,
    Resource_Vespene_Geyser,
    Special_Warp_Gate,
    Special_Psi_Disrupter,
    Unused_Zerg_Marker,
    Unused_Terran_Marker,
    Unused_Protoss_Marker,
    Special_Zerg_Beacon,
    Special_Terran_Beacon,
    Special_Protoss_Beacon,
    Special_Zerg_Flag_Beacon,
    Special_Terran_Flag_Beacon,
    Special_Protoss_Flag_Beacon,
    Special_Power_Generator,
    Special_Overmind_Cocoon,
    Spell_Dark_Swarm,
    Special_Floor_Missile_Trap,
    Special_Floor_Hatch,
    Special_Upper_Level_Door,
    Special_Right_Upper_Level_Door,
    Special_Pit_Door,
    Special_Right_Pit_Door,
    Special_Floor_Gun_Trap,
    Special_Wall_Missile_Trap,
    Special_Wall_Flame_Trap,
    Special_Right_Wall_Missile_Trap,
    Special_Right_Wall_Flame_Trap,
    Special_Start_Location,
    Powerup_Flag,
    Powerup_Young_Chrysalis,
    Powerup_Psi_Emitter,
    Powerup_Data_Disk,
    Powerup_Khaydarin_Crystal,
    Powerup_Mineral_Cluster_Type_1,
    Powerup_Mineral_Cluster_Type_2,
    Powerup_Protoss_Gas_Orb_Type_1,
    Powerup_Protoss_Gas_Orb_Type_2,
    Powerup_Zerg_Gas_Sac_Type_1,
    Powerup_Zerg_Gas_Sac_Type_2,
    Powerup_Terran_Gas_Tank_Type_1,
    Powerup_Terran_Gas_Tank_Type_2,
    None,
    AllUnits,
    Men,
    Buildings,
    Factories,
    Unknown;

    private int id;
    private Race race;
    private Pair<UnitType, Integer> whatBuilds;
    private HashMap<UnitType, Integer> requiredUnits;
    private TechType requiredTech;
    private TechType cloakingTech;
    private ArrayList<TechType> abilities;
    private ArrayList<UpgradeType> upgrades;
    private UpgradeType armorUpgrade;
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
    private UnitSizeType size;
    private int tileWidth;
    private int tileHeight;
    private int dimensionLeft;
    private int dimensionUp;
    private int dimensionRight;
    private int dimensionDown;
    private int width;
    private int height;
    private int seekRange;
    private int sightRange;
    private WeaponType groundWeapon;
    private int maxGroundHits;
    private WeaponType airWeapon;
    private int maxAirHits;
    private double topSpeed;
    private int acceleration;
    private int haltDistance;
    private int turnRadius;
    private boolean canProduce;
    private boolean canAttack;
    private boolean canMove;
    private boolean isFlyer;
    private boolean regeneratesHP;
    private boolean isSpellcaster;
    private boolean hasPermanentCloak;
    private boolean isInvincible;
    private boolean isOrganic;
    private boolean isMechanical;
    private boolean isRobotic;
    private boolean isDetector;
    private boolean isResourceContainer;
    private boolean isResourceDepot;
    private boolean isRefinery;
    private boolean isWorker;
    private boolean requiresPsi;
    private boolean requiresCreep;
    private boolean isTwoUnitsInOneEgg;
    private boolean isBurrowable;
    private boolean isCloakable;
    private boolean isBuilding;
    private boolean isAddon;
    private boolean isFlyingBuilding;
    private boolean isNeutral;
    private boolean isHero;
    private boolean isPowerup;
    private boolean isBeacon;
    private boolean isFlagBeacon;
    private boolean isSpecialBuilding;
    private boolean isSpell;
    private boolean producesCreep;
    private boolean producesLarva;
    private boolean isMineralField;
    private boolean isCritter;
    private boolean canBuildAddon;
    private ArrayList<TechType> researchesWhat;
    private ArrayList<UpgradeType> upgradesWhat;

    private UnitType() {

        // this.whatBuilds will be created via JNI
        this.requiredUnits = new HashMap<UnitType, Integer>();
        this.abilities = new ArrayList<TechType>();
        this.upgrades = new ArrayList<UpgradeType>();
        this.researchesWhat = new ArrayList<TechType>();
        this.upgradesWhat = new ArrayList<UpgradeType>();
    }

    public int getId() {
        return this.id;
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
        return this.whatBuilds;
    }

    /**
     * Retrieves the immediate technology tree requirements to make this unit
     * type. Returns std::map containing a UnitType to number mapping of
     * UnitTypes required.
     */
    public Map<UnitType, Integer> requiredUnits() {
        return this.requiredUnits;
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
        return this.requiredTech;
    }

    /**
     * Retrieves the cloaking technology associated with certain units. Returns
     * TechType referring to the cloaking technology that this unit type uses as
     * an ability. Return values TechTypes::None If this unit type does not have
     * an active cloak ability.
     */
    public TechType cloakingTech() {
        return this.cloakingTech;
    }

    /**
     * Retrieves the set of abilities that this unit can use, provided it is
     * available to you in the game. Returns Set of TechTypes containing ability
     * information.
     */
    public List<TechType> abilities() {
        return this.abilities;
    }

    /**
     * Retrieves the set of upgrades that this unit can use to enhance its
     * fighting ability. Returns Set of UpgradeTypes containing upgrade types
     * that will impact this unit type.
     */
    public List<UpgradeType> upgrades() {
        return this.upgrades;
    }

    /**
     * Retrieves the upgrade type used to increase the armor of this unit type.
     * For each upgrade, this unit type gains +1 additional armor. Returns
     * UpgradeType indicating the upgrade that increases this unit type's armor
     * amount.
     */
    public UpgradeType armorUpgrade() {
        return this.armorUpgrade;
    }

    /**
     * Retrieves the default maximum amount of hit points that this unit type
     * can have. Note This value may not necessarily match the value seen in the
     * Use Map Settings game type. Returns Integer indicating the maximum amount
     * of hit points for this unit type.
     */
    public int maxHitPoints() {
        return this.maxHitPoints;
    }

    /**
     * Retrieves the default maximum amount of shield points that this unit type
     * can have. Note This value may not necessarily match the value seen in the
     * Use Map Settings game type. Returns Integer indicating the maximum amount
     * of shield points for this unit type. Return values 0 If this unit type
     * does not have shields.
     */
    public int maxShields() {
        return this.maxShields;
    }

    /**
     * Retrieves the maximum amount of energy this unit type can have by
     * default. Returns Integer indicating the maximum amount of energy for this
     * unit type. Return values 0 If this unit does not gain energy for
     * abilities.
     */
    public int maxEnergy() {
        return this.maxEnergy;
    }

    /**
     * Retrieves the default amount of armor that the unit type starts with,
     * excluding upgrades. Note This value may not necessarily match the value
     * seen in the Use Map Settings game type. Returns The amount of armor the
     * unit type has.
     */
    public int armor() {
        return this.armor;
    }

    /**
     * Retrieves the default mineral price of purchasing the unit. Note This
     * value may not necessarily match the value seen in the Use Map Settings
     * game type. Returns Mineral cost of the unit.
     */
    public int mineralPrice() {
        return this.mineralPrice;
    }

    /**
     * Retrieves the default vespene gas price of purchasing the unit. Note This
     * value may not necessarily match the value seen in the Use Map Settings
     * game type. Returns Vespene gas cost of the unit.
     */
    public int gasPrice() {
        return this.gasPrice;
    }

    /**
     * Retrieves the default time, in frames, needed to train, morph, or build
     * the unit. Note This value may not necessarily match the value seen in the
     * Use Map Settings game type. Returns Number of frames needed in order to
     * build the unit. See also UnitInterface::getRemainingBuildTime
     */
    public int buildTime() {
        return this.buildTime;
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
        return this.supplyRequired;
    }

    /**
     * Retrieves the amount of supply that this unit type produces for its
     * appropriate Race's supply pool. Note In Starcraft programming, the
     * managed supply values are double than what they appear in the game. The
     * reason for this is because Zerglings use 0.5 visible supply. See also
     * supplyRequired, PlayerInterface::supplyTotal, PlayerInterface::supplyUsed
     */
    public int supplyProvided() {
        return this.supplyProvided;
    }

    /**
     * Retrieves the amount of space required by this unit type to fit inside a
     * Bunker or Transport(Dropship, Shuttle, Overlord ). Returns Amount of
     * space required by this unit type for transport. return values 255 If this
     * unit type can not be transported. See also spaceProvided
     */
    public int spaceRequired() {
        return this.spaceRequired;
    }

    /**
     * Retrieves the amount of space provided by this Bunker or
     * Transport(Dropship, Shuttle, Overlord ) for unit transportation. Returns
     * The number of slots provided by this unit type. See also spaceRequired
     */
    public int spaceProvided() {
        return this.spaceProvided;
    }

    /**
     * Retrieves the amount of score points awarded for constructing this unit
     * type. This value is used for calculating scores in the post-game score
     * screen. Returns Number of points awarded for constructing this unit type.
     * See also destroyScore
     */
    public int buildScore() {
        return this.buildScore;
    }

    /**
     * Retrieves the amount of score points awarded for killing this unit type.
     * This value is used for calculating scores in the post-game score screen.
     * Returns Number of points awarded for killing this unit type. See also
     * buildScore
     */
    public int destroyScore() {
        return this.destroyScore;
    }

    /**
     * Retrieves the UnitSizeType of this unit, which is used in calculations
     * along with weapon damage types to determine the amount of damage that
     * will be dealt to this type. Returns UnitSizeType indicating the
     * conceptual size of the unit type. See also WeaponType::damageType
     */
    public UnitSizeType size() {
        return this.size;
    }

    /**
     * Retrieves the width of this unit type, in tiles. Used for determining the
     * tile size of structures. Returns Width of this unit type, in tiles.
     */
    public int tileWidth() {
        return this.tileWidth;
    }

    /**
     * Retrieves the height of this unit type, in tiles. Used for determining
     * the tile size of structures. Returns Height of this unit type, in tiles.
     */
    public int tileHeight() {
        return this.tileHeight;
    }

    /**
     * Retrieves the tile size of this unit type. Used for determining the tile
     * size of structures. Returns TilePosition containing the width (x) and
     * height (y) of the unit type, in tiles.
     */
    public TilePosition tileSize() {
        return new TilePosition(this.tileWidth, this.tileHeight);
    }

    /**
     * Retrieves the distance from the center of the unit type to its left edge.
     * Returns Distance to this unit type's left edge from its center, in
     * pixels.
     */
    public int dimensionLeft() {
        return this.dimensionLeft;
    }

    /**
     * Retrieves the distance from the center of the unit type to its top edge.
     * Returns Distance to this unit type's top edge from its center, in pixels.
     */
    public int dimensionUp() {
        return this.dimensionUp;
    }

    /**
     * Retrieves the distance from the center of the unit type to its right
     * edge. Returns Distance to this unit type's right edge from its center, in
     * pixels.
     */
    public int dimensionRight() {
        return this.dimensionRight;
    }

    /**
     * Retrieves the distance from the center of the unit type to its bottom
     * edge. Returns Distance to this unit type's bottom edge from its center,
     * in pixels.
     */
    public int dimensionDown() {
        return this.dimensionDown;
    }

    /**
     * A macro for retrieving the width of the unit type, which is calculated
     * using dimensionLeft + dimensionRight + 1. Returns Width of the unit, in
     * pixels.
     */
    public int width() {
        return this.width;
    }

    /**
     * A macro for retrieving the height of the unit type, which is calculated
     * using dimensionUp + dimensionDown + 1. Returns Height of the unit, in
     * pixels.
     */
    public int height() {
        return this.height;
    }

    /**
     * Retrieves the range at which this unit type will start targeting enemy
     * units. Returns Distance at which this unit type begins to seek out enemy
     * units, in pixels.
     */
    public int seekRange() {
        return this.seekRange;
    }

    /**
     * Retrieves the sight range of this unit type. Returns Sight range of this
     * unit type, measured in pixels.
     */
    public int sightRange() {
        return this.sightRange;
    }

    /**
     * Retrieves this unit type's weapon type used when attacking targets on the
     * ground. Returns WeaponType used as this unit type's ground weapon. See
     * also maxGroundHits, airWeapon
     */
    public WeaponType groundWeapon() {
        return this.groundWeapon;
    }

    /**
     * Retrieves the maximum number of hits this unit can deal to a ground
     * target using its ground weapon. This value is multiplied by the ground
     * weapon's damage to calculate the unit type's damage potential. Returns
     * Maximum number of hits given to ground targets. See also groundWeapon,
     * maxAirHits
     */
    public int maxGroundHits() {
        return this.maxGroundHits;
    }

    /**
     * Retrieves this unit type's weapon type used when attacking targets in the
     * air. Returns WeaponType used as this unit type's air weapon. See also
     * maxAirHits, groundWeapon
     */
    public WeaponType airWeapon() {
        return this.airWeapon;
    }

    /**
     * Retrieves the maximum number of hits this unit can deal to a flying
     * target using its air weapon. This value is multiplied by the air weapon's
     * damage to calculate the unit type's damage potential. Returns Maximum
     * number of hits given to air targets. See also airWeapon, maxGroundHits
     */
    public int maxAirHits() {
        return this.maxAirHits;
    }

    /**
     * Retrieves this unit type's top movement speed with no upgrades. Note That
     * some units have inconsistent movement and this value is sometimes an
     * approximation. Returns The approximate top speed, in pixels per frame, as
     * a double. For liftable Terran structures, this function returns their
     * movement speed while lifted.
     */
    public double topSpeed() {
        return this.topSpeed;
    }

    /**
     * Retrieves the unit's acceleration amount. Returns How fast the unit can
     * accelerate to its top speed.
     */
    public int acceleration() {
        return this.acceleration;
    }

    /**
     * Retrieves the unit's halting distance. This determines how fast a unit
     * can stop moving. Returns A halting distance value.
     */
    public int haltDistance() {
        return this.haltDistance;
    }

    /**
     * Retrieves a unit's turning radius. This determines how fast a unit can
     * turn. Returns A turn radius value.
     */
    public int turnRadius() {
        return this.turnRadius;
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
        return this.canProduce;
    }

    /**
     * Checks if this unit is capable of attacking. Note This function returns
     * false for units that can only inflict damage via special abilities, such
     * as the High Templar. Returns true if this unit type is capable of
     * damaging other units with a standard attack, and false otherwise.
     */
    public boolean canAttack() {
        return this.canAttack;
    }

    /**
     * Checks if this unit type is capable of movement. Note Buildings will
     * return false, including Terran liftable buildings which are capable of
     * moving when lifted. Returns true if this unit can use a movement command,
     * and false if they cannot move.
     */
    public boolean canMove() {
        return this.canMove;
    }

    /**
     * Checks if this unit type is a flying unit. Flying units ignore ground
     * pathing and collisions. Returns true if this unit type is in the air by
     * default, and false otherwise.
     */
    public boolean isFlyer() {
        return this.isFlyer;
    }

    /**
     * Checks if this unit type can regenerate hit points. This generally
     * applies to Zerg units. Returns true if this unit type regenerates its hit
     * points, and false otherwise.
     */
    public boolean regeneratesHP() {
        return this.regeneratesHP;
    }

    /**
     * Checks if this unit type has the capacity to store energy and use it for
     * special abilities. Returns true if this unit type generates energy, and
     * false if it does not have an energy pool.
     */
    public boolean isSpellcaster() {
        return this.isSpellcaster;
    }

    /**
     * Checks if this unit type is permanently cloaked. This means the unit type
     * is always cloaked and requires a detector in order to see it. Returns
     * true if this unit type is permanently cloaked, and false otherwise.
     */
    public boolean hasPermanentCloak() {
        return this.hasPermanentCloak;
    }

    /**
     * Checks if this unit type is invincible by default. Invincible units
     * cannot take damage. Returns true if this unit type is invincible, and
     * false if it is vulnerable to attacks.
     */
    public boolean isInvincible() {
        return this.isInvincible;
    }

    /**
     * Checks if this unit is an organic unit. The organic property is required
     * for some abilities such as Heal. Returns true if this unit type has the
     * organic property, and false otherwise.
     */
    public boolean isOrganic() {
        return this.isOrganic;
    }

    /**
     * Checks if this unit is mechanical. The mechanical property is required
     * for some actions such as Repair. Returns true if this unit type has the
     * mechanical property, and false otherwise.
     */
    public boolean isMechanical() {
        return this.isMechanical;
    }

    /**
     * Checks if this unit is robotic. The robotic property is applied to
     * robotic units such as the Probe which prevents them from taking damage
     * from Irradiate. Returns true if this unit type has the robotic property,
     * and false otherwise.
     */
    public boolean isRobotic() {
        return this.isRobotic;
    }

    /**
     * Checks if this unit type is capable of detecting units that are cloaked
     * or burrowed. Returns true if this unit type is a detector by default,
     * false if it does not have this property
     */
    public boolean isDetector() {
        return this.isDetector;
    }

    /**
     * Checks if this unit type is capable of storing resources such as Mineral
     * Fields. Resources are harvested from resource containers. Returns true if
     * this unit type may contain resources that can be harvested, false
     * otherwise.
     */
    public boolean isResourceContainer() {
        return this.isResourceContainer;
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
        return this.isResourceDepot;
    }

    public boolean isRefinery() {
        return this.isRefinery;
    }

    /**
     * Checks if this unit type is a worker unit. Worker units can harvest
     * resources and build structures. Worker unit types include the SCV ,
     * Probe, and Drone. Returns true if this unit type is a worker, and false
     * if it is not.
     */
    public boolean isWorker() {
        return this.isWorker;
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
        return this.requiresPsi;
    }

    /**
     * Checks if this structure must be placed on Zerg creep. Returns true if
     * this unit type requires creep, false otherwise. Note If this function
     * returns a successful state, then the following function calls will also
     * return a successful state: isBuilding(), getRace() == Races::Zerg
     */
    public boolean requiresCreep() {
        return this.requiresCreep;
    }

    /**
     * Checks if this unit type spawns two units when being hatched from an Egg.
     * This is only applicable to Zerglings and Scourges. Returns true if
     * morphing this unit type will spawn two of them, and false if only one is
     * spawned.
     */
    public boolean isTwoUnitsInOneEgg() {
        return this.isTwoUnitsInOneEgg;
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
        return this.isBurrowable;
    }

    /**
     * Checks if this unit type has the capability to use a cloaking ability
     * when it is researched. This applies only to Wraiths and Ghosts, and does
     * not include units which are permanently cloaked. Returns true if this
     * unit has a cloaking ability, false otherwise. See also hasPermanentCloak,
     * TechTypes::Cloaking_Field, TechTypes::Personnel_Cloaking
     */
    public boolean isCloakable() {
        return this.isCloakable;
    }

    /**
     * Checks if this unit is a structure. This includes Mineral Fields and
     * Vespene Geysers. Returns true if this unit is a building, and false
     * otherwise.
     */
    public boolean isBuilding() {
        return this.isBuilding;
    }

    /**
     * Checks if this unit is an add-on. Add-ons are attachments used by some
     * Terran structures such as the Comsat Station. Returns true if this unit
     * is an add-on, and false otherwise. Note If this function returns a
     * successful state, then the following function calls will also return a
     * successful state: getRace() == Races::Terran, isBuilding()
     */
    public boolean isAddon() {
        return this.isAddon;
    }

    /**
     * Checks if this structure has the capability to use the lift-off command.
     * Returns true if this unit type is a flyable building, false otherwise.
     * Note If this function returns a successful state, then the following
     * function calls will also return a successful state: isBuilding()
     */
    public boolean isFlyingBuilding() {
        return this.isFlyingBuilding;
    }

    /**
     * Checks if this unit type is a neutral type, such as critters and
     * resources. Returns true if this unit is intended to be neutral, and false
     * otherwise.
     */
    public boolean isNeutral() {
        return this.isNeutral;
    }

    /**
     * Checks if this unit type is a hero. Heroes are types that the player
     * cannot obtain normally, and are identified by the white border around
     * their icon when selected with a group. Note There are two non-hero units
     * included in this set, the Civilian and Dark Templar Hero. Returns true if
     * this unit type is a hero type, and false otherwise.
     */
    public boolean isHero() {
        return this.isHero;
    }

    /**
     * Checks if this unit type is a powerup. Powerups can be picked up and
     * carried by workers. They are usually only seen in campaign maps and
     * Capture the Flag. Returns true if this unit type is a powerup type, and
     * false otherwise.
     */
    public boolean isPowerup() {
        return this.isPowerup;
    }

    /**
     * Checks if this unit type is a beacon. Each race has exactly one beacon
     * each. They are UnitTypes::Special_Zerg_Beacon,
     * UnitTypes::Special_Terran_Beacon, and UnitTypes::Special_Protoss_Beacon.
     * See also isFlagBeacon Returns true if this unit type is one of the three
     * race beacons, and false otherwise.
     */
    public boolean isBeacon() {
        return this.isBeacon;
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
        return this.isFlagBeacon;
    }

    /**
     * Checks if this structure is special and cannot be obtained normally
     * within the game. Returns true if this structure is a special building,
     * and false otherwise. Note If this function returns a successful state,
     * then the following function calls will also return a successful state:
     * isBuilding()
     */
    public boolean isSpecialBuilding() {
        return this.isSpecialBuilding;
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
        return this.isSpell;
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
        return this.producesCreep;
    }

    /**
     * Checks if this unit type produces larva. This is essentially used to
     * check if the unit type is a Hatchery, Lair, or Hive. Returns true if this
     * unit type produces larva. Note If this function returns a successful
     * state, then the following function calls will also return a successful
     * state: getRace() == Races::Zerg, isBuilding()
     */
    public boolean producesLarva() {
        return this.producesLarva;
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
        return this.isMineralField;
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
        return this.isCritter;
    }

    /**
     * Checks if this unit type is capable of constructing an add-on. An add-on
     * is an extension or attachment for Terran structures, specifically the
     * Command Center, Factory, Starport, and Science Facility. Returns true if
     * this unit type can construct an add-on, and false if it can not. See also
     * isAddon
     */
    public boolean canBuildAddon() {
        return this.canBuildAddon;
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
        return this.researchesWhat;
    }

    /**
     * Retrieves the set of upgrades that this unit type is capable of
     * upgrading. Note Some maps have special upgrade limitations. Use
     * PlayerInterface::getMaxUpgradeLevel to check if an upgrade is available.
     * Returns UpgradeType::set containing the upgrade types that can be
     * upgraded. See also PlayerInterface::getMaxUpgradeLevel Since 4.1.2
     */
    public List<UpgradeType> upgradesWhat() {
        return this.upgradesWhat;
    }
}
