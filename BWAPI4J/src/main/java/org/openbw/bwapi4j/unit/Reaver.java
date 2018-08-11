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

import static org.openbw.bwapi4j.type.UnitCommandType.Train;
import static org.openbw.bwapi4j.type.UnitType.Protoss_Scarab;

public class Reaver extends MobileUnitImpl implements Mechanical {

    protected Reaver(int id) {
        
        super(id, UnitType.Protoss_Reaver);
    }
    
    public int getScarabCount() {
        
        return this.scarabCount;
    }
    
    public boolean trainScarab() {
        
        return issueCommand(id, Train, Protoss_Scarab.getId(), -1, -1, -1);
    }
}
