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

/**
 * See original C++ BWEM for an explanation of this code.
 * Do NOT mimic BWEM's C++ inheritance for this code.
 * See "src/test/util/OldMarkable.java" for what NOT to do.
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
