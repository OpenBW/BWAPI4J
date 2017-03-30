package org.openbw.bwapi4j;

import java.util.HashMap;
import java.util.Map;

import org.openbw.bwapi4j.util.AbstractPoint;

public class TilePosition extends AbstractPoint<TilePosition>{
    private int x, y;

    public static final int SIZE_IN_PIXELS = 32;

    public TilePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public native boolean isValid();

    public native TilePosition makeValid();

    public native double getLength();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static TilePosition Invalid;

    public static TilePosition None;

    public static TilePosition Unknown;

    private static Map<Long, TilePosition> instances = new HashMap<Long, TilePosition>();

    private TilePosition(long pointer) {
        this.pointer = pointer;
    }

    private static TilePosition get(long pointer) {
        TilePosition instance = instances.get(pointer);
        if (instance == null) {
            instance = new TilePosition(pointer);
            instances.put(pointer, instance);
        }
        return instance;
    }

    private long pointer;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TilePosition)) return false;

        TilePosition that = (TilePosition) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    public TilePosition getPoint(){
        return this;
    }

    public Position toPosition(){
        return new Position(x * SIZE_IN_PIXELS, y * SIZE_IN_PIXELS);
    }
}
