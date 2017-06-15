package bwem;

import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;

public class Neutral {

    private Unit unit = null;
    private Position position = null;
    private TilePosition topLeft = null;
    private TilePosition size = null;
    private Map map = null;
    private Neutral nextStacked = null;
    private List<WalkPosition> blockedAreas = null;

    private Neutral() {}

    protected Neutral(Unit unit, Map pMap) {
        this.unit = unit;
        this.map = pMap;
        this.position = unit.getPosition();
        this.topLeft = this.position.toTilePosition();
//        if (this.m_bwapiType == UnitType.Special_Right_Pit_Door) {
//            this.position = this.position.add(new Position(1, 0));
//        }

        putOnTiles(); //TODO
    }

    public Unit getUnit() {
        return this.unit;
    }

    public Position getPosition() {
        return this.position;
    }

    public TilePosition getTopLeft() {
        return this.topLeft;
    }

    public TilePosition getBottomRight() {
        return this.topLeft.add(this.size).subtract(new TilePosition(1, 1));
    }

    public TilePosition getSize() {
        return this.size;
    }

    public boolean isBlocking() {
        return !this.blockedAreas.isEmpty();
    }

    public List<Area> getBlockedAreas() {
        List<Area> ret = new ArrayList<>();
        for (WalkPosition w : this.blockedAreas) {
            ret.add(this.map.getArea(w));
        }
        return ret;
    }

    public Neutral getNextStacked() {
        return this.nextStacked;
    }

    public Neutral getLastStacked() {
        Neutral last = getNextStacked();
        while (last != null) {
            last = last.getNextStacked();
        }
        return last;
    }

    public void setBlockingAreas(List<WalkPosition> blockedAreas) {
        this.blockedAreas = blockedAreas;
    }

    protected Map getMap() {
        return this.map;
    }

    //TODO
    private void putOnTiles() {
//        bwem_assert(!m_pNextStacked);
        if (this.nextStacked == null) {
            throw new IllegalStateException();
        }

        for (int dy = 0 ; dy < getSize().getY() ; ++dy)
        for (int dx = 0 ; dx < getSize().getX() ; ++dx)
        {
            Tile tile = this.map.getTile(getTopLeft().add(new TilePosition(dx, dy)));
            if (tile.getNeutral() != null)  {
                //TODO: What is this for or how does this work?
                //neutral.cpp:74:if (!tile.GetNeutral()) tile.AddNeutral(this);
                tile.setNeutral(this);
            } else {
                Neutral lastStacked = tile.getNeutral().getLastStacked();
                //TODO
//                bwem_assert(this != tile.GetNeutral());
//                bwem_assert(this != pTop);
//                bwem_assert(!pTop->IsGeyser());
//                bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName());
//                bwem_assert_plus(pTop->TopLeft() == TopLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->TopLeft()) + " / " + my_to_string(TopLeft()));
//                bwem_assert((dx == 0) && (dy == 0));
//
                lastStacked.nextStacked = this;
                return;
            }
        }
    }

}
