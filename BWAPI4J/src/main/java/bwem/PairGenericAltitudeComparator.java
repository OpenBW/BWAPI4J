package bwem;

import java.util.Comparator;
import org.openbw.bwapi4j.util.Pair;

public final class PairGenericAltitudeComparator<T> implements Comparator<Pair<T, Altitude>> {

    public enum Order { ASCENDING, DESCENDING }

    private final Order order;

    public PairGenericAltitudeComparator() {
        this.order = Order.ASCENDING;
    }

    public PairGenericAltitudeComparator(Order order) {
        this.order = order;
    }

    @Override
    public int compare(Pair o1, Pair o2) {
        int a1 = ((Altitude) o1.second).intValue();
        int a2 = ((Altitude) o2.second).intValue();
        switch (this.order) {
            case ASCENDING:
                return (a1 < a2) ? -1 : (a1 > a2) ? 1 : 0;
            case DESCENDING:
                return (a1 < a2) ? 1 : (a1 > a2) ? -1 : 0;
            default:
                throw new UnsupportedOperationException("Ordering algorithm not yet supported: " + this.order.toString());
        }
    }

}
