package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Train;
import static org.openbw.bwapi4j.type.UnitType.Protoss_Scarab;

public class Reaver extends MobileUnit implements Mechanical {

    private int scarabCount;
    
    protected Reaver(int id) {
        
        super(id, UnitType.Protoss_Reaver);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.scarabCount = 0;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.scarabCount = unitData[index + Unit.SCARAB_COUNT_INDEX];
        super.update(unitData, index, frame);
    }
    
    public int getScarabCount() {
        
        return this.scarabCount;
    }
    
    public boolean trainScarab() {
        
        return issueCommand(id, Train, Protoss_Scarab.getId(), -1, -1, -1);
    }
}
