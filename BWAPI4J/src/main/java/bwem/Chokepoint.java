package bwem;

import java.util.Deque;
import java.util.List;
import java.util.Objects;
import org.openbw.bwapi4j.WalkPosition;

public class Chokepoint {

    private Index index;
    private boolean isBlocked;
    private List<WalkPosition> nodes;

    private Chokepoint() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

//    public Chokepoint(Graph graph, Index idx, Area area1, Area area2,  Deque<WalkPosition> Geometry, Neutral pBlockingNeutral) {
//        //TODO
//    }

//    public Chokepoint(Graph graph, Index idx, Area area1, Area area2, Deque<WalkPosition> Geometry) {
//        //TODO
//    }

//    public Chokepoint(Chokepoint chokepoint) {
//        //TODO
//    }

    public Index getIndex() {
        return new Index(this.index);
    }

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public WalkPosition getCenter() {
        return getPosition(Node.Middle);
    }

    public WalkPosition getPosition(Node node) {
//        bwem_assert(n < node_count);
        if (!(node.toInt() < Node.Count.toInt())) {
            throw new IllegalArgumentException();
        }
        return nodes.get(node.toInt());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Chokepoint)) {
            return false;
        } else {
            Chokepoint that = (Chokepoint) o;
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

        public int toInt() {
            return this.val;
        }

    }

}
