package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Mutalisk extends MobileUnit implements Organic {

    protected Mutalisk(int id) {
        
        super(id, UnitType.Zerg_Mutalisk);
    }
    
    public boolean morph() {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Guardian.getId());
    }
}
