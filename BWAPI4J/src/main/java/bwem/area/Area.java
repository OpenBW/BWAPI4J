package bwem.area;

import bwem.*;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.tile.TileImpl;
import bwem.typedef.Altitude;
import bwem.Check;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.Resource;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.util.Pair;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Area
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Areas are regions that BWEM automatically computes from Brood War's maps
// Areas aim at capturing relevant regions that can be walked, though they may contain small inner non walkable regions called lakes.
// More formally:
//  - An area consists in a set of 4-connected MiniTiles, which are either Terrain-MiniTiles or Lake-MiniTiles.
//  - An Area is delimited by the side of the Map, by Water-MiniTiles, or by other Areas. In the latter case
//    the adjoining Areas are called neighbouring Areas, and each pair of such Areas defines at least one ChokePoint.
// Like ChokePoints and Bases, the number and the addresses of Area instances remain unchanged.
// To access Areas one can use their ids or their addresses with equivalent efficiency.
//
// Areas inherit utils::Markable, which provides marking ability
// Areas inherit utils::UserData, which provides free-to-use data.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Area {

    private static final StaticMarkable staticMarkable = new StaticMarkable();
    private final Markable markable;

    private final Graph graph;
    private final AreaId id;
    private GroupId groupId = new GroupId(0);
    private final WalkPosition top;
    private TilePosition topLeft = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private TilePosition bottomRight = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private Altitude maxAltitude;
    private final int miniTiles;
    private int tiles = 0;
    private int buildableTiles = 0; /* Set and later incremented but not used in BWEM 1.4.1. Remains for portability consistency. */
    private int highGroundTiles = 0;
    private int veryHighGroundTiles = 0;
    private final AbstractMap<Area, List<ChokePoint>> chokePointsByArea = new ConcurrentHashMap<>();
    private final List<Area> accessibleNeighbors = new ArrayList<>();
    private final List<ChokePoint> chokePoints = new ArrayList<>();
    private final List<Mineral> minerals = new ArrayList<>();
    private final List<Geyser> geysers = new ArrayList<>();
	private final List<Base> Bases = new ArrayList<>();

    public Area(Graph graph, AreaId areaId, WalkPosition top, int miniTiles) {
        this.markable = new Markable(Area.staticMarkable);

        this.graph = graph;
        id = areaId;
        this.top = top;
        this.miniTiles = miniTiles;

//        bwem_assert(areaId > 0);
        if (!(areaId.intValue() > 0)) {
            throw new IllegalArgumentException();
        }

        MiniTile topMiniTile = getMap().getData().getMiniTile(top);
//        bwem_assert(topMiniTile.AreaId() == areaId);
        if (!(topMiniTile.getAreaId().equals(areaId))) {
            throw new IllegalStateException("assert failed: topMiniTile.AreaId().equals(areaId): expected: " + topMiniTile.getAreaId().intValue() + ", actual: " + areaId.intValue());
        }

        maxAltitude = new Altitude(topMiniTile.getAltitude());
    }

    public static StaticMarkable getStaticMarkable() {
        return Area.staticMarkable;
    }

    public Markable getMarkable() {
        return this.markable;
    }

    /**
     * Returns the internal Graph object. Not used in BWEM 1.4.1. Remains for portability consistency.
     */
    private Graph getGraph() {
        return graph;
    }

	// Unique id > 0 of this Area. Range = 1 .. Map::areas().size()
	// this == Map::getArea(id())
	// id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.
	// Area::ids are guaranteed to remain unchanged.
    public AreaId getId() {
        return id;
    }

	// Unique id > 0 of the group of areas which are accessible from this Area.
	// For each pair (a, b) of areas: a->groupId() == b->groupId()  <==>  a->accessibleFrom(b)
	// A groupId uniquely identifies a maximum set of mutually accessible areas, that is, in the absence of blocking getChokePoints, a continent.
    public GroupId getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupId gid) {
//        bwem_assert(gid >= 1);
        if (!(gid.intValue() >= 1)) {
            throw new IllegalArgumentException();
        }
        groupId = gid;
    }

    public TilePosition getTopLeft() {
        return topLeft;
    }

    public TilePosition getBottomRight() {
        return bottomRight;
    }

    public TilePosition getBoundingBoxSize() {
        return bottomRight.subtract (topLeft).add(new TilePosition(1, 1));
    }

    // Position of the MiniTile with the highest Altitude() value.
    public WalkPosition getTop() {
        return top;
    }

	// Returns Map::GetMiniTile(top()).Altitude().
    public Altitude getMaxAltitude() {
        return maxAltitude;
    }

	// Returns the number of miniTiles in this Area.
	// This most accurately defines the size of this Area.
    public int miniTiles() {
        return miniTiles;
    }

	// Returns the percentage of low ground Tiles in this Area.
    public int getLowGroundPercentage() {
        return (( (tiles - highGroundTiles - veryHighGroundTiles) * 100) / tiles);
    }

	// Returns the percentage of high ground Tiles in this Area.
    public int getHighGroundPercentage() {
        return ( (highGroundTiles * 100) / tiles);
    }

	// Returns the percentage of very high ground Tiles in this Area.
    public int getVeryHighGroundPercentage() {
        return ( (veryHighGroundTiles * 100) / tiles);
    }

	// Returns the getChokePoints between this Area and the neighbouring ones.
	// Note: if there are no neighbouring areas, then an empty set is returned.
	// Note there may be more getChokePoints returned than the number of neighbouring areas, as there may be several getChokePoints between two areas (Cf. getChokePoints(const Area * pArea)).
    public List<ChokePoint> getChokePoints() {
        return chokePoints;
    }

	// Returns the getChokePoints between this Area and pArea.
	// Assumes pArea is a neighbour of this Area, i.e. getChokePointsByArea().find(pArea) != getChokePointsByArea().end()
	// Note: there is always at least one ChokePoint between two neighbouring areas.
    public List<ChokePoint> getChokePoints(Area pArea) {
        List<ChokePoint> ret = chokePointsByArea.get(pArea);
//        bwem_assert(it != getChokePointsByArea.end());
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }

	// Returns the getChokePoints of this Area grouped by neighbouring areas
	// Note: if there are no neighbouring areas, than an empty set is returned.
	public AbstractMap<Area, List<ChokePoint>> getChokePointsByArea() {
        return chokePointsByArea;
    }

	// Returns the accessible neighbouring areas.
	// The accessible neighbouring areas are a subset of the neighbouring areas (the neighbouring areas can be iterated using getChokePointsByArea()).
	// Two neighbouring areas are accessible from each over if at least one the getChokePoints they share is not blocked (Cf. ChokePoint::blocked).
	public List<Area> getAccessibleNeighbors() {
        return accessibleNeighbors;
    }

	// Returns whether this Area is accessible from pArea, that is, if they share the same groupId().
	// Note: accessibility is always symmetrical.
	// Note: even if a and b are neighbouring areas,
	//       we can have: a->accessibleFrom(b)
	//       and not:     contains(a->AccessibleNeighbours(), b)
	// See also groupId()
	public boolean accessibleFrom(Area pArea) {
        return getGroupId().equals(pArea.getGroupId());
    }

	// Returns the minerals contained in this Area.
	// Note: only a call to Map::onMineralDestroyed(BWAPI::unit u) may change the result (by removing eventually one element).
	public List<Mineral> getMinerals() {
        return minerals;
    }

	// Returns the geysers contained in this Area.
	// Note: the result will remain unchanged.
	public List<Geyser> getGeysers() {
        return geysers;
    }

	// Returns the bases contained in this Area.
	// Note: the result will remain unchanged.
	public List<Base> getBases() {
        return Bases;
    }

    public Map getMap() {
        return graph.getMap();
    }

    public void addChokePoints(Area pArea, List<ChokePoint> pChokePoints) {
//        bwem_assert(!getChokePointsByArea[pArea] && pChokePoints);
        if (! (chokePointsByArea.get(pArea) == null && pChokePoints != null)) {
            throw new IllegalArgumentException();
        }

        chokePointsByArea.put(pArea, pChokePoints);

        chokePoints.addAll(pChokePoints);
    }

	public void addMineral(Mineral pMineral) {
//        bwem_assert(pMineral && !contains (minerals, pMineral));
        if (!(pMineral != null && !minerals.contains(pMineral))) {
            throw new IllegalStateException();
        }
        minerals.add(pMineral);
    }

	public void addGeyser(Geyser pGeyser) {
//        bwem_assert(pGeyser && !contains (geysers, pGeyser));
        if (!(pGeyser != null && !geysers.contains(pGeyser))) {
            throw new IllegalStateException();
        }
        geysers.add(pGeyser);
    }

    // Called for each tile t of this Area
    public void addTileInformation(final TilePosition tilePosition, final Tile tile) {
        ++tiles;

        if (tile.isBuildable()) ++buildableTiles;

        if (tile.getGroundHeight() == Tile.GroundHeight.HIGH_GROUND) ++highGroundTiles;
        else if (tile.getGroundHeight() == Tile.GroundHeight.VERY_HIGH_GROUND) ++veryHighGroundTiles;

        if (tilePosition.getX() < topLeft.getX()) topLeft = new TilePosition(tilePosition.getX(), topLeft.getY());
        if (tilePosition.getY() < topLeft.getY()) topLeft = new TilePosition (topLeft.getX(), tilePosition.getY());
        if (tilePosition.getX() > bottomRight.getX()) bottomRight = new TilePosition(tilePosition.getX(), bottomRight.getY());
        if (tilePosition.getY() > bottomRight.getY()) bottomRight = new TilePosition (bottomRight.getX(), tilePosition.getY());
    }

    // Called after addTileInformation(t) has been called for each tile t of this Area
    public void postCollectInformation() {
        /* Do nothing. This function is blank in BWEM 1.4.1 */
    }

    public void onMineralDestroyed(Mineral mineral) {
//        bwem_assert(mineral);
        if (mineral == null) {
            throw new IllegalArgumentException();
        }

        minerals.remove(mineral);

        // let's examine the bases even if mineral was not found in this Area,
        // which could arise if minerals were allowed to be assigned to neighbouring areas.
        for (Base base : getBases()) {
            base.onMineralDestroyed(mineral);
        }
    }

    public int[] computeDistances(final ChokePoint startCP, final List<ChokePoint> targetCPs) {
//        bwem_assert(!contains(targetCPs, startCP));
        if (targetCPs.contains(startCP)) {
            throw new IllegalStateException();
        }

        // findCond
        // visitCond
        final TilePosition start = getMap().breadthFirstSearch(
            startCP.positionOfNodeInArea(ChokePoint.Node.MIDDLE, this).toTilePosition(),
                args -> {
                    final Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        final Tile tile = (Tile) ttile;
                        return tile.getAreaId().equals(getId());
                    } else {
                        throw new IllegalArgumentException();
                    }
                },
                args -> true
        );

        final List<TilePosition> targets = new ArrayList<>();
        for (final ChokePoint cp : targetCPs) {
            // findCond
            // visitCond
            final TilePosition t = getMap().breadthFirstSearch(
                cp.positionOfNodeInArea(ChokePoint.Node.MIDDLE, this).toTilePosition(),
                    args -> {
                        final Object ttile = args[0];
                        if (ttile instanceof Tile) {
                            final Tile tile = (Tile) ttile;
                            return (tile.getAreaId().equals(getId()));
                        } else {
                            throw new IllegalArgumentException();
                        }
                    },
                    args -> true
            );
            targets.add(t);
        }

        return computeDistances(start, targets);
    }

    public void updateAccessibleNeighbors() {
        accessibleNeighbors.clear();
        for (final Area area : getChokePointsByArea().keySet()) {
            for (final ChokePoint cp : getChokePointsByArea().get(area)) {
                if (!cp.isBlocked()) {
                    accessibleNeighbors.add(area);
                    break;
                }
            }
        }
    }

    // Fills in bases with good locations in this Area.
    // The algorithm repeatedly searches the best possible location L (near ressources)
    // When it finds one, the nearby ressources are assigned to L, which makes the remaining ressources decrease.
    // This causes the algorithm to always terminate due to the lack of remaining ressources.
    // To efficiently compute the distances to the ressources, with use Potiential Fields in the InternalData() value of the Tiles.
    public void createBases() {
        TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();
        Map pMap = getMap();

        // initialize the RemainingRessources with all the minerals and geysers in this Area satisfying some conditions:
        List<Resource> remainingResources = new ArrayList<>();
        for (Mineral m : getMinerals()) {
            if ((m.getInitialAmount() >= 40) && !m.isBlocking()) {
                remainingResources.add(m);
            }
        }
        for (Geyser g : getGeysers()) {
            if ((g.getInitialAmount() >= 300) && !g.isBlocking()) {
                remainingResources.add(g);
            }
        }

        while (!remainingResources.isEmpty()) {
            // 1) Calculate the SearchBoundingBox (needless to search too far from the RemainingRessources):

            TilePosition topLeftResources = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
            TilePosition bottomRightResources = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
            for (Resource r : remainingResources) {
                ImmutablePair<TilePosition, TilePosition> pair1 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.getTopLeft());
                topLeftResources = pair1.getLeft();
                bottomRightResources = pair1.getRight();
                ImmutablePair<TilePosition, TilePosition> pair2 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.getBottomRight());
                topLeftResources = pair2.getLeft();
                bottomRightResources = pair2.getRight();
            }

            TilePosition topLeftSearchBoundingBox = topLeftResources.subtract(dimCC).subtract(new TilePosition(BwemExt.max_tiles_between_CommandCenter_and_resources, BwemExt.max_tiles_between_CommandCenter_and_resources));
            TilePosition bottomRightSearchBoundingBox = bottomRightResources.add(new TilePosition(1, 1)).add(new TilePosition(BwemExt.max_tiles_between_CommandCenter_and_resources, BwemExt.max_tiles_between_CommandCenter_and_resources));
            topLeftSearchBoundingBox = BwemExt.makePointFitToBoundingBox(topLeftSearchBoundingBox, getTopLeft(), getBottomRight().subtract(dimCC).add(new TilePosition(1, 1)));
            bottomRightSearchBoundingBox = BwemExt.makePointFitToBoundingBox(bottomRightSearchBoundingBox, getTopLeft(), getBottomRight().subtract(dimCC).add(new TilePosition(1, 1)));

            // 2) Mark the Tiles with their distances from each remaining Ressource (Potential Fields >= 0)
            for (Resource r : remainingResources)
            for (int dy = -dimCC.getY() - BwemExt.max_tiles_between_CommandCenter_and_resources; dy < r.getSize().getY() + dimCC.getY() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dy)
            for (int dx = -dimCC.getX() - BwemExt.max_tiles_between_CommandCenter_and_resources; dx < r.getSize().getX() + dimCC.getX() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dx) {
                TilePosition t = r.getTopLeft().add(new TilePosition(dx, dy));
                if (pMap.getData().getMapData().isValid(t)) {
                    Tile tile = pMap.getData().getTile(t, Check.NO_CHECK);
                    int dist = (BwemExt.distToRectangle(BwemExt.center(t), r.getTopLeft().toPosition(), r.getSize().toPosition()) + 16) / 32; //TODO: Replace 16 and 32 with TilePosition.SIZE_IN_PIXELS constant?
                    int score = Math.max(BwemExt.max_tiles_between_CommandCenter_and_resources + 3 - dist, 0);
                    if (r instanceof Geyser) { // somewhat compensates for Geyser alone vs the several minerals
                        score *= 3;
                    }
                    if (tile.getAreaId().equals(getId())) { // note the additive effect (assume tile.InternalData() is 0 at the begining)
                        ((TileImpl) tile).getInternalData().setValue(((TileImpl) tile).getInternalData().intValue() + score);
                    }
                }
            }

            // 3) Invalidate the 7 x 7 Tiles around each remaining Ressource (Starcraft rule)
            for (Resource r : remainingResources)
            for (int dy = -3; dy < r.getSize().getY() + 3; ++dy)
            for (int dx = -3; dx < r.getSize().getX() + 3; ++dx) {
                TilePosition t = r.getTopLeft().add(new TilePosition(dx, dy));
                if (pMap.getData().getMapData().isValid(t)) {
                    ((TileImpl) pMap.getData().getTile(t, Check.NO_CHECK)).getInternalData().setValue(-1);
                }
            }


            // 4) Search the best location inside the SearchBoundingBox:
            TilePosition bestLocation = null;
            int bestScore = 0;
            List<Mineral> blockingMinerals = new ArrayList<>();

            for (int y = topLeftSearchBoundingBox.getY(); y <= bottomRightSearchBoundingBox.getY(); ++y)
            for (int x = topLeftSearchBoundingBox.getX(); x <= bottomRightSearchBoundingBox.getX(); ++x) {
                int score = computeBaseLocationScore(new TilePosition(x, y));
                if (score > bestScore) {
                    if (validateBaseLocation(new TilePosition(x, y), blockingMinerals)) {
                        bestScore = score;
                        bestLocation = new TilePosition(x, y);
                    }
                }
            }

            // 5) Clear Tile::m_internalData (required due to our use of Potential Fields: see comments in 2))
            for (Resource r : remainingResources) {
                for (int dy = -dimCC.getY() - BwemExt.max_tiles_between_CommandCenter_and_resources; dy < r.getSize().getY() + dimCC.getY() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dy)
                for (int dx = -dimCC.getX() - BwemExt.max_tiles_between_CommandCenter_and_resources; dx < r.getSize().getX() + dimCC.getX() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dx) {
                    TilePosition t = r.getTopLeft().add(new TilePosition(dx, dy));
                    if (pMap.getData().getMapData().isValid(t)) {
                        ((TileImpl) pMap.getData().getTile(t, Check.NO_CHECK)).getInternalData().setValue(0);
                    }
                }
            }

            if (bestScore == 0) {
                break;
            }

            // 6) Create a new Base at bestLocation, assign to it the relevant ressources and remove them from RemainingRessources:
            List<Resource> assignedResources = new ArrayList<>();
            for (Resource r : remainingResources) {
                if (BwemExt.distToRectangle(r.getCenter(), bestLocation.toPosition(), dimCC.toPosition()) + 2 <= BwemExt.max_tiles_between_CommandCenter_and_resources * TilePosition.SIZE_IN_PIXELS) {
                    assignedResources.add(r);
                }
            }

            for (int i = 0; i < remainingResources.size(); ++i) {
                Resource r = remainingResources.get(i);
                if (assignedResources.contains(r)) {
                    remainingResources.remove(i--);
                }
            }

            if (assignedResources.isEmpty()) {
                break;
            }

            Bases.add(new Base(this, bestLocation, assignedResources, blockingMinerals));
        }
    }

    // Calculates the score >= 0 corresponding to the placement of a Base Command center at 'location'.
    // The more there are ressources nearby, the higher the score is.
    // The function assumes the distance to the nearby ressources has already been computed (in InternalData()) for each tile around.
    // The job is therefore made easier : just need to sum the InternalData() values.
    // Returns -1 if the location is impossible.
    private int computeBaseLocationScore(TilePosition location) {
        final TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        int sumScore = 0;
        for (int dy = 0; dy < dimCC.getY(); ++dy)
        for (int dx = 0; dx < dimCC.getX(); ++dx) {
            final Tile tile = getMap().getData().getTile(location.add(new TilePosition(dx, dy)), Check.NO_CHECK);
            if (!tile.isBuildable()) return -1;
            if (((TileImpl) tile).getInternalData().intValue() == -1) return -1; // The special value InternalData() == -1 means there is some ressource at maximum 3 tiles, which Starcraft rules forbid.
                                                                    // Unfortunately, this is guaranteed only for the ressources in this Area, which is the very reason of validateBaseLocation
            if (!tile.getAreaId().equals(getId())) return -1;
            if (tile.getNeutral() != null && (tile.getNeutral() instanceof StaticBuilding)) return -1;

            sumScore += ((TileImpl) tile).getInternalData().intValue();
        }

        return sumScore;
    }

    // Checks if 'location' is a valid location for the placement of a Base Command center.
    // If the location is valid except for the presence of Mineral patches of less than 9 (see Andromeda.scx),
    // the function returns true, and these minerals are reported in blockingMinerals
    // The function is intended to be called after computeBaseLocationScore, as it is more expensive.
    // See also the comments inside computeBaseLocationScore.
    private boolean validateBaseLocation(TilePosition location, List<Mineral> blockingMinerals) {
        Map pMap = getMap();
        TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        blockingMinerals.clear();

        for (int dy = -3; dy < dimCC.getY() + 3; ++dy)
        for (int dx = -3; dx < dimCC.getX() + 3; ++dx) {
            TilePosition t = location.add(new TilePosition(dx, dy));
            if (pMap.getData().getMapData().isValid(t)) {
                Tile tile = pMap.getData().getTile(t, Check.NO_CHECK);
                Neutral n = tile.getNeutral();
                if (n != null) {
                    if (n instanceof Geyser) {
                        return false;
                    } else if (n instanceof Mineral) {
                        Mineral m = (Mineral) n;
                        if (m.getInitialAmount() <= 8) {
                            blockingMinerals.add(m);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        // checks the distance to the bases already created:
        for (Base base : getBases()) {
            if (BwemExt.roundedDist(base.getLocation(), location) < BwemExt.min_tiles_between_Bases) {
                return false;
            }
        }

        return true;
    }

    // Returns Distances such that Distances[i] == ground_distance(start, targets[i]) in pixels
    // Note: same algorithm than Graph::computeDistances (derived from Dijkstra)
    private int[] computeDistances(final TilePosition start, final List<TilePosition> targets) {
        final int[] distances = new int[targets.size()];

        TileImpl.getStaticMarkable().unmarkAll();

        final Queue<Pair<Integer, TilePosition>> toVisit = new PriorityQueue<>(Comparator.comparingInt(a -> a.first)); // a priority queue holding the tiles to visit ordered by their distance to start.
                                                                                              //Using ArrayListValuedHashMap to substitute std::multimap since it sorts keys but not values.
        toVisit.offer(new Pair<>(0, start));

        int remainingTargets = targets.size();
        while (!toVisit.isEmpty()) {
            Pair<Integer, TilePosition> distanceAndTilePosition = toVisit.poll();
            final int currentDist = distanceAndTilePosition.first;
            final TilePosition current = distanceAndTilePosition.second;
            final Tile currentTile = getMap().getData().getTile(current, Check.NO_CHECK);
//            bwem_assert(currentTile.InternalData() == currentDist);
            if (!(((TileImpl) currentTile).getInternalData().intValue() == currentDist)) {
                throw new IllegalStateException("currentTile.InternalData().intValue()=" + ((TileImpl) currentTile).getInternalData().intValue() + ", currentDist=" + currentDist);
            }
            ((TileImpl) currentTile).getInternalData().setValue(0); // resets Tile::m_internalData for future usage
            currentTile.getMarkable().setMarked();

            for (int i = 0; i < targets.size(); ++i) {
                if (current.equals(targets.get(i))) {
                    distances[i] = (int) (0.5 + (currentDist * 32.0 /10000.0));
                    --remainingTargets;
                }
            }
            if (remainingTargets == 0) {
                break;
            }

            final TilePosition[] deltas = {
                new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(+1, -1),
                new TilePosition(-1,  0),                          new TilePosition(+1,  0),
                new TilePosition(-1, +1), new TilePosition(0, +1), new TilePosition(+1, +1)
            };
            for (final TilePosition delta : deltas) {
                final boolean diagonalMove = (delta.getX() != 0) && (delta.getY() != 0);
                final int newNextDist = currentDist + (diagonalMove ? 14142 : 10000);

                final TilePosition next = current.add(delta);
                if (getMap().getData().getMapData().isValid(next)) {
                    final Tile nextTile = getMap().getData().getTile(next, Check.NO_CHECK);
                    if (!nextTile.getMarkable().isMarked()) {
                        if (((TileImpl) nextTile).getInternalData().intValue() != 0) { // next already in toVisit
                            if (newNextDist < ((TileImpl) nextTile).getInternalData().intValue()) { // nextNewDist < nextOldDist
                                // To update next's distance, we need to remove-insert it from toVisit:
//                                bwem_assert(iNext != range.second);
                                final boolean removed = toVisit.remove(new Pair<>(((TileImpl) nextTile).getInternalData().intValue(), next));
                                if (!removed) {
                                    throw new IllegalStateException();
                                }
                                ((TileImpl) nextTile).getInternalData().setValue(newNextDist);
                                toVisit.offer(new Pair<>(newNextDist, next));
                            }
                        } else if ((nextTile.getAreaId().equals(getId())) || (nextTile.getAreaId().equals(new AreaId(-1)))) {
                            ((TileImpl) nextTile).getInternalData().setValue(newNextDist);
                            toVisit.offer(new Pair<>(newNextDist, next));
                        }
                    }
                }
            }
        }

//        bwem_assert(!remainingTargets);
        if (!(remainingTargets == 0)) {
            throw new IllegalStateException();
        }

        for (Pair<Integer, TilePosition> distanceAndTilePosition : toVisit) {
            ((TileImpl) getMap().getData().getTile(distanceAndTilePosition.second, Check.NO_CHECK)).getInternalData().setValue(0);
        }

        return distances;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Area)) {
            return false;
        } else {
            Area that = (Area) object;
            return (this .id.equals(that .id));
        }
    }

    @Override
    public int hashCode() {
        return this .id.hashCode();
    }


}
