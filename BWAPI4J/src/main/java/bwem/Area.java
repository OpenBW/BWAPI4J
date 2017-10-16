package bwem;

import java.util.List;
import java.util.Objects;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Area
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Areas are regions that BWEM automatically computes from Brood War's maps
// Areas aim at capturing relevant regions that can be walked, though they may contain small inner non walkable regions called lakes.
// More formally:
//  - An area consists in a set of 4-connected MiniTiles, which are either Terrain-MiniTiles or Lake-MiniTiles.
//  - An Area is delimited by the side of the Map, by Water-MiniTiles, or by other Areas. In the latter case
//    the adjoining Areas are called neighbouring Areas, and each pair of such Areas defines at least one ChokePoint.
// Like ChokePoints and Bases, the number and the addresses of Area instances remain unchanged.
// To access Areas one can use their ids or their addresses with equivalent efficiency.
//
// Areas inherit utils::Markable, which provides marking ability
// Areas inherit utils::UserData, which provides free-to-use data.
public final class Area {

    private Area.Id areaId;
    private Area.GroupId groupId = new Area.GroupId(0);
    private WalkPosition top;
    private TilePosition topleft = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private TilePosition bottomRight = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private Altitude maxAltitude;
    private int miniTileCount = 0;
    private int tileCount = 0;
    private int buildableTileCount = 0;
    private int highGroundTileCount = 0;
    private int veryHighGroundTileCount = 0;
    private List<Area> accessibleNeighbors;

	// Unique id > 0 of this Area. Range = 1 .. Map::Areas().size()
	// this == Map::GetArea(Id())
	// Id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.
	// Area::ids are guaranteed to remain unchanged.
    public Area.Id getId() {
        return new Area.Id(this.areaId);
    }

	// Unique id > 0 of the group of Areas which are accessible from this Area.
	// For each pair (a, b) of Areas: a->GroupId() == b->GroupId()  <==>  a->AccessibleFrom(b)
	// A groupId uniquely identifies a maximum set of mutually accessible Areas, that is, in the absence of blocking ChokePoints, a continent.
    public Area.GroupId getGroupId() {
        return new Area.GroupId(this.groupId);
    }

    public void setGroupId(Area.GroupId groupId) {
//        { bwem_assert(gid >= 1); m_groupId = gid; }
        if (!(groupId.intValue() >= 1)) {
            throw new IllegalArgumentException();
        } else {
            this.groupId = new Area.GroupId(groupId);
        }
    }

    public TilePosition getTopLeft() {
        return new TilePosition(this.topleft.getX(), this.topleft.getY());
    }

    public TilePosition getBottomRight() {
        return new TilePosition(this.bottomRight.getX(), this.bottomRight.getY());
    }

    public TilePosition getBoundingBoxSize() {
        return (this.bottomRight.subtract(this.topleft).add(new TilePosition(1, 1)));
    }

    // Position of the MiniTile with the highest Altitude() value.
    public WalkPosition getTop() {
        return this.top;
    }

    //TODO: Double check this function's description is true and/or change the function and/or delete it.
    // Returns Map::GetMiniTile(Top()).Altitude().
    public Altitude getMaxAltitude() {
        return new Altitude(this.maxAltitude);
    }

	// Returns the number of MiniTiles in this Area.
	// This most accurately defines the size of this Area.
    public int getMiniTileCount() {
        return this.miniTileCount;
    }

    // Returns the percentage of low ground Tiles in this Area.
    public int getLowGroundPercentage() {
        return ((this.tileCount - this.highGroundTileCount - this.veryHighGroundTileCount) * 100 / this.tileCount);
    }
    // Returns the percentage of high ground Tiles in this Area.
    public int getHighGroundPercentage() {
        return ((this.highGroundTileCount * 100) / this.tileCount);
    }

    // Returns whether this Area is accessible from pArea, that is, if they share the same GroupId().
    // Note: accessibility is always symmetrical.
    // Note: even if a and b are neighbouring Areas,
    //       we can have: a->AccessibleFrom(b)
    //       and not:     contains(a->AccessibleNeighbours(), b)
    // See also GroupId()
    public boolean isAccessibleFrom(Area area) {
        return (getGroupId().equals(area.getGroupId()));
    }

	// Returns the accessible neighbouring Areas.
	// The accessible neighbouring Areas are a subset of the neighbouring Areas (the neighbouring Areas can be iterated using ChokePointsByArea()).
	// Two neighbouring Areas are accessible from each over if at least one the ChokePoints they share is not Blocked (Cf. ChokePoint::Blocked).
    public List<Area> getAccessibleNeighbors() {
        return this.accessibleNeighbors;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Area)) {
            throw new IllegalArgumentException("object is not an instance of Area");
        } else {
            Area that = (Area) object;
            return (this.areaId.intValue() == that.areaId.intValue());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.areaId.intValue());
    }

    /**
     * Immutable wrapper of the integer primitive to satisfy
     * the original C++ definition:
     * area.h:54:typedef int16_t id;
     */
    public static final class Id implements IWrappedInteger<Id>, Comparable<Id> {

        private final int val;

        public Id(int val) {
            this.val = val;
        }

        public Id(Id id) {
            this.val = id.val;
        }

        @Override
        public Id add(Id that) {
            return new Id(this.val + that.val);
        }

        @Override
        public Id subtract(Id that) {
            return new Id(this.val + that.val);
        }

        @Override
        public int intValue() {
            return this.val;
        }

        @Override
        public int compareTo(Id that) {
            int lhs = this.val;
            int rhs = that.val;
            return (lhs < rhs) ? -1 : (lhs > rhs) ? 1 : 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            } else if (!(object instanceof Area.Id)) {
                throw new IllegalArgumentException("object is not an instance of Area.Id");
            } else {
                Area.Id that = (Area.Id) object;
                return (this.val == that.val);
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.val);
        }

    }

    /**
     * Immutable wrapper of the integer primitive to satisfy
     * the original C++ definition:
     * area.h:56:typedef int16_t groupId;
     */
    public static final class GroupId implements IWrappedInteger<GroupId>, Comparable<GroupId> {

        private final int val;

        public GroupId(int val) {
            this.val = val;
        }

        public GroupId(GroupId groupId) {
            this.val = groupId.val;
        }

        @Override
        public GroupId add(GroupId that) {
            return new GroupId(this.val + that.val);
        }

        @Override
        public GroupId subtract(GroupId that) {
            return new GroupId(this.val - that.val);
        }

        @Override
        public int intValue() {
            return this.val;
        }

        @Override
        public int compareTo(GroupId that) {
            int lhs = this.val;
            int rhs = that.val;
            return (lhs < rhs) ? -1 : (lhs > rhs) ? 1 : 0;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            } else if (!(object instanceof Area.GroupId)) {
                throw new IllegalArgumentException("object is not an instance of Area.GroupId");
            } else {
                Area.GroupId that = (Area.GroupId) object;
                return (this.val == that.val);
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.val);
        }

    }

}
