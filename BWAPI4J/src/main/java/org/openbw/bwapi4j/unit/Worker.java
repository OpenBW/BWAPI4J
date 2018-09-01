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

import static org.openbw.bwapi4j.type.UnitCommandType.Build;
import static org.openbw.bwapi4j.type.UnitCommandType.Gather;
import static org.openbw.bwapi4j.type.UnitCommandType.Return_Cargo;

import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public abstract class Worker extends MobileUnitImpl implements GroundAttacker {
  public UnitType getBuildType() {
    return buildType;
  }

  public boolean isConstructing() {
    return constructing;
  }

  public boolean isGatheringMinerals() {
    return this.gatheringMinerals;
  }

  public boolean isCarryingMinerals() {
    return this.carryingMinerals;
  }

  public boolean isCarryingGas() {
    return this.carryingGas;
  }

  public boolean isGatheringGas() {
    return this.gatheringGas;
  }

  public boolean returnCargo() {
    return issueCommand(this.iD, Return_Cargo, -1, -1, -1, -1);
  }

  public boolean returnCargo(boolean queued) {
    return issueCommand(this.iD, Return_Cargo, -1, -1, -1, queued ? 1 : 0);
  }

  public boolean gather(Gatherable resource) {
    return issueCommand(this.iD, Gather, resource.getId(), -1, -1, 0);
  }

  public boolean gather(Gatherable resource, boolean shiftQueueCommand) {
    return issueCommand(this.iD, Gather, resource.getId(), -1, -1, shiftQueueCommand ? 1 : 0);
  }

  public boolean build(TilePosition p, UnitType type) {
    return issueCommand(this.iD, Build, -1, p.getX(), p.getY(), type.getId());
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
