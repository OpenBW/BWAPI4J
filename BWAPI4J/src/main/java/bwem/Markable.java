/*
Status: Ready for use
*/

package bwem;

// utils.h
//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Markable
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
//  Provides efficient marking ability.
//
//  Usage: class MyNode : (public) Markable<MyNode, unsigned> {...};
//
//  Note: This implementation uses a static member.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Markable<Derived> {

    private int m_lastMark;
    private static int m_currentMark = 0;

    public Markable() {
        m_lastMark = 0;
    }

    public boolean Marked() {
        return (m_lastMark == m_currentMark);
    }

    public void SetMarked() {
        m_lastMark = m_currentMark;
    }

    public void SetUnmarked() {
        m_lastMark = m_currentMark - 1;
    }

    public static void UnmarkAll() {
        ++m_currentMark;
    }

}
