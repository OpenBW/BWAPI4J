package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Assimilator extends GasMiningFacility implements Mechanical {

    protected Assimilator(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Assimilator, timeSpotted);
    }
}
