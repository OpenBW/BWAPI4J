package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.*;

public class HighTemplar extends MobileUnit implements Organic, SpellCaster {

    private int energy;

    protected HighTemplar(int id) {
        
        super(id, UnitType.Protoss_High_Templar);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.energy = 0; // TODO actually check start value
        super.initialize(unitData, index);
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
    
    public boolean archonWarp() {
        
        // TODO how does this spell work? does the other templars ID have to be passed as well?
        return issueCommand(this.id, Use_Tech, -1, -1, -1, Archon_Warp.getId());
    }

    public boolean hallucination(MobileUnit unit) {
        
        if (this.energy < TechType.Hallucination.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Unit, unit.getId(),
                    -1, -1, Hallucination.getId());
        }
    }

    public boolean psionicStorm(Position position) {
    
        if (this.energy < TechType.Psionic_Storm.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Position, -1,
                    position.getX(), position.getY(), Psionic_Storm.getId());
        }
    }
}
