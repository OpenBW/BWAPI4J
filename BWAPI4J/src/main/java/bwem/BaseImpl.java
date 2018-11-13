package bwem;

import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Resource;
import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;

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
    setLocation(location);
    this.blockingMinerals = blockingMinerals;

    for (final Resource resource : assignedResources) {
      if (resource instanceof Mineral) {
        final Mineral mineral = (Mineral) resource;
        minerals.add(mineral);
      } else if (resource instanceof Geyser) {
        final Geyser geyser = (Geyser) resource;
        geysers.add(geyser);
      }
    }
  }

  @Override
  public boolean Starting() {
    return isStartingLocation;
  }

  @Override
  public Area GetArea() {
    return area;
  }

  @Override
  public TilePosition Location() {
    return location;
  }

  @Override
  public Position Center() {
    return center;
  }

  @Override
  public List<Mineral> Minerals() {
    return minerals;
  }

  @Override
  public List<Geyser> Geysers() {
    return geysers;
  }

  @Override
  public List<Mineral> BlockingMinerals() {
    return blockingMinerals;
  }

  public void SetStartingLocation(final TilePosition actualLocation) {
    isStartingLocation = true;
    setLocation(actualLocation);
  }

  private void setLocation(final TilePosition location) {
    this.location = location;
    this.center = location.toPosition().add(RESOURCE_DEPOT_CENTER_OFFSET_IN_PIXELS);
  }
}
