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

package bwem;

import bwem.map.Map;
import bwem.map.MapInitializer;
import bwem.map.MapInitializerImpl;
import org.openbw.bwapi4j.BW;

public final class BWEM {

    private final Map map;

    public BWEM(final BW bw) {
        this.map = new MapInitializerImpl(
                bw.getBWMap(),
                bw.getMapDrawer(),
                bw.getAllPlayers(),
                bw.getMineralPatches(),
                bw.getVespeneGeysers(),
                bw.getAllUnits()
        );
    }

    /**
     * Returns the root internal data container.
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Default value for {@code enableTimer} is {@code false}.
     *
     * @see #initialize()
     */
    public void initialize() {
        initialize(false);
    }

    /**
     * Initializes and pre-computes all of the internal data.
     *
     * @param enableTimer whether to print the elapsed time of each initialization stage
     */
    public void initialize(final boolean enableTimer) {
        if (!(this.map instanceof MapInitializer)) {
            throw new IllegalStateException("BWEM was not instantiated properly.");
        } else {
            ((MapInitializer) this.map).initialize(enableTimer);
        }
    }

}
