package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class CommandCenter extends Building implements Mechanical {

	private boolean isTraining;
	private boolean isLifted;
	private int trainingQueueSize;
	
	private int addonId;
	
	public CommandCenter(int id) {
		super(id, UnitType.Terran_Command_Center);
	}

	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		this.addonId = -1;
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		super.update(unitData, index);
		
		this.isTraining = unitData[index + Unit.IS_TRAINING_INDEX] == 1;
		this.isLifted = unitData[index + Unit.IS_LIFTED_INDEX] == 1;
		this.trainingQueueSize = unitData[index + Unit.TRAINING_QUEUE_SIZE_INDEX];
		this.addonId = unitData[index + Unit.ADDON_INDEX];
		
		return index;
	}
	
	public NuclearSilo getNuclearSilo() {
		
		Unit unit = super.getUnit(addonId);
		if (unit != null && unit instanceof NuclearSilo) {
			return (NuclearSilo)unit;
		} else {
			return null;
		}
	}
	
	public ComsatStation getComsatStation() {
		
		Unit unit = super.getUnit(addonId);
		if (unit != null && unit instanceof ComsatStation) {
			return (ComsatStation)unit;
		} else {
			return null;
		}
	}
	
	public boolean isLifted() {
		return this.isLifted;
	}
	
	public boolean isTraining() {
		return this.isTraining;
	}

	public int getTrainingQueueSize() {
		return this.trainingQueueSize;
	}
	
	public boolean buildComsatStation() {
		return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), UnitType.Terran_Comsat_Station.getId(), -1, -1, -1);
	}
	
	public boolean buildNuclearSilo() {
		return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), UnitType.Terran_Nuclear_Silo.getId(), -1, -1, -1);
	}
	
	public boolean trainWorker() {
		return issueCommand(this.id, UnitCommandType.Train.ordinal(), UnitType.Terran_SCV.getId(), -1, -1, -1);
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
