package org.openbw.bwapi4j.util.buffer;

import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.tile.Tile;
import bwem.tile.TileImpl;

public class BwemBufferData extends BwapiBufferData {
  public BwemBufferData(final int[] data) {
    super(data);
  }

  public Tile readTile() {
    final boolean isBuildable = readBoolean();
    final int groundHeight = readInt();
    final boolean isDoodad = readBoolean();
    final int minAltitude = readInt();
    final int areaId = readInt();
    final int neutralId = readInt();

    final TileImpl tile = new TileImpl();

    if (isBuildable) {
      tile.SetBuildable();
    }

    tile.SetGroundHeight(groundHeight);

    if (isDoodad) {
      tile.SetDoodad();
    }

    tile.SetMinAltitude(minAltitude);

    tile.SetAreaId(areaId);

    if (neutralId != -1) {
      // TODO: Set neutral object from ID.
    }

    return tile;
  }

  public MiniTile readMiniTile() {
    final int altitude = readInt();
    final int areaId = readInt();

    return new MiniTileImpl(altitude, areaId);
  }
}
