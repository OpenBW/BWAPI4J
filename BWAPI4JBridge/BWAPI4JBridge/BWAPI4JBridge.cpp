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
#include "So.h"
#include "BridgeEnum.h"
#include "BridgeMap.h"
#include "org_openbw_bwapi4j_BW.h"

using namespace BWAPI;

jint *intBuf;
const int bufferSize = 5000000;

bool finished = false;

// conversion ratios
double TO_DEGREES = 180.0 / M_PI;
double fixedScale = 100.0;

JNIEnv * globalEnv;
jobject globalBW;

jclass arrayListClass;
jmethodID arrayListAdd;

jclass integerClass;
jmethodID integerNew;

jclass tilePositionClass;
jmethodID tilePositionNew;

jclass weaponTypeClass;
jclass techTypeClass;
jclass unitTypeClass;
jclass upgradeTypeClass;
jclass damageTypeClass;
jclass explosionTypeClass;
jclass raceClass;
jclass unitSizeTypeClass;
jclass orderClass;

jclass pairClass;
jmethodID pairNew;

jclass bwMapClass;
jmethodID bwMapNew;

jmethodID addRequiredUnit;

/*
* Finds and stores references to Java classes and methods globally.
*/
void initializeJavaReferences(JNIEnv *env, jobject caller) {

	std::cout << "initializing Java references..." << std::endl;
	arrayListClass = env->FindClass("java/util/ArrayList");
	arrayListAdd = env->GetMethodID(arrayListClass, "add",
		"(Ljava/lang/Object;)Z");

	integerClass = env->FindClass("java/lang/Integer");
	integerNew = env->GetMethodID(integerClass, "<init>", "(I)V");

	tilePositionClass = env->FindClass("org/openbw/bwapi4j/TilePosition");
	tilePositionNew = env->GetMethodID(tilePositionClass, "<init>", "(II)V");

	weaponTypeClass = env->FindClass("org/openbw/bwapi4j/type/WeaponType");
	techTypeClass = env->FindClass("org/openbw/bwapi4j/type/TechType");
	unitTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitType");
	upgradeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UpgradeType");
	damageTypeClass = env->FindClass("org/openbw/bwapi4j/type/DamageType");
	explosionTypeClass = env->FindClass(
		"org/openbw/bwapi4j/type/ExplosionType");
	raceClass = env->FindClass("org/openbw/bwapi4j/type/Race");
	unitSizeTypeClass = env->FindClass("org/openbw/bwapi4j/type/UnitSizeType");
	orderClass = env->FindClass("org/openbw/bwapi4j/type/Order");
	pairClass = env->FindClass("org/openbw/bwapi4j/util/Pair");
	pairNew = env->GetMethodID(pairClass, "<init>",
		"(Ljava/lang/Object;Ljava/lang/Object;)V");

	bwMapClass = env->FindClass("org/openbw/bwapi4j/BWMap");

	addRequiredUnit = env->GetMethodID(unitTypeClass, "addRequiredUnit",
		"(II)V");

	std::cout << "done." << std::endl;
}

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

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_createUnit(JNIEnv *, jobject, jint ownerColor, jint unitType, jint posX, jint posY) {

	// not implemented in original BW
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_killUnit(JNIEnv *, jobject, jint unitID) {

	// not implemented in original BW
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_exit(JNIEnv *, jobject) {

	finished = true;
	printf("exiting after current game.");
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_mainThread(JNIEnv *, jobject) {

	// do nothing
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv * env, jobject bwObject, jobject bw) {

	globalEnv = env;
	globalBW = bw;

	env->EnsureLocalCapacity(512);
	jclass jc = env->GetObjectClass(bw);

	// allocate "shared memory"
	intBuf = new jint[bufferSize];

	initializeJavaReferences(env, bwObject);

	BridgeEnum *bridgeEnum = new BridgeEnum();
	BridgeMap *bridgeMap = new BridgeMap();

	println("Connecting to Broodwar...");
	reconnect();

	println("Connection successful, starting match...");

	while (!finished) {

		while (!Broodwar->isInGame()) {
			BWAPI::BWAPIClient.update();
			if (!BWAPI::BWAPIClient.isConnected()) {
				println("Reconnecting...");
				reconnect();
			}
		}
		std::cout << "Client version: " << Broodwar->getClientVersion() << std::endl;;

		bridgeEnum->initialize();
		bridgeMap->initialize(env, env->GetObjectClass(bwObject), bw, bwMapClass);
		
		if (false && Broodwar->isReplay()) { // right now don't treat replays any different

		} else {
			
			jmethodID preFrameCallback = env->GetMethodID(jc, "preFrame", "()V");
			env->CallObjectMethod(bw, preFrameCallback);
			env->CallObjectMethod(bw, env->GetMethodID(jc, "onStart", "()V"));

			jmethodID onEndCallback = env->GetMethodID(jc, "onEnd", "(Z)V");
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
				
				env->CallObjectMethod(bw, preFrameCallback);
				for (auto &e : Broodwar->getEvents()) {

					switch (e.getType()) {
						case EventType::MatchEnd: {
							//  std::cout << "calling onEnd..." << std::endl;
							env->CallObjectMethod(bw, onEndCallback, (jboolean)e.isWinner());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::MatchFrame: {
							// std::cout << "calling onFrame..." << std::endl;
							env->CallObjectMethod(bw, onFrameCallback);
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::SendText: {
							// std::cout << "calling onSend..." << std::endl;
							jstring string = env->NewStringUTF(e.getText().c_str());
							env->CallObjectMethod(bw, onSendCallback, string);
							env->DeleteLocalRef(string);
							// std::cout << "done." << std::endl;;
							}
							break;
						case EventType::ReceiveText: {
							// std::cout << "calling onReceive..." << std::endl;
							jstring string = env->NewStringUTF(e.getText().c_str());
							env->CallObjectMethod(bw, onReceiveCallback, e.getPlayer()->getID(), string);
							env->DeleteLocalRef(string);
							// std::cout << "done." << std::endl;;
							}
							break;
						case EventType::PlayerLeft: {
							// std::cout << "calling onPlayerLeft..." << std::endl;
							env->CallObjectMethod(bw, onPlayerLeftCallback, e.getPlayer()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::NukeDetect: {
							// std::cout << "calling onNukeDetect..." << std::endl;
							env->CallObjectMethod(bw, onNukeDetectCallback, e.getPosition().x, e.getPosition().y);
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitDiscover: {
							// std::cout << "calling onUnitDiscover..." << std::endl;
							env->CallObjectMethod(bw, onUnitDiscoverCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitEvade: {
							// std::cout << "calling onUnitEvade..." << std::endl;
							env->CallObjectMethod(bw, onUnitEvadeCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitShow: {
							// std::cout << "calling onUnitShow..." << std::endl;
							env->CallObjectMethod(bw, onUnitShowCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitHide: {
							// std::cout << "calling onUnitHide..." << std::endl;
							env->CallObjectMethod(bw, onUnitHideCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitCreate: {
							// std::cout << "calling onUnitCreate..." << std::endl;
							env->CallObjectMethod(bw, onUnitCreateCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitDestroy: {
							// std::cout << "calling onUnitDestroy..." << std::endl;
							env->CallObjectMethod(bw, onUnitDestroyCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitMorph: {
							// std::cout << "calling onUnitMorph..." << std::endl;
							env->CallObjectMethod(bw, onUnitMorphCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::UnitRenegade: {
							// std::cout << "calling onUnitRenegade..." << std::endl;
							env->CallObjectMethod(bw, onUnitRenegadeCallback, e.getUnit()->getID());
							// std::cout << "done." << std::endl;;
						}
							break;
						case EventType::SaveGame: {
							std::cout << "calling onSaveGame..." << std::endl;
							jstring string = env->NewStringUTF(e.getText().c_str());
							env->CallObjectMethod(bw, onSaveGameCallback, string);
							env->DeleteLocalRef(string);
							std::cout << "done." << std::endl;;
							}
							break;
						case EventType::UnitComplete: {
							// std::cout << "calling onUnitComplete..." << std::endl;
							env->CallObjectMethod(bw, onUnitCompleteCallback, e.getUnit()->getID());
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

