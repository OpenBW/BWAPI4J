package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class Academy extends Building implements Mechanical, ResearchingFacility {

    private Researcher researcher;

    protected Academy(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Academy, timeSpotted);
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

    public boolean researchStimPacks() {
        return this.researcher.research(TechType.Stim_Packs);
    }

    public boolean researchRestoration() {
        return this.researcher.research(TechType.Restoration);
    }

    public boolean researchOpticalFlare() {
        return this.researcher.research(TechType.Optical_Flare);
    }

    public boolean upgradeU238Shells() {
        return this.researcher.upgrade(UpgradeType.U_238_Shells);
    }

    public boolean upgradeCaduceusReactor() {
        return this.researcher.upgrade(UpgradeType.Caduceus_Reactor);
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
