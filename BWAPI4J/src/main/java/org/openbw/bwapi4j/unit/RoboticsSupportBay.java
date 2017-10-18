package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class RoboticsSupportBay extends Building implements Mechanical, ResearchingFacility {

    private Researcher researcher;
    
    protected RoboticsSupportBay(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Robotics_Support_Bay, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index) {

        this.researcher.update(unitData, index);
        super.update(unitData, index);
    }

	public boolean upgradeGraviticDrive() {

		return this.researcher.upgrade(UpgradeType.Gravitic_Drive);
	}
	
	public boolean upgradeScarabDamage() {

		return this.researcher.upgrade(UpgradeType.Scarab_Damage);
	}
	
	public boolean upgradeReaverCapacity() {

		return this.researcher.upgrade(UpgradeType.Reaver_Capacity);
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
