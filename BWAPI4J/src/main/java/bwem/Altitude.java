package bwem;

/**
 * Immutable wrapper for the integer primitive to satisfty
 * the original C++ definition:
 * defs.h:54:typedef int16_t altitude_t;
 */
public final class Altitude {

    private final int val;

    private Altitude() throws InstantiationException {
        throw new InstantiationException("Parameterless instantiation is prohibited.");
    }

    public Altitude(int val) {
        this.val = val;
    }

    public Altitude(Altitude a) {
        this.val = a.toInt();
    }

    public Altitude add(Altitude altitude) {
        return new Altitude(this.val + altitude.toInt());
    }

    public Altitude subtract(Altitude altitude) {
        return new Altitude(this.val - altitude.toInt());
    }

    /**
     * Returns the value of the underlying type.
     */
    public int toInt() {
        return this.val;
    }

}
