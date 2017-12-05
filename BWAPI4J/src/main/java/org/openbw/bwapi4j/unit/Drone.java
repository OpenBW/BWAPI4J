package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Drone extends MobileUnit implements Organic, Burrowable {

    private boolean burrowed;
    private boolean isGatheringGas;
    private boolean isGatheringMinerals;
    private boolean isCarryingGas;
    private boolean isCarryingMinerals;
    
    protected Drone(int id) {
        
        super(id, UnitType.Zerg_Drone);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.burrowed = false;
        this.isGatheringGas = false;
        this.isGatheringMinerals = false;
        this.isCarryingGas = false;
        this.isCarryingMinerals = false;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        this.isGatheringGas = unitData[index + Unit.IS_GATHERING_GAS_INDEX] == 1;
        this.isGatheringMinerals = unitData[index + Unit.IS_GATHERING_MINERALS_INDEX] == 1;
        this.isCarryingGas = unitData[index + Unit.IS_CARRYING_GAS_INDEX] == 1;
        this.isCarryingMinerals = unitData[index + Unit.IS_CARRYING_MINERALS_INDEX] == 1;
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

    public boolean isGatheringGas() {
        
        return this.isGatheringGas;
    }

    public boolean returnCargo() {
        
        return issueCommand(this.id, UnitCommandType.Return_Cargo.ordinal(), -1, -1, -1, -1);
    }
    
    public boolean returnCargo(boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Return_Cargo.ordinal(), -1, -1, -1, queued ? 1 : 0);
    }

    public boolean gather(Extractor extractor) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), extractor.getId(), -1, -1, 0);
    }

    public boolean gather(Extractor extractor, boolean shiftQueueCommand) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), extractor.getId(), -1, -1,
                shiftQueueCommand ? 1 : 0);
    }

    public boolean gather(MineralPatch mineralPatch) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), mineralPatch.getId(), -1, -1, 0);
    }

    public boolean gather(MineralPatch mineralPatch, boolean shiftQueueCommand) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), mineralPatch.getId(), -1, -1,
                shiftQueueCommand ? 1 : 0);
    }
    
    @Override
    public boolean burrow() {
        
        return issueCommand(this.id, UnitCommandType.Burrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean unburrow() {
        
        return issueCommand(this.id, UnitCommandType.Unburrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean isBurrowed() {
        
        return this.burrowed;
    }
    
    public boolean morph(UnitType type) {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, type.getId());
    }
}
