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
import bwem.tile.MiniTileImpl;
import bwem.tile.Tile;
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

    @Override
    public Tile getTile_(final TilePosition tilePosition, final CheckMode checkMode) {
        return getTile(tilePosition, checkMode);
    }

    @Override
    public Tile getTile_(final TilePosition tilePosition) {
        return getTile_(tilePosition, CheckMode.CHECK);
    }

    @Override
    public MiniTile getMiniTile_(final WalkPosition walkPosition, final CheckMode checkMode) {
        return getMiniTile(walkPosition, checkMode);
    }

    @Override
    public MiniTile getMiniTile_(final WalkPosition walkPosition) {
        return getMiniTile_(walkPosition, CheckMode.CHECK);
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
                        ((MiniTileImpl) getMiniTile_(walkPosition, CheckMode.NO_CHECK)).setWalkable(false);
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
                    ((MiniTileImpl) getMiniTile_(walkPosition.add(new WalkPosition(dx, dy)), CheckMode.NO_CHECK)).setWalkable(true);
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
    public void decideSeasOrLakes(final int lakeMaxMiniTiles, final int lakeMaxWidthInMiniTiles) {
        for (int y = 0; y < getMapData().getWalkSize().getY(); ++y)
        for (int x = 0; x < getMapData().getWalkSize().getX(); ++x) {
            final WalkPosition originWalkPosition = new WalkPosition(x, y);
            final MiniTile originMiniTile = getMiniTile_(originWalkPosition, CheckMode.NO_CHECK);

            if (((MiniTileImpl) originMiniTile).isSeaOrLake()) {
                final List<WalkPosition> toSearch = new ArrayList<>();
                toSearch.add(originWalkPosition);

                final List<MiniTile> seaExtent = new ArrayList<>();
                ((MiniTileImpl) originMiniTile).setSea();
                seaExtent.add(originMiniTile);

                int topLeftX = originWalkPosition.getX();
                int topLeftY = originWalkPosition.getY();
                int bottomRightX = originWalkPosition.getX();
                int bottomRightY = originWalkPosition.getY();

                while (!toSearch.isEmpty()) {
                    final WalkPosition current = toSearch.remove(toSearch.size() - 1);
                    if (current.getX() < topLeftX) topLeftX = current.getX();
                    if (current.getY() < topLeftY) topLeftY = current.getY();
                    if (current.getX() > bottomRightX) bottomRightX = current.getX();
                    if (current.getY() > bottomRightY) bottomRightY = current.getY();

                    final WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
                    for (final WalkPosition delta : deltas) {
                        final WalkPosition nextWalkPosition = current.add(delta);
                        if (getMapData().isValid(nextWalkPosition)) {
                            final MiniTile nextMiniTile = getMiniTile_(nextWalkPosition, CheckMode.NO_CHECK);
                            if (((MiniTileImpl) nextMiniTile).isSeaOrLake()) {
                                toSearch.add(nextWalkPosition);
                                if (seaExtent.size() <= lakeMaxMiniTiles) {
                                    seaExtent.add(nextMiniTile);
                                }
                                ((MiniTileImpl) nextMiniTile).setSea();
                            }
                        }
                    }
                }

                if ((seaExtent.size() <= lakeMaxMiniTiles) &&
                        (bottomRightX - topLeftX <= lakeMaxWidthInMiniTiles) &&
                        (bottomRightY - topLeftY <= lakeMaxWidthInMiniTiles) &&
                        (topLeftX >= 2) && (topLeftY >= 2) && (bottomRightX < getMapData().getWalkSize().getX() - 2) && (bottomRightY < getMapData().getWalkSize().getY() - 2)) {
                    for (final MiniTile miniTile : seaExtent) {
                        ((MiniTileImpl) miniTile).setLake();
                    }
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////

}
