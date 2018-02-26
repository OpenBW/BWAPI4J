package bwem.map;

import bwem.CheckMode;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileData;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface AdvancedData {

    public abstract MapData getMapData();

    public abstract TileData getTileData();

    public abstract Tile getTile(TilePosition tilePosition, CheckMode checkMode);

    public abstract Tile getTile(TilePosition tilePosition);

    public abstract MiniTile getMiniTile(WalkPosition walkPosition, CheckMode checkMode);

    public abstract MiniTile getMiniTile(WalkPosition walkPosition);

    // map.cpp:29:seaSide
    public abstract boolean isSeaWithNonSeaNeighbors(WalkPosition walkPosition);

}
