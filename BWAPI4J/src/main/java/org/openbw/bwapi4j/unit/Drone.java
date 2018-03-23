package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public class Drone extends Worker implements Organic, Burrowable, Morphable {

    private boolean burrowed;

    protected Drone(int id) {
        
        super(id, UnitType.Zerg_Drone);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.burrowed = false;
        super.initialize(unitData, index, frame);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        super.update(unitData, index, frame);
    }
    
    @Override
    public boolean burrow() {
        
        return issueCommand(this.id, Burrow, -1, -1, -1, -1);
    }

    @Override
    public boolean unburrow() {
        
        return issueCommand(this.id, Unburrow, -1, -1, -1, -1);
    }

    @Override
    public boolean isBurrowed() {
        
        return this.burrowed;
    }

    @Override
    public boolean morph(UnitType type) {
        
        return issueCommand(this.id, Morph, -1, -1, -1, type.getId());
    }
}
