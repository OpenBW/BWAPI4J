package bwem.map;

import bwem.check_t;
import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.tile.TileData;
import bwem.tile.TileImpl;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.ArrayList;
import java.util.List;

public class AdvancedDataInitializerImpl extends AdvancedDataImpl implements AdvancedDataInitializer {

    public AdvancedDataInitializerImpl(final MapData mapData, final TileData tileData) {
        super(mapData, tileData);
    }

    ////////////////////////////////////////////////////////////////////////
    // MapImpl::LoadData
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void markUnwalkableMiniTiles(final BWMap bwMap) {
        // Mark unwalkable minitiles (minitiles are walkable by default).
        for (int y = 0; y < getMapData().getWalkSize().getY(); ++y)
        for (int x = 0; x < getMapData().getWalkSize().getX(); ++x) {
            if (!bwMap.isWalkable(x, y)) {
                // For each unwalkable minitile, we also mark its 8 neighbors as not walkable.
                // According to some tests, this prevents from wrongly pretending one marine can go by some thin path.
                for (int dy = -1; dy <= 1; ++dy)
                for (int dx = -1; dx <= 1; ++dx) {
                    final WalkPosition walkPosition = new WalkPosition(x + dx, y + dy);
                    if (getMapData().isValid(walkPosition)) {
                        ((MiniTileImpl) getMiniTile_(walkPosition, check_t.no_check)).setWalkable(false);
                    }
                }
            }
        }
    }

    @Override
    public void markBuildableTilesAndGroundHeight(final BWMap bwMap) {
        // Mark buildable tiles (tiles are unbuildable by default).
        for (int y = 0; y < getMapData().getTileSize().getY(); ++y)
        for (int x = 0; x < getMapData().getTileSize().getX(); ++x) {
            final TilePosition tilePosition = new TilePosition(x, y);
            final WalkPosition walkPosition = tilePosition.toWalkPosition();
            final TileImpl tile = (TileImpl) getTile_(tilePosition);

            if (bwMap.isBuildable(tilePosition, false)) {
                tile.setBuildable();

                // Ensures buildable ==> walkable.
                for (int dy = 0; dy < 4; ++dy)
                for (int dx = 0; dx < 4; ++dx) {
                    ((MiniTileImpl) getMiniTile_(walkPosition.add(new WalkPosition(dx, dy)), check_t.no_check)).setWalkable(true);
                }
            }

            // Add ground height and doodad information.
            final int bwapiGroundHeight = bwMap.getGroundHeight(tilePosition);
            tile.setGroundHeight(bwapiGroundHeight / 2);
            if (bwapiGroundHeight % 2 != 0) {
                tile.setDoodad();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::DecideSeasOrLakes
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void decideSeasOrLakes(final int lake_max_miniTiles, final int lake_max_width_in_miniTiles) {
        for (int y = 0; y < getMapData().getWalkSize().getY(); ++y)
        for (int x = 0; x < getMapData().getWalkSize().getX(); ++x) {
            final WalkPosition originWalkPosition = new WalkPosition(x, y);
            final MiniTile originMiniTile = getMiniTile_(originWalkPosition, check_t.no_check);

            if (((MiniTileImpl) originMiniTile).isSeaOrLake()) {
                final List<WalkPosition> toSearch = new ArrayList<>();
                toSearch.add(originWalkPosition);

                final List<MiniTile> seaExtent = new ArrayList<>();
                ((MiniTileImpl) originMiniTile).setSea();
                seaExtent.add(originMiniTile);

                int topLeft_x = originWalkPosition.getX();
                int topLeft_y = originWalkPosition.getY();
                int bottomRight_x = originWalkPosition.getX();
                int bottomRight_y = originWalkPosition.getY();

                while (!toSearch.isEmpty()) {
                    final WalkPosition current = toSearch.remove(toSearch.size() - 1);
                    if (current.getX() < topLeft_x) topLeft_x = current.getX();
                    if (current.getY() < topLeft_y) topLeft_y = current.getY();
                    if (current.getX() > bottomRight_x) bottomRight_x = current.getX();
                    if (current.getY() > bottomRight_y) bottomRight_y = current.getY();

                    final WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
                    for (final WalkPosition delta : deltas) {
                        final WalkPosition nextWalkPosition = current.add(delta);
                        if (getMapData().isValid(nextWalkPosition)) {
                            final MiniTile nextMiniTile = getMiniTile_(nextWalkPosition, check_t.no_check);
                            if (((MiniTileImpl) nextMiniTile).isSeaOrLake()) {
                                toSearch.add(nextWalkPosition);
                                if (seaExtent.size() <= lake_max_miniTiles) {
                                    seaExtent.add(nextMiniTile);
                                }
                                ((MiniTileImpl) nextMiniTile).setSea();
                            }
                        }
                    }
                }

                if ((seaExtent.size() <= lake_max_miniTiles) &&
                        (bottomRight_x - topLeft_x <= lake_max_width_in_miniTiles) &&
                        (bottomRight_y - topLeft_y <= lake_max_width_in_miniTiles) &&
                        (topLeft_x >= 2) && (topLeft_y >= 2) && (bottomRight_x < getMapData().getWalkSize().getX() - 2) && (bottomRight_y < getMapData().getWalkSize().getY() - 2)) {
                    for (final MiniTile miniTile : seaExtent) {
                        ((MiniTileImpl) miniTile).setLake();
                    }
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////

}
