#include "BWAPI/GameImpl.h"
#include "BW/BWData.h"
#include "org_openbw_bwapi4j_DamageEvaluator.h"

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageFrom_1native__Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_Player_2(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer) {
	return Broodwar->getDamageFrom((UnitType)fromType, (UnitType)toType, Broodwar->getPlayer(fromPlayer));
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageFrom_1native__Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_type_UnitType_2(JNIEnv *, jobject, jint fromType, jint toType) {
	return Broodwar->getDamageFrom((UnitType)fromType, (UnitType)toType);
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageFrom_1native__Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_Player_2Lorg_openbw_bwapi4j_Player_2(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer, jint toPlayer) {
	return Broodwar->getDamageFrom((UnitType)fromType, (UnitType)toType, Broodwar->getPlayer(fromPlayer), Broodwar->getPlayer(toPlayer));
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageTo_1native__Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_Player_2(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer) {
	return Broodwar->getDamageTo((UnitType)fromType, (UnitType)toType, Broodwar->getPlayer(fromPlayer));
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageTo_1native__Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_type_UnitType_2(JNIEnv *, jobject, jint fromType, jint toType) {
	return Broodwar->getDamageTo((UnitType)fromType, (UnitType)toType);
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_DamageEvaluator_getDamageTo_1native__Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_type_UnitType_2Lorg_openbw_bwapi4j_Player_2Lorg_openbw_bwapi4j_Player_2(JNIEnv *, jobject, jint fromType, jint toType, jint fromPlayer, jint toPlayer) {
	return Broodwar->getDamageTo((UnitType)fromType, (UnitType)toType, Broodwar->getPlayer(fromPlayer), Broodwar->getPlayer(toPlayer));
}
