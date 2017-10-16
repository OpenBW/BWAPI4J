package bwem;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class MiniTile
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Corresponds to BWAPI/Starcraft's concept of minitile (8x8 pixels).
// MiniTiles are accessed using WalkPositions (Cf. Map::GetMiniTile).
// A Map holds Map::WalkSize().x * Map::WalkSize().y MiniTiles as its "MiniTile map".
// A MiniTile contains essentialy 3 informations:
//	- its Walkability
//	- its altitude (distance from the nearest non walkable MiniTile, except those which are part of small enough zones (lakes))
//	- the id of the Area it is part of, if ever.
// The whole process of analysis of a Map relies on the walkability information
// from which are derived successively : altitudes, Areas, ChokePoints.
public class MiniTile {

    public static final int SIZE_IN_PIXELS = 8;

    private static final Area.Id BLOCKING_CP_AREA_ID = new Area.Id(Integer.MIN_VALUE); //TODO: Use a boolean for isBlocked?

    private Altitude altitude = null;
    private Area.Id areaId = null;

    public MiniTile() {
        this.altitude = new Altitude(-1); // 0 for seas  ;  != 0 for terrain and lakes (-1 = not computed yet)  ;  1 = SeaOrLake intermediate value
        this.areaId = new Area.Id(-1); // 0 -> unwalkable  ;  > 0 -> index of some Area  ;  < 0 -> some walkable terrain, but too small to be part of an Area
    }

    public boolean isAltitudeMissing() {
        return (this.altitude.intValue() == -1);
    }

	// Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile
	// Sea-MiniTiles all have their Altitude() equal to 0.
	// MiniTiles having Altitude() > 0 are not Sea-MiniTiles. They can be either Terrain-MiniTiles or Lake-MiniTiles.
    public Altitude getAltitude() {
        return new Altitude(this.altitude);
    }

    public void setAltitude(Altitude altitude) {
//        { bwem_assert_debug_only(AltitudeMissing() && (a > 0)); m_altitude = a; }
        if (!(isAltitudeMissing() && altitude.intValue() > 0)) {
            throw new IllegalStateException();
        } else {
            this.altitude = new Altitude(altitude);
        }
    }

	// For Sea and Lake MiniTiles, returns 0
	// For Terrain MiniTiles, returns a non zero id:
	//    - if (id > 0), id uniquely identifies the Area A that contains this MiniTile.
	//      Moreover we have: A.Id() == id and Map::GetArea(id) == A
	//      For more information about positive Area::ids, see Area::Id()
	//    - if (id < 0), then this MiniTile is part of a Terrain-zone that was considered too small to create an Area for it.
	//      Note: negative Area::ids start from -2
	// Note: because of the lakes, Map::GetNearestArea should be prefered over Map::GetArea.
    public Area.Id getAreaId() {
        return new Area.Id(this.areaId);
    }

    public void setAreaId(Area.Id areaId) {
//        { bwem_assert(AreaIdMissing() && (id >= 1)); m_areaId = id; }
        if (!(isAreaIdMissing() && areaId.intValue() >= 1)) {
            throw new IllegalStateException();
        } else {
            this.areaId = new Area.Id(areaId);
        }
    }

    public void replaceAreaId(Area.Id areaId) {
//        { bwem_assert((m_areaId > 0) && ((id >= 1) || (id <= -2)) && (id != m_areaId)); m_areaId = id; }
        if (!((this.areaId.intValue() > 0)
                && ((areaId.intValue() >= 1) || (areaId.intValue() <= -2))
                && (areaId.intValue() != this.areaId.intValue()))) {
            throw new IllegalArgumentException("invalid Area.Id");
        } else {
            this.areaId = new Area.Id(areaId);
        }
    }

    public boolean isAreaIdMissing() {
        return (this.areaId.intValue() == -1);
    }

    //TODO: Use boolean instead of BLOCKING_CP_AREA_ID?
    public boolean isBlocked() {
        return this.areaId.equals(MiniTile.BLOCKING_CP_AREA_ID);
    }

    public void replaceBlockedAreaId(Area.Id areaId) {
//        { bwem_assert((m_areaId == blockingCP) && (id >= 1)); m_areaId = id; }
        if (!(this.areaId.equals(MiniTile.BLOCKING_CP_AREA_ID)
                && areaId.intValue() >= 1)) {
            throw new IllegalStateException();
        } else {
            this.areaId = new Area.Id(areaId);
        }
    }

    public void setBlocked() {
//        { bwem_assert(AreaIdMissing()); m_areaId = blockingCP; }
        if (!isAreaIdMissing()) {
            throw new IllegalStateException();
        } else {
            this.areaId = new Area.Id(BLOCKING_CP_AREA_ID);
        }
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

    public void setWalkable(boolean walkable) {
        this.areaId = new Area.Id(walkable ? -1 : 0);
        this.altitude = new Altitude(walkable ? -1 : 1);
    }

    // Terrain MiniTiles are just walkable MiniTiles
    public boolean isTerrain() {
        return isWalkable();
    }

    // Sea-MiniTiles are unwalkable MiniTiles that have their Altitude() equal to 0.
    public boolean isSea() {
        return (this.altitude.intValue() == 0);
    }

    public void setSea() {
//        { bwem_assert(!Walkable() && SeaOrLake()); m_altitude = 0; }
        if (!(!isWalkable() && isSeaOrLake())) {
            throw new IllegalStateException();
        } else {
            this.altitude = new Altitude(0);
        }
    }

	// Lake-MiniTiles are unwalkable MiniTiles that have their Altitude() > 0.
	// They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)
	// The intent is to preserve the continuity of altitudes inside Areas.
    public boolean isLake() {
        return (this.altitude.intValue() != 0 && !isWalkable());
    }

    public void setLake() {
//        { bwem_assert(!Walkable() && Sea()); m_altitude = -1; }
        if (!(!isWalkable() && isSea())) {
            throw new IllegalStateException();
        } else {
            this.altitude = new Altitude(-1);
        }
    }

    public boolean isSeaOrLake() {
        return (this.altitude.intValue() == 1);
    }

}
