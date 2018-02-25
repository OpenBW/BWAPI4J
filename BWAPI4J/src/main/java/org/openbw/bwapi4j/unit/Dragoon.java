package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Dragoon extends MobileUnit implements Mechanical, Armed {

    protected Dragoon(int id) {
        
        super(id, UnitType.Protoss_Dragoon);
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
