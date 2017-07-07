package bwem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class Area {

    private Map map;
    private Id areaId;
    private GroupId groupId;
    private TilePosition topLeft;
    private TilePosition bottomRight;
    private WalkPosition top;
    private List<Chokepoint> chokepoints;
    private ConcurrentHashMap<Area, List<Chokepoint>> chokepointsByArea;
    private int tileCount;
    private int miniTileCount;
    private int buildableTiles;
    private int highGroundTiles;
    private int veryHighGroundTiles;
    private Altitude maxAltitude;

    /**
     * Disabled default constructor.
     */
    private Area() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    /**
     * Class constructor.
     *
     * @param map The map to use by class methods.
     * @param areaId The id for this object.
     * @param top //TODO: What is this? How is this different from maximum altitude?
     * @param miniTileCount the number of mini tiles in this area.
     * @throws IllegalArgumentException
     *     - If the specified area ID is invalid.<br>
     *     - If the specified top area ID does not match the specified area ID.<br>
     */
    public Area(Map map, Area.Id areaId, WalkPosition top, int miniTileCount) {
        this.chokepoints = new ArrayList<>();
        this.chokepointsByArea = new ConcurrentHashMap<>();

        this.map = map;
        this.areaId = new Area.Id(areaId);
        this.top = new WalkPosition(top.getX(), top.getY());
        this.miniTileCount = miniTileCount;

        this.tileCount = 0;
        this.buildableTiles = 0;
        this.highGroundTiles = 0;
        this.veryHighGroundTiles = 0;

        this.topLeft = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.bottomRight = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);

//        bwem_assert(areaId > 0);
        if (!(this.areaId.intValue() > 0)) {
            throw new IllegalArgumentException("invalid Area.Id");
        }

        MiniTile topMiniTile = this.map.getMiniTile(top);
//        bwem_assert(topMiniTile.AreaId() == areaId);
        if (!(topMiniTile.getAreaId().equals(areaId))) {
            throw new IllegalArgumentException();
        }

        this.maxAltitude = new Altitude(topMiniTile.getAltitude());
    }

    /**
     * Unique id > 0 of this Area. Range = 1 .. Map::Areas().size()<br>
     * this == Map::GetArea(Id())<br>
     * Id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.<br>
     * Area::ids are guaranteed to remain unchanged.<br>
     */
    public Id getAreaId() {
        return new Id(this.areaId);
    }

    /**
     * Unique id > 0 of the group of Areas which are accessible from this Area.<br>
     * For each pair (a, b) of Areas: a->GroupId() == b->GroupId() &lt;==&gt;  a->AccessibleFrom(b)<br>
     * A groupId uniquely identifies a maximum set of mutually accessible Areas, that is, in the absence of blocking ChokePoints, a continent.<br>
     */
    public GroupId getGroupId() {
        return new GroupId(this.groupId);
    }

    /**
     * @param groupId The specified group ID.
     * @throws IllegalArgumentException If the specified group ID is invalid.
     */
    public void setGroupId(GroupId groupId) {
//        { bwem_assert(gid >= 1); m_groupId = gid; }
        if (!(groupId.intValue() >= 1)) {
            throw new IllegalArgumentException("invalid Area.GroupId");
        }
        this.groupId = new GroupId(groupId);
    }

    public TilePosition getTopLeft() {
        return new TilePosition(this.topLeft.getX(), this.topLeft.getY());
    }

    public TilePosition getBottomRight() {
        return new TilePosition(this.bottomRight.getX(), this.bottomRight.getY());
    }

    public TilePosition getBoundingBoxSize() {
        return this.bottomRight.subtract(this.topLeft).add(new TilePosition(1, 1));
    }

    //////////////////////////////////////////////////////////////////////
    //TODO: Double-check these two methods.
    //////////////////////////////////////////////////////////////////////

    /**
     * Returns the position of the MiniTile with the highest Altitude value.
     * (Formerly known as "Top()" or "getTop".)
     */
    public WalkPosition getTop() {
        return this.top;
    }

    /**
     * Returns Map::GetMiniTile(Top()).Altitude().
     */
    //TODO: It does? ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public Altitude getMaxAltitude() {
        return new Altitude(this.maxAltitude);
    }

    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////

    /**
     * Returns the number of MiniTiles in this Area.
     * This most accurately defines the size of this Area.
     */
    //TODO: Rename this function to `size()'?
    public int getMiniTileCount() {
        return this.miniTileCount;
    }

    public boolean isAccessibleFrom(Area that) {
        return (this.groupId.val == that.groupId.val);
    }

    public List<Chokepoint> getChokepoints() {
        return this.chokepoints;
    }

    public void addChokepoints(Area area, List<Chokepoint> chokepoints) {
//        bwem_assert(!m_ChokePointsByArea[pArea] && pChokePoints);
        if (this.chokepointsByArea.get(area) != null) {
            throw new IllegalArgumentException("area already exists");
        } else if (chokepoints == null) {
            throw new IllegalArgumentException("chokepoints cannot be null");
        }

        this.chokepointsByArea.put(area, chokepoints);

        for (Chokepoint cp : chokepoints) {
            this.chokepoints.add(cp);
        }
    }

    List<Integer> computeDistances(Chokepoint startCP, List<Chokepoint> targetCPs) {
//        bwem_assert(!contains(TargetCPs, pStartCP));
        if (targetCPs.contains(startCP)) {
            throw new IllegalStateException();
        }

        TilePosition start = this.map.breadthFirstSearch(
            startCP.getPositionInArea(Chokepoint.Node.Middle, this).toPosition().toTilePosition(),
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        Tile tile = (Tile) ttile;
                        return (tile.getAreaId().intValue() == getAreaId().intValue());
                    } else {
                        throw new IllegalArgumentException("Invalid tile type.");
                    }
                }
            },
            new Pred() {
                @Override
                public boolean is(Object... args) {
                    return true;
                }
            }
        );

        List<TilePosition> targets = new ArrayList<>();
        for (Chokepoint cp : targetCPs) {
            TilePosition t = this.map.breadthFirstSearch(
                cp.getPositionInArea(Chokepoint.Node.Middle, this).toPosition().toTilePosition(),
                new Pred() {
                    @Override
                    public boolean is(Object... args) {
                        Object ttile = args[0];
                        if (ttile instanceof Tile) {
                            Tile tile = (Tile) ttile;
                            return (tile.getAreaId().intValue() == getAreaId().intValue());
                        } else {
                            throw new IllegalArgumentException("Invalid Tile object.");
                        }
                    }
                },
                new Pred() {
                    @Override
                    public boolean is(Object... args) {
                        return true;
                    }
                }
            );
            targets.add(t);
        }

        return computeDistances(start, targets);
    }

    private List<Integer> computeDistances(TilePosition start, List<TilePosition> targets ) {
        List<Integer> distances = new ArrayList<>();

        Tile.UnmarkAll();

        MultiValuedMap<Integer, TilePosition> toVisit = new ArrayListValuedHashMap<>(); //Using ArrayListValuedHashMap to substitute std::multimap since it sorts keys but not values.
        toVisit.put(0, start);

        int remainingTargets = targets.size();
        while (!toVisit.isEmpty()) {
//            int currentDist = ToVisit.begin()->first;
            int currentDist = toVisit.mapIterator().getKey();

//            TilePosition current = ToVisit.begin()->second;
            TilePosition current = toVisit.mapIterator().getValue();

            Tile currentTile = this.map.getTile(current, CheckMode.NoCheck);
//            bwem_assert(currentTile.InternalData() == currentDist);
            if (!(currentTile.getUserData().getData() == currentDist)) {
                throw new IllegalStateException();
            }

//            ToVisit.erase(ToVisit.begin());
            toVisit.removeMapping(toVisit.mapIterator().getKey(), toVisit.mapIterator().getValue());

            currentTile.getUserData().setData(0);
            currentTile.setMarked();

            for (int i = 0; i < targets.size(); i++) {
                if (current.equals(targets.get(i))) {
                    distances.set(i, (int) (Double.valueOf("0.5") + (Double.valueOf(currentDist) * Double.valueOf("32")) / Double.valueOf("10000")));
                    --remainingTargets;
                }
            }
            if (remainingTargets == 0) {
                break;
            }

            TilePosition[] deltas = {
                new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(1, -1),
                new TilePosition(-1,  0),                          new TilePosition(1,  0),
                new TilePosition(-1,  1), new TilePosition(0,  1), new TilePosition(1,  1)
            };
            for (TilePosition delta : deltas) {
                boolean diagonalMove = (delta.getX() != 0) && (delta.getY() != 0);
                int newNextDist = currentDist + (diagonalMove ? 14142 : 10000);

                TilePosition next = current.add(delta);
                if (this.map.isValid(next)) {
                    Tile nextTile = this.map.getTile(next, CheckMode.NoCheck);
                    if (nextTile.isMarked()) {
                        if (nextTile.getUserData().getData() != 0) {
                            // next already in ToVisit
                            if (newNextDist < nextTile.getUserData().getData()) { // nextNewDist < nextOldDist
                                // To update next's distance, we need to remove-insert it from ToVisit:
                                Collection<TilePosition> range = toVisit.get(nextTile.getUserData().getData());
                                //TODO
                            }
                        }
                    }
                }
            }
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * - Tests whether the specified object is equal to this object.<br>
     * - Tests whether the specified object is an instance of this class.<br>
     * - Tests if the internal Area.Id values match.<br>
     *
     * @param object The specified object to test against this object.
     */
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
    public static class Id implements IWrappedInteger<Id>, Comparable<Id> {

        private int val;

        private Id() {
            throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
        }

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

        /**
         * - Tests whether the specified object is equal to this object.<br>
         * - Tests whether the specified object is an instance of this class.<br>
         * - Tests if the internal integer values match.<br>
         *
         * @param object The specified object to test against this object.
         */
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
    public static class GroupId implements IWrappedInteger<GroupId>, Comparable<GroupId> {

        private int val;

        private GroupId() {}

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

        /**
         * - Tests whether the specified object is equal to this object.<br>
         * - Tests whether the specified object is an instance of this class.<br>
         * - Tests if the internal integer values match.<br>
         *
         * @param object The specified object to test against this object.
         */
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

    /**
     * Helper class for void Map::ComputeAreas()
     * Maintains some information about an area being computed.
     * A TempAreaInfo is not Valid() in two cases:
     * - a default-constructed TempAreaInfo instance is never Valid (used as a dummy value to simplify the algorithm).
     * - any other instance becomes invalid when absorbed (see Merge)
     */
    public static class TempInfo {

        private boolean isValid;
        private Area.Id areaId;
        private WalkPosition top;
        private Altitude highestAltitude;
        private int size;

        public TempInfo() {
            this.isValid = false;
            this.areaId = new Area.Id(0);
            this.top = new WalkPosition(0, 0);

//            bwem_assert(!Valid());
        }

//        TempAreaInfo(Area::id id, MiniTile * pMiniTile, WalkPosition pos)
//                : m_valid(true), m_id(id), m_top(pos), m_size(0), m_highestAltitude(pMiniTile->Altitude())
//                                            { Add(pMiniTile); bwem_assert(Valid()); }
        public TempInfo(Area.Id areaId, MiniTile miniTile, WalkPosition w) {
            this.isValid = true;
            this.areaId = areaId;
            this.top = w;
            this.size = 0;
            this.highestAltitude = miniTile.getAltitude();

            add(miniTile);

            /*
                TODO: Further investigation required. I don't think this
                will ever fail here but it's included just in case and to stay
                true to the original code.
            */
            if (!this.isValid) {
                throw new IllegalStateException();
            }
        }

        public boolean isValid() {
            return this.isValid;
        }

        public Area.Id getId() {
//            { bwem_assert(Valid()); return m_id; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.areaId;
        }

        public WalkPosition getTop()  {
//            { bwem_assert(Valid()); return m_top; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.top;
        }

        public int getSize() {
//            { bwem_assert(Valid()); return m_size; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.size;
        }

        public Altitude getHighestAltitude() {
//            { bwem_assert(Valid()); return m_highestAltitude; }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            return this.highestAltitude;
        }

        public void add(MiniTile miniTile) {
//            { bwem_assert(Valid()); ++m_size; pMiniTile->SetAreaId(m_id); }
            if (!isValid()) {
                throw new IllegalStateException();
            }
            this.size++;
            miniTile.setAreaId(this.areaId);
        }

        public void merge(TempInfo absorbed) {
//            bwem_assert(Valid() && Absorbed.Valid());
//            bwem_assert(m_size >= Absorbed.m_size);
            if (!(this.isValid && absorbed.isValid)) {
                throw new IllegalStateException("failed: isValid");
            } else if (!(this.size >= absorbed.size)) {
                throw new IllegalArgumentException("invalid TempInfo size");
            } else {
                this.size += absorbed.size;
                absorbed.isValid = false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.areaId.intValue());
        }

    }

}
