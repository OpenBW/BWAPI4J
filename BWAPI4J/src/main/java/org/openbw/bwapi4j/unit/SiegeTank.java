package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class SiegeTank extends MobileUnit implements Mechanical {

    private boolean isSieged;

    protected SiegeTank(int id) {
        
        super(id, UnitType.Terran_Siege_Tank_Tank_Mode);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.isSieged = unitData[index + Unit.IS_SIEGED_INDEX] == 1;
        super.update(unitData, index);
    }

    protected boolean siege() {
        
        return issueCommand(this.id, UnitCommandType.Siege.ordinal(), -1, -1, -1, -1);
    }

    protected boolean unsiege() {
        
        return issueCommand(this.id, UnitCommandType.Unsiege.ordinal(), -1, -1, -1, -1);
    }

    public boolean isSieged() {
        
        return this.isSieged;
    }
}
