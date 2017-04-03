package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Vulture extends MobileUnit implements Mechanical {

	public Vulture(int id) {
		super(id, UnitType.Terran_Vulture);
	}
}
