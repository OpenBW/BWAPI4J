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

package bwem;

import bwem.area.Area;
import bwem.typedef.CPPath;
import bwem.unit.Neutral;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.util.Pair;

import java.util.List;

/**
 * ChokePoints are frontiers that BWEM automatically computes from Brood War's maps.<br/>
 * A ChokePoint represents (part of) the frontier between exactly 2 Areas. It has a form of line.<br/>
 * A ChokePoint doesn't contain any MiniTile: All the MiniTiles whose positions are returned by its Geometry()
 * are just guaranteed to be part of one of the 2 Areas.<br/>
 * Among the MiniTiles of its Geometry, 3 particular ones called nodes can also be accessed using Pos(middle), Pos(end1) and Pos(end2).<br/>
 * ChokePoints play an important role in BWEM:<br/>
 * - they define accessibility between Areas.<br/>
 * - the Paths provided by Map::GetPath are made of ChokePoints.<br/>
 * Like Areas and Bases, the number and the addresses of ChokePoint instances remain unchanged.<br/>
 * <br/>
 * Pseudo ChokePoints:<br/>
 * Some Neutrals can be detected as blocking Neutrals (Cf. Neutral::Blocking).<br/>
 * Because only ChokePoints can serve as frontiers between Areas, BWEM automatically creates a ChokePoint
 * for each blocking Neutral (only one in the case of stacked blocking Neutral).<br/>
 * Such ChokePoints are called pseudo ChokePoints and they behave differently in several ways.
 */
public interface ChokePoint {

    /**
     * ChokePoint::middle denotes the "middle" MiniTile of Geometry(), while
     * ChokePoint::END_1 and ChokePoint::END_2 denote its "ends".
     * It is guaranteed that, among all the MiniTiles of Geometry(), ChokePoint::middle has the highest altitude value (Cf. MiniTile::Altitude()).
     */
    enum Node {
        END1,
        MIDDLE,
        END2,
        NODE_COUNT
    }

    /**
     * Tells whether this ChokePoint is a pseudo ChokePoint, i.e., it was created on top of a blocking Neutral.
     */
	boolean isPseudo();

    /**
     * Returns the two areas of this ChokePoint.
     */
    Pair<Area, Area> getAreas();

    /**
     * Returns the center of this ChokePoint.
     */
    WalkPosition getCenter();

    /**
     * Returns the position of one of the 3 nodes of this ChokePoint (Cf. node definition).<br/>
     * - Note: the returned value is contained in geometry()
     */
    WalkPosition getNodePosition(final Node node);

    /**
     * Pretty much the same as pos(n), except that the returned MiniTile position is guaranteed to be part of pArea.
     * That is: Map::getArea(positionOfNodeInArea(n, pArea)) == pArea.
     */
    WalkPosition getNodePositionInArea(final Node node, final Area area);

    /**
     * Returns the set of positions that defines the shape of this ChokePoint.<br/>
     * - Note: none of these miniTiles actually belongs to this ChokePoint (a ChokePoint doesn't contain any MiniTile).
     * They are however guaranteed to be part of one of the 2 areas.<br/>
     * - Note: the returned set contains pos(middle), pos(END_1) and pos(END_2).
     * If isPseudo(), returns {p} where p is the position of a walkable MiniTile near from blockingNeutral()->pos().
     */
    List<WalkPosition> getGeometry();

    /**
     * If !isPseudo(), returns false.
     * Otherwise, returns whether this ChokePoint is considered blocked.
     * Normally, a pseudo ChokePoint either remains blocked, or switches to not isBlocked when blockingNeutral()
     * is destroyed and there is no remaining Neutral stacked with it.
     * However, in the case where Map::automaticPathUpdate() == false, blocked() will always return true
     * whatever blockingNeutral() returns.
     * Cf. Area::AccessibleNeighbors().
     */
    boolean isBlocked();

    /**
     * If !isPseudo(), returns nullptr.
     * Otherwise, returns a pointer to the blocking Neutral on top of which this pseudo ChokePoint was created,
     * unless this blocking Neutral has been destroyed.
     * In this case, returns a pointer to the next blocking Neutral that was stacked at the same location,
     * or nullptr if no such Neutral exists.
     */
    Neutral getBlockingNeutral();

    /**
     * If accessibleFrom(cp) == false, returns -1.
     * Otherwise, returns the ground distance in pixels between center() and cp->center().
     * - Note: if this == cp, returns 0.<br/>
     * - Time complexity: O(1)<br/>
     * - Note: Corresponds to the length in pixels of getPathTo(cp). So it suffers from the same lack of accuracy.
     * In particular, the value returned tends to be slightly higher than expected when getPathTo(cp).size() is high.
     */
    int distanceFrom(final ChokePoint chokePoint);

    /**
     * Returns whether this ChokePoint is accessible from cp (through a walkable path).<br/>
     * - Note: the relation is symmetric: this->accessibleFrom(cp) == cp->accessibleFrom(this)<br/>
     * - Note: if this == cp, returns true.<br/>
     * - Time complexity: O(1)<br/>
     */
    boolean accessibleFrom(final ChokePoint chokePoint);

    /**
     * Returns a list of getChokePoints, which is intended to be the shortest walking path from this ChokePoint to cp.
     * The path always starts with this ChokePoint and ends with cp, unless accessibleFrom(cp) == false.
     * In this case, an empty list is returned.<br/>
     * - Note: if this == cp, returns [cp].<br/>
     * Time complexity: O(1)<br/>
     * To get the length of the path returned in pixels, use distanceFrom(cp).<br/>
     * - Note: all the possible Paths are precomputed during Map::initialize().<br/>
     * The best one is then stored for each pair of getChokePoints.
     * However, only the center of the getChokePoints is considered.
     * As a consequence, the returned path may not be the shortest one.
     */
    CPPath getPathTo(ChokePoint cp);

}
