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

// TODO: Remove static imports...
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Research;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Train;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Train_Slot;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Upgrade;
import static org.openbw.bwapi4j.type.UnitCommandType.Gather;
import static org.openbw.bwapi4j.type.UnitCommandType.Land;
import static org.openbw.bwapi4j.type.UnitCommandType.Lift;
import static org.openbw.bwapi4j.type.UnitCommandType.Move;
import static org.openbw.bwapi4j.type.UnitCommandType.Research;
import static org.openbw.bwapi4j.type.UnitCommandType.Set_Rally_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Set_Rally_Unit;
import static org.openbw.bwapi4j.type.UnitCommandType.Train;
import static org.openbw.bwapi4j.type.UnitCommandType.Upgrade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.DamageEvaluator;
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
import org.openbw.bwapi4j.type.UnitSizeType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;

@LookedUp(method = "getUnit")
@NativeClass(name = "BWAPI::Unit")
public class Unit implements Comparable<Unit> {

  private static final Logger logger = LogManager.getLogger();

  @BridgeValue(initializeOnly = true)
  @Named(name = "ID")
  int iD;

  @BridgeValue(accessor = "getPosition()", initializeOnly = true)
  @Named(name = "INITIAL_POSITION")
  Position initialPosition;

  @BridgeValue(accessor = "getTilePosition()", initializeOnly = true)
  @Named(name = "INITIAL_TILE_POSITION")
  TilePosition initialTilePosition;

  int initiallySpotted;

  @Named(name = "TYPE")
  @BridgeValue(initializeOnly = true)
  UnitType type;

  // dynamic
  @BridgeValue Position position;
  @BridgeValue TilePosition tilePosition;
  @BridgeValue double angle;
  int lastCommandFrame;
  @BridgeValue UnitCommandType lastCommand;

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

  // static
  @BridgeValue(initializeOnly = true)
  @Named(name = "INITIAL_HIT_POINTS")
  int initialHitPoints;

  // dynamic
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
  int lastKnownResources;

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

  // other
  Position lastKnownPosition;
  TilePosition lastKnownTilePosition;
  int lastKnownHitPoints;

  // internal
  private BW bw;
  int lastSpotted;

  public Unit(final int id, final UnitType type, final int lastSpotted) {
    this.iD = id;
    this.type = type;
    this.lastSpotted = lastSpotted;
  }

  // TODO: Remove this function and mark "bw" object as final. Add to constructor.
  final void setBW(BW bw) {
    this.bw = bw;
  }

  public int getLastSpotted() {
    return this.lastSpotted;
  }

  public int getInitiallySpotted() {
    return initiallySpotted;
  }

  protected Collection<Unit> getAllUnits() {
    return bw.getAllUnits();
  }

  protected Unit getUnit(int id) {
    return bw.getUnit(id);
  }

  protected DamageEvaluator getDamageEvaluator() {
    return bw.getDamageEvaluator();
  }

