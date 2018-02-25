package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Goliath extends MobileUnit implements Mechanical, Armed {

    protected Goliath(int id) {
        
        super(id, UnitType.Terran_Goliath);
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
