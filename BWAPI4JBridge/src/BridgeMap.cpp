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
#include "Logger.h"
#include "org_openbw_bwapi4j_BWMapImpl.h"

void BridgeMap::initialize(JNIEnv *env, jclass jc, jobject bwObject, jclass bwMapClass) {
  LOGGER("Reading map information...");

  auto bwMap = env->GetObjectField(bwObject, env->GetFieldID(jc, "bwMap", "Lorg/openbw/bwapi4j/BWMapImpl;"));

  auto mapHash = env->NewStringUTF(BWAPI::Broodwar->mapHash().c_str());
  env->SetObjectField(bwMap, env->GetFieldID(bwMapClass, "mapHash", "Ljava/lang/String;"), mapHash);

  auto mapFileName = env->NewStringUTF(BWAPI::Broodwar->mapFileName().c_str());
  env->SetObjectField(bwMap, env->GetFieldID(bwMapClass, "mapFileName", "Ljava/lang/String;"), mapFileName);

  auto mapName = env->NewStringUTF(BWAPI::Broodwar->mapName().c_str());
  env->SetObjectField(bwMap, env->GetFieldID(bwMapClass, "mapName", "Ljava/lang/String;"), mapName);

  const auto mapTileSize = BWAPI::TilePosition(BWAPI::Broodwar->mapWidth(), BWAPI::Broodwar->mapHeight());
  const auto mapWalkSize = BWAPI::WalkPosition(mapTileSize);
  const auto mapPixelSize = BWAPI::Position(mapTileSize);

  env->SetIntField(bwMap, env->GetFieldID(bwMapClass, "tileWidth", "I"), mapTileSize.x);
  env->SetIntField(bwMap, env->GetFieldID(bwMapClass, "tileHeight", "I"), mapTileSize.y);

  env->SetIntField(bwMap, env->GetFieldID(bwMapClass, "walkWidth", "I"), mapWalkSize.x);
  env->SetIntField(bwMap, env->GetFieldID(bwMapClass, "walkHeight", "I"), mapWalkSize.y);

  env->SetIntField(bwMap, env->GetFieldID(bwMapClass, "pixelWidth", "I"), mapPixelSize.x);
  env->SetIntField(bwMap, env->GetFieldID(bwMapClass, "pixelHeight", "I"), mapPixelSize.y);

  auto groundHeightData = env->NewObjectArray(mapTileSize.x, env->GetObjectClass(env->NewIntArray(mapTileSize.y)), 0);
  for (int x = 0; x < mapTileSize.x; ++x) {
    auto *arr = new jint[mapTileSize.y];
    for (int y = 0; y < mapTileSize.y; ++y) {
      arr[y] = BWAPI::Broodwar->getGroundHeight(x, y);
    }
    auto jniArr = env->NewIntArray(mapTileSize.y);
    env->SetIntArrayRegion(jniArr, 0, mapTileSize.y, arr);
    env->SetObjectArrayElement(groundHeightData, x, jniArr);
  }
  env->SetObjectField(bwMap, env->GetFieldID(bwMapClass, "groundHeightData", "[[I"), groundHeightData);

  auto isWalkableData = env->NewObjectArray(mapWalkSize.x, env->GetObjectClass(env->NewIntArray(mapWalkSize.y)), 0);
  for (int x = 0; x < mapWalkSize.x; ++x) {
    auto *arr = new jint[mapWalkSize.y];
    for (int y = 0; y < mapWalkSize.y; ++y) {
      arr[y] = BWAPI::Broodwar->isWalkable(x, y) ? 1 : 0;
    }
    auto jniArr = env->NewIntArray(mapWalkSize.y);
    env->SetIntArrayRegion(jniArr, 0, mapWalkSize.y, arr);
    env->SetObjectArrayElement(isWalkableData, x, jniArr);
  }
  env->SetObjectField(bwMap, env->GetFieldID(bwMapClass, "isWalkableData", "[[I"), isWalkableData);

  jobject startLocationsList = env->GetObjectField(bwMap, env->GetFieldID(bwMapClass, "startLocations", "Ljava/util/ArrayList;"));

  for (const auto &tilePosition : BWAPI::Broodwar->getStartLocations()) {
    auto startLocation = env->NewObject(javaRefs.tilePositionClass, javaRefs.tilePositionConstructor, tilePosition.x, tilePosition.y);
    env->CallObjectMethod(startLocationsList, javaRefs.arrayListClass_add, startLocation);
  }

  if (env->ExceptionOccurred()) {
    env->ExceptionDescribe();
    return;
  }

  LOGGER("Reading map information... done");
}

// TODO: Check if "isBuildable" is static. If yes, move this into a native init method such as "int[] getIsBuildableData()" and call from Java during onStart.
JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1isBuildable(JNIEnv *, jobject, jint tileX, jint tileY, jboolean considerBuildings) {
  return BWAPI::Broodwar->isBuildable(tileX, tileY, considerBuildings) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1isExplored(JNIEnv *, jobject, jint tileX, jint tileY) {
  return BWAPI::Broodwar->isExplored(tileX, tileY) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1isVisible(JNIEnv *, jobject, jint tileX, jint tileY) {
  return BWAPI::Broodwar->isVisible(tileX, tileY) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1hasPath(JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2) {
  return BWAPI::Broodwar->hasPath(BWAPI::Position(x1, y1), BWAPI::Position(x2, y2)) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1canBuildHere__III(JNIEnv *, jobject, jint x, jint y, jint typeId) {
  return BWAPI::Broodwar->canBuildHere(BWAPI::TilePosition(x, y), (BWAPI::UnitType)typeId) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1canBuildHere__IIII(JNIEnv *, jobject, jint x, jint y, jint typeId, jint builderId) {
  return BWAPI::Broodwar->canBuildHere(BWAPI::TilePosition(x, y), (BWAPI::UnitType)typeId, BWAPI::Broodwar->getUnit(builderId)) ? 1 : 0;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BWMapImpl_getCreepData_1native(JNIEnv *env, jobject) {
  bridgeData.reset();

  for (int tileX = 0; tileX < BWAPI::Broodwar->mapWidth(); ++tileX) {
    for (int tileY = 0; tileY < BWAPI::Broodwar->mapHeight(); ++tileY) {
      const auto currentTilePosition = BWAPI::TilePosition(tileX, tileY);
      bridgeData.add(BWAPI::Broodwar->hasCreep(currentTilePosition));
    }
  }

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getNukeDotsData_1native(JNIEnv *env, jobject) {
  bridgeData.reset();

  for (const auto &nukeDotPosition : BWAPI::Broodwar->getNukeDots()) {
    bridgeData.addFields(nukeDotPosition);
  }

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
  return result;
}
