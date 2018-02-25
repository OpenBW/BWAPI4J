package bwem.area.typedef;

import bwem.util.IWrappedInteger;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * area.h:56:typedef int16_t groupId;
 */
public final class GroupId implements IWrappedInteger<GroupId>, Comparable<GroupId> {

    private final int val;

    public GroupId(final int val) {
        this.val = val;
    }

    public GroupId(final GroupId groupId) {
        this.val = groupId.val;
    }

    @Override
    public GroupId add(final GroupId that) {
        return new GroupId(this.val + that.val);
    }

    @Override
    public GroupId add(final int val) {
        return new GroupId(this.val + val);
    }

    @Override
    public GroupId subtract(final GroupId that) {
        return new GroupId(this.val - that.val);
    }

    @Override
    public GroupId subtract(final int val) {
        return new GroupId(this.val - val);
    }

    @Override
    public int intValue() {
        return this.val;
    }

    @Override
    public int compareTo(final GroupId that) {
        return Integer.compare(this.val, that.val);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof GroupId)) {
            return false;
        } else {
            final GroupId that = (GroupId) object;
            return (this.val == that.val);
        }
    }

    @Override
    public int hashCode() {
        return this.val;
    }

}
