package bwem.unit;

import org.openbw.bwapi4j.unit.Unit;

public class StaticBuilding extends NeutralImpl {
  public StaticBuilding(final Unit unit) {
    super(unit);
  }

  @Override
  public StaticBuilding IsStaticBuilding() {
    return this;
  }
}
