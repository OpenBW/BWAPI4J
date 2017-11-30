package org.openbw.bwapi4j;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.BwError;
import org.openbw.bwapi4j.type.Key;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

/**
 * Contains all interaction-related bwapi functionality.
 */
public final class InteractionHandler {

	private static final Logger logger = LogManager.getLogger();
	
    public static int LAST_ERROR_INDEX                  = 0;
    public static int SCREEN_POSITION_X_INDEX           = 1;
    public static int SCREEN_POSITION_Y_INDEX           = 2;
    public static int MOUSE_POSITION_X_INDEX            = 3;
    public static int MOUSE_POSITION_Y_INDEX            = 4;
    public static int FRAMECOUNT_INDEX                  = 5;
    public static int FPS_INDEX                         = 6;
    public static int LATCOM_ENABLED_INDEX              = 7;
    public static int REMAINING_LATENCY_FRAMES_INDEX    = 8;
    public static int LATENCY_FRAMES_INDEX              = 9;
    public static int LATENCY_INDEX                     = 10;
    public static int SELF_ID_INDEX                     = 11;
    public static int ENEMY_ID_INDEX                    = 12;

    public static int TOTAL_PROPERTIES                  = 13;

    private BW bw;

    private BwError lastError;
    private int screenPositionX;
    private int screenPositionY;
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
    
    /* default */ InteractionHandler(BW bw) {
        
        this.bw = bw;
    }

    void update(int[] data) {

        this.lastError = BwError.values()[data[LAST_ERROR_INDEX]];
        this.screenPositionX = data[SCREEN_POSITION_X_INDEX];
        this.screenPositionY = data[SCREEN_POSITION_Y_INDEX];
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
    
    public native void sendText(String text);

    public native void setLocalSpeed(int speed);

    public native void enableLatCom(boolean enabled);

    public native void enableUserInput();

    public native void enableCompleteMapInformation();
}
