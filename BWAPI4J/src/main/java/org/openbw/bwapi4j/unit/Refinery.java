package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitType;

public class Refinery extends Building implements Mechanical {

    private int initialResources;
    private int resources;
    private boolean isBeingGathered;

    public Refinery(int id, int timeSpotted) {
        super(id, UnitType.Terran_Refinery, timeSpotted);
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        super.initialize(unitData, index, allUnits);
        this.initialResources = unitData[index + Unit.INITIAL_RESOURCES_INDEX];

        return index;
    }

    @Override
    public int update(int[] unitData, int index) {

        super.update(unitData, index);
        this.resources = unitData[index + Unit.RESOURCES_INDEX];
        this.isBeingGathered = unitData[index + Unit.IS_BEING_GATHERED_INDEX] == 1;

        return index;
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
