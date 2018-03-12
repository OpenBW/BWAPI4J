package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;

public class UnitStatCalculator {

    final Player player;

    /* default */ UnitStatCalculator(Player player) {

        this.player = player;
    }

    /**
     * Retrieves the maximum amount of energy that a unit type will have,
     * taking the player's energy upgrades into consideration.
     */
    public int maxEnergy(UnitType unitType) {
        int energy = unitType.maxEnergy();
        if ((unitType == UnitType.Protoss_Arbiter       && this.player.getUpgradeLevel(UpgradeType.Khaydarin_Core)    > 0) ||
                (unitType == UnitType.Protoss_Corsair       && this.player.getUpgradeLevel(UpgradeType.Argus_Jewel)       > 0) ||
                (unitType == UnitType.Protoss_Dark_Archon   && this.player.getUpgradeLevel(UpgradeType.Argus_Talisman)    > 0) ||
                (unitType == UnitType.Protoss_High_Templar  && this.player.getUpgradeLevel(UpgradeType.Khaydarin_Amulet)  > 0) ||
                (unitType == UnitType.Terran_Ghost          && this.player.getUpgradeLevel(UpgradeType.Moebius_Reactor)   > 0) ||
                (unitType == UnitType.Terran_Battlecruiser  && this.player.getUpgradeLevel(UpgradeType.Colossus_Reactor)  > 0) ||
                (unitType == UnitType.Terran_Science_Vessel && this.player.getUpgradeLevel(UpgradeType.Titan_Reactor)     > 0) ||
                (unitType == UnitType.Terran_Wraith         && this.player.getUpgradeLevel(UpgradeType.Apollo_Reactor)    > 0) ||
                (unitType == UnitType.Terran_Medic          && this.player.getUpgradeLevel(UpgradeType.Caduceus_Reactor)  > 0) ||
                (unitType == UnitType.Zerg_Defiler          && this.player.getUpgradeLevel(UpgradeType.Metasynaptic_Node) > 0) ||
                (unitType == UnitType.Zerg_Queen            && this.player.getUpgradeLevel(UpgradeType.Gamete_Meiosis)    > 0) ) {
            energy += 50;
        }
        return energy;
    }

    /**
     * Retrieves the top speed of a unit type, taking the player's speed
     * upgrades into consideration.
     */
    public double topSpeed(UnitType unitType) {
        double speed = unitType.topSpeed();
        if ((unitType == UnitType.Terran_Vulture   && this.player.getUpgradeLevel(UpgradeType.Ion_Thrusters)        > 0) ||
                (unitType == UnitType.Zerg_Overlord    && this.player.getUpgradeLevel(UpgradeType.Pneumatized_Carapace) > 0) ||
                (unitType == UnitType.Zerg_Zergling    && this.player.getUpgradeLevel(UpgradeType.Metabolic_Boost)      > 0) ||
                (unitType == UnitType.Zerg_Hydralisk   && this.player.getUpgradeLevel(UpgradeType.Muscular_Augments)    > 0) ||
                (unitType == UnitType.Protoss_Zealot   && this.player.getUpgradeLevel(UpgradeType.Leg_Enhancements)     > 0) ||
                (unitType == UnitType.Protoss_Shuttle  && this.player.getUpgradeLevel(UpgradeType.Gravitic_Drive)       > 0) ||
                (unitType == UnitType.Protoss_Observer && this.player.getUpgradeLevel(UpgradeType.Gravitic_Boosters)    > 0) ||
                (unitType == UnitType.Protoss_Scout    && this.player.getUpgradeLevel(UpgradeType.Gravitic_Thrusters)   > 0) ||
                (unitType == UnitType.Zerg_Ultralisk   && this.player.getUpgradeLevel(UpgradeType.Anabolic_Synthesis)   > 0)) {
            if ( unitType == UnitType.Protoss_Scout )
                speed += 427.0/256.0;
            else
                speed = speed * 1.5;
            if ( speed < 853.0/256.0 )
                speed = 853.0/256.0;
        }
        return speed;
    }

    /**
     * Retrieves the maximum weapon range of a weapon type, taking the player's weapon
     * upgrades into consideration.
     */
    public int weaponMaxRange(WeaponType weaponType) {
        int range = weaponType.maxRange();
        if ( (weaponType == WeaponType.Gauss_Rifle   && this.player.getUpgradeLevel(UpgradeType.U_238_Shells)   > 0) ||
                (weaponType == WeaponType.Needle_Spines && this.player.getUpgradeLevel(UpgradeType.Grooved_Spines) > 0) )
            range += 1*32;
        else if ( weaponType == WeaponType.Phase_Disruptor       && this.player.getUpgradeLevel(UpgradeType.Singularity_Charge) > 0 )
            range += 2*32;
        else if ( weaponType == WeaponType.Hellfire_Missile_Pack && this.player.getUpgradeLevel(UpgradeType.Charon_Boosters)    > 0 )
            range += 3*32;
        return range;
    }

    /**
     * Retrieves the sight range of a unit type, taking the player's sight range
     * upgrades into consideration.
     */
    public int sightRange(UnitType unitType) {
        int range = unitType.sightRange();
        if ((unitType == UnitType.Terran_Ghost     && this.player.getUpgradeLevel(UpgradeType.Ocular_Implants) > 0) ||
                (unitType == UnitType.Zerg_Overlord    && this.player.getUpgradeLevel(UpgradeType.Antennae)        > 0) ||
                (unitType == UnitType.Protoss_Observer && this.player.getUpgradeLevel(UpgradeType.Sensor_Array)    > 0) ||
                (unitType == UnitType.Protoss_Scout    && this.player.getUpgradeLevel(UpgradeType.Apial_Sensors)   > 0))
            range = 11*32;
        return range;
    }

    /**
     * Retrieves the weapon cooldown of a unit type, taking the player's attack speed
     * upgrades into consideration.
     */
    public int weaponDamageCooldown(UnitType unitType) {
        int cooldown = unitType.groundWeapon().damageCooldown();
        if (unitType == UnitType.Zerg_Zergling && this.player.getUpgradeLevel(UpgradeType.Adrenal_Glands) > 0) {
            // Divide cooldown by 2
            cooldown /= 2;
            // Prevent cooldown from going out of bounds
            cooldown = Math.min(Math.max(cooldown,5), 250);
        }
        return cooldown;
    }

    /**
     * Calculates the armor that a given unit type will have, including upgrades.
     */
    public int armor(UnitType unitType) {
        int armor = unitType.armor();
        armor += this.player.getUpgradeLevel(unitType.armorUpgrade());
        if ( unitType == UnitType.Zerg_Ultralisk && this.player.getUpgradeLevel(UpgradeType.Chitinous_Plating) > 0 )
            armor += 2;
        else if ( unitType == UnitType.Hero_Torrasque )
            armor += 2;
        return armor;
    }

    /**
     * Calculates the damage that a given weapon type can deal, including upgrades.
     */
    public int damage(WeaponType weaponType) {
        int dmg = weaponType.damageAmount();
        dmg += this.player.getUpgradeLevel(weaponType.upgradeType()) * weaponType.damageBonus();
        dmg *= weaponType.damageFactor();
        return dmg;
    }
}
