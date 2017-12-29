package org.openbw.bwapi4j.unit;

/**
 * Units implementing this interface can attack
 */
public interface Armed {
    Weapon getGroundWeapon();
    Weapon getAirWeapon();
}
