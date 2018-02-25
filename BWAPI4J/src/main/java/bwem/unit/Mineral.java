package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Mineral
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Minerals Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType().isMineralField(),
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Mineral extends Resource {

    public Mineral(Unit u, Map map) {
        super(u, map);

//        bwem_assert(Type().isMineralField());
        if (!(u instanceof MineralPatch)) {
            throw new IllegalArgumentException("Unit is not a MineralPatch: " + u.getClass().getName());
        }
    }

    @Override
    public int getInitialAmount() {
        MineralPatch ret = (MineralPatch) super.getUnit();
        return ret.getInitialResources();
    }

    @Override
    public int getAmount() {
        MineralPatch ret = (MineralPatch) super.getUnit();
        return ret.getResources();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Mineral)) {
            return false;
        } else {
            Mineral that = (Mineral) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
