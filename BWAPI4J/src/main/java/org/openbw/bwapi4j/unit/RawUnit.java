package org.openbw.bwapi4j.unit;

import java.util.List;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.Order;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class RawUnit extends UnitImpl {
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
    return upgrading;
  }

  public boolean isResearching() {
    return researching;
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
    return cloaked;
  }

  public boolean isDetected() {
    return detected;
  }

  public double getVelocityX() {
    return velocityX;
  }

  public double getVelocityY() {
    return velocityY;
  }

  public boolean isIdle() {
    return idle;
  }

  public boolean isCompleted() {
    return completed;
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

  public Unit getTarget() {
    return target;
  }

  public boolean isAccelerating() {
    return accelerating;
  }

  public boolean isAttacking() {
    return attacking;
  }

  public boolean isAttackFrame() {
    return attackFrame;
  }

  public boolean isBeingConstructed() {
    return beingConstructed;
  }

  public boolean isBeingHealed() {
    return beingHealed;
  }

  public boolean isIrradiated() {
    return irradiated;
  }

  public boolean isLockedDown() {
    return lockedDown;
  }

  public boolean isMaelstrommed() {
    return maelstrommed;
  }

  public boolean isStartingAttack() {
    return startingAttack;
  }

  public boolean isUnderAttack() {
    return underAttack;
  }

  public boolean isPowered() {
    return powered;
  }

  public boolean isInterruptible() {
    return interruptible;
  }

  public Unit getBuildUnit() {
    return buildUnit;
  }

  public Player getPlayer() {
    return player;
  }

  public int getEnergy() {
    return energy;
  }

  public boolean isTraining() {
    return training;
  }

  public int getRemainingTrainTime() {
    return remainingTrainTime;
  }

  public List<TrainingSlot> getTrainingQueue() {
    return trainingQueue;
  }

  public boolean isLoaded() {
    return loaded;
  }

  public int getInterceptorCount() {
    return interceptorCount;
  }

  public boolean isFollowing() {
    return following;
  }

  public boolean isHoldingPosition() {
    return holdingPosition;
  }

  public boolean isStuck() {
    return stuck;
  }

  public boolean isStasised() {
    return stasised;
  }

  public boolean isUnderDarkSwarm() {
    return underDarkSwarm;
  }

  public boolean isUnderDisruptionWeb() {
    return underDisruptionWeb;
  }

  public boolean isUnderStorm() {
    return underStorm;
  }

  public boolean isMoving() {
    return moving;
  }

  public boolean isParasited() {
    return parasited;
  }

  public boolean isPatrolling() {
    return patrolling;
  }

  public boolean isPlagued() {
    return plagued;
  }

  public Position getTargetPosition() {
    return targetPosition;
  }

  public Unit getTransportId() {
    return transport;
  }

  public int getAcidSporeCount() {
    return acidSporeCount;
  }

  public boolean isHallucination() {
    return hallucination;
  }

  public boolean isBlind() {
    return blind;
  }

  public boolean isBraking() {
    return braking;
  }

  public boolean isDefenseMatrixed() {
    return defenseMatrixed;
  }

  public boolean isEnsnared() {
    return ensnared;
  }

  public Unit getAddonId() {
    return addon;
  }

  public int getRemainingBuildTime() {
    return remainingBuildTime;
  }

  public boolean isLifted() {
    return lifted;
  }

  public boolean isBurrowed() {
    return burrowed;
  }

  public UnitType getBuildType() {
    return buildType;
  }

  public boolean isStimmed() {
    return stimmed;
  }

  public int getInitialResources() {
    return initialResources;
  }

  public int getResources() {
    return resources;
  }

  public boolean isBeingGathered() {
    return beingGathered;
  }

  public Unit getCarrierId() {
    return carrier;
  }

  public Unit getHatcheryId() {
    return hatchery;
  }

  public int getLastKnownResources() {
    return lastKnownResources;
  }

  public boolean isHasNuke() {
    return hasNuke;
  }

  public Unit getNydusExitId() {
    return nydusExit;
  }

  public int getScarabCount() {
    return scarabCount;
  }

  public boolean isRepairing() {
    return repairing;
  }

  public boolean isSieged() {
    return sieged;
  }

  public int getSpiderMineCount() {
    return spiderMineCount;
  }

  public boolean isConstructing() {
    return constructing;
  }

  public boolean isGatheringGas() {
    return gatheringGas;
  }

  public boolean isGatheringMinerals() {
    return gatheringMinerals;
  }

  public boolean isCarryingGas() {
    return carryingGas;
  }

  public boolean isCarryingMinerals() {
    return carryingMinerals;
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

  public List<Unit> getLoadedUnits() {
    return loadedUnits;
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
  public boolean issueCommand(
      int unitId, UnitCommandType unitCommandType, int targetUnitId, int x, int y, int extra) {
    return super.issueCommand(unitId, unitCommandType, targetUnitId, x, y, extra);
  }
}
