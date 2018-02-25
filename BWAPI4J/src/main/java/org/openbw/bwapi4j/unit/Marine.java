package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Marine extends MobileUnit implements Organic, Armed {

    private boolean isStimmed;

    protected Marine(int id) {
        
        super(id, UnitType.Terran_Marine);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.isStimmed = unitData[index + Unit.IS_STIMMED_INDEX] == 1;
        super.update(unitData, index, frame);
    }

    public boolean isStimmed() {
        
        return this.isStimmed;
    }

    public boolean stimPack() {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, -1, -1, TechType.Stim_Packs.getId());
    }

    @Override
    public Weapon getGroundWeapon() {
        return groundWeapon;
    }

    @Override
    public Weapon getAirWeapon() {
        return airWeapon;
    }
}
