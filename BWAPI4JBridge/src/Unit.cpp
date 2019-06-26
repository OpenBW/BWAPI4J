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

#include "bwapi_Unit.h"

#include <BWAPI.h>

#include "Globals.h"

JNIEXPORT jboolean JNICALL Java_bwapi_Unit_issueCommand_1native(JNIEnv *, jobject, jint unitID, jint unitCommandTypeID, jint targetUnitID, jint x, jint y,
                                                                jint extra) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitID);
  if (unit) {
    const BWAPI::UnitCommandType unitCommandType(unitCommandTypeID);
    const auto &targetUnit = BWAPI::Broodwar->getUnit(targetUnitID);

    const BWAPI::UnitCommand command(unit, unitCommandType, targetUnit, x, y, extra);

    return command.unit->issueCommand(command);
  }
  return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_bwapi_Unit_hasPath_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  return unit && unit->hasPath(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_bwapi_Unit_hasPath_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  const auto &targetUnit = BWAPI::Broodwar->getUnit((int)targetUnitId);
  return unit && targetUnit && unit->hasPath(targetUnit);
}

JNIEXPORT jintArray JNICALL Java_bwapi_Unit_getLastCommand_1native(JNIEnv *env, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  const auto &unitCommand = unit->getLastCommand();

  BUFFER_SETUP;

  Bridge::Globals::dataBuffer.addId(unitCommand.unit);

  Bridge::Globals::dataBuffer.add(unitCommand.type.getID());

  const auto &targetUnit = unitCommand.target;
  Bridge::Globals::dataBuffer.add(targetUnit ? targetUnit->getID() : -1);

  Bridge::Globals::dataBuffer.add(unitCommand.x);
  Bridge::Globals::dataBuffer.add(unitCommand.y);

  Bridge::Globals::dataBuffer.add(unitCommand.extra);

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_bwapi_Unit_getUnitsInRadius_1native(JNIEnv *env, jobject, jint unitId, jint radius) {
  BUFFER_SETUP;

  const auto &unit = BWAPI::Broodwar->getUnit(unitId);

  if (unit) {
    for (const auto &unitInRadius : unit->getUnitsInRadius(radius)) {
      Bridge::Globals::dataBuffer.add(unitInRadius->getID());
    }
  }

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_bwapi_Unit_getUnitsInWeaponRange_1native(JNIEnv *env, jobject, jint unitId, jint weaponTypeId) {
  BUFFER_SETUP;

  const auto &unit = BWAPI::Broodwar->getUnit(unitId);

  if (unit) {
    const auto weaponType = BWAPI::WeaponType(weaponTypeId);
    for (const auto &unitInWeaponRange : unit->getUnitsInWeaponRange(weaponType)) {
      Bridge::Globals::dataBuffer.add(unitInWeaponRange->getID());
    }
  }

  BUFFER_RETURN;
}

JNIEXPORT jboolean JNICALL Java_bwapi_Unit_isInWeaponRange_1native(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitId);
  const auto &targetUnit = BWAPI::Broodwar->getUnit(targetUnitId);

  return unit && targetUnit && unit->isInWeaponRange(targetUnit);
}

JNIEXPORT jboolean JNICALL Java_bwapi_Unit_isVisible_1native(JNIEnv *, jobject, jint unitId, jint playerId) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  const auto &player = BWAPI::Broodwar->getPlayer((int)playerId);

  return unit && player && unit->isVisible(player);
}

JNIEXPORT jboolean JNICALL Java_bwapi_Unit_train_1native(JNIEnv *, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  return unit && unit->train();
}

JNIEXPORT jboolean JNICALL Java_bwapi_Unit_placeCOP_1native(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  return unit && unit->placeCOP(BWAPI::TilePosition((int)x, (int)y));
}
