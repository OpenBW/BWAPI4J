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

    // Returns a Tile, given its position.
    Tile getTile(TilePosition tilePosition, CheckMode checkMode);

    Tile getTile(TilePosition tilePosition);

    Tile getTile_(TilePosition tilePosition, CheckMode checkMode);

    Tile getTile_(TilePosition tilePosition);

    // Returns a MiniTile, given its position.
    MiniTile getMiniTile(WalkPosition walkPosition, CheckMode checkMode);

    MiniTile getMiniTile(WalkPosition walkPosition);

    MiniTile getMiniTile_(WalkPosition walkPosition, CheckMode checkMode);

    MiniTile getMiniTile_(WalkPosition walkPosition);

    // map.cpp:29:seaSide
    boolean isSeaWithNonSeaNeighbors(WalkPosition walkPosition);

}
