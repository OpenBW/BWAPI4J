package org.openbw.bwapi4j;

import org.junit.Assert;
import org.junit.Test;

/** These tests were ported from the original BWAPI 4.2.0 positionTest.cpp */
public class PositionTest {

  @Test
  public void PositionCtorValue() {
    final Position pos = new Position(32, 32);
    final TilePosition tpos = new TilePosition(32, 32);
    final WalkPosition wpos = new WalkPosition(32, 32);

    Assert.assertEquals(32, pos.getX());
    Assert.assertEquals(32, pos.getY());
    Assert.assertEquals(32, tpos.getX());
    Assert.assertEquals(32, tpos.getY());
    Assert.assertEquals(32, wpos.getX());
    Assert.assertEquals(32, wpos.getY());
  }

  @Test
  public void PositionCtorConversion() {
    // From position
    final Position pos = new Position(32, 32);
    final TilePosition tpos = new TilePosition(pos);
    final WalkPosition wpos = new WalkPosition(pos);
    Assert.assertEquals(32, pos.getX());
    Assert.assertEquals(32, pos.getY());
    Assert.assertEquals(1, tpos.getX());
    Assert.assertEquals(1, tpos.getY());
    Assert.assertEquals(4, wpos.getX());
    Assert.assertEquals(4, wpos.getY());

    // From tile position
    final Position pos2 = new Position(tpos);
    final WalkPosition wpos2 = new WalkPosition(tpos);
    Assert.assertEquals(32, pos2.getX());
    Assert.assertEquals(32, pos2.getY());
    Assert.assertEquals(4, wpos2.getX());
    Assert.assertEquals(4, wpos2.getY());

    // From walk position
    final Position pos3 = new Position(wpos);
    final TilePosition tpos3 = new TilePosition(wpos);
    Assert.assertEquals(32, pos3.getX());
    Assert.assertEquals(32, pos3.getY());
    Assert.assertEquals(1, tpos3.getX());
    Assert.assertEquals(1, tpos3.getY());
  }

  @Test
  public void PositionEquality() {
    final TilePosition p1 = new TilePosition(0, 0);
    final TilePosition p2 = new TilePosition(2, 2);

    Assert.assertFalse(p1.equals(p2));
    Assert.assertTrue(!p1.equals(p2));
  }

  @Test
  public void PositionAdd() {
    Position p1 = new Position(1, 1);
    Position p2 = new Position(1, 2);

    Position p3 = p1.add(p2);
    Assert.assertEquals(new Position(2, 3), p3);

    p3 = p3.add(p1);
    Assert.assertEquals(new Position(3, 4), p3);

    p3 = p3.add(new Position(0, 0));
    Assert.assertEquals(new Position(3, 4), p3);
  }

  @Test
  public void PositionSubtract() {
    Position p1 = new Position(1, 1);
    Position p2 = new Position(1, 2);

    Position p3 = p1.subtract(p2);
    Assert.assertEquals(new Position(0, -1), p3);

    p3 = p3.subtract(p1);
    Assert.assertEquals(new Position(-1, -2), p3);

    p3 = p3.subtract(new Position(0, 0));
    Assert.assertEquals(new Position(-1, -2), p3);
  }

  @Test
  public void PositionMultiply() {
    Position p1 = new Position(1, 2);

    Position p2 = p1.multiply(new Position(1, 1));
    Position p3 = p1.multiply(new Position(2, 2));
    Assert.assertEquals(new Position(1, 2), p2);
    Assert.assertEquals(new Position(2, 4), p3);

    p2 = p2.multiply(new Position(2, 2));
    p3 = p3.multiply(new Position(1, 1));
    Assert.assertEquals(new Position(2, 4), p2);
    Assert.assertEquals(new Position(2, 4), p3);
  }

  @Test
  public void PositionDivide() {
    Position p1 = new Position(1, 2);

    Position p2 = p1.divide(new Position(1, 1));
    Position p3 = p1.divide(new Position(2, 2));
    Assert.assertEquals(new Position(1, 2), p2);
    Assert.assertEquals(new Position(0, 1), p3);

    p2 = p2.divide(new Position(2, 2));
    p3 = p3.divide(new Position(1, 1));
    Assert.assertEquals(new Position(0, 1), p2);
    Assert.assertEquals(new Position(0, 1), p3);
  }

  @Test
  public void PositionOstream() {
    Position p1 = new Position(2, -3);
    Assert.assertEquals("[2, -3]", p1.toString());
  }
}
