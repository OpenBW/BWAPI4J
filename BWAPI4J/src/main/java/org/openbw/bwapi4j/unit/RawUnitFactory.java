package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class RawUnitFactory extends UnitFactory {
    @Override
    public Unit createUnit(int unitId, UnitType unitType, int timeSpotted) {
        return new RawUnit(unitId, unitType);
    }
}
