package bwem.util;

public interface IWrappedInteger<T extends IWrappedInteger> {

    T add(T that);

    T add(int val);

    T subtract(T that);

    T subtract(int val);

    int intValue();

    int compareTo(T that);

    boolean equals(Object that);

    int hashCode();

    String toString();

}
