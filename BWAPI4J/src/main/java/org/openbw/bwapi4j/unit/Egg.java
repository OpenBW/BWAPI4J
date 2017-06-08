package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Egg extends PlayerUnit implements Organic {

    protected Egg(int id) {
        
        super(id, UnitType.Zerg_Egg);
    }
    
    public boolean cancelMorph() {
        
        return issueCommand(this.id, UnitCommandType.Cancel_Morph.ordinal(), -1, -1, -1, -1);
    }
}
