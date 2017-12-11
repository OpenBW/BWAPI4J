package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class SCV extends MobileUnit implements Mechanical {

	private static final Logger logger = LogManager.getLogger();
	
    private boolean isGatheringGas;
    private boolean isGatheringMinerals;
    private boolean isCarryingGas;
    private boolean isCarryingMinerals;
    private boolean isConstructing;
    private boolean isRepairing;
    private int builderId;

    // TODO Worker superclass for all 3 races?
    
    protected SCV(int id) {
        
        super(id, UnitType.Terran_SCV);
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.isGatheringGas = false;
        this.isGatheringMinerals = false;
        this.isCarryingGas = false;
        this.isCarryingMinerals = false;
        this.isConstructing = false;
        this.isRepairing = false;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.isGatheringGas = unitData[index + Unit.IS_GATHERING_GAS_INDEX] == 1;
        this.isGatheringMinerals = unitData[index + Unit.IS_GATHERING_MINERALS_INDEX] == 1;
        this.isCarryingGas = unitData[index + Unit.IS_CARRYING_GAS_INDEX] == 1;
        this.isCarryingMinerals = unitData[index + Unit.IS_CARRYING_MINERALS_INDEX] == 1;
        this.isConstructing = unitData[index + Unit.IS_CONSTRUCTING_INDEX] == 1;
        this.isRepairing = unitData[index + Unit.IS_REPAIRING_INDEX] == 1;
        this.builderId = unitData[index + Unit.BUILD_UNIT_ID_INDEX];
        super.update(unitData, index, frame);
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

    public Building getBuildUnit() {
    
    	Unit unit = this.getUnit(builderId);
    	if (unit instanceof Building) {
    		return (Building) unit;
    	} else {
    		
    		logger.error("build unit for {} should be Building but is {}.", this, unit);
    		return null;
    	}
    }
    
    public boolean build(TilePosition p, UnitType type) {
        
        return issueCommand(this.id, UnitCommandType.Build.ordinal(), -1, p.getX(), p.getY(), type.getId());
    }

    public boolean cancelConstruction() {
        
        return issueCommand(this.id, UnitCommandType.Cancel_Construction.ordinal(), -1, -1, -1, -1);
    }

    public boolean resumeBuilding(Building building) {
        
        return issueCommand(this.id, UnitCommandType.Right_Click_Unit.ordinal(), -1, -1, -1, building.getId());
    }
}
