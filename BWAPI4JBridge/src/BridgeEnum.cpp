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

#include "BridgeEnum.h"
#include "DataBuffer.h"
#include "Globals.h"

#include <BWAPI.h>

#include "Logger.h"

void BridgeEnum::addUpgradeTypeEnumsTo(DataBuffer &dataBuffer) {
  LOGGER("Reading upgrade types...");

  for (BWAPI::UpgradeType upgradeType : BWAPI::UpgradeTypes::allUpgradeTypes()) {
    dataBuffer.addFields(upgradeType);
  }

  LOGGER("Reading upgrade types... done");
}

void BridgeEnum::addTechTypeEnumsTo(DataBuffer &dataBuffer) {
  LOGGER("Reading tech types...");

  for (BWAPI::TechType techType : BWAPI::TechTypes::allTechTypes()) {
    dataBuffer.addFields(techType);
  }

  LOGGER("Reading tech types... done");
}

void BridgeEnum::addWeaponTypeEnumsTo(DataBuffer &dataBuffer) {
  LOGGER("Reading weapon types...");

  for (BWAPI::WeaponType weaponType : BWAPI::WeaponTypes::allWeaponTypes()) {
    dataBuffer.addFields(weaponType);
  }

  LOGGER("Reading weapon types... done");
}

void BridgeEnum::addUnitTypeEnumsTo(DataBuffer &dataBuffer) {
  LOGGER("Reading unit types...");

  for (BWAPI::UnitType unitType : BWAPI::UnitTypes::allUnitTypes()) {
    dataBuffer.addFields(unitType);
  }

  LOGGER("Reading unit types... done");
}
