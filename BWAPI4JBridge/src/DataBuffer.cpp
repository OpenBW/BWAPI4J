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

#include "DataBuffer.h"

#ifdef _WIN32
#define _USE_MATH_DEFINES
#endif
#include <math.h>

#include "Logger.h"
#include "org_openbw_bwapi4j_unit_UnitImplBridge.hpp"
#include "org_openbw_bwapi4j_PlayerBridge.hpp"
#include "org_openbw_bwapi4j_BulletBridge.hpp"

// "Static" types
#include "org_openbw_bwapi4j_type_TechTypeBridge.hpp"
#include "org_openbw_bwapi4j_type_UnitTypeBridge.hpp"
#include "org_openbw_bwapi4j_type_UpgradeTypeBridge.hpp"
#include "org_openbw_bwapi4j_type_WeaponTypeBridge.hpp"

const double DataBuffer::RADIANS_TO_DEGREES = 180.0 / M_PI;
const double DataBuffer::DECIMAL_PRESERVATION_SCALE = 100.0;
const int DataBuffer::NO_VALUE = -1;

DataBuffer::DataBuffer() : _index(0) {
  for (size_t i = 0; i < INT_BUF_SIZE; ++i) {
    intBuf[i] = 0;
  }
}

void DataBuffer::reset() { _index = 0; }

void DataBuffer::add(const int val) { intBuf[_index++] = val; }

void DataBuffer::add(const double val) {
  const int preservedDouble = toPreservedDouble(val);
  add(preservedDouble);
}

void DataBuffer::add(const size_t val) { add(int(val)); }

void DataBuffer::add(const bool b) { add(b ? 1 : 0); }

void DataBuffer::add(const char ch) { add(int(ch)); }

int DataBuffer::getIndex() const { return _index; }

void DataBuffer::addFields(const BWAPI::TilePosition &tilePosition) {
  add(tilePosition.x);
  add(tilePosition.y);
}

void DataBuffer::addFields(const BWAPI::WalkPosition &walkPosition) {
  add(walkPosition.x);
  add(walkPosition.y);
}

void DataBuffer::addFields(const BWAPI::Position &position) {
  add(position.x);
  add(position.y);
}

void DataBuffer::addId(const BWAPI::UnitType &unitType) { add(unitType.getID()); }

void DataBuffer::addId(const BWAPI::Unit &unit) { add(unit ? unit->getID() : NO_VALUE); }

void DataBuffer::addId(const BWAPI::Player &player) { add(player ? player->getID() : NO_VALUE); }

void DataBuffer::addId(const BWAPI::Bullet &bullet) { add(bullet ? bullet->getID() : NO_VALUE); }

void DataBuffer::addId(const BWAPI::BulletType &bulletType) { add(bulletType.getID()); }

void DataBuffer::addId(const BWAPI::UnitCommand &unitCommand) { add(unitCommand.getType().getID()); }

void DataBuffer::addId(const BWAPI::TechType &techType) { add(techType.getID()); }

void DataBuffer::addId(const BWAPI::UpgradeType &upgradeType) { add(upgradeType.getID()); }

void DataBuffer::addId(const BWAPI::Order &order) { add(order.getID()); }

void DataBuffer::addId(const BWAPI::Race &race) { add(race.getID()); }

void DataBuffer::addId(const BWAPI::Color color) { add(convertColor(color)); }

void DataBuffer::addId(const BWAPI::PlayerType &playerType) { add(playerType.getID()); }

void DataBuffer::addId(const BWAPI::Force &force) { add(force->getID()); }

void DataBuffer::addId(const BWAPI::GameType &gameType) { add(gameType.getID()); }

void DataBuffer::addId(const BWAPI::WeaponType &weaponType) { add(weaponType.getID()); }

void DataBuffer::addId(const BWAPI::UnitSizeType &unitSizeType) { add(unitSizeType.getID()); }

void DataBuffer::addId(const BWAPI::DamageType &damageType) { add(damageType.getID()); }

void DataBuffer::addId(const BWAPI::ExplosionType &explosionType) { add(explosionType.getID()); }

void DataBuffer::addIds(const BWAPI::Playerset &playerset) {
  for (const auto &player : playerset) {
    addId(player);
  }
}

double DataBuffer::toDegrees(const double radians) { return radians * RADIANS_TO_DEGREES; }

// BWAPI 4.2.0:
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/UnitUpdate.cpp#L206-L212
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/BulletImpl.cpp#L93-L97
double DataBuffer::toPreservedBwapiAngle(const double angle) { return (angle * 128.0 / M_PI); }

int DataBuffer::toPreservedDouble(const double d) { return static_cast<int>(DECIMAL_PRESERVATION_SCALE * d); }

// required for the OpenBW version since player->getColor() returns ordinal value instead of 256 color value.
int DataBuffer::convertColor(const int ordinal) {
#ifdef OPENBW
  switch (ordinal) {
    case 0:
      return 111;
    case 1:
      return 165;
    case 2:
      return 159;
    case 3:
      return 164;
    case 4:
      return 179;
    case 5:
      return 19;
    case 6:
      return 255;
    case 7:
      return 135;
    case 8:
      return 117;
    case 9:
      return 128;
    case 10:
      return 0;
    case 11:
      return 74;
    default:
      LOGGER("warning: unrecognized color ordinal value.");
      return 0;
  }
#else
  return ordinal;
#endif
}
