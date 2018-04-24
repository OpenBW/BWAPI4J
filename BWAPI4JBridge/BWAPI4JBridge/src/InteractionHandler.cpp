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

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_setTextSize(JNIEnv *env, jobject jObj, jint bwapi4jTextSize) {

	Text::Size::Enum textSize = Text::Size::Default;

	switch (bwapi4jTextSize) {
	case 0:
		textSize = Text::Size::Small;
		break;
	case 1:
		textSize = Text::Size::Default;
		break;
	case 2:
		textSize = Text::Size::Large;
		break;
	case 3:
		textSize = Text::Size::Huge;
		break;
	default:
		textSize = Text::Size::Default;
		break;
	}

	Broodwar->setTextSize(textSize);
}