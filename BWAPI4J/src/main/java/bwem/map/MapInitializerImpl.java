// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic 
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License. 
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.map;

import bwem.CheckMode;
import bwem.area.TempAreaInfo;
import bwem.area.typedef.AreaId;
import bwem.tile.*;
import bwem.typedef.Altitude;
import bwem.unit.*;
import bwem.util.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.*;
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
    public void initialize(final boolean enableTimer) {
        final Timer overallTimer = new Timer();
        final Timer timer = new Timer();

        initializeTerrainData(getBWMap().mapWidth(), getBWMap().mapHeight(), getBWMap().getStartPositions());
//    ///	bw << "Map::initialize-resize: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::initialize-resize: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        // Computes walkability, buildability and groundHeight and doodad information, using BWAPI corresponding functions
        ((TerrainDataInitializer) getData()).markUnwalkableMiniTiles(getBWMap());
        ((TerrainDataInitializer) getData()).markBuildableTilesAndGroundHeight(getBWMap());
//    ///	bw << "Map::LoadData: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::LoadData: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }
//
        ((TerrainDataInitializer) getData()).decideSeasOrLakes(BwemExt.lake_max_miniTiles, BwemExt.lake_max_width_in_miniTiles);
//    ///	bw << "Map::DecideSeasOrLakes: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::DecideSeasOrLakes: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        initializeNeutralData(
                super.mineralPatches,
                super.vespeneGeysers,
                filterNeutralPlayerUnits(super.units, super.players)
        );
//    ///	bw << "Map::InitializeNeutrals: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::InitializeNeutrals: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        computeAltitude(getData());
//    ///	bw << "Map::ComputeAltitude: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::ComputeAltitude: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        processBlockingNeutrals(getCandidates(getNeutralData().getStaticBuildings(), getNeutralData().getMinerals()));
//    ///	bw << "Map::processBlockingNeutrals: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::processBlockingNeutrals: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        computeAreas(computeTempAreas(getSortedMiniTilesByDescendingAltitude()), BwemExt.area_min_miniTiles);
//    ///	bw << "Map::computeAreas: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::computeAreas: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        getGraph().createChokePoints(getNeutralData().getStaticBuildings(), getNeutralData().getMinerals(), getRawFrontier());
//    ///	bw << "Graph::createChokePoints: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::createChokePoints: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        getGraph().computeChokePointDistanceMatrix();
//    ///	bw << "Graph::computeChokePointDistanceMatrix: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::computeChokePointDistanceMatrix: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        getGraph().collectInformation();
//    ///	bw << "Graph::collectInformation: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::collectInformation: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

        getGraph().createBases(getData());
//    ///	bw << "Graph::createBases: " << timer.elapsedMilliseconds() << " ms" << endl; timer.reset();
        if (enableTimer) { logger.info("Map::createBases: " + timer.elapsedMilliseconds() + " ms"); timer.reset(); }

