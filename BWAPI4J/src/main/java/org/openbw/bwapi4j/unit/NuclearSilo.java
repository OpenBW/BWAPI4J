package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class NuclearSilo extends Addon implements Mechanical {

    private boolean hasNuke;
    
    protected NuclearSilo(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Nuclear_Silo, timeSpotted);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.hasNuke = false;
        super.initialize(unitData, index);
    }
    
    @Override
    public void update(int[] unitData, int index, int frame) {

        this.hasNuke = unitData[index + Unit.HAS_NUKE_INDEX] == 1;
        super.update(unitData, index, frame);
    }
    
    public boolean hasNuke() {
        
        return this.hasNuke;
    }
}
