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

import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public abstract class Worker extends MobileUnit implements GroundAttacker {

    protected Worker(int id, UnitType unitType) {
        super(id, unitType);
    }

    public UnitType getBuildType() {
        return buildType;
    }

    public boolean isConstructing() {
        return isConstructing;
    }

    public boolean isGatheringMinerals() {

        return this.isGatheringMinerals;
    }

    public boolean isCarryingMinerals() {

        return this.isCarryingMinerals;
    }

    public boolean isCarryingGas() {

        return this.isCarryingGas;
    }

    public boolean isGatheringGas() {

        return this.isGatheringGas;
    }

    public boolean returnCargo() {

        return issueCommand(this.id, Return_Cargo, -1, -1, -1, -1);
    }

    public boolean returnCargo(boolean queued) {

        return issueCommand(this.id, Return_Cargo, -1, -1, -1, queued ? 1 : 0);
    }

    public boolean gather(Gatherable resource) {
        return issueCommand(this.id, Gather, resource.getId(), -1, -1, 0);
    }
    
    public boolean gather(Gatherable resource, boolean shiftQueueCommand) {
        return issueCommand(this.id, Gather, resource.getId(), -1, -1, shiftQueueCommand ? 1 : 0);
    }

    public boolean build(TilePosition p, UnitType type) {

        return issueCommand(this.id, Build, -1, p.getX(), p.getY(), type.getId());
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
