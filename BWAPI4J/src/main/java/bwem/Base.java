// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem;

import bwem.area.Area;
import bwem.map.MapData;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;

/**
 * After Areas and ChokePoints, Bases are the third kind of object BWEM automatically computes from
 * Brood War's maps. A Base is essentially a suggested location (intended to be optimal) to put a
 * resource depot. It also provides information on the resources available, and some statistics. A
 * Base always belongs to some Area. An Area may contain zero, one or several Bases. Like Areas and
 * ChokePoints, the number and the addresses of Base instances remain unchanged.
 */
public interface Base {
  /**
   * Tests whether this base is a start location.<br>
   * - Note: all players start at locations taken from {@link MapData#getStartingLocations()},<br>
   * which doesn't mean all the locations in {@link MapData#getStartingLocations()} are actually
   * used.
   */
  boolean isStartingLocation();

  /** Returns the area in which this base is located. */
  Area getArea();

  /**
   * Returns the position (top-left TilePosition) of the location for a resource depot.<br>
   * - Note: If {@link #isStartingLocation()} == true, it is guaranteed that the location
   * corresponds exactly to one of {@link MapData#getStartingLocations()}.
   */
  TilePosition getLocation();

  /** Returns the center position of {@link #getLocation()}. */
  Position getCenter();

  /**
   * Returns the available minerals.<br>
   * - These minerals are assigned to this base (it is guaranteed that no other base provides them).
   * <br>
   * - Note: The size of the returned list may decrease, as some of the minerals may get destroyed.
   */
  List<Mineral> getMinerals();

  /**
   * Returns the available geysers.<br>
   * - These geysers are assigned to this Base (it is guaranteed that no other Base provides them).
   * <br>
   * - Note: The size of the returned list will NOT decrease, as geysers never get destroyed.
   */
  List<Geyser> getGeysers();

  /**
   * Returns the blocking minerals.<br>
   * - These are special minerals. They are placed at or near the resource depot location,<br>
   * thus blocking the building of a resource depot from being close to the resources.<br>
   * - So, before trying to build a resource depot, these minerals must be gathered first.<br>
   * - Fortunately, these are guaranteed to have their initialAmount() <= 8.<br>
   * - As an example of blocking minerals, see the two islands in Andromeda.scx.<br>
   * - Note: if {@link #isStartingLocation()} == true, an empty list is returned.<br>
   * - Note: {@link #getBlockingMinerals()} should not be confused with {@link
   * ChokePoint#getBlockingNeutral()} and {@link Neutral#isBlocking()}:<br>
   * The last two refer to a Neutral blocking a ChokePoint, not a Base.
   */
  List<Mineral> getBlockingMinerals();
}
