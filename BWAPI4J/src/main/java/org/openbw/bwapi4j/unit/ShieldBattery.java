package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class ShieldBattery extends Building implements Mechanical {

    protected ShieldBattery(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Shield_Battery, timeSpotted);
    }
    
    /**
     * Workaround: the recharge command actually performs a right click command of the target unit on the ShieldBattery.
     * This is because BWAPI does not support a dedicated recharge command.
     * @param target unit to be recharged
     * @param queued true if command is queued, false else
     * @return true if successful, false else
     */
    protected boolean recharge(Unit target, boolean queued) {
        
    	return issueCommand(target.id, UnitCommandType.Right_Click_Unit.ordinal(), this.getId(), -1, -1, queued ? 1 : 0);
    }
}
