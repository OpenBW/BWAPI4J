package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public abstract class Addon extends Building {

    protected Addon(int id, UnitType unitType, int timeSpotted) {
        
        super(id, unitType, timeSpotted);
    }
    
    public Building getMainBuilding() {
        
        return (Building) this.getUnit(builderId);
    }
}
