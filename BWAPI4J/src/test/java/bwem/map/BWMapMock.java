package bwem.map;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public class BWMapMock implements BWMap {

	private int[][] buildable;
    private int[][] walkabilityInfo;
    private int[][] groundInfo;
    private int width;
    private int height;
    private ArrayList<TilePosition> startLocations;
    
    private OriginalBwapiData data;
    
	public BWMapMock() {

		try {
			this.data = new OriginalBwapiData();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Could not load dummy map data.");
		}
		initializeMock();
	}

	private void initializeMock() {
		this.width = 128;
		this.height = 128;

        int walkWidth = this.width * 4;
        int walkHeight = this.height * 4;

		this.walkabilityInfo = new int[walkWidth][walkHeight];
		for (int y = 0; y < walkHeight; ++y) {
			for (int x = 0; x < walkWidth; ++x) {
                this.walkabilityInfo[x][y] = this.data.walkabilityInfo_ORIGINAL[walkWidth * y + x];
			}
		}

		this.groundInfo = new int[this.width][this.height];
		for (int y = 0; y < this.height; ++y) {
			for (int x = 0; x < this.width; ++x) {
                this.groundInfo[x][y] = this.data.groundInfo_ORIGINAL[this.width * y + x];
			}
		}

		this.buildable = new int[this.width][this.height];
		for (int y = 0; y < this.height; ++y) {
			for (int x = 0; x < this.width; ++x) {
				this.buildable[x][y] = data.buildableInfo_ORIGINAL[this.width * y + x];
			}
		}

		this.startLocations = new ArrayList<>();
        for (TilePosition loc : this.data.startLocations_FightingSpirit_ORIGINAL) {
            this.startLocations.add(loc);
        }
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

	@Override
	public String mapHash() {
		
		return "mockHash";
	}

	@Override
	public String mapFileName() {
		
		return "FightingSpiritMock";
	}

	@Override
	public boolean isBuildable(int tileX, int tileY, boolean considerBuildings) {
		
		return this.buildable[tileX][tileY] == 1;
	}

	@Override
	public boolean isBuildable(TilePosition position, boolean considerBuildings) {
		
		return isBuildable(position.getX(), position.getY(), considerBuildings);
	}

	@Override
	public boolean isVisible(int tileX, int tileY) {

		return true;
	}

	@Override
	public boolean isVisible(TilePosition position) {

		return true;
	}

	@Override
	public boolean hasPath(Position source, Position destination) {

		return true;
	}

	@Override
	public boolean canBuildHere(TilePosition position, UnitType type) {

		return true;
	}
}
