package org.openbw.bwapi4j.util.buffer;

public class DataBuffer {
  private int index;
  private final int[] data;

  public DataBuffer(final int[] data) {
    this.index = 0;
    this.data = data;
  }

  public int size() {
    return data == null ? 0 : data.length;
  }

  protected int readIntAndIncrementIndex() {
    return data[index++];
  }

  public int readInt() {
    return readIntAndIncrementIndex();
  }

  public boolean readBoolean() {
    return readInt() == 1;
  }
}
