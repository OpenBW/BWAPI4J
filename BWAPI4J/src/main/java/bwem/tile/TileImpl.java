package bwem.tile;

import bwem.unit.Neutral;

public class TileImpl implements Tile {
  private boolean buildable;
  private int groundHeight;
  private boolean doodad;
  private Neutral neutral = null;
  private int minAltitude;
  private int areaId = 0;

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

  ////////////////////////////////////////////////////////////////////////////
  //	Details: The functions below are used by the BWEM's internals

  public void SetBuildable() {
    buildable = true;
  }

  public void SetGroundHeight(final int height) {
    //    bwem_assert((0 <= h) && (h <= 2)); // TODO
    groundHeight = height;
  }

  public void SetDoodad() {
    doodad = true;
  }

  public void AddNeutral(final Neutral pNeutral) {
    //    bwem_assert(!neutral && pNeutral); // TODO
    neutral = pNeutral;
  }

  public void SetAreaId(final int id) {
    //    bwem_assert((id == -1) || !areaId && id); // TODO
    areaId = id;
  }

  public void ResetAreaId() {
    areaId = 0;
  }

  public void SetMinAltitude(final int a) {
    //    bwem_assert(a >= 0); // TODO
    minAltitude = a;
  }

  public void RemoveNeutral(Neutral pNeutral) {
    //    bwem_assert(pNeutral && (neutral == pNeutral)); // TODO
    //    utils::unused(pNeutral);
    neutral = null;
  }
}
