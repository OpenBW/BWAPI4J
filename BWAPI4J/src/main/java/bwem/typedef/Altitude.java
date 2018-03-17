package bwem.typedef;

import bwem.util.IWrappedInteger;

/**
 * Immutable wrapper of the integer primitive to satisfy
 * the original C++ definition:
 * defs.h:54:typedef int16_t altitude_t;
 *
 * Type of the altitudes in pixels.
 */
public final class Altitude implements IWrappedInteger<Altitude>, Comparable<Altitude> {

    private final int val;

    public Altitude(final int val) {
        this.val = val;
    }

    @Override
    public int intValue() {
        return this.val;
    }

    @Override
    public int compareTo(final Altitude that) {
        return Integer.compare(this.val, that.val);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Altitude)) {
            return false;
        } else {
            final Altitude that = (Altitude) object;
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
