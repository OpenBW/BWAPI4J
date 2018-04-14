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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Unit;

public class Queen extends MobileUnit implements Organic, SpellCaster {

    private static final Logger logger = LogManager.getLogger();

    private int energy;

    protected Queen(int id) {
        super(id, UnitType.Zerg_Queen);
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

    /**
     * Infests a given Command Center.
     * @param commandCenter Command Center to be infested
     * @return true if command successful, false else
     */
    public boolean infestation(CommandCenter commandCenter) {
        
        if (this.energy < TechType.Infestation.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Unit, commandCenter.getId(),
                    -1, -1, Infestation.getId());
        }
    }
    
    /**
     * Casts a parasite spell on target unit.
     * @param target the unit to parasite
     * @return true if command successful, false else
     */
    public boolean parasite(MobileUnit target) {
        
        if (this.energy < TechType.Parasite.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Unit, target.getId(),
                    -1, -1, Parasite.getId());
        }
    }
    
    public boolean spawnBroodling(MobileUnit target) {
        
        if (this.energy < TechType.Spawn_Broodlings.energyCost()) {
            
            return false;
        } else if (target instanceof Robotic || target instanceof Archon || target instanceof DarkArchon) {
            
            logger.info("Spawn Broodling spell cannot target a {}", target);
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Unit, target.getId(), -1, -1, Spawn_Broodlings.getId());
        }
    }
    
    public boolean ensnare(Position position) {
        
        if (this.energy < TechType.Ensnare.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Position, -1, position.getX(), position.getY(), Ensnare.getId());
        }
    }
}
