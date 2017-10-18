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
#include "org_openbw_bwapi4j_BW.h"

namespace OpenBridge
{

class OpenBridgeModule : public BWAPI::AIModule
{

public:

	  virtual void onStart() override;
	  virtual void onEnd(bool isWinner) override;
	  virtual void onFrame() override;
	  virtual void onSendText(std::string text) override;
	  virtual void onReceiveText(BWAPI::Player player, std::string text) override;
	  virtual void onPlayerLeft(BWAPI::Player player) override;
	  virtual void onNukeDetect(BWAPI::Position target) override;
	  virtual void onUnitDiscover(BWAPI::Unit unit) override;
	  virtual void onUnitEvade(BWAPI::Unit unit) override;
	  virtual void onUnitShow(BWAPI::Unit unit) override;
	  virtual void onUnitHide(BWAPI::Unit unit) override;
	  virtual void onUnitCreate(BWAPI::Unit unit) override;
	  virtual void onUnitDestroy(BWAPI::Unit unit) override;
	  virtual void onUnitMorph(BWAPI::Unit unit) override;
	  virtual void onUnitRenegade(BWAPI::Unit unit) override;
	  virtual void onSaveGame(std::string gameName) override;
	  virtual void onUnitComplete(BWAPI::Unit unit) override;
};

}
