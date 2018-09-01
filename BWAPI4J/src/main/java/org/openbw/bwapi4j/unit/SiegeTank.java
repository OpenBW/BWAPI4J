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

package org.openbw.bwapi4j.unit;

import static org.openbw.bwapi4j.type.UnitCommandType.Siege;
import static org.openbw.bwapi4j.type.UnitCommandType.Unsiege;

public class SiegeTank extends MobileUnitImpl implements Mechanical, GroundAttacker {
  public boolean siege() {
    return issueCommand(this.iD, Siege, -1, -1, -1, -1);
  }

  public boolean unsiege() {
    return issueCommand(this.iD, Unsiege, -1, -1, -1, -1);
  }

  public boolean isSieged() {
    return this.sieged;
  }

  @Override
  public Weapon getGroundWeapon() {
    return groundWeapon;
  }

  @Override
  public int getGroundWeaponMaxRange() {
    return super.getGroundWeaponMaxRange();
  }

  @Override
  public int getGroundWeaponMaxCooldown() {
    return super.getGroundWeaponMaxCooldown();
  }

  @Override
  public int getGroundWeaponCooldown() {
    return super.getGroundWeaponCooldown(this);
  }

  @Override
  public int getGroundWeaponDamage() {
    return super.getGroundWeaponDamage();
  }

  @Override
  public int getMaxGroundHits() {
    return super.getMaxGroundHits();
  }
}
