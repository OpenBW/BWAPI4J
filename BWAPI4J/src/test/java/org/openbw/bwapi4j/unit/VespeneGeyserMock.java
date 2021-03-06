package org.openbw.bwapi4j.unit;

import bwem.util.BwemExt;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public class VespeneGeyserMock extends VespeneGeyser implements Resource {
  private final int initialResources;
  private final TilePosition tilePosition;

  public VespeneGeyserMock(int id, int initialResources, final TilePosition tilePosition) {
    this.iD = id;
    this.initialResources = initialResources;
    this.tilePosition = tilePosition;
  }

  @Override
  public int getResources() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getInitialResources() {
    return this.initialResources;
  }

  @Override
  public int getLastKnownResources() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isBeingGathered() {
    throw new UnsupportedOperationException();
  }

  @Override
  public TilePosition getTilePosition() {
    return this.tilePosition;
  }

  @Override
  public TilePosition getInitialTilePosition() {
    return this.tilePosition;
  }

  @Override
  public Position getInitialPosition() {
    return BwemExt.centerOfBuilding(
        getInitialTilePosition(), UnitType.Resource_Mineral_Field.tileSize());
  }
}
