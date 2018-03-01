package bwem.map;

import bwem.CheckMode;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileData;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface AdvancedData {

    MapData getMapData();

    TileData getTileData();

    Tile getTile(TilePosition tilePosition, CheckMode checkMode);

    Tile getTile(TilePosition tilePosition);

    MiniTile getMiniTile(WalkPosition walkPosition, CheckMode checkMode);

    MiniTile getMiniTile(WalkPosition walkPosition);

    boolean isSeaWithNonSeaNeighbors(WalkPosition walkPosition);

}
