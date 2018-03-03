package bwem.util;

/**
 * This timer class is similar to the one provided by the original C++ BWEM.
 */
public class Timer {

    private long start;

    public Timer() {
        reset();
    }

    public void reset() {
        this.start = now();
    }

    public long elapsedMilliseconds() {
        return (now() - this.start);
    }

    private long now() {
        return System.currentTimeMillis();
    }

}
