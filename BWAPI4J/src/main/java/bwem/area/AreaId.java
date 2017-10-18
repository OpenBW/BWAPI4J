package bwem.area;

import bwem.IWrappedInteger;
import java.util.Objects;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * area.h:54:typedef int16_t id;
 */
public final class AreaId implements IWrappedInteger<AreaId>, Comparable<AreaId> {

    private final int val;

    public AreaId(int val) {
        this.val = val;
    }

    public AreaId(AreaId areaId) {
        this.val = areaId.val;
    }

    @Override
    public AreaId add(AreaId that) {
        return new AreaId(this.val + that.val);
    }

    @Override
    public AreaId subtract(AreaId that) {
        return new AreaId(this.val + that.val);
    }

    @Override
    public int intValue() {
        return this.val;
    }

    @Override
    public int compareTo(AreaId that) {
        int lhs = this.val;
        int rhs = that.val;
        return (lhs < rhs) ? -1 : (lhs > rhs) ? 1 : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof AreaId)) {
            throw new IllegalArgumentException("Object is not an instance of AreaId");
        } else {
            AreaId that = (AreaId) object;
            return (this.val == that.val);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.val);
    }

    public static boolean isNullOrZero(AreaId id) {
        return (id == null || id.intValue() == 0);
    }

}