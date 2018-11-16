package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.unit.Unit;

public class UnitCommandSimulation {
  public boolean canIssueCommand(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanBuildUnitType,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanBuildUnitType,
      final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanBuildUnitType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(final int unitId, final UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommand(
      final int unitId,
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
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(final int unitId, final UnitCommand command) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canIssueCommandGrouped(
      final int unitId,
      final UnitCommand command,
      final boolean checkCanUseTechPositionOnPositions,
      final boolean checkCanUseTechUnitOnUnits,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canCommand(final int unitId) {
    return canCommand_native(unitId);
  }

  private native boolean canCommand_native(int unitId);

  public boolean canCommandGrouped(final int unitId) {
    return canCommandGrouped_native(unitId);
  }

  private native boolean canCommandGrouped_native(int unitId);

  public boolean canCommandGrouped(final int unitId, final boolean checkCommandibility) {
    return canCommandGrouped_native(unitId, checkCommandibility);
  }

  private native boolean canCommandGrouped_native(int unitId, boolean checkCommandibility);

  public boolean canIssueCommandType(final int unitId, final UnitCommandType ct) {
    return canIssueCommandType_native(unitId, ct.ordinal());
  }

  private native boolean canIssueCommandType_native(int unitId, int ctId);

  public boolean canIssueCommandType(
      final int unitId, final UnitCommandType ct, final boolean checkCommandibility) {
    return canIssueCommandType_native(unitId, ct.ordinal(), checkCommandibility);
  }

  private native boolean canIssueCommandType_native(
      int unitId, int ctId, boolean checkCommandibility);

  public boolean canIssueCommandTypeGrouped(
      final int unitId, final UnitCommandType ct, final boolean checkCommandibilityGrouped) {
    return canIssueCommandTypeGrouped_native(unitId, ct.ordinal(), checkCommandibilityGrouped);
  }

  private native boolean canIssueCommandTypeGrouped_native(
      int unitId, int ctId, boolean checkCommandibilityGrouped);

  public boolean canIssueCommandTypeGrouped(final int unitId, final UnitCommandType ct) {
    return canIssueCommandTypeGrouped_native(unitId, ct.ordinal());
  }

  private native boolean canIssueCommandTypeGrouped_native(int unitId, int ctId);

  public boolean canIssueCommandTypeGrouped(
      final int unitId,
      final UnitCommandType ct,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canIssueCommandTypeGrouped_native(
        unitId, ct.ordinal(), checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canIssueCommandTypeGrouped_native(
      int unitId, int ctId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canTargetUnit(final int unitId, final Unit targetUnit) {
    return canTargetUnit_native(unitId, targetUnit.getID());
  }

  private native boolean canTargetUnit_native(int unitId, int targetUnitId);

  public boolean canTargetUnit(
      final int unitId, final Unit targetUnit, final boolean checkCommandibility) {
    return canTargetUnit_native(unitId, targetUnit.getID(), checkCommandibility);
  }

  private native boolean canTargetUnit_native(
      int unitId, int targetUnitId, boolean checkCommandibility);

  public boolean canAttack(final int unitId) {
    return canAttack_native(unitId);
  }

  private native boolean canAttack_native(int unitId);

  public boolean canAttack(final int unitId, final boolean checkCommandibility) {
    return canAttack_native(unitId, checkCommandibility);
  }

  private native boolean canAttack_native(int unitId, boolean checkCommandibility);

  public boolean canAttack(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canAttack_native(
        unitId, target.getX(), target.getY(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canAttack_native(
      int unitId, int x, int y, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canAttack(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canAttack_native(unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canAttack_native(
      int unitId, int targetId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canAttack(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      final int unitId, final Position target, final boolean checkCanTargetUnit) {
    return canAttack_native(unitId, target.getX(), target.getY(), checkCanTargetUnit);
  }

  private native boolean canAttack_native(int unitId, int x, int y, boolean checkCanTargetUnit);

  public boolean canAttack(final int unitId, final Unit target, final boolean checkCanTargetUnit) {
    return canAttack_native(unitId, target.getID(), checkCanTargetUnit);
  }

  private native boolean canAttack_native(int unitId, int targetId, boolean checkCanTargetUnit);

  public boolean canAttack(
      final int unitId, final PositionOrUnit target, final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(final int unitId, final Position target) {
    return canAttack_native(unitId, target.getX(), target.getY());
  }

  private native boolean canAttack_native(int unitId, int x, int y);

  public boolean canAttack(final int unitId, final Unit target) {
    return canAttack_native(unitId, target.getID());
  }

  private native boolean canAttack_native(int unitId, int targetId);

  public boolean canAttack(final int unitId, final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttack(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canAttack_native(
        unitId,
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canAttack_native(
      int unitId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canAttack(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canAttack_native(
        unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canAttack_native(
      int unitId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canAttack(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(final int unitId, final boolean checkCommandibilityGrouped) {
    return canAttackGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canAttackGrouped_native(int unitId, boolean checkCommandibilityGrouped);

  public boolean canAttackGrouped(final int unitId) {
    return canAttackGrouped_native(unitId);
  }

  private native boolean canAttackGrouped_native(int unitId);

  public boolean canAttackGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canAttackGrouped_native(unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canAttackGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canAttackGrouped(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return canAttackGrouped_native(
        unitId,
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped);
  }

  private native boolean canAttackGrouped_native(
      int unitId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped);

  public boolean canAttackGrouped(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return canAttackGrouped_native(
        unitId,
        target.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped);
  }

  private native boolean canAttackGrouped_native(
      int unitId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped);

  public boolean canAttackGrouped(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canAttackGrouped_native(
        unitId, target.getX(), target.getY(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canAttackGrouped_native(
      int unitId, int x, int y, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canAttackGrouped(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canAttackGrouped_native(
        unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canAttackGrouped_native(
      int unitId, int targetId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canAttackGrouped(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      final int unitId, final Position target, final boolean checkCanTargetUnit) {
    return canAttackGrouped_native(unitId, target.getX(), target.getY(), checkCanTargetUnit);
  }

  private native boolean canAttackGrouped_native(
      int unitId, int x, int y, boolean checkCanTargetUnit);

  public boolean canAttackGrouped(
      final int unitId, final Unit target, final boolean checkCanTargetUnit) {
    return canAttackGrouped_native(unitId, target.getID(), checkCanTargetUnit);
  }

  private native boolean canAttackGrouped_native(
      int unitId, int targetId, boolean checkCanTargetUnit);

  public boolean canAttackGrouped(
      final int unitId, final PositionOrUnit target, final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(final int unitId, final Position target) {
    return canAttackGrouped_native(unitId, target.getX(), target.getY());
  }

  private native boolean canAttackGrouped_native(int unitId, int x, int y);

  public boolean canAttackGrouped(final int unitId, final Unit target) {
    return canAttackGrouped_native(unitId, target.getID());
  }

  private native boolean canAttackGrouped_native(int unitId, int targetId);

  public boolean canAttackGrouped(final int unitId, final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackGrouped(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canAttackGrouped_native(
        unitId,
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped,
        checkCommandibility);
  }

  private native boolean canAttackGrouped_native(
      int unitId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility);

  public boolean canAttackGrouped(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canAttackGrouped_native(
        unitId,
        target.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped,
        checkCommandibility);
  }

  private native boolean canAttackGrouped_native(
      int unitId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility);

  public boolean canAttackGrouped(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canAttackMove(final int unitId) {
    return canAttackMove_native(unitId);
  }

  private native boolean canAttackMove_native(int unitId);

  public boolean canAttackMove(final int unitId, final boolean checkCommandibility) {
    return canAttackMove_native(unitId, checkCommandibility);
  }

  private native boolean canAttackMove_native(int unitId, boolean checkCommandibility);

  public boolean canAttackMoveGrouped(final int unitId, final boolean checkCommandibilityGrouped) {
    return canAttackMoveGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canAttackMoveGrouped_native(
      int unitId, boolean checkCommandibilityGrouped);

  public boolean canAttackMoveGrouped(final int unitId) {
    return canAttackMoveGrouped_native(unitId);
  }

  private native boolean canAttackMoveGrouped_native(int unitId);

  public boolean canAttackMoveGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canAttackMoveGrouped_native(unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canAttackMoveGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canAttackUnit(final int unitId) {
    return canAttackUnit_native(unitId);
  }

  private native boolean canAttackUnit_native(int unitId);

  public boolean canAttackUnit(final int unitId, final boolean checkCommandibility) {
    return canAttackUnit_native(unitId, checkCommandibility);
  }

  private native boolean canAttackUnit_native(int unitId, boolean checkCommandibility);

  public boolean canAttackUnit(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canAttackUnit_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canAttackUnit_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canAttackUnit(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canAttackUnit_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canAttackUnit_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canAttackUnit(final int unitId, final Unit targetUnit) {
    return canAttackUnit_native(unitId, targetUnit.getID());
  }

  private native boolean canAttackUnit_native(int unitId, int targetUnitId);

  public boolean canAttackUnit(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canAttackUnit_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canAttackUnit_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canAttackUnitGrouped(final int unitId, final boolean checkCommandibilityGrouped) {
    return canAttackUnitGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canAttackUnitGrouped_native(
      int unitId, boolean checkCommandibilityGrouped);

  public boolean canAttackUnitGrouped(final int unitId) {
    return canAttackUnitGrouped_native(unitId);
  }

  private native boolean canAttackUnitGrouped_native(int unitId);

  public boolean canAttackUnitGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canAttackUnitGrouped_native(unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canAttackUnitGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canAttackUnitGrouped(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return canAttackUnitGrouped_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped);
  }

  private native boolean canAttackUnitGrouped_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped);

  public boolean canAttackUnitGrouped(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canAttackUnitGrouped_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canAttackUnitGrouped_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canAttackUnitGrouped(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canAttackUnitGrouped_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canAttackUnitGrouped_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canAttackUnitGrouped(final int unitId, final Unit targetUnit) {
    return canAttackUnitGrouped_native(unitId, targetUnit.getID());
  }

  private native boolean canAttackUnitGrouped_native(int unitId, int targetUnitId);

  public boolean canAttackUnitGrouped(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canAttackUnitGrouped_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped,
        checkCommandibility);
  }

  private native boolean canAttackUnitGrouped_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility);

  public boolean canBuild(final int unitId) {
    return canBuild_native(unitId);
  }

  private native boolean canBuild_native(int unitId);

  public boolean canBuild(final int unitId, final boolean checkCommandibility) {
    return canBuild_native(unitId, checkCommandibility);
  }

  private native boolean canBuild_native(int unitId, boolean checkCommandibility);

  public boolean canBuild(
      final int unitId, final UnitType uType, final boolean checkCanIssueCommandType) {
    return canBuild_native(unitId, uType.getID(), checkCanIssueCommandType);
  }

  private native boolean canBuild_native(int unitId, int uTypeId, boolean checkCanIssueCommandType);

  public boolean canBuild(final int unitId, final UnitType uType) {
    return canBuild_native(unitId, uType.getID());
  }

  private native boolean canBuild_native(int unitId, int uTypeId);

  public boolean canBuild(
      final int unitId,
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canBuild_native(unitId, uType.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canBuild_native(
      int unitId, int uTypeId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canBuild(
      final int unitId,
      final UnitType uType,
      final TilePosition tilePos,
      final boolean checkTargetUnitType,
      final boolean checkCanIssueCommandType) {
    return canBuild_native(
        unitId,
        uType.getID(),
        tilePos.getX(),
        tilePos.getY(),
        checkTargetUnitType,
        checkCanIssueCommandType);
  }

  private native boolean canBuild_native(
      int unitId,
      int uTypeId,
      int x,
      int y,
      boolean checkTargetUnitType,
      boolean checkCanIssueCommandType);

  public boolean canBuild(
      final int unitId,
      final UnitType uType,
      final TilePosition tilePos,
      final boolean checkTargetUnitType) {
    return canBuild_native(
        unitId, uType.getID(), tilePos.getX(), tilePos.getY(), checkTargetUnitType);
  }

  private native boolean canBuild_native(
      int unitId, int uTypeId, int x, int y, boolean checkTargetUnitType);

  public boolean canBuild(final int unitId, final UnitType uType, final TilePosition tilePos) {
    return canBuild_native(unitId, uType.getID(), tilePos.getX(), tilePos.getY());
  }

  private native boolean canBuild_native(int unitId, int uTypeId, int x, int y);

  public boolean canBuild(
      final int unitId,
      final UnitType uType,
      final TilePosition tilePos,
      final boolean checkTargetUnitType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canBuild_native(
        unitId,
        uType.getID(),
        tilePos.getX(),
        tilePos.getY(),
        checkTargetUnitType,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canBuild_native(
      int unitId,
      int uTypeId,
      int x,
      int y,
      boolean checkTargetUnitType,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canBuildAddon(final int unitId) {
    return canBuildAddon_native(unitId);
  }

  private native boolean canBuildAddon_native(int unitId);

  public boolean canBuildAddon(final int unitId, final boolean checkCommandibility) {
    return canBuildAddon_native(unitId, checkCommandibility);
  }

  private native boolean canBuildAddon_native(int unitId, boolean checkCommandibility);

  public boolean canBuildAddon(
      final int unitId, final UnitType uType, final boolean checkCanIssueCommandType) {
    return canBuildAddon_native(unitId, uType.getID(), checkCanIssueCommandType);
  }

  private native boolean canBuildAddon_native(
      int unitId, int uTypeId, boolean checkCanIssueCommandType);

  public boolean canBuildAddon(final int unitId, final UnitType uType) {
    return canBuildAddon_native(unitId, uType.getID());
  }

  private native boolean canBuildAddon_native(int unitId, int uTypeId);

  public boolean canBuildAddon(
      final int unitId,
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canBuildAddon_native(
        unitId, uType.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canBuildAddon_native(
      int unitId, int uTypeId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canTrain(final int unitId) {
    return canTrain_native(unitId);
  }

  private native boolean canTrain_native(int unitId);

  public boolean canTrain(final int unitId, final boolean checkCommandibility) {
    return canTrain_native(unitId, checkCommandibility);
  }

  private native boolean canTrain_native(int unitId, boolean checkCommandibility);

  public boolean canTrain(
      final int unitId, final UnitType uType, final boolean checkCanIssueCommandType) {
    return canTrain_native(unitId, uType.getID(), checkCanIssueCommandType);
  }

  private native boolean canTrain_native(int unitId, int uTypeId, boolean checkCanIssueCommandType);

  public boolean canTrain(final int unitId, final UnitType uType) {
    return canTrain_native(unitId, uType.getID());
  }

  private native boolean canTrain_native(int unitId, int uTypeId);

  public boolean canTrain(
      final int unitId,
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canTrain_native(unitId, uType.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canTrain_native(
      int unitId, int uTypeId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canMorph(final int unitId) {
    return canMorph_native(unitId);
  }

  private native boolean canMorph_native(int unitId);

  public boolean canMorph(final int unitId, final boolean checkCommandibility) {
    return canMorph_native(unitId, checkCommandibility);
  }

  private native boolean canMorph_native(int unitId, boolean checkCommandibility);

  public boolean canMorph(
      final int unitId, final UnitType uType, final boolean checkCanIssueCommandType) {
    return canMorph_native(unitId, uType.getID(), checkCanIssueCommandType);
  }

  private native boolean canMorph_native(int unitId, int uTypeId, boolean checkCanIssueCommandType);

  public boolean canMorph(final int unitId, final UnitType uType) {
    return canMorph_native(unitId, uType.getID());
  }

  private native boolean canMorph_native(int unitId, int uTypeId);

  public boolean canMorph(
      final int unitId,
      final UnitType uType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canMorph_native(unitId, uType.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canMorph_native(
      int unitId, int uTypeId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canResearch(final int unitId) {
    return canResearch_native(unitId);
  }

  private native boolean canResearch_native(int unitId);

  public boolean canResearch(final int unitId, final boolean checkCommandibility) {
    return canResearch_native(unitId, checkCommandibility);
  }

  private native boolean canResearch_native(int unitId, boolean checkCommandibility);

  public boolean canResearch(final int unitId, final TechType type) {
    return canResearch_native(unitId, type.getID());
  }

  private native boolean canResearch_native(int unitId, int typeId);

  public boolean canResearch(
      final int unitId, final TechType type, final boolean checkCanIssueCommandType) {
    return canResearch_native(unitId, type.getID(), checkCanIssueCommandType);
  }

  private native boolean canResearch_native(
      int unitId, int typeId, boolean checkCanIssueCommandType);

  public boolean canUpgrade(final int unitId) {
    return canUpgrade_native(unitId);
  }

  private native boolean canUpgrade_native(int unitId);

  public boolean canUpgrade(final int unitId, final boolean checkCommandibility) {
    return canUpgrade_native(unitId, checkCommandibility);
  }

  private native boolean canUpgrade_native(int unitId, boolean checkCommandibility);

  public boolean canUpgrade(final int unitId, final UpgradeType type) {
    return canUpgrade_native(unitId, type.getID());
  }

  private native boolean canUpgrade_native(int unitId, int typeId);

  public boolean canUpgrade(
      final int unitId, final UpgradeType type, final boolean checkCanIssueCommandType) {
    return canUpgrade_native(unitId, type.getID(), checkCanIssueCommandType);
  }

  private native boolean canUpgrade_native(
      int unitId, int typeId, boolean checkCanIssueCommandType);

  public boolean canSetRallyPoint(final int unitId) {
    return canSetRallyPoint_native(unitId);
  }

  private native boolean canSetRallyPoint_native(int unitId);

  public boolean canSetRallyPoint(final int unitId, final boolean checkCommandibility) {
    return canSetRallyPoint_native(unitId, checkCommandibility);
  }

  private native boolean canSetRallyPoint_native(int unitId, boolean checkCommandibility);

  public boolean canSetRallyPoint(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canSetRallyPoint_native(
        unitId, target.getX(), target.getY(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canSetRallyPoint_native(
      int unitId, int x, int y, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canSetRallyPoint(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canSetRallyPoint_native(
        unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canSetRallyPoint_native(
      int unitId, int targetId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canSetRallyPoint(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      final int unitId, final Position target, final boolean checkCanTargetUnit) {
    return canSetRallyPoint_native(unitId, target.getX(), target.getY(), checkCanTargetUnit);
  }

  private native boolean canSetRallyPoint_native(
      int unitId, int x, int y, boolean checkCanTargetUnit);

  public boolean canSetRallyPoint(
      final int unitId, final Unit target, final boolean checkCanTargetUnit) {
    return canSetRallyPoint_native(unitId, target.getID(), checkCanTargetUnit);
  }

  private native boolean canSetRallyPoint_native(
      int unitId, int targetId, boolean checkCanTargetUnit);

  public boolean canSetRallyPoint(
      final int unitId, final PositionOrUnit target, final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(final int unitId, final Position target) {
    return canSetRallyPoint_native(unitId, target.getX(), target.getY());
  }

  private native boolean canSetRallyPoint_native(int unitId, int x, int y);

  public boolean canSetRallyPoint(final int unitId, final Unit target) {
    return canSetRallyPoint_native(unitId, target.getID());
  }

  private native boolean canSetRallyPoint_native(int unitId, int targetId);

  public boolean canSetRallyPoint(final int unitId, final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPoint(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canSetRallyPoint_native(
        unitId,
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canSetRallyPoint_native(
      int unitId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canSetRallyPoint(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canSetRallyPoint_native(
        unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canSetRallyPoint_native(
      int unitId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canSetRallyPoint(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canSetRallyPosition(final int unitId) {
    return canSetRallyPosition_native(unitId);
  }

  private native boolean canSetRallyPosition_native(int unitId);

  public boolean canSetRallyPosition(final int unitId, final boolean checkCommandibility) {
    return canSetRallyPosition_native(unitId, checkCommandibility);
  }

  private native boolean canSetRallyPosition_native(int unitId, boolean checkCommandibility);

  public boolean canSetRallyUnit(final int unitId) {
    return canSetRallyUnit_native(unitId);
  }

  private native boolean canSetRallyUnit_native(int unitId);

  public boolean canSetRallyUnit(final int unitId, final boolean checkCommandibility) {
    return canSetRallyUnit_native(unitId, checkCommandibility);
  }

  private native boolean canSetRallyUnit_native(int unitId, boolean checkCommandibility);

  public boolean canSetRallyUnit(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canSetRallyUnit_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canSetRallyUnit_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canSetRallyUnit(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canSetRallyUnit_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canSetRallyUnit_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canSetRallyUnit(final int unitId, final Unit targetUnit) {
    return canSetRallyUnit_native(unitId, targetUnit.getID());
  }

  private native boolean canSetRallyUnit_native(int unitId, int targetUnitId);

  public boolean canSetRallyUnit(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canSetRallyUnit_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canSetRallyUnit_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canMove(final int unitId) {
    return canMove_native(unitId);
  }

  private native boolean canMove_native(int unitId);

  public boolean canMove(final int unitId, final boolean checkCommandibility) {
    return canMove_native(unitId, checkCommandibility);
  }

  private native boolean canMove_native(int unitId, boolean checkCommandibility);

  public boolean canMoveGrouped(final int unitId, final boolean checkCommandibilityGrouped) {
    return canMoveGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canMoveGrouped_native(int unitId, boolean checkCommandibilityGrouped);

  public boolean canMoveGrouped(final int unitId) {
    return canMoveGrouped_native(unitId);
  }

  private native boolean canMoveGrouped_native(int unitId);

  public boolean canMoveGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canMoveGrouped_native(unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canMoveGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canPatrol(final int unitId) {
    return canPatrol_native(unitId);
  }

  private native boolean canPatrol_native(int unitId);

  public boolean canPatrol(final int unitId, final boolean checkCommandibility) {
    return canPatrol_native(unitId, checkCommandibility);
  }

  private native boolean canPatrol_native(int unitId, boolean checkCommandibility);

  public boolean canPatrolGrouped(final int unitId, final boolean checkCommandibilityGrouped) {
    return canPatrolGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canPatrolGrouped_native(int unitId, boolean checkCommandibilityGrouped);

  public boolean canPatrolGrouped(final int unitId) {
    return canPatrolGrouped_native(unitId);
  }

  private native boolean canPatrolGrouped_native(int unitId);

  public boolean canPatrolGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canPatrolGrouped_native(unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canPatrolGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canFollow(final int unitId) {
    return canFollow_native(unitId);
  }

  private native boolean canFollow_native(int unitId);

  public boolean canFollow(final int unitId, final boolean checkCommandibility) {
    return canFollow_native(unitId, checkCommandibility);
  }

  private native boolean canFollow_native(int unitId, boolean checkCommandibility);

  public boolean canFollow(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canFollow_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canFollow_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canFollow(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canFollow_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canFollow_native(int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canFollow(final int unitId, final Unit targetUnit) {
    return canFollow_native(unitId, targetUnit.getID());
  }

  private native boolean canFollow_native(int unitId, int targetUnitId);

  public boolean canFollow(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canFollow_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canFollow_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canGather(final int unitId) {
    return canGather_native(unitId);
  }

  private native boolean canGather_native(int unitId);

  public boolean canGather(final int unitId, final boolean checkCommandibility) {
    return canGather_native(unitId, checkCommandibility);
  }

  private native boolean canGather_native(int unitId, boolean checkCommandibility);

  public boolean canGather(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canGather_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canGather_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canGather(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canGather_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canGather_native(int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canGather(final int unitId, final Unit targetUnit) {
    return canGather_native(unitId, targetUnit.getID());
  }

  private native boolean canGather_native(int unitId, int targetUnitId);

  public boolean canGather(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canGather_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canGather_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canReturnCargo(final int unitId) {
    return canReturnCargo_native(unitId);
  }

  private native boolean canReturnCargo_native(int unitId);

  public boolean canReturnCargo(final int unitId, final boolean checkCommandibility) {
    return canReturnCargo_native(unitId, checkCommandibility);
  }

  private native boolean canReturnCargo_native(int unitId, boolean checkCommandibility);

  public boolean canHoldPosition(final int unitId) {
    return canHoldPosition_native(unitId);
  }

  private native boolean canHoldPosition_native(int unitId);

  public boolean canHoldPosition(final int unitId, final boolean checkCommandibility) {
    return canHoldPosition_native(unitId, checkCommandibility);
  }

  private native boolean canHoldPosition_native(int unitId, boolean checkCommandibility);

  public boolean canStop(final int unitId) {
    return canStop_native(unitId);
  }

  private native boolean canStop_native(int unitId);

  public boolean canStop(final int unitId, final boolean checkCommandibility) {
    return canStop_native(unitId, checkCommandibility);
  }

  private native boolean canStop_native(int unitId, boolean checkCommandibility);

  public boolean canRepair(final int unitId) {
    return canRepair_native(unitId);
  }

  private native boolean canRepair_native(int unitId);

  public boolean canRepair(final int unitId, final boolean checkCommandibility) {
    return canRepair_native(unitId, checkCommandibility);
  }

  private native boolean canRepair_native(int unitId, boolean checkCommandibility);

  public boolean canRepair(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canRepair_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canRepair_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canRepair(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canRepair_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canRepair_native(int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canRepair(final int unitId, final Unit targetUnit) {
    return canRepair_native(unitId, targetUnit.getID());
  }

  private native boolean canRepair_native(int unitId, int targetUnitId);

  public boolean canRepair(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canRepair_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canRepair_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canBurrow(final int unitId) {
    return canBurrow_native(unitId);
  }

  private native boolean canBurrow_native(int unitId);

  public boolean canBurrow(final int unitId, final boolean checkCommandibility) {
    return canBurrow_native(unitId, checkCommandibility);
  }

  private native boolean canBurrow_native(int unitId, boolean checkCommandibility);

  public boolean canUnburrow(final int unitId) {
    return canUnburrow_native(unitId);
  }

  private native boolean canUnburrow_native(int unitId);

  public boolean canUnburrow(final int unitId, final boolean checkCommandibility) {
    return canUnburrow_native(unitId, checkCommandibility);
  }

  private native boolean canUnburrow_native(int unitId, boolean checkCommandibility);

  public boolean canCloak(final int unitId) {
    return canCloak_native(unitId);
  }

  private native boolean canCloak_native(int unitId);

  public boolean canCloak(final int unitId, final boolean checkCommandibility) {
    return canCloak_native(unitId, checkCommandibility);
  }

  private native boolean canCloak_native(int unitId, boolean checkCommandibility);

  public boolean canDecloak(final int unitId) {
    return canDecloak_native(unitId);
  }

  private native boolean canDecloak_native(int unitId);

  public boolean canDecloak(final int unitId, final boolean checkCommandibility) {
    return canDecloak_native(unitId, checkCommandibility);
  }

  private native boolean canDecloak_native(int unitId, boolean checkCommandibility);

  public boolean canSiege(final int unitId) {
    return canSiege_native(unitId);
  }

  private native boolean canSiege_native(int unitId);

  public boolean canSiege(final int unitId, final boolean checkCommandibility) {
    return canSiege_native(unitId, checkCommandibility);
  }

  private native boolean canSiege_native(int unitId, boolean checkCommandibility);

  public boolean canUnsiege(final int unitId) {
    return canUnsiege_native(unitId);
  }

  private native boolean canUnsiege_native(int unitId);

  public boolean canUnsiege(final int unitId, final boolean checkCommandibility) {
    return canUnsiege_native(unitId, checkCommandibility);
  }

  private native boolean canUnsiege_native(int unitId, boolean checkCommandibility);

  public boolean canLift(final int unitId) {
    return canLift_native(unitId);
  }

  private native boolean canLift_native(int unitId);

  public boolean canLift(final int unitId, final boolean checkCommandibility) {
    return canLift_native(unitId, checkCommandibility);
  }

  private native boolean canLift_native(int unitId, boolean checkCommandibility);

  public boolean canLand(final int unitId) {
    return canLand_native(unitId);
  }

  private native boolean canLand_native(int unitId);

  public boolean canLand(final int unitId, final boolean checkCommandibility) {
    return canLand_native(unitId, checkCommandibility);
  }

  private native boolean canLand_native(int unitId, boolean checkCommandibility);

  public boolean canLand(
      final int unitId, final TilePosition target, final boolean checkCanIssueCommandType) {
    return canLand_native(unitId, target.getX(), target.getY(), checkCanIssueCommandType);
  }

  private native boolean canLand_native(int unitId, int x, int y, boolean checkCanIssueCommandType);

  public boolean canLand(final int unitId, final TilePosition target) {
    return canLand_native(unitId, target.getX(), target.getY());
  }

  private native boolean canLand_native(int unitId, int x, int y);

  public boolean canLand(
      final int unitId,
      final TilePosition target,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canLand_native(
        unitId, target.getX(), target.getY(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canLand_native(
      int unitId, int x, int y, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canLoad(final int unitId) {
    return canLoad_native(unitId);
  }

  private native boolean canLoad_native(int unitId);

  public boolean canLoad(final int unitId, final boolean checkCommandibility) {
    return canLoad_native(unitId, checkCommandibility);
  }

  private native boolean canLoad_native(int unitId, boolean checkCommandibility);

  public boolean canLoad(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canLoad_native(unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canLoad_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canLoad(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canLoad_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canLoad_native(int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canLoad(final int unitId, final Unit targetUnit) {
    return canLoad_native(unitId, targetUnit.getID());
  }

  private native boolean canLoad_native(int unitId, int targetUnitId);

  public boolean canLoad(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canLoad_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canLoad_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canUnloadWithOrWithoutTarget(final int unitId) {
    return canUnloadWithOrWithoutTarget_native(unitId);
  }

  private native boolean canUnloadWithOrWithoutTarget_native(int unitId);

  public boolean canUnloadWithOrWithoutTarget(final int unitId, final boolean checkCommandibility) {
    return canUnloadWithOrWithoutTarget_native(unitId, checkCommandibility);
  }

  private native boolean canUnloadWithOrWithoutTarget_native(
      int unitId, boolean checkCommandibility);

  public boolean canUnloadAtPosition(
      final int unitId, final Position targDropPos, final boolean checkCanIssueCommandType) {
    return canUnloadAtPosition_native(
        unitId, targDropPos.getX(), targDropPos.getY(), checkCanIssueCommandType);
  }

  private native boolean canUnloadAtPosition_native(
      int unitId, int x, int y, boolean checkCanIssueCommandType);

  public boolean canUnloadAtPosition(final int unitId, final Position targDropPos) {
    return canUnloadAtPosition_native(unitId, targDropPos.getX(), targDropPos.getY());
  }

  private native boolean canUnloadAtPosition_native(int unitId, int x, int y);

  public boolean canUnloadAtPosition(
      final int unitId,
      final Position targDropPos,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUnloadAtPosition_native(
        unitId,
        targDropPos.getX(),
        targDropPos.getY(),
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canUnloadAtPosition_native(
      int unitId, int x, int y, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canUnload(final int unitId) {
    return canUnload_native(unitId);
  }

  private native boolean canUnload_native(int unitId);

  public boolean canUnload(final int unitId, final boolean checkCommandibility) {
    return canUnload_native(unitId, checkCommandibility);
  }

  private native boolean canUnload_native(int unitId, boolean checkCommandibility);

  public boolean canUnload(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkPosition,
      final boolean checkCanIssueCommandType) {
    return canUnload_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkPosition, checkCanIssueCommandType);
  }

  private native boolean canUnload_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkPosition,
      boolean checkCanIssueCommandType);

  public boolean canUnload(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkPosition) {
    return canUnload_native(unitId, targetUnit.getID(), checkCanTargetUnit, checkPosition);
  }

  private native boolean canUnload_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkPosition);

  public boolean canUnload(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canUnload_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canUnload_native(int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canUnload(final int unitId, final Unit targetUnit) {
    return canUnload_native(unitId, targetUnit.getID());
  }

  private native boolean canUnload_native(int unitId, int targetUnitId);

  public boolean canUnload(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkPosition,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUnload_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkPosition,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canUnload_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkPosition,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canUnloadAll(final int unitId) {
    return canUnloadAll_native(unitId);
  }

  private native boolean canUnloadAll_native(int unitId);

  public boolean canUnloadAll(final int unitId, final boolean checkCommandibility) {
    return canUnloadAll_native(unitId, checkCommandibility);
  }

  private native boolean canUnloadAll_native(int unitId, boolean checkCommandibility);

  public boolean canUnloadAllPosition(final int unitId) {
    return canUnloadAllPosition_native(unitId);
  }

  private native boolean canUnloadAllPosition_native(int unitId);

  public boolean canUnloadAllPosition(final int unitId, final boolean checkCommandibility) {
    return canUnloadAllPosition_native(unitId, checkCommandibility);
  }

  private native boolean canUnloadAllPosition_native(int unitId, boolean checkCommandibility);

  public boolean canUnloadAllPosition(
      final int unitId, final Position targDropPos, final boolean checkCanIssueCommandType) {
    return canUnloadAllPosition_native(
        unitId, targDropPos.getX(), targDropPos.getY(), checkCanIssueCommandType);
  }

  private native boolean canUnloadAllPosition_native(
      int unitId, int x, int y, boolean checkCanIssueCommandType);

  public boolean canUnloadAllPosition(final int unitId, final Position targDropPos) {
    return canUnloadAllPosition_native(unitId, targDropPos.getX(), targDropPos.getY());
  }

  private native boolean canUnloadAllPosition_native(int unitId, int x, int y);

  public boolean canUnloadAllPosition(
      final int unitId,
      final Position targDropPos,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUnloadAllPosition_native(
        unitId,
        targDropPos.getX(),
        targDropPos.getY(),
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canUnloadAllPosition_native(
      int unitId, int x, int y, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canRightClick(final int unitId) {
    return canRightClick_native(unitId);
  }

  private native boolean canRightClick_native(int unitId);

  public boolean canRightClick(final int unitId, final boolean checkCommandibility) {
    return canRightClick_native(unitId, checkCommandibility);
  }

  private native boolean canRightClick_native(int unitId, boolean checkCommandibility);

  public boolean canRightClick(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canRightClick_native(
        unitId, target.getX(), target.getY(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canRightClick_native(
      int unitId, int x, int y, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canRightClick(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canRightClick_native(
        unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canRightClick_native(
      int unitId, int targetId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canRightClick(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      final int unitId, final Position target, final boolean checkCanTargetUnit) {
    return canRightClick_native(unitId, target.getX(), target.getY(), checkCanTargetUnit);
  }

  private native boolean canRightClick_native(int unitId, int x, int y, boolean checkCanTargetUnit);

  public boolean canRightClick(
      final int unitId, final Unit target, final boolean checkCanTargetUnit) {
    return canRightClick_native(unitId, target.getID(), checkCanTargetUnit);
  }

  private native boolean canRightClick_native(int unitId, int targetId, boolean checkCanTargetUnit);

  public boolean canRightClick(
      final int unitId, final PositionOrUnit target, final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(final int unitId, final Position target) {
    return canRightClick_native(unitId, target.getX(), target.getY());
  }

  private native boolean canRightClick_native(int unitId, int x, int y);

  public boolean canRightClick(final int unitId, final Unit target) {
    return canRightClick_native(unitId, target.getID());
  }

  private native boolean canRightClick_native(int unitId, int targetId);

  public boolean canRightClick(final int unitId, final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClick(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canRightClick_native(
        unitId,
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canRightClick_native(
      int unitId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canRightClick(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canRightClick_native(
        unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canRightClick_native(
      int unitId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canRightClick(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(final int unitId, final boolean checkCommandibilityGrouped) {
    return canRightClickGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canRightClickGrouped_native(
      int unitId, boolean checkCommandibilityGrouped);

  public boolean canRightClickGrouped(final int unitId) {
    return canRightClickGrouped_native(unitId);
  }

  private native boolean canRightClickGrouped_native(int unitId);

  public boolean canRightClickGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canRightClickGrouped_native(unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canRightClickGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canRightClickGrouped(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return canRightClickGrouped_native(
        unitId,
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped);
  }

  private native boolean canRightClickGrouped_native(
      int unitId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped);

  public boolean canRightClickGrouped(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return canRightClickGrouped_native(
        unitId,
        target.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped);
  }

  private native boolean canRightClickGrouped_native(
      int unitId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped);

  public boolean canRightClickGrouped(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canRightClickGrouped_native(
        unitId, target.getX(), target.getY(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canRightClickGrouped_native(
      int unitId, int x, int y, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canRightClickGrouped(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canRightClickGrouped_native(
        unitId, target.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canRightClickGrouped_native(
      int unitId, int targetId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canRightClickGrouped(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      final int unitId, final Position target, final boolean checkCanTargetUnit) {
    return canRightClickGrouped_native(unitId, target.getX(), target.getY(), checkCanTargetUnit);
  }

  private native boolean canRightClickGrouped_native(
      int unitId, int x, int y, boolean checkCanTargetUnit);

  public boolean canRightClickGrouped(
      final int unitId, final Unit target, final boolean checkCanTargetUnit) {
    return canRightClickGrouped_native(unitId, target.getID(), checkCanTargetUnit);
  }

  private native boolean canRightClickGrouped_native(
      int unitId, int targetId, boolean checkCanTargetUnit);

  public boolean canRightClickGrouped(
      final int unitId, final PositionOrUnit target, final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(final int unitId, final Position target) {
    return canRightClickGrouped_native(unitId, target.getX(), target.getY());
  }

  private native boolean canRightClickGrouped_native(int unitId, int x, int y);

  public boolean canRightClickGrouped(final int unitId, final Unit target) {
    return canRightClickGrouped_native(unitId, target.getID());
  }

  private native boolean canRightClickGrouped_native(int unitId, int targetId);

  public boolean canRightClickGrouped(final int unitId, final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickGrouped(
      final int unitId,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canRightClickGrouped_native(
        unitId,
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped,
        checkCommandibility);
  }

  private native boolean canRightClickGrouped_native(
      int unitId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility);

  public boolean canRightClickGrouped(
      final int unitId,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canRightClickGrouped_native(
        unitId,
        target.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped,
        checkCommandibility);
  }

  private native boolean canRightClickGrouped_native(
      int unitId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility);

  public boolean canRightClickGrouped(
      final int unitId,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canRightClickPosition(final int unitId) {
    return canRightClickPosition_native(unitId);
  }

  private native boolean canRightClickPosition_native(int unitId);

  public boolean canRightClickPosition(final int unitId, final boolean checkCommandibility) {
    return canRightClickPosition_native(unitId, checkCommandibility);
  }

  private native boolean canRightClickPosition_native(int unitId, boolean checkCommandibility);

  public boolean canRightClickPositionGrouped(
      final int unitId, final boolean checkCommandibilityGrouped) {
    return canRightClickPositionGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canRightClickPositionGrouped_native(
      int unitId, boolean checkCommandibilityGrouped);

  public boolean canRightClickPositionGrouped(final int unitId) {
    return canRightClickPositionGrouped_native(unitId);
  }

  private native boolean canRightClickPositionGrouped_native(int unitId);

  public boolean canRightClickPositionGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canRightClickPositionGrouped_native(
        unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canRightClickPositionGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canRightClickUnit(final int unitId) {
    return canRightClickUnit_native(unitId);
  }

  private native boolean canRightClickUnit_native(int unitId);

  public boolean canRightClickUnit(final int unitId, final boolean checkCommandibility) {
    return canRightClickUnit_native(unitId, checkCommandibility);
  }

  private native boolean canRightClickUnit_native(int unitId, boolean checkCommandibility);

  public boolean canRightClickUnit(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canRightClickUnit_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canRightClickUnit_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canRightClickUnit(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canRightClickUnit_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canRightClickUnit_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canRightClickUnit(final int unitId, final Unit targetUnit) {
    return canRightClickUnit_native(unitId, targetUnit.getID());
  }

  private native boolean canRightClickUnit_native(int unitId, int targetUnitId);

  public boolean canRightClickUnit(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canRightClickUnit_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canRightClickUnit_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canRightClickUnitGrouped(
      final int unitId, final boolean checkCommandibilityGrouped) {
    return canRightClickUnitGrouped_native(unitId, checkCommandibilityGrouped);
  }

  private native boolean canRightClickUnitGrouped_native(
      int unitId, boolean checkCommandibilityGrouped);

  public boolean canRightClickUnitGrouped(final int unitId) {
    return canRightClickUnitGrouped_native(unitId);
  }

  private native boolean canRightClickUnitGrouped_native(int unitId);

  public boolean canRightClickUnitGrouped(
      final int unitId,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canRightClickUnitGrouped_native(unitId, checkCommandibilityGrouped, checkCommandibility);
  }

  private native boolean canRightClickUnitGrouped_native(
      int unitId, boolean checkCommandibilityGrouped, boolean checkCommandibility);

  public boolean canRightClickUnitGrouped(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped) {
    return canRightClickUnitGrouped_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped);
  }

  private native boolean canRightClickUnitGrouped_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped);

  public boolean canRightClickUnitGrouped(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType) {
    return canRightClickUnitGrouped_native(
        unitId, targetUnit.getID(), checkCanTargetUnit, checkCanIssueCommandType);
  }

  private native boolean canRightClickUnitGrouped_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit, boolean checkCanIssueCommandType);

  public boolean canRightClickUnitGrouped(
      final int unitId, final Unit targetUnit, final boolean checkCanTargetUnit) {
    return canRightClickUnitGrouped_native(unitId, targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canRightClickUnitGrouped_native(
      int unitId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canRightClickUnitGrouped(final int unitId, final Unit targetUnit) {
    return canRightClickUnitGrouped_native(unitId, targetUnit.getID());
  }

  private native boolean canRightClickUnitGrouped_native(int unitId, int targetUnitId);

  public boolean canRightClickUnitGrouped(
      final int unitId,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibilityGrouped,
      final boolean checkCommandibility) {
    return canRightClickUnitGrouped_native(
        unitId,
        targetUnit.getID(),
        checkCanTargetUnit,
        checkCanIssueCommandType,
        checkCommandibilityGrouped,
        checkCommandibility);
  }

  private native boolean canRightClickUnitGrouped_native(
      int unitId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkCanIssueCommandType,
      boolean checkCommandibilityGrouped,
      boolean checkCommandibility);

  public boolean canHaltConstruction(final int unitId) {
    return canHaltConstruction_native(unitId);
  }

  private native boolean canHaltConstruction_native(int unitId);

  public boolean canHaltConstruction(final int unitId, final boolean checkCommandibility) {
    return canHaltConstruction_native(unitId, checkCommandibility);
  }

  private native boolean canHaltConstruction_native(int unitId, boolean checkCommandibility);

  public boolean canCancelConstruction(final int unitId) {
    return canCancelConstruction_native(unitId);
  }

  private native boolean canCancelConstruction_native(int unitId);

  public boolean canCancelConstruction(final int unitId, final boolean checkCommandibility) {
    return canCancelConstruction_native(unitId, checkCommandibility);
  }

  private native boolean canCancelConstruction_native(int unitId, boolean checkCommandibility);

  public boolean canCancelAddon(final int unitId) {
    return canCancelAddon_native(unitId);
  }

  private native boolean canCancelAddon_native(int unitId);

  public boolean canCancelAddon(final int unitId, final boolean checkCommandibility) {
    return canCancelAddon_native(unitId, checkCommandibility);
  }

  private native boolean canCancelAddon_native(int unitId, boolean checkCommandibility);

  public boolean canCancelTrain(final int unitId) {
    return canCancelTrain_native(unitId);
  }

  private native boolean canCancelTrain_native(int unitId);

  public boolean canCancelTrain(final int unitId, final boolean checkCommandibility) {
    return canCancelTrain_native(unitId, checkCommandibility);
  }

  private native boolean canCancelTrain_native(int unitId, boolean checkCommandibility);

  public boolean canCancelTrainSlot(final int unitId) {
    return canCancelTrainSlot_native(unitId);
  }

  private native boolean canCancelTrainSlot_native(int unitId);

  public boolean canCancelTrainSlot(final int unitId, final boolean checkCommandibility) {
    return canCancelTrainSlot_native(unitId, checkCommandibility);
  }

  private native boolean canCancelTrainSlot_native(int unitId, boolean checkCommandibility);

  public boolean canCancelTrainSlot(
      final int unitId, final int slot, final boolean checkCanIssueCommandType) {
    return canCancelTrainSlot_native(unitId, slot, checkCanIssueCommandType);
  }

  private native boolean canCancelTrainSlot_native(
      int unitId, int slot, boolean checkCanIssueCommandType);

  public boolean canCancelTrainSlot(final int unitId, final int slot) {
    return canCancelTrainSlot_native(unitId, slot);
  }

  private native boolean canCancelTrainSlot_native(int unitId, int slot);

  public boolean canCancelTrainSlot(
      final int unitId,
      final int slot,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canCancelTrainSlot_native(unitId, slot, checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canCancelTrainSlot_native(
      int unitId, int slot, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canCancelMorph(final int unitId) {
    return canCancelMorph_native(unitId);
  }

  private native boolean canCancelMorph_native(int unitId);

  public boolean canCancelMorph(final int unitId, final boolean checkCommandibility) {
    return canCancelMorph_native(unitId, checkCommandibility);
  }

  private native boolean canCancelMorph_native(int unitId, boolean checkCommandibility);

  public boolean canCancelResearch(final int unitId) {
    return canCancelResearch_native(unitId);
  }

  private native boolean canCancelResearch_native(int unitId);

  public boolean canCancelResearch(final int unitId, final boolean checkCommandibility) {
    return canCancelResearch_native(unitId, checkCommandibility);
  }

  private native boolean canCancelResearch_native(int unitId, boolean checkCommandibility);

  public boolean canCancelUpgrade(final int unitId) {
    return canCancelUpgrade_native(unitId);
  }

  private native boolean canCancelUpgrade_native(int unitId);

  public boolean canCancelUpgrade(final int unitId, final boolean checkCommandibility) {
    return canCancelUpgrade_native(unitId, checkCommandibility);
  }

  private native boolean canCancelUpgrade_native(int unitId, boolean checkCommandibility);

  public boolean canUseTechWithOrWithoutTarget(final int unitId) {
    return canUseTechWithOrWithoutTarget_native(unitId);
  }

  private native boolean canUseTechWithOrWithoutTarget_native(int unitId);

  public boolean canUseTechWithOrWithoutTarget(
      final int unitId, final boolean checkCommandibility) {
    return canUseTechWithOrWithoutTarget_native(unitId, checkCommandibility);
  }

  private native boolean canUseTechWithOrWithoutTarget_native(
      int unitId, boolean checkCommandibility);

  public boolean canUseTechWithOrWithoutTarget(
      final int unitId, final TechType tech, final boolean checkCanIssueCommandType) {
    return canUseTechWithOrWithoutTarget_native(unitId, tech.getID(), checkCanIssueCommandType);
  }

  private native boolean canUseTechWithOrWithoutTarget_native(
      int unitId, int techId, boolean checkCanIssueCommandType);

  public boolean canUseTechWithOrWithoutTarget(final int unitId, final TechType tech) {
    return canUseTechWithOrWithoutTarget_native(unitId, tech.getID());
  }

  private native boolean canUseTechWithOrWithoutTarget_native(int unitId, int techId);

  public boolean canUseTechWithOrWithoutTarget(
      final int unitId,
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTechWithOrWithoutTarget_native(
        unitId, tech.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canUseTechWithOrWithoutTarget_native(
      int unitId, int techId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType) {
    return canUseTech_native(
        unitId,
        tech.getID(),
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkTargetsType,
        checkCanIssueCommandType);
  }

  private native boolean canUseTech_native(
      int unitId,
      int techId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType) {
    return canUseTech_native(
        unitId,
        tech.getID(),
        target.getID(),
        checkCanTargetUnit,
        checkTargetsType,
        checkCanIssueCommandType);
  }

  private native boolean canUseTech_native(
      int unitId,
      int techId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType) {
    return canUseTech_native(
        unitId, tech.getID(), target.getX(), target.getY(), checkCanTargetUnit, checkTargetsType);
  }

  private native boolean canUseTech_native(
      int unitId, int techId, int x, int y, boolean checkCanTargetUnit, boolean checkTargetsType);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType) {
    return canUseTech_native(
        unitId, tech.getID(), target.getID(), checkCanTargetUnit, checkTargetsType);
  }

  private native boolean canUseTech_native(
      int unitId, int techId, int targetId, boolean checkCanTargetUnit, boolean checkTargetsType);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final Position target,
      final boolean checkCanTargetUnit) {
    return canUseTech_native(
        unitId, tech.getID(), target.getX(), target.getY(), checkCanTargetUnit);
  }

  private native boolean canUseTech_native(
      int unitId, int techId, int x, int y, boolean checkCanTargetUnit);

  public boolean canUseTech(
      final int unitId, final TechType tech, final Unit target, final boolean checkCanTargetUnit) {
    return canUseTech_native(unitId, tech.getID(), target.getID(), checkCanTargetUnit);
  }

  private native boolean canUseTech_native(
      int unitId, int techId, int targetId, boolean checkCanTargetUnit);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(final int unitId, final TechType tech, final Position target) {
    return canUseTech_native(unitId, tech.getID(), target.getX(), target.getY());
  }

  private native boolean canUseTech_native(int unitId, int techId, int x, int y);

  public boolean canUseTech(final int unitId, final TechType tech, final Unit target) {
    return canUseTech_native(unitId, tech.getID(), target.getID());
  }

  private native boolean canUseTech_native(int unitId, int techId, int targetId);

  public boolean canUseTech(final int unitId, final TechType tech, final PositionOrUnit target) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTech(final int unitId, final TechType tech) {
    return canUseTech_native(unitId, tech.getID());
  }

  private native boolean canUseTech_native(int unitId, int techId);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final Position target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTech_native(
        unitId,
        tech.getID(),
        target.getX(),
        target.getY(),
        checkCanTargetUnit,
        checkTargetsType,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canUseTech_native(
      int unitId,
      int techId,
      int x,
      int y,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final Unit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTech_native(
        unitId,
        tech.getID(),
        target.getID(),
        checkCanTargetUnit,
        checkTargetsType,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canUseTech_native(
      int unitId,
      int techId,
      int targetId,
      boolean checkCanTargetUnit,
      boolean checkTargetsType,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canUseTech(
      final int unitId,
      final TechType tech,
      final PositionOrUnit target,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsType,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public boolean canUseTechWithoutTarget(
      final int unitId, final TechType tech, final boolean checkCanIssueCommandType) {
    return canUseTechWithoutTarget_native(unitId, tech.getID(), checkCanIssueCommandType);
  }

  private native boolean canUseTechWithoutTarget_native(
      int unitId, int techId, boolean checkCanIssueCommandType);

  public boolean canUseTechWithoutTarget(final int unitId, final TechType tech) {
    return canUseTechWithoutTarget_native(unitId, tech.getID());
  }

  private native boolean canUseTechWithoutTarget_native(int unitId, int techId);

  public boolean canUseTechWithoutTarget(
      final int unitId,
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTechWithoutTarget_native(
        unitId, tech.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canUseTechWithoutTarget_native(
      int unitId, int techId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canUseTechUnit(
      final int unitId, final TechType tech, final boolean checkCanIssueCommandType) {
    return canUseTechUnit_native(unitId, tech.getID(), checkCanIssueCommandType);
  }

  private native boolean canUseTechUnit_native(
      int unitId, int techId, boolean checkCanIssueCommandType);

  public boolean canUseTechUnit(final int unitId, final TechType tech) {
    return canUseTechUnit_native(unitId, tech.getID());
  }

  private native boolean canUseTechUnit_native(int unitId, int techId);

  public boolean canUseTechUnit(
      final int unitId,
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTechUnit_native(
        unitId, tech.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canUseTechUnit_native(
      int unitId, int techId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canUseTechUnit(
      final int unitId,
      final TechType tech,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsUnits,
      final boolean checkCanIssueCommandType) {
    return canUseTechUnit_native(
        unitId,
        tech.getID(),
        targetUnit.getID(),
        checkCanTargetUnit,
        checkTargetsUnits,
        checkCanIssueCommandType);
  }

  private native boolean canUseTechUnit_native(
      int unitId,
      int techId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkTargetsUnits,
      boolean checkCanIssueCommandType);

  public boolean canUseTechUnit(
      final int unitId,
      final TechType tech,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsUnits) {
    return canUseTechUnit_native(
        unitId, tech.getID(), targetUnit.getID(), checkCanTargetUnit, checkTargetsUnits);
  }

  private native boolean canUseTechUnit_native(
      int unitId,
      int techId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkTargetsUnits);

  public boolean canUseTechUnit(
      final int unitId,
      final TechType tech,
      final Unit targetUnit,
      final boolean checkCanTargetUnit) {
    return canUseTechUnit_native(unitId, tech.getID(), targetUnit.getID(), checkCanTargetUnit);
  }

  private native boolean canUseTechUnit_native(
      int unitId, int techId, int targetUnitId, boolean checkCanTargetUnit);

  public boolean canUseTechUnit(final int unitId, final TechType tech, final Unit targetUnit) {
    return canUseTechUnit_native(unitId, tech.getID(), targetUnit.getID());
  }

  private native boolean canUseTechUnit_native(int unitId, int techId, int targetUnitId);

  public boolean canUseTechUnit(
      final int unitId,
      final TechType tech,
      final Unit targetUnit,
      final boolean checkCanTargetUnit,
      final boolean checkTargetsUnits,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTechUnit_native(
        unitId,
        tech.getID(),
        targetUnit.getID(),
        checkCanTargetUnit,
        checkTargetsUnits,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canUseTechUnit_native(
      int unitId,
      int techId,
      int targetUnitId,
      boolean checkCanTargetUnit,
      boolean checkTargetsUnits,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canUseTechPosition(
      final int unitId, final TechType tech, final boolean checkCanIssueCommandType) {
    return canUseTechPosition_native(unitId, tech.getID(), checkCanIssueCommandType);
  }

  private native boolean canUseTechPosition_native(
      int unitId, int techId, boolean checkCanIssueCommandType);

  public boolean canUseTechPosition(final int unitId, final TechType tech) {
    return canUseTechPosition_native(unitId, tech.getID());
  }

  private native boolean canUseTechPosition_native(int unitId, int techId);

  public boolean canUseTechPosition(
      final int unitId,
      final TechType tech,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTechPosition_native(
        unitId, tech.getID(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canUseTechPosition_native(
      int unitId, int techId, boolean checkCanIssueCommandType, boolean checkCommandibility);

  public boolean canUseTechPosition(
      final int unitId,
      final TechType tech,
      final Position target,
      final boolean checkTargetsPositions,
      final boolean checkCanIssueCommandType) {
    return canUseTechPosition_native(
        unitId,
        tech.getID(),
        target.getX(),
        target.getY(),
        checkTargetsPositions,
        checkCanIssueCommandType);
  }

  private native boolean canUseTechPosition_native(
      int unitId,
      int techId,
      int x,
      int y,
      boolean checkTargetsPositions,
      boolean checkCanIssueCommandType);

  public boolean canUseTechPosition(
      final int unitId,
      final TechType tech,
      final Position target,
      final boolean checkTargetsPositions) {
    return canUseTechPosition_native(
        unitId, tech.getID(), target.getX(), target.getY(), checkTargetsPositions);
  }

  private native boolean canUseTechPosition_native(
      int unitId, int techId, int x, int y, boolean checkTargetsPositions);

  public boolean canUseTechPosition(final int unitId, final TechType tech, final Position target) {
    return canUseTechPosition_native(unitId, tech.getID(), target.getX(), target.getY());
  }

  private native boolean canUseTechPosition_native(int unitId, int techId, int x, int y);

  public boolean canUseTechPosition(
      final int unitId,
      final TechType tech,
      final Position target,
      final boolean checkTargetsPositions,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canUseTechPosition_native(
        unitId,
        tech.getID(),
        target.getX(),
        target.getY(),
        checkTargetsPositions,
        checkCanIssueCommandType,
        checkCommandibility);
  }

  private native boolean canUseTechPosition_native(
      int unitId,
      int techId,
      int x,
      int y,
      boolean checkTargetsPositions,
      boolean checkCanIssueCommandType,
      boolean checkCommandibility);

  public boolean canPlaceCOP(final int unitId) {
    return canPlaceCOP_native(unitId);
  }

  private native boolean canPlaceCOP_native(int unitId);

  public boolean canPlaceCOP(final int unitId, final boolean checkCommandibility) {
    return canPlaceCOP_native(unitId, checkCommandibility);
  }

  private native boolean canPlaceCOP_native(int unitId, boolean checkCommandibility);

  public boolean canPlaceCOP(
      final int unitId, final TilePosition target, final boolean checkCanIssueCommandType) {
    return canPlaceCOP_native(unitId, target.getX(), target.getY(), checkCanIssueCommandType);
  }

  private native boolean canPlaceCOP_native(
      int unitId, int x, int y, boolean checkCanIssueCommandType);

  public boolean canPlaceCOP(final int unitId, final TilePosition target) {
    return canPlaceCOP_native(unitId, target.getX(), target.getY());
  }

  private native boolean canPlaceCOP_native(int unitId, int x, int y);

  public boolean canPlaceCOP(
      final int unitId,
      final TilePosition target,
      final boolean checkCanIssueCommandType,
      final boolean checkCommandibility) {
    return canPlaceCOP_native(
        unitId, target.getX(), target.getY(), checkCanIssueCommandType, checkCommandibility);
  }

  private native boolean canPlaceCOP_native(
      int unitId, int x, int y, boolean checkCanIssueCommandType, boolean checkCommandibility);
}
