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

  public int cropX(final int x) {
    return (x < minX) ? minX : (x > maxX) ? maxX : x;
  }

  public int cropY(final int y) {
    return (y < minY) ? minY : (y > maxY) ? maxY : y;
  }

  public int[] crop(final int x, final int y) {
    return new int[] {cropX(x), cropY(y)};
  }
}
