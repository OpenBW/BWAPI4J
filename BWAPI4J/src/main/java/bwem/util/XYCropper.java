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

public class XYCropper {

    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;

    public XYCropper(final int minX, final int minY, final int maxX, final int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public int cropX(int x) {
        return (x < this.minX ? this.minX : (x > this.maxX) ? this.maxX : x);
    }

    public int cropY(int y) {
        return (y < this.minY ? this.minY : (y > this.maxY) ? this.maxY : y);
    }

    public int[] crop(int x, int y) {
        x = cropX(x);
        y = cropY(y);
        return new int[]{x, y};
    }

}
