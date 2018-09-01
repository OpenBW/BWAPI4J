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

import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Research;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Train;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Train_Slot;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Upgrade;
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
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.ap.Named;
import org.openbw.bwapi4j.ap.Native;
import org.openbw.bwapi4j.ap.NativeClass;
import org.openbw.bwapi4j.type.Order;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitSizeType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.type.WeaponType;

@NativeClass(name = "BWAPI::Unit")
public abstract class UnitImpl implements Unit {

  private static final Logger logger = LogManager.getLogger();

  @Native
  @Named(name = "ID")
  int iD;

  Position initialPosition;
  TilePosition initialTilePosition;
  int initiallySpotted;

  @Named(name = "TYPE")
  @Native
  UnitType type;

  // dynamic
  protected int x;
  protected int y;
  @Native
  protected Position position;
  @Native
  protected TilePosition tilePosition;
  @Native
  double angle;
  int lastCommandFrame;
  @Native
  UnitCommandType lastCommand;

  @Native
  boolean visible;

  @Native(accessor = "exists()")
  boolean exists;

  @Native
  boolean selected;
  @Native
  boolean flying;

  @Native
  boolean upgrading;
  @Native
  boolean researching;
  @Native
  int remainingResearchTime;
  @Native
  int remainingUpgradeTime;
  @Native
  UpgradeType upgrade;
  @Native
  TechType tech;

  // static
  int initialHitPoints;

  // dynamic
  @Native
  int hitPoints;
  @Native
  int shields;
  @Native
  int killCount;
  @Native
  boolean cloaked;
  @Native
  boolean detected;
  @Native
  double velocityX;
  @Native
  double velocityY;
  @Native
  boolean idle;
  @Native
  boolean completed;
  @Native
  Weapon groundWeapon = new Weapon(WeaponType.None, 0);
  @Native
  Weapon airWeapon = new Weapon(WeaponType.None, 0);
  @Native
  int spellCooldown;
  @Native
  Unit target;
  @Native
  boolean accelerating;
  @Native
  boolean attacking;
  @Native
  boolean attackFrame;
  @Native
  boolean beingConstructed;
  @Native
  boolean beingHealed;
  @Native
  boolean irradiated;
  @Native
  boolean lockedDown;
  @Native
  boolean maelstrommed;
  @Native
  boolean startingAttack;
  @Native
  boolean underAttack;
  @Native
  boolean powered;
  @Native
  boolean interruptible;
  @Native
  Player player;
  @Native
  int energy;
  @Native
  boolean training;
  @Native
  Unit buildUnit;
  @Native
  int remainingTrainTime;
  @Native
  Position rallyPosition;
  @Native
  Unit rallyUnit;
  @Native
  List<TrainingSlot> trainingQueue = new ArrayList<>();
  @Native
  boolean loaded;
  @Native
  int spaceRemaining;
  @Native
  List<Unit> loadedUnits = new ArrayList<>();
  @Native
  int interceptorCount;
  @Native
  boolean following;
  @Native
  boolean holdingPosition;
  @Native
  boolean stuck;
  @Native
  boolean stasised;
  @Native
  boolean underDarkSwarm;
  @Native
  boolean underDisruptionWeb;
  @Native
  boolean underStorm;
  @Native
  boolean moving;
  @Native
  boolean parasited;
  @Native
  boolean patrolling;
  @Native
  boolean plagued;
  @Native
  Position targetPosition;
  @Native
  Unit transport;
  @Native
  int acidSporeCount;
  @Native
  boolean hallucination;
  @Native
  boolean blind;
  @Native
  boolean braking;
  @Native
  boolean defenseMatrixed;
  @Native
  boolean ensnared;
  @Native
  Unit addon;
  @Native
  int remainingBuildTime;
  @Native
  boolean lifted;
  @Native
  boolean burrowed;
  @Native
  UnitType buildType;
  @Native
  boolean stimmed;
  int initialResources;
  @Native
  int resources;
  @Native
  boolean beingGathered;
  @Native
  Unit carrier;
  @Native
  Unit hatchery;
  int lastKnownResources;