//    ///	bw << "Map::initialize: " << overallTimer.elapsedMilliseconds() << " ms" << endl;
        if (enableTimer) { logger.info("Map::initialize Total: " + overallTimer.elapsedMilliseconds() + " ms"); timer.reset(); }
    }

    @Override
    public void initializeTerrainData(final int mapTileWidth, final int mapTileHeight, final List<TilePosition> startingLocations) {
        final MapData mapData = new MapDataImpl(mapTileWidth, mapTileHeight, startingLocations);
        final TileData tileData = new TileDataImpl(
                mapData.getTileSize().getX() * mapData.getTileSize().getY(),
                mapData.getWalkSize().getX() * mapData.getWalkSize().getY()
        );
        super.terrainData = new TerrainDataInitializerImpl(mapData, tileData);
    }



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::InitializeNeutrals
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void initializeNeutralData(
            final List<MineralPatch> mineralPatches,
            final List<VespeneGeyser> vespeneGeysers,
            final List<PlayerUnit> neutralUnits
    ) {
        super.neutralData = new NeutralDataImpl(this, mineralPatches, vespeneGeysers, neutralUnits);
    }

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::ComputeAltitude
    ////////////////////////////////////////////////////////////////////////

    // Assigns MiniTile::m_altitude foar each miniTile having AltitudeMissing()
    // Cf. MiniTile::Altitude() for meaning of altitude_t.
    // Altitudes are computed using the straightforward Dijkstra's algorithm : the lower ones are computed first, starting from the seaside-miniTiles neighbors.
    // The point here is to precompute all possible altitudes for all possible tiles, and sort them.
    @Override
    public void computeAltitude(final TerrainData terrainData) {
        final int altitudeScale = 8; // 8 provides a pixel definition for altitude_t, since altitudes are computed from miniTiles which are 8x8 pixels

        final List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude
                = getSortedDeltasByAscendingAltitude(
                terrainData.getMapData().getWalkSize().getX(),
                terrainData.getMapData().getWalkSize().getY(),
                altitudeScale);

        final List<MutablePair<WalkPosition, Altitude>> activeSeaSides = getActiveSeaSideList(terrainData);

        setHighestAltitude(setAltitudesAndGetUpdatedHighestAltitude(getHighestAltitude(), terrainData, deltasByAscendingAltitude, activeSeaSides, altitudeScale));
    }

    /**
     * 1) Fill in and sort DeltasByAscendingAltitude
     */
    @Override
    public List<MutablePair<WalkPosition, Altitude>> getSortedDeltasByAscendingAltitude(final int mapWalkTileWidth, final int mapWalkTileHeight, int altitudeScale) {
        final int range = Math.max(mapWalkTileWidth, mapWalkTileHeight) / 2 + 3; // should suffice for maps with no Sea.

        final List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude = new ArrayList<>();

        for (int dy = 0; dy <= range; ++dy) {
            for (int dx = dy; dx <= range; ++dx) { // Only consider 1/8 of possible deltas. Other ones obtained by symmetry.
                if (dx != 0 || dy != 0) {
                    deltasByAscendingAltitude.add(new MutablePair<>(
                            new WalkPosition(dx, dy),
                            new Altitude((int) Math.round(Utils.norm(dx, dy) * altitudeScale))
                    ));
                }
            }
        }

        deltasByAscendingAltitude.sort(new PairGenericAltitudeComparator<>());

        return deltasByAscendingAltitude;
    }

    /**
     * 2) Fill in ActiveSeaSideList, which basically contains all the seaside miniTiles (from which altitudes are to be computed)
     *    It also includes extra border-miniTiles which are considered as seaside miniTiles too.
     */
    @Override
    public List<MutablePair<WalkPosition, Altitude>> getActiveSeaSideList(final TerrainData terrainData) {
        final List<MutablePair<WalkPosition, Altitude>> activeSeaSideList = new ArrayList<>();

        for (int y = -1; y <= terrainData.getMapData().getWalkSize().getY(); ++y) {
            for (int x = -1; x <= terrainData.getMapData().getWalkSize().getX(); ++x) {
                final WalkPosition walkPosition = new WalkPosition(x, y);
                if (!terrainData.getMapData().isValid(walkPosition) || terrainData.isSeaWithNonSeaNeighbors(walkPosition)) {
                    activeSeaSideList.add(new MutablePair<>(walkPosition, Altitude.ZERO));
                }
            }
        }

        return activeSeaSideList;
    }

    //----------------------------------------------------------------------
    // 3) Dijkstra's algorithm to set altitude for mini tiles.
    //----------------------------------------------------------------------

    @Override
    public Altitude setAltitudesAndGetUpdatedHighestAltitude(
            final Altitude currentHighestAltitude,
            final TerrainData terrainData,
            final List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude,
            final List<MutablePair<WalkPosition, Altitude>> activeSeaSideList,
            final int altitudeScale
    ) {
        Altitude updatedHighestAltitude = currentHighestAltitude;

        for (final MutablePair<WalkPosition, Altitude> deltaAltitude : deltasByAscendingAltitude) {
            final WalkPosition d = deltaAltitude.getLeft();
            final Altitude altitude = deltaAltitude.getRight();

            for (int i = 0; i < activeSeaSideList.size(); ++i) {
                final MutablePair<WalkPosition, Altitude> current = activeSeaSideList.get(i);
                if (altitude.intValue() - current.getRight().intValue() >= 2 * altitudeScale) {
                    // optimization : once a seaside miniTile verifies this condition,
                    // we can throw it away as it will not generate min altitudes anymore
                    Utils.fastErase(activeSeaSideList, i--);
                } else {
                    final WalkPosition[] deltas = {new WalkPosition(d.getX(), d.getY()), new WalkPosition(-d.getX(), d.getY()), new WalkPosition(d.getX(), -d.getY()), new WalkPosition(-d.getX(), -d.getY()),
                                                   new WalkPosition(d.getY(), d.getX()), new WalkPosition(-d.getY(), d.getX()), new WalkPosition(d.getY(), -d.getX()), new WalkPosition(-d.getY(), -d.getX())};
                    for (final WalkPosition delta : deltas) {
                        final WalkPosition w = current.getLeft().add(delta);
                        if (terrainData.getMapData().isValid(w)) {
                            final MiniTile miniTile = ((TerrainDataInitializer) terrainData).getMiniTile_(w, CheckMode.NO_CHECK);
                            if (((MiniTileImpl) miniTile).isAltitudeMissing()) {
                                if (updatedHighestAltitude != null && updatedHighestAltitude.intValue() > altitude.intValue()) {
                                    throw new IllegalStateException();
                                }
                                updatedHighestAltitude = altitude;
                                current.setRight(altitude);
                                ((MiniTileImpl) miniTile).setAltitude(altitude);
                            }
                        }
                    }
                }
            }
        }

        return updatedHighestAltitude;
    }

    @Override
    public void setHighestAltitude(final Altitude altitude) {
        super.highestAltitude = altitude;
    }

    //----------------------------------------------------------------------

    ////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////
    // MapImpl::processBlockingNeutrals
    ////////////////////////////////////////////////////////////////////////

    @Override
    public void processBlockingNeutrals(final List<Neutral> candidates) {
        for (final Neutral pCandidate : candidates) {
            if (pCandidate.getNextStacked() == null) { // in the case where several neutrals are stacked, we only consider the top one
                final List<WalkPosition> border = trimOuterMiniTileBorder(getOuterMiniTileBorderOfNeutral(pCandidate));

                final List<WalkPosition> doors = getDoors(border);

                final List<WalkPosition> trueDoors = getTrueDoors(doors, pCandidate);

                markBlockingStackedNeutrals(pCandidate, trueDoors);
            }
        }
    }

    @Override
    public List<Neutral> getCandidates(
            final List<StaticBuilding> staticBuildings,
            final List<Mineral> minerals
    ) {
        final List<Neutral> candidates = new ArrayList<>();
        candidates.addAll(staticBuildings);
        candidates.addAll(minerals);
        return candidates;
    }

    //----------------------------------------------------------------------
    // 1)  Retrieve the Border: the outer border of pCandidate
    //----------------------------------------------------------------------

    @Override
    public List<WalkPosition> getOuterMiniTileBorderOfNeutral(final Neutral pCandidate) {
        return BwemExt.outerMiniTileBorder(pCandidate.getTopLeft(), pCandidate.getSize());
    }

    @Override
    public List<WalkPosition> trimOuterMiniTileBorder(final List<WalkPosition> border) {
        border.removeIf(w -> (!getData().getMapData().isValid(w)
                || !getData().getMiniTile(w, CheckMode.NO_CHECK).isWalkable()
                || getData().getTile(w.toTilePosition(), CheckMode.NO_CHECK).getNeutral() != null));
        return border;
    }

    //----------------------------------------------------------------------

    /**
     * 2)  Find the doors in border: one door for each connected set of walkable, neighboring miniTiles.
     *     The searched connected miniTiles all have to be next to some lake or some static building, though they can't be part of one.
     */
    @Override
    public List<WalkPosition> getDoors(final List<WalkPosition> border) {
        final List<WalkPosition> doors = new ArrayList<>();

        while (!border.isEmpty()) {
            final WalkPosition door = border.remove(border.size() - 1);
            doors.add(door);

            final List<WalkPosition> toVisit = new ArrayList<>();
            toVisit.add(door);

            final List<WalkPosition> visited = new ArrayList<>();
            visited.add(door);

            while (!toVisit.isEmpty()) {
                final WalkPosition current = toVisit.remove(toVisit.size() - 1);

                final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                for (final WalkPosition delta : deltas) {
                    final WalkPosition next = current.add(delta);
                    if (getData().getMapData().isValid(next) && !visited.contains(next)) {
                        if (getData().getMiniTile(next, CheckMode.NO_CHECK).isWalkable()) {
                            if (getData().getTile((next.toPosition()).toTilePosition(), CheckMode.NO_CHECK).getNeutral() == null) {
                                if (BwemExt.adjoins8SomeLakeOrNeutral(next, this)) {
                                    toVisit.add(next);
                                    visited.add(next);
                                }
                            }
                        }
                    }
                }
            }

            border.removeIf(visited::contains);
        }

        return doors;
    }

    /**
     * 3)  If at least 2 doors, find the true doors in Border: a true door is a door that gives onto an area big enough
     */
    @Override
    public List<WalkPosition> getTrueDoors(final List<WalkPosition> doors, final Neutral pCandidate) {
        final List<WalkPosition> trueDoors = new ArrayList<>();

        if (doors.size() >= 2) {
            for (final WalkPosition door : doors) {
                final List<WalkPosition> toVisit = new ArrayList<>();
                toVisit.add(door);

                final List<WalkPosition> visited = new ArrayList<>();
                visited.add(door);

                final int limit = (pCandidate instanceof StaticBuilding) ? 10 : 400; //TODO: Description for 10 and 400?

                while (!toVisit.isEmpty() && (visited.size() < limit)) {
                    final WalkPosition current = toVisit.remove(toVisit.size() - 1);
                    final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
                    for (final WalkPosition delta : deltas) {
                        final WalkPosition next = current.add(delta);
                        if (getData().getMapData().isValid(next) && !visited.contains(next)) {
                            if (getData().getMiniTile(next, CheckMode.NO_CHECK).isWalkable()) {
                                if (getData().getTile(next.toTilePosition(), CheckMode.NO_CHECK).getNeutral() == null) {
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

        return trueDoors;
    }

    /**
     * 4)  If at least 2 true doors, pCandidate is a blocking static building
     */
    @Override
    public void markBlockingStackedNeutrals(final Neutral pCandidate, final List<WalkPosition> trueDoors) {
        if (trueDoors.size() >= 2) {
            // Marks pCandidate (and any Neutral stacked with it) as blocking.
            for (Neutral pNeutral = getData().getTile(pCandidate.getTopLeft()).getNeutral(); pNeutral != null; pNeutral = pNeutral.getNextStacked()) {
                ((NeutralImpl) pNeutral).setBlocking(trueDoors);
            }

            // Marks all the miniTiles of pCandidate as blocked.
            // This way, areas at trueDoors won't merge together.
            final WalkPosition pCandidateW = pCandidate.getSize().toWalkPosition();
            for (int dy = 0; dy < pCandidateW.getY(); ++dy) {
                for (int dx = 0; dx < pCandidateW.getX(); ++dx) {
                    final MiniTile miniTile = ((TerrainDataInitializer) getData()).getMiniTile_(((pCandidate.getTopLeft().toPosition()).toWalkPosition()).add(new WalkPosition(dx, dy)));
                    if (miniTile.isWalkable()) {
                        ((MiniTileImpl) miniTile).setBlocked();
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
    //   - is added to some existing neighboring area.
    //   - makes two neighboring areas merge together.
    @Override
    public void computeAreas(final List<TempAreaInfo> tempAreaList, final int areaMinMiniTiles) {
        createAreas(tempAreaList, areaMinMiniTiles);
        setAreaIdAndLowestAltitudeInTiles();
    }

    @Override
    public List<MutablePair<WalkPosition, MiniTile>> getSortedMiniTilesByDescendingAltitude() {
        final List<MutablePair<WalkPosition, MiniTile>> miniTilesByDescendingAltitude = new ArrayList<>();

        for (int y = 0; y < getData().getMapData().getWalkSize().getY(); ++y) {
            for (int x = 0; x < getData().getMapData().getWalkSize().getX(); ++x) {
                final WalkPosition w = new WalkPosition(x, y);
                final MiniTile miniTile = ((TerrainDataInitializer) getData()).getMiniTile_(w, CheckMode.NO_CHECK);
                if (((MiniTileImpl) miniTile).isAreaIdMissing()) {
                    miniTilesByDescendingAltitude.add(new MutablePair<>(w, miniTile));
                }
            }
        }

        miniTilesByDescendingAltitude.sort(new PairGenericMiniTileAltitudeComparator<>());
        Collections.reverse(miniTilesByDescendingAltitude);

        return miniTilesByDescendingAltitude;
    }

    @Override
    public List<TempAreaInfo> computeTempAreas(final List<MutablePair<WalkPosition, MiniTile>> miniTilesByDescendingAltitude) {
        final List<TempAreaInfo> tempAreaList = new ArrayList<>();
        tempAreaList.add(new TempAreaInfo()); // tempAreaList[0] left unused, as AreaIds are > 0

        for (final MutablePair<WalkPosition, MiniTile> current : miniTilesByDescendingAltitude) {
            final WalkPosition pos = new WalkPosition(current.getLeft().getX(), current.getLeft().getY());
            final MiniTile cur = current.getRight();

            final MutablePair<AreaId, AreaId> neighboringAreas = findNeighboringAreas(pos);
            if (neighboringAreas.getLeft() == null) { // no neighboring area : creates of a new area
                tempAreaList.add(new TempAreaInfo(new AreaId(tempAreaList.size()), cur, pos));
            } else if (neighboringAreas.getRight() == null) { // one neighboring area : adds cur to the existing area
                tempAreaList.get(neighboringAreas.getLeft().intValue()).add(cur);
            } else { // two neighboring areas : adds cur to one of them  &  possible merging
                AreaId smaller = neighboringAreas.getLeft();
                AreaId bigger = neighboringAreas.getRight();
                if (tempAreaList.get(smaller.intValue()).getSize() > tempAreaList.get(bigger.intValue()).getSize()) {
                    AreaId smallerTmp = smaller;
                    smaller = bigger;
                    bigger = smallerTmp;
                }

                // Condition for the neighboring areas to merge:
//                any_of(StartingLocations().begin(), StartingLocations().end(), [&pos](const TilePosition & startingLoc)
//                    { return dist(TilePosition(pos), startingLoc + TilePosition(2, 1)) <= 3;})
                boolean cppAlgorithmStdAnyOf = getData().getMapData().getStartingLocations().stream().anyMatch(startingLoc ->
                        BwemExt.dist(pos.toTilePosition(), startingLoc.add(new TilePosition(2, 1))) <= 3.0);
                final int curAltitude = cur.getAltitude().intValue();
                final int biggerHighestAltitude = tempAreaList.get(bigger.intValue()).getHighestAltitude().intValue();
                final int smallerHighestAltitude = tempAreaList.get(smaller.intValue()).getHighestAltitude().intValue();
                if ((tempAreaList.get(smaller.intValue()).getSize() < 80)
                        || (smallerHighestAltitude < 80)
                        || ((double) curAltitude / (double) biggerHighestAltitude >= 0.90)
                        || ((double) curAltitude / (double) smallerHighestAltitude >= 0.90)
                        || cppAlgorithmStdAnyOf) {
                    // adds cur to the absorbing area:
                    tempAreaList.get(bigger.intValue()).add(cur);

                    // merges the two neighboring areas:
                    replaceAreaIds(tempAreaList.get(smaller.intValue()).getWalkPositionWithHighestAltitude(), bigger);
                    tempAreaList.get(bigger.intValue()).merge(tempAreaList.get(smaller.intValue()));
                } else { // no merge : cur starts or continues the frontier between the two neighboring areas
                    // adds cur to the chosen Area:
                    tempAreaList.get(chooseNeighboringArea(smaller, bigger).intValue()).add(cur);
                    super.rawFrontier.add(new MutablePair<>(neighboringAreas, pos));
                }
            }
        }

        // Remove from the frontier obsolete positions
        rawFrontier.removeIf(f -> f.getLeft().getLeft().equals(f.getLeft().getRight()));

        return tempAreaList;
    }

    @Override
    public void replaceAreaIds(final WalkPosition p, final AreaId newAreaId) {
        final MiniTile origin = ((TerrainDataInitializer) getData()).getMiniTile_(p, CheckMode.NO_CHECK);
        final AreaId oldAreaId = origin.getAreaId();
        ((MiniTileImpl) origin).replaceAreaId(newAreaId);

        List<WalkPosition> toSearch = new ArrayList<>();
        toSearch.add(p);
        while (!toSearch.isEmpty()) {
            final WalkPosition current = toSearch.remove(toSearch.size() - 1);

            final WalkPosition[] deltas = {new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(+1, 0), new WalkPosition(0, +1)};
            for (final WalkPosition delta : deltas) {
                final WalkPosition next = current.add(delta);
                if (getData().getMapData().isValid(next)) {
                    final MiniTile miniTile = ((TerrainDataInitializer) getData()).getMiniTile_(next, CheckMode.NO_CHECK);
                    if (miniTile.getAreaId().equals(oldAreaId)) {
                        toSearch.add(next);
                        ((MiniTileImpl) miniTile).replaceAreaId(newAreaId);
                    }
                }
            }
        }

        // also replaces references of oldAreaId by newAreaId in getRawFrontier:
        if (newAreaId.intValue() > 0) {
            for (final MutablePair<MutablePair<AreaId, AreaId>, WalkPosition> f : super.rawFrontier) {
                if (f.getLeft().getLeft().equals(oldAreaId)) {
                    f.getLeft().setLeft(newAreaId);
                }
                if (f.getLeft().getRight().equals(oldAreaId)) {
                    f.getLeft().setRight(newAreaId);
                }
            }
        }
    }

    // Initializes Graph with the valid and big enough areas in tempAreaList.
    @Override
    public void createAreas(final List<TempAreaInfo> tempAreaList, final int areaMinMiniTiles) {
        final List<MutablePair<WalkPosition, Integer>> areasList = new ArrayList<>();

        int newAreaId = 1;
        int newTinyAreaId = -2;

        for (final TempAreaInfo tempArea : tempAreaList) {
            if (tempArea.isValid()) {
                if (tempArea.getSize() >= areaMinMiniTiles) {
//                    bwem_assert(newAreaId <= tempArea.id());
                    if (!(newAreaId <= tempArea.getId().intValue())) {
                        throw new IllegalStateException();
                    }
                    if (newAreaId != tempArea.getId().intValue()) {
                        replaceAreaIds(tempArea.getWalkPositionWithHighestAltitude(), new AreaId(newAreaId));
                    }

                    areasList.add(new MutablePair<>(tempArea.getWalkPositionWithHighestAltitude(), tempArea.getSize()));
                    ++newAreaId;
                } else {
                    replaceAreaIds(tempArea.getWalkPositionWithHighestAltitude(), new AreaId(newTinyAreaId));
                    --newTinyAreaId;
                }
            }
        }

        getGraph().createAreas(areasList);
    }

    // Renamed from "MapImpl::SetAltitudeInTile"
    @Override
    public void setLowestAltitudeInTile(final TilePosition t) {
        Altitude lowestAltitude = new Altitude(Integer.MAX_VALUE);

        for (int dy = 0; dy < 4; ++dy)
            for (int dx = 0; dx < 4; ++dx) {
                final Altitude altitude = getData().getMiniTile(((t.toPosition()).toWalkPosition()).add(new WalkPosition(dx, dy)), CheckMode.NO_CHECK).getAltitude();
                if (altitude.intValue() < lowestAltitude.intValue()) {
                    lowestAltitude = altitude;
                }
            }

        ((TileImpl) ((TerrainDataInitializer) getData()).getTile_(t)).setLowestAltitude(lowestAltitude);
    }

    // Renamed from "MapImpl::SetAreaIdInTiles"
    private void setAreaIdAndLowestAltitudeInTiles() {
        for (int y = 0; y < getData().getMapData().getTileSize().getY(); ++y)
            for (int x = 0; x < getData().getMapData().getTileSize().getX(); ++x) {
                final TilePosition t = new TilePosition(x, y);
                setAreaIdInTile(t);
                setLowestAltitudeInTile(t);
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
