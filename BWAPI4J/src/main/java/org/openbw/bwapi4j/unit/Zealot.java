package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Zealot extends MobileUnit implements Organic, Armed {

    protected Zealot(int id) {
        
        super(id, UnitType.Protoss_Zealot);
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
