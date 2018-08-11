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

import static org.openbw.bwapi4j.type.TechType.*;
import static org.openbw.bwapi4j.type.UnitCommandType.*;

public class HighTemplar extends MobileUnitImpl implements Organic, SpellCaster {


    protected HighTemplar(int id) {
        
        super(id, UnitType.Protoss_High_Templar);
    }
    
    @Override
    public int getEnergy() {
        
        return this.energy;
    }

    @Override
    public int getMaxEnergy() {

        return super.getMaxEnergy();
    }

    public boolean archonWarp() {
        
        // TODO how does this spell work? does the other templars ID have to be passed as well?
        return issueCommand(this.id, Use_Tech, -1, -1, -1, Archon_Warp.getId());
    }

    public boolean hallucination(MobileUnit unit) {
        
        if (this.energy < TechType.Hallucination.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Unit, unit.getId(),
                    -1, -1, Hallucination.getId());
        }
    }

    public boolean psionicStorm(Position position) {
    
        if (this.energy < TechType.Psionic_Storm.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, Use_Tech_Position, -1,
                    position.getX(), position.getY(), Psionic_Storm.getId());
        }
    }
}
