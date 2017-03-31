package org.openbw.bwapi4j.type;

public enum UpgradeType {

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
    U_238_Shells,
    Ion_Thrusters,
    Titan_Reactor,
    Ocular_Implants,
    Moebius_Reactor,
    Apollo_Reactor,
    Colossus_Reactor,
    Ventral_Sacs,
    Antennae,
    Pneumatized_Carapace,
    Metabolic_Boost,
    Adrenal_Glands,
    Muscular_Augments,
    Grooved_Spines,
    Gamete_Meiosis,
    Metasynaptic_Node,
    Singularity_Charge,
    Leg_Enhancements,
    Scarab_Damage,
    Reaver_Capacity,
    Gravitic_Drive,
    Sensor_Array,
    Gravitic_Boosters,
    Khaydarin_Amulet,
    Apial_Sensors,
    Gravitic_Thrusters,
    Carrier_Capacity,
    Khaydarin_Core,
    Argus_Jewel,
    Argus_Talisman,
    Caduceus_Reactor,
    Chitinous_Plating,
    Anabolic_Synthesis,
    Charon_Boosters,
    Upgrade_60,
    None;
	
	private Race race;
	private int[] mineralPrices;
	private int mineralPriceFactor;
	private int[] gasPrices;
	private int gasPriceFactor;
	private int[] upgradeTimes;
	private int upgradeTimeFactor;
	private int maxRepeats;
	private UnitType whatUpgrades;
	private UnitType[] whatsRequired;

	/**
	 * Retrieves the race the upgrade is for. For example,
	 * UpgradeTypes::Terran_Infantry_Armor.getRace() will return Races::Terran.
	 * Returns Race that this upgrade belongs to.
	 */
	public Race getRace() {
		return this.race;
	}

	/**
	 * Returns the mineral price for the upgrade. Parameters level (optional)
	 * The next upgrade level. Note Upgrades start at level 0. Returns The
	 * mineral cost of the upgrade for the given level.
	 */
	public int mineralPrice(int level) {
		return this.mineralPrices[level];
	}

	/**
	 * The amount that the mineral price increases for each additional upgrade.
	 * Returns The mineral cost added to the upgrade after each level.
	 */
	public int mineralPriceFactor() {
		return this.mineralPriceFactor;
	}

	/**
	 * Returns the vespene gas price for the first upgrade. Parameters level
	 * (optional) The next upgrade level. Note Upgrades start at level 0.
	 * Returns The gas cost of the upgrade for the given level.
	 */
	public int gasPrice(int level) {
		return this.gasPrices[level];
	}

	/**
	 * Returns the amount that the vespene gas price increases for each
	 * additional upgrade. Returns The gas cost added to the upgrade after each
	 * level.
	 */
	public int gasPriceFactor() {
		return this.gasPriceFactor;
	}

	/**
	 * Returns the number of frames needed to research the first upgrade.
	 * Parameters level (optional) The next upgrade level. Note Upgrades start
	 * at level 0. Returns The time cost of the upgrade for the given level.
	 */
	public int upgradeTime(int level) {
		return this.upgradeTimes[level];
	}

	/**
	 * Returns the number of frames that the upgrade time increases for each
	 * additional upgrade. Returns The time cost added to the upgrade after each
	 * level.
	 */
	public int upgradeTimeFactor() {
		return this.upgradeTimeFactor;
	}

	/**
	 * Returns the maximum number of times the upgrade can be researched.
	 * Returns Maximum number of times this upgrade can be upgraded.
	 */
	public int maxRepeats() {
		return this.maxRepeats;
	}

	/**
	 * Returns the type of unit that researches the upgrade. Returns The
	 * UnitType that is used to upgrade this type.
	 */
	public UnitType whatUpgrades() {
		return this.whatUpgrades;
	}

	/**
	 * Returns the type of unit that is required for the upgrade. The player
	 * must have at least one of these units completed in order to start
	 * upgrading this upgrade. Parameters level (optional) The next upgrade
	 * level. Note Upgrades start at level 0. Returns UnitType required to
	 * obtain this upgrade.
	 */
	public UnitType whatsRequired(int level) {
		return this.whatsRequired[level];
	}
}
