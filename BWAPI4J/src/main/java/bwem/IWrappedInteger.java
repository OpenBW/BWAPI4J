package bwem;

public interface IWrappedInteger<T extends IWrappedInteger> {

    public T add(T that);
    public T subtract(T that);
    public int toInt();
    public int compareTo(T that);

}
