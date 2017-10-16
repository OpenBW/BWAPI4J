package bwem;

public final class Timer {

    private long start;
    private long end;

    public Timer() {
        reset();
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public long stop() {
        this.end = System.currentTimeMillis();
        return getElapsedMilliseconds();
    }

    public long getElapsedMilliseconds() {
        return (System.currentTimeMillis() - this.start);
    }

    public void reset() {
        start();
        this.end = this.start;
    }

}
