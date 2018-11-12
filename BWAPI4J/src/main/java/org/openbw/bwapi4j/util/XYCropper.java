package org.openbw.bwapi4j.util;

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
