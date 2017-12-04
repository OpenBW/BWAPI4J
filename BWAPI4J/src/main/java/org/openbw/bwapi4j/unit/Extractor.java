package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Extractor extends Building implements Organic {

    private int initialResources;
    private int resources;
    private boolean isBeingGathered;

    protected Extractor(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Extractor, timeSpotted);
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
        super.update(unitData, index, frame);
    }

    public int getResources() {
        
        return this.resources;
    }

    public int getInitialResources() {
        
        return this.initialResources;
    }

    public boolean isBeingGathered() {
        
        return this.isBeingGathered;
    }
}
