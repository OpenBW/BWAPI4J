package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Larva extends PlayerUnit implements Organic {

    Larva(int id) {
        super(id, UnitType.Zerg_Larva);
    }
    
    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        return super.update(unitData, index);
    }
    
    public boolean morph(UnitType type) {
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, type.getId());
    }
}
