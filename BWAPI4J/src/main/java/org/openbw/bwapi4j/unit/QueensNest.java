package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class QueensNest extends Building implements Organic, ResearchingFacility {

    private Researcher researcher;
    
    protected QueensNest(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Queens_Nest, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.researcher.update(unitData, index);
        super.update(unitData, index, frame);
    }
    
    public boolean researchEnsnare() {
        
        return this.researcher.research(TechType.Ensnare);
    }
    
    public boolean upgradeGameteMeiosis() {
        
        return this.researcher.upgrade(UpgradeType.Gamete_Meiosis);
    }
    
    public boolean researchSpawnBroodlings() {
        
        return this.researcher.research(TechType.Spawn_Broodlings);
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
