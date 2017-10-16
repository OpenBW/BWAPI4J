package bwem;

import bwem.map.MapImpl;
import bwem.map.Map;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final BW bw;
    private final Map map;

    public BWEM(BW bw) {
        this.bw = bw;
        this.map = new MapImpl(this.bw);
    }

    public Map getMap() {
        return this.map;
    }

}
