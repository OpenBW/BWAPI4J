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

package org.openbw.bwapi4j.unit;

import java.util.List;

public interface Loadable extends PlayerUnit {
  boolean isLoaded();

  boolean load(MobileUnit target);

  /**
   * Loads target unit into this transporter.
   *
   * @param target unit to load
   * @param queued true if command is queued
   * @return true is command successful, false else
   */
  boolean load(MobileUnit target, boolean queued);

  boolean unload(MobileUnit target);

  boolean unloadAll();

  boolean unloadAll(boolean queued);

  int getSpaceRemaining();

  List<MobileUnit> getLoadedUnits();
}
