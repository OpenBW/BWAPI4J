package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class ComsatStation extends Unit implements Mechanical {

	public ComsatStation(int id) {
		super(id, UnitType.Terran_Comsat_Station);
	}
}
