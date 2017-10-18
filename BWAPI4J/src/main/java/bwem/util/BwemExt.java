/*
Status: Incomplete
*/

package bwem.util;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;

public final class BwemExt {

    private BwemExt() throws InstantiationException {
        throw new InstantiationException();
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
                return roundedDist(a, new Position(topLeft.getX(), bottomRight.getY()));
            } else if (a.getY() < topLeft.getY()) {
                return roundedDist(a, topLeft);
            } else					{
                return (topLeft.getX() - a.getX());
            }
        }
    }

}
