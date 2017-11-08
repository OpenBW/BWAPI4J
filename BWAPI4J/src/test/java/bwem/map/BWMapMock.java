package bwem.map;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.SCV;
import org.openbw.bwapi4j.unit.Unit;

public class BWMapMock implements BWMap {

    private TilePosition[] startLocations_FightingSpirit_ORIGINAL = {
        new TilePosition(117, 7), new TilePosition(7, 6), new TilePosition(7, 116), new TilePosition(117, 117)
    };

    private OriginalData originalData;

	private int[][] walkabilityInfo;
    private int[][] groundInfo;
    private int width;
    private int height;
    private ArrayList<TilePosition> startLocations;

	public BWMapMock() {

		initializeMock();
	}

	private void initializeMock() {
        try {
            this.originalData = new OriginalData();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

		this.width = 128;
		this.height = 128;

        int walkWidth = this.width * 4;
        int walkHeight = this.height * 4;

		this.walkabilityInfo = new int[walkWidth][walkHeight];
		for (int x = 0; x < walkWidth; ++x) {

			for (int y = 0; y < walkHeight; ++y) {

                this.walkabilityInfo[x][y] = this.originalData.walkabilityInfo_ORIGINAL[walkHeight * y + x];
			}
		}

		this.groundInfo = new int[this.width][this.height];
		for (int x = 0; x < this.width; ++x) {

			for (int y = 0; y < this.height; ++y) {

                this.groundInfo[x][y] = this.originalData.groundInfo_ORIGINAL[this.height * y + x];
			}
		}

		this.startLocations = new ArrayList<>();
        for (TilePosition loc : this.startLocations_FightingSpirit_ORIGINAL) {
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
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public String mapFileName() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean isBuildable(int tileX, int tileY, boolean considerBuildings) {
        return true;
    }

    @Override
    public boolean isBuildable(TilePosition position, boolean considerBuildings) {
        return true;
    }

    @Override
    public boolean isVisible(int tileX, int tileY) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean isVisible(TilePosition position) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean hasPath(Position source, Position destination) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean canBuildHere(TilePosition position, UnitType type) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean canBuildHere(TilePosition position, UnitType type, SCV builder, Collection<Unit> units) {
        throw new UnsupportedOperationException("TODO");
    }
}
