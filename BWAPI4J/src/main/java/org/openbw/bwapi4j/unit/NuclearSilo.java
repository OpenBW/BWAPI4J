package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class NuclearSilo extends Addon implements Mechanical {

	public NuclearSilo(int id, int timeSpotted) {
		super(id, UnitType.Terran_Nuclear_Silo, timeSpotted);
	}
}
