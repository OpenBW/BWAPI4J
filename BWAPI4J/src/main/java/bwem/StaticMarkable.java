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

package bwem;

/**
 * See original C++ BWEM for an explanation of this code. Do NOT mimic BWEM's C++ inheritance for
 * this code. See "src/test/util/OldMarkable.java" for what NOT to do.
 */
public final class StaticMarkable {
  private int currentMark;

  public StaticMarkable() {
    this.currentMark = 0;
  }

  public int getCurrentMark() {
    return this.currentMark;
  }

  public void unmarkAll() {
    ++this.currentMark;
  }
}
