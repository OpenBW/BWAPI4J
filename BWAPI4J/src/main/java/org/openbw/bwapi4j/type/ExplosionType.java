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

public enum ExplosionType {
  None,
  Normal,
  Radial_Splash,
  Enemy_Splash,
  Lockdown,
  Nuclear_Missile,
  Parasite,
  Broodlings,
  EMP_Shockwave,
  Irradiate,
  Ensnare,
  Plague,
  Stasis_Field,
  Dark_Swarm,
  Consume,
  Yamato_Gun,
  Restoration,
  Disruption_Web,
  Corrosive_Acid,
  Mind_Control,
  Feedback,
  Optical_Flare,
  Maelstrom,
  Air_Splash,
  Unknown;

  public static ExplosionType withId(int id) {
    return values()[id];
  }
}
