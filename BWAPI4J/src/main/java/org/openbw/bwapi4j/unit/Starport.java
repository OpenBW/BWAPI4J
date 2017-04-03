package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Starport extends Unit implements Mechanical {

	public Starport(int id) {
		super(id, UnitType.Terran_Starport);
	}
}
