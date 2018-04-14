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

package bwem.map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.List;

public interface MapData {

    /**
     * Returns the size of the map in tiles.
     */
    TilePosition getTileSize();

    /**
     * Returns the size of the map in walktiles.
     */
    WalkPosition getWalkSize();

    /**
     * Returns the size of the map in pixels.
     */
    Position getPixelSize();

    /**
     * Returns the center of the map in pixels.
     */
    Position getCenter();

    /**
     * Returns the internal container of the starting Locations.<br/>
     * Note: these correspond to BWAPI::getStartLocations().
     */
    List<TilePosition> getStartingLocations();

    /**
     * Tests whether the specified position is inside the map.
     *
     * @param tilePosition the specified position
     */
    boolean isValid(TilePosition tilePosition);

    /**
     * Tests whether the specified position is inside the map.
     *
     * @param walkPosition the specified position
     */
    boolean isValid(WalkPosition walkPosition);

    /**
     * Tests whether the specified position is inside the map.
     *
     * @param position the specified position
     */
    boolean isValid(Position position);

    /**
     * Returns a cropped version of the specified position if
     * it is not inside the map.
     *
     * @param tilePosition the specified position
     */
    TilePosition crop(TilePosition tilePosition);

    /**
     * Returns a cropped version of the specified position if
     * it is not inside the map.
     *
     * @param walkPosition the specified position
     */
    WalkPosition crop(WalkPosition walkPosition);

    /**
     * Returns a cropped version of the specified position if
     * it is not inside the map.
     *
     * @param position the specified position
     */
    Position crop(Position position);

    /**
     * Returns a random position on the map in pixels.
     */
    Position getRandomPosition();

}
