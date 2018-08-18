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

import static org.openbw.bwapi4j.type.TechType.Dark_Archon_Meld;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech;

import org.openbw.bwapi4j.type.UnitType;

public class DarkTemplar extends MobileUnitImpl implements Organic, Cloakable, GroundAttacker {
  protected DarkTemplar(int id) {
    super(id, UnitType.Protoss_Dark_Templar);
  }

  /**
   * Merges two dark templars into one dark archon. Both templars must be selected.
   *
   * @return true if command successful, false else
   */
  public boolean darkArchonMeld() {
    // TODO how does this spell work? does the other templars ID have to be passed as well?
    return issueCommand(this.id, Use_Tech, -1, -1, -1, Dark_Archon_Meld.getId());
  }

  @Override
  public boolean cloak() {
    return true;
  }

  @Override
  public boolean decloak() {
    return false;
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
