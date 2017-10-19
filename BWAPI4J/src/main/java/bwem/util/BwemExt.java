/*
Status: Incomplete
*/

package bwem.util;

import bwem.CheckMode;
import bwem.map.Map;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public final class BwemExt {

    private static final int TILE_POSITION_CENTER_OFFSET_IN_PIXELS = TilePosition.SIZE_IN_PIXELS / 2;
    public static final Position TILE_POSITION_CENTER_IN_PIXELS = new Position(BwemExt.TILE_POSITION_CENTER_OFFSET_IN_PIXELS, BwemExt.TILE_POSITION_CENTER_OFFSET_IN_PIXELS);

    private static final int WALK_POSITION_CENTER_OFFSET_IN_PIXELS = WalkPosition.SIZE_IN_PIXELS / 2;
    public static final Position WALK_POSITION_CENTER_IN_PIXELS = new Position(BwemExt.WALK_POSITION_CENTER_OFFSET_IN_PIXELS, BwemExt.WALK_POSITION_CENTER_OFFSET_IN_PIXELS);

    private BwemExt() throws InstantiationException {
        throw new InstantiationException();
    }

    public boolean seaSide(WalkPosition p, Map pMap) {
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

}
