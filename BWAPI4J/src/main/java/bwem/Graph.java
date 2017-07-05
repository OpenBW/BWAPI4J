package bwem;

import bwem.unit.Mineral;
import bwem.unit.Neutral;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.util.Pair;

public class Graph {

    private Map map = null;
    private List<Area> areas = null;
    private List<Chokepoint> chokepointList;
    private List<List<List<Chokepoint>>> chokePointsMatrix = null; // index == Area::id x Area::id
    private List<List<Integer>> chokepointDistanceMatrix = null; // index == ChokePoint::index x ChokePoint::index
    private List<List<CPPath>> pathsBetweenChokepoints = null; // index == ChokePoint::index x ChokePoint::index

    /**
     * Disabled default constructor.
     */
    private Graph() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    /**
     * Class constructor.
     *
     * @param map The Map object to use in class methods.
     */
    public Graph(Map map) {
        this.map = map;
        this.areas = new ArrayList<>();
        this.chokepointList = new ArrayList<>();
        this.chokePointsMatrix = new ArrayList<>();
        this.chokepointDistanceMatrix = new ArrayList<>();
        this.pathsBetweenChokepoints = new ArrayList<>();
    }

    public Map getMap() {
        return this.map;
    }

    public CPPath getPath(Position a, Position b, MutableInt length) {
        Area areaA = getNearestArea(a.toWalkPosition());
        Area areaB = getNearestArea(b.toWalkPosition());

        if (areaA.equals(areaB)) {
            if (length != null) {
                length.setValue((int) a.getDistance(b));
            }
            return new CPPath(); //TODO: Return empty list or null?
        }

        if (!areaA.isAccessibleFrom(areaB)) {
            if (length != null) {
                length.setValue(-1);
            }
            return new CPPath(); //TODO: Return empty list or null?
        }

        int minDist_A_B = Integer.MAX_VALUE;

        Chokepoint bestCpA = null;
        Chokepoint bestCpB = null;

        for (Chokepoint cpA : areaA.getChokepoints()) {
            if (cpA.isBlocked()) {
                continue;
            }
            int dist_A_cpA = (int) a.getDistance(cpA.getCenter().toPosition());
            for (Chokepoint cpB : areaB.getChokepoints()) {
                if (cpB.isBlocked()) {
                    continue;
                }
                int dist_B_cpB = (int) b.getDistance(cpB.getCenter().toPosition());
                int dist_A_B = dist_A_cpA + dist_B_cpB + getDistance(cpA, cpB);
                if (dist_A_B < minDist_A_B)
                {
                    minDist_A_B = dist_A_B;
                    bestCpA = cpA;
                    bestCpB = cpB;
                }
            }
        }

//        bwem_assert(minDist_A_B != numeric_limits<int>::max());
        if (!(minDist_A_B != Integer.MAX_VALUE)) {
            throw new IllegalStateException("failed assert: minDist_A_B != Integer.MAX_VALUE");
        }

        CPPath path = getPath(bestCpA, bestCpB);

        if (length != null) {
//            bwem_assert(Path.size() >= 1);
            if (!(path.size() >= 1)) {
                throw new IllegalStateException("failed assert: path.size() >= 1");
            }

            length.setValue(minDist_A_B);

            if (path.size() == 1) {
                if (!(bestCpA.equals(bestCpB))) {
                    throw new IllegalStateException("failed assert: bestCpA.equals(bestCpB)");
                }
                Chokepoint cp = bestCpA;

                Position cpEnd1 = BWEM.getCenter(cp.getPosition(Chokepoint.Node.End1));
                Position cpEnd2 = BWEM.getCenter(cp.getPosition(Chokepoint.Node.End2));
                if (BWEM.intersect(a.getX(), a.getY(), b.getX(), b.getY(), cpEnd1.getX(), cpEnd1.getY(), cpEnd2.getX(), cpEnd2.getY())) {
                    length.setValue(a.getDistance(b));
                } else {
                    Chokepoint.Node[] nodes = {Chokepoint.Node.End1, Chokepoint.Node.End2};
                    for (Chokepoint.Node node : nodes) {
                        Position c = BWEM.getCenter(cp.getPosition(node));
                        int dist_A_B = (int) (a.getDistance(c) + b.getDistance(c));
                        if (dist_A_B < length.intValue()) {
                            length.setValue(dist_A_B);
                        }
                    }
                }
            }
        }

        return getPath(bestCpA, bestCpB);
    }

    public CPPath getPath(Position a, Position b) {
        return getPath(a, b, null);
    }

    public CPPath getPath(Chokepoint a, Chokepoint b) {
        return this.pathsBetweenChokepoints.get(a.getIndex().intValue()).get(b.getIndex().intValue());
    }

    public Area getArea(Area.Id areaId) {
//        bwem_assert(Valid(id));
        if (!isValid(areaId)) {
            throw new IllegalArgumentException("Area.Id is not valid");
        } else {
            return this.areas.get(areaId.intValue() - 1);
        }
    }

