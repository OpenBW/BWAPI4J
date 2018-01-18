package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Devourer extends MobileUnit implements Organic, Armed {

    protected Devourer(int id) {
        
        super(id, UnitType.Zerg_Devourer);
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
