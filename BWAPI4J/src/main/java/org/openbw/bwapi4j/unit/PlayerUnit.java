package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public abstract class PlayerUnit extends Unit {

	protected int initialHitPoints;
	protected int initialResources;
	
	protected int hitPoints;
	protected int resources;
	
	PlayerUnit(int id, UnitType unitType) {
		super(id, unitType);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		index = super.initialize(unitData, index, allUnits);
		this.initialHitPoints = unitData[index + Unit.INITIAL_HITPOINTS_INDEX];
		this.initialResources = unitData[index + Unit.INITIAL_RESOURCES_INDEX];
		
		return index;
	}

	@Override
	public int update(int[] unitData, int index) {
		
		index = super.update(unitData, index);
		this.hitPoints = unitData[index  + Unit.HITPOINTS_INDEX];
		this.resources = unitData[index  + Unit.RESOURCES_INDEX];
		
		return index;
	}
}
