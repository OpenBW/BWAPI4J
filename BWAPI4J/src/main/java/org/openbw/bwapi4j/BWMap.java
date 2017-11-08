package org.openbw.bwapi4j;

import java.util.Collection;
import java.util.List;

import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.SCV;
import org.openbw.bwapi4j.unit.Unit;

public interface BWMap {

    public String mapHash();

    public String mapFileName();

    public int getGroundHeight(TilePosition position);

    public int getGroundHeight(int tileX, int tileY);

    public List<TilePosition> getStartPositions();

    public boolean isWalkable(int walkX, int walkY);

    public int mapWidth();

    public int mapHeight();

    public boolean isBuildable(int tileX, int tileY, boolean considerBuildings);

    public boolean isBuildable(TilePosition position, boolean considerBuildings);
    
    public boolean isVisible(int tileX, int tileY);
    
    public boolean isVisible(TilePosition position);

    public boolean hasPath(Position source, Position destination);

    public boolean canBuildHere(TilePosition position, UnitType type);

	public boolean canBuildHere(TilePosition position, UnitType type, SCV builder, Collection<Unit> units);
}
