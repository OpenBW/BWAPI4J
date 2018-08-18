package org.openbw.bwapi4j;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class WalkPositionTest {

  @Test
  public void testArrayListContains() {
    final List<WalkPosition> list = new ArrayList<>();
    list.add(new WalkPosition(0, 0));
    list.add(new WalkPosition(24, 87));
    list.add(new WalkPosition(48, 39));
    list.add(new WalkPosition(361, 92));
    list.add(new WalkPosition(510, 6));

    Assert.assertTrue(list.contains(new WalkPosition(0, 0)));
    Assert.assertTrue(list.contains(new WalkPosition(24, 87)));
    Assert.assertTrue(list.contains(new WalkPosition(48, 39)));
    Assert.assertTrue(list.contains(new WalkPosition(361, 92)));
    Assert.assertTrue(list.contains(new WalkPosition(510, 6)));
  }
}
