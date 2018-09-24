package org.openbw.bwapi4j.ap;

public class ArrayValue {

  private final String amountBy;
  private final RValue rValue;
  private final String componentType;

  public ArrayValue(String amountBy, RValue rValue, String componentType) {
    this.amountBy = amountBy;
    this.rValue = rValue;
    this.componentType = componentType;
  }

  public RValue getRValue() {
    return rValue;
  }

  public String getAmountBy() {
    return amountBy;
  }

  public String getComponentType() {
    return componentType;
  }
}
