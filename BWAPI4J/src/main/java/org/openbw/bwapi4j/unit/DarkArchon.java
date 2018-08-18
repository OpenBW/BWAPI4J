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

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;

public class DarkArchon extends MobileUnitImpl implements Organic, SpellCaster {

  protected DarkArchon(int id) {

    super(id, UnitType.Protoss_Dark_Archon);
  }

  @Override
  public int getEnergy() {

    return this.energy;
  }

  @Override
  public int getMaxEnergy() {

    return super.getMaxEnergy();
  }

  public boolean feedback(MobileUnit unit) {
    // FIXME Due to latency this check if not always correct
    if (this.energy < TechType.Feedback.energyCost()) {

      return false;
    } else {

      return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1, Feedback.getId());
    }
  }

  public boolean mindControl(MobileUnit unit) {
    // FIXME Due to latency this check if not always correct
    if (this.energy < TechType.Mind_Control.energyCost()) {

      return false;
    } else {

      return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1, Mind_Control.getId());
    }
  }

  public boolean maelstrom(Position position) {
    // FIXME Due to latency this check if not always correct
    if (this.energy < TechType.Maelstrom.energyCost()) {

      return false;
    } else {

      return issueCommand(
          this.id, Use_Tech_Position, -1, position.getX(), position.getY(), Maelstrom.getId());
    }
  }
}
