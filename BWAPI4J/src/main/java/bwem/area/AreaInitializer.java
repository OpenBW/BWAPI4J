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

package bwem.area;

import bwem.ChokePoint;
import bwem.Markable;
import bwem.area.typedef.GroupId;
import bwem.map.AdvancedData;
import bwem.map.Map;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import org.openbw.bwapi4j.TilePosition;

import java.util.List;

public interface AreaInitializer {

    Markable getMarkable();

    void addChokePoints(Area area, List<ChokePoint> chokePoints);

    void addMineral(final Mineral mineral);

    void addGeyser(Geyser geyser);

    // Called for each tile t of this Area
    void addTileInformation(TilePosition tilePosition, Tile tile);

    void setGroupId(GroupId gid);

    Map getMap();

    // Called after addTileInformation(t) has been called for each tile t of this Area
    void postCollectInformation();

    int[] computeDistances(ChokePoint startCP, List<ChokePoint> targetCPs);

    // Returns Distances such that Distances[i] == ground_distance(start, targets[i]) in pixels
    // Note: same algorithm than Graph::computeDistances (derived from Dijkstra)
    int[] computeDistances(TilePosition start, List<TilePosition> targets);

    void updateAccessibleNeighbors();

    // Fills in bases with good locations in this Area.
    // The algorithm repeatedly searches the best possible location L (near resources)
    // When it finds one, the nearby resources are assigned to L, which makes the remaining resources decrease.
    // This causes the algorithm to always terminate due to the lack of remaining resources.
    // To efficiently compute the distances to the resources, with use Potiential Fields in the InternalData() value of the Tiles.
    void createBases(AdvancedData mapAdvancedData);

    // Calculates the score >= 0 corresponding to the placement of a Base Command center at 'location'.
    // The more there are resources nearby, the higher the score is.
    // The function assumes the distance to the nearby resources has already been computed (in InternalData()) for each tile around.
    // The job is therefore made easier : just need to sum the InternalData() values.
    // Returns -1 if the location is impossible.
    int computeBaseLocationScore(AdvancedData mapAdvancedData, TilePosition location);

    // Checks if 'location' is a valid location for the placement of a Base Command center.
    // If the location is valid except for the presence of Mineral patches of less than 9 (see Andromeda.scx),
    // the function returns true, and these minerals are reported in blockingMinerals
    // The function is intended to be called after computeBaseLocationScore, as it is more expensive.
    // See also the comments inside computeBaseLocationScore.
    boolean validateBaseLocation(AdvancedData mapAdvancedData, TilePosition location, List<Mineral> blockingMinerals);

}
