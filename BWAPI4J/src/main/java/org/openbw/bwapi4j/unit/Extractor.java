package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Extractor extends GasMiningFacility implements Organic {

    protected Extractor(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Extractor, timeSpotted);
    }
}
