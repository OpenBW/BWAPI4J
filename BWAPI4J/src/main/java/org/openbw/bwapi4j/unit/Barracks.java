package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Barracks extends Unit implements Mechanical {

	public Barracks(int id) {
		super(id, UnitType.Terran_Barracks);
	}
}
