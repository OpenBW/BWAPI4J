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

import static org.openbw.bwapi4j.type.UnitCommandType.Burrow;
import static org.openbw.bwapi4j.type.UnitCommandType.Morph;
import static org.openbw.bwapi4j.type.UnitCommandType.Unburrow;

import org.openbw.bwapi4j.type.UnitType;

public class Drone extends Worker implements Organic, Burrowable, Morphable {
  protected Drone(int id) {
    super(id, UnitType.Zerg_Drone);
  }

  public boolean burrow() {
    return issueCommand(this.iD, Burrow, -1, -1, -1, -1);
  }

  @Override
  public boolean unburrow() {
    return issueCommand(this.iD, Unburrow, -1, -1, -1, -1);
  }

  @Override
  public boolean isBurrowed() {
    return this.burrowed;
  }

  @Override
  public boolean morph(UnitType type) {
    return issueCommand(this.iD, Morph, -1, -1, -1, type.getId());
  }
}
