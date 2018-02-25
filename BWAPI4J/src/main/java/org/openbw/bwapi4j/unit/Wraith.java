package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Wraith extends MobileUnit implements Mechanical, Cloakable, Armed {

    protected Wraith(int id) {
        
        super(id, UnitType.Terran_Wraith);
    }

    public boolean cloak() {
        
        return issueCommand(this.id, UnitCommandType.Cloak.ordinal(), -1, -1, -1, -1);
    }

    public boolean decloak() {
        
        return issueCommand(this.id, UnitCommandType.Decloak.ordinal(), -1, -1, -1, -1);
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
