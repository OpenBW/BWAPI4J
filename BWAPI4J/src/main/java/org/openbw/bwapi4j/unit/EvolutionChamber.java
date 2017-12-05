package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class EvolutionChamber extends Building implements Organic, ResearchingFacility {

    private Researcher researcher;
    
    protected EvolutionChamber(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Evolution_Chamber, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.researcher.update(unitData, index);
        super.update(unitData, index, frame);
    }
    
    public boolean upgradeMeleeAttacks() {
        
        return this.researcher.upgrade(UpgradeType.Zerg_Melee_Attacks);
    }
    
    public boolean upgradeMissileAttacks() {
        
        return this.researcher.upgrade(UpgradeType.Zerg_Missile_Attacks);
    }
    
    public boolean upgradeCarapace() {
        
        return this.researcher.upgrade(UpgradeType.Zerg_Carapace);
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
