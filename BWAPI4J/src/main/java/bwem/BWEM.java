package bwem;

import bwem.map.MapImpl;
import bwem.map.Map;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public final class BWEM {

    /**
     * This constant contributes to deciding between Seas and Lakes.
     */
    public static final int LAKE_MAX_MINI_TILES = 300;

    /**
     * This constant contributes to deciding between Seas and Lakes.
     */
    public static final int LAKE_MAX_WIDTH_IN_MINITILES = 8 * 4;

    /**
     * At least this amount of connected MiniTiles are necessary for an Area to be created.
     */
    public static final int AREA_MIN_MINI_TILES = 64;

    public static final double CLUSTER_MIN_DISTANCE = Math.sqrt(LAKE_MAX_MINI_TILES);

    private final BW bw;
    private final Map map;

    public BWEM(BW bw) {
        this.bw = bw;
        this.map = new MapImpl(this.bw);
    }

    public Map getMap() {
        return this.map;
    }

    public static Altitude getMinAltitudeTop(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(1, 0)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(2, 0)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeBottom(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(1, 3)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(2, 3)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeLeft(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(0, 1)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(0, 2)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

    public static Altitude getMinAltitudeRight(TilePosition t, Map map) {
        WalkPosition w = t.toPosition().toWalkPosition();
        return new Altitude(Math.min(
                map.getMiniTile(w.add(new WalkPosition(3, 1)), CheckMode.NoCheck).getAltitude().intValue(),
                map.getMiniTile(w.add(new WalkPosition(3, 2)), CheckMode.NoCheck).getAltitude().intValue()
        ));
    }

}
