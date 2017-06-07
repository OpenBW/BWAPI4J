package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class SunkenColony extends Building implements Organic {

    SunkenColony(int id, int timeSpotted) {
        super(id, UnitType.Zerg_Sunken_Colony, timeSpotted);
    }

    protected boolean attack(Unit target, boolean queued) {
        return issueCommand(this.id, UnitCommandType.Attack_Unit.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
    }
}
