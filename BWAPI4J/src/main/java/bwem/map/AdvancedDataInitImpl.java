package bwem.map;

import bwem.check_t;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class AdvancedDataInitImpl extends AdvancedDataImpl implements AdvancedDataInit {

    public AdvancedDataInitImpl(final MapData mapData, final TileData tileData) {
        super(mapData, tileData);
    }

    ////////////////////////////////////////////////////////////////////////
    // MapImpl::LoadData
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void markUnwalkableMiniTiles(final BWMap bwMap) {
        // Mark unwalkable minitiles (minitiles are walkable by default)
        for (int y = 0; y < getMapData().getWalkSize().getY(); ++y)
        for (int x = 0; x < getMapData().getWalkSize().getX(); ++x) {
            if (!bwMap.isWalkable(x, y)) {
                // For each unwalkable minitile, we also mark its 8 neighbours as not walkable.
                // According to some tests, this prevents from wrongly pretending one Marine can go by some thin path.
                for (int dy = -1; dy <= +1; ++dy)
                for (int dx = -1; dx <= +1; ++dx) {
                    final WalkPosition w = new WalkPosition(x + dx, y + dy);
                    if (getMapData().isValid(w)) {
                        getMiniTile_(w, check_t.no_check).SetWalkable(false);
                    }
                }
            }
        }
    }

    @Override
    public void markBuildableTilesAndGroundHeight(final BWMap bwMap) {
        // Mark buildable tiles (tiles are unbuildable by default)
        for (int y = 0; y < getMapData().getTileSize().getY(); ++y)
        for (int x = 0; x < getMapData().getTileSize().getX(); ++x) {
            final TilePosition t = new TilePosition(x, y);
            if (bwMap.isBuildable(t, false)) {
                getTile_(t).SetBuildable();

                // Ensures buildable ==> walkable:
                for (int dy = 0; dy < 4; ++dy)
                for (int dx = 0; dx < 4; ++dx) {
                    getMiniTile_((t.toPosition().toWalkPosition()).add(new WalkPosition(dx, dy)), check_t.no_check).SetWalkable(true);
                }
            }

            // Add groundHeight and doodad information:
            final int bwapiGroundHeight = bwMap.getGroundHeight(t);
            getTile_(t).SetGroundHeight(bwapiGroundHeight / 2);
            if (bwapiGroundHeight % 2 != 0) {
                getTile_(t).SetDoodad();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////

}
