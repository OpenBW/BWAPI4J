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

#include "org_openbw_bwapi4j_unit_Unit.h"

#include <BWAPI.h>

#include "Globals.h"

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_Unit_issueCommand_1native(JNIEnv *, jobject, jint unitID, jint unitCommandTypeID, jint targetUnitID,
                                                                                  jint x, jint y, jint extra) {
  const auto &unit = BWAPI::Broodwar->getUnit(unitID);
  if (unit) {
    const BWAPI::UnitCommandType unitCommandType(unitCommandTypeID);
    const auto &targetUnit = BWAPI::Broodwar->getUnit(targetUnitID);

    const BWAPI::UnitCommand command(unit, unitCommandType, targetUnit, x, y, extra);

    return command.unit->issueCommand(command);
  }
  return JNI_FALSE;
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_Unit_hasPath_1native__III(JNIEnv *, jobject, jint unitId, jint x, jint y) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  return unit && unit->hasPath(BWAPI::Position((int)x, (int)y));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_Unit_hasPath_1native__II(JNIEnv *, jobject, jint unitId, jint targetUnitId) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  const auto &targetUnit = BWAPI::Broodwar->getUnit((int)targetUnitId);
  return unit && targetUnit && unit->hasPath(targetUnit);
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_unit_Unit_getLastCommand_1native(JNIEnv *env, jobject, jint unitId) {
  const auto &unit = BWAPI::Broodwar->getUnit((int)unitId);
  const auto &unitCommand = unit->getLastCommand();

  Bridge::Globals::dataBuffer.reset();

  Bridge::Globals::dataBuffer.addId(unitCommand.unit);

  Bridge::Globals::dataBuffer.add(unitCommand.type.getID());

  const auto &targetUnit = unitCommand.target;
  Bridge::Globals::dataBuffer.add(targetUnit ? targetUnit->getID() : -1);

  Bridge::Globals::dataBuffer.add(unitCommand.x);
  Bridge::Globals::dataBuffer.add(unitCommand.y);

  Bridge::Globals::dataBuffer.add(unitCommand.extra);

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}
