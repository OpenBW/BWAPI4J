package bwem;

import bwem.map.MapImpl;
import bwem.map.Map;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public final class BWEM {

    /**
     * This constant contributes to deciding between Seas and Lakes.
     */
    public static final int LAKE_MAX_MINI_TILES = 300;

    /**
     * This constant contributes to deciding between Seas and Lakes.
     */
    public static final int LAKE_MAX_WIDTH_IN_MINITILES = 8 * 4;

    /**
     * At least this amount of connected MiniTiles are necessary for an Area to be created.
     */
    public static final int AREA_MIN_MINI_TILES = 64;

    public static final double CLUSTER_MIN_DISTANCE = Math.sqrt(LAKE_MAX_MINI_TILES);

    private final BW bw;
    private final Map map;

    public BWEM(BW bw) {
        this.bw = bw;
        this.map = new MapImpl(this.bw);
    }

    public Map getMap() {
        return this.map;
    }


    /**
     * Tests if the specified position has any non-sea neighbors.
     *
     * @param w specified position
     * @param map specified map containing the specified position
     */
    public static boolean hasNonSeaNeighbor(WalkPosition w, Map map) {
        if (!map.getMiniTile(w).isSea()) {
            return false;
        }

        WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
        for (WalkPosition delta : deltas) {
            WalkPosition w_delta = w.add(delta);
            if (map.isValid(w_delta) && !map.getMiniTile(w_delta, CheckMode.NoCheck).isSea()) {
                return true;
            }
        }

        return false;
    }

    public static int queenwiseDistance(WalkPosition a, WalkPosition b) {
       WalkPosition c = a.subtract(b);
       return queenwiseNorm(c.getX(), c.getY());
    }

    public static int queenwiseNorm(int dx, int dy) {
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    public static int squaredNorm(int dx, int dy) {
        return (dx * dx + dy * dy);
    }

    public static double norm(int dx, int dy) {
        return Math.sqrt((double) squaredNorm(dx, dy));
    }

    public static int scalarProduct(int ax, int ay, int bx, int by) {
        return (ax * bx) + (ay * by);
    }

    /**
     * Returns true if the lines intersect, otherwise false. In addition, if the lines
     * intersect the intersection point may be stored in i_x and i_y.
     *
     * From http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
     */
    public static boolean get_line_intersection(
            double p0_x, double p0_y,
            double p1_x, double p1_y,
            double p2_x, double p2_y,
            double p3_x, double p3_y,
            MutableDouble i_x,
            MutableDouble i_y) {
        double s1_x, s1_y;
        double s2_x, s2_y;
        s1_x = p1_x - p0_x; s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x; s2_y = p3_y - p2_y;

        double s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (Double.compare(s, 0) >= 0 && Double.compare(s, 1) <= 0
                && Double.compare(t, 0) >= 0 && Double.compare(t, 1) <= 0) {
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

    public static Position getCenter(WalkPosition w) {
        Position delta = new Position(MiniTile.SIZE_IN_PIXELS / 2, MiniTile.SIZE_IN_PIXELS / 2);
        Position center = w.toPosition().add(delta);
        return center;
    }

    public static Altitude getMinAltitudeTop(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(1, 0)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(2, 0)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeBottom(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(1, 3)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(2, 3)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeLeft(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(0, 1)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(0, 2)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeRight(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(3, 1)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(3, 2)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

}