  @Native(accessor = "hasNuke()")
  boolean hasNuke;

  @Native
  Unit nydusExit;
  @Native
  int scarabCount;
  @Native
  boolean repairing;
  @Native
  boolean sieged;
  @Native
  int spiderMineCount;
  @Native
  boolean constructing;
  @Native
  boolean gatheringGas;
  @Native
  boolean gatheringMinerals;
  @Native
  boolean carryingGas;
  @Native
  boolean carryingMinerals;

  // other
  Position lastKnownPosition;
  TilePosition lastKnownTilePosition;
  int lastKnownHitPoints;

  // internal
  private BW bw;
  int lastSpotted;

  protected UnitImpl(int id, UnitType unitType) {
    this.iD = id;
    this.type = unitType;
    this.lastSpotted = 0;
  }

  final void setBW(BW bw) {
    this.bw = bw;
  }

  public int getKillCount() {
    return killCount;
  }

  public int getLastSpotted() {
    return this.lastSpotted;
  }

  public int getInitiallySpotted() {
    return initiallySpotted;
  }

  protected Collection<UnitImpl> getAllUnits() {
    return bw.getAllUnits();
  }

  protected Unit getUnit(int id) {
    return bw.getUnit(id);
  }

  protected DamageEvaluator getDamageEvaluator() {
    return bw.getDamageEvaluator();
  }

  protected Player getPlayer() {
    return player;
  }

  protected Player getPlayer(int id) {
    return bw.getPlayer(id);
  }

  public int getId() {
    return this.iD;
  }

  public int getLeft() {
    return this.x - this.type.dimensionLeft();
  }

  public int getTop() {
    return this.y - this.type.dimensionUp();
  }

  public int getRight() {
    return this.x + this.type.dimensionRight();
  }

  public int getBottom() {
    return this.y + this.type.dimensionDown();
  }

  public Position getMiddle(Unit unit) {
    int x = this.getPosition().getX();
    int y = this.getPosition().getY();

    int dx = unit.getPosition().getX() - x;
    int dy = unit.getPosition().getY() - y;

    return new Position(x + dx / 2, y + dy / 2);
  }

