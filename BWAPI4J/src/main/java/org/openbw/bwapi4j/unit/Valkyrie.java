package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Valkyrie extends MobileUnit implements Mechanical, AirAttacker {

    protected Valkyrie(int id) {
        
        super(id, UnitType.Terran_Valkyrie);
    }

    @Override
    public Weapon getAirWeapon() {

        return airWeapon;
    }

    @Override
    public int getAirWeaponMaxRange() {

        return super.getAirWeaponMaxRange();
    }

    @Override
    public int getAirWeaponCooldown() {

        return super.getAirWeaponCooldown();
    }

    @Override
    public int getAirWeaponDamage() {

        return super.getAirWeaponDamage();
    }

    @Override
    public int getMaxAirHits() {

        return super.getMaxAirHits();
    }
}
