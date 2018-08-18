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

package bwem.unit;

import bwem.area.Area;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.unit.Unit;

/**
 * Neutral is the abstract base class for a small hierarchy of wrappers around some BWAPI::Units<br>
 * The units concerned are the Resources (Minerals and Geysers) and the static Buildings.<br>
 * Stacked Neutrals are supported, provided they share the same type at the same location.
 */
public interface Neutral {

  /** Returns the BWAPI::Unit this Neutral is wrapping around. */
  Unit getUnit();

  /** Returns the center of this Neutral, in pixels (same as unit()->getInitialPosition()). */
  Position getCenter();

  /**
   * Returns the top left Tile position of this Neutral (same as unit()->getInitialTilePosition()).
   */
  TilePosition getTopLeft();

  /** Returns the bottom right Tile position of this Neutral */
  TilePosition getBottomRight();

  /** Returns the size of this Neutral, in Tiles (same as Type()->tileSize()) */
  TilePosition getSize();

  /**
   * Tells whether this Neutral is blocking some ChokePoint.<br>
   * - This applies to minerals and StaticBuildings only.<br>
   * - For each blocking Neutral, a pseudo ChokePoint (which is blocked()) is created on top of it,
   * with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is
   * created.<br>
   * - Cf. definition of pseudo getChokePoints in class ChokePoint comment.<br>
   * - Cf. ChokePoint::blockingNeutral and ChokePoint::blocked.
   */
  boolean isBlocking();

  /** If blocking() == true, returns the set of areas blocked by this Neutral. */
  List<Area> getBlockedAreas();

  /**
   * Returns the next Neutral stacked over this Neutral, if ever.<br>
   * - To iterate through the whole stack, one can use the following:<br>
   * <code>for (const Neutral * n = Map::GetTile(topLeft()).GetNeutral() ; n ; n = n->nextStacked())
   * </code>
   */
  Neutral getNextStacked();

  /** Returns the last Neutral stacked over this Neutral, if ever. */
  Neutral getLastStacked();
}
