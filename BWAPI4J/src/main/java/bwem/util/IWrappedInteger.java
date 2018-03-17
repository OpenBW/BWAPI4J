package bwem.util;

public interface IWrappedInteger<T extends IWrappedInteger> {

    int intValue();

    int compareTo(T that);

    boolean equals(Object that);

    int hashCode();

    String toString();

}
