package bwem;

import bwem.area.Area;
import bwem.map.Map;
import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.typedef.CPPath;
import bwem.typedef.Index;
import bwem.unit.Neutral;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.WalkPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ChokePoints are frontiers that BWEM automatically computes from Brood War's maps.<br/>
 * A ChokePoint represents (part of) the frontier between exactly 2 Areas. It has a form of line.<br/>
 * A ChokePoint doesn't contain any MiniTile: All the MiniTiles whose positions are returned by its Geometry()
 * are just guaranteed to be part of one of the 2 Areas.<br/>
 * Among the MiniTiles of its Geometry, 3 particular ones called nodes can also be accessed using Pos(middle), Pos(end1) and Pos(end2).<br/>
 * ChokePoints play an important role in BWEM:<br/>
 * - they define accessibility between Areas.<br/>
 * - the Paths provided by Map::GetPath are made of ChokePoints.<br/>
 * Like Areas and Bases, the number and the addresses of ChokePoint instances remain unchanged.<br/>
 * <br/>
 * Pseudo ChokePoints:<br/>
 * Some Neutrals can be detected as blocking Neutrals (Cf. Neutral::Blocking).<br/>
 * Because only ChokePoints can serve as frontiers between Areas, BWEM automatically creates a ChokePoint
 * for each blocking Neutral (only one in the case of stacked blocking Neutral).<br/>
 * Such ChokePoints are called pseudo ChokePoints and they behave differently in several ways.
 */
public final class ChokePoint {

    /**
     * ChokePoint::middle denotes the "middle" MiniTile of Geometry(), while
     * ChokePoint::END_1 and ChokePoint::END_2 denote its "ends".
     * It is guaranteed that, among all the MiniTiles of Geometry(), ChokePoint::middle has the highest altitude value (Cf. MiniTile::Altitude()).
     */
    public enum Node {
        END1,
        MIDDLE,
        END2,
        NODE_COUNT
    }

    private final Graph graph;
    private final boolean isPseudo;
    private final Index index;
    private final ImmutablePair<Area, Area> areas;
    private final WalkPosition[] nodes;
    private final List<MutablePair<WalkPosition, WalkPosition>> nodesInArea;
    private final List<WalkPosition> geometry;
    private boolean isBlocked;
    private Neutral blockingNeutral;
    private ChokePoint pathBackTrace = null;

