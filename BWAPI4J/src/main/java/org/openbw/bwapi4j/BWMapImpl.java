package org.openbw.bwapi4j;

import java.util.ArrayList;
import java.util.List;

import org.openbw.bwapi4j.type.UnitType;

class BWMapImpl implements BWMap {

	private String mapHash;
    private String mapFileName;
    
    // Walk resolution
    private int[][] walkabilityInfo;
    
    // Tile resolution
    private int[][] groundInfo;
    private int width;
    private int height;
    private ArrayList<TilePosition> startLocations;

    BWMapImpl() {
    	
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

    public List<TilePosition> getStartPositions() {
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

    public native boolean isBuildable(int tileX, int tileY, boolean considerBuildings);

    public boolean isBuildable(TilePosition position, boolean considerBuildings) {
    	return isBuildable(position.getX(), position.getY(), considerBuildings);
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
}
