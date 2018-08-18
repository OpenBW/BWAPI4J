package bwem.util;

import bwem.area.typedef.AreaId;
import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.typedef.Altitude;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.MutablePair;
import org.junit.Assert;
import org.junit.Test;
import org.openbw.bwapi4j.WalkPosition;

public class PairGenericMiniTileAltitudeComparatorTest {

  @Test
  public void testPairGenericMiniTileAltitudeComparator() {
    final List<MutablePair<WalkPosition, MiniTile>> list = new ArrayList<>();

    final MiniTile m1 = new MiniTileImpl();
    ((MiniTileImpl) m1).setAreaId(new AreaId(2));
    ((MiniTileImpl) m1).setAltitude(new Altitude(5));
    final MiniTile m2 = new MiniTileImpl();
    ((MiniTileImpl) m2).setAreaId(new AreaId(1));
    ((MiniTileImpl) m2).setAltitude(new Altitude(2));
    final MiniTile m3 = new MiniTileImpl();
    ((MiniTileImpl) m3).setAreaId(new AreaId(3));
    ((MiniTileImpl) m3).setAltitude(new Altitude(7));
    final MiniTile m4 = new MiniTileImpl();
    ((MiniTileImpl) m4).setAreaId(new AreaId(5));
    ((MiniTileImpl) m4).setAltitude(new Altitude(1));
    final MiniTile m5 = new MiniTileImpl();
    ((MiniTileImpl) m5).setAreaId(new AreaId(4));
    ((MiniTileImpl) m5).setAltitude(new Altitude(8));

    list.add(new MutablePair<>(new WalkPosition(5, 15), m1));
    list.add(new MutablePair<>(new WalkPosition(2, 98), m2));
    list.add(new MutablePair<>(new WalkPosition(63, 123), m3));
    list.add(new MutablePair<>(new WalkPosition(103, 435), m4));
    list.add(new MutablePair<>(new WalkPosition(89, 77), m5));

    // Sort by descending order.
    Collections.sort(list, new PairGenericMiniTileAltitudeComparator<>());
    Collections.reverse(list);

    Assert.assertEquals(new MutablePair<>(new WalkPosition(89, 77), m5), list.get(0));
    Assert.assertEquals(new MutablePair<>(new WalkPosition(63, 123), m3), list.get(1));
    Assert.assertEquals(new MutablePair<>(new WalkPosition(5, 15), m1), list.get(2));
    Assert.assertEquals(new MutablePair<>(new WalkPosition(2, 98), m2), list.get(3));
    Assert.assertEquals(new MutablePair<>(new WalkPosition(103, 435), m4), list.get(4));
  }
}
