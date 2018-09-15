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

package org.openbw.bwapi4j.util;

import java.util.Objects;
import org.openbw.bwapi4j.ap.BridgeValue;

public class Pair<K, V> {
  private final K first;
  private final V second;

  @BridgeValue
  public Pair(K first, V second) {
    this.first = first;
    this.second = second;
  }

  public K getFirst() {
    return first;
  }

  public V getSecond() {
    return second;
  }

  @Override
  public String toString() {
    return "Pair{" + "first=" + first + ", second=" + second + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Pair<?, ?> pair = (Pair<?, ?>) o;

    if (first != null ? !first.equals(pair.first) : pair.first != null) {
      return false;
    }
    if (second != null ? !second.equals(pair.second) : pair.second != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    final int first = this.first == null ? 0 : this.first.hashCode();
    final int second = this.second == null ? 0 : this.second.hashCode();

    return Objects.hashCode(new int[] {first, second});
  }
}
