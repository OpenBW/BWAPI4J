////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published 
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public class SCV extends Worker implements Mechanical {

	private static final Logger logger = LogManager.getLogger();
	
    private boolean isRepairing;
    private int builderId;

    protected SCV(int id) {
        
        super(id, UnitType.Terran_SCV);
    }

    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.isRepairing = false;
        super.initialize(unitData, index, frame);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.isRepairing = unitData[index + Unit.IS_REPAIRING_INDEX] == 1;
        this.builderId = unitData[index + Unit.BUILD_UNIT_ID_INDEX];
        super.update(unitData, index, frame);
    }

    public boolean isRepairing() {
        return isRepairing;
    }

    public boolean repair(Mechanical target) {
        return issueCommand(id, Repair, ((Unit) target).id, -1, -1, -1);
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

    public boolean haltConstruction() {
        
        return issueCommand(this.id, Halt_Construction, -1, -1, -1, -1);
    }

    public boolean resumeBuilding(Building building) {
        
        return issueCommand(this.id, Right_Click_Unit, building.getId(), -1, -1, -1);
    }
}
