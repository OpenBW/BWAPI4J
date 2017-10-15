package bwem.unit;

import bwem.Map;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;

public class Mineral extends Resource {

    public Mineral(Unit unit, Map map) {
        super(unit, map);

        if (!(unit instanceof MineralPatch)) {
            throw new IllegalStateException();
        }
    }

}
