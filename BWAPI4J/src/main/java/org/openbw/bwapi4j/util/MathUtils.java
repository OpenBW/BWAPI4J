////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j.util;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/** Contains utility functions for basic calculations. */
public final class MathUtils {
  private MathUtils() throws InstantiationException {
    Utils.throwInstantiationException();
  }

  /**
   * Returns the distance between two boxes. The distance is calculated from the edge of the boxes.
   * If the boxes overlap in a dimension, the delta in that dimension is zero.
   *
   * @param left1 the left of the first box.
   * @param top1 the top of the first box.
   * @param right1 the right of the first box.
   * @param bottom1 the bottom of the first box.
   * @param left2 the left of the second box.
   * @param top2 the top of the second box.
   * @param right2 the right of the second box.
   * @param bottom2 the bottom of the second box.
   * @return An estimated distance between two boxes.
   */
  public static double distanceBetween(
      final int left1,
      final int top1,
      final int right1,
      final int bottom1,
      final int left2,
      final int top2,
      final int right2,
      final int bottom2) {
    return distanceWithDelta(
        calculateDelta(left1, right1, left2, right2), calculateDelta(top1, bottom1, top2, bottom2));
  }

  /**
   * Returns the distance as BW would between two points. This is ported from BWAPI's
   * getApproxDistance method.
   *
   * @param x1 the x value of the first point.
   * @param y1 the y value of the first point.
   * @param x2 the x value of the second point.
   * @param y2 the y value of the second point.
   * @return An estimated distance between two points.
   */
  public static int estimateDistanceBetween(
      final int x1, final int y1, final int x2, final int y2) {
    return estimateDistanceWithDelta(x2 - x1, y2 - y1);
  }

  /**
   * Returns the distance as BW would between two boxes. This is ported from BWAPI's
   * getApproxDistance method.
   *
   * <p>The distance is calculated from the edge of the boxes. If the boxes overlap in a dimension,
   * the delta in that dimension is zero.
   *
   * @param left1 the left of the first box.
   * @param top1 the top of the first box.
   * @param right1 the right of the first box.
   * @param bottom1 the bottom of the first box.
   * @param left2 the left of the second box.
   * @param top2 the top of the second box.
   * @param right2 the right of the second box.
   * @param bottom2 the bottom of the second box.
   * @return An estimated distance between two boxes.
   */
  public static int estimateDistanceBetween(
      final int left1,
      final int top1,
      final int right1,
      final int bottom1,
      final int left2,
      final int top2,
      final int right2,
      final int bottom2) {
    return estimateDistanceWithDelta(
        calculateDelta(top1, bottom1, top2, bottom2), calculateDelta(left1, right1, left2, right2));
  }

  private static int calculateDelta(
      final int topLeft1, final int bottomRight1, final int topLeft2, final int bottomRight2) {
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

  private static int estimateDistanceWithDelta(final int dx, final int dy) {
    int min = abs(dx);
    int max = abs(dy);

    if (max < min) {
      final int t = max;
      max = min;
      min = t;
    }

    if (min < (max >> 2)) return max;

    final int minCalc = (3 * min) >> 3;
    return (minCalc >> 5) + minCalc + max - (max >> 4) - (max >> 6);
  }

  private static double distanceWithDelta(final int dx, final int dy) {
    return sqrt(dx * dx + dy * dy);
  }
}
