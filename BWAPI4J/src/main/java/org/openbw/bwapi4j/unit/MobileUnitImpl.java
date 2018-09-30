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

import static org.openbw.bwapi4j.type.UnitCommandType.Attack_Move;
import static org.openbw.bwapi4j.type.UnitCommandType.Attack_Unit;
import static org.openbw.bwapi4j.type.UnitCommandType.Follow;
import static org.openbw.bwapi4j.type.UnitCommandType.Hold_Position;
import static org.openbw.bwapi4j.type.UnitCommandType.Move;
import static org.openbw.bwapi4j.type.UnitCommandType.Patrol;
import static org.openbw.bwapi4j.type.UnitCommandType.Stop;

import org.openbw.bwapi4j.Position;

public abstract class MobileUnitImpl extends PlayerUnitImpl implements MobileUnit {
  public boolean attack(Position p) {
    return attack(p, false);
  }

  public boolean attack(Position p, boolean queued) {
    return issueCommand(this.iD, Attack_Move, -1, p.getX(), p.getY(), queued ? 1 : 0);
  }

  public boolean attack(Unit target) {
    return attack(target, false);
  }

  public boolean attack(Unit target, boolean queued) {
    return issueCommand(this.iD, Attack_Unit, target.getId(), -1, -1, queued ? 1 : 0);
  }

  public boolean move(Position p) {
    return move(p, false);
  }

  public boolean move(Position p, boolean queued) {
    return issueCommand(this.iD, Move, -1, p.getX(), p.getY(), queued ? 1 : 0);
  }

  public boolean patrol(Position p) {
    return patrol(p, false);
  }

  public boolean patrol(Position p, boolean queued) {
    return issueCommand(this.iD, Patrol, -1, p.getX(), p.getY(), queued ? 1 : 0);
  }

  public boolean holdPosition() {
    return holdPosition(false);
  }

  public boolean holdPosition(boolean queued) {
    return issueCommand(this.iD, Hold_Position, -1, -1, -1, queued ? 1 : 0);
  }

  public boolean stop(boolean queued) {
    return issueCommand(this.iD, Stop, -1, -1, -1, queued ? 1 : 0);
  }

  public boolean follow(Unit target, boolean queued) {
    return issueCommand(this.iD, Follow, target.getId(), -1, -1, queued ? 1 : 0);
  }

  public int getAcidSporeCount() {
    return this.acidSporeCount;
  }

  public Loadable getTransport() {
    return (Loadable) transport;
  }

  public boolean isFollowing() {
    return following;
  }

  public boolean isHoldingPosition() {
    return holdingPosition;
  }

  public boolean isStasised() {
    return stasised;
  }

  public boolean isUnderDarkSwarm() {
    return underDarkSwarm;
  }

  public boolean isUnderDisruptionWeb() {
    return underDisruptionWeb;
  }

  public boolean isUnderStorm() {
    return underStorm;
  }

  public boolean isParasited() {
    return parasited;
  }

  public boolean isPatrolling() {
    return patrolling;
  }

  public boolean isPlagued() {
    return plagued;
  }

  public boolean isMoving() {
    return this.moving;
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
    return this.stuck;
  }

  public int getSupplyRequired() {
    return this.type.supplyRequired();
  }

  public boolean isHallucination() {
    return this.hallucination;
  }

  public boolean isBlind() {
    return this.blind;
  }

  public boolean isBraking() {
    return this.braking;
  }

  public boolean isDefenseMatrixed() {
    return this.defenseMatrixed;
  }

  public boolean isEnsnared() {
    return this.ensnared;
  }

  public double getTopSpeed() {
    return getUnitStatCalculator().topSpeed(type);
  }
}
