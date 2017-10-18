package bwem.area;

import bwem.IWrappedInteger;
import java.util.Objects;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * area.h:56:typedef int16_t groupId;
 */
public final class GroupId implements IWrappedInteger<GroupId>, Comparable<GroupId> {

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
        } else if (!(object instanceof GroupId)) {
            throw new IllegalArgumentException("Object is not an instance of GroupId");
        } else {
            GroupId that = (GroupId) object;
            return (this.val == that.val);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.val);
    }
    
}
