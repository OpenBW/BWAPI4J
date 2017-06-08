package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Zealot extends MobileUnit implements Organic {

    protected Zealot(int id) {
        
        super(id, UnitType.Protoss_Zealot);
    }
}
