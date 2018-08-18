package bwem;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class OldMarkableTest {
  /** This test fails which is the reason for the new "Markable" approach. */
  @Ignore
  @Test
  public void testMarkable() {
    final int maxElements = 6;

    OldMarkableClassA.UnmarkAll();
    final List<OldMarkableClassA> a;
    a = new ArrayList<>();
    for (int i = 0; i < maxElements; ++i) {
      OldMarkableClassA markable = new OldMarkableClassA(i);
      if (i == 0 || i == 1 || i == 2) {
        markable.SetMarked();
      }
      a.add(markable);
    }

    OldMarkableClassB.UnmarkAll();
    final List<OldMarkableClassB> b;
    b = new ArrayList<>();
    for (int i = 0; i < maxElements; ++i) {
      OldMarkableClassB markable = new OldMarkableClassB(i);
      if (i == 3 || i == 4 || i == 5) {
        markable.SetMarked();
      }
      b.add(markable);
    }

    for (int i = 0; i < maxElements; ++i) {
      OldMarkableClassA markable = a.get(i);
      if (i == 0 || i == 1 || i == 2) {
        Assert.assertEquals("index=" + i, true, markable.Marked());
      } else {
        Assert.assertEquals("index=" + i, false, markable.Marked());
      }
    }

    for (int i = 0; i < maxElements; ++i) {
      OldMarkableClassB markable = b.get(i);
      if (i == 3 || i == 4 || i == 5) {
        Assert.assertEquals("index=" + i, true, markable.Marked());
      } else {
        Assert.assertEquals("index=" + i, false, markable.Marked());
      }
    }
  }
}
