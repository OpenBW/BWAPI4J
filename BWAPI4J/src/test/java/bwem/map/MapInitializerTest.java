package bwem.map;

import bwem.BWEM;
import bwem.Graph;
import bwem.util.BwemExt;
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
import org.openbw.bwapi4j.unit.Unit;

import java.util.Arrays;

public class MapInitializerTest implements BWEventListener {

    private static final Logger logger = LogManager.getLogger();

    private OriginalBwapiData bwapiData;

    private BW bw;
    private Map map;

    @Before
    public void setUp() {
        if (this.bwapiData == null) {
            try {
                this.bwapiData = new OriginalBwapiData();
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Could not load dummy BWAPI data.");
            }
        }
    }

    @Test
    public void testInitializer() {
        this.bw = new BW(this);
        this.bw.startGame();
    }

    @Override
    public void onStart() {
        logger.debug("ack");

        BWMap bwMap = this.bw.getBWMap();
        this.map = new BWEM(this.bw).GetMap();
        Graph graph = ((MapImpl) this.map).GetGraph();

        MapInitializer mapInitializer = (MapInitializer) this.map;

        mapInitializer.compileAdvancedData(
                this.bwapiData.mapSize_FightingSpirit_ORIGINAL.getX(),
                this.bwapiData.mapSize_FightingSpirit_ORIGINAL.getY(),
                Arrays.asList(this.bwapiData.startLocations_FightingSpirit_ORIGINAL)
        );
        AdvancedData advancedData = this.map.getData();

        mapInitializer.markUnwalkableMiniTiles(advancedData, bwMap);
        mapInitializer.markBuildableTilesAndGroundHeight(advancedData, bwMap);

        mapInitializer.DecideSeasOrLakes(advancedData, BwemExt.lake_max_miniTiles, BwemExt.lake_max_width_in_miniTiles);

        Assert.assertTrue("MineralPatches list is null", this.bw.getMineralPatches() != null);
        Assert.assertTrue("Minerals list is null", this.map.Minerals() != null);

        mapInitializer.InitializeNeutrals(
                this.bw.getMineralPatches(), this.map.Minerals(),
                this.bw.getVespeneGeysers(), this.map.Geysers(),
                mapInitializer.filterNeutralPlayerUnits(this.bw.getAllUnits(), this.bw.getAllPlayers()), this.map.StaticBuildings()
        );

        mapInitializer.ComputeAltitude(advancedData);

        mapInitializer.ProcessBlockingNeutrals(mapInitializer.getCandidates(this.map.StaticBuildings(), this.map.Minerals()));

        mapInitializer.ComputeAreas(mapInitializer.ComputeTempAreas(mapInitializer.getSortedMiniTilesByDescendingAltitude()), BwemExt.area_min_miniTiles);

        graph.CreateChokePoints(this.map.StaticBuildings(), this.map.Minerals(), this.map.RawFrontier());

        graph.ComputeChokePointDistanceMatrix();

        graph.CollectInformation();

        graph.CreateBases();

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
