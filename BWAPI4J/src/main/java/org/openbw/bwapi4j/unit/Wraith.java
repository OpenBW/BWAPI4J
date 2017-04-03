package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Wraith extends Unit implements Mechanical {

	public Wraith(int id) {
		super(id, UnitType.Terran_Wraith);
	}

	protected boolean cloak() {
		return issueCommand(this.id, UnitCommandType.Cloak.ordinal(), -1, -1, -1, -1);
	}
	
	protected boolean decloak() {
		return issueCommand(this.id, UnitCommandType.Decloak.ordinal(), -1, -1, -1, -1);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int[] unitData, int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}
