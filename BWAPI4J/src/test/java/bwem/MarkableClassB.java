package bwem;

public class MarkableClassB {

    private static final StaticMarkable staticMarkable = new StaticMarkable();
    private final Markable markable;

    private final int val;

    public MarkableClassB(final int val) {
        this.markable = new Markable(staticMarkable);
        this.val = val;
    }

    public static StaticMarkable getStaticMarkable() {
        return staticMarkable;
    }

    public Markable getMarkable() {
        return this.markable;
    }

    public int intValue() {
        return this.val;
    }

}
