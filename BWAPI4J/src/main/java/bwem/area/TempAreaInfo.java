package bwem.area;

import bwem.area.typedef.AreaId;
import bwem.tile.MiniTileImpl;
import bwem.typedef.Altitude;
import bwem.tile.MiniTile;
import org.openbw.bwapi4j.WalkPosition;

// Helper class for void Map::ComputeAreas()
// Maintains some information about an area being computed
// A TempAreaInfo is not Valid() in two cases:
//   - a default-constructed TempAreaInfo instance is never Valid (used as a dummy value to simplify the algorithm).
//   - any other instance becomes invalid when absorbed (see Merge)

public class TempAreaInfo {

    private boolean valid;
    private final AreaId id;
    private final WalkPosition top;
    private final Altitude highestAltitude;
    private int size;

    public TempAreaInfo() {
        valid = false;
        id = new AreaId(0);
        top = new WalkPosition(0, 0);
        highestAltitude = new Altitude(0);

//        bwem_assert(!valid());
        if (isValid()) {
            throw new IllegalStateException();
        }
    }

    public TempAreaInfo(final boolean valid, final AreaId areaId, final WalkPosition top, final Altitude highestAltitude, final int size) {
        this.valid = valid;
        this.id = new AreaId(areaId);
        this.top = top;
        this.highestAltitude = new Altitude(highestAltitude);
        this.size = size;
    }

    public TempAreaInfo(AreaId id, MiniTile pMiniTile, WalkPosition pos) {
        valid = true;
        this.id = id;
        top = pos;
        size = 0;
        highestAltitude = pMiniTile.getAltitude();

        add(pMiniTile);

//        { bwem_assert(valid()); }
        if (!isValid()) {
            throw new IllegalStateException();
        }
    }

    public boolean isValid() {
        return valid;
    }

	public AreaId getId() {
//        bwem_assert(valid());
        if (!isValid()) {
            throw new IllegalStateException();
        }
        return id;
    }

    public WalkPosition getTop() {
//        { bwem_assert(valid());
        if (!isValid()) {
            throw new IllegalStateException();
        }
        return top;
    }

    public int getSize() {
//        bwem_assert(valid());
        if (!isValid()) {
            throw new IllegalStateException();
        }
        return size;
    }

	public Altitude getHighestAltitude() {
//        bwem_assert(valid());
        if (!isValid()) {
            throw new IllegalStateException();
        }
        return highestAltitude;
    }

    public void add(MiniTile pMiniTile) {
//        bwem_assert(valid());
        if (!isValid()) {
            throw new IllegalStateException();
        }
        ++size;
        ((MiniTileImpl) pMiniTile).setAreaId (id);
    }

	// Left to caller : m.SetAreaId(this->id()) for each MiniTile m in absorbed
	public void merge(TempAreaInfo absorbed) {
        if (!(isValid() && absorbed.isValid())) {
//            bwem_assert(valid() && absorbed.valid());
            throw new IllegalStateException();
        } else if (! (size >= absorbed .size)) {
//            bwem_assert (size >= absorbed .size);
            throw new IllegalStateException();
        }
        size += absorbed .size;
        absorbed .valid = false;
    }

}
