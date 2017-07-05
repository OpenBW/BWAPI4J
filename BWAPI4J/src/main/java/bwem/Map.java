package bwem;

import bwem.unit.Neutral;
import bwem.unit.Mineral;
import bwem.unit.Geyser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Building;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;
import org.openbw.bwapi4j.util.Pair;


/**
 * Map is the entry point:
 *  - to access general information on the Map
 *  - to access the Tiles and the MiniTiles
 *  - to access the Areas
 *  - to access the StartingLocations
 *  - to access the Minerals, the Geysers and the StaticBuildings
 *  - to parametrize the analysis process
 *  - to update the information
 * Map also provides some useful tools such as Paths between ChokePoints and generic algorithms like BreadthFirstSearch.
 */
public class Map {

    private static final Logger logger = LogManager.getLogger();

    private static java.util.Map<Pair<Integer, Integer>, Integer> areaPairCounter = new HashMap<>();

    private BW bw;
    private TilePosition tileSize;
    private WalkPosition walkSize;
    private Position pixelSize;
    private Position center;
    private List<Tile> tiles;
    private List<MiniTile> miniTiles;
    private List<TilePosition> startLocations;
    private Altitude maxAltitude;
    private List<Mineral> mineralPatches;
    private List<Geyser> vespeneGeysers;
    private List<StaticBuilding> staticBuildings;
    private Graph graph;
    private List<Pair<Pair<Area.Id, Area.Id>, WalkPosition>> rawFrontier;

    private Map() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Map(BW bw) {
        this.bw = bw;
        initialize();
    }

    public Graph getGraph() {
        return this.graph;
    }

	// This has to be called before any other function is called.
	// A good place to do this is in ExampleAIModule::onStart()
    //TODO
    private void initialize() {
        this.tileSize = new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight());
        this.walkSize = new WalkPosition(getTileSize());
        this.pixelSize = getTileSize().toPosition();

        this.center = new Position(getPixelSize().getX() / 2, getPixelSize().getY() / 2);

        this.tiles = new ArrayList<>();
        int tileSizeLength = this.tileSize.getX() * this.tileSize.getY();
        for (int i = 0; i < tileSizeLength; i++) {
            this.tiles.add(new Tile());
        }

        this.miniTiles = new ArrayList<>();
        int walkSizeLength = this.walkSize.getX() * this.walkSize.getY();
        for (int i = 0; i < walkSizeLength; i++) {
            this.miniTiles.add(new MiniTile());
        }

        this.startLocations = new ArrayList<>();
        for (TilePosition t : this.bw.getBWMap().getStartPositions()) {
            this.startLocations.add(t);
        }

        this.maxAltitude = new Altitude(0);

        this.mineralPatches = new ArrayList<>();
        this.vespeneGeysers = new ArrayList<>();
        this.staticBuildings = new ArrayList<>();

        this.graph = new Graph(this);

        this.rawFrontier = new ArrayList<>();

        loadData();
        decideSeasOrLakes();
        initializeNeutrals();
        computeAltitude();
        processBlockingNeutrals();
        computeAreas();

