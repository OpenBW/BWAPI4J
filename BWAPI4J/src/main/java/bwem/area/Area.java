package bwem.area;

import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.typedef.Altitude;
import bwem.Base;
import bwem.check_t;
import bwem.ChokePoint;
import bwem.Graph;
import bwem.Markable;
import bwem.typedef.Pred;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.Resource;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;

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

public final class Area extends Markable<Area> {

    private final Graph m_pGraph;
    private final AreaId m_id;
    private GroupId m_groupId = new GroupId(0);
    private WalkPosition m_top;
    private TilePosition m_topLeft = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private TilePosition m_bottomRight = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private Altitude m_maxAltitude;
    private int m_miniTiles;
    private int m_tiles = 0;
    private int m_buildableTiles = 0; /* Set and later incremented but not used in BWEM 1.4.1. Remains for portability consistency. */
    private int m_highGroundTiles = 0;
    private int m_veryHighGroundTiles = 0;
    private AbstractMap<Area, List<ChokePoint>> m_ChokePointsByArea = new ConcurrentHashMap<>();
    private List<Area> m_AccessibleNeighbors = new ArrayList<>();
    private List<ChokePoint> m_ChokePoints = new ArrayList<>();
    private List<Mineral> m_Minerals = new ArrayList<>();
    private List<Geyser> m_Geysers = new ArrayList<>();
	private List<Base> m_Bases = new ArrayList<>();

    public Area(Graph pGraph, AreaId areaId, WalkPosition top, int miniTiles) {
        m_pGraph = pGraph;
        m_id = areaId;
        m_top = top;
        m_miniTiles = miniTiles;

//        bwem_assert(areaId > 0);
        if (!(areaId.intValue() > 0)) {
            throw new IllegalArgumentException();
        }

        MiniTile topMiniTile = GetMap().GetMiniTile(top);
//        bwem_assert(topMiniTile.AreaId() == areaId);
        if (!(topMiniTile.AreaId().equals(areaId))) {
            throw new IllegalStateException("assert failed: topMiniTile.AreaId().equals(areaId): expected: " + topMiniTile.AreaId().intValue() + ", actual: " + areaId.intValue());
        }

        m_maxAltitude = new Altitude(topMiniTile.Altitude());
    }

    /**
     * Returns the internal Graph object. Not used in BWEM 1.4.1. Remains for portability consistency.
     */
    private Graph GetGraph() {
        return m_pGraph;
    }

	// Unique id > 0 of this Area. Range = 1 .. Map::Areas().size()
	// this == Map::GetArea(Id())
	// Id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.
	// Area::ids are guaranteed to remain unchanged.
    public AreaId Id() {
        return m_id;
    }

	// Unique id > 0 of the group of Areas which are accessible from this Area.
	// For each pair (a, b) of Areas: a->GroupId() == b->GroupId()  <==>  a->AccessibleFrom(b)
	// A groupId uniquely identifies a maximum set of mutually accessible Areas, that is, in the absence of blocking ChokePoints, a continent.
    public GroupId GroupId() {
        return m_groupId;
    }

    public void SetGroupId(GroupId gid) {
//        bwem_assert(gid >= 1);
        if (!(gid.intValue() >= 1)) {
            throw new IllegalArgumentException();
        }
        m_groupId = gid;
    }

    public TilePosition TopLeft() {
        return m_topLeft;
    }

    public TilePosition BottomRight() {
        return m_bottomRight;
    }

    public TilePosition BoundingBoxSize() {
        return m_bottomRight.subtract(m_topLeft).add(new TilePosition(1, 1));
    }

    // Position of the MiniTile with the highest Altitude() value.
    public WalkPosition Top() {
        return m_top;
    }

	// Returns Map::GetMiniTile(Top()).Altitude().
    public Altitude MaxAltitude() {
        return m_maxAltitude;
    }

	// Returns the number of MiniTiles in this Area.
	// This most accurately defines the size of this Area.
    public int MiniTiles() {
        return m_miniTiles;
    }

	// Returns the percentage of low ground Tiles in this Area.
    public int LowGroundPercentage() {
        return (((m_tiles - m_highGroundTiles - m_veryHighGroundTiles) * 100) / m_tiles);
    }

