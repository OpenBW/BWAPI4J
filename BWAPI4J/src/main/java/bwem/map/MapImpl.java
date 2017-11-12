package bwem.map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import bwem.*;
import bwem.typedef.Pred;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.MapDrawer;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.unit.Building;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import bwem.area.Area;
import bwem.area.TempAreaInfo;
import bwem.area.typedef.AreaId;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.typedef.Altitude;
import bwem.typedef.CPPath;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
import bwem.util.PairGenericAltitudeComparator;
import bwem.util.PairGenericMiniTileAltitudeComparator;
import bwem.util.Timer;
import bwem.util.Utils;

public final class MapImpl implements Map {

    private final MapPrinter m_pMapPrinter;
    private int m_size = 0;
    private TilePosition m_Size = null;
    private WalkPosition m_WalkSize = null;
    private Position m_PixelSize = null;
    private Position m_center = null;
    private List<Tile> m_Tiles = null;
    private List<MiniTile> m_MiniTiles = null;
    private MapDrawer mapDrawer;

//    public MapImpl(MapDrawer mapDrawer) {
//        this.mapDrawer = mapDrawer;
//        m_pMapPrinter = new MapPrinter();
//    }

    private Altitude m_maxAltitude;
    private MutableBoolean m_automaticPathUpdate = new MutableBoolean(false);
    private Graph m_Graph;
    private List<Mineral> m_Minerals = new ArrayList<>();
    private List<Geyser> m_Geysers = new ArrayList<>();
    private List<StaticBuilding> m_StaticBuildings = new ArrayList<>();
    private List<TilePosition> m_StartingLocations = new ArrayList<>();
    private List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> m_RawFrontier = new ArrayList<>();

    private BWMap bwMap;
	private List<MineralPatch> mineralPatches;
	private Collection<Player> players;
	private List<VespeneGeyser> vespeneGeysers;
	private Collection<Unit> units;
    
    public MapImpl(
            BWMap bwMap,
            MapDrawer mapDrawer,
            Collection<Player> players,
            List<MineralPatch> mineralPatches,
            List<VespeneGeyser> vespeneGeysers,
            Collection<Unit> units
    ) {
        m_pMapPrinter = new MapPrinter();
    	this.mapDrawer = mapDrawer;
    	this.bwMap = bwMap;
    	this.players = players;
    	this.mineralPatches = mineralPatches;
    	this.vespeneGeysers = vespeneGeysers;
    	this.units = units;
        m_Graph = new Graph(this);
    }

//    MapImpl::~MapImpl()
//    {
//        m_automaticPathUpdate = false;		// now there is no need to update the paths
//    }

    @Override
    public MapPrinter getMapPrinter() {
        return m_pMapPrinter;
    }

