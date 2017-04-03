package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Valkyrie extends Unit implements Mechanical {

	public Valkyrie(int id) {
		super(id, UnitType.Terran_Valkyrie);
	}
}
