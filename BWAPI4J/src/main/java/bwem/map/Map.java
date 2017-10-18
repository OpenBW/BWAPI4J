/*
Status: Incomplete
*/

package bwem.map;

import org.openbw.bwapi4j.BW;

public abstract class Map {

    private final BW m_pBW;

    protected Map(BW bw) {
        m_pBW = bw;
    }

    protected BW getBW() {
        return m_pBW;
    }

    public abstract void initialize();

}
