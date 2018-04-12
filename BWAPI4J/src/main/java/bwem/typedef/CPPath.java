package bwem.typedef;

import bwem.ChokePoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Type of all the Paths used in BWEM (Cf. Map::GetPath).
 *
 * cp.h:68:typedef std::vector<const ChokePoint *> Path;
 * cp.h:168:typedef ChokePoint::Path CPPath;
 */
public class CPPath implements Iterable<ChokePoint> {

    private final List<ChokePoint> chokepoints;

    public CPPath() {
        this.chokepoints = new ArrayList<>();
    }

    public int size() {
        return this.chokepoints.size();
    }

    public ChokePoint get(final int index) {
        return this.chokepoints.get(index);
    }

    public void add(final ChokePoint chokepoint) {
        this.chokepoints.add(chokepoint);
    }

    public void add(final int index, final ChokePoint chokepoint) {
        this.chokepoints.add(index, chokepoint);
    }

    public void clear() {
        this.chokepoints.clear();
    }

    public boolean isEmpty() {
        return this.chokepoints.isEmpty();
    }

    @Override
    public Iterator<ChokePoint> iterator() {
        return chokepoints.iterator();
    }

}
