package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class ScienceFacility extends Unit implements Mechanical {

	public ScienceFacility(int id) {
		super(id, UnitType.Terran_Science_Facility);
	}
}