        this.graph.createChokepoints();
    }

    /**
     * Computes walkability, buildability, groundHeight, and doodad information.
     */
    private void loadData() {
        /* Mark unwalkable minitiles (minitiles are walkable by default). */
        for (int y = 0; y < getWalkSize().getY(); ++y)
        for (int x = 0; x < getWalkSize().getX(); ++x) {
            if (!this.bw.getBWMap().isWalkable(x, y)) {
                /**
                 * For each unwalkable minitile, we also mark its eight neighbors as not walkable.
                 * According to some tests, this prevents from wrongly pretending one Marine can go by some thin path.
                 */
                for (int dy = -1; dy <= 1; ++dy)
                for (int dx = -1; dx <= 1; ++dx) {
                    WalkPosition w = new WalkPosition(x + dx, y + dy);
                    if (isValid(w)) {
                        getMiniTile(w, CheckMode.NoCheck).setWalkable(false);
                    }
                }
            }
        }

        /* Mark buildable tiles (tiles are unbuildable by default). */
        for (int y = 0 ; y < getTileSize().getY() ; ++y)
        for (int x = 0 ; x < getTileSize().getX() ; ++x) {
            TilePosition t = new TilePosition(x, y);
//            if (broodwar.getBWMap().isBuildable(t)) { //TODO: isBuildable is not implemented yet.
            if (false) {
                getTile(t).setBuildable();
                /* Ensures buildable ==> walkable: */
                for (int dy = 0 ; dy < 4 ; ++dy)
                for (int dx = 0 ; dx < 4 ; ++dx) {
                    getMiniTile(new WalkPosition(t).add(new WalkPosition(dx, dy)), CheckMode.NoCheck).setWalkable(true);
                }
            }

            /* Set groundHeight and doodad information. */
            int bwapiGroundHeight = this.bw.getBWMap().getGroundHeight(t);
            getTile(t).setGroundHeight(bwapiGroundHeight / 2);
            if (bwapiGroundHeight % 2 != 0) {
                getTile(t).setDoodad();
            }
        }
    }

    private void decideSeasOrLakes() {
        for (int y = 0 ; y < getWalkSize().getY(); ++y)
        for (int x = 0 ; x < getWalkSize().getX(); ++x) {
            WalkPosition walkOrigin = new WalkPosition(x, y);
            MiniTile miniOrigin = getMiniTile(walkOrigin, CheckMode.NoCheck);
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
                        if (isValid(nextWalkPosition)) {
                            MiniTile nextMiniTile = getMiniTile(nextWalkPosition, CheckMode.NoCheck);
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

    private void initializeNeutrals() {
        for (MineralPatch patch : this.bw.getMineralPatches()) {
            this.mineralPatches.add(new Mineral(patch, this));
        }
        for (VespeneGeyser geyser : this.bw.getVespeneGeysers()) {
            this.vespeneGeysers.add(new Geyser(geyser, this));
        }
        for (Player player : this.bw.getAllPlayers()) {
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

    private void computeAltitude() {
        /**
         * 1) Fill in and sort deltasByAscendingAltitude.
         */

        int range = Math.max(getWalkSize().getX(), getWalkSize().getY() / 2 + 3);

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

        for (int y = -1 ; y <= getWalkSize().getY() ; ++y)
        for (int x = -1 ; x <= getWalkSize().getX() ; ++x) {
            WalkPosition w = new WalkPosition(x, y);
            if (!isValid(w) || BWEM.hasNonSeaNeighbor(w, this)) {
                activeSeaSideList.add(new Pair<>(w, new Altitude(0)));
            }
        }

        /**
         * 3) Use Dijkstra's algorithm.
         */

        for (Pair<WalkPosition, Altitude> deltaAltitude : deltasByAscendingAltitude) {
            WalkPosition d = deltaAltitude.first;
            Altitude altitude = deltaAltitude.second;
            for (int i = 0; i < activeSeaSideList.size(); i++) {
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
                            MiniTile miniTile = getMiniTile(w, CheckMode.NoCheck);
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
            if (candidate.getNextStacked() != null) {
                continue;
            }

            /**
             * 1) Retrieve the Border: the outer border of candidate.
             */

            List<WalkPosition> border = BWEM.outerMiniTileBorder(candidate.getPosition().toTilePosition(), candidate.getSize());

//			really_remove_if(Border, [this](WalkPosition w)	{
//				return !Valid(w) || !GetMiniTile(w, check_t::no_check).Walkable() ||
//					GetTile(TilePosition(w), check_t::no_check).GetNeutral(); });
            for (int i = 0; i < border.size(); i++) {
                WalkPosition w = border.get(i);
                if (!isValid(w)
                        || !getMiniTile(w, CheckMode.NoCheck).isWalkable()
                        || getTile(w.toPosition().toTilePosition(), CheckMode.NoCheck).getNeutral() != null) {
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
                doors.add(door);
                border.remove(border.size() - 1);
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
                                && getTile(next.toPosition().toTilePosition(), CheckMode.NoCheck).getNeutral() == null
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
                            if (isValid(next) && !visited.contains(next)
                                    && getMiniTile(next, CheckMode.NoCheck).isWalkable()
                                    && getTile(next.toPosition().toTilePosition(), CheckMode.NoCheck).getNeutral() == null) {
                                toVisit.add(next);
                                visited.add(next);
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
                for (Neutral neutral = getTile(candidate.getPosition().toTilePosition()).getNeutral();
                        neutral != null;
                        neutral = neutral.getNextStacked()) {
                    neutral.setBlocking(trueDoors);
                }

				/* Marks all the miniTiles of pCandidate as blocked. */
				/* This way, areas at TrueDoors won't merge together. */
				for (int dy = 0 ; dy < candidate.getSize().toPosition().toWalkPosition().getY(); ++dy)
				for (int dx = 0 ; dx < candidate.getSize().toPosition().toWalkPosition().getX(); ++dx) {
					MiniTile miniTile = getMiniTile(candidate.getPosition().toWalkPosition().add(new WalkPosition(dx, dy)));
					if (miniTile.isWalkable()) {
                        miniTile.setBlocked();
                    }
				}
            }
        }
    }

    // Assigns MiniTile::m_areaId for each miniTile having AreaIdMissing()
    // Areas are computed using MiniTile::Altitude() information only.
    // The miniTiles are considered successively in descending order of their Altitude().
    // Each of them either:
    //   - involves the creation of a new area.
    //   - is added to some existing neighbouring area.
    //   - makes two neighbouring areas merge together.
    private void computeAreas() {
        List<Pair<WalkPosition, MiniTile>> miniTilesByDescendingAltitude = new ArrayList<>();
        for (int y = 0 ; y < getWalkSize().getY() ; ++y)
        for (int x = 0 ; x < getWalkSize().getX() ; ++x)
        {
            WalkPosition w = new WalkPosition(x, y);
            MiniTile m = getMiniTile(w, CheckMode.NoCheck);
            if (m.isAreaIdMissing()) {
                miniTilesByDescendingAltitude.add(new Pair<>(w, m));
            }
        }
        Collections.sort(miniTilesByDescendingAltitude, new PairGenericAltitudeComparator(PairGenericAltitudeComparator.Order.Descending));

        List<Area.TempInfo> tempAreaList = computeTempAreas(miniTilesByDescendingAltitude);

        createAreas(tempAreaList);

        setAreaIdInTiles();
    }

    private List<Area.TempInfo> computeTempAreas(List<Pair<WalkPosition, MiniTile>> miniTilesByDescendingAltitude) {
        List<Area.TempInfo> tempAreaList = new ArrayList<>();
        tempAreaList.add(new Area.TempInfo()); /* TempAreaList[0] left unused, as AreaIds are > 0. */

        for (Pair<WalkPosition, MiniTile> current : miniTilesByDescendingAltitude) {
            WalkPosition pos = current.first;
            MiniTile cur = current.second;

            Pair<Area.Id, Area.Id> neighboringAreas = BWEM.findNeighboringAreas(pos, this);
            if (neighboringAreas.first == null || neighboringAreas.first.intValue() == 0) {
                tempAreaList.add(new Area.TempInfo(new Area.Id(tempAreaList.size()), cur, pos));
            } else if (neighboringAreas.second == null || neighboringAreas.second.intValue() == 0) {
                tempAreaList.get(neighboringAreas.first.intValue()).add(cur);
            } else {
                Area.Id smaller = neighboringAreas.first;
                Area.Id bigger = neighboringAreas.second;
                if (tempAreaList.get(smaller.intValue()).getSize() > tempAreaList.get(bigger.intValue()).getSize()) {
                    Area.Id tmp = new Area.Id(smaller);
                    smaller = new Area.Id(bigger);
                    bigger = new Area.Id(tmp);
                }

                /* Condition for the neighboring areas to merge: */
//                any_of(StartingLocations().begin(), StartingLocations().end(), [&pos](const TilePosition & startingLoc)
//                    { return dist(TilePosition(pos), startingLoc + TilePosition(2, 1)) <= 3;})
                boolean cpp_algorithm_std_any_of = false;
                for (TilePosition startingLoc : this.startLocations) {
                    if (Double.compare(BWEM.dist(pos.toPosition().toTilePosition(), startingLoc.add(new TilePosition(2, 1))), Double.valueOf("3")) <= 0) {
                        cpp_algorithm_std_any_of = true;
                        break;
                    }
                }
                if (tempAreaList.get(smaller.intValue()).getSize() < 80
                        || tempAreaList.get(smaller.intValue()).getHighestAltitude().intValue() < 80
                        || Double.compare((double) cur.getAltitude().intValue() / (double) tempAreaList.get(bigger.intValue()).getHighestAltitude().intValue(), Double.valueOf("0.90")) >= 0
                        || Double.compare((double) cur.getAltitude().intValue() / (double) tempAreaList.get(smaller.intValue()).getHighestAltitude().intValue(), Double.valueOf("0.90")) >= 0
                        || cpp_algorithm_std_any_of) {
                    /* Add cur to the absorbing area. */
                    tempAreaList.get(bigger.intValue()).add(cur);

                    /* Merges the two neighboring areas. */
                    replaceAreaIds(tempAreaList.get(smaller.intValue()).getTop(), bigger);
                    tempAreaList.get(bigger.intValue()).merge(tempAreaList.get(smaller.intValue()));
                } else {
                    /* No merge : cur starts or continues the frontier between the two neighboring areas. */
                    tempAreaList.get(chooseNeighboringArea(smaller, bigger).intValue()).add(cur);
                    this.rawFrontier.add(new Pair<>(neighboringAreas, pos));
                }
            }
        }

        /* Remove from the frontier obsolete positions. */
//        really_remove_if(m_RawFrontier, [](const pair<pair<Area::id, Area::id>, BWAPI::WalkPosition> & f)
//            { return f.first.first == f.first.second; });
        for (int i = 0; i < this.rawFrontier.size(); i++) {
            int first = this.rawFrontier.get(i).first.first.intValue();
            int second = this.rawFrontier.get(i).first.second.intValue();
            if (first == second) {
                this.rawFrontier.remove(i--);
            }
        }

        return tempAreaList;
    }

    private void createAreas(List<Area.TempInfo> tempAreaList) {
        List<Pair<WalkPosition, Integer>> areasList = new ArrayList<>();

        Area.Id newAreaId = new Area.Id(1);
        Area.Id newTinyAreaId = new Area.Id(-2);

        for (Area.TempInfo tempArea : tempAreaList) {
            if (tempArea.isValid()) {
                if (tempArea.getSize() >= BWEM.AREA_MIN_MINI_TILES) {
//                    bwem_assert(newAreaId <= TempArea.Id());
                    if (!(newAreaId.intValue() <= tempArea.getId().intValue())) {
                        throw new IllegalStateException();
                    }
                    if (!newAreaId.equals(tempArea.getId())) {
                        replaceAreaIds(tempArea.getTop(), newAreaId);
                    }
                    areasList.add(new Pair<>(tempArea.getTop(), tempArea.getSize()));
                    newAreaId = new Area.Id(newAreaId.intValue() + 1);
                } else {
                    replaceAreaIds(tempArea.getTop(), newTinyAreaId);
                    newTinyAreaId = new Area.Id(newTinyAreaId.intValue() - 1);
                }
            }
        }

        this.graph.createAreas(areasList);
    }

    private void setAreaIdInTiles() {
        for (int y = 0; y < getTileSize().getY(); ++y)
        for (int x = 0; x < getTileSize().getX(); ++x) {
            TilePosition t = new TilePosition(x, y);
            setAreaIdInTile(t);
            setAltitudeInTile(t);
        }
    }

    private void setAreaIdInTile(TilePosition t) {
        Tile tile = getTile(t);
//        bwem_assert(tile.AreaId() == 0); // initialized to 0
        if (!(tile.getAreaId().intValue() == 0)) {
            throw new IllegalStateException();
        }

        for (int dy = 0 ; dy < 4 ; ++dy)
        for (int dx = 0 ; dx < 4 ; ++dx) {
            Area.Id areaId = getMiniTile(new WalkPosition(t).add(new WalkPosition(dx, dy)), CheckMode.NoCheck).getAreaId();
            if (areaId.intValue() != 0) {
                if (tile.getAreaId().intValue() == 0) {
                    tile.setAreaId(areaId);
                } else if (!tile.getAreaId().equals(areaId)) {
                    tile.setAreaId(new Area.Id(-1));
                    return;
                }
            }
        }
    }

    private void setAltitudeInTile(TilePosition t) {
        Altitude minAltitude = new Altitude(Integer.MAX_VALUE);

        for (int dy = 0 ; dy < 4 ; ++dy)
        for (int dx = 0 ; dx < 4 ; ++dx) {
            Altitude altitude = getMiniTile(new WalkPosition(t).add(new WalkPosition(dx, dy)), CheckMode.NoCheck).getAltitude();
            if (altitude.intValue() < minAltitude.intValue()) {
                minAltitude = new Altitude(altitude);
            }
        }

        getTile(t).setMinAltitude(minAltitude);
    }

    private void replaceAreaIds(WalkPosition w, Area.Id newAreaId) {
        MiniTile origin = getMiniTile(w, CheckMode.NoCheck);
        Area.Id oldAreaId = origin.getAreaId();
        origin.replaceAreaId(newAreaId);

        List<WalkPosition> toSearch = new ArrayList<>();
        toSearch.add(w);
        while (!toSearch.isEmpty()) {
            WalkPosition current = toSearch.get(toSearch.size() - 1);
            toSearch.remove(toSearch.size() - 1);
            WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
            for (WalkPosition delta : deltas) {
                WalkPosition nextWalk = current.add(delta);
                if (isValid(nextWalk)) {
                    MiniTile nextMini = getMiniTile(nextWalk, CheckMode.NoCheck);
                    if (nextMini.getAreaId().equals(oldAreaId)) {
                        toSearch.add(nextWalk);
                        nextMini.replaceAreaId(newAreaId);
                    }
                }
            }
        }

        /* Also replace references of oldAreaId by newAreaId in m_RawFrontier. */
        if (newAreaId.intValue() > 0) {
            for (Pair<Pair<Area.Id, Area.Id>, WalkPosition> f : this.rawFrontier) {
                if (f.first.first.intValue() == oldAreaId.intValue()) {
                    f.first.first = new Area.Id(newAreaId);
                }
                if (f.first.second.intValue() == oldAreaId.intValue()) {
                    f.first.second = new Area.Id(newAreaId);
                }
            }
        }
    }

    /**
     * Returns the map size using the TilePosition scale.
     * i.e. {@link TilePosition#SIZE_IN_PIXELS}.
     */
    public TilePosition getTileSize() {
        return new TilePosition(this.tileSize.getX(), this.tileSize.getY());
    }

    /**
     * Returns the map size using the WalkPosition scale.
     * i.e. {@link WalkPosition#SIZE_IN_PIXELS}.
     */
    public WalkPosition getWalkSize() {
        return new WalkPosition(this.walkSize.getX(), this.walkSize.getY());
    }

    /**
     * Returns the map size using the Position scale.
     */
    public Position getPixelSize() {
        return new Position(this.pixelSize.getX(), this.pixelSize.getY());
    }

    /**
     * Returns the Tile associated with the specified position.
     *
     * @param t The specified position.
     * @param checkMode Whether to validate the specified position.
     */
    public Tile getTile(TilePosition t, CheckMode checkMode) {
//        { bwem_assert((checkMode == utils::check_t::no_check) || Valid(p));
        if (!(checkMode == CheckMode.NoCheck || isValid(t))) {
            throw new IllegalArgumentException("TilePosition is not valid");
        } else {
            return this.tiles.get(getTileSize().getX() * t.getY() + t.getX());
        }
    }

    /**
     * Returns the Tile associated with the specified position. The specified
     * position will be validated first.
     *
     * @see #getTile(org.openbw.bwapi4j.TilePosition, bwem.CheckMode)
     * @param t The specified position.
     */
    public Tile getTile(TilePosition t) {
        return getTile(t, CheckMode.Check);
    }

    /**
     * Returns the MiniTile associated with the specified position.
     *
     * @param w The specified position.
     * @param checkMode Whether to validate the specified position.
     */
    public MiniTile getMiniTile(WalkPosition w, CheckMode checkMode) {
//        { bwem_assert((checkMode == utils::check_t::no_check) || Valid(p)); }
        if (!(checkMode == CheckMode.NoCheck || isValid(w))) {
            throw new IllegalArgumentException("WalkPosition is not valid");
        }
        return this.miniTiles.get(getWalkSize().getX() * w.getY() + w.getX());
    }

    /**
     * Returns the MiniTile associated with the specified position. The specified
     * position will be validated first.
     *
     * @see #getMiniTile(org.openbw.bwapi4j.WalkPosition, bwem.CheckMode)
     * @param w The specified position.
     */
    public MiniTile getMiniTile(WalkPosition w) {
        return getMiniTile(w, CheckMode.Check);
    }

    /**
     * Tests whether the specified position is within bounds of the map.
     *
     * @param t The specified position.
     */
    public boolean isValid(TilePosition t) {
        return (t.getX() >= 0 && t.getX() < getTileSize().getX()
                && t.getY() >= 0 && t.getY() < getTileSize().getY());
    }

    /**
     * Tests whether the specified position is within bounds of the map.
     *
     * @param w The specified position.
     */
    public boolean isValid(WalkPosition w) {
        return (w.getX() >= 0 && w.getX() < getWalkSize().getX()
                && w.getY() >= 0 && w.getY() < getWalkSize().getY());
    }

    /**
     * Tests whether the specified position is within bounds of the map.
     *
     * @param p The specified position.
     */
    public boolean isValid(Position p) {
        return (p.getX() >= 0 && p.getX() < getPixelSize().getX()
                && p.getY() >= 0 && p.getY() < getPixelSize().getY());
    }

    /**
     * Returns the center position of the map.
     */
    public Position getCenter() {
        return new Position(this.center.getX(), this.center.getY());
    }

    //TODO: Disabled for now. Why not just use "getGraph().getArea()". Convenience?
//    public Area getArea(WalkPosition w) {
//        return this.graph.getArea(w);
//    }

    public List<StaticBuilding> getStaticBuildings() {
        return this.staticBuildings;
    }

    public List<Mineral> getMineralPatches() {
        return this.mineralPatches;
    }

    public List<Pair<Pair<Area.Id, Area.Id>, WalkPosition>> getRawFrontier() {
        return this.rawFrontier;
    }

    public WalkPosition breadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond) {
        if (findCond.is(getMiniTile(start), start)) {
            return start;
        }

        List<WalkPosition> visited = new ArrayList<>();
        Queue<WalkPosition> toVisit = new LinkedList<>();

        toVisit.add(start);
        visited.add(start);

        WalkPosition[] deltas = {
            new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(1, -1),
            new WalkPosition( 1,  0),                          new WalkPosition(1,  0),
            new WalkPosition(-1,  1), new WalkPosition(0,  1), new WalkPosition(1,  1)
        };
        while (!toVisit.isEmpty()) {
            WalkPosition current = toVisit.remove();
            for (WalkPosition delta : deltas) {
                WalkPosition next = current.add(delta);
                if (isValid(next)) {
                    MiniTile nextTile = getMiniTile(next, CheckMode.NoCheck);
                    if (findCond.is(nextTile, next)) {
                        return next;
                    }
                    if (visitCond.is(nextTile, next) && !visited.contains(next)) {
                        toVisit.add(next);
                        visited.add(next);
                    }
                }
            }
        }

//        bwem_assert(false);
        throw new IllegalStateException("failed to determine a suitable return object");
//        return start;
    }

    //TODO: Double-check this method.
    public static Area.Id chooseNeighboringArea(Area.Id a, Area.Id b) {
        Area.Id tmpA = new Area.Id(a);
        Area.Id tmpB = new Area.Id(b);
        if (tmpA.intValue() > tmpB.intValue()) {
            Area.Id tmp = new Area.Id(tmpA);
            tmpA = new Area.Id(tmpB);
            tmpB = new Area.Id(tmp);
        }

        Pair<Integer, Integer> key = new Pair<>(tmpA.intValue(), tmpB.intValue());
        if (areaPairCounter.containsKey(key)) {
            int val = areaPairCounter.get(key);
            areaPairCounter.replace(key, val + 1);
        } else {
            areaPairCounter.put(key, 1);
        }

        return ((areaPairCounter.get(key) - 1) % 2 == 0) ? tmpA : tmpB;
    }

}
