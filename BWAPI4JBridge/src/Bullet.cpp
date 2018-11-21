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

#include "org_openbw_bwapi4j_Bullet.h"

#include <BWAPI.h>

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_Bullet_isVisible(JNIEnv *, jobject, jint bulletId, jint playerId) {
  const auto &player = BWAPI::Broodwar->getPlayer((int)playerId);

  for (const auto &bullet : BWAPI::Broodwar->getBullets()) {
    if (bullet->getID() == bulletId) {
      return bullet->isVisible(player);
    }
  }

  return false;
}
