package bwem.unit;

import org.openbw.bwapi4j.unit.Unit;

public class Mineral extends Resource {
  public Mineral(final Unit unit) {
    super(unit);
  }

  @Override
  public Mineral IsMineral() {
    return this;
  }
}