  protected Player getPlayer(int id) {
    return bw.getPlayer(id);
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

  public int getX() {
    return this.position.getX();
  }

  public int getY() {
    return this.position.getY();
  }

  public int height() {
    return this.type.height();
  }

  public int width() {
    return this.type.width();
  }

  public int tileHeight() {
    return this.type.tileHeight();
  }

  public int tileWidth() {
    return this.type.tileWidth();
  }

  public UnitSizeType getSize() {
    return type.size();
  }

  public double getDistance(Position target) {
    return getDistance(target.getX(), target.getY());
  }

  public double getDistance(int x, int y) {
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

  public int getDistance(Unit target) {
    if (this.position == target.getPosition()) {
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
    logger.trace("dx, dy: {}, {}.", xDist, yDist);

    return new Position(0, 0).getDistance(new Position(xDist, yDist));
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

  @Override
  public int hashCode() {
    return this.iD;
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
      int unitId, UnitCommandType unitCommandType, int targetUnitId, int x, int y, int extra) {
    if (issueCommand_native(unitId, unitCommandType.ordinal(), targetUnitId, x, y, extra)) {
      lastCommandFrame = getCurrentFrame();
      lastCommand = unitCommandType;
      return true;
    } else {
      return false;
    }
  }

  private native boolean issueCommand_native(
      int unitId, int unitCommandTypeId, int targetUnitId, int x, int y, int extra);

  // --------------------------------------------------
  // dynamic

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

  Order secondaryOrder;
  @BridgeValue boolean morphing;
  @BridgeValue boolean targetable;
  @BridgeValue boolean invincible;

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
      return issueCommand(iD, Cancel_Train_Slot, -1, -1, -1, this.slotIndex);
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

  public int getDistance(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getDistance(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
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
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getAirWeaponCooldown() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getSpellCooldown() {
    throw new UnsupportedOperationException("TODO"); // TODO
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
    throw new UnsupportedOperationException("TODO"); // TODO
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
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public int getSpaceRemaining() {
    return spaceRemaining;
  }

  public Unit getCarrier() {
    return carrier;
  }

  public List<Unit> getInterceptors() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public Unit getHatchery() {
    return hatchery;
  }

  public List<Unit> getLarva() {
    throw new UnsupportedOperationException("TODO"); // TODO
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

  public boolean attack(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean attack(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean attack(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean attack(Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean attack(Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean attack(PositionOrUnit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean build(UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean build(UnitType type, TilePosition target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean buildAddon(UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean train() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean train(final UnitType unitType) {
    return issueCommand(iD, Train, -1, -1, -1, unitType.getId());
  }

  public boolean morph(UnitType type) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean research(final TechType techType) {
    return issueCommand(iD, Research, -1, -1, -1, techType.getId());
  }

  public boolean upgrade(final UpgradeType upgradeType) {
    return issueCommand(iD, Upgrade, -1, -1, -1, upgradeType.getId());
  }

  public boolean setRallyPoint(final Position target) {
    return issueCommand(iD, Set_Rally_Position, -1, target.getX(), target.getY(), -1);
  }

  public boolean setRallyPoint(final Unit target) {
    return issueCommand(iD, Set_Rally_Unit, target.getID(), -1, -1, -1);
  }

  public boolean setRallyPoint(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean move(final Position target) {
    return issueCommand(iD, Move, -1, target.getX(), target.getY(), -1);
  }

  public boolean move(final Position target, final boolean shiftQueueCommand) {
    return issueCommand(iD, Move, -1, target.getX(), target.getY(), shiftQueueCommand ? 1 : 0);
  }

  public boolean patrol(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean patrol(Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean holdPosition() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean holdPosition(boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean stop() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean stop(boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean follow(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean follow(Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean gather(final Unit resource) {
    return issueCommand(this.iD, Gather, resource.getId(), -1, -1, 0);
  }

  public boolean gather(final Unit resource, boolean shiftQueueCommand) {
    return issueCommand(this.iD, Gather, resource.getId(), -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean returnCargo() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean returnCargo(boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean repair(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean repair(Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean burrow() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean unburrow() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean cloak() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean decloak() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean siege() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean unsiege() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean lift() {
    return issueCommand(iD, Lift, -1, -1, -1, -1);
  }

  public boolean land(final TilePosition target) {
    return issueCommand(iD, Land, -1, target.getX(), target.getY(), -1);
  }

  public boolean load(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean load(Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean unload(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean unloadAll() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean unloadAll(boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean unloadAll(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean unloadAll(Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean rightClick(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean rightClick(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean rightClick(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean rightClick(Position target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean rightClick(Unit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean rightClick(PositionOrUnit target, boolean shiftQueueCommand) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean haltConstruction() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean cancelConstruction() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean cancelAddon() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean cancelTrain() {
    return issueCommand(iD, Cancel_Train, -1, -1, -1, -1);
  }

  public boolean cancelTrain(final int slot) {
    return issueCommand(iD, Cancel_Train_Slot, -1, -1, -1, slot);
  }

  public boolean cancelMorph() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean cancelResearch() {
    return issueCommand(iD, Cancel_Research, -1, -1, -1, -1);
  }

  public boolean cancelUpgrade() {
    return issueCommand(iD, Cancel_Upgrade, -1, -1, -1, -1);
  }

  public boolean useTech(TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean useTech(TechType tech, Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean useTech(TechType tech, Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean useTech(TechType tech, PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean placeCOP(TilePosition target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanBuildUnitType,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanBuildUnitType,
      boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanBuildUnitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(UnitCommand command, boolean checkCanUseTechPositionOnPositions) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanBuildUnitType,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      UnitCommand command, boolean checkCanUseTechPositionOnPositions) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      UnitCommand command,
      boolean checkCanUseTechPositionOnPositions,
      boolean checkCanUseTechUnitOnUnits,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCommand() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCommandGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCommandGrouped(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandType(UnitCommandType ct) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandType(UnitCommandType ct, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandTypeGrouped(
      UnitCommandType ct, boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandTypeGrouped(UnitCommandType ct) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandTypeGrouped(
      UnitCommandType ct, boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canTargetUnit(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canTargetUnit(Unit targetUnit, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      Position target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      Unit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      PositionOrUnit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(Position target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(Unit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(PositionOrUnit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      Position target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      Position target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      Position target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      Unit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      PositionOrUnit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(Position target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(Unit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(PositionOrUnit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      Position target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackMove() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackMove(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackMoveGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackMoveGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackMoveGrouped(
      boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnit() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnit(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnit(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnit(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnit(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnit(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped(
      boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackUnitGrouped(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(UnitType uType, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(UnitType uType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(
      UnitType uType, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(
      UnitType uType,
      TilePosition tilePos,
      boolean checkTargetUnitType,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(UnitType uType, TilePosition tilePos, boolean checkTargetUnitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(UnitType uType, TilePosition tilePos) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuild(
      UnitType uType,
      TilePosition tilePos,
      boolean checkTargetUnitType,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildAddon() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildAddon(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildAddon(UnitType uType, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildAddon(UnitType uType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBuildAddon(
      UnitType uType, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canTrain() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canTrain(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canTrain(UnitType uType, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canTrain(final UnitType unitType) {
    return getType().equals(unitType.whatBuilds().getUnitType())
        && getPlayer().canMake(unitType)
        && unitType
            .requiredUnits()
            .keySet()
            .stream()
            .allMatch(ut -> !ut.isAddon() || (addon != null && addon.getType() == ut));
  }

  public boolean canTrain(
      UnitType uType, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMorph() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMorph(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMorph(UnitType uType, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMorph(UnitType uType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMorph(
      UnitType uType, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canResearch() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canResearch(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canResearch(final TechType techType) {
    return getType().equals(techType.whatResearches()) && getPlayer().canResearch(techType);
  }

  public boolean canResearch(final TechType type, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUpgrade() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUpgrade(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUpgrade(final UpgradeType upgradeType) {
    return getType().equals(upgradeType.whatUpgrades()) && getPlayer().canUpgrade(upgradeType);
  }

  public boolean canUpgrade(UpgradeType type, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      Position target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      Unit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      PositionOrUnit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(Position target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(Unit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(PositionOrUnit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      Position target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPosition() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPosition(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyUnit() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyUnit(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyUnit(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyUnit(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyUnit(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyUnit(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMove() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMove(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMoveGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMoveGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canMoveGrouped(boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPatrol() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPatrol(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPatrolGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPatrolGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPatrolGrouped(boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canFollow() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canFollow(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canFollow(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canFollow(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canFollow(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canFollow(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canGather() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canGather(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canGather(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canGather(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canGather(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canGather(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canReturnCargo() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canReturnCargo(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canHoldPosition() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canHoldPosition(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canStop() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canStop(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRepair() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRepair(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRepair(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRepair(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRepair(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRepair(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBurrow() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canBurrow(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnburrow() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnburrow(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCloak() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCloak(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canDecloak() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canDecloak(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSiege() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSiege(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnsiege() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnsiege(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLift() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLift(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLand() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLand(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLand(TilePosition target, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLand(TilePosition target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLand(
      TilePosition target, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLoad() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLoad(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLoad(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLoad(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLoad(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canLoad(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadWithOrWithoutTarget() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadWithOrWithoutTarget(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAtPosition(Position targDropPos, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAtPosition(Position targDropPos) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAtPosition(
      Position targDropPos, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnload() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnload(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnload(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkPosition,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnload(Unit targetUnit, boolean checkCanTargetUnit, boolean checkPosition) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnload(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnload(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnload(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkPosition,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAll() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAll(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAllPosition() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAllPosition(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAllPosition(Position targDropPos, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAllPosition(Position targDropPos) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUnloadAllPosition(
      Position targDropPos, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      Position target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      Unit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      PositionOrUnit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(Position target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(Unit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(PositionOrUnit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      Position target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      Position target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      Position target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      Unit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      PositionOrUnit target, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(Position target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(Unit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(PositionOrUnit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      Position target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickPosition() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickPosition(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickPositionGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickPositionGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickPositionGrouped(
      boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnit() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnit(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnit(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnit(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnit(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnit(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped(boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped(
      boolean checkCommandibilityGrouped, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped(
      Unit targetUnit, boolean checkCanTargetUnit, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped(Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped(Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickUnitGrouped(
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canHaltConstruction() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canHaltConstruction(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelConstruction() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelConstruction(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelAddon() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelAddon(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelTrain() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelTrain(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelTrainSlot() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelTrainSlot(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelTrainSlot(int slot, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelTrainSlot(int slot) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelTrainSlot(
      int slot, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelMorph() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelMorph(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelResearch() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelResearch(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelUpgrade() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCancelUpgrade(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithOrWithoutTarget() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithOrWithoutTarget(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithOrWithoutTarget(TechType tech, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithOrWithoutTarget(TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithOrWithoutTarget(
      TechType tech, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech,
      Position target,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech,
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech,
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech, Position target, boolean checkCanTargetUnit, boolean checkTargetsType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech, Unit target, boolean checkCanTargetUnit, boolean checkTargetsType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech, PositionOrUnit target, boolean checkCanTargetUnit, boolean checkTargetsType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(TechType tech, Position target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(TechType tech, Unit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(TechType tech, PositionOrUnit target, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(TechType tech, Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(TechType tech, Unit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(TechType tech, PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech,
      Position target,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech,
      Unit target,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      TechType tech,
      PositionOrUnit target,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithoutTarget(TechType tech, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithoutTarget(TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithoutTarget(
      TechType tech, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(TechType tech, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(
      TechType tech, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(
      TechType tech,
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkTargetsUnits,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(
      TechType tech, Unit targetUnit, boolean checkCanTargetUnit, boolean checkTargetsUnits) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(TechType tech, Unit targetUnit, boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(TechType tech, Unit targetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechUnit(
      TechType tech,
      Unit targetUnit,
      boolean checkCanTargetUnit,
      boolean checkTargetsUnits,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechPosition(TechType tech, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechPosition(TechType tech) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechPosition(
      TechType tech, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechPosition(
      TechType tech,
      Position target,
      boolean checkTargetsPositions,
      boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechPosition(TechType tech, Position target, boolean checkTargetsPositions) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechPosition(TechType tech, Position target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechPosition(
      TechType tech,
      Position target,
      boolean checkTargetsPositions,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPlaceCOP() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPlaceCOP(boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPlaceCOP(TilePosition target, boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPlaceCOP(TilePosition target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canPlaceCOP(
      TilePosition target, boolean checkCanIssueCommandType, boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }
}
