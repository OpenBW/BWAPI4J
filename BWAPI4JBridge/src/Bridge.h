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

#include <list>

#include <BWAPI.h>
#include <jni.h>

extern JNIEnv *globalEnv;
extern jobject globalBW;

extern const size_t intBufSize;
extern jint intBuf[];

struct JavaRefs {
  jclass arrayListClass;
  jmethodID arrayListClass_add;

  jclass integerClass;
  jmethodID integerClassConstructor;

  jclass tilePositionClass;
  jmethodID tilePositionConstructor;

  jclass weaponTypeClass;
  jclass techTypeClass;

  jclass unitTypeClass;
  jmethodID unitTypeClass_addRequiredUnit;

  jclass upgradeTypeClass;
  jmethodID upgradeTypeClass_addUsingUnit;

  jclass damageTypeClass;
  jclass explosionTypeClass;
  jclass raceClass;
  jclass unitSizeTypeClass;
  jclass orderClass;

  jclass pairClass;
  jmethodID pairClassConstructor;

  jclass bwMapClass;

  void initialize(JNIEnv *);
};

extern JavaRefs javaRefs;

struct Callbacks {
  jmethodID preFrameCallback;
  jmethodID onStartCallback;
  jmethodID onEndCallback;
  jmethodID onFrameCallback;
  jmethodID onSendTextCallback;
  jmethodID onReceiveTextCallback;
  jmethodID onPlayerLeftCallback;
  jmethodID onNukeDetectCallback;
  jmethodID onUnitDiscoverCallback;
  jmethodID onUnitEvadeCallback;
  jmethodID onUnitShowCallback;
  jmethodID onUnitHideCallback;
  jmethodID onUnitCreateCallback;
  jmethodID onUnitDestroyCallback;
  jmethodID onUnitMorphCallback;
  jmethodID onUnitRenegadeCallback;
  jmethodID onUnitCompleteCallback;
  jmethodID onSaveGameCallback;

  void initialize(JNIEnv *, jclass);

  void processEvents(JNIEnv *, jobject, const std::list<BWAPI::Event> &);
};

extern Callbacks callbacks;
