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
    private Graph graph = null;
    private TilePosition tileSize = null;
    private WalkPosition walkSize = null;
    private Position pixelSize = null;
    private Position center = null;
    private List<Tile> tiles = null;
    private List<MiniTile> miniTiles = null;
    private List<TilePosition> startingLocations = null;
    private Altitude maxAltitude = null;
    private List<MineralPatch> minerals;
    private List<VespeneGeyser> geysers;
    private List<Building> staticBuildings;
    private List<Critter> critters;
    private List<Egg> neutralEggs;

    private Map() {}

    public static Map Instance() {
        return INSTANCE;
    }

    /* mapImpl.cpp:68:void MapImpl::Initialize() */
    public void initialize(BW bw) {
        this.bw = bw;
        this.graph = new Graph(this);
        this.tiles = new ArrayList<>();
        this.miniTiles = new ArrayList<>();
        this.startingLocations = new ArrayList<>();
        this.tileSize = new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight());
        this.walkSize = new WalkPosition(getTileSize());
        this.pixelSize = getTileSize().toPosition();
        this.center = new Position(getPixelSize().getX() / 2, getPixelSize().getY() / 2);
        for (TilePosition tilePosition : this.bw.getBWMap().getStartPositions()) {
            this.startingLocations.add(tilePosition);
        }
        this.maxAltitude = new Altitude(0);

        loadData(); //TODO
        decideSeasOrLakes();
        initializeNeutrals();
        computeAltitude();
        processBlockingNeutrals(); //TODO
        //TODO
    }

    public boolean isInitialized() {
        return (this.tileSize != null);
    }



//////////////////////////////////////////////////////////////////////
/// map.h:75
//////////////////////////////////////////////////////////////////////

//	// Returns the status of the automatic path update (off (false) by default).
//	// When on, each time a blocking Neutral (either Mineral or StaticBuilding) is destroyed,
//	// any information relative to the paths through the Areas is updated accordingly.
//	// For this to function, the Map still needs to be informed of such destructions
//	// (by calling OnMineralDestroyed and OnStaticBuildingDestroyed).
//	virtual bool						AutomaticPathUpdate() const = 0;
//
//	// Enables the automatic path update (Cf. AutomaticPathUpdate()).
//	// One might NOT want to call this function, in order to make the accessibility between Areas remain the same throughout the game.
//	// Even in this case, one should keep calling OnMineralDestroyed and OnStaticBuildingDestroyed.
//	virtual void						EnableAutomaticPathAnalysis() const = 0;
//
//	// Tries to assign one Base for each starting Location in StartingLocations().
//	// Only nearby Bases can be assigned (Cf. detail::max_tiles_between_StartingLocation_and_its_AssignedBase).
//	// Each such assigned Base then has Starting() == true, and its Location() is updated.
//	// Returns whether the function succeeded (a fail may indicate a failure in BWEM's Base placement analysis
//	// or a suboptimal placement in one of the starting Locations).
//	// You normally should call this function, unless you want to compare the StartingLocations() with
//	// BWEM's suggested locations for the Bases.
//	virtual bool						FindBasesForStartingLocations() = 0;

//////////////////////////////////////////////////////////////////////



    public TilePosition getTileSize() {
        return new TilePosition(this.tileSize.getX(), this.tileSize.getY());
    }

    public WalkPosition getWalkSize() {
        return new WalkPosition(this.walkSize.getX(), this.walkSize.getY());
    }

    public Position getPixelSize() {
        return new Position(this.pixelSize.getX(), this.pixelSize.getY());
    }

    public Position getCenter() {
        return new Position(this.center.getX(), this.center.getY());
    }

    public Altitude getMaxAltitude() {
        return this.maxAltitude;
    }



//////////////////////////////////////////////////////////////////////
/// map.h:101
//////////////////////////////////////////////////////////////////////

//	// Returns the number of Bases.
//	virtual int							BaseCount() const = 0;
//
//	// Returns the number of ChokePoints.
//	virtual int							ChokePointCount() const = 0;

