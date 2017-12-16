package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Refinery extends GasMiningFacility implements Mechanical {

    protected Refinery(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Refinery, timeSpotted);
    }
}
