/*
 * BWTA.cpp
 *
 *  Created on: Oct 8, 2017
 *      Author: imp
 */

#include <BWAPI.h>
#include <BWAPI/Client.h>
#include "BWTA.h"
#include "bwta_BWTA.h"
#include "bwta_Polygon.h"
#include "bwta_BaseLocation.h"

using namespace BWAPI;
//
//	BWTA
//
jobject createPolygon(JNIEnv * env, const BWTA::Polygon &polygon, jclass positionClass, jmethodID positionNew, jclass polygonClass, jmethodID polygonNew, jmethodID arrayListAdd) {

	jobject jpolygon = env->NewObject(polygonClass, polygonNew, (long)&polygon);
	env->SetDoubleField(jpolygon, env->GetFieldID(polygonClass, "area", "D"), (jdouble)polygon.getArea());
	env->SetDoubleField(jpolygon, env->GetFieldID(polygonClass, "perimeter", "D"), (jdouble)polygon.getPerimeter());
	jobject jpolcenter = env->NewObject(positionClass, positionNew, (jint)polygon.getCenter().x, (jint)polygon.getCenter().y);
	env->SetObjectField(jpolygon, env->GetFieldID(polygonClass, "center", "Lorg/openbw/bwapi4j/Position;"), jpolcenter);

	// set holes recursively in polygon
	jobject holeList = env->GetObjectField(jpolygon, env->GetFieldID(polygonClass, "holes", "Ljava/util/ArrayList;"));
	for (const auto& hole : polygon.getHoles()) {

		env->CallObjectMethod(holeList, arrayListAdd, createPolygon(env, *hole, positionClass, positionNew, polygonClass, polygonNew, arrayListAdd));
	}
	return jpolygon;
}

