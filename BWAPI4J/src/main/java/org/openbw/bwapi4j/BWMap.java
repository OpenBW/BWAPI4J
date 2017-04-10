package org.openbw.bwapi4j;

import java.util.List;

import org.openbw.bwapi4j.type.UnitType;

public final class BWMap {

    private static int GROUND_HEIGHT = 0;
    private static int WALKABILITY = 1;

    private String mapHash;
    private String mapFileName;
    private int[][][] mapInfo;
    private int width;
    private int height;

    /* default */ BWMap() {

    }

    public String mapHash() {
        return this.mapHash;
    }

    public String mapFileName() {
        return this.mapFileName;
    }

    public int getGroundHeight(TilePosition position) {
        return this.mapInfo[position.getX() * 4][position.getY() * 4][GROUND_HEIGHT];
    }

    public int getGroundHeight(int tileX, int tileY) {
        return this.mapInfo[tileX * 4][tileY * 4][GROUND_HEIGHT];
    }

    public List<TilePosition> getStartLocations() {
        return null;
    }

    public TilePosition getMyStartLocation() {
        return null;
    }

    public boolean isWalkable(int walkX, int walkY) {
        return this.mapInfo[walkX][walkY][WALKABILITY] == 1;
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
