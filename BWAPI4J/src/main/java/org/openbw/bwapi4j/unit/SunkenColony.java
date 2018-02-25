package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.WeaponType;

public class SunkenColony extends Building implements Organic, Armed {

    protected SunkenColony(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Sunken_Colony, timeSpotted);
    }

    @Override
    public boolean attack(Unit target) {
        
        return attack(target, false);
    }
    
    @Override
    public boolean attack(Unit target, boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Attack_Unit.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
    }

    @Override
    public Weapon getGroundWeapon() {
        
        return groundWeapon;
    }

    @Override
    public Unit getTargetUnit() {
        return super.getTargetUnit();
    }

    @Override
    public Weapon getAirWeapon() {
        return airWeapon;
    }

    public int getMaxGroundHits() {
        return this.type.maxGroundHits();
    }
}