JNIEXPORT void JNICALL Java_bwta_BWTA_analyze(JNIEnv * env, jobject caller, jobject bwtaObject) {

	std::cout << "Starting BWTA analysis..." << std::endl;;
	BWTA::analyze();
	std::cout << "BWTA analysis done." << std::endl;;

	jclass longClass = env->FindClass("java/lang/Long");
	jmethodID longNew = env->GetMethodID(longClass, "<init>", "(J)V");

	jclass bwtaClass = env->FindClass("bwta/BWTA");
	jclass arrayListClass = env->FindClass("java/util/ArrayList");
	jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

	jclass regionClass = env->FindClass("bwta/Region");
	jmethodID regionNew = env->GetMethodID(regionClass, "<init>", "(J)V");
	jclass baseLocationClass = env->FindClass("bwta/BaseLocation");
	jmethodID baseLocationNew = env->GetMethodID(baseLocationClass, "<init>", "(J)V");
	jclass chokepointClass = env->FindClass("bwta/Chokepoint");
	jmethodID chokepointNew = env->GetMethodID(chokepointClass, "<init>", "(J)V");

	jclass polygonClass = env->FindClass("bwta/Polygon");
	jmethodID polygonNew = env->GetMethodID(polygonClass, "<init>", "(J)V");

	jclass positionClass = env->FindClass("org/openbw/bwapi4j/Position");
	jmethodID positionNew = env->GetMethodID(positionClass, "<init>", "(II)V");

	jclass tilePositionClass = env->FindClass("org/openbw/bwapi4j/TilePosition");
	jmethodID tilePositionNew = env->GetMethodID(tilePositionClass, "<init>", "(II)V");

	jclass pairClass = env->FindClass("org/openbw/bwapi4j/util/Pair");
	jmethodID pairNew = env->GetMethodID(pairClass, "<init>", "(Ljava/lang/Object;Ljava/lang/Object;)V");

	// set all regions
	std::cout << "reading regions..." << std::endl;;
	jobject regionList = env->GetObjectField(bwtaObject, env->GetFieldID(bwtaClass, "regions", "Ljava/util/ArrayList;"));

	for (const BWTA::Region *region : BWTA::getRegions()) {

		jobject jRegion = env->NewObject(regionClass, regionNew, (jlong)region);

		jobject jcenter = env->NewObject(positionClass, positionNew, (jint)region->getCenter().x, (jint)region->getCenter().y);

		env->SetObjectField(jRegion, env->GetFieldID(regionClass, "center", "Lorg/openbw/bwapi4j/Position;"), jcenter);

		env->SetObjectField(jRegion, env->GetFieldID(regionClass, "polygon", "Lbwta/Polygon;"), createPolygon(env, region->getPolygon(), positionClass, positionNew, polygonClass, polygonNew, arrayListAdd));

		env->SetIntField(jRegion, env->GetFieldID(regionClass, "maxDistance", "I"), (jint)region->getMaxDistance());

		// set chokepoints
		jobject chokepointList = env->GetObjectField(jRegion, env->GetFieldID(regionClass, "chokepointIds", "Ljava/util/ArrayList;"));
		for (const BWTA::Chokepoint *chokepoint : region->getChokepoints()) {
			env->CallObjectMethod(chokepointList, arrayListAdd, env->NewObject(longClass, longNew, (jlong)chokepoint));
		}

		// set base locations
		jobject baseLocationList = env->GetObjectField(jRegion, env->GetFieldID(regionClass, "baseLocationIds", "Ljava/util/ArrayList;"));
		for (const BWTA::BaseLocation *baseLocation : region->getBaseLocations()) {
			env->CallObjectMethod(baseLocationList, arrayListAdd, env->NewObject(longClass, longNew, (jlong)baseLocation));
		}

		// set reachable regions
		jobject reachableRegionsList = env->GetObjectField(jRegion, env->GetFieldID(regionClass, "reachableRegionIds", "Ljava/util/ArrayList;"));
		for (const BWTA::Region *reachableRegion : region->getReachableRegions()) {
			env->CallObjectMethod(reachableRegionsList, arrayListAdd, env->NewObject(longClass, longNew, (jlong)reachableRegion));
		}

		env->CallObjectMethod(regionList, arrayListAdd, jRegion);
	}
	if (env->ExceptionOccurred()) {
		env->ExceptionDescribe();
		return;
	}
	std::cout << "reading regions done." << std::endl;;

	// set all chokepoints
	std::cout << "reading chokepoints..." << std::endl;;
	jobject chokepointList = env->GetObjectField(bwtaObject, env->GetFieldID(bwtaClass, "chokepoints", "Ljava/util/ArrayList;"));

	for (const BWTA::Chokepoint *chokepoint : BWTA::getChokepoints()) {

		jobject jchokepoint = env->NewObject(chokepointClass, chokepointNew, (jlong)chokepoint);

		env->SetDoubleField(jchokepoint, env->GetFieldID(chokepointClass, "width", "D"), (jdouble)chokepoint->getWidth());

		jobject jcenter = env->NewObject(positionClass, positionNew, (jint)chokepoint->getCenter().x, (jint)chokepoint->getCenter().y);
		env->SetObjectField(jchokepoint, env->GetFieldID(chokepointClass, "center", "Lorg/openbw/bwapi4j/Position;"), jcenter);

		jobject first = env->NewObject(positionClass, positionNew, (jint)chokepoint->getSides().first.x, (jint)chokepoint->getSides().first.y);
		jobject second = env->NewObject(positionClass, positionNew, (jint)chokepoint->getSides().second.x, (jint)chokepoint->getSides().second.y);

		jobject pairObject = env->NewObject(pairClass, pairNew, first, second);
		env->SetObjectField(jchokepoint, env->GetFieldID(chokepointClass, "sides", "Lorg/openbw/bwapi4j/util/Pair;"), pairObject);

		env->SetLongField(jchokepoint, env->GetFieldID(chokepointClass, "region1Id", "J"), (jlong)chokepoint->getRegions().first);
		env->SetLongField(jchokepoint, env->GetFieldID(chokepointClass, "region2Id", "J"), (jlong)chokepoint->getRegions().second);

		env->CallObjectMethod(chokepointList, arrayListAdd, jchokepoint);
	}
	if (env->ExceptionOccurred()) {
		env->ExceptionDescribe();
		return;
	}
	std::cout << "reading chokepoints done." << std::endl;;

	// set all base locations
	std::cout << "reading base locations..." << std::endl;;
	jobject baseLocationList = env->GetObjectField(bwtaObject, env->GetFieldID(bwtaClass, "baseLocations", "Ljava/util/ArrayList;"));

	for (const BWTA::BaseLocation *baseLocation : BWTA::getBaseLocations()) {

		jobject jbaseLocation = env->NewObject(baseLocationClass, baseLocationNew, (jlong)baseLocation);

		jobject jposition = env->NewObject(positionClass, positionNew, (jint)baseLocation->getPosition().x, (jint)baseLocation->getPosition().y);
		env->SetObjectField(jbaseLocation, env->GetFieldID(baseLocationClass, "position", "Lorg/openbw/bwapi4j/Position;"), jposition);

		jobject jtilePosition = env->NewObject(tilePositionClass, tilePositionNew, (jint)baseLocation->getTilePosition().x, (jint)baseLocation->getTilePosition().y);
		env->SetObjectField(jbaseLocation, env->GetFieldID(baseLocationClass, "tilePosition", "Lorg/openbw/bwapi4j/TilePosition;"), jtilePosition);

		env->SetBooleanField(jbaseLocation, env->GetFieldID(baseLocationClass, "isIsland", "Z"), (jboolean)baseLocation->isIsland());
		env->SetBooleanField(jbaseLocation, env->GetFieldID(baseLocationClass, "isMineralsOnly", "Z"), (jboolean)baseLocation->isMineralOnly());
		env->SetBooleanField(jbaseLocation, env->GetFieldID(baseLocationClass, "isStartLocation", "Z"), (jboolean)baseLocation->isStartLocation());

		env->SetLongField(jbaseLocation, env->GetFieldID(baseLocationClass, "regionId", "J"), (jlong)baseLocation->getRegion());

		// TODO set List<MineralPatch> mineralPatches;
		// TODO set List<VespeneGeyser> geysers;

		env->CallObjectMethod(baseLocationList, arrayListAdd, jbaseLocation);
	}
	if (env->ExceptionOccurred()) {
		env->ExceptionDescribe();
		return;
	}
	std::cout << "reading base locations done." << std::endl;;
}

