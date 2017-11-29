package bwem.tile;

import bwem.typedef.Altitude;
import bwem.typedef.Bits;
import bwem.Markable;
import bwem.area.typedef.AreaId;
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

public final class Tile extends Markable<Tile> {

    private Neutral m_pNeutral = null;
    private Altitude m_minAltitude = new Altitude(0);
    private AreaId m_areaId = new AreaId(0);
    private MutableInt m_internalData = new MutableInt(0);
    private Bits m_bits = new Bits();

    public Tile() {
        /* Do nothing. */
    }

	// Corresponds to BWAPI::isBuildable
	// Note: BWEM enforces the relation buildable ==> walkable (Cf. MiniTile::Walkable)
    public boolean Buildable() {
        return (m_bits.buildable != 0x0);
    }

	// Tile::AreaId() somewhat aggregates the MiniTile::AreaId() values of the 4 x 4 sub-MiniTiles.
	// Let S be the set of MiniTile::AreaId() values for each walkable MiniTile in this Tile.
	// If empty(S), returns 0. Note: in this case, no contained MiniTile is walkable, so all of them have their AreaId() == 0.
	// If S = {a}, returns a (whether positive or negative).
	// If size(S) > 1 returns -1 (note that -1 is never returned by MiniTile::AreaId()).
    public AreaId AreaId() {
        return m_areaId;
    }

	// Tile::MinAltitude() somewhat aggregates the MiniTile::Altitude() values of the 4 x 4 sub-MiniTiles.
	// Returns the minimum value.
    public Altitude MinAltitude() {
        return m_minAltitude;
    }

    // Tells if at least one of the sub-MiniTiles is Walkable.
    public boolean Walkable() {
        return (m_areaId.intValue() != 0);
    }

    // Tells if at least one of the sub-MiniTiles is a Terrain-MiniTile.
    public boolean Terrain() {
        return Walkable();
    }

	// 0: lower ground    1: high ground    2: very high ground
	// Corresponds to BWAPI::getGroundHeight / 2
    public int GroundHeight() {
        return m_bits.groundHeight;
    }

	// Tells if this Tile is part of a doodad.
	// Corresponds to BWAPI::getGroundHeight % 2
    public boolean Doodad() {
        return (m_bits.doodad != 0x0);
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
    public Neutral GetNeutral() {
        return m_pNeutral;
    }

    // Returns the number of Neutrals that occupy this Tile (Cf. GetNeutral).
    public int StackedNeutrals() {
        int stackSize = 0;
        for (Neutral pStacked = GetNeutral(); pStacked != null; pStacked = pStacked.NextStacked()) {
            ++stackSize;
        }
        return stackSize;
    }

    public void SetBuildable() {
        m_bits.buildable = 0x1;
    }

    public void SetGroundHeight(int h) {
//        { bwem_assert((0 <= h) && (h <= 2)); m_bits.groundHeight = h; }
        if (!((0 <= h) && (h <= 2))) {
            throw new IllegalArgumentException();
        }
        m_bits.groundHeight = h;
    }

    public void SetDoodad() {
        m_bits.doodad = 0x1;
    }

    public void AddNeutral(Neutral pNeutral) {
//        { bwem_assert(!m_pNeutral && pNeutral); m_pNeutral = pNeutral; }
        if (!(m_pNeutral == null && pNeutral != null)) {
            throw new IllegalStateException();
        }
        m_pNeutral = pNeutral;
    }

    public void SetAreaId(AreaId id) {
//        { bwem_assert((id == -1) || !m_areaId && id); m_areaId = id; }
        if (!(id.intValue() == -1 || m_areaId.intValue() == 0 && id.intValue() != 0)) {
            throw new IllegalStateException();
        }
        m_areaId = id;
    }

    public void ResetAreaId() {
        m_areaId = new AreaId(0);
    }

    public void SetMinAltitude(Altitude a) {
//        { bwem_assert(a >= 0); m_minAltitude = a; }
        if (!(a.intValue() >= 0)) {
            throw new IllegalArgumentException();
        }
        m_minAltitude = a;
    }

    public void RemoveNeutral(Neutral pNeutral) {
//        { bwem_assert(pNeutral && (m_pNeutral == pNeutral));
//          utils::unused(pNeutral); m_pNeutral = nullptr; }
        if (!(pNeutral != null) && m_pNeutral.equals(pNeutral)) {
            throw new IllegalStateException();
        }
        m_pNeutral = null;
    }

    public MutableInt InternalData() {
        return m_internalData;
    }

    public void SetInternalData(MutableInt data) {
        m_internalData = data;
    }

}
