package org.openbw.bwapi4j;

import java.util.ArrayList;
import java.util.List;

import org.openbw.bwapi4j.type.UnitType;

public final class BWMap {

    private String mapHash;
    private String mapFileName;
    // Walk resolution
    private int[][] walkabilityInfo;
    // Tile resolution
    private int[][] groundInfo;
    private int width;
    private int height;
    private ArrayList<TilePosition> startLocations;

    /* default */ BWMap() {
        this.startLocations = new ArrayList<TilePosition>();
    }

    public String mapHash() {
        return this.mapHash;
    }

    public String mapFileName() {
        return this.mapFileName;
    }

    public int getGroundHeight(TilePosition position) {
        return this.groundInfo[position.getX()][position.getY()];
    }

    public int getGroundHeight(int tileX, int tileY) {
        return this.groundInfo[tileX][tileY];
    }

    public List<TilePosition> getStartLocations() {
        return this.startLocations;
    }

    public boolean isWalkable(int walkX, int walkY) {
        return this.walkabilityInfo[walkX][walkY] == 1;
    }

    public int mapWidth() {
        return this.width;
    }

    public int mapHeight() {
        return this.height;
    }

    public native boolean isVisible(int tileX, int tileY);

    public boolean isVisible(TilePosition position) {
        return isVisible(position.getX(), position.getY());
    }

    private native boolean hasPath(int x1, int y1, int x2, int y2);

    public boolean hasPath(Position source, Position destination) {
        return hasPath(source.getX(), source.getY(), destination.getX(), destination.getY());
    }

    private native boolean canBuildHere(int x, int y, int typeId);

    public boolean canBuildHere(TilePosition position, UnitType type) {
        return canBuildHere(position.getX(), position.getY(), type.getId());
    }

    // TODO where to move this?
    public boolean canBuildHere(TilePosition position, UnitType type, boolean accountForUnits) {
        return false;
        // if (!accountForUnits) {
        // return canBuildHere(position, type);
        // }
        // if (game.canBuildHere(position, type)) {
        // for (Unit unit : game.getAllUnits()) {
        //
        // if (unit.getTilePosition().getX() + unit.tileWidth() >
        // position.getX() && unit.getTilePosition().getX() < position.getX() +
        // type.tileWidth()
        // && unit.getTilePosition().getY() + unit.tileHeight() >
        // position.getY() && unit.getTilePosition().getY() < position.getY() +
        // type.tileHeight()) {
        //
        //
        // return false;
        // }
        // }
        // return true;
        // }
        // return false;
    }
}
