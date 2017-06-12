package bwem;

import java.util.Comparator;
import org.openbw.bwapi4j.util.Pair;

public class PairGenericAltitudeComparator<T> implements Comparator<Pair<T, Altitude>> {

    @Override
    public int compare(Pair o1, Pair o2) {
        int a1 = ((Altitude) o1.second).toInt();
        int a2 = ((Altitude) o2.second).toInt();
        return (a1 < a2) ? -1 : (a1 > a2) ? 1 : 0;
    }

}
