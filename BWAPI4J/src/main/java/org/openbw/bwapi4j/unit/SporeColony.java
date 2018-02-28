package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.WeaponType;

import static org.openbw.bwapi4j.type.UnitCommandType.Attack_Unit;

public class SporeColony extends Building implements Organic {

    protected SporeColony(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Spore_Colony, timeSpotted);
    }

    public boolean attack(Unit target) {
        
        return attack(target, false);
    }
    
    public boolean attack(Unit target, boolean queued) {
        
        return issueCommand(this.id, Attack_Unit, target.getId(), -1, -1, queued ? 1 : 0);
    }

    public WeaponType getAirWeapon() {
        
        return this.type.airWeapon();
    }
    
    public int getMaxAirHits() {
        return this.type.maxAirHits();
    }
}
