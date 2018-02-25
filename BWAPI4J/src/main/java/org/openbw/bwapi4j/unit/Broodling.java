package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Broodling extends MobileUnit implements Organic, Armed {

    protected Broodling(int id) {
        
        super(id, UnitType.Zerg_Broodling);
    }

    @Override
    public Weapon getGroundWeapon() {
        return groundWeapon;
    }

    @Override
    public Weapon getAirWeapon() {
        return airWeapon;
    }
}
