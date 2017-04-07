package org.openbw.bwapi4j;

import java.util.List;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

import bwta.Region;

/**
 * Contains all map-related bwapi functionality.
 */
public final class BWMap {

	/* default */ BWMap() {
	
	}
	
	public List<Region> getAllRegions() {
		return null;
	}	
	
	public Region getRegion(int pos) {
		return null;
	}	
	
	public Region getRegionAt(Position position) {
		return null;
	}
	
	public Region getRegionAt(int x, int y) {
		return null;
	}
	
	public String mapHash() {
		return null;
	}
	
	public String mapFileName() {
		return null;
	}
	
	public int getGroundHeight(TilePosition position) {
		return 0;
	}

	public int getGroundHeight(int tileX, int tileY) {
		return 0;
	}
	
	public List<TilePosition> getStartLocations() {
		return null;
	}
	
	public TilePosition getMyStartLocation() {
		return null;
	}

	public boolean isVisible(int tileX, int tileY) {
		return false;
	}

	public boolean isWalkable(int walkX, int walkY) {
		return false;
	}

	public boolean hasPath(Position source, Position destination) {
		return false;
	}
	
	public int mapWidth() {
		return 0;
	}

	public int mapHeight() {
		return 0;
	}
	
	public boolean isVisible(TilePosition position) {
		return false;
	}
	
	/**
	 * {@link bwapi.Game#canBuildHere(TilePosition, UnitType)}
	 */
	public boolean canBuildHere(TilePosition position, UnitType type) {
		return false;
	}
	
	public boolean canBuildHere(TilePosition position, UnitType type, boolean accountForUnits) {
		return false;
//		if (!accountForUnits) {
//			return canBuildHere(position, type);
//		}
//		if (game.canBuildHere(position, type)) {
//			for (Unit unit : game.getAllUnits()) {
//					
//				if (unit.getTilePosition().getX() + unit.tileWidth() > position.getX() &&  unit.getTilePosition().getX() < position.getX() + type.tileWidth()
//						&& unit.getTilePosition().getY() + unit.tileHeight() > position.getY() && unit.getTilePosition().getY() < position.getY() + type.tileHeight()) {
//					
//					
//					return false;
//				}
//			}
//			return true;
//		}
//		return false;
	}
}
