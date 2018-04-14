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

package bwem.tile;

import bwem.area.typedef.AreaId;
import bwem.typedef.Altitude;
import org.openbw.bwapi4j.WalkPosition;

/**
 * Corresponds to BWAPI/Starcraft's concept of walk tile (8x8 pixels).<br/>
 * - MiniTiles are accessed using WalkPositions {@link bwem.map.AdvancedData#getMiniTile(WalkPosition)}<br/>
 * - A Map holds Map::WalkSize().x * Map::WalkSize().y MiniTiles as its "MiniTile map".<br/>
 * - A MiniTile contains essentialy 3 pieces of information:<br/>
 *   i) its Walkability<br/>
 *   ii) its altitude (distance from the nearest non walkable MiniTile, except those which are part of small enough zones (lakes))<br/>
 *   iii) the id of the Area it is part of, if ever.<br/>
 * - The whole process of analysis of a Map relies on the walkability information<br/>
 * from which are derived successively: altitudes, Areas, ChokePoints.
 */
public interface MiniTile {

    /**
     * Corresponds approximatively to BWAPI::isWalkable<br/>
     * The differences are:<br/>
     * - For each BWAPI's unwalkable MiniTile, we also mark its 8 neighbors as not walkable.<br/>
     * According to some tests, this prevents from wrongly pretending one small unit can go by some thin path.<br/>
     * - The relation buildable ==> walkable is enforced, by marking as walkable any MiniTile part of a buildable Tile (Cf. {@link Tile#isBuildable()})<br/>
     * Among the miniTiles having Altitude() > 0, the walkable ones are considered Terrain-miniTiles, and the other ones Lake-miniTiles.
     */
    boolean isWalkable();

    /**
     * Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile<br/>
     * - Sea-miniTiles all have their Altitude() equal to 0.<br/>
     * - miniTiles having Altitude() > 0 are not Sea-miniTiles. They can be either Terrain-miniTiles or Lake-miniTiles.
     */
    Altitude getAltitude();

    /**
     * Sea-miniTiles are unwalkable miniTiles that have their altitude equal to 0.
     */
    boolean isSea();

    /**
     * Lake-miniTiles are unwalkable miniTiles that have their Altitude() > 0.<br/>
     * - They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)<br/>
     * - The intent is to preserve the continuity of altitudes inside areas.
     */
    boolean isLake();

    /**
     * Terrain miniTiles are just walkable miniTiles
     */
    boolean isTerrain();

    /**
     * For Sea and Lake miniTiles, returns 0<br/>
     * For Terrain miniTiles, returns a non zero id:<br/>
     * - if (id > 0), id uniquely identifies the Area A that contains this MiniTile.<br/>
     * Moreover we have: A.id() == id and Map::getArea(id) == A<br/>
     * - For more information about positive Area::ids, see Area::id()<br/>
     * - if (id < 0), then this MiniTile is part of a Terrain-zone that was considered too small to create an Area for it.<br/>
     * - Note: negative Area::ids start from -2<br/>
     * - Note: because of the lakes, Map::getNearestArea should be prefered over Map::getArea.
     */
    AreaId getAreaId();

}
