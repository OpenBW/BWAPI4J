package bwem;

/**
 * See original C++ BWEM for an explanation of this code.
 * Do NOT mimic BWEM's C++ inheritance for this code.
 * See "src/test/util/OldMarkable.java" for what NOT to do.
 */
public final class Markable {

    private final StaticMarkable staticMarkable;
    private int lastMark;

    public Markable(final StaticMarkable staticMarkable) {
        this.staticMarkable = staticMarkable;
        this.lastMark = 0;
    }

    private StaticMarkable getStaticMarkable() {
        return this.staticMarkable;
    }

    public boolean isMarked() {
        return (this.lastMark == getStaticMarkable().getCurrentMark());
    }

    public void setMarked() {
        this.lastMark = getStaticMarkable().getCurrentMark();
    }

    public void setUnmarked() {
        this.lastMark = getStaticMarkable().getCurrentMark() - 1;
    }

}
