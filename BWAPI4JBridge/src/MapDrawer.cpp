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
#include "org_openbw_bwapi4j_MapDrawer.h"

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawCircleMap_1native__IIII(JNIEnv *, jobject, jint x, jint y, jint radius, jint colorValue) {
  BWAPI::Color color(colorValue);
  BWAPI::Broodwar->drawCircleMap(x, y, radius, color);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawCircleMap_1native__IIIIZ(JNIEnv *, jobject, jint x, jint y, jint radius, jint colorValue,
                                                                                      jboolean isSolid) {
  BWAPI::Color color(colorValue);
  BWAPI::Broodwar->drawCircleMap(x, y, radius, color, isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawTextScreen_1native(JNIEnv *env, jobject, jint x, jint y, jstring cstr_format) {
  const char *messagechars = env->GetStringUTFChars(cstr_format, 0);
  BWAPI::Broodwar->drawTextScreen(x, y, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawBoxMap_1native__IIIII(JNIEnv *, jobject, jint left, jint top, jint right, jint bottom,
                                                                                   jint colorValue) {
  BWAPI::Color color(colorValue);
  BWAPI::Broodwar->drawBoxMap(left, top, right, bottom, color);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawBoxMap_1native__IIIIIZ(JNIEnv *, jobject, jint left, jint top, jint right, jint bottom,
                                                                                    jint colorValue, jboolean isSolid) {
  BWAPI::Color color(colorValue);
  BWAPI::Broodwar->drawBoxMap(left, top, right, bottom, color, isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawBoxScreen_1native(JNIEnv *, jobject, jint left, jint top, jint right, jint bottom, jint colorValue,
                                                                               jboolean isSolid) {
  BWAPI::Color color(colorValue);
  BWAPI::Broodwar->drawBoxScreen(left, top, right, bottom, color, isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawLineMap_1native(JNIEnv *, jobject, jint x1, jint y1, jint x2, jint y2, jint colorValue) {
  BWAPI::Color color(colorValue);
  BWAPI::Broodwar->drawLineMap(x1, y1, x2, y2, color);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawTextMap_1native(JNIEnv *env, jobject, jint x, jint y, jstring cstr_format) {
  const char *messagechars = env->GetStringUTFChars(cstr_format, 0);
  BWAPI::Broodwar->drawTextMap(x, y, messagechars);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_setTextSize(JNIEnv *env, jobject jObj, jint bwapi4jTextSize) {
  BWAPI::Text::Size::Enum textSize = BWAPI::Text::Size::Default;

  switch (bwapi4jTextSize) {
    case 0:
      textSize = BWAPI::Text::Size::Small;
      break;
    case 1:
      textSize = BWAPI::Text::Size::Default;
      break;
    case 2:
      textSize = BWAPI::Text::Size::Large;
      break;
    case 3:
      textSize = BWAPI::Text::Size::Huge;
      break;
    default:
      textSize = BWAPI::Text::Size::Default;
      break;
  }

  BWAPI::Broodwar->setTextSize(textSize);
}
