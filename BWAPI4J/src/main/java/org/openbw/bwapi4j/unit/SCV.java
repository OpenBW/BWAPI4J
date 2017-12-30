package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class SCV extends Worker<Refinery> implements Mechanical {

	private static final Logger logger = LogManager.getLogger();
	
    private boolean isRepairing;
    private int builderId;

    protected SCV(int id) {
        
        super(id, UnitType.Terran_SCV);
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.isRepairing = false;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.isRepairing = unitData[index + Unit.IS_REPAIRING_INDEX] == 1;
        this.builderId = unitData[index + Unit.BUILD_UNIT_ID_INDEX];
        super.update(unitData, index, frame);
    }
    
    public Building getBuildUnit() {
    
    	Unit unit = this.getUnit(builderId);
    	if (unit instanceof Building) {
    		return (Building) unit;
    	} else {
    		
    		logger.error("build unit for {} should be Building but is {}.", this, unit);
    		return null;
    	}
    }

    public boolean cancelConstruction() {
        
        return issueCommand(this.id, UnitCommandType.Cancel_Construction.ordinal(), -1, -1, -1, -1);
    }

    public boolean resumeBuilding(Building building) {
        
        return issueCommand(this.id, UnitCommandType.Right_Click_Unit.ordinal(), -1, -1, -1, building.getId());
    }
}
