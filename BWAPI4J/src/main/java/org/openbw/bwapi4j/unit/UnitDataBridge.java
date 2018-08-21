package org.openbw.bwapi4j.unit;

import static java.util.Objects.requireNonNull;
import static org.openbw.bwapi4j.unit.UnitImpl.MAX_LOADED_UNITS_COUNT;

import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.Order;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.util.BridgeUtils;

/**
 * Internal API to transfer unit data received from C++ to java {@link UnitImpl}s.
 */
public class UnitDataBridge {

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

  // TODO: Refactor
  private static final int TRAINING_QUEUE_SLOT_0_INDEX = 127;
  private static final int TRAINING_QUEUE_SLOT_1_INDEX = 128;
  private static final int TRAINING_QUEUE_SLOT_2_INDEX = 129;
  private static final int TRAINING_QUEUE_SLOT_3_INDEX = 130;
  private static final int TRAINING_QUEUE_SLOT_4_INDEX = 131;

  private static final int SPACE_REMAINING_INDEX = 132;

  // TODO: Refactor.
  private static final int LOADED_UNIT_SLOT_0_INDEX = 133;
  private static final int LOADED_UNIT_SLOT_1_INDEX = 134;
  private static final int LOADED_UNIT_SLOT_2_INDEX = 135;
  private static final int LOADED_UNIT_SLOT_3_INDEX = 136;
  private static final int LOADED_UNIT_SLOT_4_INDEX = 137;
  private static final int LOADED_UNIT_SLOT_5_INDEX = 138;
  private static final int LOADED_UNIT_SLOT_6_INDEX = 139;
  private static final int LOADED_UNIT_SLOT_7_INDEX = 140;

  public static final int TOTAL_PROPERTIES = 141;
  private final BW bw;

  public UnitDataBridge(BW bw) {
    requireNonNull(bw);

    this.bw = bw;
  }

  public void initialize(UnitImpl unit, int[] unitData, int index, int frame) {
    // TODO this is a workaround because initialTilePosition gives wrong results with OpenBW
    unit.initialPosition =
        new Position(unitData[index + POSITION_X_INDEX], unitData[index + POSITION_Y_INDEX]);
    unit.initialTilePosition =
        new TilePosition(
            unitData[index + TILEPOSITION_X_INDEX], unitData[index + TILEPOSITION_Y_INDEX]);
    unit.initiallySpotted = frame;
    unit.initialHitPoints = unitData[index + INITIAL_HITPOINTS_INDEX];
    unit.isInterruptible = unitData[index + IS_INTERRUPTIBLE_INDEX] == 1;
    unit.lastKnownPosition = unit.initialPosition;
    unit.lastKnownTilePosition = unit.initialTilePosition;
    unit.lastKnownHitPoints = unit.initialHitPoints;
    unit.initialResources = unitData[index + INITIAL_RESOURCES_INDEX];
    unit.carrierId = unitData[index + CARRIER_INDEX];
    unit.hatcheryId = unitData[index + HATCHERY_INDEX];
  }

  public void preUpdate(UnitImpl unit) {
    unit.isVisible = false;
    unit.exists = false;
  }

