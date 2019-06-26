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

package bwapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Player;
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
import org.openbw.bwapi4j.util.buffer.BwapiDataBuffer;
import org.openbw.bwapi4j.util.buffer.DataBuffer;

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
      return issueCommand(
          getID(),
          UnitCommandType.Cancel_Train_Slot,
          UnitCommand.Unused.INTEGER,
          UnitCommand.Unused.INTEGER,
          UnitCommand.Unused.INTEGER,
          this.slotIndex);
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

  @BridgeValue
  @Named(name = "INITIAL_TYPE")
  UnitType initialType;

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

  private native boolean hasPath_native(int unitId, int x, int y);

  public boolean hasPath(Position target) {
    return hasPath_native(getID(), target.getX(), target.getY());
  }

  private native boolean hasPath_native(int unitId, int targetUnitId);

  public boolean hasPath(Unit target) {
    return hasPath_native(getID(), target.getID());
  }

  public int getLastCommandFrame() {
    return lastCommandFrame;
  }

  private native int[] getLastCommand_native(int unitId);

  public UnitCommand getLastCommand() {
    final DataBuffer data = new DataBuffer(getLastCommand_native(getID()));

    return BwapiDataBuffer.readUnitCommand(data, bw);
  }

  public Player getLastAttackingPlayer() {
    return lastAttackingPlayer;
  }

  public UnitType getInitialType() {
    return initialType;
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

  private native int[] getUnitsInRadius_native(int unitId, int radius);

  public List<Unit> getUnitsInRadius(final int radius) {
    final DataBuffer data = new DataBuffer(getUnitsInRadius_native(getID(), radius));

    return BwapiDataBuffer.readUnits(data, bw);
  }

  private native int[] getUnitsInWeaponRange_native(int unitId, int weaponTypeId);

  public List<Unit> getUnitsInWeaponRange(final WeaponType weaponType) {
    final DataBuffer data =
        new DataBuffer(getUnitsInWeaponRange_native(getID(), weaponType.getID()));

    return BwapiDataBuffer.readUnits(data, bw);
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

  private native boolean isInWeaponRange_native(int unitId, int targetUnitId);

  public boolean isInWeaponRange(final Unit target) {
    return isInWeaponRange_native(getID(), target.getID());
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

  private native boolean isVisible_native(int unitId, int playerId);

  public boolean isVisible(final Player player) {
    return isVisible_native(getID(), player.getID());
  }

  public boolean isTargetable() {
    return targetable;
  }

  public boolean issueCommand(final UnitCommand command) {
    final int unitId =
        command.getUnit() != null ? command.getUnit().getID() : UnitCommand.Unused.INTEGER;

    final Unit targetUnit = command.getTarget();
    final int targetUnitId = targetUnit != null ? targetUnit.getID() : UnitCommand.Unused.INTEGER;

    final int x = command.x;
    final int y = command.y;

    final int extra = command.extra;

    return issueCommand(unitId, command.getType(), targetUnitId, x, y, extra);
  }

  public boolean attack(final Position target) {
    return issueCommand(
        getID(),
        UnitCommandType.Attack_Move,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        0);
  }

  public boolean attack(final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Attack_Unit,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean attack(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Attack_Move,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean attack(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Attack_Unit,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean build(final UnitType unitType) {
    return issueCommand(
        getID(),
        UnitCommandType.Build,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        unitType.getID());
  }

  public boolean build(final UnitType unitType, final TilePosition target) {
    // TODO: Double-check that we should pass the XY of a TilePosition and not a Position.
    return issueCommand(
        getID(),
        UnitCommandType.Build,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        unitType.getID());
  }

  public boolean buildAddon(final UnitType unitType) {
    return issueCommand(
        getID(),
        UnitCommandType.Build_Addon,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        unitType.getID());
  }

  private native boolean train_native(int unitId);

  public boolean train() {
    return train_native(getID());
  }

  public boolean train(final UnitType unitType) {
    return issueCommand(
        getID(),
        UnitCommandType.Train,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        unitType.getID());
  }

  public boolean morph(final UnitType unitType) {
    return issueCommand(
        getID(),
        UnitCommandType.Morph,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        unitType.getID());
  }

  public boolean research(final TechType techType) {
    return issueCommand(
        getID(),
        UnitCommandType.Research,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        techType.getID());
  }

  public boolean upgrade(final UpgradeType upgradeType) {
    return issueCommand(
        getID(),
        UnitCommandType.Upgrade,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        upgradeType.getID());
  }

  public boolean setRallyPoint(final Position target) {
    return issueCommand(
        getID(),
        UnitCommandType.Set_Rally_Position,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        UnitCommand.Unused.INTEGER);
  }

  public boolean setRallyPoint(final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Set_Rally_Unit,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean move(final Position target) {
    return issueCommand(
        getID(),
        UnitCommandType.Move,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        UnitCommand.Unused.INTEGER);
  }

  public boolean move(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Move,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean patrol(final Position target) {
    return issueCommand(
        getID(),
        UnitCommandType.Patrol,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        0);
  }

  public boolean patrol(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Patrol,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean holdPosition() {
    return issueCommand(
        getID(),
        UnitCommandType.Hold_Position,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean holdPosition(final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Hold_Position,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean stop() {
    return issueCommand(
        getID(),
        UnitCommandType.Stop,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean stop(final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Stop,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean follow(final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Follow,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean follow(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Follow,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean gather(final Unit resource) {
    return issueCommand(
        getID(),
        UnitCommandType.Gather,
        resource.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean gather(final Unit resource, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Gather,
        resource.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean returnCargo() {
    return issueCommand(
        getID(),
        UnitCommandType.Return_Cargo,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean returnCargo(final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Return_Cargo,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean repair(final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Repair,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean repair(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Repair,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean burrow() {
    return issueCommand(
        getID(),
        UnitCommandType.Burrow,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean unburrow() {
    return issueCommand(
        getID(),
        UnitCommandType.Unburrow,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean cloak() {
    return issueCommand(
        getID(),
        UnitCommandType.Cloak,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean decloak() {
    return issueCommand(
        getID(),
        UnitCommandType.Decloak,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean siege() {
    return issueCommand(
        getID(),
        UnitCommandType.Siege,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean unsiege() {
    return issueCommand(
        getID(),
        UnitCommandType.Unsiege,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean lift() {
    return issueCommand(
        getID(),
        UnitCommandType.Lift,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean land(final TilePosition target) {
    return issueCommand(
        getID(),
        UnitCommandType.Land,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean load(final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Load,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean load(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Load,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean unload(final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Unload,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean unloadAll() {
    return issueCommand(
        getID(),
        UnitCommandType.Unload_All,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean unloadAll(final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Unload_All,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean unloadAll(final Position target) {
    return issueCommand(
        getID(),
        UnitCommandType.Unload_All_Position,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        0);
  }

  public boolean unloadAll(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Unload_All_Position,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean rightClick(final Position target) {
    return issueCommand(
        getID(),
        UnitCommandType.Right_Click_Position,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        0);
  }

  public boolean rightClick(final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Right_Click_Unit,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        0);
  }

  public boolean rightClick(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Right_Click_Position,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        shiftQueueCommand ? 1 : 0);
  }

  public boolean rightClick(final Unit target, final boolean shiftQueueCommand) {
    return issueCommand(
        getID(),
        UnitCommandType.Right_Click_Unit,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        shiftQueueCommand ? 1 : 0);
  }

  public boolean haltConstruction() {
    return issueCommand(
        getID(),
        UnitCommandType.Halt_Construction,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean cancelConstruction() {
    return issueCommand(
        getID(),
        UnitCommandType.Cancel_Construction,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean cancelAddon() {
    return issueCommand(
        getID(),
        UnitCommandType.Cancel_Addon,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean cancelTrain() {
    return issueCommand(
        getID(),
        UnitCommandType.Cancel_Train,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean cancelTrain(final int slot) {
    return issueCommand(
        getID(),
        UnitCommandType.Cancel_Train_Slot,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        slot);
  }

  public boolean cancelMorph() {
    return issueCommand(
        getID(),
        UnitCommandType.Cancel_Morph,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean cancelResearch() {
    return issueCommand(
        getID(),
        UnitCommandType.Cancel_Research,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean cancelUpgrade() {
    return issueCommand(
        getID(),
        UnitCommandType.Cancel_Upgrade,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean useTech(final TechType tech) {
    return issueCommand(
        getID(),
        UnitCommandType.Use_Tech,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  public boolean useTech(final TechType tech, final Position target) {
    return issueCommand(
        getID(),
        UnitCommandType.Use_Tech_Position,
        UnitCommand.Unused.INTEGER,
        target.getX(),
        target.getY(),
        UnitCommand.Unused.INTEGER);
  }

  public boolean useTech(final TechType tech, final Unit target) {
    return issueCommand(
        getID(),
        UnitCommandType.Use_Tech_Unit,
        target.getID(),
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER,
        UnitCommand.Unused.INTEGER);
  }

  private native boolean placeCOP_native(int unitId, int x, int y);

  public boolean placeCOP(final TilePosition target) {
    return placeCOP_native(getID(), target.getX(), target.getY());
  }

  public boolean canIssueCommand(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanBuildUnitType,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanBuildUnitType,
      final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanBuildUnitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final UnitCommand command, final boolean checkCanUseTechPositionOnPositions) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(final UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanBuildUnitType,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final UnitCommand command, final boolean checkCanUseTechPositionOnPositions) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(final UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCommand() {
    return bw.getUnitCommandSimulation().canCommand(getID());
  }

  public boolean canCommandGrouped() {
    return bw.getUnitCommandSimulation().canCommandGrouped(getID());
  }

  public boolean canCommandGrouped(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCommandGrouped(getID(), checkCommandibility);
  }

  public boolean canIssueCommandType(final UnitCommandType ct) {
    return bw.getUnitCommandSimulation().canIssueCommandType(getID(), ct);
  }

  public boolean canIssueCommandType(final UnitCommandType ct, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canIssueCommandType(getID(), ct, checkCommandibility);
  }

  public boolean canIssueCommandTypeGrouped(
      final UnitCommandType ct, final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canIssueCommandTypeGrouped(getID(), ct, checkCommandibilityGrouped);
  }

  public boolean canIssueCommandTypeGrouped(final UnitCommandType ct) {
    return bw.getUnitCommandSimulation().canIssueCommandTypeGrouped(getID(), ct);
  }

  public boolean canIssueCommandTypeGrouped(
      final UnitCommandType ct,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canIssueCommandTypeGrouped(getID(), ct, checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canTargetUnit(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canTargetUnit(getID(), targetUnit);
  }

  public boolean canTargetUnit(final Unit targetUnit, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canTargetUnit(getID(), targetUnit, checkCommandibility);
  }

  public boolean canAttack() {
    return bw.getUnitCommandSimulation().canAttack(getID());
  }

  public boolean canAttack(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canAttack(getID(), checkCommandibility);
  }

  public boolean canAttack(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canAttack(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canAttack(
      final Unit target, final boolean checkCanTargetUnit, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canAttack(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canAttack(final Position target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canAttack(getID(), target, checkCanTargetUnit);
  }

  public boolean canAttack(final Unit target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canAttack(getID(), target, checkCanTargetUnit);
  }

  public boolean canAttack(final Position target) {
    return bw.getUnitCommandSimulation().canAttack(getID(), target);
  }

  public boolean canAttack(final Unit target) {
    return bw.getUnitCommandSimulation().canAttack(getID(), target);
  }

  public boolean canAttack(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttack(
            getID(), target, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canAttack(
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttack(
            getID(), target, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canAttackGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation().canAttackGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canAttackGrouped() {
    return bw.getUnitCommandSimulation().canAttackGrouped(getID());
  }

  public boolean canAttackGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttackGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canAttackGrouped(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canAttackGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped);
  }

  public boolean canAttackGrouped(
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canAttackGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped);
  }

  public boolean canAttackGrouped(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canAttackGrouped(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canAttackGrouped(
      final Unit target, final boolean checkCanTargetUnit, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canAttackGrouped(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canAttackGrouped(final Position target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canAttackGrouped(getID(), target, checkCanTargetUnit);
  }

  public boolean canAttackGrouped(final Unit target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canAttackGrouped(getID(), target, checkCanTargetUnit);
  }

  public boolean canAttackGrouped(final Position target) {
    return bw.getUnitCommandSimulation().canAttackGrouped(getID(), target);
  }

  public boolean canAttackGrouped(final Unit target) {
    return bw.getUnitCommandSimulation().canAttackGrouped(getID(), target);
  }

  public boolean canAttackGrouped(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttackGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped,
            checkCommandibility);
  }

  public boolean canAttackGrouped(
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttackGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped,
            checkCommandibility);
  }

  public boolean canAttackMove() {
    return bw.getUnitCommandSimulation().canAttackMove(getID());
  }

  public boolean canAttackMove(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canAttackMove(getID(), checkCommandibility);
  }

  public boolean canAttackMoveGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation().canAttackMoveGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canAttackMoveGrouped() {
    return bw.getUnitCommandSimulation().canAttackMoveGrouped(getID());
  }

  public boolean canAttackMoveGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttackMoveGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canAttackUnit() {
    return bw.getUnitCommandSimulation().canAttackUnit(getID());
  }

  public boolean canAttackUnit(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canAttackUnit(getID(), checkCommandibility);
  }

  public boolean canAttackUnit(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canAttackUnit(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canAttackUnit(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canAttackUnit(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canAttackUnit(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canAttackUnit(getID(), targetUnit);
  }

  public boolean canAttackUnit(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttackUnit(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canAttackUnitGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation().canAttackUnitGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canAttackUnitGrouped() {
    return bw.getUnitCommandSimulation().canAttackUnitGrouped(getID());
  }

  public boolean canAttackUnitGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttackUnitGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canAttackUnitGrouped(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canAttackUnitGrouped(
            getID(),
            targetUnit,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped);
  }

  public boolean canAttackUnitGrouped(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canAttackUnitGrouped(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canAttackUnitGrouped(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation()
        .canAttackUnitGrouped(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canAttackUnitGrouped(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canAttackUnitGrouped(getID(), targetUnit);
  }

  public boolean canAttackUnitGrouped(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canAttackUnitGrouped(
            getID(),
            targetUnit,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped,
            checkCommandibility);
  }

  public boolean canBuild() {
    return bw.getUnitCommandSimulation().canBuild(getID());
  }

  public boolean canBuild(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canBuild(getID(), checkCommandibility);
  }

  public boolean canBuild(final UnitType uType, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canBuild(getID(), uType, checkCanIssueCommandType);
  }

  public boolean canBuild(final UnitType uType) {
    return bw.getUnitCommandSimulation().canBuild(getID(), uType);
  }

  public boolean canBuild(
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canBuild(getID(), uType, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canBuild(
      final UnitType uType,
      final TilePosition tilePos,
      final boolean checkTargetUnitType,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canBuild(getID(), uType, tilePos, checkTargetUnitType, checkCanIssueCommandType);
  }

  public boolean canBuild(
      final UnitType uType, final TilePosition tilePos, final boolean checkTargetUnitType) {
    return bw.getUnitCommandSimulation().canBuild(getID(), uType, tilePos, checkTargetUnitType);
  }

  public boolean canBuild(final UnitType uType, final TilePosition tilePos) {
    return bw.getUnitCommandSimulation().canBuild(getID(), uType, tilePos);
  }

  public boolean canBuild(
      final UnitType uType,
      final TilePosition tilePos,
      final boolean checkTargetUnitType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canBuild(
            getID(),
            uType,
            tilePos,
            checkTargetUnitType,
            checkCanIssueCommandType,
            checkCommandibility);
  }

  public boolean canBuildAddon() {
    return bw.getUnitCommandSimulation().canBuildAddon(getID());
  }

  public boolean canBuildAddon(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canBuildAddon(getID(), checkCommandibility);
  }

  public boolean canBuildAddon(final UnitType uType, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canBuildAddon(getID(), uType, checkCanIssueCommandType);
  }

  public boolean canBuildAddon(final UnitType uType) {
    return bw.getUnitCommandSimulation().canBuildAddon(getID(), uType);
  }

  public boolean canBuildAddon(
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canBuildAddon(getID(), uType, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canTrain() {
    return bw.getUnitCommandSimulation().canTrain(getID());
  }

  public boolean canTrain(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canTrain(getID(), checkCommandibility);
  }

  public boolean canTrain(final UnitType uType, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canTrain(getID(), uType, checkCanIssueCommandType);
  }

  public boolean canTrain(final UnitType uType) {
    return bw.getUnitCommandSimulation().canTrain(getID(), uType);
  }

  public boolean canTrain(
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canTrain(getID(), uType, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canMorph() {
    return bw.getUnitCommandSimulation().canMorph(getID());
  }

  public boolean canMorph(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canMorph(getID(), checkCommandibility);
  }

  public boolean canMorph(final UnitType uType, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canMorph(getID(), uType, checkCanIssueCommandType);
  }

  public boolean canMorph(final UnitType uType) {
    return bw.getUnitCommandSimulation().canMorph(getID(), uType);
  }

  public boolean canMorph(
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canMorph(getID(), uType, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canResearch() {
    return bw.getUnitCommandSimulation().canResearch(getID());
  }

  public boolean canResearch(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canResearch(getID(), checkCommandibility);
  }

  public boolean canResearch(final TechType type) {
    return bw.getUnitCommandSimulation().canResearch(getID(), type);
  }

  public boolean canResearch(final TechType type, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canResearch(getID(), type, checkCanIssueCommandType);
  }

  public boolean canUpgrade() {
    return bw.getUnitCommandSimulation().canUpgrade(getID());
  }

  public boolean canUpgrade(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canUpgrade(getID(), checkCommandibility);
  }

  public boolean canUpgrade(final UpgradeType type) {
    return bw.getUnitCommandSimulation().canUpgrade(getID(), type);
  }

  public boolean canUpgrade(final UpgradeType type, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canUpgrade(getID(), type, checkCanIssueCommandType);
  }

  public boolean canSetRallyPoint() {
    return bw.getUnitCommandSimulation().canSetRallyPoint(getID());
  }

  public boolean canSetRallyPoint(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canSetRallyPoint(getID(), checkCommandibility);
  }

  public boolean canSetRallyPoint(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canSetRallyPoint(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canSetRallyPoint(
      final Unit target, final boolean checkCanTargetUnit, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canSetRallyPoint(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canSetRallyPoint(final Position target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canSetRallyPoint(getID(), target, checkCanTargetUnit);
  }

  public boolean canSetRallyPoint(final Unit target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canSetRallyPoint(getID(), target, checkCanTargetUnit);
  }

  public boolean canSetRallyPoint(final Position target) {
    return bw.getUnitCommandSimulation().canSetRallyPoint(getID(), target);
  }

  public boolean canSetRallyPoint(final Unit target) {
    return bw.getUnitCommandSimulation().canSetRallyPoint(getID(), target);
  }

  public boolean canSetRallyPoint(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canSetRallyPoint(
            getID(), target, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canSetRallyPoint(
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canSetRallyPoint(
            getID(), target, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canSetRallyPosition() {
    return bw.getUnitCommandSimulation().canSetRallyPosition(getID());
  }

  public boolean canSetRallyPosition(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canSetRallyPosition(getID(), checkCommandibility);
  }

  public boolean canSetRallyUnit() {
    return bw.getUnitCommandSimulation().canSetRallyUnit(getID());
  }

  public boolean canSetRallyUnit(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canSetRallyUnit(getID(), checkCommandibility);
  }

  public boolean canSetRallyUnit(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canSetRallyUnit(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canSetRallyUnit(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canSetRallyUnit(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canSetRallyUnit(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canSetRallyUnit(getID(), targetUnit);
  }

  public boolean canSetRallyUnit(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canSetRallyUnit(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canMove() {
    return bw.getUnitCommandSimulation().canMove(getID());
  }

  public boolean canMove(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canMove(getID(), checkCommandibility);
  }

  public boolean canMoveGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation().canMoveGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canMoveGrouped() {
    return bw.getUnitCommandSimulation().canMoveGrouped(getID());
  }

  public boolean canMoveGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canMoveGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canPatrol() {
    return bw.getUnitCommandSimulation().canPatrol(getID());
  }

  public boolean canPatrol(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canPatrol(getID(), checkCommandibility);
  }

  public boolean canPatrolGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation().canPatrolGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canPatrolGrouped() {
    return bw.getUnitCommandSimulation().canPatrolGrouped(getID());
  }

  public boolean canPatrolGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canPatrolGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canFollow() {
    return bw.getUnitCommandSimulation().canFollow(getID());
  }

  public boolean canFollow(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canFollow(getID(), checkCommandibility);
  }

  public boolean canFollow(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canFollow(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canFollow(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canFollow(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canFollow(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canFollow(getID(), targetUnit);
  }

  public boolean canFollow(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canFollow(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canGather() {
    return bw.getUnitCommandSimulation().canGather(getID());
  }

  public boolean canGather(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canGather(getID(), checkCommandibility);
  }

  public boolean canGather(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canGather(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canGather(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canGather(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canGather(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canGather(getID(), targetUnit);
  }

  public boolean canGather(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canGather(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canReturnCargo() {
    return bw.getUnitCommandSimulation().canReturnCargo(getID());
  }

  public boolean canReturnCargo(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canReturnCargo(getID(), checkCommandibility);
  }

  public boolean canHoldPosition() {
    return bw.getUnitCommandSimulation().canHoldPosition(getID());
  }

  public boolean canHoldPosition(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canHoldPosition(getID(), checkCommandibility);
  }

  public boolean canStop() {
    return bw.getUnitCommandSimulation().canStop(getID());
  }

  public boolean canStop(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canStop(getID(), checkCommandibility);
  }

  public boolean canRepair() {
    return bw.getUnitCommandSimulation().canRepair(getID());
  }

  public boolean canRepair(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canRepair(getID(), checkCommandibility);
  }

  public boolean canRepair(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canRepair(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canRepair(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canRepair(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canRepair(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canRepair(getID(), targetUnit);
  }

  public boolean canRepair(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRepair(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canBurrow() {
    return bw.getUnitCommandSimulation().canBurrow(getID());
  }

  public boolean canBurrow(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canBurrow(getID(), checkCommandibility);
  }

  public boolean canUnburrow() {
    return bw.getUnitCommandSimulation().canUnburrow(getID());
  }

  public boolean canUnburrow(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canUnburrow(getID(), checkCommandibility);
  }

  public boolean canCloak() {
    return bw.getUnitCommandSimulation().canCloak(getID());
  }

  public boolean canCloak(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCloak(getID(), checkCommandibility);
  }

  public boolean canDecloak() {
    return bw.getUnitCommandSimulation().canDecloak(getID());
  }

  public boolean canDecloak(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canDecloak(getID(), checkCommandibility);
  }

  public boolean canSiege() {
    return bw.getUnitCommandSimulation().canSiege(getID());
  }

  public boolean canSiege(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canSiege(getID(), checkCommandibility);
  }

  public boolean canUnsiege() {
    return bw.getUnitCommandSimulation().canUnsiege(getID());
  }

  public boolean canUnsiege(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canUnsiege(getID(), checkCommandibility);
  }

  public boolean canLift() {
    return bw.getUnitCommandSimulation().canLift(getID());
  }

  public boolean canLift(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canLift(getID(), checkCommandibility);
  }

  public boolean canLand() {
    return bw.getUnitCommandSimulation().canLand(getID());
  }

  public boolean canLand(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canLand(getID(), checkCommandibility);
  }

  public boolean canLand(final TilePosition target, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canLand(getID(), target, checkCanIssueCommandType);
  }

  public boolean canLand(final TilePosition target) {
    return bw.getUnitCommandSimulation().canLand(getID(), target);
  }

  public boolean canLand(
      final TilePosition target,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canLand(getID(), target, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canLoad() {
    return bw.getUnitCommandSimulation().canLoad(getID());
  }

  public boolean canLoad(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canLoad(getID(), checkCommandibility);
  }

  public boolean canLoad(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canLoad(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canLoad(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canLoad(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canLoad(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canLoad(getID(), targetUnit);
  }

  public boolean canLoad(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canLoad(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canUnloadWithOrWithoutTarget() {
    return bw.getUnitCommandSimulation().canUnloadWithOrWithoutTarget(getID());
  }

  public boolean canUnloadWithOrWithoutTarget(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canUnloadWithOrWithoutTarget(getID(), checkCommandibility);
  }

  public boolean canUnloadAtPosition(
      final Position targDropPos, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUnloadAtPosition(getID(), targDropPos, checkCanIssueCommandType);
  }

  public boolean canUnloadAtPosition(final Position targDropPos) {
    return bw.getUnitCommandSimulation().canUnloadAtPosition(getID(), targDropPos);
  }

  public boolean canUnloadAtPosition(
      final Position targDropPos,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUnloadAtPosition(getID(), targDropPos, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canUnload() {
    return bw.getUnitCommandSimulation().canUnload(getID());
  }

  public boolean canUnload(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canUnload(getID(), checkCommandibility);
  }

  public boolean canUnload(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkPosition,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUnload(
            getID(), targetUnit, checkCanTargetUnit, checkPosition, checkCanIssueCommandType);
  }

  public boolean canUnload(
      final Unit targetUnit, final boolean checkCanTargetUnit, final boolean checkPosition) {
    return bw.getUnitCommandSimulation()
        .canUnload(getID(), targetUnit, checkCanTargetUnit, checkPosition);
  }

  public boolean canUnload(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canUnload(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canUnload(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canUnload(getID(), targetUnit);
  }

  public boolean canUnload(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkPosition,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUnload(
            getID(),
            targetUnit,
            checkCanTargetUnit,
            checkPosition,
            checkCanIssueCommandType,
            checkCommandibility);
  }

  public boolean canUnloadAll() {
    return bw.getUnitCommandSimulation().canUnloadAll(getID());
  }

  public boolean canUnloadAll(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canUnloadAll(getID(), checkCommandibility);
  }

  public boolean canUnloadAllPosition() {
    return bw.getUnitCommandSimulation().canUnloadAllPosition(getID());
  }

  public boolean canUnloadAllPosition(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canUnloadAllPosition(getID(), checkCommandibility);
  }

  public boolean canUnloadAllPosition(
      final Position targDropPos, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUnloadAllPosition(getID(), targDropPos, checkCanIssueCommandType);
  }

  public boolean canUnloadAllPosition(final Position targDropPos) {
    return bw.getUnitCommandSimulation().canUnloadAllPosition(getID(), targDropPos);
  }

  public boolean canUnloadAllPosition(
      final Position targDropPos,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUnloadAllPosition(getID(), targDropPos, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canRightClick() {
    return bw.getUnitCommandSimulation().canRightClick(getID());
  }

  public boolean canRightClick(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canRightClick(getID(), checkCommandibility);
  }

  public boolean canRightClick(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canRightClick(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canRightClick(
      final Unit target, final boolean checkCanTargetUnit, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canRightClick(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canRightClick(final Position target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canRightClick(getID(), target, checkCanTargetUnit);
  }

  public boolean canRightClick(final Unit target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canRightClick(getID(), target, checkCanTargetUnit);
  }

  public boolean canRightClick(final Position target) {
    return bw.getUnitCommandSimulation().canRightClick(getID(), target);
  }

  public boolean canRightClick(final Unit target) {
    return bw.getUnitCommandSimulation().canRightClick(getID(), target);
  }

  public boolean canRightClick(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClick(
            getID(), target, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canRightClick(
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClick(
            getID(), target, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canRightClickGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation().canRightClickGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canRightClickGrouped() {
    return bw.getUnitCommandSimulation().canRightClickGrouped(getID());
  }

  public boolean canRightClickGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClickGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canRightClickGrouped(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canRightClickGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped);
  }

  public boolean canRightClickGrouped(
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canRightClickGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped);
  }

  public boolean canRightClickGrouped(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canRightClickGrouped(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canRightClickGrouped(
      final Unit target, final boolean checkCanTargetUnit, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canRightClickGrouped(getID(), target, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canRightClickGrouped(final Position target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canRightClickGrouped(getID(), target, checkCanTargetUnit);
  }

  public boolean canRightClickGrouped(final Unit target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canRightClickGrouped(getID(), target, checkCanTargetUnit);
  }

  public boolean canRightClickGrouped(final Position target) {
    return bw.getUnitCommandSimulation().canRightClickGrouped(getID(), target);
  }

  public boolean canRightClickGrouped(final Unit target) {
    return bw.getUnitCommandSimulation().canRightClickGrouped(getID(), target);
  }

  public boolean canRightClickGrouped(
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClickGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped,
            checkCommandibility);
  }

  public boolean canRightClickGrouped(
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClickGrouped(
            getID(),
            target,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped,
            checkCommandibility);
  }

  public boolean canRightClickPosition() {
    return bw.getUnitCommandSimulation().canRightClickPosition(getID());
  }

  public boolean canRightClickPosition(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canRightClickPosition(getID(), checkCommandibility);
  }

  public boolean canRightClickPositionGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canRightClickPositionGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canRightClickPositionGrouped() {
    return bw.getUnitCommandSimulation().canRightClickPositionGrouped(getID());
  }

  public boolean canRightClickPositionGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClickPositionGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canRightClickUnit() {
    return bw.getUnitCommandSimulation().canRightClickUnit(getID());
  }

  public boolean canRightClickUnit(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canRightClickUnit(getID(), checkCommandibility);
  }

  public boolean canRightClickUnit(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnit(getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canRightClickUnit(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canRightClickUnit(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canRightClickUnit(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canRightClickUnit(getID(), targetUnit);
  }

  public boolean canRightClickUnit(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnit(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canRightClickUnitGrouped(final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnitGrouped(getID(), checkCommandibilityGrouped);
  }

  public boolean canRightClickUnitGrouped() {
    return bw.getUnitCommandSimulation().canRightClickUnitGrouped(getID());
  }

  public boolean canRightClickUnitGrouped(
      final boolean checkCommandibilityGrouped, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnitGrouped(getID(), checkCommandibilityGrouped, checkCommandibility);
  }

  public boolean canRightClickUnitGrouped(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnitGrouped(
            getID(),
            targetUnit,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped);
  }

  public boolean canRightClickUnitGrouped(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnitGrouped(
            getID(), targetUnit, checkCanTargetUnit, checkCanIssueCommandType);
  }

  public boolean canRightClickUnitGrouped(final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnitGrouped(getID(), targetUnit, checkCanTargetUnit);
  }

  public boolean canRightClickUnitGrouped(final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canRightClickUnitGrouped(getID(), targetUnit);
  }

  public boolean canRightClickUnitGrouped(
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canRightClickUnitGrouped(
            getID(),
            targetUnit,
            checkCanTargetUnit,
            checkCanIssueCommandType,
            checkCommandibilityGrouped,
            checkCommandibility);
  }

  public boolean canHaltConstruction() {
    return bw.getUnitCommandSimulation().canHaltConstruction(getID());
  }

  public boolean canHaltConstruction(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canHaltConstruction(getID(), checkCommandibility);
  }

  public boolean canCancelConstruction() {
    return bw.getUnitCommandSimulation().canCancelConstruction(getID());
  }

  public boolean canCancelConstruction(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCancelConstruction(getID(), checkCommandibility);
  }

  public boolean canCancelAddon() {
    return bw.getUnitCommandSimulation().canCancelAddon(getID());
  }

  public boolean canCancelAddon(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCancelAddon(getID(), checkCommandibility);
  }

  public boolean canCancelTrain() {
    return bw.getUnitCommandSimulation().canCancelTrain(getID());
  }

  public boolean canCancelTrain(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCancelTrain(getID(), checkCommandibility);
  }

  public boolean canCancelTrainSlot() {
    return bw.getUnitCommandSimulation().canCancelTrainSlot(getID());
  }

  public boolean canCancelTrainSlot(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCancelTrainSlot(getID(), checkCommandibility);
  }

  public boolean canCancelTrainSlot(final int slot, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canCancelTrainSlot(getID(), slot, checkCanIssueCommandType);
  }

  public boolean canCancelTrainSlot(final int slot) {
    return bw.getUnitCommandSimulation().canCancelTrainSlot(getID(), slot);
  }

  public boolean canCancelTrainSlot(
      final int slot, final boolean checkCanIssueCommandType, final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canCancelTrainSlot(getID(), slot, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canCancelMorph() {
    return bw.getUnitCommandSimulation().canCancelMorph(getID());
  }

  public boolean canCancelMorph(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCancelMorph(getID(), checkCommandibility);
  }

  public boolean canCancelResearch() {
    return bw.getUnitCommandSimulation().canCancelResearch(getID());
  }

  public boolean canCancelResearch(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCancelResearch(getID(), checkCommandibility);
  }

  public boolean canCancelUpgrade() {
    return bw.getUnitCommandSimulation().canCancelUpgrade(getID());
  }

  public boolean canCancelUpgrade(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canCancelUpgrade(getID(), checkCommandibility);
  }

  public boolean canUseTechWithOrWithoutTarget() {
    return bw.getUnitCommandSimulation().canUseTechWithOrWithoutTarget(getID());
  }

  public boolean canUseTechWithOrWithoutTarget(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTechWithOrWithoutTarget(getID(), checkCommandibility);
  }

  public boolean canUseTechWithOrWithoutTarget(
      final TechType tech, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUseTechWithOrWithoutTarget(getID(), tech, checkCanIssueCommandType);
  }

  public boolean canUseTechWithOrWithoutTarget(final TechType tech) {
    return bw.getUnitCommandSimulation().canUseTechWithOrWithoutTarget(getID(), tech);
  }

  public boolean canUseTechWithOrWithoutTarget(
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTechWithOrWithoutTarget(
            getID(), tech, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canUseTech(
      final TechType tech,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUseTech(
            getID(), tech, target, checkCanTargetUnit, checkTargetsType, checkCanIssueCommandType);
  }

  public boolean canUseTech(
      final TechType tech,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUseTech(
            getID(), tech, target, checkCanTargetUnit, checkTargetsType, checkCanIssueCommandType);
  }

  public boolean canUseTech(
      final TechType tech,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType) {
    return bw.getUnitCommandSimulation()
        .canUseTech(getID(), tech, target, checkCanTargetUnit, checkTargetsType);
  }

  public boolean canUseTech(
      final TechType tech,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType) {
    return bw.getUnitCommandSimulation()
        .canUseTech(getID(), tech, target, checkCanTargetUnit, checkTargetsType);
  }

  public boolean canUseTech(
      final TechType tech, final Position target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canUseTech(getID(), tech, target, checkCanTargetUnit);
  }

  public boolean canUseTech(
      final TechType tech, final Unit target, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation().canUseTech(getID(), tech, target, checkCanTargetUnit);
  }

  public boolean canUseTech(final TechType tech, final Position target) {
    return bw.getUnitCommandSimulation().canUseTech(getID(), tech, target);
  }

  public boolean canUseTech(final TechType tech, final Unit target) {
    return bw.getUnitCommandSimulation().canUseTech(getID(), tech, target);
  }

  public boolean canUseTech(final TechType tech) {
    return bw.getUnitCommandSimulation().canUseTech(getID(), tech);
  }

  public boolean canUseTech(
      final TechType tech,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTech(
            getID(),
            tech,
            target,
            checkCanTargetUnit,
            checkTargetsType,
            checkCanIssueCommandType,
            checkCommandibility);
  }

  public boolean canUseTech(
      final TechType tech,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTech(
            getID(),
            tech,
            target,
            checkCanTargetUnit,
            checkTargetsType,
            checkCanIssueCommandType,
            checkCommandibility);
  }

  public boolean canUseTechWithoutTarget(
      final TechType tech, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUseTechWithoutTarget(getID(), tech, checkCanIssueCommandType);
  }

  public boolean canUseTechWithoutTarget(final TechType tech) {
    return bw.getUnitCommandSimulation().canUseTechWithoutTarget(getID(), tech);
  }

  public boolean canUseTechWithoutTarget(
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTechWithoutTarget(getID(), tech, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canUseTechUnit(final TechType tech, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canUseTechUnit(getID(), tech, checkCanIssueCommandType);
  }

  public boolean canUseTechUnit(final TechType tech) {
    return bw.getUnitCommandSimulation().canUseTechUnit(getID(), tech);
  }

  public boolean canUseTechUnit(
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTechUnit(getID(), tech, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canUseTechUnit(
      final TechType tech,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsUnits,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUseTechUnit(
            getID(),
            tech,
            targetUnit,
            checkCanTargetUnit,
            checkTargetsUnits,
            checkCanIssueCommandType);
  }

  public boolean canUseTechUnit(
      final TechType tech,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsUnits) {
    return bw.getUnitCommandSimulation()
        .canUseTechUnit(getID(), tech, targetUnit, checkCanTargetUnit, checkTargetsUnits);
  }

  public boolean canUseTechUnit(
      final TechType tech, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return bw.getUnitCommandSimulation()
        .canUseTechUnit(getID(), tech, targetUnit, checkCanTargetUnit);
  }

  public boolean canUseTechUnit(final TechType tech, final Unit targetUnit) {
    return bw.getUnitCommandSimulation().canUseTechUnit(getID(), tech, targetUnit);
  }

  public boolean canUseTechUnit(
      final TechType tech,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsUnits,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTechUnit(
            getID(),
            tech,
            targetUnit,
            checkCanTargetUnit,
            checkTargetsUnits,
            checkCanIssueCommandType,
            checkCommandibility);
  }

  public boolean canUseTechPosition(final TechType tech, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUseTechPosition(getID(), tech, checkCanIssueCommandType);
  }

  public boolean canUseTechPosition(final TechType tech) {
    return bw.getUnitCommandSimulation().canUseTechPosition(getID(), tech);
  }

  public boolean canUseTechPosition(
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTechPosition(getID(), tech, checkCanIssueCommandType, checkCommandibility);
  }

  public boolean canUseTechPosition(
      final TechType tech,
      final Position target,
      final boolean checkTargetsPositions,
      final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation()
        .canUseTechPosition(getID(), tech, target, checkTargetsPositions, checkCanIssueCommandType);
  }

  public boolean canUseTechPosition(
      final TechType tech, final Position target, final boolean checkTargetsPositions) {
    return bw.getUnitCommandSimulation()
        .canUseTechPosition(getID(), tech, target, checkTargetsPositions);
  }

  public boolean canUseTechPosition(final TechType tech, final Position target) {
    return bw.getUnitCommandSimulation().canUseTechPosition(getID(), tech, target);
  }

  public boolean canUseTechPosition(
      final TechType tech,
      final Position target,
      final boolean checkTargetsPositions,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canUseTechPosition(
            getID(),
            tech,
            target,
            checkTargetsPositions,
            checkCanIssueCommandType,
            checkCommandibility);
  }

  public boolean canPlaceCOP() {
    return bw.getUnitCommandSimulation().canPlaceCOP(getID());
  }

  public boolean canPlaceCOP(final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation().canPlaceCOP(getID(), checkCommandibility);
  }

  public boolean canPlaceCOP(final TilePosition target, final boolean checkCanIssueCommandType) {
    return bw.getUnitCommandSimulation().canPlaceCOP(getID(), target, checkCanIssueCommandType);
  }

  public boolean canPlaceCOP(final TilePosition target) {
    return bw.getUnitCommandSimulation().canPlaceCOP(getID(), target);
  }

  public boolean canPlaceCOP(
      final TilePosition target,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return bw.getUnitCommandSimulation()
        .canPlaceCOP(getID(), target, checkCanIssueCommandType, checkCommandibility);
  }
}
