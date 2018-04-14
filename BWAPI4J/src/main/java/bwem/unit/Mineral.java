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

package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;

/**
 * Minerals Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType().isMineralField().
 */
public final class Mineral extends Resource {

    public Mineral(final Unit unit, final Map map) {
        super(unit, map);

//        bwem_assert(Type().isMineralField());
        if (!(unit instanceof MineralPatch)) {
            throw new IllegalArgumentException("Unit is not a MineralPatch: " + unit.getClass().getName());
        }
    }

    @Override
    public int getInitialAmount() {
        final MineralPatch bwapi4jMineral = (MineralPatch) super.getUnit();
        return bwapi4jMineral.getInitialResources();
    }

    @Override
    public int getAmount() {
        final MineralPatch bwapi4jMineral = (MineralPatch) super.getUnit();
        return bwapi4jMineral.getResources();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Mineral)) {
            return false;
        } else {
            final Mineral that = (Mineral) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
