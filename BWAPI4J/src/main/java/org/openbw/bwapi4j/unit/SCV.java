package org.openbw.bwapi4j.unit;

import java.util.Map;

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

    // TODO Worker superclass for all 3 races?
    
    protected SCV(int id) {
        
        super(id, UnitType.Terran_SCV);
    }

    @Override
    public void initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        this.isGatheringGas = false;
        this.isGatheringMinerals = false;
        this.isCarryingGas = false;
        this.isCarryingMinerals = false;
        this.isConstructing = false;
        this.isRepairing = false;
        super.initialize(unitData, index, allUnits);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.isGatheringGas = unitData[index + Unit.IS_GATHERING_GAS_INDEX] == 1;
        this.isGatheringMinerals = unitData[index + Unit.IS_GATHERING_MINERALS_INDEX] == 1;
        this.isCarryingGas = unitData[index + Unit.IS_CARRYING_GAS_INDEX] == 1;
        this.isCarryingMinerals = unitData[index + Unit.IS_CARRYING_MINERALS_INDEX] == 1;
        this.isConstructing = unitData[index + Unit.IS_CONSTRUCTING_INDEX] == 1;
        this.isRepairing = unitData[index + Unit.IS_REPAIRING_INDEX] == 1;
        super.update(unitData, index);
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

    public boolean returnCargo() {
        
        return issueCommand(this.id, UnitCommandType.Return_Cargo.ordinal(), -1, -1, -1, -1);
    }
    
    public boolean returnCargo(boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Return_Cargo.ordinal(), -1, -1, -1, queued ? 1 : 0);
    }

    public boolean repair(Mechanical target) {
        
        return issueCommand(this.id, UnitCommandType.Repair.ordinal(), ((Unit) target).getId(), -1, -1, -1);
    }
    
    public boolean repair(Mechanical target, boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Repair.ordinal(), ((Unit) target).getId(), -1, -1, queued ? 1 : 0);
    }

    public boolean haltConstruction() {
        
        return issueCommand(this.id, UnitCommandType.Halt_Construction.ordinal(), -1, -1, -1, -1);
    }

    public boolean gather(Refinery refinery) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), refinery.getId(), -1, -1, 0);
    }

    public boolean gather(Refinery refinery, boolean shiftQueueCommand) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), refinery.getId(), -1, -1,
                shiftQueueCommand ? 1 : 0);
    }

    public boolean gather(MineralPatch mineralPatch) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), mineralPatch.getId(), -1, -1, 0);
    }

    public boolean gather(MineralPatch mineralPatch, boolean shiftQueueCommand) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), mineralPatch.getId(), -1, -1,
                shiftQueueCommand ? 1 : 0);
    }

    public boolean build(Position p, UnitType type) {
        
        return issueCommand(this.id, UnitCommandType.Build.ordinal(), -1, p.getX(), p.getY(), type.getId());
    }

    public boolean cancelConstruction() {
        
        return issueCommand(this.id, UnitCommandType.Cancel_Construction.ordinal(), -1, -1, -1, -1);
    }

    public boolean resumeBuilding(Building building) {
        
        return issueCommand(this.id, UnitCommandType.Right_Click_Unit.ordinal(), -1, -1, -1, building.getId());
    }
}
