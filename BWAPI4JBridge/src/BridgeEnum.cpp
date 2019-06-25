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

/* TODO: Add dataBuffer parameter to each of these functions' signature instead of directing mutating the global buffer. */

#include "BridgeEnum.h"
#include "DataBuffer.h"
#include "Globals.h"

#include <BWAPI.h>

#include "Logger.h"

void BridgeEnum::addUpgradeTypeEnums() {
  LOGGER("Reading upgrade types...");

  for (BWAPI::UpgradeType upgradeType : BWAPI::UpgradeTypes::allUpgradeTypes()) {
    Bridge::Globals::dataBuffer.addFields(upgradeType);
  }

  LOGGER("Reading upgrade types... done");
}

void BridgeEnum::addTechTypeEnums() {
  LOGGER("Reading tech types...");

  for (BWAPI::TechType techType : BWAPI::TechTypes::allTechTypes()) {
    Bridge::Globals::dataBuffer.addFields(techType);
  }

  LOGGER("Reading tech types... done");
}

void BridgeEnum::addWeaponTypeEnums() {
  LOGGER("Reading weapon types...");

  for (BWAPI::WeaponType weaponType : BWAPI::WeaponTypes::allWeaponTypes()) {
    Bridge::Globals::dataBuffer.addFields(weaponType);
  }

  LOGGER("Reading weapon types... done");
}

void BridgeEnum::addUnitTypeEnums() {
  LOGGER("Reading unit types...");

  for (BWAPI::UnitType unitType : BWAPI::UnitTypes::allUnitTypes()) {
    Bridge::Globals::dataBuffer.addFields(unitType);
  }

  LOGGER("Reading unit types... done");
}
