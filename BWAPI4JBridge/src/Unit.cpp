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

#include <BWAPI/Client.h>
#include "org_openbw_bwapi4j_unit_Unit.h"

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_Unit_issueCommand(JNIEnv* env, jobject jObj, jint unitID, jint unitCommandTypeID, jint targetUnitID,
                                                                          jint x, jint y, jint extra) {
  //	std::cout << "issuing command " << unitCommandTypeID << " to " << unitID << std::endl;
  BWAPI::Unit unit = BWAPI::Broodwar->getUnit(unitID);
  if (unit != NULL) {
    BWAPI::UnitCommand c = BWAPI::UnitCommand();
    c.unit = unit;
    c.type = unitCommandTypeID;
    c.target = BWAPI::Broodwar->getUnit(targetUnitID);
    c.x = x;
    c.y = y;
    c.extra = extra;
    return c.unit->issueCommand(c);
  }
  return JNI_FALSE;
}
