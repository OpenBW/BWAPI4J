package bwem.util;

public interface IWrappedInteger<T extends IWrappedInteger> {

    public T add(T that);

    public T add(int val);

    public T subtract(T that);

    public T subtract(int val);

    public int intValue();

    public int compareTo(T that);

}
