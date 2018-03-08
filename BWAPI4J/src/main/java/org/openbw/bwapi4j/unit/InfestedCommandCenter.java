package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

import java.util.List;

public class InfestedCommandCenter extends Building implements Organic, FlyingBuilding, TrainingFacility {

    private Flyer flyer;
    private Trainer trainer;

    protected InfestedCommandCenter(int id, int timeSpotted) {
        super(id, UnitType.Zerg_Infested_Command_Center, timeSpotted);
        this.flyer = new Flyer();
        this.trainer = new Trainer();
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.flyer.update(unitData, index);
        this.trainer.update(unitData, index);
        super.update(unitData, index, frame);
    }

    public boolean trainInfestedTerran() {
        
        return this.trainer.train(UnitType.Zerg_Infested_Terran);
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
