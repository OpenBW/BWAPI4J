package bwem;

import bwem.unit.Neutral;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.util.Pair;

public class Chokepoint {

    private Graph graph;
    private Index index;
    private Pair<Area, Area> areaPair;
    private List<WalkPosition> geometry;
    private Neutral blockingNeutral;
    private boolean isBlocked;
    private boolean pseudo;
    private List<WalkPosition> nodes;
    private List<Pair<WalkPosition, WalkPosition>> nodesInArea;

    private Chokepoint() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Chokepoint(Graph graph, Index index, Area area1, Area area2, List<WalkPosition> geometry, Neutral blockingNeutral) {
        this.graph = graph;
        this.index = index;
        this.areaPair = new Pair<>(area1, area2);
        this.geometry = geometry;
        this.blockingNeutral = blockingNeutral;
        this.isBlocked = (this.blockingNeutral != null);
        this.pseudo = this.isBlocked;
        this.nodes = new ArrayList<>();
        this.nodesInArea = new ArrayList<>();

        if (this.geometry.isEmpty()) {
            throw new IllegalArgumentException("Geometry cannot be empty.");
        }

        // Ensures that in the case where several neutrals are stacked, m_pBlockingNeutral points to the bottom one:
        if (this.blockingNeutral != null) {
            this.blockingNeutral = this.graph.getMap().getTile(this.blockingNeutral.getPosition().toTilePosition()).getOccupyingNeutral();
        }

        this.nodes.add(this.geometry.get(0));
        this.nodes.add(this.geometry.get(geometry.size() - 1));

        int i = this.geometry.size() / 2;
        while ((i > 0)
                && (this.graph.getMap().getMiniTile(this.geometry.get(i + 1)).getAltitude().intValue()
                    > this.graph.getMap().getMiniTile(this.geometry.get(i)).getAltitude().intValue())) {
            --i;
        }
        while ((i < this.geometry.size() - 1)
                && this.graph.getMap().getMiniTile(this.geometry.get(i + 1)).getAltitude().intValue()
                    > this.graph.getMap().getMiniTile(this.geometry.get(i)).getAltitude().intValue()) {
            ++i;
        }
        this.nodes.add(Node.Middle.intValue(), this.geometry.get(i));

        List<Area> tmpAreas = new ArrayList<>();
        tmpAreas.add(area1);
        tmpAreas.add(area2);
        for (int n = 0; n < Node.Count.intValue(); n++) {
            for (Area tmpArea : tmpAreas) {
                WalkPosition nodeInArea = this.graph.getMap().breadthFirstSearch(
                    this.nodes.get(n),
                    new Pred() {
                        @Override
                        public boolean is(Object... args) {
                            Object tmap = args[args.length - 1];
                            Map map = null;
                            if (tmap instanceof Map) {
                                map = (Map) tmap;
                            } else {
                                throw new IllegalArgumentException("Invalid map argument.");
                            }

                            Object ttile = args[0];
                            Object tpos = args[1];
                            if (ttile instanceof MiniTile && tpos instanceof WalkPosition) {
                                MiniTile miniTile = (MiniTile) ttile;
                                WalkPosition w = (WalkPosition) tpos;
                                TilePosition t = w.toPosition().toTilePosition();
                                return (miniTile.getAreaId().intValue() > tmpArea.getAreaId().intValue() && map.getTile(t, CheckMode.NoCheck).getOccupyingNeutral() == null);
                            } else {
                                throw new IllegalArgumentException("Invalid argument list.");
                            }
                        }
                    },
                    new Pred() {
                        @Override
                        public boolean is(Object... args) {
                            Object tmap = args[args.length - 1];
                            Map map;
                            if (tmap instanceof Map) {
                                map = (Map) tmap;
                            } else {
                                throw new IllegalArgumentException("Invalid map argument.");
                            }

                            Object ttile = args[0];
                            Object tpos = args[1];
                            if (ttile instanceof MiniTile && tpos instanceof WalkPosition) {
                                MiniTile miniTile = (MiniTile) ttile;
                                WalkPosition w = (WalkPosition) tpos;
                                TilePosition t = w.toPosition().toTilePosition();
                                return (miniTile.getAreaId().intValue() > tmpArea.getAreaId().intValue() || (isBlocked() && (miniTile.isBlocked() || map.getTile(t, CheckMode.NoCheck).getOccupyingNeutral() != null)));
                            } else {
                                throw new IllegalArgumentException("Invalid argument list.");
                            }
                        }
                    }
                );

                /**
                 * Note: In the original C++ code, "nodeInArea" is a reference to a "WalkPosition" in
                 * "nodesInArea" which changes! Change that object here (after the call to "breadthFirstSearch")...
                 */
//                WalkPosition & nodeInArea = (pArea == m_Areas.first) ? m_nodesInArea[n].first : m_nodesInArea[n].second;
                WalkPosition first = this.nodesInArea.get(n).first;
                WalkPosition second = this.nodesInArea.get(n).second;
                Pair<WalkPosition, WalkPosition> replacementPair;
                if (tmpArea.equals(this.areaPair.first)) {
                    replacementPair = new Pair<>(new WalkPosition(nodeInArea.getX(), nodeInArea.getY()), new WalkPosition(second.getX(), second.getY()));
                } else {
                    //TODO: Determine if we should test "else if (tmpArea.equals(this.areaPair.second))" and then throw an exception if that test also fails.
                    replacementPair = new Pair<>(new WalkPosition(first.getX(), first.getY()), new WalkPosition(nodeInArea.getX(), nodeInArea.getY()));
                }
                this.nodesInArea.remove(n);
                this.nodesInArea.add(n, replacementPair);
            }
        }
    }

    public Chokepoint(Graph graph, Index index, Area area1, Area area2, List<WalkPosition> geometry) {
        this(graph, index, area1, area2, geometry, null);
    }

//    public Chokepoint(Chokepoint chokepoint) {
//        //TODO
//    }

    public Index getIndex() {
        return new Index(this.index);
    }

    public WalkPosition getCenter() {
        return getPosition(Node.Middle);
    }

    public WalkPosition getPosition(Node node) {
//        bwem_assert(n < node_count);
        if (!(node.intValue() < Node.Count.intValue())) {
            throw new IllegalArgumentException();
        }
        return nodes.get(node.intValue());
    }

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public WalkPosition getPositionInArea(Node n, Area area) {
//        bwem_assert((pArea == m_Areas.first) || (pArea == m_Areas.second));
        if (!((area.equals(this.areaPair.first) || (area.equals(this.areaPair.second))))) {
            throw new IllegalArgumentException();
        }
        return area.equals(this.areaPair.first)
                ? this.nodesInArea.get(n.intValue()).first
                : this.nodesInArea.get(n.intValue()).second;
    }

    /**
     * - Tests whether the specified object is equal to this object.<br>
     * - Tests whether the specified object is an instance of this class.<br>
     * - Tests if the internal Index values match.<br>
     *
     * @param object The specified object to test against this object.
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof Chokepoint)) {
            throw new IllegalArgumentException("Object is not an instance of Chokepoint");
        } else {
            Chokepoint that = (Chokepoint) object;
            return (this.index.intValue() == that.index.intValue());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.index, this.nodes);
    }

    public static enum Node {

        End1(0),
        Middle(1),
        End2(2),
        Count(3)
        ;

        private final int val;

        private Node(int val) {
            this.val = val;
        }

        public int intValue() {
            return this.val;
        }

    }

}
