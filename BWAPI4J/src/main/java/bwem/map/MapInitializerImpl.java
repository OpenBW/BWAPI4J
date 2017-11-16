package bwem.map;

import bwem.area.TempAreaInfo;
import bwem.area.typedef.AreaId;
import bwem.check_t;
import bwem.tile.MiniTile;
import bwem.typedef.Altitude;
import bwem.typedef.Pred;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
import bwem.util.PairGenericAltitudeComparator;
import bwem.util.PairGenericMiniTileAltitudeComparator;
import bwem.util.Timer;
import bwem.util.Utils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.MapDrawer;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Building;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MapInitializerImpl extends MapImpl implements MapInitializer {

    private static final Logger logger = LogManager.getLogger();

    public MapInitializerImpl(
            BWMap bwMap,
            MapDrawer mapDrawer,
            Collection<Player> players,
            List<MineralPatch> mineralPatches,
            List<VespeneGeyser> vespeneGeysers,
            Collection<Unit> units
    ) {
        super(bwMap, mapDrawer, players, mineralPatches, vespeneGeysers, units);
    }

    @Override
    public void Initialize() {
        final Timer overallTimer = new Timer();
        final Timer timer = new Timer();

        compileAdvancedData(super.bwMap.mapWidth(), super.bwMap.mapHeight(), super.bwMap.getStartPositions());
//    ///	bw << "Map::Initialize-resize: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::Initialize-resize: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        // Computes walkability, buildability and groundHeight and doodad information, using BWAPI corresponding functions
//        LoadData();
        markUnwalkableMiniTiles(super.advancedData, super.bwMap);
        markBuildableTilesAndGroundHeight(super.advancedData, super.bwMap);
//    ///	bw << "Map::LoadData: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::LoadData: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();
//
        DecideSeasOrLakes(super.advancedData, BwemExt.lake_max_miniTiles, BwemExt.lake_max_width_in_miniTiles);
//    ///	bw << "Map::DecideSeasOrLakes: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::DecideSeasOrLakes: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        InitializeNeutrals(
                super.mineralPatches, Minerals(),
                super.vespeneGeysers, Geysers(),
                filterNeutralPlayerUnits(super.units, super.players), StaticBuildings()
        );
//    ///	bw << "Map::InitializeNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::InitializeNeutrals: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ComputeAltitude(super.advancedData);
//    ///	bw << "Map::ComputeAltitude: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::ComputeAltitude: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ProcessBlockingNeutrals(getCandidates(StaticBuildings(), Minerals()));
//    ///	bw << "Map::ProcessBlockingNeutrals: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::ProcessBlockingNeutrals: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        ComputeAreas(ComputeTempAreas(getSortedMiniTilesByDescendingAltitude()), BwemExt.area_min_miniTiles);
//    ///	bw << "Map::ComputeAreas: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::ComputeAreas: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().CreateChokePoints(StaticBuildings(), Minerals(), RawFrontier());
//    ///	bw << "Graph::CreateChokePoints: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::CreateChokePoints: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().ComputeChokePointDistanceMatrix();
//    ///	bw << "Graph::ComputeChokePointDistanceMatrix: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::ComputeChokePointDistanceMatrix: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().CollectInformation();
//    ///	bw << "Graph::CollectInformation: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::CollectInformation: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

        GetGraph().CreateBases();
//    ///	bw << "Graph::CreateBases: " << timer.ElapsedMilliseconds() << " ms" << endl; timer.Reset();
        logger.info("Map::CreateBases: " + timer.ElapsedMilliseconds() + " ms"); timer.Reset();

//    ///	bw << "Map::Initialize: " << overallTimer.ElapsedMilliseconds() << " ms" << endl;
        logger.info("Map::Initialize: " + overallTimer.ElapsedMilliseconds() + " ms"); timer.Reset();
    }

    @Override
    public void compileAdvancedData(final int mapTileWidth, final int mapTileHeight, final List<TilePosition> startingLocations) {
        final MapData mapData = new MapDataImpl(mapTileWidth, mapTileHeight, startingLocations);
        final TileData tileData = new TileDataImpl(
                mapData.getTileSize().getX() * mapData.getTileSize().getY(),
                mapData.getWalkSize().getX() * mapData.getWalkSize().getY()
        );
        super.advancedData = new AdvancedDataImpl(mapData, tileData);
    }



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::LoadData
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void markUnwalkableMiniTiles(final AdvancedData advancedData, final BWMap bwMap) {
        // Mark unwalkable minitiles (minitiles are walkable by default)
        for (int y = 0; y < advancedData.getMapData().getWalkSize().getY(); ++y)
            for (int x = 0; x < advancedData.getMapData().getWalkSize().getX(); ++x) {
                if (!bwMap.isWalkable(x, y)) {
                    // For each unwalkable minitile, we also mark its 8 neighbours as not walkable.
                    // According to some tests, this prevents from wrongly pretending one Marine can go by some thin path.
                    for (int dy = -1; dy <= +1; ++dy)
                        for (int dx = -1; dx <= +1; ++dx) {
                            final WalkPosition w = new WalkPosition(x + dx, y + dy);
                            if (advancedData.getMapData().isValid(w)) {
                                advancedData.getMiniTile_(w, check_t.no_check).SetWalkable(false);
                            }
                        }
                }
            }
    }

    @Override
    public void markBuildableTilesAndGroundHeight(final AdvancedData advancedData, final BWMap bwMap) {
        // Mark buildable tiles (tiles are unbuildable by default)
        for (int y = 0; y < advancedData.getMapData().getTileSize().getY(); ++y)
            for (int x = 0; x < advancedData.getMapData().getTileSize().getX(); ++x) {
                final TilePosition t = new TilePosition(x, y);
                if (bwMap.isBuildable(t, false)) {
                    advancedData.getTile_(t).SetBuildable();

                    // Ensures buildable ==> walkable:
                    for (int dy = 0; dy < 4; ++dy)
                        for (int dx = 0; dx < 4; ++dx) {
                            advancedData.getMiniTile_((t.toPosition().toWalkPosition()).add(new WalkPosition(dx, dy)), check_t.no_check).SetWalkable(true);
                        }
                }

                // Add groundHeight and doodad information:
                final int bwapiGroundHeight = bwMap.getGroundHeight(t);
                advancedData.getTile_(t).SetGroundHeight(bwapiGroundHeight / 2);
                if (bwapiGroundHeight % 2 != 0) {
                    advancedData.getTile_(t).SetDoodad();
                }
            }
    }

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::DecideSeasOrLakes
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void DecideSeasOrLakes(final AdvancedData advancedData, final int lake_max_miniTiles, final int lake_max_width_in_miniTiles) {
        for (int y = 0; y < advancedData.getMapData().getWalkSize().getY(); ++y) {
            for (int x = 0; x < advancedData.getMapData().getWalkSize().getX(); ++x) {
                final WalkPosition origin = new WalkPosition(x, y);
                final MiniTile Origin = advancedData.getMiniTile_(origin, check_t.no_check);
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
                        if (current.getX() > bottomRight.getX())
                            bottomRight = new WalkPosition(current.getX(), bottomRight.getY());
                        if (current.getY() > bottomRight.getY())
                            bottomRight = new WalkPosition(bottomRight.getX(), current.getY());

                        ToSearch.remove(ToSearch.size() - 1);
                        final WalkPosition deltas[] = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                        for (final WalkPosition delta : deltas) {
                            final WalkPosition next = current.add(delta);
                            if (advancedData.getMapData().isValid(next)) {
                                final MiniTile Next = advancedData.getMiniTile_(next, check_t.no_check);
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
                            (topLeft.getX() >= 2) && (topLeft.getY() >= 2) && (bottomRight.getX() < advancedData.getMapData().getWalkSize().getX() - 2) && (bottomRight.getY() < advancedData.getMapData().getWalkSize().getY() - 2)) {
                        for (final MiniTile pSea : SeaExtent) {
                            pSea.SetLake();
                        }
                    }
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::InitializeNeutrals
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void InitializeNeutrals(
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

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::ComputeAltitude
    ////////////////////////////////////////////////////////////////////////

    // Assigns MiniTile::m_altitude foar each miniTile having AltitudeMissing()
    // Cf. MiniTile::Altitude() for meaning of altitude_t.
    // Altitudes are computed using the straightforward Dijkstra's algorithm : the lower ones are computed first, starting from the seaside-miniTiles neighbours.
    // The point here is to precompute all possible altitudes for all possible tiles, and sort them.
    @Override
    public void ComputeAltitude(final AdvancedData advancedData) {
        final int altitude_scale = 8; // 8 provides a pixel definition for altitude_t, since altitudes are computed from miniTiles which are 8x8 pixels

        final List<MutablePair<WalkPosition, Altitude>> DeltasByAscendingAltitude
                = getSortedDeltasByAscendingAltitude(
                advancedData.getMapData().getWalkSize().getX(),
                advancedData.getMapData().getWalkSize().getY(),
                altitude_scale);

        final List<MutablePair<WalkPosition, Altitude>> ActiveSeaSides = getActiveSeaSideList(advancedData.getMapData());

        setAltitudes(advancedData, DeltasByAscendingAltitude, ActiveSeaSides, altitude_scale);
    }

    /**
     * 1) Fill in and sort DeltasByAscendingAltitude
     */
    @Override
    public List<MutablePair<WalkPosition, Altitude>> getSortedDeltasByAscendingAltitude(final int mapWalkTileWidth, final int mapWalkTileHeight, int altitude_scale) {
        final int range = Math.max(mapWalkTileWidth, mapWalkTileHeight) / 2 + 3; // should suffice for maps with no Sea.

        final List<MutablePair<WalkPosition, Altitude>> DeltasByAscendingAltitude = new ArrayList<>();

        for (int dy = 0; dy <= range; ++dy) {
            for (int dx = dy; dx <= range; ++dx) { // Only consider 1/8 of possible deltas. Other ones obtained by symmetry.
                if (dx != 0 || dy != 0) {
                    DeltasByAscendingAltitude.add(new MutablePair<>(
                            new WalkPosition(dx, dy),
                            new Altitude((int) (Double.valueOf("0.5") + (Utils.norm(dx, dy) * (double) altitude_scale)))
                    ));
                }
            }
        }

        Collections.sort(DeltasByAscendingAltitude, new PairGenericAltitudeComparator<>());

        return DeltasByAscendingAltitude;
    }

    /**
     * 2) Fill in ActiveSeaSideList, which basically contains all the seaside miniTiles (from which altitudes are to be computed)
     *    It also includes extra border-miniTiles which are considered as seaside miniTiles too.
     */
    @Override
    public List<MutablePair<WalkPosition, Altitude>> getActiveSeaSideList(final MapData mapData) {
        final List<MutablePair<WalkPosition, Altitude>> ActiveSeaSideList = new ArrayList<>();

        for (int y = -1; y <= mapData.getWalkSize().getX(); ++y) {
            for (int x = -1; x <= mapData.getWalkSize().getY(); ++x) {
                final WalkPosition w = new WalkPosition(x, y);
                if (!mapData.isValid(w) || BwemExt.seaSide(w, this)) {
                    ActiveSeaSideList.add(new MutablePair<>(w, new Altitude(0)));
                }
            }
        }

        return ActiveSeaSideList;
    }

    /**
     * 3) Dijkstra's algorithm to set altitude for mini tiles.
     */
    @Override
    public void setAltitudes(
            final AdvancedData advancedData,
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
                        if (advancedData.getMapData().isValid(w)) {
                            final MiniTile miniTile = advancedData.getMiniTile_(w, check_t.no_check);
                            if (miniTile.AltitudeMissing()) {
                                super.m_maxAltitude = new Altitude(altitude);
                                Current.right = new Altitude(altitude);
                                miniTile.SetAltitude(altitude);
                            }
                        }
                    }
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::ProcessBlockingNeutrals
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void ProcessBlockingNeutrals(final List<Neutral> Candidates) {
        for (final Neutral pCandidate : Candidates) {
            if (pCandidate.NextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top one
                final List<WalkPosition> Border = trimOuterMiniTileBorder(getOuterMiniTileBorderOfNeutral(pCandidate));

                final List<WalkPosition> Doors = getDoors(Border);

                final List<WalkPosition> TrueDoors = getTrueDoors(Doors, pCandidate);

                markBlockingStackedNeutrals(pCandidate, TrueDoors);
            }
        }
    }

    @Override
    public List<Neutral> getCandidates(
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

    //----------------------------------------------------------------------
    // 1)  Retrieve the Border: the outer border of pCandidate
    //----------------------------------------------------------------------

    @Override
    public List<WalkPosition> getOuterMiniTileBorderOfNeutral(final Neutral pCandidate) {
        return BwemExt.outerMiniTileBorder(pCandidate.TopLeft(), pCandidate.Size());
    }

    @Override
    public List<WalkPosition> trimOuterMiniTileBorder(final List<WalkPosition> Border) {
        Utils.really_remove_if(Border, new Pred() {
            @Override
            public boolean isTrue(Object... args) {
                WalkPosition w = (WalkPosition) args[0];
                return (!getData().getMapData().isValid(w)
                        || !getData().getMiniTile(w, check_t.no_check).Walkable()
                        || getData().getTile(w.toPosition().toTilePosition(), check_t.no_check).GetNeutral() != null);
            }
        });
        return Border;
    }

    //----------------------------------------------------------------------

    /**
     * 2)  Find the doors in Border: one door for each connected set of walkable, neighbouring miniTiles.
     *     The searched connected miniTiles all have to be next to some lake or some static building, though they can't be part of one.
     */
    @Override
    public List<WalkPosition> getDoors(final List<WalkPosition> Border) {
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
                    if (getData().getMapData().isValid(next) && !Visited.contains(next)) {
                        if (getData().getMiniTile(next, check_t.no_check).Walkable()) {
                            if (getData().getTile((next.toPosition()).toTilePosition(), check_t.no_check).GetNeutral() == null) {
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

    /**
     * 3)  If at least 2 doors, find the true doors in Border: a true door is a door that gives onto an area big enough
     */
    @Override
    public List<WalkPosition> getTrueDoors(final List<WalkPosition> Doors, final Neutral pCandidate) {
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
                        if (getData().getMapData().isValid(next) && !Visited.contains(next)) {
                            if (getData().getMiniTile(next, check_t.no_check).Walkable()) {
                                if (getData().getTile(next.toPosition().toTilePosition(), check_t.no_check).GetNeutral() == null) {
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

    /**
     * 4)  If at least 2 true doors, pCandidate is a blocking static building
     */
    @Override
    public void markBlockingStackedNeutrals(final Neutral pCandidate, final List<WalkPosition> TrueDoors) {
        if (TrueDoors.size() >= 2) {
            // Marks pCandidate (and any Neutral stacked with it) as blocking.
            for (Neutral pNeutral = getData().getTile(pCandidate.TopLeft()).GetNeutral(); pNeutral != null; pNeutral = pNeutral.NextStacked()) {
                pNeutral.SetBlocking(TrueDoors);
            }

            // Marks all the miniTiles of pCandidate as blocked.
            // This way, areas at TrueDoors won't merge together.
            final WalkPosition pCandidateW = pCandidate.Size().toPosition().toWalkPosition();
            for (int dy = 0; dy < pCandidateW.getY(); ++dy) {
                for (int dx = 0; dx < pCandidateW.getX(); ++dx) {
                    final MiniTile miniTile = getData().getMiniTile_(((pCandidate.TopLeft().toPosition()).toWalkPosition()).add(new WalkPosition(dx, dy)));
                    if (miniTile.Walkable()) {
                        miniTile.SetBlocked();
                    }
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::ComputeAreas
    ////////////////////////////////////////////////////////////////////////

    // Assigns MiniTile::m_areaId for each miniTile having AreaIdMissing()
    // Areas are computed using MiniTile::Altitude() information only.
    // The miniTiles are considered successively in descending order of their Altitude().
    // Each of them either:
    //   - involves the creation of a new area.
    //   - is added to some existing neighbouring area.
    //   - makes two neighbouring areas merge together.
    @Override
    public void ComputeAreas(final List<TempAreaInfo> TempAreaList, final int area_min_miniTiles) {
        CreateAreas(TempAreaList, area_min_miniTiles);
        SetAreaIdAndMinAltitudeInTiles();
    }

    @Override
    public List<MutablePair<WalkPosition, MiniTile>> getSortedMiniTilesByDescendingAltitude() {
        final List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude = new ArrayList<>();

        for (int y = 0; y < getData().getMapData().getWalkSize().getY(); ++y) {
            for (int x = 0; x < getData().getMapData().getWalkSize().getX(); ++x) {
                final WalkPosition w = new WalkPosition(x, y);
                final MiniTile miniTile = getData().getMiniTile_(w, check_t.no_check);
                if (miniTile.AreaIdMissing()) {
                    MiniTilesByDescendingAltitude.add(new MutablePair<>(w, miniTile));
                }
            }
        }

        Collections.sort(MiniTilesByDescendingAltitude, new PairGenericMiniTileAltitudeComparator<>());
        Collections.reverse(MiniTilesByDescendingAltitude);

        return MiniTilesByDescendingAltitude;
    }

    @Override
    public List<TempAreaInfo> ComputeTempAreas(final List<MutablePair<WalkPosition, MiniTile>> MiniTilesByDescendingAltitude) {
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
                for (final TilePosition startingLoc : getData().getMapData().getStartingLocations()) {
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
                    super.m_RawFrontier.add(new MutablePair<>(neighboringAreas, pos));
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

    @Override
    public void ReplaceAreaIds(final WalkPosition p, final AreaId newAreaId) {
        final MiniTile Origin = getData().getMiniTile_(p, check_t.no_check);
        final AreaId oldAreaId = Origin.AreaId();
        Origin.ReplaceAreaId(newAreaId);

        List<WalkPosition> ToSearch = new ArrayList<>();
        ToSearch.add(p);
        while (!ToSearch.isEmpty()) {
            final WalkPosition current = ToSearch.remove(ToSearch.size() - 1);

            final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
            for (final WalkPosition delta : deltas) {
                final WalkPosition next = current.add(delta);
                if (getData().getMapData().isValid(next)) {
                    final MiniTile Next = getData().getMiniTile_(next, check_t.no_check);
                    if (Next.AreaId().equals(oldAreaId)) {
                        ToSearch.add(next);
                        Next.ReplaceAreaId(newAreaId);
                    }
                }
            }
        }

        // also replaces references of oldAreaId by newAreaId in m_RawFrontier:
        if (newAreaId.intValue() > 0) {
            for (final MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f : super.m_RawFrontier) {
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
    @Override
    public void CreateAreas(final List<TempAreaInfo> TempAreaList, final int area_min_miniTiles) {
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

    // Renamed from "MapImpl::SetAltitudeInTile"
    @Override
    public void SetMinAltitudeInTile(final TilePosition t) {
        Altitude minAltitude = new Altitude(Integer.MAX_VALUE);

        for (int dy = 0; dy < 4; ++dy)
            for (int dx = 0; dx < 4; ++dx) {
                final Altitude altitude = new Altitude(getData().getMiniTile(((t.toPosition()).toWalkPosition()).add(new WalkPosition(dx, dy)), check_t.no_check).Altitude());
                if (altitude.intValue() < minAltitude.intValue()) {
                    minAltitude = new Altitude(altitude);
                }
            }

        getData().getTile_(t).SetMinAltitude(minAltitude);
    }

    // Renamed from "MapImpl::SetAreaIdInTiles"
    private void SetAreaIdAndMinAltitudeInTiles() {
        for (int y = 0; y < getData().getMapData().getTileSize().getY(); ++y)
            for (int x = 0; x < getData().getMapData().getTileSize().getX(); ++x) {
                final TilePosition t = new TilePosition(x, y);
                SetAreaIdInTile(t);
                SetMinAltitudeInTile(t);
            }
    }

    ////////////////////////////////////////////////////////////////////////



    @Override
    public List<PlayerUnit> filterPlayerUnits(final Collection<Unit> units, final Player player) {
        return super.filterPlayerUnits(units, player);
    }

    @Override
    public List<PlayerUnit> filterNeutralPlayerUnits(final Collection<Unit> units, final Collection<Player> players) {
        return super.filterNeutralPlayerUnits(units, players);
    }

}
