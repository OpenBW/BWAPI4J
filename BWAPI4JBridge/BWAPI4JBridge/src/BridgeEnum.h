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
