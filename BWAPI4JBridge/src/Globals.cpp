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

#include "Globals.h"

#include "BridgeEnum.h"
#include "BridgeMap.h"

namespace Bridge {
namespace Globals {
bool finished = false;
DataBuffer dataBuffer;
JavaRefs javaRefs;
Callbacks callbacks;
JNIEnv *env;
jobject bw;

void initialize(JNIEnv *newEnv, jobject newBW) {
  env = newEnv;
  bw = newBW;

  javaRefs.initialize(env);

  callbacks.initialize(env, Bridge::Globals::javaRefs.bwClass);
}

void initializeGame(JNIEnv *env, jobject bw) {
  BridgeMap bwMap;
  bwMap.initialize(env, bw, Bridge::Globals::javaRefs);
}
}  // namespace Globals
}  // namespace Bridge
