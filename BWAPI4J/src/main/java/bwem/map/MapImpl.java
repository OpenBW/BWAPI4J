package bwem.map;

import bwem.Altitude;
import bwem.BWEM;
import bwem.CheckMode;
import bwem.MiniTile;
import bwem.PairGenericAltitudeComparator;
import bwem.Tile;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import java.util.ArrayList;
import java.util.Collections;
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
import org.openbw.bwapi4j.util.Pair;

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

	computeAltitude();
///	bw << "Map::ComputeAltitude: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
//    System.out.println("Map::ComputeAltitude: " + timer.getElapsedMilliseconds() + " ms"); timer.reset();

	processBlockingNeutrals();
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

    // Assigns MiniTile::m_altitude foar each miniTile having AltitudeMissing()
    // Cf. MiniTile::Altitude() for meaning of altitude_t.
    // Altitudes are computed using the straightforward Dijkstra's algorithm : the lower ones are computed first, starting from the seaside-miniTiles neighbours.
    // The point here is to precompute all possible altitudes for all possible tiles, and sort them.
    private void computeAltitude() {
        /**
         * 1) Fill in and sort deltasByAscendingAltitude.
         */

        int range = Math.max(super.getWalkSize().getX(), super.getWalkSize().getY()) / 2 + 3; // should suffice for maps with no Sea.

        List<Pair<WalkPosition, Altitude>> deltasByAscendingAltitude = new ArrayList<>();

        for (int dy = 0; dy <= range; ++dy)
        for (int dx = dy; dx <= range; ++dx) { // Only consider 1/8 of possible deltas. Other ones obtained by symmetry.
            if (dx != 0 || dy != 0) {
                deltasByAscendingAltitude.add(new Pair<>(
                        new WalkPosition(dx, dy),
                        new Altitude((int) (Double.valueOf("0.5") + (double) BWEM.norm(dx, dy) * (double) MiniTile.SIZE_IN_PIXELS))
                ));
            }
        }

        Collections.sort(deltasByAscendingAltitude, new PairGenericAltitudeComparator());

        /**
         * 2) Fill in ActiveSeaSideList, which basically contains all the seaside miniTiles (from which altitudes are to be computed)
         * It also includes extra border-miniTiles which are considered as seaside miniTiles too.
         */

        List<Pair<WalkPosition, Altitude>> activeSeaSideList = new ArrayList<>();

        for (int y = -1 ; y <= super.getWalkSize().getY() ; ++y)
        for (int x = -1 ; x <= super.getWalkSize().getX() ; ++x) {
            WalkPosition w = new WalkPosition(x, y);
            if (!super.isValid(w) || BWEM.hasNonSeaNeighbor(w, this)) {
                activeSeaSideList.add(new Pair<>(w, new Altitude(0)));
            }
        }

        /**
         * 3) Use Dijkstra's algorithm.
         */

        for (Pair<WalkPosition, Altitude> deltaAltitude : deltasByAscendingAltitude) {
            WalkPosition d = deltaAltitude.first;
            Altitude altitude = deltaAltitude.second;
            for (int i = 0; i < activeSeaSideList.size(); ++i) {
                Pair<WalkPosition, Altitude> current = activeSeaSideList.get(i);
                if (altitude.subtract(current.second).intValue() >= (2 * MiniTile.SIZE_IN_PIXELS)) { // optimization : once a seaside miniTile verifies this condition,
                    activeSeaSideList.remove(i--);                                                   // we can throw it away as it will not generate min altitudes anymore
                } else {
                    WalkPosition[] tmpDeltas = {
                        new WalkPosition(d.getX(),  d.getY()), new WalkPosition(-d.getX(),  d.getY()),
                        new WalkPosition(d.getX(), -d.getY()), new WalkPosition(-d.getX(), -d.getY()),
                        new WalkPosition(d.getY(),  d.getX()), new WalkPosition(-d.getY(),  d.getX()),
                        new WalkPosition(d.getY(), -d.getX()), new WalkPosition(-d.getY(), -d.getX()),
                    };
                    for (WalkPosition tmpDelta : tmpDeltas) {
                        WalkPosition w = (current.first).add(tmpDelta);
                        if (isValid(w)) {
                            MiniTile miniTile = getMiniTile_(w, CheckMode.NoCheck);
                            if (miniTile.isAltitudeMissing()) {
                                this.maxAltitude = new Altitude(altitude);
                                current.second = new Altitude(altitude);
                                miniTile.setAltitude(new Altitude(this.maxAltitude));
                            }
                        }
                    }
                }
            }
        }
    }

    private void processBlockingNeutrals() {
        List<Neutral> candidates = new ArrayList<>();
        for (StaticBuilding staticBuilding : this.staticBuildings) {
            candidates.add(staticBuilding);
        }
        for (Mineral mineralPatch : this.mineralPatches) {
            candidates.add(mineralPatch);
        }

        for (Neutral candidate : candidates) {
            if (candidate.getNextStacked() == null) {
                /**
                 * 1) Retrieve the Border: the outer border of candidate.
                 */

                List<WalkPosition> border = BWEM.outerMiniTileBorder(candidate.getTopLeft(), candidate.getSize());

    //			really_remove_if(Border, [this](WalkPosition w)	{
    //				return !Valid(w) || !GetMiniTile(w, check_t::no_check).Walkable() ||
    //					GetTile(TilePosition(w), check_t::no_check).GetNeutral(); });
                for (int i = 0; i < border.size(); i++) {
                    WalkPosition w = border.get(i);
                    if (!isValid(w)
                            || !getMiniTile(w, CheckMode.NoCheck).isWalkable()
                            || getTile(w.toPosition().toTilePosition(), CheckMode.NoCheck).getOccupyingNeutral() != null) {
                        border.remove(i--);
                    }
                }

                /**
                 * 2) Find the doors in Border: one door for each connected set of walkable, neighboring miniTiles.
                 * The searched connected miniTiles all have to be next to some lake or some static building, though they can't be part of one.
                 */

                List<WalkPosition> doors = new ArrayList<>();
                while (!border.isEmpty()) {
                    WalkPosition door = border.get(border.size() - 1);
                    border.remove(border.size() - 1);
                    doors.add(door);
                    List<WalkPosition> toVisit = new ArrayList<>();
                    toVisit.add(door);
                    List<WalkPosition> visited = new ArrayList<>();
                    visited.add(door);
                    while (!toVisit.isEmpty()) {
                        WalkPosition current = toVisit.get(toVisit.size() - 1);
                        toVisit.remove(toVisit.size() - 1);
                        WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
                        for (WalkPosition delta : deltas) {
                            WalkPosition next = current.add(delta);
                            if (isValid(next)
                                    && !visited.contains(next)
                                    && getMiniTile(next, CheckMode.NoCheck).isWalkable()
                                    && getTile(next.toPosition().toTilePosition(), CheckMode.NoCheck).getOccupyingNeutral() == null
                                    && BWEM.adjoins8SomeLakeOrNeutral(next, this)) {
                                toVisit.add(next);
                                visited.add(next);
                            }
                        }
                    }

    //                really_remove_if(Border, [&Visited](WalkPosition w)	{ return contains(Visited, w); });
                    for (int i = 0; i < border.size(); i++) {
                        WalkPosition w = border.get(i);
                        if (visited.contains(w)) {
                            border.remove(i--);
                        }
                    }
                }

                /**
                 * 3)  If at least 2 doors, find the true doors in Border: a true door is a door that gives onto an area big enough.
                 */

                List<WalkPosition> trueDoors = new ArrayList<>();
                if (doors.size() >= 2) {
                    for (WalkPosition door : doors) {
                        List<WalkPosition> toVisit = new ArrayList<>();
                        toVisit.add(door);
                        List<WalkPosition> visited = new ArrayList<>();
                        visited.add(door);
                        int limit = (candidate.getUnit() instanceof Building) ? 10 : 400;
                        while (!toVisit.isEmpty() && visited.size() < limit) {
                            WalkPosition current = toVisit.get(toVisit.size() - 1);
                            toVisit.remove(toVisit.size() - 1);
                            WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
                            for (WalkPosition delta : deltas) {
                                WalkPosition next = current.add(delta);
                                if (isValid(next) && !visited.contains(next)) {
                                    if (getMiniTile(next, CheckMode.NoCheck).isWalkable()) {
                                        if (getTile(next.toPosition().toTilePosition(), CheckMode.NoCheck).getOccupyingNeutral() == null) {
                                            toVisit.add(next);
                                            visited.add(next);
                                        }
                                    }
                                }
                            }
                        }
                        if (visited.size() >= limit) {
                            trueDoors.add(door);
                        }
                    }
                }

                /**
                 * 4) If at least 2 true doors, pCandidate is a blocking static building
                 */

                if (trueDoors.size() >= 2) {
                    /* Marks pCandidate (and any Neutral stacked with it) as blocking. */
                    for (Neutral neutral = getTile(candidate.getTopLeft()).getOccupyingNeutral();
                            neutral != null;
                            neutral = neutral.getNextStacked()) {
                        neutral.setBlockedWalkPositions(trueDoors);
                    }

                    /* Marks all the miniTiles of pCandidate as blocked. */
                    /* This way, areas at TrueDoors won't merge together. */
                    for (int dy = 0 ; dy < candidate.getSize().toPosition().toWalkPosition().getY(); ++dy)
                    for (int dx = 0 ; dx < candidate.getSize().toPosition().toWalkPosition().getX(); ++dx) {
                        MiniTile miniTile = getMiniTile_(candidate.getTopLeft().toPosition().toWalkPosition().add(new WalkPosition(dx, dy)));
                        if (miniTile.isWalkable()) {
                            miniTile.setBlocked();
                        }
                    }
                }
            }
        }
    }

}
