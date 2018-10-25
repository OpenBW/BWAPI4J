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

#include <BWAPI.h>

#include "org_openbw_bwapi4j_MapDrawer.h"

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_setTextSize_1native(JNIEnv *env, jobject jObj, jint bwapi4jTextSize) {
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

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawText_1native(JNIEnv *env, jobject, jint coordinateType, jint x, jint y, jstring text) {
  const char *buffer = env->GetStringUTFChars(text, 0);
  BWAPI::Broodwar->drawText(BWAPI::CoordinateType::Enum(coordinateType), x, y, buffer);
  env->ReleaseStringUTFChars(text, buffer);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawBox_1native(JNIEnv *, jobject, jint coordinateType, jint left, jint top, jint right, jint bottom,
                                                                         jint color, jboolean isSolid) {
  BWAPI::Broodwar->drawBox(BWAPI::CoordinateType::Enum(coordinateType), left, top, right, bottom, BWAPI::Color(color), isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawTriangle_1native(JNIEnv *, jobject, jint coordinateType, jint ax, jint ay, jint bx, jint by,
                                                                              jint cx, jint cy, jint color, jboolean isSolid) {
  BWAPI::Broodwar->drawTriangle(BWAPI::CoordinateType::Enum(coordinateType), ax, ay, bx, by, cx, cy, BWAPI::Color(color), isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawCircle_1native(JNIEnv *, jobject, jint coordinateType, jint x, jint y, jint radius, jint color,
                                                                            jboolean isSolid) {
  BWAPI::Broodwar->drawCircle(BWAPI::CoordinateType::Enum(coordinateType), x, y, radius, BWAPI::Color(color), isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawEllipse_1native(JNIEnv *, jobject, jint coordinateType, jint x, jint y, jint xrad, jint yrad,
                                                                             jint color, jboolean isSolid) {
  BWAPI::Broodwar->drawEllipse(BWAPI::CoordinateType::Enum(coordinateType), x, y, xrad, yrad, BWAPI::Color(color), isSolid);
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawDot_1native(JNIEnv *, jobject, jint coordinateType, jint x, jint y, jint color) {
  BWAPI::Broodwar->drawDot(BWAPI::CoordinateType::Enum(coordinateType), x, y, BWAPI::Color(color));
}

JNIEXPORT void JNICALL Java_org_openbw_bwapi4j_MapDrawer_drawLine_1native(JNIEnv *, jobject, jint coordinateType, jint ax, jint ay, jint bx, jint by,
                                                                          jint color) {
  BWAPI::Broodwar->drawLine(BWAPI::CoordinateType::Enum(coordinateType), ax, ay, bx, by, BWAPI::Color(color));
}
