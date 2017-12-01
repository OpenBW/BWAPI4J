package bwem.tile;

import bwem.typedef.Altitude;
import bwem.area.typedef.AreaId;

public final class MiniTile {

    private static final AreaId blockingCP = new AreaId(Integer.MIN_VALUE);

    private Altitude altitude; // 0 for seas  ;  != 0 for terrain and lakes (-1 = not computed yet)  ;  1 = SeaOrLake intermediate value
    private AreaId areaId; // 0 -> unwalkable  ;  > 0 -> index of some Area  ;  < 0 -> some walkable terrain, but too small to be part of an Area

    public MiniTile() {
        this.altitude = new Altitude(-1);
        this.areaId = new AreaId(-1);
    }

	// Corresponds approximatively to BWAPI::isWalkable
	// The differences are:
	//  - For each BWAPI's unwalkable MiniTile, we also mark its 8 neighbours as not walkable.
	//    According to some tests, this prevents from wrongly pretending one small unit can go by some thin path.
	//  - The relation buildable ==> walkable is enforced, by marking as walkable any MiniTile part of a buildable Tile (Cf. Tile::Buildable)
	// Among the MiniTiles having Altitude() > 0, the walkable ones are considered Terrain-MiniTiles, and the other ones Lake-MiniTiles.
    public boolean isWalkable() {
        return (this.areaId.intValue() != 0);
    }

	// Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile
	// Sea-MiniTiles all have their Altitude() equal to 0.
	// MiniTiles having Altitude() > 0 are not Sea-MiniTiles. They can be either Terrain-MiniTiles or Lake-MiniTiles.
    public Altitude getAltitude() {
        return this.altitude;
    }

    // Sea-MiniTiles are unwalkable MiniTiles that have their Altitude() equal to 0.
    public boolean isSea() {
        return (this.altitude.intValue() == 0);
    }

	// Lake-MiniTiles are unwalkable MiniTiles that have their Altitude() > 0.
	// They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)
	// The intent is to preserve the continuity of altitudes inside Areas.
    public boolean isLake() {
        return (this.altitude.intValue() != 0 && !isWalkable());
    }

    // Terrain MiniTiles are just walkable MiniTiles
    public boolean isTerrain() {
        return isWalkable();
    }

	// For Sea and Lake MiniTiles, returns 0
	// For Terrain MiniTiles, returns a non zero id:
	//    - if (id > 0), id uniquely identifies the Area A that contains this MiniTile.
	//      Moreover we have: A.Id() == id and Map::GetArea(id) == A
	//      For more information about positive Area::ids, see Area::Id()
	//    - if (id < 0), then this MiniTile is part of a Terrain-zone that was considered too small to create an Area for it.
	//      Note: negative Area::ids start from -2
	// Note: because of the lakes, Map::GetNearestArea should be prefered over Map::GetArea.
    public AreaId getAreaId() {
        return this.areaId;
    }

    public void setWalkable(boolean walkable) {
        this.areaId = new AreaId(walkable ? -1 : 0);
        this.altitude = new Altitude(walkable ? -1 : 1);
    }

    public boolean isSeaOrLake() {
        return (this.altitude.intValue() == 1);
    }

    public void setSea() {
//        { bwem_assert(!Walkable() && SeaOrLake()); this.m_altitude = 0; }
        if (!(!isWalkable() && isSeaOrLake())) {
            throw new IllegalStateException();
        }
        this.altitude = new Altitude(0);
    }

    public void setLake() {
//        { bwem_assert(!Walkable() && Sea()); this.m_altitude = -1; }
        if (!(!isWalkable() && isSea())) {
            throw new IllegalStateException();
        }
        this.altitude = new Altitude(-1);
    }

    public boolean isAltitudeMissing() {
        return (altitude.intValue() == -1);
    }

    public void setAltitude(final Altitude altitude) {
//        { bwem_assert_debug_only(AltitudeMissing() && (a > 0)); this.m_altitude = a; }
        if (!(isAltitudeMissing() && altitude.intValue() > 0)) {
            throw new IllegalStateException();
        }
        this.altitude = altitude;
    }

    public boolean isAreaIdMissing() {
        return (this.areaId.intValue() == -1);
    }

    public void setAreaId(final AreaId areaId) {
//        { bwem_assert(AreaIdMissing() && (id >= 1)); this.m_areaId = id; }
        if (!(isAreaIdMissing() && areaId.intValue() >= 1)) {
            throw new IllegalStateException();
        }
        this.areaId = areaId;
    }

    public void replaceAreaId(final AreaId areaId) {
//        { bwem_assert((m_areaId > 0) && ((id >= 1) || (id <= -2)) && (id != this.areaId)); this.areaId = id; }
//        if (!((m_areaId.intValue() > 0) && ((id.intValue() >= 1) || (id.intValue() <= -2)) && (!id.equals(areaId)))) {
        if (!(this.areaId.intValue() > 0)) {
            throw new IllegalStateException("Failed assert: this.m_areaId.intValue() > 0: " + this.areaId.intValue());
        } else if (!((areaId.intValue() >= 1) || (areaId.intValue() <= -2))) {
            throw new IllegalArgumentException("Failed assert: (id.intValue() >= 1) || (id.intValue() <= -2): " + areaId.intValue());
        } else if (!(!areaId.equals(this.areaId))) {
            throw new IllegalArgumentException("Failed assert: !id.equals(m_areaId): not expected: " + this.areaId.intValue() + ", actual: " + areaId.intValue());
        } else {
            this.areaId = areaId;
        }
    }

    public void setBlocked() {
//        { bwem_assert(AreaIdMissing()); this.m_areaId = blockingCP; }
        if (!isAreaIdMissing()) {
            throw new IllegalStateException();
        }
        this.areaId = MiniTile.blockingCP;
    }

    public boolean isBlocked() {
        return this.areaId.equals(blockingCP);
    }

    public void replaceBlockedAreaId(final AreaId areaId) {
//        { bwem_assert((m_areaId == blockingCP) && (id >= 1)); this.areaId = id; }
        if (!(this.areaId.equals(MiniTile.blockingCP) && areaId.intValue() >= 1)) {
            throw new IllegalStateException();
        }
        this.areaId = areaId;
    }

}
