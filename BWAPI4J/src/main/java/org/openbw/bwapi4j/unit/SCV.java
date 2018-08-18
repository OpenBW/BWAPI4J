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

import static org.openbw.bwapi4j.type.UnitCommandType.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;

public class SCV extends Worker implements Mechanical {
  private static final Logger logger = LogManager.getLogger();

  protected SCV(int id) {
    super(id, UnitType.Terran_SCV);
  }

  public boolean isRepairing() {
    return isRepairing;
  }

  public boolean repair(Mechanical target) {
    return issueCommand(id, Repair, target.getId(), -1, -1, -1);
  }

  public boolean haltConstruction() {
    return issueCommand(this.id, Halt_Construction, -1, -1, -1, -1);
  }

  public boolean resumeBuilding(Building building) {
    return issueCommand(this.id, Right_Click_Unit, building.getId(), -1, -1, -1);
  }
}
