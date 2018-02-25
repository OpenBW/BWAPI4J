package org.openbw.bwapi4j.unit;

/**
 * Units implementing this interface can attack
 */
public interface Armed {
    boolean attack(Unit target);

    boolean attack(Unit target, boolean queued);

    Weapon getGroundWeapon();

    Weapon getAirWeapon();

    Unit getTargetUnit();
}
