package org.openbw.bwapi4j;

import java.util.Map;

/**
 * Represents a choke point in a StarCraft map.
 * 
 * For a description of fields see: http://code.google.com/p/bwta/wiki/Chokepoint
 */
public class ChokePoint {
	
	public static final int numAttributes = 9;
	public static final double fixedScale = 100.0;
	
	private final Position center;
	private final double radius;
	private final int firstRegionID;
	private final int secondRegionID;
	private final Position firstSide;
	private final Position secondSide;
	private final Region firstRegion;
	private final Region secondRegion;
	
	public ChokePoint(int[] data, int index, Map<Integer, Region> idToRegion) {
		int centerX = data[index++];
		int centerY = data[index++];
		center = new Position(centerX, centerY);
		radius = data[index++] / fixedScale;
		firstRegionID = data[index++];
		secondRegionID = data[index++];
		int firstSideX = data[index++];
		int firstSideY = data[index++];
		firstSide = new Position(firstSideX, firstSideY);
		int secondSideX = data[index++];
		int secondSideY = data[index++];
		secondSide = new Position(secondSideX, secondSideY);
		firstRegion = idToRegion.get(firstRegionID);
		secondRegion = idToRegion.get(secondRegionID);
	}
	
	public Region getOtherRegion(Region region) {
		return region.equals(firstRegion) ? secondRegion : firstRegion;
	}
	
	public Region getFirstRegion() {
		return firstRegion;
	}
	
	public Region getSecondRegion() {
		return secondRegion;
	}
	
	public Position getCenter() {
		return center;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Position getFirstSide() {
		return firstSide;
	}
	
	public Position getSecondSide() {
		return secondSide;
	}
}
