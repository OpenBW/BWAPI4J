package bwapi;

import java.util.Arrays;
import java.util.List;
import mockdata.BWAPI_DummyData;

public class BWMapMock implements BWMap {
  private final BWAPI_DummyData data;

  public BWMapMock(final String mapHash, final String dataSetBwapiVersion) throws Exception {
    this.data = new BWAPI_DummyData(mapHash, dataSetBwapiVersion);
  }

  public BWAPI_DummyData getDummyData() {
    return this.data;
  }

  @Override
  public int getGroundHeight(final TilePosition position) {
    return getGroundHeight(position.getX(), position.getY());
  }

  @Override
  public int getGroundHeight(final int tileX, final int tileY) {
    return this.data.getGroundHeightData()[mapWidth() * tileY + tileX];
  }

  @Override
  public List<TilePosition> getStartLocations() {
    return Arrays.asList(this.data.getStartingLocations());
  }

  @Override
  public boolean isWalkable(final int walkX, final int walkY) {
    return this.data.getIsWalkableData()[mapWidth() * 4 * walkY + walkX] == 1;
  }

  @Override
  public boolean isWalkable(final WalkPosition walkPosition) {
    return isWalkable(walkPosition.getX(), walkPosition.getY());
  }

  @Override
  public boolean isValidPosition(final TilePosition tilePosition) {
    return tilePosition.getX() >= 0
        && tilePosition.getX() < mapWidth()
        && tilePosition.getY() >= 0
        && tilePosition.getY() < mapHeight();
  }

  @Override
  public boolean isValidPosition(final WalkPosition walkPosition) {
    return walkPosition.getX() >= 0
        && walkPosition.getX() < mapWidth() * 4
        && walkPosition.getY() >= 0
        && walkPosition.getY() < mapHeight() * 4;
  }

  @Override
  public boolean isValidPosition(final Position position) {
    return position.getX() >= 0
        && position.getX() < mapWidth() * 32
        && position.getY() >= 0
        && position.getY() < mapHeight() * 32;
  }

  @Override
  public int mapWidth() {
    return this.data.getMapTileWidth();
  }

  @Override
  public int mapHeight() {
    return this.data.getMapTileHeight();
  }

  @Override
  public String mapHash() {
    return this.data.getMapHash();
  }

  @Override
  public String mapFileName() {
    return this.data.getMapFilename();
  }

  public String mapName() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isBuildable(final int tileX, final int tileY, final boolean considerBuildings) {
    return this.data.getIsBuildableData()[mapWidth() * tileY + tileX] == 1;
  }

  @Override
  public boolean isBuildable(final TilePosition tilePosition, final boolean considerBuildings) {
    return isBuildable(tilePosition.getX(), tilePosition.getY(), considerBuildings);
  }

  @Override
  public boolean isBuildable(int tileX, int tileY) {
    return isBuildable(tileX, tileY, false);
  }

  @Override
  public boolean isBuildable(TilePosition position) {
    return isBuildable(position.getX(), position.getY(), false);
  }

  @Override
  public boolean isExplored(final int tileX, final int tileY) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isExplored(final TilePosition position) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isVisible(final int tileX, final int tileY) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isVisible(final TilePosition position) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasPath(final Position source, final Position destination) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasCreep(int tileX, int tileY) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasCreep(TilePosition tilePosition) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasPower(int tileX, int tileY) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean hasPower(TilePosition tilePosition) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean canBuildHere(TilePosition tilePosition, UnitType type) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean canBuildHere(TilePosition tilePosition, UnitType type, Unit builder) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean canBuildHere(
      TilePosition tilePosition, UnitType type, Unit builder, boolean checkExplored) {
    throw new UnsupportedOperationException();
  }
}
