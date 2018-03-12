package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Attack_Unit;

public class MissileTurret extends Building implements Mechanical, Detector, AirAttacker {

    protected MissileTurret(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Missile_Turret, timeSpotted);
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
    public Unit getTargetUnit() {

        return super.getTargetUnit();
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
