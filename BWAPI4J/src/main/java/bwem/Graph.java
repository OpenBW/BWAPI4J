package bwem;

import bwem.area.Area;
import bwem.area.AreaInitializer;
import bwem.area.AreaInitializerImpl;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.map.AdvancedData;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import bwem.typedef.Altitude;
import bwem.typedef.CPPath;
import bwem.typedef.Index;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
import bwem.util.Utils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public final class Graph {

    private final Map map;
    private final List<Area> areas = new ArrayList<>();
    private final List<ChokePoint> chokePoints = new ArrayList<>();
    private final List<List<List<ChokePoint>>> chokePointsMatrix = new ArrayList<>(); // index == Area::id x Area::id
    private final List<List<Integer>> chokePointDistanceMatrix = new ArrayList<>(); // index == ChokePoint::index x ChokePoint::index
    private final List<List<CPPath>> pathsBetweenChokePoints = new ArrayList<>(); // index == ChokePoint::index x ChokePoint::index
    private final List<Base> bases = new ArrayList<>();

    public Graph(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public int getAreaCount() {
        return areas.size();
    }

    public Area getArea(final AreaId id) {
//        bwem_assert(valid(id));
        if (!isValid(id)) {
            throw new IllegalArgumentException();
        }
        return areas.get(id.intValue() - 1);
    }

    public Area getArea(final WalkPosition walkPosition) {
        final AreaId areaId = getMap().getData().getMiniTile(walkPosition).getAreaId();
        return (areaId.intValue() > 0)
                ? getArea(areaId)
                : null;
    }

    public Area getArea(final TilePosition tilePosition) {
        final AreaId areaId = getMap().getData().getTile(tilePosition).getAreaId();
        return (areaId.intValue() > 0)
                ? getArea(areaId)
                : null;
    }

    public Area getNearestArea(final WalkPosition walkPosition) {
        final Area area = getArea(walkPosition);
        if (area != null) {
            return area;
        }

        final WalkPosition w = getMap().breadthFirstSearch(
            walkPosition,
                // findCond
                args -> {
                    final Object ttile = args[0];
                    if (ttile instanceof MiniTile) {
                        final MiniTile miniTile = (MiniTile) ttile;
                        return (miniTile.getAreaId().intValue() > 0);
                    } else {
                        throw new IllegalArgumentException();
                    }
                },
                // visitCond
                args -> true
        );

        return getArea(w);
    }

    public Area getNearestArea(final TilePosition tilePosition) {
        final Area area = getArea(tilePosition);
        if (area != null) {
            return area;
        }

        final TilePosition t = getMap().breadthFirstSearch(
            tilePosition,
                // findCond
                args -> {
                    Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        Tile tile = (Tile) ttile;
                        return (tile.getAreaId().intValue() > 0);
                    } else {
                        throw new IllegalArgumentException();
                    }
                },
                // visitCond
                args -> true
        );

        return getArea(t);
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

        return chokePointsMatrix.get(bVal).get(aVal);
    }

    // Returns the getChokePoints between two areas.
    public List<ChokePoint> getChokePoints(final Area a, final Area b) {
        return getChokePoints(a.getId(), b.getId());
    }

	// Returns the ground distance in pixels between cpA->center() and cpB>center()
	public int distance(ChokePoint cpA, ChokePoint cpB) {
        return chokePointDistanceMatrix.get(((ChokePointImpl) cpA).getIndex().intValue()).get(((ChokePointImpl) cpB).getIndex().intValue());
    }

    // Returns a list of getChokePoints, which is intended to be the shortest walking path from cpA to cpB.
	public CPPath getPath(ChokePoint cpA, ChokePoint cpB) {
        return pathsBetweenChokePoints.get(((ChokePointImpl) cpA).getIndex().intValue()).get(((ChokePointImpl) cpB).getIndex().intValue());
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

                final Position cpEnd1 = BwemExt.center(pBestCpA.getNodePosition(ChokePoint.Node.END1));
                final Position cpEnd2 = BwemExt.center(pBestCpA.getNodePosition(ChokePoint.Node.END2));
                if (Utils.intersect(a.getX(), a.getY(), b.getX(), b.getY(), cpEnd1.getX(), cpEnd1.getY(), cpEnd2.getX(), cpEnd2.getY())) {
                    pLength.setValue(BwemExt.getApproxDistance(a, b));
                } else {
                    for (final ChokePoint.Node node : new ChokePoint.Node[]{ChokePoint.Node.END1, ChokePoint.Node.END2}) {
                        final Position c = BwemExt.center(pBestCpA.getNodePosition(node));
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

    public List<Base> getBases() {
        return this.bases;
    }

	// Creates a new Area for each pair (top, miniTiles) in areasList (See Area::top() and Area::miniTiles())
    public void createAreas(final List<MutablePair<WalkPosition, Integer>> areasList) {
        for (int id = 1; id <= areasList.size(); ++id) {
            final WalkPosition top = areasList.get(id - 1).getLeft();
            final int miniTileCount = areasList.get(id - 1).getRight();
            this.areas.add(new AreaInitializerImpl(getMap(), new AreaId(id), top, miniTileCount));
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
            final int areasCount) {

//      ChokePointsMatrix.resize(areasCount() + 1);
        chokePointsMatrix.clear();
        for (int i = 0; i < areasCount + 1; ++i) {
            chokePointsMatrix.add(new ArrayList<>());
        }
//    	for (Area::id id = 1 ; id <= areasCount() ; ++id)
//    		m_ChokePointsMatrix[id].resize(id);			// triangular matrix
        for (int id = 1; id <= areasCount; ++id) { // triangular matrix
            for (int n = 0; n < id; ++n) {
                chokePointsMatrix.get(id).add(new ArrayList<>());
            }
        }
    }
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // 2) Dispatch the global raw frontier between all the relevant pairs of areas:
    //----------------------------------------------------------------------
    private java.util.Map<MutablePair<AreaId, AreaId>, List<WalkPosition>> createRawFrontierByAreaPairMap(
            final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> rawFrontier) {

        final java.util.Map<MutablePair<AreaId, AreaId>, List<WalkPosition>> rawFrontierByAreaPair = new HashMap<>();

        for (final MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> raw : rawFrontier) {
            int a = raw.getLeft().getLeft().intValue();
            int b = raw.getLeft().getRight().intValue();
            if (a > b) {
                final int a_tmp = a;
                a = b;
                b = a_tmp;
            }
//    		bwem_assert(a <= b);
            if (!(a <= b)) {
                throw new IllegalStateException();
            }
//    		bwem_assert((a >= 1) && (b <= areasCount()));
            if (!((a >= 1) && (b <= getAreaCount()))) {
                throw new IllegalStateException();
            }

            final MutablePair<AreaId, AreaId> key = new MutablePair<>(new AreaId(a), new AreaId(b));
            rawFrontierByAreaPair.computeIfAbsent(key, mp -> new ArrayList<>()).add(raw.getRight());
        }

        return rawFrontierByAreaPair;
    }
    //----------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////

    // Creates a new Area for each pair (top, miniTiles) in AreasList (See Area::top() and Area::miniTiles())
    public void createChokePoints(
            final List<StaticBuilding> staticBuildings,
            final List<Mineral> minerals,
            final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> rawFrontier) {

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

        //Note: pseudoChokePointsToCreate is only used for pre-allocating the GetChokePoints array size.
        //      This number will highly likely be very small. There is no reason to set a minimum size.
//        int pseudoChokePointsToCreate = 0;
//        for (final Neutral blockingNeutral : blockingNeutrals) {
//            if (blockingNeutral.getNextStacked() == null) {
//                ++pseudoChokePointsToCreate;
//            }
//        }

    	// 1) size the matrix
        initializeChokePointsMatrix(this.chokePointsMatrix, getAreaCount());

    	// 2) Dispatch the global raw frontier between all the relevant pairs of areas:
        final java.util.Map<MutablePair<AreaId, AreaId>, List<WalkPosition>> rawFrontierByAreaPair
                = createRawFrontierByAreaPairMap(rawFrontier);

    	// 3) For each pair of areas (A, B):
    	for (final MutablePair<AreaId, AreaId> rawleft : rawFrontierByAreaPair.keySet()) {
    		final List<WalkPosition> rawFrontierAB = rawFrontierByAreaPair.get(rawleft);

    		// Because our dispatching preserved order,
    		// and because Map::m_RawFrontier was populated in descending order of the altitude (see Map::computeAreas),
    		// we know that rawFrontierAB is also ordered the same way, but let's check it:
    		{
    			final List<Altitude> altitudes = new ArrayList<>();
    			for (final WalkPosition w : rawFrontierAB) {
    				altitudes.add(new Altitude(getMap().getData().getMiniTile(w).getAltitude()));
                }

                // Check if the altitudes array is sorted in descending order.
//    			bwem_assert(is_sorted(altitudes.rbegin(), altitudes.rend()));
    			for (int i = 1; i < altitudes.size(); ++i) {
    			    final int prev = altitudes.get(i - 1).intValue();
    			    final int curr = altitudes.get(i).intValue();
    			    if (prev < curr) {
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
            final AreaId a = new AreaId(rawleft.getLeft());
            final AreaId b = new AreaId(rawleft.getRight());
//            getChokePoints(a, b).reserve(clusters.size() + pseudoChokePointsToCreate);
    		for (final List<WalkPosition> cluster : clusters) {
    			getChokePoints(a, b).add(new ChokePointImpl(this, new Index(newIndex), getArea(a), getArea(b), cluster));
                newIndex = newIndex.add(1);
            }
    	}

    	// 4) Create one Chokepoint for each pair of blocked areas, for each blocking Neutral:
    	for (final Neutral blockingNeutral : blockingNeutrals) {
    		if (blockingNeutral.getNextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top
    			final List<Area> blockedAreas = blockingNeutral.getBlockedAreas();
    			for (final Area blockedAreaA : blockedAreas)
    			for (final Area blockedAreaB : blockedAreas) {
    				if (blockedAreaB.equals(blockedAreaA)) {
                        break; // breaks symmetry
                    }

                    final WalkPosition center = getMap().breadthFirstSearch(
                            blockingNeutral.getCenter().toWalkPosition(),
                            // findCond
                            args -> {
                                Object ttile = args[0];
                                if (!(ttile instanceof MiniTile)) {
                                    throw new IllegalArgumentException();
                                }
                                MiniTile miniTile = (MiniTile) ttile;
                                return miniTile.isWalkable();
                            },
                            // visitCond
                            args -> true
                    );

                    final List<WalkPosition> list = new ArrayList<>();
                    list.add(center);
    				getChokePoints(blockedAreaA, blockedAreaB).add(new ChokePointImpl(this, new Index(newIndex), blockedAreaA, blockedAreaB, list, blockingNeutral));
                    newIndex = newIndex.add(1);
    			}
    		}
        }

    	// 5) Set the references to the freshly created Chokepoints:
    	for (int loopA = 1; loopA <= getAreaCount(); ++loopA)
    	for (int loopB = 1; loopB < loopA; ++loopB) {
            final AreaId a = new AreaId(loopA);
            final AreaId b = new AreaId(loopB);
    		if (!getChokePoints(a, b).isEmpty()) {
                ((AreaInitializer) getArea(a)).addChokePoints(getArea(b), getChokePoints(a, b));
                ((AreaInitializer) getArea(b)).addChokePoints(getArea(a), getChokePoints(a, b));

                this.chokePoints.addAll(getChokePoints(a, b));
    		}
        }
    }

    //----------------------------------------------------------------------

    // Computes the ground distances between any pair of ChokePoints in pContext
    // This is achieved by invoking several times pContext->ComputeDistances,
    // which effectively computes the distances from one starting ChokePoint, using Dijkstra's algorithm.
    // If Context == Area, Dijkstra's algorithm works on the Tiles inside one Area.
    // If Context == Graph, Dijkstra's algorithm works on the GetChokePoints between the AreaS.
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
            for (int n = 0; n < chokePoints.size(); ++n) {
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
            for (int n = 0; n < chokePoints.size(); ++n) {
                pathsBetweenChokePoints.get(i).add(new CPPath());
            }
        }

    	// 2) Compute distances inside each Area
    	for (final Area area : getAreas()) {
    		computeChokePointDistances(area);
        }

    	// 3) Compute distances through connected areas
    	computeChokePointDistances(this);

    	for (final ChokePoint cp : getChokePoints()) {
    		setDistance(cp, cp, 0);
            CPPath cppath = new CPPath();
            cppath.add(cp);
    		setPath(cp, cp, cppath);
    	}

    	// 4) Update Area::m_AccessibleNeighbors for each Area
    	for (final Area area : getAreas())
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

        for (final Area area : this.areas) {
            ((AreaInitializer) area).postCollectInformation();
        }
    }

    public void createBases(final AdvancedData mapAdvancedData) {
        this.bases.clear();
        for (final Area area : this.areas) {
            ((AreaInitializer) area).createBases(mapAdvancedData);
            this.bases.addAll(area.getBases());
        }
    }

    ////////////////////////////////////////////////////////////////////////
    // Graph::ComputeChokePointDistances
    ////////////////////////////////////////////////////////////////////////

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

            setPathForComputeChokePointDistances(distanceToTargets, pStart, targets, false);
        }
    }

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

            setPathForComputeChokePointDistances(distanceToTargets, pStart, targets, true);
        }
    }

    private void setPathForComputeChokePointDistances(final int[] distanceToTargets, ChokePoint pStart, List<ChokePoint> targets, boolean collectIntermediateChokePoints) {
        for (int i = 0; i < targets.size(); ++i) {
            final int newDist = distanceToTargets[i];
            final ChokePoint target = targets.get(i);
            final int existingDist = distance(pStart, target);

            if (newDist != 0 && ((existingDist == -1) || (newDist < existingDist))) {
                setDistance(pStart, target, newDist);

                // Build the path from pStart to targets[i]:

                final CPPath path = new CPPath();
                path.add(pStart);
                path.add(target);

//                // if (Context == Graph), there may be intermediate getChokePoints. They have been set by computeDistances,
//                // so we just have to collect them (in the reverse order) and insert them into Path:
//                if ((void *)(pContext) == (void *)(this))	// tests (Context == Graph) without warning about constant condition
                if (collectIntermediateChokePoints) {
                    for (ChokePoint pPrev = ((ChokePointImpl) target).getPathBackTrace();
                         !pPrev.equals(pStart);
                         pPrev = ((ChokePointImpl) pPrev).getPathBackTrace()) {

                        path.add(1, pPrev);
                    }
                }

                setPath(pStart, target, path);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////

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
        final Queue<Pair<Integer, ChokePoint>> toVisit = new PriorityQueue<>(Comparator.comparingInt(a -> a.first));
        toVisit.offer(new Pair<>(0, start));

        int remainingTargets = targets.size();
        while (!toVisit.isEmpty()) {
            final Pair<Integer, ChokePoint> distanceAndChokePoint = toVisit.poll();
            final int currentDist = distanceAndChokePoint.first;
            final ChokePoint current = distanceAndChokePoint.second;
            final Tile currentTile = getMap().getData().getTile(current.getCenter().toTilePosition(), CheckMode.NO_CHECK);
//            bwem_assert(currentTile.InternalData() == currentDist);
            if (!(((TileImpl) currentTile).getInternalData() == currentDist)) {
                throw new IllegalStateException();
            }
            ((TileImpl) currentTile).setInternalData(0); // resets Tile::m_internalData for future usage
            ((TileImpl) currentTile).getMarkable().setMarked();

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

            for (final Area pArea : new Area[]{current.getAreas().getFirst(), current.getAreas().getSecond()}) {
                for (final ChokePoint next : pArea.getChokePoints()) {
                    if (!next.equals(current)) {
                        final int newNextDist = currentDist + distance(current, next);
                        final Tile nextTile = getMap().getData().getTile(next.getCenter().toTilePosition(), CheckMode.NO_CHECK);
                        if (!((TileImpl) nextTile).getMarkable().isMarked()) {
                            if (((TileImpl) nextTile).getInternalData() != 0) { // next already in toVisit
                                if (newNextDist < ((TileImpl) nextTile).getInternalData()) { // nextNewDist < nextOldDist
                                                                                           // To update next's distance, we need to remove-insert it from toVisit:
//                                    bwem_assert(iNext != range.second);
                                    final boolean removed = toVisit.remove(new Pair<>(((TileImpl) nextTile).getInternalData(), next));
                                    if (!removed) {
                                        throw new IllegalStateException();
                                    }
                                    ((TileImpl) nextTile).setInternalData(newNextDist);
                                    ((ChokePointImpl) next).setPathBackTrace(current);
                                    toVisit.offer(new Pair<>(newNextDist, next));
                                }
                            } else {
                                ((TileImpl) nextTile).setInternalData(newNextDist);
                                ((ChokePointImpl) next).setPathBackTrace(current);
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
            ((TileImpl) getMap().getData().getTile(distanceToChokePoint.second.getCenter().toTilePosition(), CheckMode.NO_CHECK)).setInternalData(0);
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
        final int indexA = ((ChokePointImpl) cpA).getIndex().intValue();
        final int indexB = ((ChokePointImpl) cpB).getIndex().intValue();
        this.chokePointDistanceMatrix.get(indexA).set(indexB, value);
        this.chokePointDistanceMatrix.get(indexB).set(indexA, value);
    }

    private void setPath(final ChokePoint cpA, final ChokePoint cpB, final CPPath pathAB) {
        final int indexA = ((ChokePointImpl) cpA).getIndex().intValue();
        final int indexB = ((ChokePointImpl) cpB).getIndex().intValue();

        this.pathsBetweenChokePoints.get(indexA).set(indexB, pathAB);

        if (cpA != cpB) {
            final CPPath reversePath = this.pathsBetweenChokePoints.get(indexB).get(indexA);
            reversePath.clear();
            for (int i = pathAB.size() - 1; i >= 0; --i) {
                final ChokePoint cp = pathAB.get(i);
                reversePath.add(cp);
            }
        }
    }

    private boolean isValid(AreaId id) {
        return (1 <= id.intValue() && id.intValue() <= getAreaCount());
    }

}
