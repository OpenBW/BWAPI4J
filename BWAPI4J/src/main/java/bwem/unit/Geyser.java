package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Geyser
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Geysers Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType() == Resource_Vespene_Geyser
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Geyser extends Resource {

    public Geyser(Unit u, Map pMap) {
        super(u, pMap);

//        bwem_assert(Type() == Resource_Vespene_Geyser);
        if (!(u instanceof VespeneGeyser)) {
            throw new IllegalArgumentException("Unit is not a VespeneGeyser: " + u.getClass().getName());
        }
    }

    @Override
    public int getInitialAmount() {
        VespeneGeyser ret = (VespeneGeyser) super.getUnit();
        return ret.getInitialResources();
    }

    @Override
    public int getAmount() {
        VespeneGeyser ret = (VespeneGeyser) super.getUnit();
        return ret.getResources();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Geyser)) {
            return false;
        } else {
            Geyser that = (Geyser) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
