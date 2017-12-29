package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;

public interface TrainingFacility {

    boolean isTraining();

    int getTrainingQueueSize();

    boolean cancelTrain(int slot);

    boolean cancelTrain();

    boolean setRallyPoint(Position p);

    boolean setRallyPoint(Unit target);
}
