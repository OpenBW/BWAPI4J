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

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Spore_Colony;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Sunken_Colony;

import org.openbw.bwapi4j.type.UnitType;

public class CreepColony extends BuildingImpl implements Organic, Morphable {

  protected CreepColony(UnitType unitType, int timeSpotted) {
    super(unitType, timeSpotted);
  }

  /**
   * Morph into either SunkenColony or SporeColony
   *
   * @param type UnitType.Zerg_Sunken_Colony or UnitType.Zerg_Spore_Colony
   * @return true if morph successful, false else
   */
  @Override
  public boolean morph(UnitType type) {
    if (type == UnitType.Zerg_Sunken_Colony) {
      return morphSunkenColony();
    } else if (type == UnitType.Zerg_Spore_Colony) {
      return morphSporeColony();
    } else {
      return false;
    }
  }

  public boolean morphSporeColony() {
    return issueCommand(this.iD, Morph, -1, -1, -1, Zerg_Spore_Colony.getId());
  }

  public boolean morphSunkenColony() {
    return issueCommand(this.iD, Morph, -1, -1, -1, Zerg_Sunken_Colony.getId());
  }
}
