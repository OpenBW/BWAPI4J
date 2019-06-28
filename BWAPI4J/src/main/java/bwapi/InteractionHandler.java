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

package bwapi;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.util.BridgeUtils;
import org.openbw.bwapi4j.util.Cache;
import org.openbw.bwapi4j.util.buffer.BwapiDataBuffer;
import org.openbw.bwapi4j.util.buffer.DataBuffer;

public final class InteractionHandler {
  private static final Logger logger = LogManager.getLogger();

  private enum CacheIndex {
    SCREEN_POSITION_X,
    SCREEN_POSITION_Y,
    SCREEN_SIZE_X,
    SCREEN_SIZE_Y,
    MOUSE_POSITION_X,
    MOUSE_POSITION_Y,
    FRAME_COUNT,
    REPLAY_FRAME_COUNT,
    FPS,
    AVERAGE_FPS,
    LATCOM_ENABLED,
    REMAINING_LATENCY_FRAMES,
    REMAINING_LATENCY_TIME,
    LATENCY_FRAMES,
    LATENCY_TIME,
    LATENCY,
    GAME_TYPE_ID,
    IS_REPLAY,
    IS_PAUSED,
    APM,
    APM_INCLUDING_SELECTS,
    SELF_ID,
    ENEMY_ID,
    NEUTRAL_ID,
  }

  private final BW bw;

  private int screenPositionX;
  private int screenPositionY;
  private int screenSizeX;
  private int screenSizeY;
  private int mousePositionX;
  private int mousePositionY;
  private int frameCount;
  private int replayFrameCount;
  private int fps;
  private double averageFPS;
  private boolean latComEnabled;
  private int remainingLatencyFrames;
  private int remainingLatencyTime;
  private int latencyFrames;
  private int latencyTime;
  private int latency;
  private int selfId;
  private int enemyId;
  private int neutralId;
  private int gameTypeId;
  private boolean isReplay;
  private boolean isPaused;
  private int apm;
  private int apm_including_selects;

  private Cache<List<Player>> getAlliesCache;
  private Cache<List<Player>> getEnemiesCache;

  private Cache<List<Position>> getNukeDotsCache;

  InteractionHandler(final BW bw) {
    this.bw = bw;
    resetCache();
  }

  void resetCache() {
    this.getAlliesCache = new Cache<>(this::allies_from_native, this);
    this.getEnemiesCache = new Cache<>(this::enemies_from_native, this);
    this.getNukeDotsCache = new Cache<>(this::getNukeDotsData, this);
  }

  void update(int[] data) {
    this.screenPositionX = data[CacheIndex.SCREEN_POSITION_X.ordinal()];
    this.screenPositionY = data[CacheIndex.SCREEN_POSITION_Y.ordinal()];
    this.screenSizeX = data[CacheIndex.SCREEN_SIZE_X.ordinal()];
    this.screenSizeY = data[CacheIndex.SCREEN_SIZE_Y.ordinal()];
    this.mousePositionX = data[CacheIndex.MOUSE_POSITION_X.ordinal()];
    this.mousePositionY = data[CacheIndex.MOUSE_POSITION_Y.ordinal()];
    this.frameCount = data[CacheIndex.FRAME_COUNT.ordinal()];
    this.replayFrameCount = data[CacheIndex.REPLAY_FRAME_COUNT.ordinal()];
    this.fps = data[CacheIndex.FPS.ordinal()];
    this.averageFPS = BridgeUtils.parsePreservedDouble(data[CacheIndex.AVERAGE_FPS.ordinal()]);
    this.latComEnabled = data[CacheIndex.LATCOM_ENABLED.ordinal()] == 1;
    this.remainingLatencyFrames = data[CacheIndex.REMAINING_LATENCY_FRAMES.ordinal()];
    this.remainingLatencyTime = data[CacheIndex.REMAINING_LATENCY_TIME.ordinal()];
    this.latencyFrames = data[CacheIndex.LATENCY_FRAMES.ordinal()];
    this.latencyTime = data[CacheIndex.LATENCY_TIME.ordinal()];
    this.latency = data[CacheIndex.LATENCY.ordinal()];
    this.selfId = data[CacheIndex.SELF_ID.ordinal()];
    this.enemyId = data[CacheIndex.ENEMY_ID.ordinal()];
    this.neutralId = data[CacheIndex.NEUTRAL_ID.ordinal()];
    this.gameTypeId = data[CacheIndex.GAME_TYPE_ID.ordinal()];
    this.isReplay = data[CacheIndex.IS_REPLAY.ordinal()] == 1;
    this.isPaused = data[CacheIndex.IS_PAUSED.ordinal()] == 1;
    this.apm = data[CacheIndex.APM.ordinal()];
    this.apm_including_selects = data[CacheIndex.APM_INCLUDING_SELECTS.ordinal()];
  }

