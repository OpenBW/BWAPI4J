package bwem;

import java.util.Objects;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * cp.h:143:typedef int index;
 */
public class Index implements IWrappedInteger<Index>, Comparable<Index> {

    private int val;

    private Index() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

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
    public Index subtract(Index that) {
        return new Index(this.val - that.val);
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

    /**
     * <p>
     * - Tests whether the specified object is equal to this object.<br/>
     * - Tests whether the specified object is an instance of this class.<br/>
     * - Tests if the internal integer values match.<br/>
     * </p>
     *
     * @param object The specified object to test against this object.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Index)) {
            throw new IllegalArgumentException("object is not an instance of Index");
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
