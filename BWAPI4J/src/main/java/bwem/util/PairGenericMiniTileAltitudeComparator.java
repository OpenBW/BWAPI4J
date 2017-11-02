package bwem.util;

import bwem.tile.MiniTile;
import java.util.Comparator;
import org.apache.commons.lang3.tuple.MutablePair;

public final class PairGenericMiniTileAltitudeComparator<T> implements Comparator<MutablePair<T, MiniTile>> {

    public enum Order { ASCENDING, DESCENDING }

    private final Order order;

    public PairGenericMiniTileAltitudeComparator() {
        this.order = Order.ASCENDING;
    }

    public PairGenericMiniTileAltitudeComparator(Order order) {
        this.order = order;
    }

    @Override
    public int compare(MutablePair o1, MutablePair o2) {
        MiniTile mt1 = (MiniTile) o1.right;
        int a1 = mt1.Altitude().intValue();
        MiniTile mt2 = (MiniTile) o2.right;
        int a2 = mt2.Altitude().intValue();
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
