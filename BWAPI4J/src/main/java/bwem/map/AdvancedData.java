package bwem.map;

import bwem.check_t;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileData;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface AdvancedData {

    public abstract MapData getMapData();

    public abstract TileData getTileData();

    // Returns a Tile, given its position.
    public abstract Tile getTile(TilePosition tilePosition, check_t checkMode);

    public abstract Tile getTile(TilePosition tilePosition);

    public abstract Tile getTile_(TilePosition tilePosition, check_t checkMode);

    public abstract Tile getTile_(TilePosition tilePosition);

    // Returns a MiniTile, given its position.
    public abstract MiniTile getMiniTile(WalkPosition walkPosition, check_t checkMode);

    public abstract MiniTile getMiniTile(WalkPosition walkPosition);

    public abstract MiniTile getMiniTile_(WalkPosition walkPosition, check_t checkMode);

    public abstract MiniTile getMiniTile_(WalkPosition walkPosition);

}
