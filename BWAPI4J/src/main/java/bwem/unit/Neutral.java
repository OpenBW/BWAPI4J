package bwem.unit;

import bwem.area.Area;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.unit.Unit;

import java.util.List;

/**
 * Neutral is the abstract base class for a small hierarchy of wrappers around some BWAPI::Units
 * The units concerned are the Resources (Minerals and Geysers) and the static Buildings.
 * Stacked Neutrals are supported, provided they share the same type at the same location.
 */
public interface Neutral {

    /**
     * Returns the BWAPI::Unit this Neutral is wrapping around.
     */
    Unit getUnit();

    /**
     * Returns the center of this Neutral, in pixels (same as unit()-&gt;getInitialPosition()).
     */
    Position getCenter();

    /**
     * Returns the top left Tile position of this Neutral (same as unit()-&gt;getInitialTilePosition()).
     */
    TilePosition getTopLeft();

    /**
     * Returns the bottom right Tile position of this Neutral
     */
    TilePosition getBottomRight();

    /**
     * Returns the size of this Neutral, in Tiles (same as Type()-&gt;tileSize())
     */
    TilePosition getSize();

    /**
     * Tells whether this Neutral is blocking some ChokePoint.
     * - This applies to minerals and StaticBuildings only.
     * - For each blocking Neutral, a pseudo ChokePoint (which is blocked()) is created on top of it,
     * with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is created.
     * - Cf. definition of pseudo getChokePoints in class ChokePoint comment.
     * - Cf. ChokePoint::blockingNeutral and ChokePoint::blocked.
     */
    boolean isBlocking();

    /**
     * If blocking() == true, returns the set of areas blocked by this Neutral.
     */
    List<Area> getBlockedAreas();

    /**
     * Returns the next Neutral stacked over this Neutral, if ever.
     * - To iterate through the whole stack, one can use the following:
     * <code>for (const Neutral * n = Map::GetTile(topLeft()).GetNeutral() ; n ; n = n-&gt;nextStacked())</code>
     */
    Neutral getNextStacked();

    /**
     * Returns the last Neutral stacked over this Neutral, if ever.
     */
    Neutral getLastStacked();

}
