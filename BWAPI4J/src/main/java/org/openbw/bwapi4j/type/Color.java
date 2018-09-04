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

package org.openbw.bwapi4j.type;

import org.openbw.bwapi4j.ap.LookedUp;

@LookedUp(method = "getColor")
public enum Color {
  RED(111),
  BLUE(165),
  TEAL(159),
  PURPLE(164),
  ORANGE(179),
  BROWN(19),
  WHITE(255),
  YELLOW(135),
  GREEN(117),
  CYAN(128),
  BLACK(0),
  GREY(74);

  private int rgb;

  private Color(int rgb) {
    this.rgb = rgb;
  }

  public int getValue() {
    return this.rgb;
  }

  public boolean matches(int rgb) {
    return this.rgb == rgb;
  }
}
