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

/*
 * InteractionHandler.cpp
 *
 *  Created on: Oct 7, 2017
 *      Author: imp
 */
#include <BWAPI/Client.h>
#include "org_openbw_bwapi4j_InteractionHandler.h"

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getKeyState(JNIEnv *env, jobject jObj, jint keyValue) {
  jboolean result = BWAPI::Broodwar->getKeyState((BWAPI::Key)keyValue);

  return result;
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableLatCom(JNIEnv *, jobject jObj, jboolean enabled) {
  std::cout << "enable latency compensation: " << (enabled != JNI_FALSE) << std::endl;
  BWAPI::Broodwar->setLatCom(enabled != JNI_FALSE);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_leaveGame(JNIEnv *env, jobject jObj) { BWAPI::Broodwar->leaveGame(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_printf(JNIEnv *env, jobject jObj, jstring message) {
  const char *messagechars = env->GetStringUTFChars(message, 0);
  BWAPI::Broodwar->printf(messagechars);
  env->ReleaseStringUTFChars(message, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_sendText(JNIEnv *env, jobject jObj, jstring message) {
  const char *messagechars = env->GetStringUTFChars(message, 0);
  BWAPI::Broodwar->sendText("%s", messagechars);
  env->ReleaseStringUTFChars(message, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setLocalSpeed(JNIEnv *env, jobject jObj, jint speed) {
  BWAPI::Broodwar->setLocalSpeed(speed);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableUserInput(JNIEnv *env, jobject jObj) {
  BWAPI::Broodwar->enableFlag(BWAPI::Flag::UserInput);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableCompleteMapInformation(JNIEnv *env, jobject jObj) {
  BWAPI::Broodwar->enableFlag(BWAPI::Flag::CompleteMapInformation);
}

JNIEXPORT jlong JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getRandomSeed(JNIEnv *env, jobject jObj) { return (jlong)BWAPI::Broodwar->getRandomSeed(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setFrameSkip(JNIEnv *env, jobject jObj, jint frameSkip) {
  BWAPI::Broodwar->setFrameSkip(frameSkip);
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_allies_1native(JNIEnv *env, jobject jObj) {
  const size_t predictedMaxAllyIds = 16;
  const auto actualMaxAllyIds = BWAPI::Broodwar->allies().size();

  if (predictedMaxAllyIds < actualMaxAllyIds) {
    std::cout << "error: predicted number of ally IDs is less than actual number of ally IDs\n";
    return NULL;
  }

  jint allyIds[predictedMaxAllyIds];

  for (size_t i = 0; i < predictedMaxAllyIds; ++i) {
    allyIds[i] = -1;
  }

  size_t allyIdsIndex = 0;
  for (const auto &ally : BWAPI::Broodwar->allies()) {
    if (ally) {
      allyIds[allyIdsIndex++] = ally->getID();
    }
  }

  jintArray ret = env->NewIntArray(predictedMaxAllyIds);

  if (ret == NULL) {
    /* Probably out of memory error. */
    std::cout << "error: failed to create jintArray for ally IDs array\n";
    return NULL;
  }

  env->SetIntArrayRegion(ret, 0, predictedMaxAllyIds, allyIds);

  return ret;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enemies_1native(JNIEnv *env, jobject jObj) {
  const auto &enemies = BWAPI::Broodwar->enemies();

  const size_t predictedMaxEnemyIds = 16;
  const auto actualMaxEnemyIds = enemies.size();

  if (predictedMaxEnemyIds < actualMaxEnemyIds) {
    std::cout << "error: predicted number of enemy IDs is less than actual number of enemy IDs\n";
    return NULL;
  }

  jint enemyIds[predictedMaxEnemyIds];

  for (size_t i = 0; i < predictedMaxEnemyIds; ++i) {
    enemyIds[i] = -1;
  }

  size_t enemyIdsIndex = 0;
  for (const auto &enemy : enemies) {
    if (enemy) {
      enemyIds[enemyIdsIndex++] = enemy->getID();
    }
  }

  jintArray ret = env->NewIntArray(predictedMaxEnemyIds);

  if (ret == NULL) {
    /* Probably out of memory error. */
    std::cout << "error: failed to create jintArray for enemy IDs array\n";
    return NULL;
  }

  env->SetIntArrayRegion(ret, 0, predictedMaxEnemyIds, enemyIds);

  return ret;
}
