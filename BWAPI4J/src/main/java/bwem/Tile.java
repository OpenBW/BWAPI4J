package bwem;

public class Tile {

    private Area.Id areaId = null;
    private Bits bits = null;
    private Neutral neutral = null;

    public Tile() {
        this.areaId = new Area.Id(0);
        this.bits = new Bits();
    }

    public Area.Id getAreaId() {
        return new Area.Id(this.areaId);
    }

    public void setAreaId(Area.Id areaId) {
        this.areaId = new Area.Id(areaId);
    }

    public void resetAreaId() {
        this.areaId = new Area.Id(0);
    }

    public boolean isBuildable() {
        return (this.bits.buildable != 0x0);
    }

    public void setBuildable() {
        this.bits.buildable = 0x1;
    }

    public int getGroundHeight() {
        return ((int) this.bits.groundHeight);
    }

    public void setGroundHeight(int height) {
//        { bwem_assert((0 <= h) && (h <= 2)); m_bits.groundHeight = h; }
        if (!((height >= 0) && (height <= 2))) {
            throw new IllegalArgumentException("invalid height: h=" + height);
        } else {
            this.bits.groundHeight = (byte) height;
        }
    }

    public boolean isDoodad() {
        return (this.bits.doodad != 0x0);
    }

    public void setDoodad() {
        this.bits.doodad = 0x1;
    }

    public Neutral getNeutral() {
        return this.neutral;
    }

    public void setNeutral(Neutral neutral) {
//        { bwem_assert(!m_pNeutral && pNeutral); m_pNeutral = pNeutral; }
        if (this.neutral != null) {
            throw new IllegalStateException("Neutral already set");
        } else if (neutral == null) {
            throw new IllegalArgumentException("neutral=null");
        } else {
            this.neutral = neutral;
        }
    }

    public void removeNeutral(Neutral neutral) {
//        { bwem_assert(pNeutral && (m_pNeutral == pNeutral)); utils::unused(pNeutral); m_pNeutral = nullptr; }
        if (this.neutral == null) {
            throw new IllegalStateException("Neutral is not set");
        } else if (!neutral.equals(this.neutral)) {
            throw new IllegalArgumentException("the specified Neutral does not match this object's Neutral");
        } else {
            this.neutral = null;
        }
    }

}
