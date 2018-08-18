package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;

public interface Building extends PlayerUnit {

  boolean cancelConstruction();

  int getBuildTime();

  int getRemainingBuildTime();

  int getProbableConstructionStart();

  /**
   * Returns the distance to given position from where this unit was located when it last was
   * visible.
   *
   * @param position tile position to measure distance to
   * @return distance in tiles
   */
  int getLastKnownDistance(TilePosition position);

  /**
   * Returns the distance to given position from where this unit was located when it last was
   * visible.
   *
   * @param position position to measure distance to
   * @return distance in pixels
   */
  double getLastKnownDistance(Position position);

  double getLastKnownDistance(Unit target);
}
