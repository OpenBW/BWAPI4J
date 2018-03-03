package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;

public class Larva extends PlayerUnit implements Organic, Morphable {

    private int hatcheryId;
    
    protected Larva(int id) {
        
        super(id, UnitType.Zerg_Larva);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.hatcheryId = unitData[index + Unit.HATCHERY_INDEX];
        super.initialize(unitData, index);
    }

    public Hatchery getHatchery() {
        
        return (Hatchery) super.getUnit(hatcheryId);
    }

    @Override
    public boolean morph(UnitType type) {
        
        return issueCommand(this.id, Morph, -1, -1, -1, type.getId());
    }
}
