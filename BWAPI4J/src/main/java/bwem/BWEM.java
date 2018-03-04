package bwem;

import bwem.map.Map;
import bwem.map.MapInitializerImpl;
import bwem.map.MapInitializer;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final Map map;

    public BWEM(final BW bw) {
        this.map = new MapInitializerImpl(
                bw.getBWMap(),
                bw.getMapDrawer(),
                bw.getAllPlayers(),
                bw.getMineralPatches(),
                bw.getVespeneGeysers(),
                bw.getAllUnits()
        );
    }

    public Map getMap() {
        return this.map;
    }

    public void initialize() {
        initialize(false);
    }

    public void initialize(final boolean enableTimer) {
        if (!(this.map instanceof MapInitializer)) {
            throw new IllegalStateException("BWEM initialization failed.");
        } else {
            final MapInitializer map = (MapInitializer) this.map;
            map.initialize(enableTimer);
        }
    }

}
