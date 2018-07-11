package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.DamageEvaluator;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.*;

public class RawUnit extends Unit {
    protected RawUnit(int id, UnitType unitType) {
        super(id, unitType);
    }

    @Override
    protected DamageEvaluator getDamageEvaluator() {
        return super.getDamageEvaluator();
    }

    @Override
    protected Player getPlayer(int id) {
        return super.getPlayer(id);
    }

    @Override
    protected Order getOrder() {
        return super.getOrder();
    }

    @Override
    protected Unit getOrderTarget() {
        return super.getOrderTarget();
    }

    @Override
    protected Position getOrderTargetPosition() {
        return super.getOrderTargetPosition();
    }

    @Override
    protected Order getSecondaryOrder() {
        return super.getSecondaryOrder();
    }

    @Override
    protected boolean cancelResearch() {
        return super.cancelResearch();
    }

    @Override
    protected boolean cancelUpgrade() {
        return super.cancelUpgrade();
    }

    @Override
    protected boolean canResearch(TechType techType) {
        return super.canResearch(techType);
    }

    @Override
    protected boolean canUpgrade(UpgradeType upgradeType) {
        return super.canUpgrade(upgradeType);
    }

    @Override
    protected boolean research(TechType techType) {
        return super.research(techType);
    }

    @Override
    protected boolean upgrade(UpgradeType upgrade) {
        return super.upgrade(upgrade);
    }

    @Override
    protected ResearchingFacility.UpgradeInProgress getUpgradeInProgress() {
        return super.getUpgradeInProgress();
    }

    @Override
    protected ResearchingFacility.ResearchInProgress getResearchInProgress() {
        return super.getResearchInProgress();
    }

    @Override
    protected boolean canTrain(UnitType type) {
        return super.canTrain(type);
    }

    @Override
    protected boolean train(UnitType type) {
        return super.train(type);
    }

    @Override
    protected boolean cancelTrain(int slot) {
        return super.cancelTrain(slot);
    }

    @Override
    protected boolean cancelTrain() {
        return super.cancelTrain();
    }

    @Override
    protected boolean setRallyPoint(Position p) {
        return super.setRallyPoint(p);
    }

    @Override
    protected boolean setRallyPoint(Unit target) {
        return super.setRallyPoint(target);
    }

    @Override
    protected Position getRallyPosition() {
        return super.getRallyPosition();
    }

    @Override
    protected Unit getRallyUnit() {
        return super.getRallyUnit();
    }


    @Override
    protected boolean issueCommand(int unitId, UnitCommandType unitCommandType, int targetUnitId, int x, int y, int extra) {
        return super.issueCommand(unitId, unitCommandType, targetUnitId, x, y, extra);
    }
}
