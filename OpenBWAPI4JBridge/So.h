/*
 * So.h
 *
 *  Created on: Jul 16, 2017
 *      Author: imp
 */
#include <jni.h>

#ifndef SO_H_
#define SO_H_

extern JNIEnv * globalEnv;
extern jobject globalBW;
extern jint *intBuf;


extern jclass arrayListClass;
extern jmethodID arrayListAdd;

extern jclass integerClass;
extern jmethodID integerNew;

extern jclass tilePositionClass;
extern jmethodID tilePositionNew;

extern jclass weaponTypeClass;
extern jclass techTypeClass;
extern jclass unitTypeClass;
extern jclass upgradeTypeClass;
extern jclass damageTypeClass ;
extern jclass explosionTypeClass;
extern jclass raceClass;
extern jclass unitSizeTypeClass;
extern jclass orderClass;

extern jclass pairClass;
extern jmethodID pairNew;

extern jclass bwMapClass;
extern jmethodID bwMapNew;

extern jmethodID addRequiredUnit;


#endif /* SO_H_ */
