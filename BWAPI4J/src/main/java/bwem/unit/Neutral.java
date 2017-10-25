package bwem.unit;

import bwem.area.Area;
import bwem.map.Map;
import bwem.tile.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    private final Unit m_bwapiUnit;
    private final Position m_pos;
    private TilePosition m_topLeft;
    private final TilePosition m_size;
    private final Map m_pMap;
    private Neutral m_pNextStacked = null;
    private List<WalkPosition> m_blockedAreas = new ArrayList<>();

    protected Neutral(Unit u, Map pMap) {
        m_bwapiUnit = u;
        m_pMap = pMap;
        m_pos = u.getInitialPosition();
        m_topLeft = u.getInitialTilePosition();
        m_size = u.getInitialType().tileSize();

        //TODO:
//        if (u->getType() == Special_Right_Pit_Door) ++m_topLeft.x;

        PutOnTiles();
    }

    //TODO:
    //protected:
//    Neutral::~Neutral()
//    {
//        try
//        {
//            RemoveFromTiles();
//
//            if (Blocking())
//                MapImpl::Get(GetMap())->OnBlockingNeutralDestroyed(this);
//        }
//        catch(...)
//        {
//            bwem_assert(false);
//        }
//    }

    // Returns the BWAPI::Unit this Neutral is wrapping around.
    public Unit Unit() {
        return m_bwapiUnit;
    }

    // Returns the center of this Neutral, in pixels (same as Unit()->getInitialPosition()).
    public Position Pos() {
        return m_pos;
    }

    // Returns the top left Tile position of this Neutral (same as Unit()->getInitialTilePosition()).
    public TilePosition TopLeft() {
        return m_topLeft;
    }

    // Returns the bottom right Tile position of this Neutral
    public TilePosition BottomRight() {
        return m_topLeft.add(m_size).subtract(new TilePosition(1, 1));
    }

    // Returns the size of this Neutral, in Tiles (same as Type()->tileSize())
    public TilePosition Size() {
        return m_size;
    }

	// Tells whether this Neutral is blocking some ChokePoint.
	// This applies to Minerals and StaticBuildings only.
	// For each blocking Neutral, a pseudo ChokePoint (which is Blocked()) is created on top of it,
	// with the exception of stacked blocking Neutrals for which only one pseudo ChokePoint is created.
	// Cf. definition of pseudo ChokePoints in class ChokePoint comment.
	// Cf. ChokePoint::BlockingNeutral and ChokePoint::Blocked.
    public boolean Blocking() {
        return !m_blockedAreas.isEmpty();
    }

	// If Blocking() == true, returns the set of Areas blocked by this Neutral.
    public List<Area> BlockedAreas() {
        List<Area> Result = new ArrayList<>();
        for (WalkPosition w : m_blockedAreas) {
            Result.add(GetMap().GetArea(w));
        }

        return Result;
    }

	// Returns the next Neutral stacked over this Neutral, if ever.
	// To iterate through the whole stack, one can use the following:
	// for (const Neutral * n = Map::GetTile(TopLeft()).GetNeutral() ; n ; n = n->NextStacked())
    public Neutral NextStacked() {
        return m_pNextStacked;
    }

	// Returns the last Neutral stacked over this Neutral, if ever.
	public Neutral LastStacked() {
        Neutral pTop = this;
        while (pTop.m_pNextStacked != null) {
            pTop = pTop.m_pNextStacked;
        }
        return pTop;
    }

    public void SetBlocking(List<WalkPosition> blockedAreas) {
//        bwem_assert(m_blockedAreas.empty() && !blockedAreas.empty());
        if (!(m_blockedAreas.isEmpty() && !blockedAreas.isEmpty())) {
            throw new IllegalStateException();
        }
        m_blockedAreas = blockedAreas;
    }

    public boolean isSameUnitTypeAs(Neutral neutral) {
        return this.Unit().getClass().getName().equals(neutral.Unit().getClass().getName());
    }

    private void PutOnTiles() {
//        bwem_assert(!m_pNextStacked);
        if (!(m_pNextStacked == null)) {
            throw new IllegalStateException();
        }

        for (int dy = 0; dy < Size().getY(); ++dy)
        for (int dx = 0; dx < Size().getX(); ++dx) {
            Tile tile = GetMap().GetTile_(TopLeft().add(new TilePosition(dx, dy)));
            if (tile.GetNeutral() == null) {
                tile.AddNeutral(this);
            } else {
                Neutral pTop = tile.GetNeutral().LastStacked();
                if (!(!this.equals(tile.GetNeutral()))) {
//                    bwem_assert(this != tile.GetNeutral());
                    throw new IllegalStateException();
                } else if (!(!this.equals(pTop))) {
//                    bwem_assert(this != pTop);
                    throw new IllegalStateException();
                } else if (!(!(pTop.getClass().getName().equals(Geyser.class.getName())))) {
//                    bwem_assert(!pTop->IsGeyser());
                    throw new IllegalStateException();
                } else if (!pTop.isSameUnitTypeAs(this)) {
//                    bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName());
                    throw new IllegalStateException("Stacked Neutral objects have different types: top=" + pTop.getClass().getName() + ", this=" + this.getClass().getName());
                } else if (!(pTop.TopLeft().equals(TopLeft()))) {
//                    bwem_assert_plus(pTop->TopLeft() == TopLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->TopLeft()) + " / " + my_to_string(TopLeft()));
                    throw new IllegalStateException("Stacked Neutral objects not aligned: top=" + pTop.toString() + ", this=" + TopLeft().toString());
                } else if (!(dx == 0 && dy == 0)) {
//                    bwem_assert((dx == 0) && (dy == 0));
                    throw new IllegalStateException();
                }
                pTop.m_pNextStacked = this;
                return;
            }
        }
    }

    private void RemoveFromTiles() {
        for (int dy = 0; dy < Size().getY(); ++dy)
        for (int dx = 0; dx < Size().getX(); ++dx) {
            Tile tile = GetMap().GetTile_(TopLeft().add(new TilePosition(dx, dy)));
//            bwem_assert(tile.GetNeutral());
            if (!(tile.GetNeutral() != null)) {
                throw new IllegalStateException();
            }

            if (tile.GetNeutral().equals(this)) {
                tile.RemoveNeutral(this);
                if (m_pNextStacked != null) {
                    tile.AddNeutral(m_pNextStacked);
                }
            } else {
                Neutral pPrevStacked = tile.GetNeutral();
                while (!pPrevStacked.NextStacked().equals(this)) {
                    pPrevStacked = pPrevStacked.NextStacked();
                }
                if (!(pPrevStacked.isSameUnitTypeAs(this))) {
//                    bwem_assert(pPrevStacked->Type() == Type());
                    throw new IllegalStateException();
                } else if (!(pPrevStacked.TopLeft().equals(TopLeft()))) {
//                    bwem_assert(pPrevStacked->TopLeft() == TopLeft());
                    throw new IllegalStateException();
                } else if (!(dx == 0 && dy == 0)) {
//                    bwem_assert((dx == 0) && (dy == 0));
                    throw new IllegalStateException();
                }

                pPrevStacked.m_pNextStacked = m_pNextStacked;
                m_pNextStacked = null;
                return;
            }
        }

        m_pNextStacked = null;
    }

    protected Map GetMap() {
        return m_pMap;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Neutral)) {
            throw new IllegalArgumentException("Object is not an instance of Neutral.");
        } else {
            Neutral that = (Neutral) object;
            return (this.Unit().getId() == that.Unit().getId());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.Unit().getId());
    }

}
