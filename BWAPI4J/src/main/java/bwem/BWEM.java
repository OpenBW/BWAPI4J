package bwem;

import bwem.map.Map;
import bwem.map.MapInitializerImpl;
import bwem.map.MapInitializer;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final Map pMap;

    public BWEM(final BW bw) {
        pMap = new MapInitializerImpl(
                bw.getBWMap(),
                bw.getMapDrawer(),
                bw.getAllPlayers(),
                bw.getMineralPatches(),
                bw.getVespeneGeysers(),
                bw.getAllUnits()
        );
    }

    public Map getMap() {
        return pMap;
    }

    public void initialize() {
        if (!(this.pMap instanceof MapInitializer)) {
            throw new IllegalStateException("BWEM initialization failed.");
        } else {
            MapInitializer map = (MapInitializer) this .pMap;
            map.initialize();
        }
    }

}
