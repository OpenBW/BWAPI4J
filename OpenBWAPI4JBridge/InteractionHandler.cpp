/*
 * InteractionHandler.cpp
 *
 *  Created on: Oct 7, 2017
 *      Author: imp
 */
#include <BWAPI/Client.h>
#include "org_openbw_bwapi4j_InteractionHandler.h"

using namespace BWAPI;

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getKeyState(JNIEnv *env, jobject jObj, jint keyValue) {

	jboolean result = Broodwar->getKeyState((Key)keyValue);

	return result;
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableLatCom(JNIEnv *, jobject jObj, jboolean enabled) {

	std::cout << "enable latency compensation: " << (enabled != JNI_FALSE) << std::endl;
	Broodwar->setLatCom(enabled != JNI_FALSE);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_leaveGame(JNIEnv *env, jobject jObj) {

	Broodwar->leaveGame();
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_printf(JNIEnv *env, jobject jObj, jstring message) {

	const char* messagechars = env->GetStringUTFChars(message, 0);
	Broodwar->printf(messagechars);
	env->ReleaseStringUTFChars(message, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_sendText(JNIEnv *env, jobject jObj, jstring message) {

	const char* messagechars = env->GetStringUTFChars(message, 0);
	Broodwar->sendText("%s", messagechars);
	env->ReleaseStringUTFChars(message, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setLocalSpeed(JNIEnv *env, jobject jObj, jint speed) {

	Broodwar->setLocalSpeed(speed);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableUserInput(JNIEnv *env, jobject jObj) {

	Broodwar->enableFlag(Flag::UserInput);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableCompleteMapInformation(JNIEnv *env, jobject jObj) {

	Broodwar->enableFlag(Flag::CompleteMapInformation);
}

JNIEXPORT jlong JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getRandomSeed(JNIEnv *env, jobject jObj) {

	return (jlong)Broodwar->getRandomSeed();
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setFrameSkip(JNIEnv *env, jobject jObj, jint frameSkip) {

	Broodwar->setFrameSkip(frameSkip);
}
