// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic 
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License. 
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;

/**
 * StaticBuildings Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType().isSpecialBuilding.
 * StaticBuilding also wrappers some special units like Special_Pit_Door.
 */
public class StaticBuilding extends NeutralImpl {

    public StaticBuilding(final Unit unit, final Map map) {
        super(unit, map);

        //TODO
//        bwem_assert(Type().isSpecialBuilding() ||
//                    (Type() == Special_Pit_Door) ||
//                    Type() == Special_Right_Pit_Door);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof StaticBuilding)) {
            return false;
        } else {
            final StaticBuilding that = (StaticBuilding) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
