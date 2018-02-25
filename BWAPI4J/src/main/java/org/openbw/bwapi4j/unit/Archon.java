package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Archon extends MobileUnit implements Armed {

    protected Archon(int id) {
        
        super(id, UnitType.Protoss_Archon);
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
