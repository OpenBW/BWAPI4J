package bwapi;

public enum Flag {
  CompleteMapInformation(0),
  UserInput(1);

  private final int value;

  Flag(final int value) {
    this.value = value;
  }

  public int intValue() {
    return value;
  }
}
