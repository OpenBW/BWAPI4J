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

    public abstract Markable getMarkable();

    public abstract void addChokePoints(Area area, List<ChokePoint> chokePoints);

    public abstract void addMineral(final Mineral mineral);

    public abstract void addGeyser(Geyser geyser);

    // Called for each tile t of this Area
    public abstract void addTileInformation(TilePosition tilePosition, Tile tile);

    public abstract void setGroupId(GroupId gid);

    public abstract Map getMap();

    // Called after addTileInformation(t) has been called for each tile t of this Area
    public abstract void postCollectInformation();

    public abstract int[] computeDistances(ChokePoint startCP, List<ChokePoint> targetCPs);

    public abstract void updateAccessibleNeighbors();

    // Fills in bases with good locations in this Area.
    // The algorithm repeatedly searches the best possible location L (near ressources)
    // When it finds one, the nearby ressources are assigned to L, which makes the remaining ressources decrease.
    // This causes the algorithm to always terminate due to the lack of remaining ressources.
    // To efficiently compute the distances to the ressources, with use Potiential Fields in the InternalData() value of the Tiles.
    public abstract void createBases(AdvancedData mapAdvancedData);

    // Calculates the score >= 0 corresponding to the placement of a Base Command center at 'location'.
    // The more there are ressources nearby, the higher the score is.
    // The function assumes the distance to the nearby ressources has already been computed (in InternalData()) for each tile around.
    // The job is therefore made easier : just need to sum the InternalData() values.
    // Returns -1 if the location is impossible.
    public abstract int computeBaseLocationScore(AdvancedData mapAdvancedData, TilePosition location);

    // Checks if 'location' is a valid location for the placement of a Base Command center.
    // If the location is valid except for the presence of Mineral patches of less than 9 (see Andromeda.scx),
    // the function returns true, and these minerals are reported in blockingMinerals
    // The function is intended to be called after computeBaseLocationScore, as it is more expensive.
    // See also the comments inside computeBaseLocationScore.
    public abstract boolean validateBaseLocation(AdvancedData mapAdvancedData, TilePosition location, List<Mineral> blockingMinerals);

    // Returns Distances such that Distances[i] == ground_distance(start, targets[i]) in pixels
    // Note: same algorithm than Graph::computeDistances (derived from Dijkstra)
    public abstract int[] computeDistances(TilePosition start, List<TilePosition> targets);

}
