package bwem.map;

import bwem.tile.MiniTile;
import bwem.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class TileDataImpl implements TileData {

    private final List<Tile> tiles;
    private final List<MiniTile> miniTiles;

    public TileDataImpl(final int tileCount, final int miniTileCount) {
        this.tiles = new ArrayList<>(tileCount);
        for (int i = 0; i < tileCount; ++i) {
            this.tiles.add(new Tile());
        }

        this.miniTiles = new ArrayList<>(miniTileCount);
        for (int i = 0; i < miniTileCount; ++i) {
            this.miniTiles.add(new MiniTile());
        }
    }

    @Override
    public List<Tile> getTiles() {
        return this.tiles;
    }

    @Override
    public List<MiniTile> getMiniTiles() {
        return this.miniTiles;
    }

}
