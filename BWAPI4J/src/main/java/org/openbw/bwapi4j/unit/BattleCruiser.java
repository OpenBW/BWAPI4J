package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class BattleCruiser extends Unit implements Mechanical {

	public BattleCruiser(int id) {
		super(id, UnitType.Terran_Battlecruiser);
	}
}
