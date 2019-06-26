package bwapi;

import java.util.List;

public class Region {
  public int getID() {
    throw new UnsupportedOperationException("TODO");
  }

  public int getRegionGroupID() {
    throw new UnsupportedOperationException("TODO");
  }

  public Position getCenter() {
    throw new UnsupportedOperationException("TODO");
  }

  public boolean isHigherGround() {
    throw new UnsupportedOperationException("TODO");
  }

  public int getDefensePriority() {
    throw new UnsupportedOperationException("TODO");
  }

  public boolean isAccessible() {
    throw new UnsupportedOperationException("TODO");
  }

  public List<Region> getNeighbors() {
    throw new UnsupportedOperationException("TODO");
  }

  public int getBoundsLeft() {
    throw new UnsupportedOperationException("TODO");
  }

  public int getBoundsTop() {
    throw new UnsupportedOperationException("TODO");
  }

  public int getBoundsRight() {
    throw new UnsupportedOperationException("TODO");
  }

  public int getBoundsBottom() {
    throw new UnsupportedOperationException("TODO");
  }

  public Region getClosestAccessibleRegion() {
    throw new UnsupportedOperationException("TODO");
  }

  public int getDistance(Region other) {
    throw new UnsupportedOperationException("TODO");
  }

  public List<Unit> getUnits() {
    throw new UnsupportedOperationException("TODO");
  }

  @Override
  public boolean equals(final Object object) {
    if (object instanceof Region) {
      final Region that = (Region) object;
      return this.getID() == that.getID();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return getID();
  }
}
