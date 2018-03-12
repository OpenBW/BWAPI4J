package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Zealot extends MobileUnit implements Organic, GroundAttacker {

    protected Zealot(int id) {
        
        super(id, UnitType.Protoss_Zealot);
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
