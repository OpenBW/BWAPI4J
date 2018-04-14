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

public class NuclearSilo extends Addon implements Mechanical {

    private boolean hasNuke;
    
    protected NuclearSilo(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Nuclear_Silo, timeSpotted);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.hasNuke = false;
        super.initialize(unitData, index, frame);
    }
    
    @Override
    public void update(int[] unitData, int index, int frame) {

        this.hasNuke = unitData[index + Unit.HAS_NUKE_INDEX] == 1;
        super.update(unitData, index, frame);
    }
    
    public boolean hasNuke() {
        
        return this.hasNuke;
    }
}
