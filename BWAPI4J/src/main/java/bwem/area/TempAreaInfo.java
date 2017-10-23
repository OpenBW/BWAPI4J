package bwem.area;

import bwem.Altitude;
import bwem.tile.MiniTile;
import org.openbw.bwapi4j.WalkPosition;

// Helper class for void Map::ComputeAreas()
// Maintains some information about an area being computed
// A TempAreaInfo is not Valid() in two cases:
//   - a default-constructed TempAreaInfo instance is never Valid (used as a dummy value to simplify the algorithm).
//   - any other instance becomes invalid when absorbed (see Merge)

public class TempAreaInfo {

    private boolean m_valid;
    private final AreaId m_id;
    private final WalkPosition m_top;
    private final Altitude m_highestAltitude;
    private int m_size;

    public TempAreaInfo() {
        m_valid = false;
        m_id = new AreaId(0);
        m_top = new WalkPosition(0, 0);
        m_highestAltitude = new Altitude(0);

//        bwem_assert(!Valid());
        if (Valid()) {
            throw new IllegalStateException();
        }
    }

    public TempAreaInfo(AreaId id, MiniTile pMiniTile, WalkPosition pos) {
        m_valid = true;
        m_id = id;
        m_top = pos;
        m_size = 0;
        m_highestAltitude = pMiniTile.Altitude();

        Add(pMiniTile);

//        { bwem_assert(Valid()); }
        if (!Valid()) {
            throw new IllegalStateException();
        }
    }

    public boolean Valid() {
        return m_valid;
    }

	public AreaId Id() {
//        bwem_assert(Valid());
        if (!Valid()) {
            throw new IllegalStateException();
        }
        return m_id;
    }

    public WalkPosition Top() {
//        { bwem_assert(Valid());
        if (!Valid()) {
            throw new IllegalStateException();
        }
        return m_top;
    }

    public int Size() {
//        bwem_assert(Valid());
        if (!Valid()) {
            throw new IllegalStateException();
        }
        return m_size;
    }

	public Altitude HighestAltitude() {
//        bwem_assert(Valid());
        if (!Valid()) {
            throw new IllegalStateException();
        }
        return m_highestAltitude;
    }

    public void Add(MiniTile pMiniTile) {
//        bwem_assert(Valid());
        if (!Valid()) {
            throw new IllegalStateException();
        }
        ++m_size;
        pMiniTile.SetAreaId(m_id);
    }

	// Left to caller : m.SetAreaId(this->Id()) for each MiniTile m in Absorbed
	public void Merge(TempAreaInfo Absorbed) {
        if (!(Valid() && Absorbed.Valid())) {
//            bwem_assert(Valid() && Absorbed.Valid());
            throw new IllegalStateException();
        } else if (!(m_size >= Absorbed.m_size)) {
//            bwem_assert(m_size >= Absorbed.m_size);
            throw new IllegalStateException();
        }
        m_size += Absorbed.m_size;
        Absorbed.m_valid = false;
    }

}
