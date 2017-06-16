package bwem;

import java.util.Objects;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.unit.Unit;

public class Neutral {

    private Map map = null;
    private Unit unit = null;
    private TilePosition size = null;
    private Position position = null;
    private Neutral nextStacked = null;

    private Neutral() {
        throw new IllegalArgumentException("Parameterless instantiation is prohibited.");
    }

    public Neutral(Unit unit, Map map) {
        this.map = map;
        this.unit = unit;
        this.size = unit.getInitialType().tileSize();
        this.position = this.unit.getPosition();
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
