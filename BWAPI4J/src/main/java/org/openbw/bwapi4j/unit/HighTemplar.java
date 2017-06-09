package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

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
    public void update(int[] unitData, int index) {

        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index);
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }
    
    public boolean archonWarp() {
        
        // TODO how does this spell work? does the other templars ID have to be passed as well?
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, -1, -1, TechType.Archon_Warp.getId());
    }

    public boolean hallucination(MobileUnit unit) {
        
        if (this.energy < TechType.Hallucination.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), 
                    -1, -1, TechType.Hallucination.getId());
        }
    }

    public boolean psionicStorm(Position position) {
    
        if (this.energy < TechType.Psionic_Storm.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, 
                    position.getX(), position.getY(), TechType.Psionic_Storm.getId());
        }
    }
}
