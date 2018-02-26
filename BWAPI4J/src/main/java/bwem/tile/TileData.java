package bwem.tile;

import java.util.List;

public interface TileData {

    // Provides access to the internal array of Tiles.
    public abstract List<Tile> getTiles();

    // Provides access to the internal array of miniTiles.
    public abstract List<MiniTile> getMiniTiles();

}
