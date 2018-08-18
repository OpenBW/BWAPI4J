// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.tile;

import static bwem.area.typedef.AreaId.UNINITIALIZED;

import bwem.area.typedef.AreaId;
import bwem.typedef.Altitude;

public class MiniTileImpl implements MiniTile {
  private static final AreaId blockingCP = new AreaId(Integer.MIN_VALUE);

  private Altitude
      altitude; // 0 for seas  ;  != 0 for terrain and lakes (-1 = not computed yet)  ;  1 =
  // SeaOrLake intermediate value
  private AreaId
      areaId; // 0 -> unwalkable  ;  > 0 -> index of some Area  ;  < 0 -> some walkable terrain, but
  // too small to be part of an Area

  public MiniTileImpl() {
    this.altitude = Altitude.UNINITIALIZED;
    this.areaId = UNINITIALIZED;
  }

  @Override
  public boolean isWalkable() {
    return (this.areaId.intValue() != 0);
  }

  @Override
  public Altitude getAltitude() {
    return this.altitude;
  }

  @Override
  public boolean isSea() {
    return (this.altitude.intValue() == 0);
  }

  @Override
  public boolean isLake() {
    return (this.altitude.intValue() != 0 && !isWalkable());
  }

  @Override
  public boolean isTerrain() {
    return isWalkable();
  }

  @Override
  public AreaId getAreaId() {
    return this.areaId;
  }

  public void setWalkable(boolean walkable) {
    this.areaId = new AreaId(walkable ? -1 : 0);
    this.altitude = new Altitude(walkable ? -1 : 1);
  }

  public boolean isSeaOrLake() {
    return (this.altitude.intValue() == 1);
  }

  public void setSea() {
    //        { bwem_assert(!Walkable() && SeaOrLake()); this.altitude = 0; }
    if (!(!isWalkable() && isSeaOrLake())) {
      throw new IllegalStateException();
    }
    this.altitude = Altitude.ZERO;
  }

  public void setLake() {
    //        { bwem_assert(!Walkable() && Sea()); this.altitude = -1; }
    if (!(!isWalkable() && isSea())) {
      throw new IllegalStateException();
    }
    this.altitude = Altitude.UNINITIALIZED;
  }

  public boolean isAltitudeMissing() {
    return (altitude.intValue() == -1);
  }

  public void setAltitude(final Altitude altitude) {
    //        { bwem_assert_debug_only(AltitudeMissing() && (a > 0)); this.altitude = a; }
    if (!(isAltitudeMissing() && altitude.intValue() > 0)) {
      throw new IllegalStateException();
    }
    this.altitude = altitude;
  }

  public boolean isAreaIdMissing() {
    return (this.areaId.intValue() == -1);
  }

  public void setAreaId(final AreaId areaId) {
    //        { bwem_assert(AreaIdMissing() && (id >= 1)); this.areaId = id; }
    if (!(isAreaIdMissing() && areaId.intValue() >= 1)) {
      throw new IllegalStateException();
    }
    this.areaId = areaId;
  }

  public void replaceAreaId(final AreaId areaId) {
    //        { bwem_assert( (areaId > 0) && ((id >= 1) || (id <= -2)) && (id != this.areaId));
    // this.areaId = id; }
    //        if (!( (areaId.intValue() > 0) && ((id.intValue() >= 1) || (id.intValue() <= -2)) &&
    // (!id.equals(areaId)))) {
    if (!(this.areaId.intValue() > 0)) {
      throw new IllegalStateException(
          "Failed assert: this.areaId.intValue() > 0: " + this.areaId.intValue());
    } else if (!((areaId.intValue() >= 1) || (areaId.intValue() <= -2))) {
      throw new IllegalArgumentException(
          "Failed assert: (id.intValue() >= 1) || (id.intValue() <= -2): " + areaId.intValue());
    } else if (areaId.equals(this.areaId)) {
      throw new IllegalArgumentException(
          "Failed assert: !id.equals (areaId): not expected: "
              + this.areaId.intValue()
              + ", actual: "
              + areaId.intValue());
    } else {
      this.areaId = areaId;
    }
  }

  public void setBlocked() {
    //        { bwem_assert(AreaIdMissing()); this.areaId = blockingCP; }
    if (!isAreaIdMissing()) {
      throw new IllegalStateException();
    }
    this.areaId = MiniTileImpl.blockingCP;
  }

  public boolean isBlocked() {
    return this.areaId.equals(MiniTileImpl.blockingCP);
  }

  public void replaceBlockedAreaId(final AreaId areaId) {
    //        { bwem_assert( (areaId == blockingCP) && (id >= 1)); this.areaId = id; }
    if (!(this.areaId.equals(MiniTileImpl.blockingCP) && areaId.intValue() >= 1)) {
      throw new IllegalStateException();
    }
    this.areaId = areaId;
  }
}
