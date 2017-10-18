/*
Status: Incomplete
*/

package bwem;

import bwem.map.Map;
import bwem.map.MapImpl;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final BW m_pBW;
    private final Map m_pMap;

    public BWEM(BW bw) {
        m_pBW = bw;
        m_pMap = new MapImpl(m_pBW);
    }

    public Map GetMap() {
        return m_pMap;
    }

}
