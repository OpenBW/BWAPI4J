package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public class BuildingMock extends BuildingImpl {
  public BuildingMock(
      int id,
      UnitType type,
      int timeSpotted,
      final TilePosition lastKnownTilePosition,
      final Position lastKnownPosition) {
    super(type, timeSpotted);
    this.iD = id;
    this.lastKnownTilePosition = lastKnownTilePosition;
    this.lastKnownPosition = lastKnownPosition;
    this.completed = true;
    this.hitPoints = maxHitPoints();
    this.shields = maxShields();
  }
}
