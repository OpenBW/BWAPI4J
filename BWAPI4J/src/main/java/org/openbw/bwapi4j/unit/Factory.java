package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Factory extends Building implements Mechanical {

	private boolean isTraining;
	private int trainingQueueSize;
	
	public Factory(int id) {
		super(id, UnitType.Terran_Factory);
	}

	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int[] unitData, int index) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean isTraining() {
		return this.isTraining;
	}

	public int getTrainingQueueSize() {
		return this.trainingQueueSize;
	}
	
	public boolean trainVulture() {
		return issueCommand(this.id, UnitCommandType.Train.ordinal(), UnitType.Terran_Vulture.getId(), -1, -1, -1);
	}

	public boolean trainSiegeTank() {
		return issueCommand(this.id, UnitCommandType.Train.ordinal(), UnitType.Terran_Siege_Tank_Tank_Mode.getId(), -1, -1, -1);
	}

	public boolean trainGoliath() {
		return issueCommand(this.id, UnitCommandType.Train.ordinal(), UnitType.Terran_Goliath.getId(), -1, -1, -1);
	}

	public boolean setRallyPoint(Position p) {
		return issueCommand(this.id, UnitCommandType.Set_Rally_Position.ordinal(), -1, p.getX(), p.getY(), -1);
	}
	
	public boolean setRallyPoint(Unit target) {
		return issueCommand(this.id, UnitCommandType.Set_Rally_Unit.ordinal(), target.getId(), -1, -1, -1);
	}
	
	public boolean cancelTrain(int slot) {
		return issueCommand(this.id, UnitCommandType.Cancel_Train_Slot.ordinal(), -1, -1, -1, slot);
	}
	
	public boolean cancelTrain() {
		return issueCommand(this.id, UnitCommandType.Cancel_Train.ordinal(), -1, -1, -1, -1);
	}
}
