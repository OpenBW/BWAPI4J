package mockdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.WalkPosition;

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
  private final List<WalkPosition> chokePointCenters;

  public BWEM_DummyData(
      final String mapHash, final String dataSetBwapiVersion, final String dataSetBwemVersion)
      throws IOException {
    this.mapHash = mapHash;
    this.dataSetBwapiVersion = dataSetBwapiVersion;
    this.dataSetBwemVersion = dataSetBwemVersion;

    this.miniTileAltitudes =
        DummyDataUtils.readIntegerArrayFromArchiveFile(
            DummyDataUtils.compileBwemDataSetArchiveFilename(
                "MiniTileAltitudes",
                BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString(),
                DataSetBwemVersion.BWEM_141.toString()),
            mapHash,
            " ");

    this.chokePointCenters = new ArrayList<>();
    final int[] chokepointCentersVals_ORIGINAL =
        DummyDataUtils.readIntegerArrayFromArchiveFile(
            DummyDataUtils.compileBwemDataSetArchiveFilename(
                "ChokePoints",
                BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString(),
                BWEM_DummyData.DataSetBwemVersion.BWEM_141.toString()),
            mapHash,
            " ");
    final int valuesPerGroup = 6;
    for (int i = 0; i < chokepointCentersVals_ORIGINAL.length; i += valuesPerGroup) {
      final int x = chokepointCentersVals_ORIGINAL[i];
      final int y = chokepointCentersVals_ORIGINAL[i + 1];
      final WalkPosition chokepoint = new WalkPosition(x, y);
      this.chokePointCenters.add(chokepoint);
    }
  }

  public int[] getMiniTileAltitudes() {
    return this.miniTileAltitudes;
  }

  public List<WalkPosition> getChokePointCenters() {
    return this.chokePointCenters;
  }
}
