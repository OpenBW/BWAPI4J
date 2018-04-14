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

public class Bullet {

	public static int EXISTS_INDEX = 0;
    public static int ANGLE_INDEX = 1;
    public static int ID_INDEX = 2;
    public static int PLAYER_INDEX = 3;
    public static int POSITION_X_INDEX = 4;
    public static int POSITION_Y_INDEX = 5;
    public static int REMOVE_TIMER_INDEX = 6;
    public static int SOURCE_INDEX = 7;
    public static int TARGET_INDEX = 8;
    public static int TARGET_POSITION_X_INDEX = 9;
    public static int TARGET_POSITION_Y_INDEX = 10;
    public static int TYPE_INDEX = 11;
    public static int VELOCITY_X_INDEX = 12;
    public static int VELOCITY_Y_INDEX = 13;
    public static int VISIBLE_INDEX = 14;
    
    public static int TOTAL_PROPERTIES = 15;
    
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

    	this.id = bulletData[index + Bullet.ID_INDEX];
    	this.playerId = bulletData[index + Bullet.PLAYER_INDEX];
    	this.sourceId = bulletData[index + Bullet.SOURCE_INDEX];
        this.type = BulletType.valueOf(bulletData[index + Bullet.TYPE_INDEX]);
    	this.playerId = bulletData[index + Bullet.PLAYER_INDEX];
    }

    /**
     * Updates dynamic bullet information. To be called once per frame.
     * @param bulletData raw data array
     * @param index current pointer
     */
    public void update(int[] bulletData, int index) {

    	this.exists = bulletData[index + Bullet.EXISTS_INDEX] == 1;
    	this.angle = bulletData[index + Bullet.ANGLE_INDEX] / 100.0;
    	int x = bulletData[index + Bullet.POSITION_X_INDEX];
        int y = bulletData[index + Bullet.POSITION_Y_INDEX];
        this.position = new Position(x, y);
        this.removeTimer = bulletData[index + Bullet.REMOVE_TIMER_INDEX];
        this.targetId = bulletData[index + Bullet.TARGET_INDEX];
        int tx = bulletData[index + Bullet.TARGET_POSITION_X_INDEX];
        int ty = bulletData[index + Bullet.TARGET_POSITION_Y_INDEX];
        this.targetPosition = new Position(tx, ty);
    	this.velocityX = bulletData[index + Bullet.VELOCITY_X_INDEX] / 100.0;
        this.velocityY = bulletData[index + Bullet.VELOCITY_Y_INDEX] / 100.0;
        this.visible = bulletData[index + Bullet.VISIBLE_INDEX] == 1;
    }
    
    public Player getPlayer() {
		
		return bw.getPlayer(playerId);
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
