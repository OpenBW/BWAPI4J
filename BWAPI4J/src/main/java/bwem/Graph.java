package bwem;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Building;
import org.openbw.bwapi4j.util.Pair;

public class Graph {

    private Map map = null;
    private List<Area> areas = null;
    private List<List<Integer>> chokepointDistanceMatrix = null;
    private List<List<CPPath>> pathsBetweenChokepoints = null;

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
        this.chokepointDistanceMatrix = new ArrayList<>();
        this.pathsBetweenChokepoints = new ArrayList<>();
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

        w = this.map.breadFirstSearch(
            w,
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof MiniTile) {
                        MiniTile tile = (MiniTile) ttile;
                        return (tile.getAreaId().intValue() > 0);
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

    //TODO: Need to port/organize the custom Neutral types from original BWEM first. (e.g. Resource, StaticBuilding, etc)
    /**
     * This method should only be used in the initialization phase.
     * //TODO: Hide this from the API.
     */
    public void createChokepoints() {
       Index newIndex = new Index(0);

        List<Neutral> blockingNeutrals = new ArrayList<>();
//        for (auto & s : GetMap()->StaticBuildings())		if (s->Blocking()) BlockingNeutrals.push_back(s.get());
//        for (Building s : this.map.getStaticBuildings()) {
////            if (s.isb)
//        }
//        for (auto & m : GetMap()->Minerals())			if (m->Blocking()) BlockingNeutrals.push_back(m.get());
//
//        const int pseudoChokePointsToCreate = count_if(BlockingNeutrals.begin(), BlockingNeutrals.end(),
//                                                [](const Neutral * n){ return !n->NextStacked(); });
    }

}
