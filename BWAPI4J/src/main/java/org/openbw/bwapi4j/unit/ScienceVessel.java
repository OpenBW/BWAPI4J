package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class ScienceVessel extends Unit implements Mechanical {

	public ScienceVessel(int id) {
		super(id, UnitType.Terran_Science_Vessel);
	}
}
