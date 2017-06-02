package bwta;

import java.util.HashMap;
import java.util.Map;

import org.openbw.bwapi4j.util.AbstractPoint;

public class Tmp_Position extends AbstractPoint<Tmp_Position> {

    private int x;
    private int y;

    public Tmp_Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Tmp_Position(Tmp_TilePosition p) {
        this.x = p.getX() * Tmp_TilePosition.SIZE_IN_PIXELS;
        this.y = p.getY() * Tmp_TilePosition.SIZE_IN_PIXELS;
    }
    
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    public native boolean isValid();

    public native Tmp_Position makeValid();

    public native int getApproxDistance(Tmp_Position position);

    public native double getLength();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Tmp_Position Invalid;

    public static Tmp_Position None;

    public static Tmp_Position Unknown;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tmp_Position)) {
            return false;
        }

        Tmp_Position position = (Tmp_Position) o;

        if (x != position.x) {
            return false;
        }
        if (y != position.y) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return x * 256 * 32 + y;
    }

    private static Map<Long, Tmp_Position> instances = new HashMap<Long, Tmp_Position>();

    private Tmp_Position(long pointer) {
        this.pointer = pointer;
    }

    private static Tmp_Position get(long pointer) {
        Tmp_Position instance = instances.get(pointer);
        if (instance == null) {
            instance = new Tmp_Position(pointer);
            instances.put(pointer, instance);
        }
        return instance;
    }

    private long pointer;

    public Tmp_Position getPoint() {
        return this;
    }

    public Tmp_TilePosition toTilePosition() {
        return new Tmp_TilePosition(x / Tmp_TilePosition.SIZE_IN_PIXELS, y / Tmp_TilePosition.SIZE_IN_PIXELS);
    }
}
