package bwem.area;

import bwem.Base;
import bwem.BaseImpl;
import bwem.CheckMode;
import bwem.ChokePoint;
import bwem.Markable;
import bwem.StaticMarkable;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.map.AdvancedData;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import bwem.typedef.Altitude;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import bwem.unit.Neutral;
import bwem.unit.Resource;
import bwem.unit.StaticBuilding;
import bwem.util.BwemExt;
import bwem.util.Utils;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AreaInitializerImpl extends AreaImpl implements AreaInitializer {

    private static final StaticMarkable staticMarkable = new StaticMarkable();
    private final Markable markable;

    private final Map map;

    public AreaInitializerImpl(final Map map, final AreaId areaId, final WalkPosition top, final int miniTileCount) {
        super(areaId, top, miniTileCount);

        this.map = map;

        this.markable = new Markable(staticMarkable);

//        bwem_assert(areaId > 0);
        if (!(areaId.intValue() > 0)) {
            throw new IllegalArgumentException();
        }

        final MiniTile topMiniTile = getMap().getData().getMiniTile(top);
//        bwem_assert(topMiniTile.AreaId() == areaId);
        if (!(topMiniTile.getAreaId().equals(areaId))) {
            throw new IllegalStateException("assert failed: topMiniTile.AreaId().equals(areaId): expected: " + topMiniTile.getAreaId().intValue() + ", actual: " + areaId.intValue());
        }

        super.highestAltitude = new Altitude(topMiniTile.getAltitude());
    }

    public static StaticMarkable getStaticMarkable() {
        return staticMarkable;
    }

    @Override
    public Markable getMarkable() {
        return this.markable;
    }

    @Override
    public void addChokePoints(final Area area, final List<ChokePoint> chokePoints) {
//        bwem_assert(!getChokePointsByArea[pArea] && pChokePoints);
        if (!(super.chokePointsByArea.get(area) == null && chokePoints != null)) {
            throw new IllegalArgumentException();
        }

        super.chokePointsByArea.put(area, chokePoints);

        super.chokePoints.addAll(chokePoints);
    }

    @Override
    public void addMineral(final Mineral mineral) {
//        bwem_assert(pMineral && !contains (minerals, pMineral));
        if (!(mineral != null && !super.minerals.contains(mineral))) {
            throw new IllegalStateException();
        }
        super.minerals.add(mineral);
    }

    @Override
    public void addGeyser(final Geyser geyser) {
//        bwem_assert(pGeyser && !contains (geysers, pGeyser));
        if (!(geyser != null && !super.geysers.contains(geyser))) {
            throw new IllegalStateException();
        }
        super.geysers.add(geyser);
    }

    @Override
    public void addTileInformation(final TilePosition tilePosition, final Tile tile) {
        ++super.tileCount;

        if (tile.isBuildable()) ++super.buildableTileCount;

        if (tile.getGroundHeight() == Tile.GroundHeight.HIGH_GROUND) {
            ++super.highGroundTileCount;
        } else if (tile.getGroundHeight() == Tile.GroundHeight.VERY_HIGH_GROUND) {
            ++super.veryHighGroundTileCount;
        }

        if (tilePosition.getX() < super.topLeft.getX()) {
            super.topLeft = new TilePosition(tilePosition.getX(), super.topLeft.getY());
        }
        if (tilePosition.getY() < super.topLeft.getY()) {
            super.topLeft = new TilePosition (super.topLeft.getX(), tilePosition.getY());
        }
        if (tilePosition.getX() > super.bottomRight.getX()) {
            super.bottomRight = new TilePosition(tilePosition.getX(), super.bottomRight.getY());
        }
        if (tilePosition.getY() > super.bottomRight.getY()) {
            super.bottomRight = new TilePosition (super.bottomRight.getX(), tilePosition.getY());
        }
    }

    @Override
    public void setGroupId(final GroupId gid) {
//        bwem_assert(gid >= 1);
        if (!(gid.intValue() >= 1)) {
            throw new IllegalArgumentException();
        }
        super.groupId = gid;
    }

    @Override
    public Map getMap() {
        return this.map;
    }

    @Override
    public void postCollectInformation() {
        /* Do nothing. This function is blank in the original BWEM 1.4.1 */
    }

    @Override
    public int[] computeDistances(final ChokePoint startCP, final List<ChokePoint> targetCPs) {
//        bwem_assert(!contains(targetCPs, startCP));
        if (targetCPs.contains(startCP)) {
            throw new IllegalStateException();
        }

        final TilePosition start = getMap().breadthFirstSearch(
                startCP.getNodePositionInArea(ChokePoint.Node.MIDDLE, this).toTilePosition(),
                // findCond
                args -> {
                    final Object ttile = args[0];
                    if (ttile instanceof Tile) {
                        final Tile tile = (Tile) ttile;
                        return tile.getAreaId().equals(getId());
                    } else {
                        throw new IllegalArgumentException();
                    }
                },
                // visitCond
                args -> true
        );

        final List<TilePosition> targets = new ArrayList<>();
        for (final ChokePoint cp : targetCPs) {
            final TilePosition t = getMap().breadthFirstSearch(
                    cp.getNodePositionInArea(ChokePoint.Node.MIDDLE, this).toTilePosition(),
                    // findCond
                    args -> {
                        final Object ttile = args[0];
                        if (ttile instanceof Tile) {
                            final Tile tile = (Tile) ttile;
                            return (tile.getAreaId().equals(getId()));
                        } else {
                            throw new IllegalArgumentException();
                        }
                    },
                    // visitCond
                    args -> true
            );
            targets.add(t);
        }

        return computeDistances(start, targets);
    }

    @Override
    public int[] computeDistances(final TilePosition start, final List<TilePosition> targets) {
        final int[] distances = new int[targets.size()];

        TileImpl.getStaticMarkable().unmarkAll();

        final Queue<Pair<Integer, TilePosition>> toVisit = new PriorityQueue<>(Comparator.comparingInt(Pair::getFirst)); // a priority queue holding the tiles to visit ordered by their distance to start.
        toVisit.offer(new Pair<>(0, start));

        int remainingTargets = targets.size();
        while (!toVisit.isEmpty()) {
            final Pair<Integer, TilePosition> distanceAndTilePosition = toVisit.poll();
            final int currentDist = distanceAndTilePosition.getFirst();
            final TilePosition current = distanceAndTilePosition.getSecond();
            final Tile currentTile = getMap().getData().getTile(current, CheckMode.NO_CHECK);
//            bwem_assert(currentTile.InternalData() == currentDist);
            if (!(((TileImpl) currentTile).getInternalData() == currentDist)) {
                throw new IllegalStateException("currentTile.InternalData().intValue()=" + ((TileImpl) currentTile).getInternalData() + ", currentDist=" + currentDist);
            }
            ((TileImpl) currentTile).setInternalData(0); // resets Tile::m_internalData for future usage
            ((TileImpl) currentTile).getMarkable().setMarked();

            for (int i = 0; i < targets.size(); ++i) {
                if (current.equals(targets.get(i))) {
                    distances[i] = (int) (0.5 + (currentDist * 32.0 /10000.0));
                    --remainingTargets;
                }
            }
            if (remainingTargets == 0) {
                break;
            }

            final TilePosition[] deltas = {
                    new TilePosition(-1, -1), new TilePosition(0, -1), new TilePosition(+1, -1),
                    new TilePosition(-1,  0),                          new TilePosition(+1,  0),
                    new TilePosition(-1, +1), new TilePosition(0, +1), new TilePosition(+1, +1)
            };
            for (final TilePosition delta : deltas) {
                final boolean diagonalMove = (delta.getX() != 0) && (delta.getY() != 0);
                final int newNextDist = currentDist + (diagonalMove ? 14142 : 10000);

                final TilePosition next = current.add(delta);
                if (getMap().getData().getMapData().isValid(next)) {
                    final Tile nextTile = getMap().getData().getTile(next, CheckMode.NO_CHECK);
                    if (!((TileImpl) nextTile).getMarkable().isMarked()) {
                        if (((TileImpl) nextTile).getInternalData() != 0) { // next already in toVisit
                            if (newNextDist < ((TileImpl) nextTile).getInternalData()) { // nextNewDist < nextOldDist
                                // To update next's distance, we need to remove-insert it from toVisit:
//                                bwem_assert(iNext != range.second);
                                final boolean removed = toVisit.remove(new Pair<>(((TileImpl) nextTile).getInternalData(), next));
                                if (!removed) {
                                    throw new IllegalStateException();
                                }
                                ((TileImpl) nextTile).setInternalData(newNextDist);
                                toVisit.offer(new Pair<>(newNextDist, next));
                            }
                        } else if ((nextTile.getAreaId().equals(getId())) || (nextTile.getAreaId().equals(new AreaId(-1)))) {
                            ((TileImpl) nextTile).setInternalData(newNextDist);
                            toVisit.offer(new Pair<>(newNextDist, next));
                        }
                    }
                }
            }
        }

//        bwem_assert(!remainingTargets);
        if (!(remainingTargets == 0)) {
            throw new IllegalStateException();
        }

        for (final Pair<Integer, TilePosition> distanceAndTilePosition : toVisit) {
            final TileImpl tileToUpdate = ((TileImpl) getMap().getData().getTile(distanceAndTilePosition.getSecond(), CheckMode.NO_CHECK));
            tileToUpdate.setInternalData(0);
        }

        return distances;
    }

    @Override
    public void updateAccessibleNeighbors() {
        super.accessibleNeighbors.clear();
        for (final Area area : getChokePointsByArea().keySet()) {
            for (final ChokePoint cp : getChokePointsByArea().get(area)) {
                if (!cp.isBlocked()) {
                    super.accessibleNeighbors.add(area);
                    break;
                }
            }
        }
    }

    @Override
    public void createBases(final AdvancedData mapAdvancedData) {
        final TilePosition resourceDepotDimensions = UnitType.Terran_Command_Center.tileSize();

        final List<Resource> remainingResources = new ArrayList<>();

        for (final Mineral mineral : getMinerals()) {
            if ((mineral.getInitialAmount() >= 40) && !mineral.isBlocking()) {
                remainingResources.add(mineral);
            }
        }

        for (final Geyser geyser : getGeysers()) {
            if ((geyser.getInitialAmount() >= 300) && !geyser.isBlocking()) {
                remainingResources.add(geyser);
            }
        }

        while (!remainingResources.isEmpty()) {
            // 1) Calculate the SearchBoundingBox (needless to search too far from the remainingResources):
            TilePosition topLeftResources = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
            TilePosition bottomRightResources = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
            for (final Resource r : remainingResources) {
                final Pair<TilePosition, TilePosition> pair1 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.getTopLeft());
                topLeftResources = pair1.getFirst();
                bottomRightResources = pair1.getSecond();

                final Pair<TilePosition, TilePosition> pair2 = BwemExt.makeBoundingBoxIncludePoint(topLeftResources, bottomRightResources, r.getBottomRight());
                topLeftResources = pair2.getFirst();
                bottomRightResources = pair2.getSecond();
            }

            final TilePosition dimensionsBetweenResourceDepotAndResources = new TilePosition(BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES, BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES);
            TilePosition topLeftSearchBoundingBox = topLeftResources.subtract(resourceDepotDimensions).subtract(dimensionsBetweenResourceDepotAndResources);
            TilePosition bottomRightSearchBoundingBox = bottomRightResources.add(new TilePosition(1, 1)).add(dimensionsBetweenResourceDepotAndResources);
            topLeftSearchBoundingBox = BwemExt.makePointFitToBoundingBox(topLeftSearchBoundingBox, getTopLeft(), getBottomRight().subtract(resourceDepotDimensions).add(new TilePosition(1, 1)));
            bottomRightSearchBoundingBox = BwemExt.makePointFitToBoundingBox(bottomRightSearchBoundingBox, getTopLeft(), getBottomRight().subtract(resourceDepotDimensions).add(new TilePosition(1, 1)));

            // 2) Mark the Tiles with their distances from each remaining Resource (Potential Fields >= 0)
            for (final Resource r : remainingResources)
                for (int dy = -resourceDepotDimensions.getY() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dy < r.getSize().getY() + resourceDepotDimensions.getY() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dy)
                    for (int dx = -resourceDepotDimensions.getX() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dx < r.getSize().getX() + resourceDepotDimensions.getX() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dx) {
                        final TilePosition deltaTilePosition = r.getTopLeft().add(new TilePosition(dx, dy));
                        if (mapAdvancedData.getMapData().isValid(deltaTilePosition)) {
                            final Tile tile = mapAdvancedData.getTile(deltaTilePosition, CheckMode.NO_CHECK);
                            int dist = (BwemExt.distToRectangle(BwemExt.center(deltaTilePosition), r.getTopLeft().toPosition(), r.getSize().toPosition()) + (TilePosition.SIZE_IN_PIXELS / 2)) / TilePosition.SIZE_IN_PIXELS;
                            int score = Math.max(BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES + 3 - dist, 0);
                            if (r instanceof Geyser) {
                                // somewhat compensates for Geyser alone vs the several minerals
                                score *= 3;
                            }
                            if (tile.getAreaId().equals(getId())) {
                                // note the additive effect (assume tile.InternalData() is 0 at the beginning)
                                ((TileImpl) tile).setInternalData(((TileImpl) tile).getInternalData() + score);
                            }
                        }
                    }

            // 3) Invalidate the 7 x 7 Tiles around each remaining Resource (Starcraft rule)
            for (final Resource r : remainingResources)
                for (int dy = -3; dy < r.getSize().getY() + 3; ++dy)
                    for (int dx = -3; dx < r.getSize().getX() + 3; ++dx) {
                        final TilePosition deltaTilePosition = r.getTopLeft().add(new TilePosition(dx, dy));
                        if (mapAdvancedData.getMapData().isValid(deltaTilePosition)) {
                            final Tile tileToUpdate = mapAdvancedData.getTile(deltaTilePosition, CheckMode.NO_CHECK);
                            ((TileImpl) tileToUpdate).setInternalData(-1);
                        }
                    }


            // 4) Search the best location inside the SearchBoundingBox:
            TilePosition bestLocation = null;
            int bestScore = 0;
            final List<Mineral> blockingMinerals = new ArrayList<>();

            for (int y = topLeftSearchBoundingBox.getY(); y <= bottomRightSearchBoundingBox.getY(); ++y)
                for (int x = topLeftSearchBoundingBox.getX(); x <= bottomRightSearchBoundingBox.getX(); ++x) {
                    final int score = computeBaseLocationScore(mapAdvancedData, new TilePosition(x, y));
                    if (score > bestScore) {
                        if (validateBaseLocation(mapAdvancedData, new TilePosition(x, y), blockingMinerals)) {
                            bestScore = score;
                            bestLocation = new TilePosition(x, y);
                        }
                    }
                }

            // 5) Clear Tile::m_internalData (required due to our use of Potential Fields: see comments in 2))
            for (Resource r : remainingResources) {
                for (int dy = -resourceDepotDimensions.getY() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dy < r.getSize().getY() + resourceDepotDimensions.getY() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dy)
                    for (int dx = -resourceDepotDimensions.getX() - BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; dx < r.getSize().getX() + resourceDepotDimensions.getX() + BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES; ++dx) {
                        final TilePosition deltaTilePosition = r.getTopLeft().add(new TilePosition(dx, dy));
                        if (mapAdvancedData.getMapData().isValid(deltaTilePosition)) {
                            final Tile tileToUpdate = mapAdvancedData.getTile(deltaTilePosition, CheckMode.NO_CHECK);
                            ((TileImpl) tileToUpdate).setInternalData(0);
                        }
                    }
            }

            if (bestScore == 0) {
                break;
            }

            // 6) Create a new Base at bestLocation, assign to it the relevant resources and remove them from RemainingResources:
            final List<Resource> assignedResources = new ArrayList<>();
            for (final Resource r : remainingResources) {
                if (BwemExt.distToRectangle(r.getCenter(), bestLocation.toPosition(), resourceDepotDimensions.toPosition()) + 2 <= BwemExt.MAX_TILES_BETWEEN_COMMAND_CENTER_AND_RESOURCES * TilePosition.SIZE_IN_PIXELS) {
                    assignedResources.add(r);
                }
            }

//            for (int i = 0; i < remainingResources.size(); ++i) {
//                Resource r = remainingResources.get(i);
//                if (assignedResources.contains(r)) {
//                    remainingResources.remove(i--);
//                }
//            }
            Utils.reallyRemoveIf(remainingResources, args -> {
                final Object tresource = args[0];
                if (tresource instanceof Resource) {
                    final Resource resource = (Resource) tresource;
                    return assignedResources.contains(resource);
                } else {
                    throw new IllegalStateException();
                }
            });

            if (assignedResources.isEmpty()) {
                break;
            }

            super.bases.add(new BaseImpl(this, bestLocation, assignedResources, blockingMinerals));
        }
    }

    @Override
    public int computeBaseLocationScore(final AdvancedData mapAdvancedData, final TilePosition location) {
        final TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        int sumScore = 0;
        for (int dy = 0; dy < dimCC.getY(); ++dy)
            for (int dx = 0; dx < dimCC.getX(); ++dx) {
                final Tile tile = mapAdvancedData.getTile(location.add(new TilePosition(dx, dy)), CheckMode.NO_CHECK);
                if (!tile.isBuildable()) {
                    return -1;
                }
                if (((TileImpl) tile).getInternalData() == -1) {
                    // The special value InternalData() == -1 means there is some resource at maximum 3 tiles, which Starcraft rules forbid.
                    // Unfortunately, this is guaranteed only for the resources in this Area, which is the very reason of validateBaseLocation
                    return -1;
                }
                if (!tile.getAreaId().equals(getId())){
                    return -1;
                }
                if (tile.getNeutral() != null && (tile.getNeutral() instanceof StaticBuilding)) {
                    return -1;
                }

                sumScore += ((TileImpl) tile).getInternalData();
            }

        return sumScore;
    }

    @Override
    public boolean validateBaseLocation(final AdvancedData mapAdvancedData, final TilePosition location, final List<Mineral> blockingMinerals) {
        final TilePosition dimCC = UnitType.Terran_Command_Center.tileSize();

        blockingMinerals.clear();

        for (int dy = -3; dy < dimCC.getY() + 3; ++dy)
            for (int dx = -3; dx < dimCC.getX() + 3; ++dx) {
                final TilePosition deltaLocation = location.add(new TilePosition(dx, dy));
                if (mapAdvancedData.getMapData().isValid(deltaLocation)) {
                    final Tile deltaTile = mapAdvancedData.getTile(deltaLocation, CheckMode.NO_CHECK);
                    final Neutral deltaTileNeutral = deltaTile.getNeutral();
                    if (deltaTileNeutral != null) {
                        if (deltaTileNeutral instanceof Geyser) {
                            return false;
                        } else if (deltaTileNeutral instanceof Mineral) {
                            final Mineral deltaTileMineral = (Mineral) deltaTileNeutral;
                            if (deltaTileMineral.getInitialAmount() <= 8) {
                                blockingMinerals.add(deltaTileMineral);
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }

        // checks the distance to the bases already created:
        for (final Base base : getBases()) {
            if (BwemExt.roundedDist(base.getLocation(), location) < BwemExt.min_tiles_between_Bases) {
                return false;
            }
        }

        return true;
    }

}
