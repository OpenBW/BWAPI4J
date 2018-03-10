package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Cloak;
import static org.openbw.bwapi4j.type.UnitCommandType.Decloak;

public class Wraith extends MobileUnit implements Mechanical, Cloakable, Armed {

    protected Wraith(int id) {
        
        super(id, UnitType.Terran_Wraith);
    }

    public boolean cloak() {
        
        return issueCommand(this.id, Cloak, -1, -1, -1, -1);
    }

    public boolean decloak() {
        
        return issueCommand(this.id, Decloak, -1, -1, -1, -1);
    }

    public int getMaxEnergy() {

        return super.getMaxEnergy();
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
