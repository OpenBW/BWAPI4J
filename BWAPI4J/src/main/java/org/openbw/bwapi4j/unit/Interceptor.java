package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class Interceptor extends MobileUnit implements Mechanical, Armed {

    private int carrierId;
    
    protected Interceptor(int id) {
        
        super(id, UnitType.Protoss_Interceptor);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.carrierId = unitData[index + Unit.CARRIER_INDEX];
        super.initialize(unitData, index);
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
}
