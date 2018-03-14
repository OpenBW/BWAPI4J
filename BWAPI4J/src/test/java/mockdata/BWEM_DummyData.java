package mockdata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class BWEM_DummyData {

    private static final Logger logger = LogManager.getLogger();

    public enum DataSetBwemVersion {

        BWEM_141("BWEM-1.4.1");

        private final String str;

        private DataSetBwemVersion(final String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return this.str;
        }

    }

    private final String mapHash;
    private final String dataSetBwapiVersion;
    private final String dataSetBwemVersion;

    private final int[] miniTileAltitudes;

    public BWEM_DummyData(final String mapHash, final String dataSetBwapiVersion, final String dataSetBwemVersion) throws IOException {
        this.mapHash = mapHash;
        this.dataSetBwapiVersion = dataSetBwapiVersion;
        this.dataSetBwemVersion = dataSetBwemVersion;

        this.miniTileAltitudes = DummyDataUtils.readIntegerArrayFromArchiveFile(
                DummyDataUtils.compileBwemDataSetArchiveFilename("MiniTileAltitudes", BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString(), DataSetBwemVersion.BWEM_141.toString()),
                mapHash,
                " "
        );
    }

    public int[] getMiniTileAltitudes() {
        return this.miniTileAltitudes;
    }

}
