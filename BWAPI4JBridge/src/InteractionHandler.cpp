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

#include <BWAPI.h>

#include "Globals.h"
#include "Logger.h"
#include "org_openbw_bwapi4j_InteractionHandler.h"

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getKeyState_1native(JNIEnv *, jobject, jint keyValue) {
  return BWAPI::Broodwar->getKeyState(BWAPI::Key(keyValue));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getMouseState_1native(JNIEnv *, jobject, jint mouseButtonValue) {
  return BWAPI::Broodwar->getMouseState(BWAPI::MouseButton(mouseButtonValue));
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableLatCom(JNIEnv *, jobject, jboolean enabled) {
  const bool parsedEnabled = (enabled == JNI_TRUE);
  LOGGER(fmt::format("Enable latency compensation: {}", parsedEnabled));
  BWAPI::Broodwar->setLatCom(parsedEnabled);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_leaveGame(JNIEnv *, jobject) { BWAPI::Broodwar->leaveGame(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_printf(JNIEnv *env, jobject, jstring message) {
  const char *messagechars = env->GetStringUTFChars(message, 0);
  BWAPI::Broodwar->printf(messagechars);
  env->ReleaseStringUTFChars(message, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_sendText(JNIEnv *env, jobject, jstring message) {
  const char *messagechars = env->GetStringUTFChars(message, 0);
  BWAPI::Broodwar->sendText("%s", messagechars);
  env->ReleaseStringUTFChars(message, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setLocalSpeed(JNIEnv *, jobject, jint speed) { BWAPI::Broodwar->setLocalSpeed(speed); }

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_isFlagEnabled_1native(JNIEnv *, jobject, jint flag) {
  return BWAPI::Broodwar->isFlagEnabled((int)flag);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableFlag_1native(JNIEnv *, jobject, jint flag) { BWAPI::Broodwar->enableFlag((int)flag); }

// Move this into onStart initialziation.
JNIEXPORT jlong JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getRandomSeed(JNIEnv *, jobject) { return (jlong)BWAPI::Broodwar->getRandomSeed(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setFrameSkip(JNIEnv *, jobject, jint frameSkip) { BWAPI::Broodwar->setFrameSkip(frameSkip); }

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_allies_1native(JNIEnv *env, jobject) {
  Bridge::Globals::dataBuffer.reset();

  Bridge::Globals::dataBuffer.addIds(BWAPI::Broodwar->allies());

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enemies_1native(JNIEnv *env, jobject) {
  Bridge::Globals::dataBuffer.reset();

  Bridge::Globals::dataBuffer.addIds(BWAPI::Broodwar->enemies());

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getLastError_1native(JNIEnv *, jobject) {
  return (jint)BWAPI::Broodwar->getLastError().getID();
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setScreenPosition_1native(JNIEnv *, jobject, jint pixelX, jint pixelY) {
  BWAPI::Broodwar->setScreenPosition(pixelX, pixelY);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_pauseGame(JNIEnv *, jobject) { BWAPI::Broodwar->pauseGame(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_resumeGame(JNIEnv *, jobject) { BWAPI::Broodwar->resumeGame(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_restartGame(JNIEnv *, jobject) { BWAPI::Broodwar->restartGame(); }

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_isGUIEnabled(JNIEnv *, jobject) { return BWAPI::Broodwar->isGUIEnabled(); }

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setGUI(JNIEnv *, jobject, jboolean enabled) { BWAPI::Broodwar->setGUI(enabled); }

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getNukeDotsData_1native(JNIEnv *env, jobject) {
  Bridge::Globals::dataBuffer.reset();

  for (const auto &nukeDotPosition : BWAPI::Broodwar->getNukeDots()) {
    Bridge::Globals::dataBuffer.addFields(nukeDotPosition);
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}
