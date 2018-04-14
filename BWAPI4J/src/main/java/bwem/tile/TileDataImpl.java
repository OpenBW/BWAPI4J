////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published 
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package bwem.tile;

import java.util.ArrayList;
import java.util.List;

public class TileDataImpl implements TileData {

    private final List<Tile> tiles;
    private final List<MiniTile> miniTiles;

    public TileDataImpl(final int tileCount, final int miniTileCount) {
        this.tiles = new ArrayList<>(tileCount);
        for (int i = 0; i < tileCount; ++i) {
            this.tiles.add(new TileImpl());
        }

        this.miniTiles = new ArrayList<>(miniTileCount);
        for (int i = 0; i < miniTileCount; ++i) {
            this.miniTiles.add(new MiniTileImpl());
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
