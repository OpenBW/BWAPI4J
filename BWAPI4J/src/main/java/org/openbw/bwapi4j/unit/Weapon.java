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

import org.openbw.bwapi4j.type.WeaponType;

/** Holds information for a ground/air weapon. */
public class Weapon {
  private WeaponType type;
  private int cooldown;

  public Weapon(WeaponType type, int cooldown) {
    this.type = type;
    this.cooldown = cooldown;
  }

  public int cooldown() {
    return cooldown;
  }

  public WeaponType type() {
    return type;
  }

  public int maxRange() {
    return type.maxRange();
  }

  public int maxCooldown() {
    return type.damageCooldown();
  }

  void update(WeaponType type, int cooldown) {
    this.type = type;
    this.cooldown = cooldown;
  }
}
