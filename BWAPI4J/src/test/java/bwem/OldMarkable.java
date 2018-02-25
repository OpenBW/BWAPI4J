package bwem;

public class OldMarkable<Derived> {

    private int lastMark;
    private static int currentMark = 0;

    public OldMarkable() {
        lastMark = 0;
    }

    public boolean Marked() {
        return  (lastMark == currentMark);
    }

    public void SetMarked() {
        lastMark = currentMark;
    }

    public void SetUnmarked() {
        lastMark = currentMark - 1;
    }

    public static void UnmarkAll() {
        ++currentMark;
    }

}
