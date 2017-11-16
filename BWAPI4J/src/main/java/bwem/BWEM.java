package bwem;

import bwem.map.Map;
import bwem.map.MapInitializerImpl;
import bwem.map.MapInitializer;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final Map m_pMap;

    public BWEM(final BW bw) {
        m_pMap = new MapInitializerImpl(
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

    public void initialize() {
        if (!(this.m_pMap instanceof MapInitializer)) {
            throw new IllegalStateException("BWEM initialization failed.");
        } else {
            MapInitializer map = (MapInitializer) this.m_pMap;
            map.Initialize();
        }
    }

}
