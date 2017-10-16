package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;

// StaticBuildings Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType().isSpecialBuilding
// StaticBuilding also wrappers some special units like Special_Pit_Door.
public class StaticBuilding extends Neutral {

    public StaticBuilding(Unit unit, Map map) {
        super(unit, map);

        //TODO: Assert
//        bwem_assert(Type().isSpecialBuilding() ||
//                    (Type() == Special_Pit_Door) ||
//                    Type() == Special_Right_Pit_Door);
    }

}
