package org.openbw.bwapi4j.util.buffer;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class BwapiDataBuffer {
  public static TilePosition readTilePosition(final DataBuffer data) {
    final int x = data.readInt();
    final int y = data.readInt();
    return new TilePosition(x, y);
  }

  public static WalkPosition readWalkPosition(final DataBuffer data) {
    final int x = data.readInt();
    final int y = data.readInt();
    return new WalkPosition(x, y);
  }

  public static Position readPosition(final DataBuffer data) {
    final int x = data.readInt();
    final int y = data.readInt();
    return new Position(x, y);
  }
}
