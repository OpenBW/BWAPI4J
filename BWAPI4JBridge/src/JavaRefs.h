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

#include <jni.h>

struct JavaRefs {
  jclass listClass;
  jmethodID listClass_add;

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

  jclass bwClass;

  jclass bwMapClass;

  void initialize(JNIEnv *);
};
