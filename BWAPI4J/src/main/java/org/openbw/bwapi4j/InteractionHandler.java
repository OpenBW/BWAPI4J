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

	public enum TextSize {
	    Small,
        Default,
        Large,
        Huge
    }

    public static int LAST_ERROR_INDEX                  = 0;
    public static int SCREEN_POSITION_X_INDEX           = 1;
    public static int SCREEN_POSITION_Y_INDEX           = 2;
    public static int SCREEN_SIZE_X_INDEX               = 3;
    public static int SCREEN_SIZE_Y_INDEX               = 4;
    public static int MOUSE_POSITION_X_INDEX            = 5;
    public static int MOUSE_POSITION_Y_INDEX            = 6;
    public static int FRAMECOUNT_INDEX                  = 7;
    public static int FPS_INDEX                         = 8;
    public static int LATCOM_ENABLED_INDEX              = 9;
    public static int REMAINING_LATENCY_FRAMES_INDEX    = 10;
    public static int LATENCY_FRAMES_INDEX              = 11;
    public static int LATENCY_INDEX                     = 12;
    public static int GAME_TYPE_ID_INDEX                = 13;
    public static int IS_REPLAY_INDEX                   = 14;
    public static int IS_PAUSED_INDEX                   = 15;
    public static int SELF_ID_INDEX                     = 16;
    public static int ENEMY_ID_INDEX                    = 17;

    public static int TOTAL_PROPERTIES                  = 18;

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

        this.lastError = BwError.values()[data[LAST_ERROR_INDEX]];
        this.screenPositionX = data[SCREEN_POSITION_X_INDEX];
        this.screenPositionY = data[SCREEN_POSITION_Y_INDEX];
        this.screenSizeX = data[SCREEN_SIZE_X_INDEX];
        this.screenSizeY = data[SCREEN_SIZE_Y_INDEX];
        this.mousePositionX = data[MOUSE_POSITION_X_INDEX];
        this.mousePositionY = data[MOUSE_POSITION_Y_INDEX];
        this.frameCount = data[FRAMECOUNT_INDEX];
        this.fps = data[FPS_INDEX];
        this.latComEnabled = data[LATCOM_ENABLED_INDEX] == 1;
        this.remainingLatencyFrames = data[REMAINING_LATENCY_FRAMES_INDEX];
        this.latencyFrames = data[LATENCY_FRAMES_INDEX];
        this.latency = data[LATENCY_FRAMES_INDEX];
        this.selfId = data[SELF_ID_INDEX];
        this.enemyId = data[ENEMY_ID_INDEX];
        this.gameTypeId = data[GAME_TYPE_ID_INDEX];
        this.isReplay = data[IS_REPLAY_INDEX] == 1;
        this.isPaused = data[IS_PAUSED_INDEX] == 1;
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
    	
        return bw.getAllUnits().stream().filter(u -> u.isSelected()).collect(Collectors.toList());
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

    public void setTextSize(TextSize textSize) {

        setTextSize(textSize.ordinal());
    }

    private native void setTextSize(int textSize);

}
