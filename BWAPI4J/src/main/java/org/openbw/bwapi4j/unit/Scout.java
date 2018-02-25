package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Scout extends MobileUnit implements Mechanical, Armed {

    protected Scout(int id) {
        super(id, UnitType.Protoss_Scout);
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
