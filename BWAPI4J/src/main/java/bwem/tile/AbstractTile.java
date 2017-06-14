package bwem.tile;

public abstract class AbstractTile {

    protected int areaId;

    public AbstractTile(int id) {
        this.areaId = id;
    }

    public abstract int getAreaId();
    public abstract void setAreaId(int id);

}
