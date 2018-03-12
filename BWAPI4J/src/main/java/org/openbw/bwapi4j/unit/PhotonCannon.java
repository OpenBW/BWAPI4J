package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Attack_Unit;

public class PhotonCannon extends Building implements Detector, Mechanical, Armed {

    protected PhotonCannon(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Photon_Cannon, timeSpotted);
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
    public Weapon getAirWeapon() {
        
        return airWeapon;
    }

    @Override
    public Unit getTargetUnit() {
        return super.getTargetUnit();
    }

    public int getMaxGroundHits() {
        return this.type.maxGroundHits();
    }
    
    public int getMaxAirHits() {
        return this.type.maxAirHits();
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
}
