package bwem;

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

    private static final int blockingCP = 0; // static const Area::id blockingCP;

    private Altitude m_altitude; // 0 for seas  ;  != 0 for terrain and lakes (-1 = not computed yet)  ;  1 = SeaOrLake intermediate value
    private int m_areaId; // 0 -> unwalkable  ;  > 0 -> index of some Area  ;  < 0 -> some walkable terrain, but too small to be part of an Area

    public MiniTile() {
        this.m_altitude = new Altitude(-1);
        this.m_areaId = -1;
    }

    // Corresponds approximatively to BWAPI::isWalkable
    // The differences are:
    //  - For each BWAPI's unwalkable MiniTile, we also mark its 8 neighbours as not walkable.
    //    According to some tests, this prevents from wrongly pretending one small unit can go by some thin path.
    //  - The relation buildable ==> walkable is enforced, by marking as walkable any MiniTile part of a buildable Tile (Cf. Tile::Buildable)
    // Among the MiniTiles having Altitude() > 0, the walkable ones are considered Terrain-MiniTiles, and the other ones Lake-MiniTiles.
    public boolean Walkable() {
        return (this.m_areaId != 0);
    }

    // Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile
    // Sea-MiniTiles all have their Altitude() equal to 0.
    // MiniTiles having Altitude() > 0 are not Sea-MiniTiles. They can be either Terrain-MiniTiles or Lake-MiniTiles.
    public Altitude Altitude() {
        return this.m_altitude;
    }

    // Sea-MiniTiles are unwalkable MiniTiles that have their Altitude() equal to 0.
    public boolean Sea() {
        return (this.m_altitude.toInt() == 0);
    }

    // Lake-MiniTiles are unwalkable MiniTiles that have their Altitude() > 0.
    // They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)
    // The intent is to preserve the continuity of altitudes inside Areas.
    public boolean Lake() {
        return (this.m_altitude.toInt() != 0 && !Walkable());
    }

    // Terrain MiniTiles are just walkable MiniTiles
    public boolean Terrain() {
        return Walkable();
    }

    // For Sea and Lake MiniTiles, returns 0
    // For Terrain MiniTiles, returns a non zero id:
    //    - if (id > 0), id uniquely identifies the Area A that contains this MiniTile.
    //      Moreover we have: A.Id() == id and Map::GetArea(id) == A
    //      For more information about positive Area::ids, see Area::Id()
    //    - if (id < 0), then this MiniTile is part of a Terrain-zone that was considered too small to create an Area for it.
    //      Note: negative Area::ids start from -2
    // Note: because of the lakes, Map::GetNearestArea should be prefered over Map::GetArea.
    public int AreaId() {
        return this.m_areaId;
    }

    ////////////////////////////////////////////////////////////////////////////
    //	Details: The functions below are used by the BWEM's internals

    public void SetWalkable(boolean walkable) {
        this.m_areaId = (walkable ? -1 : 0);
        this.m_altitude = new Altitude((walkable ? -1 : 1));
    }

    public boolean SeaOrLake() {
        return (this.m_altitude.toInt() == 1);
    }

    public void SetSea() {
//        assert (!Walkable() && SeaOrLake()); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(!Walkable() && SeaOrLake())) {
            throw new IllegalStateException();
        }
        this.m_altitude = new Altitude(0);
    }

    public void SetLake() {
//        assert (!Walkable() && Sea()); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(!Walkable() && Sea())) {
            throw new IllegalStateException();
        }
        this.m_altitude = new Altitude(-1);
    }

    public boolean AltitudeMissing() {
        return (this.m_altitude.toInt() == -1);
    }

    public void SetAltitude(int a) {
//        bwem_assert_debug_only(AltitudeMissing() && (a > 0));
//        assert (AltitudeMissing() && (a > 0)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(AltitudeMissing() && (a > 0))) {
            throw new IllegalStateException();
        }
        this.m_altitude = new Altitude(a);
    }

    public boolean AreaIdMissing() {
        return (this.m_areaId == -1);
    }

    public void SetAreaId(int id) {
//        assert (AreaIdMissing() && (id >= 1)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(AreaIdMissing() && (id >= 1))) {
            throw new IllegalStateException();
        }
        this.m_areaId = id;
    }

    public void ReplaceAreaId(int id) {
//        assert ((m_areaId > 0) && ((id >= 1) || (id <= -2)) && (id != m_areaId)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!((this.m_areaId > 0) && ((id >= 1) || (id <= -2)) && (id != this.m_areaId))) {
            throw new IllegalStateException();
        }
        this.m_areaId = id;
    }

    public void SetBlocked() {
//        assert AreaIdMissing(); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!AreaIdMissing()) {
            throw new IllegalStateException();
        }
        this.m_areaId = this.blockingCP;
    }

    public boolean Blocked() {
        return (this.m_areaId == this.blockingCP);
    }

    public void ReplaceBlockedAreaId(int id) {
//        assert ((m_areaId == blockingCP) && (id >= 1)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!((this.m_areaId == this.blockingCP) && (id >= 1))) {
            throw new IllegalStateException();
        }
        this.m_areaId = id;
    }

}