JNIEXPORT void JNICALL Java_bwta_BWTA_computeDistanceTransform(JNIEnv *, jobject) {

	BWTA::computeDistanceTransform();
}

JNIEXPORT void JNICALL Java_bwta_BWTA_balanceAnalysis(JNIEnv *, jobject) {

	BWTA::balanceAnalysis();
}

JNIEXPORT void JNICALL Java_bwta_BWTA_cleanMemory(JNIEnv *, jobject) {

	BWTA::cleanMemory();
}

JNIEXPORT jlong JNICALL Java_bwta_BWTA_getRegionT(JNIEnv *, jobject, jint x, jint y) {

	return (jlong) BWTA::getRegion(TilePosition(x, y));
}

JNIEXPORT jlong JNICALL Java_bwta_BWTA_getRegionP(JNIEnv *, jobject, jint x, jint y) {

	return (jlong) BWTA::getRegion(Position(x, y));
}

JNIEXPORT jdouble JNICALL Java_bwta_BWTA_getGroundDistance(JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2) {

	return (jdouble) BWTA::getGroundDistance(TilePosition(x1, y1), TilePosition(x2, y2));
}

JNIEXPORT jboolean JNICALL Java_bwta_BWTA_isConnected(JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2) {

	return (jboolean) BWTA::isConnected(x1, y1, x2, y2);
}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getShortestPath(JNIEnv *env, jobject, jint x1, jint y1, jint x2, jint y2) {

	jclass arrayListClass = env->FindClass("java/util/ArrayList");
	jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
	jmethodID arrayListNew = env->GetMethodID(arrayListClass, "<init>", "()V");
	jobject list = env->NewObject(arrayListClass, arrayListNew);

	jclass tilePositionClass = env->FindClass("org/openbw/bwapi4j/TilePosition");
	jmethodID tilePositionNew = env->GetMethodID(tilePositionClass, "<init>", "(II)V");

	for (const auto& pathItem : BWTA::getShortestPath(TilePosition(x1, y1), TilePosition(x2, y2))) {

		jobject tilePosition = env->NewObject(tilePositionClass, tilePositionNew, pathItem.x, pathItem.y);
		env->CallObjectMethod(list, arrayListAdd, tilePosition);
	}
	return list;
}

