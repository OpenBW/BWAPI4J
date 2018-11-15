package bwem.tile;

import bwem.unit.Neutral;

public class TileImpl implements Tile {
  public boolean buildable = false;
  public int groundHeight = 0;
  public boolean doodad = false;
  public Neutral neutral = null;
  public int minAltitude = Integer.MAX_VALUE;
  public int areaId = 0;

  @Override
  public boolean Buildable() {
    return buildable;
  }

  @Override
  public int AreaId() {
    return areaId;
  }

  @Override
  public int MinAltitude() {
    return minAltitude;
  }

  @Override
  public boolean Walkable() {
    return areaId != 0;
  }

  @Override
  public boolean Terrain() {
    return Walkable();
  }

  @Override
  public int GroundHeight() {
    return groundHeight;
  }

  @Override
  public boolean Doodad() {
    return doodad;
  }

  @Override
  public Neutral GetNetural() {
    return neutral;
  }

  @Override
  public int StackedNeutrals() {
    int count = 0;

    Neutral stacked = GetNetural();
    while (stacked != null) {
      ++count;
      stacked = stacked.NextStacked();
    }

    return count;
  }
}
