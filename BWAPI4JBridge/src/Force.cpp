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

#include "bwapi_Force.h"

#include <BWAPI.h>

#include "Globals.h"

JNIEXPORT jstring JNICALL Java_bwapi_Force_getName_1native(JNIEnv *env, jobject, jint forceId) {
	return env->NewStringUTF(BWAPI::Broodwar->getForce(forceId)->getName().c_str());
}

JNIEXPORT jintArray JNICALL Java_bwapi_Force_getPlayerIds_1native(JNIEnv *env, jobject, jint forceId) {
	BUFFER_SETUP;

	const auto &force = BWAPI::Broodwar->getForce(forceId);
	for (const auto &player : force->getPlayers()) {
		Bridge::Globals::dataBuffer.addId(player);
	}

	BUFFER_RETURN;
}
