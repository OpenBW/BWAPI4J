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
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.Spider_Mines;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;

public class Vulture extends MobileUnit implements Mechanical, GroundAttacker {

    private int spiderMineCount;
    
    protected Vulture(int id) {
        
        super(id, UnitType.Terran_Vulture);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.spiderMineCount = 0;
        super.initialize(unitData, index, frame);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.spiderMineCount = unitData[index + Unit.SPIDERMINE_COUNT_INDEX];
        super.update(unitData, index, frame);
    }
    
    public int getSpiderMineCount() {
        
        return this.spiderMineCount;
    }
    
    /**
     * Places a spider mine at the given position.
     * @param position target position of the spider mine to be placed
     * @return true if successful, false else
     */
    public boolean spiderMine(Position position) {
        
        return issueCommand(this.id, Use_Tech_Position, -1,
                position.getX(), position.getY(), Spider_Mines.getId());
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
    public int getGroundWeaponMaxCooldown() {

        return super.getGroundWeaponMaxCooldown();
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
