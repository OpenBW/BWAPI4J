package bwem.map;

import bwem.Altitude;
import bwem.BWEM;
import bwem.CheckMode;
import bwem.MiniTile;
import bwem.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.StaticBuilding;
import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Building;
import org.openbw.bwapi4j.unit.Egg;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public final class MapImpl extends Map {

    private Altitude maxAltitude = null;
    private List<TilePosition> startingLocations = null;
    private List<Mineral> mineralPatches = null;
    private List<Geyser> vespeneGeysers = null;
    private List<StaticBuilding> staticBuildings = null;

    public MapImpl(BW bw) {
        super(bw);
    }

    //TODO
    @Override
    public void initialize() {
//        Timer overallTimer = new Timer();
//        Timer timer = new Timer();

        super.tileSize = new TilePosition(super.getBW().getBWMap().mapWidth(), super.getBW().getBWMap().mapHeight());
        super.tilePositionCount = super.getTileSize().getX() * super.getTileSize().getY();
        //TODO: m_Tiles.resize(m_size); OR declare tiles array
        super.tiles = new ArrayList<>();
        for (int i = 0; i < super.tilePositionCount; ++i) {
            super.tiles.add(new Tile());
        }

        super.walkSize = super.getTileSize().toPosition().toWalkPosition();
        super.walkPositionCount = super.getWalkSize().getX() * super.getWalkSize().getY();
        super.miniTiles = new ArrayList<>();
        for (int i = 0; i < super.walkPositionCount; ++i) {
            super.miniTiles.add(new MiniTile());
        }

        super.pixelSize = super.getTileSize().toPosition();
        super.pixelCount = super.getPixelSize().getX() * super.getPixelSize().getY();

        super.center = new Position(getPixelSize().getX() / 2, getPixelSize().getY() / 2);

        this.startingLocations = new ArrayList<>();
        for (TilePosition t : super.getBW().getBWMap().getStartPositions()) {
            this.startingLocations.add(t);
        }

        this.maxAltitude = new Altitude(0);

        this.mineralPatches = new ArrayList<>();
        this.vespeneGeysers = new ArrayList<>();
        this.staticBuildings = new ArrayList<>();
//
//        this.graph = new Graph(this);
//
//        this.rawFrontier = new ArrayList<>();

///	bw << "Map::Initialize-resize: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();

	loadData();
///	bw << "Map::LoadData: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::LoadData: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

	decideSeasOrLakes();
///	bw << "Map::DecideSeasOrLakes: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::DecideSeasOrLakes: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

	initializeNeutrals();
///	bw << "Map::InitializeNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::InitializeNeutrals: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

//	ComputeAltitude();
///	bw << "Map::ComputeAltitude: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::ComputeAltitude: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

//	ProcessBlockingNeutrals();
///	bw << "Map::ProcessBlockingNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::ProcessBlockingNeutrals: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

//	ComputeAreas();
///	bw << "Map::ComputeAreas: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::ComputeAreas: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

//	GetGraph().CreateChokePoints();
///	bw << "Graph::CreateChokePoints: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::CreateChokePoints: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

//	GetGraph().ComputeChokePointDistanceMatrix();
///	bw << "Graph::ComputeChokePointDistanceMatrix: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::ComputeChokePointDistanceMatrix: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

//	GetGraph().CollectInformation();
///	bw << "Graph::CollectInformation: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::CollectInformation: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

//	GetGraph().CreateBases();
///	bw << "Graph::CreateBases: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::CreateBases: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

///	bw << "Map::Initialize: " << overallTimer.ElapsedMilliseconds() << " ms" << endl;
//    System.out.println("Map::Initialize: " + overallTimer.getElapsedMilliseconds() + " ms"); overallTimer.stop();

        //TODO
        throw new UnsupportedOperationException("todo");
    }

    @Override
    public Altitude getMaxAltitude() {
        return this.maxAltitude;
    }

    @Override
    public List<TilePosition> getStartingLocations() {
        List<TilePosition> ret = new ArrayList<>();
        for (TilePosition t : this.startingLocations) {
            ret.add(t);
        }
        return ret;
    }

    private void loadData()  {
        /* Mark unwalkable minitiles (minitiles are walkable by default). */
        for (int y = 0; y < super.getWalkSize().getY(); ++y)
        for (int x = 0; x < super.getWalkSize().getX(); ++x) {
            if (!super.getBW().getBWMap().isWalkable(x, y)) {
                /**
                 * For each unwalkable minitile, we also mark its eight neighbors as not walkable.
                 * According to some tests, this prevents from wrongly pretending one Marine can go by some thin path.
                 */
                for (int dy = -1; dy <= 1; ++dy)
                for (int dx = -1; dx <= 1; ++dx) {
                    WalkPosition w = new WalkPosition(x + dx, y + dy);
                    if (super.isValid(w)) {
                        super.getMiniTile_(w, CheckMode.NoCheck).setWalkable(false);
                    }
                }
            }
        }

        /* Mark buildable tiles (tiles are unbuildable by default). */
        for (int y = 0; y < super.getTileSize().getY(); ++y)
        for (int x = 0; x < super.getTileSize().getX(); ++x) {
            TilePosition t = new TilePosition(x, y);
//            if (broodwar.getBWMap().isBuildable(t)) { //TODO: isBuildable is not implemented yet.
            if (false) {
                super.getTile_(t).setBuildable();
                /* Ensures buildable ==> walkable: */
                for (int dy = 0 ; dy < 4 ; ++dy)
                for (int dx = 0 ; dx < 4 ; ++dx) {
                    super.getMiniTile_(new WalkPosition(t).add(new WalkPosition(dx, dy)), CheckMode.NoCheck).setWalkable(true);
                }
            }

            /* Set groundHeight and doodad information. */
            int bwapiGroundHeight = super.getBW().getBWMap().getGroundHeight(t);
            super.getTile_(t).setGroundHeight(bwapiGroundHeight / 2);
            if (bwapiGroundHeight % 2 != 0) {
                super.getTile_(t).setDoodad();
            }
        }
    }

    private void decideSeasOrLakes() {
        for (int y = 0 ; y < super.getWalkSize().getY(); ++y)
        for (int x = 0 ; x < super.getWalkSize().getX(); ++x) {
            WalkPosition walkOrigin = new WalkPosition(x, y);
            MiniTile miniOrigin = super.getMiniTile_(walkOrigin, CheckMode.NoCheck);
            if (miniOrigin.isSeaOrLake()) {
                List<WalkPosition> ToSearch = new ArrayList<>();
                ToSearch.add(walkOrigin);
                List<MiniTile> SeaExtent = new ArrayList<>();
                SeaExtent.add(miniOrigin);
                miniOrigin.setSea();
                WalkPosition topLeft = walkOrigin;
                WalkPosition bottomRight = walkOrigin;
                while (!ToSearch.isEmpty()) {
                    WalkPosition current = ToSearch.get(ToSearch.size() - 1);
                    if (current.getX() < topLeft.getX()) topLeft = new WalkPosition(current.getX(), topLeft.getY());
                    if (current.getY() < topLeft.getY()) topLeft = new WalkPosition(topLeft.getX(), current.getY());
                    if (current.getX() > bottomRight.getX()) bottomRight = new WalkPosition(current.getX(), bottomRight.getY());
                    if (current.getY() > bottomRight.getY()) bottomRight = new WalkPosition(bottomRight.getX(), current.getY());

                    ToSearch.remove(ToSearch.size() - 1);
                    WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
                    for (WalkPosition delta : deltas) {
                        WalkPosition nextWalkPosition = current.add(delta);
                        if (super.isValid(nextWalkPosition)) {
                            MiniTile nextMiniTile = super.getMiniTile_(nextWalkPosition, CheckMode.NoCheck);
                            if (nextMiniTile.isSeaOrLake()) {
                                ToSearch.add(nextWalkPosition);
                                nextMiniTile.setSea();
                                if (SeaExtent.size() <= BWEM.LAKE_MAX_MINI_TILES) {
                                    SeaExtent.add(nextMiniTile);
                                }
                            }
                        }
                    }
                }

                if ((SeaExtent.size() <= BWEM.LAKE_MAX_MINI_TILES)
                        && (bottomRight.getX() - topLeft.getX() <= BWEM.LAKE_MAX_MINI_TILES)
                        && (bottomRight.getY() - topLeft.getY() <= BWEM.LAKE_MAX_MINI_TILES)
                        && (topLeft.getX() >= 2)
                        && (topLeft.getY() >= 2)
                        && (bottomRight.getX() < getWalkSize().getX() - 2)
                        && (bottomRight.getY() < getWalkSize().getY() - 2)) {
                    for (MiniTile pSea : SeaExtent) {
                        pSea.setLake();
                    }
                }
            }
        }
    }

    //TODO: This is different from the original. Double-check this is accurate.
    private void initializeNeutrals() {
        for (MineralPatch patch : super.getBW().getMineralPatches()) {
            this.mineralPatches.add(new Mineral(patch, this));
        }
        for (VespeneGeyser geyser : super.getBW().getVespeneGeysers()) {
            this.vespeneGeysers.add(new Geyser(geyser, this));
        }
        for (Player player : super.getBW().getAllPlayers()) {
            if (!player.isNeutral()) {
                continue;
            }
            for (Unit unit : player.getUnits()) {
                if (unit instanceof Building) {
                    this.staticBuildings.add(new StaticBuilding(unit, this));
                }
                //TODO: Add "Special_Pit_Door" and "Special_Right_Pit_Door" to static buildings list? See mapImpl.cpp:238.
            }
        }
    }

}
