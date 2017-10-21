package bwem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// Type of all the Paths used in BWEM (Cf. Map::GetPath).
public class CPPath implements Iterable<ChokePoint> {

    private List<ChokePoint> chokepoints;

    public CPPath() {
        this.chokepoints = new ArrayList<>();
    }

    public int size() {
        return this.chokepoints.size();
    }

    public ChokePoint get(int index) {
        return this.chokepoints.get(index);
    }

    public void add(ChokePoint chokepoint) {
        this.chokepoints.add(chokepoint);
    }

    private class CPPathIterator implements Iterator<ChokePoint> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return (this.cursor < size());
        }

        @Override
        public ChokePoint next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                return get(this.cursor++);
            }
        }

    }

    @Override
    public Iterator<ChokePoint> iterator() {
        return new CPPathIterator();
    }

}
