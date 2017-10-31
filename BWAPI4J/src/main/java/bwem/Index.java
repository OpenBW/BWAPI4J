package bwem;

import java.util.Objects;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * cp.h:143:typedef int index;
 */
public final class Index implements IWrappedInteger<Index>, Comparable<Index> {

    private final int val;

    public Index(int val) {
        this.val = val;
    }

    public Index(Index index) {
        this.val = index.val;
    }

    @Override
    public Index add(Index that) {
        return new Index(this.val + that.val);
    }


    @Override
    public Index add(int val) {
        return new Index(this.val + val);
    }

    @Override
    public Index subtract(Index that) {
        return new Index(this.val - that.val);
    }

    @Override
    public Index subtract(int val) {
        return new Index(this.val - val);
    }

    @Override
    public int intValue() {
        return this.val;
    }

    @Override
    public int compareTo(Index that) {
        int lhs = this.val;
        int rhs = that.val;
        return (lhs < rhs) ? -1 : (lhs > rhs) ? 1 : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Index)) {
            return false;
        } else {
            Index that = (Index) object;
            return (this.val == that.val);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.val);
    }

}
