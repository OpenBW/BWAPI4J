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

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public abstract class Transporter extends MobileUnitImpl implements Loadable {
  private static final Logger logger = LogManager.getLogger();

  protected Transporter(final int id, final UnitType unitType) {
    super(id, unitType);
  }

  @Override
  public boolean isLoaded() {
    return this.isLoaded;
  }

  @Override
  public boolean load(final MobileUnit target) {
    return load(target, false);
  }

  @Override
  public boolean load(final MobileUnit target, final boolean queued) {
    if (target.isFlyer()) {
      logger.error("Can't load a {} into a transport. Only non-flying units allowed.", target);
      return false;
    } else {
      return issueCommand(this.id, Load, target.getId(), -1, -1, queued ? 1 : 0);
    }
  }

  @Override
  public boolean unload(final MobileUnit target) {
    return issueCommand(this.id, Unload, target.getId(), -1, -1, -1);
  }

  @Override
  public boolean unloadAll() {
    return unloadAll(false);
  }

  @Override
  public boolean unloadAll(final boolean queued) {
    return issueCommand(this.id, Unload_All, -1, -1, -1, queued ? 1 : 0);
  }

  public boolean unloadAll(final Position p) {
    return unloadAll(p, false);
  }

  public boolean unloadAll(final Position p, final boolean queued) {
    return issueCommand(this.id, Unload_All_Position, -1, p.getX(), p.getY(), queued ? 1 : 0);
  }

  @Override
  public int getSpaceRemaining() {
    return super.spaceRemaining;
  }

  @Override
  public List<MobileUnit> getLoadedUnits() {
    return super.getLoadedUnits();
  }
}
