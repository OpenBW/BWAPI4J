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
#include "Bridge.h"
#include "Logger.h"
#include "org_openbw_bwapi4j_InteractionHandler.h"

namespace BWAPI4JBridge {
int addPlayerIdToBuffer(const BWAPI::Player &player, int index) {
  intBuf[index++] = player->getID();
  return index;
}

int addPlayerIdsToBuffer(const BWAPI::Playerset &players) {
  int index = 0;

  intBuf[index++] = players.size();

  for (const auto &player : players) {
    index = addPlayerIdToBuffer(player, index);
  }

  return index;
}
}  // namespace BWAPI4JBridge

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getKeyState(JNIEnv *env, jobject jObj, jint keyValue) {
  jboolean result = BWAPI::Broodwar->getKeyState((BWAPI::Key)keyValue);

  return result;
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableLatCom(JNIEnv *, jobject jObj, jboolean enabled) {
  LOGGER(fmt::format("enable latency compensation: {}", (enabled != JNI_FALSE)));
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
  const auto index = BWAPI4JBridge::addPlayerIdsToBuffer(BWAPI::Broodwar->allies());
  jintArray result = env->NewIntArray(index);
  env->SetIntArrayRegion(result, 0, index, intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enemies_1native(JNIEnv *env, jobject jObj) {
  const auto index = BWAPI4JBridge::addPlayerIdsToBuffer(BWAPI::Broodwar->enemies());
  jintArray result = env->NewIntArray(index);
  env->SetIntArrayRegion(result, 0, index, intBuf);
  return result;
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_pauseGame(JNIEnv *, jobject) { BWAPI::Broodwar->pauseGame(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setGUI(JNIEnv *, jobject, jboolean enabled) { BWAPI::Broodwar->setGUI(enabled); }
