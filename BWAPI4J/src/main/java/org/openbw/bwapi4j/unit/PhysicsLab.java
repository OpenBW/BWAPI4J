package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class PhysicsLab extends Addon implements Mechanical, ResearchingFacility {

    private Researcher researcher;

    PhysicsLab(int id, int timeSpotted) {
        super(id, UnitType.Terran_Physics_Lab, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        this.researcher.update(unitData, index);
        return super.update(unitData, index);
    }

    public boolean researchYamatoGun() {
        return this.researcher.research(TechType.Yamato_Gun);
    }

    public boolean upgradeColossusReactor() {
        return this.researcher.upgrade(UpgradeType.Colossus_Reactor);
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
