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

		// read static data
		jclass unitTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitType");


		const Race::set& races = Races::allRaces();
		for_each(races.begin(), races.end(), [&env, &unitTypeClass](Race race) {

			// get the Java enum value for the current race
			jclass raceClass = env->FindClass("org/openbw/bwapi4j/type/Race");
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

