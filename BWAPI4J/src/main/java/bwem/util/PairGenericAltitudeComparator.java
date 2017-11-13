package bwem.util;

import bwem.typedef.Altitude;
import java.util.Comparator;
import org.apache.commons.lang3.tuple.MutablePair;

public final class PairGenericAltitudeComparator<T> implements Comparator<MutablePair<T, Altitude>> {

    @Override
    public int compare(MutablePair o1, MutablePair o2) {
        int a1 = ((Altitude) o1.right).intValue();
        int a2 = ((Altitude) o2.right).intValue();
        return (a1 < a2) ? -1 : (a1 > a2) ? 1 : 0;
    }

}
