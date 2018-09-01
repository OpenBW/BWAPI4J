package org.openbw.bwapi4j.unit;

import java.util.Collection;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.ap.NativeLookup;
import org.openbw.bwapi4j.type.UnitSizeType;
import org.openbw.bwapi4j.type.UnitType;

@NativeLookup(method = "getUnit")
public interface Unit extends Comparable<Unit> {

  int getKillCount();

  /**
   * Returns the frame number this unit was visible last.
   */
  int getLastSpotted();

  /**
   * Returns the frame number this unit was visible the first time.
   */
  int getInitiallySpotted();

  int getId();

  int getLeft();

  int getTop();

  int getRight();

  int getBottom();

  Position getMiddle(Unit unit);

  double getAngle();

  <T extends Unit> T getClosest(Collection<T> group);

  <T extends Unit> List<T> getUnitsInRadius(int radius, Collection<T> group);

  int getX();

  int getY();

  int height();

  int width();

  int tileHeight();

  int tileWidth();

  TilePosition getTilePosition();

  Position getPosition();

  UnitSizeType getSize();

  double getDistance(Position target);

  double getDistance(int x, int y);

  int getDistance(Unit target);

  boolean exists();

  /**
   * Returns the type of this unit. Always returns the same type. If the BWAPI type changes (ie.
   * from {@link UnitType#Resource_Vespene_Geyser} to {@link UnitType#Zerg_Extractor} a new instance
   * will be created, and this instance will no longer be updated.
   */
  UnitType getType();

  Position getInitialPosition();

  TilePosition getInitialTilePosition();

  boolean isFlying();

  boolean isVisible();

  boolean isSelected();
}
