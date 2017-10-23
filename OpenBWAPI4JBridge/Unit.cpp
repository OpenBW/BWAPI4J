/*
 * Unit.cpp
 *
 *  Created on: Oct 8, 2017
 *      Author: imp
 */
#include <BWAPI/Client.h>
#include "org_openbw_bwapi4j_unit_Unit.h"

using namespace BWAPI;

JNIEXPORT jboolean JNICALL Java_org_openbw_bwapi4j_unit_Unit_issueCommand(JNIEnv * env, jobject jObj, jint unitID, jint unitCommandTypeID, jint targetUnitID, jint x, jint y, jint extra) {

	std::cout << "issuing command " << unitCommandTypeID << " to " << unitID << std::endl;
	Unit unit = Broodwar->getUnit(unitID);
	if (unit != NULL) {
		UnitCommand c = BWAPI::UnitCommand();
		c.unit = unit;
		c.type = unitCommandTypeID;
		c.target = Broodwar->getUnit(targetUnitID);
		c.x = x;
		c.y = y;
		c.extra = extra;
		return c.unit->issueCommand(c);
	}
	return JNI_FALSE;
}