	// Returns the percentage of high ground Tiles in this Area.
    public int HighGroundPercentage() {
        return ((m_highGroundTiles * 100) / m_tiles);
    }

	// Returns the percentage of very high ground Tiles in this Area.
    public int VeryHighGroundPercentage() {
        return ((m_veryHighGroundTiles * 100) / m_tiles);
    }

	// Returns the ChokePoints between this Area and the neighbouring ones.
	// Note: if there are no neighbouring Areas, then an empty set is returned.
	// Note there may be more ChokePoints returned than the number of neighbouring Areas, as there may be several ChokePoints between two Areas (Cf. ChokePoints(const Area * pArea)).
    public List<ChokePoint> ChokePoints() {
        return m_ChokePoints;
    }

	// Returns the ChokePoints between this Area and pArea.
	// Assumes pArea is a neighbour of this Area, i.e. ChokePointsByArea().find(pArea) != ChokePointsByArea().end()
	// Note: there is always at least one ChokePoint between two neighbouring Areas.
    public List<ChokePoint> ChokePoints(Area pArea) {
        List<ChokePoint> ret = m_ChokePointsByArea.get(pArea);
//        bwem_assert(it != m_ChokePointsByArea.end());
        if (!(ret != null)) {
            throw new IllegalArgumentException();
        }
        return ret;
    }

	// Returns the ChokePoints of this Area grouped by neighbouring Areas
	// Note: if there are no neighbouring Areas, than an empty set is returned.
	public AbstractMap<Area, List<ChokePoint>> ChokePointsByArea() {
        return m_ChokePointsByArea;
    }

	// Returns the accessible neighbouring Areas.
	// The accessible neighbouring Areas are a subset of the neighbouring Areas (the neighbouring Areas can be iterated using ChokePointsByArea()).
	// Two neighbouring Areas are accessible from each over if at least one the ChokePoints they share is not Blocked (Cf. ChokePoint::Blocked).
	public List<Area> AccessibleNeighbours() {
        return m_AccessibleNeighbors;
    }

	// Returns whether this Area is accessible from pArea, that is, if they share the same GroupId().
	// Note: accessibility is always symmetrical.
	// Note: even if a and b are neighbouring Areas,
	//       we can have: a->AccessibleFrom(b)
	//       and not:     contains(a->AccessibleNeighbours(), b)
	// See also GroupId()
	public boolean AccessibleFrom(Area pArea) {
        return (GroupId().equals(pArea.GroupId()));
    }

	// Returns the Minerals contained in this Area.
	// Note: only a call to Map::OnMineralDestroyed(BWAPI::Unit u) may change the result (by removing eventually one element).
	public List<Mineral> Minerals() {
        return m_Minerals;
    }

	// Returns the Geysers contained in this Area.
	// Note: the result will remain unchanged.
	public List<Geyser> Geysers() {
        return m_Geysers;
    }

	// Returns the Bases contained in this Area.
	// Note: the result will remain unchanged.
	public List<Base> Bases() {
        return m_Bases;
    }

    public Map GetMap() {
        return m_pGraph.GetMap();
    }

    public void AddChokePoints(Area pArea, List<ChokePoint> pChokePoints) {
//        bwem_assert(!m_ChokePointsByArea[pArea] && pChokePoints);
        if (!(m_ChokePointsByArea.get(pArea) == null && pChokePoints != null)) {
            throw new IllegalArgumentException();
        }

        m_ChokePointsByArea.put(pArea, pChokePoints);

        for (ChokePoint cp : pChokePoints) {
            m_ChokePoints.add(cp);
        }
    }

	public void AddMineral(Mineral pMineral) {
//        bwem_assert(pMineral && !contains(m_Minerals, pMineral));
        if (!(pMineral != null && !m_Minerals.contains(pMineral))) {
            throw new IllegalStateException();
        }
        m_Minerals.add(pMineral);
    }

	public void AddGeyser(Geyser pGeyser) {
//        bwem_assert(pGeyser && !contains(m_Geysers, pGeyser));
        if (!(pGeyser != null && !m_Geysers.contains(pGeyser))) {
            throw new IllegalStateException();
        }
        m_Geysers.add(pGeyser);
    }

