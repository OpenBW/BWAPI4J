package bwem.unit;

import bwem.area.Area;
import bwem.map.Map;
import bwem.map.MapImpl;
import bwem.tile.Tile;
import java.util.ArrayList;
import java.util.List;

import bwem.tile.TileImpl;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Neutral
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Neutral is the abstract base class for a small hierarchy of wrappers around some BWAPI::Units
// The units concerned are the Ressources (Minerals and Geysers) and the static Buildings.
// Stacked Neutrals are supported, provided they share the same type at the same location.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public class Neutral {

    private final Unit bwapiUnit;
    private final Position pos;
    private final TilePosition topLeft;
    private final TilePosition size;
    private final Map map;
    private Neutral nextStacked = null;
    private List<WalkPosition> blockedAreas = new ArrayList<>();

    protected Neutral(Unit u, Map map) {
        bwapiUnit = u;
        this.map = map;
        pos = u.getInitialPosition();
        topLeft = u.getInitialTilePosition();
        size = u.getInitialType().tileSize();

        //TODO:
//        if (u->getType() == Special_Right_Pit_Door) ++topLeft.x;

        putOnTiles();
    }

    //protected:
//    Neutral::~Neutral()
//    {
//        try
//        {
//            removeFromTiles();
//
//            if (blocking())
//                MapImpl::Get(getMap())->onBlockingNeutralDestroyed(this);
//        }
//        catch(...)
//        {
//            bwem_assert(false);
//        }
//    }
    public void simulateCPPObjectDestructor() {
        removeFromTiles();

        if (isBlocking()) {
            Map map = getMap();
            if (map instanceof MapImpl) {
                MapImpl mapImpl = (MapImpl) map;
                mapImpl.onBlockingNeutralDestroyed(this);
            } else {
                throw new IllegalStateException("expected class: " + MapImpl.class.getName() + ", actual class: " + map.getClass().getName());
            }
        }
    }

    // Returns the BWAPI::unit this Neutral is wrapping around.
    public Unit getUnit() {
        return bwapiUnit;
    }

    // Returns the center of this Neutral, in pixels (same as unit()->getInitialPosition()).
    public Position getCenter() {
        return pos;
    }

    // Returns the top left Tile position of this Neutral (same as unit()->getInitialTilePosition()).
    public TilePosition getTopLeft() {
        return topLeft;
    }

    // Returns the bottom right Tile position of this Neutral
    public TilePosition getBottomRight() {
        return topLeft.add (size).subtract(new TilePosition(1, 1));
    }

    // Returns the size of this Neutral, in Tiles (same as Type()->tileSize())
    public TilePosition getSize() {
        return size;
    }

	// Tells whether this Neutral is blocking some ChokePoint.
	// This applies to minerals and StaticBuildings only.
	// For each blocking Neutral, a pseudo ChokePoint (which is blocked()) is created on top of it,
	// with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is created.
	// Cf. definition of pseudo getChokePoints in class ChokePoint comment.
	// Cf. ChokePoint::blockingNeutral and ChokePoint::blocked.
    public boolean isBlocking() {
        return !blockedAreas.isEmpty();
    }

	// If blocking() == true, returns the set of areas blocked by this Neutral.
    public List<Area> getBlockedAreas() {
        List<Area> result = new ArrayList<>();
        for (WalkPosition w : blockedAreas) {
            result.add(getMap().getArea(w));
        }

        return result;
    }

	// Returns the next Neutral stacked over this Neutral, if ever.
	// To iterate through the whole stack, one can use the following:
	// for (const Neutral * n = Map::GetTile(topLeft()).GetNeutral() ; n ; n = n->nextStacked())
    public Neutral getNextStacked() {
        return nextStacked;
    }

	// Returns the last Neutral stacked over this Neutral, if ever.
	public Neutral getLastStacked() {
        Neutral pTop = this;
        while (pTop .nextStacked != null) {
            pTop = pTop .nextStacked;
        }
        return pTop;
    }

    public void setBlocking(List<WalkPosition> blockedAreas) {
//        bwem_assert (blockedAreas.empty() && !blockedAreas.empty());
        if (! (this.blockedAreas.isEmpty() && !blockedAreas.isEmpty())) {
            throw new IllegalStateException();
        }
        this.blockedAreas = blockedAreas;
    }

    public boolean isSameUnitTypeAs(Neutral neutral) {
        return this.getUnit().getClass().getName().equals(neutral.getUnit().getClass().getName());
    }

    private void putOnTiles() {
//        bwem_assert(!pNextStacked);
        if (! (nextStacked == null)) {
            throw new IllegalStateException();
        }

        for (int dy = 0; dy < getSize().getY(); ++dy)
        for (int dx = 0; dx < getSize().getX(); ++dx) {
            Tile tile = getMap().getData().getTile_(getTopLeft().add(new TilePosition(dx, dy)));
            if (tile.getNeutral() == null) {
                ((TileImpl) tile).addNeutral(this);
            } else {
                Neutral pTop = tile.getNeutral().getLastStacked();
                if (this.equals(tile.getNeutral())) {
//                    bwem_assert(this != tile.GetNeutral());
                    throw new IllegalStateException();
                } else if (this.equals(pTop)) {
//                    bwem_assert(this != pTop);
                    throw new IllegalStateException();
                } else if (pTop.getClass().getName().equals(Geyser.class.getName())) {
//                    bwem_assert(!pTop->IsGeyser());
                    throw new IllegalStateException();
                } else if (!pTop.isSameUnitTypeAs(this)) {
//                    bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName());
                    throw new IllegalStateException("Stacked Neutral objects have different types: top=" + pTop.getClass().getName() + ", this=" + this.getClass().getName());
                } else if (!(pTop.getTopLeft().equals(getTopLeft()))) {
//                    bwem_assert_plus(pTop->topLeft() == topLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->topLeft()) + " / " + my_to_string(topLeft()));
                    throw new IllegalStateException("Stacked Neutral objects not aligned: top=" + pTop.toString() + ", this=" + getTopLeft().toString());
                } else if (!(dx == 0 && dy == 0)) {
//                    bwem_assert((dx == 0) && (dy == 0));
                    throw new IllegalStateException();
                }
                pTop .nextStacked = this;
                return;
            }
        }
    }

    /**
     * Warning: Do not use this function outside of BWEM's internals.
     * This method was originally private and designed to be called from the
     * "~Neutral" destructor in C++.
     */
    public void removeFromTiles() {
        for (int dy = 0; dy < getSize().getY(); ++dy)
        for (int dx = 0; dx < getSize().getX(); ++dx) {
            Tile tile = getMap().getData().getTile_(getTopLeft().add(new TilePosition(dx, dy)));
//            bwem_assert(tile.GetNeutral());
            if (tile.getNeutral() == null) {
                throw new IllegalStateException();
            }

            if (tile.getNeutral().equals(this)) {
                ((TileImpl) tile).removeNeutral(this);
                if  (nextStacked != null) {
                    ((TileImpl) tile).addNeutral (nextStacked);
                }
            } else {
                Neutral pPrevStacked = tile.getNeutral();
                while (!pPrevStacked.getNextStacked().equals(this)) {
                    pPrevStacked = pPrevStacked.getNextStacked();
                }
                if (!(pPrevStacked.isSameUnitTypeAs(this))) {
//                    bwem_assert(pPrevStacked->Type() == Type());
                    throw new IllegalStateException();
                } else if (!(pPrevStacked.getTopLeft().equals(getTopLeft()))) {
//                    bwem_assert(pPrevStacked->topLeft() == topLeft());
                    throw new IllegalStateException();
                } else if (!(dx == 0 && dy == 0)) {
//                    bwem_assert((dx == 0) && (dy == 0));
                    throw new IllegalStateException();
                }

                pPrevStacked .nextStacked = nextStacked;
                nextStacked = null;
                return;
            }
        }

        nextStacked = null;
    }

    protected Map getMap() {
        return map;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Neutral)) {
            return false;
        } else {
            Neutral that = (Neutral) object;
            return (this.getUnit().getId() == that.getUnit().getId());
        }
    }

    @Override
    public int hashCode() {
        return getUnit().hashCode();
    }

}
