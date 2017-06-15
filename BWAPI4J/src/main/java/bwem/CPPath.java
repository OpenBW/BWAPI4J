package bwem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CPPath implements Iterable<Chokepoint> {

    private List<Chokepoint> chokepoints;

    public CPPath() {
        this.chokepoints = new ArrayList<>();
    }

    public int size() {
        return this.chokepoints.size();
    }

    public Chokepoint get(int index) {
        return this.chokepoints.get(index);
    }

    public void add(Chokepoint chokepoint) {
        this.chokepoints.add(chokepoint);
    }

    public class CPPathIterator implements Iterator<Chokepoint> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return (this.cursor < size());
        }

        @Override
        public Chokepoint next() {
            if (!hasNext()) {
               throw new NoSuchElementException();
            }
            return get(this.cursor++);
        }

    }

    @Override
    public Iterator<Chokepoint> iterator() {
        return new CPPathIterator();
    }

}