  public void update(UnitImpl unit, int[] unitData, int index, int frame) {
    unit.x = unitData[index + POSITION_X_INDEX];
    unit.y = unitData[index + POSITION_Y_INDEX];
    unit.position = new Position(unit.x, unit.y);
    unit.tilePosition =
        new TilePosition(
            unitData[index + TILEPOSITION_X_INDEX], unitData[index + TILEPOSITION_Y_INDEX]);
    unit.angle =
        BridgeUtils.parsePreservedBwapiAngle(
            BridgeUtils.parsePreservedDouble(unitData[index + ANGLE_INDEX]));
    unit.isVisible = unitData[index + IS_VISIBLE_INDEX] == 1;
    unit.exists = unitData[index + EXISTS_INDEX] == 1;
    unit.isSelected = unitData[index + IS_SELECTED_INDEX] == 1;
    unit.isFlying = unitData[index + IS_FLYING_INDEX] == 1;

    unit.order = Order.values()[unitData[index + ORDER_ID_INDEX]];
    unit.orderTargetId = unitData[index + ORDER_TARGET_ID_INDEX];

    final int orderTargetPositionX = unitData[index + ORDER_TARGET_POSITION_X_INDEX];
    final int orderTargetPositionY = unitData[index + ORDER_TARGET_POSITION_Y_INDEX];
    unit.orderTargetPosition = new Position(orderTargetPositionX, orderTargetPositionY);

    unit.secondaryOrder = Order.values()[unitData[index + SECONDARY_ORDER_ID_INDEX]];
    unit.isUpgrading = unitData[index + IS_UPGRADING_INDEX] == 1;
    unit.isResearching = unitData[index + IS_RESEARCHING_INDEX] == 1;
    unit.remainingResearchTime = unitData[index + REMAINING_RESEARCH_TIME_INDEX];
    unit.remainingUpgradeTime = unitData[index + REMAINING_UPGRADE_TIME_INDEX];
    unit.currentUpgrade = UpgradeType.withId(unitData[index + UPGRADE_ID_INDEX]);
    unit.currentResearch = TechType.withId(unitData[index + TECH_ID_INDEX]);

    unit.playerId = unitData[index + PLAYER_ID_INDEX];
    unit.player = bw.getPlayer(unit.playerId);

    unit.hitPoints = unitData[index + HITPOINTS_INDEX];
    unit.shields = unitData[index + SHIELDS_INDEX];
    unit.killCount = unitData[index + KILLCOUNT_INDEX];
    unit.isCloaked = unitData[index + IS_CLOAKED_INDEX] == 1;
    unit.isDetected = unitData[index + IS_DETECTED_INDEX] == 1;
    unit.velocityX = BridgeUtils.parsePreservedDouble(unitData[index + VELOCITY_X_INDEX]);
    unit.velocityY = BridgeUtils.parsePreservedDouble(unitData[index + VELOCITY_Y_INDEX]);
    unit.isIdle = unitData[index + IS_IDLE_INDEX] == 1;
    unit.isCompleted = unitData[index + IS_COMPLETED_INDEX] == 1;
    unit.spellCooldown = unitData[index + SPELL_COOLDOWN_INDEX];
    unit.isAccelerating = unitData[index + IS_ACCELERATING_INDEX] == 1;
    unit.isAttacking = unitData[index + IS_ATTACKING_INDEX] == 1;
    unit.isAttackFrame = unitData[index + IS_ATTACK_FRAME_INDEX] == 1;
    unit.isBeingConstructed = unitData[index + IS_BEING_CONSTRUCTED_INDEX] == 1;
    unit.isBeingHealed = unitData[index + IS_BEING_HEALED_INDEX] == 1;
    unit.isIrradiated = unitData[index + IS_IRRADIATED_INDEX] == 1;
    unit.isLockedDown = unitData[index + IS_LOCKED_DOWN_INDEX] == 1;
    unit.isMaelstrommed = unitData[index + IS_MAELSTROMMED_INDEX] == 1;
    unit.isStartingAttack = unitData[index + IS_STARTING_ATTACK_INDEX] == 1;
    unit.isUnderAttack = unitData[index + IS_UNDER_ATTACK_INDEX] == 1;
    unit.isPowered = unitData[index + IS_POWERED_INDEX] == 1;
    unit.targetId = unitData[index + TARGET_ID_INDEX];
    unit.isInterruptible = unitData[index + IS_INTERRUPTIBLE_INDEX] == 1;
    unit.lastCommand = UnitCommandType.values()[unitData[index + LAST_COMMAND_TYPE_ID_INDEX]];
    unit.lastCommandFrame = unitData[index + LAST_COMMAND_FRAME_INDEX];

    unit.builderId = unitData[index + BUILD_UNIT_ID_INDEX];
    unit.groundWeapon.update(
        unit.type.groundWeapon(), unitData[index + GROUND_WEAPON_COOLDOWN_INDEX]);
    unit.airWeapon.update(unit.type.airWeapon(), unitData[index + AIR_WEAPON_COOLDOWN_INDEX]);
    unit.energy = unitData[index + ENERGY_INDEX];
    unit.isTraining = unitData[index + IS_TRAINING_INDEX] == 1;
    unit.trainingQueueSize = unitData[index + TRAINING_QUEUE_SIZE_INDEX];

    unit.trainingQueueUnitTypeIds[0] = unitData[index + TRAINING_QUEUE_SLOT_0_INDEX];
    unit.trainingQueueUnitTypeIds[1] = unitData[index + TRAINING_QUEUE_SLOT_1_INDEX];
    unit.trainingQueueUnitTypeIds[2] = unitData[index + TRAINING_QUEUE_SLOT_2_INDEX];
    unit.trainingQueueUnitTypeIds[3] = unitData[index + TRAINING_QUEUE_SLOT_3_INDEX];
    unit.trainingQueueUnitTypeIds[4] = unitData[index + TRAINING_QUEUE_SLOT_4_INDEX];
    // TODO: Move this to a separate function and populate using cache.
    /* Populate training queue. */
    {
      unit.trainingQueue.clear();
      for (int i = 0; i < unit.trainingQueueSize; ++i) {
        unit.trainingQueue.add(
            unit.new TrainingSlot(i, UnitType.values()[unit.trainingQueueUnitTypeIds[i]]));
      }
    }

    unit.spaceRemaining = unitData[index + SPACE_REMAINING_INDEX];

    unit.loadedUnitIds[0] = unitData[index + LOADED_UNIT_SLOT_0_INDEX];
    unit.loadedUnitIds[1] = unitData[index + LOADED_UNIT_SLOT_1_INDEX];
    unit.loadedUnitIds[2] = unitData[index + LOADED_UNIT_SLOT_2_INDEX];
    unit.loadedUnitIds[3] = unitData[index + LOADED_UNIT_SLOT_3_INDEX];
    unit.loadedUnitIds[4] = unitData[index + LOADED_UNIT_SLOT_4_INDEX];
    unit.loadedUnitIds[5] = unitData[index + LOADED_UNIT_SLOT_5_INDEX];
    unit.loadedUnitIds[6] = unitData[index + LOADED_UNIT_SLOT_6_INDEX];
    unit.loadedUnitIds[7] = unitData[index + LOADED_UNIT_SLOT_7_INDEX];
    // TODO: Move this to a separate function and populate using cache.
    /* Populate list of loaded units. */
    {
      unit.loadedUnits.clear();
      int loadedUnitIdsIndex = 0;
      while (loadedUnitIdsIndex < MAX_LOADED_UNITS_COUNT) {
        final int unitId = unit.loadedUnitIds[loadedUnitIdsIndex++];
        if (unitId < 0) {
          break;
        } else {
          final MobileUnit loadedUnit = (MobileUnit) bw.getUnit(unitId);
          unit.loadedUnits.add(loadedUnit);
        }
      }
    }

    unit.remainingTrainTime = unitData[index + REMAINING_TRAIN_TIME_INDEX];
    unit.rallyUnitId = unitData[index + RALLY_UNIT_INDEX];
    unit.rallyPositionX = unitData[index + RALLY_POSITION_X_INDEX];
    unit.rallyPositionY = unitData[index + RALLY_POSITION_Y_INDEX];

    unit.isLoaded = unitData[index + IS_LOADED_INDEX] == 1;
    unit.interceptorCount = unitData[index + INTERCEPTOR_COUNT_INDEX];

    unit.isFollowing = unitData[index + IS_FOLLOWING_INDEX] == 1;
    unit.isHoldingPosition = unitData[index + IS_HOLDING_POSITION_INDEX] == 1;
    unit.isStuck = unitData[index + IS_STUCK_INDEX] == 1;
    unit.isStasised = unitData[index + IS_STASISED_INDEX] == 1;
    unit.isUnderDarkSwarm = unitData[index + IS_UNDER_DARK_SWARM_INDEX] == 1;
    unit.isUnderDisruptionWeb = unitData[index + IS_UNDER_DISRUPTION_WEB_INDEX] == 1;
    unit.isUnderStorm = unitData[index + IS_UNDER_STORM_INDEX] == 1;
    unit.isMoving = unitData[index + IS_MOVING_INDEX] == 1;
    unit.isParasited = unitData[index + IS_PARASITED_INDEX] == 1;
    unit.isPatrolling = unitData[index + IS_PATROLLING_INDEX] == 1;
    unit.isPlagued = unitData[index + IS_PLAGUED_INDEX] == 1;
    unit.targetPosition =
        new Position(
            unitData[index + TARGET_POSITION_X_INDEX], unitData[index + TARGET_POSITION_Y_INDEX]);
    unit.transportId = unitData[index + TRANSPORT_INDEX];
    unit.acidSporeCount = unitData[index + ACID_SPORE_COUNT_INDEX];
    unit.isHallucination = unitData[index + IS_HALLUCINATION_INDEX] == 1;
    unit.isBlind = unitData[index + IS_BLIND_INDEX] == 1;
    unit.isBraking = unitData[index + IS_BRAKING_INDEX] == 1;
    unit.isDefenseMatrixed = unitData[index + IS_DEFENSE_MATRIXED_INDEX] == 1;
    unit.isEnsnared = unitData[index + IS_ENSNARED_INDEX] == 1;
    unit.addonId = unitData[index + ADDON_INDEX];
    unit.remainingBuildTime = unitData[index + REMAINING_BUILD_TIME_INDEX];
    unit.isLifted = unitData[index + IS_LIFTED_INDEX] == 1;
    unit.burrowed = unitData[index + IS_BURROWED_INDEX] == 1;
    unit.remainingMorphTime = unitData[index + REMAINING_BUILD_TIME_INDEX];
    unit.isStimmed = unitData[index + IS_STIMMED_INDEX] == 1;
    unit.resources = unitData[index + RESOURCES_INDEX];
    unit.isBeingGathered = unitData[index + IS_BEING_GATHERED_INDEX] == 1;
    unit.hasNuke = unitData[index + HAS_NUKE_INDEX] == 1;
    unit.nydusExitId = unitData[index + NYDUS_EXIT_INDEX];
    unit.scarabCount = unitData[index + SCARAB_COUNT_INDEX];
    unit.isRepairing = unitData[index + IS_REPAIRING_INDEX] == 1;
    unit.sieged = unitData[index + IS_SIEGED_INDEX] == 1;
    unit.spiderMineCount = unitData[index + SPIDERMINE_COUNT_INDEX];
    unit.isConstructing = unitData[index + IS_CONSTRUCTING_INDEX] == 1;
    unit.isGatheringGas = unitData[index + IS_GATHERING_GAS_INDEX] == 1;
    unit.isGatheringMinerals = unitData[index + IS_GATHERING_MINERALS_INDEX] == 1;
    unit.isCarryingGas = unitData[index + IS_CARRYING_GAS_INDEX] == 1;
    unit.isCarryingMinerals = unitData[index + IS_CARRYING_MINERALS_INDEX] == 1;
    unit.buildType = UnitType.values()[unitData[index + BUILDTYPE_ID_INDEX]];

    if (unit.isVisible) {
      unit.lastSpotted = frame;
      unit.lastKnownPosition = unit.position;
      unit.lastKnownTilePosition = unit.tilePosition;
      unit.lastKnownHitPoints = unit.hitPoints;
      unit.lastKnownResources = unit.resources;
    }
  }
}
