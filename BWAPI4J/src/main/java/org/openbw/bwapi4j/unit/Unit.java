////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.PositionOrUnit;
import org.openbw.bwapi4j.Region;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.UnitCommand;
import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.LookedUp;
import org.openbw.bwapi4j.ap.Named;
import org.openbw.bwapi4j.ap.NativeClass;
import org.openbw.bwapi4j.ap.Reset;
import org.openbw.bwapi4j.type.Order;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;

@LookedUp(method = "getUnit")
@NativeClass(name = "BWAPI::Unit")
public class Unit implements Comparable<Unit> {
  public class TrainingSlot {
    private int slotIndex;

    private final UnitType unitType;

    @BridgeValue
    TrainingSlot(final UnitType unitType) {
      this.unitType = unitType;
    }

    public UnitType getUnitType() {
      return this.unitType;
    }

    public boolean cancel() {
      return issueCommand(getID(), UnitCommandType.Cancel_Train_Slot, -1, -1, -1, this.slotIndex);
    }

    @Override
    public boolean equals(final Object object) {
      if (this == object) {
        return true;
      } else if (!(object instanceof TrainingSlot)) {
        return false;
      } else {
        final TrainingSlot trainingSlot = (TrainingSlot) object;
        return (this.slotIndex == trainingSlot.slotIndex && this.unitType == trainingSlot.unitType);
      }
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.slotIndex, this.unitType);
    }
  }

  protected final BW bw;

  @BridgeValue(initializeOnly = true)
  @Named(name = "ID")
  int iD;

  @BridgeValue(accessor = "getPosition()", initializeOnly = true)
  @Named(name = "INITIAL_POSITION")
  Position initialPosition;

  @BridgeValue(accessor = "getTilePosition()", initializeOnly = true)
  @Named(name = "INITIAL_TILE_POSITION")
  TilePosition initialTilePosition;

  @BridgeValue
  @Named(name = "TYPE")
  UnitType type;

  @BridgeValue Position position;
  @BridgeValue TilePosition tilePosition;
  @BridgeValue double angle;

  int lastCommandFrame;
  UnitCommandType lastCommandType;

  @Reset(value = "false")
  @BridgeValue
  boolean visible;

  @Reset(value = "false")
  @BridgeValue(accessor = "exists()")
  boolean exists;

  @BridgeValue boolean selected;
  @BridgeValue boolean flying;
  @BridgeValue boolean upgrading;
  @BridgeValue boolean researching;
  @BridgeValue int remainingResearchTime;
  @BridgeValue int remainingUpgradeTime;
  @BridgeValue UpgradeType upgrade;
  @BridgeValue TechType tech;

  @BridgeValue(initializeOnly = true)
  @Named(name = "INITIAL_HIT_POINTS")
  int initialHitPoints;

  @BridgeValue int hitPoints;
  @BridgeValue int shields;
  @BridgeValue int killCount;
  @BridgeValue boolean cloaked;
  @BridgeValue boolean detected;
  @BridgeValue double velocityX;
  @BridgeValue double velocityY;
  @BridgeValue boolean idle;
  @BridgeValue boolean completed;

  @BridgeValue Weapon groundWeapon = new Weapon(WeaponType.None, 0);

  @BridgeValue Weapon airWeapon = new Weapon(WeaponType.None, 0);

  @BridgeValue int spellCooldown;
  @BridgeValue Unit target;
  @BridgeValue boolean accelerating;
  @BridgeValue boolean attacking;
  @BridgeValue boolean attackFrame;
  @BridgeValue boolean beingConstructed;
  @BridgeValue boolean beingHealed;
  @BridgeValue boolean irradiated;
  @BridgeValue boolean lockedDown;
  @BridgeValue boolean maelstrommed;
  @BridgeValue boolean startingAttack;
  @BridgeValue boolean underAttack;
  @BridgeValue boolean powered;
  @BridgeValue boolean interruptible;
  @BridgeValue Player player;
  @BridgeValue int energy;
  @BridgeValue boolean training;
  @BridgeValue Unit buildUnit;
  @BridgeValue int remainingTrainTime;
  @BridgeValue Position rallyPosition;
  @BridgeValue Unit rallyUnit;
  // The bridge code currently cannot provide "generated" values, so we just provide the slot index
  // here
  @BridgeValue
  List<TrainingSlot> trainingQueue =
      new ArrayList<TrainingSlot>() {
        @Override
        public boolean add(TrainingSlot trainingSlot) {
          trainingSlot.slotIndex = size();
          return super.add(trainingSlot);
        }
      };

  @BridgeValue boolean loaded;
  @BridgeValue int spaceRemaining;
  @BridgeValue List<Unit> loadedUnits = new ArrayList<>();
  @BridgeValue List<Unit> larva = new ArrayList<>();
  @BridgeValue List<Unit> interceptors = new ArrayList<>();
  @BridgeValue int interceptorCount;
  @BridgeValue boolean following;
  @BridgeValue boolean holdingPosition;
  @BridgeValue boolean stuck;
  @BridgeValue boolean stasised;
  @BridgeValue boolean underDarkSwarm;
  @BridgeValue boolean underDisruptionWeb;
  @BridgeValue boolean underStorm;
  @BridgeValue boolean moving;
  @BridgeValue boolean parasited;
  @BridgeValue boolean patrolling;
  @BridgeValue boolean plagued;
  @BridgeValue Position targetPosition;
  @BridgeValue Unit transport;
  @BridgeValue int acidSporeCount;
  @BridgeValue boolean hallucination;
  @BridgeValue boolean blind;
  @BridgeValue boolean braking;
  @BridgeValue boolean defenseMatrixed;
  @BridgeValue boolean ensnared;
  @BridgeValue Unit addon;
  @BridgeValue int remainingBuildTime;
  @BridgeValue boolean lifted;
  @BridgeValue boolean burrowed;
  @BridgeValue UnitType buildType;
  @BridgeValue boolean stimmed;

  @BridgeValue(initializeOnly = true)
  @Named(name = "INITIAL_RESOURCES")
  int initialResources;

  @BridgeValue int resources;
  @BridgeValue boolean beingGathered;
  @BridgeValue Unit carrier;
  @BridgeValue Unit hatchery;

  @BridgeValue(accessor = "hasNuke()")
  boolean hasNuke;

  @BridgeValue Unit nydusExit;
  @BridgeValue int scarabCount;
  @BridgeValue boolean repairing;
  @BridgeValue boolean sieged;
  @BridgeValue int spiderMineCount;
  @BridgeValue boolean constructing;
  @BridgeValue boolean gatheringGas;
  @BridgeValue boolean gatheringMinerals;
  @BridgeValue boolean carryingGas;
  @BridgeValue boolean carryingMinerals;

  @BridgeValue int stimTimer;

  @BridgeValue int replayID;
  @BridgeValue int resourceGroup;
  @BridgeValue Player lastAttackingPlayer;
  @BridgeValue int defenseMatrixPoints;
  @BridgeValue int defenseMatrixTimer;
  @BridgeValue int ensnareTimer;
  @BridgeValue int irradiateTimer;
  @BridgeValue int lockdownTimer;
  @BridgeValue int maelstromTimer;
  @BridgeValue int orderTimer;
  @BridgeValue int plagueTimer;
  @BridgeValue int removeTimer;
  @BridgeValue int stasisTimer;

  @BridgeValue Order order;
  @BridgeValue Unit orderTarget;
  @BridgeValue Position orderTargetPosition;
  @BridgeValue Order secondaryOrder;

  @BridgeValue boolean morphing;
  @BridgeValue boolean targetable;
  @BridgeValue boolean invincible;

  private final int initiallySpotted;

  public Unit(final BW bw, final int id, final UnitType type, final int initiallySpotted) {
    this.bw = bw;
    this.iD = id;
    this.type = type;
    this.initiallySpotted = initiallySpotted;
  }

  public int getInitiallySpotted() {
    return initiallySpotted;
  }

  public Position getMiddle(Unit unit) {
    int x = this.getPosition().getX();
    int y = this.getPosition().getY();

    int dx = unit.getPosition().getX() - x;
    int dy = unit.getPosition().getY() - y;

    return new Position(x + dx / 2, y + dy / 2);
  }

  public <T extends Unit> T getClosest(Collection<T> group) {
    Comparator<T> comp = Comparator.comparingDouble(this::getDistance);
    return group.stream().min(comp).get();
  }

  public <T extends Unit> List<T> getUnitsInRadius(int radius, Collection<T> group) {
    return group.stream().filter(t -> this.getDistance(t) <= radius).collect(Collectors.toList());
  }

  public Weapon getGroundWeapon() {
    return groundWeapon;
  }

  public Weapon getAirWeapon() {
    return airWeapon;
  }

  protected int getCurrentFrame() {
    return bw.getInteractionHandler().getFrameCount();
  }

  public UnitCommandType getLastCommandType() {
    return lastCommandType;
  }

  @Override
  public int hashCode() {
    return getID();
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    } else if (object instanceof Unit) {
      final Unit that = (Unit) object;
      return this.getID() == that.getID();
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return getID() + ":" + getType();
  }

  @Override
  public int compareTo(final Unit other) {
    return this.getID() - other.getID();
  }

  protected boolean issueCommand(
      final int unitId,
      final UnitCommandType unitCommandType,
      final int targetUnitId,
      final int x,
      final int y,
      final int extra) {
    if (issueCommand_native(unitId, unitCommandType.ordinal(), targetUnitId, x, y, extra)) {
      lastCommandFrame = getCurrentFrame();
      lastCommandType = unitCommandType;
      return true;
    } else {
      return false;
    }
  }

  private native boolean issueCommand_native(
      int unitId, int unitCommandTypeId, int targetUnitId, int x, int y, int extra);

  public int getID() {
    return iD;
  }

  public boolean exists() {
    return exists;
  }

  public int getReplayID() {
    return replayID;
  }

  public Player getPlayer() {
    return player;
  }

  public UnitType getType() {
    return type;
  }

  public Position getPosition() {
    return position;
  }

  public TilePosition getTilePosition() {
    return tilePosition;
  }

  public double getAngle() {
    return angle;
  }

  public double getVelocityX() {
    return velocityX;
  }

  public double getVelocityY() {
    return velocityY;
  }

  public Region getRegion() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getLeft() {
    return getPosition().getX() - getType().dimensionLeft();
  }

  public int getTop() {
    return getPosition().getY() - getType().dimensionUp();
  }

  public int getRight() {
    return getPosition().getX() + getType().dimensionRight();
  }

  public int getBottom() {
    return getPosition().getY() + getType().dimensionDown();
  }

  public int getHitPoints() {
    return hitPoints;
  }

  public int getShields() {
    return shields;
  }

  public int getEnergy() {
    return energy;
  }

  public int getResources() {
    return resources;
  }

  public int getResourceGroup() {
    return resourceGroup;
  }

  public int getDistance(final int x, final int y) {
    int xDist = getLeft() - (x + 1);
    if (xDist < 0) {
      xDist = x - (getRight() + 1);
      if (xDist < 0) {
        xDist = 0;
      }
    }
    int yDist = getTop() - (y + 1);
    if (yDist < 0) {
      yDist = y - (getBottom() + 1);
      if (yDist < 0) {
        yDist = 0;
      }
    }

    return new Position(0, 0).getDistance(new Position(xDist, yDist));
  }

  public int getDistance(final Position target) {
    return getDistance(target.getX(), target.getY());
  }

  public int getDistance(final Unit target) {
    if (getPosition().equals(target.getPosition())) {
      return 0;
    }

    int xDist = getLeft() - (target.getRight() + 1);
    if (xDist < 0) {
      xDist = target.getLeft() - (getRight() + 1);
      if (xDist < 0) {
        xDist = 0;
      }
    }
    int yDist = getTop() - (target.getBottom() + 1);
    if (yDist < 0) {
      yDist = target.getTop() - (getBottom() + 1);
      if (yDist < 0) {
        yDist = 0;
      }
    }

    return new Position(0, 0).getDistance(new Position(xDist, yDist));
  }

  public int getDistance(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPath(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPath(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasPath(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getLastCommandFrame() {
    return lastCommandFrame;
  }

  public UnitCommand getLastCommand() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Player getLastAttackingPlayer() {
    return lastAttackingPlayer;
  }

  public UnitType getInitialType() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Position getInitialPosition() {
    return initialPosition;
  }

  public TilePosition getInitialTilePosition() {
    return initialTilePosition;
  }

  public int getInitialHitPoints() {
    return initialHitPoints;
  }

  public int getInitialResources() {
    return initialResources;
  }

  public int getKillCount() {
    return killCount;
  }

  public int getAcidSporeCount() {
    return acidSporeCount;
  }

  public int getInterceptorCount() {
    return interceptorCount;
  }

  public int getScarabCount() {
    return scarabCount;
  }

  public int getSpiderMineCount() {
    return spiderMineCount;
  }

  public int getGroundWeaponCooldown() {
    return getGroundWeapon().cooldown();
  }

  public int getAirWeaponCooldown() {
    return getAirWeapon().cooldown();
  }

  public int getSpellCooldown() {
    return spellCooldown;
  }

  public int getDefenseMatrixPoints() {
    return defenseMatrixPoints;
  }

  public int getDefenseMatrixTimer() {
    return defenseMatrixTimer;
  }

  public int getEnsnareTimer() {
    return ensnareTimer;
  }

  public int getIrradiateTimer() {
    return irradiateTimer;
  }

  public int getLockdownTimer() {
    return lockdownTimer;
  }

  public int getMaelstromTimer() {
    return maelstromTimer;
  }

  public int getOrderTimer() {
    return orderTimer;
  }

  public int getPlagueTimer() {
    return plagueTimer;
  }

  public int getRemoveTimer() {
    return removeTimer;
  }

  public int getStasisTimer() {
    return stasisTimer;
  }

  public int getStimTimer() {
    return stimTimer;
  }

  public UnitType getBuildType() {
    return buildType;
  }

  public List<UnitType> getTrainingQueue() {
    final List<UnitType> unitTypeQueue = new ArrayList<>();

    for (final TrainingSlot slot : trainingQueue) {
      unitTypeQueue.add(slot.getUnitType());
    }

    return unitTypeQueue;
  }

  public TechType getTech() {
    return tech;
  }

  public UpgradeType getUpgrade() {
    return upgrade;
  }

  public int getRemainingBuildTime() {
    return remainingBuildTime;
  }

  public int getRemainingTrainTime() {
    return remainingTrainTime;
  }

  public int getRemainingResearchTime() {
    return remainingResearchTime;
  }

  public int getRemainingUpgradeTime() {
    return remainingUpgradeTime;
  }

  public Unit getBuildUnit() {
    return buildUnit;
  }

  public Unit getTarget() {
    return target;
  }

  public Position getTargetPosition() {
    return targetPosition;
  }

  public Order getOrder() {
    return order;
  }

  public Order getSecondaryOrder() {
    return secondaryOrder;
  }

  public Unit getOrderTarget() {
    return orderTarget;
  }

  public Position getOrderTargetPosition() {
    return orderTargetPosition;
  }

  public Position getRallyPosition() {
    return rallyPosition;
  }

  public Unit getRallyUnit() {
    return rallyUnit;
  }

  public Unit getAddon() {
    return addon;
  }

  public Unit getNydusExit() {
    return nydusExit;
  }

  public Unit getPowerUp() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Unit getTransport() {
    return transport;
  }

  public List<Unit> getLoadedUnits() {
    return loadedUnits;
  }

  public int getSpaceRemaining() {
    return spaceRemaining;
  }

  public Unit getCarrier() {
    return carrier;
  }

  public List<Unit> getInterceptors() {
    return interceptors;
  }

  public Unit getHatchery() {
    return hatchery;
  }

  public List<Unit> getLarva() {
    return larva;
  }

  public List<Unit> getUnitsInRadius(int radius) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Unit> getUnitsInWeaponRange(WeaponType weapon) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean hasNuke() {
    return hasNuke;
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

  public boolean isBeingGathered() {
    return beingGathered;
  }

  public boolean isBeingHealed() {
    return beingHealed;
  }

  public boolean isBlind() {
    return blind;
  }

  public boolean isBraking() {
    return braking;
  }

  public boolean isBurrowed() {
    return burrowed;
  }

  public boolean isCarryingGas() {
    return carryingGas;
  }

  public boolean isCarryingMinerals() {
    return carryingMinerals;
  }

  public boolean isCloaked() {
    return cloaked;
  }

  public boolean isCompleted() {
    return completed;
  }

  public boolean isConstructing() {
    return constructing;
  }

  public boolean isDefenseMatrixed() {
    return defenseMatrixed;
  }

  public boolean isDetected() {
    return detected;
  }

  public boolean isEnsnared() {
    return ensnared;
  }

  public boolean isFlying() {
    return flying;
  }

  public boolean isFollowing() {
    return following;
  }

  public boolean isGatheringGas() {
    return gatheringGas;
  }

  public boolean isGatheringMinerals() {
    return gatheringMinerals;
  }

  public boolean isHallucination() {
    return hallucination;
  }

  public boolean isHoldingPosition() {
    return holdingPosition;
  }

  public boolean isIdle() {
    return idle;
  }

  public boolean isInterruptible() {
    return interruptible;
  }

  public boolean isInvincible() {
    return invincible;
  }

  public boolean isInWeaponRange(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isIrradiated() {
    return irradiated;
  }

  public boolean isLifted() {
    return lifted;
  }

  public boolean isLoaded() {
    return loaded;
  }

  public boolean isLockedDown() {
    return lockedDown;
  }

  public boolean isMaelstrommed() {
    return maelstrommed;
  }

  public boolean isMorphing() {
    return morphing;
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

  public boolean isRepairing() {
    return repairing;
  }

  public boolean isResearching() {
    return researching;
  }

  public boolean isSelected() {
    return selected;
  }

  public boolean isSieged() {
    return sieged;
  }

  public boolean isStartingAttack() {
    return startingAttack;
  }

  public boolean isStasised() {
    return stasised;
  }

  public boolean isStimmed() {
    return stimmed;
  }

  public boolean isStuck() {
    return stuck;
  }

  public boolean isTraining() {
    return training;
  }

  public boolean isUnderAttack() {
    return underAttack;
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

  public boolean isPowered() {
    return powered;
  }

  public boolean isUpgrading() {
    return upgrading;
  }

  public boolean isVisible() {
    return visible;
  }

  public boolean isVisible(Player player) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean isTargetable() {
    return targetable;
  }

  public boolean issueCommand(UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean attack(final Position target) {
    return issueCommand(getID(), UnitCommandType.Attack_Move, -1, target.getX(), target.getY(), 0);
  }

  public boolean attack(final Unit target) {
    return issueCommand(getID(), UnitCommandType.Attack_Unit, target.getID(), -1, -1, 0);
  }

  public boolean attack(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean attack(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Attack_Move,
        -1,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean attack(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Attack_Unit, target.getID(), -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean attack(final PositionOrUnit target, final boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean build(final UnitType unitType) {
    return issueCommand(getID(), UnitCommandType.Build, -1, -1, -1, unitType.getID());
  }

  public boolean build(final UnitType unitType, final TilePosition target) {
    // TODO: Double-check that we should pass the XY of a TilePosition and not a Position.
    return issueCommand(
        getID(), UnitCommandType.Build, -1, target.getX(), target.getY(), unitType.getID());
  }

  public boolean buildAddon(final UnitType unitType) {
    return issueCommand(getID(), UnitCommandType.Build_Addon, -1, -1, -1, unitType.getID());
  }

  public boolean train() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean train(final UnitType unitType) {
    return issueCommand(getID(), UnitCommandType.Train, -1, -1, -1, unitType.getID());
  }

  public boolean morph(final UnitType unitType) {
    return issueCommand(getID(), UnitCommandType.Morph, -1, -1, -1, unitType.getID());
  }

  public boolean research(final TechType techType) {
    return issueCommand(getID(), UnitCommandType.Research, -1, -1, -1, techType.getID());
  }

  public boolean upgrade(final UpgradeType upgradeType) {
    return issueCommand(getID(), UnitCommandType.Upgrade, -1, -1, -1, upgradeType.getID());
  }

  public boolean setRallyPoint(final Position target) {
    return issueCommand(
        getID(), UnitCommandType.Set_Rally_Position, -1, target.getX(), target.getY(), -1);
  }

  public boolean setRallyPoint(final Unit target) {
    return issueCommand(getID(), UnitCommandType.Set_Rally_Unit, target.getID(), -1, -1, -1);
  }

  public boolean setRallyPoint(final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean move(final Position target) {
    return issueCommand(getID(), UnitCommandType.Move, -1, target.getX(), target.getY(), -1);
  }

  public boolean move(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Move, -1, target.getX(), target.getY(), shiftQueueCommand ? 1 : 0);
  }

  public boolean patrol(final Position target) {
    return issueCommand(getID(), UnitCommandType.Patrol, -1, target.getX(), target.getY(), 0);
  }

  public boolean patrol(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Patrol,
        -1,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean holdPosition() {
    return issueCommand(getID(), UnitCommandType.Hold_Position, -1, -1, -1, 0);
  }

  public boolean holdPosition(final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Hold_Position, -1, -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean stop() {
    return issueCommand(getID(), UnitCommandType.Stop, -1, -1, -1, 0);
  }

  public boolean stop(final boolean shiftQueueCommand) {
    return issueCommand(getID(), UnitCommandType.Stop, -1, -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean follow(final Unit target) {
    return issueCommand(getID(), UnitCommandType.Follow, target.getID(), -1, -1, 0);
  }

  public boolean follow(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Follow, target.getID(), -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean gather(final Unit resource) {
    return issueCommand(getID(), UnitCommandType.Gather, resource.getID(), -1, -1, 0);
  }

  public boolean gather(final Unit resource, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Gather, resource.getID(), -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean returnCargo() {
    return issueCommand(getID(), UnitCommandType.Return_Cargo, -1, -1, -1, 0);
  }

  public boolean returnCargo(final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Return_Cargo, -1, -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean repair(final Unit target) {
    return issueCommand(getID(), UnitCommandType.Repair, target.getID(), -1, -1, 0);
  }

  public boolean repair(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Repair, target.getID(), -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean burrow() {
    return issueCommand(getID(), UnitCommandType.Burrow, -1, -1, -1, -1);
  }

  public boolean unburrow() {
    return issueCommand(getID(), UnitCommandType.Unburrow, -1, -1, -1, -1);
  }

  public boolean cloak() {
    return issueCommand(getID(), UnitCommandType.Cloak, -1, -1, -1, -1);
  }

  public boolean decloak() {
    return issueCommand(getID(), UnitCommandType.Decloak, -1, -1, -1, -1);
  }

  public boolean siege() {
    return issueCommand(getID(), UnitCommandType.Siege, -1, -1, -1, -1);
  }

  public boolean unsiege() {
    return issueCommand(getID(), UnitCommandType.Unsiege, -1, -1, -1, -1);
  }

  public boolean lift() {
    return issueCommand(getID(), UnitCommandType.Lift, -1, -1, -1, -1);
  }

  public boolean land(final TilePosition target) {
    return issueCommand(getID(), UnitCommandType.Land, -1, -1, -1, -1);
  }

  public boolean load(final Unit target) {
    return issueCommand(getID(), UnitCommandType.Load, target.getID(), -1, -1, 0);
  }

  public boolean load(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(), UnitCommandType.Load, target.getID(), -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean unload(final Unit target) {
    return issueCommand(getID(), UnitCommandType.Unload, target.getID(), -1, -1, -1);
  }

  public boolean unloadAll() {
    return issueCommand(getID(), UnitCommandType.Unload_All, -1, -1, -1, 0);
  }

  public boolean unloadAll(final boolean shiftQueueCommand) {
    return issueCommand(getID(), UnitCommandType.Unload_All, -1, -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean unloadAll(final Position target) {
    return issueCommand(
        getID(), UnitCommandType.Unload_All_Position, -1, target.getX(), target.getY(), 0);
  }

  public boolean unloadAll(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Unload_All_Position,
        -1,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean rightClick(final Position target) {
    return issueCommand(
        getID(), UnitCommandType.Right_Click_Position, -1, target.getX(), target.getY(), 0);
  }

  public boolean rightClick(final Unit target) {
    return issueCommand(getID(), UnitCommandType.Right_Click_Unit, target.getID(), -1, -1, 0);
  }

  public boolean rightClick(final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean rightClick(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Right_Click_Position,
        -1,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean rightClick(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Right_Click_Unit,
        target.getID(),
        -1,
        -1,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean rightClick(final PositionOrUnit target, final boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean haltConstruction() {
    return issueCommand(getID(), UnitCommandType.Halt_Construction, -1, -1, -1, -1);
  }

  public boolean cancelConstruction() {
    return issueCommand(getID(), UnitCommandType.Cancel_Construction, -1, -1, -1, -1);
  }

  public boolean cancelAddon() {
    return issueCommand(getID(), UnitCommandType.Cancel_Addon, -1, -1, -1, -1);
  }

  public boolean cancelTrain() {
    return issueCommand(getID(), UnitCommandType.Cancel_Train, -1, -1, -1, -1);
  }

  public boolean cancelTrain(final int slot) {
    return issueCommand(getID(), UnitCommandType.Cancel_Train_Slot, -1, -1, -1, slot);
  }

  public boolean cancelMorph() {
    return issueCommand(getID(), UnitCommandType.Cancel_Morph, -1, -1, -1, -1);
  }

  public boolean cancelResearch() {
    return issueCommand(getID(), UnitCommandType.Cancel_Research, -1, -1, -1, -1);
  }

  public boolean cancelUpgrade() {
    return issueCommand(getID(), UnitCommandType.Cancel_Upgrade, -1, -1, -1, -1);
  }

  public boolean useTech(final TechType tech) {
    return issueCommand(getID(), UnitCommandType.Use_Tech, -1, -1, -1, -1);
  }

  public boolean useTech(final TechType tech, final Position target) {
    return issueCommand(
        getID(), UnitCommandType.Use_Tech_Position, -1, target.getX(), target.getY(), -1);
  }

  public boolean useTech(final TechType tech, final Unit target) {
    return issueCommand(getID(), UnitCommandType.Use_Tech_Unit, target.getID(), -1, -1, -1);
  }

  public boolean useTech(final TechType tech, final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean placeCOP(TilePosition target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }
}
