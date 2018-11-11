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
import org.openbw.bwapi4j.util.BridgeUtils;
import org.openbw.bwapi4j.util.Cache;

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
    FPS,
    AVERAGE_FPS,
    LATCOM_ENABLED,
    REMAINING_LATENCY_FRAMES,
    LATENCY_FRAMES,
    LATENCY,
    GAME_TYPE_ID,
    IS_REPLAY,
    IS_PAUSED,
    APM,
    APM_INCLUDING_SELECTS,
    SELF_ID,
    ENEMY_ID
  }

  private final BW bw;

  private int screenPositionX;
  private int screenPositionY;
  private int screenSizeX;
  private int screenSizeY;
  private int mousePositionX;
  private int mousePositionY;
  private int frameCount;
  private int fps;
  private double averageFPS;
  private boolean latComEnabled;
  private int remainingLatencyFrames;
  private int latencyFrames;
  private int latency;
  private int selfId;
  private int enemyId;
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
    this.fps = data[CacheIndex.FPS.ordinal()];
    this.averageFPS = BridgeUtils.parsePreservedDouble(data[CacheIndex.AVERAGE_FPS.ordinal()]);
    this.latComEnabled = data[CacheIndex.LATCOM_ENABLED.ordinal()] == 1;
    this.remainingLatencyFrames = data[CacheIndex.REMAINING_LATENCY_FRAMES.ordinal()];
    this.latencyFrames = data[CacheIndex.LATENCY_FRAMES.ordinal()];
    this.latency = data[CacheIndex.LATENCY.ordinal()];
    this.selfId = data[CacheIndex.SELF_ID.ordinal()];
    this.enemyId = data[CacheIndex.ENEMY_ID.ordinal()];
    this.gameTypeId = data[CacheIndex.GAME_TYPE_ID.ordinal()];
    this.isReplay = data[CacheIndex.IS_REPLAY.ordinal()] == 1;
    this.isPaused = data[CacheIndex.IS_PAUSED.ordinal()] == 1;
    this.apm = data[CacheIndex.APM.ordinal()];
    this.apm_including_selects = data[CacheIndex.APM_INCLUDING_SELECTS.ordinal()];
  }

  /**
   * Creates a unit of given type for given player at the given x,y coordinates. This method works
   * with OpenBW only and will do nothing if used with original BW.
   *
   * @param owner
   * @param type
   * @param posX
   * @param posY
   */
  public void createUnit(Player owner, UnitType type, int posX, int posY) {
    this.bw.createUnit(owner, type, posX, posY);
  }

  /**
   * Kills the given unit. This method works with OpenBW only and will do nothing if used with
   * original BW.
   *
   * @param unit to kill
   */
  public void killUnit(Unit unit) {
    this.bw.killUnit(unit);
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

  private List<Player> parsePlayers(final int[] data) {
    final List<Player> players = new ArrayList<>();

    for (int i = 0; i < data.length; ++i) {
      final int playerId = data[i];
      final Player player = bw.getPlayer(playerId);
      if (player != null) {
        players.add(player);
      }
    }

    return players;
  }

  private native int getLastError_native();

  public BwError getLastError() {
    final int lastErrorId = getLastError_native();
    return BwError.values()[lastErrorId];
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

  public int getFPS() {
    return this.fps;
  }

  public double getAverageFPS() {
    return this.averageFPS;
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

  public int getAPM() {
    return this.apm;
  }

  public int getAPM(final boolean includeSelects) {
    return (includeSelects ? this.apm_including_selects : this.apm);
  }

  public List<Unit> getSelectedUnits() {
    return bw.getAllUnits().stream().filter(Unit::isSelected).collect(Collectors.toList());
  }

  public boolean isKeyPressed(final Key key) {
    return getKeyState(key.getValue());
  }

  private native boolean getKeyState(int keyCode);

  public native void leaveGame();

  public void sendTextAndLog(final String text) {
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

  public native void pauseGame();

  public native void resumeGame();

  public native void restartGame();

  public native void setGUI(boolean enabled);

  private native int[] getNukeDotsData_native();

  private List<Position> getNukeDotsData() {
    final List<Position> nukeDotPositions = new ArrayList<>();

    final int[] data = getNukeDotsData_native();

    int index = 0;

    while (index < data.length) {
      final int x = data[index++];
      final int y = data[index++];

      final Position nukeDotPosition = new Position(x, y);

      nukeDotPositions.add(nukeDotPosition);
    }

    return nukeDotPositions;
  }

  public List<Position> getNukeDots() {
    return this.getNukeDotsCache.get();
  }
}
