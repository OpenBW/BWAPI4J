package org.openbw.bwapi4j.util.buffer;

public class DataBuffer {
  private int index;
  private final int[] data;
  private final int dataSize;

  public DataBuffer(final int[] data) {
    this.index = 0;
    this.data = data == null ? new int[0] : data;
    this.dataSize = this.data.length;
  }

  public int size() {
    return dataSize;
  }

  public boolean hasNext() {
    return index < dataSize;
  }

  public int readInt() {
    return data[index++];
  }

  public boolean readBoolean() {
    return readInt() == 1;
  }
}
