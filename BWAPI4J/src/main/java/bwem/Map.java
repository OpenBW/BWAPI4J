package bwem;

import java.util.ArrayList;
import java.util.Collections;
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
import org.openbw.bwapi4j.unit.Critter;
import org.openbw.bwapi4j.unit.Egg;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;
import org.openbw.bwapi4j.util.Pair;

public class Map {

    private static final Logger logger = LogManager.getLogger();

    private static final Map INSTANCE = new Map();

    private BW bw = null;
    private TilePosition tileSize = null;
    private WalkPosition walkSize = null;
    private Position pixelSize = null;
    private Position center = null;
    private List<Tile> tiles = null;
    private List<MiniTile> miniTiles = null;
    private List<TilePosition> startLocations = null;
    private Altitude maxAltitude = null;
    private List<MineralPatch> minerals;
    private List<VespeneGeyser> geysers;
    private List<Building> staticBuildings;
    private List<Critter> critters;
    private List<Egg> neutralEggs;

    private Map() {
        /* Do nothing. */
    }

    public static Map Instance() {
        return INSTANCE;
    }

    //TODO
    public void initialize(BW bw) {
        this.bw = bw;
        this.tileSize = new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight());
        this.walkSize = new WalkPosition(getTileSize());
        this.pixelSize = getTileSize().toPosition();
        this.center = new Position(getTileSize().getX() / 2, getTileSize().getY() / 2);
        this.tiles = new ArrayList<>();

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

