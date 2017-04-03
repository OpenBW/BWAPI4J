package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class Armory extends Unit implements Mechanical {

	public Armory(int id) {
		super(id, UnitType.Terran_Armory);
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
