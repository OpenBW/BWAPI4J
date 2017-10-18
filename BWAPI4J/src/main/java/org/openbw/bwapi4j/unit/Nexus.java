package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class Nexus extends Building implements Mechanical, TrainingFacility {

    private Trainer trainer;

    protected Nexus(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Nexus, timeSpotted);
        this.trainer = new Trainer();
    }

    @Override
    public void initialize(int[] unitData, int index) {

        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.trainer.update(unitData, index);
        super.update(unitData, index);
    }

    public boolean trainWorker() {
        
        return this.trainer.train(UnitType.Protoss_Probe);
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