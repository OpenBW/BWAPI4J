package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class Goliath extends Unit implements Mechanical {

	public Goliath(int id) {
		super(id, UnitType.Terran_Goliath);
	}

	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int[] unitData, int index) {
		// TODO Auto-generated method stub
		return 0;
	}
}
