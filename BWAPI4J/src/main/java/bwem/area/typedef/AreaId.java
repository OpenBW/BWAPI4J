package bwem.area.typedef;

import bwem.util.IWrappedInteger;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * area.h:54:typedef int16_t id;
 */
public final class AreaId implements IWrappedInteger<AreaId>, Comparable<AreaId> {

    private final int val;

    public AreaId(final int val) {
        this.val = val;
    }

    @Override
    public int intValue() {
        return this.val;
    }

    @Override
    public int compareTo(final AreaId that) {
        return Integer.compare(this.val, that.val);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof AreaId)) {
            return false;
        } else {
            final AreaId that = (AreaId) object;
            return (this.val == that.val);
        }
    }

    @Override
    public int hashCode() {
        return this.val;
    }

}
