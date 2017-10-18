package bwem;

public interface IWrappedInteger<T extends IWrappedInteger> {

    public T add(T that);

    public T subtract(T that);

    public int intValue();

    public int compareTo(T that);

}