        loadData();
        decideSeasOrLakes();
        initializeNeutrals();
        computeAltitude();
        processBlockingNeutrals();
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
        for (int x = 0 ; x < getWalkSize().getX(); ++x)
        {
            WalkPosition walkOrigin = new WalkPosition(x, y);
            MiniTile miniOrigin = getMiniTile(walkOrigin, CheckMode.NoCheck);
            if (miniOrigin.isSeaOrLake())
            {
                List<WalkPosition> ToSearch = new ArrayList<>();
                ToSearch.add(walkOrigin);
                List<MiniTile> SeaExtent = new ArrayList<>();
                SeaExtent.add(miniOrigin);
                miniOrigin.setSea();
                WalkPosition topLeft = walkOrigin;
                WalkPosition bottomRight = walkOrigin;
                while (!ToSearch.isEmpty())
                {
                    WalkPosition current = ToSearch.get(ToSearch.size() - 1);
                    if (current.getX() < topLeft.getX()) topLeft = new WalkPosition(current.getX(), topLeft.getY());
                    if (current.getY() < topLeft.getY()) topLeft = new WalkPosition(topLeft.getX(), current.getY());
                    if (current.getX() > bottomRight.getX()) bottomRight = new WalkPosition(current.getX(), bottomRight.getY());
                    if (current.getY() > bottomRight.getY()) bottomRight = new WalkPosition(bottomRight.getX(), current.getY());

                    ToSearch.remove(ToSearch.size() - 1);
                    WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
                    for (WalkPosition delta : deltas)
                    {
                        WalkPosition nextWalkPosition = current.add(delta);
                        if (isValid(nextWalkPosition))
                        {
                            MiniTile nextMiniTile = getMiniTile(nextWalkPosition, CheckMode.NoCheck);
                            if (nextMiniTile.isSeaOrLake())
                            {
                                ToSearch.add(nextWalkPosition);
                                nextMiniTile.setSea();
                                if (SeaExtent.size() <= BWEM.MAX_LAKE_MINI_TILES) SeaExtent.add(nextMiniTile);
                            }
                        }
                    }
                }

                if ((SeaExtent.size() <= BWEM.MAX_LAKE_MINI_TILES)
                        && (bottomRight.getX() - topLeft.getX() <= BWEM.MAX_LAKE_MINI_TILES)
                        && (bottomRight.getY() - topLeft.getY() <= BWEM.MAX_LAKE_MINI_TILES)
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
            this.minerals.add(patch);
        }
        for (VespeneGeyser geyser : this.bw.getVespeneGeysers()) {
            this.geysers.add(geyser);
        }
        for (Player player : this.bw.getAllPlayers()) {
            if (!player.isNeutral()) {
                continue;
            }
            for (Unit unit : player.getUnits()) {
                if (unit instanceof Building) {
                    this.staticBuildings.add((Building) unit);
                } else if (unit instanceof Critter) {
                    this.critters.add((Critter) unit);
                } else if (unit instanceof Egg) {
                    this.neutralEggs.add((Egg) unit);
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
                deltasByAscendingAltitude.add(new Pair<WalkPosition, Altitude>(
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
        for (int x = -1 ; x <= getWalkSize().getX() ; ++x)
        {
            WalkPosition w = new WalkPosition(x, y);
            if (!isValid(w) || BWEM.hasNonSeaNeighbor(w, this)) {
                activeSeaSideList.add(new Pair<WalkPosition, Altitude>(w, new Altitude(0)));
            }
        }

        /**
         * 3) Use Dijkstra's algorithm.
         */

        for (Pair<WalkPosition, Altitude> pair : activeSeaSideList) {
            WalkPosition delta = pair.first;
            Altitude altitude = pair.second;
            for (int i = 0; i < activeSeaSideList.size(); i++) {
                Pair<WalkPosition, Altitude> current = activeSeaSideList.get(i);
                if (altitude.subtract(current.second).intValue() >= (2 * MiniTile.SIZE_IN_PIXELS)) { // optimization : once a seaside miniTile verifies this condition,
                    activeSeaSideList.remove(i--);                                                   // we can throw it away as it will not generate min altitudes anymore
                } else {
                    WalkPosition[] tmpDeltas = {
                        new WalkPosition(delta.getX(),  delta.getY()), new WalkPosition(-delta.getX(),  delta.getY()),
                        new WalkPosition(delta.getX(), -delta.getY()), new WalkPosition(-delta.getX(), -delta.getY()),
                        new WalkPosition(delta.getY(),  delta.getX()), new WalkPosition(-delta.getY(),  delta.getX()),
                        new WalkPosition(delta.getY(), -delta.getX()), new WalkPosition(-delta.getY(), -delta.getX()),
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

    //TODO
    private void processBlockingNeutrals() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public TilePosition getTileSize() {
        return new TilePosition(this.tileSize.getX(), this.tileSize.getY());
    }

    public WalkPosition getWalkSize() {
        return new WalkPosition(this.walkSize.getX(), this.walkSize.getY());
    }

    public Position getPixelSize() {
        return new Position(this.pixelSize.getX(), this.pixelSize.getY());
    }

    public Tile getTile(TilePosition t, CheckMode checkMode) {
//        { bwem_assert((checkMode == utils::check_t::no_check) || Valid(p));
        if (!(checkMode == CheckMode.NoCheck || isValid(t))) {
            throw new IllegalArgumentException("TilePosition is not valid");
        } else {
            return this.tiles.get(getTileSize().getX() * t.getY() + t.getX());
        }
    }

    public Tile getTile(TilePosition t) {
        return getTile(t, CheckMode.Check);
    }

    public MiniTile getMiniTile(WalkPosition w, CheckMode checkMode) {
//        { bwem_assert((checkMode == utils::check_t::no_check) || Valid(p)); }
        if (!(checkMode == CheckMode.NoCheck || isValid(w))) {
            throw new IllegalArgumentException("WalkPosition is not valid");
        }
        return this.miniTiles.get(getWalkSize().getX() * w.getY() + w.getX());
    }

    public MiniTile getMiniTile(WalkPosition p) {
        return getMiniTile(p, CheckMode.Check);
    }

    public boolean isValid(TilePosition t) {
        return (t.getX() >= 0)
                && (t.getX() < getTileSize().getX())
                && (t.getY() >= 0)
                && (t.getY() < getTileSize().getY());
    }

    public boolean isValid(WalkPosition w) {
        return (w.getX() >= 0)
                && (w.getX() < getWalkSize().getX())
                && (w.getY() >= 0)
                && (w.getY() < getWalkSize().getY());
    }

    public WalkPosition breadFirstSearch(WalkPosition start, Pred findCond, Pred visitCond) {
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
        return start;
    }

}
