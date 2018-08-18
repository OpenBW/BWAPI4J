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
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface TerrainDataInitializer {

  Tile getTile_(TilePosition tilePosition, CheckMode checkMode);

  Tile getTile_(TilePosition tilePosition);

  MiniTile getMiniTile_(WalkPosition walkPosition, CheckMode checkMode);

  MiniTile getMiniTile_(WalkPosition walkPosition);

  void markUnwalkableMiniTiles(BWMap bwMap);

  void markBuildableTilesAndGroundHeight(BWMap bwMap);

  void decideSeasOrLakes(int lakeMaxMiniTiles, int lakeMaxWidthInMiniTiles);
}
