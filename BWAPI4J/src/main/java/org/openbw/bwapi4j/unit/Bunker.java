package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Bunker extends Unit implements Mechanical {

	public Bunker(int id) {
		super(id, UnitType.Terran_Bunker);
	}
}
