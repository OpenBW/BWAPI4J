package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.DamageEvaluator;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.*;

import java.util.List;

public class RawUnit extends Unit {
    protected RawUnit(int id, UnitType unitType) {
        super(id, unitType);
    }

    public UnitType getType() {
        return type;
    }
    public int getLastCommandFrame() {
        return lastCommandFrame;
    }

    public UnitCommandType getLastCommand() {
        return lastCommand;
    }

    public boolean isUpgrading() {
        return isUpgrading;
    }

    public boolean isResearching() {
        return isResearching;
    }

    public int getInitialHitPoints() {
        return initialHitPoints;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getShields() {
        return shields;
    }

    public boolean isCloaked() {
        return isCloaked;
    }

    public boolean isDetected() {
        return isDetected;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Weapon getGroundWeapon() {
        return groundWeapon;
    }

    public Weapon getAirWeapon() {
        return airWeapon;
    }

    public int getSpellCooldown() {
        return spellCooldown;
    }

    public int getTargetId() {
        return targetId;
    }

    public boolean isAccelerating() {
        return isAccelerating;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isAttackFrame() {
        return isAttackFrame;
    }

    public boolean isBeingConstructed() {
        return isBeingConstructed;
    }

    public boolean isBeingHealed() {
        return isBeingHealed;
    }

    public boolean isIrradiated() {
        return isIrradiated;
    }

    public boolean isLockedDown() {
        return isLockedDown;
    }

    public boolean isMaelstrommed() {
        return isMaelstrommed;
    }

    public boolean isStartingAttack() {
        return isStartingAttack;
    }

    public boolean isUnderAttack() {
        return isUnderAttack;
    }

    public boolean isPowered() {
        return isPowered;
    }

    public boolean isInterruptible() {
        return isInterruptible;
    }

    public int getBuilderId() {
        return builderId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isTraining() {
        return isTraining;
    }

    public int getTrainingQueueSize() {
        return trainingQueueSize;
    }

    public int getRemainingTrainTime() {
        return remainingTrainTime;
    }

    public List<TrainingSlot> getTrainingQueue() {
        return trainingQueue;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public int getInterceptorCount() {
        return interceptorCount;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public boolean isHoldingPosition() {
        return isHoldingPosition;
    }

    public boolean isStuck() {
        return isStuck;
    }

    public boolean isStasised() {
        return isStasised;
    }

    public boolean isUnderDarkSwarm() {
        return isUnderDarkSwarm;
    }

    public boolean isUnderDisruptionWeb() {
        return isUnderDisruptionWeb;
    }

    public boolean isUnderStorm() {
        return isUnderStorm;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isParasited() {
        return isParasited;
    }

    public boolean isPatrolling() {
        return isPatrolling;
    }

    public boolean isPlagued() {
        return isPlagued;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public int getTransportId() {
        return transportId;
    }

    public int getAcidSporeCount() {
        return acidSporeCount;
    }

    public boolean isHallucination() {
        return isHallucination;
    }

    public boolean isBlind() {
        return isBlind;
    }

    public boolean isBraking() {
        return isBraking;
    }

    public boolean isDefenseMatrixed() {
        return isDefenseMatrixed;
    }

    public boolean isEnsnared() {
        return isEnsnared;
    }

    public int getAddonId() {
        return addonId;
    }

    public int getRemainingBuildTime() {
        return remainingBuildTime;
    }

    public boolean isLifted() {
        return isLifted;
    }

    public boolean isBurrowed() {
        return burrowed;
    }

    public int getRemainingMorphTime() {
        return remainingMorphTime;
    }

    public UnitType getBuildType() {
        return buildType;
    }

    public boolean isStimmed() {
        return isStimmed;
    }

    public int getInitialResources() {
        return initialResources;
    }

    public int getResources() {
        return resources;
    }

    public boolean isBeingGathered() {
        return isBeingGathered;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public int getHatcheryId() {
        return hatcheryId;
    }

    public int getLastKnownResources() {
        return lastKnownResources;
    }

    public boolean isHasNuke() {
        return hasNuke;
    }

    public int getNydusExitId() {
        return nydusExitId;
    }

    public int getScarabCount() {
        return scarabCount;
    }

    public boolean isRepairing() {
        return isRepairing;
    }

    public boolean isSieged() {
        return sieged;
    }

    public int getSpiderMineCount() {
        return spiderMineCount;
    }

    public boolean isConstructing() {
        return isConstructing;
    }

    public boolean isGatheringGas() {
        return isGatheringGas;
    }

    public boolean isGatheringMinerals() {
        return isGatheringMinerals;
    }

    public boolean isCarryingGas() {
        return isCarryingGas;
    }

    public boolean isCarryingMinerals() {
        return isCarryingMinerals;
    }

    public Position getLastKnownPosition() {
        return lastKnownPosition;
    }

    public TilePosition getLastKnownTilePosition() {
        return lastKnownTilePosition;
    }

    public int getLastKnownHitPoints() {
        return lastKnownHitPoints;
    }

    @Override
    public Player getPlayer(int id) {
        return super.getPlayer(id);
    }

    @Override
    public Order getOrder() {
        return super.getOrder();
    }

    @Override
    public Unit getOrderTarget() {
        return super.getOrderTarget();
    }

    @Override
    public Position getOrderTargetPosition() {
        return super.getOrderTargetPosition();
    }

    @Override
    public Order getSecondaryOrder() {
        return super.getSecondaryOrder();
    }

    @Override
    public boolean cancelResearch() {
        return super.cancelResearch();
    }

    @Override
    public boolean cancelUpgrade() {
        return super.cancelUpgrade();
    }

    @Override
    public boolean canResearch(TechType techType) {
        return super.canResearch(techType);
    }

    @Override
    public boolean canUpgrade(UpgradeType upgradeType) {
        return super.canUpgrade(upgradeType);
    }

    @Override
    public boolean research(TechType techType) {
        return super.research(techType);
    }

    @Override
    public boolean upgrade(UpgradeType upgrade) {
        return super.upgrade(upgrade);
    }

    @Override
    public ResearchingFacility.UpgradeInProgress getUpgradeInProgress() {
        return super.getUpgradeInProgress();
    }

    @Override
    public ResearchingFacility.ResearchInProgress getResearchInProgress() {
        return super.getResearchInProgress();
    }

    @Override
    public boolean canTrain(UnitType type) {
        return super.canTrain(type);
    }

    @Override
    public boolean train(UnitType type) {
        return super.train(type);
    }

    @Override
    public boolean cancelTrain(int slot) {
        return super.cancelTrain(slot);
    }

    @Override
    public boolean cancelTrain() {
        return super.cancelTrain();
    }

    @Override
    public boolean setRallyPoint(Position p) {
        return super.setRallyPoint(p);
    }

    @Override
    public boolean setRallyPoint(Unit target) {
        return super.setRallyPoint(target);
    }

    @Override
    public Position getRallyPosition() {
        return super.getRallyPosition();
    }

    @Override
    public Unit getRallyUnit() {
        return super.getRallyUnit();
    }

    @Override
    public boolean issueCommand(int unitId, UnitCommandType unitCommandType, int targetUnitId, int x, int y, int extra) {
        return super.issueCommand(unitId, unitCommandType, targetUnitId, x, y, extra);
    }
}