JNIEXPORT jintArray JNICALL Java_bwta_Polygon_getNearestPoint(JNIEnv *env, jobject, jlong id, jint x, jint y) {

	BWTA::Polygon* x_polygon = (BWTA::Polygon*)id;
	Position nearest = x_polygon->getNearestPoint(Position(x, y));

	jint* intBuf = new jint[2];

	intBuf[0] = nearest.x;
	intBuf[1] = nearest.y;
	jintArray result = env->NewIntArray(2);
	env->SetIntArrayRegion(result, 0, 2, intBuf);

	return result;
}

JNIEXPORT jdouble JNICALL Java_bwta_BaseLocation_getGroundDistance(JNIEnv *env, jobject caller, jobject baseLocation) {

	jclass baseLocationClass = env->FindClass("bwta/BaseLocation");

	long pointer1 = (long)env->GetObjectField(caller, env->GetFieldID(baseLocationClass, "id", "J"));
	BWTA::BaseLocation* baseLocation1 = (BWTA::BaseLocation*)pointer1;

	long pointer2 = (long)env->GetObjectField(baseLocation, env->GetFieldID(baseLocationClass, "id", "J"));
	BWTA::BaseLocation* baseLocation2 = (BWTA::BaseLocation*)pointer2;

	return (jdouble) baseLocation1->getGroundDistance(baseLocation2);
}

JNIEXPORT jdouble JNICALL Java_bwta_BaseLocation_getAirDistance(JNIEnv *env, jobject caller, jobject baseLocation) {

	jclass baseLocationClass = env->FindClass("bwta/BaseLocation");

	long pointer1 = (long)env->GetObjectField(caller, env->GetFieldID(baseLocationClass, "id", "J"));
	BWTA::BaseLocation* baseLocation1 = (BWTA::BaseLocation*)pointer1;

	long pointer2 = (long)env->GetObjectField(baseLocation, env->GetFieldID(baseLocationClass, "id", "J"));
	BWTA::BaseLocation* baseLocation2 = (BWTA::BaseLocation*)pointer2;

	return (jdouble)baseLocation1->getAirDistance(baseLocation2);
}


/*
JNIEXPORT jint JNICALL Java_bwta_BWTA_getMaxDistanceTransform(JNIEnv *, jclass) {
	return BWTA::getMaxDistanceTransform();
}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getUnwalkablePolygons(JNIEnv *, jclass) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getStartLocation(JNIEnv *, jclass, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getRegion__II(JNIEnv *, jclass, jint, jint) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestChokepoint__II(JNIEnv *, jclass, jint, jint) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestChokepoint__Lorg_openbw_bwapi4j_TilePosition_2(JNIEnv *, jclass, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestChokepoint__Lorg_openbw_bwapi4j_Position_2(JNIEnv *, jclass, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestBaseLocation__II(JNIEnv *, jclass, jint, jint) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestBaseLocation__Lorg_openbw_bwapi4j_TilePosition_2(JNIEnv *, jclass, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestBaseLocation__Lorg_openbw_bwapi4j_Position_2(JNIEnv *, jclass, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestUnwalkablePolygon__II(JNIEnv *, jclass, jint, jint) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestUnwalkablePolygon__Lorg_openbw_bwapi4j_TilePosition_2(JNIEnv *, jclass, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestUnwalkablePosition(JNIEnv *, jclass, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getNearestTilePosition(JNIEnv *, jclass, jobject, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getGroundDistances(JNIEnv *, jclass, jobject, jobject) {

}

JNIEXPORT jobject JNICALL Java_bwta_BWTA_getShortestPath__Lorg_openbw_bwapi4j_TilePosition_2Ljava_util_List_2(JNIEnv *, jclass, jobject, jobject) {

}

JNIEXPORT void JNICALL Java_bwta_BWTA_buildChokeNodes(JNIEnv *, jclass) {

}

JNIEXPORT jint JNICALL Java_bwta_BWTA_getGroundDistance2(JNIEnv *, jclass, jobject, jobject) {

}
*/
