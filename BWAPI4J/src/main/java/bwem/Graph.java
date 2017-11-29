package bwem;

import bwem.map.MapImpl;
import bwem.typedef.CPPath;
import bwem.typedef.Pred;
import bwem.typedef.Altitude;
import bwem.typedef.Index;
import bwem.area.Area;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
import bwem.util.Utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Graph
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Graph {

    private final MapImpl m_pMap;
    private List<Area> m_Areas = new ArrayList<>();
    private List<ChokePoint> m_ChokePointList = new ArrayList<>();
    private List<List<List<ChokePoint>>> m_ChokePointsMatrix = new ArrayList<>(); // index == Area::id x Area::id
    private List<List<Integer>> m_ChokePointDistanceMatrix = new ArrayList<>(); // index == ChokePoint::index x ChokePoint::index
    private List<List<CPPath>> m_PathsBetweenChokePoints = new ArrayList<>(); // index == ChokePoint::index x ChokePoint::index
    private int m_baseCount = 0;

    public Graph(MapImpl pMap) {
        m_pMap = pMap;
    }

    public MapImpl GetMap() {
        return m_pMap;
    }

    public List<Area> Areas() {
        return m_Areas;
    }

    public int AreasCount() {
        return m_Areas.size();
    }

    public Area GetArea(final AreaId id) {
//        bwem_assert(Valid(id));
        if (!(Valid(id))) {
            throw new IllegalArgumentException();
        }
        return m_Areas.get(id.intValue() - 1);
    }

    public Area GetArea(final WalkPosition walkPosition) {
        final AreaId id = GetMap().getData().getMiniTile(walkPosition).AreaId();
        return (id.intValue() > 0)
                ? GetArea(id)
                : null;
    }

    public Area GetArea(final TilePosition tilePosition) {
        final AreaId id = GetMap().getData().getTile(tilePosition).AreaId();
        return (id.intValue() > 0)
                ? GetArea(id)
                : null;
    }

    public Area GetNearestArea(final WalkPosition walkPosition) {
        final Area area = GetArea(walkPosition);
        if (area != null) {
            return area;
        }

        final WalkPosition p = GetMap().BreadthFirstSearch(
            walkPosition,
            new Pred() { // findCond
                @Override
                public boolean isTrue(Object... args) {
                    final Object ttile = args[0];
                    if (ttile instanceof MiniTile) {
                        final MiniTile miniTile = (MiniTile) ttile;
                        return (miniTile.AreaId().intValue() > 0);
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

        return GetArea(p);
    }

    public Area GetNearestArea(final TilePosition tilePosition) {
        final Area area = GetArea(tilePosition);
        if (area != null) {
            return area;
        }

        final TilePosition p = GetMap().BreadthFirstSearch(
            tilePosition,
            new Pred() { // findCond
                @Override
                public boolean isTrue(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        Tile tile = (Tile) ttile;
                        return (tile.AreaId().intValue() > 0);
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

        return GetArea(p);
    }

    // Returns the list of all the ChokePoints in the Map.
    public List<ChokePoint> ChokePoints() {
        return m_ChokePointList;
    }

    // Returns the ChokePoints between two Areas.
    public List<ChokePoint> GetChokePoints(final AreaId a, final AreaId b) {
        if (!Valid(a)) {
//            bwem_assert(Valid(a));
            throw new IllegalArgumentException();
        } else if (!Valid(b)) {
//            bwem_assert(Valid(b));
            throw new IllegalArgumentException();
        } else if (!(a.intValue() != b.intValue())) {
//            bwem_assert(a != b);
            throw new IllegalArgumentException();
        }

        int a_val = a.intValue();
        int b_val = b.intValue();
        if (a_val > b_val) {
            int a_val_tmp = a_val;
            a_val = b_val;
            b_val = a_val_tmp;
        }

        return m_ChokePointsMatrix.get(b_val).get(a_val);
    }

    // Returns the ChokePoints between two Areas.
    public List<ChokePoint> GetChokePoints(Area a, Area b) {
        return GetChokePoints(a.Id(), b.Id());
    }

	// Returns the ground distance in pixels between cpA->Center() and cpB>Center()
	public int Distance(ChokePoint cpA, ChokePoint cpB) {
        return m_ChokePointDistanceMatrix.get(cpA.Index().intValue()).get(cpB.Index().intValue());
    }

    // Returns a list of ChokePoints, which is intended to be the shortest walking path from cpA to cpB.
	public CPPath GetPath(ChokePoint cpA, ChokePoint cpB) {
        return m_PathsBetweenChokePoints.get(cpA.Index().intValue()).get(cpB.Index().intValue());
    }

    public CPPath GetPath(final Position a, final Position b, final MutableInt pLength) {
        final Area areaA = GetNearestArea(a.toWalkPosition());
        final Area areaB = GetNearestArea(b.toWalkPosition());

        if (areaA.equals(areaB)) {
            if (pLength != null) {
                pLength.setValue(BwemExt.getApproxDistance(a, b));
            }
            return new CPPath();
        }

        if (!areaA.AccessibleFrom(areaB)) {
            if (pLength != null) {
                pLength.setValue(-1);
            }
            return new CPPath();
        }

        int minDist_A_B = Integer.MAX_VALUE;

        ChokePoint pBestCpA = null;
        ChokePoint pBestCpB = null;

        for (final ChokePoint cpA : areaA.ChokePoints()) {
            if (!cpA.Blocked()) {
                final int dist_A_cpA = BwemExt.getApproxDistance(a, cpA.Center().toPosition());
                for (final ChokePoint cpB : areaB.ChokePoints()) {
                    if (!cpB.Blocked()) {
                        final int dist_B_cpB = BwemExt.getApproxDistance(b, cpB.Center().toPosition());
                        final int dist_A_B = dist_A_cpA + dist_B_cpB + Distance(cpA, cpB);
                        if (dist_A_B < minDist_A_B) {
                            minDist_A_B = dist_A_B;
                            pBestCpA = cpA;
                            pBestCpB = cpB;
                        }
                    }
                }
            }
        }

//        bwem_assert(minDist_A_B != numeric_limits<int>::max());
        if (!(minDist_A_B != Integer.MAX_VALUE)) {
            throw new IllegalStateException();
        }

        final CPPath path = GetPath(pBestCpA, pBestCpB);

        if (pLength != null) {
//            bwem_assert(Path.size() >= 1);
            if (!(path.size() >= 1)) {
                throw new IllegalStateException();
            }

            pLength.setValue(minDist_A_B);

            if (path.size() == 1) {
//                bwem_assert(pBestCpA == pBestCpB);
                if (!((pBestCpA == null && pBestCpB == null) || pBestCpA.equals(pBestCpB))) {
                    throw new IllegalStateException();
                }
                final ChokePoint cp = pBestCpA;

                final Position cpEnd1 = BwemExt.center(cp.Pos(ChokePoint.Node.end1));
                final Position cpEnd2 = BwemExt.center(cp.Pos(ChokePoint.Node.end2));
                if (Utils.intersect(a.getX(), a.getY(), b.getX(), b.getY(), cpEnd1.getX(), cpEnd1.getY(), cpEnd2.getX(), cpEnd2.getY())) {
                    pLength.setValue(BwemExt.getApproxDistance(a, b));
                } else {
                    final ChokePoint.Node[] nodes = {ChokePoint.Node.end1, ChokePoint.Node.end2};
                    for (final ChokePoint.Node node : nodes) {
                        final Position c = BwemExt.center(cp.Pos(node));
                        final int dist_A_B = BwemExt.getApproxDistance(a, c) + BwemExt.getApproxDistance(b, c);
                        if (dist_A_B < pLength.intValue()) {
                            pLength.setValue(dist_A_B);
                        }
                    }
                }
            }
        }

        return GetPath(pBestCpA, pBestCpB);
    }

	public CPPath GetPath(Position a, Position b) {
        return GetPath(a, b, null);
    }

	public int BaseCount() {
        return m_baseCount;
    }

	// Creates a new Area for each pair (top, miniTiles) in AreasList (See Area::Top() and Area::MiniTiles())
    public void CreateAreas(final List<MutablePair<WalkPosition, Integer>> AreasList) {
        for (int id = 1; id <= AreasList.size(); ++id) {
            final WalkPosition top = AreasList.get(id - 1).left;
            final int miniTiles = AreasList.get(id - 1).right;
            m_Areas.add(new Area(this, new AreaId(id), top, miniTiles));
        }
    }

    ////////////////////////////////////////////////////////////////////////
    // Graph::CreateChokePoints
    ////////////////////////////////////////////////////////////////////////

    //----------------------------------------------------------------------
    // 1) Size the matrix
    //----------------------------------------------------------------------
    private void initializeChokePointsMatrix(
            final List<List<List<ChokePoint>>> chokePointsMatrix,
            final int AreasCount
    ) {
//      m_ChokePointsMatrix.resize(AreasCount() + 1);
        chokePointsMatrix.clear();
        for (int i = 0; i <= AreasCount + 1; ++i) {
            chokePointsMatrix.add(new ArrayList<>());
        }
//    	for (Area::id id = 1 ; id <= AreasCount() ; ++id)
//    		m_ChokePointsMatrix[id].resize(id);			// triangular matrix
        for (int id = 1; id <= AreasCount; ++id) { // triangular matrix
            for (int n = 1; n <= id; ++n) {
                chokePointsMatrix.get(id).add(new ArrayList<>());
            }
        }
    }
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // 2) Dispatch the global raw frontier between all the relevant pairs of Areas:
    //----------------------------------------------------------------------
    private AbstractMap<MutablePair<AreaId, AreaId>, List<WalkPosition>> createRawFrontierByAreaPairMap(
            final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> RawFrontier
    ) {
        final AbstractMap<MutablePair<AreaId, AreaId>, List<WalkPosition>> RawFrontierByAreaPair = new ConcurrentHashMap<>();

        for (final MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> raw : RawFrontier) {
            AreaId a = new AreaId(raw.left.left);
            AreaId b = new AreaId(raw.left.right);
            if (a.intValue() > b.intValue()) {
                AreaId a_tmp = new AreaId(a);
                a = new AreaId(b);
                b = new AreaId(a_tmp);
            }
//    		bwem_assert(a <= b);
            if (!(a.intValue() <= b.intValue())) {
                throw new IllegalStateException();
            }
//    		bwem_assert((a >= 1) && (b <= AreasCount()));
            if (!((a.intValue() >= 1) && (b.intValue() <= AreasCount()))) {
                throw new IllegalStateException();
            }

            final MutablePair<AreaId, AreaId> key = new MutablePair<>(a, b);
            if (!RawFrontierByAreaPair.containsKey(key)) {
                final List<WalkPosition> list = new ArrayList<>();
                list.add(raw.right);
                RawFrontierByAreaPair.put(key, list);
            } else {
                RawFrontierByAreaPair.get(key).add(raw.right);
            }
        }

        return RawFrontierByAreaPair;
    }
    //----------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////

    // Creates a new Area for each pair (top, miniTiles) in AreasList (See Area::Top() and Area::MiniTiles())
    public void CreateChokePoints(
            final List<StaticBuilding> staticBuildings,
            final List<Mineral> minerals,
            final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> RawFrontier
    ) {
        Index newIndex = new Index(0);

    	final List<Neutral> BlockingNeutrals = new ArrayList<>();
    	for (final StaticBuilding s : staticBuildings) {
            if (s.Blocking()) {
                BlockingNeutrals.add(s);
            }
        }
    	for (final Mineral m : minerals) {
            if (m.Blocking()) {
                BlockingNeutrals.add(m);
            }
        }

        //Note: pseudoChokePointsToCreate is only used for resizing the array.
//        int pseudoChokePointsToCreate = 0;
//        for (Neutral n : BlockingNeutrals) {
//            if (n.NextStacked() == null) {
//                ++pseudoChokePointsToCreate;
//            }
//        }

    	// 1) Size the matrix
        initializeChokePointsMatrix(m_ChokePointsMatrix, AreasCount());

    	// 2) Dispatch the global raw frontier between all the relevant pairs of Areas:
        final AbstractMap<MutablePair<AreaId, AreaId>, List<WalkPosition>> RawFrontierByAreaPair
                = createRawFrontierByAreaPairMap(RawFrontier);

    	// 3) For each pair of Areas (A, B):
    	for (final MutablePair<AreaId, AreaId> rawleft : RawFrontierByAreaPair.keySet()) {
            final AreaId a = new AreaId(rawleft.left);
            final AreaId b = new AreaId(rawleft.right);

    	    final List<WalkPosition> rawright = RawFrontierByAreaPair.get(rawleft);

    		final List<WalkPosition> RawFrontierAB = rawright;

    		// Because our dispatching preserved order,
    		// and because Map::m_RawFrontier was populated in descending order of the altitude (see Map::ComputeAreas),
    		// we know that RawFrontierAB is also ordered the same way, but let's check it:
    		{
    			final List<Altitude> Altitudes = new ArrayList<>();
    			for (final WalkPosition w : RawFrontierAB) {
    				Altitudes.add(new Altitude(GetMap().getData().getMiniTile(w).Altitude()));
                }

//    			bwem_assert(is_sorted(Altitudes.rbegin(), Altitudes.rend()));
                List<Altitude> SortedAltitudesCopy = new ArrayList<>();
                for (Altitude altitude : Altitudes) {
                    SortedAltitudesCopy.add(new Altitude(altitude));
                }
                Collections.sort(SortedAltitudesCopy, Collections.reverseOrder());
                for (int i = 0; i < Altitudes.size(); ++i) {
                    if (!Altitudes.get(i).equals(SortedAltitudesCopy.get(i))) {
                        throw new IllegalStateException();
                    }
                }
    		}

    		// 3.1) Use that information to efficiently cluster RawFrontierAB in one or several chokepoints.
    		//    Each cluster will be populated starting with the center of a chokepoint (max altitude)
    		//    and finishing with the ends (min altitude).
    		final int cluster_min_dist = (int) Math.sqrt(BwemExt.lake_max_miniTiles);
    		final List<List<WalkPosition>> Clusters = new ArrayList<>();
    		for (final WalkPosition w : RawFrontierAB) {
    			boolean added = false;
    			for (final List<WalkPosition> Cluster : Clusters) {
    				final int distToFront = BwemExt.queenWiseDist(Cluster.get(0), w);
    				final int distToBack = BwemExt.queenWiseDist(Cluster.get(Cluster.size() - 1), w);
    				if (Math.min(distToFront, distToBack) <= cluster_min_dist) {
                        if (distToFront < distToBack) {
                            Cluster.add(0, w);
                        } else {
                            Cluster.add(w);
                        }
    					added = true;
    					break;
    				}
    			}

    			if (!added) {
                    final List<WalkPosition> list = new ArrayList<>();
                    list.add(w);
                    Clusters.add(list);
                }
    		}

    		// 3.2) Create one Chokepoint for each cluster:
//            GetChokePoints(a, b).reserve(Clusters.size() + pseudoChokePointsToCreate);
    		for (final List<WalkPosition> Cluster : Clusters) {
    			GetChokePoints(a, b).add(new ChokePoint(this, new Index(newIndex), GetArea(a), GetArea(b), Cluster));
                newIndex = newIndex.add(1);
            }
    	}

    	// 4) Create one Chokepoint for each pair of blocked areas, for each blocking Neutral:
    	for (final Neutral pNeutral : BlockingNeutrals) {
    		if (pNeutral.NextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top
    			final List<Area> BlockedAreas = pNeutral.BlockedAreas();
    			for (final Area pA : BlockedAreas)
    			for (final Area pB : BlockedAreas) {
    				if (pB.equals(pA)) {
                        break; // breaks symmetry
                    }

                    final WalkPosition center = GetMap().BreadthFirstSearch(
                            pNeutral.Pos().toWalkPosition(),
                            new Pred() { // findCond
                                @Override
                                public boolean isTrue(Object... args) {
                                    Object ttile = args[0];
                                    if (!(ttile instanceof MiniTile)) {
                                        throw new IllegalArgumentException();
                                    }
                                    MiniTile miniTile = (MiniTile) ttile;
                                    return miniTile.Walkable();
                                }
                            },
                            new Pred() { // visitCond
                                @Override
                                public boolean isTrue(Object... args) {
                                    return true;
                                }
                            }
                    );

                    final List<WalkPosition> list = new ArrayList<>();
                    list.add(center);
    				GetChokePoints(pA, pB).add(new ChokePoint(this, new Index(newIndex), pA, pB, list, pNeutral));
                    newIndex = newIndex.add(1);
    			}
    		}
        }

    	// 5) Set the references to the freshly created Chokepoints:
    	for (int loop_a = 1; loop_a <= AreasCount(); ++loop_a)
    	for (int loop_b = 1; loop_b < loop_a; ++loop_b) {
            final AreaId a = new AreaId(loop_a);
            final AreaId b = new AreaId(loop_b);
    		if (!GetChokePoints(a, b).isEmpty()) {
    			GetArea(a).AddChokePoints(GetArea(b), GetChokePoints(a, b));
    			GetArea(b).AddChokePoints(GetArea(a), GetChokePoints(a, b));

    			for (ChokePoint cp : GetChokePoints(a, b)) {
    				m_ChokePointList.add(cp);
                }
    		}
        }
    }

    //----------------------------------------------------------------------

    public void ComputeChokePointDistanceMatrix() {
    	// 1) Size the matrix
        m_ChokePointDistanceMatrix.clear();
//    	m_ChokePointDistanceMatrix.resize(m_ChokePointList.size());
        for (int i = 0; i < m_ChokePointList.size(); ++i) {
            m_ChokePointDistanceMatrix.add(new ArrayList<>());
        }
//    	for (auto & line : m_ChokePointDistanceMatrix)
//    		line.resize(m_ChokePointList.size(), -1);
        for (int i = 0; i < m_ChokePointDistanceMatrix.size(); ++i) {
            for (int j = 0; j < m_ChokePointList.size(); ++j) {
                m_ChokePointDistanceMatrix.get(i).add(-1);
            }
        }

//    	m_PathsBetweenChokePoints.resize(m_ChokePointList.size());
        m_PathsBetweenChokePoints.clear();
        for (int i = 0; i < m_ChokePointList.size(); ++i) {
            m_PathsBetweenChokePoints.add(new ArrayList<>());
        }
//    	for (auto & line : m_PathsBetweenChokePoints)
//    		line.resize(m_ChokePointList.size());
        for (int i = 0; i < m_PathsBetweenChokePoints.size(); ++i) {
            for (int j = 0; j < m_ChokePointList.size(); ++j) {
                m_PathsBetweenChokePoints.get(i).add(new CPPath());
            }
        }

    	// 2) Compute distances inside each Area
    	for (Area area : Areas()) {
    		ComputeChokePointDistances(area);
        }

    	// 3) Compute distances through connected Areas
    	ComputeChokePointDistances(this);

    	for (ChokePoint cp : ChokePoints()) {
    		SetDistance(cp, cp, 0);
            CPPath cppath = new CPPath();
            cppath.add(cp);
    		SetPath(cp, cp, cppath);
    	}

    	// 4) Update Area::m_AccessibleNeighbours for each Area
    	for (Area area : Areas())
    		area.UpdateAccessibleNeighbors();

    	// 5)  Update Area::m_groupId for each Area
    	UpdateGroupIds();
    }

    public void CollectInformation() {
        // 1) Process the whole Map:

        for (Mineral m : GetMap().getNeutralData().getMinerals()) {
            Area pArea = mainArea(GetMap(), m.TopLeft(), m.Size());
            if (pArea != null) {
                pArea.AddMineral(m);
            }
        }

        for (Geyser g : GetMap().getNeutralData().getGeysers()) {
            Area pArea = mainArea(GetMap(), g.TopLeft(), g.Size());
            if (pArea != null) {
                pArea.AddGeyser(g);
            }
        }

        for (int y = 0; y < GetMap().getData().getMapData().getTileSize().getY(); ++y)
        for (int x = 0; x < GetMap().getData().getMapData().getTileSize().getX(); ++x) {
            Tile tile = GetMap().getData().getTile(new TilePosition(x, y));
            if (tile.AreaId().intValue() > 0) {
                GetArea(tile.AreaId()).AddTileInformation(new TilePosition(x, y), tile);
            }
        }

        // 2) Post-process each Area separately:

        for (Area area : m_Areas) {
            area.PostCollectInformation();
        }
    }

    public void CreateBases() {
        m_baseCount = 0;
        for (Area area : m_Areas) {
            area.CreateBases();
            m_baseCount += area.Bases().size();
        }
    }

    // Computes the ground distances between any pair of ChokePoints in pContext
    // This is achieved by invoking several times pContext->ComputeDistances,
    // which effectively computes the distances from one starting ChokePoint, using Dijkstra's algorithm.
    // If Context == Area, Dijkstra's algorithm works on the Tiles inside one Area.
    // If Context == Graph, Dijkstra's algorithm works on the GetChokePoints between the AreaS.
    private void ComputeChokePointDistances(final Area pContext) {
        for (final ChokePoint pStart : pContext.ChokePoints()) {
            final List<ChokePoint> Targets = new ArrayList<>();
            for (final ChokePoint cp : pContext.ChokePoints()) {
                if (cp.equals(pStart)) {
                    break; // breaks symmetry
                }
                Targets.add(cp);
            }

            final List<Integer> DistanceToTargets = pContext.ComputeDistances(pStart, Targets);

            for (int i = 0; i < Targets.size(); ++i) {
                final int newDist = DistanceToTargets.get(i);
                final int existingDist = Distance(pStart, Targets.get(i));

                if (newDist != 0 && ((existingDist == -1) || (newDist < existingDist))) {
                    SetDistance(pStart, Targets.get(i), newDist);

                    // Build the path from pStart to Targets[i]:

                    final CPPath path = new CPPath();
                    path.add(pStart);
                    path.add(Targets.get(i));

                    SetPath(pStart, Targets.get(i), path);
                }
            }
        }
    }

    // Computes the ground distances between any pair of ChokePoints in pContext
    // This is achieved by invoking several times pContext->ComputeDistances,
    // which effectively computes the distances from one starting ChokePoint, using Dijkstra's algorithm.
    // If Context == Area, Dijkstra's algorithm works on the Tiles inside one Area.
    // If Context == Graph, Dijkstra's algorithm works on the GetChokePoints between the AreaS.
    private void ComputeChokePointDistances(final Graph pContext) {
        for (final ChokePoint pStart : pContext.ChokePoints()) {
            final List<ChokePoint> Targets = new ArrayList<>();
            for (final ChokePoint cp : pContext.ChokePoints()) {
                if (cp.equals(pStart)) {
                    break; // breaks symmetry
                }
                Targets.add(cp);
            }

            final List<Integer> DistanceToTargets = pContext.ComputeDistances(pStart, Targets);

            for (int i = 0; i < Targets.size(); ++i) {
                final int newDist = DistanceToTargets.get(i);
                final int existingDist = Distance(pStart, Targets.get(i));

                if (newDist != 0 && ((existingDist == -1) || (newDist < existingDist))) {
                    SetDistance(pStart, Targets.get(i), newDist);

                    // Build the path from pStart to Targets[i]:

                    final CPPath path = new CPPath();
                    path.add(pStart);
                    path.add(Targets.get(i));

//                    // if (Context == Graph), there may be intermediate ChokePoints. They have been set by ComputeDistances,
//                    // so we just have to collect them (in the reverse order) and insert them into Path:
//                    if ((void *)(pContext) == (void *)(this))	// tests (Context == Graph) without warning about constant condition
                        //TODO: Verify this loop is correct.
                        for (ChokePoint pPrev = Targets.get(i).PathBackTrace(); !pPrev.equals(pStart); pPrev = pPrev.PathBackTrace()) {
                            path.add(1, pPrev);
                        }

                    SetPath(pStart, Targets.get(i), path);
                }
            }
        }
    }

    // Returns Distances such that Distances[i] == ground_distance(start, Targets[i]) in pixels
    // Any Distances[i] may be 0 (meaning Targets[i] is not reachable).
    // This may occur in the case where start and Targets[i] leave in different continents or due to Bloqued intermediate ChokePoint(s).
    // For each reached target, the shortest path can be derived using
    // the backward trace set in cp->PathBackTrace() for each intermediate ChokePoint cp from the target.
    // Note: same algo than Area::ComputeDistances (derived from Dijkstra)
    private List<Integer> ComputeDistances(final ChokePoint start, final List<ChokePoint> Targets) {
        final List<Integer> Distances = new ArrayList<>(Targets.size());
        for (int i = 0; i < Targets.size(); ++i) {
            Distances.add(0);
        }

        Tile.UnmarkAll();

        final MultiValuedMap<Integer, ChokePoint> ToVisit = new ArrayListValuedHashMap<>(); // a priority queue holding the GetChokePoints to visit ordered by their distance to start.
                                                                                            //Using ArrayListValuedHashMap to substitute std::multimap since it sorts keys but not values.
        ToVisit.put(0, start);

        int remainingTargets = Targets.size();
        while (!ToVisit.isEmpty()) {
            final int currentDist = ToVisit.keys().iterator().next();
            final ChokePoint current = ToVisit.get(currentDist).iterator().next();
            final Tile currentTile = GetMap().getData().getTile(current.Center().toTilePosition(), check_t.no_check);
//            bwem_assert(currentTile.InternalData() == currentDist);
            if (!(currentTile.InternalData().intValue() == currentDist)) {
                throw new IllegalStateException();
            }
            ToVisit.removeMapping(currentDist, current);
            currentTile.SetInternalData(new MutableInt(0)); // resets Tile::m_internalData for future usage
            currentTile.SetMarked();

            for (int i = 0; i < Targets.size(); ++i) {
                if (current.equals(Targets.get(i))) {
                    Distances.set(i, currentDist);
                    --remainingTargets;
                }
            }
            if (remainingTargets == 0) {
                break;
            }

            if (current.Blocked() && (!current.equals(start))){
                continue;
            }

            final Area[] areas = {current.GetAreas().left, current.GetAreas().right};
            for (final Area pArea : areas) {
                for (final ChokePoint next : pArea.ChokePoints()) {
                    if (!next.equals(current)) {
                        final int newNextDist = currentDist + Distance(current, next);
                        final Tile nextTile = GetMap().getData().getTile(next.Center().toTilePosition(), check_t.no_check);
                        if (!nextTile.Marked()) {
                            if (nextTile.InternalData().intValue() != 0) { // next already in ToVisit
                                if (newNextDist < nextTile.InternalData().intValue()) { // nextNewDist < nextOldDist
                                                                                        // To update next's distance, we need to remove-insert it from ToVisit:
//                                    bwem_assert(iNext != range.second);
                                    final boolean removed = ToVisit.removeMapping(nextTile.InternalData().intValue(), next);
                                    if (!removed) {
                                        throw new IllegalStateException();
                                    }
                                    nextTile.SetInternalData(new MutableInt(newNextDist));
                                    next.SetPathBackTrace(current);
                                    ToVisit.put(newNextDist, next);
                                }
                            } else {
                                nextTile.SetInternalData(new MutableInt(newNextDist));
                                next.SetPathBackTrace(current);
                                ToVisit.put(newNextDist, next);
                            }
                        }
                    }
                }
            }
        }

//    //	bwem_assert(!remainingTargets);
//        if (!(remainingTargets == 0)) {
//            throw new IllegalStateException();
//        }

        // Reset Tile::m_internalData for future usage
        for (final Integer key : ToVisit.keySet()) {
            final Collection<ChokePoint> coll = ToVisit.get(key);
            for (final ChokePoint cp : coll) {
                GetMap().getData().getTile(cp.Center().toTilePosition(), check_t.no_check).SetInternalData(new MutableInt(0));
            }
        }

        return Distances;
    }

    private void UpdateGroupIds() {
    	int nextGroupId = 1;

    	Area.UnmarkAll();
    	for (final Area start : Areas()) {
    		if (!start.Marked()) {
    			final List<Area> ToVisit = new ArrayList<>();
                ToVisit.add(start);
    			while (!ToVisit.isEmpty()) {
    				final Area current = ToVisit.remove(ToVisit.size() - 1);
    				current.SetGroupId(new GroupId(nextGroupId));

    				for (final Area next : current.AccessibleNeighbors()) {
    					if (!next.Marked()) {
    						next.SetMarked();
    						ToVisit.add(next);
    					}
                    }
    			}
                ++nextGroupId;
    		}
        }
    }

    private void SetDistance(final ChokePoint cpA, final ChokePoint cpB, final int value) {
        m_ChokePointDistanceMatrix.get(cpA.Index().intValue()).set(cpB.Index().intValue(), value);
        m_ChokePointDistanceMatrix.get(cpB.Index().intValue()).set(cpA.Index().intValue(), value);
    }

    private void SetPath(final ChokePoint cpA, final ChokePoint cpB, final CPPath PathAB) {
        m_PathsBetweenChokePoints.get(cpA.Index().intValue()).set(cpB.Index().intValue(), PathAB);

        m_PathsBetweenChokePoints.get(cpB.Index().intValue()).get(cpA.Index().intValue()).clear();
        for (int i = PathAB.size() - 1; i >= 0; --i) {
            final ChokePoint cp = PathAB.get(i);
            m_PathsBetweenChokePoints.get(cpB.Index().intValue()).get(cpA.Index().intValue()).add(cp);
        }
    }

    private boolean Valid(AreaId id) {
        return (1 <= id.intValue() && id.intValue() <= AreasCount());
    }

    public static Area mainArea(MapImpl pMap, TilePosition topLeft, TilePosition size) {
        AbstractMap<Area, Integer> map_Area_freq = new ConcurrentHashMap<>();

        for (int dy = 0; dy < size.getY(); ++dy)
        for (int dx = 0; dx < size.getX(); ++dx) {
            Area area = pMap.GetArea(topLeft.add(new TilePosition(dx, dy)));
            if (area != null) {
                Integer val = map_Area_freq.get(area);
                if (val == null) {
                    val = 0;
                }
                map_Area_freq.put(area, val + 1);
            }
        }

        Area lastArea = null;
        if (!map_Area_freq.isEmpty()) {
            for (Area tmpArea : map_Area_freq.keySet()) {
                lastArea = tmpArea;
            }
        }
        return lastArea;
    }

}
