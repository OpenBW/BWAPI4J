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

/**
 * VespeneGeyser is not Gatherable, it needs a {@link GasMiningFacility}.
 */
public class VespeneGeyser extends Unit implements Resource {

    private int initialResources;
    private int resources;
    private int lastKnownResources;

    private boolean isBeingGathered;

    protected VespeneGeyser(int id) {
        super(id, UnitType.Resource_Vespene_Geyser);
    }

    @Override
    public void initialize(int[] unitData, int index, int frame) {

        this.initialResources = unitData[index + Unit.INITIAL_RESOURCES_INDEX];
        super.initialize(unitData, index, frame);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.resources = unitData[index + Unit.RESOURCES_INDEX];
        this.isBeingGathered = unitData[index + Unit.IS_BEING_GATHERED_INDEX] == 1;

        if (this.isVisible) {
            this.lastKnownResources = this.resources;
        }
        
        super.update(unitData, index, frame);
    }

    @Override
    public int getResources() {
        
        return this.resources;
    }

    @Override
    public int getInitialResources() {
        
        return this.initialResources;
    }

    @Override
    public int getLastKnownResources() {
        
        return this.lastKnownResources;
    }

    @Override
    public boolean isBeingGathered() {
        
        return this.isBeingGathered;
    }
}
