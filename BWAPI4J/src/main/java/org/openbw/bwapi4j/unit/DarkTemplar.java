package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.Dark_Archon_Meld;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech;

public class DarkTemplar extends MobileUnit implements Organic, Cloakable, GroundAttacker {

    protected DarkTemplar(int id) {
        
        super(id, UnitType.Protoss_Dark_Templar);
    }
    
    /**
     * Merges two dark templars into one dark archon. Both templars must be selected.
     * @return true if command successful, false else
     */
    public boolean darkArchonMeld() {
        
        // TODO how does this spell work? does the other templars ID have to be passed as well?
        return issueCommand(this.id, Use_Tech, -1, -1, -1, Dark_Archon_Meld.getId());
    }

    @Override
    public boolean cloak() {
        
        return true;
    }

    @Override
    public boolean decloak() {
        
        return false;
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
