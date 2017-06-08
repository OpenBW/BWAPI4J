package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Scarab extends MobileUnit implements Mechanical {

    protected Scarab(int id) {
        
        super(id, UnitType.Protoss_Scarab);
    }
}
