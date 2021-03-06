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

#include "BridgeEnum.h"
#include "BridgeMap.h"
#include "Globals.h"
#include "Logger.h"

#ifdef OPENBW
#ifdef _WIN32
#include <Windows.h>
#define DLLEXPORT __declspec(dllexport)
BOOL APIENTRY DllMain(HANDLE hModule, DWORD ul_reason_for_call, LPVOID lpReserved) { return TRUE; }
#else
#define DLLEXPORT
#endif

extern "C" DLLEXPORT void gameInit(BWAPI::Game *game) { BWAPI::BroodwarPtr = game; }
extern "C" DLLEXPORT BWAPI::AIModule *newAIModule() { return new OpenBridge::OpenBridgeModule(); }
#endif

namespace OpenBridge {
void OpenBridgeModule::onStart() {
  Bridge::Globals::initializeGame(Bridge::Globals::env, Bridge::Globals::bw);

  Bridge::Globals::env->CallObjectMethod(Bridge::Globals::bw, Bridge::Globals::callbacks.preFrameCallback);
  //	globalEnv->CallObjectMethod(globalBW, onStartCallback);
}

void OpenBridgeModule::onEnd(bool isWinner) {}

void OpenBridgeModule::onFrame() {
  Bridge::Globals::callbacks.processEvents(Bridge::Globals::env, Bridge::Globals::bw, BWAPI::Broodwar->getEvents());

  Bridge::Globals::env->CallObjectMethod(Bridge::Globals::bw, Bridge::Globals::callbacks.onFrameCallback);
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
