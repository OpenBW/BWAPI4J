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
#else
#define DLLEXPORT
#endif

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

bool finished = false;
bool onStartInitializationIsDone = false;

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

/**
 * Returns the list of active bullets in the game.
 *
 * Each bullet takes up a fixed number of integer values. Currently: 15.
 */
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllBulletsData(JNIEnv *env, jobject) {
  bridgeData.reset();

  for (BWAPI::Bullet bullet : BWAPI::Broodwar->getBullets()) {
    bridgeData.addFields(bullet);
  }

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllUnitsData(JNIEnv *env, jobject jObject) {
  bridgeData.reset();

  for (BWAPI::Unit unit : BWAPI::Broodwar->getAllUnits()) {
    bridgeData.addFields(unit);
  }

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
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
