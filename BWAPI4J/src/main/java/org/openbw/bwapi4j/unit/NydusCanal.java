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

import static org.openbw.bwapi4j.type.UnitCommandType.Build;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Nydus_Canal;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class NydusCanal extends BuildingImpl implements Organic {

  protected NydusCanal(UnitType unitType, int timeSpotted) {
    super(unitType, timeSpotted);
  }

  public NydusCanal getNydusExit() {
    return (NydusCanal) nydusExit;
  }

  /**
   * Builds a nydus exit for this nydus canal at given position.
   *
   * @param position the position of the nydus exit
   * @return true if command successful, false else
   */
  public boolean buildNydusExit(Position position) {
    return issueCommand(
        this.iD, Build, -1, position.getX(), position.getY(), Zerg_Nydus_Canal.getId());
  }
}
