package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Interceptor extends MobileUnit implements Mechanical, GroundAttacker, AirAttacker {

    private int carrierId;
    
    protected Interceptor(int id) {
        
        super(id, UnitType.Protoss_Interceptor);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.carrierId = unitData[index + Unit.CARRIER_INDEX];
        super.initialize(unitData, index, frame);
    }

    public Unit getCarrier() {
        
        return super.getUnit(carrierId);
    }

    @Override
    public Weapon getGroundWeapon() {

        return groundWeapon;
    }

    @Override
    public Weapon getAirWeapon() {

        return airWeapon;
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
