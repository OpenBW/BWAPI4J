package bwem;

public class OldMarkable<Derived> {

    private int m_lastMark;
    private static int m_currentMark = 0;

    public OldMarkable() {
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
