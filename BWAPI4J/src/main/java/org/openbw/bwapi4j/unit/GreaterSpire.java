package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class GreaterSpire extends Building implements Organic, ResearchingFacility {

    private Researcher researcher;
    
    protected GreaterSpire(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Greater_Spire, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.researcher.update(unitData, index);
        super.update(unitData, index, frame);
    }
    
    public boolean upgradeFlyerAttacks() {
        
        return this.researcher.upgrade(UpgradeType.Zerg_Flyer_Attacks);
    }
    
    public boolean upgradeFlyerCarapace() {
        
        return this.researcher.upgrade(UpgradeType.Zerg_Flyer_Carapace);
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
