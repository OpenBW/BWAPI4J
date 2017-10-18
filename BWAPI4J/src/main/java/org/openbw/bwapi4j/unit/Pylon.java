package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Pylon extends Building implements Mechanical {

    protected Pylon(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Pylon, timeSpotted);
    }
}
