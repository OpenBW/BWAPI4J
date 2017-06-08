package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class CreepColony extends Building implements Organic {

    protected CreepColony(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Creep_Colony, timeSpotted);
    }

    /**
     * Morph into either SunkenColony or SporeColony
     * @param type UnitType.Zerg_Sunken_Colony or UnitType.Zerg_Spore_Colony
     * @return true if morph successful, false else
     */
    public boolean morph(UnitType type) {
        
        if (type == UnitType.Zerg_Sunken_Colony) {
            
            return morphSunkenColony();
        } else if (type == UnitType.Zerg_Spore_Colony) {
            
            return morphSporeColony();
        } else {
            
            return false;
        }
    }
    
    public boolean morphSporeColony() {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Spore_Colony.getId());
    }
    
    public boolean morphSunkenColony() {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Sunken_Colony.getId());
    }
}
