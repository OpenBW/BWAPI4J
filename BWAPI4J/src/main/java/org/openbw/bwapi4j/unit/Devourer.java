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

import org.openbw.bwapi4j.type.UnitType;

public class Devourer extends MobileUnitImpl implements Organic, AirAttacker {
  protected Devourer(int id) {
    super(id, UnitType.Zerg_Devourer);
  }

  @Override
  public Weapon getAirWeapon() {
    return airWeapon;
  }

  @Override
  public int getAirWeaponMaxRange() {
    return super.getAirWeaponMaxRange();
  }

  @Override
  public int getAirWeaponMaxCooldown() {
    return super.getAirWeaponMaxCooldown();
  }

  @Override
  public int getAirWeaponCooldown() {
    return super.getAirWeaponCooldown(this);
  }

  @Override
  public int getAirWeaponDamage() {
    return super.getAirWeaponDamage();
  }

  @Override
  public int getMaxAirHits() {
    return super.getMaxAirHits();
  }
}
