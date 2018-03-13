package mockdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.TilePosition;

import java.io.IOException;
import java.util.List;

public class BWAPI_DummyData {

    private static final Logger logger = LogManager.getLogger();

    public enum DataSetBwapiVersion {

        BWAPI_420("BWAPI-4.2.0");

        private final String str;

        private DataSetBwapiVersion(final String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return this.str;
        }

    }

    private enum DataSetFilename {
        getGroundHeight,
        getStartLocations,
        isBuildable,
        isWalkable,
        MapInfo,
        Neutrals;
    }

    private final String mapHash;
    private final DataSetBwapiVersion dataSetBwapiVersion;
    protected String mapFilename = null;
    protected TilePosition mapSize = null;
    protected TilePosition[] startingLocations = null;
    protected int[] groundHeightData = null;
    protected int[] isWalkableData = null;
    protected int[] isBuildableData = null;

    public BWAPI_DummyData(final String mapHash, final DataSetBwapiVersion dataSetBwapiVersion) throws IOException {
        this.mapHash = mapHash;
        this.dataSetBwapiVersion = dataSetBwapiVersion;

        populateArrays();
    }

    private void populateArrays() throws IOException {
        final String filenamePrefix = "DummyBwapiData_";
        final String filenameSuffix = "_" + this.dataSetBwapiVersion.toString();

        System.out.println(filenamePrefix + DataSetFilename.getGroundHeight.toString() + filenameSuffix);
        this.groundHeightData = DummyDataUtils.readIntegerArrayFromArchiveFile(filenamePrefix + DataSetFilename.getGroundHeight.toString() + filenameSuffix, this.mapHash, " ");

        final List<List<Integer>> startLocations = DummyDataUtils.readMultiLineIntegerArraysFromArchiveFile(filenamePrefix + DataSetFilename.getStartLocations.toString() + filenameSuffix, this.mapHash, " ");
        this.startingLocations = new TilePosition[startLocations.size()];
        int startLocationIndex = 0;
        for (final List<Integer> startLocation : startLocations) {
            final int x = startLocation.get(0);
            final int y = startLocation.get(1);
            this.startingLocations[startLocationIndex++] = new TilePosition(x, y);
        }

        final List<List<String>> mapInfo = DummyDataUtils.reacMultiLinesAsStringsFromArchiveFile(filenamePrefix + DataSetFilename.MapInfo.toString() + filenameSuffix, this.mapHash, " ");
        final int tileWidth = Integer.parseInt(mapInfo.get(3).get(0));
        final int tileHeight = Integer.parseInt(mapInfo.get(3).get(1));
        this.mapSize = new TilePosition(tileWidth, tileHeight);

        this.isBuildableData = DummyDataUtils.readIntegerArrayFromArchiveFile(filenamePrefix + DataSetFilename.isBuildable.toString() + filenameSuffix, this.mapHash, " ");

        this.isWalkableData = DummyDataUtils.readIntegerArrayFromArchiveFile(filenamePrefix + DataSetFilename.isWalkable.toString() + filenameSuffix, this.mapHash, " ");
    }

    public String getMapFilename() {
        return this.mapFilename;
    }

    public String getMapHash() {
        return this.mapHash;
    }

    public TilePosition getMapSize() {
        return this.mapSize;
    }

    public TilePosition[] getStartingLocations() {
        return this.startingLocations;
    }

    public int[] getIsWalkableData() {
        return this.isWalkableData;
    }

    public int[] getGroundHeightData() {
        return this.groundHeightData;
    }

    public int[] getIsBuildableData() {
        return this.isBuildableData;
    }

}
