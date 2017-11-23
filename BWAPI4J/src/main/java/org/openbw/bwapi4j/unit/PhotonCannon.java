package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.WeaponType;

public class PhotonCannon extends Building implements Detector, Mechanical {

    protected PhotonCannon(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Photon_Cannon, timeSpotted);
    }

    protected boolean attack(Unit target, boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Attack_Unit.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
    }
    
    public WeaponType getGroundWeapon() {
        
        return this.type.groundWeapon();
    }

    public WeaponType getAirWeapon() {
        
        return this.type.airWeapon();
    }

    public int getMaxGroundHits() {
        return this.type.maxGroundHits();
    }
    
    public int getMaxAirHits() {
        return this.type.maxAirHits();
    }
}
