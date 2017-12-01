package bwem.util;

import bwem.typedef.Altitude;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.Assert;
import org.junit.Test;
import org.openbw.bwapi4j.WalkPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PairGenericAltitudeComparatorTest {

    @Test
    public void testPairGenericAltitudeComparator() {
        final List<MutablePair<WalkPosition, Altitude>> list = new ArrayList<>();

        list.add(new MutablePair<>(new WalkPosition(4, 2), new Altitude(1)));
        list.add(new MutablePair<>(new WalkPosition(15, 9), new Altitude(2)));
        list.add(new MutablePair<>(new WalkPosition(17, 8), new Altitude(4)));
        list.add(new MutablePair<>(new WalkPosition(127, 0), new Altitude(5)));
        list.add(new MutablePair<>(new WalkPosition(78, 301), new Altitude(3)));

        // Sort by ascending order.
        Collections.sort(list, new PairGenericAltitudeComparator<>());

        Assert.assertEquals(new MutablePair<>(new WalkPosition(4, 2), new Altitude(1)), list.get(0));
        Assert.assertEquals(new MutablePair<>(new WalkPosition(15, 9), new Altitude(2)), list.get(1));
        Assert.assertEquals(new MutablePair<>(new WalkPosition(78, 301), new Altitude(3)), list.get(2));
        Assert.assertEquals(new MutablePair<>(new WalkPosition(17, 8), new Altitude(4)), list.get(3));
        Assert.assertEquals(new MutablePair<>(new WalkPosition(127, 0), new Altitude(5)), list.get(4));
    }

}
