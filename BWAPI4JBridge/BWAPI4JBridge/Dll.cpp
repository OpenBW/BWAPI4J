// dllmain.cpp : Defines the entry point for the DLL application.

#include <Windows.h>

#include <BWAPI.h>

extern "C" __declspec(dllexport) void gameInit(BWAPI::Game* game) {
	BWAPI::BroodwarPtr = game; 
}

BOOL APIENTRY DllMain(HANDLE hModule, DWORD ul_reason_for_call, LPVOID lpReserved) {
	switch (ul_reason_for_call) {
		case DLL_PROCESS_ATTACH:
		case DLL_THREAD_ATTACH:
		case DLL_THREAD_DETACH:
		case DLL_PROCESS_DETACH:
		break;
	}
	return TRUE;
}
