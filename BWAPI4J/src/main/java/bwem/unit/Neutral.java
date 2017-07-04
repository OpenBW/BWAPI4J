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

    private Map map = null;
    private Unit unit = null;
    private TilePosition size = null;
    private Position position = null;
    private Neutral nextStacked = null;
    private List<WalkPosition> blockedAreas = null;

    private Neutral() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Neutral(Unit unit, Map map) {
        this.map = map;
        this.unit = unit;
        this.size = unit.getInitialType().tileSize();
        this.position = this.unit.getInitialPosition();
        this.blockedAreas = new ArrayList<>();

        putOnTiles();
    }

    public Unit getUnit() {
        return this.unit;
    }

    public TilePosition getSize() {
        return new TilePosition(this.size.getX(), this.size.getY());
    }

    public Position getPosition() {
        return new Position(this.position.getX(), this.position.getY());
    }

    public Neutral getNextStacked() {
        return this.nextStacked;
    }

    public Neutral getLastStacked() {
        Neutral ret = getNextStacked();
        while (ret != null) {
            ret = ret.getNextStacked();
        }
        return ret;
    }

    public boolean isBlocking() {
        return !this.blockedAreas.isEmpty();
    }

    public List<Area> getBlockedAreas() {
        List<Area> ret = new ArrayList<>();
        for (WalkPosition w : this.blockedAreas) {
            ret.add(this.map.getGraph().getArea(w));
        }
        return ret;
    }

    public void setBlocking(List<WalkPosition> blockedAreas) {
        this.blockedAreas = blockedAreas;
    }

    private void putOnTiles() {
//        bwem_assert(!m_pNextStacked);
        if (this.nextStacked != null) {
            throw new IllegalStateException();
        }

        for (int dy = 0 ; dy < this.size.getY() ; ++dy)
        for (int dx = 0 ; dx < this.size.getX() ; ++dx)
        {
            Tile tile = this.map.getTile(this.position.toTilePosition().add(new TilePosition(dx, dy)));
            if (tile.getNeutral() == null) {
                tile.setNeutral(this);
            } else {
//                bwem_assert(this != tile.GetNeutral());
//                bwem_assert(this != pTop);
//                bwem_assert(!pTop->IsGeyser());
//                bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName());
//                bwem_assert_plus(pTop->TopLeft() == TopLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->TopLeft()) + " / " + my_to_string(TopLeft()));
//                bwem_assert((dx == 0) && (dy == 0));
                if (this == tile.getNeutral()
                        || (tile.getNeutral().getUnit() instanceof VespeneGeyser)) {
                    throw new IllegalStateException();
                } else if (!(tile.getNeutral().getUnit().getClass().getName().equals(this.unit.getClass().getName()))) {
                    throw new IllegalStateException("Stacked neutrals have different types.");
                } else if (!tile.getNeutral().getPosition().equals(this.position)) {
                    throw new IllegalStateException("Stacked neutrals not aligned.");
                } else if ((dx != 0) || (dy != 0)) {
                    throw new IllegalStateException();
                } else {
                    tile.getNeutral().nextStacked = this;
                    return;
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Neutral)) {
            return false;
        } else {
            Unit oUnit = ((Neutral) o).getUnit();
            return (this.unit.getId() == oUnit.getId());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.unit.getId());
    }

}
