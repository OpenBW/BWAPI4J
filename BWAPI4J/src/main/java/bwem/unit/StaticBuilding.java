/*
Status: Incomplete
*/

package bwem.unit;

import bwem.map.Map;

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

    public StaticBuilding(org.openbw.bwapi4j.unit.Unit u, Map pMap) {
        super(u, pMap);

        //TODO
//        bwem_assert(Type().isSpecialBuilding() ||
//                    (Type() == Special_Pit_Door) ||
//                    Type() == Special_Right_Pit_Door);
    }

}
