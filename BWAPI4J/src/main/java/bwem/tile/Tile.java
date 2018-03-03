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

public interface Tile {

    // 0: lower ground    1: high ground    2: very high ground
    // Corresponds to BWAPI::getGroundHeight / 2
    enum GroundHeight {

        LOW_GROUND(0),
        HIGH_GROUND(1),
        VERY_HIGH_GROUND(2);

        private final int val;

        GroundHeight(final int val) {
            this.val = val;
        }

        public int intValue() {
            return this.val;
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

    // Corresponds to BWAPI::isBuildable
	// Note: BWEM enforces the relation buildable ==> walkable (Cf. MiniTile::Walkable)
    boolean isBuildable();

	// Tile::AreaId() somewhat aggregates the MiniTile::getAreaId() values of the 4 x 4 sub-miniTiles.
	// Let S be the set of MiniTile::AreaId() values for each walkable MiniTile in this Tile.
	// If empty(S), returns 0. Note: in this case, no contained MiniTile is walkable, so all of them have their AreaId() == 0.
	// If S = {a}, returns a (whether positive or negative).
	// If size(S) > 1 returns -1 (note that -1 is never returned by MiniTile::AreaId()).
    AreaId getAreaId();

	// Tile::MinAltitude() somewhat aggregates the MiniTile::Altitude() values of the 4 x 4 sub-miniTiles.
	// Returns the minimum value.
    Altitude getMinAltitude();

    // Tells if at least one of the sub-miniTiles is Walkable.
    boolean isWalkable();

    // Tells if at least one of the sub-miniTiles is a Terrain-MiniTile.
    boolean isTerrain();

	// 0: lower ground    1: high ground    2: very high ground
	// Corresponds to BWAPI::getGroundHeight / 2
    GroundHeight getGroundHeight();

	// Tells if this Tile is part of a doodad.
	// Corresponds to BWAPI::getGroundHeight % 2
    boolean isDoodad();

	// If any Neutral occupies this Tile, returns it (note that all the Tiles it occupies will then return it).
	// Otherwise, returns nullptr.
	// Neutrals are minerals, geysers and StaticBuildings (Cf. Neutral).
	// In some maps (e.g. Benzene.scx), several Neutrals are stacked at the same location.
	// In this case, only the "bottom" one is returned, while the other ones can be accessed using Neutral::nextStacked().
	// Because Neutrals never move on the Map, the returned value is guaranteed to remain the same, unless some Neutral
	// is destroyed and BWEM is informed of that by a call of Map::onMineralDestroyed(BWAPI::unit u) for exemple. In such a case,
	// BWEM automatically updates the data by deleting the Neutral instance and clearing any reference to it such as the one
	// returned by Tile::GetNeutral(). In case of stacked Neutrals, the next one is then returned.
    Neutral getNeutral();

    // Returns the number of Neutrals that occupy this Tile (Cf. GetNeutral).
    int getStackedNeutralCount();

}
