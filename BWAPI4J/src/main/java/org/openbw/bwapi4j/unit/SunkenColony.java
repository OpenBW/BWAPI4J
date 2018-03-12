package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Attack_Unit;

public class SunkenColony extends Building implements Organic, GroundAttacker {

    protected SunkenColony(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Sunken_Colony, timeSpotted);
    }

    @Override
    public boolean attack(Unit target) {
        
        return attack(target, false);
    }
    
    @Override
    public boolean attack(Unit target, boolean queued) {
        
        return issueCommand(this.id, Attack_Unit, target.getId(), -1, -1, queued ? 1 : 0);
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
    public int getMaxGroundHits() {

        return this.type.maxGroundHits();
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
}
