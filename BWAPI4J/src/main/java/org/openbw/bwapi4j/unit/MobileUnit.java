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

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public abstract class MobileUnit extends PlayerUnit {

    protected MobileUnit(int id, UnitType unitType) {

        super(id, unitType);
    }

    public boolean attack(Position p) {

        return attack(p, false);
    }

    public boolean attack(Position p, boolean queued) {

        return issueCommand(this.id, Attack_Move, -1, p.getX(), p.getY(), queued ? 1 : 0);
    }

    public boolean attack(Unit target) {

        return attack(target, false);
    }

    public boolean attack(Unit target, boolean queued) {

        return issueCommand(this.id, Attack_Unit, target.getId(), -1, -1, queued ? 1 : 0);
    }

    public boolean move(Position p) {

        return move(p, false);
    }

    public boolean move(Position p, boolean queued) {

        return issueCommand(this.id, Move, -1, p.getX(), p.getY(), queued ? 1 : 0);
    }

    public boolean patrol(Position p) {

        return patrol(p, false);
    }

    public boolean patrol(Position p, boolean queued) {

        return issueCommand(this.id, Patrol, -1, p.getX(), p.getY(), queued ? 1 : 0);
    }

    public boolean holdPosition() {

        return holdPosition(false);
    }

    public boolean holdPosition(boolean queued) {

        return issueCommand(this.id, Hold_Position, -1, -1, -1, queued ? 1 : 0);
    }

    public boolean stop(boolean queued) {

        return issueCommand(this.id, Stop, -1, -1, -1, queued ? 1 : 0);
    }

    public boolean follow(Unit target, boolean queued) {

        return issueCommand(this.id, Follow, target.getId(), -1, -1, queued ? 1 : 0);
    }

    public int getAcidSporeCount() {

        return this.acidSporeCount;
    }

    public Unit getTransport() {

        return this.getUnit(this.transportId);
    }

    public boolean isFollowing() {

        return isFollowing;
    }

    public boolean isHoldingPosition() {

        return isHoldingPosition;
    }

    public boolean isStasised() {

        return isStasised;
    }

    public boolean isUnderDarkSwarm() {

        return isUnderDarkSwarm;
    }

    public boolean isUnderDisruptionWeb() {

        return isUnderDisruptionWeb;
    }

    public boolean isUnderStorm() {

        return isUnderStorm;
    }

    public boolean isParasited() {

        return isParasited;
    }

    public boolean isPatrolling() {

        return isPatrolling;
    }

    public boolean isPlagued() {

        return isPlagued;
    }

    public boolean isMoving() {

        return this.isMoving;
    }

    public Position getTargetPosition() {

        return this.targetPosition;
    }

    @Override
    public Unit getTargetUnit() {

        return super.getTargetUnit();
    }

    public int getTurnRadius() {

        return this.type.turnRadius();
    }

    public boolean isStuck() {

        return this.isStuck;
    }

    public int getSupplyRequired() {

        return this.type.supplyRequired();
    }

    public boolean isHallucination() {

        return this.isHallucination;
    }

    public boolean isBlind() {

        return this.isBlind;
    }

    public boolean isBraking() {

        return this.isBraking;
    }

    public boolean isDefenseMatrixed() {

        return this.isDefenseMatrixed;
    }

    public boolean isEnsnared() {

        return this.isEnsnared;
    }

    public double getTopSpeed() {

        return getUnitStatCalculator().topSpeed(type);
    }
}
