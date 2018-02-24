package bwem.map;

import bwem.*;
import bwem.area.Area;
import bwem.area.typedef.AreaId;
import bwem.Check;
import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import bwem.typedef.Altitude;
import bwem.typedef.CPPath;
import bwem.typedef.Pred;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.NeutralData;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
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
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class MapImpl implements Map {

    private final MapPrinter mapPrinter;

    protected AdvancedData advancedData = null;
    protected NeutralData neutralData = null;

    protected Altitude maxAltitude;
    private final MutableBoolean automaticPathUpdate = new MutableBoolean(false);
    private final Graph graph;

    protected final List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> RawFrontier = new ArrayList<>();

    private final BWMap bwMap;
    private final MapDrawer mapDrawer;
	protected final List<MineralPatch> mineralPatches;
    protected final Collection<Player> players;
    protected final List<VespeneGeyser> vespeneGeysers;
    protected final Collection<Unit> units;
    
    public MapImpl(
            BWMap bwMap,
            MapDrawer mapDrawer,
            Collection<Player> players,
            List<MineralPatch> mineralPatches,
            List<VespeneGeyser> vespeneGeysers,
            Collection<Unit> units
    ) {
        mapPrinter = new MapPrinter();
    	this.mapDrawer = mapDrawer;
    	this.bwMap = bwMap;
    	this.players = players;
    	this.mineralPatches = mineralPatches;
    	this.vespeneGeysers = vespeneGeysers;
    	this.units = units;
        graph = new Graph(this);
    }

//    MapImpl::~MapImpl()
//    {
//        automaticPathUpdate = false;		// now there is no need to update the paths
//    }

    protected BWMap getBWMap() {
        return this.bwMap;
    }

    @Override
    public AdvancedData getData() {
        return this.advancedData;
    }

    @Override
    public MapPrinter getMapPrinter() {
        return mapPrinter;
    }

    @Override
    public boolean isInitialized() {
        return (this.advancedData != null);
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public List<MutablePair<MutablePair<AreaId, AreaId>, WalkPosition>> getRawFrontier() {
        return RawFrontier;
    }

    @Override
    public MutableBoolean automaticPathUpdate() {
        return automaticPathUpdate;
    }

    @Override
    public void enableAutomaticPathAnalysis() {
        automaticPathUpdate.setTrue();
    }

    @Override
    public boolean findBasesForStartingLocations() {
        boolean atLeastOneFailed = false;
        for (TilePosition location : getData().getMapData().getStartingLocations()) {
            boolean found = false;
            for (Area area : getGraph().getAreas()) {
                if (!found) {
                    for (Base base : area.getBases()) {
                        if (!found) {
                            if (BwemExt.queenWiseDist(base.getLocation(), location) <= BwemExt.max_tiles_between_StartingLocation_and_its_AssignedBase) {
                                base.setStartingLocation(location);
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
    public Altitude getMaxAltitude() {
        return maxAltitude;
    }

    @Override
    public int getBaseCount() {
        return getGraph().getBaseCount();
    }

    @Override
    public int getChokePointCount() {
        return getGraph().getChokePoints().size();
    }

    @Override
    public NeutralData getNeutralData() {
        return this.neutralData;
    }

    @Override
    public void onUnitDestroyed(Unit u) {
        if (u instanceof MineralPatch) {
            onMineralDestroyed(u);
        } else {
            try {
                onStaticBuildingDestroyed(u);
            } catch (Exception ex) {
                //TODO: Handle this exception appropriately.
                /**
                 * An exception WILL be thrown if the unit is not in
                 * the "Map .StaticBuildings" list.
                 * Just ignore the exception.
                 */
            }
        }
    }

    @Override
    public void onMineralDestroyed(Unit u) {
        for (int i = 0; i < getNeutralData().getMinerals().size(); ++i) {
            Mineral mineral = getNeutralData().getMinerals().get(i);
            if (mineral.getUnit().equals(u)) {
                onMineralDestroyed(mineral);
                mineral.simulateCPPObjectDestructor(); /* IMPORTANT! These actions are performed in the "~Neutral" dtor in BWEM 1.4.1 C++. */
                getNeutralData().getMinerals().remove(i--);
                return;
            }
        }
//        bwem_assert(iMineral != minerals.end());
        throw new IllegalArgumentException("unit is not a Mineral");
    }

    /**
     * This method could be placed in {@link #onMineralDestroyed(org.openbw.bwapi4j.unit.Unit)}.
     * This remains as a separate method for portability consistency.
     */
    private void onMineralDestroyed(Mineral pMineral) {
        for (Area area : getGraph().getAreas()) {
            area.onMineralDestroyed(pMineral);
        }
    }

    @Override
    public void onStaticBuildingDestroyed(Unit u) {
        for (int i = 0; i < getNeutralData().getStaticBuildings().size(); ++i) {
            StaticBuilding building = getNeutralData().getStaticBuildings().get(i);
            if (building.getUnit().equals(u)) {
                building.simulateCPPObjectDestructor(); /* IMPORTANT! These actions are performed in the "~Neutral" dtor in BWEM 1.4.1 C++. */
                getNeutralData().getStaticBuildings().remove(i--);
                return;
            }
        }
//        bwem_assert(iStaticBuilding != StaticBuildings.end());
        throw new IllegalArgumentException("unit is not a StaticBuilding");
    }

    public void onBlockingNeutralDestroyed(Neutral pBlocking) {
//        bwem_assert(pBlocking && pBlocking->blocking());
        if (!(pBlocking != null && pBlocking.isBlocking())) {
            throw new IllegalArgumentException();
        }

        for (Area pArea : pBlocking.getBlockedAreas())
        for (ChokePoint cp : pArea.getChokePoints()) {
            cp.onBlockingNeutralDestroyed(pBlocking);
        }

        if (getData().getTile(pBlocking.getTopLeft()).getNeutral() != null) { // there remains some blocking Neutrals at the same location
            return;
        }

        // Unblock the miniTiles of pBlocking:
        AreaId newId = new AreaId(pBlocking.getBlockedAreas().iterator().next().getId());
        WalkPosition pBlockingW = pBlocking.getSize().toWalkPosition();
        for (int dy = 0; dy < pBlockingW.getY(); ++dy)
        for (int dx = 0; dx < pBlockingW.getX(); ++dx) {
            MiniTile miniTile = getData().getMiniTile_(pBlocking.getTopLeft().toWalkPosition().add(new WalkPosition(dx, dy)));
            if (miniTile.isWalkable()) {
                ((MiniTileImpl) miniTile).replaceBlockedAreaId(newId);
            }
        }

        // Unblock the Tiles of pBlocking:
        for (int dy = 0; dy < pBlocking.getSize().getY(); ++dy)
        for (int dx = 0; dx < pBlocking.getSize().getX(); ++dx) {
            ((TileImpl) getData().getTile_(pBlocking.getTopLeft().add(new TilePosition(dx, dy)))).resetAreaId();
            setAreaIdInTile(pBlocking.getTopLeft().add(new TilePosition(dx, dy)));
        }

        if (automaticPathUpdate().booleanValue()) {
            getGraph().computeChokePointDistanceMatrix();
        }
    }

    @Override
    public List<Area> getAreas() {
        return getGraph().getAreas();
    }

    // Returns an Area given its id. Range = 1..size()
    @Override
    public Area getArea(AreaId id) {
        return graph.getArea(id);
    }

    @Override
    public Area getArea(WalkPosition w) {
        return graph.getArea(w);
    }

    @Override
    public Area getArea(TilePosition t) {
        return graph.getArea(t);
    }

    @Override
    public Area getNearestArea(WalkPosition w) {
        return graph.getNearestArea(w);
    }

    @Override
    public Area getNearestArea(TilePosition t) {
        return graph.getNearestArea(t);
    }

    //graph.cpp:30:Area * mainArea(MapImpl * pMap, TilePosition topLeft, TilePosition size)
    //Note: The original C++ code appears to return the last discovered area instead of the area with the highest frequency.
    //TODO: Determine if we desire the last discovered area or the area with the highest frequency.
    @Override
    public Area getMainArea(final TilePosition topLeft, final TilePosition size) {
//        //----------------------------------------------------------------------
//        // Area with the highest frequency.
//        //----------------------------------------------------------------------
//        final AbstractMap<Area, Integer> areaFrequency = new HashMap<>();
//        for (int dy = 0; dy < size.getY(); ++dy)
//        for (int dx = 0; dx < size.getX(); ++dx) {
//            final Area area = getArea(topLeft.add(new TilePosition(dx, dy)));
//            if (area != null) {
//                Integer val = areaFrequency.get(area);
//                if (val == null) {
//                    val = 0;
//                }
//                areaFrequency.put(area, val + 1);
//            }
//        }
//
//        if (!areaFrequency.isEmpty()) {
//            java.util.Map.Entry<Area, Integer> highestFreqEntry = null;
//            for (final java.util.Map.Entry<Area, Integer> currentEntry : areaFrequency.entrySet()) {
//                if (highestFreqEntry == null || (currentEntry.getValue() > highestFreqEntry.getValue())) {
//                    highestFreqEntry = currentEntry;
//                }
//            }
//            return highestFreqEntry.getKey();
//        } else {
//            return null;
//        }
//        //----------------------------------------------------------------------



        //----------------------------------------------------------------------
        // Last area.
        //----------------------------------------------------------------------
        final List<Area> areas = new ArrayList<>();
        for (int dy = 0; dy < size.getY(); ++dy)
        for (int dx = 0; dx < size.getX(); ++dx) {
            final Area area = getArea(topLeft.add(new TilePosition(dx, dy)));
            if (area != null && !areas.contains(area)) {
                areas.add(area);
            }
        }

        return areas.isEmpty() ? null : areas.get(areas.size() - 1);
        //----------------------------------------------------------------------
    }

    @Override
    public CPPath getPath(Position a, Position b, MutableInt pLength) {
        return graph.getPath(a, b, pLength);
    }

    @Override
    public CPPath getPath(Position a, Position b) {
        return getPath(a, b, null);
    }

    public TilePosition breadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond, boolean connect8) {
        if (findCond.isTrue(getData().getTile(start), start, this)) {
            return start;
        }

        List<TilePosition> visited = new ArrayList<>();
        Queue<TilePosition> toVisit = new LinkedList<>();

        toVisit.add(start);
        visited.add(start);

        TilePosition[] dir8 = {
                new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(1, -1),
                new TilePosition(-1,  0),                          new TilePosition(1,  0),
                new TilePosition(-1,  1), new TilePosition(0,  1), new TilePosition(1,  1)
        };
        TilePosition[] dir4 = {new TilePosition(0, -1), new TilePosition(-1, 0), new TilePosition(+1, 0), new TilePosition(0, +1)};
        TilePosition[] directions = connect8 ? dir8 : dir4;

        while (!toVisit.isEmpty()) {
            TilePosition current = toVisit.remove();
            for (TilePosition delta : directions) {
                TilePosition next = current.add(delta);
                if (getData().getMapData().isValid(next)) {
                    Tile nextTile = getData().getTile(next, Check.NO_CHECK);
                    if (findCond.isTrue(nextTile, next, this)) {
                        return next;
                    }
                    if (visitCond.isTrue(nextTile, next, this) && !visited.contains(next)) {
                        toVisit.add(next);
                        visited.add(next);
                    }
                }
            }
        }

        //TODO: Are we supposed to return start or not?
//        bwem_assert(false);
        throw new IllegalStateException();
//        return start;
    }

    public TilePosition breadthFirstSearch(TilePosition start, Pred findCond, Pred visitCond) {
        return breadthFirstSearch(start, findCond, visitCond, true);
    }

    public WalkPosition breadthFirstSearch(final WalkPosition start, final Pred findCond, final Pred visitCond, final boolean connect8) {
        if (findCond.isTrue(getData().getMiniTile(start), start, this)) {
            return start;
        }

        final List<WalkPosition> visited = new ArrayList<>();
        final Queue<WalkPosition> toVisit = new LinkedList<>();

        toVisit.add(start);
        visited.add(start);

        final WalkPosition[] dir8 = {
                new WalkPosition(-1, -1), new WalkPosition(0, -1), new WalkPosition(1, -1),
                new WalkPosition(-1,  0),                          new WalkPosition(1,  0),
                new WalkPosition(-1,  1), new WalkPosition(0,  1), new WalkPosition(1,  1)
        };
        final WalkPosition[] dir4 = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1)};
        final WalkPosition[] directions = connect8 ? dir8 : dir4;

        while (!toVisit.isEmpty()) {
            final WalkPosition current = toVisit.remove();
            for (final WalkPosition delta : directions) {
                final WalkPosition next = current.add(delta);
                if (getData().getMapData().isValid(next)) {
                    final MiniTile miniTile = getData().getMiniTile(next, Check.NO_CHECK);
                    if (findCond.isTrue(miniTile, next, this)) {
                        return next;
                    }
                    if (visitCond.isTrue(miniTile, next, this) && !visited.contains(next)) {
                        toVisit.add(next);
                        visited.add(next);
                    }
                }
            }
        }

        //TODO: Are we supposed to return start or not?
//        bwem_assert(false);
        throw new IllegalStateException();
//        return start;
    }

    public WalkPosition breadthFirstSearch(final WalkPosition start, final Pred findCond, final Pred visitCond) {
        return breadthFirstSearch(start, findCond, visitCond, true);
    }

    public void drawDiagonalCrossMap(Position topLeft, Position bottomRight, Color col) {
        this.mapDrawer.drawLineMap(topLeft, bottomRight, col);
        this.mapDrawer.drawLineMap(new Position(bottomRight.getX(), topLeft.getY()), new Position(topLeft.getX(), bottomRight.getY()), col);
    }

    protected List<PlayerUnit> filterPlayerUnits(final Collection<Unit> units, final Player player) {
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

    protected List<PlayerUnit> filterNeutralPlayerUnits(final Collection<Unit> units, final Collection<Player> players) {
        final List<PlayerUnit> ret = new ArrayList<>();
        for (final Player player : players) {
            if (player.isNeutral()) {
                ret.addAll(filterPlayerUnits(units, player));
            }
        }
        return ret;
    }

    public void setAreaIdInTile(final TilePosition t) {
        final Tile tile = getData().getTile_(t);
//        bwem_assert(tile.AreaId() == 0);	// initialized to 0
        if (!(tile.getAreaId().intValue() == 0)) { // initialized to 0
            throw new IllegalStateException();
        }

        for (int dy = 0; dy < 4; ++dy) {
            for (int dx = 0; dx < 4; ++dx) {
                final AreaId id = getData().getMiniTile(t.toWalkPosition().add(new WalkPosition(dx, dy)), Check.NO_CHECK).getAreaId();
                if (id.intValue() != 0) {
                    if (tile.getAreaId().intValue() == 0) {
                        ((TileImpl) tile).setAreaId(id);
                    } else if (!tile.getAreaId().equals(id)) {
                        ((TileImpl) tile).setAreaId(new AreaId(-1));
                        return;
                    }
                }
            }
        }
    }

    public MutablePair<AreaId, AreaId> findNeighboringAreas(final WalkPosition p) {
        final MutablePair<AreaId, AreaId> result = new MutablePair<>(null, null);

        final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
        for (final WalkPosition delta : deltas) {
            if (getData().getMapData().isValid(p.add(delta))) {
                final AreaId areaId = getData().getMiniTile(p.add(delta), Check.NO_CHECK).getAreaId();
                if (areaId.intValue() > 0) {
                    if (result.getLeft() == null) {
                        result.setLeft(areaId);
                    } else if (!result.getLeft().equals(areaId)) {
                        if (result.getRight() == null || ((areaId.intValue() < result.getRight().intValue()))) {
                            result.setRight(areaId);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static final AbstractMap<MutablePair<AreaId, AreaId>, Integer> map_AreaPair_counter = new ConcurrentHashMap<>();
    public static AreaId chooseNeighboringArea(final AreaId a, final AreaId b) {
        int aVal = a.intValue();
        int bVal = b.intValue();

        if (aVal > bVal) {
            int aValTmp = aVal;
            aVal = bVal;
            bVal = aValTmp;
        }

        final MutablePair<AreaId, AreaId> key = new MutablePair<>(new AreaId(aVal), new AreaId(bVal));
        Integer val = map_AreaPair_counter.get(key);
        if (val == null) {
            val = 0;
        }
        map_AreaPair_counter.put(key, val + 1);

        return new AreaId((val % 2 == 0) ? aVal : bVal);
    }

}
