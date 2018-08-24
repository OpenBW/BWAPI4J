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

package org.openbw.bwapi4j;

public class BWAPI4J {
  private BWAPI4J() {}

  public enum Property {
    EXTRACT_DEPENDENCIES("bwapi4j.extractDependencies"),
    BRIDGE_TYPE("bwapi4j.bridgeType");

    private final String property;

    Property(final String property) {
      this.property = property;
    }

    public String toString() {
      return this.property;
    }
  }

  public enum BridgeType {
    VANILLA("BWAPI4JBridge"),
    OPENBW("OpenBWAPI4JBridge");

    private final String name;

    BridgeType(final String name) {
      this.name = name;
    }

    public String getLibraryName() {
      return this.name;
    }

    public static BridgeType parseBridgeType(final String str) {
      for (final BridgeType bridgeType : BridgeType.values()) {
        if (bridgeType.toString().equalsIgnoreCase(str)) {
          return bridgeType;
        }
      }

      throw new IllegalArgumentException("Unrecognized bridge type: " + str);
    }
  }
}
