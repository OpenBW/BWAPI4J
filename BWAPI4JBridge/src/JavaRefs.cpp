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

  listClass = env->FindClass("java/util/List");
  listClass_add = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");

  tilePositionClass = env->FindClass("org/openbw/bwapi4j/TilePosition");
  tilePositionConstructor = env->GetMethodID(tilePositionClass, "<init>", "(II)V");

  bwClass = env->FindClass("org/openbw/bwapi4j/BW");

  bwMapClass = env->FindClass("org/openbw/bwapi4j/BWMapImpl");

  LOGGER("Initializing Java references... done");
}