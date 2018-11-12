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

#include "JniBwem.h"
#include "Globals.h"

namespace Bridge {
JniBwem::JniBwem() {}

void JniBwem::initialize(BWAPI::Game *game) { getMap().Initialize(game); }

BWEM::Map &JniBwem::getMap() { return BWEM::Map::Instance(); }

JniBwem bwem;
}  // namespace Bridge

JNIEXPORT void JNICALL Java_bwem_BWEM_Initialize_1native(JNIEnv *, jobject) { Bridge::bwem.initialize(BWAPI::BroodwarPtr); }

JNIEXPORT jintArray JNICALL Java_bwem_BWEM_getInitializedData_1native(JNIEnv *env, jobject) {
  Bridge::Globals::dataBuffer.reset();

  Bridge::Globals::dataBuffer.add(Bridge::bwem.getMap().Tiles().size());
  Bridge::Globals::dataBuffer.addFields(Bridge::bwem.getMap().Size());

  Bridge::Globals::dataBuffer.add(Bridge::bwem.getMap().MiniTiles().size());
  Bridge::Globals::dataBuffer.addFields(Bridge::bwem.getMap().WalkSize());

  Bridge::Globals::dataBuffer.addFields(Bridge::bwem.getMap().Center());

  Bridge::Globals::dataBuffer.add(Bridge::bwem.getMap().MaxAltitude());

  const auto &startingLocations = Bridge::bwem.getMap().StartingLocations();
  Bridge::Globals::dataBuffer.add(startingLocations.size());
  for (const auto &startingLocation : startingLocations) {
    Bridge::Globals::dataBuffer.addFields(startingLocation);
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

JNIEXPORT jboolean JNICALL Java_bwem_BWEM_EnableAutomaticPathAnalysis_1native(JNIEnv *, jobject) {
  Bridge::bwem.getMap().EnableAutomaticPathAnalysis();
  return Bridge::bwem.getMap().AutomaticPathUpdate();
}

JNIEXPORT jboolean JNICALL Java_bwem_BWEM_FindBasesForStartingLocations(JNIEnv *, jobject) { return Bridge::bwem.getMap().FindBasesForStartingLocations(); }
