package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Wraith extends MobileUnit implements Mechanical, Cloakable {

	public Wraith(int id) {
		super(id, UnitType.Terran_Wraith);
	}

	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		return super.update(unitData, index);
	}
	
	public boolean cloak() {
		return issueCommand(this.id, UnitCommandType.Cloak.ordinal(), -1, -1, -1, -1);
	}
	
	public boolean decloak() {
		return issueCommand(this.id, UnitCommandType.Decloak.ordinal(), -1, -1, -1, -1);
	}
}
