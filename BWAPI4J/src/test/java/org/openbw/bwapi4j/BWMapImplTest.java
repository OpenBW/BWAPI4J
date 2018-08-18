package org.openbw.bwapi4j;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class BWMapImplTest {
  private final BWMapImpl sut = new BWMapImpl(null);

  @Before
  public void setup() {
    sut.width = 10;
    sut.height = 20;
  }

  @DataPoints("validTilePositions")
  public static final TilePosition[] validMapTilePositions = {
    new TilePosition(0, 0), new TilePosition(9, 19),
  };

  @Theory
  public void shouldAcceptValidTilePositions(
      @FromDataPoints("validTilePositions") TilePosition tilePosition) {
    assertTrue(sut.isValidPosition(tilePosition));
  }

  @DataPoints("invalidTilePositions")
  public static final TilePosition[] invalidMapTilePositions = {
    new TilePosition(-1, 0), new TilePosition(5, 21),
  };

  @Theory
  public void shouldDenyInvalidTilePositions(
      @FromDataPoints("invalidTilePositions") TilePosition tilePosition) {
    assertFalse(sut.isValidPosition(tilePosition));
  }

  @DataPoints("validWalkPositions")
  public static final WalkPosition[] validMapWalkPositions = {
    new WalkPosition(0, 0), new WalkPosition(39, 79),
  };

  @Theory
  public void shouldAcceptValidWalkPositions(
      @FromDataPoints("validWalkPositions") WalkPosition walkPosition) {
    assertTrue(sut.isValidPosition(walkPosition));
  }

  @DataPoints("invalidWalkPositions")
  public static final WalkPosition[] invalidMapWalkPositions = {
    new WalkPosition(-1, 0), new WalkPosition(20, 80),
  };

  @Theory
  public void shouldDenyInvalidWalkPositions(
      @FromDataPoints("invalidWalkPositions") WalkPosition walkPosition) {
    assertFalse(sut.isValidPosition(walkPosition));
  }

  @DataPoints("validPositions")
  public static final Position[] validMapPositions = {
    new Position(0, 0), new Position(319, 639),
  };

  @Theory
  public void shouldAcceptValidPositions(@FromDataPoints("validPositions") Position position) {
    assertTrue(sut.isValidPosition(position));
  }

  @DataPoints("invalidPositions")
  public static final Position[] invalidMapPositions = {
    new Position(-1, 0), new Position(160, 641),
  };

  @Theory
  public void shouldDenyInvalidPositions(@FromDataPoints("invalidPositions") Position position) {
    assertFalse(sut.isValidPosition(position));
  }
}
