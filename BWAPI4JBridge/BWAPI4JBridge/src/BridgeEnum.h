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

/*
 * BridgeEnum.h
 *
 *  Created on: Jul 18, 2017
 *      Author: imp
 */

#ifndef BRIDGEENUM_H_
#define BRIDGEENUM_H_

class BridgeEnum {
 public:
  BridgeEnum();
  virtual ~BridgeEnum();
  void initialize();

 private:
  void createUpgradeTypeEnum();
  void createTechTypeEnum();
  void createWeaponTypeEnum();
  void createUnitTypeEnum();
  void createRaceEnum();
};

#endif /* BRIDGEENUM_H_ */
