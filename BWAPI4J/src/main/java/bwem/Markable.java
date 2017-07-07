package bwem;

public class Markable<Derived> {

    private int lastMark = 0;
    private static int currentMark = 0;

    public boolean isMarked() {
        return this.lastMark == Markable.currentMark;
    }

    public void setMarked() {
        this.lastMark = Markable.currentMark;
    }

    public void setUnmarked() {
        this.lastMark = Markable.currentMark - 1;
    }

    public static void UnmarkAll() {
        Markable.currentMark++;
    }

}
