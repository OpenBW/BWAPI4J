package bwem.map;

import bwem.CheckMode;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface AdvancedDataInitializer {

    Tile getTile_(TilePosition tilePosition, CheckMode checkMode);

    Tile getTile_(TilePosition tilePosition);

    MiniTile getMiniTile_(WalkPosition walkPosition, CheckMode checkMode);

    MiniTile getMiniTile_(WalkPosition walkPosition);

    void markUnwalkableMiniTiles(BWMap bwMap);

    void markBuildableTilesAndGroundHeight(BWMap bwMap);

    void decideSeasOrLakes(int lakeMaxMiniTiles, int lakeMaxWidthInMiniTiles);

}
