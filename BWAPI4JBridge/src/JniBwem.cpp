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

JNIEXPORT void JNICALL Java_bwem_Map_Initialize_1native(JNIEnv *, jobject) { Bridge::bwem.initialize(BWAPI::BroodwarPtr); }

JNIEXPORT jintArray JNICALL Java_bwem_Map_getInitializedData_1native(JNIEnv *env, jobject) {
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

  {
    const auto &minerals = Bridge::bwem.getMap().Minerals();
    Bridge::Globals::dataBuffer.add(minerals.size());
    for (const auto &mineral : minerals) {
      Bridge::Globals::dataBuffer.addId(mineral.get()->Unit());
    }
  }

  {
    const auto &geysers = Bridge::bwem.getMap().Geysers();
    Bridge::Globals::dataBuffer.add(geysers.size());
    for (const auto &geyser : geysers) {
      Bridge::Globals::dataBuffer.addId(geyser.get()->Unit());
    }
  }

  {
    const auto &staticBuildings = Bridge::bwem.getMap().StaticBuildings();
    Bridge::Globals::dataBuffer.add(staticBuildings.size());
    for (const auto &staticBuilding : staticBuildings) {
      Bridge::Globals::dataBuffer.addId(staticBuilding.get()->Unit());
    }
  }

  {
    const auto &tiles = Bridge::bwem.getMap().Tiles();
    Bridge::Globals::dataBuffer.add(tiles.size());
    for (const auto &tile : tiles) {
      Bridge::Globals::dataBuffer.add(tile.Buildable());
      Bridge::Globals::dataBuffer.add(tile.GroundHeight());
      Bridge::Globals::dataBuffer.add(tile.Doodad());
      Bridge::Globals::dataBuffer.add(tile.MinAltitude());
      Bridge::Globals::dataBuffer.add(tile.AreaId());

      if (tile.GetNeutral()) {
        Bridge::Globals::dataBuffer.add(tile.GetNeutral()->Unit()->getID());
      } else {
        Bridge::Globals::dataBuffer.add(-1);
      }
    }
  }

  {
    const auto &miniTiles = Bridge::bwem.getMap().MiniTiles();
    Bridge::Globals::dataBuffer.add(miniTiles.size());
    for (const auto &miniTile : miniTiles) {
      Bridge::Globals::dataBuffer.add(miniTile.Altitude());
      Bridge::Globals::dataBuffer.add(miniTile.AreaId());
    }
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

JNIEXPORT jboolean JNICALL Java_bwem_Map_EnableAutomaticPathAnalysis_1native(JNIEnv *, jobject) {
  Bridge::bwem.getMap().EnableAutomaticPathAnalysis();
  return Bridge::bwem.getMap().AutomaticPathUpdate();
}

JNIEXPORT jboolean JNICALL Java_bwem_Map_FindBasesForStartingLocations(JNIEnv *, jobject) { return Bridge::bwem.getMap().FindBasesForStartingLocations(); }

JNIEXPORT void JNICALL Java_bwem_Map_OnMineralDestroyed_1native(JNIEnv *, jobject, jint unitId) {
  const auto &mineral = BWAPI::Broodwar->getUnit((int)unitId);
  Bridge::bwem.getMap().OnMineralDestroyed(mineral);
}

JNIEXPORT void JNICALL Java_bwem_Map_OnStaticBuildingDestroyed_1native(JNIEnv *, jobject, jint unitId) {
  const auto &building = BWAPI::Broodwar->getUnit((int)unitId);
  Bridge::bwem.getMap().OnStaticBuildingDestroyed(building);
}

JNIEXPORT jintArray JNICALL Java_bwem_Map_GetPath_1native(JNIEnv *env, jobject, jint ax, jint ay, jint bx, jint by) {
  Bridge::Globals::dataBuffer.reset();

  const BWAPI::Position a((int)ax, (int)ay);
  const BWAPI::Position b((int)bx, (int)by);

  int length = 0;

  const auto &path = Bridge::bwem.getMap().GetPath(a, b, &length);

  const auto chokepointCount = path.size();
  Bridge::Globals::dataBuffer.add(chokepointCount);

  for (const auto &cp : path) {
    const auto &center = cp->Center();
    Bridge::Globals::dataBuffer.addFields(center);
  }

  Bridge::Globals::dataBuffer.add(length);

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}
