package bwem;

import java.util.List;
import java.util.Objects;

public class Area {

    private Id areaId = null;
    private GroupId groupId = null;
    private List<Chokepoint> chokepoints = null;

    private Area() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Id getAreaId() {
        return new Id(this.areaId);
    }

    public GroupId getGroupId() {
        return new GroupId(this.groupId);
    }

    public boolean isAccessibleFrom(Area that) {
        return (this.groupId.val == that.groupId.val);
    }

    public List<Chokepoint> getChokepoints() {
        return this.chokepoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Area)) {
            return false;
        } else {
            Area that = (Area) o;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Area.Id)) {
                return false;
            } else {
                Area.Id that = (Area.Id) o;
                return this.val == that.val;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (!(o instanceof Area.GroupId)) {
                return false;
            } else {
                Area.GroupId that = (Area.GroupId) o;
                return this.val == that.val;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.val);
        }

    }

}
