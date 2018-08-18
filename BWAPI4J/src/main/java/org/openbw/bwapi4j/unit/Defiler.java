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
import static org.openbw.bwapi4j.type.UnitCommandType.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;

public class Defiler extends MobileUnitImpl implements Organic, SpellCaster, Burrowable {
  private static final Logger logger = LogManager.getLogger();

  protected Defiler(int id) {
    super(id, UnitType.Zerg_Defiler);
  }

  @Override
  public int getEnergy() {
    return this.energy;
  }

  @Override
  public int getMaxEnergy() {
    return super.getMaxEnergy();
  }

  /**
   * Consumes target Zerg mobile unit (except larva).
   *
   * @param target Zerg unit (no larva)
   * @return true if spell is successful, false else
   */
  public boolean consume(MobileUnit target) {
    if (this.energy < TechType.Spawn_Broodlings.energyCost()) {
      return false;
    } else if (target.getType().getRace() != Race.Zerg || target.getType() == UnitType.Zerg_Larva) {
      logger.info(
          "Consume spell does not work on {} (only non-larva Zerg units can be consumed)", target);
      return false;
    } else {
      return issueCommand(this.id, Use_Tech_Unit, target.getId(), -1, -1, Spawn_Broodlings.getId());
    }
  }

  public boolean plague(Position position) {
    if (this.energy < TechType.Plague.energyCost()) {
      return false;
    } else {
      return issueCommand(
          this.id, Use_Tech_Position, -1, position.getX(), position.getY(), Plague.getId());
    }
  }

  public boolean darkSwarm(Position position) {
    if (this.energy < TechType.Dark_Swarm.energyCost()) {
      return false;
    } else {
      return issueCommand(
          this.id, Use_Tech_Position, -1, position.getX(), position.getY(), Dark_Swarm.getId());
    }
  }

  @Override
  public boolean burrow() {
    return issueCommand(this.id, Burrow, -1, -1, -1, -1);
  }

  @Override
  public boolean unburrow() {
    return issueCommand(this.id, Unburrow, -1, -1, -1, -1);
  }

  @Override
  public boolean isBurrowed() {
    return this.burrowed;
  }
}
