package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

/**
 * Units implementing this can have an addon.
 */
public interface ExtendibleByAddon {
    boolean build(UnitType addon);
    boolean cancelAddon();
    Addon getAddon();
    default boolean isBuildingAddon() {
        return getAddon() != null && !getAddon().isCompleted;
    }
}
