package bwem.map;

import java.util.ArrayList;
import java.util.List;

import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.TilePosition;

public class BWMapMock extends BWMap {

	private int[][] walkabilityInfo;
    private int[][] groundInfo;
    private int width;
    private int height;
    private ArrayList<TilePosition> startLocations;
    
	public BWMapMock() {
		
		initializeMock();
	}
	
	private void initializeMock() {
		
		this.width = 128;
		this.height = 128;
		this.walkabilityInfo = new int[this.width * 4][this.height * 4];
		for (int i = 0; i < width; i++) {
			
			for (int j = 0; j < height; j++) {
				
				// TODO fill in mock walkability data here
			}
		}
		
		this.groundInfo = new int[this.width][this.height];
		for (int i = 0; i < width; i++) {
			
			for (int j = 0; j < height; j++) {
				
				// TODO fill in mock ground info data here
			}
		}
		
		this.startLocations = new ArrayList<>();
		// TODO replace the "10, 10" coordinates with real start location coordinates for the mock
		this.startLocations.add(new TilePosition(10, 10));
		this.startLocations.add(new TilePosition(10, 10));
		this.startLocations.add(new TilePosition(10, 10));
		this.startLocations.add(new TilePosition(10, 10));
	}

	@Override
	public int getGroundHeight(TilePosition position) {
		
        return this.groundInfo[position.getX()][position.getY()];
    }

	@Override
    public int getGroundHeight(int tileX, int tileY) {
		
        return this.groundInfo[tileX][tileY];
    }

	@Override
    public List<TilePosition> getStartPositions() {
		
        return this.startLocations;
    }

	@Override
    public boolean isWalkable(int walkX, int walkY) {
		
        return this.walkabilityInfo[walkX][walkY] == 1;
    }

	@Override
    public int mapWidth() {
		
        return this.width;
    }

	@Override
    public int mapHeight() {
		
        return this.height;
    }
}
