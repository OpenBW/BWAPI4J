/*
 * BridgeMap.h
 *
 *  Created on: Jul 16, 2017
 *      Author: imp
 */
#include <jni.h>
#include "Bridge.h"

#ifndef BRIDGEMAP_H_
#define BRIDGEMAP_H_

class BridgeMap {
public:
	BridgeMap();
	virtual ~BridgeMap();
	void initialize(JNIEnv * env, jclass jc, jobject bwObject, jclass bwMapClass);
};

#endif /* BRIDGEMAP_H_ */
