package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Morph;

public class Egg extends PlayerUnit implements Organic {

    private UnitType buildType;
    private int remainingMorphTime;

    protected Egg(int id) {
        
        super(id, UnitType.Zerg_Egg);
    }

    @Override
    public void initialize(int[] unitData, int index) {
        super.initialize(unitData, index);
        buildType = UnitType.values()[unitData[index + Unit.BUILDTYPE_ID_INDEX]];
    }

    @Override
    public void update(int[] unitData, int index, int frame) {
        super.update(unitData, index, frame);
        remainingMorphTime = unitData[index + Unit.REMAINING_BUILD_TIME_INDEX];
    }

    public boolean cancelMorph() {
        
        return issueCommand(this.id, Cancel_Morph, -1, -1, -1, -1);
    }

    public UnitType getBuildType() {
        return buildType;
    }

    public int getRemainingMorphTime() {
        return remainingMorphTime;
    }
}
