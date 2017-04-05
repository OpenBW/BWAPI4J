package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class Factory extends Building implements Mechanical, FlyingBuilding, TrainingFacility {

	private Flyer flyer;
	private Trainer trainer;
	
	public Factory(int id, int timeSpotted) {
		super(id, UnitType.Terran_Factory, timeSpotted);
		this.flyer = new Flyer();
		this.trainer = new Trainer();
	}

	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		this.flyer.update(unitData, index);
		this.trainer.update(unitData, index);
		return super.update(unitData, index);
	}
	
	public boolean trainVulture() {
		return this.trainer.train(UnitType.Terran_Vulture);
	}

	public boolean trainSiegeTank() {
		return this.trainer.train(UnitType.Terran_Siege_Tank_Tank_Mode);
	}

	public boolean trainGoliath() {
		return this.trainer.train(UnitType.Terran_Goliath);
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
