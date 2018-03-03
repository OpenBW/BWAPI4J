package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Spore_Colony;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Sunken_Colony;

public class CreepColony extends Building implements Organic, Morphable {

    protected CreepColony(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Creep_Colony, timeSpotted);
    }

    /**
     * Morph into either SunkenColony or SporeColony
     * @param type UnitType.Zerg_Sunken_Colony or UnitType.Zerg_Spore_Colony
     * @return true if morph successful, false else
     */
    @Override
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
        
        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Spore_Colony.getId());
    }
    
    public boolean morphSunkenColony() {
        
        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Sunken_Colony.getId());
    }
}
