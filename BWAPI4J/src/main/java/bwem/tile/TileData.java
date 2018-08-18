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

import java.util.List;

public interface TileData {
  /** Provides access to the internal array of Tiles. */
  List<Tile> getTiles();

  /** Provides access to the internal array of miniTiles. */
  List<MiniTile> getMiniTiles();
}
