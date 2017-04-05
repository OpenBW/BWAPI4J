package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class Barracks extends Building implements Mechanical, FlyingBuilding, TrainingFacility {

	private Flyer flyer;
	private Trainer trainer;
	
	public Barracks(int id, int timeSpotted) {
		super(id, UnitType.Terran_Barracks, timeSpotted);
		this.flyer = new Flyer();
		this.trainer = new Trainer();
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		return super.initialize(unitData, index, allUnits);
		
	}

	@Override
	public int update(int[] unitData, int index) {
		
		super.update(unitData, index);
		this.flyer.update(unitData, index);
		this.trainer.update(unitData, index);
		
		return index;
	}
	
	public boolean trainMarine() {
		return trainer.train(UnitType.Terran_Marine);
	}

	public boolean trainMedic() {
		return trainer.train(UnitType.Terran_Medic);
	}

	public boolean trainFirebat() {
		return trainer.train(UnitType.Terran_Firebat);
	}

	public boolean trainGhost() {
		return trainer.train(UnitType.Terran_Ghost);
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
	public boolean isLifted() {
		return this.flyer.isLifted();
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
