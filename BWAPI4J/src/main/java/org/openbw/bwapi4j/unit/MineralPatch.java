package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class MineralPatch extends Unit {

	public MineralPatch(int id) {
		super(id, UnitType.Resource_Mineral_Field);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		return 0;
	}

	@Override
	public int update(int[] unitData, int index) {
		return 0;
	}

}
