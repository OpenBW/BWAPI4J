package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;

public interface MobileUnit extends PlayerUnit {

	boolean attack(Position p);

	boolean attack(Position p, boolean queued);

	boolean attack(Unit target);

	boolean attack(Unit target, boolean queued);

	boolean move(Position p);

	boolean move(Position p, boolean queued);

	boolean patrol(Position p);

	boolean patrol(Position p, boolean queued);

	boolean holdPosition();

	boolean holdPosition(boolean queued);

	boolean stop(boolean queued);

	boolean follow(Unit target, boolean queued);

	int getAcidSporeCount();

	Unit getTransport();

	boolean isFollowing();

	boolean isHoldingPosition();

	boolean isStasised();

	boolean isUnderDarkSwarm();

	boolean isUnderDisruptionWeb();

	boolean isUnderStorm();

	boolean isParasited();

	boolean isPatrolling();

	boolean isPlagued();

	boolean isMoving();

	Position getTargetPosition();

	Unit getTargetUnit();

	int getTurnRadius();

	boolean isStuck();

	int getSupplyRequired();

	boolean isHallucination();

	boolean isBlind();

	boolean isBraking();

	boolean isDefenseMatrixed();

	boolean isEnsnared();

	double getTopSpeed();

}