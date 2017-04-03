package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class VespeneGeyser extends Unit {

	private int initialResources;
	private int resources;
	
	public VespeneGeyser(int id) {
		super(id, UnitType.Resource_Vespene_Geyser);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		super.initialize(unitData, index, allUnits);
		this.initialResources = unitData[index + Unit.INITIAL_RESOURCES_INDEX];
		
		return index;
	}

	@Override
	public int update(int[] unitData, int index) {
		
		super.update(unitData, index);
		this.resources = unitData[index  + Unit.RESOURCES_INDEX];
		
		return index;
	}
	
	public int getResources() {
		return this.resources;
	}
	
	public int getInitialResources() {
		return this.initialResources;
	}
}
