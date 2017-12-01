package bwem.tile;

import bwem.Markable;
import bwem.StaticMarkable;
import bwem.area.typedef.AreaId;
import bwem.typedef.Altitude;
import bwem.unit.Neutral;
import org.apache.commons.lang3.mutable.MutableInt;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Tile
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Corresponds to BWAPI/Starcraft's concept of tile (32x32 pixels).
// Tiles are accessed using TilePositions (Cf. Map::GetTile).
// A Map holds Map::Size().x * Map::Size().y Tiles as its "Tile map".
//
// It should be noted that a Tile exactly overlaps 4 x 4 MiniTiles.
// As there are 16 times as many MiniTiles as Tiles, we allow a Tiles to contain more data than MiniTiles.
// As a consequence, Tiles should be preferred over MiniTiles, for efficiency.
// The use of Tiles is further facilitated by some functions like Tile::AreaId or Tile::MinAltitude
// which somewhat aggregate the MiniTile's corresponding information
//
// Tiles inherit utils::Markable, which provides marking ability
// Tiles inherit utils::UserData, which provides free-to-use data.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Tile {

    // 0: lower ground    1: high ground    2: very high ground
    // Corresponds to BWAPI::getGroundHeight / 2
    public static enum GroundHeight {

        LOW_GROUND(0),
        HIGH_GROUND(1),
        VERY_HIGH_GROUND(2);

        private final int val;

        private GroundHeight(final int val) {
            this.val = val;
        }

        public int intValue() {
            return this.val;
        }

        @Override
        public String toString() {
            return String.valueOf(intValue());
        }

        public static GroundHeight parseGroundHeight(final int height) {
            for (final GroundHeight val : GroundHeight.values()) {
                if (val.intValue() == height) {
                    return val;
                }
            }
            throw new IllegalArgumentException("Unrecognized height: " + height);
        }

    }

    private final static StaticMarkable staticMarkable = new StaticMarkable();
    private final Markable markable;

    private Neutral neutral;
    private Altitude minAltitude;
    private AreaId areaId;
    private final MutableInt internalData;
    private GroundHeight groundHeight;
    private boolean isBuildable;
    private boolean isDoodad;

    public Tile() {
        this.markable = new Markable(Tile.staticMarkable);
        this.neutral = null;
        this.minAltitude = new Altitude(0);
        this.areaId = new AreaId(0);
        this.internalData = new MutableInt(0);
        this.groundHeight = GroundHeight.LOW_GROUND;
        this.isBuildable = false;
        this.isDoodad = false;
    }

    public static StaticMarkable getStaticMarkable() {
        return Tile.staticMarkable;
    }

    public Markable getMarkable() {
        return this.markable;
    }

    // Corresponds to BWAPI::isBuildable
	// Note: BWEM enforces the relation buildable ==> walkable (Cf. MiniTile::Walkable)
    public boolean isBuildable() {
        return this.isBuildable;
    }

	// Tile::AreaId() somewhat aggregates the MiniTile::getAreaId() values of the 4 x 4 sub-MiniTiles.
	// Let S be the set of MiniTile::AreaId() values for each walkable MiniTile in this Tile.
	// If empty(S), returns 0. Note: in this case, no contained MiniTile is walkable, so all of them have their AreaId() == 0.
	// If S = {a}, returns a (whether positive or negative).
	// If size(S) > 1 returns -1 (note that -1 is never returned by MiniTile::AreaId()).
    public AreaId getAreaId() {
        return this.areaId;
    }

	// Tile::MinAltitude() somewhat aggregates the MiniTile::Altitude() values of the 4 x 4 sub-MiniTiles.
	// Returns the minimum value.
    public Altitude getMinAltitude() {
        return this.minAltitude;
    }

    // Tells if at least one of the sub-MiniTiles is Walkable.
    public boolean isWalkable() {
        return (getAreaId().intValue() != 0);
    }

    // Tells if at least one of the sub-MiniTiles is a Terrain-MiniTile.
    public boolean isTerrain() {
        return isWalkable();
    }

	// 0: lower ground    1: high ground    2: very high ground
	// Corresponds to BWAPI::getGroundHeight / 2
    public GroundHeight getGroundHeight() {
        return this.groundHeight;
    }

	// Tells if this Tile is part of a doodad.
	// Corresponds to BWAPI::getGroundHeight % 2
    public boolean isDoodad() {
        return this.isDoodad;
    }

	// If any Neutral occupies this Tile, returns it (note that all the Tiles it occupies will then return it).
	// Otherwise, returns nullptr.
	// Neutrals are Minerals, Geysers and StaticBuildings (Cf. Neutral).
	// In some maps (e.g. Benzene.scx), several Neutrals are stacked at the same location.
	// In this case, only the "bottom" one is returned, while the other ones can be accessed using Neutral::NextStacked().
	// Because Neutrals never move on the Map, the returned value is guaranteed to remain the same, unless some Neutral
	// is destroyed and BWEM is informed of that by a call of Map::OnMineralDestroyed(BWAPI::Unit u) for exemple. In such a case,
	// BWEM automatically updates the data by deleting the Neutral instance and clearing any reference to it such as the one
	// returned by Tile::GetNeutral(). In case of stacked Neutrals, the next one is then returned.
    public Neutral getNeutral() {
        return this.neutral;
    }

    // Returns the number of Neutrals that occupy this Tile (Cf. GetNeutral).
    public int getStackedNeutralCount() {
        int stackSize = 0;
        for (Neutral pStacked = getNeutral(); pStacked != null; pStacked = pStacked.NextStacked()) {
            ++stackSize;
        }
        return stackSize;
    }

    public void setBuildable() {
        this.isBuildable = true;
    }

    public void setGroundHeight(final int groundHeight) {
//        { bwem_assert((0 <= h) && (h <= 2)); m_bits.groundHeight = h; }
//        if (!((0 <= h) && (h <= 2))) {
//            throw new IllegalArgumentException();
//        }
        this.groundHeight = GroundHeight.parseGroundHeight(groundHeight);
    }

    public void setDoodad() {
        this.isDoodad = true;
    }

    public void addNeutral(final Neutral neutral) {
//        { bwem_assert(!m_pNeutral && pNeutral); neutral = pNeutral; }
        if (!(getNeutral() == null && neutral != null)) {
            throw new IllegalStateException();
        }
        this.neutral = neutral;
    }

    public void setAreaId(final AreaId areaId) {
//        { bwem_assert((id == -1) || !m_areaId && id); areaId = id; }
        if (!(areaId.intValue() == -1 || getAreaId().intValue() == 0 && areaId.intValue() != 0)) {
            throw new IllegalStateException();
        }
        this.areaId = areaId;
    }

    public void resetAreaId() {
        this.areaId = new AreaId(0);
    }

    public void setMinAltitude(final Altitude minAltitude) {
//        { bwem_assert(a >= 0); this.m_minAltitude = a; }
        if (!(minAltitude.intValue() >= 0)) {
            throw new IllegalArgumentException();
        }
        this.minAltitude = minAltitude;
    }

    public void RemoveNeutral(final Neutral neutral) {
//        { bwem_assert(pNeutral && (m_pNeutral == pNeutral));
//          utils::unused(pNeutral); m_pNeutral = nullptr; }
        if (!(neutral != null && getNeutral().equals(neutral))) {
            throw new IllegalStateException();
        }
        this.neutral = null;
    }

    public MutableInt getInternalData() {
        return this.internalData;
    }

}
