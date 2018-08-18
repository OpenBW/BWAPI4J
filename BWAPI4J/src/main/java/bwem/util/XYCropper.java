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

public class XYCropper {
  private final int minX;
  private final int minY;
  private final int maxX;
  private final int maxY;

  public XYCropper(final int minX, final int minY, final int maxX, final int maxY) {
    this.minX = minX;
    this.minY = minY;
    this.maxX = maxX;
    this.maxY = maxY;
  }

  public int cropX(int x) {
    return (x < this.minX ? this.minX : (x > this.maxX) ? this.maxX : x);
  }

  public int cropY(int y) {
    return (y < this.minY ? this.minY : (y > this.maxY) ? this.maxY : y);
  }

  public int[] crop(int x, int y) {
    x = cropX(x);
    y = cropY(y);
    return new int[] {x, y};
  }
}
