package bwem.unit;

import bwapi.Unit;

public class Geyser extends Resource {
  public Geyser(final Unit unit) {
    super(unit);
  }

  @Override
  public Geyser IsGeyser() {
    return this;
  }
}
