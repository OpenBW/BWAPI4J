package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class MachineShop extends Addon implements Mechanical, ResearchingFacility {

	private Researcher researcher;
	
	public MachineShop(int id, int timeSpotted) {
		super(id, UnitType.Terran_Machine_Shop, timeSpotted);
		this.researcher = new Researcher();
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
	
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		this.researcher.update(unitData, index);
		return super.update(unitData, index);
	}
	
	public boolean researchSiegeMode() {
		return this.researcher.research(TechType.Tank_Siege_Mode);
	}
	
	public boolean researchSpiderMines() {
		return this.researcher.research(TechType.Spider_Mines);
	}
	
	public boolean upgradeIonThrusters() {
		return this.researcher.upgrade(UpgradeType.Ion_Thrusters);
	}
	
	public boolean upgradeCharonBoosters() {
		return this.researcher.upgrade(UpgradeType.Charon_Boosters);
	}
	
	@Override
	public boolean isUpgrading() {
		return this.researcher.isUpgrading();
	}

	@Override
	public boolean isResearching() {
		return this.researcher.isResearching();
	}

	@Override
	public boolean cancelResearch() {
		return this.researcher.cancelResearch();
	}

	@Override
	public boolean cancelUpgrade() {
		return this.researcher.cancelUpgrade();
	}
}
