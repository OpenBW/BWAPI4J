package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class ControlTower extends Unit implements Mechanical {

	public ControlTower(int id) {
		super(id, UnitType.Terran_Control_Tower);
	}
}
