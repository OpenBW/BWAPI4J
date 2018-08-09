package org.openbw.bwapi4j.util;

public class BridgeUtils {
    private static final double DECIMAL_PRESERVATION_SCALE = 100d;

    public static final double parsePreservedDouble(final int i) {
        return ((double) i) / DECIMAL_PRESERVATION_SCALE;
    }
}
