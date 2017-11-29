package bwem.util;

import bwem.check_t;
import bwem.map.Map;
import bwem.map.MapImpl;
import bwem.typedef.Altitude;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openbw.bwapi4j.MapDrawer;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.Color;

import java.util.ArrayList;
import java.util.List;

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

    public static Position center(final TilePosition tilePosition) {
        final Position ret = tilePosition.toPosition().add(BwemExt.TILE_POSITION_CENTER_IN_PIXELS);
        return ret;
    }

    public static Position center(final WalkPosition walkPosition) {
        final Position ret = walkPosition.toPosition().add(BwemExt.WALK_POSITION_CENTER_IN_PIXELS);
        return ret;
    }

    public static Position centerOfBuilding(final TilePosition tilePosition, final TilePosition buildingSize) {
        final Position pixelSize = buildingSize.toPosition();
        final Position pixelOffset = pixelSize.divide(new Position(2, 2));
        return tilePosition.toPosition().add(pixelOffset);
    }

    // Enlarges the bounding box [TopLeft, BottomRight] so that it includes A.
    public static ImmutablePair<TilePosition, TilePosition> makeBoundingBoxIncludePoint(final TilePosition topLeft, final TilePosition bottomRight, final TilePosition point) {
        int topLeft_x = topLeft.getX();
        int topLeft_y = topLeft.getY();

        int bottomRight_x = bottomRight.getX();
        int bottomRight_y = bottomRight.getY();

        if (point.getX() < topLeft_x) topLeft_x = point.getX();
        if (point.getX() > bottomRight_x) bottomRight_x = point.getX();

        if (point.getY() < topLeft_y) topLeft_y = point.getY();
        if (point.getY() > bottomRight_y) bottomRight_y = point.getY();

        return new ImmutablePair<>(new TilePosition(topLeft_x, topLeft_y), new TilePosition(bottomRight_x, bottomRight_y));
    }

    // Makes the smallest change to A so that it is included in the bounding box [TopLeft, BottomRight].
    public static TilePosition makePointFitToBoundingBox(final TilePosition point, final TilePosition topLeft, final TilePosition bottomRight) {
        int point_x = point.getX();
        int point_y = point.getY();

        if (point_x < topLeft.getX()) point_x = topLeft.getX();
        else if (point_x > bottomRight.getX()) point_x = bottomRight.getX();

        if (point_y < topLeft.getY()) point_y = topLeft.getY();
        else if (point_y > bottomRight.getY()) point_y = bottomRight.getY();

        return new TilePosition(point_x, point_y);
    }

    //bwapiExt.h:71:inBoundingBox
    public static boolean isPointInBoundingBox(final TilePosition point, final TilePosition topLeft, final TilePosition bottomRight) {
        return (point.getX() >= topLeft.getX()) && (point.getX() <= bottomRight.getX()) &&
                (point.getY() >= topLeft.getY()) && (point.getY() <= bottomRight.getY());
    }

    public static int queenWiseDist(final TilePosition A, final TilePosition B) {
        final TilePosition ret = A.subtract(B);
        return Utils.queenWiseNorm(ret.getX(), ret.getY());
    }

    public static int queenWiseDist(final WalkPosition A, final WalkPosition B) {
        final WalkPosition ret = A.subtract(B);
        return Utils.queenWiseNorm(ret.getX(), ret.getY());
    }

    public static int queenWiseDist(final Position A, final Position B) {
        final Position ret = A.subtract(B);
        return Utils.queenWiseNorm(ret.getX(), ret.getY());
    }

    public static int squaredDist(final TilePosition A, final TilePosition B) {
        final TilePosition ret = A.subtract(B);
        return Utils.squaredNorm(ret.getX(), ret.getY());
    }

    public static int squaredDist(final WalkPosition A, final WalkPosition B) {
        final WalkPosition ret = A.subtract(B);
        return Utils.squaredNorm(ret.getX(), ret.getY());
    }

    public static int squaredDist(final Position A, final Position B) {
        final Position ret = A.subtract(B);
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

    public static int roundedDist(final TilePosition A, final TilePosition B) {
        return ((int) (Double.valueOf("0.5") + dist(A, B)));
    }

    public static int roundedDist(final WalkPosition A, final WalkPosition B) {
        return ((int) (Double.valueOf("0.5") + dist(A, B)));
    }

    public static int roundedDist(final Position A, final Position B) {
        return ((int) (Double.valueOf("0.5") + dist(A, B)));
    }

    public static int distToRectangle(final Position a, final Position topLeft, final Position size) {
        final Position bottomRight = topLeft.add(size).subtract(new Position(1, 1));

        if (a.getX() >= topLeft.getX())
            if (a.getX() <= bottomRight.getX())
                if (a.getY() > bottomRight.getY())  return a.getY() - bottomRight.getY();                                    // S
                else if (a.getY() < topLeft.getY()) return topLeft.getY() - a.getY();                                        // N
                else return 0;                                                                                               // inside
            else
                if (a.getY() > bottomRight.getY())  return roundedDist(a, bottomRight);                                      // SE
                else if (a.getY() < topLeft.getY()) return roundedDist(a, new Position(bottomRight.getX(), topLeft.getY())); // NE
                else return a.getX() - bottomRight.getX();                                                                   // E
        else
            if (a.getY() > bottomRight.getY())      return roundedDist(a, new Position(topLeft.getX(), bottomRight.getY())); // SW
            else if (a.getY() < topLeft.getY())     return roundedDist(a, topLeft);                                          // NW
            else                                    return topLeft.getX() - a.getX();                                        // W
    }

    private static List<ImmutablePair<Integer, Integer>> innerBorderDeltas(final int size_x, final int size_y, final boolean noCorner) {
        final List<ImmutablePair<Integer, Integer>> border = new ArrayList<>();

        for (int dy = 0; dy < size_y; ++dy)
        for (int dx = 0; dx < size_x; ++dx) {
            if ((dy == 0) || (dy == size_y - 1) ||
                (dx == 0) || (dx == size_x - 1)) {
                if (!noCorner ||
                    !(((dx == 0) && (dy == 0)) || ((dx == size_x - 1) && (dy == size_y - 1)) ||
                      ((dx == 0) && (dy == size_y - 1)) || ((dx == size_x - 1) && (dy == 0)))) {
                    border.add(new ImmutablePair<>(dx, dy));
                }
            }
        }

        return border;
    }

    public static List<TilePosition> innerBorder(final TilePosition topLeft, final TilePosition size, final boolean noCorner) {
        final List<TilePosition> border = new ArrayList<>();
        final List<ImmutablePair<Integer, Integer>> deltas = innerBorderDeltas(size.getX(), size.getY(), noCorner);
        for (final ImmutablePair<Integer, Integer> delta : deltas) {
            border.add(topLeft.add(new TilePosition(delta.getLeft(), delta.getRight())));
        }
        return border;
    }

    public static List<TilePosition> innerBorder(final TilePosition topLeft, final TilePosition size) {
        return innerBorder(topLeft, size, false);
    }

    public static List<WalkPosition> innerBorder(final WalkPosition topLeft, final WalkPosition size, boolean noCorner) {
        final List<WalkPosition> border = new ArrayList<>();
        final List<ImmutablePair<Integer, Integer>> deltas = innerBorderDeltas(size.getX(), size.getY(), noCorner);
        for (final ImmutablePair<Integer, Integer> delta : deltas) {
            border.add(topLeft.add(new WalkPosition(delta.getLeft(), delta.getRight())));
        }
        return border;
    }

    public static List<WalkPosition> innerBorder(final WalkPosition topLeft, final WalkPosition size) {
        return innerBorder(topLeft, size, false);
    }

    public static List<TilePosition> outerBorder(final TilePosition topLeft, final TilePosition size, final boolean noCorner) {
        return innerBorder(topLeft.subtract(new TilePosition(1, 1)), size.add(new TilePosition(2, 2)), noCorner);
    }

    public static List<TilePosition> outerBorder(final TilePosition topLeft, final TilePosition size) {
        return outerBorder(topLeft, size, false);
    }

    public static List<WalkPosition> outerBorder(final WalkPosition topLeft, final WalkPosition size, final boolean noCorner) {
        return innerBorder(topLeft.subtract(new WalkPosition(1, 1)), size.add(new WalkPosition(2, 2)), noCorner);
    }

    public static List<WalkPosition> outerBorder(final WalkPosition topLeft, final WalkPosition size) {
        return outerBorder(topLeft, size, false);
    }

    public static List<WalkPosition> outerMiniTileBorder(final TilePosition topLeft, final TilePosition size, final boolean noCorner) {
        return outerBorder(topLeft.toWalkPosition(), size.toWalkPosition(), noCorner);
    }

    public static List<WalkPosition> outerMiniTileBorder(final TilePosition topLeft, final TilePosition size) {
        return outerMiniTileBorder(topLeft, size, false);
    }

    public static List<WalkPosition> innerMiniTileBorder(final TilePosition topLeft, final TilePosition size, final boolean noCorner) {
        return innerBorder(topLeft.toWalkPosition(), size.toWalkPosition(), noCorner);
    }

    public static List<WalkPosition> innerMiniTileBorder(final TilePosition topLeft, TilePosition size) {
        return innerMiniTileBorder(topLeft, size, false);
    }

    public static boolean adjoins8SomeLakeOrNeutral(final WalkPosition p, final MapImpl pMap) {
        final WalkPosition[] deltas = {new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(+1, -1),
                                       new WalkPosition(-1,  0),                          new WalkPosition(+1,  0),
                                       new WalkPosition(-1, +1), new WalkPosition(0, +1), new WalkPosition(+1, +1)};
        for (final WalkPosition delta : deltas) {
            final WalkPosition next = p.add(delta);
            if (pMap.getData().getMapData().isValid(next)) {
                if (pMap.getData().getTile(next.toTilePosition(), check_t.no_check).GetNeutral() != null) {
                    return true;
                }
                if (pMap.getData().getMiniTile(next, check_t.no_check).Lake()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void drawDiagonalCrossMap(final MapDrawer mapDrawer, final Position topLeft, final Position bottomRight, final Color col) {
        mapDrawer.drawLineMap(topLeft, bottomRight, col);
        mapDrawer.drawLineMap(new Position(bottomRight.getX(), topLeft.getY()), new Position(topLeft.getX(), bottomRight.getY()), col);
    }

    private static boolean overlap(
            final int topLeft1_x, final int topLeft1_y, final int size1_x, final int size1_y,
            final int topLeft2_x, final int topLeft2_y, final int size2_x, final int size2_y
    ) {
        if (topLeft2_x >= topLeft1_x + size1_x) return false;
        if (topLeft2_y >= topLeft1_y + size1_y) return false;
        if (topLeft1_x >= topLeft2_x + size2_x) return false;
        if (topLeft1_y >= topLeft2_y + size2_y) return false;
        return true;
    }

    public static boolean overlap(final TilePosition topLeft1, final TilePosition size1, final TilePosition topLeft2, final TilePosition size2) {
        return overlap(
                topLeft1.getX(), topLeft1.getY(), size1.getX(), size1.getY(),
                topLeft2.getX(), topLeft2.getY(), size2.getX(), size2.getY()
        );
    }

    public static boolean overlap(final WalkPosition topLeft1, final WalkPosition size1, final WalkPosition topLeft2, final WalkPosition size2) {
        return overlap(
                topLeft1.getX(), topLeft1.getY(), size1.getX(), size1.getY(),
                topLeft2.getX(), topLeft2.getY(), size2.getX(), size2.getY()
        );
    }

    public static boolean overlap(final Position topLeft1, final Position size1, final Position topLeft2, final Position size2) {
        return overlap(
                topLeft1.getX(), topLeft1.getY(), size1.getX(), size1.getY(),
                topLeft2.getX(), topLeft2.getY(), size2.getX(), size2.getY()
        );
    }

    private static boolean disjoint(
            final int topLeft1_x, final int topLeft1_y, final int size1_x, final int size1_y,
            final int topLeft2_x, final int topLeft2_y, final int size2_x, final int size2_y
    ) {
        if (topLeft2_x > topLeft1_x + size1_x) return true;
        if (topLeft2_y > topLeft1_y + size1_y) return true;
        if (topLeft1_x > topLeft2_x + size2_x) return true;
        if (topLeft1_y > topLeft2_y + size2_y) return true;
        return false;
    }

    public static boolean disjoint(final TilePosition topLeft1, final TilePosition size1, final TilePosition topLeft2, final TilePosition size2) {
        return disjoint(
                topLeft1.getX(), topLeft1.getY(), size1.getX(), size1.getY(),
                topLeft2.getX(), topLeft2.getY(), size2.getX(), size2.getY()
        );
    }

    public static boolean disjoint(final WalkPosition topLeft1, final WalkPosition size1, final WalkPosition topLeft2, final WalkPosition size2) {
        return disjoint(
                topLeft1.getX(), topLeft1.getY(), size1.getX(), size1.getY(),
                topLeft2.getX(), topLeft2.getY(), size2.getX(), size2.getY()
        );
    }

    public static boolean disjoint(final Position topLeft1, final Position size1, final Position topLeft2, final Position size2) {
        return disjoint(
                topLeft1.getX(), topLeft1.getY(), size1.getX(), size1.getY(),
                topLeft2.getX(), topLeft2.getY(), size2.getX(), size2.getY()
        );
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

}
