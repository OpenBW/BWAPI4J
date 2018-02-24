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
// geysers Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType() == Resource_Vespene_Geyser
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class Geyser extends Resource {

    public Geyser(Unit u, Map pMap) {
        super(u, pMap);

//        bwem_assert(Type() == Resource_Vespene_Geyser);
        if (!(u instanceof VespeneGeyser)) {
            throw new IllegalArgumentException("unit is not a VespeneGeyser: " + u.getClass().getName());
        }
    }

    @Override
    public int initialAmount() {
        VespeneGeyser ret = (VespeneGeyser) super.unit();
        return ret.getInitialResources();
    }

    @Override
    public int amount() {
        VespeneGeyser ret = (VespeneGeyser) super.unit();
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
            return (this.unit().getId() == that.unit().getId());
        }
    }

    @Override
    public int hashCode() {
        return unit().hashCode();
    }

}
