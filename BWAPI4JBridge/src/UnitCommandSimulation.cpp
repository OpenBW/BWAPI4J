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

#include <BWAPI.h>

#include "org_openbw_bwapi4j_unit_UnitCommandSimulation.h"

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCommand_1native(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCommand();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCommandGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCommandGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCommandGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                            jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCommandGrouped((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canIssueCommandType_1native__II(JNIEnv *, jobject, jint unitId, jint ctId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canIssueCommandType(BWAPI::UnitCommandType(ctId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canIssueCommandType_1native__IIZ(JNIEnv *, jobject, jint unitId, jint ctId,
                                                                                                               jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canIssueCommandType(BWAPI::UnitCommandType(ctId), (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canIssueCommandTypeGrouped_1native__IIZ(JNIEnv *, jobject, jint unitId, jint ctId,
                                                                                                                      jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canIssueCommandTypeGrouped(BWAPI::UnitCommandType(ctId), (bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canIssueCommandTypeGrouped_1native__II(JNIEnv *, jobject, jint unitId,
                                                                                                                     jint ctId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canIssueCommandTypeGrouped(BWAPI::UnitCommandType(ctId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canIssueCommandTypeGrouped_1native__IIZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                       jint ctId,
                                                                                                                       jboolean checkCommandibilityGrouped,
                                                                                                                       jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canIssueCommandTypeGrouped(BWAPI::UnitCommandType(ctId), (bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canTargetUnit_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canTargetUnit(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canTargetUnit_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                         jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canTargetUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                       jboolean checkCanTargetUnit,
                                                                                                       jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                      jboolean checkCanTargetUnit,
                                                                                                      jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                      jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                     jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__II(JNIEnv *, jobject, jint unitId, jint targetId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Broodwar->getUnit(targetId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__IIIZZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                        jboolean checkCanTargetUnit,
                                                                                                        jboolean checkCanIssueCommandType,
                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttack_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                       jboolean checkCanTargetUnit,
                                                                                                       jboolean checkCanIssueCommandType,
                                                                                                       jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttack(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                           jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                            jboolean checkCommandibilityGrouped,
                                                                                                            jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIIZZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                               jboolean checkCanTargetUnit,
                                                                                                               jboolean checkCanIssueCommandType,
                                                                                                               jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit &&
         unit->canAttackGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                              jboolean checkCanTargetUnit,
                                                                                                              jboolean checkCanIssueCommandType,
                                                                                                              jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit &&
         unit->canAttackGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                              jboolean checkCanTargetUnit,
                                                                                                              jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                             jboolean checkCanTargetUnit,
                                                                                                             jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                             jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                            jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__II(JNIEnv *, jobject, jint unitId, jint targetId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Broodwar->getUnit(targetId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIIZZZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                                jboolean checkCanTargetUnit,
                                                                                                                jboolean checkCanIssueCommandType,
                                                                                                                jboolean checkCommandibilityGrouped,
                                                                                                                jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                        (bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackGrouped_1native__IIZZZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                               jboolean checkCanTargetUnit,
                                                                                                               jboolean checkCanIssueCommandType,
                                                                                                               jboolean checkCommandibilityGrouped,
                                                                                                               jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                        (bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackMove_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackMove();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackMove_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackMove((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackMoveGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                               jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackMoveGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackMoveGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackMoveGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackMoveGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                jboolean checkCommandibilityGrouped,
                                                                                                                jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackMoveGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnit_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnit();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnit_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnit((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnit_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                          jboolean checkCanTargetUnit,
                                                                                                          jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnit_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                         jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnit_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnit(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnit_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                           jboolean checkCanTargetUnit,
                                                                                                           jboolean checkCanIssueCommandType,
                                                                                                           jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit &&
         unit->canAttackUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                               jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                jboolean checkCommandibilityGrouped,
                                                                                                                jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__IIZZZ(
    JNIEnv *, jobject, jint unitId, jint targetUnitId, jboolean checkCanTargetUnit, jboolean checkCanIssueCommandType, jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                            (bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__IIZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                 jint targetUnitId, jboolean checkCanTargetUnit,
                                                                                                                 jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__IIZ(JNIEnv *, jobject, jint unitId,
                                                                                                                jint targetUnitId,
                                                                                                                jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__II(JNIEnv *, jobject, jint unitId,
                                                                                                               jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canAttackUnitGrouped_1native__IIZZZZ(
    JNIEnv *, jobject, jint unitId, jint targetUnitId, jboolean checkCanTargetUnit, jboolean checkCanIssueCommandType, jboolean checkCommandibilityGrouped,
    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canAttackUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                            (bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                   jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__IIZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                    jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__II(JNIEnv *, jobject, jint unitId, jint uTypeId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild(BWAPI::UnitType(uTypeId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                     jboolean checkCanIssueCommandType,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__IIIIZZ(JNIEnv *, jobject, jint unitId, jint uTypeId, jint x,
                                                                                                       jint y, jboolean checkTargetUnitType,
                                                                                                       jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild(BWAPI::UnitType(uTypeId), BWAPI::TilePosition((int)x, (int)y), (bool)checkTargetUnitType, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__IIIIZ(JNIEnv *, jobject, jint unitId, jint uTypeId, jint x,
                                                                                                      jint y, jboolean checkTargetUnitType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild(BWAPI::UnitType(uTypeId), BWAPI::TilePosition((int)x, (int)y), (bool)checkTargetUnitType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__IIII(JNIEnv *, jobject, jint unitId, jint uTypeId, jint x,
                                                                                                     jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild(BWAPI::UnitType(uTypeId), BWAPI::TilePosition((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuild_1native__IIIIZZZ(JNIEnv *, jobject, jint unitId, jint uTypeId, jint x,
                                                                                                        jint y, jboolean checkTargetUnitType,
                                                                                                        jboolean checkCanIssueCommandType,
                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuild(BWAPI::UnitType(uTypeId), BWAPI::TilePosition((int)x, (int)y), (bool)checkTargetUnitType, (bool)checkCanIssueCommandType,
                                (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuildAddon_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuildAddon();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuildAddon_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuildAddon((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuildAddon_1native__IIZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                         jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuildAddon(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuildAddon_1native__II(JNIEnv *, jobject, jint unitId, jint uTypeId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuildAddon(BWAPI::UnitType(uTypeId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBuildAddon_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                          jboolean checkCanIssueCommandType,
                                                                                                          jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBuildAddon(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canTrain_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canTrain();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canTrain_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                   jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canTrain((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canTrain_1native__IIZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                    jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canTrain(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canTrain_1native__II(JNIEnv *, jobject, jint unitId, jint uTypeId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canTrain(BWAPI::UnitType(uTypeId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canTrain_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                     jboolean checkCanIssueCommandType,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canTrain(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMorph_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMorph();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMorph_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                   jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMorph((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMorph_1native__IIZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                    jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMorph(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMorph_1native__II(JNIEnv *, jobject, jint unitId, jint uTypeId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMorph(BWAPI::UnitType(uTypeId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMorph_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint uTypeId,
                                                                                                     jboolean checkCanIssueCommandType,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMorph(BWAPI::UnitType(uTypeId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canResearch_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canResearch();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canResearch_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                      jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canResearch((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canResearch_1native__II(JNIEnv *, jobject, jint unitId, jint typeId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canResearch(BWAPI::TechType(typeId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canResearch_1native__IIZ(JNIEnv *, jobject, jint unitId, jint typeId,
                                                                                                       jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canResearch(BWAPI::TechType(typeId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUpgrade_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUpgrade();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUpgrade_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUpgrade((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUpgrade_1native__II(JNIEnv *, jobject, jint unitId, jint typeId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUpgrade(BWAPI::UpgradeType(typeId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUpgrade_1native__IIZ(JNIEnv *, jobject, jint unitId, jint typeId,
                                                                                                      jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUpgrade(BWAPI::UpgradeType(typeId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                           jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                              jboolean checkCanTargetUnit,
                                                                                                              jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                             jboolean checkCanTargetUnit,
                                                                                                             jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                             jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                            jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__II(JNIEnv *, jobject, jint unitId, jint targetId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint(BWAPI::Broodwar->getUnit(targetId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__IIIZZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                               jboolean checkCanTargetUnit,
                                                                                                               jboolean checkCanIssueCommandType,
                                                                                                               jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPoint(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPoint_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                              jboolean checkCanTargetUnit,
                                                                                                              jboolean checkCanIssueCommandType,
                                                                                                              jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit &&
         unit->canSetRallyPoint(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPosition_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPosition();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyPosition_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                              jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyPosition((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyUnit_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyUnit();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyUnit_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                          jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyUnit((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyUnit_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                            jboolean checkCanTargetUnit,
                                                                                                            jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyUnit_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                           jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyUnit_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSetRallyUnit(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSetRallyUnit_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                             jboolean checkCanTargetUnit,
                                                                                                             jboolean checkCanIssueCommandType,
                                                                                                             jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit &&
         unit->canSetRallyUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMove_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMove();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMove_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                  jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMove((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMoveGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                         jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMoveGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMoveGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMoveGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canMoveGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                          jboolean checkCommandibilityGrouped,
                                                                                                          jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canMoveGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPatrol_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPatrol();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPatrol_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPatrol((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPatrolGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                           jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPatrolGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPatrolGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPatrolGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPatrolGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                            jboolean checkCommandibilityGrouped,
                                                                                                            jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPatrolGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canFollow_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canFollow();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canFollow_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canFollow((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canFollow_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                      jboolean checkCanTargetUnit,
                                                                                                      jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canFollow(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canFollow_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                     jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canFollow(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canFollow_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canFollow(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canFollow_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                       jboolean checkCanTargetUnit,
                                                                                                       jboolean checkCanIssueCommandType,
                                                                                                       jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canFollow(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canGather_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canGather();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canGather_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canGather((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canGather_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                      jboolean checkCanTargetUnit,
                                                                                                      jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canGather(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canGather_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                     jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canGather(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canGather_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canGather(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canGather_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                       jboolean checkCanTargetUnit,
                                                                                                       jboolean checkCanIssueCommandType,
                                                                                                       jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canGather(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canReturnCargo_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canReturnCargo();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canReturnCargo_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                         jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canReturnCargo((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canHoldPosition_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canHoldPosition();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canHoldPosition_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                          jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canHoldPosition((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canStop_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canStop();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canStop_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                  jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canStop((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRepair_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRepair();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRepair_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRepair((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRepair_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                      jboolean checkCanTargetUnit,
                                                                                                      jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRepair(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRepair_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                     jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRepair(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRepair_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRepair(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRepair_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                       jboolean checkCanTargetUnit,
                                                                                                       jboolean checkCanIssueCommandType,
                                                                                                       jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRepair(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBurrow_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBurrow();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canBurrow_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canBurrow((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnburrow_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnburrow();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnburrow_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                      jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnburrow((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCloak_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCloak();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCloak_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                   jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCloak((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canDecloak_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canDecloak();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canDecloak_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canDecloak((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSiege_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSiege();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canSiege_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                   jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canSiege((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnsiege_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnsiege();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnsiege_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnsiege((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLift_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLift();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLift_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                  jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLift((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLand_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLand();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLand_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                  jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLand((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLand_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                    jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLand(BWAPI::TilePosition((int)x, (int)y), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLand_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLand(BWAPI::TilePosition((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLand_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                     jboolean checkCanIssueCommandType,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLand(BWAPI::TilePosition((int)x, (int)y), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLoad_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLoad();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLoad_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                  jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLoad((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLoad_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                    jboolean checkCanTargetUnit,
                                                                                                    jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLoad(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLoad_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                   jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLoad(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLoad_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLoad(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canLoad_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                     jboolean checkCanTargetUnit,
                                                                                                     jboolean checkCanIssueCommandType,
                                                                                                     jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canLoad(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadWithOrWithoutTarget_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadWithOrWithoutTarget();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadWithOrWithoutTarget_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                                       jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadWithOrWithoutTarget((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAtPosition_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                                jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAtPosition(BWAPI::Position((int)x, (int)y), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAtPosition_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAtPosition(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAtPosition_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                                 jboolean checkCanIssueCommandType,
                                                                                                                 jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAtPosition(BWAPI::Position((int)x, (int)y), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnload_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnload();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnload_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnload((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnload_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                       jboolean checkCanTargetUnit, jboolean checkPosition,
                                                                                                       jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnload(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkPosition, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnload_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                      jboolean checkCanTargetUnit, jboolean checkPosition) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnload(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkPosition);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnload_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                     jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnload(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnload_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnload(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnload_1native__IIZZZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                        jboolean checkCanTargetUnit, jboolean checkPosition,
                                                                                                        jboolean checkCanIssueCommandType,
                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnload(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkPosition, (bool)checkCanIssueCommandType,
                                 (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAll_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAll();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAll_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                       jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAll((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAllPosition_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAllPosition();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAllPosition_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                               jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAllPosition((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAllPosition_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                                 jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAllPosition(BWAPI::Position((int)x, (int)y), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAllPosition_1native__III(JNIEnv *, jobject, jint unitId, jint x,
                                                                                                                jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAllPosition(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUnloadAllPosition_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x,
                                                                                                                  jint y, jboolean checkCanIssueCommandType,
                                                                                                                  jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUnloadAllPosition(BWAPI::Position((int)x, (int)y), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                           jboolean checkCanTargetUnit,
                                                                                                           jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                          jboolean checkCanTargetUnit,
                                                                                                          jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                          jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                         jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__II(JNIEnv *, jobject, jint unitId, jint targetId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Broodwar->getUnit(targetId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__IIIZZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                            jboolean checkCanTargetUnit,
                                                                                                            jboolean checkCanIssueCommandType,
                                                                                                            jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClick_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                           jboolean checkCanTargetUnit,
                                                                                                           jboolean checkCanIssueCommandType,
                                                                                                           jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClick(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                               jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                jboolean checkCommandibilityGrouped,
                                                                                                                jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIIZZZ(JNIEnv *, jobject, jint unitId, jint x,
                                                                                                                   jint y, jboolean checkCanTargetUnit,
                                                                                                                   jboolean checkCanIssueCommandType,
                                                                                                                   jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                            (bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIZZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                                  jboolean checkCanTargetUnit,
                                                                                                                  jboolean checkCanIssueCommandType,
                                                                                                                  jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                            (bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x,
                                                                                                                  jint y, jboolean checkCanTargetUnit,
                                                                                                                  jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                                 jboolean checkCanTargetUnit,
                                                                                                                 jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                                 jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetId,
                                                                                                                jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__III(JNIEnv *, jobject, jint unitId, jint x,
                                                                                                                jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__II(JNIEnv *, jobject, jint unitId, jint targetId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Broodwar->getUnit(targetId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIIZZZZ(JNIEnv *, jobject, jint unitId, jint x,
                                                                                                                    jint y, jboolean checkCanTargetUnit,
                                                                                                                    jboolean checkCanIssueCommandType,
                                                                                                                    jboolean checkCommandibilityGrouped,
                                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                            (bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickGrouped_1native__IIZZZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                   jint targetId, jboolean checkCanTargetUnit,
                                                                                                                   jboolean checkCanIssueCommandType,
                                                                                                                   jboolean checkCommandibilityGrouped,
                                                                                                                   jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickGrouped(BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                            (bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickPosition_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickPosition();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickPosition_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                                jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickPosition((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickPositionGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                                       jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickPositionGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickPositionGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickPositionGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickPositionGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                        jboolean checkCommandibilityGrouped,
                                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickPositionGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnit_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnit();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnit_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                            jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnit((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnit_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                              jboolean checkCanTargetUnit,
                                                                                                              jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnit_1native__IIZ(JNIEnv *, jobject, jint unitId, jint targetUnitId,
                                                                                                             jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnit_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnit(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnit_1native__IIZZZ(JNIEnv *, jobject, jint unitId,
                                                                                                               jint targetUnitId, jboolean checkCanTargetUnit,
                                                                                                               jboolean checkCanIssueCommandType,
                                                                                                               jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit &&
         unit->canRightClickUnit(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                                   jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped((bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__IZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                    jboolean checkCommandibilityGrouped,
                                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped((bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__IIZZZ(
    JNIEnv *, jobject, jint unitId, jint targetUnitId, jboolean checkCanTargetUnit, jboolean checkCanIssueCommandType, jboolean checkCommandibilityGrouped) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                                (bool)checkCommandibilityGrouped);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__IIZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                     jint targetUnitId,
                                                                                                                     jboolean checkCanTargetUnit,
                                                                                                                     jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__IIZ(JNIEnv *, jobject, jint unitId,
                                                                                                                    jint targetUnitId,
                                                                                                                    jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__II(JNIEnv *, jobject, jint unitId,
                                                                                                                   jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canRightClickUnitGrouped_1native__IIZZZZ(
    JNIEnv *, jobject, jint unitId, jint targetUnitId, jboolean checkCanTargetUnit, jboolean checkCanIssueCommandType, jboolean checkCommandibilityGrouped,
    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canRightClickUnitGrouped(BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkCanIssueCommandType,
                                                (bool)checkCommandibilityGrouped, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canHaltConstruction_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canHaltConstruction();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canHaltConstruction_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                              jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canHaltConstruction((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelConstruction_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelConstruction();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelConstruction_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                                jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelConstruction((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelAddon_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelAddon();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelAddon_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                         jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelAddon((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelTrain_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelTrain();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelTrain_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                         jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelTrain((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelTrainSlot_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelTrainSlot();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelTrainSlot_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                             jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelTrainSlot((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelTrainSlot_1native__IIZ(JNIEnv *, jobject, jint unitId, jint slot,
                                                                                                              jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelTrainSlot((int)slot, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelTrainSlot_1native__II(JNIEnv *, jobject, jint unitId, jint slot) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelTrainSlot((int)slot);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelTrainSlot_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint slot,
                                                                                                               jboolean checkCanIssueCommandType,
                                                                                                               jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelTrainSlot((int)slot, (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelMorph_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelMorph();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelMorph_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                         jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelMorph((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelResearch_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelResearch();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelResearch_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                            jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelResearch((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelUpgrade_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelUpgrade();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canCancelUpgrade_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                           jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canCancelUpgrade((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithOrWithoutTarget_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithOrWithoutTarget();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithOrWithoutTarget_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                                        jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithOrWithoutTarget((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithOrWithoutTarget_1native__IIZ(JNIEnv *, jobject, jint unitId,
                                                                                                                         jint techId,
                                                                                                                         jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithOrWithoutTarget(BWAPI::TechType(techId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithOrWithoutTarget_1native__II(JNIEnv *, jobject, jint unitId,
                                                                                                                        jint techId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithOrWithoutTarget(BWAPI::TechType(techId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithOrWithoutTarget_1native__IIZZ(JNIEnv *, jobject, jint unitId,
                                                                                                                          jint techId,
                                                                                                                          jboolean checkCanIssueCommandType,
                                                                                                                          jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithOrWithoutTarget(BWAPI::TechType(techId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIIZZZ(JNIEnv *, jobject, jint unitId, jint techId, jint x,
                                                                                                          jint y, jboolean checkCanTargetUnit,
                                                                                                          jboolean checkTargetsType,
                                                                                                          jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkTargetsType,
                                  (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIZZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                         jint targetId, jboolean checkCanTargetUnit,
                                                                                                         jboolean checkTargetsType,
                                                                                                         jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkTargetsType,
                                  (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIIZZ(JNIEnv *, jobject, jint unitId, jint techId, jint x,
                                                                                                         jint y, jboolean checkCanTargetUnit,
                                                                                                         jboolean checkTargetsType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkTargetsType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                        jint targetId, jboolean checkCanTargetUnit,
                                                                                                        jboolean checkTargetsType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkTargetsType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIIZ(JNIEnv *, jobject, jint unitId, jint techId, jint x,
                                                                                                        jint y, jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                       jint targetId, jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIII(JNIEnv *, jobject, jint unitId, jint techId, jint x,
                                                                                                       jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__III(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                      jint targetId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__II(JNIEnv *, jobject, jint unitId, jint techId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIIZZZZ(JNIEnv *, jobject, jint unitId, jint techId, jint x,
                                                                                                           jint y, jboolean checkCanTargetUnit,
                                                                                                           jboolean checkTargetsType,
                                                                                                           jboolean checkCanIssueCommandType,
                                                                                                           jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y), (bool)checkCanTargetUnit, (bool)checkTargetsType,
                                  (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTech_1native__IIIZZZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                          jint targetId, jboolean checkCanTargetUnit,
                                                                                                          jboolean checkTargetsType,
                                                                                                          jboolean checkCanIssueCommandType,
                                                                                                          jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTech(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetId), (bool)checkCanTargetUnit, (bool)checkTargetsType,
                                  (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithoutTarget_1native__IIZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                                   jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithoutTarget(BWAPI::TechType(techId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithoutTarget_1native__II(JNIEnv *, jobject, jint unitId, jint techId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithoutTarget(BWAPI::TechType(techId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechWithoutTarget_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                                    jboolean checkCanIssueCommandType,
                                                                                                                    jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechWithoutTarget(BWAPI::TechType(techId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__IIZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                          jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__II(JNIEnv *, jobject, jint unitId, jint techId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                           jboolean checkCanIssueCommandType,
                                                                                                           jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__IIIZZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                             jint targetUnitId, jboolean checkCanTargetUnit,
                                                                                                             jboolean checkTargetsUnits,
                                                                                                             jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkTargetsUnits,
                                      (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                            jint targetUnitId, jboolean checkCanTargetUnit,
                                                                                                            jboolean checkTargetsUnits) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkTargetsUnits);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                           jint targetUnitId, jboolean checkCanTargetUnit) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__III(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                          jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetUnitId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechUnit_1native__IIIZZZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                              jint targetUnitId, jboolean checkCanTargetUnit,
                                                                                                              jboolean checkTargetsUnits,
                                                                                                              jboolean checkCanIssueCommandType,
                                                                                                              jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechUnit(BWAPI::TechType(techId), BWAPI::Broodwar->getUnit(targetUnitId), (bool)checkCanTargetUnit, (bool)checkTargetsUnits,
                                      (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechPosition_1native__IIZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                              jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechPosition(BWAPI::TechType(techId), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechPosition_1native__II(JNIEnv *, jobject, jint unitId, jint techId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechPosition(BWAPI::TechType(techId));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechPosition_1native__IIZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                               jboolean checkCanIssueCommandType,
                                                                                                               jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechPosition(BWAPI::TechType(techId), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechPosition_1native__IIIIZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                                 jint x, jint y, jboolean checkTargetsPositions,
                                                                                                                 jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit &&
         unit->canUseTechPosition(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y), (bool)checkTargetsPositions, (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechPosition_1native__IIIIZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                                jint x, jint y,
                                                                                                                jboolean checkTargetsPositions) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechPosition(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y), (bool)checkTargetsPositions);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechPosition_1native__IIII(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                               jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechPosition(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canUseTechPosition_1native__IIIIZZZ(JNIEnv *, jobject, jint unitId, jint techId,
                                                                                                                  jint x, jint y,
                                                                                                                  jboolean checkTargetsPositions,
                                                                                                                  jboolean checkCanIssueCommandType,
                                                                                                                  jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canUseTechPosition(BWAPI::TechType(techId), BWAPI::Position((int)x, (int)y), (bool)checkTargetsPositions, (bool)checkCanIssueCommandType,
                                          (bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPlaceCOP_1native__I(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPlaceCOP();
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPlaceCOP_1native__IZ(JNIEnv *, jobject, jint unitId,
                                                                                                      jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPlaceCOP((bool)checkCommandibility);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPlaceCOP_1native__IIIZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                        jboolean checkCanIssueCommandType) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPlaceCOP(BWAPI::TilePosition((int)x, (int)y), (bool)checkCanIssueCommandType);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPlaceCOP_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPlaceCOP(BWAPI::TilePosition((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_UnitCommandSimulation_canPlaceCOP_1native__IIIZZ(JNIEnv *, jobject, jint unitId, jint x, jint y,
                                                                                                         jboolean checkCanIssueCommandType,
                                                                                                         jboolean checkCommandibility) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  return unit && unit->canPlaceCOP(BWAPI::TilePosition((int)x, (int)y), (bool)checkCanIssueCommandType, (bool)checkCommandibility);
}
