package bwem.typedef;

import bwem.util.IWrappedInteger;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * cp.h:143:typedef int index;
 */
public final class Index implements IWrappedInteger<Index>, Comparable<Index> {

    private final int val;

    public Index(final int val) {
        this.val = val;
    }

    public Index(final Index index) {
        this.val = index.val;
    }

    public Index add(final int val) {
        return new Index(this.val + val);
    }

    @Override
    public int intValue() {
        return this.val;
    }

    @Override
    public int compareTo(final Index that) {
        return Integer.compare(this.val, that.val);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Index)) {
            return false;
        } else {
            final Index that = (Index) object;
            return (this.val == that.val);
        }
    }

    @Override
    public int hashCode() {
        return this.val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }

}
