package bwem.unit;

import bwapi.Unit;

public class Mineral extends Resource {
  public Mineral(final Unit unit) {
    super(unit);
  }

  @Override
  public Mineral IsMineral() {
    return this;
  }
}
