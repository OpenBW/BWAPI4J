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
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

/**
 * Geysers Correspond to the units in BWAPI::getStaticNeutralUnits() for which getType() == Resource_Vespene_Geyser.
 */
public final class Geyser extends Resource {

    public Geyser(final Unit unit, final Map map) {
        super(unit, map);

//        bwem_assert(Type() == Resource_Vespene_Geyser);
        if (!(unit instanceof VespeneGeyser)) {
            throw new IllegalArgumentException("Unit is not a VespeneGeyser: " + unit.getClass().getName());
        }
    }

    @Override
    public int getInitialAmount() {
        final VespeneGeyser bwapi4jGeyser = (VespeneGeyser) super.getUnit();
        return bwapi4jGeyser.getInitialResources();
    }

    @Override
    public int getAmount() {
        final VespeneGeyser bwapi4jGeyser = (VespeneGeyser) super.getUnit();
        return bwapi4jGeyser.getResources();
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Geyser)) {
            return false;
        } else {
            final Geyser that = (Geyser) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
