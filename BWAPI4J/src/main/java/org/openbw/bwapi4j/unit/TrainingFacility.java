package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

import java.util.List;

public interface TrainingFacility {

    boolean isTraining();

    int getTrainingQueueSize();

    List<Building.TrainingSlot> getTrainingQueue();

    int getRemainingTrainTime();

    boolean train(UnitType type);

    boolean canTrain(UnitType type);

    boolean cancelTrain(int slot);

    boolean cancelTrain();

    boolean setRallyPoint(Position p);

    boolean setRallyPoint(Unit target);
}
