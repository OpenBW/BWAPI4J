package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public abstract class Spell extends PlayerUnit {

    protected int timeSpotted;
    
    Spell(int id, int timeSpotted, UnitType unitType) {
        
        super(id, unitType);
        this.timeSpotted = timeSpotted;
    }

    public int getTimeSpotted() {
        return this.timeSpotted;
    }
    
    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        index = super.initialize(unitData, index, allUnits);

        return index;
    }

    @Override
    public int update(int[] unitData, int index) {

        index = super.update(unitData, index);
        
        return index;
    }
}
