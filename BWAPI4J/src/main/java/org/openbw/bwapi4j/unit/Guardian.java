package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Guardian extends MobileUnit implements Organic, Armed {

    protected Guardian(int id) {
        
        super(id, UnitType.Zerg_Guardian);
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
