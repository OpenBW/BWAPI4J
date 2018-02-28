package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Siege;
import static org.openbw.bwapi4j.type.UnitCommandType.Unsiege;

public class SiegeTank extends MobileUnit implements Mechanical, Armed {

    private boolean sieged;

    protected SiegeTank(int id, boolean sieged) {
        
        super(id, sieged ? UnitType.Terran_Siege_Tank_Siege_Mode : UnitType.Terran_Siege_Tank_Tank_Mode);
        this.sieged = sieged;
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.sieged = unitData[index + Unit.IS_SIEGED_INDEX] == 1;
        super.update(unitData, index, frame);
    }

    public boolean siege() {
        
        return issueCommand(this.id, Siege, -1, -1, -1, -1);
    }

    public boolean unsiege() {
        
        return issueCommand(this.id, Unsiege, -1, -1, -1, -1);
    }

    public boolean isSieged() {
        
        return this.sieged;
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
