package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class VespeneGeyser extends Unit {

	public VespeneGeyser(int id) {
		super(id, UnitType.Resource_Vespene_Geyser);
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
