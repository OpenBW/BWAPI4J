package org.openbw.bwapi4j.type;

public enum WeaponType {

    Gauss_Rifle,
    Gauss_Rifle_Jim_Raynor,
    C_10_Canister_Rifle,
    C_10_Canister_Rifle_Sarah_Kerrigan,
    Fragmentation_Grenade,
    Fragmentation_Grenade_Jim_Raynor,
    Spider_Mines,
    Twin_Autocannons,
    Hellfire_Missile_Pack,
    Twin_Autocannons_Alan_Schezar,
    Hellfire_Missile_Pack_Alan_Schezar,
    Arclite_Cannon,
    Arclite_Cannon_Edmund_Duke,
    Fusion_Cutter,
    Gemini_Missiles,
    Burst_Lasers,
    Gemini_Missiles_Tom_Kazansky,
    Burst_Lasers_Tom_Kazansky,
    ATS_Laser_Battery,
    ATA_Laser_Battery,
    ATS_Laser_Battery_Hero,
    ATA_Laser_Battery_Hero,
    ATS_Laser_Battery_Hyperion,
    ATA_Laser_Battery_Hyperion,
    Flame_Thrower,
    Flame_Thrower_Gui_Montag,
    Arclite_Shock_Cannon,
    Arclite_Shock_Cannon_Edmund_Duke,
    Longbolt_Missile,
    Yamato_Gun,
    Nuclear_Strike,
    Lockdown,
    EMP_Shockwave,
    Irradiate,
    Claws,
    Claws_Devouring_One,
    Claws_Infested_Kerrigan,
    Needle_Spines,
    Needle_Spines_Hunter_Killer,
    Kaiser_Blades,
    Kaiser_Blades_Torrasque,
    Toxic_Spores,
    Spines,
    Acid_Spore,
    Acid_Spore_Kukulza,
    Glave_Wurm,
    Glave_Wurm_Kukulza,
    Seeker_Spores,
    Subterranean_Tentacle,
    Suicide_Infested_Terran,
    Suicide_Scourge,
    Parasite,
    Spawn_Broodlings,
    Ensnare,
    Dark_Swarm,
    Plague,
    Consume,
    Particle_Beam,
    Psi_Blades,
    Psi_Blades_Fenix,
    Phase_Disruptor,
    Phase_Disruptor_Fenix,
    Psi_Assault,
    Psionic_Shockwave,
    Psionic_Shockwave_TZ_Archon,
    Dual_Photon_Blasters,
    Anti_Matter_Missiles,
    Dual_Photon_Blasters_Mojo,
    Anti_Matter_Missiles_Mojo,
    Phase_Disruptor_Cannon,
    Phase_Disruptor_Cannon_Danimoth,
    Pulse_Cannon,
    STS_Photon_Cannon,
    STA_Photon_Cannon,
    Scarab,
    Stasis_Field,
    Psionic_Storm,
    Warp_Blades_Zeratul,
    Warp_Blades_Hero,
    Platform_Laser_Battery,
    Independant_Laser_Battery,
    Twin_Autocannons_Floor_Trap,
    Hellfire_Missile_Pack_Wall_Trap,
    Flame_Thrower_Wall_Trap,
    Hellfire_Missile_Pack_Floor_Trap,
    Neutron_Flare,
    Disruption_Web,
    Restoration,
    Halo_Rockets,
    Corrosive_Acid,
    Mind_Control,
    Feedback,
    Optical_Flare,
    Maelstrom,
    Subterranean_Spines,
    Warp_Blades,
    C_10_Canister_Rifle_Samir_Duran,
    C_10_Canister_Rifle_Infested_Duran,
    Dual_Photon_Blasters_Artanis,
    Anti_Matter_Missiles_Artanis,
    C_10_Canister_Rifle_Alexei_Stukov,
    None,
    Unknown;
    
    private int id;
    private TechType tech;
    private UnitType whatUses;
    private int damageAmount;
    private int damageBonus;
    private int damageCooldown;
    private int damageFactor;
    private UpgradeType upgradeType;
    private DamageType damageType;
    private ExplosionType explosionType;
    private int minRange;
    private int maxRange;
    private int innerSplashRadius;
    private int medianSplashRadius;
    private int outerSplashRadius;
    private boolean targetsAir;
    private boolean targetsGround;
    private boolean targetsMechanical;
    private boolean targetsOrganic;
    private boolean targetsNonBuilding;
    private boolean targetsNonRobotic;
    private boolean targetsTerrain;
    private boolean targetsOrgOrMech;
    private boolean targetsOwn;
    
    public int getId() {
        return this.id;
    }
    
    /**
     * Retrieves the technology type that must be researched before this weapon
     * can be used. Returns TechType required by this weapon. Return values
     * TechTypes::None if no tech type is required to use this weapon. See also
     * TechType::getWeapon
     */
    public TechType getTech() {
        return this.tech;
    }

    /**
     * Retrieves the unit type that is intended to use this weapon type. Note
     * There is a rare case where some hero unit types use the same weapon.
     * Returns The UnitType that uses this weapon. See also
     * UnitType::groundWeapon, UnitType::airWeapon
     */
    public UnitType whatUses() {
        return this.whatUses;
    }

    /**
     * Retrieves the base amount of damage that this weapon can deal per attack.
     * Note That this damage amount must go through a DamageType and
     * UnitSizeType filter before it is applied to a unit. Returns Amount of
     * base damage that this weapon deals.
     */
    public int damageAmount() {
        return this.damageAmount;
    }

