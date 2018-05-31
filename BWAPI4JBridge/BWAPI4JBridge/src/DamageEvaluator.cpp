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

#include <BWAPI/Client.h>
#include "org_openbw_bwapi4j_DamageEvaluator.h"

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageFrom_1native__III(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer) {
  return BWAPI::Broodwar->getDamageFrom((BWAPI::UnitType)fromType, (BWAPI::UnitType)toType, BWAPI::Broodwar->getPlayer(fromPlayer));
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageFrom_1native__II(JNIEnv *, jobject, jint fromType, jint toType) {
  return BWAPI::Broodwar->getDamageFrom((BWAPI::UnitType)fromType, (BWAPI::UnitType)toType);
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageFrom_1native__IIII(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer,
                                                                                           jint toPlayer) {
  return BWAPI::Broodwar->getDamageFrom((BWAPI::UnitType)fromType, (BWAPI::UnitType)toType, BWAPI::Broodwar->getPlayer(fromPlayer),
                                        BWAPI::Broodwar->getPlayer(toPlayer));
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageTo_1native__III(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer) {
  return BWAPI::Broodwar->getDamageTo((BWAPI::UnitType)fromType, (BWAPI::UnitType)toType, BWAPI::Broodwar->getPlayer(fromPlayer));
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageTo_1native__II(JNIEnv *, jobject, jint fromType, jint toType) {
  return BWAPI::Broodwar->getDamageTo((BWAPI::UnitType)fromType, (BWAPI::UnitType)toType);
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageTo_1native__IIII(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer,
                                                                                         jint toPlayer) {
  return BWAPI::Broodwar->getDamageTo((BWAPI::UnitType)fromType, (BWAPI::UnitType)toType, BWAPI::Broodwar->getPlayer(fromPlayer),
                                      BWAPI::Broodwar->getPlayer(toPlayer));
}
