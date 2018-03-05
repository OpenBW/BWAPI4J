package bwem.util;

import bwem.typedef.Altitude;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Comparator;

public final class PairGenericAltitudeComparator<T> implements Comparator<MutablePair<T, Altitude>> {

    @Override
    public int compare(MutablePair<T, Altitude> o1, MutablePair<T, Altitude> o2) {
        int a1 = o1.getRight().intValue();
        int a2 = o2.getRight().intValue();
        return Integer.compare(a1, a2);
    }
}
