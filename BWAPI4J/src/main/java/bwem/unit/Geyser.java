package bwem.unit;

import org.openbw.bwapi4j.unit.Unit;

public class Geyser extends Resource {
  public Geyser(final Unit unit) {
    super(unit);
  }

  @Override
  public Geyser IsGeyser() {
    return this;
  }
}
