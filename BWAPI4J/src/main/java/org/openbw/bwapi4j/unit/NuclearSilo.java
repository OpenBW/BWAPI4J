package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class NuclearSilo extends Unit implements Mechanical {

	public NuclearSilo(int id) {
		super(id, UnitType.Terran_Nuclear_Silo);
	}
}
