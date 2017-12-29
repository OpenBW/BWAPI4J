package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Probe extends MobileUnit implements Mechanical, Robotic {

    private boolean isGatheringGas;
    private boolean isGatheringMinerals;
    private boolean isCarryingGas;
    private boolean isCarryingMinerals;

    protected Probe(int id) {
        super(id, UnitType.Protoss_Probe);
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.isGatheringGas = false;
        this.isGatheringMinerals = false;
        this.isCarryingGas = false;
        this.isCarryingMinerals = false;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

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

    public boolean gather(Assimilator assimilator) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), assimilator.getId(), -1, -1, 0);
    }

    public boolean gather(Assimilator assimilator, boolean shiftQueueCommand) {
        
        return issueCommand(this.id, UnitCommandType.Gather.ordinal(), assimilator.getId(), -1, -1,
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
}
