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

import static org.openbw.bwapi4j.type.TechType.Scanner_Sweep;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;

public class ComsatStation extends Addon implements Mechanical, SpellCaster {

    private int energy;

    protected ComsatStation(int id, int timeSpotted) {

        super(id, UnitType.Terran_Comsat_Station, timeSpotted);
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

    public boolean scannerSweep(Position p) {

        return issueCommand(this.id, Use_Tech_Position, -1, p.getX(), p.getY(), Scanner_Sweep.getId());
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
