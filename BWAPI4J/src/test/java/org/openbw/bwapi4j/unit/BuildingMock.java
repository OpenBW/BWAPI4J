package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public class BuildingMock extends BuildingImpl {
    public BuildingMock(int id, UnitType type, int timeSpotted, final TilePosition lastKnownTilePosition, final Position lastKnownPosition) {
        super(id, type, timeSpotted);
        super.lastKnownTilePosition = lastKnownTilePosition;
        super.lastKnownPosition = lastKnownPosition;
        super.isCompleted = true;
        super.hitPoints = maxHitPoints();
        super.shields = maxShields();
    }
}
