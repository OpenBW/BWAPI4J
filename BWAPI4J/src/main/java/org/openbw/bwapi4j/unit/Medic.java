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
import static org.openbw.bwapi4j.type.UnitCommandType.Attack_Move;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Unit;

public class Medic extends MobileUnitImpl implements SpellCaster, Organic {

    protected Medic(int id) {
        
        super(id, UnitType.Terran_Medic);
    }

    public boolean heal(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1, Healing.getId());
    }

    public boolean heal(Position position) {

        return heal(position, false);
    }

    public boolean heal(Position position, boolean queued) {

        return issueCommand(this.id, Attack_Move, -1, position.getX(), position.getY(), queued ? 1 : 0);
    }

    public boolean opticalFlare(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1,
                Optical_Flare.getId());
    }

    public boolean restoration(PlayerUnit unit) {
        
        return issueCommand(this.id, Use_Tech_Unit, unit.getId(), -1, -1,
                Restoration.getId());
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }

    @Override
    public int getMaxEnergy() {

        return super.getMaxEnergy();
    }
}
