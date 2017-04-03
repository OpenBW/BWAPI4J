package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class MissileTurret extends Unit implements Mechanical {

	public MissileTurret(int id) {
		super(id, UnitType.Terran_Missile_Turret);
	}
}
