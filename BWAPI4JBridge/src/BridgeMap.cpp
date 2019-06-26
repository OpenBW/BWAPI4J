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

#include "BridgeMap.h"

#include <BWAPI.h>

#include "Globals.h"
#include "Logger.h"
#include "org_openbw_bwapi4j_BWMapImpl.h"

template <typename F>
void setJava2DIntArray(JNIEnv *env, jclass javaRef, jobject targetObject, const std::string &targetVariableName, const int maxX, const int maxY, F func) {
  auto data = env->NewObjectArray(maxX, env->GetObjectClass(env->NewIntArray(maxY)), 0);
  auto *arr = new jint[maxY];
  for (int x = 0; x < maxX; ++x) {
    for (int y = 0; y < maxY; ++y) {
      arr[y] = static_cast<int>(func(x, y));
    }
    auto jniArr = env->NewIntArray(maxY);
    env->SetIntArrayRegion(jniArr, 0, maxY, arr);
    env->SetObjectArrayElement(data, x, jniArr);
  }
  env->SetObjectField(targetObject, env->GetFieldID(javaRef, targetVariableName.c_str(), "[[I"), data);
  delete[] arr;
}

void BridgeMap::initialize(JNIEnv *env, jobject bw, const JavaRefs &javaRefs) {
  LOGGER("Reading map information...");

  auto bwMap = env->GetObjectField(bw, env->GetFieldID(javaRefs.bwClass, "bwMap", "Lorg/openbw/bwapi4j/BWMapImpl;"));

  auto mapHash = env->NewStringUTF(BWAPI::Broodwar->mapHash().c_str());
  env->SetObjectField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "mapHash", "Ljava/lang/String;"), mapHash);

  auto mapFileName = env->NewStringUTF(BWAPI::Broodwar->mapFileName().c_str());
  env->SetObjectField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "mapFileName", "Ljava/lang/String;"), mapFileName);

  auto mapName = env->NewStringUTF(BWAPI::Broodwar->mapName().c_str());
  env->SetObjectField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "mapName", "Ljava/lang/String;"), mapName);

  const auto mapTileSize = BWAPI::TilePosition(BWAPI::Broodwar->mapWidth(), BWAPI::Broodwar->mapHeight());
  const auto mapWalkSize = BWAPI::WalkPosition(mapTileSize);
  const auto mapPixelSize = BWAPI::Position(mapTileSize);

  env->SetIntField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "tileWidth", "I"), mapTileSize.x);
  env->SetIntField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "tileHeight", "I"), mapTileSize.y);

  env->SetIntField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "walkWidth", "I"), mapWalkSize.x);
  env->SetIntField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "walkHeight", "I"), mapWalkSize.y);

  env->SetIntField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "pixelWidth", "I"), mapPixelSize.x);
  env->SetIntField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "pixelHeight", "I"), mapPixelSize.y);

  setJava2DIntArray(env, javaRefs.bwMapClass, bwMap, "groundHeightData", mapTileSize.x, mapTileSize.y,
                    [&](const int x, const int y) { return BWAPI::Broodwar->getGroundHeight(x, y); });
  setJava2DIntArray(env, javaRefs.bwMapClass, bwMap, "isBuildableData", mapTileSize.x, mapTileSize.y,
                    [&](const int x, const int y) { return BWAPI::Broodwar->isBuildable(x, y); });
  setJava2DIntArray(env, javaRefs.bwMapClass, bwMap, "isWalkableData", mapWalkSize.x, mapWalkSize.y,
                    [&](const int x, const int y) { return BWAPI::Broodwar->isWalkable(x, y); });

  auto startLocationsList = env->GetObjectField(bwMap, env->GetFieldID(javaRefs.bwMapClass, "startLocations", "Ljava/util/ArrayList;"));
  for (const auto &tilePosition : BWAPI::Broodwar->getStartLocations()) {
    auto startLocation = env->NewObject(javaRefs.tilePositionClass, javaRefs.tilePositionConstructor, tilePosition.x, tilePosition.y);
    env->CallObjectMethod(startLocationsList, javaRefs.listClass_add, startLocation);
  }

  if (env->ExceptionOccurred()) {
    env->ExceptionDescribe();
    return;
  }

  LOGGER("Reading map information... done");
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl_isBuildable_1native(JNIEnv *, jobject, jint tileX, jint tileY, jboolean considerBuildings) {
  return BWAPI::Broodwar->isBuildable(tileX, tileY, considerBuildings) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl_isExplored_1native(JNIEnv *, jobject, jint tileX, jint tileY) {
  return BWAPI::Broodwar->isExplored(tileX, tileY) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl_isVisible_1native(JNIEnv *, jobject, jint tileX, jint tileY) {
  return BWAPI::Broodwar->isVisible(tileX, tileY) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1hasPath(JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2) {
  return BWAPI::Broodwar->hasPath(BWAPI::Position(x1, y1), BWAPI::Position(x2, y2)) ? 1 : 0;
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_BWMapImpl_canBuildHere_1native(JNIEnv *, jobject, jint x, jint y, jint unitTypeId, jint builderId,
                                                                                  jboolean checkExplored) {
  const auto &builder = BWAPI::Broodwar->getUnit(builderId);
  return BWAPI::Broodwar->canBuildHere(BWAPI::TilePosition((int)x, (int)y), BWAPI::UnitType((int)unitTypeId), builder, (bool)checkExplored);
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BWMapImpl_getCreepData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  for (int tileX = 0; tileX < BWAPI::Broodwar->mapWidth(); ++tileX) {
    for (int tileY = 0; tileY < BWAPI::Broodwar->mapHeight(); ++tileY) {
      const auto currentTilePosition = BWAPI::TilePosition(tileX, tileY);
      Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->hasCreep(currentTilePosition));
    }
  }

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BWMapImpl_getPylonPowerData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  for (int tileX = 0; tileX < BWAPI::Broodwar->mapWidth(); ++tileX) {
    for (int tileY = 0; tileY < BWAPI::Broodwar->mapHeight(); ++tileY) {
      const auto currentTilePosition = BWAPI::TilePosition(tileX, tileY);
      Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->hasPower(currentTilePosition));
    }
  }

  BUFFER_RETURN;
}
