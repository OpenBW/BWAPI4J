package bwem.unit;

import bwem.Map;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class Resource extends Neutral {

    private int initialAmount;

    public Resource(Unit unit, Map map) {
        super(unit, map);

        if (getUnit() instanceof MineralPatch) {
            MineralPatch patch = (MineralPatch) getUnit();
            this.initialAmount = patch.getInitialResources();
        } else if (getUnit() instanceof VespeneGeyser) {
            VespeneGeyser geyser = (VespeneGeyser) getUnit();
            this.initialAmount = geyser.getInitialResources();
        } else {
            throw new UnsupportedOperationException("unsupported resource type");
        }
    }

    public int getInitialAmount() {
        return this.initialAmount;
    }

    public int getAmount() {
        if (getUnit() instanceof MineralPatch) {
            MineralPatch patch = (MineralPatch) getUnit();
            return patch.getResources();
        } else if (getUnit() instanceof VespeneGeyser) {
            VespeneGeyser geyser = (VespeneGeyser) getUnit();
            return geyser.getResources();
        } else {
            throw new UnsupportedOperationException("unsupported resource type");
        }
    }

}
