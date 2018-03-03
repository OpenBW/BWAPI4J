package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

/**
 * Interface for units that can morph (many Zerg units can).
 */
public interface Morphable {
    /**
     * Morphs this unit into the given unit type.
     * Be aware that "this" object will be obsolete after the call and a new object with the same {@link Unit#getId()}
     * will be created.
     *
     * @param type the target type to morph to
     * @return true if the morph command was successfully delivered to Broodwar, false if the command was denied (for example due to missing supply or resources).
     * @throws IllegalArgumentException if the given type is invalid for this unit
     */
    boolean morph(UnitType type);
}
