package bwem.unit;

import bwapi.Unit;

public class Resource extends NeutralImpl {
  private final int initialAmount;

  protected Resource(final Unit unit) {
    super(unit);

    this.initialAmount = unit.getInitialResources();
  }

  @Override
  public Resource IsResource() {
    return this;
  }

  public int InitialAmount() {
    return initialAmount;
  }

  public int Amount() {
    return Unit().getResources();
  }
}
