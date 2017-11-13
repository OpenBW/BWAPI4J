package bwem.util;

import bwem.typedef.Altitude;
import bwem.check_t;
import bwem.map.Map;
import bwem.map.MapImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.Color;

public final class BwemExt {

    private static final int TILE_POSITION_CENTER_OFFSET_IN_PIXELS = TilePosition.SIZE_IN_PIXELS / 2;
    public static final Position TILE_POSITION_CENTER_IN_PIXELS = new Position(BwemExt.TILE_POSITION_CENTER_OFFSET_IN_PIXELS, BwemExt.TILE_POSITION_CENTER_OFFSET_IN_PIXELS);

    private static final int WALK_POSITION_CENTER_OFFSET_IN_PIXELS = WalkPosition.SIZE_IN_PIXELS / 2;
    public static final Position WALK_POSITION_CENTER_IN_PIXELS = new Position(BwemExt.WALK_POSITION_CENTER_OFFSET_IN_PIXELS, BwemExt.WALK_POSITION_CENTER_OFFSET_IN_PIXELS);

    // These constants control how to decide between Seas and Lakes.
    public static final int lake_max_miniTiles = 300;
    public static final int lake_max_width_in_miniTiles = 8 * 4;

    // At least area_min_miniTiles connected MiniTiles are necessary for an Area to be created.
    public static final int area_min_miniTiles = 64;

    public static final int max_tiles_between_CommandCenter_and_resources = 10;
    public static final int min_tiles_between_Bases = 10;

    public static final int max_tiles_between_StartingLocation_and_its_AssignedBase = 3;

    private BwemExt() {}