    // Called for each tile t of this Area
    public void AddTileInformation(TilePosition t, Tile tile) {
        ++m_tiles;
        if (tile.Buildable()) ++m_buildableTiles;
        if (tile.GroundHeight() == 1) ++m_highGroundTiles;
        if (tile.GroundHeight() == 2) ++m_veryHighGroundTiles;

        if (t.getX() < m_topLeft.getX()) m_topLeft = new TilePosition(t.getX(), m_topLeft.getY());
        if (t.getY() < m_topLeft.getY()) m_topLeft = new TilePosition(m_topLeft.getX(), t.getY());
        if (t.getX() > m_bottomRight.getX()) m_bottomRight = new TilePosition(t.getX(), m_bottomRight.getY());
        if (t.getY() > m_bottomRight.getY()) m_bottomRight = new TilePosition(m_bottomRight.getX(), t.getY());
    }

    public void OnMineralDestroyed(Mineral pMineral) {
//        bwem_assert(pMineral);
        if (!(pMineral != null)) {
            throw new IllegalArgumentException();
        }

        m_Minerals.remove(pMineral);

        // let's examine the bases even if pMineral was not found in this Area,
        // which could arise if Minerals were allowed to be assigned to neighbouring Areas.
        for (Base base : Bases()) {
            base.OnMineralDestroyed(pMineral);
        }
    }

    // Called after AddTileInformation(t) has been called for each tile t of this Area
    public void PostCollectInformation() {
        /* Do nothing. Empty in BWEM 1.4.1 */
    }

