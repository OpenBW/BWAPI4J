// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

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
