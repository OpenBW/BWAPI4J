package org.openbw.bwapi4j;

public enum Flag {
  CompleteMapInformation(0),
  UserInput(1);

  private int value;

  Flag(final int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
