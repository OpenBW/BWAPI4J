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

public interface BWMap {
  String mapHash();

  String mapFileName();

  String mapName();

  String mapPathName();

  int getGroundHeight(TilePosition position);

  int getGroundHeight(int tileX, int tileY);

  List<TilePosition> getStartLocations();

  /**
   * Return true, if the given "walk position" is walkable. Coordinates must be within the map grid,
   * otherwise an {@link IndexOutOfBoundsException} will be thrown.
   */
  boolean isWalkable(int walkX, int walkY);

  boolean isWalkable(WalkPosition walkPosition);

  boolean isValidPosition(TilePosition tilePosition);

  boolean isValidPosition(WalkPosition walkPosition);

  boolean isValidPosition(Position position);

  int mapWidth();

  int mapHeight();

  boolean isBuildable(int tileX, int tileY, boolean considerBuildings);

  boolean isBuildable(TilePosition position, boolean considerBuildings);

  boolean isBuildable(int tileX, int tileY);

  boolean isBuildable(TilePosition position);

  boolean isExplored(int tileX, int tileY);

  boolean isExplored(TilePosition position);

  boolean isVisible(int tileX, int tileY);

  boolean isVisible(TilePosition position);

  boolean hasPath(Position source, Position destination);

  boolean hasCreep(int tileX, int tileY);

  boolean hasCreep(TilePosition tilePosition);

  boolean hasPower(int tileX, int tileY);

  boolean hasPower(TilePosition tilePosition);

  boolean canBuildHere(TilePosition tilePosition, UnitType unitType);

  boolean canBuildHere(TilePosition tilePosition, UnitType unitType, Unit builder);

  boolean canBuildHere(
      TilePosition tilePosition, UnitType unitType, Unit builder, boolean checkExplored);

  TilePosition getBuildLocation(
      UnitType unitType, TilePosition desiredTilePosition, int maxRange, boolean creep);

  TilePosition getBuildLocation(UnitType unitType, TilePosition desiredTilePosition, int maxRange);

  TilePosition getBuildLocation(UnitType unitType, TilePosition desiredTilePosition);
}
