// BWAPI4JBridge.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include <BWAPI.h>
#include <BWAPI/Client.h>
#include <thread>
#include <chrono>
#include <jni.h>
#include <stdio.h>
#define _USE_MATH_DEFINES
#include <math.h>
#include <BWTA.h>
#include "org_openbw_bwapi4j_BW.h"
#include "org_openbw_bwapi4j_InteractionHandler.h"
#include "org_openbw_bwapi4j_DamageEvaluator.h"
#include "org_openbw_bwapi4j_MapDrawer.h"
#include "org_openbw_bwapi4j_BWMap.h"
#include "org_openbw_bwapi4j_unit_Unit.h"
#include "bwta_BWTA.h"
#include "bwta_Polygon.h"
#include "bwta_BaseLocation.h"

using namespace BWAPI;

jint *intBuf;
const int bufferSize = 5000000;

// conversion ratios
double TO_DEGREES = 180.0 / M_PI;
double fixedScale = 100.0;

void reconnect() {
	while (!BWAPIClient.connect()) {
		std::this_thread::sleep_for(std::chrono::milliseconds{ 1000 });
	}
}

void flushPrint(const char * text) {
	printf(text);
	fflush(stdout);
}

void println(const char * text) {
	printf(text);
	flushPrint("\n");
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_Unit_issueCommand(JNIEnv * env, jobject jObj, jint unitID, jint unitCommandTypeID, jint targetUnitID, jint x, jint y, jint extra) {

	Unit unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		UnitCommand c = BWAPI::UnitCommand();
		c.unit = unit;
		c.type = unitCommandTypeID;
		c.target = Broodwar->getUnit(targetUnitID);
		c.x = x;
		c.y = y;
		c.extra = extra;
		return c.unit->issueCommand(c);
	}
	return JNI_FALSE;
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv * env, jobject bwObject, jobject classref) {

	env->EnsureLocalCapacity(512);
	jclass jc = env->GetObjectClass(classref);

	// allocate "shared memory"
	intBuf = new jint[bufferSize];

	println("Connecting to Broodwar...");
	reconnect();

	println("Connection successful, starting match...");
	while (true) {
		while (!Broodwar->isInGame()) {
			BWAPI::BWAPIClient.update();
			if (!BWAPI::BWAPIClient.isConnected()) {
				println("Reconnecting...");
				reconnect();
			}
		}
		std::cout << "Client version: " << Broodwar->getClientVersion() << std::endl;;

		jclass arrayListClass = env->FindClass("java/util/ArrayList");
		jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

		jclass integerClass = env->FindClass("java/lang/Integer");
		jmethodID integerNew = env->GetMethodID(integerClass, "<init>", "(I)V");

		jclass tilePositionClass = env->FindClass("org/openbw/bwapi4j/TilePosition");
		jmethodID tilePositionNew = env->GetMethodID(tilePositionClass, "<init>", "(II)V");

		jclass weaponTypeClass = env->FindClass("org/openbw/bwapi4j/type/WeaponType");
		jclass techTypeClass = env->FindClass("org/openbw/bwapi4j/type/TechType");
		jclass unitTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitType");
		jclass upgradeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UpgradeType");
		jclass damageTypeClass = env->FindClass("org/openbw/bwapi4j/type/DamageType");
		jclass explosionTypeClass = env->FindClass("org/openbw/bwapi4j/type/ExplosionType");
		jclass raceClass = env->FindClass("org/openbw/bwapi4j/type/Race");
		jclass unitSizeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitSizeType");
		jclass orderClass = env->FindClass("org/openbw/bwapi4j/type/Order");
		jclass pairClass = env->FindClass("org/openbw/bwapi4j/util/Pair");
		jmethodID pairNew = env->GetMethodID(pairClass, "<init>", "(Ljava/lang/Object;Ljava/lang/Object;)V");

		jclass bwMapClass = env->FindClass("org/openbw/bwapi4j/BWMap");
		
		jmethodID addRequiredUnit = env->GetMethodID(unitTypeClass, "addRequiredUnit", "(II)V");

		// read static data: UpgradeType
		println("reading upgrade types...");
		for (UpgradeType upgradeType : UpgradeTypes::allUpgradeTypes()) {

			if (upgradeType.getName().empty()) {
				return;
			}
			jfieldID upgradeTypeField = env->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;");
			jobject CurrentUpgradeType = env->GetStaticObjectField(upgradeTypeClass, upgradeTypeField);

			// set int fields
			env->SetIntField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "id", "I"), upgradeType.getID());
			env->SetIntField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "mineralPriceFactor", "I"), upgradeType.mineralPriceFactor());
			env->SetIntField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "gasPriceFactor", "I"), upgradeType.gasPriceFactor());
			env->SetIntField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "upgradeTimeFactor", "I"), upgradeType.upgradeTimeFactor());
			env->SetIntField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "maxRepeats", "I"), upgradeType.maxRepeats());
			//set int[] fields
			jint *gasPrices = new jint[upgradeType.maxRepeats()];
			jint *mineralPrices = new jint[upgradeType.maxRepeats()];
			jint *upgradeTimes = new jint[upgradeType.maxRepeats()];
			jobject *whatsRequired = new jobject[upgradeType.maxRepeats()];

			for (int i = 0; i < upgradeType.maxRepeats(); i++) {
				gasPrices[i] = upgradeType.gasPrice(i + 1);
				mineralPrices[i] = upgradeType.mineralPrice(i + 1);
				upgradeTimes[i] = upgradeType.upgradeTime(i + 1);
				whatsRequired[i] = env->GetStaticObjectField(unitTypeClass, env->GetStaticFieldID(unitTypeClass, upgradeType.whatsRequired(i + 1).getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
			}
			jintArray gasPricesArray = env->NewIntArray(upgradeType.maxRepeats());
			env->SetIntArrayRegion(gasPricesArray, 0, upgradeType.maxRepeats(), gasPrices);
			env->SetObjectField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "gasPrices", "[I"), gasPricesArray);

			jintArray mineralPricesArray = env->NewIntArray(upgradeType.maxRepeats());
			env->SetIntArrayRegion(mineralPricesArray, 0, upgradeType.maxRepeats(), mineralPrices);
			env->SetObjectField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "mineralPrices", "[I"), mineralPricesArray);

			jintArray upgradeTimesArray = env->NewIntArray(upgradeType.maxRepeats());
			env->SetIntArrayRegion(upgradeTimesArray, 0, upgradeType.maxRepeats(), upgradeTimes);
			env->SetObjectField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "upgradeTimes", "[I"), upgradeTimesArray);

			jobjectArray objectArray = env->NewObjectArray(upgradeType.maxRepeats(), unitTypeClass, NULL);
			env->SetObjectField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "whatsRequired", "[Lorg/openbw/bwapi4j/type/UnitType;"), objectArray);
			for (int i = 0; i < upgradeType.maxRepeats(); i++) {
				env->SetObjectArrayElement(objectArray, i, whatsRequired[i]);
			}

			// set enum fields
			jobject race = env->GetStaticObjectField(raceClass, env->GetStaticFieldID(raceClass, upgradeType.getRace().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
			env->SetObjectField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);
			
			jobject whatUpgrades = env->GetStaticObjectField(unitTypeClass, env->GetStaticFieldID(unitTypeClass, upgradeType.whatUpgrades().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
			env->SetObjectField(CurrentUpgradeType, env->GetFieldID(upgradeTypeClass, "whatUpgrades", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUpgrades);

		}
		println("done.");

		// read static data: TechType
		println("reading tech types...");
		for (TechType techType : TechTypes::allTechTypes()) {

			if (techType.getName().empty()) {
				return;
			}
			jfieldID techTypeField = env->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;");
			jobject CurrentTechType = env->GetStaticObjectField(techTypeClass, techTypeField);

			// set int fields
			env->SetIntField(CurrentTechType, env->GetFieldID(techTypeClass, "id", "I"), techType.getID());
			env->SetIntField(CurrentTechType, env->GetFieldID(techTypeClass, "mineralPrice", "I"), techType.mineralPrice());
			env->SetIntField(CurrentTechType, env->GetFieldID(techTypeClass, "gasPrice", "I"), techType.gasPrice());
			env->SetIntField(CurrentTechType, env->GetFieldID(techTypeClass, "researchTime", "I"), techType.researchTime());
			env->SetIntField(CurrentTechType, env->GetFieldID(techTypeClass, "energyCost", "I"), techType.energyCost());
			// set boolean fields
			env->SetBooleanField(CurrentTechType, env->GetFieldID(techTypeClass, "targetsUnit", "Z"), techType.targetsUnit());
			env->SetBooleanField(CurrentTechType, env->GetFieldID(techTypeClass, "targetsPosition", "Z"), techType.targetsPosition());
			// set enum fields
			jobject race = env->GetStaticObjectField(raceClass, env->GetStaticFieldID(raceClass, techType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
			env->SetObjectField(CurrentTechType, env->GetFieldID(techTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

			jobject weaponType = env->GetStaticObjectField(weaponTypeClass, env->GetStaticFieldID(weaponTypeClass, techType.getWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
			env->SetObjectField(CurrentTechType, env->GetFieldID(techTypeClass, "weaponType", "Lorg/openbw/bwapi4j/type/WeaponType;"), weaponType);

			jobject whatResearches = env->GetStaticObjectField(unitTypeClass, env->GetStaticFieldID(unitTypeClass, techType.whatResearches().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
			env->SetObjectField(CurrentTechType, env->GetFieldID(techTypeClass, "whatResearches", "Lorg/openbw/bwapi4j/type/UnitType;"), whatResearches);

			jobject order = env->GetStaticObjectField(orderClass, env->GetStaticFieldID(orderClass, techType.getOrder().getName().c_str(), "Lorg/openbw/bwapi4j/type/Order;"));
			env->SetObjectField(CurrentTechType, env->GetFieldID(techTypeClass, "order", "Lorg/openbw/bwapi4j/type/Order;"), order);

			jobject requiredUnit = env->GetStaticObjectField(unitTypeClass, env->GetStaticFieldID(unitTypeClass, techType.requiredUnit().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
			env->SetObjectField(CurrentTechType, env->GetFieldID(techTypeClass, "requiredUnit", "Lorg/openbw/bwapi4j/type/UnitType;"), requiredUnit);

		}
		println("done.");

		// read static data: WeaponType
		println("reading weapon types...");
		for (WeaponType weaponType : WeaponTypes::allWeaponTypes()) {

			if (weaponType.getName().empty()) {
				return;
			}
			jfieldID typeField = env->GetStaticFieldID(weaponTypeClass, weaponType.getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;");
			jobject CurrentWeaponType = env->GetStaticObjectField(weaponTypeClass, typeField);
			
			// set int fields
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "id", "I"), weaponType.getID());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "damageAmount", "I"), weaponType.damageAmount());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "damageBonus", "I"), weaponType.damageBonus());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "damageCooldown", "I"), weaponType.damageCooldown());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "damageFactor", "I"), weaponType.damageFactor());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "minRange", "I"), weaponType.minRange());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "maxRange", "I"), weaponType.maxRange());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "innerSplashRadius", "I"), weaponType.innerSplashRadius());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "medianSplashRadius", "I"), weaponType.medianSplashRadius());
			env->SetIntField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "outerSplashRadius", "I"), weaponType.outerSplashRadius());
			// set boolean fields
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsAir", "Z"), weaponType.targetsAir());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsGround", "Z"), weaponType.targetsGround());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsMechanical", "Z"), weaponType.targetsMechanical());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsOrganic", "Z"), weaponType.targetsOrganic());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsNonBuilding", "Z"), weaponType.targetsNonBuilding());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsNonRobotic", "Z"), weaponType.targetsNonRobotic());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsTerrain", "Z"), weaponType.targetsTerrain());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsOrgOrMech", "Z"), weaponType.targetsOrgOrMech());
			env->SetBooleanField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "targetsOwn", "Z"), weaponType.targetsOwn());
			
			// set enum fields
			jobject tech = env->GetStaticObjectField(techTypeClass, env->GetStaticFieldID(techTypeClass, weaponType.getTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
			env->SetObjectField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "tech", "Lorg/openbw/bwapi4j/type/TechType;"), tech);
			
			jobject whatUses = env->GetStaticObjectField(unitTypeClass, env->GetStaticFieldID(unitTypeClass, weaponType.whatUses().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
			env->SetObjectField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "whatUses", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUses);
			
			jobject upgradeType = env->GetStaticObjectField(upgradeTypeClass, env->GetStaticFieldID(upgradeTypeClass, weaponType.upgradeType().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
			env->SetObjectField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "upgradeType", "Lorg/openbw/bwapi4j/type/UpgradeType;"), whatUses);
			
			jobject damageType = env->GetStaticObjectField(damageTypeClass, env->GetStaticFieldID(damageTypeClass, weaponType.damageType().getName().c_str(), "Lorg/openbw/bwapi4j/type/DamageType;"));
			env->SetObjectField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "damageType", "Lorg/openbw/bwapi4j/type/DamageType;"), whatUses);
			
			jobject explosionType = env->GetStaticObjectField(explosionTypeClass, env->GetStaticFieldID(explosionTypeClass, weaponType.explosionType().getName().c_str(), "Lorg/openbw/bwapi4j/type/ExplosionType;"));
			env->SetObjectField(CurrentWeaponType, env->GetFieldID(weaponTypeClass, "explosionType", "Lorg/openbw/bwapi4j/type/ExplosionType;"), whatUses);
		}
		println("done.");
		
		// read static data: UnitType
		println("reading unit types...");
		for (UnitType unitType : UnitTypes::allUnitTypes()) {

			if (unitType.getName().empty()) {
				return;
			}

			jfieldID typeField = env->GetStaticFieldID(unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
			jobject CurrentUnitType = env->GetStaticObjectField(unitTypeClass, typeField);

			// set int fields
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "id", "I"), unitType.getID());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "maxHitPoints", "I"), unitType.maxHitPoints());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "maxShields", "I"), unitType.maxShields());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "maxEnergy", "I"), unitType.maxEnergy());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "armor", "I"), unitType.armor());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "mineralPrice", "I"), unitType.mineralPrice());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "gasPrice", "I"), unitType.gasPrice());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "buildTime", "I"), unitType.buildTime());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "supplyRequired", "I"), unitType.supplyRequired());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "supplyProvided", "I"), unitType.supplyProvided());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "spaceRequired", "I"), unitType.spaceRequired());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "spaceProvided", "I"), unitType.spaceProvided());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "buildScore", "I"), unitType.buildScore());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "destroyScore", "I"), unitType.destroyScore());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "tileWidth", "I"), unitType.tileWidth());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "tileHeight", "I"), unitType.tileHeight());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "dimensionLeft", "I"), unitType.dimensionLeft());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "dimensionUp", "I"), unitType.dimensionUp());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "dimensionRight", "I"), unitType.dimensionRight());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "dimensionDown", "I"), unitType.dimensionDown());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "width", "I"), unitType.width());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "height", "I"), unitType.height());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "seekRange", "I"), unitType.seekRange());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "sightRange", "I"), unitType.sightRange());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "maxGroundHits", "I"), unitType.maxGroundHits());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "maxAirHits", "I"), unitType.maxAirHits());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "acceleration", "I"), unitType.acceleration());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "haltDistance", "I"), unitType.haltDistance());
			env->SetIntField(CurrentUnitType, env->GetFieldID(unitTypeClass, "turnRadius", "I"), unitType.turnRadius());
			// set boolean fields
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "canProduce", "Z"), unitType.canProduce());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "canAttack", "Z"), unitType.canAttack());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "canMove", "Z"), unitType.canMove());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isFlyer", "Z"), unitType.isFlyer());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "regeneratesHP", "Z"), unitType.regeneratesHP());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isSpellcaster", "Z"), unitType.isSpellcaster());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "hasPermanentCloak", "Z"), unitType.hasPermanentCloak());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isInvincible", "Z"), unitType.isInvincible());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isOrganic", "Z"), unitType.isOrganic());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isRobotic", "Z"), unitType.isRobotic());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isDetector", "Z"), unitType.isDetector());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isResourceContainer", "Z"), unitType.isResourceContainer());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isResourceDepot", "Z"), unitType.isResourceDepot());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isRefinery", "Z"), unitType.isRefinery());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isWorker", "Z"), unitType.isWorker());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "requiresPsi", "Z"), unitType.requiresPsi());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "requiresCreep", "Z"), unitType.requiresCreep());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isTwoUnitsInOneEgg", "Z"), unitType.isTwoUnitsInOneEgg());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isBurrowable", "Z"), unitType.isBurrowable());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isCloakable", "Z"), unitType.isCloakable());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isBuilding", "Z"), unitType.isBuilding());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isAddon", "Z"), unitType.isAddon());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isFlyingBuilding", "Z"), unitType.isFlyingBuilding());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isNeutral", "Z"), unitType.isNeutral());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isHero", "Z"), unitType.isHero());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isPowerup", "Z"), unitType.isPowerup());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isBeacon", "Z"), unitType.isBeacon());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isFlagBeacon", "Z"), unitType.isFlagBeacon());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isSpecialBuilding", "Z"), unitType.isSpecialBuilding());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isSpell", "Z"), unitType.isSpell());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "producesCreep", "Z"), unitType.producesCreep());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "producesLarva", "Z"), unitType.producesLarva());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isMineralField", "Z"), unitType.isMineralField());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "isCritter", "Z"), unitType.isCritter());
			env->SetBooleanField(CurrentUnitType, env->GetFieldID(unitTypeClass, "canBuildAddon", "Z"), unitType.canBuildAddon());
			// set double fields
			env->SetDoubleField(CurrentUnitType, env->GetFieldID(unitTypeClass, "topSpeed", "D"), unitType.topSpeed());
			// set enum values
			jobject race = env->GetStaticObjectField(raceClass, env->GetStaticFieldID(raceClass, unitType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

			jobject requiredTech = env->GetStaticObjectField(techTypeClass, env->GetStaticFieldID(techTypeClass, unitType.requiredTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "requiredTech", "Lorg/openbw/bwapi4j/type/TechType;"), requiredTech);

			jobject cloakingTech = env->GetStaticObjectField(techTypeClass, env->GetStaticFieldID(techTypeClass, unitType.cloakingTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "cloakingTech", "Lorg/openbw/bwapi4j/type/TechType;"), cloakingTech);

			jobject armorUpgrade = env->GetStaticObjectField(upgradeTypeClass, env->GetStaticFieldID(upgradeTypeClass, unitType.armorUpgrade().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "armorUpgrade", "Lorg/openbw/bwapi4j/type/UpgradeType;"), armorUpgrade);

			jobject size = env->GetStaticObjectField(unitSizeTypeClass, env->GetStaticFieldID(unitSizeTypeClass, unitType.size().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitSizeType;"));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "size", "Lorg/openbw/bwapi4j/type/UnitSizeType;"), size);

			jobject groundWeapon = env->GetStaticObjectField(weaponTypeClass, env->GetStaticFieldID(weaponTypeClass, unitType.groundWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "groundWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), groundWeapon);

			jobject airWeapon = env->GetStaticObjectField(weaponTypeClass, env->GetStaticFieldID(weaponTypeClass, unitType.airWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "airWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), airWeapon);
			
			// set complex values
			jobject upgradesList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "upgrades", "Ljava/util/ArrayList;"));
			for(UpgradeType upgradeType : unitType.upgrades()) {
				
				jobject upgradesMemberType = env->GetStaticObjectField(upgradeTypeClass, env->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
				env->CallObjectMethod(upgradesList, arrayListAdd, upgradesMemberType);
				env->DeleteLocalRef(upgradesMemberType);
			}
			env->DeleteLocalRef(upgradesList);

			jobject upgradesWhatList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "upgradesWhat", "Ljava/util/ArrayList;"));
			for (UpgradeType upgradeType : unitType.upgradesWhat()) {

				jobject upgradesWhatMemberType = env->GetStaticObjectField(upgradeTypeClass, env->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
				env->CallObjectMethod(upgradesWhatList, arrayListAdd, upgradesWhatMemberType);
				env->DeleteLocalRef(upgradesWhatMemberType);
			}
			env->DeleteLocalRef(upgradesWhatList);

			jobject abilitiesList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "abilities", "Ljava/util/ArrayList;"));
			for (TechType techType : unitType.abilities()) {

				jobject abilitiesMemberType = env->GetStaticObjectField(techTypeClass, env->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
				env->CallObjectMethod(abilitiesList, arrayListAdd, abilitiesMemberType);
				env->DeleteLocalRef(abilitiesMemberType);
			}
			env->DeleteLocalRef(abilitiesList);

			jobject researchesWhatList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "researchesWhat", "Ljava/util/ArrayList;"));
			for(TechType techType : unitType.researchesWhat()) {

				jobject researchesWhatMemberType = env->GetStaticObjectField(techTypeClass, env->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
				env->CallObjectMethod(researchesWhatList, arrayListAdd, researchesWhatMemberType);
				env->DeleteLocalRef(researchesWhatMemberType);
			}
			env->DeleteLocalRef(researchesWhatList);
			
			// create a new Pair object and fill in UnitType,Integer
			jfieldID whatBuildsField = env->GetStaticFieldID(unitTypeClass, unitType.whatBuilds().first.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
			jobject whatBuildsType = env->GetStaticObjectField(unitTypeClass, whatBuildsField);
			
			jobject pairObject = env->NewObject(pairClass, pairNew, whatBuildsType, env->NewObject(integerClass, integerNew, unitType.whatBuilds().second));
			env->SetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "whatBuilds", "Lorg/openbw/bwapi4j/util/Pair;"), pairObject);
			
			// read existing requiredUnits map and put <UnitType,Integer> entries
			for (auto const& req : unitType.requiredUnits()) {

				// std::cout << "putting " << req.first.getID() << ":" << req.second << std::endl;
				env->CallObjectMethod(CurrentUnitType, addRequiredUnit, (jint)req.first.getID(), (jint)req.second);
			}
			env->DeleteLocalRef(CurrentUnitType);
		}
		println("done.");
		
		// read static data: Race
		println("reading race types...");
		for (Race race : Races::allRaces()) {

			if (race.getName().empty()) {
				return;
			}

			// get the Java enum value for the current race
			jfieldID raceField = env->GetStaticFieldID(raceClass, race.getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;");
			jobject CurrentRace = env->GetStaticObjectField(raceClass, raceField);

			// set ID
			jfieldID idField = env->GetFieldID(raceClass, "id", "I");
			env->SetIntField(CurrentRace, idField, race.getID());

			// set unit types
			const char* fieldNames[] = { "worker", "center", "refinery", "transport", "supplyProvider" };
			const char* fieldValues[] = { race.getWorker().getName().c_str(), race.getResourceDepot().getName().c_str(), race.getRefinery().getName().c_str(), race.getTransport().getName().c_str(), race.getSupplyProvider().getName().c_str() };

			for (int i = 0; i < 5; i++) {
				
				jfieldID unitTypeField = env->GetStaticFieldID(unitTypeClass, fieldValues[i], "Lorg/openbw/bwapi4j/type/UnitType;");
				jobject unitType = env->GetStaticObjectField(unitTypeClass, unitTypeField);

				jfieldID typeField = env->GetFieldID(raceClass, fieldNames[i], "Lorg/openbw/bwapi4j/type/UnitType;");
				env->SetObjectField(CurrentRace, typeField, unitType);
				env->DeleteLocalRef(unitType);
			}
			env->DeleteLocalRef(CurrentRace);
		}
		println("done.");
		
		// read map information
		println("reading map information...");
		jfieldID bwMapField = env->GetFieldID(jc, "bwMap", "Lorg/openbw/bwapi4j/BWMap;");
		jobject bwMap = env->GetObjectField(bwObject, bwMapField);

		// set mapHash
		jfieldID mapHashField = env->GetFieldID(bwMapClass, "mapHash", "Ljava/lang/String;");
		jstring mapHash = env->NewStringUTF(Broodwar->mapHash().c_str());
		env->SetObjectField(bwMap, mapHashField, mapHash);

		//set mapFileName
		jfieldID mapFileNameField = env->GetFieldID(bwMapClass, "mapFileName", "Ljava/lang/String;");
		jstring mapFileName = env->NewStringUTF(Broodwar->mapFileName().c_str());
		env->SetObjectField(bwMap, mapFileNameField, mapFileName);

		// set width
		jfieldID mapWidthField = env->GetFieldID(bwMapClass, "width", "I");
		env->SetIntField(bwMap, mapWidthField, (jint)Broodwar->mapWidth());
		
		// set height
		jfieldID mapHeightField = env->GetFieldID(bwMapClass, "height", "I");
		env->SetIntField(bwMap, mapHeightField, (jint)Broodwar->mapHeight());
		
		// set groundInfo (tile resolution)
		jfieldID groundInfoField = env->GetFieldID(bwMapClass, "groundInfo", "[[I");
		jobject* groundInfo2D = new jobject[Broodwar->mapWidth()];
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
		jobject* walkabilityInfo2D = new jobject[Broodwar->mapWidth() * 4];
		jobjectArray walkabilityInfo2DArray = env->NewObjectArray(Broodwar->mapWidth() * 4, env->GetObjectClass(env->NewIntArray(Broodwar->mapHeight() * 4)), 0);
		for (int i = 0; i < Broodwar->mapWidth() * 4; ++i) {

			jint* walkabilityInfo = new jint[Broodwar->mapHeight() * 4];
			for (int j = 0; j < Broodwar->mapHeight() * 4; ++j) {
				walkabilityInfo[j] = Broodwar->isWalkable(i, j) ? 1 : 0;
			}
			jintArray walkabilityInfoArray = env->NewIntArray(Broodwar->mapHeight() * 4);
			env->SetIntArrayRegion(walkabilityInfoArray, 0, Broodwar->mapHeight(), walkabilityInfo);
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
		println("done.");
		
		if (false && Broodwar->isReplay()) { // right now don't treat replays any different

		} else {
			
			env->CallObjectMethod(classref, env->GetMethodID(jc, "onStart", "()V"));
			jmethodID onEndCallback = env->GetMethodID(jc, "onEnd", "(Z)V");
			jmethodID preFrameCallback = env->GetMethodID(jc, "preFrame", "()V");
			jmethodID onFrameCallback = env->GetMethodID(jc, "onFrame", "()V");
			jmethodID onSendCallback = env->GetMethodID(jc, "onSendText", "(Ljava/lang/String;)V");
			jmethodID onReceiveCallback = env->GetMethodID(jc, "onReceiveText", "(ILjava/lang/String;)V");
			jmethodID onPlayerLeftCallback = env->GetMethodID(jc, "onPlayerLeft", "(I)V");
			jmethodID onNukeDetectCallback = env->GetMethodID(jc, "onNukeDetect", "(II)V");
			jmethodID onUnitDiscoverCallback = env->GetMethodID(jc, "onUnitDiscover", "(I)V");
			jmethodID onUnitEvadeCallback = env->GetMethodID(jc, "onUnitEvade", "(I)V");
			jmethodID onUnitShowCallback = env->GetMethodID(jc, "onUnitShow", "(I)V");
			jmethodID onUnitHideCallback = env->GetMethodID(jc, "onUnitHide", "(I)V");
			jmethodID onUnitCreateCallback = env->GetMethodID(jc, "onUnitCreate", "(I)V");
			jmethodID onUnitDestroyCallback = env->GetMethodID(jc, "onUnitDestroy", "(I)V");
			jmethodID onUnitMorphCallback = env->GetMethodID(jc, "onUnitMorph", "(I)V");
			jmethodID onUnitRenegadeCallback = env->GetMethodID(jc, "onUnitRenegade", "(I)V");
			jmethodID onSaveGameCallback = env->GetMethodID(jc, "onSaveGame", "(Ljava/lang/String;)V");
			jmethodID onUnitCompleteCallback = env->GetMethodID(jc, "onUnitComplete", "(I)V");

			while (Broodwar->isInGame()) {
				
				env->CallObjectMethod(classref, preFrameCallback);
				for (auto &e : Broodwar->getEvents()) {

					switch (e.getType()) {
						case EventType::MatchEnd: {
							//  std::cout << "calling onEnd..." << std::endl;
							env->CallObjectMethod(classref, onEndCallback, (jboolean)e.isWinner());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::MatchFrame: {
							// std::cout << "calling onFrame..." << std::endl;
							env->CallObjectMethod(classref, onFrameCallback);
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::SendText: {
							// std::cout << "calling onSend..." << std::endl;
							jstring string = env->NewStringUTF(e.getText().c_str());
							env->CallObjectMethod(classref, onSendCallback, string);
							env->DeleteLocalRef(string);
							// std::cout << "done." << std::endl;;
							}
							break;
						case EventType::ReceiveText: {
							// std::cout << "calling onReceive..." << std::endl;
							jstring string = env->NewStringUTF(e.getText().c_str());
							env->CallObjectMethod(classref, onReceiveCallback, e.getPlayer()->getID(), string);
							env->DeleteLocalRef(string);
							// std::cout << "done." << std::endl;;
							}
							break;
						case EventType::PlayerLeft: {
							// std::cout << "calling onPlayerLeft..." << std::endl;
							env->CallObjectMethod(classref, onPlayerLeftCallback, e.getPlayer()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::NukeDetect: {
							// std::cout << "calling onNukeDetect..." << std::endl;
							env->CallObjectMethod(classref, onNukeDetectCallback, e.getPosition().x, e.getPosition().y);
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitDiscover: {
							// std::cout << "calling onUnitDiscover..." << std::endl;
							env->CallObjectMethod(classref, onUnitDiscoverCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitEvade: {
							// std::cout << "calling onUnitEvade..." << std::endl;
							env->CallObjectMethod(classref, onUnitEvadeCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitShow: {
							// std::cout << "calling onUnitShow..." << std::endl;
							env->CallObjectMethod(classref, onUnitShowCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitHide: {
							// std::cout << "calling onUnitHide..." << std::endl;
							env->CallObjectMethod(classref, onUnitHideCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitCreate: {
							// std::cout << "calling onUnitCreate..." << std::endl;
							env->CallObjectMethod(classref, onUnitCreateCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitDestroy: {
							// std::cout << "calling onUnitDestroy..." << std::endl;
							env->CallObjectMethod(classref, onUnitDestroyCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitMorph: {
							// std::cout << "calling onUnitMorph..." << std::endl;
							env->CallObjectMethod(classref, onUnitMorphCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitRenegade: {
							// std::cout << "calling onUnitRenegade..." << std::endl;
							env->CallObjectMethod(classref, onUnitRenegadeCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::SaveGame: {
							std::cout << "calling onSaveGame..." << std::endl;
							jstring string = env->NewStringUTF(e.getText().c_str());
							env->CallObjectMethod(classref, onSaveGameCallback, string);
							env->DeleteLocalRef(string);
							std::cout << "done." << std::endl;;
							}
							break;
						case EventType::UnitComplete: {
							// std::cout << "calling onUnitComplete..." << std::endl;
							env->CallObjectMethod(classref, onUnitCompleteCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
					}
				}

				// std::cout << "updating to next frame..." << std::endl;
				BWAPI::BWAPIClient.update();
				// std::cout << "done." << std::endl;;
				if (!BWAPI::BWAPIClient.isConnected()) {
					std::cout << "reconnecting..." << std::endl;;
					reconnect();
				}
			}
			std::cout << "game over." << std::endl;;
		}
	}
}

int addUnitDataToBuffer(Unit &u, int index) {

	intBuf[index++] = u->getID();
	intBuf[index++] = u->getReplayID();
	intBuf[index++] = u->getPlayer()->getID();
	intBuf[index++] = u->getType().getID();
	intBuf[index++] = u->getPosition().x;
	intBuf[index++] = u->getPosition().y;
	intBuf[index++] = u->getTilePosition().x;
	intBuf[index++] = u->getTilePosition().y;
	intBuf[index++] = static_cast<int>(TO_DEGREES * u->getAngle());
	intBuf[index++] = static_cast<int>(fixedScale * u->getVelocityX());
	intBuf[index++] = static_cast<int>(fixedScale * u->getVelocityY());
	intBuf[index++] = u->getHitPoints();
	intBuf[index++] = u->getShields();
	intBuf[index++] = u->getEnergy();
	intBuf[index++] = u->getResources();
	intBuf[index++] = u->getResourceGroup();
	intBuf[index++] = u->getLastCommandFrame();
	intBuf[index++] = u->getLastCommand().getType().getID();
	// getLastAttackingPlayer doesn't work as documented, have to check for "None" player
	intBuf[index++] = (u->getLastAttackingPlayer() != NULL
		&& u->getLastAttackingPlayer()->getType() != PlayerTypes::None)
		? u->getLastAttackingPlayer()->getID() : -1;
	intBuf[index++] = u->getInitialType().getID();
	intBuf[index++] = u->getInitialPosition().x;
	intBuf[index++] = u->getInitialPosition().y;
	intBuf[index++] = u->getInitialTilePosition().x;
	intBuf[index++] = u->getInitialTilePosition().y;
	intBuf[index++] = u->getInitialHitPoints();
	intBuf[index++] = u->getInitialResources();
	intBuf[index++] = u->getKillCount();
	intBuf[index++] = u->getAcidSporeCount();
	intBuf[index++] = u->getInterceptorCount();
	intBuf[index++] = u->getScarabCount();
	intBuf[index++] = u->getSpiderMineCount();
	intBuf[index++] = u->getGroundWeaponCooldown();
	intBuf[index++] = u->getAirWeaponCooldown();
	intBuf[index++] = u->getSpellCooldown();
	intBuf[index++] = u->getDefenseMatrixPoints();
	intBuf[index++] = u->getDefenseMatrixTimer();
	intBuf[index++] = u->getEnsnareTimer();
	intBuf[index++] = u->getIrradiateTimer();
	intBuf[index++] = u->getLockdownTimer();
	intBuf[index++] = u->getMaelstromTimer();
	intBuf[index++] = u->getOrderTimer();
	intBuf[index++] = u->getPlagueTimer();
	intBuf[index++] = u->getRemoveTimer();
	intBuf[index++] = u->getStasisTimer();
	intBuf[index++] = u->getStimTimer();
	intBuf[index++] = u->getBuildType().getID();
	intBuf[index++] = u->getTrainingQueue().size();
	intBuf[index++] = u->getTech().getID();
	intBuf[index++] = u->getUpgrade().getID();
	intBuf[index++] = u->getRemainingBuildTime();
	intBuf[index++] = u->getRemainingTrainTime();
	intBuf[index++] = u->getRemainingResearchTime();
	intBuf[index++] = u->getRemainingUpgradeTime();
	intBuf[index++] = (u->getBuildUnit() != NULL) ? u->getBuildUnit()->getID() : -1;
	intBuf[index++] = (u->getTarget() != NULL) ? u->getTarget()->getID() : -1;
	intBuf[index++] = u->getTargetPosition().x;
	intBuf[index++] = u->getTargetPosition().y;
	intBuf[index++] = u->getOrder().getID();
	intBuf[index++] = (u->getOrderTarget() != NULL) ? u->getOrderTarget()->getID() : -1;
	intBuf[index++] = u->getSecondaryOrder().getID();
	intBuf[index++] = u->getRallyPosition().x;
	intBuf[index++] = u->getRallyPosition().y;
	intBuf[index++] = (u->getRallyUnit() != NULL) ? u->getRallyUnit()->getID() : -1;
	intBuf[index++] = (u->getAddon() != NULL) ? u->getAddon()->getID() : -1;
	intBuf[index++] = (u->getNydusExit() != NULL) ? u->getNydusExit()->getID() : -1;
	intBuf[index++] = (u->getTransport() != NULL) ? u->getTransport()->getID() : -1;
	intBuf[index++] = u->getLoadedUnits().size(); // see separate getLoadedUnits method
	intBuf[index++] = (u->getCarrier() != NULL) ? u->getCarrier()->getID() : -1;
	// see getInterceptorCount and separate getInterceptors method
	intBuf[index++] = (u->getHatchery() != NULL) ? u->getHatchery()->getID() : -1;
	intBuf[index++] = u->getLarva().size(); // see separate getLarva method
	intBuf[index++] = (u->getPowerUp() != NULL) ? u->getPowerUp()->getID() : -1;
	intBuf[index++] = u->exists() ? 1 : 0;
	intBuf[index++] = u->hasNuke() ? 1 : 0;
	intBuf[index++] = u->isAccelerating() ? 1 : 0;
	intBuf[index++] = u->isAttacking() ? 1 : 0;
	intBuf[index++] = u->isAttackFrame() ? 1 : 0;
	intBuf[index++] = u->isBeingConstructed() ? 1 : 0;
	intBuf[index++] = u->isBeingGathered() ? 1 : 0;
	intBuf[index++] = u->isBeingHealed() ? 1 : 0;
	intBuf[index++] = u->isBlind() ? 1 : 0;
	intBuf[index++] = u->isBraking() ? 1 : 0;
	intBuf[index++] = u->isBurrowed() ? 1 : 0;
	intBuf[index++] = u->isCarryingGas() ? 1 : 0;
	intBuf[index++] = u->isCarryingMinerals() ? 1 : 0;
	intBuf[index++] = u->isCloaked() ? 1 : 0;
	intBuf[index++] = u->isCompleted() ? 1 : 0;
	intBuf[index++] = u->isConstructing() ? 1 : 0;
	intBuf[index++] = u->isDefenseMatrixed() ? 1 : 0;
	intBuf[index++] = u->isDetected() ? 1 : 0;
	intBuf[index++] = u->isEnsnared() ? 1 : 0;
	intBuf[index++] = u->isFollowing() ? 1 : 0;
	intBuf[index++] = u->isGatheringGas() ? 1 : 0;
	intBuf[index++] = u->isGatheringMinerals() ? 1 : 0;
	intBuf[index++] = u->isHallucination() ? 1 : 0;
	intBuf[index++] = u->isHoldingPosition() ? 1 : 0;
	intBuf[index++] = u->isIdle() ? 1 : 0;
	intBuf[index++] = u->isInterruptible() ? 1 : 0;
	intBuf[index++] = u->isInvincible() ? 1 : 0;
	intBuf[index++] = u->isIrradiated() ? 1 : 0;
	intBuf[index++] = u->isLifted() ? 1 : 0;
	intBuf[index++] = u->isLoaded() ? 1 : 0;
	intBuf[index++] = u->isLockedDown() ? 1 : 0;
	intBuf[index++] = u->isMaelstrommed() ? 1 : 0;
	intBuf[index++] = u->isMorphing() ? 1 : 0;
	intBuf[index++] = u->isMoving() ? 1 : 0;
	intBuf[index++] = u->isParasited() ? 1 : 0;
	intBuf[index++] = u->isPatrolling() ? 1 : 0;
	intBuf[index++] = u->isPlagued() ? 1 : 0;
	intBuf[index++] = u->isRepairing() ? 1 : 0;
	intBuf[index++] = u->isSelected() ? 1 : 0;
	intBuf[index++] = u->isSieged() ? 1 : 0;
	intBuf[index++] = u->isStartingAttack() ? 1 : 0;
	intBuf[index++] = u->isStasised() ? 1 : 0;
	intBuf[index++] = u->isStimmed() ? 1 : 0;
	intBuf[index++] = u->isStuck() ? 1 : 0;
	intBuf[index++] = u->isTraining() ? 1 : 0;
	intBuf[index++] = u->isUnderAttack() ? 1 : 0;
	intBuf[index++] = u->isUnderDarkSwarm() ? 1 : 0;
	intBuf[index++] = u->isUnderDisruptionWeb() ? 1 : 0;
	intBuf[index++] = u->isUnderStorm() ? 1 : 0;
	intBuf[index++] = u->isPowered() ? 1 : 0;
	intBuf[index++] = u->isUpgrading() ? 1 : 0;
	intBuf[index++] = u->isVisible() ? 1 : 0;
	intBuf[index++] = u->isResearching() ? 1 : 0;
	intBuf[index++] = u->isFlying() ? 1 : 0;

	return index;
}

/**
* Returns the list of active units in the game.
*
* Each unit takes up a fixed number of integer values. Currently: 125
*/
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllUnitsData(JNIEnv * env, jobject jObject) {

	// std::cout << "processing units..." << std::endl;;

	int index = 0;

	for (Unit unit : Broodwar->getAllUnits()) {

			// std::cout << "adding " << unit->getID() << ":" << unit->getType().getName() << std::endl;
			index = addUnitDataToBuffer(unit, index);
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

int addPlayerDataToBuffer(Player &player, int index) {

	intBuf[index++] = player->getID();
	intBuf[index++] = player->getRace();
	intBuf[index++] = player->getStartLocation().x;
	intBuf[index++] = player->getStartLocation().y;
	intBuf[index++] = player->getColor();
	intBuf[index++] = player->getTextColor();
	intBuf[index++] = player->getType();
	intBuf[index++] = player->getForce()->getID();
	intBuf[index++] = player->isNeutral() ? 1 : 0;
	intBuf[index++] = player->isVictorious() ? 1 : 0;
	intBuf[index++] = player->isDefeated() ? 1 : 0;
	intBuf[index++] = player->leftGame() ? 1 : 0;
	intBuf[index++] = player->minerals();
	intBuf[index++] = player->gas();
	intBuf[index++] = player->gatheredMinerals();
	intBuf[index++] = player->gatheredGas();
	intBuf[index++] = player->repairedMinerals();
	intBuf[index++] = player->repairedGas();
	intBuf[index++] = player->refundedMinerals();
	intBuf[index++] = player->refundedGas();
	intBuf[index++] = player->spentMinerals();
	intBuf[index++] = player->spentGas();
	intBuf[index++] = player->supplyTotal();
	intBuf[index++] = player->getUnitScore();
	intBuf[index++] = player->getKillScore();
	intBuf[index++] = player->getBuildingScore();
	intBuf[index++] = player->getRazingScore();
	intBuf[index++] = player->getCustomScore();
	intBuf[index++] = player->isObserver() ? 1 : 0;
	intBuf[index++] = player->supplyUsed();
	intBuf[index++] = player->supplyTotal(Races::Zerg);
	intBuf[index++] = player->supplyTotal(Races::Terran);
	intBuf[index++] = player->supplyTotal(Races::Protoss);
	intBuf[index++] = player->supplyUsed(Races::Zerg);
	intBuf[index++] = player->supplyUsed(Races::Terran);
	intBuf[index++] = player->supplyUsed(Races::Protoss);
	intBuf[index++] = player->allUnitCount();
	intBuf[index++] = player->visibleUnitCount();
	intBuf[index++] = player->completedUnitCount();
	intBuf[index++] = player->incompleteUnitCount();
	intBuf[index++] = player->deadUnitCount();
	intBuf[index++] = player->killedUnitCount();

	return index;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllPlayersData(JNIEnv *env, jobject jObject) {

	int index = 0;

	for (Player player : Broodwar->getPlayers()) {

		index = addPlayerDataToBuffer(player, index);
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BW_getClientVersion(JNIEnv *env, jobject jObj) {

	return (jint)Broodwar->getClientVersion();
}

JNIEXPORT jstring JNICALL Java_org_openbw_bwapi4j_BW_getPlayerName(JNIEnv *env, jobject jObj, jint playerID) {

	// NewStringUTF can cause issues with unusual characters like Korean symbols
	return env->NewStringUTF(Broodwar->getPlayer(playerID)->getName().c_str());
	/* alternatively, use byte array:
	std::string str = Broodwar->getPlayer(playerID)->getName();
	jbyteArray jbArray = env->NewByteArray(str.length());
	env->SetByteArrayRegion(jbArray, 0, str.length(), (jbyte*)str.c_str());
	
	return jbArray;
	*/
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getResearchStatus(JNIEnv *env, jobject jObj, jint playerID) {

	int index = 0;
	Player p = Broodwar->getPlayer(playerID);

	for (TechType techType : TechTypes::allTechTypes()) {
		intBuf[index++] = techType.getID();
		intBuf[index++] = p->hasResearched((techType)) ? 1 : 0;
		intBuf[index++] = p->isResearching((techType)) ? 1 : 0;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getUpgradeStatus(JNIEnv *env, jobject jObj, jint playerID) {

	int index = 0;
	Player p = Broodwar->getPlayer(playerID);

	for (UpgradeType upgradeType : UpgradeTypes::allUpgradeTypes()) {
		intBuf[index++] = upgradeType.getID();
		intBuf[index++] = p->getUpgradeLevel((upgradeType));
		intBuf[index++] = p->isUpgrading((upgradeType)) ? 1 : 0;
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);
	return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getGameData(JNIEnv *env, jobject jObj) {
	
	int index = 0;
	intBuf[index++] = Broodwar->getLastError();
	intBuf[index++] = Broodwar->getScreenPosition().x;
	intBuf[index++] = Broodwar->getScreenPosition().y;
	intBuf[index++] = Broodwar->getMousePosition().x;
	intBuf[index++] = Broodwar->getMousePosition().y;
	intBuf[index++] = Broodwar->getFrameCount();
	intBuf[index++] = Broodwar->getFPS();
	intBuf[index++] = Broodwar->isLatComEnabled() ? 1 : 0;
	intBuf[index++] = Broodwar->getRemainingLatencyFrames();
	intBuf[index++] = Broodwar->getLatencyFrames();
	intBuf[index++] = Broodwar->getLatency();

	if (Broodwar->isReplay()) {

		for (Player player : Broodwar->getPlayers()) {

			intBuf[index++] = player->getID();
		}
	} else {
		intBuf[index++] = Broodwar->self()->getID();
		intBuf[index++] = Broodwar->enemy()->getID();
	}

	jintArray result = env->NewIntArray(index);
	env->SetIntArrayRegion(result, 0, index, intBuf);

	return result;
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_InteractionHandler_getKeyState(JNIEnv *env, jobject jObj, jint keyValue) {

	jboolean result = Broodwar->getKeyState((Key)keyValue);

	return result;
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_enableLatCom(JNIEnv *, jobject jObj, jboolean enabled) {

	Broodwar->setLatCom(enabled != JNI_FALSE);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_InteractionHandler_leaveGame(JNIEnv *env, jobject jObj) {

	Broodwar->leaveGame();
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

/*
//
//	DamageEvaluator
//
*/
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

/*
//
//	BWMap
//
*/
JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_BWMap_isVisible(JNIEnv *, jobject, jint tileX, jint tileY) {
	return Broodwar->isVisible(tileX, tileY);
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_BWMap_hasPath(JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2) {
	return Broodwar->hasPath(BWAPI::Position(x1, y1), BWAPI::Position(x2, y2));
}

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_BWMap_canBuildHere(JNIEnv *, jobject, jint x, jint y, jint typeId) {
	return Broodwar->canBuildHere(BWAPI::TilePosition(x, y), (UnitType)typeId);
}


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

		jobject jtilePosition = env->NewObject(positionClass, positionNew, (jint)baseLocation->getTilePosition().x, (jint)baseLocation->getTilePosition().y);
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

JNIEXPORT jint JNICALL Java_bwta_BWTA_getRegionT(JNIEnv *, jobject, jint x, jint y) {

	return (jint) BWTA::getRegion(TilePosition(x, y));
}

JNIEXPORT jint JNICALL Java_bwta_BWTA_getRegionP(JNIEnv *, jobject, jint x, jint y) {

	return (jint) BWTA::getRegion(Position(x, y));
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
	jmethodID arrayListNew = env->GetMethodID(arrayListClass, "init", "()V");
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

	intBuf[0] = x;
	intBuf[1] = y;
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
