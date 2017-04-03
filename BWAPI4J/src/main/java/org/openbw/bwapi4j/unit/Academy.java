package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class Academy extends Building implements Mechanical {

	private boolean isUpgrading;
	private boolean isResearching;
	
	Academy(int id) {
		super(id,  UnitType.Terran_Academy);
	}

	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		index = super.update(unitData, index);
		this.isUpgrading = unitData[index++] == 1;
		this.isResearching = unitData[index++] == 1;
		
		return index;
	}
	
	public boolean isUpgrading() {
		return this.isUpgrading;
	}

	public boolean isResearching() {
		return this.isResearching;
	}
	
	public boolean cancelResearch() {
		return issueCommand(this.id, UnitCommandType.Cancel_Research.ordinal(), -1, -1, -1, -1);
	}
	
	public boolean cancelUpgrade() {
		return issueCommand(this.id, UnitCommandType.Cancel_Upgrade.ordinal(), -1, -1, -1, -1);
	}
	
	public boolean researchStimPacks() {
		return issueCommand(this.id, UnitCommandType.Research.ordinal(), -1, -1, -1, TechType.Stim_Packs.getId());
	}
	
	public boolean researchRestoration() {
		return issueCommand(this.id, UnitCommandType.Research.ordinal(), -1, -1, -1, TechType.Restoration.getId());
	}
	
	public boolean researchOpticalFlare() {
		return issueCommand(this.id, UnitCommandType.Research.ordinal(), -1, -1, -1, TechType.Optical_Flare.getId());
	}
	
	public boolean upgradeU238Shells() {
		return issueCommand(this.id, UnitCommandType.Upgrade.ordinal(), -1, -1, -1, UpgradeType.U_238_Shells.getId());
	}
	
	public boolean upgradeCaduceusReactor() {
		return issueCommand(this.id, UnitCommandType.Upgrade.ordinal(), -1, -1, -1, UpgradeType.Caduceus_Reactor.getId());
	}
}
