////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published 
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Lurker;

public class Hydralisk extends MobileUnit implements Organic, Burrowable, GroundAttacker, AirAttacker, Morphable {

    private boolean burrowed;
    
    protected Hydralisk(int id) {
        super(id, UnitType.Zerg_Hydralisk);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.burrowed = false;
        super.initialize(unitData, index, frame);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        super.update(unitData, index, frame);
    }
    
    @Override
    public boolean burrow() {
        
        return issueCommand(this.id, Burrow, -1, -1, -1, -1);
    }

    @Override
    public boolean unburrow() {
        
        return issueCommand(this.id, Unburrow, -1, -1, -1, -1);
    }

    @Override
    public boolean isBurrowed() {
        
        return this.burrowed;
    }

    @Override
    public boolean morph(UnitType type) {
        if (type != Zerg_Lurker) {
            throw new IllegalArgumentException("Cannot morph to " + type);
        }
        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Lurker.getId());
    }

    public boolean morph() {
        return morph(Zerg_Lurker);
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
    public int getGroundWeaponMaxCooldown() {

        return super.getGroundWeaponMaxCooldown();
    }

    @Override
    public int getGroundWeaponCooldown() {

        return super.getGroundWeaponCooldown(this);
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
    public int getAirWeaponMaxCooldown() {

        return super.getAirWeaponMaxCooldown();
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
