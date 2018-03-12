package org.openbw.bwapi4j.unit;

/**
 * Units implementing this interface can attack ground units.
 */
public interface GroundAttacker extends Attacker {

    Weapon getGroundWeapon();

    int getGroundWeaponMaxRange();

    int getGroundWeaponCooldown();

    int getGroundWeaponDamage();

    int getMaxGroundHits();

}