    public static boolean seaSide(WalkPosition p, Map pMap) {
        if (!pMap.GetMiniTile(p).Sea()) {
            return false;
        }

        WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
        for (WalkPosition delta : deltas) {
            if (pMap.Valid(p.add(delta))) {
                if (!pMap.GetMiniTile(p.add(delta), check_t.no_check).Sea()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Position center(TilePosition A) {
        Position ret = (A.toPosition()).add(BwemExt.TILE_POSITION_CENTER_IN_PIXELS);
        return ret;
    }

    public static Position center(WalkPosition A) {
        Position ret = (A.toPosition()).add(BwemExt.WALK_POSITION_CENTER_IN_PIXELS);
        return ret;
    }

    public static Position center(Position A) {
        return A;
    }

    public static int queenWiseDist(TilePosition A, TilePosition B) {
        TilePosition ret = A.subtract(B);
        return Utils.queenWiseNorm(ret.getX(), ret.getY());
    }

    public static int queenWiseDist(WalkPosition A, WalkPosition B) {
        WalkPosition ret = A.subtract(B);
        return Utils.queenWiseNorm(ret.getX(), ret.getY());
    }

    public static int queenWiseDist(Position A, Position B) {
        Position ret = A.subtract(B);
        return Utils.queenWiseNorm(ret.getX(), ret.getY());
    }

    public static int squaredDist(TilePosition A, TilePosition B) {
        TilePosition ret = A.subtract(B);
        return Utils.squaredNorm(ret.getX(), ret.getY());
    }

    public static int squaredDist(WalkPosition A, WalkPosition B) {
        WalkPosition ret = A.subtract(B);
        return Utils.squaredNorm(ret.getX(), ret.getY());
    }

    public static int squaredDist(Position A, Position B) {
        Position ret = A.subtract(B);
        return Utils.squaredNorm(ret.getX(), ret.getY());
    }

    public static double dist(final TilePosition A, final TilePosition B) {
        final TilePosition ret = A.subtract(B);
        return Utils.norm(ret.getX(), ret.getY());
    }

    public static double dist(final WalkPosition A, final WalkPosition B) {
        final WalkPosition ret = A.subtract(B);
        return Utils.norm(ret.getX(), ret.getY());
    }

    public static double dist(final Position A, final Position B) {
        final Position ret = A.subtract(B);
        return Utils.norm(ret.getX(), ret.getY());
    }

    public static int roundedDist(TilePosition A, TilePosition B) {
        return ((int) (Double.valueOf("0.5") + dist(A, B)));
    }

    public static int roundedDist(WalkPosition A, WalkPosition B) {
        return ((int) (Double.valueOf("0.5") + dist(A, B)));
    }

    public static int roundedDist(Position A, Position B) {
        return ((int) (Double.valueOf("0.5") + dist(A, B)));
    }

    public static int distToRectangle(Position a, TilePosition TopLeft, TilePosition Size) {
    	Position topLeft = TopLeft.toPosition();
        Position bottomRight = ((TopLeft.add(Size)).toPosition()).subtract(new Position(1, 1));

    	if (a.getX() >= topLeft.getX()) {
    		if (a.getX() <= bottomRight.getX()) {
    			if (a.getY() > bottomRight.getY()) {
                    return a.getY() - bottomRight.getY(); // S
                } else if (a.getY() < topLeft.getY()) {
                    return topLeft.getY() - a.getY(); // N
                } else {
                    return 0; // inside
                }
            } else {
    			if (a.getY() > bottomRight.getY()) {
                    return roundedDist(a, bottomRight); // SE
                } else if (a.getY() < topLeft.getY()) {
                    return roundedDist(a, new Position(bottomRight.getX(), topLeft.getY())); // NE
                } else {
                    return a.getX() - bottomRight.getX(); // E
                }
            }
        } else {
    		if (a.getY() > bottomRight.getY()){
                return roundedDist(a, new Position(topLeft.getX(), bottomRight.getY())); // SW
            } else if (a.getY() < topLeft.getY()) {
                return roundedDist(a, topLeft); // NW
            } else {
                return topLeft.getX() - a.getX(); // W
            }
        }
    }

    // Enlarges the bounding box [TopLeft, BottomRight] so that it includes A.
    public static ImmutablePair<TilePosition, TilePosition> makeBoundingBoxIncludePoint(TilePosition TopLeft, TilePosition BottomRight, TilePosition point) {
        int tl_x = TopLeft.getX();
        int tl_y = TopLeft.getY();

        int br_x = BottomRight.getX();
        int br_y = BottomRight.getY();

        if (point.getX() < tl_x) tl_x = point.getX();
        if (point.getX() > br_x) br_x = point.getX();

        if (point.getY() < tl_y) tl_y = point.getY();
        if (point.getY() > br_y) br_y = point.getY();

        return new ImmutablePair<>(new TilePosition(tl_x, tl_y), new TilePosition(br_x, br_y));
    }

    // Makes the smallest change to A so that it is included in the bounding box [TopLeft, BottomRight].
    public static TilePosition makePointFitToBoundingBox(TilePosition point, TilePosition TopLeft, TilePosition BottomRight) {
        int ret_x = point.getX();
        int ret_y = point.getY();

        if (ret_x < TopLeft.getX()) ret_x = TopLeft.getX();
        else if (ret_x > BottomRight.getX()) ret_x = BottomRight.getX();

        if (ret_y < TopLeft.getY()) ret_y = TopLeft.getY();
        else if (ret_y > BottomRight.getY()) ret_y = BottomRight.getY();

        return new TilePosition(ret_x, ret_y);
    }

    public static boolean inBoundingBox(TilePosition A, TilePosition topLeft, TilePosition bottomRight) {
        return (A.getX() >= topLeft.getX()) && (A.getX() <= bottomRight.getX()) &&
                (A.getY() >= topLeft.getY()) && (A.getY() <= bottomRight.getY());
    }

    public static List<TilePosition> innerBorder(TilePosition TopLeft, TilePosition Size, boolean noCorner) {
        List<TilePosition> Border = new ArrayList<>();
        for (int dy = 0; dy < Size.getY(); ++dy)
        for (int dx = 0; dx < Size.getX(); ++dx) {
            if ((dy == 0) || (dy == Size.getY() - 1) ||
                (dx == 0) || (dx == Size.getX() - 1)) {
                if (!noCorner ||
                    !(((dx == 0) && (dy == 0)) || ((dx == Size.getX() - 1) && (dy == Size.getY() - 1)) ||
                      ((dx == 0) && (dy == Size.getY() - 1)) || ((dx == Size.getX() - 1) && (dy == 0)))) {
                    Border.add(TopLeft.add(new TilePosition(dx, dy)));
                }
            }
        }

        return Border;
    }

    public static List<TilePosition> innerBorder(TilePosition TopLeft, TilePosition Size) {
        return innerBorder(TopLeft, Size, false);
    }

    public static List<WalkPosition> innerBorder(WalkPosition TopLeft, WalkPosition Size, boolean noCorner) {
        List<WalkPosition> Border = new ArrayList<>();
        for (int dy = 0; dy < Size.getY(); ++dy)
        for (int dx = 0; dx < Size.getX(); ++dx) {
            if ((dy == 0) || (dy == Size.getY() - 1) ||
                (dx == 0) || (dx == Size.getX() - 1)) {
                if (!noCorner ||
                    !(((dx == 0) && (dy == 0)) || ((dx == Size.getX() - 1) && (dy == Size.getY() - 1)) ||
                      ((dx == 0) && (dy == Size.getY() - 1)) || ((dx == Size.getX() - 1) && (dy == 0)))) {
                    Border.add(TopLeft.add(new WalkPosition(dx, dy)));
                }
            }
        }

        return Border;
    }

    public static List<WalkPosition> innerBorder(WalkPosition TopLeft, WalkPosition Size) {
        return innerBorder(TopLeft, Size, false);
    }

    public static List<TilePosition> outerBorder(TilePosition TopLeft, TilePosition Size, boolean noCorner) {
        return innerBorder(TopLeft.subtract(new TilePosition(1, 1)), Size.add(new TilePosition(2, 2)), noCorner);
    }

    public static List<TilePosition> outerBorder(TilePosition TopLeft, TilePosition Size) {
        return outerBorder(TopLeft, Size, false);
    }

    public static List<WalkPosition> outerBorder(WalkPosition TopLeft, WalkPosition Size, boolean noCorner) {
        return innerBorder(TopLeft.subtract(new WalkPosition(1, 1)), Size.add(new WalkPosition(2, 2)), noCorner);
    }

    public static List<WalkPosition> outerBorder(WalkPosition TopLeft, WalkPosition Size) {
        return outerBorder(TopLeft, Size, false);
    }

    public static List<WalkPosition> outerMiniTileBorder(TilePosition TopLeft, TilePosition Size, boolean noCorner) {
        return outerBorder(TopLeft.toPosition().toWalkPosition(), Size.toPosition().toWalkPosition(), noCorner);
    }

    public static List<WalkPosition> outerMiniTileBorder(TilePosition TopLeft, TilePosition Size) {
        return outerMiniTileBorder(TopLeft, Size, false);
    }

    public static List<WalkPosition> innerMiniTileBorder(TilePosition TopLeft, TilePosition Size, boolean noCorner) {
        return innerBorder(TopLeft.toPosition().toWalkPosition(), Size.toPosition().toWalkPosition(), noCorner);
    }

    public static List<WalkPosition> innerMiniTileBorder(TilePosition TopLeft, TilePosition Size) {
        return innerMiniTileBorder(TopLeft, Size, false);
    }

    public static boolean disjoint(TilePosition TopLeft1, TilePosition Size1, TilePosition TopLeft2, TilePosition Size2) {
        if (TopLeft2.getX() > TopLeft1.getX() + Size1.getX()) return true;
        if (TopLeft2.getY() > TopLeft1.getY() + Size1.getY()) return true;
        if (TopLeft1.getX() > TopLeft2.getX() + Size2.getX()) return true;
        if (TopLeft1.getY() > TopLeft2.getY() + Size2.getY()) return true;
        return false;
    }

    public static boolean adjoins8SomeLakeOrNeutral(final WalkPosition p, final MapImpl pMap) {
        final WalkPosition[] deltas = {new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(+1, -1),
                                       new WalkPosition(-1,  0),                          new WalkPosition(+1,  0),
                                       new WalkPosition(-1, +1), new WalkPosition(0, +1), new WalkPosition(+1, +1)};
        for (final WalkPosition delta : deltas) {
            final WalkPosition next = p.add(delta);
            if (pMap.Valid(next)) {
                if (pMap.GetTile(next.toPosition().toTilePosition(), check_t.no_check).GetNeutral() != null) {
                    return true;
                }
                if (pMap.GetMiniTile(next, check_t.no_check).Lake()) {
                    return true;
                }
            }
        }

        return false;
    }

//    template<typename T, int Scale = 1>
//    inline bool overlap(const BWAPI::Point<T, Scale> & TopLeft1, const BWAPI::Point<T, Scale> & Size1, const BWAPI::Point<T, Scale> & TopLeft2, const BWAPI::Point<T, Scale> & Size2)
//    {
//        if (TopLeft2.x >= TopLeft1.x + Size1.x) return false;
//        if (TopLeft2.y >= TopLeft1.y + Size1.y) return false;
//        if (TopLeft1.x >= TopLeft2.x + Size2.x) return false;
//        if (TopLeft1.y >= TopLeft2.y + Size2.y) return false;
//        return true;
//    }

    public static void drawDiagonalCrossMap(BW bw, Position topLeft, Position bottomRight, Color col) {
        bw.getMapDrawer().drawLineMap(topLeft, bottomRight, col);
        bw.getMapDrawer().drawLineMap(new Position(bottomRight.getX(), topLeft.getY()), new Position(topLeft.getX(), bottomRight.getY()), col);
    }

    public static <T> void fast_erase(List<T> Vector, int i) {
//        bwem_assert((0 <= i) && (i < (int)Vector.size()));
        if (!((0 <= i) && (i < Vector.size()))) {
            throw new IllegalArgumentException("" + i);
        }

        final boolean isBackElement = (i >= Vector.size() - 1);

        Vector.remove(i);

        if (Vector.size() > 1 && !isBackElement) {
            /* Move the back element to where the ith element was. */
            T BackElement = Vector.remove(Vector.size() - 1);
            Vector.add(i, BackElement);
        }
    }

    //----------------------------------------------------------------------
    //TODO: Add these functions to main BWAPI4J target type source files?
    //----------------------------------------------------------------------
    private static int getApproxDistance(int x0, int y0, int x1, int y1) {
        int min = Math.abs(x0 - x1);
        int max = Math.abs(y0 - y1);
        if (max < min) {
            int min_tmp = min;
            min = max;
            max = min_tmp;
        }

        if (min < (max >> 2)) {
            return max;
        }

        int minCalc = (3 * min) >> 3;
        return (minCalc >> 5) + minCalc + max - (max >> 4) - (max >> 6);
    }
    public static int getApproxDistance(TilePosition source, TilePosition target) {
        return getApproxDistance(source.getX(), source.getY(), target.getX(), target.getY());
    };
    public static int getApproxDistance(WalkPosition source, WalkPosition target) {
        return getApproxDistance(source.getX(), source.getY(), target.getX(), target.getY());
    };
    public static int getApproxDistance(Position source, Position target) {
        return getApproxDistance(source.getX(), source.getY(), target.getX(), target.getY());
    };
    //----------------------------------------------------------------------

    public static Altitude getMinAltitudeTop(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(1, 0)), check_t.no_check).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(2, 0)), check_t.no_check).Altitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeBottom(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(1, 3)), check_t.no_check).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(2, 3)), check_t.no_check).Altitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeLeft(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(0, 1)), check_t.no_check).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(0, 2)), check_t.no_check).Altitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeRight(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(3, 1)), check_t.no_check).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(3, 2)), check_t.no_check).Altitude().intValue()
        ));
    }

}
