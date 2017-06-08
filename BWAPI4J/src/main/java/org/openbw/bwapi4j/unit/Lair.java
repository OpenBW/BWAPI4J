package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class Lair extends Building implements Organic, ResearchingFacility {

    private Researcher researcher;
    
    protected Lair(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Lair, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        super.update(unitData, index);
        this.researcher.update(unitData, index);

        return index;
    }
    
    public boolean researchBurrowing() {
        
        return this.researcher.research(TechType.Burrowing);
    }
    
    public boolean upgradeVentralSacs() {
        
        return this.researcher.upgrade(UpgradeType.Ventral_Sacs);
    }
    
    public boolean upgradeAntennae() {
        
        return this.researcher.upgrade(UpgradeType.Antennae);
    }
    
    public boolean upgradePneumatizedCarapace() {
        
        return this.researcher.upgrade(UpgradeType.Pneumatized_Carapace);
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
    
    public boolean morph() {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Hive.getId());
    }
}
