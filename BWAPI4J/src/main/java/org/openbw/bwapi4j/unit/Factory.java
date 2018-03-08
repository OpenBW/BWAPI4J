package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import java.util.List;

import static org.openbw.bwapi4j.type.UnitCommandType.Build_Addon;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Addon;
import static org.openbw.bwapi4j.type.UnitType.Terran_Machine_Shop;

public class Factory extends Building implements Mechanical, FlyingBuilding, TrainingFacility, ExtendibleByAddon {

    private int addonId;
    
    private Flyer flyer;
    private Trainer trainer;

    protected Factory(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Factory, timeSpotted);
        this.flyer = new Flyer();
        this.trainer = new Trainer();
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.addonId = -1;
        super.initialize(unitData, index);
    }
    
    @Override
    public void update(int[] unitData, int index, int frame) {

        this.flyer.update(unitData, index);
        this.trainer.update(unitData, index);
        this.addonId = unitData[index + Unit.ADDON_INDEX];
        super.update(unitData, index, frame);
    }

    public boolean trainVulture() {
        
        return this.trainer.train(UnitType.Terran_Vulture);
    }

    public boolean trainSiegeTank() {
        
        return this.trainer.train(UnitType.Terran_Siege_Tank_Tank_Mode);
    }

    public boolean trainGoliath() {
        
        return this.trainer.train(UnitType.Terran_Goliath);
    }

    @Override
    public boolean canTrain(UnitType type) {
        return this.trainer.canTrain(type);
    }

    @Override
    public boolean train(UnitType type) {
        return this.trainer.train(type);
    }

    @Override
    public boolean cancelAddon() {
        
        return issueCommand(this.id, Cancel_Addon, -1, -1, -1, -1);
    }
    
    /**
     * Builds a machine shop addon to this factory.
     * @return true if command successful, false else
     */
    public boolean buildMachineShop() {
        
        return issueCommand(this.id, Build_Addon, -1, -1, -1, Terran_Machine_Shop.getId());
    }

    public MachineShop getMachineShop() {
        
        return (MachineShop) this.getUnit(this.addonId);
    }

    @Override
    public Addon getAddon() {
        return getMachineShop();
    }

    @Override
    public boolean build(UnitType addon) {
        return issueCommand(this.id, Build_Addon, -1, -1, -1, addon.getId());
    }

    @Override
    public boolean isLifted() {
        
        return this.flyer.isLifted();
    }

    @Override
    public boolean lift() {
        
        return this.flyer.lift();
    }

    @Override
    public boolean land(Position p) {
        
        return this.flyer.land(p);
    }

    @Override
    public boolean move(Position p) {
        
        return this.flyer.move(p);
    }

    @Override
    public boolean isTraining() {
        
        return this.trainer.isTraining();
    }

    @Override
    public int getTrainingQueueSize() {
        
        return this.trainer.getTrainingQueueSize();
    }

    @Override
    public List<UnitType> getTrainingQueue() {

        return this.trainer.getTrainingQueue();
    }

    @Override
    public boolean cancelTrain(int slot) {
        
        return this.trainer.cancelTrain(slot);
    }

    @Override
    public boolean cancelTrain() {
        
        return this.trainer.cancelTrain();
    }

    @Override
    public boolean setRallyPoint(Position p) {
        
        return this.trainer.setRallyPoint(p);
    }

    @Override
    public boolean setRallyPoint(Unit target) {
        
        return this.trainer.setRallyPoint(target);
    }

    @Override
    public int getRemainingTrainTime() {
        return trainer.getRemainingTrainingTime();
    }
}
