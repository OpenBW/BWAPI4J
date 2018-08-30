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

#include <stdio.h>

#include <BWAPI.h>
#include <jni.h>

#include "BridgeEnum.h"
#include "BridgeMap.h"
#include "Globals.h"
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

namespace Bridge {
namespace Globals {
bool finished = false;
}  // namespace Globals
}  // namespace Bridge

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
  Bridge::Globals::finished = true;
  LOGGER("Exiting after current game.");
#endif
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv *env, jobject, jobject bw) {
  Bridge::Globals::globalEnv = env;
  Bridge::Globals::globalBW = bw;

#ifndef OPENBW
  env->EnsureLocalCapacity(512);
#endif

  Bridge::Globals::javaRefs.initialize(env);

#ifdef OPENBW
  std::thread mainThread([] {
    BW::sacrificeThreadForUI([] {
      while (!Bridge::Globals::finished) std::this_thread::sleep_for(std::chrono::seconds(5));
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
  Bridge::Globals::finished = true;
#else
  BridgeEnum bridgeEnum;
  BridgeMap bridgeMap;

  LOGGER("Connecting to Broodwar...");
  reconnect();

  LOGGER("Connection successful, starting match...");

  while (!Bridge::Globals::finished) {
    // TODO: Determine if we need all these different "connect/reconnect" calls. Can it not be just one?
    while (!BWAPI::Broodwar->isInGame()) {
      BWAPI::BWAPIClient.update();
      if (!BWAPI::BWAPIClient.isConnected()) {
        LOGGER("Reconnecting...");
        reconnect();
      }
    }
    LOGGER(fmt::format("Client version: {}", BWAPI::Broodwar->getClientVersion()));

    bridgeEnum.initialize(env, Bridge::Globals::javaRefs);
    bridgeMap.initialize(env, bw, Bridge::Globals::javaRefs);

    if (false && BWAPI::Broodwar->isReplay()) {  // right now don't treat replays any different

    } else {
      Bridge::Globals::callbacks.initialize(env, Bridge::Globals::javaRefs.bwClass);

      LOGGER("Calling onStart callback...");
      env->CallObjectMethod(bw, Bridge::Globals::callbacks.onStartCallback);
      LOGGER("Calling onStart callback... done");

      LOGGER("Entering in-game event loop...");

      while (BWAPI::Broodwar->isInGame()) {
        Bridge::Globals::callbacks.processEvents(env, bw, BWAPI::Broodwar->getEvents());

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
  Bridge::Globals::dataBuffer.reset();

  for (BWAPI::Bullet bullet : BWAPI::Broodwar->getBullets()) {
    Bridge::Globals::dataBuffer.addFields(bullet);
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllUnitsData(JNIEnv *env, jobject) {
  Bridge::Globals::dataBuffer.reset();

  for (const auto &unit : BWAPI::Broodwar->getAllUnits()) {
    Bridge::Globals::dataBuffer.addFields(unit);
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllPlayersData(JNIEnv *env, jobject) {
  Bridge::Globals::dataBuffer.reset();

  for (const auto &player : BWAPI::Broodwar->getPlayers()) {
#ifdef OPENBW
    // TODO: Determine if this test has any significance or if it can be removed.
    if (player->getID() != -1) {
      Bridge::Globals::dataBuffer.addFields(player);
    }
#else
    Bridge::Globals::dataBuffer.addFields(player);
#endif
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
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

// TODO: Refactor to be one call for all players. Possibly also merge with "getUpgradeStatus".
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getResearchStatus(JNIEnv *env, jobject, jint playerID) {
  Bridge::Globals::dataBuffer.reset();

  const auto &player = BWAPI::Broodwar->getPlayer(playerID);

  for (const auto &techType : BWAPI::TechTypes::allTechTypes()) {
    Bridge::Globals::dataBuffer.addId(techType);
    Bridge::Globals::dataBuffer.add(player->hasResearched(techType));
    Bridge::Globals::dataBuffer.add(player->isResearching(techType));
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

// TODO: Refactor to be one call for all players. Possibly also merge with "getResearchStatus".
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getUpgradeStatus(JNIEnv *env, jobject, jint playerID) {
  Bridge::Globals::dataBuffer.reset();

  const auto &player = BWAPI::Broodwar->getPlayer(playerID);

  for (const auto &upgradeType : BWAPI::UpgradeTypes::allUpgradeTypes()) {
    Bridge::Globals::dataBuffer.addId(upgradeType);
    Bridge::Globals::dataBuffer.add(player->getUpgradeLevel(upgradeType));
    Bridge::Globals::dataBuffer.add(player->isUpgrading(upgradeType));
  }

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getGameData(JNIEnv *env, jobject) {
  Bridge::Globals::dataBuffer.reset();

  Bridge::Globals::dataBuffer.addFields(BWAPI::Broodwar->getScreenPosition());

#ifdef OPENBW
  Bridge::Globals::dataBuffer.addFields(BWAPI::Broodwar->getScreenSize());
#else
  Bridge::Globals::dataBuffer.add(DataBuffer::NO_VALUE);
  Bridge::Globals::dataBuffer.add(DataBuffer::NO_VALUE);
#endif

  Bridge::Globals::dataBuffer.addFields(BWAPI::Broodwar->getMousePosition());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getFrameCount());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getFPS());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getAverageFPS());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->isLatComEnabled());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getRemainingLatencyFrames());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getLatencyFrames());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getLatency());
  Bridge::Globals::dataBuffer.addId(BWAPI::Broodwar->getGameType());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->isReplay());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->isPaused());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getAPM(false));
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getAPM(true));
  Bridge::Globals::dataBuffer.addId(BWAPI::Broodwar->self());
  Bridge::Globals::dataBuffer.addId(BWAPI::Broodwar->enemy());

  jintArray result = env->NewIntArray(Bridge::Globals::dataBuffer.getIndex());
  env->SetIntArrayRegion(result, 0, Bridge::Globals::dataBuffer.getIndex(), Bridge::Globals::dataBuffer.intBuf);
  return result;
}