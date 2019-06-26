package bwapi;

public class DefaultUnitFactory implements UnitFactory {
  private final BW bw;

  public DefaultUnitFactory(final BW bw) {
    this.bw = bw;
  }

  @Override
  public Unit createUnit(final int unitId, final UnitType unitType, final int timeSpotted) {
    final Unit unit = new Unit(bw, unitId, unitType, timeSpotted);
    return unit;
  }
}
