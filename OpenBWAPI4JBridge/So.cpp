/*
 * So.cpp
 *
 *  Created on: Jun 7, 2017
 *      Author: imp
 */
#include <BWAPI.h>
#include "BWAPI/GameImpl.h"
#include "BW/BWData.h"
#include <cstdio>
#ifdef _WIN32
#include <Windows.h>
#define DLLEXPORT __declspec(dllexport)
#else
#define DLLEXPORT
#endif

#include "OpenBridgeModule.h"
#include "org_openbw_bwapi4j_BW.h"

extern "C" DLLEXPORT void gameInit(BWAPI::Game* game) { BWAPI::BroodwarPtr = game; }
#ifdef _WIN32
BOOL APIENTRY DllMain( HANDLE hModule, DWORD ul_reason_for_call, LPVOID lpReserved )
{
  switch (ul_reason_for_call)
  {
  case DLL_PROCESS_ATTACH:
    break;
  case DLL_PROCESS_DETACH:
    break;
  }
  return TRUE;
}
#endif

extern "C" DLLEXPORT BWAPI::AIModule* newAIModule()
{
  return new OpenBridge::OpenBridgeModule();
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv *env, jobject caller, jobject bw) {

	try {

	    BW::GameOwner gameOwner;

	    gameOwner.setPrintTextCallback([](const char* str) {
	      std::string s;
	      while (*str) {
	        char c = *str++;
	        if ((unsigned)c >= 0x20 || c == 9 || c == 10 || c == 13) s += c;
	      }
	      printf("%s\n", s.c_str());
	    });

	    BWAPI::BroodwarImpl_handle h(gameOwner.getGame());

	    do {
	      h->autoMenuManager.startGame();

	      while (!h->bwgame.gameOver()) {
	        h->update();
	        h->bwgame.nextFrame();

	        if (!h->externalModuleConnected) {
	          printf("No module loaded, exiting\n");
	          return;
	        }
	      }
	      h->onGameEnd();
	      h->bwgame.leaveGame();
	    } while (!h->bwgame.gameClosed() && h->autoMenuManager.autoMenuRestartGame != "" && h->autoMenuManager.autoMenuRestartGame != "OFF");
	  } catch (const std::exception& e) {
	    printf("Error: %s\n", e.what());
	  }
}
