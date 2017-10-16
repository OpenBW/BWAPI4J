package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class Geyser extends Resource {

    public Geyser(Unit unit, Map map) {
        super(unit, map);

        if (!(unit instanceof VespeneGeyser)) {
            throw new IllegalStateException();
        }
    }

}
