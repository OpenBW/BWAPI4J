// BWAPI4JBridge.cpp : Defines the exported functions for the DLL application.
//

#include "stdafx.h"
#include <BWAPI.h>
#include <BWAPI/Client.h>
#include <thread>
#include <chrono>
#include <jni.h>
#include <stdio.h>
#include "org_openbw_bwapi4j_BW.h"

using namespace BWAPI;

void reconnect() {
	while (!BWAPIClient.connect()) {
		std::this_thread::sleep_for(std::chrono::milliseconds{ 1000 });
	}
}

void flushPrint(const char * text) {
	printf(text);
	fflush(stdout);
}

void println(const char * text) {
	printf(text);
	flushPrint("\n");
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_BW_startGame(JNIEnv *, jobject) {

	println("Connecting to Broodwar...");
	reconnect();
	println("Connection successful, starting match...");
}

JNIEXPORT void JNICALL Java_BWAPI4JBridge_sayHello(JNIEnv *env, jobject thisObj) {
	printf("Hello World!\n");
	return;
}