  public double getAngle() {
    return this.angle;
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

  public TilePosition getTilePosition() {
    return this.tilePosition;
  }

  public Position getPosition() {
    return this.position;
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

  boolean lift() {
    return issueCommand(iD, Lift, -1, -1, -1, -1);
  }

  boolean land(Position p) {
    return issueCommand(iD, Land, -1, p.getX(), p.getY(), -1);
  }

  boolean move(Position p) {
    return issueCommand(iD, Move, -1, p.getX(), p.getY(), -1);
  }

  public boolean exists() {
    return this.exists;
  }

  public UnitType getType() {
    return type;
  }

  public Position getInitialPosition() {
    return initialPosition;
  }

  public TilePosition getInitialTilePosition() {
    return initialTilePosition;
  }

  protected Order getOrder() {
    return this.order;
  }

  protected Unit getOrderTarget() {
    return (this.orderTargetId >= 0) ? this.getUnit(this.orderTargetId) : null;
  }

  protected Position getOrderTargetPosition() {
    return this.orderTargetPosition;
  }

  protected Order getSecondaryOrder() {
    return this.secondaryOrder;
  }

  protected int getCurrentFrame() {
    return bw.getInteractionHandler().getFrameCount();
  }

  protected boolean cancelResearch() {
    return issueCommand(iD, Cancel_Research, -1, -1, -1, -1);
  }

  protected boolean cancelUpgrade() {
    return issueCommand(iD, Cancel_Upgrade, -1, -1, -1, -1);
  }

  protected boolean canResearch(TechType techType) {
    return type.equals(techType.whatResearches()) && player.canResearch(techType);
  }

  protected boolean canUpgrade(UpgradeType upgradeType) {
    return type.equals(upgradeType.whatUpgrades()) && player.canUpgrade(upgradeType);
  }

  protected boolean research(TechType techType) {
    return issueCommand(iD, Research, -1, -1, -1, techType.getId());
  }

  protected boolean upgrade(UpgradeType upgrade) {
    return issueCommand(iD, Upgrade, -1, -1, -1, upgrade.getId());
  }

  protected ResearchingFacility.UpgradeInProgress getUpgradeInProgress() {
    if (upgrade == UpgradeType.None) {
      return ResearchingFacility.UpgradeInProgress.NONE;
    }
    return new ResearchingFacility.UpgradeInProgress(upgrade, remainingUpgradeTime);
  }

  protected ResearchingFacility.ResearchInProgress getResearchInProgress() {
    if (tech == TechType.None) {
      return ResearchingFacility.ResearchInProgress.NONE;
    }
    return new ResearchingFacility.ResearchInProgress(tech, remainingResearchTime);
  }

  protected boolean canTrain(UnitType type) {
    return this.type.equals(type.whatBuilds().getFirst())
        && player.canMake(type)
        && type.requiredUnits()
        .stream()
        .allMatch(ut -> !ut.isAddon() || (addon != null && addon.getType() == ut));
  }

  protected boolean train(UnitType type) {
    return issueCommand(iD, Train, -1, -1, -1, type.getId());
  }

  protected boolean cancelTrain(int slot) {
    return issueCommand(iD, Cancel_Train_Slot, -1, -1, -1, slot);
  }

  protected boolean cancelTrain() {
    return issueCommand(iD, Cancel_Train, -1, -1, -1, -1);
  }

  protected boolean setRallyPoint(Position p) {
    return issueCommand(iD, Set_Rally_Position, -1, p.getX(), p.getY(), -1);
  }

  protected boolean setRallyPoint(Unit target) {
    return issueCommand(iD, Set_Rally_Unit, target.getId(), -1, -1, -1);
  }

  protected Position getRallyPosition() {
    return rallyPosition;
  }

  protected Unit getRallyUnit() {
    return rallyUnit;
  }

  public boolean isFlying() {
    return flying;
  }

  public boolean isVisible() {
    return visible;
  }

  public boolean isSelected() {
    return selected;
  }

  @Override
  public int hashCode() {
    return this.iD;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Unit) {
      return this.getId() == ((Unit) obj).getId();
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return this.getId() + ":" + this.type;
  }

  @Override
  public int compareTo(Unit otherUnit) {
    return this.getId() - otherUnit.getId();
  }

  protected boolean issueCommand(
      int unitId, UnitCommandType unitCommandType, int targetUnitId, int x, int y, int extra) {
    if (issueCommand(unitId, unitCommandType.ordinal(), targetUnitId, x, y, extra)) {
      lastCommandFrame = getCurrentFrame();
      lastCommand = unitCommandType;
      return true;
    }
    return false;
  }

  private native boolean issueCommand(
      int unitId, int unitCommandTypeId, int targetUnitId, int x, int y, int extra);

  // --------------------------------------------------
  // dynamic

  private int replayID;
  private int resourceGroup;
  private Player lastAttackingPlayer;
  private int defenseMatrixPoints;
  private int defenseMatrixTimer;
  private int ensnareTimer;
  private int irradiateTimer;
  private int lockDownTimer;
  private int maelstromTimer;
  private int orderTimer;
  private int plagueTimer;
  private int removeTimer;
  private int stasisTimer;
  private int stimTimer;

  Order order;
  int orderTargetId;
  Position orderTargetPosition;

  Order secondaryOrder;
  private boolean morphing;
  private boolean targetable;
  private boolean invincible;

  public class TrainingSlot {

    private final int slotIndex;

    private final UnitType unitType;

    @Native
    TrainingSlot(final int slotIndex, final UnitType unitType) {
      this.slotIndex = slotIndex;
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
}