    /**
     * Determines the bonus amount of damage that this weapon type increases by
     * for every upgrade to this type. See also upgradeType Returns Amount of
     * damage added for every weapon upgrade.
     */
    public int damageBonus() {
        return this.damageBonus;
    }

    /**
     * Retrieves the base amount of cooldown time between each attack, in
     * frames. Returns The amount of base cooldown applied to the unit after an
     * attack. See also UnitInterface::getGroundWeaponCooldown,
     * UnitInterface::getAirWeaponCooldown
     */
    public int damageCooldown() {
        return this.damageCooldown;
    }

    /**
     * Obtains the intended number of missiles/attacks that are used. This is
     * used to multiply with the damage amount to obtain the full amount of
     * damage for an attack. Returns The damage factor multiplied by the amount
     * to obtain the total damage. See also damageAmount
     */
    public int damageFactor() {
        return this.damageFactor;
    }

    /**
     * Retrieves the upgrade type that increases this weapon's damage output.
     * Returns The UpgradeType used to upgrade this weapon's damage. See also
     * damageBonus
     */
    public UpgradeType upgradeType() {
        return this.upgradeType;
    }

    /**
     * Retrieves the damage type that this weapon applies to a unit type.
     * Returns DamageType used for damage calculation. See also DamageType,
     * UnitSizeType
     */
    public DamageType damageType() {
        return this.damageType;
    }

    /**
     * Retrieves the explosion type that indicates how the weapon deals damage.
     * Returns ExplosionType identifying how damage is applied to a target
     * location.
     */
    public ExplosionType explosionType() {
        return this.explosionType;
    }

    /**
     * Retrieves the minimum attack range of the weapon, measured in pixels.
     * This value is 0 for almost all weapon types, except for
     * WeaponTypes::Arclite_Shock_Cannon and
     * WeaponTypes::Arclite_Shock_Cannon_Edmund_Duke. Returns Minimum attack
     * range, in pixels.
     */
    public int minRange() {
        return this.minRange;
    }

    /**
     * Retrieves the maximum attack range of the weapon, measured in pixels.
     * Returns Maximum attack range, in pixels.
     */
    public int maxRange() {
        return this.maxRange;
    }

    /**
     * Retrieves the inner radius used for splash damage calculations, in
     * pixels. Returns Radius of the inner splash area, in pixels.
     */
    public int innerSplashRadius() {
        return this.innerSplashRadius;
    }

    /**
     * Retrieves the middle radius used for splash damage calculations, in
     * pixels. Returns Radius of the middle splash area, in pixels.
     */
    public int medianSplashRadius() {
        return this.medianSplashRadius;
    }

    /**
     * Retrieves the outer radius used for splash damage calculations, in
     * pixels. Returns Radius of the outer splash area, in pixels.
     */
    public int outerSplashRadius() {
        return this.outerSplashRadius;
    }

    /**
     * Checks if this weapon type can target air units. Returns true if this
     * weapon type can target air units, and false otherwise. See also
     * UnitInterface::isFlying, UnitType::isFlyer
     */
    public boolean targetsAir() {
        return this.targetsAir;
    }

    /**
     * Checks if this weapon type can target ground units. Returns true if this
     * weapon type can target ground units, and false otherwise. See also
     * UnitInterface::isFlying, UnitType::isFlyer
     */
    public boolean targetsGround() {
        return this.targetsGround;
    }

    /**
     * Checks if this weapon type can only target mechanical units. Returns true
     * if this weapon type can only target mechanical units, and false
     * otherwise. See also targetsOrgOrMech, UnitType::isMechanical
     */
    public boolean targetsMechanical() {
        return this.targetsMechanical;
    }

    /**
     * Checks if this weapon type can only target organic units. Returns true if
     * this weapon type can only target organic units, and false otherwise. See
     * also targetsOrgOrMech, UnitType::isOrganic
     */
    public boolean targetsOrganic() {
        return this.targetsOrganic;
    }

    /**
     * Checks if this weapon type cannot target structures. Returns true if this
     * weapon type cannot target buildings, and false if it can. See also
     * UnitType::isBuilding
     */
    public boolean targetsNonBuilding() {
        return this.targetsNonBuilding;
    }

    /**
     * Checks if this weapon type cannot target robotic units. Returns true if
     * this weapon type cannot target robotic units, and false if it can. See
     * also UnitType::isRobotic
     */
    public boolean targetsNonRobotic() {
        return this.targetsNonRobotic;
    }

    /**
     * Checks if this weapon type can target the ground. Note This is more for
     * attacks like Psionic Storm which can target a location, not to be
     * confused with attack move. Returns true if this weapon type can target a
     * location, and false otherwise.
     */
    public boolean targetsTerrain() {
        return this.targetsTerrain;
    }

    /**
     * Checks if this weapon type can only target organic or mechanical units.
     * Returns true if this weapon type can only target organic or mechanical
     * units, and false otherwise. See also targetsOrganic, targetsMechanical,
     * UnitType::isOrganic, UnitType::isMechanical
     */
    public boolean targetsOrgOrMech() {
        return this.targetsOrgOrMech;
    }

    /**
     * Checks if this weapon type can only target units owned by the same
     * player. This is used for WeaponTypes::Consume. Returns true if this
     * weapon type can only target your own units, and false otherwise. See also
     * UnitInterface::getPlayer
     */
    public boolean targetsOwn() {
        return this.targetsOwn;
    }
}
