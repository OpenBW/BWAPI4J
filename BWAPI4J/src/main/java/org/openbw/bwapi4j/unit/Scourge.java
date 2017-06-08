package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Scourge extends MobileUnit implements Organic {

    protected Scourge(int id) {
        super(id, UnitType.Zerg_Scourge);
    }
}
