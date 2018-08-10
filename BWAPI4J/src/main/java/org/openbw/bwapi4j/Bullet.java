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

package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.BulletType;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.util.BridgeUtils;

public class Bullet {

    enum CacheIndex {
		EXISTS,
		ANGLE,
		ID,
		PLAYER,
		POSITION_X,
		POSITION_Y,
		REMOVE_TIMER,
		SOURCE,
		TARGET,
		TARGET_POSITION_X,
		TARGET_POSITION_Y,
		TYPE,
		VELOCITY_X,
		VELOCITY_Y,
		VISIBLE
	}
    
	private boolean exists;
	private double angle;
	private int id;
	private int playerId;
	private Position position;
	private int removeTimer;
	private int sourceId;
	private int targetId;
	private Position targetPosition;
	private BulletType type;
	private double velocityX;
	private double velocityY;
	private boolean visible;
	private Player player;
	
	private BW bw;
	
	Bullet(BW bw) {
		
		this.bw = bw;
	}
	
	/**
     * Initializes a bullet with static information (constant through the course of a game).
     * @param bulletData raw data array
     * @param index current pointer
     */
    public void initialize(int[] bulletData, int index) {

    	this.id = bulletData[index + CacheIndex.ID.ordinal()];
    	this.playerId = bulletData[index + CacheIndex.PLAYER.ordinal()];
		this.player = bw.getPlayer(this.playerId);
    	this.sourceId = bulletData[index + CacheIndex.SOURCE.ordinal()];
        this.type = BulletType.valueOf(bulletData[index + CacheIndex.TYPE.ordinal()]);
    }

    /**
     * Updates dynamic bullet information. To be called once per frame.
     * @param bulletData raw data array
     * @param index current pointer
     */
    public void update(int[] bulletData, int index) {

    	this.exists = bulletData[index + CacheIndex.EXISTS.ordinal()] == 1;
    	this.angle = BridgeUtils.parsePreservedBwapiAngle(BridgeUtils.parsePreservedDouble(bulletData[index + CacheIndex.ANGLE.ordinal()]));
    	int x = bulletData[index + CacheIndex.POSITION_X.ordinal()];
        int y = bulletData[index + CacheIndex.POSITION_Y.ordinal()];
        this.position = new Position(x, y);
        this.removeTimer = bulletData[index + CacheIndex.REMOVE_TIMER.ordinal()];
        this.targetId = bulletData[index + CacheIndex.TARGET.ordinal()];
        int tx = bulletData[index + CacheIndex.TARGET_POSITION_X.ordinal()];
        int ty = bulletData[index + CacheIndex.TARGET_POSITION_Y.ordinal()];
        this.targetPosition = new Position(tx, ty);
    	this.velocityX = BridgeUtils.parsePreservedDouble(bulletData[index + CacheIndex.VELOCITY_X.ordinal()]);
        this.velocityY = BridgeUtils.parsePreservedDouble(bulletData[index + CacheIndex.VELOCITY_Y.ordinal()]);
        this.visible = bulletData[index + CacheIndex.VISIBLE.ordinal()] == 1;
    }
    
    public Player getPlayer() {
		
		return this.player;
	}
    
    public Unit getSource() {
    	
    	return this.bw.getUnit(this.sourceId);
    }
    
    public Unit getTarget() {
    	
    	return this.bw.getUnit(this.targetId);
    }

	public boolean isExists() {
		
		return exists;
	}

	/**
	 * Returns the angle in degrees.
	 */
	public double getAngle() {
		
		return angle;
	}

	public int getId() {
		
		return id;
	}

	public Position getPosition() {
		
		return position;
	}

	public int getRemoveTimer() {
		
		return removeTimer;
	}

	public Position getTargetPosition() {
		
		return targetPosition;
	}

	public BulletType getType() {
		
		return type;
	}

	public double getVelocityX() {
		
		return velocityX;
	}

	public double getVelocityY() {
		
		return velocityY;
	}

	public boolean isVisible() {
		
		return visible;
	}
}
