package bwem.util;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Timer
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////

public class Timer {

    private long m_start;

    public Timer() {
        Reset();
    }

    public void Reset() {
        m_start = System.currentTimeMillis();
    }

    public long ElapsedMilliseconds() {
        return (System.currentTimeMillis() - m_start);
    }

}
