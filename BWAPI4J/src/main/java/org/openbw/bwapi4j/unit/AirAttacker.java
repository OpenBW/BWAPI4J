package org.openbw.bwapi4j.unit;

/**
 * Units implementing this interface can attack air units.
 */
public interface AirAttacker extends Attacker {

    Weapon getAirWeapon();

    int getAirWeaponMaxRange();

    int getAirWeaponCooldown();

    int getAirWeaponDamage();

    int getMaxAirHits();

}
