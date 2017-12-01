package bwem;

public final class Markable {

    private final StaticMarkable staticMarkable;
    private int lastMark;

    public Markable(StaticMarkable staticMarkable) {
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
