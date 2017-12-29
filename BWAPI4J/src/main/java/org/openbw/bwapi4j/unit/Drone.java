package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Drone extends Worker<Extractor> implements Organic, Burrowable {

    private boolean burrowed;

    protected Drone(int id) {
        
        super(id, UnitType.Zerg_Drone);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.burrowed = false;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        super.update(unitData, index, frame);
    }
    
    @Override
    public boolean burrow() {
        
        return issueCommand(this.id, UnitCommandType.Burrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean unburrow() {
        
        return issueCommand(this.id, UnitCommandType.Unburrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean isBurrowed() {
        
        return this.burrowed;
    }
    
    public boolean morph(UnitType type) {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, type.getId());
    }
}
