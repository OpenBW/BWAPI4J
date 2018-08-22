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

#include <chrono>
#include <thread>

#ifdef _WIN32
#include <Windows.h>
#define DLLEXPORT __declspec(dllexport)
#define _USE_MATH_DEFINES
#else
#define DLLEXPORT
#endif

#include <math.h>
#include <stdio.h>

#include <BWAPI.h>
#include <jni.h>

#include "Bridge.h"
#include "BridgeEnum.h"
#include "BridgeMap.h"
#include "Logger.h"
#include "org_openbw_bwapi4j_BW.h"

#ifdef OPENBW
#include "BW/BWData.h"
#include "BWAPI/GameImpl.h"
#include "OpenBridgeModule.h"
#else
#include <BWAPI/Client.h>
#endif

#include "JniBwem.h"

const size_t intBufSize = 5000000;
jint intBuf[intBufSize];

bool finished = false;
bool onStartInitializationIsDone = false;

const double RADIANS_TO_DEGREES = 180.0 / M_PI;
const double DECIMAL_PRESERVATION_SCALE = 100.0;

double toDegrees(const double radians) { return radians * RADIANS_TO_DEGREES; }

int toPreservedDouble(const double d) { return static_cast<int>(DECIMAL_PRESERVATION_SCALE * d); }

// BWAPI 4.2.0:
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/UnitUpdate.cpp#L206-L212
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/BulletImpl.cpp#L93-L97
double toPreservedBwapiAngle(const double angle) { return (angle * 128.0 / M_PI); }

BridgeData bridgeData;

Callbacks callbacks;

JavaRefs javaRefs;

JNIEnv *globalEnv;
jobject globalBW;

#ifdef _WIN32
BOOL APIENTRY DllMain(HANDLE hModule, DWORD ul_reason_for_call, LPVOID lpReserved) { return TRUE; }
#endif

#ifdef OPENBW
extern "C" DLLEXPORT void gameInit(BWAPI::Game *game) { BWAPI::BroodwarPtr = game; }
extern "C" DLLEXPORT BWAPI::AIModule *newAIModule() { return new OpenBridge::OpenBridgeModule(); }
#endif

