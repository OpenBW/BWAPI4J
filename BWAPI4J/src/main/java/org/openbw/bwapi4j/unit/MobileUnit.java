package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public abstract class MobileUnit extends PlayerUnit implements Armed {

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
    private int transportId;
    private int acidSporeCount;
    private boolean isHallucination;
    private boolean isBlind;
    private boolean isBraking;
    private boolean isDefenseMatrixed;
    private boolean isEnsnared;
    private int remainingTrainTime;

    protected MobileUnit(int id, UnitType unitType) {

        super(id, unitType);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

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
        this.targetPosition = new Position(unitData[index + Unit.TARGET_POSITION_X_INDEX],
                unitData[index + Unit.TARGET_POSITION_Y_INDEX]);
        this.transportId = unitData[index + Unit.TRANSPORT_INDEX];
        this.acidSporeCount = unitData[index + Unit.ACID_SPORE_COUNT_INDEX];
        this.isHallucination = unitData[index + Unit.IS_HALLUCINATION_INDEX] == 1;
        this.isBlind = unitData[index + Unit.IS_BLIND_INDEX] == 1;
        this.isBraking = unitData[index + Unit.IS_BRAKING_INDEX] == 1;
        this.isDefenseMatrixed = unitData[index + Unit.IS_DEFENSE_MATRIXED_INDEX] == 1;
        this.isEnsnared = unitData[index + Unit.IS_ENSNARED_INDEX] == 1;
        remainingTrainTime = unitData[index + Unit.REMAINING_TRAIN_TIME_INDEX];
        super.update(unitData, index, frame);
    }

    public boolean attack(Position p) {

        return attack(p, false);
    }

    public boolean attack(Position p, boolean queued) {

        return issueCommand(this.id, UnitCommandType.Attack_Move.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
    }

    public boolean attack(Unit target) {

        return attack(target, false);
    }

    public boolean attack(Unit target, boolean queued) {

        return issueCommand(this.id, UnitCommandType.Attack_Unit.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
    }

    public boolean move(Position p) {

        return move(p, false);
    }

    public boolean move(Position p, boolean queued) {

        return issueCommand(this.id, UnitCommandType.Move.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
    }

    public boolean patrol(Position p) {

        return patrol(p, false);
    }

    public boolean patrol(Position p, boolean queued) {

        return issueCommand(this.id, UnitCommandType.Patrol.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
    }

    public boolean holdPosition() {

        return holdPosition(false);
    }

    public boolean holdPosition(boolean queued) {

        return issueCommand(this.id, UnitCommandType.Hold_Position.ordinal(), -1, -1, -1, queued ? 1 : 0);
    }

    public boolean stop(boolean queued) {

        return issueCommand(this.id, UnitCommandType.Stop.ordinal(), -1, -1, -1, queued ? 1 : 0);
    }

    public boolean follow(Unit target, boolean queued) {

        return issueCommand(this.id, UnitCommandType.Follow.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
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

    @Override
    public Weapon getGroundWeapon() {

        return groundWeapon;
    }

    @Override
    public Weapon getAirWeapon() {

        return airWeapon;
    }

    public int getMaxGroundHits() {
        return this.type.maxGroundHits();
    }

    public int getMaxAirHits() {
        return this.type.maxAirHits();
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

    public int getRemainingTrainTime() {
        return remainingTrainTime;
    }
}
