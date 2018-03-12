package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.Stim_Packs;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech;

public class Firebat extends MobileUnit implements Organic, GroundAttacker {

    private boolean isStimmed;

    protected Firebat(int id) {
        
        super(id, UnitType.Terran_Firebat);
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
        
        return issueCommand(this.id, Use_Tech, -1, -1, -1, Stim_Packs.getId());
    }

    @Override
    public Weapon getGroundWeapon() {

        return groundWeapon;
    }

    @Override
    public int getGroundWeaponMaxRange() {

        return super.getGroundWeaponMaxRange();
    }

    @Override
    public int getGroundWeaponCooldown() {

        return super.getGroundWeaponCooldown();
    }

    @Override
    public int getGroundWeaponDamage() {

        return super.getGroundWeaponDamage();
    }

    @Override
    public int getMaxGroundHits() {

        return super.getMaxGroundHits();
    }
}
