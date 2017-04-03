package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class PhysicsLab extends Unit implements Mechanical {

	public PhysicsLab(int id) {
		super(id, UnitType.Terran_Physics_Lab);
	}
}
