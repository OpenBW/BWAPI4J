package bwta;

import java.util.HashMap;
import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.util.Pair;

public class Chokepoint extends CenteredObject {

    public Pair<Region, Region> getRegions() {
        return getRegions_native(pointer);
    }

    public Pair<Position, Position> getSides() {
        return getSides_native(pointer);
    }

    public Position getCenter() {
        return getCenter_native(pointer);
    }

    public double getWidth() {
        return getWidth_native(pointer);
    }


    private static Map<Long, Chokepoint> instances = new HashMap<Long, Chokepoint>();

    private Chokepoint(long pointer) {
        this.pointer = pointer;
    }

    private static Chokepoint get(long pointer) {
        if (pointer == 0) {
            return null;
        }
        Chokepoint instance = instances.get(pointer);
        if (instance == null) {
            instance = new Chokepoint(pointer);
            instances.put(pointer, instance);
        }
        return instance;
    }

    private long pointer;

    private native Pair<Region, Region> getRegions_native(long pointer);

    private native Pair<Position, Position> getSides_native(long pointer);

    private native Position getCenter_native(long pointer);

    private native double getWidth_native(long pointer);


}