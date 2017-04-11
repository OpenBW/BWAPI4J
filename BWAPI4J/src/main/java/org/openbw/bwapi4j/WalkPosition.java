package org.openbw.bwapi4j;

import java.util.HashMap;
import java.util.Map;

import org.openbw.bwapi4j.util.AbstractPoint;

public class WalkPosition extends AbstractPoint<WalkPosition> {

    private int x;
    private int y;

    public static final int SIZE_IN_PIXELS = 8;

    public WalkPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public WalkPosition(TilePosition p) {
        this.x = p.getX() * TilePosition.SIZE_IN_PIXELS / WalkPosition.SIZE_IN_PIXELS;
        this.y = p.getY() * TilePosition.SIZE_IN_PIXELS / WalkPosition.SIZE_IN_PIXELS;
    }
    
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public native boolean isValid();

    public native WalkPosition makeValid();

    public native double getLength();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static WalkPosition Invalid;

    public static WalkPosition None;

    public static WalkPosition Unknown;

    private static Map<Long, WalkPosition> instances = new HashMap<Long, WalkPosition>();

    private WalkPosition(long pointer) {
        this.pointer = pointer;
    }

    private static WalkPosition get(long pointer) {
        WalkPosition instance = instances.get(pointer);
        if (instance == null) {
            instance = new WalkPosition(pointer);
            instances.put(pointer, instance);
        }
        return instance;
    }

    private long pointer;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalkPosition)) {
            return false;
        }

        WalkPosition that = (WalkPosition) o;

        if (x != that.x) {
            return false;
        }
        if (y != that.y) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return x * 256 * 4 + y;
    }

    public WalkPosition getPoint() {
        return this;
    }

    public Position toPosition() {
        return new Position(x * SIZE_IN_PIXELS, y * SIZE_IN_PIXELS);
    }
}
