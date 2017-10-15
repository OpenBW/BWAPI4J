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
        return elapsedMilliseconds();
    }

    public long elapsedMilliseconds() {
        return (this.end - this.start);
    }

    public void reset() {
        this.start = 0;
        this.end = 0;
    }

}
