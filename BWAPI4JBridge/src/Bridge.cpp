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
#include "BWAPILauncher.h"
#include "OpenBridgeModule.h"
#else
#include <BWAPI/Client.h>
#endif

#include "JniBwem.h"

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_createUnit_1native(JNIEnv *, jobject, jint playerID, jint unitType, jint posX, jint posY) {
#ifdef OPENBW
  BWAPI::Broodwar->createUnit(BWAPI::Broodwar->getPlayer(playerID), (BWAPI::UnitType)unitType, BWAPI::Position(posX, posY));
#endif
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_killUnit_1native(JNIEnv *, jobject, jint unitID) {
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

#ifndef OPENBW
void reconnect() {
  while (!BWAPI::BWAPIClient.connect()) {
    std::this_thread::sleep_for(std::chrono::milliseconds{1000});
  }
}
#endif

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame_1native(JNIEnv *env, jobject, jobject bw) {
  Bridge::Globals::initialize(env, bw);

#ifdef OPENBW
  Bridge::BWAPILauncher::run(env);
#else
  env->EnsureLocalCapacity(512);

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

    Bridge::Globals::initializeGame(env, bw);

    LOGGER(fmt::format("Initial random seed: {}", BWAPI::Broodwar->getRandomSeed()));

    if (false && BWAPI::Broodwar->isReplay()) {  // right now don't treat replays any different

    } else {
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

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllBulletsData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  for (BWAPI::Bullet bullet : BWAPI::Broodwar->getBullets()) {
    Bridge::Globals::dataBuffer.addFields(bullet);
  }

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllUnitsData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  for (const auto &unit : BWAPI::Broodwar->getAllUnits()) {
    Bridge::Globals::dataBuffer.addFields(unit);
  }

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getAllPlayersData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

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

  BUFFER_RETURN;
}

JNIEXPORT jint JNICALL Java_org_openbw_bwapi4j_BW_getClientVersion(JNIEnv *, jobject) { return (jint)BWAPI::Broodwar->getClientVersion(); }

JNIEXPORT jstring JNICALL Java_org_openbw_bwapi4j_BW_getPlayerName_1native(JNIEnv *env, jobject, jint playerID) {
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
JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getPlayerAdditionalData_1native(JNIEnv *env, jobject, jint playerID) {
  BUFFER_SETUP;

  const auto &player = BWAPI::Broodwar->getPlayer(playerID);

  const auto &upgradeTypes = BWAPI::UpgradeTypes::allUpgradeTypes();
  Bridge::Globals::dataBuffer.add(upgradeTypes.size());
  for (const auto &upgradeType : upgradeTypes) {
    Bridge::Globals::dataBuffer.addId(upgradeType);
    Bridge::Globals::dataBuffer.add(player->getUpgradeLevel(upgradeType));
    Bridge::Globals::dataBuffer.add(player->isUpgrading(upgradeType));
  }

  const auto &techTypes = BWAPI::TechTypes::allTechTypes();
  Bridge::Globals::dataBuffer.add(techTypes.size());
  for (const auto &techType : techTypes) {
    Bridge::Globals::dataBuffer.addId(techType);
    Bridge::Globals::dataBuffer.add(player->hasResearched(techType));
    Bridge::Globals::dataBuffer.add(player->isResearching(techType));
  }

  if (BWAPI::Broodwar->isReplay()) {
    Bridge::Globals::dataBuffer.add(DataBuffer::NO_VALUE);
    Bridge::Globals::dataBuffer.add(DataBuffer::NO_VALUE);
  } else {
    Bridge::Globals::dataBuffer.add(player->getID() == BWAPI::Broodwar->self()->getID() || player->isAlly(BWAPI::Broodwar->self()));
    Bridge::Globals::dataBuffer.add(player->getID() != BWAPI::Broodwar->self()->getID() && player->isEnemy(BWAPI::Broodwar->self()));
  }

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getGameData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  Bridge::Globals::dataBuffer.addFields(BWAPI::Broodwar->getScreenPosition());

#ifdef OPENBW
  Bridge::Globals::dataBuffer.addFields(BWAPI::Broodwar->getScreenSize());
#else
  Bridge::Globals::dataBuffer.add(DataBuffer::NO_VALUE);
  Bridge::Globals::dataBuffer.add(DataBuffer::NO_VALUE);
#endif

  Bridge::Globals::dataBuffer.addFields(BWAPI::Broodwar->getMousePosition());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getFrameCount());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getReplayFrameCount());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getFPS());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getAverageFPS());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->isLatComEnabled());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getRemainingLatencyFrames());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getRemainingLatencyTime());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getLatencyFrames());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getLatencyTime());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getLatency());
  Bridge::Globals::dataBuffer.addId(BWAPI::Broodwar->getGameType());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->isReplay());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->isPaused());
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getAPM(false));
  Bridge::Globals::dataBuffer.add(BWAPI::Broodwar->getAPM(true));
  Bridge::Globals::dataBuffer.addId(BWAPI::Broodwar->self());
  Bridge::Globals::dataBuffer.addId(BWAPI::Broodwar->enemy());
  Bridge::Globals::dataBuffer.addId(BWAPI::Broodwar->neutral());

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getUpgradeTypesData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  BridgeEnum enums;
  enums.addUpgradeTypeEnumsTo(Bridge::Globals::dataBuffer);

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getWeaponTypesData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  BridgeEnum enums;
  enums.addWeaponTypeEnumsTo(Bridge::Globals::dataBuffer);

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getTechTypesData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  BridgeEnum enums;
  enums.addTechTypeEnumsTo(Bridge::Globals::dataBuffer);

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getUnitTypesData_1native(JNIEnv *env, jobject) {
  BUFFER_SETUP;

  BridgeEnum enums;
  enums.addUnitTypeEnumsTo(Bridge::Globals::dataBuffer);

  BUFFER_RETURN;
}

JNIEXPORT jintArray JNICALL Java_org_openbw_bwapi4j_BW_getStaticMinerals_1native(JNIEnv *env, jobject) {
	BUFFER_SETUP;

	for (const auto &staticMineral : BWAPI::Broodwar->getStaticMinerals()) {
		Bridge::Globals::dataBuffer.addId(staticMineral);
	}

	BUFFER_RETURN;
}