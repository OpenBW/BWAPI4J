package bwem;

import bwem.area.AreaInitializer;
import bwem.area.AreaInitializerImpl;
import bwem.map.AdvancedData;
import bwem.map.MapImpl;
import bwem.tile.TileImpl;
import bwem.typedef.CPPath;
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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.util.Pair;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Graph
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Graph {

    private final MapImpl map;
    private final List<Area> areas = new ArrayList<>();
    private final List<ChokePoint> chokePoints = new ArrayList<>();
    private final List<List<List<ChokePoint>>> ChokePointsMatrix = new ArrayList<>(); // index == Area::id x Area::id
    private final List<List<Integer>> chokePointDistanceMatrix = new ArrayList<>(); // index == ChokePoint::index x ChokePoint::index
    private final List<List<CPPath>> pathsBetweenChokePoints = new ArrayList<>(); // index == ChokePoint::index x ChokePoint::index
    private int baseCount = 0;

    public Graph(MapImpl map) {
        this.map = map;
    }

    public MapImpl getMap() {
        return map;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public int areasCount() {
        return areas.size();
    }

    public Area getArea(final AreaId id) {
//        bwem_assert(valid(id));
        if (!(isValid(id))) {
            throw new IllegalArgumentException();
        }
        return areas.get(id.intValue() - 1);
    }

    public Area getArea(final WalkPosition walkPosition) {
        final AreaId id = getMap().getData().getMiniTile(walkPosition).getAreaId();
        return (id.intValue() > 0)
                ? getArea(id)
                : null;
    }

    public Area getArea(final TilePosition tilePosition) {
        final AreaId id = getMap().getData().getTile(tilePosition).getAreaId();
        return (id.intValue() > 0)
                ? getArea(id)
                : null;
    }

    public Area getNearestArea(final WalkPosition walkPosition) {
        final Area area = getArea(walkPosition);
        if (area != null) {
            return area;
        }

        // findCond
        // visitCond
        final WalkPosition p = getMap().breadthFirstSearch(
            walkPosition,
                args -> {
                    final Object ttile = args[0];
                    if (ttile instanceof MiniTile) {
                        final MiniTile miniTile = (MiniTile) ttile;
                        return (miniTile.getAreaId().intValue() > 0);
                    } else {
                        throw new IllegalArgumentException();
                    }
                },
                args -> true
        );

        return getArea(p);
    }

    public Area getNearestArea(final TilePosition tilePosition) {
        final Area area = getArea(tilePosition);
        if (area != null) {
            return area;
        }

        // findCond
        // visitCond
        final TilePosition p = getMap().breadthFirstSearch(
            tilePosition,
                args -> {
                    Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        Tile tile = (Tile) ttile;
                        return (tile.getAreaId().intValue() > 0);
                    } else {
                        throw new IllegalArgumentException();
                    }
                },
                args -> true
        );

        return getArea(p);
    }

    // Returns the list of all the getChokePoints in the Map.
    public List<ChokePoint> getChokePoints() {
        return chokePoints;
    }

    // Returns the getChokePoints between two areas.
    public List<ChokePoint> getChokePoints(final AreaId a, final AreaId b) {
        if (!isValid(a)) {
//            bwem_assert(valid(a));
            throw new IllegalArgumentException();
        } else if (!isValid(b)) {
//            bwem_assert(valid(b));
            throw new IllegalArgumentException();
        } else if (a.intValue() == b.intValue()) {
//            bwem_assert(a != b);
            throw new IllegalArgumentException();
        }

        int aVal = a.intValue();
        int bVal = b.intValue();
        if (aVal > bVal) {
            int aValTmp = aVal;
            aVal = bVal;
            bVal = aValTmp;
        }

        return ChokePointsMatrix.get(bVal).get(aVal);
    }

    // Returns the getChokePoints between two areas.
    public List<ChokePoint> getChokePoints(Area a, Area b) {
        return getChokePoints(a.getId(), b.getId());
    }

	// Returns the ground distance in pixels between cpA->center() and cpB>center()
	public int distance(ChokePoint cpA, ChokePoint cpB) {
        return chokePointDistanceMatrix.get(cpA.getIndex().intValue()).get(cpB.getIndex().intValue());
    }

    // Returns a list of getChokePoints, which is intended to be the shortest walking path from cpA to cpB.
	public CPPath getPath(ChokePoint cpA, ChokePoint cpB) {
        return pathsBetweenChokePoints.get(cpA.getIndex().intValue()).get(cpB.getIndex().intValue());
    }

    public CPPath getPath(final Position a, final Position b, final MutableInt pLength) {
        final Area areaA = getNearestArea(a.toWalkPosition());
        final Area areaB = getNearestArea(b.toWalkPosition());

        if (areaA.equals(areaB)) {
            if (pLength != null) {
                pLength.setValue(BwemExt.getApproxDistance(a, b));
            }
            return new CPPath();
        }

        if (!areaA.isAccessibleFrom(areaB)) {
            if (pLength != null) {
                pLength.setValue(-1);
            }
            return new CPPath();
        }

        int minDistAB = Integer.MAX_VALUE;

        ChokePoint pBestCpA = null;
        ChokePoint pBestCpB = null;

        for (final ChokePoint cpA : areaA.getChokePoints()) {
            if (!cpA.isBlocked()) {
                final int distACpA = BwemExt.getApproxDistance(a, cpA.getCenter().toPosition());
                for (final ChokePoint cpB : areaB.getChokePoints()) {
                    if (!cpB.isBlocked()) {
                        final int distBToCPB = BwemExt.getApproxDistance(b, cpB.getCenter().toPosition());
                        final int distAToB = distACpA + distBToCPB + distance(cpA, cpB);
                        if (distAToB < minDistAB) {
                            minDistAB = distAToB;
                            pBestCpA = cpA;
                            pBestCpB = cpB;
                        }
                    }
                }
            }
        }

//        bwem_assert(minDistAB != numeric_limits<int>::max());
        if (minDistAB == Integer.MAX_VALUE) {
            throw new IllegalStateException();
        }

        final CPPath path = getPath(pBestCpA, pBestCpB);

        if (pLength != null) {
//            bwem_assert(Path.size() >= 1);
            if (!(path.size() >= 1)) {
                throw new IllegalStateException();
            }

            pLength.setValue(minDistAB);

            if (path.size() == 1) {
//                bwem_assert(pBestCpA == pBestCpB);
                if (!pBestCpA.equals(pBestCpB)) {
                    throw new IllegalStateException();
                }

                final Position cpEnd1 = BwemExt.center(pBestCpA.positionOfNode(ChokePoint.Node.END_1));
                final Position cpEnd2 = BwemExt.center(pBestCpA.positionOfNode(ChokePoint.Node.END_2));
                if (Utils.intersect(a.getX(), a.getY(), b.getX(), b.getY(), cpEnd1.getX(), cpEnd1.getY(), cpEnd2.getX(), cpEnd2.getY())) {
                    pLength.setValue(BwemExt.getApproxDistance(a, b));
                } else {
                    final ChokePoint.Node[] nodes = {ChokePoint.Node.END_1, ChokePoint.Node.END_2};
                    for (final ChokePoint.Node node : nodes) {
                        final Position c = BwemExt.center(pBestCpA.positionOfNode(node));
                        final int distAToB = BwemExt.getApproxDistance(a, c) + BwemExt.getApproxDistance(b, c);
                        if (distAToB < pLength.intValue()) {
                            pLength.setValue(distAToB);
                        }
                    }
                }
            }
        }

        return getPath(pBestCpA, pBestCpB);
    }

	public CPPath getPath(Position a, Position b) {
        return getPath(a, b, null);
    }

	public int getBaseCount() {
        return baseCount;
    }

	// Creates a new Area for each pair (top, miniTiles) in areasList (See Area::top() and Area::miniTiles())
    public void createAreas(final List<MutablePair<WalkPosition, Integer>> areasList) {
        for (int id = 1; id <= areasList.size(); ++id) {
            final WalkPosition top = areasList.get(id - 1).getLeft();
            final int miniTiles = areasList.get(id - 1).getRight();
            areas.add(new AreaInitializerImpl(getMap(), new AreaId(id), top, miniTiles));
        }
    }

    ////////////////////////////////////////////////////////////////////////
    // Graph::createChokePoints
    ////////////////////////////////////////////////////////////////////////

    //----------------------------------------------------------------------
    // 1) size the matrix
    //----------------------------------------------------------------------
    private void initializeChokePointsMatrix(
            final List<List<List<ChokePoint>>> chokePointsMatrix,
            final int areasCount
    ) {
//      ChokePointsMatrix.resize(areasCount() + 1);
        chokePointsMatrix.clear();
        for (int i = 0; i <= areasCount + 1; ++i) {
            chokePointsMatrix.add(new ArrayList<>());
        }
//    	for (Area::id id = 1 ; id <= areasCount() ; ++id)
//    		m_ChokePointsMatrix[id].resize(id);			// triangular matrix
        for (int id = 1; id <= areasCount; ++id) { // triangular matrix
            for (int n = 1; n <= id; ++n) {
                chokePointsMatrix.get(id).add(new ArrayList<>());
            }
        }
    }
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // 2) Dispatch the global raw frontier between all the relevant pairs of areas:
    //----------------------------------------------------------------------
    private AbstractMap<MutablePair<AreaId, AreaId>, List<WalkPosition>> createRawFrontierByAreaPairMap(
            final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> rawFrontier
    ) {
        final AbstractMap<MutablePair<AreaId, AreaId>, List<WalkPosition>> rawFrontierByAreaPair = new ConcurrentHashMap<>();

        for (final MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> raw : rawFrontier) {
            AreaId a = new AreaId(raw.getLeft().getLeft());
            AreaId b = new AreaId(raw.getLeft().getRight());
            if (a.intValue() > b.intValue()) {
                AreaId aTmp = new AreaId(a);
                a = new AreaId(b);
                b = new AreaId(aTmp);
            }
//    		bwem_assert(a <= b);
            if (!(a.intValue() <= b.intValue())) {
                throw new IllegalStateException();
            }
//    		bwem_assert((a >= 1) && (b <= areasCount()));
            if (!((a.intValue() >= 1) && (b.intValue() <= areasCount()))) {
                throw new IllegalStateException();
            }

            final MutablePair<AreaId, AreaId> key = new MutablePair<>(a, b);
            if (!rawFrontierByAreaPair.containsKey(key)) {
                final List<WalkPosition> list = new ArrayList<>();
                list.add(raw.getRight());
                rawFrontierByAreaPair.put(key, list);
            } else {
                rawFrontierByAreaPair.get(key).add(raw.getRight());
            }
        }

        return rawFrontierByAreaPair;
    }
    //----------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////

    // Creates a new Area for each pair (top, miniTiles) in AreasList (See Area::top() and Area::miniTiles())
    public void createChokePoints(
            final List<StaticBuilding> staticBuildings,
            final List<Mineral> minerals,
            final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> rawFrontier
    ) {
        Index newIndex = new Index(0);

    	final List<Neutral> blockingNeutrals = new ArrayList<>();
    	for (final StaticBuilding s : staticBuildings) {
            if (s.isBlocking()) {
                blockingNeutrals.add(s);
            }
        }
    	for (final Mineral m : minerals) {
            if (m.isBlocking()) {
                blockingNeutrals.add(m);
            }
        }

        //Note: pseudoChokePointsToCreate is only used for resizing the array.
//        int pseudoChokePointsToCreate = 0;
//        for (Neutral n : blockingNeutrals) {
//            if (n.nextStacked() == null) {
//                ++pseudoChokePointsToCreate;
//            }
//        }

    	// 1) size the matrix
        initializeChokePointsMatrix (ChokePointsMatrix, areasCount());

    	// 2) Dispatch the global raw frontier between all the relevant pairs of areas:
        final AbstractMap<MutablePair<AreaId, AreaId>, List<WalkPosition>> rawFrontierByAreaPair
                = createRawFrontierByAreaPairMap(rawFrontier);

    	// 3) For each pair of areas (A, B):
    	for (final MutablePair<AreaId, AreaId> rawleft : rawFrontierByAreaPair.keySet()) {
            final AreaId a = new AreaId(rawleft.getLeft());
            final AreaId b = new AreaId(rawleft.getRight());

    		final List<WalkPosition> rawFrontierAB = rawFrontierByAreaPair.get(rawleft);

    		// Because our dispatching preserved order,
    		// and because Map::m_RawFrontier was populated in descending order of the altitude (see Map::computeAreas),
    		// we know that rawFrontierAB is also ordered the same way, but let's check it:
    		{
    			final List<Altitude> altitudes = new ArrayList<>();
    			for (final WalkPosition w : rawFrontierAB) {
    				altitudes.add(new Altitude(getMap().getData().getMiniTile(w).getAltitude()));
                }

//    			bwem_assert(is_sorted(altitudes.rbegin(), altitudes.rend()));
                List<Altitude> sortedAltitudesCopy = new ArrayList<>();
                for (Altitude altitude : altitudes) {
                    sortedAltitudesCopy.add(new Altitude(altitude));
                }
                sortedAltitudesCopy.sort(Collections.reverseOrder());
                for (int i = 0; i < altitudes.size(); ++i) {
                    if (!altitudes.get(i).equals(sortedAltitudesCopy.get(i))) {
                        throw new IllegalStateException();
                    }
                }
    		}

    		// 3.1) Use that information to efficiently cluster rawFrontierAB in one or several chokepoints.
    		//    Each cluster will be populated starting with the center of a chokepoint (max altitude)
    		//    and finishing with the ends (min altitude).
    		final int clusterMinDist = (int) Math.sqrt(BwemExt.lake_max_miniTiles);
    		final List<List<WalkPosition>> clusters = new ArrayList<>();
    		for (final WalkPosition w : rawFrontierAB) {
    			boolean added = false;
    			for (final List<WalkPosition> cluster : clusters) {
    				final int distToFront = BwemExt.queenWiseDist(cluster.get(0), w);
    				final int distToBack = BwemExt.queenWiseDist(cluster.get(cluster.size() - 1), w);
    				if (Math.min(distToFront, distToBack) <= clusterMinDist) {
                        if (distToFront < distToBack) {
                            cluster.add(0, w);
                        } else {
                            cluster.add(w);
                        }
    					added = true;
    					break;
    				}
    			}

    			if (!added) {
                    final List<WalkPosition> list = new ArrayList<>();
                    list.add(w);
                    clusters.add(list);
                }
    		}

    		// 3.2) Create one Chokepoint for each cluster:
//            getChokePoints(a, b).reserve(clusters.size() + pseudoChokePointsToCreate);
    		for (final List<WalkPosition> cluster : clusters) {
    			getChokePoints(a, b).add(new ChokePoint(this, new Index(newIndex), getArea(a), getArea(b), cluster));
                newIndex = newIndex.add(1);
            }
    	}

    	// 4) Create one Chokepoint for each pair of blocked areas, for each blocking Neutral:
    	for (final Neutral pNeutral : blockingNeutrals) {
    		if (pNeutral.getNextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top
    			final List<Area> blockedAreas = pNeutral.getBlockedAreas();
    			for (final Area pA : blockedAreas)
    			for (final Area pB : blockedAreas) {
    				if (pB.equals(pA)) {
                        break; // breaks symmetry
                    }

                    // findCond
                    // visitCond
                    final WalkPosition center = getMap().breadthFirstSearch(
                            pNeutral.getCenter().toWalkPosition(),
                            args -> {
                                Object ttile = args[0];
                                if (!(ttile instanceof MiniTile)) {
                                    throw new IllegalArgumentException();
                                }
                                MiniTile miniTile = (MiniTile) ttile;
                                return miniTile.isWalkable();
                            },
                            args -> true
                    );

                    final List<WalkPosition> list = new ArrayList<>();
                    list.add(center);
    				getChokePoints(pA, pB).add(new ChokePoint(this, new Index(newIndex), pA, pB, list, pNeutral));
                    newIndex = newIndex.add(1);
    			}
    		}
        }

    	// 5) Set the references to the freshly created Chokepoints:
    	for (int loopA = 1; loopA <= areasCount(); ++loopA)
    	for (int loopB = 1; loopB < loopA; ++loopB) {
            final AreaId a = new AreaId(loopA);
            final AreaId b = new AreaId(loopB);
    		if (!getChokePoints(a, b).isEmpty()) {
                ((AreaInitializer) getArea(a)).addChokePoints(getArea(b), getChokePoints(a, b));
                ((AreaInitializer) getArea(b)).addChokePoints(getArea(a), getChokePoints(a, b));

                chokePoints.addAll(getChokePoints(a, b));
    		}
        }
    }

    //----------------------------------------------------------------------

    public void computeChokePointDistanceMatrix() {
    	// 1) size the matrix
        chokePointDistanceMatrix.clear();
//    	m_ChokePointDistanceMatrix.resize (chokePoints.size());
        for (int i = 0; i < chokePoints.size(); ++i) {
            chokePointDistanceMatrix.add(new ArrayList<>());
        }
//    	for (auto & line : chokePointDistanceMatrix)
//    		line.resize (chokePoints.size(), -1);
        for (int i = 0; i < chokePointDistanceMatrix.size(); ++i) {
            for (int j = 0; j < chokePoints.size(); ++j) {
                chokePointDistanceMatrix.get(i).add(-1);
            }
        }

//    	m_PathsBetweenChokePoints.resize (chokePoints.size());
        pathsBetweenChokePoints.clear();
        for (int i = 0; i < chokePoints.size(); ++i) {
            pathsBetweenChokePoints.add(new ArrayList<>());
        }
//    	for (auto & line : pathsBetweenChokePoints)
//    		line.resize (chokePoints.size());
        for (int i = 0; i < pathsBetweenChokePoints.size(); ++i) {
            for (int j = 0; j < chokePoints.size(); ++j) {
                pathsBetweenChokePoints.get(i).add(new CPPath());
            }
        }

    	// 2) Compute distances inside each Area
    	for (Area area : getAreas()) {
    		computeChokePointDistances(area);
        }

    	// 3) Compute distances through connected areas
    	computeChokePointDistances(this);

    	for (ChokePoint cp : getChokePoints()) {
    		setDistance(cp, cp, 0);
            CPPath cppath = new CPPath();
            cppath.add(cp);
    		setPath(cp, cp, cppath);
    	}

    	// 4) Update Area::m_AccessibleNeighbours for each Area
    	for (Area area : getAreas())
            ((AreaInitializer) area).updateAccessibleNeighbors();

    	// 5)  Update Area::m_groupId for each Area
    	updateGroupIds();
    }

    public void collectInformation() {
        // 1) Process the whole Map:

        for (final Mineral mineral : getMap().getNeutralData().getMinerals()) {
            final Area area = getMap().getMainArea(mineral.getTopLeft(), mineral.getSize());
            if (area != null) {
                ((AreaInitializer) area).addMineral(mineral);
            }
        }

        for (Geyser geyser : getMap().getNeutralData().getGeysers()) {
            final Area area = getMap().getMainArea(geyser.getTopLeft(), geyser.getSize());
            if (area != null) {
                ((AreaInitializer) area).addGeyser(geyser);
            }
        }

        for (int y = 0; y < getMap().getData().getMapData().getTileSize().getY(); ++y)
        for (int x = 0; x < getMap().getData().getMapData().getTileSize().getX(); ++x) {
            final Tile tile = getMap().getData().getTile(new TilePosition(x, y));
            if (tile.getAreaId().intValue() > 0) {
                ((AreaInitializer) getArea(tile.getAreaId())).addTileInformation(new TilePosition(x, y), tile);
            }
        }

        // 2) Post-process each Area separately:

        for (final Area area : areas) {
            ((AreaInitializer) area).postCollectInformation();
        }
    }

    public void createBases(final AdvancedData mapAdvancedData) {
        baseCount = 0;
        for (Area area : areas) {
            ((AreaInitializer) area).createBases(mapAdvancedData);
            baseCount += area.getBases().size();
        }
    }

    // Computes the ground distances between any pair of getChokePoints in pContext
    // This is achieved by invoking several times pContext->computeDistances,
    // which effectively computes the distances from one starting ChokePoint, using Dijkstra's algorithm.
    // If Context == Area, Dijkstra's algorithm works on the Tiles inside one Area.
    // If Context == Graph, Dijkstra's algorithm works on the getChokePoints between the AreaS.
    private void computeChokePointDistances(final Area pContext) {
        for (final ChokePoint pStart : pContext.getChokePoints()) {
            final List<ChokePoint> targets = new ArrayList<>();
            for (final ChokePoint cp : pContext.getChokePoints()) {
                if (cp.equals(pStart)) {
                    break; // breaks symmetry
                }
                targets.add(cp);
            }

            final int[] distanceToTargets = ((AreaInitializer) pContext).computeDistances(pStart, targets);

            for (int i = 0; i < targets.size(); ++i) {
                final int newDist = distanceToTargets[i];
                final int existingDist = distance(pStart, targets.get(i));

                if (newDist != 0 && ((existingDist == -1) || (newDist < existingDist))) {
                    setDistance(pStart, targets.get(i), newDist);

                    // Build the path from pStart to targets[i]:

                    final CPPath path = new CPPath();
                    path.add(pStart);
                    path.add(targets.get(i));

                    setPath(pStart, targets.get(i), path);
                }
            }
        }
    }

    // Computes the ground distances between any pair of getChokePoints in pContext
    // This is achieved by invoking several times pContext->computeDistances,
    // which effectively computes the distances from one starting ChokePoint, using Dijkstra's algorithm.
    // If Context == Area, Dijkstra's algorithm works on the Tiles inside one Area.
    // If Context == Graph, Dijkstra's algorithm works on the getChokePoints between the AreaS.
    private void computeChokePointDistances(final Graph pContext) {
        for (final ChokePoint pStart : pContext.getChokePoints()) {
            final List<ChokePoint> targets = new ArrayList<>();
            for (final ChokePoint cp : pContext.getChokePoints()) {
                if (cp.equals(pStart)) {
                    break; // breaks symmetry
                }
                targets.add(cp);
            }

            final int[] distanceToTargets = pContext.computeDistances(pStart, targets);

            for (int i = 0; i < targets.size(); ++i) {
                final int newDist = distanceToTargets[i];
                ChokePoint target = targets.get(i);
                final int existingDist = distance(pStart, target);

                if (newDist != 0 && ((existingDist == -1) || (newDist < existingDist))) {
                    setDistance(pStart, target, newDist);

                    // Build the path from pStart to targets[i]:

                    final CPPath path = new CPPath();
                    path.add(pStart);
                    path.add(target);

//                    // if (Context == Graph), there may be intermediate getChokePoints. They have been set by computeDistances,
//                    // so we just have to collect them (in the reverse order) and insert them into Path:
//                    if ((void *)(pContext) == (void *)(this))	// tests (Context == Graph) without warning about constant condition
                        //TODO: Verify this loop is correct.
                        for (ChokePoint pPrev = target.getPathBackTrace(); !pPrev.equals(pStart); pPrev = pPrev.getPathBackTrace()) {
                            path.add(1, pPrev);
                        }

                    setPath(pStart, target, path);
                }
            }
        }
    }

    // Returns Distances such that Distances[i] == ground_distance(start, targets[i]) in pixels
    // Any Distances[i] may be 0 (meaning targets[i] is not reachable).
    // This may occur in the case where start and targets[i] leave in different continents or due to Bloqued intermediate ChokePoint(s).
    // For each reached target, the shortest path can be derived using
    // the backward trace set in cp->getPathBackTrace() for each intermediate ChokePoint cp from the target.
    // Note: same algo than Area::computeDistances (derived from Dijkstra)
    private int[] computeDistances(final ChokePoint start, final List<ChokePoint> targets) {
        final int[] distances = new int[targets.size()];

        TileImpl.getStaticMarkable().unmarkAll();

//        final MultiValuedMap<Integer, ChokePoint> toVisit = new ArrayListValuedHashMap<>(); // a priority queue holding the getChokePoints to visit ordered by their distance to start.
                                                                                            //Using ArrayListValuedHashMap to substitute std::multimap since it sorts keys but not values.
        Queue<Pair<Integer, ChokePoint>> toVisit = new PriorityQueue<>(Comparator.comparingInt(a -> a.first));
        toVisit.offer(new Pair<>(0, start));

        int remainingTargets = targets.size();
        while (!toVisit.isEmpty()) {
            Pair<Integer, ChokePoint> distanceAndChokePoint = toVisit.poll();
            final int currentDist = distanceAndChokePoint.first;
            final ChokePoint current = distanceAndChokePoint.second;
            final Tile currentTile = getMap().getData().getTile(current.getCenter().toTilePosition(), CheckMode.NO_CHECK);
//            bwem_assert(currentTile.InternalData() == currentDist);
            if (!(((TileImpl) currentTile).getInternalData().intValue() == currentDist)) {
                throw new IllegalStateException();
            }
            ((TileImpl) currentTile).getInternalData().setValue(0); // resets Tile::m_internalData for future usage
            currentTile.getMarkable().setMarked();

            for (int i = 0; i < targets.size(); ++i) {
                if (current == targets.get(i)) {
                    distances[i] = currentDist;
                    --remainingTargets;
                }
            }
            if (remainingTargets == 0) {
                break;
            }

            if (current.isBlocked() && (!current.equals(start))){
                continue;
            }

            final Area[] areas = {current.getAreas().getLeft(), current.getAreas().getRight()};
            for (final Area pArea : areas) {
                for (final ChokePoint next : pArea.getChokePoints()) {
                    if (!next.equals(current)) {
                        final int newNextDist = currentDist + distance(current, next);
                        final Tile nextTile = getMap().getData().getTile(next.getCenter().toTilePosition(), CheckMode.NO_CHECK);
                        if (!nextTile.getMarkable().isMarked()) {
                            if (((TileImpl) nextTile).getInternalData().intValue() != 0) { // next already in toVisit
                                if (newNextDist < ((TileImpl) nextTile).getInternalData().intValue()) { // nextNewDist < nextOldDist
                                                                                           // To update next's distance, we need to remove-insert it from toVisit:
//                                    bwem_assert(iNext != range.second);
                                    final boolean removed = toVisit.remove(new Pair<>(((TileImpl) nextTile).getInternalData().intValue(), next));
                                    if (!removed) {
                                        throw new IllegalStateException();
                                    }
                                    ((TileImpl) nextTile).getInternalData().setValue(newNextDist);
                                    next.setPathBackTrace(current);
                                    toVisit.offer(new Pair<>(newNextDist, next));
                                }
                            } else {
                                ((TileImpl) nextTile).getInternalData().setValue(newNextDist);
                                next.setPathBackTrace(current);
                                toVisit.offer(new Pair<>(newNextDist, next));
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

        // reset Tile::m_internalData for future usage
        for (Pair<Integer, ChokePoint> distanceToChokePoint : toVisit) {
            ((TileImpl) getMap().getData().getTile(distanceToChokePoint.second.getCenter().toTilePosition(), CheckMode.NO_CHECK)).getInternalData().setValue(0);
        }

        return distances;
    }

    private void updateGroupIds() {
    	int nextGroupId = 1;

    	AreaInitializerImpl.getStaticMarkable().unmarkAll();
    	for (final Area start : getAreas()) {
    		if (!((AreaInitializer) start).getMarkable().isMarked()) {
    			final List<Area> toVisit = new ArrayList<>();
                toVisit.add(start);
    			while (!toVisit.isEmpty()) {
    				final Area current = toVisit.remove(toVisit.size() - 1);
                    ((AreaInitializer) current).setGroupId(new GroupId(nextGroupId));

    				for (final Area next : current.getAccessibleNeighbors()) {
    					if (!((AreaInitializer) next).getMarkable().isMarked()) {
                            ((AreaInitializer) next).getMarkable().setMarked();
    						toVisit.add(next);
    					}
                    }
    			}
                ++nextGroupId;
    		}
        }
    }

    private void setDistance(final ChokePoint cpA, final ChokePoint cpB, final int value) {
        chokePointDistanceMatrix.get(cpA.getIndex().intValue()).set(cpB.getIndex().intValue(), value);
        chokePointDistanceMatrix.get(cpB.getIndex().intValue()).set(cpA.getIndex().intValue(), value);
    }

    private void setPath(final ChokePoint cpA, final ChokePoint cpB, final CPPath pathAB) {
        pathsBetweenChokePoints.get(cpA.getIndex().intValue()).set(cpB.getIndex().intValue(), pathAB);

        pathsBetweenChokePoints.get(cpB.getIndex().intValue()).get(cpA.getIndex().intValue()).clear();
        for (int i = pathAB.size() - 1; i >= 0; --i) {
            final ChokePoint cp = pathAB.get(i);
            pathsBetweenChokePoints.get(cpB.getIndex().intValue()).get(cpA.getIndex().intValue()).add(cp);
        }
    }

    private boolean isValid(AreaId id) {
        return (1 <= id.intValue() && id.intValue() <= areasCount());
    }

}
