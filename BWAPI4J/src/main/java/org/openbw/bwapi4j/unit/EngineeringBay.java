package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class EngineeringBay extends Building implements Mechanical, FlyingBuilding, ResearchingFacility {

    private Flyer flyer;
    private Researcher researcher;

    EngineeringBay(int id, int timeSpotted) {
        super(id, UnitType.Terran_Engineering_Bay, timeSpotted);
        this.flyer = new Flyer();
        this.researcher = new Researcher();
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        this.flyer.update(unitData, index);
        this.researcher.update(unitData, index);
        return super.update(unitData, index);
    }

    @Override
    public boolean isLifted() {
        return this.flyer.isLifted();
    }

    @Override
    public boolean lift() {
        return this.flyer.lift();
    }

    @Override
    public boolean land(Position p) {
        return this.flyer.land(p);
    }

    @Override
    public boolean move(Position p) {
        return this.flyer.move(p);
    }

    public boolean upgradeInfantryWeapons() {
        return this.researcher.upgrade(UpgradeType.Terran_Infantry_Weapons);
    }

    public boolean upgradeInfantryArmor() {
        return this.researcher.upgrade(UpgradeType.Terran_Infantry_Armor);
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
