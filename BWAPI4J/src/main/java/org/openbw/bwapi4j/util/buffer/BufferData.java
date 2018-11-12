package org.openbw.bwapi4j.util.buffer;

public class BufferData {
  private int index;
  private final int[] data;

  public BufferData(final int[] data) {
    this.index = 0;
    this.data = data;
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
