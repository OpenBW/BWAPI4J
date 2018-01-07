package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class Forge extends Building implements Mechanical, ResearchingFacility {

    private Researcher researcher;
    
    protected Forge(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Forge, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.researcher.update(unitData, index);
        super.update(unitData, index, frame);
    }
    
    public boolean upgradeGroundArmor() {
        
        return this.researcher.upgrade(UpgradeType.Protoss_Ground_Armor);
    }
	    
	public boolean upgradeGroundWeapons() {
	        
	        return this.researcher.upgrade(UpgradeType.Protoss_Ground_Weapons);
	    }
	
	public boolean upgradePlasmaShields() {
	    
	    return this.researcher.upgrade(UpgradeType.Protoss_Plasma_Shields);
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

    @Override
    public boolean canResearch(TechType techType) {
        return this.researcher.canResearch(techType);
    }

    @Override
    public boolean canUpgrade(UpgradeType upgradeType) {
        return this.researcher.canUpgrade(upgradeType);
    }

    @Override
    public boolean research(TechType techType) {
        return this.researcher.research(techType);
    }

    @Override
    public boolean upgrade(UpgradeType upgradeType) {
        return this.researcher.upgrade(upgradeType);
    }
}
