package bwem;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;

public class BWEM {

    public static final int MAX_LAKE_MINI_TILES = 300;

    private BWEM() {}

//    /* map.cpp:29 */
////    bool seaSide(WalkPosition p, const Map * pMap)
////    {
////        if (!pMap->GetMiniTile(p).Sea()) return false;
////
////        for (WalkPosition delta : {WalkPosition(0, -1), WalkPosition(-1, 0), WalkPosition(+1, 0), WalkPosition(0, +1)})
////            if (pMap->Valid(p + delta))
////                if (!pMap->GetMiniTile(p + delta, check_t::no_check).Sea())
////                    return true;
////
////        return false;
////    }
//    /**
//     * Tests if the specified position has any non-sea neighbors.
//     *
//     * @param p specified position
//     * @param map specified map containing the specified position
//     */
//    public static boolean hasNonSeaNeighbor(WalkPosition p, Map map) {
//        if (!map.getMiniTile(p).isSea()) {
//            return false;
//        }
//
//        WalkPosition[] deltas = { new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1) };
//        for (WalkPosition delta : deltas) {
//            WalkPosition pDelta = p.add(delta);
//            if (map.isValid(pDelta) && !map.getMiniTile(pDelta).isSea()) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public static int squaredNorm(int dx, int dy) {
        return (dx * dx + dy * dy);
    }

    public static double norm(int dx, int dy) {
        return Math.sqrt((double) squaredNorm(dx, dy));
    }

    public static boolean get_line_intersection(
            double p0_x, double p0_y,
            double p1_x, double p1_y,
            double p2_x, double p2_y,
            double p3_x, double p3_y,
            MutableDouble i_x,
            MutableDouble i_y) {
        double s1_x, s1_y;
        double s2_x, s2_y;
        s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;

        double s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (Double.compare(s, 0) >= 0 && Double.compare(s, 1) <= 0
                && Double.compare(t, 0) >= 0 && Double.compare(t, 1) <= 0)
        {
            // Collision detected
            if (i_x != null) i_x.setValue(p0_x + (t * s1_x));
            if (i_y != null) i_y.setValue(p0_y + (t * s1_y));
            return true;
        }

        return false; // No collision
    }

    public static boolean intersect(int ax, int ay, int bx, int by, int cx, int cy, int dx, int dy) {
        return get_line_intersection(ax, ay, bx, by, cx, cy, dx, dy, null, null);
    }

//    template<typename T, int Scale = 1>
//    inline BWAPI::Position center(BWAPI::Point<T, Scale> A)	{ return BWAPI::Position(A) + BWAPI::Position(Scale/2, Scale/2); }
    public static Position getCenter(WalkPosition w) {
        Position delta = new Position(MiniTile.SIZE_IN_PIXELS / 2, MiniTile.SIZE_IN_PIXELS / 2);
        Position center = w.toPosition().add(delta);
        return center;
    }

}
