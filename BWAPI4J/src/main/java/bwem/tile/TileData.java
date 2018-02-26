package bwem.tile;

import java.util.List;

public interface TileData {

    // Provides access to the internal array of Tiles.
    List<Tile> getTiles();

    // Provides access to the internal array of miniTiles.
    List<MiniTile> getMiniTiles();

}
