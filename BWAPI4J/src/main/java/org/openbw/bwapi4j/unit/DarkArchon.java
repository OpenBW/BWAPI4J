package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class DarkArchon extends MobileUnit implements Organic, SpellCaster {

    private int energy;

    protected DarkArchon(int id) {
        
        super(id, UnitType.Protoss_Dark_Archon);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.energy = 0;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index);
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }
    
    public boolean feedback(MobileUnit unit) {
        
        if (this.energy < TechType.Feedback.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), 
                    -1, -1, TechType.Feedback.getId());
        }
    }
    
    public boolean mindControl(MobileUnit unit) {
        
        if (this.energy < TechType.Mind_Control.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), 
                    -1, -1, TechType.Mind_Control.getId());
        }
    }

    public boolean maelstrom(Position position) {
    
        if (this.energy < TechType.Maelstrom.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, 
                    position.getX(), position.getY(), TechType.Maelstrom.getId());
        }
    }
}
