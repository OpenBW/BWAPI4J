package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public abstract class Spell extends PlayerUnit {

    protected int timeSpotted;
    
    protected Spell(int id, int timeSpotted, UnitType unitType) {
        
        super(id, unitType);
        this.timeSpotted = timeSpotted;
    }

    public int getTimeSpotted() {
        
        return this.timeSpotted;
    }
}
