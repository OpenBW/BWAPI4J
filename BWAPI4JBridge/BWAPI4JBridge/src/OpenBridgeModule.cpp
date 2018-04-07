#include "OpenBridgeModule.h"
#include "So.h"
#include <jni.h>
#include "BridgeEnum.h"
#include "BridgeMap.h"

using namespace OpenBridge;

jmethodID onStartCallback;
jmethodID onFrameCallback;
jmethodID onEndCallback;
jmethodID onReceiveTextCallback;
jmethodID onPlayerLeftCallback;
jmethodID onNukeDetectCallback;
jmethodID onUnitDiscoverCallback;
jmethodID onUnitEvadeCallback;
jmethodID onUnitDestroyCallback;
jmethodID onUnitMorphCallback;
jmethodID onSendTextCallback;
jmethodID onUnitCreateCallback;
jmethodID onUnitCompleteCallback;
jmethodID onUnitShowCallback;
jmethodID onUnitHideCallback;
jmethodID onUnitRenegadeCallback;
jmethodID onSaveGameCallback;

jmethodID preFrameCallback;

void initializeCallbackMethods() {

	jclass jc = globalEnv->GetObjectClass(globalBW);
	onStartCallback = globalEnv->GetMethodID(jc, "onStart", "()V");
	onEndCallback = globalEnv->GetMethodID(jc, "onEnd", "(Z)V");
	onFrameCallback = globalEnv->GetMethodID(jc, "onFrame", "()V");
	onSendTextCallback = globalEnv->GetMethodID(jc, "onSendText", "(Ljava/lang/String;)V");
	onReceiveTextCallback = globalEnv->GetMethodID(jc, "onReceiveText", "(ILjava/lang/String;)V");
	onPlayerLeftCallback = globalEnv->GetMethodID(jc, "onPlayerLeft", "(I)V");
	onNukeDetectCallback = globalEnv->GetMethodID(jc, "onNukeDetect", "(II)V");
	onUnitDiscoverCallback = globalEnv->GetMethodID(jc, "onUnitDiscover", "(I)V");
	onUnitEvadeCallback = globalEnv->GetMethodID(jc, "onUnitEvade", "(I)V");
	onUnitShowCallback = globalEnv->GetMethodID(jc, "onUnitShow", "(I)V");
	onUnitHideCallback = globalEnv->GetMethodID(jc, "onUnitHide", "(I)V");
	onUnitCreateCallback = globalEnv->GetMethodID(jc, "onUnitCreate", "(I)V");
	onUnitDestroyCallback = globalEnv->GetMethodID(jc, "onUnitDestroy", "(I)V");
	onUnitMorphCallback = globalEnv->GetMethodID(jc, "onUnitMorph", "(I)V");
	onUnitRenegadeCallback = globalEnv->GetMethodID(jc, "onUnitRenegade", "(I)V");
	onSaveGameCallback = globalEnv->GetMethodID(jc, "onSaveGame", "(Ljava/lang/String;)V");
	onUnitCompleteCallback = globalEnv->GetMethodID(jc, "onUnitComplete", "(I)V");

	preFrameCallback = globalEnv->GetMethodID(jc, "preFrame", "()V");
}

// This gets called when the bot starts!
void OpenBridgeModule::onStart()
{
	initializeCallbackMethods();

	BridgeEnum *bridgeEnum = new BridgeEnum();
	BridgeMap *bridgeMap = new BridgeMap();

	bridgeEnum->initialize();
	bridgeMap->initialize(globalEnv, globalEnv->GetObjectClass(globalBW), globalBW, bwMapClass);

	globalEnv->CallObjectMethod(globalBW, preFrameCallback);
//	globalEnv->CallObjectMethod(globalBW, onStartCallback);
}

void OpenBridgeModule::onEnd(bool isWinner)
{
	std::cout << "c winner: " << isWinner << " / " << (isWinner ? "true" : "false") << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onEndCallback, (jboolean)isWinner);
}

