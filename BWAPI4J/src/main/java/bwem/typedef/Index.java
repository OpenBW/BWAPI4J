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

package bwem.typedef;

import bwem.util.IWrappedInteger;

/**
 * Immutable wrapper of the integer primitive to satisfy the original C++ definition:
 * cp.h:143:typedef int index;
 */
public final class Index implements IWrappedInteger<Index>, Comparable<Index> {
  private final int val;

  public Index(final int val) {
    this.val = val;
  }

  public Index add(final int val) {
    return new Index(this.val + val);
  }

  @Override
  public int intValue() {
    return this.val;
  }

  @Override
  public int compareTo(final Index that) {
    return Integer.compare(this.val, that.val);
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    } else if (!(object instanceof Index)) {
      return false;
    } else {
      final Index that = (Index) object;
      return (this.val == that.val);
    }
  }

  @Override
  public int hashCode() {
    return this.val;
  }

  @Override
  public String toString() {
    return String.valueOf(this.val);
  }
}
