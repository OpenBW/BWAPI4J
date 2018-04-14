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

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;

public class Larva extends PlayerUnit implements Organic, Morphable {

    private int hatcheryId;
    
    protected Larva(int id) {
        
        super(id, UnitType.Zerg_Larva);
    }
    
    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.hatcheryId = unitData[index + Unit.HATCHERY_INDEX];
        super.initialize(unitData, index, frame);
    }

    public Hatchery getHatchery() {
        
        return (Hatchery) super.getUnit(hatcheryId);
    }

    @Override
    public boolean morph(UnitType type) {
        
        return issueCommand(this.id, Morph, -1, -1, -1, type.getId());
    }
}
