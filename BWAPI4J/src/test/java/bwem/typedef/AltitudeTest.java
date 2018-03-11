package bwem.typedef;

import org.junit.Assert;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AltitudeTest {

    @Test
    public void Test_Collection_get() {
        final List<Altitude> arrayList = new ArrayList<>();
        arrayList.add(new Altitude(0));
        arrayList.add(new Altitude(1));
        arrayList.add(new Altitude(2));
        arrayList.add(new Altitude(3));
        arrayList.add(new Altitude(4));

        Assert.assertEquals(new Altitude(0), arrayList.get(0));
        Assert.assertEquals(new Altitude(1), arrayList.get(1));
        Assert.assertEquals(new Altitude(2), arrayList.get(2));
        Assert.assertEquals(new Altitude(3), arrayList.get(3));
        Assert.assertEquals(new Altitude(4), arrayList.get(4));

        Assert.assertNotEquals(new Altitude(1), arrayList.get(0));
        Assert.assertNotEquals(new Altitude(0), arrayList.get(1));
        Assert.assertNotEquals(new Altitude(9), arrayList.get(2));
        Assert.assertNotEquals(new Altitude(8), arrayList.get(3));
        Assert.assertNotEquals(new Altitude(5), arrayList.get(4));
    }

    @Test
    public void Test_compareTo() {
        {
            final Altitude a1 = new Altitude(0);
            final Altitude a2 = new Altitude(0);
            Assert.assertTrue(a1.compareTo(a2) == 0);
        }

        {
            final Altitude a1 = new Altitude(1);
            final Altitude a2 = new Altitude(2);
            Assert.assertTrue(a1.compareTo(a2) < 0);
        }

        {
            final Altitude a1 = new Altitude(1);
            final Altitude a2 = new Altitude(2);
            Assert.assertTrue(a2.compareTo(a1) > 0);
        }
    }

    @Test
    public void Test_Collections_sort() {
        final List<Altitude> arrayList = new ArrayList<>();
        arrayList.add(new Altitude(9));
        arrayList.add(new Altitude(2));
        arrayList.add(new Altitude(3));
        arrayList.add(new Altitude(0));
        arrayList.add(new Altitude(5));
        arrayList.add(new Altitude(1));
        arrayList.add(new Altitude(6));
        arrayList.add(new Altitude(7));
        arrayList.add(new Altitude(4));
        arrayList.add(new Altitude(8));

        /* Assert the list is not in ascending order. */ {
            boolean ascendingOrder = true;
            for (int i = 1; i < arrayList.size(); ++i) {
                if (arrayList.get(i - 1).compareTo(arrayList.get(i)) > 0) {
                    ascendingOrder = false;
                }
            }
            Assert.assertFalse(ascendingOrder);
        }

        Collections.sort(arrayList);

        /* Assert the list is now in ascending order. */ {
            boolean ascendingOrder = true;
            for (int i = 1; i < arrayList.size(); ++i) {
                if (arrayList.get(i - 1).compareTo(arrayList.get(i)) > 0) {
                    ascendingOrder = false;
                }
            }
            Assert.assertTrue(ascendingOrder);
        }
    }

    @Test
    public void Test_HashMap_get() {
        final AbstractMap<Altitude, Integer> map = new ConcurrentHashMap<>();
        map.put(new Altitude(0), 0);
        map.put(new Altitude(1), 1);
        map.put(new Altitude(2), 2);
        map.put(new Altitude(3), 3);
        map.put(new Altitude(4), 4);

        Assert.assertEquals(0, map.get(new Altitude(0)).intValue());
        Assert.assertEquals(1, map.get(new Altitude(1)).intValue());
        Assert.assertEquals(2, map.get(new Altitude(2)).intValue());
        Assert.assertEquals(3, map.get(new Altitude(3)).intValue());
        Assert.assertEquals(4, map.get(new Altitude(4)).intValue());

        Assert.assertNotEquals(1, map.get(new Altitude(0)).intValue());
        Assert.assertNotEquals(4, map.get(new Altitude(1)).intValue());
        Assert.assertNotEquals(3, map.get(new Altitude(2)).intValue());
        Assert.assertNotEquals(4, map.get(new Altitude(3)).intValue());
        Assert.assertNotEquals(8, map.get(new Altitude(4)).intValue());
    }

}
