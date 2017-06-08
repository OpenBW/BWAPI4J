package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Reaver extends MobileUnit implements Mechanical {

    private int scarabCount;
    
    protected Reaver(int id) {
        
        super(id, UnitType.Protoss_Reaver);
    }
    
    @Override
    public void initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        this.scarabCount = 0;
        super.initialize(unitData, index, allUnits);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.scarabCount = unitData[index + Unit.SCARAB_COUNT_INDEX];
        super.update(unitData, index);
    }
    
    public int getScarabCount() {
        
        return this.scarabCount;
    }
    
    public boolean trainScarab() {
        
        return issueCommand(id, UnitCommandType.Train.ordinal(), UnitType.Protoss_Scarab.getId(), -1, -1, -1);
    }
}
