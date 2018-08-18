// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.util;

import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import org.apache.commons.lang3.mutable.MutableDouble;

public final class Utils {
  private static final SplittableRandom randomGenerator = new SplittableRandom();

  private Utils() {}

  public static int queenWiseNorm(final int dx, final int dy) {
    return Math.max(Math.abs(dx), Math.abs(dy));
  }

  public static int squaredNorm(final int dx, final int dy) {
    return ((dx * dx) + (dy * dy));
  }

  public static double norm(final int dx, final int dy) {
    return Math.sqrt(squaredNorm(dx, dy));
  }

  public static int scalarProduct(int ax, int ay, int bx, int by) {
    return ((ax * bx) + (ay * by));
  }

  /**
   * Returns true if the lines intersect, otherwise false. In addition, if the lines intersect the
   * intersection point may be stored in iX and iY.
   *
   * <p>From
   * http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
   */
  public static boolean getLineIntersection(
      double p0X,
      double p0Y,
      double p1X,
      double p1Y,
      double p2X,
      double p2Y,
      double p3X,
      double p3Y,
      MutableDouble iX,
      MutableDouble iY) {
    double s1X, s1Y;
    double s2X, s2Y;
    s1X = p1X - p0X;
    s1Y = p1Y - p0Y;
    s2X = p3X - p2X;
    s2Y = p3Y - p2Y;

    double s, t;
    s = (-s1Y * (p0X - p2X) + s1X * (p0Y - p2Y)) / (-s2X * s1Y + s1X * s2Y);
    t = (s2X * (p0Y - p2Y) - s2Y * (p0X - p2X)) / (-s2X * s1Y + s1X * s2Y);

    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
      // Collision detected
      if (iX != null) iX.setValue(p0X + (t * s1X));
      if (iY != null) iY.setValue(p0Y + (t * s1Y));
      return true;
    }

    return false; // No collision
  }

  // Returns whether the line segments [a, b] and [c, d] intersect.
  public static boolean intersect(int ax, int ay, int bx, int by, int cx, int cy, int dx, int dy) {
    return getLineIntersection(ax, ay, bx, by, cx, cy, dx, dy, null, null);
  }

  /**
   * Returns a random element from the specified container. There is nothing special about this
   * function other than to provide a function similar to the one provided in the original C++ BWEM.
   *
   * @param list the specified list
   */
  public static <T> T getRandomElement(final List<T> list) {
    final int randomIndex = randomGenerator.nextInt(list.size());
    return list.get(randomIndex);
  }

  public static <T> void fastErase(final List<T> list, final int index) {
    //        bwem_assert((0 <= i) && (i < (int)Vector.size()));
    if (!((0 <= index) && (index < list.size()))) {
      throw new IllegalArgumentException("index: " + index);
    }

    Collections.swap(list, index, list.size() - 1);
    list.remove(list.size() - 1);
  }
}
