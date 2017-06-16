/*
 * So.cpp
 *
 *  Created on: Jun 7, 2017
 *      Author: imp
 */
#include <BWAPI.h>
#ifdef _WIN32
#include <Windows.h>
#define DLLEXPORT __declspec(dllexport)
#else
#define DLLEXPORT
#endif

#include "DummyBotModule.h"

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
  return new DummyBot::DummyBotModule();
}