    public ChokePoint(
            final Graph graph,
            final Index index,
            final Area area1,
            final Area area2,
            final List<WalkPosition> geometry,
            final Neutral blockingNeutral) {

//        bwem_assert(!geometry.empty());
        if (geometry.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.graph = graph;
        this.index = index;
        this.areas = new ImmutablePair<>(area1, area2);
        this.geometry = geometry;

        // Ensures that in the case where several neutrals are stacked, blockingNeutral points to the bottom one:
        this.blockingNeutral = blockingNeutral != null
                ? getMap().getData().getTile(blockingNeutral.getTopLeft()).getNeutral()
                : blockingNeutral;

        this.isBlocked = blockingNeutral != null;
        this.isPseudo = this.isBlocked;

        this.nodes = new WalkPosition[Node.NODE_COUNT.ordinal()];
        this.nodes[Node.END1.ordinal()] = geometry.get(0);
        this.nodes[Node.END2.ordinal()] = geometry.get(geometry.size() - 1);

        this.nodesInArea = new ArrayList<>(Node.NODE_COUNT.ordinal());
        for (int i = 0; i < Node.NODE_COUNT.ordinal(); ++i) {
            this.nodesInArea.add(new MutablePair<>(new WalkPosition(0, 0), new WalkPosition(0, 0)));
        }

        int i = geometry.size() / 2;
        while ((i > 0)
                && (getMap().getData().getMiniTile(geometry.get(i - 1)).getAltitude().intValue()
                    > getMap().getData().getMiniTile(geometry.get(i)).getAltitude().intValue())) {
            --i;
        }
        while ((i < geometry.size() - 1)
                && (getMap().getData().getMiniTile(geometry.get(i + 1)).getAltitude().intValue()
                    > getMap().getData().getMiniTile(geometry.get(i)).getAltitude().intValue())) {
            ++i;
        }
        this.nodes[Node.MIDDLE.ordinal()] = geometry.get(i);

        for (int n = 0; n < Node.NODE_COUNT.ordinal(); ++n) {
            for (final Area area : new Area[]{area1, area2}) {
                final WalkPosition nodeInArea = getGraph().getMap().breadthFirstSearch(
                    this.nodes[n],
                        // findCond
                        args -> {
                            final Object ttile = args[0];
                            final Object tpos = args[1];
                            final Object tmap = args[args.length - 1];
                            if (ttile instanceof MiniTile && tpos instanceof WalkPosition && tmap instanceof Map) {
                                final MiniTile miniTile = (MiniTile) ttile;
                                final WalkPosition w = (WalkPosition) tpos;
                                final Map map = (Map) tmap;
                                return (miniTile.getAreaId().equals(area.getId()) && map.getData().getTile(w.toTilePosition(), CheckMode.NO_CHECK).getNeutral() == null);
                            } else {
                                throw new IllegalArgumentException();
                            }
                        },
                        // visitCond
                        args -> {
                            final Object ttile = args[0];
                            final Object tpos = args[1];
                            final Object tmap = args[args.length - 1];
                            if (ttile instanceof MiniTile && tpos instanceof WalkPosition) {
                                final MiniTile miniTile = (MiniTile) ttile;
                                final WalkPosition w = (WalkPosition) tpos;
                                final Map map = (Map) tmap;
                                return (miniTile.getAreaId().equals(area.getId()) || (isBlocked() && (((MiniTileImpl) miniTile).isBlocked() || map.getData().getTile(w.toTilePosition(), CheckMode.NO_CHECK).getNeutral() != null)));
                            } else {
                                throw new IllegalArgumentException("Invalid argument list.");
                            }
                        }
                );

                /**
                 * Note: In the original C++ code, "nodeInArea" is a reference to a "WalkPosition" in
                 * "nodesInArea" which changes! Change that object here (after the call to "breadthFirstSearch")...
                 */
                final WalkPosition left = nodesInArea.get(n).getLeft();
                final WalkPosition right = nodesInArea.get(n).getRight();
                final MutablePair<WalkPosition, WalkPosition> replacementPair = new MutablePair<>(left, right);
                if (area.equals(this.areas.getLeft())) {
                    replacementPair.setLeft(nodeInArea);
                } else {
                    replacementPair.setRight(nodeInArea);
                }
                this.nodesInArea.set(n, replacementPair);
            }
        }
    }

    public ChokePoint(final Graph graph, final Index index, final Area area1, final Area area2, final List<WalkPosition> geometry) {
        this(graph, index, area1, area2, geometry, null);
    }

    /**
     * Tells whether this ChokePoint is a pseudo ChokePoint, i.e., it was created on top of a blocking Neutral.
     */
	public boolean isPseudo() {
        return this.isPseudo;
    }

    /**
     * Returns the two areas of this ChokePoint.
     */
	public ImmutablePair<Area, Area> getAreas() {
        return this.areas;
    }

    /**
     * Returns the center of this ChokePoint.
     */
	public WalkPosition getCenter() {
        return getNodePosition(Node.MIDDLE);
    }

    /**
     * Returns the position of one of the 3 nodes of this ChokePoint (Cf. node definition).<br/>
     * - Note: the returned value is contained in geometry()
     */
    public WalkPosition getNodePosition(final Node node) {
//        bwem_assert(n < NODE_COUNT);
        if (!(node.ordinal() < Node.NODE_COUNT.ordinal())) {
            throw new IllegalArgumentException();
        }
        return this.nodes[node.ordinal()];
    }

    /**
     * Pretty much the same as pos(n), except that the returned MiniTile position is guaranteed to be part of pArea.
     * That is: Map::getArea(positionOfNodeInArea(n, pArea)) == pArea.
     */
    public WalkPosition getNodePositionInArea(final Node node, final Area area) {
//        bwem_assert((pArea == areas.getLeft()) || (pArea == areas.getRight()));
        if (!(area.equals(this.areas.getLeft()) || area.equals(this.areas.getRight()))) {
            throw new IllegalArgumentException();
        }
        return area.equals(areas.getLeft())
                ? this.nodesInArea.get(node.ordinal()).getLeft()
                : this.nodesInArea.get(node.ordinal()).getRight();
    }


    /**
     * Returns the set of positions that defines the shape of this ChokePoint.<br/>
     * - Note: none of these miniTiles actually belongs to this ChokePoint (a ChokePoint doesn't contain any MiniTile).
     * They are however guaranteed to be part of one of the 2 areas.<br/>
     * - Note: the returned set contains pos(middle), pos(END_1) and pos(END_2).
     * If isPseudo(), returns {p} where p is the position of a walkable MiniTile near from blockingNeutral()->pos().
     */
	public List<WalkPosition> getGeometry() {
        return this.geometry;
    }


    /**
     * If !isPseudo(), returns false.
     * Otherwise, returns whether this ChokePoint is considered blocked.
     * Normally, a pseudo ChokePoint either remains blocked, or switches to not isBlocked when blockingNeutral()
     * is destroyed and there is no remaining Neutral stacked with it.
     * However, in the case where Map::automaticPathUpdate() == false, blocked() will always return true
     * whatever blockingNeutral() returns.
     * Cf. Area::AccessibleNeighbors().
     */
	public boolean isBlocked() {
        return this.isBlocked;
    }

    /**
     * If !isPseudo(), returns nullptr.
     * Otherwise, returns a pointer to the blocking Neutral on top of which this pseudo ChokePoint was created,
     * unless this blocking Neutral has been destroyed.
     * In this case, returns a pointer to the next blocking Neutral that was stacked at the same location,
     * or nullptr if no such Neutral exists.
     */
	public Neutral getBlockingNeutral() {
        return this.blockingNeutral;
    }



    /**
     * If accessibleFrom(cp) == false, returns -1.
     * Otherwise, returns the ground distance in pixels between center() and cp->center().
     * - Note: if this == cp, returns 0.<br/>
     * - Time complexity: O(1)<br/>
     * - Note: Corresponds to the length in pixels of getPathTo(cp). So it suffers from the same lack of accuracy.
     * In particular, the value returned tends to be slightly higher than expected when getPathTo(cp).size() is high.
     */
	public int distanceFrom(final ChokePoint chokePoint) {
        return getGraph().distance(this, chokePoint);
    }

    /**
     * Returns whether this ChokePoint is accessible from cp (through a walkable path).<br/>
     * - Note: the relation is symmetric: this->accessibleFrom(cp) == cp->accessibleFrom(this)<br/>
     * - Note: if this == cp, returns true.<br/>
     * - Time complexity: O(1)<br/>
     */
	public boolean accessibleFrom(final ChokePoint chokePoint) {
        return (distanceFrom(chokePoint) >= 0);
    }



    /**
     * Returns a list of getChokePoints, which is intended to be the shortest walking path from this ChokePoint to cp.
     * The path always starts with this ChokePoint and ends with cp, unless accessibleFrom(cp) == false.
     * In this case, an empty list is returned.<br/>
     * - Note: if this == cp, returns [cp].<br/>
     * Time complexity: O(1)<br/>
     * To get the length of the path returned in pixels, use distanceFrom(cp).<br/>
     * - Note: all the possible Paths are precomputed during Map::initialize().<br/>
     * The best one is then stored for each pair of getChokePoints.
     * However, only the center of the getChokePoints is considered.
     * As a consequence, the returned path may not be the shortest one.
     */
    public CPPath getPathTo(ChokePoint cp) {
        return getGraph().getPath(this, cp);
    }

    private Map getMap() {
        return this.graph.getMap();
    }

    // Assumes pBlocking->removeFromTiles() has been called
    public void onBlockingNeutralDestroyed(Neutral pBlocking) {
//        bwem_assert(pBlocking && pBlocking->blocking());
        if (!(pBlocking != null && pBlocking.isBlocking())) {
            throw new IllegalStateException();
        }

        if (this.blockingNeutral.equals(pBlocking)) {
            // Ensures that in the case where several neutrals are stacked, blockingNeutral points to the bottom one:
            this.blockingNeutral = getMap().getData().getTile(this.blockingNeutral.getTopLeft()).getNeutral();

            if (this.blockingNeutral == null) {
                if (getGraph().getMap().automaticPathUpdate()) {
                    this.isBlocked = false;
                }
            }
        }
    }

	public Index getIndex() {
        return this.index;
    }

    public ChokePoint getPathBackTrace() {
        return this.pathBackTrace;
    }

	public void setPathBackTrace(final ChokePoint pathBackTrace) {
        this.pathBackTrace = pathBackTrace;
    }

    private Graph getGraph() {
        return this.graph;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ChokePoint)) {
            return false;
        } else {
            ChokePoint that = (ChokePoint) object;
            boolean lel = this.areas.getLeft().equals(that.areas.getLeft());
            boolean ler = this.areas.getLeft().equals(that.areas.getRight());
            boolean rer = this.areas.getRight().equals(that.areas.getRight());
            boolean rel = this.areas.getRight().equals(that.areas.getLeft());
            return (((lel && rer) || (ler && rel))); /* true if area pairs are an exact match or if one pair is reversed. */
        }
    }

    @Override
    public int hashCode() {
        int idLeft = areas.getLeft().getId().intValue();
        int idRight = areas.getRight().getId().intValue();
        if (idLeft > idRight) {
            int tmp = idLeft;
            idLeft = idRight;
            idRight = tmp;
        }
        return Objects.hash(idLeft, idRight);
    }

}
