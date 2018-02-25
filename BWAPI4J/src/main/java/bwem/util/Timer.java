package bwem.util;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Timer
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////

public class Timer {

    private long start;

    public Timer() {
        reset();
    }

    public void reset() {
        start = System.currentTimeMillis();
    }

    public long elapsedMilliseconds() {
        return (System.currentTimeMillis() - start);
    }

}
