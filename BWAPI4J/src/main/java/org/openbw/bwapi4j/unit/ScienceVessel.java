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

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Unit;

public class ScienceVessel extends MobileUnitImpl implements Mechanical, SpellCaster, Detector {

    protected ScienceVessel(int id) {
        
        super(id, UnitType.Terran_Science_Vessel);
    }

    public boolean defensiveMatrix(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1,
                Defensive_Matrix.getId());
    }

    public boolean irradiate(Organic unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, ((Unit) unit).getId(), -1, -1,
                Irradiate.getId());
    }

    public boolean empShockWave(Position p) {
        
        return issueCommand(this.id, Use_Tech_Position, -1, p.getX(), p.getY(),
                EMP_Shockwave.getId());
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }

    @Override
    public int getMaxEnergy() {

        return this.energy;
    }
}
