package bwem.util.buffer;

import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import org.openbw.bwapi4j.util.buffer.DataBuffer;

public class BwemDataBuffer {
  public static Tile readTile(final DataBuffer data) {
    final boolean isBuildable = data.readBoolean();
    final int groundHeight = data.readInt();
    final boolean isDoodad = data.readBoolean();
    final int minAltitude = data.readInt();
    final int areaId = data.readInt();
    final int neutralId = data.readInt();

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

  public static MiniTile readMiniTile(final DataBuffer data) {
    final int altitude = data.readInt();
    final int areaId = data.readInt();

    return new MiniTileImpl(altitude, areaId);
  }
}
