package bwem.unit;

import org.openbw.bwapi4j.unit.Unit;

public class Resource extends Neutral {
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
