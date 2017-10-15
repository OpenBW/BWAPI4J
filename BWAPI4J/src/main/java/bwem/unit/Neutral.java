package bwem.unit;

import bwem.Area;
import bwem.Map;
import bwem.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class Neutral {

    private final Unit unit;
    private Position position = null;
    private TilePosition topLeft = null;
    private TilePosition size = null;
    protected final Map map;
    private Neutral nextStacked = null;
    private List<WalkPosition> blockedWalkPositions = null;

    protected Neutral(Unit unit, Map map) {
        this.unit = unit;
        this.position = this.unit.getInitialPosition();
        this.topLeft = this.unit.getInitialTilePosition();
        this.size = unit.getInitialType().tileSize();
        this.map = map;
        this.blockedWalkPositions = new ArrayList<>();

        //TODO: Support "Special_Right_Pit_Door"?
//        neutral.cpp:38:if (u->getType() == Special_Right_Pit_Door) ++m_topLeft.x;

        putOnTiles();
    }

    public Unit getUnit() {
        return this.unit;
    }

    public Position getPosition() {
        return new Position(this.position.getX(), this.position.getY());
    }

    public TilePosition getTopLeft() {
        return this.topLeft;
    }

    public TilePosition getSize() {
        return new TilePosition(this.size.getX(), this.size.getY());
    }

    public boolean isBlocking() {
        return !this.blockedWalkPositions.isEmpty();
    }

    public List<Area> getBlockedWalkPositions() {
        List<Area> ret = new ArrayList<>();
        for (WalkPosition w : this.blockedWalkPositions) {
            ret.add(this.map.getGraph().getArea(w));
        }
        return ret;
    }

    public void setBlockedWalkPositions(List<WalkPosition> blockedWalkPositions) {
//        bwem_assert(m_blockedAreas.empty() && !blockedAreas.empty());
        if (!this.blockedWalkPositions.isEmpty()) {
            throw new IllegalStateException();
        } else if (blockedWalkPositions.isEmpty()) {
            throw new IllegalArgumentException("null");
        } else {
            this.blockedWalkPositions = blockedWalkPositions;
        }
    }

    public Neutral getNextStacked() {
        return this.nextStacked;
    }

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

        for (int dy = 0 ; dy < this.size.getY() ; ++dy)
        for (int dx = 0 ; dx < this.size.getX() ; ++dx) {
            Tile tile = this.map.getTile(getTopLeft().add(new TilePosition(dx, dy)));
            if (tile.getOccupyingNeutral() == null) {
                tile.setOccupyingNeutral(this);
            } else {
//                bwem_assert(this != tile.GetNeutral());
//                bwem_assert(this != pTop);
//                bwem_assert(!pTop->IsGeyser());
//                bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName());
//                bwem_assert_plus(pTop->TopLeft() == TopLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->TopLeft()) + " / " + my_to_string(TopLeft()));
//                bwem_assert((dx == 0) && (dy == 0));
                if (this == tile.getOccupyingNeutral()
                        || (tile.getOccupyingNeutral().getUnit() instanceof VespeneGeyser)) {
                    throw new IllegalStateException();
                } else if (!(tile.getOccupyingNeutral().getUnit().getClass().getName().equals(this.unit.getClass().getName()))) {
                    throw new IllegalStateException("Stacked neutrals have different types.");
                } else if (!tile.getOccupyingNeutral().getTopLeft().equals(getTopLeft())) {
                    throw new IllegalStateException("Stacked neutrals not aligned.");
                } else if ((dx != 0) || (dy != 0)) {
                    throw new IllegalStateException();
                } else {
                    tile.getOccupyingNeutral().nextStacked = this;
                    return;
                }
            }
        }
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
