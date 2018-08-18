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

package bwem;

import bwem.area.Area;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Resource;
import bwem.util.BwemExt;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public class BaseImpl implements Base {
  private final Area area;
  private TilePosition location;
  private Position center;
  private final List<Mineral> minerals = new ArrayList<>();
  private final List<Geyser> geysers = new ArrayList<>();
  private final List<Mineral> blockingMinerals;
  private boolean isStartingLocation = false;

  public BaseImpl(
      final Area area,
      final TilePosition location,
      final List<Resource> assignedResources,
      final List<Mineral> blockingMinerals) {
    this.area = area;
    this.location = location;
    this.center = BwemExt.centerOfBuilding(location, UnitType.Terran_Command_Center.tileSize());
    this.blockingMinerals = blockingMinerals;

    //        bwem_assert(!AssignedResources.empty());
    if (assignedResources.isEmpty()) {
      throw new IllegalArgumentException();
    }

    for (final Resource assignedResource : assignedResources) {
      if (assignedResource instanceof Mineral) {
        final Mineral assignedMineral = (Mineral) assignedResource;
        this.minerals.add(assignedMineral);
      } else if (assignedResource instanceof Geyser) {
        final Geyser assignedGeyser = (Geyser) assignedResource;
        this.geysers.add(assignedGeyser);
      }
    }
  }

  @Override
  public boolean isStartingLocation() {
    return this.isStartingLocation;
  }

  @Override
  public Area getArea() {
    return this.area;
  }

  @Override
  public TilePosition getLocation() {
    return this.location;
  }

  @Override
  public Position getCenter() {
    return this.center;
  }

  @Override
  public List<Mineral> getMinerals() {
    return this.minerals;
  }

  @Override
  public List<Geyser> getGeysers() {
    return this.geysers;
  }

  @Override
  public List<Mineral> getBlockingMinerals() {
    return this.blockingMinerals;
  }

  public void assignStartingLocation(final TilePosition actualLocation) {
    this.isStartingLocation = true;
    this.location = actualLocation;
    this.center =
        BwemExt.centerOfBuilding(actualLocation, UnitType.Terran_Command_Center.tileSize());
  }

  public void onMineralDestroyed(final Mineral mineral) {
    //    	bwem_assert(pMineral);
    if (mineral == null) {
      throw new IllegalArgumentException();
    }

    this.minerals.remove(mineral);
    this.blockingMinerals.remove(mineral);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    } else if (!(object instanceof Base)) {
      return false;
    } else {
      final Base that = (Base) object;
      return (getArea().equals(that.getArea())
          && getLocation().equals(that.getLocation())
          && getCenter().equals(that.getCenter()));
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.area, this.location, this.center);
  }
}
