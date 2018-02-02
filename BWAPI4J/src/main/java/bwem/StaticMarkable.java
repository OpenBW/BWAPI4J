package bwem;

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
