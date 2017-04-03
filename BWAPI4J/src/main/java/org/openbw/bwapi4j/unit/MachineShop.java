package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class MachineShop extends Addon implements Mechanical {

	public MachineShop(int id) {
		super(id, UnitType.Terran_Machine_Shop);
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
