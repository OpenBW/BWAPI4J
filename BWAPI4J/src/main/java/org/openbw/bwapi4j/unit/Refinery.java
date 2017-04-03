package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Refinery extends Unit implements Mechanical {

	public Refinery(int id) {
		super(id, UnitType.Terran_Refinery);
	}
}
