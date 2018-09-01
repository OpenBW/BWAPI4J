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

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;

import org.openbw.bwapi4j.type.UnitType;

public class Mutalisk extends MobileUnitImpl
    implements Organic, GroundAttacker, AirAttacker, Morphable {
  @Override
  public boolean morph(UnitType unitType) {
    if (unitType != UnitType.Zerg_Guardian && unitType != UnitType.Zerg_Devourer) {
      throw new IllegalArgumentException("Cannot morph to " + type);
    }
    return issueCommand(this.iD, Morph, -1, -1, -1, unitType.getId());
  }

  public boolean morphGuardian() {
    return morph(UnitType.Zerg_Guardian);
  }

  public boolean morphDevourer() {
    return morph(UnitType.Zerg_Devourer);
  }

  @Override
  public Weapon getGroundWeapon() {
    return groundWeapon;
  }

  @Override
  public Weapon getAirWeapon() {
    return airWeapon;
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
