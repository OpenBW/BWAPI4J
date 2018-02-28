package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Morph;

public class LurkerEgg extends PlayerUnit implements Organic {

    protected LurkerEgg(int id) {
        
        super(id, UnitType.Zerg_Lurker_Egg);
    }
    
    public boolean cancelMorph() {
        
        return issueCommand(this.id, Cancel_Morph, -1, -1, -1, -1);
    }
}
