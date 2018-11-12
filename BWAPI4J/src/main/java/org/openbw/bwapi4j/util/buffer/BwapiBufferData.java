package org.openbw.bwapi4j.util.buffer;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class BwapiBufferData extends BufferData {
  public BwapiBufferData(final int[] data) {
    super(data);
  }

  public TilePosition readTilePosition() {
    final int x = readInt();
    final int y = readInt();
    return new TilePosition(x, y);
  }

  public WalkPosition readWalkPosition() {
    final int x = readInt();
    final int y = readInt();
    return new WalkPosition(x, y);
  }

  public Position readPosition() {
    final int x = readInt();
    final int y = readInt();
    return new Position(x, y);
  }
}
