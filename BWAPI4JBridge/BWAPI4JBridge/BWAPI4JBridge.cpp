// BWAPI4JBridge.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include <BWAPI.h>
#include <BWAPI/Client.h>
#include <thread>
#include <chrono>
#include <jni.h>
#include <stdio.h>
#include "org_openbw_bwapi4j_BW.h"

using namespace BWAPI;

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

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv * env, jobject jObj, jobject classref) {

	jclass jc = env->GetObjectClass(classref);
	jmethodID gameStartCallback = env->GetMethodID(jc, "onStart", "()V");
	jmethodID gameEndCallback = env->GetMethodID(jc, "onEnd", "(Z)V");

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

		jclass arrayListClass = env->FindClass("java/util/ArrayList");
		jclass weaponTypeClass = env->FindClass("org/openbw/bwapi4j/type/WeaponType");
		jclass techTypeClass = env->FindClass("org/openbw/bwapi4j/type/TechType");
		jclass unitTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitType");
		jclass upgradeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UpgradeType");
		jclass damageTypeClass = env->FindClass("org/openbw/bwapi4j/type/DamageType");
		jclass explosionTypeClass = env->FindClass("org/openbw/bwapi4j/type/ExplosionType");
		jclass raceClass = env->FindClass("org/openbw/bwapi4j/type/Race");
		jclass unitSizeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitSizeType");
		jclass orderClass = env->FindClass("org/openbw/bwapi4j/type/Order");

		// read static data: UpgradeType
		println("reading upgrade types...");
		const UpgradeType::set& upgradeTypes = UpgradeTypes::allUpgradeTypes();
		for_each(upgradeTypes.begin(), upgradeTypes.end(), [&env, &upgradeTypeClass, &raceClass, &unitTypeClass](UpgradeType upgradeType) {

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

		});
		println("done.");

		// read static data: TechType
		println("reading tech types...");
		const TechType::set& techTypes = TechTypes::allTechTypes();
		for_each(techTypes.begin(), techTypes.end(), [&env, &techTypeClass, &raceClass, &weaponTypeClass, &unitTypeClass, &orderClass](TechType techType) {

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

		});
		println("done.");

		// read static data: WeaponType
		println("reading weapon types...");
		const WeaponType::set& weaponTypes = WeaponTypes::allWeaponTypes();
		for_each(weaponTypes.begin(), weaponTypes.end(), [&env, &weaponTypeClass, &techTypeClass, &unitTypeClass, &upgradeTypeClass, &damageTypeClass, &explosionTypeClass](WeaponType weaponType) {

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
		});
		println("done.");

		// read static data: UnitType
		println("reading unit types...");
		const UnitType::set& types = UnitTypes::allUnitTypes();
		for_each(types.begin(), types.end(), [&env, &unitTypeClass, &raceClass, &upgradeTypeClass, &techTypeClass, &weaponTypeClass, &unitSizeTypeClass, &arrayListClass](UnitType unitType) {

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
			jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

			jobject upgradesList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "upgrades", "Ljava/util/ArrayList;"));
			const UpgradeType::set& upgradeTypes = unitType.upgrades();
			for_each(upgradeTypes.begin(), upgradeTypes.end(), [&env, &upgradeTypeClass, &upgradesList, &arrayListAdd](UpgradeType upgradeType) {
				
				jobject upgradesMemberType = env->GetStaticObjectField(upgradeTypeClass, env->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
				env->CallObjectMethod(upgradesList, arrayListAdd, upgradesMemberType);
			});

			jobject upgradesWhatList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "upgradesWhat", "Ljava/util/ArrayList;"));
			const UpgradeType::set& upgradesWhatTypes = unitType.upgradesWhat();
			for_each(upgradesWhatTypes.begin(), upgradesWhatTypes.end(), [&env, &upgradeTypeClass, &upgradesWhatList, &arrayListAdd](UpgradeType upgradeType) {

				jobject upgradesWhatMemberType = env->GetStaticObjectField(upgradeTypeClass, env->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
				env->CallObjectMethod(upgradesWhatList, arrayListAdd, upgradesWhatMemberType);
			});

			jobject abilitiesList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "abilities", "Ljava/util/ArrayList;"));
			const TechType::set& abilitiesTypes = unitType.abilities();
			for_each(abilitiesTypes.begin(), abilitiesTypes.end(), [&env, &techTypeClass, &abilitiesList, &arrayListAdd](TechType techType) {

				jobject abilitiesMemberType = env->GetStaticObjectField(techTypeClass, env->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
				env->CallObjectMethod(abilitiesList, arrayListAdd, abilitiesMemberType);
			});

			jobject researchesWhatList = env->GetObjectField(CurrentUnitType, env->GetFieldID(unitTypeClass, "researchesWhat", "Ljava/util/ArrayList;"));
			const TechType::set& researchesWhatTypes = unitType.researchesWhat();
			for_each(researchesWhatTypes.begin(), researchesWhatTypes.end(), [&env, &techTypeClass, &researchesWhatList, &arrayListAdd](TechType techType) {

				jobject researchesWhatMemberType = env->GetStaticObjectField(techTypeClass, env->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
				env->CallObjectMethod(researchesWhatList, arrayListAdd, researchesWhatMemberType);
			});
			/* TODO:

	private TilePosition tileSize;
	private Pair<UnitType, Integer> whatBuilds;
	private Map<UnitType, Integer> requiredUnits;
			
			*/
		});
		println("done.");

		// read static data: Race
		println("reading race types...");
		const Race::set& races = Races::allRaces();
		for_each(races.begin(), races.end(), [&env, &unitTypeClass, &raceClass](Race race) {

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
			const char* fieldValues[] = { race.getWorker().getName().c_str(), race.getCenter().getName().c_str(), race.getRefinery().getName().c_str(), race.getTransport().getName().c_str(), race.getSupplyProvider().getName().c_str() };

			for (int i = 0; i < 5; i++) {
				
				jfieldID unitTypeField = env->GetStaticFieldID(unitTypeClass, fieldValues[i], "Lorg/openbw/bwapi4j/type/UnitType;");
				jobject unitType = env->GetStaticObjectField(unitTypeClass, unitTypeField);

				jfieldID typeField = env->GetFieldID(raceClass, fieldNames[i], "Lorg/openbw/bwapi4j/type/UnitType;");
				env->SetObjectField(CurrentRace, typeField, unitType);
			}

		});
		println("done.");

		if (Broodwar->isReplay()) {

		}
		else {
			
			env->CallObjectMethod(classref, gameStartCallback);

			while (Broodwar->isInGame()) {
				for (auto &e : Broodwar->getEvents()) {

					switch (e.getType()) {
						case EventType::MatchEnd:
							env->CallObjectMethod(classref, gameEndCallback, (jboolean)e.isWinner());
							break;
					}
				}

				BWAPI::BWAPIClient.update();
				if (!BWAPI::BWAPIClient.isConnected()) {
					reconnect();
				}
			}
			
		}
	}
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getUnitTypes(JNIEnv * env, jobject jObj) {

	const UnitType::set& types = UnitTypes::allUnitTypes();

	const int attributes = 57;
	jint *intBuf = new jint[attributes * types.size()];
	int index = 0;

	std::cout << types.size() << std::endl;

	for_each(types.begin(), types.end(), [&index, &intBuf, &attributes](UnitType unitType) {

		int bufferPtr = index * attributes;
		// std::cout << bufferPtr << std::endl;

		intBuf[bufferPtr + 0] = unitType.getID();
		intBuf[bufferPtr + 1] = unitType.getRace().getID();
		intBuf[bufferPtr + 2] = unitType.whatBuilds().first.getID();
		intBuf[bufferPtr + 3] = unitType.requiredTech().getID();
		intBuf[bufferPtr + 4] = unitType.armorUpgrade().getID();
		intBuf[bufferPtr + 5] = unitType.maxHitPoints();
		intBuf[bufferPtr + 6] = unitType.maxShields();
		intBuf[bufferPtr + 7] = unitType.maxEnergy();
		intBuf[bufferPtr + 8] = unitType.armor();
		intBuf[bufferPtr + 9] = unitType.mineralPrice();
		intBuf[bufferPtr + 10] = unitType.gasPrice();
		intBuf[bufferPtr + 11] = unitType.buildTime();
		intBuf[bufferPtr + 12] = unitType.supplyRequired();
		intBuf[bufferPtr + 13] = unitType.supplyProvided();
		intBuf[bufferPtr + 14] = unitType.spaceRequired();
		intBuf[bufferPtr + 15] = unitType.spaceProvided();
		intBuf[bufferPtr + 16] = unitType.buildScore();
		intBuf[bufferPtr + 17] = unitType.destroyScore();
		intBuf[bufferPtr + 18] = unitType.size().getID();
		intBuf[bufferPtr + 19] = unitType.tileWidth();
		intBuf[bufferPtr + 20] = unitType.tileHeight();
		intBuf[bufferPtr + 21] = unitType.dimensionLeft();
		intBuf[bufferPtr + 22] = unitType.dimensionUp();
		intBuf[bufferPtr + 23] = unitType.dimensionRight();
		intBuf[bufferPtr + 24] = unitType.dimensionDown();
		intBuf[bufferPtr + 25] = unitType.seekRange();
		intBuf[bufferPtr + 26] = unitType.sightRange();
		intBuf[bufferPtr + 27] = unitType.groundWeapon().getID();
		intBuf[bufferPtr + 28] = unitType.maxGroundHits();
		intBuf[bufferPtr + 29] = unitType.airWeapon().getID();
		intBuf[bufferPtr + 30] = unitType.maxAirHits();
		intBuf[bufferPtr + 31] = unitType.topSpeed() * 100; // hack to convert double to int
		intBuf[bufferPtr + 32] = unitType.acceleration();
		intBuf[bufferPtr + 33] = unitType.haltDistance();
		intBuf[bufferPtr + 34] = unitType.turnRadius();
		intBuf[bufferPtr + 35] = unitType.canProduce() ? 1 : 0;
		intBuf[bufferPtr + 36] = unitType.canAttack() ? 1 : 0;
		intBuf[bufferPtr + 37] = unitType.canMove() ? 1 : 0;
		intBuf[bufferPtr + 38] = unitType.isFlyer() ? 1 : 0;
		intBuf[bufferPtr + 39] = unitType.regeneratesHP() ? 1 : 0;
		intBuf[bufferPtr + 40] = unitType.isSpellcaster() ? 1 : 0;
		intBuf[bufferPtr + 41] = unitType.isInvincible() ? 1 : 0;
		intBuf[bufferPtr + 42] = unitType.isOrganic() ? 1 : 0;
		intBuf[bufferPtr + 43] = unitType.isMechanical() ? 1 : 0;
		intBuf[bufferPtr + 44] = unitType.isRobotic() ? 1 : 0;
		intBuf[bufferPtr + 45] = unitType.isDetector() ? 1 : 0;
		intBuf[bufferPtr + 46] = unitType.isResourceContainer() ? 1 : 0;
		intBuf[bufferPtr + 47] = unitType.isRefinery() ? 1 : 0;
		intBuf[bufferPtr + 48] = unitType.isWorker() ? 1 : 0;
		intBuf[bufferPtr + 49] = unitType.requiresPsi() ? 1 : 0;
		intBuf[bufferPtr + 50] = unitType.requiresCreep() ? 1 : 0;
		intBuf[bufferPtr + 51] = unitType.isBurrowable() ? 1 : 0;
		intBuf[bufferPtr + 52] = unitType.isCloakable() ? 1 : 0;
		intBuf[bufferPtr + 53] = unitType.isBuilding() ? 1 : 0;
		intBuf[bufferPtr + 54] = unitType.isAddon() ? 1 : 0;
		intBuf[bufferPtr + 55] = unitType.isFlyingBuilding() ? 1 : 0;
		intBuf[bufferPtr + 56] = unitType.isSpell() ? 1 : 0;

		// cloakingTech
		// abilities
		// upgrades
		// isMineralField
		// isTwoUnitsInOneEgg
		// isNeutral
		// isHero
		// isPowerup
		// isBeacon
		// isFlagBeacon
		// isSpecialBuilding
		index++;
	});

	jintArray result = env->NewIntArray(attributes * types.size());
	env->SetIntArrayRegion(result, 0, attributes * types.size(), intBuf);
	return result;
}

