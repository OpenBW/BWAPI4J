/*
 * DummyBotModule.h
 *
 *  Created on: Jun 7, 2017
 *      Author: imp
 */

#pragma once

#include <BWAPI.h>
#include <iostream>
#include <fstream>

namespace OpenBridge
{

class OpenBridgeModule : public BWAPI::AIModule
{

public:

	void	onStart();
	void	onFrame();
	void	onEnd(bool isWinner);
	void	onUnitDestroy(BWAPI::Unit unit);
	void	onUnitMorph(BWAPI::Unit unit);
	void	onSendText(std::string text);
	void	onUnitCreate(BWAPI::Unit unit);
	void	onUnitComplete(BWAPI::Unit unit);
	void	onUnitShow(BWAPI::Unit unit);
	void	onUnitHide(BWAPI::Unit unit);
	void	onUnitRenegade(BWAPI::Unit unit);
};

}
