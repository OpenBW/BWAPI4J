#include "OpenBridgeModule.h"

using namespace OpenBridge;

int frame;

// This gets called when the bot starts!
void OpenBridgeModule::onStart()
{

	frame = 0;
}

void OpenBridgeModule::onEnd(bool isWinner)
{

}

void OpenBridgeModule::onFrame()
{

	std::cout << "frame: " << frame++ << std::endl;;
}

void OpenBridgeModule::onUnitDestroy(BWAPI::Unit unit)
{

}

void OpenBridgeModule::onUnitMorph(BWAPI::Unit unit)
{

}

void OpenBridgeModule::onSendText(std::string text)
{

}

void OpenBridgeModule::onUnitCreate(BWAPI::Unit unit)
{

}

void OpenBridgeModule::onUnitComplete(BWAPI::Unit unit)
{

}

void OpenBridgeModule::onUnitShow(BWAPI::Unit unit)
{

}

void OpenBridgeModule::onUnitHide(BWAPI::Unit unit)
{

}

void OpenBridgeModule::onUnitRenegade(BWAPI::Unit unit)
{

}
