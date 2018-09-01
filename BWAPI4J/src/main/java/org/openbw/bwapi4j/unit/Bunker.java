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

import static org.openbw.bwapi4j.type.UnitCommandType.Load;
import static org.openbw.bwapi4j.type.UnitCommandType.Unload;
import static org.openbw.bwapi4j.type.UnitCommandType.Unload_All;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;

public class Bunker extends BuildingImpl implements Mechanical, Loadable {
  private static final Logger logger = LogManager.getLogger();

  protected Bunker(final int id, final int timeSpotted) {
    super(id, UnitType.Terran_Bunker, timeSpotted);
  }

  public boolean isLoaded() {
    return loaded;
  }

  @Override
  public boolean load(final MobileUnit target) {
    return load(target, false);
  }

  @Override
  public boolean load(final MobileUnit target, final boolean queued) {
    if (target.isFlyer()) {
      logger.error("Can't load a {} into a garrison. Only non-flying units allowed.", target);
      return false;
    } else {
      return issueCommand(this.iD, Load, target.getId(), -1, -1, queued ? 1 : 0);
    }
  }

  @Override
  public boolean unload(final MobileUnit target) {
    return issueCommand(this.iD, Unload, target.getId(), -1, -1, -1);
  }

  @Override
  public boolean unloadAll() {
    return unloadAll(false);
  }

  @Override
  public boolean unloadAll(final boolean queued) {
    return issueCommand(this.iD, Unload_All, -1, -1, -1, queued ? 1 : 0);
  }

  @Override
  public int getSpaceRemaining() {
    return super.spaceRemaining;
  }

  @Override
  public List<MobileUnit> getLoadedUnits() {
    return (List<MobileUnit>) (List<?>) loadedUnits;
  }
}
