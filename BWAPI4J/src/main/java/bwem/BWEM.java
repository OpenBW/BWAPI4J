package bwem;

import bwem.map.Map;
import bwem.map.MapInitializerImpl;
import bwem.map.MapInitializer;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final Map map;

    public BWEM(final BW bw) {
        map = new MapInitializerImpl(
                bw.getBWMap(),
                bw.getMapDrawer(),
                bw.getAllPlayers(),
                bw.getMineralPatches(),
                bw.getVespeneGeysers(),
                bw.getAllUnits()
        );
    }

    public Map getMap() {
        return map;
    }

    public void initialize() {
        if (!(this.map instanceof MapInitializer)) {
            throw new IllegalStateException("BWEM initialization failed.");
        } else {
            MapInitializer map = (MapInitializer) this.map;
            map.initialize();
        }
    }

}
