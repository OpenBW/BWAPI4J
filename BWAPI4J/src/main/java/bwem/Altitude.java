package bwem;

import java.util.Objects;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * defs.h:54:typedef int16_t altitude_t;
 */
public final class Altitude implements IWrappedInteger<Altitude>, Comparable<Altitude> {

    private final int val;

    private Altitude() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Altitude(int val) {
        this.val = val;
    }

    public Altitude(Altitude altitude) {
        this.val = altitude.val;
    }

    @Override
    public Altitude add(Altitude that) {
        return new Altitude(this.val + that.val);
    }

    @Override
    public Altitude subtract(Altitude that) {
        return new Altitude(this.val - that.val);
    }

    @Override
    public int intValue() {
        return this.val;
    }

    @Override
    public int compareTo(Altitude that) {
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
        } else if (!(object instanceof Altitude)) {
            throw new IllegalArgumentException("object is not an instance of Altitude");
        } else {
            Altitude that = (Altitude) object;
            return (this.val == that.val);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.val);
    }

}
