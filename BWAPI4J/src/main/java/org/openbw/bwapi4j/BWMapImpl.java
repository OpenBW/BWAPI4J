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
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.util.Cache;

class BWMapImpl implements BWMap {
  private String mapHash;
  private String mapFileName;
  private String mapName;

  private final InteractionHandler interactionHandler;

  // TilePosition resolution
  int tileWidth;
  int tileHeight;
  private int[][] groundHeightData;
  private int[][] isBuildableData;
  private Cache<boolean[][]> getCreepDataCache;
  private ArrayList<TilePosition> startLocations;

  // WalkPosition resolution
  private int[][] isWalkableData;
  int walkWidth;
  int walkHeight;

  // Position resolution
  int pixelWidth;
  int pixelHeight;

  BWMapImpl(final InteractionHandler interactionHandler) {
    this.interactionHandler = interactionHandler;
    this.startLocations = new ArrayList<>();
    this.getCreepDataCache = new Cache<>(this::getCreepData, this.interactionHandler);
  }

  public String mapHash() {
    return this.mapHash;
  }

  public String mapFileName() {
    return this.mapFileName;
  }

  public String mapName() {
    return this.mapName;
  }

  public int getGroundHeight(TilePosition position) {
    return this.groundHeightData[position.getX()][position.getY()];
  }

  public int getGroundHeight(int tileX, int tileY) {
    return this.groundHeightData[tileX][tileY];
  }

  public List<TilePosition> getStartPositions() {
    return this.startLocations;
  }

  public boolean isWalkable(int walkX, int walkY) {
    return this.isWalkableData[walkX][walkY] == 1;
  }

  @Override
  public boolean isWalkable(WalkPosition walkPosition) {
    return isWalkable(walkPosition.getX(), walkPosition.getY());
  }

  @Override
  public boolean isValidPosition(TilePosition tilePosition) {
    return tilePosition.getX() >= 0
        && tilePosition.getX() < mapWidth()
        && tilePosition.getY() >= 0
        && tilePosition.getY() < mapHeight();
  }

  @Override
  public boolean isValidPosition(WalkPosition walkPosition) {
    return walkPosition.getX() >= 0
        && walkPosition.getX() < mapWalkWidth()
        && walkPosition.getY() >= 0
        && walkPosition.getY() < mapWalkHeight();
  }

  @Override
  public boolean isValidPosition(Position position) {
    return position.getX() >= 0
        && position.getX() < mapPixelWidth()
        && position.getY() >= 0
        && position.getY() < mapPixelHeight();
  }

  @Override
  public int mapWidth() {
    return this.tileWidth;
  }

  @Override
  public int mapHeight() {
    return this.tileHeight;
  }

  private int mapWalkWidth() {
    return this.walkWidth;
  }

  private int mapWalkHeight() {
    return this.walkHeight;
  }

  private int mapPixelWidth() {
    return this.pixelWidth;
  }

  private int mapPixelHeight() {
    return this.pixelHeight;
  }

  private native int _isBuildable(int tileX, int tileY, boolean considerBuildings);

  public boolean isBuildable(final int tileX, final int tileY, final boolean considerBuildings) {
    return considerBuildings
        ? (_isBuildable(tileX, tileY, considerBuildings) == 1)
        : isBuildable(tileX, tileY);
  }

  public boolean isBuildable(final TilePosition tilePosition, final boolean considerBuildings) {
    return isBuildable(tilePosition.getX(), tilePosition.getY(), considerBuildings);
  }

  public boolean isBuildable(final int tileX, final int tileY) {
    return (tileX >= 0
        && tileX < mapWidth()
        && tileY >= 0
        && tileY < mapHeight()
        && this.isBuildableData[tileX][tileY] == 1);
  }

  public boolean isBuildable(final TilePosition tilePosition) {
    return isBuildable(tilePosition.getX(), tilePosition.getY());
  }

  private native int _isExplored(int tileX, int tileY);

  public boolean isExplored(int tileX, int tileY) {
    return _isExplored(tileX, tileY) == 1;
  }

  public boolean isExplored(TilePosition position) {
    return _isExplored(position.getX(), position.getY()) == 1;
  }

  private native int _isVisible(int tileX, int tileY);

  public boolean isVisible(int tileX, int tileY) {
    return _isVisible(tileX, tileY) == 1;
  }

  public boolean isVisible(TilePosition position) {
    return _isVisible(position.getX(), position.getY()) == 1;
  }

  private native int _hasPath(int x1, int y1, int x2, int y2);

  public boolean hasPath(Position source, Position destination) {
    return _hasPath(source.getX(), source.getY(), destination.getX(), destination.getY()) == 1;
  }

  private native int _canBuildHere(int x, int y, int typeId);

  private native int _canBuildHere(int x, int y, int typeId, int builderId);

  public boolean canBuildHere(TilePosition position, UnitType type) {
    return _canBuildHere(position.getX(), position.getY(), type.getId()) == 1;
  }

  public boolean canBuildHere(TilePosition position, UnitType type, PlayerUnit builder) {
    return _canBuildHere(position.getX(), position.getY(), type.getId(), builder.getId()) == 1;
  }

  private native int[] getCreepData_native();

  private boolean[][] getCreepData() {
    final int[] data = getCreepData_native();

    final int mapTileWidth = mapWidth();
    final int mapTileHeight = mapHeight();

    final boolean[][] creepData = new boolean[mapTileWidth][mapTileHeight];

    int index = 0;

    for (int tileX = 0; tileX < mapTileWidth; ++tileX) {
      for (int tileY = 0; tileY < mapTileHeight; ++tileY) {
        creepData[tileX][tileY] = data[index++] == 1;
      }
    }

    return creepData;
  }

  public boolean hasCreep(final int tileX, final int tileY) {
    return this.getCreepDataCache.get()[tileX][tileY];
  }

  @Override
  public boolean hasCreep(final TilePosition tilePosition) {
    return hasCreep(tilePosition.getX(), tilePosition.getY());
  }
}
