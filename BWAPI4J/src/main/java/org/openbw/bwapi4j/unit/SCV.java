package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class SCV extends MobileUnit implements Mechanical {

	private boolean isGatheringGas;
	private boolean isGatheringMinerals;
	private boolean isCarryingGas;
	private boolean isCarryingMinerals;
	private boolean isConstructing;
	private boolean isRepairing;
	
	public SCV(int id) {
		super(id, UnitType.Terran_SCV);
	}
	
	public boolean isGatheringMinerals() {
		return this.isGatheringMinerals;
	}

	public boolean isCarryingMinerals() {
		return this.isCarryingMinerals;
	}
	
	public boolean isCarryingGas() {
		return this.isCarryingGas;
	}
	
	public boolean isConstructing() {
		return this.isConstructing;
	}
	
	public boolean isGatheringGas() {
		return this.isGatheringGas;
	}
	
	public boolean isRepairing() {
		return this.isRepairing;
	}
	
	public boolean returnCargo(boolean queued) {
		return issueCommand(this.id, UnitCommandType.Return_Cargo.ordinal(), -1, -1, -1, queued ? 1 : 0);
	}
	
	public boolean repair(Unit target, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Repair.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
	}

	public boolean haltConstruction() {
		return issueCommand(this.id, UnitCommandType.Halt_Construction.ordinal(), -1, -1, -1, -1);
	}
	
	public boolean gather(Refinery refinery) {
		return issueCommand(this.id, UnitCommandType.Gather.ordinal(), refinery.getId(), -1, -1, 0);
	}
	
	public boolean gather(Refinery refinery, boolean shiftQueueCommand) {
		return issueCommand(this.id, UnitCommandType.Gather.ordinal(), refinery.getId(), -1, -1, shiftQueueCommand ? 1 : 0);
	}
	
	public boolean gather(MineralPatch mineralPatch) {
		return issueCommand(this.id, UnitCommandType.Gather.ordinal(), mineralPatch.getId(), -1, -1, 0);
	}
	
	public boolean gather(MineralPatch mineralPatch, boolean shiftQueueCommand) {
		return issueCommand(this.id, UnitCommandType.Gather.ordinal(), mineralPatch.getId(), -1, -1, shiftQueueCommand ? 1 : 0);
	}
	
	public boolean build(Position p, UnitType type) {
		return issueCommand(this.id, UnitCommandType.Build.ordinal(), type.getId(), p.getX(), p.getY(), -1);
	}
	
	public boolean cancelConstruction() {
		return issueCommand(this.id, UnitCommandType.Cancel_Construction.ordinal(), -1, -1, -1, -1);
	}
	
	public boolean returnCargo() {
		return issueCommand(this.id, UnitCommandType.Return_Cargo.ordinal(), -1, -1, -1, -1);
	}

	public boolean resumeBuilding(Building building) {
		return issueCommand(this.id, UnitCommandType.Right_Click_Unit.ordinal(), building.getId(), -1, -1, -1);
	}
	
	public boolean repair(Mechanical unit) {
		return issueCommand(this.id, UnitCommandType.Repair.ordinal(), ((Unit)unit).getId(), -1, -1, -1);
	}
}
