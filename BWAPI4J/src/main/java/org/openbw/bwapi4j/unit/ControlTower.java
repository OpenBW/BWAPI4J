package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class ControlTower extends Addon implements Mechanical, ResearchingFacility {

    private Researcher researcher;

    protected ControlTower(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Control_Tower, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index) {

        this.researcher.update(unitData, index);
        super.update(unitData, index);
    }

    public boolean researchCloakingField() {
        
        return this.researcher.research(TechType.Cloaking_Field);
    }

    public boolean upgradeApolloReactor() {
        
        return this.researcher.upgrade(UpgradeType.Apollo_Reactor);
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
