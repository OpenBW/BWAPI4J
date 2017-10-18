/*
 * MapDrawer.cpp
 *
 *  Created on: Oct 9, 2017
 *      Author: imp
 */

#include "org_openbw_bwapi4j_MapDrawer.h"
#include "BWAPI/GameImpl.h"
#include "BW/BWData.h"

using namespace BWAPI;

/*
//
//	Drawing
//
*/
JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawCircleMap_1native__IIII(JNIEnv *, jobject, jint x, jint y, jint radius, jint colorValue) {

	Color color(colorValue);
	Broodwar->drawCircleMap(x, y, radius, color);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawCircleMap_1native__IIIIZ(JNIEnv *, jobject, jint x, jint y, jint radius, jint colorValue, jboolean isSolid) {

	Color color(colorValue);
	Broodwar->drawCircleMap(x, y, radius, color, isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawTextScreen_1native(JNIEnv *env, jobject, jint x, jint y, jstring cstr_format) {

	const char* messagechars = env->GetStringUTFChars(cstr_format, 0);
	Broodwar->drawTextScreen(x, y, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawBoxMap_1native__IIIII(JNIEnv *, jobject, jint left, jint top, jint right, jint bottom, jint colorValue) {

	Color color(colorValue);
	Broodwar->drawBoxMap(left, top, right, bottom, color);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawBoxMap_1native__IIIIIZ(JNIEnv *, jobject, jint left, jint top, jint right, jint bottom, jint colorValue, jboolean isSolid) {

	Color color(colorValue);
	Broodwar->drawBoxMap(left, top, right, bottom, color, isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawBoxScreen_1native(JNIEnv *, jobject, jint left, jint top, jint right, jint bottom, jint colorValue, jboolean isSolid) {

	Color color(colorValue);
	Broodwar->drawBoxScreen(left, top, right, bottom, color, isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawLineMap_1native(JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2, jint colorValue) {

	Color color(colorValue);
	Broodwar->drawLineMap(x1, y1, x2, y2, color);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawTextMap_1native(JNIEnv *env, jobject, jint x, jint y, jstring cstr_format) {

	const char* messagechars = env->GetStringUTFChars(cstr_format, 0);
	Broodwar->drawTextMap(x, y, messagechars);
}

