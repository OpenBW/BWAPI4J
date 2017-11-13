package bwem.util;

import bwem.typedef.Pred;
import org.apache.commons.lang3.mutable.MutableDouble;

import java.util.List;

public final class Utils {

    private Utils() throws InstantiationException {
        throw new InstantiationException();
    }

    public static int queenWiseNorm(int dx, int dy) {
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    public static int squaredNorm(final int dx, final int dy) {
        return ((dx * dx) + (dy * dy));
    }

    public static double norm(final int dx, final int dy) {
        return Math.sqrt(squaredNorm(dx, dy));
    }

    public static int scalar_product(int ax, int ay, int bx, int by) {
        return ((ax * bx) + (ay * by));
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

    // Returns whether the line segments [a, b] and [c, d] intersect.
    public static boolean intersect(int ax, int ay, int bx, int by, int cx, int cy, int dx, int dy) {
        return get_line_intersection(ax, ay, bx, by, cx, cy, dx, dy, null, null);
    }

    public static <T> void really_remove_if(final List<T> Container, final Pred pred) {
        for (int i = 0; i < Container.size(); ++i) {
            if (pred.isTrue(Container.get(i))) {
                Container.remove(i--);
            }
        }
    }

}
