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
    public int InitialAmount() {
        VespeneGeyser ret = (VespeneGeyser) super.Unit();
        return ret.getInitialResources();
    }

    @Override
    public int Amount() {
        VespeneGeyser ret = (VespeneGeyser) super.Unit();
        return ret.getResources();
    }

}
