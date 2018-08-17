package org.openbw.bwapi4j.util;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * Contains utility functions for basic calculations.
 */
public class MathUtil {

    private MathUtil() {
    }

    /**
     * Returns the distance between two boxes.
     * The distance is calculated from the edge of the boxes.
     * If the boxes overlap in a dimension, the delta in that dimension is zero.
     *
     * @param left1   the left of the first box.
     * @param top1    the top of the first box.
     * @param right1  the right of the first box.
     * @param bottom1 the bottom of the first box.
     * @param left2   the left of the second box.
     * @param top2    the top of the second box.
     * @param right2  the right of the second box.
     * @param bottom2 the bottom of the second box.
     * @return An estimated distance between two boxes.
     */
    public static double distanceBetween(int left1, int top1, int right1, int bottom1, int left2, int top2, int right2, int bottom2) {
        return distanceWithDelta(
                calculateDelta(left1, right1, left2, right2),
                calculateDelta(top1, bottom1, top2, bottom2));
    }

    /**
     * Returns the distance as BW would between two points.
     * This is ported from BWAPI's getApproxDistance method.
     *
     * @param x1 the x value of the first point.
     * @param y1 the y value of the first point.
     * @param x2 the x value of the second point.
     * @param y2 the y value of the second point.
     * @return An estimated distance between two points.
     */
    public static int estimateDistanceBetween(int x1, int y1, int x2, int y2) {
        return estimateDistanceWithDelta(x2 - x1, y2 - y1);
    }

    /**
     * Returns the distance as BW would between two boxes.
     * This is ported from BWAPI's getApproxDistance method.
     * <p>
     * The distance is calculated from the edge of the boxes.
     * If the boxes overlap in a dimension, the delta in that dimension is zero.
     *
     * @param left1   the left of the first box.
     * @param top1    the top of the first box.
     * @param right1  the right of the first box.
     * @param bottom1 the bottom of the first box.
     * @param left2   the left of the second box.
     * @param top2    the top of the second box.
     * @param right2  the right of the second box.
     * @param bottom2 the bottom of the second box.
     * @return An estimated distance between two boxes.
     */
    public static int estimateDistanceBetween(int left1, int top1, int right1, int bottom1, int left2, int top2, int right2, int bottom2) {
        return estimateDistanceWithDelta(
                calculateDelta(top1, bottom1, top2, bottom2),
                calculateDelta(left1, right1, left2, right2));
    }

    private static int calculateDelta(int topLeft1, int bottomRight1, int topLeft2, int bottomRight2) {
        // 1 is the source, 2 is the destination
        // Assuming the destination is top/left of the source
        int xDist = topLeft1 - bottomRight2;
        // The destination is to the bottom/right of the source.
        if (xDist < 0) {
            xDist = topLeft2 - bottomRight1;
            // there is overlap, the delta is zero.
            if (xDist < 0) {
                return 0;
            }
        }
        return xDist;
    }

    private static int estimateDistanceWithDelta(int dx, int dy) {
        int min = abs(dx);
        int max = abs(dy);

        if (max < min) {
            int t = max;
            max = min;
            min = t;
        }

        if (min < (max >> 2)) return max;

        int minCalc = (3 * min) >> 3;
        return (minCalc >> 5) + minCalc + max - (max >> 4) - (max >> 6);
    }

    private static double distanceWithDelta(int dx, int dy) {
        return sqrt(dx * dx + dy * dy);
    }
}
