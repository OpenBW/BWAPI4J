package bwem.map;

import bwem.BWEM;
import bwem.Graph;
import bwem.area.TempAreaInfo;
import bwem.example.MapPrinterExample;
import bwem.tile.MiniTile;
import bwem.typedef.Altitude;
import bwem.util.BwemExt;
import mockdata.BWAPI_FightingSpirit;
import mockdata.BWAPI_DummyData;
import mockdata.BWEM_DummyData;
import mockdata.BWEM_FightingSpirit;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;

import java.util.Arrays;
import java.util.List;

public class MapInitializerTest implements BWEventListener {

    private static final Logger logger = LogManager.getLogger();

    private BWAPI_DummyData bwapiData;
    private BWEM_DummyData bwemData;

    private BW bw;
    private Map map;

    @Before
    public void setUp() {
        if (this.bwapiData == null) {
            try {
                this.bwapiData = new BWAPI_FightingSpirit();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Failed to load dummy BWAPI data.");
            }
        }

        if (this.bwemData == null) {
            try {
                this.bwemData = new BWEM_FightingSpirit();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Failed to load dummy BWEM data");
            }
        }
    }

    @Test
    public void testInitializer() {
        this.bw = new BW(this);
        this.bw.startGame();
    }

    private void test_MapImpl_Initialize(final boolean useOriginalValues) {
        final BWMap bwMap = this.bw.getBWMap();
        final Graph graph = ((MapImpl) this.map).GetGraph();

        final MapInitializer mapInitializer = (MapInitializer) this.map;

        mapInitializer.compileAdvancedData(
                (useOriginalValues ? this.bwapiData.getMapSize().getX() : this.bw.getBWMap().mapWidth()),
                (useOriginalValues ? this.bwapiData.getMapSize().getY() : this.bw.getBWMap().mapHeight()),
                (useOriginalValues ? Arrays.asList(this.bwapiData.getStartingLocations()) : this.bw.getBWMap().getStartPositions())
        );
        final AdvancedData advancedData = this.map.getData();

        ((AdvancedDataInit) advancedData).markUnwalkableMiniTiles(bwMap);
        ((AdvancedDataInit) advancedData).markBuildableTilesAndGroundHeight(bwMap);

        ((AdvancedDataInit) advancedData).decideSeasOrLakes(BwemExt.lake_max_miniTiles, BwemExt.lake_max_width_in_miniTiles);

        mapInitializer.InitializeNeutrals(
                this.bw.getMineralPatches(), this.map.Minerals(),
                this.bw.getVespeneGeysers(), this.map.Geysers(),
                mapInitializer.filterNeutralPlayerUnits(this.bw.getAllUnits(), this.bw.getAllPlayers()), this.map.StaticBuildings()
        );

        //////////////////////////////////////////////////////////////////////
        // ComputeAltitude
        //////////////////////////////////////////////////////////////////////

//        mapInitializer.ComputeAltitude(advancedData);
        final int altitude_scale = 8; // 8 provides a pixel definition for altitude_t, since altitudes are computed from miniTiles which are 8x8 pixels

        //----------------------------------------------------------------------
        // DeltasByAscendingAltitude
        //----------------------------------------------------------------------
        final boolean useOriginalDeltasByAscendingAltitude = useOriginalValues && true;
        final List<MutablePair<WalkPosition, Altitude>> DeltasByAscendingAltitude = useOriginalDeltasByAscendingAltitude
                ? this.bwemData.deltasByAscendingAltitude
                : mapInitializer.getSortedDeltasByAscendingAltitude(
                advancedData.getMapData().getWalkSize().getX(),
                advancedData.getMapData().getWalkSize().getY(),
                altitude_scale);
        //----------------------------------------------------------------------

        final List<MutablePair<WalkPosition, Altitude>> ActiveSeaSides = mapInitializer.getActiveSeaSideList(advancedData.getMapData());

        mapInitializer.setMaxAltitude(mapInitializer.setAltitudesAndGetUpdatedMaxAltitude(this.map.MaxAltitude(), advancedData, DeltasByAscendingAltitude, ActiveSeaSides, altitude_scale));

        //////////////////////////////////////////////////////////////////////

        mapInitializer.ProcessBlockingNeutrals(mapInitializer.getCandidates(this.map.StaticBuildings(), this.map.Minerals()));

        //////////////////////////////////////////////////////////////////////
        // ComputeAreas
        //////////////////////////////////////////////////////////////////////

        final boolean useOriginalMiniTilesByDescendingAltitude = useOriginalValues && true;
        final List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude = useOriginalMiniTilesByDescendingAltitude
                ? this.bwemData.miniTilesByDescendingAltitude
                : mapInitializer.getSortedMiniTilesByDescendingAltitude();
        final boolean useOriginalTempAreaList = useOriginalValues && false;
        final List<TempAreaInfo> TempAreaList = useOriginalTempAreaList
                ? this.bwemData.tempAreaList
                : mapInitializer.ComputeTempAreas(MiniTilesByDescendingAltitude);
        mapInitializer.ComputeAreas(TempAreaList, BwemExt.area_min_miniTiles);

        //////////////////////////////////////////////////////////////////////

        graph.CreateChokePoints(this.map.StaticBuildings(), this.map.Minerals(), this.map.RawFrontier());

        graph.ComputeChokePointDistanceMatrix();

        graph.CollectInformation();

        graph.CreateBases();
    }

    private void runMapPrinterExample() {
        this.map.EnableAutomaticPathAnalysis();
        this.map.getMapPrinter().Initialize(this.bw, this.map);
        final MapPrinterExample example = new MapPrinterExample(this.map.getMapPrinter());
        example.printMap(this.map);
        example.pathExample(this.map);
    }

    @Override
    public void onStart() {
        this.map = new BWEM(this.bw).GetMap();

        test_MapImpl_Initialize(true);

        runMapPrinterExample();

        this.bw.exit();
        this.bw.getInteractionHandler().leaveGame();
    }

    @Override
    public void onEnd(boolean isWinner) {

    }

    @Override
    public void onFrame() {

    }

    @Override
    public void onSendText(String text) {

    }

    @Override
    public void onReceiveText(Player player, String text) {

    }

    @Override
    public void onPlayerLeft(Player player) {

    }

    @Override
    public void onNukeDetect(Position target) {

    }

    @Override
    public void onUnitDiscover(Unit unit) {

    }

    @Override
    public void onUnitEvade(Unit unit) {

    }

    @Override
    public void onUnitShow(Unit unit) {

    }

    @Override
    public void onUnitHide(Unit unit) {

    }

    @Override
    public void onUnitCreate(Unit unit) {

    }

    @Override
    public void onUnitDestroy(Unit unit) {

    }

    @Override
    public void onUnitMorph(Unit unit) {

    }

    @Override
    public void onUnitRenegade(Unit unit) {

    }

    @Override
    public void onSaveGame(String gameName) {

    }

    @Override
    public void onUnitComplete(Unit unit) {

    }

}
