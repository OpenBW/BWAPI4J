package bwem.area.typedef;

import bwem.util.IWrappedInteger;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * area.h:56:typedef int16_t groupId;
 */
public final class GroupId implements IWrappedInteger<GroupId>, Comparable<GroupId> {
    public static final GroupId ZERO = new GroupId(0);
    private final int val;

    public GroupId(final int val) {
        this.val = val;
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
