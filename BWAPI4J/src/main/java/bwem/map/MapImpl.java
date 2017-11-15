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

    private MapData mapData = null;

    private final List<Tile> m_Tiles = new ArrayList<>();
    private final List<MiniTile> m_MiniTiles = new ArrayList<>();

    private Altitude m_maxAltitude;
    private MutableBoolean m_automaticPathUpdate = new MutableBoolean(false);
    private final Graph m_Graph;
    private final List<Mineral> m_Minerals = new ArrayList<>();
    private final List<Geyser> m_Geysers = new ArrayList<>();
    private final List<StaticBuilding> m_StaticBuildings = new ArrayList<>();
    private final List<TilePosition> m_StartingLocations = new ArrayList<>();
    private final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> m_RawFrontier = new ArrayList<>();

    private final BWMap bwMap;
    private final MapDrawer mapDrawer;
	private final List<MineralPatch> mineralPatches;
	private final Collection<Player> players;
	private final List<VespeneGeyser> vespeneGeysers;
	private final Collection<Unit> units;
    
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

        this.mapData = new MapDataImpl();
        setBasicMapData(this.bwMap.mapWidth(), this.bwMap.mapHeight(), this.bwMap.getStartPositions());
//    ///	bw << "Map::Initialize-resize: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::Initialize-resize: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        // Computes walkability, buildability and groundHeight and doodad information, using BWAPI corresponding functions
//        LoadData();
        markUnwalkableMiniTiles(this.bwMap);
        markBuildableTilesAndGroundHeight(this.bwMap);
//    ///	bw << "Map::LoadData: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::LoadData: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();
//
        DecideSeasOrLakes(BwemExt.lake_max_miniTiles, BwemExt.lake_max_width_in_miniTiles);
//    ///	bw << "Map::DecideSeasOrLakes: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::DecideSeasOrLakes: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        InitializeNeutrals(
                this.mineralPatches, m_Minerals,
                this.vespeneGeysers, m_Geysers,
                filterNeutralPlayerUnits(this.units, this.players), m_StaticBuildings
        );
