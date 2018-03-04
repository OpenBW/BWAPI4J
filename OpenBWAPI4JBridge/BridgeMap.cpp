/*
 * BridgeMap.cpp
 *
 *  Created on: Jul 16, 2017
 *      Author: imp
 */

#include "BridgeMap.h"
#include "org_openbw_bwapi4j_BWMapImpl.h"
#include <BWAPI.h>

BridgeMap::BridgeMap() {

}

BridgeMap::~BridgeMap() {

}

using namespace BWAPI;

void BridgeMap::initialize(JNIEnv * env, jclass jc, jobject bwObject, jclass bwMapClass) {

	// read map information
		std::cout << "reading map information..." << std::endl;
		jfieldID bwMapField = env->GetFieldID(jc, "bwMap", "Lorg/openbw/bwapi4j/BWMapImpl;");
		jobject bwMap = env->GetObjectField(bwObject, bwMapField);

		// set mapHash
		jfieldID mapHashField = env->GetFieldID(bwMapClass, "mapHash", "Ljava/lang/String;");
		jstring mapHash = env->NewStringUTF(Broodwar->mapHash().c_str());
		env->SetObjectField(bwMap, mapHashField, mapHash);

		//set mapFileName
		jfieldID mapFileNameField = env->GetFieldID(bwMapClass, "mapFileName", "Ljava/lang/String;");
		jstring mapFileName = env->NewStringUTF(Broodwar->mapFileName().c_str());
		env->SetObjectField(bwMap, mapFileNameField, mapFileName);

		//set mapName
		jfieldID mapNameField = env->GetFieldID(bwMapClass, "mapName", "Ljava/lang/String;");
		jstring mapName = env->NewStringUTF(Broodwar->mapName().c_str());
		env->SetObjectField(bwMap, mapNameField, mapName);

		// set width
		jfieldID mapWidthField = env->GetFieldID(bwMapClass, "width", "I");
		env->SetIntField(bwMap, mapWidthField, (jint)Broodwar->mapWidth());

		// set height
		jfieldID mapHeightField = env->GetFieldID(bwMapClass, "height", "I");
		env->SetIntField(bwMap, mapHeightField, (jint)Broodwar->mapHeight());

		// set groundInfo (tile resolution)
		jfieldID groundInfoField = env->GetFieldID(bwMapClass, "groundInfo", "[[I");
		jobjectArray groundInfo2DArray = env->NewObjectArray(Broodwar->mapWidth(), env->GetObjectClass(env->NewIntArray(Broodwar->mapHeight())), 0);
		for (int i = 0; i < Broodwar->mapWidth(); ++i) {

			jint* groundInfo = new jint[Broodwar->mapHeight()];
			for (int j = 0; j < Broodwar->mapHeight(); ++j) {
				groundInfo[j] = Broodwar->getGroundHeight(i, j);
			}
			jintArray groundInfoArray = env->NewIntArray(Broodwar->mapHeight());
			env->SetIntArrayRegion(groundInfoArray, 0, Broodwar->mapHeight(), groundInfo);
			env->SetObjectArrayElement(groundInfo2DArray, i, groundInfoArray);
		}
		env->SetObjectField(bwMap, groundInfoField, groundInfo2DArray);

		// set walkabilityInfo (mini-tile resolution)
		jfieldID walkabilityInfoField = env->GetFieldID(bwMapClass, "walkabilityInfo", "[[I");
		jobjectArray walkabilityInfo2DArray = env->NewObjectArray(Broodwar->mapWidth() * 4, env->GetObjectClass(env->NewIntArray(Broodwar->mapHeight() * 4)), 0);
		for (int i = 0; i < Broodwar->mapWidth() * 4; ++i) {

			jint* walkabilityInfo = new jint[Broodwar->mapHeight() * 4];
			for (int j = 0; j < Broodwar->mapHeight() * 4; ++j) {
				walkabilityInfo[j] = Broodwar->isWalkable(i, j) ? 1 : 0;
			}
			jintArray walkabilityInfoArray = env->NewIntArray(Broodwar->mapHeight() * 4);
			env->SetIntArrayRegion(walkabilityInfoArray, 0, Broodwar->mapHeight() * 4, walkabilityInfo);
			env->SetObjectArrayElement(walkabilityInfo2DArray, i, walkabilityInfoArray);
		}
		env->SetObjectField(bwMap, walkabilityInfoField, walkabilityInfo2DArray);

		// set starting locations
		jobject startLocationsList = env->GetObjectField(bwMap, env->GetFieldID(bwMapClass, "startLocations", "Ljava/util/ArrayList;"));

		for (TilePosition tilePosition : Broodwar->getStartLocations()) {
			jobject startLocation = env->NewObject(tilePositionClass, tilePositionNew, tilePosition.x, tilePosition.y);
			env->CallObjectMethod(startLocationsList, arrayListAdd, startLocation);
		}

		if (env->ExceptionOccurred()) {
			env->ExceptionDescribe();
			return;
		}
		std::cout << "done." << std::endl;
}

/*
//
//	BWMap
//
*/
JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1isBuildable (JNIEnv *, jobject, jint tileX, jint tileY, jboolean considerBuildings) {
	return Broodwar->isBuildable(tileX, tileY, considerBuildings) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1isExplored (JNIEnv *, jobject, jint tileX, jint tileY) {
	return Broodwar->isExplored(tileX, tileY) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1isVisible (JNIEnv *, jobject, jint tileX, jint tileY) {
	return Broodwar->isVisible(tileX, tileY) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1hasPath (JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2) {
	return Broodwar->hasPath(BWAPI::Position(x1, y1), BWAPI::Position(x2, y2)) ? 1 : 0;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BWMapImpl__1canBuildHere (JNIEnv *, jobject, jint x, jint y, jint typeId) {
	return Broodwar->canBuildHere(BWAPI::TilePosition(x, y), (UnitType)typeId) ? 1 : 0;
}

