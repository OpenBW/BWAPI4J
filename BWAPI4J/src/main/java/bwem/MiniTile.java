package bwem;

public class MiniTile {

    public static final int SIZE_IN_PIXELS = 8;

    private Area.Id areaId = null;
    private Altitude altitude = null;

    public MiniTile() {
        this.areaId = new Area.Id(-1);
        this.altitude = new Altitude(-1);
    }

    public Area.Id getAreaId() {
        return new Area.Id(this.areaId);
    }

    public void setAreaId(Area.Id areaId) {
//        { bwem_assert(AreaIdMissing() && (id >= 1)); m_areaId = id; }
        if (!isAreaIdMissing()) {
            throw new IllegalStateException("Area.Id is already set");
        } else if (!(areaId.intValue() >= 1)) {
            throw new IllegalArgumentException("invalid Area.Id");
        } else {
            this.areaId = new Area.Id(areaId);
        }
    }

    public void replaceAreaId(Area.Id areaId) {
//        { bwem_assert((m_areaId > 0) && ((id >= 1) || (id <= -2)) && (id != m_areaId)); m_areaId = id; }
        if (!((this.areaId.intValue() > 0)
                && ((areaId.intValue() >= 1) || (areaId.intValue() <= -2))
                && (areaId.intValue() != this.areaId.intValue()))) {
            throw new IllegalArgumentException("invalid Area.Id");
        } else {
            this.areaId = new Area.Id(areaId);
        }
    }

    public boolean isAreaIdMissing() {
        return (this.areaId.intValue() == -1);
    }

    public Altitude getAltitude() {
        return new Altitude(this.altitude);
    }

    public void setAltitude(Altitude altitude) {
//        { bwem_assert_debug_only(AltitudeMissing() && (a > 0)); m_altitude = a; }
        if (!(isAltitudeMissing() && (altitude.intValue() > 0))) {
            throw new IllegalStateException("Altitude is already set");
        } else if (!(altitude.intValue() > 0)) {
            throw new IllegalArgumentException("invalid Altitude");
        } else {
            this.altitude = new Altitude(altitude);
        }
    }

    public boolean isAltitudeMissing() {
        return (this.altitude.intValue() == -1);
    }

    public boolean isWalkable() {
        return (this.areaId.intValue() != 0);
    }

    public void setWalkable(boolean walkable) {
        this.areaId = new Area.Id(walkable ? -1 : 0);
        this.altitude = new Altitude(walkable ? -1 : 1);
    }

    public boolean isSea() {
        return (this.altitude.intValue() == 0);
    }

    public void setSea() {
//        { bwem_assert(!Walkable() && SeaOrLake()); m_altitude = 0; }
        if (!(!isWalkable() && isSeaOrLake())) {
            throw new IllegalStateException();
        } else {
            this.altitude = new Altitude(0);
        }
    }

    public boolean isLake() {
        return (this.altitude.intValue() != 0 && !isWalkable());
    }

    public void setLake() {
//        { bwem_assert(!Walkable() && Sea()); m_altitude = -1; }
        if (!(!isWalkable() && isSea())) {
            throw new IllegalStateException();
        } else {
            this.altitude = new Altitude(-1);
        }
    }

    public boolean isSeaOrLake() {
        return (this.altitude.intValue() == 1);
    }

    public boolean isTerrain() {
        return isWalkable();
    }

}