//    ///	bw << "Map::InitializeNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::InitializeNeutrals: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ComputeAltitude(getWalkSize().getX(), getWalkSize().getY());
//    ///	bw << "Map::ComputeAltitude: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::ComputeAltitude: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ProcessBlockingNeutrals(getCandidates(StaticBuildings(), Minerals()));
//    ///	bw << "Map::ProcessBlockingNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::ProcessBlockingNeutrals: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ComputeAreas(ComputeTempAreas(getSortedMiniTilesByDescendingAltitude()), BwemExt.area_min_miniTiles);
//    ///	bw << "Map::ComputeAreas: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        System.out.println("Map::ComputeAreas: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().CreateChokePoints(StaticBuildings(), Minerals(), RawFrontier());
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
        return (tileCount != 0);
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
        for (TilePosition location : getStartingLocations()) {
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
        return (m_Tiles.get(getTileSize().getX() * p.getY() + p.getX()));
    }

    @Override
    public Tile GetTile(TilePosition p) {
        return GetTile(p, check_t.check);
    }

    @Override
    public Tile GetTile_(TilePosition p, check_t checkMode) {
        return m_Tiles.get(getTileSize().getX() * p.getY() + p.getX());
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
        return m_MiniTiles.get(getWalkSize().getX() * p.getY() + p.getX());
    }

    @Override
    public MiniTile GetMiniTile(WalkPosition p) {
        return GetMiniTile(p, check_t.check);
    }

    @Override
    public MiniTile GetMiniTile_(WalkPosition p, check_t checkMode) {
        return m_MiniTiles.get(getWalkSize().getX() * p.getY() + p.getX());
    }

    @Override
    public MiniTile GetMiniTile_(WalkPosition p) {
        return GetMiniTile_(p, check_t.check);
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
    public Geyser GetGeyser(Unit u) {
        for (Geyser geyser : m_Geysers) {
            if (geyser.Unit().equals(u)) {
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

    public WalkPosition BreadthFirstSearch(final WalkPosition start, final Pred findCond, final Pred visitCond, final boolean connect8) {
        if (findCond.isTrue(GetMiniTile(start), start, this)) {
            return start;
        }

        final List<WalkPosition> Visited = new ArrayList<>();
        final Queue<WalkPosition> ToVisit = new LinkedList<>();

        ToVisit.add(start);
        Visited.add(start);

        final WalkPosition[] dir8 = {
                new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(1, -1),
                new WalkPosition(-1,  0),                          new WalkPosition(1,  0),
                new WalkPosition(-1,  1), new WalkPosition(0,  1), new WalkPosition(1,  1)
        };
        final WalkPosition[] dir4 = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
        final WalkPosition[] directions = connect8 ? dir8 : dir4;

        while (!ToVisit.isEmpty()) {
            final WalkPosition current = ToVisit.remove();
            for (final WalkPosition delta : directions) {
                final WalkPosition next = current.add(delta);
                if (isValid(next)) {
                    final MiniTile Next = GetMiniTile(next, check_t.no_check);
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

    public WalkPosition BreadthFirstSearch(final WalkPosition start, final Pred findCond, final Pred visitCond) {
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
        tileCount = getTileSize().getX() * getTileSize().getY();
        for (int i = 0; i < tileCount; ++i) {
            m_Tiles.add(new Tile());
        }

        final int walkSize = getWalkSize().getX() * getWalkSize().getY();
        for (int i = 0; i < walkSize; ++i) {
            m_MiniTiles.add(new MiniTile());
        }

        for (final TilePosition t: startPositions) {
            m_StartingLocations.add(t);
        }
    }



    //----------------------------------------------------------------------
    // MapImpl::LoadData
    //----------------------------------------------------------------------

    private void markUnwalkableMiniTiles(final BWMap bwMap) {
        // Mark unwalkable minitiles (minitiles are walkable by default)
        for (int y = 0; y < getWalkSize().getY(); ++y)
        for (int x = 0; x < getWalkSize().getX(); ++x) {
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

    private void markBuildableTilesAndGroundHeight(final BWMap bwMap) {
        // Mark buildable tiles (tiles are unbuildable by default)
        for (int y = 0; y < getTileSize().getY(); ++y)
        for (int x = 0; x < getTileSize().getX(); ++x) {
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



    private void DecideSeasOrLakes(final int lake_max_miniTiles, final int lake_max_width_in_miniTiles) {
    	for (int y = 0; y < getWalkSize().getY(); ++y)
    	for (int x = 0; x < getWalkSize().getX(); ++x) {
    		final WalkPosition origin = new WalkPosition(x, y);
    		final MiniTile Origin = GetMiniTile_(origin, check_t.no_check);
    		if (Origin.SeaOrLake()) {
    			final List<WalkPosition> ToSearch = new ArrayList<>();
                ToSearch.add(origin);
    			final List<MiniTile> SeaExtent = new ArrayList<>();
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
                                Next.SetSea();
    							if (SeaExtent.size() <= lake_max_miniTiles) {
                                    SeaExtent.add(Next);
                                }
    						}
    					}
    				}
    			}

    			if ((SeaExtent.size() <= lake_max_miniTiles) &&
    				(bottomRight.getX() - topLeft.getX() <= lake_max_width_in_miniTiles) &&
    				(bottomRight.getY() - topLeft.getY() <= lake_max_width_in_miniTiles) &&
    				(topLeft.getX() >= 2) && (topLeft.getY() >= 2) && (bottomRight.getX() < getWalkSize().getX() - 2) && (bottomRight.getY() < getWalkSize().getY() - 2)) {
    				for (final MiniTile pSea : SeaExtent) {
    					pSea.SetLake();
                    }
                }
    		}
    	}
    }

    private void InitializeNeutrals(
            final List<MineralPatch> mineralPatches, final List<Mineral> minerals,
            final List<VespeneGeyser> vespeneGeysers, final List<Geyser> geysers,
            final List<PlayerUnit> neutralUnits, final List<StaticBuilding> staticBuildings
    ) {
        for (final MineralPatch mineralPatch : mineralPatches) {
            minerals.add(new Mineral(mineralPatch, this));
        }
        for (final VespeneGeyser vespeneGeyser : vespeneGeysers) {
            geysers.add(new Geyser(vespeneGeyser, this));
        }
        for (final Unit neutralUnit : neutralUnits) {
//                if ((neutralUnit instanceof Building) && !(neutralUnit instanceof MineralPatch || neutralUnit instanceof VespeneGeyser)) {
            if (neutralUnit instanceof Building) {
                staticBuildings.add(new StaticBuilding(neutralUnit, this));
            }
        }

        //TODO: Add "Special_Pit_Door" and "Special_Right_Pit_Door" to static buildings list? See mapImpl.cpp:238.
//				if (n->getType() == Special_Pit_Door)
//					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));
//				if (n->getType() == Special_Right_Pit_Door)
//					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));
    }



    //----------------------------------------------------------------------
    // MapImpl::ComputeAltitude
    //----------------------------------------------------------------------

    /**
     * 1) Fill in and sort DeltasByAscendingAltitude
     */
	private List<MutablePair<WalkPosition, Altitude>> getSortedDeltasByAscendingAltitude(final int mapWalkTileWidth, final int mapWalkTileHeight, int altitude_scale) {
    	final int range = Math.max(mapWalkTileWidth, mapWalkTileHeight) / 2 + 3; // should suffice for maps with no Sea.
    	
    	final List<MutablePair<WalkPosition, Altitude>> DeltasByAscendingAltitude = new ArrayList<>();
    	
    	for (int dy = 0; dy <= range; ++dy)
        for (int dx = dy; dx <= range; ++dx) { // Only consider 1/8 of possible deltas. Other ones obtained by symmetry.
            if (dx != 0 || dy != 0) {
                DeltasByAscendingAltitude.add(new MutablePair<>(
                        new WalkPosition(dx, dy),
                        new Altitude((int) (Double.valueOf("0.5") + (Utils.norm(dx, dy) * (double) altitude_scale)))
                ));
            }
        }

        Collections.sort(DeltasByAscendingAltitude, new PairGenericAltitudeComparator<>());
        
        return DeltasByAscendingAltitude;
    }
    
    /**
     * 2) Fill in ActiveSeaSideList, which basically contains all the seaside miniTiles (from which altitudes are to be computed)
     *    It also includes extra border-miniTiles which are considered as seaside miniTiles too.
     */
    private List<MutablePair<WalkPosition, Altitude>> getActiveSeaSideList(final int mapWalkTileWidth, final int mapWalkTileHeight) {
        final List<MutablePair<WalkPosition, Altitude>> ActiveSeaSideList = new ArrayList<>();

        for (int y = -1; y <= mapWalkTileHeight; ++y)
        for (int x = -1; x <= mapWalkTileWidth; ++x) {
            final WalkPosition w = new WalkPosition(x, y);
            if (!isValid(w) || BwemExt.seaSide(w, this)) {
                ActiveSeaSideList.add(new MutablePair<>(w, new Altitude(0)));
            }
        }

        return ActiveSeaSideList;
    }
    
    /**
     * 3) Dijkstra's algorithm to set altitude for mini tiles.
     */
    private void setAltitudes(
            final List<MutablePair<WalkPosition, Altitude>> DeltasByAscendingAltitude,
            final List<MutablePair<WalkPosition, Altitude>> ActiveSeaSideList,
            final int altitude_scale
    ) {
        for (final MutablePair<WalkPosition, Altitude> delta_altitude : DeltasByAscendingAltitude) {
            final WalkPosition d = new WalkPosition(delta_altitude.left.getX(), delta_altitude.left.getY());
            final Altitude altitude = new Altitude(delta_altitude.right);
            
            for (int i = 0; i < ActiveSeaSideList.size(); ++i) {
                final MutablePair<WalkPosition, Altitude> Current = ActiveSeaSideList.get(i);
                if (altitude.intValue() - Current.right.intValue() >= 2 * altitude_scale) {
                	// optimization : once a seaside miniTile verifies this condition,
                	// we can throw it away as it will not generate min altitudes anymore
                	BwemExt.fast_erase(ActiveSeaSideList, i--);
                } else {
                    final WalkPosition[] deltas = {new WalkPosition(d.getX(), d.getY()), new WalkPosition(-d.getX(), d.getY()), new WalkPosition(d.getX(), -d.getY()), new WalkPosition(-d.getX(), -d.getY()),
                                                   new WalkPosition(d.getY(), d.getX()), new WalkPosition(-d.getY(), d.getX()), new WalkPosition(d.getY(), -d.getX()), new WalkPosition(-d.getY(), -d.getX())};
                    for (final WalkPosition delta : deltas) {
                        final WalkPosition w = Current.left.add(delta);
                        if (isValid(w)) {
                            MiniTile miniTile = GetMiniTile_(w, check_t.no_check);
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
    private void ComputeAltitude(final int mapWalkTileWidth, final int mapWalkTileHeight) {
    	final int altitude_scale = 8; // 8 provides a pixel definition for altitude_t, since altitudes are computed from miniTiles which are 8x8 pixels
    	
    	final List<MutablePair<WalkPosition, Altitude>> DeltasByAscendingAltitude = getSortedDeltasByAscendingAltitude(mapWalkTileWidth, mapWalkTileHeight, altitude_scale);
    	
    	final List<MutablePair<WalkPosition, Altitude>> ActiveSeaSides = getActiveSeaSideList(mapWalkTileWidth, mapWalkTileHeight);

    	setAltitudes(DeltasByAscendingAltitude, ActiveSeaSides, altitude_scale);
    }

    //----------------------------------------------------------------------



    //----------------------------------------------------------------------
    // MapImpl::ProcessBlockingNeutrals
    //----------------------------------------------------------------------

    private List<Neutral> getCandidates(
            final List<StaticBuilding> staticBuildings,
            final List<Mineral> minerals
    ) {
        final List<Neutral> Candidates = new ArrayList<>();
        for (StaticBuilding s : staticBuildings) {
            Candidates.add(s);
        }
        for (Mineral m : minerals) {
            Candidates.add(m);
        }
        return Candidates;
    }

    /**********************************************************************/

    /**
     * 1)  Retrieve the Border: the outer border of pCandidate
     */

    private List<WalkPosition> getOuterMiniTileBorderOfNeutral(final Neutral pCandidate) {
        return BwemExt.outerMiniTileBorder(pCandidate.TopLeft(), pCandidate.Size());
    }

    private List<WalkPosition> trimOuterMiniTileBorder(final List<WalkPosition> Border) {
        Utils.really_remove_if(Border, new Pred() {
            @Override
            public boolean isTrue(Object... args) {
                WalkPosition w = (WalkPosition) args[0];
                return (!isValid(w)
                    || !GetMiniTile(w, check_t.no_check).Walkable()
                    || GetTile(w.toPosition().toTilePosition(), check_t.no_check).GetNeutral() != null);
            }
        });
        return Border;
    }

    /**********************************************************************/

    /**
     * 2)  Find the doors in Border: one door for each connected set of walkable, neighbouring miniTiles.
     *     The searched connected miniTiles all have to be next to some lake or some static building, though they can't be part of one.
     */

    private List<WalkPosition> getDoors(final List<WalkPosition> Border) {
        final List<WalkPosition> Doors = new ArrayList<>();

        while (!Border.isEmpty()) {
            final WalkPosition door = Border.remove(Border.size() - 1);
            Doors.add(door);

            final List<WalkPosition> ToVisit = new ArrayList<>();
            ToVisit.add(door);

            final List<WalkPosition> Visited = new ArrayList<>();
            Visited.add(door);

            while (!ToVisit.isEmpty()) {
                final WalkPosition current = ToVisit.remove(ToVisit.size() - 1);

                final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                for (final WalkPosition delta : deltas) {
                    final WalkPosition next = current.add(delta);
                    if (isValid(next) && !Visited.contains(next)) {
                        if (GetMiniTile(next, check_t.no_check).Walkable()) {
                            if (GetTile((next.toPosition()).toTilePosition(), check_t.no_check).GetNeutral() == null) {
                                if (BwemExt.adjoins8SomeLakeOrNeutral(next, this)) {
                                    ToVisit.add(next);
                                    Visited.add(next);
                                }
                            }
                        }
                    }
                }
            }

            Utils.really_remove_if(Border, new Pred() {
                @Override
                public boolean isTrue(Object... args) {
                    WalkPosition w = (WalkPosition) args[0];
                    return Visited.contains(w);
                }
            });
        }

        return Doors;
    }

    /**********************************************************************/

    /**
     * 3)  If at least 2 doors, find the true doors in Border: a true door is a door that gives onto an area big enough
     */

    private List<WalkPosition> getTrueDoors(final List<WalkPosition> Doors, Neutral pCandidate) {
        final List<WalkPosition> TrueDoors = new ArrayList<>();

        if (Doors.size() >= 2) {
            for (final WalkPosition door : Doors) {
                final List<WalkPosition> ToVisit = new ArrayList<>();
                ToVisit.add(door);

                final List<WalkPosition> Visited = new ArrayList<>();
                Visited.add(door);

                final int limit = (pCandidate instanceof StaticBuilding) ? 10 : 400; //TODO: Description for 10 and 400?

                while (!ToVisit.isEmpty() && (Visited.size() < limit)) {
                    final WalkPosition current = ToVisit.remove(ToVisit.size() - 1);
                    final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                    for (final WalkPosition delta : deltas) {
                        final WalkPosition next = current.add(delta);
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
        }

        return TrueDoors;
    }

    /**********************************************************************/

    /**
     * 4)  If at least 2 true doors, pCandidate is a blocking static building
     */

    private void markBlockingStackedNeutrals(final Neutral pCandidate, final List<WalkPosition> TrueDoors) {
        if (TrueDoors.size() >= 2) {
            // Marks pCandidate (and any Neutral stacked with it) as blocking.
            for (Neutral pNeutral = GetTile(pCandidate.TopLeft()).GetNeutral(); pNeutral != null; pNeutral = pNeutral.NextStacked()) {
                pNeutral.SetBlocking(TrueDoors);
            }

            // Marks all the miniTiles of pCandidate as blocked.
            // This way, areas at TrueDoors won't merge together.
            final WalkPosition pCandidateW = pCandidate.Size().toPosition().toWalkPosition();
            for (int dy = 0; dy < pCandidateW.getY(); ++dy)
                for (int dx = 0; dx < pCandidateW.getX(); ++dx) {
                    final MiniTile miniTile = GetMiniTile_(((pCandidate.TopLeft().toPosition()).toWalkPosition()).add(new WalkPosition(dx, dy)));
                    if (miniTile.Walkable()) {
                        miniTile.SetBlocked();
                    }
                }
        }
    }

    /**********************************************************************/

    private void ProcessBlockingNeutrals(final List<Neutral> Candidates) {
        for (final Neutral pCandidate : Candidates) {
            if (pCandidate.NextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top one
                final List<WalkPosition> Border = trimOuterMiniTileBorder(getOuterMiniTileBorderOfNeutral(pCandidate));

                final List<WalkPosition> Doors = getDoors(Border);

                final List<WalkPosition> TrueDoors = getTrueDoors(Doors, pCandidate);

                markBlockingStackedNeutrals(pCandidate, TrueDoors);
            }
        }
    }

    //----------------------------------------------------------------------



    // Assigns MiniTile::m_areaId for each miniTile having AreaIdMissing()
    // Areas are computed using MiniTile::Altitude() information only.
    // The miniTiles are considered successively in descending order of their Altitude().
    // Each of them either:
    //   - involves the creation of a new area.
    //   - is added to some existing neighbouring area.
    //   - makes two neighbouring areas merge together.
    private void ComputeAreas(final List<TempAreaInfo> TempAreaList, final int area_min_miniTiles) {
        CreateAreas(TempAreaList, area_min_miniTiles);
        SetAreaIdAndMinAltitudeInTiles();
    }

    private List<MutablePair<WalkPosition, MiniTile>> getSortedMiniTilesByDescendingAltitude() {
        final List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude = new ArrayList<>();

        for (int y = 0; y < getWalkSize().getY(); ++y)
        for (int x = 0; x < getWalkSize().getX(); ++x) {
            final WalkPosition w = new WalkPosition(x, y);
            final MiniTile miniTile = GetMiniTile_(w, check_t.no_check);
            if (miniTile.AreaIdMissing()) {
                MiniTilesByDescendingAltitude.add(new MutablePair<>(w, miniTile));
            }
        }

        Collections.sort(MiniTilesByDescendingAltitude, new PairGenericMiniTileAltitudeComparator<>());
        Collections.reverse(MiniTilesByDescendingAltitude);

        return MiniTilesByDescendingAltitude;
    }

    private List<TempAreaInfo> ComputeTempAreas(final List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude) {
        final List<TempAreaInfo> TempAreaList = new ArrayList<>();
        TempAreaList.add(new TempAreaInfo()); // TempAreaList[0] left unused, as AreaIds are > 0

        for (final MutablePair<WalkPosition, MiniTile> Current : MiniTilesByDescendingAltitude) {
            final WalkPosition pos = new WalkPosition(Current.left.getX(), Current.left.getY());
            final MiniTile cur = Current.right;

            final MutablePair<AreaId, AreaId> neighboringAreas = findNeighboringAreas(pos);
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
                for (final TilePosition startingLoc : getStartingLocations()) {
                    if (Double.compare(BwemExt.dist(pos.toPosition().toTilePosition(), startingLoc.add(new TilePosition(2, 1))), Double.valueOf("3")) <= 0) {
                        cpp_algorithm_std_any_of = true;
                        break;
                    }
                }
                final int curAltitude = cur.Altitude().intValue();
                final int biggerHighestAltitude = TempAreaList.get(bigger.intValue()).HighestAltitude().intValue();
                final int smallerHighestAltitude = TempAreaList.get(smaller.intValue()).HighestAltitude().intValue();
                if ((TempAreaList.get(smaller.intValue()).Size() < 80)
                        || (smallerHighestAltitude < 80)
                        || (Double.compare((double) curAltitude / (double) biggerHighestAltitude, Double.valueOf("0.90")) >= 0)
                        || (Double.compare((double) curAltitude / (double) smallerHighestAltitude, Double.valueOf("0.90")) >= 0)
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
        Utils.really_remove_if(m_RawFrontier, new Pred() {
            @Override
            public boolean isTrue(Object... args) {
                @SuppressWarnings("unchecked")
                final MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f
                        = (MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>) args[0];
                return f.left.left.equals(f.left.right);
            }
        });

        return TempAreaList;
    }

    private void ReplaceAreaIds(final WalkPosition p, final AreaId newAreaId) {
        final MiniTile Origin = GetMiniTile_(p, check_t.no_check);
        final AreaId oldAreaId = Origin.AreaId();
        Origin.ReplaceAreaId(newAreaId);

        List<WalkPosition> ToSearch = new ArrayList<>();
        ToSearch.add(p);
        while (!ToSearch.isEmpty()) {
            final WalkPosition current = ToSearch.remove(ToSearch.size() - 1);

            final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
            for (final WalkPosition delta : deltas) {
                final WalkPosition next = current.add(delta);
                if (isValid(next)) {
                    final MiniTile Next = GetMiniTile_(next, check_t.no_check);
                    if (Next.AreaId().equals(oldAreaId)) {
                        ToSearch.add(next);
                        Next.ReplaceAreaId(newAreaId);
                    }
                }
            }
        }

        // also replaces references of oldAreaId by newAreaId in m_RawFrontier:
        if (newAreaId.intValue() > 0) {
            for (final MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f : m_RawFrontier) {
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
    private void CreateAreas(final List<TempAreaInfo> TempAreaList, final int area_min_miniTiles) {
        final List<MutablePair<WalkPosition, Integer>> AreasList = new ArrayList<>();

        int newAreaId = 1;
        int newTinyAreaId = -2;

        for (final TempAreaInfo TempArea : TempAreaList) {
            if (TempArea.Valid()) {
                if (TempArea.Size() >= area_min_miniTiles) {
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

    private void SetAreaIdInTile(final TilePosition t) {
        final Tile tile = GetTile_(t);
//        bwem_assert(tile.AreaId() == 0);	// initialized to 0
        if (!(tile.AreaId().intValue() == 0)) { // initialized to 0
            throw new IllegalStateException();
        }

        for (int dy = 0; dy < 4; ++dy)
        for (int dx = 0; dx < 4; ++dx) {
            final AreaId id = GetMiniTile((t.toPosition().toWalkPosition()).add(new WalkPosition(dx, dy)), check_t.no_check).AreaId();
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

    // Renamed from "MapImpl::SetAltitudeInTile"
    private void SetMinAltitudeInTile(final TilePosition t) {
        Altitude minAltitude = new Altitude(Integer.MAX_VALUE);

        for (int dy = 0; dy < 4; ++dy)
        for (int dx = 0; dx < 4; ++dx) {
            final Altitude altitude = new Altitude(GetMiniTile(((t.toPosition()).toWalkPosition()).add(new WalkPosition(dx, dy)), check_t.no_check).Altitude());
            if (altitude.intValue() < minAltitude.intValue()) {
                minAltitude = new Altitude(altitude);
            }
        }

        GetTile_(t).SetMinAltitude(minAltitude);
    }

    // Renamed from "MapImpl::SetAreaIdInTiles"
    private void SetAreaIdAndMinAltitudeInTiles() {
        for (int y = 0; y < getTileSize().getY(); ++y)
        for (int x = 0; x < getTileSize().getX(); ++x) {
            final TilePosition t = new TilePosition(x, y);
            SetAreaIdInTile(t);
            SetMinAltitudeInTile(t);
        }
    }

    public MutablePair<AreaId, AreaId> findNeighboringAreas(final WalkPosition p) {
        final MutablePair<AreaId, AreaId> result = new MutablePair<>(null, null);

        final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
        for (final WalkPosition delta : deltas) {
            if (isValid(p.add(delta))) {
                final AreaId areaId = GetMiniTile(p.add(delta), check_t.no_check).AreaId();
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

    private static final AbstractMap<MutablePair<AreaId, AreaId>, Integer> map_AreaPair_counter = new ConcurrentHashMap<>();
    public static AreaId chooseNeighboringArea(final AreaId a, final AreaId b) {
        int a_val = a.intValue();
        int b_val = b.intValue();

        if (a_val > b_val) {
            int a_val_tmp = a_val;
            a_val = b_val;
            b_val = a_val_tmp;
        }

        final MutablePair<AreaId, AreaId> key = new MutablePair<>(new AreaId(a_val), new AreaId(b_val));
        Integer val = map_AreaPair_counter.get(key);
        if (val == null) {
            val = 0;
        }
        map_AreaPair_counter.put(key, val + 1);

        return new AreaId((val % 2 == 0) ? a_val : b_val);
    }

}
