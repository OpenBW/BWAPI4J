package bwem.tile;

public class MiniTileImpl implements MiniTile {
  private static final int blockingCP = Integer.MIN_VALUE;

  private int altitude = -1;
  private int areaId = -1;

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

  ////////////////////////////////////////////////////////////////////////////
  //	Details: The functions below are used by the BWEM's internals

  public void SetWalkable(final boolean walkable) {
    areaId = (walkable ? -1 : 0);
    altitude = (walkable ? -1 : 1);
  }

  public boolean SeaOrLake() {
    return altitude == 1;
  }

  public void SetSea() {
    //    bwem_assert(!Walkable() && SeaOrLake()); // TODO
    altitude = 0;
  }

  public void SetLake() {
    //    bwem_assert(!Walkable() && Sea()); // TODO
    altitude = -1;
  }

  public boolean AltitudeMissing() {
    return altitude == -1;
  }

  public void SetAltitude(final int a) {
    //    bwem_assert_debug_only(AltitudeMissing() && (a > 0)); // TODO
    altitude = a;
  }

  public boolean AreaIdMissing() {
    return areaId == -1;
  }

  public void SetAreaId(final int id) {
    //    bwem_assert(AreaIdMissing() && (id >= 1)); // TODO
    areaId = id;
  }

  public void ReplaceAreaId(final int id) {
    //    bwem_assert((areaId > 0) && ((id >= 1) || (id <= -2)) && (id != areaId)); // TODO
    areaId = id;
  }

  public void SetBlocked() {
    //    bwem_assert(AreaIdMissing()); // TODO
    areaId = blockingCP;
  }

  public boolean Blocked() {
    return areaId == blockingCP;
  }

  public void ReplaceBlockedAreaId(final int id) {
    //    bwem_assert((areaId == blockingCP) && (id >= 1)); // TODO
    areaId = id;
  }
}
