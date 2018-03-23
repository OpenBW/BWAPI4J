package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Unit;

public class DarkArchon extends MobileUnit implements Organic, SpellCaster {

    private int energy;

    protected DarkArchon(int id) {
        
        super(id, UnitType.Protoss_Dark_Archon);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.energy = 0;
        super.initialize(unitData, index, frame);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index, frame);
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }

    @Override
    public int getMaxEnergy() {

        return super.getMaxEnergy();
    }

    public boolean feedback(MobileUnit unit) {
        
        if (this.energy < TechType.Feedback.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Unit, unit.getId(),
                    -1, -1, Feedback.getId());
        }
    }
    
    public boolean mindControl(MobileUnit unit) {
        
        if (this.energy < TechType.Mind_Control.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Unit, unit.getId(),
                    -1, -1, Mind_Control.getId());
        }
    }

    public boolean maelstrom(Position position) {
    
        if (this.energy < TechType.Maelstrom.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Position, -1,
                    position.getX(), position.getY(), Maelstrom.getId());
        }
    }
}
