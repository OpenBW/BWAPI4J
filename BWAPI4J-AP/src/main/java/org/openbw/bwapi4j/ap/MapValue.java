package org.openbw.bwapi4j.ap;

public class MapValue {

  private final RValue index;
  private final RValue rValue;

  public MapValue(RValue index, RValue rValue) {
    this.index = index;
    this.rValue = rValue;
  }

  public RValue getIndex() {
    return index;
  }

  public RValue getRValue() {
    return rValue;
  }
}
