package org.openbw.bwapi4j.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.SplittableRandom;

public class PairTest {
    private void assertIntegersPair(final int x1, final int y1, final int x2, final int y2, final boolean displayNoCollisionDetails) {
        final Pair<Integer, Integer> pair1 = new Pair<>(x1, y1);
        final Pair<Integer, Integer> pair2 = new Pair<>(x2, y2);

        final String details = "pair1=" + pair1.toString() + ", hashCode1=" + pair1.hashCode() + ", pair2=" + pair2.toString() + ", hashCode2=" + pair2.hashCode();

        if (pair1.hashCode() == pair2.hashCode()) {
            Assert.fail("Hash code collision: " + details);
        } else if (pair1.equals(pair2)) {
            Assert.fail("Equals collision: " + details);
        } else {
            if (displayNoCollisionDetails) {
                System.out.println("No collision: " + details);
            }
        }
    }

    @Test
    public void Test_Collisions_Fixed() {
        assertIntegersPair(1, 0, 0, 1, true);
        assertIntegersPair(0, 1, 1, 0, true);
        assertIntegersPair(1, 0, 0, 31, true);
        assertIntegersPair(-1, 0, 0, -31, true);
        assertIntegersPair(500, 500, -500, -500, true);
    }

    @Test
    public void Test_Collisions_Random() {
        final SplittableRandom rng = new SplittableRandom();
        final int ceiling = 2048;

        for (int i = 0; i < 10000; ++i) {
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

            while ((x1 = rng.nextInt(ceiling)) == (x2 = rng.nextInt(ceiling)) || (y1 = rng.nextInt(ceiling)) == (y2 = rng.nextInt(ceiling)));

            assertIntegersPair(x1, y1, x2, y2, false);
        }
    }
}
