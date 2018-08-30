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

#pragma once

#include <BWAPI.h>
#include <jni.h>

class DataBuffer {
 public:
  static const double RADIANS_TO_DEGREES;
  static const double DECIMAL_PRESERVATION_SCALE;

  static const size_t INT_BUF_SIZE = 5000000U;
  jint intBuf[INT_BUF_SIZE];

  static const int NO_VALUE;

  DataBuffer();

  void reset();

  int getIndex() const;

  void add(const int val);
  void add(const double val);
  void add(const size_t val);
  void add(const bool b);
  void add(const char ch);

  void addId(const BWAPI::UnitType &unitType);
  void addId(const BWAPI::Unit &unit);
  void addId(const BWAPI::Player &player);
  void addId(const BWAPI::Bullet &bullet);
  void addId(const BWAPI::BulletType &bulletType);
  void addId(const BWAPI::UnitCommand &unitCommand);
  void addId(const BWAPI::TechType &techType);
  void addId(const BWAPI::UpgradeType &upgradeType);
  void addId(const BWAPI::Order &order);
  void addId(const BWAPI::Race &race);
  void addId(const BWAPI::Color color);
  void addId(const BWAPI::PlayerType &playerType);
  void addId(const BWAPI::Force &force);
  void addId(const BWAPI::GameType &gameType);

  void addIds(const BWAPI::Playerset &playerset);

  void addFields(const BWAPI::TilePosition &tilePosition);
  void addFields(const BWAPI::WalkPosition &walkPosition);
  void addFields(const BWAPI::Position &position);
  void addFields(const BWAPI::Bullet &bullet);
  void addFields(const BWAPI::Unit &unit);
  void addFields(const BWAPI::Player &player);

 private:
  int _index;

  static double toDegrees(const double radians);
  static double toPreservedBwapiAngle(const double angle);
  static int toPreservedDouble(const double d);
  static int convertColor(const int ordinal);
};
