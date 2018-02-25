package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Ultralisk extends MobileUnit implements Organic, Armed {

    protected Ultralisk(int id) {
        
        super(id, UnitType.Zerg_Ultralisk);
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
