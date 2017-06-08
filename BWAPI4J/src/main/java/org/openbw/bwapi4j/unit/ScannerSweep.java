package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class ScannerSweep extends Spell {

    protected ScannerSweep(int id, int timeSpotted) {
        
        super(id, timeSpotted, UnitType.Spell_Scanner_Sweep);
    }

}
