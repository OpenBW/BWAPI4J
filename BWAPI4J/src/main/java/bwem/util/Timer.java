package bwem.util;

/**
 * This timer class is similar to the one provided by the original C++ BWEM.
 */
public class Timer {

    private static final double NANOSECONDS_PER_MILLISECOND = Math.pow(10, 6);

    private long start;

    public Timer() {
        reset();
    }

    public void reset() {
        this.start = now();
    }

    public double elapsedMilliseconds() {
        return ((double) (now() - this.start)) / NANOSECONDS_PER_MILLISECOND;
    }

    private long now() {
        return System.nanoTime();
    }

}
