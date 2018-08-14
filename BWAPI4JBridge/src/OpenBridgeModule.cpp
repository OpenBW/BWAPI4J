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

#include "OpenBridgeModule.h"
#include <jni.h>
#include "Bridge.h"
#include "BridgeEnum.h"
#include "BridgeMap.h"
#include "Logger.h"

namespace OpenBridge {
void OpenBridgeModule::onStart() {
  callbacks.initialize(globalEnv, globalEnv->GetObjectClass(globalBW));

  BridgeEnum bridgeEnum;
  BridgeMap bridgeMap;

  bridgeEnum.initialize();
  bridgeMap.initialize(globalEnv, globalEnv->GetObjectClass(globalBW), globalBW, javaRefs.bwMapClass);

  globalEnv->CallObjectMethod(globalBW, callbacks.preFrameCallback);
  //	globalEnv->CallObjectMethod(globalBW, onStartCallback);
}

void OpenBridgeModule::onEnd(bool isWinner) {}

void OpenBridgeModule::onFrame() {
  callbacks.processEvents(globalEnv, globalBW, BWAPI::Broodwar->getEvents());

  globalEnv->CallObjectMethod(globalBW, callbacks.onFrameCallback);
}

void OpenBridgeModule::onSendText(std::string text) {}

void OpenBridgeModule::onReceiveText(BWAPI::Player player, std::string text) {}

void OpenBridgeModule::onPlayerLeft(BWAPI::Player player) {}

void OpenBridgeModule::onNukeDetect(BWAPI::Position target) {}

void OpenBridgeModule::onUnitDiscover(BWAPI::Unit unit) {}

void OpenBridgeModule::onUnitEvade(BWAPI::Unit unit) {}

void OpenBridgeModule::onUnitShow(BWAPI::Unit unit) {}

void OpenBridgeModule::onUnitHide(BWAPI::Unit unit) {}

void OpenBridgeModule::onUnitCreate(BWAPI::Unit unit) {}

void OpenBridgeModule::onUnitDestroy(BWAPI::Unit unit) {}

void OpenBridgeModule::onUnitMorph(BWAPI::Unit unit) {}

void OpenBridgeModule::onUnitRenegade(BWAPI::Unit unit) {}

void OpenBridgeModule::onSaveGame(std::string gameName) {}

void OpenBridgeModule::onUnitComplete(BWAPI::Unit unit) {}
}  // namespace OpenBridge
