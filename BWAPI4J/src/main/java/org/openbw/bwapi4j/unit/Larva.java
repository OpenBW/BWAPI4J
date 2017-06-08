package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Larva extends PlayerUnit implements Organic {

    private int hatcheryId;
    
    protected Larva(int id) {
        
        super(id, UnitType.Zerg_Larva);
    }
    
    @Override
    public void initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        this.hatcheryId = unitData[index + Unit.HATCHERY_INDEX];
        super.initialize(unitData, index, allUnits);
    }

    public Unit getHatchery() {
        
        // TODO not type-safe. could be hatchery, lair, or hive
        return super.getUnit(hatcheryId);
    }
    
    public boolean morph(UnitType type) {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, type.getId());
    }
}
