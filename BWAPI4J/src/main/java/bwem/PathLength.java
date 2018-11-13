package bwem;

public class PathLength {
  private int value;

  public PathLength(final int value) {
    setValue(value);
  }

  public PathLength() {
    this(0);
  }

  public int getValue() {
    return value;
  }

  public void setValue(final int value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object object) {
    if (object instanceof PathLength) {
      final PathLength that = (PathLength) object;
      return this.getValue() == that.getValue();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return getValue();
  }
}
