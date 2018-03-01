package bwem.area;

import bwem.Base;
import bwem.ChokePoint;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.typedef.Altitude;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AreaImpl implements Area {

    private final AreaId id;
    protected GroupId groupId = new GroupId(0);
    private final WalkPosition walkPositionWithHighestAltitude;
    protected Altitude highestAltitude;
    protected TilePosition topLeft = new TilePosition(Integer.MAX_VALUE, Integer.MAX_VALUE);
    protected TilePosition bottomRight = new TilePosition(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private final int miniTileCount;
    protected int tileCount = 0;
    protected int buildableTileCount = 0; /* Set and later incremented but not used in original C++ BWEM 1.4.1. Remains for portability consistency. */
    protected int highGroundTileCount = 0;
    protected int veryHighGroundTileCount = 0;
    protected final java.util.Map<Area, List<ChokePoint>> chokePointsByArea = new HashMap<>();
    protected final List<Area> accessibleNeighbors = new ArrayList<>();
    protected final List<ChokePoint> chokePoints = new ArrayList<>();
    protected final List<Mineral> minerals = new ArrayList<>();
    protected final List<Geyser> geysers = new ArrayList<>();
    protected final List<Base> bases = new ArrayList<>();

    protected AreaImpl(final AreaId areaId, final WalkPosition top, final int miniTileCount) {
        this.id = areaId;
        this.walkPositionWithHighestAltitude = top;
        this.miniTileCount = miniTileCount;
    }

    @Override
    public AreaId getId() {
        return this.id;
    }

    @Override
    public GroupId getGroupId() {
        return this.groupId;
    }

    @Override
    public TilePosition getTopLeft() {
        return this.topLeft;
    }

    @Override
    public TilePosition getBottomRight() {
        return this.bottomRight;
    }

    @Override
    public TilePosition getBoundingBoxSize() {
        return this.bottomRight.subtract(this.topLeft).add(new TilePosition(1, 1));
    }

    @Override
    public WalkPosition getWalkPositionWithHighestAltitude() {
        return this.walkPositionWithHighestAltitude;
    }

    @Override
    public Altitude getHighestAltitude() {
        return this.highestAltitude;
    }

    @Override
    public int getSize() {
        return this.miniTileCount;
    }

    @Override
    public int getLowGroundPercentage() {
        final int lowGroundTileCount = this.tileCount - this.highGroundTileCount - this.veryHighGroundTileCount;
        return ((lowGroundTileCount * 100) / this.tileCount);
    }

    @Override
    public int getHighGroundPercentage() {
        return ((this.highGroundTileCount * 100) / this.tileCount);
    }

    @Override
    public int getVeryHighGroundPercentage() {
        return ((this.veryHighGroundTileCount * 100) / tileCount);
    }

    @Override
    public List<ChokePoint> getChokePoints() {
        return this.chokePoints;
    }

    @Override
    public List<ChokePoint> getChokePoints(final Area area) {
        final List<ChokePoint> ret = this.chokePointsByArea.get(area);
//        bwem_assert(it != getChokePointsByArea.end());
        if (ret == null) {
            throw new IllegalArgumentException();
        }
        return ret;
    }

    @Override
    public java.util.Map<Area, List<ChokePoint>> getChokePointsByArea() {
        return this.chokePointsByArea;
    }

    @Override
    public List<Area> getAccessibleNeighbors() {
        return this.accessibleNeighbors;
    }

    @Override
    public boolean isAccessibleFrom(final Area area) {
        return getGroupId().equals(area.getGroupId());
    }

    @Override
    public List<Mineral> getMinerals() {
        return this.minerals;
    }

    @Override
    public List<Geyser> getGeysers() {
        return this.geysers;
    }

    @Override
    public List<Base> getBases() {
        return this.bases;
    }

    public void onMineralDestroyed(final Mineral mineral) {
//        bwem_assert(mineral);
        if (mineral == null) {
            throw new IllegalArgumentException();
        }

        this.minerals.remove(mineral);

        // let's examine the bases even if mineral was not found in this Area,
        // which could arise if minerals were allowed to be assigned to neighboring areas.
        for (final Base base : getBases()) {
            base.onMineralDestroyed(mineral);
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Area)) {
            return false;
        } else {
            final Area that = (Area) object;
            return (getId().equals(that.getId()));
        }
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

}
