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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.BwError;
import org.openbw.bwapi4j.type.GameType;
import org.openbw.bwapi4j.type.Key;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

/**
 * Contains all interaction-related bwapi functionality.
 */
public final class InteractionHandler {

	private static final Logger logger = LogManager.getLogger();

	private enum CacheIndex {
        LAST_ERROR,
        SCREEN_POSITION_X,
        SCREEN_POSITION_Y,
        SCREEN_SIZE_X,
        SCREEN_SIZE_Y,
        MOUSE_POSITION_X,
        MOUSE_POSITION_Y,
        FRAME_COUNT,
        FPS,
        LATCOM_ENABLED,
        REMAINING_LATENCY_FRAMES,
        LATENCY_FRAMES,
        LATENCY,
        GAME_TYPE_ID,
        IS_REPLAY,
        IS_PAUSED,
        SELF_ID,
        ENEMY_ID_INDEX
	}

    private BW bw;

    private BwError lastError;
    private int screenPositionX;
    private int screenPositionY;
    private int screenSizeX;
    private int screenSizeY;
    private int mousePositionX;
    private int mousePositionY;
    private int frameCount;
    private int fps;
    private boolean latComEnabled;
    private int remainingLatencyFrames;
    private int latencyFrames;
    private int latency;
    private int selfId;
    private int enemyId;
    private int gameTypeId;
    private boolean isReplay;
    private boolean isPaused;
    
    /* default */ InteractionHandler(BW bw) {
        
        this.bw = bw;
    }

    void update(int[] data) {

        this.lastError = BwError.values()[data[CacheIndex.LAST_ERROR.ordinal()]];
        this.screenPositionX = data[CacheIndex.SCREEN_POSITION_X.ordinal()];
        this.screenPositionY = data[CacheIndex.SCREEN_POSITION_Y.ordinal()];
        this.screenSizeX = data[CacheIndex.SCREEN_SIZE_X.ordinal()];
        this.screenSizeY = data[CacheIndex.SCREEN_SIZE_Y.ordinal()];
        this.mousePositionX = data[CacheIndex.MOUSE_POSITION_X.ordinal()];
        this.mousePositionY = data[CacheIndex.MOUSE_POSITION_Y.ordinal()];
        this.frameCount = data[CacheIndex.FRAME_COUNT.ordinal()];
        this.fps = data[CacheIndex.FPS.ordinal()];
        this.latComEnabled = data[CacheIndex.LATCOM_ENABLED.ordinal()] == 1;
        this.remainingLatencyFrames = data[CacheIndex.REMAINING_LATENCY_FRAMES.ordinal()];
        this.latencyFrames = data[CacheIndex.LATENCY_FRAMES.ordinal()];
        this.latency = data[CacheIndex.LATENCY_FRAMES.ordinal()];
        this.selfId = data[CacheIndex.SELF_ID.ordinal()];
        this.enemyId = data[CacheIndex.ENEMY_ID_INDEX.ordinal()];
        this.gameTypeId = data[CacheIndex.GAME_TYPE_ID.ordinal()];
        this.isReplay = data[CacheIndex.IS_REPLAY.ordinal()] == 1;
        this.isPaused = data[CacheIndex.IS_PAUSED.ordinal()] == 1;
    }

    /**
     * Creates a unit of given type for given player at the given x,y coordinates.
     * This method works with OpenBW only and will do nothing if used with original BW.
     * @param owner
     * @param type
     * @param posX
     * @param posY
     */
    public void createUnit(Player owner, UnitType type, int posX, int posY) {
    	
    	this.bw.createUnit(owner, type, posX, posY);
    }
    
    /**
     * Kills the given unit.
     * This method works with OpenBW only and will do nothing if used with original BW.
     * @param unit to kill
     */
    public void killUnit(Unit unit) {
    
    	this.bw.killUnit(unit);
    }
    
    /**
     * @return the bot player
     */
    public Player self() {
    	
        return this.bw.getPlayer(this.selfId);
    }

    /**
     * @return the enemy player in a 1on1 game
     */
    public Player enemy() {
    	
        return this.bw.getPlayer(this.enemyId);
    }

    public List<Player> allies() {

        final List<Player> allies = new ArrayList<>();

        final int[] allyIds = allies_native();

        if (allyIds == null) {
            throw new IllegalStateException("Failed to create allies list.");
        }

        for (int id = 0; id < allyIds.length; ++id) {
            final int allyId = allyIds[id];
            if (allyId >= 0) {
                final Player ally = this.bw.getPlayer(allyId);
                allies.add(ally);
            }
        }

        return allies;
    }

    private native int[] allies_native();

    public List<Player> enemies() {

        final List<Player> enemies = new ArrayList<>();

        final int[] enemyIds = enemies_native();

        if (enemyIds == null) {
            throw new IllegalStateException("Failed to create enemies list.");
        }

        for (int id = 0; id < enemyIds.length; ++id) {
            final int enemyId = enemyIds[id];
            if (enemyId >= 0) {
                final Player enemy = this.bw.getPlayer(enemyId);
                enemies.add(enemy);
            }
        }

        return enemies;
    }

    private native int[] enemies_native();

    public BwError getLastError() {
    	
        return this.lastError;
    }

    public Position getScreenPosition() {
    	
        return new Position(screenPositionX, screenPositionY);
    }

    public Position getScreenSize() {

        return new Position(screenSizeX, screenSizeY);
    }

    public Position getMousePosition() {
    	
        return new Position(mousePositionX, mousePositionY);
    }

    public int getFrameCount() {
    	
        return this.frameCount;
    }

    public int getFPS() {
    	
        return this.fps;
    }

    public boolean isLatComEnabled() {
    	
        return this.latComEnabled;
    }

    public int getRemainingLatencyFrames() {
    	
        return this.remainingLatencyFrames;
    }

    public int getLatencyFrames() {
    	
        return this.latencyFrames;
    }

    public int getLatency() {
    	
        return this.latency;
    }

    public GameType getGameType() {

        return GameType.values()[this.gameTypeId];
    }

    public boolean isReplay() {

        return this.isReplay;
    }

    public boolean isPaused() {

        return this.isPaused;
    }

    public List<Unit> getSelectedUnits() {
    	
        return bw.getAllUnits().stream().filter(Unit::isSelected).collect(Collectors.toList());
    }

    public boolean isKeyPressed(Key key) {
    	
        return getKeyState(key.getValue());
    }

    private native boolean getKeyState(int keyCode);

    public native void leaveGame();

    public void sendTextAndLog(String text) {
    
    	logger.info(text);
    	sendText(text);
    }

    public native void printf(String text);

    public native void sendText(String text);

    public native void setLocalSpeed(int speed);

    public native void enableLatCom(boolean enabled);

    public native void enableUserInput();

    public native void enableCompleteMapInformation();

    public native long getRandomSeed();

    public native void setFrameSkip(int frameSkip);

}
