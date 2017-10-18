/*
Status: Incomplete
*/

package bwem.unit;

import bwem.map.Map;
import java.util.List;
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
    private List<WalkPosition> m_blockedAreas;

    protected Neutral(Unit u, Map pMap) {
        m_bwapiUnit = u;
        m_pMap = pMap;
        m_pos = u.getInitialPosition();
        m_topLeft = u.getInitialTilePosition();
        m_size = u.getInitialType().tileSize();

        //TODO:
//        if (u->getType() == Special_Right_Pit_Door) ++m_topLeft.x;


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

    protected Map GetMap() {
        return m_pMap;
    }

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

    //TODO
//	// If Blocking() == true, returns the set of Areas blocked by this Neutral.
//    vector<const Area *> Neutral::BlockedAreas() const
//    {
//        vector<const Area *> Result;
//        for (WalkPosition w : m_blockedAreas)
//            Result.push_back(GetMap()->GetArea(w));
//
//        return Result;
//    }

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
}
