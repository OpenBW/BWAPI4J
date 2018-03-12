package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Broodling extends MobileUnit implements Organic, GroundAttacker {

    protected Broodling(int id) {
        
        super(id, UnitType.Zerg_Broodling);
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
