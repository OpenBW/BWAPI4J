package bwem.tile;

public class MiniTileImpl implements MiniTile {
  private static final int blockingCP = Integer.MIN_VALUE;

  private final int altitude;
  private final int areaId;

  public MiniTileImpl(final int altitude, final int areaId) {
    this.altitude = altitude;
    this.areaId = areaId;
  }

  @Override
  public boolean Walkable() {
    return areaId != 0;
  }

  @Override
  public int Altitude() {
    return altitude;
  }

  @Override
  public boolean Sea() {
    return altitude == 0;
  }

  @Override
  public boolean Lake() {
    return altitude != 0 && !Walkable();
  }

  @Override
  public boolean Terrain() {
    return Walkable();
  }

  @Override
  public int AreaId() {
    return areaId;
  }
}