    @Override
    public void Initialize() {
        final Timer overallTimer = new Timer();
        final Timer timer = new Timer();

        setBasicMapData(this.bwMap.mapWidth(), this.bwMap.mapHeight(), this.bwMap.getStartPositions());
//    ///	bw << "Map::Initialize-resize: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::Initialize-resize: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        // Computes walkability, buildability and groundHeight and doodad information, using BWAPI corresponding functions
//        LoadData();
        markUnwalkableMiniTiles(this.bwMap);
        markBuildableTiles(this.bwMap);
//    ///	bw << "Map::LoadData: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::LoadData: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();
//
        DecideSeasOrLakes(BwemExt.MAX_LAKE_COUNT_IN_MINI_TILES, BwemExt.MAX_LAKE_WIDTH_IN_MINI_TILES);
//    ///	bw << "Map::DecideSeasOrLakes: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::DecideSeasOrLakes: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        InitializeNeutrals(
                this,
                this.mineralPatches, m_Minerals,
                this.vespeneGeysers, m_Geysers,
                filterNeutralPlayerUnits(this.units, this.players), m_StaticBuildings
        );
//    ///	bw << "Map::InitializeNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::InitializeNeutrals: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ComputeAltitude(WalkSize().getX(), WalkSize().getY());
//    ///	bw << "Map::ComputeAltitude: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::ComputeAltitude: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ProcessBlockingNeutrals();
//    ///	bw << "Map::ProcessBlockingNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::ProcessBlockingNeutrals: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ComputeAreas();
//    ///	bw << "Map::ComputeAreas: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::ComputeAreas: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().CreateChokePoints();
//    ///	bw << "Graph::CreateChokePoints: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::CreateChokePoints: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().ComputeChokePointDistanceMatrix();
//    ///	bw << "Graph::ComputeChokePointDistanceMatrix: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::ComputeChokePointDistanceMatrix: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().CollectInformation();
//    ///	bw << "Graph::CollectInformation: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::CollectInformation: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().CreateBases();
//    ///	bw << "Graph::CreateBases: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::CreateBases: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

//    ///	bw << "Map::Initialize: " << overallTimer.ElapsedMilliseconds() << " ms" << endl;
        System.out.println("Map::Initialize: " + overallTimer.ElapsedMilliseconds() + " ms"); timer.Reset();
    }

    @Override
    public boolean Initialized() {
        return (m_size != 0);
    }

    public Graph GetGraph() {
        return m_Graph;
    }

    @Override
    public List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> RawFrontier() {
        return m_RawFrontier;
    }

    @Override
    public MutableBoolean AutomaticPathUpdate() {
        return m_automaticPathUpdate;
    }

    @Override
    public void EnableAutomaticPathAnalysis() {
        m_automaticPathUpdate.setTrue();
    }

    @Override
    public boolean FindBasesForStartingLocations() {
        boolean atLeastOneFailed = false;
        for (TilePosition location : StartingLocations()) {
            boolean found = false;
            for (Area area : GetGraph().Areas()) {
                if (!found) {
                    for (Base base : area.Bases()) {
                        if (!found) {
                            if (BwemExt.queenWiseDist(base.Location(), location) <= BwemExt.max_tiles_between_StartingLocation_and_its_AssignedBase) {
                                base.SetStartingLocation(location);
                                found = true;
                            }
                        }
                    }
                }
            }

            if (!found) {
                atLeastOneFailed = true;
            }
        }

        return !atLeastOneFailed;
    }

    @Override
    public TilePosition Size() {
        return m_Size;
    }

    @Override
    public WalkPosition WalkSize() {
        return m_WalkSize;
    }

    @Override
    public Position PixelSize() {
        return m_PixelSize;
    }

    @Override
    public Position Center() {
        return m_center;
    }

    @Override
    public Position RandomPosition() {
        Random random = new Random();
        int x = random.nextInt(PixelSize().getX());
        int y = random.nextInt(PixelSize().getY());
        return new Position(x, y);
    }

    @Override
    public Altitude MaxAltitude() {
        return m_maxAltitude;
    }

    @Override
    public int BaseCount() {
        return GetGraph().BaseCount();
    }

    @Override
    public int ChokePointCount() {
        return GetGraph().ChokePoints().size();
    }

    @Override
    public Tile GetTile(TilePosition p, check_t checkMode) {
//        bwem_assert((checkMode == utils::check_t::no_check) || Valid(p)); utils::unused(checkMode);
        if (!((checkMode == check_t.no_check) || isValid(p))) {
            throw new IllegalArgumentException();
        }
        return (m_Tiles.get(Size().getX() * p.getY() + p.getX()));
    }

    @Override
    public Tile GetTile(TilePosition p) {
        return GetTile(p, check_t.check);
    }

    @Override
    public Tile GetTile_(TilePosition p, check_t checkMode) {
        return m_Tiles.get(Size().getX() * p.getY() + p.getX());
    }

    @Override
    public Tile GetTile_(TilePosition p) {
        return GetTile_(p, check_t.check);
    }

    @Override
    public MiniTile GetMiniTile(WalkPosition p, check_t checkMode) {
//        bwem_assert((checkMode == utils::check_t::no_check) || Valid(p));
        if (!((checkMode == check_t.no_check) || isValid(p))) {
            throw new IllegalArgumentException();
        }
        return m_MiniTiles.get(WalkSize().getX() * p.getY() + p.getX());
    }

    @Override
    public MiniTile GetMiniTile(WalkPosition p) {
        return GetMiniTile(p, check_t.check);
    }

    @Override
    public MiniTile GetMiniTile_(WalkPosition p, check_t checkMode) {
        return m_MiniTiles.get(WalkSize().getX() * p.getY() + p.getX());
    }

    @Override
    public MiniTile GetMiniTile_(WalkPosition p) {
        return GetMiniTile_(p, check_t.check);
    }

    @Override
    public List<Tile> Tiles() {
        return m_Tiles;
    }

    @Override
    public List<MiniTile> MiniTiles() {
        return m_MiniTiles;
    }

    @Override
    public boolean isValid(TilePosition p) {
        return ((0 <= p.getX()) && (p.getX() < Size().getX()) && (0 <= p.getY()) && (p.getY() < Size().getY()));
    }

    @Override
    public boolean isValid(WalkPosition p) {
        return ((0 <= p.getX()) && (p.getX() < WalkSize().getX()) && (0 <= p.getY()) && (p.getY() < WalkSize().getY()));
    }

    @Override
    public boolean isValid(Position p) {
        return ((0 <= p.getX()) && (p.getX() < PixelSize().getX()) && (0 <= p.getY()) && (p.getY() < PixelSize().getY()));
    }

    private int[] crop(int x, int y, int max_x, int max_y) {
        int ret_x = x;
        int ret_y = y;

        if (ret_x < 0) ret_x = 0;
        else if (ret_x >= max_x) ret_x = max_x - 1;

        if (ret_y < 0) ret_y = 0;
        else if (ret_y >= max_y) ret_y = max_y - 1;

        int[] ret = {ret_x, ret_y};

        return ret;
    }

    @Override
    public TilePosition Crop(TilePosition p) {
        int[] ret = crop(p.getX(), p.getY(), Size().getX(), Size().getY());

        return new TilePosition(ret[0], ret[1]);
    }

    @Override
    public WalkPosition Crop(WalkPosition p) {
        int[] ret = crop(p.getX(), p.getY(), WalkSize().getX(), WalkSize().getY());

        return new WalkPosition(ret[0], ret[1]);
    }

    @Override
    public Position Crop(Position p) {
        int[] ret = crop(p.getX(), p.getY(), PixelSize().getX(), PixelSize().getY());

        return new Position(ret[0], ret[1]);
    }

    @Override
    public List<TilePosition> StartingLocations() {
        return m_StartingLocations;
    }

    @Override
    public List<Mineral> Minerals() {
        return m_Minerals;
    }

    @Override
    public List<Geyser> Geysers() {
        return m_Geysers;
    }

    @Override
    public List<StaticBuilding> StaticBuildings() {
        return m_StaticBuildings;
    }

    @Override
    public Mineral GetMineral(Unit u) {
        for (Mineral mineral : m_Minerals) {
            if (mineral.Unit().equals(u)) {
                return mineral;
            }
        }
        return null;
    }

    @Override
    public Geyser GetGeyser(Unit g) {
        for (Geyser geyser : m_Geysers) {
            if (geyser.Unit().equals(g)) {
                return geyser;
            }
        }
        return null;
    }

    @Override
    public void OnUnitDestroyed(Unit u) {
        if (u instanceof MineralPatch) {
            OnMineralDestroyed(u);
        } else {
            try {
                OnStaticBuildingDestroyed(u);
            } catch (Exception ex) {
                //TODO: Handle this exception appropriately.
                /**
                 * An exception WILL be thrown if the unit is not in
                 * the "Map.m_StaticBuildings" list.
                 * Just ignore the exception.
                 */
            }
        }
    }

    @Override
    public void OnMineralDestroyed(Unit u) {
        for (int i = 0; i < m_Minerals.size(); ++i) {
            Mineral mineral = m_Minerals.get(i);
            if (mineral.Unit().equals(u)) {
                OnMineralDestroyed(mineral);
                mineral.simulateCPPObjectDestructor(); /* IMPORTANT! These actions are performed in the "~Neutral" dtor in BWEM 1.4.1 C++. */
                m_Minerals.remove(i--);
                return;
            }
        }
//        bwem_assert(iMineral != m_Minerals.end());
        throw new IllegalArgumentException("Unit is not a Mineral");
    }

    /**
     * This method could be placed in {@link #OnMineralDestroyed(org.openbw.bwapi4j.unit.Unit)}.
     * This remains as a separate method for portability consistency.
     */
    private void OnMineralDestroyed(Mineral pMineral) {
        for (Area area : GetGraph().Areas()) {
            area.OnMineralDestroyed(pMineral);
        }
    }

    @Override
    public void OnStaticBuildingDestroyed(Unit u) {
        for (int i = 0; i < m_StaticBuildings.size(); ++i) {
            StaticBuilding building = m_StaticBuildings.get(i);
            if (building.Unit().equals(u)) {
                building.simulateCPPObjectDestructor(); /* IMPORTANT! These actions are performed in the "~Neutral" dtor in BWEM 1.4.1 C++. */
                m_StaticBuildings.remove(i--);
                return;
            }
        }
//        bwem_assert(iStaticBuilding != m_StaticBuildings.end());
        throw new IllegalArgumentException("Unit is not a StaticBuilding");
    }

    public void OnBlockingNeutralDestroyed(Neutral pBlocking) {
//        bwem_assert(pBlocking && pBlocking->Blocking());
        if (!(pBlocking != null && pBlocking.Blocking())) {
            throw new IllegalArgumentException();
        }

        for (Area pArea : pBlocking.BlockedAreas())
        for (ChokePoint cp : pArea.ChokePoints()) {
            cp.OnBlockingNeutralDestroyed(pBlocking);
        }

        if (GetTile(pBlocking.TopLeft()).GetNeutral() != null) { // there remains some blocking Neutrals at the same location
            return;
        }

        // Unblock the miniTiles of pBlocking:
        AreaId newId = new AreaId(pBlocking.BlockedAreas().iterator().next().Id());
        WalkPosition pBlockingW = pBlocking.Size().toPosition().toWalkPosition();
        for (int dy = 0; dy < pBlockingW.getY(); ++dy)
        for (int dx = 0; dx < pBlockingW.getX(); ++dx) {
            MiniTile miniTile = GetMiniTile_((pBlocking.TopLeft().toPosition().toWalkPosition()).add(new WalkPosition(dx, dy)));
            if (miniTile.Walkable()) {
                miniTile.ReplaceBlockedAreaId(newId);
            }
        }

        // Unblock the Tiles of pBlocking:
        for (int dy = 0; dy < pBlocking.Size().getY(); ++dy)
        for (int dx = 0; dx < pBlocking.Size().getX(); ++dx) {
            GetTile_(pBlocking.TopLeft().add(new TilePosition(dx, dy))).ResetAreaId();
            SetAreaIdInTile(pBlocking.TopLeft().add(new TilePosition(dx, dy)));
        }

        if (AutomaticPathUpdate().booleanValue()) {
            GetGraph().ComputeChokePointDistanceMatrix();
        }
    }

    @Override
    public List<Area> Areas() {
        return GetGraph().Areas();
    }

    // Returns an Area given its id. Range = 1..Size()
    @Override
    public Area GetArea(AreaId id) {
        return m_Graph.GetArea(id);
    }

    @Override
    public Area GetArea(WalkPosition w) {
        return m_Graph.GetArea(w);
    }

    @Override
    public Area GetArea(TilePosition t) {
        return m_Graph.GetArea(t);
    }

    @Override
    public Area GetNearestArea(WalkPosition w) {
        return m_Graph.GetNearestArea(w);
    }

    @Override
    public Area GetNearestArea(TilePosition t) {
        return m_Graph.GetNearestArea(t);
    }

    @Override
    public CPPath GetPath(Position a, Position b, MutableInt pLength) {
        return m_Graph.GetPath(a, b, pLength);
    }

    @Override
    public CPPath GetPath(Position a, Position b) {
        return GetPath(a, b, null);
    }

    public TilePosition BreadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond, boolean connect8) {
        if (findCond.isTrue(GetTile(start), start, this)) {
            return start;
        }

        List<TilePosition> Visited = new ArrayList<>();
        Queue<TilePosition> ToVisit = new LinkedList<>();

        ToVisit.add(start);
        Visited.add(start);

        TilePosition[] dir8 = {
                new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(1, -1),
                new TilePosition(-1,  0),                          new TilePosition(1,  0),
                new TilePosition(-1,  1), new TilePosition(0,  1), new TilePosition(1,  1)
        };
        TilePosition[] dir4 = {new TilePosition(0, -1), new TilePosition(-1, 0), new TilePosition(+1, 0), new TilePosition(0, +1)};
        TilePosition[] directions = connect8 ? dir8 : dir4;

        while (!ToVisit.isEmpty()) {
            TilePosition current = ToVisit.remove();
            for (TilePosition delta : directions) {
                TilePosition next = current.add(delta);
                if (isValid(next)) {
                    Tile nextTile = GetTile(next, check_t.no_check);
                    if (findCond.isTrue(nextTile, next, this)) {
                        return next;
                    }
                    if (visitCond.isTrue(nextTile, next, this) && !Visited.contains(next)) {
                        ToVisit.add(next);
                        Visited.add(next);
                    }
                }
            }
        }

        //TODO: Are we supposed to return start or not?
//        bwem_assert(false);
        throw new IllegalStateException();
//        return start;
    }

    public TilePosition BreadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond) {
        return BreadthFirstSearch(start, findCond, visitCond, true);
    }

    public WalkPosition BreadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond, boolean connect8) {
        if (findCond.isTrue(GetMiniTile(start), start, this)) {
            return start;
        }

        List<WalkPosition> Visited = new ArrayList<>();
        Queue<WalkPosition> ToVisit = new LinkedList<>();

        ToVisit.add(start);
        Visited.add(start);

        WalkPosition[] dir8 = {
                new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(1, -1),
                new WalkPosition(-1,  0),                          new WalkPosition(1,  0),
                new WalkPosition(-1,  1), new WalkPosition(0,  1), new WalkPosition(1,  1)
        };
        WalkPosition[] dir4 = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
        WalkPosition[] directions = connect8 ? dir8 : dir4;

        while (!ToVisit.isEmpty()) {
            WalkPosition current = ToVisit.remove();
            for (WalkPosition delta : directions) {
                WalkPosition next = current.add(delta);
                if (isValid(next)) {
                    MiniTile Next = GetMiniTile(next, check_t.no_check);
                    if (findCond.isTrue(Next, next, this)) {
                        return next;
                    }
                    if (visitCond.isTrue(Next, next, this) && !Visited.contains(next)) {
                        ToVisit.add(next);
                        Visited.add(next);
                    }
                }
            }
        }

        //TODO: Are we supposed to return start or not?
//        bwem_assert(false);
        throw new IllegalStateException();
//        return start;
    }

    public WalkPosition BreadthFirstSearch(WalkPosition start, Pred findCond, Pred visitCond) {
        return BreadthFirstSearch(start, findCond, visitCond, true);
    }

    public void drawDiagonalCrossMap(Position topLeft, Position bottomRight, Color col) {
        this.mapDrawer.drawLineMap(topLeft, bottomRight, col);
        this.mapDrawer.drawLineMap(new Position(bottomRight.getX(), topLeft.getY()), new Position(topLeft.getX(), bottomRight.getY()), col);
    }

    private List<PlayerUnit> filterPlayerUnits(final Collection<Unit> units, final Player player) {
//        return this.units.stream().filter(u -> u instanceof PlayerUnit
//                && ((PlayerUnit)u).getPlayer().equals(player)).map(u -> (PlayerUnit)u).collect(Collectors.toList());
        final List<PlayerUnit> ret = new ArrayList<>();
        for (final Unit u : units) {
            if (u instanceof PlayerUnit) {
                final PlayerUnit playerUnit = (PlayerUnit) u;
                if (playerUnit.getPlayer().equals(player)) {
                    ret.add(playerUnit);
                }
            }
        }
        return ret;
    }

    private List<PlayerUnit> filterNeutralPlayerUnits(final Collection<Unit> units, final Collection<Player> players) {
        final List<PlayerUnit> ret = new ArrayList<>();
        for (final Player player : players) {
            if (player.isNeutral()) {
                for (final PlayerUnit u : filterPlayerUnits(units, player)) {
                    ret.add(u);
                }
            }
        }
        return ret;
    }

    private void setBasicMapData(final int mapTileWidth, final int mapTileHeight, final List<TilePosition> startPositions) {
        m_Size = new TilePosition(mapTileWidth, mapTileHeight);
        m_WalkSize = Size().toPosition().toWalkPosition();
        m_PixelSize = Size().toPosition();

        m_size = Size().getX() * Size().getY();
        m_Tiles = new ArrayList<>();
        for (int i = 0; i < m_size; ++i) {
            m_Tiles.add(new Tile());
        }

        final int walkSize = WalkSize().getX() * WalkSize().getY();
        m_MiniTiles = new ArrayList<>();
        for (int i = 0; i < walkSize; ++i) {
            m_MiniTiles.add(new MiniTile());
        }

        m_center = new Position(PixelSize().getX() / 2, PixelSize().getY() / 2);

        m_StartingLocations = new ArrayList<>();
        for (final TilePosition t: startPositions) {
            m_StartingLocations.add(t);
        }
    }



    //----------------------------------------------------------------------
    // LoadData
    //----------------------------------------------------------------------

    private void markUnwalkableMiniTiles(final BWMap bwMap) {
        // Mark unwalkable minitiles (minitiles are walkable by default)
        for (int y = 0; y < WalkSize().getY(); ++y)
        for (int x = 0; x < WalkSize().getX(); ++x) {
            if (!bwMap.isWalkable(x, y)) {
                // For each unwalkable minitile, we also mark its 8 neighbours as not walkable.
                // According to some tests, this prevents from wrongly pretending one Marine can go by some thin path.
                for (int dy = -1; dy <= +1; ++dy)
                for (int dx = -1; dx <= +1; ++dx) {
                    final WalkPosition w = new WalkPosition(x + dx, y + dy);
                    if (isValid(w)) {
                        GetMiniTile_(w, check_t.no_check).SetWalkable(false);
                    }
                }
            }
        }
    }

    private void markBuildableTiles(final BWMap bwMap) {
        // Mark buildable tiles (tiles are unbuildable by default)
        for (int y = 0; y < Size().getY(); ++y)
        for (int x = 0; x < Size().getX(); ++x) {
            final TilePosition t = new TilePosition(x, y);
            if (bwMap.isBuildable(t, false)) {
                GetTile_(t).SetBuildable();

                // Ensures buildable ==> walkable:
                for (int dy = 0; dy < 4; ++dy)
                for (int dx = 0; dx < 4; ++dx) {
                    GetMiniTile_(new WalkPosition(t).add(new WalkPosition(dx, dy)), check_t.no_check).SetWalkable(true);
                }
            }

            // Add groundHeight and doodad information:
            final int bwapiGroundHeight = bwMap.getGroundHeight(t);
            GetTile_(t).SetGroundHeight(bwapiGroundHeight / 2);
            if (bwapiGroundHeight % 2 != 0) {
                GetTile_(t).SetDoodad();
            }
        }
    }

    //----------------------------------------------------------------------



    private void DecideSeasOrLakes(final int maxLakeCountInMiniTiles, final int maxLakeWidthInMiniTiles) {
    	for (int y = 0; y < WalkSize().getY(); ++y)
    	for (int x = 0; x < WalkSize().getX(); ++x) {
    		final WalkPosition origin = new WalkPosition(x, y);
    		final MiniTile Origin = GetMiniTile_(origin, check_t.no_check);
    		if (Origin.SeaOrLake()) {
    			List<WalkPosition> ToSearch = new ArrayList<>();
                ToSearch.add(origin);
    			List<MiniTile> SeaExtent = new ArrayList<>();
                Origin.SetSea();
                SeaExtent.add(Origin);
    			WalkPosition topLeft = new WalkPosition(origin.getX(), origin.getY());
    			WalkPosition bottomRight = new WalkPosition(origin.getX(), origin.getY());
    			while (!ToSearch.isEmpty()) {
    				final WalkPosition current = ToSearch.get(ToSearch.size() - 1);
    				if (current.getX() < topLeft.getX()) topLeft = new WalkPosition(current.getX(), topLeft.getY());
    				if (current.getY() < topLeft.getY()) topLeft = new WalkPosition(topLeft.getX(), current.getY());
    				if (current.getX() > bottomRight.getX()) bottomRight = new WalkPosition(current.getX(), bottomRight.getY());
    				if (current.getY() > bottomRight.getY()) bottomRight = new WalkPosition(bottomRight.getX(), current.getY());

    				ToSearch.remove(ToSearch.size() - 1);
                    final WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
    				for (final WalkPosition delta : deltas) {
    					final WalkPosition next = current.add(delta);
    					if (isValid(next)) {
    						final MiniTile Next = GetMiniTile_(next, check_t.no_check);
    						if (Next.SeaOrLake()) {
    							ToSearch.add(next);
    							if (SeaExtent.size() <= maxLakeCountInMiniTiles) {
                                    SeaExtent.add(Next);
                                }
    							Next.SetSea();
    						}
    					}
    				}
    			}

    			if ((SeaExtent.size() <= maxLakeCountInMiniTiles) &&
    				(bottomRight.getX() - topLeft.getX() <= maxLakeWidthInMiniTiles) &&
    				(bottomRight.getY() - topLeft.getY() <= maxLakeWidthInMiniTiles) &&
    				(topLeft.getX() >= 2) && (topLeft.getY() >= 2) && (bottomRight.getX() < WalkSize().getX() - 2) && (bottomRight.getY() < WalkSize().getY() - 2)) {
    				for (final MiniTile pSea : SeaExtent) {
    					pSea.SetLake();
                    }
                }
    		}
    	}
    }

    private void InitializeNeutrals(
            final MapImpl map,
            final List<MineralPatch> mineralPatches, final List<Mineral> minerals,
            final List<VespeneGeyser> vespeneGeysers, final List<Geyser> geysers,
            final List<PlayerUnit> neutralUnits, final List<StaticBuilding> staticBuildings
    ) {
        for (final MineralPatch mineralPatch : mineralPatches) {
            minerals.add(new Mineral(mineralPatch, map));
        }
        for (final VespeneGeyser vespeneGeyser : vespeneGeysers) {
            geysers.add(new Geyser(vespeneGeyser, map));
        }
        for (final Unit neutralUnit : neutralUnits) {
//                if ((neutralUnit instanceof Building) && !(neutralUnit instanceof MineralPatch || neutralUnit instanceof VespeneGeyser)) {
            if (neutralUnit instanceof Building) {
                staticBuildings.add(new StaticBuilding(neutralUnit, map));
            }
        }

        //TODO: Add "Special_Pit_Door" and "Special_Right_Pit_Door" to static buildings list? See mapImpl.cpp:238.
//				if (n->getType() == Special_Pit_Door)
//					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));
//				if (n->getType() == Special_Right_Pit_Door)
//					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));
    }

	private List<MutablePair<WalkPosition, Altitude>> getSortedDeltasByAscendingAltitude(int mapWalkTileWidth, int mapWalkTileHeight, int altitudeScale) {
    	
    	final int range = Math.max(mapWalkTileWidth, mapWalkTileHeight) / 2 + 3;
    	
    	List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude = new ArrayList<>();
    	
    	for (int dy = 0; dy <= range; ++dy) {
    		
            for (int dx = dy; dx <= range; ++dx) { // Only consider 1/8 of possible deltas. Other ones obtained by symmetry.
            	
                if (dx != 0 || dy != 0) {
                    deltasByAscendingAltitude.add(new MutablePair<>(new WalkPosition(dx, dy), 
                    		new Altitude((int) (Double.valueOf("0.5") + (Utils.norm(dx, dy) * (double) altitudeScale)))));
                }
            }
    	}
        Collections.sort(deltasByAscendingAltitude, new PairGenericAltitudeComparator<>());
        
        return deltasByAscendingAltitude;
    }
    
    /**
     * Fill in ActiveSeaSideList, which basically contains all the seaside miniTiles (from which altitudes are to be computed)
     * It also includes extra border-miniTiles which are considered as seaside miniTiles too.
     * @return list of active sea sides
     */
    private List<MutablePair<WalkPosition, Altitude>> getActiveSeaSides(int mapWalkTileWidth, int mapWalkTileHeight) {
    	
        List<MutablePair<WalkPosition, Altitude>> activeSeaSides = new ArrayList<>();

        for (int y = -1; y <= mapWalkTileHeight; ++y) {
        	
	        for (int x = -1; x <= mapWalkTileWidth; ++x) {
	        	
	            WalkPosition walkPosition = new WalkPosition(x, y);
	            if (!isValid(walkPosition) || BwemExt.seaSide(walkPosition, this)) {
	            	
	                activeSeaSides.add(new MutablePair<>(walkPosition, new Altitude(0)));
	            }
	        }
        }
        
        return activeSeaSides;
    }
    
    /**
     * Dijkstra's algorithm to set altitude for mini tiles.
     * @param deltasByAscendingAltitude
     * @param activeSeaSides
     * @param altitudeScale
     */
    private void setAltitudes(List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude, List<MutablePair<WalkPosition, Altitude>> activeSeaSides, int altitudeScale) {
    	
        for (MutablePair<WalkPosition, Altitude> delta_altitude : deltasByAscendingAltitude) {
        	
            final WalkPosition d = new WalkPosition(delta_altitude.left.getX(), delta_altitude.left.getY());
            final Altitude altitude = new Altitude(delta_altitude.right);
            
            for (int i = 0; i < activeSeaSides.size(); ++i) {
            	
                MutablePair<WalkPosition, Altitude> Current = activeSeaSides.get(i);
                if (altitude.intValue() - Current.right.intValue() >= 2 * altitudeScale) { 
                	
                	// optimization : once a seaside miniTile verifies this condition,
                	// we can throw it away as it will not generate min altitudes anymore
                	BwemExt.fast_erase(activeSeaSides, i--);
                } else {
                	
                    WalkPosition[] deltas = {new WalkPosition(d.getX(), d.getY()), new WalkPosition(-d.getX(), d.getY()), new WalkPosition(d.getX(), -d.getY()), new WalkPosition(-d.getX(), -d.getY()),
                                             new WalkPosition(d.getY(), d.getX()), new WalkPosition(-d.getY(), d.getX()), new WalkPosition(d.getY(), -d.getX()), new WalkPosition(-d.getY(), -d.getX())};
                    
                    for (WalkPosition delta : deltas) {
                    	
                        WalkPosition walkPosition = Current.left.add(delta);
                        
                        if (isValid(walkPosition)) {
                        	
                            MiniTile miniTile = GetMiniTile_(walkPosition, check_t.no_check);
                            
                            if (miniTile.AltitudeMissing()) {
                                m_maxAltitude = new Altitude(altitude);
                                Current.right = new Altitude(altitude);
                                miniTile.SetAltitude(altitude);
                            }
                        }
                    }
                }
            }
        }
    }

    // Assigns MiniTile::m_altitude foar each miniTile having AltitudeMissing()
    // Cf. MiniTile::Altitude() for meaning of altitude_t.
    // Altitudes are computed using the straightforward Dijkstra's algorithm : the lower ones are computed first, starting from the seaside-miniTiles neighbours.
    // The point here is to precompute all possible altitudes for all possible tiles, and sort them.
    private void ComputeAltitude(int mapWalkTileWidth, int mapWalkTileHeight) {
    	// 8 provides a pixel definition for altitude_t, since altitudes are computed from miniTiles which are 8x8 pixels
    	final int altitudeScale = 8;
    	
    	List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude = getSortedDeltasByAscendingAltitude(mapWalkTileWidth, mapWalkTileHeight, altitudeScale);
    	
    	List<MutablePair<WalkPosition, Altitude>> activeSeaSides = getActiveSeaSides(mapWalkTileWidth, mapWalkTileHeight);

    	setAltitudes(deltasByAscendingAltitude, activeSeaSides, altitudeScale);
    }

    private void ProcessBlockingNeutrals() {
        List<Neutral> Candidates = new ArrayList<>();
        for (StaticBuilding s : StaticBuildings()) {
            Candidates.add(s);
        }
        for (Mineral m : Minerals()) {
            Candidates.add(m);
        }

        for (Neutral pCandidate : Candidates) {
            if (pCandidate.NextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top one
                // 1)  Retreave the Border: the outer border of pCandidate
                List<WalkPosition> Border = BwemExt.outerMiniTileBorder(pCandidate.TopLeft(), pCandidate.Size());
                for (int i = 0; i < Border.size(); ++i) {
                    WalkPosition w = Border.get(i);
                    if (!isValid(w) || !GetMiniTile(w, check_t.no_check).Walkable() ||
                            GetTile(w.toPosition().toTilePosition(), check_t.no_check).GetNeutral() != null) {
                        Border.remove(i--);
                    }
                }

                // 2)  Find the doors in Border: one door for each connected set of walkable, neighbouring miniTiles.
                //     The searched connected miniTiles all have to be next to some lake or some static building, though they can't be part of one.
                List<WalkPosition> Doors = new ArrayList<>();
                while (!Border.isEmpty()) {
                    WalkPosition door = Border.remove(Border.size() - 1);
                    Doors.add(door);
                    List<WalkPosition> ToVisit = new ArrayList<>();
                    ToVisit.add(door);
                    List<WalkPosition> Visited = new ArrayList<>();
                    Visited.add(door);
                    while (!ToVisit.isEmpty()) {
                        WalkPosition current = ToVisit.remove(ToVisit.size() - 1);
                        WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                        for (WalkPosition delta : deltas) {
                            WalkPosition next = current.add(delta);
                            if (isValid(next) && !Visited.contains(next)) {
                                if (GetMiniTile(next, check_t.no_check).Walkable()) {
                                    if (GetTile(next.toPosition().toTilePosition(), check_t.no_check).GetNeutral() == null) {
                                        if (BwemExt.adjoins8SomeLakeOrNeutral(next, this)) {
                                            ToVisit.add(next);
                                            Visited.add(next);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < Border.size(); ++i) {
                        WalkPosition w = Border.get(i);
                        if (Visited.contains(w)) {
                            Border.remove(i--);
                        }
                    }
                }

                // 3)  If at least 2 doors, find the true doors in Border: a true door is a door that gives onto an area big enough
                List<WalkPosition> TrueDoors = new ArrayList<>();
                if (Doors.size() >= 2)
                    for (WalkPosition door : Doors) {
                        List<WalkPosition> ToVisit = new ArrayList<>();
                        ToVisit.add(door);
                        List<WalkPosition> Visited = new ArrayList<>();
                        Visited.add(door);
                        final int limit = (pCandidate instanceof StaticBuilding) ? 10 : 400;
                        while (!ToVisit.isEmpty() && (Visited.size() < limit)) {
                            WalkPosition current = ToVisit.remove(ToVisit.size() - 1);
                            WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                            for (WalkPosition delta : deltas) {
                                WalkPosition next = current.add(delta);
                                if (isValid(next) && !Visited.contains(next)) {
                                    if (GetMiniTile(next, check_t.no_check).Walkable()) {
                                        if (GetTile(next.toPosition().toTilePosition(), check_t.no_check).GetNeutral() == null) {
                                            ToVisit.add(next);
                                            Visited.add(next);
                                        }
                                    }
                                }
                            }
                        }
                        if (Visited.size() >= limit) {
                            TrueDoors.add(door);
                        }
                    }

                // 4)  If at least 2 true doors, pCandidate is a blocking static building
                if (TrueDoors.size() >= 2) {
                    // Marks pCandidate (and any Neutral stacked with it) as blocking.
                    for (Neutral pNeutral = GetTile(pCandidate.TopLeft()).GetNeutral(); pNeutral != null; pNeutral = pNeutral.NextStacked()) {
                        pNeutral.SetBlocking(TrueDoors);
                    }

                    // Marks all the miniTiles of pCandidate as blocked.
                    // This way, areas at TrueDoors won't merge together.
                    WalkPosition pCandidateW = pCandidate.Size().toPosition().toWalkPosition();
                    for (int dy = 0; dy < pCandidateW.getY(); ++dy)
                    for (int dx = 0; dx < pCandidateW.getX(); ++dx) {
                        MiniTile miniTile = GetMiniTile_((pCandidate.TopLeft().toPosition().toWalkPosition()).add(new WalkPosition(dx, dy)));
                        if (miniTile.Walkable()) {
                            miniTile.SetBlocked();
                        }
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
    private void ComputeAreas() {
        List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude = SortMiniTiles();

        List<TempAreaInfo> TempAreaList = ComputeTempAreas(MiniTilesByDescendingAltitude);

        CreateAreas(TempAreaList);

        SetAreaIdInTiles();
    }

    private List<MutablePair<WalkPosition, MiniTile>> SortMiniTiles() {
        List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude = new ArrayList<>();
        for (int y = 0; y < WalkSize().getY(); ++y)
        for (int x = 0; x < WalkSize().getX(); ++x) {
            WalkPosition w = new WalkPosition(x, y);
            MiniTile miniTile = GetMiniTile_(w, check_t.no_check);
            if (miniTile.AreaIdMissing()) {
                MiniTilesByDescendingAltitude.add(new MutablePair<>(w, miniTile));
            }
        }

        Collections.sort(MiniTilesByDescendingAltitude, new PairGenericMiniTileAltitudeComparator(PairGenericMiniTileAltitudeComparator.Order.DESCENDING));

        return MiniTilesByDescendingAltitude;
    }

    private List<TempAreaInfo> ComputeTempAreas(List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude) {
        List<TempAreaInfo> TempAreaList = new ArrayList<>();
        TempAreaList.add(new TempAreaInfo()); // TempAreaList[0] left unused, as AreaIds are > 0
        for (MutablePair<WalkPosition, MiniTile> Current : MiniTilesByDescendingAltitude) {
            final WalkPosition pos = new WalkPosition(Current.left.getX(), Current.left.getY());
            MiniTile cur = Current.right;

            MutablePair<AreaId, AreaId> neighboringAreas = findNeighboringAreas(pos, this);
            if (neighboringAreas.left == null) { // no neighboring area : creates of a new area
                TempAreaList.add(new TempAreaInfo(new AreaId(TempAreaList.size()), cur, pos));
            } else if (neighboringAreas.right == null) { // one neighboring area : adds cur to the existing area
                TempAreaList.get(neighboringAreas.left.intValue()).Add(cur);
            } else { // two neighboring areas : adds cur to one of them  &  possible merging
                AreaId smaller = new AreaId(neighboringAreas.left);
                AreaId bigger = new AreaId(neighboringAreas.right);
                if (TempAreaList.get(smaller.intValue()).Size() > TempAreaList.get(bigger.intValue()).Size()) {
                    AreaId smaller_tmp = new AreaId(smaller);
                    smaller = new AreaId(bigger);
                    bigger = new AreaId(smaller_tmp);
                }

                // Condition for the neighboring areas to merge:
//                any_of(StartingLocations().begin(), StartingLocations().end(), [&pos](const TilePosition & startingLoc)
//                    { return dist(TilePosition(pos), startingLoc + TilePosition(2, 1)) <= 3;})
                boolean cpp_algorithm_std_any_of = false;
                for (TilePosition startingLoc : StartingLocations()) {
                    if (Double.compare(BwemExt.dist(pos.toPosition().toTilePosition(), startingLoc.add(new TilePosition(2, 1))), Double.valueOf("3")) <= 0) {
                        cpp_algorithm_std_any_of = true;
                        break;
                    }
                }
                if ((TempAreaList.get(smaller.intValue()).Size() < 80)
                        || (TempAreaList.get(smaller.intValue()).HighestAltitude().intValue() < 80)
                        || Double.compare((double) cur.Altitude().intValue() / (double) TempAreaList.get(bigger.intValue()).HighestAltitude().intValue(), Double.valueOf("0.90")) >= 0
                        || Double.compare((double) cur.Altitude().intValue() / (double) TempAreaList.get(smaller.intValue()).HighestAltitude().intValue(), Double.valueOf("0.90")) >= 0
                        || cpp_algorithm_std_any_of) {
                    // adds cur to the absorbing area:
                    TempAreaList.get(bigger.intValue()).Add(cur);

                    // merges the two neighboring areas:
                    ReplaceAreaIds(TempAreaList.get(smaller.intValue()).Top(), bigger);
                    TempAreaList.get(bigger.intValue()).Merge(TempAreaList.get(smaller.intValue()));
                } else { // no merge : cur starts or continues the frontier between the two neighboring areas
                    // adds cur to the chosen Area:
                    TempAreaList.get(chooseNeighboringArea(smaller, bigger).intValue()).Add(cur);
                    m_RawFrontier.add(new MutablePair<>(neighboringAreas, pos));
                }
            }
        }

        // Remove from the frontier obsolete positions
        for (int i = 0; i < m_RawFrontier.size(); ++i) {
            MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f = m_RawFrontier.get(i);
            if (f.left.left.equals(f.left.right)) {
                m_RawFrontier.remove(i--);
            }
        }

        return TempAreaList;
    }

    private void ReplaceAreaIds(WalkPosition p, AreaId newAreaId) {
        MiniTile Origin = GetMiniTile_(p, check_t.no_check);
        AreaId oldAreaId = Origin.AreaId();
        Origin.ReplaceAreaId(newAreaId);

        List<WalkPosition> ToSearch = new ArrayList<>();
        ToSearch.add(p);
        while (!ToSearch.isEmpty()) {
            WalkPosition current = ToSearch.remove(ToSearch.size() - 1);
            WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
            for (WalkPosition delta : deltas) {
                WalkPosition next = current.add(delta);
                if (isValid(next)) {
                    MiniTile Next = GetMiniTile_(next, check_t.no_check);
                    if (Next.AreaId().equals(oldAreaId)) {
                        ToSearch.add(next);
                        Next.ReplaceAreaId(newAreaId);
                    }
                }
            }
        }

        // also replaces references of oldAreaId by newAreaId in m_RawFrontier:
        if (newAreaId.intValue() > 0) {
            for (MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f : m_RawFrontier) {
                if (f.left.left.equals(oldAreaId)) {
                    f.left.left = new AreaId(newAreaId);
                }
                if (f.left.right.equals(oldAreaId)) {
                    f.left.right = new AreaId(newAreaId);
                }
            }
        }
    }

    // Initializes m_Graph with the valid and big enough areas in TempAreaList.
    private void CreateAreas(List<TempAreaInfo> TempAreaList) {
        List<MutablePair<WalkPosition, Integer>> AreasList = new ArrayList<>();

        int newAreaId = 1;
        int newTinyAreaId = -2;

        for (TempAreaInfo TempArea : TempAreaList) {
            if (TempArea.Valid()) {
                if (TempArea.Size() >= BwemExt.area_min_miniTiles) {
//                    bwem_assert(newAreaId <= TempArea.Id());
                    if (!(newAreaId <= TempArea.Id().intValue())) {
                        throw new IllegalStateException();
                    }
                    if (newAreaId != TempArea.Id().intValue()) {
                        ReplaceAreaIds(TempArea.Top(), new AreaId(newAreaId));
                    }

                    AreasList.add(new MutablePair<>(TempArea.Top(), TempArea.Size()));
                    ++newAreaId;
                } else {
                    ReplaceAreaIds(TempArea.Top(), new AreaId(newTinyAreaId));
                    --newTinyAreaId;
                }
            }
        }

        GetGraph().CreateAreas(AreasList);
    }

    private void SetAreaIdInTile(TilePosition t) {
        Tile tile = GetTile_(t);
//        bwem_assert(tile.AreaId() == 0);	// initialized to 0
        if (!(tile.AreaId().intValue() == 0)) { // initialized to 0
            throw new IllegalStateException();
        }

        for (int dy = 0; dy < 4; ++dy)
        for (int dx = 0; dx < 4; ++dx) {
            AreaId id = GetMiniTile((t.toPosition().toWalkPosition()).add(new WalkPosition(dx, dy)), check_t.no_check).AreaId();
            if (id.intValue() != 0) {
                if (tile.AreaId().intValue() == 0) {
                    tile.SetAreaId(id);
                } else if (!tile.AreaId().equals(id)) {
                    tile.SetAreaId(new AreaId(-1));
                    return;
                }
            }
        }
    }

    private void SetAltitudeInTile(TilePosition t) {
        Altitude minAltitude = new Altitude(Integer.MAX_VALUE);

        for (int dy = 0; dy < 4; ++dy)
        for (int dx = 0; dx < 4; ++dx) {
            Altitude altitude = new Altitude(GetMiniTile((t.toPosition()).toWalkPosition().add(new WalkPosition(dx, dy)), check_t.no_check).Altitude());
            if (altitude.intValue() < minAltitude.intValue()) {
                minAltitude = new Altitude(altitude);
            }
        }

        GetTile_(t).SetMinAltitude(minAltitude);
    }

    private void SetAreaIdInTiles() {
        for (int y = 0; y < Size().getY(); ++y)
        for (int x = 0; x < Size().getX(); ++x) {
            TilePosition t = new TilePosition(x, y);
            SetAreaIdInTile(t);
            SetAltitudeInTile(t);
        }
    }

    public static MutablePair<AreaId, AreaId> findNeighboringAreas(WalkPosition p, MapImpl pMap) {
        MutablePair<AreaId, AreaId> result = new MutablePair<>(null, null);

        WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
        for (WalkPosition delta : deltas) {
            if (pMap.isValid(p.add(delta))) {
                AreaId areaId = pMap.GetMiniTile(p.add(delta), check_t.no_check).AreaId();
                if (areaId.intValue() > 0) {
                    if (result.left == null) {
                        result.left = new AreaId(areaId);
                    } else if (!result.left.equals(areaId)) {
                        if (result.right == null || ((areaId.intValue() < result.right.intValue()))) {
                            result.right = new AreaId(areaId);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static AbstractMap<MutablePair<AreaId, AreaId>, Integer> map_AreaPair_counter = new ConcurrentHashMap<>();
    public static AreaId chooseNeighboringArea(AreaId a, AreaId b) {
        if (a.intValue() > b.intValue()) {
            AreaId a_tmp = new AreaId(a);
            a = new AreaId(b);
            b = new AreaId(a_tmp);
        }

        MutablePair<AreaId, AreaId> key = new MutablePair<>(a, b);
        Integer val = map_AreaPair_counter.get(key);
        if (val == null) {
            val = 0;
        }
        map_AreaPair_counter.put(key, val + 1);

        return (val % 2 == 0) ? a : b;
    }

}
