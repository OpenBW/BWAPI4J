package bwem.unit;

import bwem.Area;
import bwem.map.Map;
import java.util.ArrayList;
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

        //TODO: PutOnTiles();
        throw new UnsupportedOperationException("todo");
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

    //TODO
    //TODO: getArea needs Graph
    // If Blocking() == true, returns the set of Areas blocked by this Neutral.
//    public List<Area> getBlockedWalkPositions() {
//        List<Area> ret = new ArrayList<>();
//        for (WalkPosition w : this.blockedAreas) {
//
//        }
//
//        throw new UnsupportedOperationException("todo");
//    }

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
