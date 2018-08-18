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

import org.openbw.bwapi4j.type.UnitType;

/** Interface for units that can morph (many Zerg units can). */
public interface Morphable extends PlayerUnit {
  /**
   * Morphs this unit into the given unit type. Be aware that "this" object will be obsolete after
   * the call and a new object with the same {@link Unit#getId()} will be created.
   *
   * @param type the target type to morph to
   * @return true if the morph command was successfully delivered to Broodwar, false if the command
   *     was denied (for example due to missing supply or resources).
   * @throws IllegalArgumentException if the given type is invalid for this unit
   */
  boolean morph(UnitType type);
}
