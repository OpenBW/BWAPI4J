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

import bwem.typedef.Altitude;
import java.util.Comparator;
import org.apache.commons.lang3.tuple.MutablePair;

public final class PairGenericAltitudeComparator<T>
    implements Comparator<MutablePair<T, Altitude>> {
  @Override
  public int compare(MutablePair<T, Altitude> o1, MutablePair<T, Altitude> o2) {
    int a1 = o1.getRight().intValue();
    int a2 = o2.getRight().intValue();
    return Integer.compare(a1, a2);
  }
}
