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

public abstract class TerrainDataImpl implements TerrainData {

    private final MapData mapData;
    private final TileData tileData;

    protected TerrainDataImpl(final MapData mapData, final TileData tileData) {
        this.mapData = mapData;
        this.tileData = tileData;
    }

    @Override
    public MapData getMapData() {
        return this.mapData;
    }

    @Override
    public TileData getTileData() {
        return this.tileData;
    }

    @Override
    public Tile getTile(final TilePosition tilePosition, final CheckMode checkMode) {
//        bwem_assert((checkMode == utils::Check::no_check) || Valid(p)); utils::unused(checkMode);
        if (!((checkMode == CheckMode.NO_CHECK) || getMapData().isValid(tilePosition))) {
            throw new IllegalArgumentException();
        }
        return getTileData().getTiles().get(getMapData().getTileSize().getX() * tilePosition.getY() + tilePosition.getX());
    }

    @Override
    public Tile getTile(final TilePosition tilePosition) {
        return getTile(tilePosition, CheckMode.CHECK);
    }

    @Override
    public MiniTile getMiniTile(final WalkPosition walkPosition, final CheckMode checkMode) {
//        bwem_assert((checkMode == utils::Check::no_check) || Valid(p));
        if (!((checkMode == CheckMode.NO_CHECK) || getMapData().isValid(walkPosition))) {
            throw new IllegalArgumentException();
        }
        return getTileData().getMiniTiles().get(getMapData().getWalkSize().getX() * walkPosition.getY() + walkPosition.getX());
    }

    @Override
    public MiniTile getMiniTile(final WalkPosition walkPosition) {
        return getMiniTile(walkPosition, CheckMode.CHECK);
    }

    @Override
    public boolean isSeaWithNonSeaNeighbors(final WalkPosition walkPosition) {
        if (!getMiniTile(walkPosition).isSea()) {
            return false;
        }

        final WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
        for (final WalkPosition delta : deltas) {
            final WalkPosition walkPositionDelta = walkPosition.add(delta);
            if (getMapData().isValid(walkPositionDelta)) {
                if (!getMiniTile(walkPositionDelta, CheckMode.NO_CHECK).isSea()) {
                    return true;
                }
            }
        }

        return false;
    }

}
