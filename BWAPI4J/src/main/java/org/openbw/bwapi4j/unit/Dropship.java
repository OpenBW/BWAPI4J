package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Dropship extends MobileUnit implements Mechanical {

	private boolean isLoaded;
	
	public Dropship(int id) {
		super(id, UnitType.Terran_Dropship);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		this.isLoaded = unitData[Unit.IS_LOADED_INDEX] == 1;
		return super.update(unitData, index);
	}
	
	public boolean isLoaded() {
		return this.isLoaded;
	}
	
	protected boolean load(Unit target, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Load.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
	}
	
	protected boolean unload(Unit target) {
		return issueCommand(this.id, UnitCommandType.Unload.ordinal(), target.getId(), -1, -1, -1);
	}
	
	protected boolean unloadAll(boolean queued) {
		return issueCommand(this.id, UnitCommandType.Unload_All.ordinal(), -1, -1, -1, queued ? 1 : 0);
	}
	
	protected boolean unloadAll(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Unload_All_Position.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
}
