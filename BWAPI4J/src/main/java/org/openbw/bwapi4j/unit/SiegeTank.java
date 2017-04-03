package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class SiegeTank extends Unit implements Mechanical {

	public SiegeTank(int id) {
		super(id, UnitType.Terran_Siege_Tank_Tank_Mode);
	}
	
	protected boolean siege() {
		return issueCommand(this.id, UnitCommandType.Siege.ordinal(), -1, -1, -1, -1);
	}
	
	protected boolean unsiege() {
		return issueCommand(this.id, UnitCommandType.Unsiege.ordinal(), -1, -1, -1, -1);
	}
}
