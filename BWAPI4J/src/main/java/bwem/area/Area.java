package bwem.area;

import bwem.Base;
import bwem.CheckMode;
import bwem.ChokePoint;
import bwem.Graph;
import bwem.Markable;
import bwem.StaticMarkable;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.map.AdvancedData;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import bwem.typedef.Altitude;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.Resource;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
import bwem.util.Utils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

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
    private final WalkPosition walkPositionWithHighestAltitude;
    private TilePosition topLeft = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private TilePosition bottomRight = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private Altitude highestAltitude;
    private final int miniTileCount;
    private int tileCount = 0;
    private int buildableTileCount = 0; /* Set and later incremented but not used in BWEM 1.4.1. Remains for portability consistency. */
    private int highGroundTileCount = 0;
    private int veryHighGroundTileCount = 0;
    private final AbstractMap<Area, List<ChokePoint>> chokePointsByArea = new ConcurrentHashMap<>();
    private final List<Area> accessibleNeighbors = new ArrayList<>();
    private final List<ChokePoint> chokePoints = new ArrayList<>();
    private final List<Mineral> minerals = new ArrayList<>();
    private final List<Geyser> geysers = new ArrayList<>();
	private final List<Base> bases = new ArrayList<>();

    public Area(final Graph graph, final AreaId areaId, final WalkPosition top, final int miniTileCount) {
        this.markable = new Markable(Area.staticMarkable);

        this.graph = graph;
        this.id = areaId;
        this.walkPositionWithHighestAltitude = top;
        this.miniTileCount = miniTileCount;

//        bwem_assert(areaId > 0);
        if (!(areaId.intValue() > 0)) {
            throw new IllegalArgumentException();
        }

        final MiniTile topMiniTile = getMap().getData().getMiniTile(top);
//        bwem_assert(topMiniTile.AreaId() == areaId);
        if (!(topMiniTile.getAreaId().equals(areaId))) {
            throw new IllegalStateException("assert failed: topMiniTile.AreaId().equals(areaId): expected: " + topMiniTile.getAreaId().intValue() + ", actual: " + areaId.intValue());
        }

        this.highestAltitude = new Altitude(topMiniTile.getAltitude());
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
        return this.graph;
    }

	// Unique id > 0 of this Area. Range = 1 .. Map::areas().size()
	// this == Map::getArea(id())
	// id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.
	// Area::ids are guaranteed to remain unchanged.
    public AreaId getId() {
        return this.id;
    }

	// Unique id > 0 of the group of areas which are accessible from this Area.
	// For each pair (a, b) of areas: a->groupId() == b->groupId()  <==>  a->accessibleFrom(b)
	// A groupId uniquely identifies a maximum set of mutually accessible areas, that is, in the absence of blocking getChokePoints, a continent.
    public GroupId getGroupId() {
        return this.groupId;
    }

    public void setGroupId(final GroupId gid) {
//        bwem_assert(gid >= 1);
        if (!(gid.intValue() >= 1)) {
            throw new IllegalArgumentException();
        }
        this.groupId = gid;
    }

    public TilePosition getTopLeft() {
        return this.topLeft;
    }

    public TilePosition getBottomRight() {
        return this.bottomRight;
    }

    public TilePosition getBoundingBoxSize() {
        return this.bottomRight.subtract(this.topLeft).add(new TilePosition(1, 1));
    }

    // Position of the MiniTile with the highest Altitude() value.
    public WalkPosition getWalkPositionWithHighestAltitude() {
        return this.walkPositionWithHighestAltitude;
    }

	// Returns Map::GetMiniTile(top()).Altitude().
    public Altitude getHighestAltitude() {
        return this.highestAltitude;
    }

    /**
     * This most accurately defines the size of this Area.
     */
    public int getMiniTileCount() {
        return this.miniTileCount;
    }

	// Returns the percentage of low ground Tiles in this Area.
    public int getLowGroundPercentage() {
        return (((this.tileCount - this.highGroundTileCount - this.veryHighGroundTileCount) * 100) / this.tileCount);
    }

	// Returns the percentage of high ground Tiles in this Area.
    public int getHighGroundPercentage() {
        return ((this.highGroundTileCount * 100) / this.tileCount);
    }

	// Returns the percentage of very high ground Tiles in this Area.
    public int getVeryHighGroundPercentage() {
        return ((this.veryHighGroundTileCount * 100) / tileCount);
    }

	// Returns the getChokePoints between this Area and the neighbouring ones.
	// Note: if there are no neighbouring areas, then an empty set is returned.
	// Note there may be more getChokePoints returned than the number of neighbouring areas, as there may be several getChokePoints between two areas (Cf. getChokePoints(const Area * pArea)).
    public List<ChokePoint> getChokePoints() {
        return this.chokePoints;
    }

	// Returns the getChokePoints between this Area and pArea.
	// Assumes pArea is a neighbour of this Area, i.e. getChokePointsByArea().find(pArea) != getChokePointsByArea().end()
	// Note: there is always at least one ChokePoint between two neighbouring areas.
    public List<ChokePoint> getChokePoints(final Area area) {
        final List<ChokePoint> ret = this.chokePointsByArea.get(area);
//        bwem_assert(it != getChokePointsByArea.end());
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }

	// Returns the getChokePoints of this Area grouped by neighbouring areas
	// Note: if there are no neighbouring areas, than an empty set is returned.
	public AbstractMap<Area, List<ChokePoint>> getChokePointsByArea() {
        return this.chokePointsByArea;
    }

	// Returns the accessible neighbouring areas.
	// The accessible neighbouring areas are a subset of the neighbouring areas (the neighbouring areas can be iterated using getChokePointsByArea()).
	// Two neighbouring areas are accessible from each over if at least one the getChokePoints they share is not blocked (Cf. ChokePoint::blocked).
	public List<Area> getAccessibleNeighbors() {
        return this.accessibleNeighbors;
    }

	// Returns whether this Area is accessible from pArea, that is, if they share the same groupId().
	// Note: accessibility is always symmetrical.
	// Note: even if a and b are neighbouring areas,
	//       we can have: a->accessibleFrom(b)
	//       and not:     contains(a->AccessibleNeighbours(), b)
	// See also groupId()
	public boolean isAccessibleFrom(final Area area) {
        return getGroupId().equals(area.getGroupId());
    }

	// Returns the minerals contained in this Area.
	// Note: only a call to Map::onMineralDestroyed(BWAPI::unit u) may change the result (by removing eventually one element).
	public List<Mineral> getMinerals() {
        return this.minerals;
    }

	// Returns the geysers contained in this Area.
	// Note: the result will remain unchanged.
	public List<Geyser> getGeysers() {
        return this.geysers;
    }

	// Returns the bases contained in this Area.
	// Note: the result will remain unchanged.
	public List<Base> getBases() {
        return this.bases;
    }

    public Map getMap() {
        return this.graph.getMap();
    }

    public void addChokePoints(final Area area, final List<ChokePoint> chokePoints) {
//        bwem_assert(!getChokePointsByArea[pArea] && pChokePoints);
        if (!(this.chokePointsByArea.get(area) == null && chokePoints != null)) {
            throw new IllegalArgumentException();
        }

        this.chokePointsByArea.put(area, chokePoints);

        this.chokePoints.addAll(chokePoints);
    }

	public void addMineral(final Mineral mineral) {
//        bwem_assert(pMineral && !contains (minerals, pMineral));
        if (!(mineral != null && !this.minerals.contains(mineral))) {
            throw new IllegalStateException();
        }
        this.minerals.add(mineral);
    }

	public void addGeyser(final Geyser geyser) {
//        bwem_assert(pGeyser && !contains (geysers, pGeyser));
        if (!(geyser != null && !this.geysers.contains(geyser))) {
            throw new IllegalStateException();
        }
        this.geysers.add(geyser);
    }

    // Called for each tile t of this Area
    public void addTileInformation(final TilePosition tilePosition, final Tile tile) {
        ++this.tileCount;

        if (tile.isBuildable()) ++this.buildableTileCount;

        if (tile.getGroundHeight() == Tile.GroundHeight.HIGH_GROUND) {
            ++this.highGroundTileCount;
        } else if (tile.getGroundHeight() == Tile.GroundHeight.VERY_HIGH_GROUND) {
            ++this.veryHighGroundTileCount;
        }

        if (tilePosition.getX() < this.topLeft.getX()) {
            this.topLeft = new TilePosition(tilePosition.getX(), this.topLeft.getY());
        }
        if (tilePosition.getY() < this.topLeft.getY()) {
            this.topLeft = new TilePosition (this.topLeft.getX(), tilePosition.getY());
        }
        if (tilePosition.getX() > this.bottomRight.getX()) {
            this.bottomRight = new TilePosition(tilePosition.getX(), this.bottomRight.getY());
        }
        if (tilePosition.getY() > this.bottomRight.getY()) {
            this.bottomRight = new TilePosition (this.bottomRight.getX(), tilePosition.getY());
        }
    }

    // Called after addTileInformation(t) has been called for each tile t of this Area
    public void postCollectInformation() {
        /* Do nothing. This function is blank in BWEM 1.4.1 */
    }

    public void onMineralDestroyed(final Mineral mineral) {
//        bwem_assert(mineral);
        if (mineral == null) {
            throw new IllegalArgumentException();
        }

        this.minerals.remove(mineral);

        // let's examine the bases even if mineral was not found in this Area,
        // which could arise if minerals were allowed to be assigned to neighbouring areas.
        for (final Base base : getBases()) {
            base.onMineralDestroyed(mineral);
        }
    }

    public int[] computeDistances(final ChokePoint startCP, final List<ChokePoint> targetCPs) {
//        bwem_assert(!contains(targetCPs, startCP));
        if (targetCPs.contains(startCP)) {
            throw new IllegalStateException();
        }

        final TilePosition start = getMap().breadthFirstSearch(
            startCP.positionOfNodeInArea(ChokePoint.Node.MIDDLE, this).toTilePosition(),
                // findCond
                args -> {
                    final Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        final Tile tile = (Tile) ttile;
                        return tile.getAreaId().equals(getId());
                    } else {
                        throw new IllegalArgumentException();
                    }
                },
                // visitCond
                args -> true
        );

        final List<TilePosition> targets = new ArrayList<>();
        for (final ChokePoint cp : targetCPs) {
            final TilePosition t = getMap().breadthFirstSearch(
                cp.positionOfNodeInArea(ChokePoint.Node.MIDDLE, this).toTilePosition(),
                    // findCond
                    args -> {
                        final Object ttile = args[0];
                        if (ttile instanceof Tile) {
                            final Tile tile = (Tile) ttile;
                            return (tile.getAreaId().equals(getId()));
                        } else {
                            throw new IllegalArgumentException();
                        }
                    },
                    // visitCond
                    args -> true
            );
            targets.add(t);
        }

        return computeDistances(start, targets);
    }

    public void updateAccessibleNeighbors() {
        this.accessibleNeighbors.clear();
        for (final Area area : getChokePointsByArea().keySet()) {
            for (final ChokePoint cp : getChokePointsByArea().get(area)) {
                if (!cp.isBlocked()) {
                    this.accessibleNeighbors.add(area);
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
    public void createBases(final AdvancedData mapAdvancedData) {
        final TilePosition resourceDepotDimensions = UnitType.Terran_Command_Center.tileSize();

        final List<Resource> remainingResources = new ArrayList<>();

        for (final Mineral mineral : getMinerals()) {
            if ((mineral.getInitialAmount() >= 40) && !mineral.isBlocking()) {
                remainingResources.add(mineral);
            }
        }

        for (final Geyser geyser : getGeysers()) {
            if ((geyser.getInitialAmount() >= 300) && !geyser.isBlocking()) {
                remainingResources.add(geyser);
            }
        }

        while (!remainingResources.isEmpty()) {
            // 1) Calculate the SearchBoundingBox (needless to search too far from the remainingResources):
            TilePosition topLeftResources = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
            TilePosition bottomRightResources = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
            for (final Resource r : remainingResources) {
                final ImmutablePair<TilePosition, TilePosition> pair1 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.getTopLeft());
                topLeftResources = pair1.getLeft();
                bottomRightResources = pair1.getRight();

                final ImmutablePair<TilePosition, TilePosition> pair2 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.getBottomRight());
                topLeftResources = pair2.getLeft();
                bottomRightResources = pair2.getRight();
            }

            final TilePosition dimensionsBetweenResourceDepotAndResources = new TilePosition(BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES, BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES);
            TilePosition topLeftSearchBoundingBox = topLeftResources.subtract(resourceDepotDimensions).subtract(dimensionsBetweenResourceDepotAndResources);
            TilePosition bottomRightSearchBoundingBox = bottomRightResources.add(new TilePosition(1, 1)).add(dimensionsBetweenResourceDepotAndResources);
            topLeftSearchBoundingBox = BwemExt.makePointFitToBoundingBox(topLeftSearchBoundingBox, getTopLeft(), getBottomRight().subtract(resourceDepotDimensions).add(new TilePosition(1, 1)));
            bottomRightSearchBoundingBox = BwemExt.makePointFitToBoundingBox(bottomRightSearchBoundingBox, getTopLeft(), getBottomRight().subtract(resourceDepotDimensions).add(new TilePosition(1, 1)));

            // 2) Mark the Tiles with their distances from each remaining Ressource (Potential Fields >= 0)
            for (final Resource r : remainingResources)
            for (int dy = -resourceDepotDimensions.getY() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dy < r.getSize().getY() + resourceDepotDimensions.getY() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dy)
            for (int dx = -resourceDepotDimensions.getX() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dx < r.getSize().getX() + resourceDepotDimensions.getX() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dx) {
                final TilePosition deltaTilePosition = r.getTopLeft().add(new TilePosition(dx, dy));
                if (mapAdvancedData.getMapData().isValid(deltaTilePosition)) {
                    final Tile tile = mapAdvancedData.getTile(deltaTilePosition, CheckMode.NO_CHECK);
                    int dist = (BwemExt.distToRectangle(BwemExt.center(deltaTilePosition), r.getTopLeft().toPosition(), r.getSize().toPosition()) + (TilePosition.SIZE_IN_PIXELS / 2)) / TilePosition.SIZE_IN_PIXELS;
                    int score = Math.max(BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES + 3 - dist, 0);
                    if (r instanceof Geyser) {
                        // somewhat compensates for Geyser alone vs the several minerals
                        score *= 3;
                    }
                    if (tile.getAreaId().equals(getId())) {
                        // note the additive effect (assume tile.InternalData() is 0 at the beginning)
                        ((TileImpl) tile).getInternalData().setValue(((TileImpl) tile).getInternalData().intValue() + score);
                    }
                }
            }

            // 3) Invalidate the 7 x 7 Tiles around each remaining Resource (Starcraft rule)
            for (final Resource r : remainingResources)
            for (int dy = -3; dy < r.getSize().getY() + 3; ++dy)
            for (int dx = -3; dx < r.getSize().getX() + 3; ++dx) {
                final TilePosition deltaTilePosition = r.getTopLeft().add(new TilePosition(dx, dy));
                if (mapAdvancedData.getMapData().isValid(deltaTilePosition)) {
                    final Tile tileToUpdate = mapAdvancedData.getTile(deltaTilePosition, CheckMode.NO_CHECK);
                    ((TileImpl) tileToUpdate).getInternalData().setValue(-1);
                }
            }


            // 4) Search the best location inside the SearchBoundingBox:
            TilePosition bestLocation = null;
            int bestScore = 0;
            final List<Mineral> blockingMinerals = new ArrayList<>();

            for (int y = topLeftSearchBoundingBox.getY(); y <= bottomRightSearchBoundingBox.getY(); ++y)
            for (int x = topLeftSearchBoundingBox.getX(); x <= bottomRightSearchBoundingBox.getX(); ++x) {
                final int score = computeBaseLocationScore(mapAdvancedData, new TilePosition(x, y));
                if (score > bestScore) {
                    if (validateBaseLocation(mapAdvancedData, new TilePosition(x, y), blockingMinerals)) {
                        bestScore = score;
                        bestLocation = new TilePosition(x, y);
                    }
                }
            }

            // 5) Clear Tile::m_internalData (required due to our use of Potential Fields: see comments in 2))
            for (Resource r : remainingResources) {
                for (int dy = -resourceDepotDimensions.getY() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dy < r.getSize().getY() + resourceDepotDimensions.getY() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dy)
                for (int dx = -resourceDepotDimensions.getX() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dx < r.getSize().getX() + resourceDepotDimensions.getX() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dx) {
                    final TilePosition deltaTilePosition = r.getTopLeft().add(new TilePosition(dx, dy));
                    if (mapAdvancedData.getMapData().isValid(deltaTilePosition)) {
                        final Tile tileToUpdate = mapAdvancedData.getTile(deltaTilePosition, CheckMode.NO_CHECK);
                        ((TileImpl) tileToUpdate).getInternalData().setValue(0);
                    }
                }
            }

            if (bestScore == 0) {
                break;
            }

            // 6) Create a new Base at bestLocation, assign to it the relevant ressources and remove them from RemainingRessources:
            final List<Resource> assignedResources = new ArrayList<>();
            for (final Resource r : remainingResources) {
                if (BwemExt.distToRectangle(r.getCenter(), bestLocation.toPosition(), resourceDepotDimensions.toPosition()) + 2 <= BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES * TilePosition.SIZE_IN_PIXELS) {
                    assignedResources.add(r);
                }
            }

//            for (int i = 0; i < remainingResources.size(); ++i) {
//                Resource r = remainingResources.get(i);
//                if (assignedResources.contains(r)) {
//                    remainingResources.remove(i--);
//                }
//            }
            Utils.reallyRemoveIf(remainingResources, args -> {
                final Object tresource = args[0];
                if (tresource instanceof Resource) {
                    final Resource resource = (Resource) tresource;
                    return assignedResources.contains(resource);
                } else {
                    throw new IllegalStateException();
                }
            });

            if (assignedResources.isEmpty()) {
                break;
            }

            this.bases.add(new Base(this, bestLocation, assignedResources, blockingMinerals));
        }
    }

    // Calculates the score >= 0 corresponding to the placement of a Base Command center at 'location'.
    // The more there are ressources nearby, the higher the score is.
    // The function assumes the distance to the nearby ressources has already been computed (in InternalData()) for each tile around.
    // The job is therefore made easier : just need to sum the InternalData() values.
    // Returns -1 if the location is impossible.
    private int computeBaseLocationScore(final AdvancedData mapAdvancedData, final TilePosition location) {
        final TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        int sumScore = 0;
        for (int dy = 0; dy < dimCC.getY(); ++dy)
        for (int dx = 0; dx < dimCC.getX(); ++dx) {
            final Tile tile = mapAdvancedData.getTile(location.add(new TilePosition(dx, dy)), CheckMode.NO_CHECK);
            if (!tile.isBuildable()) {
                return -1;
            }
            if (((TileImpl) tile).getInternalData().intValue() == -1) {
                // The special value InternalData() == -1 means there is some ressource at maximum 3 tiles, which Starcraft rules forbid.
                // Unfortunately, this is guaranteed only for the ressources in this Area, which is the very reason of validateBaseLocation
                return -1;
            }
            if (!tile.getAreaId().equals(getId())){
                return -1;
            }
            if (tile.getNeutral() != null && (tile.getNeutral() instanceof StaticBuilding)) {
                return -1;
            }

            sumScore += ((TileImpl) tile).getInternalData().intValue();
        }

        return sumScore;
    }

    // Checks if 'location' is a valid location for the placement of a Base Command center.
    // If the location is valid except for the presence of Mineral patches of less than 9 (see Andromeda.scx),
    // the function returns true, and these minerals are reported in blockingMinerals
    // The function is intended to be called after computeBaseLocationScore, as it is more expensive.
    // See also the comments inside computeBaseLocationScore.
    private boolean validateBaseLocation(final AdvancedData mapAdvancedData, final TilePosition location, final List<Mineral> blockingMinerals) {
        final TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        blockingMinerals.clear();

        for (int dy = -3; dy < dimCC.getY() + 3; ++dy)
        for (int dx = -3; dx < dimCC.getX() + 3; ++dx) {
            final TilePosition deltaLocation = location.add(new TilePosition(dx, dy));
            if (mapAdvancedData.getMapData().isValid(deltaLocation)) {
                final Tile deltaTile = mapAdvancedData.getTile(deltaLocation, CheckMode.NO_CHECK);
                final Neutral deltaTileNeutral = deltaTile.getNeutral();
                if (deltaTileNeutral != null) {
                    if (deltaTileNeutral instanceof Geyser) {
                        return false;
                    } else if (deltaTileNeutral instanceof Mineral) {
                        final Mineral deltaTileMineral = (Mineral) deltaTileNeutral;
                        if (deltaTileMineral.getInitialAmount() <= 8) {
                            blockingMinerals.add(deltaTileMineral);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        // checks the distance to the bases already created:
        for (final Base base : getBases()) {
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

        final Queue<ImmutablePair<Integer, TilePosition>> toVisit = new PriorityQueue<>(Comparator.comparingInt(ImmutablePair::getLeft)); // a priority queue holding the tiles to visit ordered by their distance to start.
        toVisit.offer(new ImmutablePair<>(0, start));

        int remainingTargets = targets.size();
        while (!toVisit.isEmpty()) {
            final ImmutablePair<Integer, TilePosition> distanceAndTilePosition = toVisit.poll();
            final int currentDist = distanceAndTilePosition.getLeft();
            final TilePosition current = distanceAndTilePosition.getRight();
            final Tile currentTile = getMap().getData().getTile(current, CheckMode.NO_CHECK);
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
                    final Tile nextTile = getMap().getData().getTile(next, CheckMode.NO_CHECK);
                    if (!nextTile.getMarkable().isMarked()) {
                        if (((TileImpl) nextTile).getInternalData().intValue() != 0) { // next already in toVisit
                            if (newNextDist < ((TileImpl) nextTile).getInternalData().intValue()) { // nextNewDist < nextOldDist
                                // To update next's distance, we need to remove-insert it from toVisit:
//                                bwem_assert(iNext != range.second);
                                final boolean removed = toVisit.remove(new ImmutablePair<>(((TileImpl) nextTile).getInternalData().intValue(), next));
                                if (!removed) {
                                    throw new IllegalStateException();
                                }
                                ((TileImpl) nextTile).getInternalData().setValue(newNextDist);
                                toVisit.offer(new ImmutablePair<>(newNextDist, next));
                            }
                        } else if ((nextTile.getAreaId().equals(getId())) || (nextTile.getAreaId().equals(new AreaId(-1)))) {
                            ((TileImpl) nextTile).getInternalData().setValue(newNextDist);
                            toVisit.offer(new ImmutablePair<>(newNextDist, next));
                        }
                    }
                }
            }
        }

//        bwem_assert(!remainingTargets);
        if (!(remainingTargets == 0)) {
            throw new IllegalStateException();
        }

        for (final ImmutablePair<Integer, TilePosition> distanceAndTilePosition : toVisit) {
            final TileImpl tileToUpdate = ((TileImpl) getMap().getData().getTile(distanceAndTilePosition.getRight(), CheckMode.NO_CHECK));
            tileToUpdate.getInternalData().setValue(0);
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
