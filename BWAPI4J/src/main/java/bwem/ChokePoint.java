package bwem;

import bwem.map.Map;
import bwem.tile.MiniTileImpl;
import bwem.typedef.CPPath;
import bwem.typedef.Index;
import bwem.area.Area;
import bwem.tile.MiniTile;
import bwem.unit.Neutral;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.tuple.MutablePair;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class ChokePoint
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// ChokePoints are frontiers that BWEM automatically computes from Brood War's maps
// A ChokePoint represents (part of) the frontier between exactly 2 Areas. It has a form of line.
// A ChokePoint doesn't contain any MiniTile: All the MiniTiles whose positions are returned by its Geometry()
// are just guaranteed to be part of one of the 2 Areas.
// Among the MiniTiles of its Geometry, 3 particular ones called nodes can also be accessed using Pos(middle), Pos(end1) and Pos(end2).
// ChokePoints play an important role in BWEM:
//   - they define accessibility between Areas.
//   - the Paths provided by Map::GetPath are made of ChokePoints.
// Like Areas and Bases, the number and the addresses of ChokePoint instances remain unchanged.
//
// Pseudo ChokePoints:
// Some Neutrals can be detected as blocking Neutrals (Cf. Neutral::Blocking).
// Because only ChokePoints can serve as frontiers between Areas, BWEM automatically creates a ChokePoint
// for each blocking Neutral (only one in the case of stacked blocking Neutral).
// Such ChokePoints are called pseudo ChokePoints and they behave differently in several ways.
//
// ChokePoints inherit utils::Markable, which provides marking ability
// ChokePoints inherit utils::UserData, which provides free-to-use data.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public final class ChokePoint {

	// ChokePoint::middle denotes the "middle" MiniTile of Geometry(), while
	// ChokePoint::END_1 and ChokePoint::END_2 denote its "ends".
	// It is guaranteed that, among all the MiniTiles of Geometry(), ChokePoint::middle has the highest altitude value (Cf. MiniTile::Altitude()).
    public enum Node {
        END_1(0),
        MIDDLE(1),
        END_2(2),
        NODE_COUNT(3)
        ;

        private final int val;

        Node(int val) {
            this.val = val;
        }

        public int intVal() {
            return this.val;
        }
    }

    private final Graph graph;
    private final boolean pseudo;
    private final Index index;
    private final MutablePair<Area, Area> areas;
    private WalkPosition[] nodes;
    private List<MutablePair<WalkPosition, WalkPosition>> nodesInArea;
    private final List<WalkPosition> geometry;
    private boolean blocked;
    private Neutral blockingNeutral;
    private ChokePoint pathBackTrace = null;

    public ChokePoint(
            final Graph graph,
            final Index idx,
            final Area area1, Area area2,
            final List<WalkPosition> geometry,
            final Neutral blockingNeutral
    ) {
        this.graph = graph;
        index = idx;
        areas = new MutablePair<>(area1, area2);
        this.geometry = geometry;
        this.blockingNeutral = blockingNeutral;
        blocked = blockingNeutral != null;
        pseudo = blockingNeutral != null;

//        bwem_assert(!geometry.empty());
        if (geometry.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // Ensures that in the case where several neutrals are stacked, blockingNeutral points to the bottom one:
        if (this.blockingNeutral != null) {
            this.blockingNeutral = getMap().getData().getTile(this.blockingNeutral.getTopLeft()).getNeutral();
        }

        nodes = new WalkPosition[ChokePoint.Node.NODE_COUNT.intVal()];
        nodes[ChokePoint.Node.END_1.intVal()] = geometry.get(0);
        nodes[ChokePoint.Node.END_2.intVal()] = geometry.get(geometry.size() - 1);

        nodesInArea = new ArrayList<>(ChokePoint.Node.NODE_COUNT.intVal());
        for (int i = 0; i < ChokePoint.Node.NODE_COUNT.intVal(); ++i) {
            nodesInArea.add(new MutablePair<>(new WalkPosition(0, 0), new WalkPosition(0, 0)));
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
        nodes[ChokePoint.Node.MIDDLE.intVal()] = geometry.get(i);

        for (int n = 0; n < ChokePoint.Node.NODE_COUNT.intVal(); ++n) {
            final List<Area> tmpAreaList = new ArrayList<>();
            tmpAreaList.add(area1);
            tmpAreaList.add(area2);
            for (final Area pArea : tmpAreaList) {
                // findCond
                // visitCond
                final WalkPosition nodeInArea = getGraph().getMap().breadthFirstSearch(
                    nodes[n],
                        args -> {
                            Object ttile = args[0];
                            Object tpos = args[1];
                            Object tmap = args[args.length - 1];
                            if (ttile instanceof MiniTile && tpos instanceof WalkPosition && tmap instanceof Map) {
                                MiniTile miniTile = (MiniTile) ttile;
                                WalkPosition w = (WalkPosition) tpos;
                                TilePosition t = (w.toPosition()).toTilePosition();
                                Map map = (Map) tmap;
                                return (miniTile.getAreaId().equals(pArea.getId()) && map.getData().getTile(t, CheckMode.NO_CHECK).getNeutral() == null);
                            } else {
                                throw new IllegalArgumentException();
                            }
                        },
                        args -> {
                            Object ttile = args[0];
                            Object tpos = args[1];
                            Object tmap = args[args.length - 1];
                            if (ttile instanceof MiniTile && tpos instanceof WalkPosition) {
                                MiniTile miniTile = (MiniTile) ttile;
                                WalkPosition w = (WalkPosition) tpos;
                                TilePosition t = (w.toPosition()).toTilePosition();
                                Map map = (Map) tmap;
                                return (miniTile.getAreaId().equals(pArea.getId()) || (isBlocked() && (((MiniTileImpl) miniTile).isBlocked() || map.getData().getTile(t, CheckMode.NO_CHECK).getNeutral() != null)));
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
                final MutablePair<WalkPosition, WalkPosition> replacementPair
                        = new MutablePair<>(new WalkPosition(left.getX(), left.getY()), new WalkPosition(right.getX(), right.getY()));
                if (pArea.equals(areas.getLeft())) {
                    replacementPair.setLeft(nodeInArea);
                } else {
                    replacementPair.setRight(nodeInArea);
                }
                nodesInArea.set(n, replacementPair);
            }
        }
    }

    public ChokePoint(Graph graph, Index idx, Area area1, Area area2, List<WalkPosition> geometry) {
        this(graph, idx, area1, area2, geometry, null);
    }

	// Tells whether this ChokePoint is a pseudo ChokePoint, i.e., it was created on top of a blocking Neutral.
	public boolean isPseudo() {
        return pseudo;
    }

	// Returns the two areas of this ChokePoint.
	public MutablePair<Area, Area> getAreas() {
        return areas;
    }

	// Returns the center of this ChokePoint.
	public WalkPosition getCenter() {
        return positionOfNode(Node.MIDDLE);
    }

	// Returns the position of one of the 3 nodes of this ChokePoint (Cf. node definition).
	// Note: the returned value is contained in geometry()
    public WalkPosition positionOfNode(Node n) {
//        bwem_assert(n < NODE_COUNT);
        if (!(n.intVal() < Node.NODE_COUNT.intVal())) {
            throw new IllegalArgumentException();
        }
        return nodes[n.intVal()];
    }

	// Pretty much the same as pos(n), except that the returned MiniTile position is guaranteed to be part of pArea.
	// That is: Map::getArea(positionOfNodeInArea(n, pArea)) == pArea.
    public WalkPosition positionOfNodeInArea(Node n, Area pArea) {
//        bwem_assert((pArea == areas.getLeft()) || (pArea == areas.getRight()));
        if (!(pArea.equals(areas.getLeft()) || pArea.equals(areas.getRight()))) {
            throw new IllegalArgumentException();
        }
        return pArea.equals(areas.getLeft())
                ? nodesInArea.get(n.intVal()).getLeft()
                : nodesInArea.get(n.intVal()).getRight();
    }

	// Returns the set of positions that defines the shape of this ChokePoint.
	// Note: none of these miniTiles actually belongs to this ChokePoint (a ChokePoint doesn't contain any MiniTile).
	//       They are however guaranteed to be part of one of the 2 areas.
	// Note: the returned set contains pos(middle), pos(END_1) and pos(END_2).
	// If isPseudo(), returns {p} where p is the position of a walkable MiniTile near from blockingNeutral()->pos().
	public List<WalkPosition> getGeometry() {
        return geometry;
    }

	// If !isPseudo(), returns false.
	// Otherwise, returns whether this ChokePoint is considered blocked.
	// Normally, a pseudo ChokePoint either remains blocked, or switches to not blocked when blockingNeutral()
	// is destroyed and there is no remaining Neutral stacked with it.
	// However, in the case where Map::automaticPathUpdate() == false, blocked() will always return true
	// whatever blockingNeutral() returns.
	// Cf. Area::AccessibleNeighbors().
	public boolean isBlocked() {
        return blocked;
    }

	// If !isPseudo(), returns nullptr.
	// Otherwise, returns a pointer to the blocking Neutral on top of which this pseudo ChokePoint was created,
	// unless this blocking Neutral has been destroyed.
	// In this case, returns a pointer to the next blocking Neutral that was stacked at the same location,
	// or nullptr if no such Neutral exists.
	public Neutral getBlockingNeutral() {
        return blockingNeutral;
    }

	// If accessibleFrom(cp) == false, returns -1.
	// Otherwise, returns the ground distance in pixels between center() and cp->center().
	// Note: if this == cp, returns 0.
	// Time complexity: O(1)
	// Note: Corresponds to the length in pixels of getPathTo(cp). So it suffers from the same lack of accuracy.
	//       In particular, the value returned tends to be slightly higher than expected when getPathTo(cp).size() is high.
	public int distanceFrom(ChokePoint cp) {
        return getGraph().distance(this, cp);
    }

	// Returns whether this ChokePoint is accessible from cp (through a walkable path).
	// Note: the relation is symmetric: this->accessibleFrom(cp) == cp->accessibleFrom(this)
	// Note: if this == cp, returns true.
	// Time complexity: O(1)
	public boolean accessibleFrom(ChokePoint cp) {
        return (distanceFrom(cp) >= 0);
    }

	// Returns a list of getChokePoints, which is intended to be the shortest walking path from this ChokePoint to cp.
	// The path always starts with this ChokePoint and ends with cp, unless accessibleFrom(cp) == false.
	// In this case, an empty list is returned.
	// Note: if this == cp, returns [cp].
	// Time complexity: O(1)
	// To get the length of the path returned in pixels, use distanceFrom(cp).
	// Note: all the possible Paths are precomputed during Map::initialize().
	//       The best one is then stored for each pair of getChokePoints.
	//       However, only the center of the getChokePoints is considered.
	//       As a consequence, the returned path may not be the shortest one.
    public CPPath getPathTo(ChokePoint cp) {
        return getGraph().getPath(this, cp);
    }

    public Map getMap() {
        return graph.getMap();
    }

    // Assumes pBlocking->removeFromTiles() has been called
    public void onBlockingNeutralDestroyed(Neutral pBlocking) {
//        bwem_assert(pBlocking && pBlocking->blocking());
        if (!(pBlocking != null && pBlocking.isBlocking())) {
            throw new IllegalStateException();
        }

        if (blockingNeutral.equals(pBlocking)) {
            // Ensures that in the case where several neutrals are stacked, blockingNeutral points to the bottom one:
            blockingNeutral = getMap().getData().getTile(blockingNeutral.getTopLeft()).getNeutral();

            if (blockingNeutral == null) {
                if (getGraph().getMap().automaticPathUpdate()) {
                    blocked = false;
                }
            }
        }
    }

	public Index getIndex() {
        return index;
    }

    public ChokePoint getPathBackTrace() {
        return pathBackTrace;
    }

	public void setPathBackTrace(ChokePoint p) {
        pathBackTrace = p;
    }

    private Graph getGraph() {
        return graph;
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
