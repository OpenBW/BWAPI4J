package bwem.tile;

import bwem.Altitude;
import bwem.Bits;
import bwem.Neutral;
import org.apache.commons.lang3.mutable.MutableInt;

public class Tile extends AbstractTile {

    public static final int SIZE_IN_PIXELS = 32;

    private Neutral neutral;
    private Altitude minAltitude;
    private MutableInt internalData;
    private Bits bits;

    public Tile() {
        super(0);
        this.minAltitude = new Altitude(0);
        this.internalData = new MutableInt(0);
        this.bits = new Bits();
    }

    // Tile::AreaId() somewhat aggregates the MiniTile::AreaId() values of the 4 x 4 sub-MiniTiles.
    // Let S be the set of MiniTile::AreaId() values for each walkable MiniTile in this Tile.
    // If empty(S), returns 0. Note: in this case, no contained MiniTile is walkable, so all of them have their AreaId() == 0.
    // If S = {a}, returns a (whether positive or negative).
    // If size(S) > 1 returns -1 (note that -1 is never returned by MiniTile::AreaId()).
	public int getAreaId() {
        return super.areaId;
    }

    public void setAreaId(int id) {
        if (!((id == -1) || (super.areaId == 0 && id != 0))) {
            throw new IllegalStateException();
        }
        super.areaId = id;
    }

    // Tile::MinAltitude() somewhat aggregates the MiniTile::Altitude() values of the 4 x 4 sub-MiniTiles.
    // Returns the minimum value.
	public Altitude getMinAltitude() {
        return new Altitude(this.minAltitude);
    }

    public void setMinAltitude(Altitude altitude) {
        if (!(altitude.toInt() >= 0)) {
            throw new IllegalArgumentException("altitude=" + altitude.toInt());
        }
        this.minAltitude = new Altitude(altitude);
    }

	// Corresponds to BWAPI::isBuildable
	// Note: BWEM enforces the relation buildable ==> walkable (Cf. MiniTile::Walkable)
    public boolean isBuildable() {
        return (this.bits.buildable != 0x0);
    }

    public void setBuildable() {
        this.bits.buildable = 0x1;
    }

    // Tells if at least one of the sub-MiniTiles is Walkable.
	public boolean isWalkable() {
        return (super.areaId != 0);
    }

    // Tells if at least one of the sub-MiniTiles is a Terrain-MiniTile.
	public boolean isTerrain() {
        return isWalkable();
    }

    // Tells if this Tile is part of a doodad.
    // Corresponds to BWAPI::getGroundHeight % 2
    public boolean isDoodad() {
        return (this.bits.doodad != 0x0);
    }

    public void setDoodad() {
        this.bits.doodad = 0x1;
    }

    // 0: lower ground    1: high ground    2: very high ground
    // Corresponds to BWAPI::getGroundHeight / 2
    public int getGroundHeight() {
        return ((int) this.bits.groundHeight);
    }

    public void setGroundHeight(int height) {
        if (!((height >= 0) && (height <= 2))) {
            throw new IllegalArgumentException("h=" + height);
        }
        this.bits.groundHeight = (byte) height;
    }

    // TODO
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

    // TODO
	// Returns the number of Neutrals that occupy this Tile (Cf. GetNeutral).
//    int Tile::StackedNeutrals() const
//    {
//        int stackSize = 0;
//        for (Neutral * pStacked = GetNeutral() ; pStacked ; pStacked = pStacked->NextStacked())
//            ++stackSize;
//
//        return stackSize;
//    };

    ////////////////////////////////////////////////////////////////////////////
    //	Details: The functions below are used by the BWEM's internals

    public void setNeutral(Neutral neutral) {
        if (!(this.neutral == null && neutral != null)) {
            throw new IllegalStateException();
        }
        this.neutral = neutral;
    }

    public void removeNeutral(Neutral neutral) {
        //TODO: Check that "this.neutral == neutral" is accurate for testing equivalency in this case.
        if (!(neutral != null && this.neutral == neutral)) {
            throw new IllegalStateException();
        }
        this.neutral = null;
    }

    public int getInternalData() {
        return this.internalData.intValue();
    }

    public void setInternalData(int data) {
        this.internalData.setValue(data);
    }

}
