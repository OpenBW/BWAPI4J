package org.openbw.bwapi4j.unit;

/**
 * Units implementing this interface can attack.
 */
public interface Attacker {

    boolean attack(Unit target);

    boolean attack(Unit target, boolean queued);

    Unit getTargetUnit();

}
