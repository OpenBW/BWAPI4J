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
    public void testGet() {
        final List<Altitude> arrayList = new ArrayList<>();
        arrayList.add(new Altitude(0));
        arrayList.add(new Altitude(1));
        arrayList.add(new Altitude(2));
        arrayList.add(new Altitude(3));
        arrayList.add(new Altitude(4));

        Assert.assertEquals(arrayList.get(0), new Altitude(0));
        Assert.assertEquals(arrayList.get(1), new Altitude(1));
        Assert.assertEquals(arrayList.get(2), new Altitude(2));
        Assert.assertEquals(arrayList.get(3), new Altitude(3));
        Assert.assertEquals(arrayList.get(4), new Altitude(4));

        Assert.assertNotEquals(arrayList.get(0), new Altitude(1));
        Assert.assertNotEquals(arrayList.get(1), new Altitude(0));
        Assert.assertNotEquals(arrayList.get(2), new Altitude(9));
        Assert.assertNotEquals(arrayList.get(3), new Altitude(8));
        Assert.assertNotEquals(arrayList.get(4), new Altitude(5));
    }

    @Test
    public void testCompareTo() {
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
    public void testSort() {
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

        {
            boolean ascendingOrder = true;
            {
                Altitude prev = arrayList.get(0);
                for (int i = 1; i < 10; ++i) {
                    if (prev.compareTo(arrayList.get(i)) >= 0) {
                        ascendingOrder = false;
                        break;
                    }
                    prev = arrayList.get(i);
                }
            }
            Assert.assertFalse(ascendingOrder);
        }

        Collections.sort(arrayList);

        {
            boolean ascendingOrder = true;
            {
                Altitude prev = arrayList.get(0);
                for (int i = 1; i < 10; ++i) {
                    if (prev.compareTo(arrayList.get(i)) >= 0) {
                        ascendingOrder = false;
                        break;
                    }
                    prev = arrayList.get(i);
                }
            }
            Assert.assertTrue(ascendingOrder);
        }
    }

    @Test
    public void testMapAndIntValue() {
        final AbstractMap<Altitude, Integer> map = new ConcurrentHashMap<>();
        map.put(new Altitude(0), 0);
        map.put(new Altitude(1), 1);
        map.put(new Altitude(2), 2);
        map.put(new Altitude(3), 3);
        map.put(new Altitude(4), 4);

        Assert.assertEquals(map.get(new Altitude(0)).intValue(), 0);
        Assert.assertEquals(map.get(new Altitude(1)).intValue(), 1);
        Assert.assertEquals(map.get(new Altitude(2)).intValue(), 2);
        Assert.assertEquals(map.get(new Altitude(3)).intValue(), 3);
        Assert.assertEquals(map.get(new Altitude(4)).intValue(), 4);

        Assert.assertNotEquals(map.get(new Altitude(0)).intValue(), 1);
        Assert.assertNotEquals(map.get(new Altitude(1)).intValue(), 4);
        Assert.assertNotEquals(map.get(new Altitude(2)).intValue(), 3);
        Assert.assertNotEquals(map.get(new Altitude(3)).intValue(), 4);
        Assert.assertNotEquals(map.get(new Altitude(4)).intValue(), 8);
    }

}
