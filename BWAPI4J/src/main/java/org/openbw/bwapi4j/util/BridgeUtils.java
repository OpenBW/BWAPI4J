package org.openbw.bwapi4j.util;

public class BridgeUtils {
  private static final double DECIMAL_PRESERVATION_SCALE = 100d;

  public static final double parsePreservedDouble(final int i) {
    return ((double) i) / DECIMAL_PRESERVATION_SCALE;
  }

  /**
   * BWAPI 4.2.0:
   * https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/UnitUpdate.cpp#L206-L212
   * https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/BulletImpl.cpp#L93-L97
   */
  public static final double parsePreservedBwapiAngle(final double angle) {
    return (angle * Math.PI / 128d);
  }
}
