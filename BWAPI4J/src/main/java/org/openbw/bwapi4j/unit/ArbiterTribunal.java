package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class ArbiterTribunal extends Building implements Mechanical, ResearchingFacility {

    private Researcher researcher;
    
    protected ArbiterTribunal(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Arbiter_Tribunal, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index) {

        this.researcher.update(unitData, index);
        super.update(unitData, index);
    }
    
	public boolean researchStasisField() {

		return this.researcher.research(TechType.Stasis_Field);
	}
	
	public boolean researchRecall() {

		return this.researcher.research(TechType.Recall);
	}

	public boolean upgradeKhaydarinCore() {

		return this.researcher.upgrade(UpgradeType.Khaydarin_Core);
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
