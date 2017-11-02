package bwem.util;

import bwem.typedef.Altitude;
import java.util.Comparator;
import org.apache.commons.lang3.tuple.MutablePair;

public final class PairGenericAltitudeComparator<T> implements Comparator<MutablePair<T, Altitude>> {

    public enum Order { ASCENDING, DESCENDING }

    private final Order order;

    public PairGenericAltitudeComparator() {
        this.order = Order.ASCENDING;
    }

    public PairGenericAltitudeComparator(Order order) {
        this.order = order;
    }

    @Override
    public int compare(MutablePair o1, MutablePair o2) {
        int a1 = ((Altitude) o1.right).intValue();
        int a2 = ((Altitude) o2.right).intValue();
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
