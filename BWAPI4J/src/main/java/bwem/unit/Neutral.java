package bwem.unit;

import bwem.Tile;
import bwem.map.Map;
import java.util.List;
import java.util.Objects;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;

public class Neutral {

    private final Unit unit;
    private final Map map;
    private Position position = null;
    private TilePosition topLeft = null;
    private TilePosition size = null;
    private Neutral nextStacked = null;
    private List<WalkPosition> blockedWalkPositions = null;

    protected Neutral(Unit unit, Map map) {
        this.unit = unit;
        this.map = map;
        this.position = this.unit.getInitialPosition();
        this.topLeft = this.unit.getInitialTilePosition();
        this.size = this.unit.getInitialType().tileSize();

        //TODO: if (u->getType() == Special_Right_Pit_Door) ++m_topLeft.x;

        putOnTiles();
    }

    // Returns the BWAPI::Unit this Neutral is wrapping around.
    public Unit getUnit() {
        return this.unit;
    }

    protected Map getMap() {
        return this.map;
    }

    // Returns the center of this Neutral, in pixels (same as Unit()->getInitialPosition()).
    public Position getPosition() {
        return new Position(this.position.getX(), this.position.getY());
    }

    // Returns the top left Tile position of this Neutral (same as Unit()->getInitialTilePosition()).
    public TilePosition getTopLeft() {
        return new TilePosition(this.topLeft.getX(), this.topLeft.getY());
    }

    // Returns the bottom right Tile position of this Neutral
    public TilePosition getBottomRight() {
        return this.topLeft.add(this.size).subtract(new TilePosition(1, 1));
    }

    // Returns the size of this Neutral, in Tiles (same as Type()->tileSize())
    public TilePosition getSize() {
        return new TilePosition(this.size.getX(), this.size.getY());
    }

	// Tells whether this Neutral is blocking some ChokePoint.
	// This applies to Minerals and StaticBuildings only.
	// For each blocking Neutral, a pseudo ChokePoint (which is Blocked()) is created on top of it,
	// with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is created.
	// Cf. definition of pseudo ChokePoints in class ChokePoint comment.
	// Cf. ChokePoint::BlockingNeutral and ChokePoint::Blocked.
    public boolean isBlocking() {
        return !this.blockedWalkPositions.isEmpty();
    }

    public void setBlockedWalkPositions(List<WalkPosition> blockedWalkPositions) {
//        bwem_assert(m_blockedAreas.empty() && !blockedAreas.empty());
        if (!(this.blockedWalkPositions.isEmpty() && !blockedWalkPositions.isEmpty())) {
            throw new IllegalStateException();
        } else {
            this.blockedWalkPositions = blockedWalkPositions;
        }
    }

	// Returns the next Neutral stacked over this Neutral, if ever.
	// To iterate through the whole stack, one can use the following:
	// for (const Neutral * n = Map::GetTile(TopLeft()).GetNeutral() ; n ; n = n->NextStacked())
    public Neutral getNextStacked() {
        return this.nextStacked;
    }

    // Returns the last Neutral stacked over this Neutral, if ever.
    public Neutral getLastStacked() {
        Neutral ret = getNextStacked();
        while (ret.getNextStacked() != null) {
            ret = ret.getNextStacked();
        }
        return ret;
    }

    private void putOnTiles() {
//        bwem_assert(!m_pNextStacked);
        if (this.nextStacked != null) {
            throw new IllegalStateException();
        }

        for (int dy = 0 ; dy < getSize().getY() ; ++dy)
        for (int dx = 0 ; dx < getSize().getX() ; ++dx) {
            Tile tile = getMap().getTile_(getTopLeft().add(new TilePosition(dx, dy)));
            if (tile.getOccupyingNeutral() == null) {
                tile.setOccupyingNeutral(this);
            } else {
                Neutral top = tile.getOccupyingNeutral().getLastStacked();
                if (this.equals(tile.getOccupyingNeutral())) {
//                    bwem_assert(this != tile.GetNeutral());
                    throw new IllegalStateException();
                } else if (this.equals(top)) {
//                    bwem_assert(this != pTop);
                    throw new IllegalStateException();
                } else if (top instanceof Geyser) {
//                    bwem_assert(!pTop->IsGeyser());
                    throw new IllegalStateException();
                } else if (!(tile.getOccupyingNeutral().isSameUnitTypeAs(this))) {
//                    bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName());
                    throw new IllegalStateException("stacked neutrals have different types");
                } else if (!(top.getTopLeft().equals(getTopLeft()))) {
//                    bwem_assert_plus(pTop->TopLeft() == TopLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->TopLeft()) + " / " + my_to_string(TopLeft()));
                    throw new IllegalStateException("stacked neutrals not aligned");
                } else if (!(dx == 0 && dy == 0)) {
//                    bwem_assert((dx == 0) && (dy == 0));
                    throw new IllegalStateException();
                } else {
                    top.nextStacked = this;
                    return;
                }
            }
        }
    }

    private void removeFromTiles() {
        for (int dy = 0; dy < getSize().getY(); ++dy)
        for (int dx = 0; dx < getSize().getX(); ++dx) {
            Tile tile = getMap().getTile_(getTopLeft().add(new TilePosition(dx, dy)));
//            bwem_assert(tile.GetNeutral());
            if (tile.getOccupyingNeutral() == null) {
                throw new IllegalStateException();
            }

            if (tile.getOccupyingNeutral().equals(this)) {
                tile.removeOccupyingNeutral(this);
                if (this.nextStacked != null) {
                    tile.setOccupyingNeutral(this.nextStacked);
                }
            } else {
                Neutral prevStacked = tile.getOccupyingNeutral();
                while (!prevStacked.getNextStacked().equals(this)) {
                    prevStacked = prevStacked.getNextStacked();
                }
//                bwem_assert(pPrevStacked->Type() == Type());
                if (!prevStacked.isSameUnitTypeAs(this)) {
                    throw new IllegalStateException();
                }
//                bwem_assert(pPrevStacked->TopLeft() == TopLeft());
                if (!(prevStacked.getTopLeft().equals(getTopLeft()))) {
                    throw new IllegalStateException();
                }
//                bwem_assert((dx == 0) && (dy == 0));
                if (!(dx == 0 && dy == 0)) {
                    throw new IllegalStateException();
                }

                prevStacked.nextStacked = this.nextStacked;
                this.nextStacked = null;
                return;
            }
        }

        this.nextStacked = null;
    }

    public boolean isSameUnitTypeAs(Neutral neutral) {
        return this.getUnit().getClass().getName().equals(neutral.getUnit().getClass().getName());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Neutral)) {
            throw new IllegalArgumentException("object is not an instance of Neutral");
        } else {
            Unit thatUnit = ((Neutral) object).getUnit();
            return (this.unit.getId() == thatUnit.getId());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.unit.getId());
    }

}
