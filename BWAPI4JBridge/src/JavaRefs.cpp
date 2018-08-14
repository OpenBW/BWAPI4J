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

#include "JavaRefs.h"
#include "Logger.h"

void JavaRefs::initialize(JNIEnv *env) {
  LOGGER("Initializing Java references...");

  arrayListClass = env->FindClass("java/util/ArrayList");
  arrayListClass_add = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

  integerClass = env->FindClass("java/lang/Integer");
  integerClassConstructor = env->GetMethodID(integerClass, "<init>", "(I)V");

  tilePositionClass = env->FindClass("org/openbw/bwapi4j/TilePosition");
  tilePositionConstructor = env->GetMethodID(tilePositionClass, "<init>", "(II)V");

  weaponTypeClass = env->FindClass("org/openbw/bwapi4j/type/WeaponType");
  techTypeClass = env->FindClass("org/openbw/bwapi4j/type/TechType");

  unitTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitType");
  unitTypeClass_addRequiredUnit = env->GetMethodID(unitTypeClass, "addRequiredUnit", "(II)V");

  upgradeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UpgradeType");
  upgradeTypeClass_addUsingUnit = env->GetMethodID(upgradeTypeClass, "addUsingUnit", "(I)V");

  damageTypeClass = env->FindClass("org/openbw/bwapi4j/type/DamageType");
  explosionTypeClass = env->FindClass("org/openbw/bwapi4j/type/ExplosionType");
  raceClass = env->FindClass("org/openbw/bwapi4j/type/Race");
  unitSizeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitSizeType");
  orderClass = env->FindClass("org/openbw/bwapi4j/type/Order");

  pairClass = env->FindClass("org/openbw/bwapi4j/util/Pair");
  pairClassConstructor = env->GetMethodID(pairClass, "<init>", "(Ljava/lang/Object;Ljava/lang/Object;)V");

  bwMapClass = env->FindClass("org/openbw/bwapi4j/BWMapImpl");

  LOGGER("Initializing Java references... done");
}