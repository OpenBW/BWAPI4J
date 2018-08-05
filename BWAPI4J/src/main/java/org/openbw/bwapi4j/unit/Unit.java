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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.*;
import org.openbw.bwapi4j.type.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public abstract class Unit implements Comparable<Unit> {

    private static final Logger logger = LogManager.getLogger();

    private static final int ID_INDEX = 0;
    private static final int REPLAY_ID_INDEX = 1;
    private static final int PLAYER_ID_INDEX = 2;
    private static final int TYPE_ID_INDEX = 3;
    private static final int POSITION_X_INDEX = 4;
    private static final int POSITION_Y_INDEX = 5;
    private static final int TILEPOSITION_X_INDEX = 6;
    private static final int TILEPOSITION_Y_INDEX = 7;
    private static final int ANGLE_INDEX = 8;
    private static final int VELOCITY_X_INDEX = 9;
    private static final int VELOCITY_Y_INDEX = 10;
    private static final int HITPOINTS_INDEX = 11;
    private static final int SHIELDS_INDEX = 12;
    private static final int ENERGY_INDEX = 13;
    private static final int RESOURCES_INDEX = 14;
    private static final int RESOURCE_GROUP_INDEX = 15;
    private static final int LAST_COMMAND_FRAME_INDEX = 16;
    private static final int LAST_COMMAND_TYPE_ID_INDEX = 17;
    private static final int LAST_ATTACKING_PLAYER_INDEX = 18;
    private static final int INITIAL_TYPE_ID_INDEX = 19;
    private static final int INITIAL_POSITION_X_INDEX = 20;
    private static final int INITIAL_POSITION_Y_INDEX = 21;
    private static final int INITIAL_TILEPOSITION_X_INDEX = 22;
    private static final int INITIAL_TILEPOSITION_Y_INDEX = 23;
    private static final int INITIAL_HITPOINTS_INDEX = 24;
    private static final int INITIAL_RESOURCES_INDEX = 25;
    private static final int KILLCOUNT_INDEX = 26;
    private static final int ACID_SPORE_COUNT_INDEX = 27;
    private static final int INTERCEPTOR_COUNT_INDEX = 28;
    private static final int SCARAB_COUNT_INDEX = 29;
    private static final int SPIDERMINE_COUNT_INDEX = 30;
    private static final int GROUND_WEAPON_COOLDOWN_INDEX = 31;
    private static final int AIR_WEAPON_COOLDOWN_INDEX = 32;
    private static final int SPELL_COOLDOWN_INDEX = 33;
    private static final int DEFENSE_MATRIX_POINTS_INDEX = 34;
    private static final int DEFENSE_MATRIX_TIMER_INDEX = 35;
    private static final int ENSNARE_TIMER_INDEX = 36;
    private static final int IRRADIATE_TIMER_INDEX = 37;
    private static final int LOCKDOWN_TIMER_INDEX = 38;
    private static final int MAELSTROM_TIMER_INDEX = 39;
    private static final int ORDER_TIMER_INDEX = 40;
    private static final int PLAGUE_TIMER_INDEX = 41;
    private static final int REMOVE_TIMER_INDEX = 42;
    private static final int STASIS_TIMER_INDEX = 43;
    private static final int STOM_TIMER_INDEX = 44;
    private static final int BUILDTYPE_ID_INDEX = 45;
    private static final int TRAINING_QUEUE_SIZE_INDEX = 46;
    private static final int TECH_ID_INDEX = 47;
    private static final int UPGRADE_ID_INDEX = 48;
    private static final int REMAINING_BUILD_TIME_INDEX = 49;
    private static final int REMAINING_TRAIN_TIME_INDEX = 50;
    private static final int REMAINING_RESEARCH_TIME_INDEX = 51;
    private static final int REMAINING_UPGRADE_TIME_INDEX = 52;
    private static final int BUILD_UNIT_ID_INDEX = 53;
    private static final int TARGET_ID_INDEX = 54;
    private static final int TARGET_POSITION_X_INDEX = 55;
    private static final int TARGET_POSITION_Y_INDEX = 56;
    private static final int ORDER_ID_INDEX = 57;
    private static final int ORDER_TARGET_ID_INDEX = 58;
    private static final int SECONDARY_ORDER_ID_INDEX = 59;
    private static final int RALLY_POSITION_X_INDEX = 60;
    private static final int RALLY_POSITION_Y_INDEX = 61;
    private static final int RALLY_UNIT_INDEX = 62;
    private static final int ADDON_INDEX = 63;
    private static final int NYDUS_EXIT_INDEX = 64;
    private static final int TRANSPORT_INDEX = 65;
    private static final int LOADED_UNITS_SIZE_INDEX = 66;
    private static final int CARRIER_INDEX = 67;
    private static final int HATCHERY_INDEX = 68;
    private static final int LARVA_SIZE_INDEX = 69;
    private static final int POWERUP_ID_INDEX = 70;
    private static final int EXISTS_INDEX = 71;
    private static final int HAS_NUKE_INDEX = 72;
    private static final int IS_ACCELERATING_INDEX = 73;
    private static final int IS_ATTACKING_INDEX = 74;
    private static final int IS_ATTACK_FRAME_INDEX = 75;
    private static final int IS_BEING_CONSTRUCTED_INDEX = 76;
    private static final int IS_BEING_GATHERED_INDEX = 77;
    private static final int IS_BEING_HEALED_INDEX = 78;
    private static final int IS_BLIND_INDEX = 79;
    private static final int IS_BRAKING_INDEX = 80;
    private static final int IS_BURROWED_INDEX = 81;
    private static final int IS_CARRYING_GAS_INDEX = 82;
    private static final int IS_CARRYING_MINERALS_INDEX = 83;
    private static final int IS_CLOAKED_INDEX = 84;
    private static final int IS_COMPLETED_INDEX = 85;
    private static final int IS_CONSTRUCTING_INDEX = 86;
    private static final int IS_DEFENSE_MATRIXED_INDEX = 87;
    private static final int IS_DETECTED_INDEX = 88;
    private static final int IS_ENSNARED_INDEX = 89;
    private static final int IS_FOLLOWING_INDEX = 90;
    private static final int IS_GATHERING_GAS_INDEX = 91;
    private static final int IS_GATHERING_MINERALS_INDEX = 92;
    private static final int IS_HALLUCINATION_INDEX = 93;
    private static final int IS_HOLDING_POSITION_INDEX = 94;
    private static final int IS_IDLE_INDEX = 95;
    private static final int IS_INTERRUPTIBLE_INDEX = 96;
    private static final int IS_INVINCIBLE_INDEX = 97;
    private static final int IS_IRRADIATED_INDEX = 98;
    private static final int IS_LIFTED_INDEX = 99;
    private static final int IS_LOADED_INDEX = 100;
    private static final int IS_LOCKED_DOWN_INDEX = 101;
    private static final int IS_MAELSTROMMED_INDEX = 102;
    private static final int IS_MORPHING_INDEX = 103;
    private static final int IS_MOVING_INDEX = 104;
    private static final int IS_PARASITED_INDEX = 105;
    private static final int IS_PATROLLING_INDEX = 106;
    private static final int IS_PLAGUED_INDEX = 107;
    private static final int IS_REPAIRING_INDEX = 108;
    private static final int IS_SELECTED_INDEX = 109;
    private static final int IS_SIEGED_INDEX = 110;
    private static final int IS_STARTING_ATTACK_INDEX = 111;
    private static final int IS_STASISED_INDEX = 112;
    private static final int IS_STIMMED_INDEX = 113;
    private static final int IS_STUCK_INDEX = 114;
    private static final int IS_TRAINING_INDEX = 115;
    private static final int IS_UNDER_ATTACK_INDEX = 116;
    private static final int IS_UNDER_DARK_SWARM_INDEX = 117;
    private static final int IS_UNDER_DISRUPTION_WEB_INDEX = 118;
    private static final int IS_UNDER_STORM_INDEX = 119;
    private static final int IS_POWERED_INDEX = 120;
    private static final int IS_UPGRADING_INDEX = 121;
    private static final int IS_VISIBLE_INDEX = 122;
    private static final int IS_RESEARCHING_INDEX = 123;
    private static final int IS_FLYING_INDEX = 124;
    private static final int ORDER_TARGET_POSITION_X_INDEX = 125;
    private static final int ORDER_TARGET_POSITION_Y_INDEX = 126;
    private static final int TRAINING_QUEUE_SLOT_0_INDEX = 127;
    private static final int TRAINING_QUEUE_SLOT_1_INDEX = 128;
    private static final int TRAINING_QUEUE_SLOT_2_INDEX = 129;
    private static final int TRAINING_QUEUE_SLOT_3_INDEX = 130;
    private static final int TRAINING_QUEUE_SLOT_4_INDEX = 131;
    private static final int MAX_TRAINING_QUEUE_SIZE = 5;

    public static final int TOTAL_PROPERTIES = 132;

    // static
    protected int id;
    UnitType initialType;
    Position initialPosition;
    TilePosition initialTilePosition;
    int initiallySpotted;

    // dynamic
    protected UnitType type;
    protected int x;
    protected int y;
    protected Position position;
    protected TilePosition tilePosition;
    double angle;
    int lastCommandFrame;
    UnitCommandType lastCommand;

    private boolean isVisible;
    private boolean exists;
    private boolean isSelected;
    private boolean isFlying;

    boolean isUpgrading;
    boolean isResearching;
    private int remainingResearchTime;
    private int remainingUpgradeTime;
    private UpgradeType currentUpgrade;
    private TechType currentResearch;

    // static
    int initialHitPoints;

    // dynamic
    int hitPoints;
    int shields;
    int killCount;
    boolean isCloaked;
    boolean isDetected;
    double velocityX;
    double velocityY;
    boolean isIdle;
    boolean isCompleted;
    Weapon groundWeapon = new Weapon(WeaponType.None, 0);
    Weapon airWeapon = new Weapon(WeaponType.None, 0);
    int spellCooldown;
    int targetId;
    boolean isAccelerating;
    boolean isAttacking;
    boolean isAttackFrame;
    boolean isBeingConstructed;
    boolean isBeingHealed;
    boolean isIrradiated;
    boolean isLockedDown;
    boolean isMaelstrommed;
    boolean isStartingAttack;
    boolean isUnderAttack;
    boolean isPowered;
    boolean isInterruptible;
    int builderId;
    int playerId;
    int energy;
    boolean isTraining;
    int trainingQueueSize;
    int remainingTrainTime;
    private int rallyPositionX;
    private int rallyPositionY;
    private int rallyUnitId;
    private int[] trainingQueueUnitTypeIds = new int[MAX_TRAINING_QUEUE_SIZE];
    List<TrainingSlot> trainingQueue = new ArrayList<>();
    boolean isLoaded;
    int interceptorCount;
    boolean isFollowing;
    boolean isHoldingPosition;
    boolean isStuck;
    boolean isStasised;
    boolean isUnderDarkSwarm;
    boolean isUnderDisruptionWeb;
    boolean isUnderStorm;
    boolean isMoving;
    boolean isParasited;
    boolean isPatrolling;
    boolean isPlagued;
    Position targetPosition;
    int transportId;
    int acidSporeCount;
    boolean isHallucination;
    boolean isBlind;
    boolean isBraking;
    boolean isDefenseMatrixed;
    boolean isEnsnared;
    int addonId;
    int remainingBuildTime;
    boolean isLifted;
    boolean burrowed;
    int remainingMorphTime;
    UnitType buildType;
    boolean isStimmed;
    int initialResources;
    int resources;
    boolean isBeingGathered;
    int carrierId;
    int hatcheryId;
    int lastKnownResources;
    boolean hasNuke;
    int nydusExitId;
    int scarabCount;
    boolean isRepairing;
    boolean sieged;
    int spiderMineCount;
    boolean isConstructing;
    boolean isGatheringGas;
    boolean isGatheringMinerals;
    boolean isCarryingGas;
    boolean isCarryingMinerals;

    // other
    Position lastKnownPosition;
    TilePosition lastKnownTilePosition;
    int lastKnownHitPoints;

    // internal
    private BW bw;
    private int lastSpotted;

    protected Unit(int id, UnitType unitType) {

        this.id = id;
        this.type = unitType;
        this.initialType = unitType;
        this.lastSpotted = 0;
    }

    final void setBW(BW bw) {
        this.bw = bw;
    }

    public void initialize(int[] unitData, int index, int frame) {

        // TODO this is a workaround because initialTilePosition gives wrong results with OpenBW
        this.initialPosition = new Position(unitData[index + Unit.POSITION_X_INDEX],
                unitData[index + Unit.POSITION_Y_INDEX]);
        this.initialTilePosition = new TilePosition(unitData[index + Unit.TILEPOSITION_X_INDEX],
                unitData[index + Unit.TILEPOSITION_Y_INDEX]);
        this.initiallySpotted = frame;
        this.initialHitPoints = unitData[index + Unit.INITIAL_HITPOINTS_INDEX];
        this.isInterruptible = unitData[index + Unit.IS_INTERRUPTIBLE_INDEX] == 1;
        this.lastKnownPosition = this.initialPosition;
        this.lastKnownTilePosition = this.initialTilePosition;
        this.lastKnownHitPoints = this.initialHitPoints;
        this.initialResources = unitData[index + Unit.INITIAL_RESOURCES_INDEX];
        this.carrierId = unitData[index + Unit.CARRIER_INDEX];
        this.hatcheryId = unitData[index + Unit.HATCHERY_INDEX];
    }

    public void preUpdate() {
        this.isVisible = false;
        this.exists = false;
    }

    public void update(int[] unitData, int index, int frame) {

        this.type = UnitType.values()[unitData[index + Unit.TYPE_ID_INDEX]];
        this.x = unitData[index + Unit.POSITION_X_INDEX];
        this.y = unitData[index + Unit.POSITION_Y_INDEX];
        this.position = new Position(x, y);
        this.tilePosition = new TilePosition(unitData[index + Unit.TILEPOSITION_X_INDEX],
                unitData[index + Unit.TILEPOSITION_Y_INDEX]);
        this.angle = unitData[index + Unit.ANGLE_INDEX] * Math.PI / 180.0;
        this.isVisible = unitData[index + Unit.IS_VISIBLE_INDEX] == 1;
        this.exists = unitData[index + Unit.EXISTS_INDEX] == 1;
        this.isSelected = unitData[index + Unit.IS_SELECTED_INDEX] == 1;
        this.isFlying = unitData[index + Unit.IS_FLYING_INDEX] == 1;

        this.order = Order.values()[unitData[index + Unit.ORDER_ID_INDEX]];
        this.orderTargetId = unitData[index + Unit.ORDER_TARGET_ID_INDEX];

        final int orderTargetPositionX = unitData[index + Unit.ORDER_TARGET_POSITION_X_INDEX];
        final int orderTargetPositionY = unitData[index + Unit.ORDER_TARGET_POSITION_Y_INDEX];
        this.orderTargetPosition = new Position(orderTargetPositionX, orderTargetPositionY);

        this.secondaryOrder = Order.values()[unitData[index + Unit.SECONDARY_ORDER_ID_INDEX]];
        this.isUpgrading = unitData[index + Unit.IS_UPGRADING_INDEX] == 1;
        this.isResearching = unitData[index + Unit.IS_RESEARCHING_INDEX] == 1;
        this.remainingResearchTime = unitData[index + Unit.REMAINING_RESEARCH_TIME_INDEX];
        this.remainingUpgradeTime = unitData[index + Unit.REMAINING_UPGRADE_TIME_INDEX];
        this.currentUpgrade = UpgradeType.withId(unitData[index + Unit.UPGRADE_ID_INDEX]);
        this.currentResearch = TechType.withId(unitData[index + Unit.TECH_ID_INDEX]);

        this.playerId = unitData[index + Unit.PLAYER_ID_INDEX];
        this.hitPoints = unitData[index + Unit.HITPOINTS_INDEX];
        this.shields = unitData[index + Unit.SHIELDS_INDEX];
        this.killCount = unitData[index + Unit.KILLCOUNT_INDEX];
        this.isCloaked = unitData[index + Unit.IS_CLOAKED_INDEX] == 1;
        this.isDetected = unitData[index + Unit.IS_DETECTED_INDEX] == 1;
        this.velocityX = unitData[index + Unit.VELOCITY_X_INDEX] / 100.0;
        this.velocityY = unitData[index + Unit.VELOCITY_Y_INDEX] / 100.0;
        this.isIdle = unitData[index + Unit.IS_IDLE_INDEX] == 1;
        this.isCompleted = unitData[index + Unit.IS_COMPLETED_INDEX] == 1;
        this.spellCooldown = unitData[index + Unit.SPELL_COOLDOWN_INDEX];
        this.isAccelerating = unitData[index + Unit.IS_ACCELERATING_INDEX] == 1;
        this.isAttacking = unitData[index + Unit.IS_ATTACKING_INDEX] == 1;
        this.isAttackFrame = unitData[index + Unit.IS_ATTACK_FRAME_INDEX] == 1;
        this.isBeingConstructed = unitData[index + Unit.IS_BEING_CONSTRUCTED_INDEX] == 1;
        this.isBeingHealed = unitData[index + Unit.IS_BEING_HEALED_INDEX] == 1;
        this.isIrradiated = unitData[index + Unit.IS_IRRADIATED_INDEX] == 1;
        this.isLockedDown = unitData[index + Unit.IS_LOCKED_DOWN_INDEX] == 1;
        this.isMaelstrommed = unitData[index + Unit.IS_MAELSTROMMED_INDEX] == 1;
        this.isStartingAttack = unitData[index + Unit.IS_STARTING_ATTACK_INDEX] == 1;
        this.isUnderAttack = unitData[index + Unit.IS_UNDER_ATTACK_INDEX] == 1;
        this.isPowered = unitData[index + Unit.IS_POWERED_INDEX] == 1;
        this.targetId = unitData[index + Unit.TARGET_ID_INDEX];
        this.isInterruptible = unitData[index + Unit.IS_INTERRUPTIBLE_INDEX] == 1;
        this.lastCommand = UnitCommandType.values()[unitData[index + Unit.LAST_COMMAND_TYPE_ID_INDEX]];
        this.lastCommandFrame = unitData[index + Unit.LAST_COMMAND_FRAME_INDEX];

        this.builderId = unitData[index + Unit.BUILD_UNIT_ID_INDEX];
        this.groundWeapon.update(type.groundWeapon(), unitData[index + Unit.GROUND_WEAPON_COOLDOWN_INDEX]);
        this.airWeapon.update(type.airWeapon(), unitData[index + Unit.AIR_WEAPON_COOLDOWN_INDEX]);
        this.energy = unitData[index + Unit.ENERGY_INDEX];
        this.isTraining = unitData[index + Unit.IS_TRAINING_INDEX] == 1;
        this.trainingQueueSize = unitData[index + Unit.TRAINING_QUEUE_SIZE_INDEX];

        this.trainingQueueUnitTypeIds[0] = unitData[index + Unit.TRAINING_QUEUE_SLOT_0_INDEX];
        this.trainingQueueUnitTypeIds[1] = unitData[index + Unit.TRAINING_QUEUE_SLOT_1_INDEX];
        this.trainingQueueUnitTypeIds[2] = unitData[index + Unit.TRAINING_QUEUE_SLOT_2_INDEX];
        this.trainingQueueUnitTypeIds[3] = unitData[index + Unit.TRAINING_QUEUE_SLOT_3_INDEX];
        this.trainingQueueUnitTypeIds[4] = unitData[index + Unit.TRAINING_QUEUE_SLOT_4_INDEX];
        this.trainingQueue.clear();
        for (int i = 0; i < this.trainingQueueSize; ++i) {
            this.trainingQueue.add(new TrainingSlot(i, UnitType.values()[this.trainingQueueUnitTypeIds[i]]));
        }

        this.remainingTrainTime = unitData[index + Unit.REMAINING_TRAIN_TIME_INDEX];
        this.rallyUnitId = unitData[index + Unit.RALLY_UNIT_INDEX];
        this.rallyPositionX = unitData[index + Unit.RALLY_POSITION_X_INDEX];
        this.rallyPositionY = unitData[index + Unit.RALLY_POSITION_Y_INDEX];

        this.isLoaded = unitData[index + Unit.IS_LOADED_INDEX] == 1;
        this.interceptorCount = unitData[index + Unit.INTERCEPTOR_COUNT_INDEX];

        this.isFollowing = unitData[index + Unit.IS_FOLLOWING_INDEX] == 1;
        this.isHoldingPosition = unitData[index + Unit.IS_HOLDING_POSITION_INDEX] == 1;
        this.isStuck = unitData[index + Unit.IS_STUCK_INDEX] == 1;
        this.isStasised = unitData[index + Unit.IS_STASISED_INDEX] == 1;
        this.isUnderDarkSwarm = unitData[index + Unit.IS_UNDER_DARK_SWARM_INDEX] == 1;
        this.isUnderDisruptionWeb = unitData[index + Unit.IS_UNDER_DISRUPTION_WEB_INDEX] == 1;
        this.isUnderStorm = unitData[index + Unit.IS_UNDER_STORM_INDEX] == 1;
        this.isMoving = unitData[index + Unit.IS_MOVING_INDEX] == 1;
        this.isParasited = unitData[index + Unit.IS_PARASITED_INDEX] == 1;
        this.isPatrolling = unitData[index + Unit.IS_PATROLLING_INDEX] == 1;
        this.isPlagued = unitData[index + Unit.IS_PLAGUED_INDEX] == 1;
        this.targetPosition = new Position(unitData[index + Unit.TARGET_POSITION_X_INDEX],
                unitData[index + Unit.TARGET_POSITION_Y_INDEX]);
        this.transportId = unitData[index + Unit.TRANSPORT_INDEX];
        this.acidSporeCount = unitData[index + Unit.ACID_SPORE_COUNT_INDEX];
        this.isHallucination = unitData[index + Unit.IS_HALLUCINATION_INDEX] == 1;
        this.isBlind = unitData[index + Unit.IS_BLIND_INDEX] == 1;
        this.isBraking = unitData[index + Unit.IS_BRAKING_INDEX] == 1;
        this.isDefenseMatrixed = unitData[index + Unit.IS_DEFENSE_MATRIXED_INDEX] == 1;
        this.isEnsnared = unitData[index + Unit.IS_ENSNARED_INDEX] == 1;
        this.addonId = unitData[index + Unit.ADDON_INDEX];
        this.remainingBuildTime = unitData[index + Unit.REMAINING_BUILD_TIME_INDEX];
        this.isLifted = unitData[index + Unit.IS_LIFTED_INDEX] == 1;
        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        this.remainingMorphTime = unitData[index + Unit.REMAINING_BUILD_TIME_INDEX];
        this.isStimmed = unitData[index + Unit.IS_STIMMED_INDEX] == 1;
        this.resources = unitData[index + Unit.RESOURCES_INDEX];
        this.isBeingGathered = unitData[index + Unit.IS_BEING_GATHERED_INDEX] == 1;
        this.hasNuke = unitData[index + Unit.HAS_NUKE_INDEX] == 1;
        this.nydusExitId = unitData[index + Unit.NYDUS_EXIT_INDEX];
        this.scarabCount = unitData[index + Unit.SCARAB_COUNT_INDEX];
        this.isRepairing = unitData[index + Unit.IS_REPAIRING_INDEX] == 1;
        this.sieged = unitData[index + Unit.IS_SIEGED_INDEX] == 1;
        this.spiderMineCount = unitData[index + Unit.SPIDERMINE_COUNT_INDEX];
        this.isConstructing = unitData[index + Unit.IS_CONSTRUCTING_INDEX] == 1;
        this.isGatheringGas = unitData[index + Unit.IS_GATHERING_GAS_INDEX] == 1;
        this.isGatheringMinerals = unitData[index + Unit.IS_GATHERING_MINERALS_INDEX] == 1;
        this.isCarryingGas = unitData[index + Unit.IS_CARRYING_GAS_INDEX] == 1;
        this.isCarryingMinerals = unitData[index + Unit.IS_CARRYING_MINERALS_INDEX] == 1;
        this.buildType = UnitType.values()[unitData[index + Unit.BUILDTYPE_ID_INDEX]];

        if (this.isVisible) {
            this.lastSpotted = frame;
            this.lastKnownPosition = this.position;
            this.lastKnownTilePosition = this.tilePosition;
            this.lastKnownHitPoints = this.hitPoints;
            this.lastKnownResources = this.resources;
        }
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

    public boolean isA(UnitType type) {
        return this.type == type;
    }

    public int getId() {

        return this.id;
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
        if (this == target) {
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

        return issueCommand(id, Lift, -1, -1, -1, -1);
    }

    boolean land(Position p) {

        return issueCommand(id, Land, -1, p.getX(), p.getY(), -1);
    }

    boolean move(Position p) {

        return issueCommand(id, Move, -1, p.getX(), p.getY(), -1);
    }

    public boolean exists() {

        return this.exists;
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

    protected Order getOrder() {

        return this.order;
    }

    protected Unit getOrderTarget() {

        return (this.orderTargetId >= 0)
                ? this.getUnit(this.orderTargetId)
                : null;
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

        return issueCommand(id, Cancel_Research, -1, -1, -1, -1);
    }

    protected boolean cancelUpgrade() {

        return issueCommand(id, Cancel_Upgrade, -1, -1, -1, -1);
    }

    protected boolean canResearch(TechType techType) {
        return type.equals(techType.whatResearches())
                && getPlayer(playerId).canResearch(techType);
    }

    protected boolean canUpgrade(UpgradeType upgradeType) {
        return type.equals(upgradeType.whatUpgrades())
                && getPlayer(playerId).canUpgrade(upgradeType);
    }

    protected boolean research(TechType techType) {

        return issueCommand(id, Research, -1, -1, -1, techType.getId());
    }

    protected boolean upgrade(UpgradeType upgrade) {

        return issueCommand(id, Upgrade, -1, -1, -1, upgrade.getId());
    }

    protected ResearchingFacility.UpgradeInProgress getUpgradeInProgress() {
        if (currentUpgrade == UpgradeType.None) {
            return ResearchingFacility.UpgradeInProgress.NONE;
        }
        return new ResearchingFacility.UpgradeInProgress(currentUpgrade, remainingUpgradeTime);
    }

    protected ResearchingFacility.ResearchInProgress getResearchInProgress() {
        if (currentResearch == TechType.None) {
            return ResearchingFacility.ResearchInProgress.NONE;
        }
        return new ResearchingFacility.ResearchInProgress(currentResearch, remainingResearchTime);
    }

    protected boolean canTrain(UnitType type) {
        return this.type.equals(type.whatBuilds().getFirst())
                && getPlayer(playerId).canMake(type)
                && type.requiredUnits().stream().allMatch(ut ->
                !ut.isAddon() || (addonId >= 0 && getUnit(addonId).type == ut));
    }

    protected boolean train(UnitType type) {

        return issueCommand(id, Train, -1, -1, -1, type.getId());
    }

    protected boolean cancelTrain(int slot) {

        return issueCommand(id, Cancel_Train_Slot, -1, -1, -1, slot);
    }

    protected boolean cancelTrain() {

        return issueCommand(id, Cancel_Train, -1, -1, -1, -1);
    }

    protected boolean setRallyPoint(Position p) {

        return issueCommand(id, Set_Rally_Position, -1, p.getX(), p.getY(), -1);
    }

    protected boolean setRallyPoint(Unit target) {

        return issueCommand(id, Set_Rally_Unit, target.getId(), -1, -1, -1);
    }

    protected Position getRallyPosition() {

        return new Position(rallyPositionX, rallyPositionY);
    }

    protected Unit getRallyUnit() {

        return getUnit(this.rallyUnitId);
    }

    public boolean isFlying() {
        return isFlying;
    }

    public boolean isVisible() {

        return isVisible;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public int hashCode() {

        return this.id;
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

    protected boolean issueCommand(int unitId, UnitCommandType unitCommandType, int targetUnitId, int x, int y, int extra) {
        if (issueCommand(unitId, unitCommandType.ordinal(), targetUnitId, x, y, extra)) {
            lastCommandFrame = getCurrentFrame();
            lastCommand = unitCommandType;
            return true;
        }
        return false;
    }

    private native boolean issueCommand(int unitId, int unitCommandTypeId, int targetUnitId, int x, int y, int extra);

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
    private TechType tech;
    private UpgradeType uppgrade;

    private Unit buildUnit;
    private Order order;
    private int orderTargetId;
    private Position orderTargetPosition;
    private Order secondaryOrder;

    private boolean isMorphing;
    private boolean isTargetable;
    private boolean isInvincible;
    private boolean isInWeaponRange;

    public class TrainingSlot {

        private final int slotIndex;
        private final UnitType unitType;

        protected TrainingSlot(final int slotIndex, final UnitType unitType) {

            this.slotIndex = slotIndex;
            this.unitType = unitType;
        }

        public UnitType getUnitType() {

            return this.unitType;
        }

        public boolean cancel() {

            return issueCommand(id, Cancel_Train_Slot, -1, -1, -1, this.slotIndex);
        }

        @Override
        public boolean equals(final Object object) {

            if (this == object) {
                return true;
            } else if (!(object instanceof TrainingSlot)) {
                return false;
            } else {
                final TrainingSlot trainingSlot = (TrainingSlot) object;
                return (this.slotIndex == trainingSlot.slotIndex
                        && this.unitType == trainingSlot.unitType);
            }
        }

        @Override
        public int hashCode() {

            return Objects.hash(this.slotIndex, this.unitType);
        }

    }
}
