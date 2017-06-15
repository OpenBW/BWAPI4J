package bwem;

import org.openbw.bwapi4j.WalkPosition;

public class Area {

    private Graph graph = null;
    private Id areaId;
    private WalkPosition top = null;

    private Area() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

//    public Area (Graph graph, Id areaId, WalkPosition top, int miniTiles) {
//        //TODO
//        this.graph = graph;
//        this.areaId = areaId;
//        this.top = top;
//    }

//    public Area(Area area) {
//        //TODO
//    }

    public static class Id implements IWrappedInteger<Id>, Comparable<Id> {

        private int val;

        private Id() {
            throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
        }

        public Id(int val) {
            this.val = val;
        }

        public Id(Id id) {
            this.val = id.val;
        }

        @Override
        public Id add(Id that) {
            return new Id(this.val + that.val);
        }

        @Override
        public Id subtract(Id that) {
            return new Id(this.val + that.val);
        }

        @Override
        public int toInt() {
            return this.val;
        }

        @Override
        public int compareTo(Id that) {
            int lhs = this.val;
            int rhs = that.val;
            return (lhs < rhs) ? -1 : (lhs > rhs) ? 1 : 0;
        }

    }

}
