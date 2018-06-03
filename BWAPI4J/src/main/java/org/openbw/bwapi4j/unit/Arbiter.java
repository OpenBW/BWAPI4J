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

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.Recall;
import static org.openbw.bwapi4j.type.TechType.Stasis_Field;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;

public class Arbiter extends MobileUnit implements Mechanical, SpellCaster, GroundAttacker, AirAttacker {

    private int energy;

    protected Arbiter(int id) {
        
        super(id, UnitType.Protoss_Arbiter);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.energy = 0;
        super.initialize(unitData, index, frame);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index, frame);
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }

    @Override
    public int getMaxEnergy() {

        return super.getMaxEnergy();
    }

    public boolean stasisField(Position position) {
        
        if (this.energy < TechType.Stasis_Field.energyCost()) {
            return false;
        } else {
            return issueCommand(this.id, Use_Tech_Position, -1,
                    position.getX(), position.getY(), Stasis_Field.getId());
        }
    }
    
    public boolean recall(Position position) {
        
        if (this.energy < TechType.Recall.energyCost()) {
            return false;
        } else {
            return issueCommand(this.id, Use_Tech_Position, -1,
                    position.getX(), position.getY(), Recall.getId());
        }
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

        return super.getAirWeaponCooldown(this);
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
