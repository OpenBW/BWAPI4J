package bwem;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class MarkableTest {

  @Test
  public void Test_setMarked_with_isMarked() {
    MarkableClassA.getStaticMarkable().unmarkAll();
    final List<MarkableClassA> a = new ArrayList<>();
    a.add(new MarkableClassA(0));
    a.add(new MarkableClassA(1));
    a.add(new MarkableClassA(2));
    a.add(new MarkableClassA(3));
    a.add(new MarkableClassA(4));
    a.add(new MarkableClassA(5));
    a.get(0).getMarkable().setMarked();
    a.get(1).getMarkable().setMarked();
    a.get(2).getMarkable().setMarked();

    MarkableClassB.getStaticMarkable().unmarkAll();
    final List<MarkableClassA> b = new ArrayList<>();
    b.add(new MarkableClassA(0));
    b.add(new MarkableClassA(1));
    b.add(new MarkableClassA(2));
    b.add(new MarkableClassA(3));
    b.add(new MarkableClassA(4));
    b.add(new MarkableClassA(5));
    b.get(3).getMarkable().setMarked();
    b.get(4).getMarkable().setMarked();
    b.get(5).getMarkable().setMarked();

    Assert.assertEquals(true, a.get(0).getMarkable().isMarked());
    Assert.assertEquals(true, a.get(1).getMarkable().isMarked());
    Assert.assertEquals(true, a.get(2).getMarkable().isMarked());
    Assert.assertEquals(false, a.get(3).getMarkable().isMarked());
    Assert.assertEquals(false, a.get(4).getMarkable().isMarked());
    Assert.assertEquals(false, a.get(5).getMarkable().isMarked());

    Assert.assertEquals(false, b.get(0).getMarkable().isMarked());
    Assert.assertEquals(false, b.get(1).getMarkable().isMarked());
    Assert.assertEquals(false, b.get(2).getMarkable().isMarked());
    Assert.assertEquals(true, b.get(3).getMarkable().isMarked());
    Assert.assertEquals(true, b.get(4).getMarkable().isMarked());
    Assert.assertEquals(true, b.get(5).getMarkable().isMarked());
  }
}
