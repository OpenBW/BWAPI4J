package org.openbw.bwapi4j.ap;

public class ArrayValue {

  private final String amountBy;
  private final RValue rValue;

  public ArrayValue(String amountBy, RValue rValue) {
    this.amountBy = amountBy;
    this.rValue = rValue;
  }

  public RValue getRValue() {
    return rValue;
  }

  public String getAmountBy() {
    return amountBy;
  }
}
