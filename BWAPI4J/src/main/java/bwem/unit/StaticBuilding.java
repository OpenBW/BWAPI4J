package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class StaticBuilding
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// StaticBuildings Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType().isSpecialBuilding
// StaticBuilding also wrappers some special units like Special_Pit_Door.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public class StaticBuilding extends Neutral {

    public StaticBuilding(Unit u, Map pMap) {
        super(u, pMap);

        //TODO
//        bwem_assert(Type().isSpecialBuilding() ||
//                    (Type() == Special_Pit_Door) ||
//                    Type() == Special_Right_Pit_Door);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof StaticBuilding)) {
            return false;
        } else {
            StaticBuilding that = (StaticBuilding) object;
            return (this.unit().getId() == that.unit().getId());
        }
    }

    @Override
    public int hashCode() {
        return unit().hashCode();
    }

}
