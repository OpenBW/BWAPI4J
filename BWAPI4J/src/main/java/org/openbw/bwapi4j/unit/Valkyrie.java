package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Valkyrie extends MobileUnit implements Mechanical, Armed {

    protected Valkyrie(int id) {
        
        super(id, UnitType.Terran_Valkyrie);
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
