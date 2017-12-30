package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public interface TrainingFacility {

    boolean isTraining();

    int getTrainingQueueSize();

    boolean train(UnitType type);

    boolean canTrain(UnitType type);

    boolean cancelTrain(int slot);

    boolean cancelTrain();

    boolean setRallyPoint(Position p);

    boolean setRallyPoint(Unit target);
}
