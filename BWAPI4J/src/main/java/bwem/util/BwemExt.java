/*
Status: Incomplete
*/

package bwem.util;

import bwem.Altitude;
import bwem.CheckMode;
import bwem.map.Map;
import bwem.map.MapImpl;
import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.util.Pair;

public final class BwemExt {

    private static final int TILE_POSITION_CENTER_OFFSET_IN_PIXELS = TilePosition.SIZE_IN_PIXELS / 2;
    public static final Position TILE_POSITION_CENTER_IN_PIXELS = new Position(BwemExt.TILE_POSITION_CENTER_OFFSET_IN_PIXELS, BwemExt.TILE_POSITION_CENTER_OFFSET_IN_PIXELS);

    private static final int WALK_POSITION_CENTER_OFFSET_IN_PIXELS = WalkPosition.SIZE_IN_PIXELS / 2;
    public static final Position WALK_POSITION_CENTER_IN_PIXELS = new Position(BwemExt.WALK_POSITION_CENTER_OFFSET_IN_PIXELS, BwemExt.WALK_POSITION_CENTER_OFFSET_IN_PIXELS);

    // These constants control how to decide between Seas and Lakes.
    public static final int lake_max_miniTiles = 300;
    public static final int lake_max_width_in_miniTiles = 8 * 4;

    public static final int max_tiles_between_CommandCenter_and_ressources = 10;
    public static final int min_tiles_between_Bases = 10;

    // At least area_min_miniTiles connected MiniTiles are necessary for an Area to be created.
    public static final int area_min_miniTiles = 64;

    public static final int max_tiles_between_StartingLocation_and_its_AssignedBase = 3;

    private BwemExt() throws InstantiationException {
        throw new InstantiationException();
    }

