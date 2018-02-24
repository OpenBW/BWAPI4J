package bwem.map;

import bwem.Check;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileData;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class AdvancedDataImpl implements AdvancedData {

    private final MapData mapData;
    private final TileData tileData;

    public AdvancedDataImpl(final MapData mapData, final TileData tileData) {
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
    public Tile getTile(final TilePosition tilePosition, final Check checkMode) {
//        bwem_assert((checkMode == utils::Check::no_check) || Valid(p)); utils::unused(checkMode);
        if (!((checkMode == Check.NO_CHECK) || getMapData().isValid(tilePosition))) {
            throw new IllegalArgumentException();
        }
        return this.tileData.getTiles().get(getMapData().getTileSize().getX() * tilePosition.getY() + tilePosition.getX());
    }

    @Override
    public Tile getTile(final TilePosition tilePosition) {
        return getTile(tilePosition, Check.CHECK);
    }

    @Override
    public Tile getTile_(final TilePosition tilePosition, final Check checkMode) {
        return getTile(tilePosition, checkMode);
    }

    @Override
    public Tile getTile_(final TilePosition p) {
        return getTile_(p, Check.CHECK);
    }

    @Override
    public MiniTile getMiniTile(final WalkPosition walkPosition, final Check checkMode) {
//        bwem_assert((checkMode == utils::Check::no_check) || Valid(p));
        if (!((checkMode == Check.NO_CHECK) || getMapData().isValid(walkPosition))) {
            throw new IllegalArgumentException();
        }
        return this.tileData.getMiniTiles().get(getMapData().getWalkSize().getX() * walkPosition.getY() + walkPosition.getX());
    }

    @Override
    public MiniTile getMiniTile(final WalkPosition walkPosition) {
        return getMiniTile(walkPosition, Check.CHECK);
    }

    @Override
    public MiniTile getMiniTile_(final WalkPosition walkPosition, final Check checkMode) {
        return getMiniTile(walkPosition, checkMode);
    }

    @Override
    public MiniTile getMiniTile_(final WalkPosition walkPosition) {
        return getMiniTile_(walkPosition, Check.CHECK);
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
                if (!getMiniTile(walkPositionDelta, Check.NO_CHECK).isSea()) {
                    return true;
                }
            }
        }

        return false;
    }

}
