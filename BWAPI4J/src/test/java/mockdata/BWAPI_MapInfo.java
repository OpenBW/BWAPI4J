package mockdata;

import java.io.IOException;
import java.util.List;
import org.openbw.bwapi4j.TilePosition;

/** Class for parsing map info from a dummy data set. */
public class BWAPI_MapInfo {
  private final String mapHash;
  private final TilePosition mapSize;
  private final String mapName;
  private final String mapFilename;

  public BWAPI_MapInfo(final String mapHash, final String dataSetBwapiVersion) throws IOException {
    this.mapHash = mapHash;

    final List<List<String>> mapInfo =
        DummyDataUtils.readMultiLinesAsStringTokensFromArchiveFile(
            DummyDataUtils.compileBwapiDataSetArchiveFilename("MapInfo", dataSetBwapiVersion),
            mapHash,
            " ");

    final StringBuilder mapFilename = new StringBuilder();
    for (final String token : mapInfo.get(1)) {
      mapFilename.append(token);
    }
    this.mapFilename = mapFilename.toString();

    final StringBuilder mapName = new StringBuilder();
    for (final String token : mapInfo.get(2)) {
      mapName.append(token);
    }
    this.mapName = mapName.toString();

    final int x = Integer.parseInt(mapInfo.get(3).get(0));
    final int y = Integer.parseInt(mapInfo.get(3).get(1));
    this.mapSize = new TilePosition(x, y);
  }

  public String getMapHash() {
    return this.mapHash;
  }

  public int getMapTileWidth() {
    return this.mapSize.getX();
  }

  public int getMapTileHeight() {
    return this.mapSize.getY();
  }

  public String getMapDisplayName() {
    return this.mapName;
  }

  public String getMapFilename() {
    return this.mapFilename;
  }
}
