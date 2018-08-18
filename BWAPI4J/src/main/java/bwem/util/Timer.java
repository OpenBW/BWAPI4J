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

/** This timer class is similar to the one provided by the original C++ BWEM. */
public class Timer {
  private static final double NANOSECONDS_PER_MILLISECOND = Math.pow(10, 6);

  private long start;

  public Timer() {
    reset();
  }

  public void reset() {
    this.start = now();
  }

  public double elapsedMilliseconds() {
    return ((double) (now() - this.start)) / NANOSECONDS_PER_MILLISECOND;
  }

  private long now() {
    return System.nanoTime();
  }
}
