package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.WeaponType;

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
	private Position targetPosition;
	private Unit target;
	
	MobileUnit(int id, UnitType unitType) {
		super(id, unitType);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		super.initialize(unitData, index, allUnits);
		
		return index;
	}

	@Override
	public int update(int[] unitData, int index) {
		
		super.update(unitData, index);
		this.isFollowing = unitData[index + Unit.IS_FOLLOWING_INDEX] == 1;
		this.isHoldingPosition = unitData[index + Unit.IS_HOLDING_POSITION_INDEX] == 1;
		this.isStuck = unitData[index + Unit.IS_STUCK_INDEX] == 1;
		this.isStasised = unitData[index + Unit.IS_STASISED_INDEX] == 1;
		this.isUnderDarkSwarm = unitData[index + Unit.IS_UNDER_DARK_SWARM_INDEX] == 1;
		this.isUnderDisruptionWeb = unitData[index + Unit.IS_UNDER_DISRUPTION_WEB_INDEX] == 1;
		this.isUnderStorm = unitData[index + Unit.IS_UNDER_STORM_INDEX] == 1;
		this.isMoving = unitData[index + Unit.IS_MOVING_INDEX] == 1;
		this.isParasited = unitData[index + Unit.IS_PARASITED_INDEX] == 1;
		this.isPatrolling = unitData[index + Unit.IS_PATROLLING_INDEX] == 1;
		this.isPlagued = unitData[index + Unit.IS_PLAGUED_INDEX] == 1;
		this.targetPosition = new Position(unitData[index + Unit.TARGET_POSITION_X_INDEX], unitData[index + Unit.TARGET_POSITION_Y_INDEX]);
		this.target = super.getUnit(unitData[index + Unit.TARGET_ID_INDEX]);
		
		return index;
	}
	
	protected boolean attack(Position p) {
		return attack(p, false);
	}
	
	protected boolean attack(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Attack_Move.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
	
	protected boolean attack(Unit target) {
		return attack(target, false);
	}
	
	protected boolean attack(Unit target, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Attack_Unit.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
	}
	
	protected boolean move(Position p) {
		return move(p, false);
	}
	
	protected boolean move(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Move.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
	
	protected boolean patrol(Position p) {
		return patrol(p, false);
	}
	
	protected boolean patrol(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Patrol.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
	
	protected boolean holdPosition() {
		return holdPosition(false);
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

	public Unit getTargetUnit() {
		return target;
	}
	
	public WeaponType getGroundWeapon() {
		return this.type.groundWeapon();
	}
	
	public WeaponType getAirWeapon() {
		return this.type.airWeapon();
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
}
