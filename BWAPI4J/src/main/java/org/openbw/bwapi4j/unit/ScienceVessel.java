package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class ScienceVessel extends MobileUnit implements Mechanical, SpellCaster, Detector {

	private int energy;
	
	public ScienceVessel(int id) {
		super(id, UnitType.Terran_Science_Vessel);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		this.energy = 0;
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		this.energy = unitData[index + Unit.ENERGY_INDEX];
		super.update(unitData, index);
		
		return index;
	}
	
	public boolean defensiveMatrix(PlayerUnit unit) {
		return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), -1, -1, TechType.Defensive_Matrix.getId());
	}
	
	public boolean irradiate(Organic unit) {
		return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), ((Unit)unit).getId(), -1, -1, TechType.Irradiate.getId());
	}
	
	public boolean empShockWave(Position p) {
		return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, p.getX(), p.getY(), TechType.EMP_Shockwave.getId());
	}

	@Override
	public int getEnergy() {
		return this.energy;
	}
}
