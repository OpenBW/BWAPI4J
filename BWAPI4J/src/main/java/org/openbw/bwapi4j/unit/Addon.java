package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public abstract class Addon extends Building {

	Addon(int id, UnitType unitType, int timeSpotted) {
		super(id, unitType, timeSpotted);
	}
}
