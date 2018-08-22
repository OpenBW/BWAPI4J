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

#include "BridgeData.h"

BridgeData::BridgeData() : _index(0) {}

void BridgeData::reset() { _index = 0; }

void BridgeData::add(const int val) { intBuf[_index++] = val; }

int BridgeData::getIndex() const { return _index; }

void BridgeData::add(const BWAPI::TilePosition &tilePosition) {
  add(tilePosition.x);
  add(tilePosition.y);
}

void BridgeData::add(const BWAPI::WalkPosition &walkPosition) {
  add(walkPosition.x);
  add(walkPosition.y);
}

void BridgeData::add(const BWAPI::Position &position) {
  add(position.x);
  add(position.y);
}

void BridgeData::add(const BWAPI::UnitType &unitType) { add(unitType.getID()); }

void BridgeData::add(const BWAPI::Unit &unit) { add(unit ? unit->getID() : -1); }

void BridgeData::add(const BWAPI::Player &player) { add(player ? player->getID() : -1); }
