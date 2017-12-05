package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Arbiter extends MobileUnit implements Mechanical, SpellCaster {

    private int energy;

    protected Arbiter(int id) {
        
        super(id, UnitType.Protoss_Arbiter);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.energy = 0;
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
    
    public boolean stasisField(Position position) {
        
        if (this.energy < TechType.Stasis_Field.energyCost()) {
            return false;
        } else {
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, 
                    position.getX(), position.getY(), TechType.Stasis_Field.getId());
        }
    }
    
    public boolean recall(Position position) {
        
        if (this.energy < TechType.Recall.energyCost()) {
            return false;
        } else {
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, 
                    position.getX(), position.getY(), TechType.Recall.getId());
        }
    }
}
