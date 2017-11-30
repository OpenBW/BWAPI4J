package bwem.map;

import bwem.check_t;
import bwem.tile.MiniTile;
import bwem.tile.TileData;
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
                    getMiniTile_(t.toWalkPosition().add(new WalkPosition(dx, dy)), check_t.no_check).SetWalkable(true);
                }
            }

            // Add groundHeight and doodad information:
            final int bwapiGroundHeight = bwMap.getGroundHeight(t);
            getTile_(t).setGroundHeight(bwapiGroundHeight / 2);
            if (bwapiGroundHeight % 2 != 0) {
                getTile_(t).SetDoodad();
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
            final WalkPosition origin = new WalkPosition(x, y);
            final MiniTile Origin = getMiniTile_(origin, check_t.no_check);
            if (Origin.SeaOrLake()) {
                final List<WalkPosition> ToSearch = new ArrayList<>();
                ToSearch.add(origin);
                final List<MiniTile> SeaExtent = new ArrayList<>();
                Origin.SetSea();
                SeaExtent.add(Origin);
                WalkPosition topLeft = origin;
                WalkPosition bottomRight = origin;
                while (!ToSearch.isEmpty()) {
                    final WalkPosition current = ToSearch.get(ToSearch.size() - 1);
                    if (current.getX() < topLeft.getX()) topLeft = new WalkPosition(current.getX(), topLeft.getY());
                    if (current.getY() < topLeft.getY()) topLeft = new WalkPosition(topLeft.getX(), current.getY());
                    if (current.getX() > bottomRight.getX()) bottomRight = new WalkPosition(current.getX(), bottomRight.getY());
                    if (current.getY() > bottomRight.getY()) bottomRight = new WalkPosition(bottomRight.getX(), current.getY());

                    ToSearch.remove(ToSearch.size() - 1);
                    final WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                    for (final WalkPosition delta : deltas) {
                        final WalkPosition next = current.add(delta);
                        if (getMapData().isValid(next)) {
                            final MiniTile Next = getMiniTile_(next, check_t.no_check);
                            if (Next.SeaOrLake()) {
                                ToSearch.add(next);
                                if (SeaExtent.size() <= lake_max_miniTiles) SeaExtent.add(Next);
                                Next.SetSea();
                            }
                        }
                    }
                }

                if ((SeaExtent.size() <= lake_max_miniTiles) &&
                        (bottomRight.getX() - topLeft.getX() <= lake_max_width_in_miniTiles) &&
                        (bottomRight.getY() - topLeft.getY() <= lake_max_width_in_miniTiles) &&
                        (topLeft.getX() >= 2) && (topLeft.getY() >= 2) && (bottomRight.getX() < getMapData().getWalkSize().getX() - 2) && (bottomRight.getY() < getMapData().getWalkSize().getY() - 2)) {
                    for (final MiniTile pSea : SeaExtent) {
                        pSea.SetLake();
                    }
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////

}
