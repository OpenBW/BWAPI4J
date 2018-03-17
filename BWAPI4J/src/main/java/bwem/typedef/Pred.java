package bwem.typedef;

@FunctionalInterface
public interface Pred {

    boolean isTrue(final Object... args);

}
