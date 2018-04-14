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

import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.PlayerUnit;

import java.util.ArrayList;
import java.util.List;

class BWMapImpl implements BWMap {

    private String mapHash;
    private String mapFileName;
    private String mapName;

    // Walk resolution
    private int[][] walkabilityInfo;

    // Tile resolution
    private int[][] groundInfo;
    int width;
    int height;
    private ArrayList<TilePosition> startLocations;

    BWMapImpl() {

        this.startLocations = new ArrayList<>();
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
        return this.groundInfo[position.getX()][position.getY()];
    }

    public int getGroundHeight(int tileX, int tileY) {
        return this.groundInfo[tileX][tileY];
    }

    public List<TilePosition> getStartPositions() {
        return this.startLocations;
    }

    public boolean isWalkable(int walkX, int walkY) {
        return this.walkabilityInfo[walkX][walkY] == 1;
    }

    @Override
    public boolean isWalkable(WalkPosition walkPosition) {
        return isWalkable(walkPosition.getX(), walkPosition.getY());
    }

    //TODO: Add the other two position class cases. I.e. "WalkPosition" and "Position" using
    //precomputed values with no arithmetic in the functions themselves. E.g. "walkWidth", "pixelWidth" etc.
    @Override
    public boolean isValidPosition(TilePosition tilePosition) {
        return tilePosition.getX() >= 0 && tilePosition.getX() < mapWidth() && tilePosition.getY() >= 0 && tilePosition.getY() < mapHeight();
    }

    //TODO: Remove arithmetic.
    @Override
    public boolean isValidPosition(WalkPosition walkPosition) {
        return walkPosition.getX() >= 0 && walkPosition.getX() < mapWidth() * 4 && walkPosition.getY() >= 0 && walkPosition.getY() < mapHeight() * 4;
    }

    //TODO: Remove arithmetic.
    @Override
    public boolean isValidPosition(Position position) {
        return position.getX() >= 0 && position.getX() < mapWidth() * 32 && position.getY() >= 0 && position.getY() < mapHeight() * 32;
    }

    public int mapWidth() {
        return this.width;
    }

    public int mapHeight() {
        return this.height;
    }

    private native int _isBuildable(int tileX, int tileY, boolean considerBuildings);

    public boolean isBuildable(int tileX, int tileY, boolean considerBuildings) {

        return _isBuildable(tileX, tileY, considerBuildings) == 1;
    }

    public boolean isBuildable(TilePosition position, boolean considerBuildings) {

        return _isBuildable(position.getX(), position.getY(), considerBuildings) == 1;
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
}
