package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;

interface TrainingFacility {

	public boolean isTraining();

	public int getTrainingQueueSize();
	
	public boolean cancelTrain(int slot);
	
	public boolean cancelTrain();
	
	public boolean setRallyPoint(Position p);
	
	public boolean setRallyPoint(Unit target);
}
