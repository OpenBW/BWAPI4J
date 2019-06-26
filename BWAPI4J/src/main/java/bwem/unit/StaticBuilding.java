package bwem.unit;

import bwapi.Unit;

public class StaticBuilding extends NeutralImpl {
  public StaticBuilding(final Unit unit) {
    super(unit);
  }

  @Override
  public StaticBuilding IsStaticBuilding() {
    return this;
  }
}
