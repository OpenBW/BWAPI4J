package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class SiegeTank extends MobileUnit implements Mechanical {

    private boolean isSieged;

    protected SiegeTank(int id) {
        super(id, UnitType.Terran_Siege_Tank_Tank_Mode);
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        this.isSieged = unitData[index + Unit.IS_SIEGED_INDEX] == 1;
        super.update(unitData, index);

        return index;
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
