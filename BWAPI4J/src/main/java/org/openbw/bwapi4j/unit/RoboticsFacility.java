package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class RoboticsFacility extends Building implements Mechanical, TrainingFacility {

    private Trainer trainer;

    protected RoboticsFacility(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Robotics_Facility, timeSpotted);
        this.trainer = new Trainer();
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.trainer.update(unitData, index);
        super.update(unitData, index, frame);
    }

    public boolean trainShuttle() {
        
        return trainer.train(UnitType.Protoss_Shuttle);
    }

    public boolean trainReaver() {
        
        return trainer.train(UnitType.Protoss_Reaver);
    }

    public boolean trainObserver() {
        
        return trainer.train(UnitType.Protoss_Observer);
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
}
