package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public abstract class MobileUnit extends PlayerUnit {

	private boolean isFollowing;
	private boolean isHoldingPosition;
	private boolean isStuck;
	private boolean isStasised;
	private boolean isUnderDarkSwarm;
	private boolean isUnderDisruptionWeb;
	private boolean isUnderStorm;
	private boolean isMoving;
	private boolean isParasited;
	private boolean isPatrolling;
	private boolean isPlagued;
	
	MobileUnit(int id, UnitType unitType) {
		super(id, unitType);
	}
	
	protected boolean attack(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Attack_Move.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
	
	protected boolean attack(Unit target, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Attack_Unit.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
	}
	
	protected boolean move(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Move.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
	
	protected boolean patrol(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Patrol.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
	
	protected boolean holdPosition(boolean queued) {
		return issueCommand(this.id, UnitCommandType.Hold_Position.ordinal(), -1, -1, -1, queued ? 1 : 0);
	}
	
	protected boolean stop(boolean queued) {
		return issueCommand(this.id, UnitCommandType.Stop.ordinal(), -1, -1, -1, queued ? 1 : 0);
	}
	
	protected boolean follow(Unit target, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Follow.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
	}
}
