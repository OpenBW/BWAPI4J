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

import java.util.List;
import java.util.stream.Collectors;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Lair;

public class Hatchery extends ZergResourceDepotImpl {

    protected Hatchery(int id, UnitType type, int timeSpotted) {
        
        super(id, type, timeSpotted);
    }
    
    protected Hatchery(int id, int timeSpotted) {
        
        this(id, UnitType.Zerg_Hatchery, timeSpotted);
    }

    @Override
    public boolean morph(UnitType type) {
        if (type != Zerg_Lair) {
            throw new IllegalArgumentException("Cannot morph to " + type);
        }

        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Lair.getId());
    }
    
    @Override
    public boolean morph() {
        return morph(Zerg_Lair);
    }
}
