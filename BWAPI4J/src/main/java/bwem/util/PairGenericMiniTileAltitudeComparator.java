// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.util;

import bwem.tile.MiniTile;
import java.util.Comparator;
import org.apache.commons.lang3.tuple.MutablePair;

public final class PairGenericMiniTileAltitudeComparator<T>
    implements Comparator<MutablePair<T, MiniTile>> {
  @Override
  public int compare(MutablePair<T, MiniTile> o1, MutablePair<T, MiniTile> o2) {
    int a1 = o1.getRight().getAltitude().intValue();
    int a2 = o2.getRight().getAltitude().intValue();
    return Integer.compare(a1, a2);
  }
}
