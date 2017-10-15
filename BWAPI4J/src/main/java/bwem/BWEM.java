package bwem;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.util.Pair;

public class BWEM {

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

    private BW bw = null;
    private Map map = null;

    private BWEM() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public BWEM(BW bw) {
        this.bw = bw;
    }

    public void initialize() {
        this.map = new Map(this.bw);
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
            if (map.isValid(w_delta) && !map.getMiniTile(w_delta).isSea()) {
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

    public static Position getCenter(WalkPosition w) {
        Position delta = new Position(MiniTile.SIZE_IN_PIXELS / 2, MiniTile.SIZE_IN_PIXELS / 2);
        Position center = w.toPosition().add(delta);
        return center;
    }

    public static List<WalkPosition> innerBorder(WalkPosition topLeft, WalkPosition size, boolean noCorner) {
        List<WalkPosition> ret = new ArrayList<>();

        for (int dy = 0 ; dy < size.getY() ; ++dy)
        for (int dx = 0 ; dx < size.getX() ; ++dx) {
            if ((dy == 0) || (dy == size.getY() - 1) ||
                (dx == 0) || (dx == size.getX() - 1)) {
                if (!noCorner ||
                    !(((dx == 0) && (dy == 0)) || ((dx == size.getX() - 1) && (dy == size.getY() - 1)) ||
                      ((dx == 0) && (dy == size.getY() - 1)) || ((dx == size.getX() - 1) && (dy == 0)))) {
                    ret.add(topLeft.add(new WalkPosition(dx, dy)));
                }
            }
        }

        return ret;
    }

    public static List<WalkPosition> innerBorder(TilePosition topLeft, TilePosition size) {
        return innerBorder(
                topLeft.toPosition().toWalkPosition(),
                size.toPosition().toWalkPosition(),
                false
        );
    }

    public static List<WalkPosition> outerBorder(TilePosition topLeft, TilePosition size, boolean noCorner) {
        return innerBorder(
                topLeft.subtract(new TilePosition(1, 1)).toPosition().toWalkPosition(),
                size.add(new TilePosition(2, 2)).toPosition().toWalkPosition(),
                noCorner
        );
    }

    public static List<WalkPosition> outerBorder(TilePosition topLeft, TilePosition size) {
        return outerBorder(topLeft, size, false);
    }

    public static List<WalkPosition> outerMiniTileBorder(TilePosition topLeft, TilePosition size, boolean noCorner) {
        return outerBorder(topLeft, size, noCorner);
    }

    public static List<WalkPosition> outerMiniTileBorder(TilePosition topLeft, TilePosition size) {
        return outerMiniTileBorder(topLeft, size, false);
    }

    public static List<WalkPosition> innerMiniTileBorder(TilePosition topLeft, TilePosition size, boolean noCorner) {
        return innerBorder(topLeft.toPosition().toWalkPosition(), size.toPosition().toWalkPosition(), noCorner);
    }

    public static List<WalkPosition> innerMiniTileBorder(TilePosition topLeft, TilePosition size) {
        return innerMiniTileBorder(topLeft, size, false);
    }

    public static boolean adjoins8SomeLakeOrNeutral(WalkPosition p, Map map) {
        WalkPosition[] deltas = {
            new WalkPosition(-1, -1), new WalkPosition( 0, -1), new WalkPosition( 1, -1),
            new WalkPosition(-1,  0),                           new WalkPosition( 1,  0),
            new WalkPosition(-1,  1), new WalkPosition( 0,  1), new WalkPosition( 1,  1)
        };
        for (WalkPosition delta : deltas) {
            WalkPosition next = p.add(delta);
            if (map.isValid(next)) {
                if (map.getTile(next.toPosition().toTilePosition(), CheckMode.NoCheck).getOccupyingNeutral() != null) {
                    return true;
                }
                if (map.getMiniTile(next, CheckMode.NoCheck).isLake()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Pair<Area.Id, Area.Id> findNeighboringAreas(WalkPosition w, Map map) {
        Pair<Area.Id, Area.Id> ret = new Pair<>(new Area.Id(0), new Area.Id(0));

        WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
        for (WalkPosition delta : deltas) {
            if (map.isValid(w.add(delta))) {
                Area.Id areaId = map.getMiniTile(w.add(delta), CheckMode.NoCheck).getAreaId();
                if (areaId.intValue() > 0) {
                    if (ret.first == null || ret.first.intValue() == 0) {
                        ret.first = new Area.Id(areaId);
                    } else if (!ret.first.equals(areaId)) {
                        ret.second = new Area.Id(areaId);
                    }
                }
            }
        }

        return ret;
    }

    public static double dist(TilePosition t0, TilePosition t1) {
        TilePosition tmp = t0.subtract(t1);
        return norm(tmp.getX(), tmp.getY());
    }

}