    public static boolean seaSide(WalkPosition p, Map pMap) {
        if (!pMap.GetMiniTile(p).Sea()) {
            return false;
        }

        WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
        for (WalkPosition delta : deltas) {
            if (pMap.Valid(p.add(delta))) {
                if (!pMap.GetMiniTile(p.add(delta), CheckMode.NoCheck).Sea()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Position center(Position A) {
        return A;
    }

    public static Position center(TilePosition A) {
        Position ret = (A.toPosition()).add(BwemExt.TILE_POSITION_CENTER_IN_PIXELS);
        return ret;
    }

    public static Position center(WalkPosition A) {
        Position ret = (A.toPosition()).add(BwemExt.WALK_POSITION_CENTER_IN_PIXELS);
        return ret;
    }

    public static int queenWiseDist(Position A, Position B) {
        Position ret = A.subtract(B);
        return Utils.queenWiseNorm(ret.getX(), ret.getY());
    }

    public static int squaredDist(Position A, Position B) {
        Position ret = A.subtract(B);
        return Utils.squaredNorm(ret.getX(), ret.getY());
    }

    public static double dist(Position A, Position B) {
        Position ret = A.subtract(B);
        return Utils.norm(ret.getX(), ret.getY());
    }

    public static double dist(TilePosition A, TilePosition B) {
        TilePosition ret = A.subtract(B);
        return Utils.norm(ret.getX(), ret.getY());
    }

    public static int roundedDist(Position A, Position B) {
        return ((int) (Double.valueOf("0.5") + dist(A, B)));
    }

    public static int distToRectangle(Position a, TilePosition TopLeft, TilePosition Size) {
        Position topLeft = TopLeft.toPosition();
        Position bottomRight = (TopLeft.add(Size).subtract(new TilePosition(1, 1))).toPosition();

        if (a.getX() >= topLeft.getX()) {
            if (a.getX() <= bottomRight.getX()) {
                if (a.getY() > bottomRight.getY()) {
                    /* S */
                    return (a.getY() - bottomRight.getY());
                } else if (a.getY() < topLeft.getY()) {
                    /* N */
                    return (topLeft.getY() - a.getY());
                } else {
                    /* Inside */
                    return 0;
                }
            } else {
                if (a.getY() > bottomRight.getY()) {
                    /* SE */
                    return roundedDist(a, bottomRight);
                } else if (a.getY() < topLeft.getY())	{
                    /* NE */
                    return roundedDist(a, new Position(bottomRight.getX(), topLeft.getY()));
                } else {
                    /* E */
                    return (a.getX() - bottomRight.getX());
                }
            }
        } else {
            if (a.getY() > bottomRight.getY()) {
                /* SW */
                return roundedDist(a, new Position(topLeft.getX(), bottomRight.getY()));
            } else if (a.getY() < topLeft.getY()) {
                /* NW */
                return roundedDist(a, topLeft);
            } else {
                /* W */
                return (topLeft.getX() - a.getX());
            }
        }
    }

    // Enlarges the bounding box [TopLeft, BottomRight] so that it includes A.
    public static Pair<TilePosition, TilePosition> makeBoundingBoxIncludePoint(TilePosition TopLeft, TilePosition BottomRight, TilePosition A) {
        TilePosition first = TopLeft;
        TilePosition second = BottomRight;

        if (A.getX() < TopLeft.getX()) first = new TilePosition(A.getX(), first.getY());
        if (A.getX() > BottomRight.getX()) second = new TilePosition(A.getX(), second.getY());

        if (A.getY() < TopLeft.getY()) first = new TilePosition(first.getX(), A.getY());
        if (A.getY() > BottomRight.getY()) second = new TilePosition(second.getX(), A.getY());

        return new Pair<>(first, second);
    }

    // Makes the smallest change to A so that it is included in the bounding box [TopLeft, BottomRight].
    public static TilePosition makePointFitToBoundingBox(TilePosition A, TilePosition TopLeft, TilePosition BottomRight) {
        TilePosition ret = new TilePosition(A.getX(), A.getY());

        if      (A.getX() < TopLeft.getX()) A = new TilePosition(TopLeft.getX(), A.getY());
        else if (A.getX() > BottomRight.getX()) A = new TilePosition(BottomRight.getX(), A.getY());

        if      (A.getY() < TopLeft.getY()) A = new TilePosition(A.getX(), TopLeft.getX());
        else if (A.getY() > BottomRight.getY())	A = new TilePosition(A.getX(), BottomRight.getY());

        return ret;
    }

    public static List<TilePosition> innerBorder(TilePosition TopLeft, TilePosition Size, boolean noCorner) {
        List<TilePosition> Border = new ArrayList<>();
        for (int dy = 0; dy < Size.getY(); ++dy)
        for (int dx = 0; dx < Size.getY(); ++dx) {
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
        for (int dx = 0; dx < Size.getY(); ++dx) {
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

    public static boolean adjoins8SomeLakeOrNeutral(WalkPosition p, MapImpl pMap) {
        WalkPosition[] deltas = {new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(+1, -1),
                                 new WalkPosition(-1,  0),                          new WalkPosition(+1,  0),
                                 new WalkPosition(-1, +1), new WalkPosition(0, +1), new WalkPosition(+1, +1)};
        for (WalkPosition delta : deltas) {
            WalkPosition next = p.add(delta);
            if (pMap.Valid(next)) {
                if (pMap.GetTile(next.toPosition().toTilePosition(), CheckMode.NoCheck).GetNeutral() != null) {
                    return true;
                }
                if (pMap.GetMiniTile(next, CheckMode.NoCheck).Lake()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static Altitude getMinAltitudeTop(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(1, 0)), CheckMode.NoCheck).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(2, 0)), CheckMode.NoCheck).Altitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeBottom(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(1, 3)), CheckMode.NoCheck).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(2, 3)), CheckMode.NoCheck).Altitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeLeft(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(0, 1)), CheckMode.NoCheck).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(0, 2)), CheckMode.NoCheck).Altitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeRight(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.GetMiniTile(w.add(new WalkPosition(3, 1)), CheckMode.NoCheck).Altitude().intValue(),
                map.GetMiniTile(w.add(new WalkPosition(3, 2)), CheckMode.NoCheck).Altitude().intValue()
        ));
    }

}
