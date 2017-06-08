package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Ultralisk extends MobileUnit implements Organic {

    protected Ultralisk(int id) {
        
        super(id, UnitType.Zerg_Ultralisk);
    }
}