    public List<Integer> ComputeDistances(ChokePoint pStartCP, List<ChokePoint> TargetCPs) {
//        bwem_assert(!contains(TargetCPs, pStartCP));
        if (!(!TargetCPs.contains(pStartCP))) {
            throw new IllegalStateException();
        }

        TilePosition start = GetMap().BreadthFirstSearch(
            pStartCP.PosInArea(ChokePoint.Node.middle, this).toPosition().toTilePosition(),
            new Pred() { // findCond
                @Override
                public boolean isTrue(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        Tile tile = (Tile) ttile;
                        return tile.AreaId().equals(Id());
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            },
            new Pred() { // visitCond
                @Override
                public boolean isTrue(Object... args) {
                    return true;
                }
            }
        );

        List<TilePosition> Targets = new ArrayList<>();
        for (ChokePoint cp : TargetCPs) {
            TilePosition t = GetMap().BreadthFirstSearch(
                cp.PosInArea(ChokePoint.Node.middle, this).toPosition().toTilePosition(),
                new Pred() { // findCond
                    @Override
                    public boolean isTrue(Object... args) {
                        Object ttile = args[0];
                        if (ttile instanceof Tile) {
                            Tile tile = (Tile) ttile;
                            return (tile.AreaId().equals(Id()));
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                },
                new Pred() { // visitCond
                    @Override
                    public boolean isTrue(Object... args) {
                        return true;
                    }
                }
            );
            Targets.add(t);
        }

        return ComputeDistances(start, Targets);
    }

    public void UpdateAccessibleNeighbors() {
        m_AccessibleNeighbors.clear();
        for (Area area : ChokePointsByArea().keySet())
        for (ChokePoint cp : ChokePointsByArea().get(area)) {
            if (!cp.Blocked()) {
                m_AccessibleNeighbors.add(area);
                break;
            }
        }
    }

    // Fills in m_Bases with good locations in this Area.
    // The algorithm repeatedly searches the best possible location L (near ressources)
    // When it finds one, the nearby ressources are assigned to L, which makes the remaining ressources decrease.
    // This causes the algorithm to always terminate due to the lack of remaining ressources.
    // To efficiently compute the distances to the ressources, with use Potiential Fields in the InternalData() value of the Tiles.
    public void CreateBases() {
        TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();
        Map pMap = GetMap();

        // Initialize the RemainingRessources with all the Minerals and Geysers in this Area satisfying some conditions:
        List<Resource> RemainingResources = new ArrayList<>();
        for (Mineral m : Minerals()) {
            if ((m.InitialAmount() >= 40) && !m.Blocking()) {
                RemainingResources.add(m);
            }
        }
        for (Geyser g : Geysers()) {
            if ((g.InitialAmount() >= 300) && !g.Blocking()) {
                RemainingResources.add(g);
            }
        }

        while (!RemainingResources.isEmpty()) {
            // 1) Calculate the SearchBoundingBox (needless to search too far from the RemainingRessources):

            TilePosition topLeftResources = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
            TilePosition bottomRightResources = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
            for (Resource r : RemainingResources) {
                ImmutablePair<TilePosition, TilePosition> pair1 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.TopLeft());
                topLeftResources = pair1.getLeft();
                bottomRightResources = pair1.getRight();
                ImmutablePair<TilePosition, TilePosition> pair2 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.BottomRight());
                topLeftResources = pair2.getLeft();
                bottomRightResources = pair2.getRight();
            }

            TilePosition topLeftSearchBoundingBox = topLeftResources.subtract(dimCC).subtract(new TilePosition(BwemExt.max_tiles_between_CommandCenter_and_resources, BwemExt.max_tiles_between_CommandCenter_and_resources));
            TilePosition bottomRightSearchBoundingBox = bottomRightResources.add(new TilePosition(1, 1)).add(new TilePosition(BwemExt.max_tiles_between_CommandCenter_and_resources, BwemExt.max_tiles_between_CommandCenter_and_resources));
            topLeftSearchBoundingBox = BwemExt.makePointFitToBoundingBox(topLeftSearchBoundingBox, TopLeft(), BottomRight().subtract(dimCC).add(new TilePosition(1, 1)));
            bottomRightSearchBoundingBox = BwemExt.makePointFitToBoundingBox(bottomRightSearchBoundingBox, TopLeft(), BottomRight().subtract(dimCC).add(new TilePosition(1, 1)));

            // 2) Mark the Tiles with their distances from each remaining Ressource (Potential Fields >= 0)
            for (Resource r : RemainingResources)
            for (int dy = -dimCC.getY() - BwemExt.max_tiles_between_CommandCenter_and_resources; dy < r.Size().getY() + dimCC.getY() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dy)
            for (int dx = -dimCC.getX() - BwemExt.max_tiles_between_CommandCenter_and_resources; dx < r.Size().getX() + dimCC.getX() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dx) {
                TilePosition t = r.TopLeft().add(new TilePosition(dx, dy));
                if (pMap.getMapData().isValid(t)) {
                    Tile tile = pMap.GetTile(t, check_t.no_check);
                    int dist = (BwemExt.distToRectangle(BwemExt.center(t), r.TopLeft(), r.Size()) + 16) / 32;
                    int score = Math.max(BwemExt.max_tiles_between_CommandCenter_and_resources + 3 - dist, 0);
                    if (r instanceof Geyser) { // somewhat compensates for Geyser alone vs the several Minerals
                        score *= 3;
                    }
                    if (tile.AreaId().equals(Id())) { // note the additive effect (assume tile.InternalData() is 0 at the begining)
                        tile.InternalData().setValue(tile.InternalData().intValue() + score);
                    }
                }
            }

            // 3) Invalidate the 7 x 7 Tiles around each remaining Ressource (Starcraft rule)
            for (Resource r : RemainingResources)
            for (int dy = -3; dy < r.Size().getY() + 3; ++dy)
            for (int dx = -3; dx < r.Size().getX() + 3; ++dx) {
                TilePosition t = r.TopLeft().add(new TilePosition(dx, dy));
                if (pMap.getMapData().isValid(t)) {
                    pMap.GetTile(t, check_t.no_check).SetInternalData(new MutableInt(-1));
                }
            }


            // 4) Search the best location inside the SearchBoundingBox:
            TilePosition bestLocation = null;
            int bestScore = 0;
            List<Mineral> BlockingMinerals = new ArrayList<>();

            for (int y = topLeftSearchBoundingBox.getY(); y <= bottomRightSearchBoundingBox.getY(); ++y)
            for (int x = topLeftSearchBoundingBox.getX(); x <= bottomRightSearchBoundingBox.getX(); ++x) {
                int score = ComputeBaseLocationScore(new TilePosition(x, y));
                if (score > bestScore) {
                    if (ValidateBaseLocation(new TilePosition(x, y), BlockingMinerals)) {
                        bestScore = score;
                        bestLocation = new TilePosition(x, y);
                    }
                }
            }

            // 5) Clear Tile::m_internalData (required due to our use of Potential Fields: see comments in 2))
            for (Resource r : RemainingResources) {
                for (int dy = -dimCC.getY() - BwemExt.max_tiles_between_CommandCenter_and_resources; dy < r.Size().getY() + dimCC.getY() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dy)
                for (int dx = -dimCC.getX() - BwemExt.max_tiles_between_CommandCenter_and_resources; dx < r.Size().getX() + dimCC.getX() + BwemExt.max_tiles_between_CommandCenter_and_resources; ++dx) {
                    TilePosition t = r.TopLeft().add(new TilePosition(dx, dy));
                    if (pMap.getMapData().isValid(t)) {
                        pMap.GetTile(t, check_t.no_check).SetInternalData(new MutableInt(0));
                    }
                }
            }

            if (bestScore == 0) {
                break;
            }

            // 6) Create a new Base at bestLocation, assign to it the relevant ressources and remove them from RemainingRessources:
            List<Resource> AssignedResources = new ArrayList<>();
            for (Resource r : RemainingResources) {
                if (BwemExt.distToRectangle(r.Pos(), bestLocation, dimCC) + 2 <= BwemExt.max_tiles_between_CommandCenter_and_resources * TilePosition.SIZE_IN_PIXELS) {
                    AssignedResources.add(r);
                }
            }

            for (int i = 0; i < RemainingResources.size(); ++i) {
                Resource r = RemainingResources.get(i);
                if (AssignedResources.contains(r)) {
                    RemainingResources.remove(i--);
                }
            }

            if (AssignedResources.isEmpty()) {
                break;
            }

            m_Bases.add(new Base(this, bestLocation, AssignedResources, BlockingMinerals));
        }
    }

    // Calculates the score >= 0 corresponding to the placement of a Base Command Center at 'location'.
    // The more there are ressources nearby, the higher the score is.
    // The function assumes the distance to the nearby ressources has already been computed (in InternalData()) for each tile around.
    // The job is therefore made easier : just need to sum the InternalData() values.
    // Returns -1 if the location is impossible.
    private int ComputeBaseLocationScore(TilePosition location) {
        Map pMap = GetMap();
        TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        int sumScore = 0;
        for (int dy = 0; dy < dimCC.getY(); ++dy)
        for (int dx = 0; dx < dimCC.getX(); ++dx) {
            Tile tile = pMap.GetTile(location.add(new TilePosition(dx, dy)), check_t.no_check);
            if (!tile.Buildable()) return -1;
            if (tile.InternalData().intValue() == -1) return -1; // The special value InternalData() == -1 means there is some ressource at maximum 3 tiles, which Starcraft rules forbid.
                                                                 // Unfortunately, this is guaranteed only for the ressources in this Area, which is the very reason of ValidateBaseLocation
            if (!tile.AreaId().equals(Id())) return -1;
            if (tile.GetNeutral() != null && (tile.GetNeutral() instanceof StaticBuilding)) return -1;

            sumScore += tile.InternalData().intValue();
        }

        return sumScore;
    }

    // Checks if 'location' is a valid location for the placement of a Base Command Center.
    // If the location is valid except for the presence of Mineral patches of less than 9 (see Andromeda.scx),
    // the function returns true, and these Minerals are reported in BlockingMinerals
    // The function is intended to be called after ComputeBaseLocationScore, as it is more expensive.
    // See also the comments inside ComputeBaseLocationScore.
    private boolean ValidateBaseLocation(TilePosition location, List<Mineral> BlockingMinerals) {
        Map pMap = GetMap();
        TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        BlockingMinerals.clear();

        for (int dy = -3; dy < dimCC.getY() + 3; ++dy)
        for (int dx = -3; dx < dimCC.getX() + 3; ++dx) {
            TilePosition t = location.add(new TilePosition(dx, dy));
            if (pMap.getMapData().isValid(t)) {
                Tile tile = pMap.GetTile(t, check_t.no_check);
                Neutral n = tile.GetNeutral();
                if (n != null) {
                    if (n instanceof Geyser) {
                        return false;
                    } else if (n instanceof Mineral) {
                        Mineral m = (Mineral) n;
                        if (m.InitialAmount() <= 8) {
                            BlockingMinerals.add(m);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        // checks the distance to the Bases already created:
        for (Base base : Bases()) {
            if (BwemExt.roundedDist(base.Location(), location) < BwemExt.min_tiles_between_Bases) {
                return false;
            }
        }

        return true;
    }

    // Returns Distances such that Distances[i] == ground_distance(start, Targets[i]) in pixels
    // Note: same algorithm than Graph::ComputeDistances (derived from Dijkstra)
    private List<Integer> ComputeDistances(TilePosition start, List<TilePosition> Targets) {
        Map pMap = GetMap();
        List<Integer> Distances = new ArrayList<>(Targets.size());
        for (int i = 0; i < Targets.size(); ++i) {
            Distances.add(0);
        }

        Tile.UnmarkAll();

        MultiValuedMap<Integer, TilePosition> ToVisit = new ArrayListValuedHashMap<>(); // a priority queue holding the tiles to visit ordered by their distance to start.
                                                                                        //Using ArrayListValuedHashMap to substitute std::multimap since it sorts keys but not values.
        ToVisit.put(0, start);

        int remainingTargets = Targets.size();
        while (!ToVisit.isEmpty()) {
            int currentDist = ToVisit.keys().iterator().next();
            TilePosition current = ToVisit.get(currentDist).iterator().next();
            Tile currentTile = pMap.GetTile(current, check_t.no_check);
//            bwem_assert(currentTile.InternalData() == currentDist);
            if (!(currentTile.InternalData().intValue() == currentDist)) {
                throw new IllegalStateException("currentTile.InternalData().intValue()=" + currentTile.InternalData().intValue() + ", currentDist=" + currentDist);
            }
            ToVisit.removeMapping(currentDist, current);
            currentTile.SetInternalData(new MutableInt(0)); // resets Tile::m_internalData for future usage
            currentTile.SetMarked();

            for (int i = 0; i < Targets.size(); ++i) {
                if (current.equals(Targets.get(i))) {
                    Distances.set(i, (int) (Double.valueOf("0.5") + ((Double.valueOf(currentDist) * Double.valueOf("32")) / Double.valueOf("10000"))));
                    --remainingTargets;
                }
            }
            if (remainingTargets == 0) {
                break;
            }

            TilePosition[] deltas = {
                new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(+1, -1),
                new TilePosition(-1,  0),                          new TilePosition(+1,  0),
                new TilePosition(-1, +1), new TilePosition(0, +1), new TilePosition(+1, +1)
            };
            for (TilePosition delta : deltas) {
                final boolean diagonalMove = (delta.getX() != 0) && (delta.getY() != 0);
                final int newNextDist = currentDist + (diagonalMove ? 14142 : 10000);

                TilePosition next = current.add(delta);
                if (pMap.getMapData().isValid(next)) {
                    Tile nextTile = pMap.GetTile(next, check_t.no_check);
                    if (!nextTile.Marked()) {
                        if (nextTile.InternalData().intValue() != 0) { // next already in ToVisit
                            if (newNextDist < nextTile.InternalData().intValue()) { // nextNewDist < nextOldDist
                                // To update next's distance, we need to remove-insert it from ToVisit:
//                                bwem_assert(iNext != range.second);
                                boolean removed = ToVisit.removeMapping(nextTile.InternalData().intValue(), next);
                                if (!removed) {
                                    throw new IllegalStateException();
                                }
                                nextTile.SetInternalData(new MutableInt(newNextDist));
                                ToVisit.put(newNextDist, next);
                            }
                        } else if ((nextTile.AreaId().equals(Id())) || (nextTile.AreaId().equals(new AreaId(-1)))) {
                            nextTile.SetInternalData(new MutableInt(newNextDist));
                            ToVisit.put(newNextDist, next);
                        }
                    }
                }
            }
        }

//        bwem_assert(!remainingTargets);
        if (!(remainingTargets == 0)) {
            throw new IllegalStateException();
        }

        for (Integer key : ToVisit.keySet()) {
            Collection<TilePosition> coll = ToVisit.get(key);
            for (TilePosition t : coll) {
                pMap.GetTile(t, check_t.no_check).SetInternalData(new MutableInt(0));
            }
        }

        return Distances;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Area)) {
            return false;
        } else {
            Area that = (Area) object;
            return (this.m_id.equals(that.m_id));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.m_id.intValue());
    }


}
