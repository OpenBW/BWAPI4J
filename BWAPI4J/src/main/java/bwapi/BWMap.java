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
  public String mapHash();

  public String mapFileName();

  public String mapName();

  public String mapPathName();

  public int getGroundHeight(TilePosition position);

  public int getGroundHeight(int tileX, int tileY);

  public List<TilePosition> getStartLocations();

  /**
   * Return true, if the given "walk position" is walkable. Coordinates must be within the map grid,
   * otherwise an {@link IndexOutOfBoundsException} will be thrown.
   */
  boolean isWalkable(int walkX, int walkY);

  boolean isWalkable(WalkPosition walkPosition);

  boolean isValidPosition(TilePosition tilePosition);

  boolean isValidPosition(WalkPosition walkPosition);

  boolean isValidPosition(Position position);

  public int mapWidth();

  public int mapHeight();

  public boolean isBuildable(int tileX, int tileY, boolean considerBuildings);

  public boolean isBuildable(TilePosition position, boolean considerBuildings);

  public boolean isBuildable(int tileX, int tileY);

  public boolean isBuildable(TilePosition position);

  public boolean isExplored(int tileX, int tileY);

  public boolean isExplored(TilePosition position);

  public boolean isVisible(int tileX, int tileY);

  public boolean isVisible(TilePosition position);

  public boolean hasPath(Position source, Position destination);

  public boolean hasCreep(int tileX, int tileY);

  public boolean hasCreep(TilePosition tilePosition);

  public boolean hasPower(int tileX, int tileY);

  public boolean hasPower(TilePosition tilePosition);

  public boolean canBuildHere(TilePosition tilePosition, UnitType unitType);

  public boolean canBuildHere(TilePosition tilePosition, UnitType unitType, Unit builder);

  public boolean canBuildHere(
      TilePosition tilePosition, UnitType unitType, Unit builder, boolean checkExplored);
}
