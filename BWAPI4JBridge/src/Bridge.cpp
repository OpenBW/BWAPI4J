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

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv *env, jobject bwObject, jobject bw) {
  globalEnv = env;
  globalBW = bw;

#ifndef OPENBW
  env->EnsureLocalCapacity(512);
  jclass jc = env->GetObjectClass(bw);
#endif

  javaRefs.initialize(env);

#ifdef OPENBW
  std::thread mainThread([] {
    BW::sacrificeThreadForUI([] {
      while (!finished) std::this_thread::sleep_for(std::chrono::seconds(5));
    });
  });

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

    bridgeEnum.initialize(env);
    bridgeMap.initialize(env, env->GetObjectClass(bwObject), bw, javaRefs.bwMapClass);

    if (false && BWAPI::Broodwar->isReplay()) {  // right now don't treat replays any different

    } else {
      LOGGER("Calling onStart callback...");
      env->CallObjectMethod(bw, env->GetMethodID(jc, "onStart", "()V"));
      LOGGER("Calling onStart callback... done");

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

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllBulletsData(JNIEnv *env, jobject) {
  bridgeData.reset();

  for (BWAPI::Bullet bullet : BWAPI::Broodwar->getBullets()) {
    bridgeData.addFields(bullet);
  }

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllUnitsData(JNIEnv *env, jobject) {
  bridgeData.reset();

  for (const auto &unit : BWAPI::Broodwar->getAllUnits()) {
    bridgeData.addFields(unit);
  }

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllPlayersData(JNIEnv *env, jobject) {
  bridgeData.reset();

  for (const auto &player : BWAPI::Broodwar->getPlayers()) {
#ifdef OPENBW
    // TODO: Determine if this test has any significance or if it can be removed.
    if (player->getID() != -1) {
      bridgeData.addFields(player);
    }
#else
    bridgeData.addFields(player);
#endif
  }

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
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

// TODO: Refactor to be one call for all players.
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getPlayerExtra(JNIEnv *env, jobject, jint playerID) {
  bridgeData.reset();

  const auto &player = BWAPI::Broodwar->getPlayer(playerID);

  const auto &upgradeTypes = BWAPI::UpgradeTypes::allUpgradeTypes();
  bridgeData.add(upgradeTypes.size());
  for (const auto &upgradeType : upgradeTypes) {
    bridgeData.addId(upgradeType);
    bridgeData.add(player->getUpgradeLevel(upgradeType));
    bridgeData.add(player->isUpgrading(upgradeType));
  }

  const auto &techTypes = BWAPI::TechTypes::allTechTypes();
  bridgeData.add(techTypes.size());
  for (const auto &techType : techTypes) {
    bridgeData.addId(techType);
    bridgeData.add(player->hasResearched(techType));
    bridgeData.add(player->isResearching(techType));
  }

  if (BWAPI::Broodwar->isReplay()) {
    bridgeData.add(BridgeData::NO_VALUE);
    bridgeData.add(BridgeData::NO_VALUE);
  } else {
    bridgeData.add(player->getID() == BWAPI::Broodwar->self()->getID() || player->isAlly(BWAPI::Broodwar->self()));
    bridgeData.add(player->getID() != BWAPI::Broodwar->self()->getID() && player->isEnemy(BWAPI::Broodwar->self()));
  }


  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getGameData(JNIEnv *env, jobject) {
  bridgeData.reset();

  bridgeData.addFields(BWAPI::Broodwar->getScreenPosition());

#ifdef OPENBW
  bridgeData.addFields(BWAPI::Broodwar->getScreenSize());
#else
  bridgeData.add(BridgeData::NO_VALUE);
  bridgeData.add(BridgeData::NO_VALUE);
#endif

  bridgeData.addFields(BWAPI::Broodwar->getMousePosition());
  bridgeData.add(BWAPI::Broodwar->getFrameCount());
  bridgeData.add(BWAPI::Broodwar->getFPS());
  bridgeData.add(BWAPI::Broodwar->getAverageFPS());
  bridgeData.add(BWAPI::Broodwar->isLatComEnabled());
  bridgeData.add(BWAPI::Broodwar->getRemainingLatencyFrames());
  bridgeData.add(BWAPI::Broodwar->getLatencyFrames());
  bridgeData.add(BWAPI::Broodwar->getLatency());
  bridgeData.addId(BWAPI::Broodwar->getGameType());
  bridgeData.add(BWAPI::Broodwar->isReplay());
  bridgeData.add(BWAPI::Broodwar->isPaused());
  bridgeData.add(BWAPI::Broodwar->getAPM(false));
  bridgeData.add(BWAPI::Broodwar->getAPM(true));
  bridgeData.addId(BWAPI::Broodwar->self());
  bridgeData.addId(BWAPI::Broodwar->enemy());

  jintArray result = env->NewIntArray(bridgeData.getIndex());
  env->SetIntArrayRegion(result, 0, bridgeData.getIndex(), bridgeData.intBuf);
  return result;
}