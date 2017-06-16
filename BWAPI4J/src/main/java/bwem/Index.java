package bwem;

/**
 * Immutable wrapper for the integer primitive to satisfty
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

}
