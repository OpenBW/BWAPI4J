package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class Dragoon extends MobileUnit implements Mechanical {

    protected Dragoon(int id) {
        
        super(id, UnitType.Protoss_Dragoon);
    }
}
