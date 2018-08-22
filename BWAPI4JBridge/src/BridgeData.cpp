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

#ifdef _WIN32
#define _USE_MATH_DEFINES
#endif
#include <math.h>

const double BridgeData::RADIANS_TO_DEGREES = 180.0 / M_PI;
const double BridgeData::DECIMAL_PRESERVATION_SCALE = 100.0;

BridgeData::BridgeData() : _index(0) {
  for (size_t i = 0; i < INT_BUF_SIZE; ++i) {
    intBuf[i] = 0;
  }
}

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

double BridgeData::toDegrees(const double radians) { return radians * RADIANS_TO_DEGREES; }

// BWAPI 4.2.0:
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/UnitUpdate.cpp#L206-L212
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/BulletImpl.cpp#L93-L97
double BridgeData::toPreservedBwapiAngle(const double angle) { return (angle * 128.0 / M_PI); }

int BridgeData::toPreservedDouble(const double d) { return static_cast<int>(DECIMAL_PRESERVATION_SCALE * d); }
