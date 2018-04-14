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

package bwem.util;

import bwem.tile.MiniTile;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Comparator;

public final class PairGenericMiniTileAltitudeComparator<T> implements Comparator<MutablePair<T, MiniTile>> {

    @Override
    public int compare(MutablePair<T, MiniTile> o1, MutablePair<T, MiniTile> o2) {
        int a1 = o1.getRight().getAltitude().intValue();
        int a2 = o2.getRight().getAltitude().intValue();
        return Integer.compare(a1, a2);
    }

}
