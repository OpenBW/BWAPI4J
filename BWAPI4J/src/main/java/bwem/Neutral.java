package bwem;

import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

public class Neutral {

    private Unit m_bwapiUnit = null;
    private UnitType m_bwapiType = null;
    private Position m_pos = null;
    private TilePosition m_topLeft = null;
    private TilePosition m_size = null;
    private Map m_pMap = null;
    private Neutral m_pNextStacked = null;
    private List<WalkPosition> m_blockedAreas = null;

    private Neutral() {}

    protected Neutral(Unit unit, Map pMap) {
        this.m_bwapiUnit = unit;
        this.m_bwapiType = unit.getInitialType();
        this.m_pMap = pMap;
        this.m_pos = unit.getInitialPosition();
        this.m_topLeft = unit.getInitialTilePosition();

        if (this.m_bwapiType == UnitType.Special_Right_Pit_Door) {
            this.m_pos = this.m_pos.add(new Position(1, 0));
        }

        PutOnTiles(); //TODO
    }

    public Unit Unit() {
        return this.m_bwapiUnit;
    }

    public UnitType Type() {
        return this.m_bwapiType;
    }

    public Position Pos() {
        return this.m_pos;
    }

    public TilePosition TopLeft() {
        return this.m_topLeft;
    }

    public TilePosition BottomRight() {
        return this.m_topLeft.add(m_size).subtract(new TilePosition(1, 1));
    }

    public TilePosition Size() {
        return this.m_size;
    }

    public boolean Blocking() {
        return !this.m_blockedAreas.isEmpty();
    }

    public List<WalkPosition> BlockedAreas() {
        return this.m_blockedAreas;
    }

    public Neutral NextStacked() {
        return this.m_pNextStacked;
    }

//    Neutral LastStacked() { Neutral * pTop = this; while (pTop->m_pNextStacked) pTop = pTop->m_pNextStacked; return pTop; }
    public Neutral LastStacked() {
        Neutral pTop = NextStacked();
        while (pTop != null) {
            pTop = pTop.NextStacked();
        }
        return pTop;
    }

    public void SetBlocking(List<WalkPosition> blockedAreas) {
        this.m_blockedAreas = blockedAreas;
    }

    protected Map GetMap() {
        return this.m_pMap;
    }

    //TODO
    private void PutOnTiles() {
//        bwem_assert(!m_pNextStacked); //TODO: next stacked

        for (int dy = 0 ; dy < Size().getY() ; ++dy)
        for (int dx = 0 ; dx < Size().getX() ; ++dx)
        {
            Tile tile = this.m_pMap.GetTile(TopLeft().add(new TilePosition(dx, dy)));
            if (tile.GetNeutral() != null) tile.AddNeutral(this);
            else
            {
                Neutral pTop = tile.GetNeutral().LastStacked();
                //TODO
//                bwem_assert(this != tile.GetNeutral());
//                bwem_assert(this != pTop);
//                bwem_assert(!pTop->IsGeyser());
//                bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName());
//                bwem_assert_plus(pTop->TopLeft() == TopLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->TopLeft()) + " / " + my_to_string(TopLeft()));
//                bwem_assert((dx == 0) && (dy == 0));
//
                pTop.m_pNextStacked = this;
                return;
            }
        }
    }

}
