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

package bwem.map;

import bwem.CheckMode;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileData;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface TerrainData {

  MapData getMapData();

  TileData getTileData();

  Tile getTile(TilePosition tilePosition, CheckMode checkMode);

  Tile getTile(TilePosition tilePosition);

  MiniTile getMiniTile(WalkPosition walkPosition, CheckMode checkMode);

  MiniTile getMiniTile(WalkPosition walkPosition);

  boolean isSeaWithNonSeaNeighbors(WalkPosition walkPosition);
}