  /** @return the bot player */
  public Player self() {
    return this.bw.getPlayer(this.selfId);
  }

  /** @return the enemy player in a 1on1 game */
  public Player enemy() {
    return this.bw.getPlayer(this.enemyId);
  }

  public List<Player> allies() {
    return this.getAlliesCache.get();
  }

  private native int[] allies_native();

  private List<Player> allies_from_native() {
    final int[] data = allies_native();

    return parsePlayers(data);
  }

  public List<Player> enemies() {
    return getEnemiesCache.get();
  }

  private native int[] enemies_native();

  private List<Player> enemies_from_native() {
    final int[] data = enemies_native();

    return parsePlayers(data);
  }

  public Player neutral() {
    return this.bw.getPlayer(this.neutralId);
  }

  private List<Player> parsePlayers(final int[] data) {
    return BwapiDataBuffer.getPlayersByIds(new DataBuffer(data), bw);
  }

  private native int getLastError_native();

  public Error getLastError() {
    return Error.withId(getLastError_native());
  }

  public Position getScreenPosition() {
    return new Position(screenPositionX, screenPositionY);
  }

  private native void setScreenPosition_native(int pixelX, int pixelY);

  public void setScreenPosition(final Position position) {
    setScreenPosition_native(position.getX(), position.getY());
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

  public int getReplayFrameCount() {
    return this.replayFrameCount;
  }

  public int getFPS() {
    return this.fps;
  }

  public double getAverageFPS() {
    return this.averageFPS;
  }

  public boolean isLatComEnabled() {
    return this.latComEnabled;
  }

  private native boolean isFlagEnabled_native(int flag);

  public boolean isFlagEnabled(final int flag) {
    return isFlagEnabled_native(flag);
  }

  private native void enableFlag_native(int flag);

  public void enableFlag(final int flag) {
    enableFlag_native(flag);
  }

  public void enableFlag(final Flag flag) {
    enableFlag_native(flag.intValue());
  }

  public int getRemainingLatencyFrames() {
    return this.remainingLatencyFrames;
  }

  public int getRemainingLatencyTime() {
    return this.remainingLatencyTime;
  }

  public native int getRevision();

  public int getLatencyFrames() {
    return this.latencyFrames;
  }

  public int getLatencyTime() {
    return this.latencyTime;
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

  public int getAPM() {
    return this.apm;
  }

  public int getAPM(final boolean includeSelects) {
    return (includeSelects ? this.apm_including_selects : this.apm);
  }

  public List<Unit> getSelectedUnits() {
    return bw.getAllUnits().stream().filter(Unit::isSelected).collect(Collectors.toList());
  }

  private native boolean getKeyState_native(int keyValue);

  public boolean getKeyState(final Key key) {
    return getKeyState_native(key.getValue());
  }

  private native boolean getMouseState_native(int mouseButtonValue);

  public boolean getMouseState(final MouseButton mouseButton) {
    return getMouseState_native(mouseButton.ordinal());
  }

  public native void leaveGame();

  public void sendTextAndLog(final String text) {
    logger.info(text);
    sendText(text);
  }

  public native void printf(String text);

  public native void sendText(String text);

  public native void sendTextEx(boolean toAllies, String text);

  public native void setLocalSpeed(int speed);

  public native void enableLatCom(boolean enabled);

  public native long getRandomSeed();

  public native void setFrameSkip(int frameSkip);

  public native void pauseGame();

  public native void resumeGame();

  public native void restartGame();

  public native boolean isGUIEnabled();

  public native void setGUI(boolean enabled);

  private native int[] getNukeDotsData_native();

  private List<Position> getNukeDotsData() {
    return BwapiDataBuffer.readPositions(new DataBuffer(getNukeDotsData_native()));
  }

  public List<Position> getNukeDots() {
    return this.getNukeDotsCache.get();
  }

  private native void pingMiniMap_native(int x, int y);

  public void pingMiniMap(final int x, final int y) {
    pingMiniMap_native(x, y);
  }

  public void pingMiniMap(final Position position) {
    pingMiniMap_native(position.getX(), position.getY());
  }
}
