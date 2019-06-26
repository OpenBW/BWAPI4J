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

#include "org_openbw_bwapi4j_Player.h"

#include <BWAPI.h>

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_Player_hasUnitTypeRequirement_1native(JNIEnv *, jobject, jint playerId, jint unitTypeId, jint amount) {
  const auto &player = BWAPI::Broodwar->getPlayer((int)playerId);
  return player && player->hasUnitTypeRequirement(BWAPI::UnitType((int)unitTypeId), (int)amount);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_Player_isAlly_1native(JNIEnv *, jobject, jint playerId, jint targetPlayerId) {
  const auto &player = BWAPI::Broodwar->getPlayer(playerId);
  const auto &targetPlayer = BWAPI::Broodwar->getPlayer(targetPlayerId);

  return player && targetPlayer && player->isAlly(targetPlayer);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_Player_isEnemy_1native(JNIEnv *, jobject, jint playerId, jint targetPlayerId) {
  const auto &player = BWAPI::Broodwar->getPlayer(playerId);
  const auto &targetPlayer = BWAPI::Broodwar->getPlayer(targetPlayerId);

  return player && targetPlayer && player->isEnemy(targetPlayer);
}
