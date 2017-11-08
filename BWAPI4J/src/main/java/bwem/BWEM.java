package bwem;

import bwem.map.MapImpl;
import bwem.map.Map;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final BW m_pBW;
    private final Map m_pMap;

    public BWEM(BW bw) {
        m_pBW = bw;
        m_pMap = new MapImpl(m_pBW.getBWMap(), m_pBW.getMapDrawer(), m_pBW.getAllPlayers(), m_pBW.getMineralPatches(), m_pBW.getVespeneGeysers(), m_pBW.getUnits(m_pBW.getInteractionHandler().self()));
    }

    public Map GetMap() {
        return m_pMap;
    }

}
