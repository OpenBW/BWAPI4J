package bwem;

import bwem.map.MapImpl;
import bwem.map.Map;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final Map m_pMap;

    public BWEM(final BW bw) {
        m_pMap = new MapImpl(
                bw.getBWMap(),
                bw.getMapDrawer(),
                bw.getAllPlayers(),
                bw.getMineralPatches(),
                bw.getVespeneGeysers(),
                bw.getAllUnits()
        );
    }

    public Map GetMap() {
        return m_pMap;
    }

}
