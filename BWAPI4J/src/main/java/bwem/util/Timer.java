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

/**
 * This timer class is similar to the one provided by the original C++ BWEM.
 */
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