void OpenBridgeModule::onFrame()
{
//	std::cout << "onFrame..." << std::endl;

	globalEnv->CallObjectMethod(globalBW, preFrameCallback);

	for (auto &e : BWAPI::Broodwar->getEvents()) {

		switch (e.getType()) {
			case BWAPI::EventType::MatchStart: {
				globalEnv->CallObjectMethod(globalBW, onStartCallback);
			}
				break;
			case BWAPI::EventType::MatchEnd: {
				std::cout << "winner: " << e.isWinner() << " / " << (e.isWinner() ? "true" : "false") << std::endl;
				globalEnv->CallObjectMethod(globalBW, onEndCallback, e.isWinner() ? JNI_TRUE : JNI_FALSE);
			}
				break;
			case BWAPI::EventType::SendText: {
				jstring string = globalEnv->NewStringUTF(e.getText().c_str());
				globalEnv->CallObjectMethod(globalBW, onSendTextCallback, string);
				globalEnv->DeleteLocalRef(string);
			}
				break;
			case BWAPI::EventType::ReceiveText: {
				jstring string = globalEnv->NewStringUTF(e.getText().c_str());
				globalEnv->CallObjectMethod(globalBW, onReceiveTextCallback,
						e.getPlayer()->getID(), string);
				globalEnv->DeleteLocalRef(string);
			}
				break;
			case BWAPI::EventType::PlayerLeft: {
				globalEnv->CallObjectMethod(globalBW, onPlayerLeftCallback,
						e.getPlayer()->getID());
			}
				break;
			case BWAPI::EventType::NukeDetect: {
				globalEnv->CallObjectMethod(globalBW, onNukeDetectCallback,
						e.getPosition().x, e.getPosition().y);
			}
				break;
			case BWAPI::EventType::UnitDiscover: {
				globalEnv->CallObjectMethod(globalBW, onUnitDiscoverCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::UnitEvade: {
				globalEnv->CallObjectMethod(globalBW, onUnitEvadeCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::UnitShow: {
				globalEnv->CallObjectMethod(globalBW, onUnitShowCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::UnitHide: {
				globalEnv->CallObjectMethod(globalBW, onUnitHideCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::UnitCreate: {
				globalEnv->CallObjectMethod(globalBW, onUnitCreateCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::UnitDestroy: {
				globalEnv->CallObjectMethod(globalBW, onUnitDestroyCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::UnitMorph: {
				globalEnv->CallObjectMethod(globalBW, onUnitMorphCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::UnitRenegade: {
				globalEnv->CallObjectMethod(globalBW, onUnitRenegadeCallback,
						e.getUnit()->getID());
			}
				break;
			case BWAPI::EventType::SaveGame: {
				jstring string = globalEnv->NewStringUTF(e.getText().c_str());
				globalEnv->CallObjectMethod(globalBW, onSaveGameCallback, string);
				globalEnv->DeleteLocalRef(string);
			}
				break;
			case BWAPI::EventType::UnitComplete: {
				globalEnv->CallObjectMethod(globalBW, onUnitCompleteCallback,
						e.getUnit()->getID());
			}
				break;
		}
	}

	globalEnv->CallObjectMethod(globalBW, onFrameCallback);
}

void OpenBridgeModule::onSendText(std::string text)
{
//	jstring string = globalEnv->NewStringUTF(text.c_str());
//	globalEnv->CallObjectMethod(globalBW, onSendTextCallback, string);
//	globalEnv->DeleteLocalRef(string);
}

void OpenBridgeModule::onReceiveText(BWAPI::Player player, std::string text)
{
//	jstring string = globalEnv->NewStringUTF(text.c_str());
//	globalEnv->CallObjectMethod(globalBW, onReceiveTextCallback, player->getID(), string);
//	globalEnv->DeleteLocalRef(string);
}

void OpenBridgeModule::onPlayerLeft(BWAPI::Player player)
{
//	globalEnv->CallObjectMethod(globalBW, onPlayerLeftCallback, player->getID());
}

void OpenBridgeModule::onNukeDetect(BWAPI::Position target)
{
//	globalEnv->CallObjectMethod(globalBW, onNukeDetectCallback, target.x, target.y);
}

void OpenBridgeModule::onUnitDiscover(BWAPI::Unit unit)
{
//	std::cout << "onUnitDiscover..." << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onUnitDiscoverCallback, unit->getID());
}

void OpenBridgeModule::onUnitEvade(BWAPI::Unit unit)
{
//	std::cout << "onUnitEvade..." << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onUnitEvadeCallback, unit->getID());
}

void OpenBridgeModule::onUnitShow(BWAPI::Unit unit)
{
//	std::cout << "onUnitShow..." << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onUnitShowCallback, unit->getID());
}

void OpenBridgeModule::onUnitHide(BWAPI::Unit unit)
{
//	std::cout << "onUnitHide..." << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onUnitHideCallback, unit->getID());
}

void OpenBridgeModule::onUnitCreate(BWAPI::Unit unit)
{
//	std::cout << "onUnitCreate..." << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onUnitCreateCallback, unit->getID());
}

void OpenBridgeModule::onUnitDestroy(BWAPI::Unit unit)
{
//	std::cout << "onUnitDestroy..." << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onUnitDestroyCallback, unit->getID());
}

void OpenBridgeModule::onUnitMorph(BWAPI::Unit unit)
{
//	globalEnv->CallObjectMethod(globalBW, onUnitMorphCallback, unit->getID());
}

void OpenBridgeModule::onUnitRenegade(BWAPI::Unit unit)
{
//	globalEnv->CallObjectMethod(globalBW, onUnitRenegadeCallback, unit->getID());
}

void OpenBridgeModule::onSaveGame(std::string gameName) {

//	jstring string = globalEnv->NewStringUTF(gameName.c_str());
//	globalEnv->CallObjectMethod(globalBW, onSaveGameCallback, string);
//	globalEnv->DeleteLocalRef(string);
}

void OpenBridgeModule::onUnitComplete(BWAPI::Unit unit)
{
//	std::cout << "onUnitComplete..." << std::endl;
//	globalEnv->CallObjectMethod(globalBW, onUnitCompleteCallback, unit->getID());
}