#ifndef OPENBW
void reconnect() {
  while (!BWAPI::BWAPIClient.connect()) {
    std::this_thread::sleep_for(std::chrono::milliseconds{1000});
  }
}
#endif

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_createUnit(JNIEnv *, jobject, jint playerID, jint unitType, jint posX, jint posY) {
#ifdef OPENBW
  BWAPI::Broodwar->createUnit(BWAPI::Broodwar->getPlayer(playerID), (BWAPI::UnitType)unitType, BWAPI::Position(posX, posY));
#endif
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_killUnit(JNIEnv *, jobject, jint unitID) {
#ifdef OPENBW
  BWAPI::Broodwar->killUnit(BWAPI::Broodwar->getUnit(unitID));
#endif
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_exit(JNIEnv *, jobject) {
#ifndef OPENBW
  finished = true;
  LOGGER("Exiting after current game.");
#endif
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_mainThread(JNIEnv *, jobject) {
#ifdef OPENBW
  BW::sacrificeThreadForUI([] {
    while (!finished) std::this_thread::sleep_for(std::chrono::seconds(5));
  });
#endif
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv *env, jobject bwObject, jobject bw) {
  globalEnv = env;
  globalBW = bw;

#ifndef OPENBW
  env->EnsureLocalCapacity(512);
  jclass jc = env->GetObjectClass(bw);
#endif

  /* Reset "shared memory". */
  for (size_t i = 0; i < intBufSize; ++i) {
    intBuf[i] = (jint)0;
  }

  javaRefs.initialize(env);

#ifdef OPENBW
  try {
    BW::GameOwner gameOwner;

    gameOwner.setPrintTextCallback([](const char *str) {
      std::string s;
      while (*str) {
        char c = *str++;
        if ((unsigned)c >= 0x20 || c == 9 || c == 10 || c == 13) s += c;
      }
      printf("%s\n", s.c_str());
    });

    BW::Game game = gameOwner.getGame();
    BWAPI::BroodwarImpl_handle handle(game);

    do {
      handle->autoMenuManager.startGame();

      while (!handle->bwgame.gameOver()) {
        handle->update();
        handle->bwgame.nextFrame();

        if (!handle->externalModuleConnected) {
          std::cerr << "error: no module loaded, exiting" << std::endl;
          if (env->ExceptionOccurred()) {
            env->ExceptionDescribe();
          }
          return;
        }
      }
      handle->onGameEnd();
      handle->bwgame.leaveGame();

    } while (!handle->bwgame.gameClosed() && handle->autoMenuManager.autoMenuRestartGame != "" && handle->autoMenuManager.autoMenuRestartGame != "OFF");

  } catch (const std::exception &e) {
    printf("Error: %s\n", e.what());
  }
  finished = true;
#else
  BridgeEnum bridgeEnum;
  BridgeMap bridgeMap;

  LOGGER("Connecting to Broodwar...");
  reconnect();

  LOGGER("Connection successful, starting match...");

  while (!finished) {
    // TODO: Determine if we need all these different "connect/reconnect" calls. Can it not be just one?
    while (!BWAPI::Broodwar->isInGame()) {
      BWAPI::BWAPIClient.update();
      if (!BWAPI::BWAPIClient.isConnected()) {
        LOGGER("Reconnecting...");
        reconnect();
      }
    }
    LOGGER(fmt::format("Client version: {}", BWAPI::Broodwar->getClientVersion()));

    bridgeEnum.initialize();
    bridgeMap.initialize(env, env->GetObjectClass(bwObject), bw, javaRefs.bwMapClass);

    if (false && BWAPI::Broodwar->isReplay()) {  // right now don't treat replays any different

    } else {
      LOGGER("Calling onStart callback...");
      env->CallObjectMethod(bw, env->GetMethodID(jc, "onStart", "()V"));
      LOGGER("Calling onStart callback... done");

      LOGGER("Waiting for Java onStart initialization to finish...");
      while (!onStartInitializationIsDone) {
        /* Do nothing. Just wait. */
      }
      LOGGER("Waiting for Java onStart initialization to finish... done");

      callbacks.initialize(env, jc);

      LOGGER("Entering in-game event loop...");

      while (BWAPI::Broodwar->isInGame()) {
        callbacks.processEvents(env, bw, BWAPI::Broodwar->getEvents());

        BWAPI::BWAPIClient.update();  // Update to next frame.

        if (!BWAPI::BWAPIClient.isConnected()) {
          LOGGER("Reconnecting...");
          reconnect();
        }
      }
      LOGGER("Game over.");
    }
  }
#endif
}

int addBulletDataToBuffer(BWAPI::Bullet &b, int index) {
  intBuf[index++] = b->exists() ? 1 : 0;
  intBuf[index++] = toPreservedDouble(toPreservedBwapiAngle(b->getAngle()));
  intBuf[index++] = b->getID();
  intBuf[index++] = b->getPlayer() ? b->getPlayer()->getID() : -1;
  intBuf[index++] = b->getPosition().x;
  intBuf[index++] = b->getPosition().y;
  intBuf[index++] = b->getRemoveTimer();
  intBuf[index++] = b->getSource() ? b->getSource()->getID() : -1;
  intBuf[index++] = b->getTarget() ? b->getTarget()->getID() : -1;
  intBuf[index++] = b->getTargetPosition().x;
  intBuf[index++] = b->getTargetPosition().y;
  intBuf[index++] = b->getType();
  intBuf[index++] = toPreservedDouble(b->getVelocityX());
  intBuf[index++] = toPreservedDouble(b->getVelocityY());
  intBuf[index++] = b->isVisible() ? 1 : 0;

  return index;
}

/**
 * Returns the list of active bullets in the game.
 *
 * Each bullet takes up a fixed number of integer values. Currently: 15.
 */
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllBulletsData(JNIEnv *env, jobject jObject) {
  int index = 0;
  for (BWAPI::Bullet bullet : BWAPI::Broodwar->getBullets()) {
    index = addBulletDataToBuffer(bullet, index);
  }

  jintArray result = env->NewIntArray(index);
  env->SetIntArrayRegion(result, 0, index, intBuf);
  return result;
}

int addUnitDataToBuffer(BWAPI::Unit &u, int index) {
  intBuf[index++] = u->getID();
  intBuf[index++] = u->getReplayID();
  intBuf[index++] = u->getPlayer()->getID();
  intBuf[index++] = u->getType().getID();
  intBuf[index++] = u->getPosition().x;
  intBuf[index++] = u->getPosition().y;
  intBuf[index++] = u->getTilePosition().x;
  intBuf[index++] = u->getTilePosition().y;
  intBuf[index++] = toPreservedDouble(toPreservedBwapiAngle(u->getAngle()));
  intBuf[index++] = toPreservedDouble(u->getVelocityX());
  intBuf[index++] = toPreservedDouble(u->getVelocityY());
  intBuf[index++] = u->getHitPoints();
  intBuf[index++] = u->getShields();
  intBuf[index++] = u->getEnergy();
  intBuf[index++] = u->getResources();
  intBuf[index++] = u->getResourceGroup();
  intBuf[index++] = u->getLastCommandFrame();
  intBuf[index++] = u->getLastCommand().getType().getID();
  intBuf[index++] = (u->getLastAttackingPlayer() && u->getLastAttackingPlayer()->getType() != BWAPI::PlayerTypes::None)
                        ? u->getLastAttackingPlayer()->getID()
                        : -1;  // getLastAttackingPlayer doesn't work as documented, have to check for "None" player
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

  const auto &trainingQueue = u->getTrainingQueue();
  const auto trainingQueueSize = trainingQueue.size();
  intBuf[index++] = trainingQueueSize;

  intBuf[index++] = u->getTech().getID();
  intBuf[index++] = u->getUpgrade().getID();
  intBuf[index++] = u->getRemainingBuildTime();
  intBuf[index++] = u->getRemainingTrainTime();
  intBuf[index++] = u->getRemainingResearchTime();
  intBuf[index++] = u->getRemainingUpgradeTime();
  intBuf[index++] = u->getBuildUnit() ? u->getBuildUnit()->getID() : -1;
  intBuf[index++] = u->getTarget() ? u->getTarget()->getID() : -1;
  intBuf[index++] = u->getTargetPosition().x;
  intBuf[index++] = u->getTargetPosition().y;
  intBuf[index++] = u->getOrder().getID();
  intBuf[index++] = u->getOrderTarget() ? u->getOrderTarget()->getID() : -1;
  intBuf[index++] = u->getSecondaryOrder().getID();
  intBuf[index++] = u->getRallyPosition().x;
  intBuf[index++] = u->getRallyPosition().y;
  intBuf[index++] = u->getRallyUnit() ? u->getRallyUnit()->getID() : -1;
  intBuf[index++] = u->getAddon() ? u->getAddon()->getID() : -1;
  intBuf[index++] = u->getNydusExit() ? u->getNydusExit()->getID() : -1;
  intBuf[index++] = u->getTransport() ? u->getTransport()->getID() : -1;
  intBuf[index++] = u->getLoadedUnits().size();                       // see separate getLoadedUnits method
  intBuf[index++] = u->getCarrier() ? u->getCarrier()->getID() : -1;  // see getInterceptorCount and separate getInterceptors method
  intBuf[index++] = u->getHatchery() ? u->getHatchery()->getID() : -1;
  intBuf[index++] = u->getLarva().size();  // see separate getLarva method
  intBuf[index++] = u->getPowerUp() ? u->getPowerUp()->getID() : -1;
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
  intBuf[index++] = u->getOrderTargetPosition().x;
  intBuf[index++] = u->getOrderTargetPosition().y;

  // TODO: Refactor and reduce duplicated code with loaded units.
  /* Training Queue */ {
    const size_t maxTrainingQueueSize = 5;
    for (size_t i = 0; i < trainingQueueSize; ++i) {
      const auto &ut = trainingQueue[i];
      intBuf[index++] = ut.getID();
    }
    const size_t remainingInQueue = maxTrainingQueueSize - trainingQueueSize;
    for (size_t i = 0; i < remainingInQueue; ++i) {
      intBuf[index++] = -1;
    }
  }

  intBuf[index++] = u->getSpaceRemaining();

  // TODO: Refactor and reduce duplicated code with training queues.
  /* Loaded Units */ {
    const size_t maxLoadedUnitsCount = 8;

    const auto &loadedUnits = u->getLoadedUnits();
    const size_t loadedUnitsCount = loadedUnits.size();

    for (const auto &loadedUnit : loadedUnits) {
      intBuf[index++] = loadedUnit->getID();
    }

    const size_t unusedLoadedUnitSlots = maxLoadedUnitsCount - loadedUnitsCount;
    for (size_t i = 0; i < unusedLoadedUnitSlots; ++i) {
      intBuf[index++] = -1;
    }
  }

  return index;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllUnitsData(JNIEnv *env, jobject jObject) {
  int index = 0;
  for (BWAPI::Unit unit : BWAPI::Broodwar->getAllUnits()) {
    index = addUnitDataToBuffer(unit, index);
  }

  jintArray result = env->NewIntArray(index);
  env->SetIntArrayRegion(result, 0, index, intBuf);
  return result;
}

#ifdef OPENBW
// required for the OpenBW version since player->getColor() returns ordinal value instead of 256 color value.
int convertColor(int ordinal) {
  switch (ordinal) {
    case 0:
      return 111;
    case 1:
      return 165;
    case 2:
      return 159;
    case 3:
      return 164;
    case 4:
      return 179;
    case 5:
      return 19;
    case 6:
      return 255;
    case 7:
      return 135;
    case 8:
      return 117;
    case 9:
      return 128;
    case 10:
      return 0;
    case 11:
      return 74;
    default:
      std::cout << "warning: unrecognized color ordinal value." << std::endl;
      return 0;
  }
}
#endif

int addPlayerDataToBuffer(BWAPI::Player &player, int index) {
  intBuf[index++] = player->getID();
  intBuf[index++] = player->getRace();
  intBuf[index++] = player->getStartLocation().x;
  intBuf[index++] = player->getStartLocation().y;
#ifdef OPENBW
  intBuf[index++] = convertColor(player->getColor());
#else
  intBuf[index++] = player->getColor();
#endif
  intBuf[index++] = player->getTextColor();
  intBuf[index++] = player->getType();
  intBuf[index++] = player->getForce()->getID();
  intBuf[index++] = player->isNeutral() ? 1 : 0;
  if (BWAPI::Broodwar->isReplay()) {
    intBuf[index++] = -1;
    intBuf[index++] = -1;
  } else {
    intBuf[index++] = (player->getID() == BWAPI::Broodwar->self()->getID() || player->isAlly(BWAPI::Broodwar->self())) ? 1 : 0;
    intBuf[index++] = (player->getID() != BWAPI::Broodwar->self()->getID() && player->isEnemy(BWAPI::Broodwar->self())) ? 1 : 0;
  }
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
  intBuf[index++] = player->supplyTotal(BWAPI::Races::Zerg);
  intBuf[index++] = player->supplyTotal(BWAPI::Races::Terran);
  intBuf[index++] = player->supplyTotal(BWAPI::Races::Protoss);
  intBuf[index++] = player->supplyUsed(BWAPI::Races::Zerg);
  intBuf[index++] = player->supplyUsed(BWAPI::Races::Terran);
  intBuf[index++] = player->supplyUsed(BWAPI::Races::Protoss);
  intBuf[index++] = player->allUnitCount();
  intBuf[index++] = player->visibleUnitCount();
  intBuf[index++] = player->completedUnitCount();
  intBuf[index++] = player->incompleteUnitCount();
  intBuf[index++] = player->deadUnitCount();
  intBuf[index++] = player->killedUnitCount();

  return index;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllPlayersData(JNIEnv *env, jobject) {
  int index = 0;

  for (BWAPI::Player player : BWAPI::Broodwar->getPlayers()) {
#ifdef OPENBW
    // TODO: Determine if this test has any significance or if it can be removed.
    if (player->getID() != -1) {
      index = addPlayerDataToBuffer(player, index);
    }
#else
    index = addPlayerDataToBuffer(player, index);
#endif
  }

  jintArray result = env->NewIntArray(index);
  env->SetIntArrayRegion(result, 0, index, intBuf);
  return result;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BW_getClientVersion(JNIEnv *, jobject) { return (jint)BWAPI::Broodwar->getClientVersion(); }

JNIEXPORT jstring JNICALL Java_org_openbw_bwapi4j_BW_getPlayerName(JNIEnv *env, jobject, jint playerID) {
  // NewStringUTF can cause issues with unusual characters like Korean symbols
  return env->NewStringUTF(BWAPI::Broodwar->getPlayer(playerID)->getName().c_str());
  /* alternatively, use byte array:
  std::string str = BWAPI::Broodwar->getPlayer(playerID)->getName();
  jbyteArray jbArray = env->NewByteArray(str.length());
  env->SetByteArrayRegion(jbArray, 0, str.length(), (jbyte*)str.c_str());

  return jbArray;
  */
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getResearchStatus(JNIEnv *env, jobject, jint playerID) {
  int index = 0;
  BWAPI::Player p = BWAPI::Broodwar->getPlayer(playerID);

  for (BWAPI::TechType techType : BWAPI::TechTypes::allTechTypes()) {
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
  BWAPI::Player p = BWAPI::Broodwar->getPlayer(playerID);

  for (BWAPI::UpgradeType upgradeType : BWAPI::UpgradeTypes::allUpgradeTypes()) {
    intBuf[index++] = upgradeType.getID();
    intBuf[index++] = p->getUpgradeLevel((upgradeType));
    intBuf[index++] = p->isUpgrading((upgradeType)) ? 1 : 0;
  }

  jintArray result = env->NewIntArray(index);
  env->SetIntArrayRegion(result, 0, index, intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getGameData(JNIEnv *env, jobject) {
  int index = 0;
  intBuf[index++] = BWAPI::Broodwar->getLastError();
  intBuf[index++] = BWAPI::Broodwar->getScreenPosition().x;
  intBuf[index++] = BWAPI::Broodwar->getScreenPosition().y;
#ifdef OPENBW
  intBuf[index++] = BWAPI::Broodwar->getScreenSize().x;
  intBuf[index++] = BWAPI::Broodwar->getScreenSize().y;
#else
  intBuf[index++] = -1;
  intBuf[index++] = -1;
#endif
  intBuf[index++] = BWAPI::Broodwar->getMousePosition().x;
  intBuf[index++] = BWAPI::Broodwar->getMousePosition().y;
  intBuf[index++] = BWAPI::Broodwar->getFrameCount();
  intBuf[index++] = BWAPI::Broodwar->getFPS();
  intBuf[index++] = BWAPI::Broodwar->isLatComEnabled() ? 1 : 0;
  intBuf[index++] = BWAPI::Broodwar->getRemainingLatencyFrames();
  intBuf[index++] = BWAPI::Broodwar->getLatencyFrames();
  intBuf[index++] = BWAPI::Broodwar->getLatency();
  intBuf[index++] = BWAPI::Broodwar->getGameType().getID();
  intBuf[index++] = BWAPI::Broodwar->isReplay();
  intBuf[index++] = BWAPI::Broodwar->isPaused();
  intBuf[index++] = BWAPI::Broodwar->getAPM(false);
  intBuf[index++] = BWAPI::Broodwar->getAPM(true);

  if (BWAPI::Broodwar->isReplay()) {
    for (BWAPI::Player player : BWAPI::Broodwar->getPlayers()) {
      intBuf[index++] = player->getID();
    }
  } else {
    intBuf[index++] = BWAPI::Broodwar->self()->getID();
    intBuf[index++] = BWAPI::Broodwar->enemy()->getID();
  }

  jintArray result = env->NewIntArray(index);
  env->SetIntArrayRegion(result, 0, index, intBuf);

  return result;
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_setOnStartInitializationIsDone_1native(JNIEnv *, jobject, jboolean isDone) {
  onStartInitializationIsDone = isDone;
}
