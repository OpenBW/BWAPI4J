package bwem;

/**
 * See original C++ BWEM for an explanation of this code.
 * Do NOT mimic BWEM's C++ inheritence for this code.
 * See "src/test/util/OldMarkable.java" for what NOT to do.
 */
public final class StaticMarkable {

    private int currentMark;

    public StaticMarkable() {
        this.currentMark = 0;
    }

    public int getCurrentMark() {
        return this.currentMark;
    }

    public void unmarkAll() {
        ++this.currentMark;
    }

}
