package bwem.tile;

// MiniTiles are accessed using WalkPositions (Cf. Map::GetMiniTile).

import bwem.Altitude;

// A Map holds Map::WalkSize().x * Map::WalkSize().y MiniTiles as its "MiniTile map".
// A MiniTile contains essentialy 3 informations:
//	- its Walkability
//	- its altitude (distance from the nearest non walkable MiniTile, except those which are part of small enough zones (lakes))
//	- the id of the Area it is part of, if ever.
// The whole process of analysis of a Map relies on the walkability information
// from which are derived successively : altitudes, Areas, ChokePoints.
public class MiniTile extends AbstractTile {

    public static final int SIZE_IN_PIXELS = 8;

    private static final int blockingCP = 0; // static const Area::id blockingCP;

    private Altitude altitude; // 0 for seas  ;  != 0 for terrain and lakes (-1 = not computed yet)  ;  1 = SeaOrLake intermediate value
//    private int areaId; // 0 -> unwalkable  ;  > 0 -> index of some Area  ;  < 0 -> some walkable terrain, but too small to be part of an Area

    public MiniTile() {
        super(-1);
        this.altitude = new Altitude(-1);
    }

    // Corresponds approximatively to BWAPI::isWalkable
    // The differences are:
    //  - For each BWAPI's unwalkable MiniTile, we also mark its 8 neighbours as not walkable.
    //    According to some tests, this prevents from wrongly pretending one small unit can go by some thin path.
    //  - The relation buildable ==> walkable is enforced, by marking as walkable any MiniTile part of a buildable Tile (Cf. Tile::Buildable)
    // Among the MiniTiles having Altitude() > 0, the walkable ones are considered Terrain-MiniTiles, and the other ones Lake-MiniTiles.
    public boolean isWalkable() {
        return (super.areaId != 0);
    }

    // Distance in pixels between the center of this MiniTile and the center of the nearest Sea-MiniTile
    // Sea-MiniTiles all have their Altitude() equal to 0.
    // MiniTiles having Altitude() > 0 are not Sea-MiniTiles. They can be either Terrain-MiniTiles or Lake-MiniTiles.
    public Altitude getAltitude() {
        return this.altitude;
    }

    // Sea-MiniTiles are unwalkable MiniTiles that have their Altitude() equal to 0.
    public boolean isSea() {
        return (this.altitude.toInt() == 0);
    }

    // Lake-MiniTiles are unwalkable MiniTiles that have their Altitude() > 0.
    // They form small zones (inside Terrain-zones) that can be eaysily walked around (e.g. Starcraft's doodads)
    // The intent is to preserve the continuity of altitudes inside Areas.
    public boolean isLake() {
        return (this.altitude.toInt() != 0 && !isWalkable());
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
    public int getAreaId() {
        return super.areaId;
    }

    ////////////////////////////////////////////////////////////////////////////
    //	Details: The functions below are used by the BWEM's internals

    public void setWalkable(boolean walkable) {
        super.areaId = (walkable ? -1 : 0);
        this.altitude = new Altitude((walkable ? -1 : 1));
    }

    public boolean isSeaOrLake() {
        return (this.altitude.toInt() == 1);
    }

    public void setSea() {
//        assert (!Walkable() && SeaOrLake()); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(!isWalkable() && isSeaOrLake())) {
            throw new IllegalStateException();
        }
        this.altitude = new Altitude(0);
    }

    public void setLake() {
//        assert (!Walkable() && Sea()); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(!isWalkable() && isSea())) {
            throw new IllegalStateException();
        }
        this.altitude = new Altitude(-1);
    }

    public boolean AltitudeMissing() {
        return (this.altitude.toInt() == -1);
    }

    public void setAltitude(int a) {
//        bwem_assert_debug_only(AltitudeMissing() && (a > 0));
//        assert (AltitudeMissing() && (a > 0)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(AltitudeMissing() && (a > 0))) {
            throw new IllegalStateException();
        }
        this.altitude = new Altitude(a);
    }

    public void setAltitude(Altitude a) {
        setAltitude(a.toInt());
    }

    public boolean isAreaIdMissing() {
        return (super.areaId == -1);
    }

    public void setAreaId(int id) {
//        assert (AreaIdMissing() && (id >= 1)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!(isAreaIdMissing() && (id >= 1))) {
            throw new IllegalStateException();
        }
        super.areaId = id;
    }

    public void replaceAreaId(int id) {
//        assert ((m_areaId > 0) && ((id >= 1) || (id <= -2)) && (id != m_areaId)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!((super.areaId > 0) && ((id >= 1) || (id <= -2)) && (id != super.areaId))) {
            throw new IllegalStateException();
        }
        super.areaId = id;
    }

    public void setBlocked() {
//        assert AreaIdMissing(); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!isAreaIdMissing()) {
            throw new IllegalStateException();
        }
        super.areaId = this.blockingCP;
    }

    public boolean isBlocked() {
        return (super.areaId == this.blockingCP);
    }

    public void replaceBlockedAreaId(int id) {
//        assert ((m_areaId == blockingCP) && (id >= 1)); /* Assertions shouldn't be used in public methods as validation even though these methods are used by BWEM's internals. */
        if (!((super.areaId == this.blockingCP) && (id >= 1))) {
            throw new IllegalStateException();
        }
        super.areaId = id;
    }

}
