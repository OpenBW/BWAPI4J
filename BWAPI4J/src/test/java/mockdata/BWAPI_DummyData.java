package mockdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.TilePosition;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class BWAPI_DummyData {

    private static final Logger logger = LogManager.getLogger();

    protected String mapFilename = null;
    protected String mapHash = null;
    protected TilePosition mapSize = null;
    protected TilePosition[] startingLocations = null;
    protected int[] walkabilityInfo = null;
    protected int[] groundInfo = null;
    protected int[] buildableInfo = null;

    protected BWAPI_DummyData() {
        /* Do nothing. */
    }

    protected void populateArrays(final String mapName, final int mapTileWidth, final int mapTileHeight) throws IOException, URISyntaxException {
        this.buildableInfo = new int[mapTileWidth * mapTileHeight];

        final String filenameSuffix = "_" + mapName + "_ORIGINAL";
        this.walkabilityInfo = DummyDataUtils.populateIntegerArray("walkabilityInfo" + filenameSuffix, " ");
        this.groundInfo = DummyDataUtils.populateIntegerArray("groundInfo" + filenameSuffix, " ");
        this.buildableInfo = DummyDataUtils.populateIntegerArray("buildableInfo" + filenameSuffix, " ");
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

    public int[] getWalkabilityInfo() {
        return this.walkabilityInfo;
    }

    public int[] getGroundInfo() {
        return this.groundInfo;
    }

    public int[] getBuildableInfo() {
        return this.buildableInfo;
    }

}
