package bwem.util;

import bwem.typedef.Altitude;
import java.util.Comparator;
import org.apache.commons.lang3.tuple.MutablePair;

public final class PairGenericAltitudeComparator<T> implements Comparator<MutablePair<T, Altitude>> {

    @Override
    public int compare(MutablePair<T, Altitude> o1, MutablePair<T, Altitude> o2) {
        int a1 = o1.right.intValue();
        int a2 = o2.right.intValue();
        return Integer.compare(a1, a2);
    }
}