    public Area getArea(WalkPosition w) {
        Area.Id areaId = this.map.getMiniTile(w).getAreaId();
        return (areaId.intValue() > 0) ? getArea(areaId) : null;
    }

    public Area getNearestArea(WalkPosition w) {
        Area area = getArea(w);
        if (area != null) {
            return area;
        }

        w = this.map.breadthFirstSearch(
            w,
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof MiniTile) {
                        MiniTile miniTile = (MiniTile) ttile;
                        return (miniTile.getAreaId().intValue() > 0);
                    } else {
                        throw new IllegalArgumentException("tile type not supported");
                    }
                }
            },
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    return true;
                }
            }
        );

        return getArea(w);
    }

    private boolean isValid(Area.Id areaId) {
        return (areaId.intValue() >= 1 && this.areas.size() >= areaId.intValue());
    }

    private int getDistance(Chokepoint cpA, Chokepoint cpB) {
        return this.chokepointDistanceMatrix.get(cpA.getIndex().intValue()).get(cpB.getIndex().intValue());
    }

    /**
     * This method should only be used in the initialization phase.
     * //TODO: Hide this from the API.
     */
    public void createAreas(List<Pair<WalkPosition, Integer>> areas) {
        for (int id = 1; id <= areas.size(); id++) {
            WalkPosition top = areas.get(id - 1).first;
            int miniTiles = areas.get(id - 1).second;
            this.areas.add(new Area(this.map, new Area.Id(id), top, miniTiles));
        }
    }

    public List<Chokepoint> getChokepoints(Area.Id a, Area.Id b) {
//        bwem_assert(Valid(a));
//        bwem_assert(Valid(b));
//        bwem_assert(a != b);
        if (!isValid(a)) {
            throw new IllegalArgumentException("a is not valid");
        } else if (!isValid(b)) {
            throw new IllegalArgumentException("b is not valid");
        } else if (!(a.intValue() != b.intValue())) {
            throw new IllegalArgumentException("a cannot equal b");
        } else {
            Area.Id tmpA = new Area.Id(a);
            Area.Id tmpB = new Area.Id(b);
            if (tmpA.intValue() > tmpB.intValue()) {
                Area.Id tmp = new Area.Id(tmpA);
                tmpA = new Area.Id(tmpB);
                tmpB = new Area.Id(tmp);
            }
            return this.chokePointsMatrix.get(tmpB.intValue()).get(tmpA.intValue());
        }
    }

    /**
     * This method should only be used in the initialization phase.
     * //TODO: Hide this from the API.
     */
    public void createChokepoints() {
       Index newIndex = new Index(0);

        List<Neutral> blockingNeutrals = new ArrayList<>();
        for (StaticBuilding s : this.map.getStaticBuildings()) {
            if (s.isBlocking()) {
                blockingNeutrals.add(s);
            }
        }
        for (Mineral m : this.map.getMineralPatches()) {
            if (m.isBlocking()) {
                blockingNeutrals.add(m);
            }
        }

//        const int pseudoChokePointsToCreate = count_if(BlockingNeutrals.begin(), BlockingNeutrals.end(),
//                                                [](const Neutral * n){ return !n->NextStacked(); });
        //TODO: This may not be required in the Java port.
        int pseudoChokePointsToCreate = 0;
        for (Neutral neutral : blockingNeutrals) {
            if (neutral.getNextStacked() == null) {
                pseudoChokePointsToCreate++;
            }
        }

        /**
         * 1) Size the matrix
         */

        for (int i = 0; i < this.areas.size() + 1; i++) {
            this.chokePointsMatrix.add(new ArrayList<>());
        }
        for (int id = 1; id <= this.areas.size(); id++) {
            this.chokePointsMatrix.get(id).add(new ArrayList<>()); // triangular matrix
        }

        /**
         * 2) Dispatch the global raw frontier between all the relevant pairs of Areas:
         */

        AbstractMap<Pair<Area.Id, Area.Id>, List<WalkPosition>> rawFrontierByAreaPair = new HashMap<>();
        for (Pair<Pair<Area.Id, Area.Id>, WalkPosition> raw : this.map.getRawFrontier()) {
            Area.Id a = raw.first.first;
            Area.Id b = raw.first.second;
            if (a.intValue() > b.intValue()) {
                Area.Id tmp = new Area.Id(a);
                a = new Area.Id(b);
                b = new Area.Id(tmp);
            }

//            bwem_assert(a <= b);
            if (!(a.intValue() <= b.intValue())) {
                throw new IllegalStateException();
            }
//            bwem_assert((a >= 1) && (b <= AreasCount()));
            if (!((a.intValue() >= 1) && (b.intValue() <= this.areas.size()))) {
                throw new IllegalStateException();
            }

//            RawFrontierByAreaPair[make_pair(a, b)].push_back(raw.second);
            Pair<Area.Id, Area.Id> pair = new Pair<>(a, b);
            List<WalkPosition> list = rawFrontierByAreaPair.get(pair);
            if (list == null) {
                rawFrontierByAreaPair.put(new Pair<>(a, b), new ArrayList<>());
                list = rawFrontierByAreaPair.get(pair);
            }
            list.add(raw.second);
        }

        /**
         * 3) For each pair of Areas (A, B):
         */

        for (Pair<Area.Id, Area.Id> raw : rawFrontierByAreaPair.keySet()) {
            Area.Id a = raw.first;
            Area.Id b = raw.second;

            List<WalkPosition> rawFrontierAB = rawFrontierByAreaPair.get(raw);

            // Because our dispatching preserved order,
            // and because Map::m_RawFrontier was populated in descending order of the altitude (see Map::ComputeAreas),
            // we know that RawFrontierAB is also ordered the same way, but let's check it:
            {
                List<Altitude> altitudes = new ArrayList<>();
                for (WalkPosition w : rawFrontierAB) {
                    altitudes.add(this.map.getMiniTile(w).getAltitude());
                }
    //			bwem_assert(is_sorted(Altitudes.rbegin(), Altitudes.rend()));
                for (int i = 1; i < altitudes.size(); i++) {
                    if (altitudes.get(i).intValue() > altitudes.get(i - 1).intValue()) {
                        throw new IllegalStateException("altitudes are not sorted in descending order");
                    }
                }
            }

            /**
             * 3.1) Use that information to efficiently cluster RawFrontierAB in one or several chokepoints.
             * Each cluster will be populated starting with the center of a chokepoint (max altitude)
             * and finishing with the ends (min altitude).
             */

            int cluster_min_dist = (int) BWEM.CLUSTER_MIN_DISTANCE;
            List<List<WalkPosition>> clusters = new ArrayList<>(); //TODO: Might have to use List<List<WalkPosition>> here instead of List<Deque<WalkPosition>> because of code yet-to-be-implemented that requires access to specific nth elements. See variable Chokepoint.geometry.
            for (WalkPosition w : rawFrontierAB) {
                boolean added = false;
                for (List<WalkPosition> cluster : clusters) {
                    if (cluster.size() > 0) {
                        int distToFront = BWEM.queenwiseDistance(cluster.get(0), w);
                        int distToBack = BWEM.queenwiseDistance(cluster.get(clusters.size() - 1), w);
                        if (Math.min(distToFront, distToBack) <= cluster_min_dist) {
                            if (distToFront < distToBack) {
                                cluster.add(0, w);
                            } else {
                                cluster.add(w);
                            }
                            added = true;
                            break;
                        }
                    }
                }

                if (!added) {
                    List<WalkPosition> wpq = new ArrayList<>();
                    wpq.add(w);
                    clusters.add(wpq);
                }
            }

            /**
             * 3.2) Create one Chokepoint for each cluster:
             */

            for (List<WalkPosition> cluster : clusters) {
                Chokepoint cp = new Chokepoint(this, newIndex, getArea(a), getArea(b), cluster);
                getChokepoints(a, b).add(cp);
            }
        }

        /**
         * 4) Create one Chokepoint for each pair of blocked areas, for each blocking Neutral:
         */

        for (Neutral neutral : blockingNeutrals) {
            if (neutral.getNextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top
                List<Area> blockedAreas = neutral.getBlockedAreas();
                for (Area tmpA : blockedAreas)
                for (Area tmpB : blockedAreas) {
                    if (tmpB.equals(tmpA)) {
                        // breaks symmetry
                        break;
                    }

                    WalkPosition center = this.map.breadthFirstSearch(
                        neutral.getPosition().toWalkPosition(),
                        new Pred() {
                            @Override
                            public boolean is(Object... args) {
                                Object ttile = args[0];
                                if (ttile instanceof MiniTile) {
                                    MiniTile miniTile = (MiniTile) ttile;
                                    return miniTile.isWalkable();
                                } else {
                                    throw new IllegalArgumentException("tile type not supported");
                                }
                            }
                        },
                        new Pred() {
                            @Override
                            public boolean is(Object... args) {
                                return true;
                            }
                        }
                    );

                    List<WalkPosition> tmpGeometry = new ArrayList<>();
                    tmpGeometry.add(center);
                    Chokepoint cp = new Chokepoint(this, new Index(newIndex.intValue() + 1), tmpA, tmpB, tmpGeometry, neutral);
                    getChokepoints(tmpA.getAreaId(), tmpB.getAreaId()).add(cp);
                }
            }
        }

        /**
         * 5) Set the references to the freshly created Chokepoints:
         */

        for (int a = 1; a <= this.areas.size(); ++a)
        for (int b = 1; b < a; ++b) {
            Area.Id idA = new Area.Id(a);
            Area.Id idB = new Area.Id(b);
            if (!getChokepoints(idA, idB).isEmpty()) {
                getArea(idA).addChokepoints(getArea(idB), getChokepoints(idA, idB));
                getArea(idB).addChokepoints(getArea(idA), getChokepoints(idA, idB));

                for (Chokepoint cp : getChokepoints(idA, idB)) {
                    this.chokepointList.add(cp);
                }
            }
        }
    }

}
