package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class VespeneGeyser extends Unit implements Resource  {

    private int initialResources;
    private int resources;
    private int lastKnownResources;

    private boolean isBeingGathered;

    protected VespeneGeyser(int id) {
        super(id, UnitType.Resource_Vespene_Geyser);
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.initialResources = unitData[index + Unit.INITIAL_RESOURCES_INDEX];
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.resources = unitData[index + Unit.RESOURCES_INDEX];
        this.isBeingGathered = unitData[index + Unit.IS_BEING_GATHERED_INDEX] == 1;

        if (this.isVisible) {
            this.lastKnownResources = this.resources;
        }
        
        super.update(unitData, index, frame);
    }

    public int getResources() {
        
        return this.resources;
    }

    public int getInitialResources() {
        
        return this.initialResources;
    }

    public int getLastKnownResources() {
        
        return this.lastKnownResources;
    }

    public boolean isBeingGathered() {
        
        return this.isBeingGathered;
    }
}
