package bwem;

public interface IWrappedInteger<T extends IWrappedInteger> {

    /**
     * Adds the specified object to this object and returns a new object.
     *
     * @param that The specified object to add.
     */
    public T add(T that);

    /**
     * Subtracts the specified object from this object and returns a new object.
     *
     * @param that The specified object to subtract.
     */
    public T subtract(T that);

    /**
     * Returns the value of this object as an integer.
     */
    public int intValue();

    public int compareTo(T that);

}