//////////////////////////////////////////////////////////////////////



    public Tile getTile(TilePosition p, boolean check) {
        if (!((check == false) || isValid(p))) {
            throw new IllegalArgumentException();
        }
        return this.tiles.get(getTileSize().getX() * p.getY() + p.getX());
    }

    public Tile getTile(TilePosition p) {
        return getTile(p, true);
    }

	public MiniTile getMiniTile(WalkPosition wp, boolean check) {
        if (!((check == false) || isValid(wp))) {
            throw new IllegalArgumentException();
        }
        return this.miniTiles.get(getWalkSize().getX() * wp.getY() + wp.getX());
    }

    public MiniTile getMiniTile(WalkPosition wp) {
        return getMiniTile(wp, true);
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    /**
     * Returns the internal representation of MiniTile objects.
     */
    public List<MiniTile> getMiniTiles() {
        return this.miniTiles;
    }

    public boolean isValid(TilePosition p) {
        return (p.getX() >= 0 && p.getX() < getTileSize().getX()
                && p.getY() >= 0 && p.getY() < getTileSize().getY());
    }

    public boolean isValid(WalkPosition p) {
        return (p.getX() >= 0 && p.getX() < getWalkSize().getX()
                && p.getY() >= 0 && p.getY() < getWalkSize().getY());
    }

    public boolean isValid(Position p) {
        return isValid(p.toTilePosition());
    }

    private int cropX(int x, int ceiling) {
        return (x < 0) ? 0 : (x >= ceiling) ? (ceiling - 1) : x;
    }

    private int cropY(int y, int ceiling) {
        return (y < 0) ? 0 : (y >= ceiling) ? (ceiling - 1) : y;
    }

    public TilePosition crop(TilePosition p) {
        int x = cropX(p.getX(), getTileSize().getX());
        int y = cropY(p.getY(), getTileSize().getY());
        return new TilePosition(x, y);
    }

    public WalkPosition crop(WalkPosition p) {
        int x = cropX(p.getX(), getWalkSize().getX());
        int y = cropY(p.getY(), getWalkSize().getY());
        return new WalkPosition(x, y);
    }

    public Position crop(Position p) {
        int x = cropX(p.getX(), getPixelSize().getX());
        int y = cropY(p.getY(), getPixelSize().getY());
        return new Position(x, y);
    }

    public List<TilePosition> getStartingLocations() {
        return this.startingLocations;
    }



//////////////////////////////////////////////////////////////////////
/// map.h:144
//////////////////////////////////////////////////////////////////////

//	// Returns a reference to the Minerals (Cf. Mineral).
//	virtual const std::vector<std::unique_ptr<Mineral>> &			Minerals() const = 0;
//
//	// Returns a reference to the Geysers (Cf. Geyser).
//	virtual const std::vector<std::unique_ptr<Geyser>> &			Geysers() const = 0;
//
//	// Returns a reference to the StaticBuildings (Cf. StaticBuilding).
//	virtual const std::vector<std::unique_ptr<StaticBuilding>> &	StaticBuildings() const = 0;
//
//	// If a Mineral wrappers the given BWAPI unit, returns a pointer to it.
//	// Otherwise, returns nullptr.
//	virtual Mineral *					GetMineral(BWAPI::Unit u) const = 0;
//
//	// If a Geyser wrappers the given BWAPI unit, returns a pointer to it.
//	// Otherwise, returns nullptr.
//	virtual Geyser *					GetGeyser(BWAPI::Unit g) const = 0;
//
//	// Should be called for each destroyed BWAPI unit u having u->getType().isMineralField() == true
//	virtual void						OnMineralDestroyed(BWAPI::Unit u) = 0;
//
//	// Should be called for each destroyed BWAPI unit u having u->getType().isSpecialBuilding() == true
//	virtual void						OnStaticBuildingDestroyed(BWAPI::Unit u) = 0;
//
//	// Returns a reference to the Areas.
//	virtual const std::vector<Area> &	Areas() const = 0;
//
//	// Returns an Area given its id.
//	virtual const Area *				GetArea(Area::id id) const = 0;
    public Area getArea(int id) {
        return this.graph.getArea(id);
    }
//
//	// If the MiniTile at w is walkable and is part of an Area, returns that Area.
//	// Otherwise, returns nullptr;
//	// Note: because of the lakes, GetNearestArea should be prefered over GetArea.
//	virtual const Area *				GetArea(BWAPI::WalkPosition w) const = 0;
    public Area getArea(WalkPosition w) {
        return this.graph.getArea(w);
    }
//
//	// If the Tile at t contains walkable sub-MiniTiles which are all part of the same Area, returns that Area.
//	// Otherwise, returns nullptr;
//	// Note: because of the lakes, GetNearestArea should be prefered over GetArea.
//	virtual const Area *				GetArea(BWAPI::TilePosition t) const = 0;
//
//	// Returns the nearest Area from w.
//	// Returns nullptr only if Areas().empty()
//	// Note: Uses a breadth first search.
//	virtual const Area *				GetNearestArea(BWAPI::WalkPosition w) const = 0;
//
//	// Returns the nearest Area from t.
//	// Returns nullptr only if Areas().empty()
//	// Note: Uses a breadth first search.
//	virtual const Area *				GetNearestArea(BWAPI::TilePosition t) const = 0;
//
//
//	// Returns a list of ChokePoints, which is intended to be the shortest walking path from 'a' to 'b'.
//	// Furthermore, if pLength != nullptr, the pointed integer is set to the corresponding length in pixels.
//	// If 'a' is not accessible from 'b', the empty Path is returned, and -1 is put in *pLength (if pLength != nullptr).
//	// If 'a' and 'b' are in the same Area, the empty Path is returned, and a.getApproxDistance(b) is put in *pLength (if pLength != nullptr).
//	// Otherwise, the function relies on ChokePoint::GetPathTo.
//	// Cf. ChokePoint::GetPathTo for more information.
//	// Note: in order to retrieve the Areas of 'a' and 'b', the function starts by calling
//	//       GetNearestArea(TilePosition(a)) and GetNearestArea(TilePosition(b)).
//	//       While this brings robustness, this could yield surprising results in the case where 'a' and/or 'b' are in the Water.
//	//       To avoid this and the potential performance penalty, just make sure GetArea(a) != nullptr and GetArea(b) != nullptr.
//	//       Then GetPath should perform very quick.
//	virtual const CPPath &				GetPath(const BWAPI::Position & a, const BWAPI::Position & b, int * pLength = nullptr) const = 0;
//
//	// Generic algorithm for breadth first search in the Map.
//	// See the several use cases in BWEM source files.
//	template<class TPosition, class Pred1, class Pred2>
//	TPosition							BreadthFirstSearch(TPosition start, Pred1 findCond, Pred2 visitCond) const;
//
//
//	// Returns the union of the geometry of all the ChokePoints. Cf. ChokePoint::Geometry()
//	virtual const std::vector<std::pair<std::pair<Area::id, Area::id>, BWAPI::WalkPosition>> & RawFrontier() const = 0;
//
//	virtual								~Map() = default;


    //TODO
    public TilePosition breadFirstSearch(TilePosition start, Pred findCond, Pred visitCond) {
        if (findCond.is(getTile(start), start)) {
            return start;
        }

        List<TilePosition> visited = new ArrayList<>();
        Queue<TilePosition> toVisit = new LinkedList<>();

        toVisit.add(start);
        visited.add(start);

        TilePosition[] deltas = {
            new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(1, -1),
            new TilePosition( 1,  0),                          new TilePosition(1,  0),
            new TilePosition(-1,  1), new TilePosition(0,  1), new TilePosition(1,  1)
        };
        while (!toVisit.isEmpty()) {
            TilePosition current = toVisit.remove();
            for (TilePosition delta : deltas) {
                TilePosition next = current.add(delta);
                if (isValid(next)) {
                    Tile nextTile = getTile(next, false);
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

//////////////////////////////////////////////////////////////////////



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
                        getMiniTile(w, false).setWalkable(false);
                    }
                }
            }
        }

        /* Mark buildable tiles (tiles are unbuildable by default). */
        for (int y = 0 ; y < getTileSize().getY() ; ++y)
        for (int x = 0 ; x < getTileSize().getX() ; ++x) {
            TilePosition t = new TilePosition(x, y);
            //TODO
//            if (broodwar.getBWMap().isBuildable(t)) { /* isBuildable is not implemented yet. */
            if (false) {
                getTile(t).setBuildable();
                /* Ensures buildable ==> walkable: */
                for (int dy = 0 ; dy < 4 ; ++dy)
                for (int dx = 0 ; dx < 4 ; ++dx) {
                    getMiniTile(new WalkPosition(t).add(new WalkPosition(dx, dy)), false).setWalkable(true);
                }
            }

            /* Set groundHeight and doodad information. */
            int bwapiGroundHeight = bw.getBWMap().getGroundHeight(t);
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
            MiniTile miniOrigin = getMiniTile(walkOrigin, false);
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
                    WalkPosition[] deltas = { new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1) };
                    for (WalkPosition delta : deltas)
                    {
                        WalkPosition next = current.add(delta);
                        if (isValid(next))
                        {
                            MiniTile Next = getMiniTile(next, false);
                            if (Next.isSeaOrLake())
                            {
                                ToSearch.add(next);
                                if (SeaExtent.size() <= BWEM.MAX_LAKE_MINI_TILES) SeaExtent.add(Next);
                                Next.setSea();
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
        for (MineralPatch patch : bw.getMineralPatches()) {
            this.minerals.add(patch);
        }
        for (VespeneGeyser geyser : bw.getVespeneGeysers()) {
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

    // Assigns MiniTile::m_altitude foar each miniTile having AltitudeMissing()
    // Cf. MiniTile::Altitude() for meaning of altitude_t.
    // Altitudes are computed using the straightforward Dijkstra's algorithm : the lower ones are computed first, starting from the seaside-miniTiles neighbours.
    // The point here is to precompute all possible altitudes for all possible tiles, and sort them.
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
                if (altitude.subtract(current.second).toInt() >= (2 * MiniTile.SIZE_IN_PIXELS)) { // optimization : once a seaside miniTile verifies this condition,
                    activeSeaSideList.remove(i--);                                                // we can throw it away as it will not generate min altitudes anymore
                } else {
                    WalkPosition[] tmpDeltas = {
                        new WalkPosition(delta.getX(), delta.getY()), new WalkPosition(-delta.getX(), delta.getY()),
                        new WalkPosition(delta.getX(), -delta.getY()), new WalkPosition(-delta.getX(), -delta.getY()),
                        new WalkPosition(delta.getY(), delta.getX()), new WalkPosition(-delta.getY(), delta.getX()),
                        new WalkPosition(delta.getY(), -delta.getX()), new WalkPosition(-delta.getY(), -delta.getX()),
                    };
                    for (WalkPosition tmpDelta : tmpDeltas) {
                        WalkPosition w = (current.first).add(tmpDelta);
                        if (isValid(w)) {
                            MiniTile miniTile = getMiniTile(w, false);
                            if (miniTile.AltitudeMissing()) {
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

    public void processBlockingNeutrals() {

    }

}
