package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public abstract class Building extends PlayerUnit {

	protected class Researcher implements ResearchingFacility {
	
		private boolean isUpgrading;
		private boolean isResearching;
		
		public void update(int[] unitData, int index) {
			
			this.isUpgrading = unitData[Unit.IS_UPGRADING_INDEX] == 1;
			this.isResearching = unitData[Unit.IS_RESEARCHING_INDEX] == 1;
		}
		
		public boolean isUpgrading() {
			return this.isUpgrading;
		}

		public boolean isResearching() {
			return this.isResearching;
		}
		
		public boolean cancelResearch() {
			return issueCommand(id, UnitCommandType.Cancel_Research.ordinal(), -1, -1, -1, -1);
		}
		
		public boolean cancelUpgrade() {
			return issueCommand(id, UnitCommandType.Cancel_Upgrade.ordinal(), -1, -1, -1, -1);
		}
		
		public boolean research(TechType techType) {
			return issueCommand(id, UnitCommandType.Research.ordinal(), -1, -1, -1, techType.getId());
		}
		
		public boolean upgrade(UpgradeType upgrade) {
			return issueCommand(id, UnitCommandType.Research.ordinal(), -1, -1, -1, upgrade.getId());
		}
	}
	
	protected class Trainer {
	
		private boolean isTraining;
		private int trainingQueueSize;
		
		public void update(int[] unitData, int index) {
			this.isTraining = unitData[index + Unit.IS_TRAINING_INDEX] == 1;
			this.trainingQueueSize = unitData[index + Unit.TRAINING_QUEUE_SIZE_INDEX];
		}
		
		public boolean isTraining() {
			return this.isTraining;
		}

		public int getTrainingQueueSize() {
			return this.trainingQueueSize;
		}
		
		public boolean train(UnitType type) {
			return issueCommand(id, UnitCommandType.Train.ordinal(), type.getId(), -1, -1, -1);
		}
		
		public boolean cancelTrain(int slot) {
			return issueCommand(id, UnitCommandType.Cancel_Train_Slot.ordinal(), -1, -1, -1, slot);
		}
		
		public boolean cancelTrain() {
			return issueCommand(id, UnitCommandType.Cancel_Train.ordinal(), -1, -1, -1, -1);
		}
		
		public boolean setRallyPoint(Position p) {
			return issueCommand(id, UnitCommandType.Set_Rally_Position.ordinal(), -1, p.getX(), p.getY(), -1);
		}
		
		public boolean setRallyPoint(Unit target) {
			return issueCommand(id, UnitCommandType.Set_Rally_Unit.ordinal(), target.getId(), -1, -1, -1);
		}
	}
	
	protected class Flyer implements FlyingBuilding {
		
		private boolean isLifted;
		
		public void update(int[] unitData, int index) {
			this.isLifted = unitData[index + Unit.IS_LIFTED_INDEX] == 1;
		}
		
		@Override
		public boolean lift() {
			return issueCommand(id, UnitCommandType.Lift.ordinal(), -1, -1, -1, -1);
		}
		
		@Override
		public boolean land(Position p) {
			return issueCommand(id, UnitCommandType.Land.ordinal(), -1, p.getX(), p.getY(), -1);
		}

		@Override
		public boolean move(Position p) {
			return issueCommand(id, UnitCommandType.Move.ordinal(), -1, p.getX(), p.getY(), -1);
		}

		@Override
		public boolean isLifted() {
			return isLifted;
		}
	};
	
	Building(int id, UnitType unitType) {
		super(id, unitType);
	}
}
