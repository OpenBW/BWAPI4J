package bwem.map;

import bwem.CheckMode;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public interface AdvancedDataInitializer {

    public abstract Tile getTile_(final TilePosition tilePosition, final CheckMode checkMode);

    public abstract Tile getTile_(final TilePosition p);

    public abstract MiniTile getMiniTile_(final WalkPosition walkPosition, final CheckMode checkMode);

    public abstract MiniTile getMiniTile_(final WalkPosition walkPosition);

    public abstract void markUnwalkableMiniTiles(BWMap bwMap);

    public abstract void markBuildableTilesAndGroundHeight(BWMap bwMap);

    public abstract void decideSeasOrLakes(int lakeMaxMiniTiles, int lakeMaxWidthInMiniTiles);

}
