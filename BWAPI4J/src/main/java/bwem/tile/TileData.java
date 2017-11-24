package bwem.tile;

import bwem.tile.MiniTile;
import bwem.tile.Tile;

import java.util.List;

public interface TileData {

    // Provides access to the internal array of Tiles.
    public abstract List<Tile> getTiles();

    // Provides access to the internal array of MiniTiles.
    public abstract List<MiniTile> getMiniTiles();

}
