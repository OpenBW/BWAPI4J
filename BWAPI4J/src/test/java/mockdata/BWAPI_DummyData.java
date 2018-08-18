package mockdata;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.test.BWDataProvider;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.MineralPatchMock;
import org.openbw.bwapi4j.unit.VespeneGeyser;
import org.openbw.bwapi4j.unit.VespeneGeyserMock;

/** Container class for BWAPI dummy data to feed into test and/or mocks. */
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

  public enum MapHash {
    Benzene("af618ea3ed8a8926ca7b17619eebcb9126f0d8b1"),
    Destination("4e24f217d2fe4dbfa6799bc57f74d8dc939d425b"),
    Heartbreak_Ridge("6f8da3c3cc8d08d9cf882700efa049280aedca8c"),
    Neo_Moon_Glaive("c8386b87051f6773f6b2681b0e8318244aa086a6"),
    Tau_Cross("9bfc271360fa5bab3707a29e1326b84d0ff58911"),
    Andromeda("1e983eb6bcfa02ef7d75bd572cb59ad3aab49285"),
    Circuit_Breaker("450a792de0e544b51af5de578061cb8a2f020f32"),
    Electric_Circuit("9505d618c63a0959f0c0bfe21c253a2ea6e58d26"),
    Empire_of_the_Sun("a220d93efdf05a439b83546a579953c63c863ca7"),
    Fighting_Spirit("d2f5633cc4bb0fca13cd1250729d5530c82c7451"),
    Icarus("0409ca0d7fe0c7f4083a70996a8f28f664d2fe37"),
    Jade("df21ac8f19f805e1e0d4e9aa9484969528195d9f"),
    La_Mancha_1_1("e47775e171fe3f67cc2946825f00a6993b5a415e"),
    Python("de2ada75fbc741cfa261ee467bf6416b10f9e301"),
    Roadrunner("9a4498a896b28d115129624f1c05322f48188fe0");

    private final String mapHash;

    private MapHash(final String mapHash) {
      this.mapHash = mapHash;
    }

    public String getMapName() {
      return this.toString();
    }

    public String getMapHash() {
      return this.mapHash;
    }
  }

  private final String mapHash;
  private final BWAPI_MapInfo mapInfo;
  private final TilePosition[] startingLocations;
  private final int[] groundHeightData;
  private final int[] isWalkableData;
  private final int[] isBuildableData;
  private final List<MineralPatch> mineralPatches;
  private final List<VespeneGeyser> vespeneGeysers;

  public BWAPI_DummyData(final String mapHash, final String dataSetBwapiVersion) throws Exception {
    BWDataProvider.injectValues();

    this.mapHash = mapHash;

    this.mapInfo = new BWAPI_MapInfo(mapHash, dataSetBwapiVersion);

    this.groundHeightData =
        DummyDataUtils.readIntegerArrayFromArchiveFile(
            DummyDataUtils.compileBwapiDataSetArchiveFilename(
                "getGroundHeight", dataSetBwapiVersion),
            this.mapHash,
            " ");

    final List<List<Integer>> startLocations =
        DummyDataUtils.readMultiLineIntegerArraysFromArchiveFile(
            DummyDataUtils.compileBwapiDataSetArchiveFilename(
                "getStartLocations", dataSetBwapiVersion),
            this.mapHash,
            " ");
    this.startingLocations = new TilePosition[startLocations.size()];
    int startLocationIndex = 0;
    for (final List<Integer> startLocation : startLocations) {
      final int x = startLocation.get(0);
      final int y = startLocation.get(1);
      this.startingLocations[startLocationIndex++] = new TilePosition(x, y);
    }

    this.isBuildableData =
        DummyDataUtils.readIntegerArrayFromArchiveFile(
            DummyDataUtils.compileBwapiDataSetArchiveFilename("isBuildable", dataSetBwapiVersion),
            this.mapHash,
            " ");

    this.isWalkableData =
        DummyDataUtils.readIntegerArrayFromArchiveFile(
            DummyDataUtils.compileBwapiDataSetArchiveFilename("isWalkable", dataSetBwapiVersion),
            this.mapHash,
            " ");

    final int[] neutralsData =
        DummyDataUtils.readIntegerArrayFromArchiveFile(
            DummyDataUtils.compileBwapiDataSetArchiveFilename("Neutrals", dataSetBwapiVersion),
            this.mapHash,
            " ");
    this.mineralPatches = new ArrayList<>();
    this.vespeneGeysers = new ArrayList<>();
    final int valuesPerGroup = 6;
    for (int i = 0; i < neutralsData.length; i += valuesPerGroup) {
      int offset = i;

      final int unitTypeId = neutralsData[offset++];

      final int initialResources = neutralsData[offset++];

      final int x = neutralsData[offset++];
      final int y = neutralsData[offset++];
      final TilePosition tilePosition = new TilePosition(x, y);

      if (unitTypeId == UnitType.Resource_Mineral_Field.getId()) {
        this.mineralPatches.add(new MineralPatchMock(i, initialResources, tilePosition));
      } else if (unitTypeId == UnitType.Resource_Vespene_Geyser.getId()) {
        this.vespeneGeysers.add(new VespeneGeyserMock(i, initialResources, tilePosition));
      } else {
        // TODO
        //                /* Treat neutral as a special building. */
        //                this.staticBuildings.add(new SpecialBuildingMock(i,
        // UnitType.valueOf(unitTypeId), tilePosition));
      }
    }
    logger.debug("Added MineralPatches: count=" + this.mineralPatches.size());
    logger.debug("Added VespeneGeysers: count=" + this.vespeneGeysers.size());
  }

  public String getMapDisplayName() {
    return this.mapInfo.getMapDisplayName();
  }

  public String getMapFilename() {
    return this.mapInfo.getMapFilename();
  }

  public String getMapHash() {
    return this.mapHash;
  }

  public int getMapTileWidth() {
    return this.mapInfo.getMapTileWidth();
  }

  public int getMapTileHeight() {
    return this.mapInfo.getMapTileHeight();
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

  public List<MineralPatch> getMineralPatches() {
    return this.mineralPatches;
  }

  public List<VespeneGeyser> getVespeneGeysers() {
    return this.vespeneGeysers;
  }
}
