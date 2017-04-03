package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class EngineeringBay extends Unit implements Mechanical {

	public EngineeringBay(int id) {
		super(id, UnitType.Terran_Engineering_Bay);
	}
}
